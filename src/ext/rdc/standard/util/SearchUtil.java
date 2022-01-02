/**
 * ext.rdc.standard.util.SearchUtil.java
 * @Author yge
 * 2019年9月2日下午10:57:51
 * Comment 
 */
package ext.rdc.standard.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.log4j.LogR;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.HTMLEncoder;
import wt.util.WTException;

import com.ptc.core.lwc.client.util.PropertyDefinitionHelper;
import com.ptc.core.lwc.common.LayoutComponent;
import com.ptc.core.lwc.common.PropertyDefinitionConstants;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.GroupDefinitionReadView;
import com.ptc.core.lwc.common.view.GroupMemberView;
import com.ptc.core.lwc.common.view.GroupMembershipReadView;
import com.ptc.core.lwc.common.view.LayoutDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.LWCAbstractAttributeTemplate;
import com.ptc.core.lwc.server.LWCStaticGroupDefinition;
import com.ptc.core.lwc.server.LWCStructEnumAttTemplate;
import com.ptc.core.lwc.server.LWCTypeDefinition;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.csm.client.utils.CSMUtils;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.service.RefineBeanService;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

import ext.rdc.standard.model.RDCRefineBean;

public class SearchUtil {
	 private static Logger log = LogR.getLogger(SearchUtil.class.getName());
	  public static ReferenceFactory rf = new ReferenceFactory();
	  
	  /**
	   * 
	   * @description TODO
	   * @param typeDef
	   * @return
	   * @throws WTException
	   * @author yge  2019年9月3日上午10:00:52
	   */
      public static LWCStaticGroupDefinition getGroupByTypeDef(LWCAbstractAttributeTemplate typeDef) throws WTException{
    	    LWCStaticGroupDefinition groupDef = null;
    	    if(typeDef==null){
    	    	return groupDef;
    	    }
    	    QuerySpec qs = new QuerySpec(LWCStaticGroupDefinition.class);
    	    qs.appendWhere(new SearchCondition(LWCStaticGroupDefinition.class,LWCStaticGroupDefinition.CONTEXT_REFERENCE+".key.id",
    	    		          "=",typeDef.getPersistInfo().getObjectIdentifier().getId()));
    	    log.debug("qs=>"+qs);
    	    QueryResult qr =  PersistenceHelper.manager.find(qs);
    	    if(qr.hasMoreElements()){
    	    	groupDef = (LWCStaticGroupDefinition)qr.nextElement();
    	    }
    		return groupDef;
      }
      /**
       * 通过类型获取类型和属性管理器中的所有分组
       * 如果类型是分类LWCStructEnumAttTemplate，只有一个布局Default Layout
       * @description TODO
       * @param typeDef
       * @return
       * @author yge  2019年9月3日上午10:19:57
       */
      public static List<GroupDefinitionReadView> getGroupDefinitionReadView(TypeDefinitionReadView typeDefReadView ){
    	  List<GroupDefinitionReadView> groupsList = new ArrayList<GroupDefinitionReadView>();
    	  
//    	  TypeDefinitionReadView typeDefReadView = CSMTypeDefHelper.getRV(typeDef);
    	  Collection<LayoutDefinitionReadView> typeLayouts = typeDefReadView.getAllLayouts();
    	  log.debug(".....Hacking the get GroupDefReadView. Found " + typeLayouts.size() + " layouts.");
    	  for(LayoutDefinitionReadView lrv:typeLayouts){
    		  log.debug(lrv.getName());
    	  }
    	  if(typeLayouts.size()>0){
    		  for(LayoutDefinitionReadView layoutReadView:typeLayouts){
    			  groupsList.addAll(new ArrayList<GroupDefinitionReadView>(layoutReadView.getAllGroups()));
    		  }
    	  }
    	  return groupsList;
      }
      
      public static Set<RDCRefineBean> convertRDCRefineBean(RefineModel model) throws WTException{
    	  TypeDefinitionReadView typeDefReadView =model.getTypeDefRV();
    	  Set<RefineBean> attBeans = model.getRefineBeanSet();
    	  Set<RDCRefineBean> result = new HashSet<RDCRefineBean>();
    	  
    	  for(RefineBean bean:attBeans){
    		  RDCRefineBean rdcBean = new RDCRefineBean();
        	  
        	  rdcBean.setAttrDefReadView(bean.getAttrDefReadView());
        	  rdcBean.setAttrDataType(bean.getAttrDataType());
        	  rdcBean.setAttrDisplayName(bean.getAttrDisplayName());
        	  rdcBean.setAttrFacets(bean.getAttrFaucets());
        	  rdcBean.setDescUrl(bean.getDescUrl());
        	  rdcBean.setAttrUnits(bean.getAttrUnits());
        	  rdcBean.setAttrId(bean.getAttrId());
        	  if(bean.getValue()!=null){
        		  rdcBean.setValue(bean.getValue());
        	  }
        	  String internalValue = bean.getInternalValue();
        	  if (internalValue != null && internalValue.trim().length() > 0){
        		  rdcBean.setInternalValue(internalValue);
        	  }
        	  if(bean.getRangeHi()!=null){
        		  rdcBean.setRangeHi(bean.getRangeHi());
        	  }
        	  if(bean.getRangeLow()!=null){
        		  rdcBean.setRangeLow(bean.getRangeLow());
        	  }
        	  if(bean.getOperator()!=null){
        		  rdcBean.setOperator(bean.getOperator());
        	  }
        	  result.add(rdcBean);
    	  }
    	 
    	  List<GroupDefinitionReadView> groupsList = getGroupDefinitionReadView(typeDefReadView);
    	  for(GroupDefinitionReadView groupDef:groupsList){
    		  String groupName = PropertyHolderHelper.getPropertyValue(groupDef, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
    		  List<GroupMembershipReadView> members = groupDef.getAllMembers();
    		  for(GroupMembershipReadView ship:members){
    			  AttributeDefinitionReadView attDef = (AttributeDefinitionReadView) ship.getMember();
    			  String attId = PartsLinkUtils.generateSolrAttributeID(attDef);
    			  for(RDCRefineBean rdcRef:result){
    				  if(attId.equals(rdcRef.getAttrId())){
    					  rdcRef.setGroupName(groupName);
//    					  rdcRef.setGroupDef(groupDef);
    				  }
    			  }
    		  }
    	  }
    	  
    	  return result;
      }
      public static Map<String,Set<RefineBean>> getGroupRefineBean(RefineModel model) throws WTException{
    	  Map<String,Set<RefineBean>> map = new TreeMap<String,Set<RefineBean>>();
    	  
    	  TypeDefinitionReadView typeDefReadView =model.getTypeDefRV();
    	  Set<RefineBean> attBeans = model.getRefineBeanSet();
    	  List<GroupDefinitionReadView> groupsList = getGroupDefinitionReadView(typeDefReadView);
    	  for(GroupDefinitionReadView groupDef:groupsList){
    		  String groupName = PropertyHolderHelper.getPropertyValue(groupDef, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
    		  String id  = groupDef.getId().toString();
    		  List<GroupMembershipReadView> members = groupDef.getAllMembers();
    		  for(GroupMembershipReadView ship:members){
    			  AttributeDefinitionReadView attDef = (AttributeDefinitionReadView) ship.getMember();
    			  String attId = PartsLinkUtils.generateSolrAttributeID(attDef);
    			  for(RefineBean ref:attBeans){
    				  if(attId.equals(ref.getAttrId())){
    					  if(map.get(id)==null){
    						  SortedSet<RefineBean> refineBeanSet = new TreeSet<RefineBean>(new RefineSearchBeanComparator());
    						  refineBeanSet.add(ref);
    						  map.put(id, refineBeanSet);
    					  }else{
    						  map.get(id).add(ref);
    					  }
    				  }
    			  }
    		  }
    	  }
    	  
    	  Set<String> groupSet = map.keySet();
    	  for(String group:groupSet){
    		  SortedSet<RefineBean> refineBeanSet = (SortedSet<RefineBean>) map.get(group);
    	  }
    	  return map;
      }
      public static GroupDefinitionReadView getGroupFromType(String groupName, TypeDefinitionReadView typeRV) {
    	  GroupDefinitionReadView groupRV = null;
    	  for (LayoutDefinitionReadView layout : typeRV.getAllLayouts()) {
    		  LayoutComponent lc = layout.getLayoutComponentByName(groupName);
    		  if (lc != null) {
    			  groupRV = (GroupDefinitionReadView) lc;
    			  break;
    		  }
    	  }
    	  return groupRV;
      }
      public static String getClassNodeDisplayName(LWCStructEnumAttTemplate lwc,boolean getParent) throws WTException{
    	  TypeDefinitionReadView rv = CSMTypeDefHelper.getRV(lwc);
    	  if(!getParent){
    		  return PropertyHolderHelper.getDisplayName( rv, SessionHelper.getLocale() );
    	  }
    	  
    	  List<String> list = new ArrayList<String>();
    	  getDisplayName(lwc, list);
    	  StringBuffer sb = new StringBuffer();
    	  for(int i=list.size()-1;i>=0;i--){
    		  if(i==0){
    			  sb.append(list.get(i));
    		  }else{
    			  sb.append(list.get(i)+"-");
    		  }
    	  }
    	  return sb.toString();
      }
      private static void getDisplayName(LWCStructEnumAttTemplate lwc,List<String> list)throws WTException{
    	  
    	  if (lwc==null || CSMUtils.isRootNode(lwc)) {
      		 return;
    	  }
    	  
    	  TypeDefinitionReadView rv = CSMTypeDefHelper.getRV(lwc);
    	  String name = PropertyHolderHelper.getDisplayName( rv, SessionHelper.getLocale() );
    	  list.add(name);
    	  if(lwc.getParent()!=null){
    		  getDisplayName((LWCStructEnumAttTemplate)lwc.getParent(), list);
    	  }
      }
      
      public static RefineBean createWTPartIBARefineBean (NmCommandBean commandBean,String ibaId, boolean populateValue) throws WTException {
  		TypeDefinitionReadView wtPartTypeDefReadView = CSMTypeDefHelper.getTypeDefView("wt.part.WTPart");

  		AttributeDefinitionReadView attrReadView = wtPartTypeDefReadView.getAttributeByName(ibaId);
  		
  		RefineBean refineBean = RefineBeanService.createAndValidateRefineBean(attrReadView, commandBean, null, populateValue);

  		return refineBean;
  	}
}
