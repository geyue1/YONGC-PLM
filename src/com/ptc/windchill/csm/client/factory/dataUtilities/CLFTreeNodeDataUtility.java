package com.ptc.windchill.csm.client.factory.dataUtilities;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.HTMLEncoder;
import wt.util.WTException;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.IconComponent;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.core.components.rendering.guicomponents.UrlDisplayComponent;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.misc.NmAction;
import com.ptc.netmarkets.util.misc.NmActionServiceHelper;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.csm.client.utils.CSMUtils;
import com.ptc.windchill.csm.common.CsmConstants;

public class CLFTreeNodeDataUtility extends AbstractDataUtility {

	private static final Logger LOGGER = LogR.getLogger( CLFTreeNodeDataUtility.class.getName() );
	ResourceBundle csmResource;

	@Override
	public Object getDataValue(String component_id, Object datum,
			ModelContext mc) throws WTException {

		csmResource = ResourceBundle.getBundle(CsmConstants.CSM_CLIENT_RESOURCE, mc.getLocale());

		if (CSMUtils.isRootNode(datum)) {
			return new TextDisplayComponent(component_id, csmResource.getString("ROOT_NODE"));
		}

		GUIComponentArray iconAndName = new GUIComponentArray();

		TypeDefinitionReadView rv = CSMTypeDefHelper.getRV(datum);

		String img = "wt/clients/images/classnode.gif"; //wt.clients.csm.classification.ClassificationRB.NODE_IMAGE;
		IconComponent icon = new IconComponent(img);
		UrlDisplayComponent name = null;

		iconAndName.addGUIComponent(icon);

		final NmAction anAction = getNodeInfoAction();
		anAction.setAction( rv.getOid().idAsString() );
		final String displayName = PropertyHolderHelper.getDisplayName( rv, SessionHelper.getLocale() );
		// need to escape the name in case it has HTML tags in it
		anAction.setDesc( HTMLEncoder.encodeForHTMLContent(displayName) );

		final NmOid nmOid = NmOid.newNmOid( rv.getOid() );
		anAction.setContextObject( nmOid );
		try {
			String url = anAction.getActionUrlExternal();
			name = new UrlDisplayComponent(displayName, url);
			name.setLabelForTheLink(displayName);
			iconAndName.addGUIComponent(name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return iconAndName;
	}

	public NmAction getNodeInfoAction() throws WTException {
		return NmActionServiceHelper.service.getAction( "csm", "csmNodeInfo" );
	}


}
