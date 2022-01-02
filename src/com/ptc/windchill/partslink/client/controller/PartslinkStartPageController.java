package com.ptc.windchill.partslink.client.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wt.access.NotAuthorizedException;
import wt.httpgw.URLFactory;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.InstalledProperties;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.BrowseModel;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;
import com.ptc.windchill.partslink.service.BrowseModelService;
import com.ptc.windchill.partslink.service.BrowseModelServiceImpl;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;


/**
 * The Class PartslinkStartPageController.
 */
@Controller
public class PartslinkStartPageController {


    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(PartslinkStartPageController.class.getName());

    /** The browse model service. */
    private BrowseModelService browseModelService = new BrowseModelServiceImpl();

    /** The Constant RESOURCE. */
	private static final String RESOURCE = partslinkClientResource.class.getName();


    /**
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws WTException the wT exception
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    protected ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, WTException {
    	Locale locale = request.getLocale();
		if(locale==null){
			locale = SessionHelper.getLocale();
		}
		return processRequest(request, response, locale);
    }

    /**
     * <BR><BR><B>Supported API: </B>false
     *
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @param locale the locale
     * @return the model and view
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws WTException
     * @throws NotAuthorizedException
     */
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response, Locale locale)
            throws ServletException, IOException, NotAuthorizedException, WTException {

        ModelAndView result = new ModelAndView();
        if (!PartsLinkUtils.shouldRenderActionOnRoleBasedCheck(PartslinkConstants.CLASSIFICATION_SEARCH_ACTION_NAME)) {
            result.addObject("message", WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.AUTHORIZATION_ERROR, null));
            result.setViewName(PartslinkConstants.Views.NO_CLASSIFICATION_ACCESS_VIEW);
            return result;
        }

        boolean isDebugEnabled = logger.isDebugEnabled();
        response.setHeader("Cache-Control", "private");
        if (isDebugEnabled) {
            logger.debug("Entered processRequest");
        }
        String classInternalName = request.getParameter(PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME);
        if (isDebugEnabled) {
            logger.debug("Got class name in request as " + classInternalName);
        }

        boolean isSUMAInstalled = InstalledProperties.isInstalled(InstalledProperties.SUMA);

        String partTypes = getSumaPartTypes(request);
        if (isDebugEnabled) {
            logger.debug("Part types to be respected are : " + partTypes);
        }
        processShowImagesFlag(request);
        BrowseModel model = null;
        try {
            model = browseModelService.getBrowseModel(classInternalName, partTypes, locale);
        } catch (NotAuthorizedException e) {
            logger.error("NotAuthorizedException in PartslinkStartPageController", e);
        } catch (WTException e) {
            logger.error("Exception in PartslinkStartPageController", e);
        }

        if (isDebugEnabled) {
            logger.debug("Got browse model as " + model);
        }

        String action = request.getParameter(PartslinkConstants.RequestParameters.PARAM_ACTION);
        if (isDebugEnabled) {
            logger.debug("Got action in request as " + action);
        }
        request.getSession().removeAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL);
        request.getSession().removeAttribute(PartslinkConstants.Model_IDS.FREEFORM_MODEL);
        request.getSession().removeAttribute(PartslinkConstants.Model_IDS.FIND_SIMILAR_MODEL_SESSION);

        if ((model != null && model.isLeaf() && model.getMatchCount() != 0) || (action != null && action.equals("results"))) {
            if (isDebugEnabled) {
                logger.debug("Redirecting to refine controller");
            }
            String redirectPageUrl = new URLFactory().getBaseHREF();
            redirectPageUrl = redirectPageUrl + "app/#ptc1/websearch/refine?class=" + classInternalName + "&executeSearch=true";
            result.addObject("redirectUrl", redirectPageUrl);
            if (isDebugEnabled) {
                logger.debug("Redirect page url is: " + redirectPageUrl);
            }
            result.setViewName(PartslinkConstants.Views.REDIRECT_PAGE_VIEW);
        } else {
            if (isDebugEnabled) {
                logger.debug("Redirecting to browse page.");
            }

            long matchCount = 0;
            String currentClassName = "";
            if (model != null) {
                matchCount = model.getMatchCount();
                currentClassName = model.getCurrentNodeInternalName();
            }

            if (matchCount == 0) {
                result.addObject("message", WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.NO_PARTS_CLASSIFIED, null));
            }
            request.getSession().setAttribute(PartslinkConstants.SessionAttributes.SESSION_ATTR_LAST_VIEWED_BROWSE_NODE, currentClassName);

            request.getSession().setAttribute(PartslinkConstants.RequestParameters.CSM_SUMA_PART_TYPES, partTypes);
            result.addObject("partType", partTypes);
            result.addObject("isSumaInstalled", isSUMAInstalled);
            result.addObject(PartslinkConstants.Model_IDS.BROWSE_MODEL, model);
            result.setViewName(PartslinkConstants.Views.BROWSE_PAGE_VIEW);
        }
        result.addObject(PartslinkConstants.Model_IDS.PROPERTY_MODEL, PartslinkPropertyModel.getInstance());
        if (isDebugEnabled) {
            logger.debug("Exiting processRequest");
        }
        return result;
    }

    /**
     * Returns the part types which should be respected based on check boxes checked on classification search page else return WTPart by default
     *
     * @param request
     * @return part types
     */
    private String getSumaPartTypes(HttpServletRequest request){
    	String[] partTypesFromRequest = request.getParameterValues(PartslinkConstants.RequestParameters.CSM_SUMA_PART_TYPES);
        StringBuffer partType = new StringBuffer();
        String sumaPartTypes = "";
		if(partTypesFromRequest!=null)
		{
			for(String str:partTypesFromRequest){
				if(partType.toString().isEmpty()){
					partType.append(str);
				}else{
					partType.append(",");
					partType.append(str);
				}
			}
			sumaPartTypes = partType.toString();
		}else{
			sumaPartTypes = (String) request.getSession().getAttribute(PartslinkConstants.RequestParameters.CSM_SUMA_PART_TYPES);
			if(sumaPartTypes==null){
				sumaPartTypes = "Part";
			}
		}
		return sumaPartTypes;
    }

    /**
	 * Process show images flag.
	 *
	 * @param request the request
	 * @return the string
	 */
	private String processShowImagesFlag(HttpServletRequest request) {
		String showImages = request.getParameter(PartslinkConstants.RequestParameters.PARAM_SHOW_IMAGES);
		if (showImages == null || showImages.trim().equals("")) {
			showImages = (String) request.getSession().getAttribute(PartslinkConstants.RequestParameters.PARAM_SHOW_IMAGES);
		}
		if (showImages == null || showImages.trim().equals("")) {
			showImages = "true";
		}
		request.getSession().setAttribute(PartslinkConstants.RequestParameters.PARAM_SHOW_IMAGES, showImages);
		return showImages;
	}

}
