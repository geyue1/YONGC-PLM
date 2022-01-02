/**
 * ext.yongc.plm.promotion.forms.filters.YJMBOMPromotionListActionFilter.java
 * @Author yge
 * 2017年7月19日下午3:04:59
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;

import java.rmi.RemoteException;

import wt.fc.Persistable;
import wt.maturity.PromotionNotice;
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
import ext.yongc.plm.util.WorkflowUtil;

public class YJMBOMPromotionListActionFilter extends DefaultSimpleValidationFilter{
    
	 public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
	            UIValidationCriteria uivalidationcriteria) {
	        Object obj = uivalidationcriteria.getContextObject().getObject();
	        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
	        if(obj instanceof WorkItem){
	        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
	        	
					Persistable p = (Persistable) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
					if(p instanceof PromotionNotice){
						PromotionNotice promotion = (PromotionNotice)p;
						String type;
						try {
							type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(promotion).getTypename();
							if(type.indexOf(TypeConstants.MBOMPromotionNotice)>-1){
								return UIValidationStatus.ENABLED;
							  }
						} catch (RemoteException e) {
							e.printStackTrace();
						} catch (WTException e) {
							e.printStackTrace();
						}
						}
					}
	      
	        return uivalidationstatus;
	    }
}
