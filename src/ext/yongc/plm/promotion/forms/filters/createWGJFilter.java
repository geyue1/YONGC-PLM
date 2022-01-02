/**
 * ext.yongc.plm.promotion.forms.filters.createWGJFilter.java
 * @Author yge
 * 2017年11月2日上午11:36:16
 * Comment 
 */
package ext.yongc.plm.promotion.forms.filters;


import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.StringUtil;

public class createWGJFilter extends DefaultSimpleValidationFilter{
	/**
	 * 外购件必须正在工作，并且代号为15位
	 */
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
//            		IBAUtil iba = new IBAUtil(part);
//            		String daihao = iba.getIBAValue(IBAConstants.DRAWINGNO);
				if(part.getState().toString().equalsIgnoreCase("INWORK")){
	        		return UIValidationStatus.ENABLED;
	        	}
			} catch (WTException e) {
				e.printStackTrace();
			}
    }
        return uivalidationstatus;
 }

}
