/**
 * ext.yongc.plm.change.search.YJAffectedObjectsPickerConfig.java
 * @Author yge
 * 2017年8月10日下午4:56:54
 * Comment 
 */
package ext.yongc.plm.change.search;

import java.rmi.RemoteException;
import java.util.Locale;

import wt.session.SessionHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.ui.componentRB;
import com.ptc.jca.mvc.components.AbstractPickerConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.change2.search.AffectedObjectsPickerConfig;
import com.ptc.jca.mvc.components.AbstractPickerConfig.ToolbarAttributeConfig;

import ext.yongc.plm.util.StringUtil;

public class YJAffectedObjectsPickerConfig extends AffectedObjectsPickerConfig{
	
	 private static final String COMPONENT_RB = componentRB.class.getName();
	 
	public String getBaseWhereClause(NmCommandBean paramNmCommandBean)
		    throws WTException
		  {
		
		    StringBuffer str = new StringBuffer("(checkoutInfo.state!='wrk')&(checkoutInfo.state!='wrk-p')&(checkoutInfo.state!='sb c/o')");
		    str.append("&(iterationInfo.latest='1')");
		    str.append("&(state.state='RELEASED')");
		    
		    String docType = (String) paramNmCommandBean.getTextParameter("doc_type");
		    System.out.println(" YJAffectedObjects2 -----------docType----->"+docType);
		   if(StringUtil.isNotEmpty(docType)){
			   try {
				TypeDefinitionReference softTypeDefRef = TypedUtilityServiceHelper.service
				            .getTypeDefinitionReference(docType);
				str.append("&(typeDefinitionReference.key.branchId='"+softTypeDefRef.getKey().getBranchId()+"')");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		   }
		    return str.toString();
		  }

	public AbstractPickerConfig.ToolbarAttributeConfig[] getTableSuggestAttributeAndLabel()
		    throws WTException
		  {
		    Locale localLocale = SessionHelper.getLocale();
		    String str1 = WTMessage.getLocalizedMessage(COMPONENT_RB, "ADD_BY_NUMBER", null, localLocale);
		    String str2 = WTMessage.getLocalizedMessage(COMPONENT_RB, "ADD_BY_NAME", null, localLocale);
//		    AbstractPickerConfig.ToolbarAttributeConfig[] arrayOfToolbarAttributeConfig = { new AbstractPickerConfig.ToolbarAttributeConfig( "number", str1, getSuggestableDisplayIdentityProperty()), new AbstractPickerConfig.ToolbarAttributeConfig("name", str2),
//		    		new AbstractPickerConfig.ToolbarAttributeConfig("gongyi_type", "工艺审签类型")};
		    AbstractPickerConfig.ToolbarAttributeConfig[] arrayOfToolbarAttributeConfig = { new AbstractPickerConfig.ToolbarAttributeConfig( "number", str1, getSuggestableDisplayIdentityProperty()),new AbstractPickerConfig.ToolbarAttributeConfig("gongyi_type", "工艺审签类型")};
		    return arrayOfToolbarAttributeConfig;
		  }
	
	public String getTypePickerObjectList(NmCommandBean paramNmCommandBean)
		    throws WTException
		  {
		  String docType = (String) paramNmCommandBean.getTextParameter("doc_type");
		  if(StringUtil.isNotEmpty(docType)){
			  docType = "WCTYPE|"+docType;
		  }
		 return docType;
		  }
}
