/**
 * ext.yongc.plm.promotion.search.AddYJPromotionObjectPickerConfig.java
 * @Author yge
 * 2017年7月7日上午11:16:48
 * Comment 
 */
package ext.yongc.plm.promotion.search;

import java.rmi.RemoteException;

import wt.type.TypeDefinitionReference;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;

import com.ptc.jca.mvc.components.AbstractGenericPickerConfig;
import com.ptc.jca.mvc.components.AbstractItemPickerConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.maturity.search.PromotionObjectsPickerConfig;

import ext.yongc.plm.util.StringUtil;

public class AddYJPromotionObjectPickerConfig extends PromotionObjectsPickerConfig{
	
	
	public String getPickerId()
	  {
	    return "add3DDocItemPicker";
	  }
	
	public String getComponentId(NmCommandBean paramNmCommandBean)
		    throws WTException
		  {
		    return "add3DDocItemPicker";
		  }
	
	public String getPickerCallback()
		    throws WTException
		  {
		      return "select3DDocCallback";
		  }
	
	public String getBaseWhereClause(NmCommandBean paramNmCommandBean)
		    throws WTException
		  {
		
		    StringBuffer str = new StringBuffer("(checkoutInfo.state!='wrk')&(checkoutInfo.state!='wrk-p')&(checkoutInfo.state!='sb c/o')");
		    str.append("&(iterationInfo.latest='1')");
		    str.append("&(state.state='INWORK')");
		    
		    String docType = (String) paramNmCommandBean.getTextParameter("doc_type");
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

