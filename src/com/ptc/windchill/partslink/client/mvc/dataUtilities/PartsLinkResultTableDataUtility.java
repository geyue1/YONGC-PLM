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
package com.ptc.windchill.partslink.client.mvc.dataUtilities;

import static com.ptc.windchill.partslink.PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME;
import static com.ptc.windchill.partslink.PartslinkConstants.RequestParameters.PARAM_OBJECT_REF;
import static com.ptc.windchill.partslink.PartslinkConstants.RequestParameters.PARAM_SEARCH_COUNT;
import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.FINDSIMILAR_ICON;
import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.META_IS_SECURED;
import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.REVISION;
import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.SMALL_THUMBNAIL;
import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.VIEW;
import static wt.index.IndexConstants.IDENTIFIER_FACTORY;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wt.change2.ChangeHelper2;
import wt.change2.VersionableChangeItem;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.log4j.LogR;
import wt.representation.Representable;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.vc.views.ViewManageable;
import wt.viewmarkup.ClashToClashReportLink;
import wt.viewmarkup.LifeCycleManagedWtMarkUp;

import com.infoengine.object.factory.Element;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.IconComponent;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.core.meta.common.AttributeIdentifier;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.type.common.TypeInstance;
import com.ptc.core.meta.type.server.TypeInstanceUtility;
import com.ptc.netmarkets.util.misc.NmAction;
import com.ptc.netmarkets.util.misc.NmActionServiceHelper;
import com.ptc.netmarkets.util.misc.NmString;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.ResultModel;

/**
 *
 * DataUtility for various PartsLink Result Table columns like Thumbnail, Find Similar Parts etc.
 *
 * <BR>
 * <BR>
 * <B>Supported API: </B>false <BR>
 * <BR>
 * <B>Extendable: </B>false
 */
public class PartsLinkResultTableDataUtility extends AbstractDataUtility {

	private static final Logger LOGGER = LogR.getLogger(PartsLinkResultTableDataUtility.class.getName());

	private static final String ACCESS_RESOURCE = "wt.access.accessResource";

	/**
	 * Returns a UI Component for a cell of the various Columns.
	 *
	 * @param component_id
	 *            Id of the column.
	 * @param datum
	 *            The cell's object.
	 * @param mc
	 *            The ModelContext.
	 *
	 * @exception WTException
	 */
	@Override
	public Object getDataValue(String component_id, Object datum, ModelContext mc) throws WTException {
		Object result = null;

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("ENTERING getDataValue");
		}

		long dataUtilityTime = 0;
		if (LOGGER.isDebugEnabled()) {
			dataUtilityTime = System.currentTimeMillis();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" -->_componentId:" + component_id);
			LOGGER.debug(" -->_datum:" + datum);
			LOGGER.debug(" -->_modelContext:" + mc);
		}

		boolean isSecured = false;
		if (datum instanceof Element) {
			Element element = (Element) datum;
			Enumeration values = element.getMetaValues(META_IS_SECURED);

			while(values.hasMoreElements()){
				Object obj = values.nextElement();
				if("true".equals(obj)) {
					isSecured = true;
					break;
				}
			}


		}

		if(isSecured){
			// returning {Secured Information}
			// get {Secured Information} message
			String message;
			try {
				message = (new WTMessage(ACCESS_RESOURCE, wt.access.accessResource.SECURED_INFORMATION, null))
						.getLocalizedMessage(SessionHelper.getLocale());
			} catch (Exception exception) {
				LOGGER.error(exception, exception);
				message = "(Secured information)";
				LOGGER.error("Unable to find access resource for wt.access.accessResource.SECURED_INFORMATION", exception);
			}

			// return TextDisplayComponent
			TextDisplayComponent tdc = new TextDisplayComponent(this.getLabel(component_id, mc), false);
			tdc.setCheckXSS(false);
			tdc.setValue(message);
			return tdc;
		}

		if (FINDSIMILAR_ICON.equals(component_id)) {
			NmAction action = NmActionServiceHelper.service.getAction("partslink", "findSimilarAction");
			action.setDesc(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
					partslinkClientResource.RESULTTABLE_FINDSIMILAR_COL_TOOLTIP, null, SessionHelper.getLocale()));
			HashMap<String, String> params = new HashMap<String, String>();

			HttpServletRequest request = mc.getNmCommandBean().getRequest();
			String clfNodeInternalName = getParameterValue(request,	PARAM_CLASS_INTERNAL_NAME);

			// Get the result model from request
			ResultModel model = (ResultModel) request.getAttribute(PartslinkConstants.Model_IDS.RESULT_MODEL);

			long resultCount = 0;
			if (model != null) {
				resultCount = model.getResultCount();
			}

			// Dig the ObjectReference out of the datum.
			ObjectReference objRef = getObjectRef(datum);

			params.put(PARAM_CLASS_INTERNAL_NAME, clfNodeInternalName);
			params.put(PARAM_SEARCH_COUNT, "" + resultCount);
			params.put(PARAM_OBJECT_REF, objRef.toString());
			action.setParams(params);
			result = action;
		}
		else if (SMALL_THUMBNAIL.equals(component_id)) {
			// Returns a UI Component for a cell of the Column. Returns an empty cell
			// (TextDisplayComponent.NBSP.getValue()) unless the cell's object is a Representable (or refers to a
			// Representable) in which case it returns a IconComponent with a special JavaScript action.
			result = TextDisplayComponent.NBSP.getValue();

			// Dig the ObjectReference out of the datum.
			ObjectReference objRef = getObjectRef(datum);

			// If the ObjectReference refers to a Representable, ClashToClashReportLink or LifeCycleManagedWtMarkUp,
			// return an IconComponent with a blank image but there is a special 'onload' JavaScript action associated
			// with the Component.
			if (objRef != null) {
				Class<?> objRefClass = objRef.getReferencedClass();
				if (Representable.class.isAssignableFrom(objRefClass) ||
						ClashToClashReportLink.class.isAssignableFrom(objRefClass) ||
						LifeCycleManagedWtMarkUp.class.isAssignableFrom(objRefClass)) {

					IconComponent icon = new IconComponent("netmarkets/images/blank24x16.gif");
					icon.addJsAction("onload", "wvsimt(this,'" + objRef.toString() + "');");
					icon.addJsAction("width", "24");
					icon.addJsAction("height", "16");
					result = icon;

					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("PartsLink:: onload action of icon: " + icon.getJsAction("onload"));
					}
				}
			}

		}
		else if (REVISION.equals(component_id)) {

			if(datum instanceof Element) {
				// get the revision value from Element
				Element element = (Element)datum;
				String versionId = element.getAtt("versionInfo.identifier.versionId").getRawValue().toString();
				String version = versionId;
				result = new NmString(version, false);
			} else {

				// Dig the ObjectReference out of the datum.
				ObjectReference objRef = getObjectRef(datum);

	            Versioned versioned = null;
	            if (objRef != null && Versioned.class.isAssignableFrom(objRef.getReferencedClass())) {
	                // objRef.inflate();
	                versioned = (Versioned) objRef.getObject();
	            } else if (datum instanceof Versioned) {
	                versioned = (Versioned) datum;
	            }

	            String versionInfo = null;
	            try {
	                boolean showRevision = true;
	                if (versioned instanceof VersionableChangeItem) {
	                    showRevision = Boolean.valueOf(ChangeHelper2.service.getTrackingChange(versioned));
	                    if (!showRevision) {
	                        if (LOGGER.isDebugEnabled()) {
	                            LOGGER.debug("Partslink:: Result Table ReviseDataUtility:ChangeItem & Tracking Change Preference is OFF or non-existent");
	                        }
	                        versionInfo = "";
	                    }
	                }
	                if (showRevision) {
	                    versionInfo = VersionControlHelper.getVersionIdentifier(versioned).getValue();
	                }
	            } catch (VersionControlException pve) {
	                LOGGER.error(pve);
	                versionInfo = null;
	            }

	            result = new NmString(versionInfo, false);
			}

		}
		else if (VIEW.equals(component_id)) {
			if (datum instanceof Element) {
				// get view name from element
				Element ele = (Element) datum;

				TextDisplayComponent textComponent = new TextDisplayComponent(this.getLabel(component_id, mc), true);
				String viewName = (String) ele.getValue("view.key");
				if(viewName!=null) {
					textComponent.setValue(viewName);
				} else {
					textComponent.setValue("");
				}
				result = textComponent;
			}
			else if (datum instanceof TypeInstance) {
				TypeIdentifier tid = (TypeIdentifier) ((TypeInstance) datum).getIdentifier().getDefinitionIdentifier();
				AttributeTypeIdentifier aid = ((AttributeTypeIdentifier) IDENTIFIER_FACTORY.get("viewName", tid));
				AttributeIdentifier[] attrIdentifier = ((TypeInstance) datum).getAttributeIdentifiers(aid);

				if (attrIdentifier.length == 0) {
					aid = ((AttributeTypeIdentifier) IDENTIFIER_FACTORY.get("view", tid));
					attrIdentifier = ((TypeInstance) datum).getAttributeIdentifiers(aid);
				}

				String viewName = (String) ((TypeInstance) datum).get(attrIdentifier[0]);
				TextDisplayComponent textComponent = new TextDisplayComponent(this.getLabel(component_id, mc), true);
				if (viewName != null) {
					textComponent.setValue(viewName);
				}
				// this is to avoid displaying "null" when part does not have any view.
				else {
					textComponent.setValue("");
				}

				result = textComponent;
			}
			else if (datum instanceof ViewManageable) {
				String viewName = ((ViewManageable) datum).getViewName();
				TextDisplayComponent textComponent = new TextDisplayComponent(this.getLabel(component_id, mc), true);
				if (viewName != null) {
					textComponent.setValue(viewName);
				}
				// this is to avoid displaying "null" when part does not have any view.
				else {
					textComponent.setValue("");
				}

				result = textComponent;
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Partslink result table data utility time " + (System.currentTimeMillis() - dataUtilityTime));
		}

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("EXITING getDataValue: " + result);
		}

		return result;
	}

	/**
	 * Gets the object reference.
	 *
	 * @param datum the datum
	 * @return the object reference
	 * @throws WTException the wT exception
	 */
	private ObjectReference getObjectRef(Object datum) throws WTException {
		ObjectReference objRef = null;
		if(datum instanceof TypeInstance) {
			TypeInstance ti = (TypeInstance) datum;
			objRef = TypeInstanceUtility.getObjectReference(ti);
		} else if (datum instanceof Persistable) {
			Persistable persistable = (Persistable) datum;
			objRef = ObjectReference.newObjectReference(persistable);
		}
		return objRef;
	}

	/**
	 * Gets the specified parameter value from request.
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param paramName
	 *            the parameter name
	 * @return parameter value
	 */
	private String getParameterValue(HttpServletRequest request, String paramName) {
		String paramValue = request.getParameter(paramName);

		if (paramValue == null || "".equals(paramValue.trim())) {
			paramValue = (String) request.getAttribute(paramName);
		}

		return paramValue;
	}

}
