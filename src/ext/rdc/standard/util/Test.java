/**
 * ext.rdc.standard.util.Test.java
 * @Author yge
 * 2019年9月3日上午9:11:15
 * Comment 
 */
package ext.rdc.standard.util;
//windchill ext.rdc.standard.util.Test OR:com.ptc.core.lwc.server.LWCStructEnumAttTemplate:138115
import java.util.Collection;
import java.util.List;

import com.ptc.core.lwc.common.PropertyDefinitionConstants;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.GroupDefinitionReadView;
import com.ptc.core.lwc.common.view.GroupMemberView;
import com.ptc.core.lwc.common.view.GroupMembershipReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.LWCAttributeSetAttDefinition;
import com.ptc.core.lwc.server.LWCDynamicGroupDefinition;
import com.ptc.core.lwc.server.LWCGroupDefinition;
import com.ptc.core.lwc.server.LWCIBAAttDefinition;
import com.ptc.core.lwc.server.LWCStaticGroupDefinition;
import com.ptc.core.lwc.server.LWCStructEnumAttTemplate;
import com.ptc.core.lwc.server.cache.TypeDefinitionManager;
import com.ptc.core.lwc.server.cache.factory.AttributeDefinitionViewFactory;
import com.ptc.core.lwc.server.cache.factory.FactoryHelper;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

import wt.access.NotAuthorizedException;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.ReferenceFactory;
import wt.feedback.StatusFeedback;
import wt.inf.container.WTContainerException;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionHelper;
import wt.util.WTException;

public class Test implements RemoteAccess{
	public static ReferenceFactory rf = new ReferenceFactory();
	
	public static void test(String oid) throws WTException{
		LWCStructEnumAttTemplate lwc = (LWCStructEnumAttTemplate)rf.getReference(oid).getObject();
		 MethodContext.getContext().sendFeedback(new StatusFeedback("lwc="+lwc.getName()+","+lwc.getDisplayIdentifier()));
		 LWCStaticGroupDefinition groupDef = SearchUtil.getGroupByTypeDef(lwc);
		 if(groupDef!=null){
			 MethodContext.getContext().sendFeedback(new StatusFeedback("groupDef="+groupDef.getName()));
			 TypeDefinitionReadView rv = CSMTypeDefHelper.getRV(groupDef);
			 String name = PropertyHolderHelper.getPropertyValue(rv, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
			 MethodContext.getContext().sendFeedback(new StatusFeedback("groupDef displayName="+name));
		 }else{
			 MethodContext.getContext().sendFeedback(new StatusFeedback("groupDef is null"));
		 }
		 
		 List<GroupDefinitionReadView> groupsList =  SearchUtil.getGroupDefinitionReadView(CSMTypeDefHelper.getRV(lwc));
		 for(GroupDefinitionReadView rv:groupsList){
			
			 MethodContext.getContext().sendFeedback(new StatusFeedback("groupDef ="+rv.getName()));
			 String name = PropertyHolderHelper.getPropertyValue(rv, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
			 MethodContext.getContext().sendFeedback(new StatusFeedback("groupDef displayName="+name));
			 Long groupId = rv.getId();
			 LWCGroupDefinition def = TypeDefinitionManager.getTypeDefinitionManagerInstance().retrieveGroupDefFromDB( groupId );
			 MethodContext.getContext().sendFeedback(new StatusFeedback("isDynamic="+rv.isDynamic()));
			 List<GroupMembershipReadView> members = rv.getAllMembers();
			 MethodContext.getContext().sendFeedback(new StatusFeedback("members="+members.size()));
			 for(GroupMembershipReadView mv:members){
				 String mName = PropertyHolderHelper.getPropertyValue(mv, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
				  MethodContext.getContext().sendFeedback(new StatusFeedback("GroupMembershipReadView displayName="+mName+","+mv.getName()));
				  MethodContext.getContext().sendFeedback(new StatusFeedback("isDeleted="+mv.isDeleted()));
				  GroupMemberView memberView = mv.getMember();
				  ObjectIdentifier attributeSetId = ((AttributeDefinitionReadView)memberView).getOid();
				  MethodContext.getContext().sendFeedback(new StatusFeedback("attributeSetId="+attributeSetId));
				  if (attributeSetId != null){
					  LWCIBAAttDefinition attributeSet =(LWCIBAAttDefinition) ObjectReference.newObjectReference(attributeSetId).getObject();
					  if(attributeSet!=null){
						  AttributeDefinitionReadView attributeSetView = AttributeDefinitionViewFactory.newAttributeDefintionView(null, attributeSet);
						//dynamic group view only has a membership which has a member that is the attribute view
						// and since the attributeSet goes with the dynamice group, its type context is the same as the group's type context
						  String attName = PropertyHolderHelper.getPropertyValue(attributeSetView, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
						  MethodContext.getContext().sendFeedback(new StatusFeedback("attributeSetView displayName="+attName));
					  }
				  }
			 }
			 if(rv.isDynamic()){
				  LWCDynamicGroupDefinition dynamicGroupDef = (LWCDynamicGroupDefinition)def;
				  LWCAttributeSetAttDefinition  attributeSet = dynamicGroupDef.getAttributeSet();
				  if(attributeSet!=null){
					  AttributeDefinitionReadView attributeSetView = AttributeDefinitionViewFactory.newAttributeDefintionView(null, attributeSet);
					//dynamic group view only has a membership which has a member that is the attribute view
					// and since the attributeSet goes with the dynamice group, its type context is the same as the group's type context
					  String attName = PropertyHolderHelper.getPropertyValue(attributeSetView, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
					  MethodContext.getContext().sendFeedback(new StatusFeedback("attributeSetView displayName="+attName));
				  }
			 }else{
				 LWCStaticGroupDefinition staticGroupDef = (LWCStaticGroupDefinition)def;
				 
			 }
		 }
	}
	public static void test2() throws NotAuthorizedException, WTContainerException, WTException{
		TypeDefinitionReadView wtPartTypeDefReadView = CSMTypeDefHelper.getTypeDefView("wt.part.WTPart");
		Collection<AttributeDefinitionReadView> collection = wtPartTypeDefReadView.getAllAttributes();
		MethodContext.getContext().sendFeedback(new StatusFeedback("collection="+collection.size()));
		for(AttributeDefinitionReadView a:collection){
			MethodContext.getContext().sendFeedback(new StatusFeedback("a.getDisplayName()="+a.getDisplayName()));
			
			MethodContext.getContext().sendFeedback(new StatusFeedback("a.getName()="+a.getName()));
		}
		
  		AttributeDefinitionReadView attrReadView = wtPartTypeDefReadView.getAttributeByName("图纸代号");
		String atrrId = PartsLinkUtils.generateSolrAttributeID(attrReadView);
		MethodContext.getContext().sendFeedback(new StatusFeedback("atrrId="+atrrId));
		
		AttributeDefinitionReadView attrReadView2 = wtPartTypeDefReadView.getAttributeByName("DRAWINGNO");
		String atrrId2 = PartsLinkUtils.generateSolrAttributeID(attrReadView2);
		MethodContext.getContext().sendFeedback(new StatusFeedback("atrrId2="+atrrId2));
	}
    public static void main(String[] args){
    	RemoteMethodServer server = RemoteMethodServer.getDefault();
		server.setUserName("wcadmin");
		server.setPassword("wcadmin");
//		Class[] classes = { String.class };
//		Object[] objs = { args[0]};

		try {
			server.invoke("test2", Test.class.getName(), null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
}
