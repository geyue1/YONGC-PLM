/**
 * ext.yongc.plm.change.forms.filters.createYFDocChangeOrder2Filter.java
 * @Author yge
 * 2017年8月23日下午1:56:42
 * Comment 
 */
package ext.yongc.plm.change.forms.filters;

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

public class CreateYFDocChangeOrder2Filter  extends DefaultSimpleValidationFilter{
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
				if(type.indexOf(TypeConstants.DOC_YF)>-1 && !type.contains(TypeConstants.DOC_YF_TYML) && 
						!type.contains(TypeConstants.DOC_YF_WJML)&& !type.contains(TypeConstants.DOC_YF_JSTZS)&&
						!type.contains(TypeConstants.DOC_YF_ZYDFJ) && doc.getState().toString().equalsIgnoreCase("RELEASED")){
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
