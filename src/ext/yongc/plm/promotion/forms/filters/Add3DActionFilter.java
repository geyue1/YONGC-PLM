/**
 * ext.yongc.plm.promotion.forms.filters.Add3DActionFilter.java
 * @Author yge
 * 2017年7月20日下午2:30:33
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;

import java.rmi.RemoteException;

import wt.epm.EPMDocument;
import wt.maturity.PromotionNotice;
import wt.util.WTException;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.constants.TypeConstants;

public class Add3DActionFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof EPMDocument){
//        	try {
//				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier((PromotionNotice)obj).getTypename();
//				if(type.indexOf("wt.epm.EPMDocument")>-1){
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
