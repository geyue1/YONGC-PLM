/**
 * ext.yongc.plm.promotion.forms.filters.CreateFXDoc1PromotionFilter.java
 * @Author yge
 * 2017年7月21日下午5:35:56
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;

import java.rmi.RemoteException;

import wt.doc.WTDocument;
import wt.fc.QueryResult;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.constants.TypeConstants;

public class CreateFXDoc1PromotionFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof WTDocument){
        	WTDocument doc = (WTDocument)obj;
        
        	try {
        		QueryResult qr = VersionControlHelper.service.allVersionsOf(doc.getMaster());
        		while(qr.hasMoreElements()){
        			doc = (WTDocument)qr.nextElement();
        			break;
        		}
        		
				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
				if(type.indexOf(TypeConstants.DOC_FX1)>-1 && doc.getState().toString().equalsIgnoreCase("INWORK")){
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
