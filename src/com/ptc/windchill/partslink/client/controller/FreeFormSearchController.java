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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wt.httpgw.URLFactory;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.WTMessage;

import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.FreeFormModel;
import com.ptc.windchill.partslink.service.FreeFormSearchServiceImpl;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * Controller class for Free Form Search functionality.
 */
@Controller
public class FreeFormSearchController {

	/** The Constant logger. */
	private static final Logger logger = LogR.getLogger(FreeFormSearchController.class.getName());

	/** The Free From Search Model Service. */
	private FreeFormSearchServiceImpl freeFormModelService = new FreeFormSearchServiceImpl();

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
		Locale locale = request.getLocale();
		if (locale == null) {
			locale = SessionHelper.getLocale();
		}
		return processRequest(request, response, locale);
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
        final boolean isDebug = logger.isDebugEnabled();

        logger.debug("In FreeFormSearchController.processRequest()");
        ModelAndView result = new ModelAndView();

        if (!PartsLinkUtils.shouldRenderActionOnRoleBasedCheck(PartslinkConstants.CLASSIFICATION_SEARCH_ACTION_NAME)) {
            result.addObject("message", WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.AUTHORIZATION_ERROR, null));
            result.setViewName(PartslinkConstants.Views.NO_CLASSIFICATION_ACCESS_VIEW);
            return result;
        }
        String freeFormSearchText = "";
        FreeFormModel freeFormModel = null;

        String matchedClass = "";
        freeFormSearchText = request.getParameter(PartslinkConstants.RequestParameters.PARAM_FREEFORM_TEXT);

        if (isDebug) {
            logger.debug("FreeForm Search Text:: " + freeFormSearchText);
        }

        try {
            // Creates FreeFormModel
            freeFormModel = freeFormModelService.getFreeFormModel(freeFormSearchText, request, locale);

            if (isDebug) {
                logger.debug("freeFormModel:: " + freeFormModel);
            }

            request.getSession().setAttribute(PartslinkConstants.Model_IDS.FREEFORM_MODEL, freeFormModel);

            if (freeFormModel != null && freeFormModel.getMostMatchedClass() != null) {
                matchedClass = freeFormModel.getMostMatchedClass().getClassInternalName();
            }

            if (isDebug) {
                logger.debug("MatchedClass= " + matchedClass);
            }

        } catch (Exception e) {
            request.setAttribute(PartslinkConstants.RequestAttributes.FREEFORM_PARSE_ERROR, true);
            result.setViewName(PartslinkConstants.Views.START_URL);
            request.setAttribute(PartslinkConstants.RequestParameters.PARAM_FREEFORM_TEXT, freeFormSearchText);
        }

        if (freeFormModel != null && !freeFormModel.isError()) {
            if (freeFormModel.getMatchedClassesCount() > 0) {
                logger.debug("Redirecting to refine controller");

                String redirectPageUrl = new URLFactory().getBaseHREF();
                redirectPageUrl = redirectPageUrl + "app/#ptc1/websearch/refine?action=freeform&class=" + matchedClass;
                result.addObject("redirectUrl", redirectPageUrl);

                if (isDebug) {
                    logger.debug("Redirect page url is: " + redirectPageUrl);
                }

                result.setViewName(PartslinkConstants.Views.REDIRECT_PAGE_VIEW);
            }
            else {
                // No Result Found OR Results found more than the limit of refine threshold
                logger.debug("No result found hence redirecting to the samp page preserving the user input.");
                freeFormModel.setError(true);
                freeFormModel.setErrorMessage(WTMessage.getLocalizedMessage(RESOURCE,
                        partslinkClientResource.FREEFORM_SEARCH_NO_RESULTS, null));
                request.setAttribute(PartslinkConstants.RequestParameters.PARAM_FREEFORM_TEXT, freeFormSearchText);
                request.setAttribute(PartslinkConstants.RequestAttributes.FREEFORM_NO_RECORDS, true);
                result.setViewName(PartslinkConstants.Views.START_URL);
            }
        }
        else {
            logger.debug("There is an error in free form model.");
        }

        logger.debug("exit FreeFormSearchController.processRequest()");

        return result;
    }
}
