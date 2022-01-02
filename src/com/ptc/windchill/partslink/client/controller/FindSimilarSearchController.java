package com.ptc.windchill.partslink.client.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.WTMessage;

import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.ClassificationBreadCrumb;
import com.ptc.windchill.partslink.model.FindSimilarModel;
import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.FindSimilarModelService;
import com.ptc.windchill.partslink.service.FindSimilarModelServiceImpl;
import com.ptc.windchill.partslink.service.ResultModelService;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;


/**
 * The Class FindSimilarSearchController.
 */
@Controller
public class FindSimilarSearchController {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(FindSimilarSearchController.class.getName());

    /** The result model service. */
    private ResultModelService resultModelService = new ResultModelServiceImpl();

    /** The findSimialr Model Service .*/
    private FindSimilarModelService  findSimilarModelService  = new FindSimilarModelServiceImpl();

    private RefineSearchController refineSearchController = new RefineSearchController();

    /** The Constant RESOURCE. */
    private static final String RESOURCE = partslinkClientResource.class.getName();

    /**
     * <BR><BR><B>Supported API: </B>false
     *
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Throwable the throwable
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    protected ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        ModelAndView result = new ModelAndView();

        if (!PartsLinkUtils.shouldRenderActionOnRoleBasedCheck(PartslinkConstants.CLASSIFICATION_SEARCH_ACTION_NAME)) {
            result.addObject("message", WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.AUTHORIZATION_ERROR, null));
            result.setViewName(PartslinkConstants.Views.NO_CLASSIFICATION_ACCESS_VIEW);
            return result;
        }
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled) {
            logger.debug("Entered FindSimilarSearchController.processRequest()");
        }

        Locale locale = request.getLocale();
        if (locale == null) {
            locale = SessionHelper.getLocale();
        }

        Long count = 0L;
        long searchResultCount = 0L;

        // Get the Node internal Name from request params.
        String classInternalName = request.getParameter(PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME);
        // Get the Search count from request params.
        String searchCount = request.getParameter(PartslinkConstants.RequestParameters.PARAM_SEARCH_COUNT);

        if (isDebugEnabled) {
            logger.debug("IN FindSimilarSearchController.processRequest() ==> Got classname(Node Internal Name) in request as :: " + classInternalName);
            logger.debug("IN FindSimilarSearchController.processRequest() ==> Got searchCount  in request as :: " + searchCount);
        }

        // When the flow is from Result Page to FindSimilar UI. The count param will have the count of the result
        // Page/This is the behaviuor in X22 also
        if (searchCount != null) {
            count = Long.parseLong(searchCount);
        }

        // check execute search flag
        boolean executeSearch = getExecuteSearch(request);
        if (isDebugEnabled) {
            logger.debug("IN FindSimilarSearchController.processRequest() ==> executeSearch :: " + executeSearch);
        }

        // read old find similar model from session this model will be used mostly for obtaining the modifiers
        FindSimilarModel oldFindSimilarModel = (FindSimilarModel) request.getSession().getAttribute(PartslinkConstants.Model_IDS.FIND_SIMILAR_MODEL_SESSION);
        if (isDebugEnabled) {
            logger.debug("IN FindSimilarSearchController.processRequest() ==> Got old find similar model as :::   " + oldFindSimilarModel);
        }

        // prepare find similar model
        FindSimilarModel findSimilarModel = findSimilarModelService.getFindSimilarModel(classInternalName, request);
        if (isDebugEnabled) {
            logger.debug("IN FindSimilarSearchController.processRequest() ==> Got find similar model as :::   " + findSimilarModel);
        }

        ResultModel resultModel = null;

        if (findSimilarModel != null) {

            // if there is error in find similar model then merge the modifiers from the old find similar model
            if (findSimilarModel.isError()) {
                findSimilarModelService.mergeFindSimilarModel(findSimilarModel, oldFindSimilarModel);
            } else {
                // if there is no error in find similar model then execute the solr query
                resultModel = findSimilarModelService.prepareResultModel(findSimilarModel, request);
                resultModel = resultModelService.query(resultModel);
                searchResultCount = resultModel.getResultCount();
                if (isDebugEnabled) {
                    logger.debug("IN FindSimilarSearchController.processRequest()==> FindSimilar Result Count :: " + searchResultCount);
                }

                if (searchResultCount > 0) {
                    findSimilarModelService.updateFindSimilarModel(findSimilarModel, resultModel);
                    // When the flow is from Result Page to FindSimilar UI. The count param will have the count of
                    // the result Page/This is the behavior in X22 also
                    findSimilarModel.setExactMatches(count);

                    // always put the find similar model in session if there is not error.
                    request.getSession().setAttribute(PartslinkConstants.Model_IDS.FIND_SIMILAR_MODEL_SESSION, findSimilarModel);

                    // put the refine model in session
                    RefineModel refineModel = findSimilarModelService.getRefineModel(findSimilarModel, request);
                    request.getSession().setAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL, refineModel);

                    // decision making logic for which page to be shown based on results count in case of search.
                    if (executeSearch) {
                        return refineSearchController.processRequest(request, response);
                    }
                } else {
                    findSimilarModelService.mergeFindSimilarModel(findSimilarModel, oldFindSimilarModel);

                    if (searchResultCount == 0) {
                        findSimilarModel.setExactMatches(resultModel.getResultCount());
                        findSimilarModel.setError(true);
                        findSimilarModel.setErrorMessage(WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.FIND_SIMIALR_SEARCH_NO_RESULTS, null));

                    }
                }

            }
        }

        ClassificationBreadCrumb clfBreadCrumb = new ClassificationBreadCrumb();

        // find similar page will be shown
        result.addObject(PartslinkConstants.Model_IDS.FINDSIMILAR_MODEL, findSimilarModel);
        result.addObject("breadCrumb", clfBreadCrumb.getFindSimilarPageBreadCrumb(request));
        result.setViewName(PartslinkConstants.Views.FIND_SIMILAR_PAGE_VIEW);

        if (isDebugEnabled) {
            logger.debug("Exiting FindSimilarSearchController.processRequest()");
        }
        return result;
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

        if(logger.isDebugEnabled()) logger.debug("IN ::  FindSimilarSearchController.getExecuteSearch()");

        boolean result = false;
        String executeSearch = (String) request.getParameter(PartslinkConstants.RequestParameters.PARAM_EXECUTE_SEARCH);
        if ("true".equals(executeSearch)) {
            result = true;
        }

        if(logger.isDebugEnabled()) logger.debug("OUT ::  FindSimilarSearchController.getExecuteSearch()");
        return result;
    }

    protected FindSimilarModelService getFindSimilarModelService() {
        return findSimilarModelService;
    }

    protected void setFindSimilarModelService(
            FindSimilarModelService findSimilarModelService) {
        this.findSimilarModelService = findSimilarModelService;
    }

    public ResultModelService getResultModelService() {
        return resultModelService;
    }

    public void setResultModelService(ResultModelService resultModelService) {
        this.resultModelService = resultModelService;
    }

    public RefineSearchController getRefineSearchController() {
        return refineSearchController;
    }

    public void setRefineSearchController(RefineSearchController refineSearchController) {
        this.refineSearchController = refineSearchController;
    }

}
