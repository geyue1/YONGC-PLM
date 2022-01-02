package com.ptc.windchill.partslink.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wt.facade.suma.SumaFacade;
import wt.fc.ObjectReference;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.httpgw.URLFactory;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.InstalledProperties;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.PartslinkConstants.SearchType;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.facet.Facet;
import com.ptc.windchill.partslink.facet.StatsInfo;
import com.ptc.windchill.partslink.fqp.FilterQueryProducer;
import com.ptc.windchill.partslink.fqp.FilterQueryProducerFactory;
import com.ptc.windchill.partslink.model.MatchedAttribute;
import com.ptc.windchill.partslink.model.MatchedClass;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * Implementation class for RefineModelService.
 */
public class RefineModelServiceImpl implements RefineModelService {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(RefineModelServiceImpl.class.getName());

    /**
     * Method to create refine model.
     * 
     * @param nodeInternalName
     *            - internal name of the classification node.
     * @param request
     *            - the HttpServletRequest.
     * @throws WTException
     */
    @Override
    public RefineModel getRefineModel(String nodeInternalName, HttpServletRequest request) throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled) {
            logger.debug("Building Refine model for " + nodeInternalName);
        }
        if (nodeInternalName == null || nodeInternalName.trim().isEmpty()) {
            nodeInternalName = PartslinkPropertyModel.getInstance().getDefaultBrowseRootNode();
            if (isDebugEnabled) {
                logger.debug("Fetched default root node as " + nodeInternalName);
            }
        }
        if (nodeInternalName == null || nodeInternalName.trim().isEmpty()) {
            nodeInternalName = PartsLinkUtils.getClfRootNodeName();
            if (isDebugEnabled) {
                logger.debug("Getting root node for browse: " + nodeInternalName);
            }
        }
        if (nodeInternalName == null || nodeInternalName.trim().isEmpty()) {
            if (isDebugEnabled) {
                logger.debug("Could not get node to browse. Returning null ");
            }
            return null;
        }
        return createRefineModel(nodeInternalName, request);
    }

    /**
     * Method to create refine model.
     * 
     * @param nodeInternalName
     *            - the node internal name
     * @param request
     *            - the HttpServletRequest
     * @throws WTException
     */
    private RefineModel createRefineModel(String nodeInternalName, HttpServletRequest request) throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled) {
            logger.debug("Creating model for  " + nodeInternalName);
        }

        NmCommandBean commandBean = PartsLinkUtils.getNmCommandBean(request);

        TypeDefinitionReadView typeDefReadView = CSMTypeDefHelper.getClassificationTypeDefView(nodeInternalName);
        Locale locale = commandBean.getLocale();
        if (locale == null) {
            locale = SessionHelper.getLocale();
        }

        RefineModel model = null;
        if (typeDefReadView != null) {
            model = new RefineModel();
            model.setClassInternalName(nodeInternalName);
            model.setTypeDefRV(typeDefReadView);
            model.setNodeDisplayName(PropertyHolderHelper.getDisplayName(typeDefReadView, locale));
            // set refine bean set
            SortedSet<RefineBean> refineBeanSet = getRefineBeanSet(typeDefReadView, commandBean);
            model.setRefineBeanSet(refineBeanSet);
            model.setLocale(locale);

            if (typeDefReadView.getPropertyValueByName("schematicPath") != null) {
                URLFactory urlFactory = new URLFactory();
                String schematicUrl = typeDefReadView.getPropertyValueByName("schematicPath").getValueAsString(locale,
                        true);
                model.setRefineSchematicURL(schematicUrl.substring(urlFactory.getBaseHREF().length()));
            } else {
                model.setRefineSchematicURL("");
            }

            validateRefineModel(model);
        }

        return model;
    }

    /**
     * Method to validate the input refine model. This method sets the error flag in the model if there is error in any
     * of the Refine Bean within the model.
     * 
     * @param refineModel
     *            - refine model.
     */
    @Override
    public void validateRefineModel(RefineModel refineModel) {
        if (refineModel != null) {
            SortedSet<RefineBean> refineBeanSet = refineModel.getRefineBeanSet();
            for (RefineBean refineBean : refineBeanSet) {
                if (refineBean.isError()) {
                    refineModel.setError(true);
                    String errorMessage = WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
                            partslinkClientResource.ERROR_NO_RESULT, null, refineModel.getLocale());
                    refineModel.setErrorMessage(errorMessage);
                    break;
                }
            }
        }
    }

    /**
     * Gets the refine bean set sorted by display names alphabetically.
     * 
     * @param typeVw
     *            - the type definition read view of classification node.
     * @param commandBean
     *            - the command bean.
     * @return the refine bean set
     * @throws WTException
     */
    private static SortedSet<RefineBean> getRefineBeanSet(TypeDefinitionReadView typeVw, NmCommandBean commandBean)
            throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled) {
            logger.debug("IN RefineModelServiceImpl.getRefineBeanSet() for TypeDef Display Name :"
                    + typeVw.getName());
        }

        // Alphabetically sorted refine bean set
        SortedSet<RefineBean> refineBeanSet = new TreeSet<RefineBean>(new RefineSearchBeanComparator());
        Collection<AttributeDefinitionReadView> typeAttributes = typeVw.getAllAttributes();

        // Iterate through collection and populate RefineSearchBean for each of the attribute type definition read view
        for (Iterator iterator = typeAttributes.iterator(); iterator.hasNext();) {
            AttributeDefinitionReadView attributeDefinitionReadView = (AttributeDefinitionReadView) iterator.next();
            // create refine bean
            RefineBean refineBean = RefineBeanService.createAndValidateRefineBean(attributeDefinitionReadView,
                    commandBean, null, true);
            if (isDebugEnabled) {
                logger.debug("Refine Bean created is : " + refineBean);
            }
            // Add to set
            refineBeanSet.add(refineBean);
        }

        // Adding the name, number attributes to refine model
        refineBeanSet.add(RefineBeanService.createNameRefineBean(commandBean, true));
        refineBeanSet.add(RefineBeanService.createNumberRefineBean(commandBean, true));

        if (InstalledProperties.isInstalled(InstalledProperties.SUMA)) {
            // Adding sourcing status attribute to refine model in case SUMA is installed.
            refineBeanSet.add(RefineBeanService.createSourcingStatusRefineBean(commandBean));
        }

        if (isDebugEnabled) {
            logger.debug("OUT PopulateRefineSearchBean.getRefineAttrDefReadVwCollection()");
        }
        return refineBeanSet;
    }

    /**
     * Method to update refine model from the input result model.
     * 
     * @param refineModel
     * @param resultModel
     * @throws WTException
     */
    @Override
    public void updateRefineModel(RefineModel refineModel, ResultModel resultModel) throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        SortedSet<RefineBean> refineBeanSet = refineModel.getRefineBeanSet();
        for (RefineBean refineBean : refineBeanSet) {
            if (isDebugEnabled) {
                logger.debug("Refine Bean before range set from refine bean set is : " + refineBean);
            }

            if (refineBean.getAttrDataType() == AttributeDataType.LONG
                    || refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT
                    || refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS
                    || refineBean.getAttrDataType() == AttributeDataType.TIMESTAMP) {
                StatsInfo statsInfo = null;
                if (resultModel.getStatsInfoMap() != null) {
                    statsInfo = resultModel.getStatsInfoMap().get(refineBean.getAttrId());
                }
                if (statsInfo == null && !(refineBean.getAttrDataType() == AttributeDataType.TIMESTAMP)) {
                    refineBean.setNullValueBean(true);
                }
                RefineBeanService.setRanges(refineBean, statsInfo, resultModel.getResultCount());
                RefineBeanService.setOperatorColumnValues(refineBean, null, refineModel.getLocale());
            } else if (refineBean.getAttrDataType() == AttributeDataType.BOOLEAN) {
                Facet facet = null;
                if (resultModel.getFacetFieldMap() != null) {
                    facet = resultModel.getFacetFieldMap().get(refineBean.getAttrId());
                }
                if (facet == null || !(facet != null && facet.getFacetCountMap().size() > 0)) {
                    refineBean.setNullValueBean(true);
                }
                RefineBeanService.setOperatorColumnValues(refineBean, facet, refineModel.getLocale());
                RefineBeanService.setRanges(refineBean, null, resultModel.getResultCount());
            } else if (refineBean.getAttrDataType() == AttributeDataType.STRING) {
                String attrId = PartsLinkUtils.translateToFacetId(refineBean.getAttrId());
                Facet facet = null;
                if (resultModel.getFacetFieldMap() != null) {
                    facet = resultModel.getFacetFieldMap().get(attrId);
                }
                if (facet == null || !(facet != null && facet.getFacetCountMap().size() > 0)) {
                    refineBean.setNullValueBean(true);
                }

                RefineBeanService.setOperatorColumnValues(refineBean, facet, refineModel.getLocale());
                RefineBeanService.setRanges(refineBean, null, resultModel.getResultCount());
            } else {
                RefineBeanService.setRanges(refineBean, null, resultModel.getResultCount());
            }
            if (isDebugEnabled) {
                logger.debug("Refine Bean after range set from refine bean set is : " + refineBean);
            }
        }

        if (InstalledProperties.isInstalled(InstalledProperties.SUMA)) {
            // updating the sourcing context
            Facet facet = null;
            if (resultModel.getFacetFieldMap() != null) {
                facet = resultModel.getFacetFieldMap().get(PartslinkConstants.IndexFields.SOURCING_CONTEXT);
            }
            if (facet != null) {
                Map<String, String> sourceContext = new HashMap<String, String>();

                // Adding default sourcing context
                ObjectReference objRef = SumaFacade.getInstance().getDefaultAXLContextRef(SessionHelper.getPrincipal());
                if (objRef != null) {
                    String dispName = SumaFacade.getInstance().getSourcingContextName(objRef);
                    sourceContext.put(objRef.toString(), dispName);
                }

                for (String key : facet.getFacetCountMap().keySet()) {
                    if (facet.getFacetCountMap().get(key) != 0) {
                        // populating the map with sourcing context internal name and its corresponding display name.
                        // Sourcing context are indexed as "com.ptc.windchill.suma.axl.AXLContext:148155#Pune" in Solr.
                        StringTokenizer tokenizer = new StringTokenizer(key,
                                PartslinkConstants.IndexFields.SRC_CONTEXT_AND_STATUS_DELIMITER);
                        if (tokenizer.countTokens() == 2) {
                            String srcContextInternalValue = tokenizer.nextToken();
                            ReferenceFactory factory = new ReferenceFactory();
                            WTReference wtRef = factory.getReference(srcContextInternalValue);
                            if (!sourceContext.containsKey(wtRef)) {
                                try {
                                    String displayName = SumaFacade.getInstance().getSourcingContextName(wtRef);
                                    sourceContext.put(wtRef.toString(), displayName);
                                } catch (Exception e) {
                                    // Ignoring exception in getting SourcingContext.
                                    // user may not have access to this SourcingContext, or Object may be deleted.
                                }
                            }
                        }
                    }
                }
                refineModel.setSourceContextValues(sourceContext);
            }
        }
        // remove the refine beans i.e attributes which do not have any values indexed.
        Iterator refineBeanSetIterator = refineModel.getRefineBeanSet().iterator();
        while (refineBeanSetIterator.hasNext()) {
            RefineBean tempBean = (RefineBean) refineBeanSetIterator.next();
            if (tempBean.isNullValueBean()) {
                refineBeanSetIterator.remove();
            }
        }
    }

    /**
     * Method to merge the new refine model with the old refine model.
     * 
     * @param refineModel
     * @param oldRefineModel
     * @throws WTException
     */
    @Override
    public void mergeRefineModel(RefineModel refineModel, RefineModel oldRefineModel) throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (refineModel != null && oldRefineModel != null) {
            Map<String, RefineBean> oldBeanMap = new HashMap<String, RefineBean>(oldRefineModel.getRefineBeanSet()
                    .size());
            for (RefineBean bean : oldRefineModel.getRefineBeanSet()) {
                oldBeanMap.put(bean.getAttrId(), bean);
            }
            if (isDebugEnabled) {
                logger.debug("Old bean map is  : " + oldBeanMap);
            }

            for (RefineBean bean : refineModel.getRefineBeanSet()) {
                if (isDebugEnabled) {
                    logger.debug("Refine Bean before range set from refine bean set is : " + bean);
                }
                RefineBean oldBean = oldBeanMap.get(bean.getAttrId());
                if (isDebugEnabled) {
                    logger.debug("Old Refine Bean in mergeRangesAndOperators() is : " + oldBean);
                }
                if (oldBean != null) {
                    bean.setOperatorColumnValues(oldBean.getOperatorColumnValues());
                    bean.setOperatorColumnDisplayValues(oldBean.getOperatorColumnDisplayValues());

                    if (bean.getAttrDataType() == AttributeDataType.LONG
                            || bean.getAttrDataType() == AttributeDataType.FLOATING_POINT
                            || bean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS) {
                        bean.setRangeValue(oldBean.getRangeValue());
                        bean.setEnabled(oldBean.isEnabled());
                    } else {
                        bean.setRangeValue("");
                    }

                } else {
                    bean.setNullValueBean(true);
                }
                if (isDebugEnabled) {
                    logger.debug("Refine Bean after range set from refine bean set is : " + bean);
                }
            }

            if (InstalledProperties.isInstalled(InstalledProperties.SUMA)) {
                // Merging the sourcing context.
                refineModel.setSourceContextValues(oldRefineModel.getSourceContextValues());
            }
            Iterator refineBeanSetIterator = refineModel.getRefineBeanSet().iterator();
            while (refineBeanSetIterator.hasNext()) {
                RefineBean tempBean = (RefineBean) refineBeanSetIterator.next();
                if (tempBean.isNullValueBean()) {
                    refineBeanSetIterator.remove();
                }
            }
        }
        else {
            if (isDebugEnabled) {
                logger.debug("refineModel : " + refineModel + " and oldRefineModel :" + oldRefineModel);
            }
        }
    }

    /**
     * Method to merge refine model from the freeform matched class attributes.
     * 
     * @param refineModel
     * @param matchedclass
     * @throws WTException
     */
    @Override
    public void mergeRefineFromFreeFrom(RefineModel refineModel, MatchedClass matchedclass) throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();

        if (refineModel != null && matchedclass != null && matchedclass.getMatchedAttributes() != null
                && matchedclass.getMatchedAttributes().size() > 0) {

            Map<String, MatchedAttribute> matchedAttribBeanMap = new HashMap<String, MatchedAttribute>(matchedclass
                    .getMatchedAttributes().size());
            for (MatchedAttribute matchedAttribBean : matchedclass.getMatchedAttributes()) {
                matchedAttribBeanMap.put(matchedAttribBean.getAttrId(), matchedAttribBean);
            }

            if (isDebugEnabled) {
                logger.debug("mergeRefineFromFreeFrom Matched Attributes bean map is  : " + matchedAttribBeanMap);
            }

            for (RefineBean refineBean : refineModel.getRefineBeanSet()) {

                if (isDebugEnabled) {
                    logger.debug("mergeRefineFromFreeFrom Refine bean : " + refineBean);
                }

                MatchedAttribute attribute = matchedAttribBeanMap.get(refineBean.getAttrId());

                if (isDebugEnabled) {
                    logger.debug("mergeRefineFromFreeFrom attribute bean : " + attribute);
                }

                if (attribute != null) {
                    if (refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS
                            && attribute.getAttrUnits() != null
                            && !attribute.getAttrUnits().equalsIgnoreCase(refineBean.getAttrUnits())) {
                        refineBean.setValue(attribute.getValue() + " " + attribute.getAttrUnits());
                    }
                    else {
                        refineBean.setValue(attribute.getValue());
                    }
                    refineBean.setSearchWithImplicitWC(attribute.isSearchWithImplicitWC());
                }
            }
        }
        else {
            if (isDebugEnabled) {
                logger.debug("refineModel : " + refineModel + " and matchedclass :" + matchedclass);
            }
        }
    }

    /**
     * Prepares the result model from refine model
     * 
     * @param refineModel
     * @param partTypes
     * @return ResultModel * @throws WTException
     */
    @Override
    public ResultModel prepareResultModel(RefineModel refineModel, HttpServletRequest request) throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        String partTypes = (String) request.getSession().getAttribute(
                PartslinkConstants.RequestParameters.CSM_SUMA_PART_TYPES);
        if (partTypes == null) {
            partTypes = "WTPart";
        }

        if (isDebugEnabled) {
            logger.debug("Selected Part types :: " + partTypes);
        }

        ResultModel resultModel = new ResultModel();
        resultModel.setClassInternalName(refineModel.getClassInternalName());
        resultModel.setFilterQueries(generateFilterQueries(refineModel, resultModel, partTypes));
        resultModel.setSearchType(SearchType.REFINE_SEARCH);
        if (isDebugEnabled) {
            logger.debug("Filter Queries in prepareResultModel() are : " + resultModel.getFilterQueries());
        }
        return resultModel;
    }

    /**
     * <B>Supported API: </B>false
     * 
     * Generate filter queries.
     * 
     * @param refineModel
     *            - the refine model
     * @param resultModel
     *            - the result model
     * @param partTypes
     *            - partTypes
     * @return the list
     * @throws WTException
     */
    public static List<String> generateFilterQueries(RefineModel refineModel, ResultModel resultModel, String partTypes)
            throws WTException {
        List<String> filterQueries = new ArrayList<String>();
        List<String> statsFields = new ArrayList<String>();
        List<String> facetFields = new ArrayList<String>();
        boolean isDebugEnabled = logger.isDebugEnabled();
        String fqClass = PartsLinkUtils.getClassificationQuery(refineModel.getClassInternalName());
        if (isDebugEnabled) {
            logger.debug("adding class query : " + fqClass);
        }
        filterQueries.add(fqClass);
        String csmTypeFq = PartsLinkUtils.getCsmSumaTypesFilterQuery(partTypes);
        if (isDebugEnabled) {
            logger.debug("adding suma part type query : " + csmTypeFq);
        }
        filterQueries.add(csmTypeFq);

        FilterQueryProducerFactory factory = new FilterQueryProducerFactory();
        Set<RefineBean> refineBeans = refineModel.getRefineBeanSet();
        for (RefineBean refineBean : refineBeans) {
            if (isDebugEnabled) {
                logger.debug("Refine bean in generateFilterQueries() is : " + refineBean);
            }

            if (refineBean.getAttrDataType() == AttributeDataType.LONG
                    || refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT
                    || refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS) {
                statsFields.add(refineBean.getAttrId());
            } else if (refineBean.getAttrDataType() == AttributeDataType.BOOLEAN) {
                facetFields.add(refineBean.getAttrId());
            } else if (refineBean.getAttrDataType() == AttributeDataType.STRING) {
                String attrId = PartsLinkUtils.translateToFacetId(refineBean.getAttrId());
                facetFields.add(attrId);
            }

            if (!refineBean.isError()) {
                FilterQueryProducer fqp = factory.createFilterQueryProducer(refineBean, refineModel);
                String fq = null;
                try {
                    fq = fqp.produceFilterQuery(refineBean);
                } catch (ParseException e) {
                    logger.error("Exception while generating filter query" + e);
                }
                if (fq != null) {
                    if (isDebugEnabled) {
                        logger.debug("adding query" + fq);
                    }
                    filterQueries.add(fq);
                }
            }
        }

        if (InstalledProperties.isInstalled(InstalledProperties.SUMA)) {
            // adding sourcing context to facet fields.
            facetFields.add(PartslinkConstants.IndexFields.SOURCING_CONTEXT);
        }

        resultModel.setFacetFields(facetFields);
        if (isDebugEnabled) {
            logger.debug("Facet Fields in generateFilterQueries() are : " + facetFields);
        }
        resultModel.setStats(statsFields);
        if (isDebugEnabled) {
            logger.debug("Stats fields in generateFilterQueries() are : " + statsFields);
        }
        return filterQueries;
    }

}

/**
 * Refine Search comparator. Sorts refine search beans in order of alphabetical display name.
 */
class RefineSearchBeanComparator implements Comparator<RefineBean> {

    @Override
    public int compare(RefineBean bean1, RefineBean bean2) {
        int result = bean1.getAttrDisplayName().compareTo(bean2.getAttrDisplayName());
        if (result == 0) {
            result = bean1.getAttrId().compareTo(bean2.getAttrId());
        }
        return result;
    }

}
