/**
 * ext.yongc.plm.doc.forms.processors.CreateGYLXDocFormProcessor.java
 * @Author yge
 * 2017年9月12日上午11:22:45
 * Comment 
 */
package ext.yongc.plm.doc.forms.processors;

import java.util.List;

import org.apache.log4j.Logger;

import wt.doc.WTDocument;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.doc.forms.CreateDocFormProcessor;

import ext.yongc.plm.util.DocUtil;

public class CreateGYLXDocFormProcessor extends CreateDocFormProcessor{
	
	Logger logger = LogR.getLogger(CreateGYLXDocFormProcessor.class.getName());
	
	@Override
	public  FormResult doOperation(NmCommandBean paramNmCommandBean, List<ObjectBean> paramList)
		    throws WTException{
		 logger.debug(" ----------------> enter doOpertion");
		 FormResult result = super.doOperation(paramNmCommandBean, paramList);
		 
		  Object obj = paramNmCommandBean.getActionOid().getRefObject();
		  WTPart part = null;
		  WTDocument doc = null;
		  if(obj instanceof WTPart){
			  part = (WTPart)obj;
		  }
		  
		  for (ObjectBean ob : paramList) {
			  if(ob.getObject()!=null &&(ob.getObject() instanceof WTDocument)){
				  doc = (WTDocument)ob.getObject();
				  break;
			  }
		  }
		  logger.debug("part-->"+part);
		  logger.debug("doc-->"+doc);
		  if(part!=null && doc!=null){
			  logger.debug("part----------->"+IdentityFactory.getDisplayIdentifier(part));
			  logger.debug("doc----------->"+IdentityFactory.getDisplayIdentifier(doc));
			  
			  DocUtil.associateDescDocToPart(part, doc);
		  }
		
		  logger.debug(" ----------------> exit doOpertion");
		return result;
		
	}
	
}
