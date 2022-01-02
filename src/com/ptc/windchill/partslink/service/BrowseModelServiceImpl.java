package com.ptc.windchill.partslink.service;
import static com.ptc.core.lwc.common.view.PropertyHolderHelper.getLWCContentDownloadControllerUrl;

import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import wt.access.NotAuthorizedException;
import wt.content.ContentHelper;
import wt.content.ContentServerHelper;
import wt.content.FormatContentHolder;
import wt.httpgw.URLFactory;
import wt.inf.container.WTContainerException;
import wt.log4j.LogR;
import wt.util.HTMLEncoder;
import wt.util.WTException;

import com.ptc.core.lwc.common.PropertyDefinitionConstants;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.PropertyValueReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.facet.Facet;
import com.ptc.windchill.partslink.model.BrowseModel;
import com.ptc.windchill.partslink.model.BrowseModelBean;
import com.ptc.windchill.partslink.model.IndexSearchResultModel;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * The Class BrowseModelService.
 */
public class BrowseModelServiceImpl implements BrowseModelService {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(BrowseModelServiceImpl.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.windchill.partslink.service.BrowseModelServiceIfc#getBrowseModel(java.lang.String, java.util.Locale)
     */
    @Override
    public BrowseModel getBrowseModel(String nodeInternalName, String partTypes, Locale locale)throws NotAuthorizedException, WTException {
        logger.debug("Building browse model for " + nodeInternalName);

        if (nodeInternalName == null || "".equals(nodeInternalName.trim())) {
            nodeInternalName = PartslinkPropertyModel.getInstance().getDefaultBrowseRootNode();
            logger.debug("Fetched default root node as " + nodeInternalName);
        }

        if (nodeInternalName == null || "".equals(nodeInternalName.trim())) {
            nodeInternalName = PartsLinkUtils.getClfRootNodeName();
            logger.debug("Getting root node for browse: " + nodeInternalName);
        }

        if (nodeInternalName == null) {
            logger.debug("Could not get node to browse. Returning null ");
            return null;
        }

        return createModel(nodeInternalName, partTypes, locale);
    }

    /**
     * Creates the model.
     * 
     * @param nodeInternalName the node internal name
     * @param locale the locale
     * @return the browse model
     * @throws NotAuthorizedException the not authorized exception
     * @throws WTContainerException the wT container exception
     * @throws WTException the wT exception
     */
    private BrowseModel createModel(String nodeInternalName, String partTypes, Locale locale) throws NotAuthorizedException, WTContainerException, WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled) {
            logger.debug("Creating mode for  " + nodeInternalName);
        }
        TypeDefinitionReadView typeDefReadView = CSMTypeDefHelper.getClassificationTypeDefView(nodeInternalName);
        String[] facetFields = new String[] { PartslinkConstants.IndexFields.PTC_CLASSIFICATION };

        String[] filterQueries = new String[] { PartsLinkUtils.getCsmSumaTypesFilterQuery(partTypes) };

        if (isDebugEnabled) {
            logger.debug("Facet fields are : " + facetFields);
            logger.debug("Filter queries are : " + filterQueries);
        }
        IndexResultModelService clfService = new IndexResultModelServiceImpl();
        IndexSearchResultModel result = clfService.getFacetsWithFilterQueries(facetFields, filterQueries);
        Map<String, Facet> facetMap = result.getFacetFieldMap();

        Facet facet = facetMap.get(facetFields[0]);
        if (isDebugEnabled) {
            logger.debug("Facet obtained from facet map : " + facet.toString());
        }
        if (typeDefReadView != null) {
            BrowseModel browseModel = new BrowseModel();
            browseModel.setCurrentNodeDisplayName(HTMLEncoder.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(typeDefReadView, locale)));
            browseModel.setCurrentNodeInternalName(typeDefReadView.getName());
            Set<TypeDefinitionReadView> childNodes = (Set<TypeDefinitionReadView>) CSMTypeDefHelper.getChildTypeDefViews(typeDefReadView);
            for (Iterator iterator = childNodes.iterator(); iterator.hasNext();) {
                TypeDefinitionReadView typeDefinitionReadView = (TypeDefinitionReadView) iterator.next();
                if (facet.getFacetCountMap().containsKey(typeDefinitionReadView.getName()) && facet.getFacetCountMap().get(typeDefinitionReadView.getName()) != 0)
                {
                    BrowseModelBean modelBean = new BrowseModelBean();
                    modelBean.setNodeDisplayName(HTMLEncoder.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(typeDefinitionReadView, locale)));
                    //SPR-2195456-01 If file vaulting service is enabled then we can not use same url more than once.
                    // So recreating imagePath url for every time. Get image path of node from contentHolder
                    String urlString = "";
                    try {
                        // get property Read view for node image.
                        PropertyValueReadView rv = typeDefinitionReadView.getPropertyValueByName(PropertyDefinitionConstants.CSM_NODE_IMAGE);
                        // when image is not attached to node PropertyValueReadView will be null
                        if (rv != null) {
                            FormatContentHolder contentHolder = (FormatContentHolder) ContentServerHelper.service.getContentHolder(rv.getPropValueId());

                            // get contents of property from cache.
                            contentHolder = (FormatContentHolder) ContentHelper.service.getContents(contentHolder);
                            // Set image url to hit LWCContentDownloadController controller which will redirect to form
                            // a new url at runtime to donload the content file.
                          
                            urlString = getLWCContentDownloadControllerUrl(contentHolder);
                            if (logger.isDebugEnabled()) {
                                logger.debug("URL generated to hit LWCContentDownloadController is :" + urlString);
                            }
                            
                        }
                    } catch (PropertyVetoException e) {
                        logger.error("Error while getting content holder. ", e);
                    }
                    modelBean.setImageURL(urlString);
                    modelBean.setNodeInternalName(typeDefinitionReadView.getName());
                    modelBean.setNodeCount(facet.getFacetCountMap().get(modelBean.getNodeInternalName()));
                    Set<TypeDefinitionReadView> immediateChilds = (Set<TypeDefinitionReadView>) CSMTypeDefHelper.getChildTypeDefViews(typeDefinitionReadView);
                    for (Iterator iterator2 = immediateChilds.iterator(); iterator2.hasNext();) {
                        TypeDefinitionReadView typeDefinitionReadView2 = (TypeDefinitionReadView) iterator2.next();
                        if (facet.getFacetCountMap().containsKey(typeDefinitionReadView2.getName()) && facet.getFacetCountMap().get(typeDefinitionReadView2.getName()) != 0) {
                            BrowseModelBean childModelBean = new BrowseModelBean();
                            childModelBean.setNodeDisplayName(HTMLEncoder.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(typeDefinitionReadView2, locale)));
                            childModelBean.setNodeInternalName(typeDefinitionReadView2.getName());
                            childModelBean.setNodeCount(facet.getFacetCountMap().get(typeDefinitionReadView2.getName()));
                            modelBean.getImmediateChilds().add(childModelBean);
                        }
                    }
                    browseModel.getBrowseBeans().add(modelBean);
                }
            }
            Set<TypeDefinitionReadView> parentNodes = (Set<TypeDefinitionReadView>) CSMTypeDefHelper.getAncestorTypeDefViews(typeDefReadView);
            LinkedList<TypeDefinitionReadView> list = new LinkedList<>(parentNodes);
            Map<String, String> ancestors = new LinkedHashMap<String, String>();
            for (Iterator iterator = list.descendingIterator(); iterator.hasNext();) {
                TypeDefinitionReadView typeDefinitionReadView = (TypeDefinitionReadView) iterator.next();
                ancestors.put(typeDefinitionReadView.getName(), HTMLEncoder.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(typeDefinitionReadView, locale)));
            }
            browseModel.setParentNodes(ancestors);
            if (facet.getFacetCountMap().containsKey(browseModel.getCurrentNodeInternalName()))
            {
                browseModel.setMatchCount(facet.getFacetCountMap().get(browseModel.getCurrentNodeInternalName()));
            }
            else
            {
                browseModel.setMatchCount(0);
            }

            return browseModel;
        } else {
            if (isDebugEnabled) {
                logger.debug("Could not build browse model as could not fetch read view for the node.  "+nodeInternalName);
            }
            return null;
        }
    }
}
