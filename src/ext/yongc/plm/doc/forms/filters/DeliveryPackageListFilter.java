/**
 * ext.yongc.plm.doc.forms.filters.DeliveryPackageListFilter.java
 * @Author yge
 * 2017年12月26日下午12:31:24
 * Comment 
 */
package ext.yongc.plm.doc.forms.filters;

import java.rmi.RemoteException;

import wt.doc.WTDocument;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WorkItem;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.util.WorkflowUtil;

public class DeliveryPackageListFilter extends DefaultSimpleValidationFilter{
	
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
		System.out.println("DeliveryPackageListFilter  preValidateAction");
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
    	try {
		        if(obj instanceof WTDocument){
		        	WTDocument doc = (WTDocument)obj;
		        	String type= TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
		        	System.out.println("doc type==>"+type);
						if(type.indexOf(TypeConstants.DOC_DP)>-1){
			        		return UIValidationStatus.ENABLED;
			        	}
		       }else  if(obj instanceof WorkItem){
		        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
		        	String name = process.getTemplate().getName();
		        	System.out.println("process name==>"+name);
					if(name.equals(WorkflowConstants.WF_DP)){
		        		return UIValidationStatus.ENABLED;
					}
	         }
    	} catch (RemoteException e) {
			e.printStackTrace();
		} catch (WTException e) {
			e.printStackTrace();
		}
        return uivalidationstatus;
 }

}
