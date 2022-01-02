package com.ptc.windchill.partslink.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wt.facade.suma.SumaFacade;
import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.fc.WTReference;
import wt.httpgw.URLFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.InstalledProperties;
import wt.util.WTMessage;

import com.ptc.windchill.csm.common.CsmConstants;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.ClassificationBreadCrumb;
import com.ptc.windchill.partslink.model.FindSimilarModel;
import com.ptc.windchill.partslink.model.FreeFormModel;
import com.ptc.windchill.partslink.model.MatchedClass;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;
import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.model.ViewAllModel;
import com.ptc.windchill.partslink.service.RefineModelService;
import com.ptc.windchill.partslink.service.RefineModelServiceImpl;
import com.ptc.windchill.partslink.service.ResultModelService;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * The Class RefineSearchController.
 */
@Controller
public class RefineSearchController {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(RefineSearchController.class.getName());

    /** The refine model service. */
    private RefineModelService refineModelService = new RefineModelServiceImpl();

    /** The result model service. */
    private ResultModelService resultModelService = new ResultModelServiceImpl();

    /** The Constant RESOURCE. */
    private static final String RESOURCE = partslinkClientResource.class.getName();

    /** The classification bread crumb model. */
    private ClassificationBreadCrumb clfBreadCrumb = new ClassificationBreadCrumb();


    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     *
     * Process request.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the model and view
     * @throws Throwable
     *             the throwable
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    protected ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        boolean isDebugEnabled = logger.isDebugEnabled();
        ModelAndView result = new ModelAndView();

        if (!PartsLinkUtils.shouldRenderActionOnRoleBasedCheck(PartslinkConstants.CLASSIFICATION_SEARCH_ACTION_NAME)) {
            result.addObject("message", WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.AUTHORIZATION_ERROR, null));
            result.setViewName(PartslinkConstants.Views.NO_CLASSIFICATION_ACCESS_VIEW);
            return result;
        }

        if (isDebugEnabled) {
            logger.debug("IN RefineSearchController.processRequest()");
        }

        String fromSearch = request.getParameter("fromSearch");

        boolean executeSearch = getExecuteSearch(request);
        String classInternalName = request.getParameter(PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME);
        if (isDebugEnabled) {
            logger.debug("Class name in request is " + classInternalName + " and executeSearch flag is "
                    + executeSearch);
        }

        RefineModel refineModel = null;
        RefineModel oldRefineModel = (RefineModel) request.getSession().getAttribute(
                PartslinkConstants.Model_IDS.REFINE_MODEL);
        String action = request.getParameter(PartslinkConstants.RequestParameters.PARAM_ACTION);
        if (isDebugEnabled) {
            logger.debug("Action parameter in request is " + action);
        }

        FindSimilarModel fsModel = (FindSimilarModel) request.getSession().getAttribute(
                PartslinkConstants.Model_IDS.FIND_SIMILAR_MODEL_SESSION);

        // create RefineModel
        if (action != null && action.equals("freeform")) {
            FreeFormModel freeFormModel = (FreeFormModel) request.getSession().getAttribute(
                    PartslinkConstants.Model_IDS.FREEFORM_MODEL);

            if (freeFormModel != null) {
                String matchedClassName = request.getParameter("class");

                if (matchedClassName != null
                        && PartslinkConstants.FreeFormSearch.OMC_INTERNAL_NAME.equalsIgnoreCase(matchedClassName)) {
                    matchedClassName = request.getParameter("oldmatchedclass");
                }

                MatchedClass matchedclass = freeFormModel.getClass(matchedClassName);
                classInternalName = matchedclass.getClassInternalName();
                refineModel = refineModelService.getRefineModel(classInternalName, request);

                refineModelService.mergeRefineFromFreeFrom(refineModel, matchedclass);

                oldRefineModel = null;
                if (request.getParameter("oldmatchedclass") == null && matchedclass.getPartsCount() == 1
                        && freeFormModel.getMatchedClassesCount() > 1) {
                    executeSearch = false;
                }
                else {
                    executeSearch = true;
                }
            }
            else {
                logger.debug("freeFormModel is null");
            }
        }
        else if (executeSearch && fsModel == null) {
            // from form submission or browse(leaf node/search button). Build refine model and read user input.
            refineModel = refineModelService.getRefineModel(classInternalName, request);
        }
        else {
            // from result page bread-crumb or view all
            refineModel = oldRefineModel;
            if (refineModel == null) {
                refineModel = refineModelService.getRefineModel(classInternalName, request);
            }
            request.getSession().removeAttribute(PartslinkConstants.Model_IDS.FIND_SIMILAR_MODEL_SESSION);
        }

        if (isDebugEnabled) {
            logger.debug("classInternalName= " + classInternalName + " and executeSearch= " + executeSearch);
        }

        if (InstalledProperties.isInstalled(InstalledProperties.SUMA)) {
            // code to consider sourcing context in case SUMA is installed.
            if (executeSearch || (action != null && action.equals("results"))) {
                String sourceContext = request.getParameter(PartslinkConstants.IndexFields.SOURCING_CONTEXT);
                if (isDebugEnabled) {
                    logger.debug("Sourcing Context :: " + sourceContext);
                }
                if (sourceContext != null) {
                    handleSourcingContext(sourceContext, refineModel, request);
                }
            }
        }

        if (isDebugEnabled) {
            logger.debug("Got refine model before range update as " + refineModel);
        }

        boolean showResultPage = false;
        boolean viewAll = false;

        // action parameter will be used only for view all action.
        if (action != null && action.equals("results")) {
            showResultPage = true;
            viewAll = true;
        }

        // create ResultModel
        ResultModel resultModel = null;
        long count = 0;

        if (viewAll) {
            if (isDebugEnabled) {
                logger.debug("In view all block");
            }
            // for View All action read the view all model from session and then build the ResultModel from this
            // view
            // all model. this case happens when atleast one refine search was executed successfully.
            ViewAllModel viewAllModel = (ViewAllModel) request.getSession().getAttribute(
                    PartslinkConstants.Model_IDS.VIEW_ALL_MODEL);
            if (isDebugEnabled) {
                logger.debug("Veiw all model from session is :" + viewAllModel);
            }
            if (viewAllModel != null) {
                resultModel = new ResultModel();
                resultModel.setClassInternalName(refineModel.getClassInternalName());
                resultModel.setFilterQueries(viewAllModel.getFilterQueries());
                resultModel.setFacetFields(viewAllModel.getFacetFields());
                resultModel.setStats(viewAllModel.getStats());
            } else {
                // if view all model is not present in session then build the ResultModel from refine model.
                // this case happens until atleast one refine search was executed successfully.
                resultModel = refineModelService.prepareResultModel(refineModel, request);
            }
            if (isDebugEnabled) {
                logger.debug("Got result model as " + resultModel);
            }
            resultModel = resultModelService.query(resultModel);
            count = resultModel.getResultCount();
            if (isDebugEnabled) {
                logger.debug("Count returned from result model is : " + count);
            }
        } else if (refineModel != null) {
            if (refineModel.isError()) {
                if (isDebugEnabled) {
                    logger.debug("Error is present in refine model hence will updated the current refine model from old refine model");
                }
                // whenever there is error in refine model the count of matches, ranges and operators should be
                // retained
                // from old refine model.
                count = oldRefineModel.getExactMatches();
                refineModel.setExactMatches(count);
                refineModelService.mergeRefineModel(refineModel, oldRefineModel);
                if (isDebugEnabled) {
                    logger.debug("Count returned from result model is : " + count);
                }
                // we need to update the refine model in session even if there is error since when we come back from
                // result page on click of view all then the criteria should be persisted.
                request.getSession().setAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL, refineModel);
            }
            else {
                if (isDebugEnabled) {
                    logger.debug("Since there is no error in refine model will execute the query and prepare the result model");
                }
                showResultPage = executeSearch;
                // we are querying the solr only when there is no error in refine model.
                resultModel = refineModelService.prepareResultModel(refineModel, request);
                if (isDebugEnabled) {
                    logger.debug("Got result model as " + resultModel);
                }
                resultModel = resultModelService.query(resultModel);
                count = resultModel.getResultCount();
                refineModel.setExactMatches(count);
                if (isDebugEnabled) {
                    logger.debug("Count returned from result model is : " + count);
                }
                // Update RefineModel Ranges and Operators
                if (count > 0) {
                    if (isDebugEnabled) {
                        logger.debug("Updateing the ranges and operators of refine model from the results returned from solr since the count is greater than zero.");
                    }
                    // valid criteria
                    refineModelService.updateRefineModel(refineModel, resultModel);
                    // put updated refineModel in session
                    request.getSession().setAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL, refineModel);
                    // build the ViewAllModel and store it in session when refine search was executed successfully
                    // since
                    // we need exactly this criteria for View All action
                    ViewAllModel viewAllModel = new ViewAllModel();
                    viewAllModel.setFilterQueries(resultModel.getFilterQueries());
                    viewAllModel.setFacetFields(resultModel.getFacetFields());
                    viewAllModel.setStats(resultModel.getStats());
                    request.getSession().setAttribute(PartslinkConstants.Model_IDS.VIEW_ALL_MODEL, viewAllModel);

                } else {
                    if (isDebugEnabled) {
                        logger.debug("Updateing the ranges and operators of refine model from the old refine model since the count is equal to zero.");
                    }
                    // invalid criteria(for values when there is no result from solr) so retain old range values
                    refineModelService.mergeRefineModel(refineModel, oldRefineModel);
                }

                if (executeSearch) {
                    // when refine search was executed we need the check the count of results and then decide
                    // whether we
                    // have to show refine search UI or result page.
                    int refineCountThreshould = PartslinkPropertyModel.getInstance().getRefineCountThreshold();
                    if (isDebugEnabled) {
                        logger.debug("Refine count threshold is : " + refineCountThreshould);
                    }
                    if (count > 0 && count < refineCountThreshould) {
                        if (isDebugEnabled) {
                            logger.debug("Result page will be displayed since count is greater than zero and within refine threshold limit.");
                        }
                        showResultPage = true;
                    }
                    else {
                        if (isDebugEnabled) {
                            logger.debug("Refine page will be displayed since count is equal to zero or count is greater than refine threshold limit");
                        }
                        if (count == 0) {
                            refineModel.setError(true);
                            refineModel.setErrorMessage(WTMessage.getLocalizedMessage(RESOURCE,
                                    partslinkClientResource.REFINE_SEARCH_NO_RESULTS, null));
                        }
                        showResultPage = false;
                    }
                }
            }
        }

        if (showResultPage) {
            // set the bread crumb for search result page.
            request.getSession().setAttribute("breadCrumb", clfBreadCrumb.getResultPageBreadCrumb(request));
            request.getSession().setAttribute(PartslinkConstants.Model_IDS.RESULT_MODEL, resultModel);
            String queryParams = PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME + "="
                    + classInternalName + "&" + PartslinkConstants.RequestParameters.PARAM_OBJECT_TYPE + "="
                    + WTPart.class.getName() + "&" + PartslinkConstants.RequestParameters.PARAM_NAMESPACE + "="
                    + CsmConstants.DEFAULT_NAMESPACE + "&ts=" + System.currentTimeMillis();

            String redirectPageUrl = new URLFactory().getBaseHREF();
            redirectPageUrl = redirectPageUrl + "app/#ptc1/websearch/result?" + queryParams;
            result.addObject("redirectUrl", redirectPageUrl);

            if (isDebugEnabled) {
                logger.debug("RefineController Redirect page url is: " + redirectPageUrl);
            }

            result.setViewName(PartslinkConstants.Views.REDIRECT_PAGE_VIEW);
        } else {
            // set the bread crumb for refine search page.
            result.addObject("breadCrumb", clfBreadCrumb.getRefineSearchPageBreadCrumb(request));
            result.addObject(PartslinkConstants.Model_IDS.REFINE_MODEL, refineModel);
            result.setViewName(PartslinkConstants.Views.REFINE_PAGE_VIEW);
        }
        if (isDebugEnabled) {
            logger.debug("exit RefineSearchController.processRequest()");
        }
        result.addObject("fromSearch", fromSearch);

        return result;
    }

    /**
     * handler for sourcing context
     *
     * @param sourceContext
     * @param refineModel
     * @param request
     */
    private void handleSourcingContext(String sourceContext,
            RefineModel refineModel, HttpServletRequest request) {

        try {
            // set source context on refineModel
            refineModel.setSourceContext(sourceContext);

            // get WTObject for AXLContext
            ReferenceFactory factory = new ReferenceFactory();
            WTReference ref = factory.getReference(sourceContext);
            Persistable persistable = ref.getObject();
            ObjectIdentifier oid = persistable.getPersistInfo().getObjectIdentifier();
            if (persistable instanceof WTObject) {
                WTObject axlContext = (WTObject) persistable;

                // adding the selected sourcing context to the recently visited object list.
                SumaFacade.getInstance().addRecentSourcingContext(axlContext);
            }

            // adding the selected sourcing contex to the request.
            request.setAttribute(PartslinkConstants.RequestParameters.SELECTED_SOURCING_CONTEXT, oid.toString());

        } catch (Exception e) {
            // ignore any exception in handling sourcing context
            // could get exception for deleted, not authorized objects
            logger.debug("Ignoring the sourcing context:" + sourceContext, e);
        }
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     *
     * Gets the execute search.
     *
     * @param request
     *            the request
     * @return the execute search
     */
    private boolean getExecuteSearch(HttpServletRequest request) {
        boolean result = false;
        String executeSearch = (String) request.getParameter(PartslinkConstants.RequestParameters.PARAM_EXECUTE_SEARCH);
        if ("true".equals(executeSearch)) {
            result = true;
        }
        return result;
    }

}
