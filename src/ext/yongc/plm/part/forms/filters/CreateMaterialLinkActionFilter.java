/**
 * ext.yongc.plm.part.forms.filters.CreateMaterialLinkActionFilter.java
 * @Author yge
 * 2017年9月18日下午1:57:12
 * Comment 
 */
package ext.yongc.plm.part.forms.filters;


import wt.part.WTPart;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;

public class CreateMaterialLinkActionFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
        if(obj instanceof WTPart){
        	WTPart part = (WTPart)obj;
        	String state = part.getState().toString();
				if( state.equalsIgnoreCase("INWORK") || state.equalsIgnoreCase("REWORK")){
	        		return UIValidationStatus.ENABLED;
	        	}
    }
        return uivalidationstatus;
 }

}
