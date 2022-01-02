/**
 * ext.yongc.plm.doc.forms.processors.GYLXDocumentCommand.java
 * @Author yge
 * 2017年9月29日上午10:05:33
 * Comment 
 */
package ext.yongc.plm.doc.forms.processors;

import java.util.Locale;

import org.apache.log4j.Logger;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.VersionControlServerHelper;
import wt.vc.VersionControlService;

import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import ext.yongc.plm.util.PartUtil;

public class GYLXDocumentCommand {
	static Logger logger = LogR.getLogger(GYLXDocumentCommand.class.getName());
	
	   public static FormResult deleteGYLX(NmCommandBean commandBean) throws WTException{
		   logger.debug("-------------- enter deleteGYLX");
		   boolean force = SessionServerHelper.manager.setAccessEnforced(false);
		  WTDocument doc = (WTDocument) commandBean.getActionOid().getRefObject();
		  QueryResult iterationQR = VersionControlHelper.service.allIterationsFrom(doc);
		  while(iterationQR.hasMoreElements()){
			  WTDocument iterationDoc = (WTDocument)iterationQR.nextElement();
			  logger.debug("iterationDoc-------->"+IdentityFactory.getDisplayIdentifier(iterationDoc));
				 QueryResult qr=PersistenceHelper.manager.navigate(iterationDoc, WTPartDescribeLink.ROLE_AOBJECT_ROLE, WTPartDescribeLink.class);
				 while(qr.hasMoreElements()){
					 WTPart part = (WTPart)qr.nextElement();
					 logger.debug("part----->"+IdentityFactory.getDisplayIdentifier(part));
					 boolean f = PartUtil.removeDescDocToPart(part, iterationDoc);
					 logger.debug(f);
				 }
		  }
		 
			 PersistenceHelper.manager.delete(doc);
			 FormResult result=new FormResult(FormProcessingStatus.SUCCESS);
			 FeedbackMessage msg = new  FeedbackMessage(FeedbackType.SUCCESS, Locale.getDefault(), "", null, "工艺路线删除成功");
			   result.addFeedbackMessage(msg);
				
		   SessionServerHelper.manager.setAccessEnforced(force);
		   logger.debug("-------------- exit deleteGYLX");
		   return result;
	   }
}
