/**
 * ext.yongc.plm.change.forms.filters.CreateMBOMChangeOrder2Filter.java
 * @Author yge
 * 2017年8月22日下午2:56:11
 * Comment 
 */
package ext.yongc.plm.change.forms.filters;

import java.rmi.RemoteException;

import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

public class CreateMBOMChangeOrder2Filter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof WTPart){
        	WTPart part = (WTPart)obj;
        
        	try {
        		QueryResult qr = VersionControlHelper.service.allVersionsOf(part.getMaster());
        		while(qr.hasMoreElements()){
        			part = (WTPart)qr.nextElement();
        			break;
        		}
				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(part).getTypename();
				if( part.getState().toString().equalsIgnoreCase("RELEASED")){
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
