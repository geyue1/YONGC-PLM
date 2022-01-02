/**
 * ext.yongc.plm.promotion.forms.filters.YJGongYiActionFilter.java
 * @Author yge
 * 2017年7月9日下午6:00:28
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;

import java.rmi.RemoteException;

import wt.fc.Persistable;
import wt.maturity.PromotionNotice;
import wt.util.WTException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.engine.WfActivity;
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

public class YJGongYiActionFilter extends DefaultSimpleValidationFilter{
	 public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
	            UIValidationCriteria uivalidationcriteria) {
	        Object obj = uivalidationcriteria.getContextObject().getObject();
	        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
	        if(obj instanceof WorkItem){
	        	WorkItem item = (WorkItem)obj;
	       
	        	WfActivity wa = (WfActivity) item.getSource().getObject();
	         	System.out.println(" wa name ------------->"+wa.getName());
	        	if(WorkflowConstants.ACTIVITY_gongyifenpei.equalsIgnoreCase(wa.getName())){
	        		return UIValidationStatus.ENABLED;
	        	}
	    }
	        return uivalidationstatus;
	 }
}
