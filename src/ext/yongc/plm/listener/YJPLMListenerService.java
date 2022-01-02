/**
 * ext.yongc.plm.listener.YJPLMListenerService.java
 * @Author yge
 * 2017年7月31日下午2:03:23
 * Comment 
 */
package ext.yongc.plm.listener;

import wt.content.ContentServiceEvent;
import wt.epm.workspaces.EPMWorkspaceManagerEvent;
import wt.fc.PersistenceManagerEvent;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressServiceEvent;


public class YJPLMListenerService extends StandardManager {
	private static final long serialVersionUID = -7952890530031805610L;
	
	public static YJPLMListenerService newYJPLMListenerService()
			throws WTException {
		YJPLMListenerService instance = new YJPLMListenerService();
		instance.initialize();
		return instance;
	}
	protected synchronized void performStartupProcess() throws ManagerException {
		super.performStartupProcess();

		YJPLMEventListener listener = new YJPLMEventListener(this.getConceptualClassname());

		getManagerService().addEventListener(
				listener,
				WorkInProgressServiceEvent
				.generateEventKey(WorkInProgressServiceEvent.POST_CHECKIN));
	
		getManagerService().addEventListener(
				listener,
				PersistenceManagerEvent
						.generateEventKey(PersistenceManagerEvent.POST_STORE));
		
		
		
		getManagerService().addEventListener(
				listener,
				EPMWorkspaceManagerEvent
						.generateEventKey("POST_WORKSPACE_CHECKIN"));
		
//		getManagerService().addEventListener(
//				listener,
//				ContentServiceEvent
//						.generateEventKey(ContentServiceEvent.POST_UPLOAD));
		
	}

}
