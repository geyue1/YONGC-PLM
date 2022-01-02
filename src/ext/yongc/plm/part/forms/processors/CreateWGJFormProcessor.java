/**
 * ext.yongc.plm.part.forms.processors.CreateWGJFormProcessor.java
 * @Author yge
 * 2017年11月20日下午2:54:10
 * Comment 
 */
package ext.yongc.plm.part.forms.processors;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.doc.forms.CreateDocFormProcessor;

import ext.yongc.plm.listener.YJDocListenerHelper;
import ext.yongc.plm.part.link.WGJPartLink;


public class CreateWGJFormProcessor extends CreateDocFormProcessor{
	
	Logger logger = LogR.getLogger(CreateWGJFormProcessor.class.getName());
	
	@Override
	public  FormResult doOperation(NmCommandBean paramNmCommandBean, List<ObjectBean> paramList)
		    throws WTException{
		 logger.debug(" ----------------> enter doOpertion");
		 FormResult result = super.doOperation(paramNmCommandBean, paramList);
		 boolean force = SessionServerHelper.manager.setAccessEnforced(false);
//		  Object obj = paramNmCommandBean.getActionOid().getRefObject();
//		  WTPart part = null;
		  WTDocument doc = null;
		  ReferenceFactory rf = new ReferenceFactory();
		  for (ObjectBean ob : paramList) {
			  if(ob.getObject()!=null &&(ob.getObject() instanceof WTDocument)){
				  doc = (WTDocument)ob.getObject();
				  break;
			  }
		  }
		  logger.debug("doc-->"+doc);
		  try {
			  WTUser currentUser = (WTUser)SessionHelper.getPrincipal();
				  if( doc!=null){
					  logger.debug("doc----------->"+IdentityFactory.getDisplayIdentifier(doc));
					  List<NmOid> l4 = paramNmCommandBean.getNmOidSelected();
					  for(int i=0;i<l4.size();i++){
						  Object obj = l4.get(i).getRefObject();
						  WGJPartLink link = WGJPartLink.newWGJPartLink();
							link.setRoleAmasterida2a2(rf.getReferenceString(doc.getMaster()));
							link.setRoleAname(doc.getName());
							link.setRoleAnumber(doc.getNumber());
							link.setCreateBy(currentUser.getFullName());
							if(obj instanceof WTPart){
								  WTPart part =(WTPart) obj;
									link.setRoleBida2a2(rf.getReferenceString(part));
									link.setRoleBname(part.getName());
									link.setRoleBnumber(part.getNumber());
							}else if(obj instanceof EPMDocument){
								EPMDocument epm = (EPMDocument)obj;
								link.setRoleBida2a2(rf.getReferenceString(epm));
								link.setRoleBname(epm.getName());
								link.setRoleBnumber(epm.getNumber());
							}
//						    link.setCreateTime(doc.getCreateTimestamp());
							PersistenceHelper.manager.save(link);
					  }
					  
				  }
			} catch (WTPropertyVetoException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				SessionServerHelper.manager.setAccessEnforced(force);
			}
		  logger.debug(" ----------------> exit doOpertion");
		return result;
		
	}
	public FormResult postProcess(NmCommandBean paramNmCommandBean, List<ObjectBean> paramList)
		    throws WTException {
	 logger.debug(" --------------- enter postProcess ----------------------");
	 
	 FormResult result= super.postProcess(paramNmCommandBean, paramList);
	 
	  WTDocument doc = null;
	  ReferenceFactory rf = new ReferenceFactory();
	  for (ObjectBean ob : paramList) {
		  if(ob.getObject()!=null &&(ob.getObject() instanceof WTDocument)){
			  doc = (WTDocument)ob.getObject();
			  break;
		  }
	  }
	  
	  if(doc!=null){
		  // start 
		  YJDocListenerHelper helper = new YJDocListenerHelper(doc);
			 try {
				helper.startWGJProcess();
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	  logger.debug(" --------------- exit postProcess ----------------------");
	 return result;
 }
	private void printList(List l){
		logger.debug(">>>>>>>>>>>>>>>>>>>>..printList");
		for(int i=0;i<l.size();i++){
			System.out.println(l.get(i));
		}
	}
}
