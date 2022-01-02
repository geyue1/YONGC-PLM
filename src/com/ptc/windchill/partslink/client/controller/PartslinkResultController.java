/* bcwti
 *
 * Copyright (c) 2013 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */
package com.ptc.windchill.partslink.client.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wt.access.AccessControlHelper;
import wt.access.AccessPermission;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.fc.collections.CollectionsHelper;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedHashMap;
import wt.fc.collections.WTKeyedMap;
import wt.httpgw.URLFactory;
import wt.log4j.LogR;
import wt.query.template.URLGenerator;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.vc.VersionForeignKey;

import com.infoengine.object.factory.Att;
import com.infoengine.object.factory.Element;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.ResultModelService;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * Controller class for Free Form Search functionality.
 */
@Controller
public class PartslinkResultController {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(PartslinkResultController.class.getName());

    /** The result model service. */
    private ResultModelService resultModelService = new ResultModelServiceImpl();

    /** The Constant RESOURCE. */
    private static final String RESOURCE = partslinkClientResource.class.getName();

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     *
     * Processes request of type GET and POST.
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
        return processRequest(request, response, SessionHelper.getLocale());
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     *
     * Processes request.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @param locale
     *            the locale
     * @return the model and view
     * @throws Throwable
     *             the throwable
     */
    protected ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response, Locale locale)
            throws Throwable {
        logger.debug("In PartslinkResultController.processRequest()");

        ModelAndView modelAndView = new ModelAndView();

        if (!PartsLinkUtils.shouldRenderActionOnRoleBasedCheck(PartslinkConstants.CLASSIFICATION_SEARCH_ACTION_NAME)) {
            modelAndView.addObject("message", WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.AUTHORIZATION_ERROR, null));
            modelAndView.setViewName(PartslinkConstants.Views.NO_CLASSIFICATION_ACCESS_VIEW);
            return modelAndView;
        }

        ResultModel resultModel = (ResultModel) request.getSession().getAttribute(
                PartslinkConstants.Model_IDS.RESULT_MODEL);

        if (resultModel != null) {
            if (resultModel.getResultCount() == 1) {
                modelAndView = getModelAndViewForSingleModel(resultModel, request);
            } else {
                modelAndView.setViewName(PartslinkConstants.Views.RESULT_PAGE_VIEW);
            }
        }
        else {
            logger.error("resultModel retrieved from request is null.");
        }

        logger.debug("exit PartslinkResultController.processRequest()");

        return modelAndView;
    }

    /**
     * Returns the ModelAndView for single model
     *
     * @param resultModel
     * @return ModelAndView
     * @throws WTException
     *             - throws WTException, if any.
     */
    private ModelAndView getModelAndViewForSingleModel(ResultModel resultModel, HttpServletRequest request)
            throws WTException
    {
        boolean isDebugEnabled = logger.isDebugEnabled();
        // ResultModelService service = new ResultModelServiceImpl();
        ModelAndView view = new ModelAndView();
        // resultModelService.populateResults(resultModel);
        List<Element> searchResults = resultModelService.getResults(resultModel);

        boolean hasAccess = false;
        WTReference wtVersionRef = null;
        if (searchResults != null && searchResults.size() > 0) {
            Element element = searchResults.get(0);

            ReferenceFactory refFactory = new ReferenceFactory();
            Att att = element.getAtt("obid");
            String reference = (String) att.getRawValue();
            wtVersionRef = refFactory.getReference(reference);

            hasAccess = this.getAccess(wtVersionRef);
        }

        if (hasAccess) {
            String redirectPageUrl = getRedirectPageURL(wtVersionRef);
            view.addObject("redirectUrl", redirectPageUrl);
            if (isDebugEnabled) {
                logger.debug("Redirect page url from result model is: " + redirectPageUrl);
            }
            view.setViewName(PartslinkConstants.Views.REDIRECT_PAGE_VIEW);
        }
        else {
            if (isDebugEnabled) {
                logger.debug("Redirecting to result table view since the user does not has access to requested object");
            }
            view.setViewName(PartslinkConstants.Views.RESULT_PAGE_VIEW);
        }

        return view;
    }

    /**
     * Checks access for given single VersionRef. Returns true if is accessible, false otherwise.
     *
     * @param wtVersionRef
     *            the version reference of WTPart
     * @return boolean - true if user has the access to, false otherwise.
     * @throws WTException
     *             - throws WTException, if any.
     */
    private boolean getAccess(WTReference wtVersionRef) throws WTException {
        boolean isAccessible = false;

        WTArrayList accessList = new WTArrayList(1, CollectionsHelper.VERSION_FOREIGN_KEY);
        accessList.add(wtVersionRef);

        WTKeyedHashMap accessMap = AccessControlHelper.manager.getAccess(accessList, AccessPermission.READ);
        Set<WTKeyedMap.WTEntry> entrySet = accessMap.entrySet();
        for (WTKeyedMap.WTEntry e : entrySet) {
            if ((Boolean) e.getValue()) {
                isAccessible = true;
            }
        }

        return isAccessible;
    }

    /**
     *
     * Returns the redirect page URL from WTReference.
     *
     * @param wtVersionRef
     * @return String
     * @throws WTException
     *             - throws WTException, if any.
     */
    private String getRedirectPageURL(WTReference wtVersionRef) throws WTException {
        String url = null;

        try {
            VersionForeignKey vfk = (VersionForeignKey) wtVersionRef.getKey();
            url = URLGenerator.buildInfoPageURL(vfk.getClassname(), String.valueOf(vfk.getBranchId()), false);
            url = URLDecoder.decode(url, "UTF-8");
            url = new URLFactory().getBaseHREF() + url;
        } catch (Exception exp) {
            throw new WTException("Exception occured while preparing the object infopage URL");
        }
        return url;
    }

}
