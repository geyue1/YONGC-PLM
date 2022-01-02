/**
 * ext.yongc.plm.doc.forms.processors.CreateDeliveryPackageProcessor.java
 * @Author yge
 * 2017年12月26日下午12:11:57
 * Comment 
 */
package ext.yongc.plm.doc.forms.processors;

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

import ext.yongc.plm.doc.link.DocItemLink;
import ext.yongc.plm.listener.YJDocListenerHelper;
import ext.yongc.plm.part.link.WGJPartLink;



public class CreateDeliveryPackageProcessor extends CreateDocFormProcessor{
	Logger logger = LogR.getLogger(CreateDeliveryPackageProcessor.class.getName());
	
	public  FormResult doOperation(NmCommandBean paramNmCommandBean, List<ObjectBean> paramList)
		    throws WTException{
		 FormResult result = super.doOperation(paramNmCommandBean, paramList);
		 boolean force = SessionServerHelper.manager.setAccessEnforced(false);
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
						   DocItemLink link = DocItemLink.newDocItemLink();
							link.setRoleAmasterida2a2(rf.getReferenceString(doc.getMaster()));
							link.setRoleAname(doc.getName());
							link.setRoleAnumber(doc.getNumber());
							link.setCreateBy(currentUser.getFullName());
							link.setLinkType(DocItemLink.LINK_TYPE_DeliveryPackage);
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
							}else if(obj instanceof WTDocument){
								WTDocument objDoc = (WTDocument)obj;
								link.setRoleBida2a2(rf.getReferenceString(objDoc));
								link.setRoleBname(objDoc.getName());
								link.setRoleBnumber(objDoc.getNumber());
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
		 return result;
	}
	
	
}
