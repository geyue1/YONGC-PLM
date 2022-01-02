/**
 * ext.yongc.plm.doc.forms.filters.CreateDeliveryPackageFilter.java
 * @Author yge
 * 2017年12月26日上午11:13:50
 * Comment 
 */
package ext.yongc.plm.doc.forms.filters;


import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;


public class CreateDeliveryPackageFilter extends DefaultSimpleValidationFilter{
	public UIValidationStatus preValidateAction(UIValidationKey uivalidationkey,
            UIValidationCriteria uivalidationcriteria) {
        Object obj = uivalidationcriteria.getContextObject().getObject();
        UIValidationStatus uivalidationstatus = UIValidationStatus.HIDDEN;
    	try {
				    if(obj instanceof WTDocument){
				    	WTDocument doc = (WTDocument)obj;
				    		QueryResult qr = VersionControlHelper.service.allVersionsOf(doc.getMaster());
				    		while(qr.hasMoreElements()){
				    			doc = (WTDocument)qr.nextElement();
				    			break;
				    		}
				    		String state = doc.getState().toString();
							if(state.equalsIgnoreCase("RELEASED")){
				        		return UIValidationStatus.ENABLED;
				        	}
				}else if(obj instanceof WTPart){
					WTPart part = (WTPart)obj;
		    		QueryResult qr = VersionControlHelper.service.allVersionsOf(part.getMaster());
		    		while(qr.hasMoreElements()){
		    			part = (WTPart)qr.nextElement();
		    			   break;
		    		}
		    		String state = part.getState().toString();
					if(state.equalsIgnoreCase("RELEASED")){
		        		return UIValidationStatus.ENABLED;
		        	}
				}else if(obj instanceof EPMDocument){
					EPMDocument epm = (EPMDocument)obj;
		    		QueryResult qr = VersionControlHelper.service.allVersionsOf(epm.getMaster());
		    		while(qr.hasMoreElements()){
		    			epm = (EPMDocument)qr.nextElement();
		    			break;
		    		}
		    		String state = epm.getState().toString();
					if(state.equalsIgnoreCase("RELEASED")){
		        		return UIValidationStatus.ENABLED;
		        	}
				}
    	} catch (WTException e) {
			e.printStackTrace();
		}
        return uivalidationstatus;
	}

}
