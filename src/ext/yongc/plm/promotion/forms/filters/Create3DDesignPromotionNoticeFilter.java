/**
 * ext.yongc.plm.promotion.forms.filters.Create3DDesignPromotionNoticeFilter.java
 * @Author yge
 * 2017年8月10日下午6:41:00
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;

import java.rmi.RemoteException;

import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.constants.TypeConstants;

public class Create3DDesignPromotionNoticeFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof EPMDocument){
        	EPMDocument doc = (EPMDocument)obj;
        
        	try {
        		QueryResult qr = VersionControlHelper.service.allVersionsOf(doc.getMaster());
        		while(qr.hasMoreElements()){
        			doc = (EPMDocument)qr.nextElement();
        			break;
        		}
        		
				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
				String version = doc.getVersionIdentifier().getValue();
				if(("A".equalsIgnoreCase(version)||"1".equals(version)) && doc.getState().toString().equalsIgnoreCase("INWORK")){
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
