/**
 * ext.yongc.plm.promotion.forms.filters.CreateMBOMPromotionNoticeFilter.java
 * @Author yge
 * 2017年8月22日下午3:02:57
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;

import java.rmi.RemoteException;

import wt.part.WTPart;
import wt.util.WTException;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

public class CreateMBOMPromotionNoticeFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof WTPart){
        	WTPart part = (WTPart)obj;
        
        	try {
				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(part).getTypename();
				String version = part.getVersionIdentifier().getValue();
				if(("A".equalsIgnoreCase(version)||"1".equals(version)) && part.getState().toString().equalsIgnoreCase("INWORK")){
	        		return UIValidationStatus.ENABLED;
	        	}
        	} catch (RemoteException e) {
				e.printStackTrace();
			} catch (WTException e) {
				e.printStackTrace();
			}
    }
        return uivalidationstatus;
 }

}
