/**
 * ext.yongc.plm.change.forms.filters.YJAffectedAndResultObjectTaskFilter.java
 * @Author yge
 * 2017年8月28日下午4:10:52
 * Comment 
 */
package ext.yongc.plm.change.forms.filters;


import wt.change2.WTChangeOrder2;
import wt.fc.Persistable;
import wt.maturity.PromotionNotice;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WorkItem;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.util.WorkflowUtil;

public class YJAffectedAndResultObjectTaskFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof WorkItem){
        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
        	
				Persistable p = (Persistable) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
				if(p instanceof WTChangeOrder2){
	        		return UIValidationStatus.ENABLED;
				}
    }
        return uivalidationstatus;
 }

}
