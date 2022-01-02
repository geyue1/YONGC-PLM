/**
 * ext.yongc.plm.part.forms.filters.WGJListFilter.java
 * @Author yge
 * 2017年11月20日下午4:36:47
 * Comment 
 */
package ext.yongc.plm.part.forms.filters;

import java.rmi.RemoteException;

import wt.change2.WTChangeOrder2;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.util.WTException;
import wt.workflow.definer.WfDefinerHelper;
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

public class WGJListFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
    	try {
		        if(obj instanceof WTDocument){
		        	WTDocument doc = (WTDocument)obj;
		        	String type= TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
						if(type.indexOf(TypeConstants.DOC_WGJ)>-1){
			        		return UIValidationStatus.ENABLED;
			        	}
		       }else  if(obj instanceof WorkItem){
		        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
		        	String name = process.getTemplate().getName();
					if(name.equals(WorkflowConstants.WF_WGJ)){
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
