/**
 * ext.yongc.plm.listener.YJPLMEventListener.java
 * @Author yge
 * 2017年7月31日下午2:42:39
 * Comment 
 */
package ext.yongc.plm.listener;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;

import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.util.DocUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WorkflowUtil;
import wt.change2.ChangeHelper2;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.content.ApplicationData;
import wt.content.ContentServerHelper;
import wt.content.ContentServiceEvent;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.events.KeyedEvent;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.collections.WTKeyedMap;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.recent.RecentUpdate;
import wt.services.ServiceEventListenerAdapter;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import wt.vc.wip.WorkInProgressServiceEvent;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.engine.WfProcess;

public class YJPLMEventListener extends ServiceEventListenerAdapter{

	private static final Logger logger = LogR.getLogger(YJPLMEventListener.class.getName());
	/**
	 * @param arg0
	 */
	public YJPLMEventListener(String arg0) {
		super(arg0);
	}
	
	public void notifyVetoableEvent(Object event) throws Exception {
		if (!(event instanceof KeyedEvent)) {
			return;
		}
		wt.events.KeyedEvent keyedEvent = (wt.events.KeyedEvent) event;
		logger.debug("keyedEvent-------------------->"+keyedEvent.getEventType());
		Object target = keyedEvent.getEventTarget();
			if (keyedEvent.getEventType().equals(WorkInProgressServiceEvent.POST_CHECKIN)) {	
				if (target instanceof WTDocument) {
					   WTDocument doc = (WTDocument)target;
						String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
						if(doc.getState().toString().equalsIgnoreCase("INWORK") || doc.getState().toString().equalsIgnoreCase("REWORK")){
							if(isNeedDoc(type)){
								 YJDocListenerHelper helper = new YJDocListenerHelper(doc);
								 helper.process();
							}
						}
						
						
				}
			}else if(keyedEvent.getEventType().equals(PersistenceManagerEvent.POST_STORE)){
				if (target instanceof RecentUpdate) {
						RecentUpdate ru = (RecentUpdate)target;
						String oid = ru.getBusinessObjectRef();
					    logger.debug("oid------->"+oid);
					    if(oid.contains("wt.doc.WTDocument")){
					    	  ReferenceFactory rf = new ReferenceFactory();
							     WTDocument doc =(WTDocument) rf.getReference(oid).getObject();
							 	String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
								String version = doc.getVersionIdentifier().getValue();
								String iteration  = doc.getIterationIdentifier().getValue();
							 	logger.debug("type------->"+type);
							    String folderPath =  doc.getFolderPath();
							    logger.debug("folderPath>>>>>>>>>>>>>>>>>>>>>>>>"+folderPath);
							    if(folderPath.contains("/System")){
							    	return;
							    }
								
									//签名
									if(doc.getState().toString().equalsIgnoreCase("INWORK") || doc.getState().toString().equalsIgnoreCase("REWORK")){
										logger.debug("version------->"+version);
										logger.debug("iteration------>"+iteration);
										if(isNeedDoc(type) && ("1".equals(version) || "A".equalsIgnoreCase(version)) && "1".equals(iteration)){
											 YJDocListenerHelper helper = new YJDocListenerHelper(doc);
											 helper.process();
										}
									}
					    }else if(oid.contains("wt.change2.WTChangeOrder2")){
					    	  ReferenceFactory rf = new ReferenceFactory();
					    	  WTChangeOrder2 ecn = (WTChangeOrder2)rf.getReference(oid).getObject();
								 String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
								if(isNeedChange(type)){
									YJChangeListenerHelper helper =new YJChangeListenerHelper(ecn);
									helper.updateSecondaryConetent();
								}
					    }
					   
					}else if(target instanceof WfProcess){
						WfProcess process = (WfProcess)target;
						WfProcessTemplate template =(WfProcessTemplate) process.getTemplate().getObject();
						logger.debug("template name------->"+template.getName());
						Object obj = WorkflowUtil.getGlobalVariable(process, "primaryBusinessObject");
						if(obj instanceof WTChangeOrder2){
							WTChangeOrder2 ecn = (WTChangeOrder2)obj;
							 String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
				    		 logger.debug("type------------->"+type);
				    		 logger.debug("isNeedChange----------->"+isNeedChange(type));
							logger.debug("isTemplate--------->"+isTemplate(template.getName()));
							if(isNeedChange(type) && !isTemplate(template.getName())){
								 YJChangeListenerHelper helper = new YJChangeListenerHelper(ecn);
								 helper.process();
								 helper.updateSecondaryConetent();
								 PersistenceHelper.manager.delete(process);
							}
						}else if(obj instanceof WTChangeActivity2){
							
							WTChangeActivity2 eca = (WTChangeActivity2)obj;
							 QueryResult qr = ChangeHelper2.service.getChangeOrder(eca);
							 logger.debug("qr size------------------->"+qr.size());
					    	 while(qr.hasMoreElements()){
					    		 WTChangeOrder2 order = (WTChangeOrder2)qr.nextElement();
					    		 String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(order).getTypename();
					    		 logger.debug("type------------->"+type);
					    		 logger.debug("isNeedChange----------->"+isNeedChange(type));
					    		 if(isNeedChange(type)){
					    			 PersistenceHelper.manager.delete(process);
					    			 break;
					    		 }
					    	 }
						}
					}
				
				
				if(target instanceof WTDocument){
					WTDocument doc = (WTDocument)target;
					String version = doc.getVersionIdentifier().getValue();
					String iteration = doc.getIterationIdentifier().getValue();
					String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
					if((version.equalsIgnoreCase("A") || "1".equals(version)) && "1".equals(iteration)&& type.indexOf(TypeConstants.DOC_DP)>-1){
						YJDocListenerHelper helper = new YJDocListenerHelper(doc);
						helper.startDeliveryPackageProcess();
					}
				}
			}
			
			if(keyedEvent.getEventType().equals("POST_WORKSPACE_CHECKIN")){
				logger.debug(">>>>>>>>>>>>POST_WORKSPACE_CHECKIN");
				if (target instanceof WTKeyedMap) {
					WTKeyedMap map = (WTKeyedMap) target;
					Iterator iter = map.keySet().iterator();
					while (iter.hasNext()) {
						Persistable p = ((ObjectReference) iter.next()).getObject();
						if(p instanceof EPMDocument){
							EPMDocument epm = (EPMDocument) p;
						YJEPMListenerHelper helper = new YJEPMListenerHelper(epm);
						helper.epmCheckin();
						}
			     	}
				}
			}
			
	}
	private boolean isNeedDoc(String docType){
		boolean flag = false;
		if(StringUtil.isEmpty(docType)){
			return flag;
		}
		
		if(docType.indexOf(TypeConstants.DOC_FX1)>-1 || docType.indexOf(TypeConstants.DOC_FX2)>-1 ||
				docType.indexOf(TypeConstants.DOC_GY)>-1	|| docType.indexOf(TypeConstants.DOC_GZ)>-1 ||
				docType.indexOf(TypeConstants.DOC_MB1)>-1 || docType.indexOf(TypeConstants.DOC_MB2)>-1 ||
				docType.indexOf(TypeConstants.DOC_YF)>-1){
			flag = true;
		}
		
		return flag;
	}
	
	private boolean isNeedChange(String type){
		boolean flag = false;
		if(StringUtil.isEmpty(type)){
			return flag;
		}
		
		if(type.indexOf(TypeConstants.ECN_3DDesign)>-1 || type.indexOf(TypeConstants.ECN_MBOM)>-1 ||
			type.indexOf(TypeConstants.ECN_YFDOC)>-1 || type.indexOf(TypeConstants.ECN_MB1)>-1 || 
			type.indexOf(TypeConstants.ECN_MB2)>-1 || type.indexOf(TypeConstants.ECN_TYWJML)>-1 ||
			type.indexOf(TypeConstants.ECN_FX1)>-1 || type.indexOf(TypeConstants.ECN_FX2)>-1 ||
			type.indexOf(TypeConstants.ECN_GY)>-1 || type.indexOf(TypeConstants.ECN_GZ)>-1 ||
			type.indexOf(TypeConstants.ECN_JSTZS)>-1 ||type.indexOf(TypeConstants.ECN_ZYDFJ)>-1){
			flag = true;
		}
		
		return flag;
	}

	private boolean isTemplate(String name){
		boolean flag = false;
		if(StringUtil.isEmpty(name)){
			return true;
		}
		
		if(name.indexOf(WorkflowConstants.WF_ECN_3DDesignDoc)>-1 || name.indexOf(WorkflowConstants.WF_ECN_MBOM)>-1 ||
			name.indexOf(WorkflowConstants.WF_ECN_YFDOC)>-1 || name.indexOf(WorkflowConstants.WF_ECN_MB1)>-1 ||
			name.indexOf(WorkflowConstants.WF_ECN_MB2)>-1 || name.indexOf(WorkflowConstants.WF_ECN_TYWJML)>-1){
			flag = true;
		}
		
		return flag;
	}
}
