/* bcwti
 *
 * Copyright (c) 2010 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package com.ptc.netmarkets.workflow.mvc.treeHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.ObjectVector;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.log4j.LogR;
import wt.org.WTPrincipalReference;
import wt.project.Role;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;

import com.ptc.core.components.beans.TreeHandlerAdapter;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.model.NmSimpleOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.work.NmWorkItemCommands;
import com.ptc.netmarkets.workflow.NmWorkflowHelper;

import wt.workflow.engine.WfActivity;
import wt.workflow.engine.WfExecutionObject;
import wt.workflow.engine.WfState;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignment;
import wt.workflow.work.WorkItem;
import wt.workflow.work.WorkItemLink;
import wt.workflow.work.WorkflowHelper;

/**
 * Tree Handler for change objects workitems. 
 *
 * <BR><BR><B>Supported API: </B>false
 * <BR><BR><B>Extendable: </B>false
 * 
 **/
public class SetupParticipantsTreeHandler extends TreeHandlerAdapter {
   
	private static final Logger logger = LogR.getLogger(SetupParticipantsTreeHandler.class.getName());
   
	private static Map<Object, List> roleMembersMap = new HashMap<Object, List>();
	
	private static HashMap<Role,Boolean[]> permissionMap = new HashMap<Role,Boolean[]>();
        
   /**
	 * This method will return the list of root nodes from the command bean.
	 * 
	 * @return java.util.List
	 */
   
	public List getRootNodes() throws WTException 
	{
		List parentNodes = new ArrayList();	
		NmCommandBean cb = getModelContext().getNmCommandBean();
		NmOid oid = cb.getPrimaryOid();
		WorkItem wi = (WorkItem) oid.getRefObject();
		
		if (cb == null) {
     		return null;
		}		
		parentNodes=NmWorkItemCommands.service.getSetupParticipantsRoles(cb,0); // 0 is pass for view persmission
		roleMembersMap=NmWorkItemCommands.service.getRoleMembersMap(cb);
		permissionMap = WorkflowHelper.service.getPermissionMap(wi);
		
		//按order排序
		Collections.sort(parentNodes, new Comparator<Object>(){
			 public int compare(Object o1, Object o2) { 
					if((o1 instanceof NmSimpleOid) && (o2 instanceof NmSimpleOid)){
						 Role role1 = Role.toRole(((NmSimpleOid)o1).getInternalName());	
						 Role role2 = Role.toRole(((NmSimpleOid)o2).getInternalName());	
						 if(role1.getOrder()<role2.getOrder()){
							 return -1;
						 }else if(role1.getOrder()>role2.getOrder()){
							 return 1;
						 }else{
							 return role1.getDisplay().compareTo(role2.getDisplay());
						 }
					}
					return 0;
			 }
		});
		return parentNodes;
	}


   /**
	 * This method will return the map child (Users) for the given list of parent views.
	 * 
	 * @param parents
	 * 			list of parent objects (Roles) .
	 * 
	 * @return java.util.Map
	 */
   
	public Map getNodes(List parents) throws WTException {

		Map<Object, List> result = new HashMap<Object, List>();
		NmOid oid = getModelContext().getNmCommandBean().getPrimaryOid();
		WorkItem wi = (WorkItem) oid.getRefObject();
		
        // Get the Child Nodes : Users for the Role
		Iterator iter = parents.iterator();
		while(iter.hasNext())
		{
			Object obj=iter.next();			
			
			if(obj instanceof NmSimpleOid){
				Role parentRole = Role.toRole(((NmSimpleOid)obj).getInternalName());	
				ArrayList roleMembers=(ArrayList)roleMembersMap.get(parentRole );
				
				HashMap additionalInfo=new HashMap();
				additionalInfo.put("permissions", permissionMap.get(parentRole));
				
				ArrayList membersList=convertPrincipalsToNmOids(roleMembers,additionalInfo);
				NmSimpleOid nmSimpleOid=(NmSimpleOid)obj;
				nmSimpleOid.setAdditionalInfo(additionalInfo);
				result.put(nmSimpleOid, membersList);				
			}
		}		
		return result;
	}    
	
	private ArrayList convertPrincipalsToNmOids(ArrayList roleMembers,HashMap additionalInfo)throws WTException{
		ArrayList membersList=new ArrayList();
		if(roleMembers!=null){
			for (Iterator iter = roleMembers.iterator(); iter.hasNext();) {
				WTPrincipalReference element = (WTPrincipalReference) iter.next();
				NmOid oid=new NmOid(element.getObject());
				oid.setAdditionalInfo(additionalInfo);
				membersList.add(oid);			
			}	
		}
		return membersList;
	}
}
