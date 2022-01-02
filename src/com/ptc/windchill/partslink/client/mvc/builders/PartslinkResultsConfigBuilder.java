package com.ptc.windchill.partslink.client.mvc.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.HTMLEncoder;
import wt.util.InstalledProperties;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.util.TypeHelper;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.AbstractComponentConfigBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentBuilderType;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.mvc.components.ds.DataSourceComponentConfig;
import com.ptc.mvc.components.ds.DataSourceMode;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;

@ComponentBuilder(value = "partslink.resultsTable", type = ComponentBuilderType.CONFIG_ONLY)
public class PartslinkResultsConfigBuilder extends AbstractComponentConfigBuilder 
{

	private static final Logger logger = LogR.getLogger(PartslinkResultsConfigBuilder.class.getName());

	ThreadLocal<List<String>> attributeList = new ThreadLocal<List<String>>();

	@Override
	public ComponentConfig buildComponentConfig(ComponentParams params)
			throws WTException {
		long buildCompConfigTime = 0;
		if (logger.isDebugEnabled()) {
			buildCompConfigTime = System.currentTimeMillis();
		}        


		NmHelperBean helper = ((JcaComponentParams) params).getHelperBean();
		NmCommandBean nmCommandBean = helper.getNmCommandBean();
		HttpServletRequest request = nmCommandBean.getRequest();

		String clfNode = getParameterValue(request, PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME);
		String objectType = getParameterValue(request, PartslinkConstants.RequestParameters.PARAM_OBJECT_TYPE);

		initAttributeList(objectType);

		if (clfNode == null || clfNode.isEmpty()) {
			throw new IllegalArgumentException("Classification node is required.");
		}

		if (objectType == null || objectType.isEmpty()) {
			throw new IllegalArgumentException("Object type is required.");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Got Classification node as " + clfNode);
		}

		TypeDefinitionReadView clfNodeReadView = CSMTypeDefHelper.getClassificationTypeDefView(clfNode);

		if (clfNodeReadView == null) {
			throw new WTException("Could not featch classification node for " + clfNode);
		}

		Collection<AttributeDefinitionReadView> typeAttributes = clfNodeReadView.getAllAttributes();
		ComponentConfigFactory factory = getComponentConfigFactory();
		TableConfig table = factory.newTableConfig();
		table.setType(objectType);
		String title = WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
				partslinkClientResource.RESULT_TABLE_TITLE, null, SessionHelper.getLocale());
		table.setLabel(title + " : " + PropertyHolderHelper.getDisplayName(clfNodeReadView, SessionHelper.getLocale()));
		table.setId("partslink.resultsTable");
		table.setActionModel("partsLinkTableActions");
		table.setHelpContext("WCClassReuseResultsView");

		List<ColumnConfig> defaultColumnConfigs = getDefaultColumnConfigs(objectType, factory);
		if (defaultColumnConfigs != null && !defaultColumnConfigs.isEmpty()) {
			table.addComponents(defaultColumnConfigs);
		}

		ColumnConfig rightClickColumnConfig = getComponentConfigFactory().newColumnConfig(
				DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
		table.addComponent(rightClickColumnConfig);

		for (AttributeDefinitionReadView attributeDefReadView : typeAttributes) {
			if (!attributeDefReadView.isSystemHidden()) {

				String attributeId = attributeDefReadView.getIBARefView().getLogicalIdentifier();
				if (attributeId == null || attributeId.isEmpty()) {
					AttributeTypeIdentifier ati = attributeDefReadView.getAttributeTypeIdentifier();
					attributeId = this.getTypeAttributeShortKey(ati.toExternalForm());
				}

				addToAttributeList(attributeId);

				ColumnConfig clfAttribColumnConfig = factory.newColumnConfig(attributeId, HTMLEncoder
						.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(attributeDefReadView,
								SessionHelper.getLocale())), true);
				table.addComponent(clfAttribColumnConfig);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Classification result table component config built in time "
					+ (System.currentTimeMillis() - buildCompConfigTime));
		}

		((DataSourceComponentConfig) table).setDataSourceMode(DataSourceMode.ASYNCHRONOUS);        

		return table;
	}
	
	/**
	* Returns the attribute name with only type of attribute i.e. MBA|name, IBA|Color etc. after removing the context
	* part.
	* 
	* For example: WCTYPE|wt.part.WTPart~MBA|number it returns the last occurrence of an attribute (the string that
	* starts with ~), in example above, it will return ~MBA|number
	* 
	* @param typeKey
	*            - attribute external form
	* @return attrNameType - Attribute name with only type of attribute
	*/

	private String getTypeAttributeShortKey(String typeKey) {
	    String key = null;
	    if (typeKey != null) {
	        int index = typeKey.lastIndexOf(TypeHelper.TYPE_ATTRIBUTE_SEPARATOR);

	        if (index != -1) {
	        key = typeKey.substring(index + 1);
	        }
	    }

	    return key;
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
		String paramValue = "";
		Object reqAttribute = request.getAttribute(paramName);

		if (reqAttribute instanceof String) {
			paramValue = (String) reqAttribute;
		}

		if (paramValue == null || "".equals(paramValue.trim())) {
			paramValue = request.getParameter(paramName);
		}

		return paramValue;
	}
	
	private void addToAttributeList(String attribute) {
		List<String> attributes = attributeList.get();
		attributes.add(attribute);
	}

	private void initAttributeList(String objectType) {
		List<String> attributes = new ArrayList<String>();
		attributeList.set(attributes);

		List<String> defaultColumns = PartslinkPropertyModel.getInstance().getDefaultColumns(objectType);        
		attributes.addAll(defaultColumns);

	}
	
	/**
	 * Gets the default column configurations.
	 * 
	 * @param objectType
	 *            the object type
	 * @param factory
	 *            the factory
	 * @return the default column configurations
	 */
	protected List<ColumnConfig> getDefaultColumnConfigs(String objectType, ComponentConfigFactory factory)
			throws WTException {
		List<String> defaultColumns = PartslinkPropertyModel.getInstance().getDefaultColumns(objectType);
		List<ColumnConfig> defaultColumnConfigs = new ArrayList<ColumnConfig>();

		if (defaultColumns != null && !defaultColumns.isEmpty()) {

			for (String col : defaultColumns) {

				if (DescriptorConstants.ColumnIdentifiers.ICON.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					defaultColumnConfigs.add(columnConfig);
				}
				else if (DescriptorConstants.ColumnIdentifiers.GENERAL_STATUS_FAMILY.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.SMALL_THUMBNAIL.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_THUMBNAIL_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else if (DescriptorConstants.ColumnIdentifiers.INFO_ACTION.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.FINDSIMILAR_ICON.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_FINDSIMILAR_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else if (DescriptorConstants.ColumnIdentifiers.VERSION.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);

					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.SOURCING_STATUS.equalsIgnoreCase(col)) {
					if (InstalledProperties.isInstalled(InstalledProperties.SUMA)) {
						ColumnConfig columnConfig = factory.newColumnConfig(col, true);
						columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
								partslinkClientResource.RESULTTABLE_SOURCING_STATUS_COL, null,
								SessionHelper.getLocale()));
						defaultColumnConfigs.add(columnConfig);
					}
				}
				else if (PartslinkConstants.ResultTable.REVISION.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_REVISION_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.VIEW.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_VIEW_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);

					defaultColumnConfigs.add(columnConfig);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Default column list is " + defaultColumnConfigs);
		}

		return defaultColumnConfigs;
	}
	
    /**
     * Get DataSourceMode
     * @return
     */
	public DataSourceMode getDataSourceMode(){
		return DataSourceMode.ASYNCHRONOUS;
	}


}
