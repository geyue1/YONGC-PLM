/**
 * ext.yongc.plm.doc.forms.filters.DeleteGYLXFilter.java
 * @Author yge
 * 2017年9月29日上午11:19:25
 * Comment 
 */
package ext.yongc.plm.doc.forms.filters;

import java.rmi.RemoteException;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.util.WTException;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.util.StringUtil;

public class DeleteGYLXFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof WTDocument){
        	WTDocument doc = (WTDocument)obj;
        
        	try {
        		String partState = null;
        		 QueryResult qr=PersistenceHelper.manager.navigate(doc, WTPartDescribeLink.ROLE_AOBJECT_ROLE, WTPartDescribeLink.class);
        		 while(qr.hasMoreElements()){
        			 WTPart part = (WTPart)qr.nextElement();
        			 partState = part.getState().toString();
        		 }
				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
				System.out.println("state>>>>>>>>>>>>>>>>>>>>"+doc.getState().toString());
				if(type.indexOf(TypeConstants.DOC_GYLX)>-1&& StringUtil.isNotEmpty(partState) &&
						(partState.equalsIgnoreCase("INWORK") ||partState.equalsIgnoreCase("REWORK"))){
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
