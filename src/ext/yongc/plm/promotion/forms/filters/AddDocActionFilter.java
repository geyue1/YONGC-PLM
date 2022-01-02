/**
 * ext.yongc.plm.promotion.forms.filters.AddDocActionFilter.java
 * @Author yge
 * 2017年7月20日下午2:35:49
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;

import java.rmi.RemoteException;

import wt.doc.WTDocument;
import wt.maturity.PromotionNotice;
import wt.util.WTException;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.constants.TypeConstants;

public class AddDocActionFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof WTDocument){
//        	try {
//				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier((PromotionNotice)obj).getTypename();
//				if(type.indexOf("wt.doc.WTDocument")>-1){
	        		return UIValidationStatus.ENABLED;
//	        	}
//        	} catch (RemoteException e) {
//				e.printStackTrace();
//			} catch (WTException e) {
//				e.printStackTrace();
//			}
    }
        return uivalidationstatus;
 }
}
