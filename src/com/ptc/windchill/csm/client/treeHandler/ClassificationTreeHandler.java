package com.ptc.windchill.csm.client.treeHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.log4j.LogR;
import wt.util.WTException;

import com.ptc.core.components.beans.TreeHandlerAdapter;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.netmarkets.model.NmSimpleOid;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.csm.client.utils.CSMUtils;
import com.ptc.windchill.csm.common.CsmConstants;


public class ClassificationTreeHandler extends TreeHandlerAdapter{

	private static final Logger LOGGER = LogR.getLogger( ClassificationTreeHandler.class.getName() );
	
	public String nameSpace = CsmConstants.NAMESPACE;

	@Override
	public void setModelContext(ModelContext mc) {
		String nameSpaceVal = mc.getNmCommandBean().getRequest().getParameter("nameSpace");
		// if nameSpaceVal is not empty the call is coming from QMS.
		if(nameSpaceVal!= null && !nameSpaceVal.isEmpty()) { 
			nameSpace = nameSpaceVal;
		}
		super.setModelContext(mc);
	}

	@Override
	public Map<Object, List> getNodes(List parents) throws WTException {

		LOGGER.debug("getNodes: Start");

		Map<Object, List> childMap = new HashMap<Object, List>();

		for(Object parent: parents) {
			List<Object> children = new ArrayList<Object>();
			childMap.put(parent, children);

			Collection<TypeDefinitionReadView> childNodes = null;
			if(CSMUtils.isRootNode(parent)) {
				childNodes = CSMTypeDefHelper.getRootTypeDefViews(nameSpace);
			} else {
				TypeDefinitionReadView parentRV = CSMTypeDefHelper.getRV(parent);
				if (parentRV.isDeleted()) {
					continue;
				}
				childNodes = CSMTypeDefHelper.getChildTypeDefViews(parentRV);
			}

			if(childNodes!=null) {
				// get persistable for each of ReadView and add to child list. Works better for cut, copy actions.
				for(TypeDefinitionReadView tdRV: childNodes) {
					if (tdRV.isDeleted()) {
						continue;
					}
					Persistable persistable = getObject(tdRV);
					children.add(persistable);
				}
			}

		}

		LOGGER.debug("getNodes: End");

		return childMap;		

	}

	@Override
	public List<Object> getRootNodes() throws WTException {
		LOGGER.debug("getRootNodes: Start");

		List<Object> rootNodes = new ArrayList<Object>();

		//Creating NmSimpleOid to display a dummy "Root" node in the classification structure.  
		NmSimpleOid simpleOid = new NmSimpleOid();
		simpleOid.setInternalName(CsmConstants.ROOT_NODE_OID);
		
		rootNodes.add(simpleOid);

		LOGGER.debug("getRootNodes: End");
		return rootNodes;
	}


	private Persistable getObject(TypeDefinitionReadView tdRV) throws WTException {
		ObjectIdentifier identifier = tdRV.getOid();
		ObjectReference ref = ObjectReference.newObjectReference(identifier);
		Persistable persistable = ref.getObject();
		return persistable;
	}


}
