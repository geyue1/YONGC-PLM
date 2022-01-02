package ext.rdc.integration.service;


import org.apache.log4j.Logger;


import org.json.JSONException;


import org.json.JSONObject;

import wt.events.KeyedEvent;
import wt.fc.PersistenceManagerEvent;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.workflow.engine.WfAssignmentEventAudit;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfState;
import wt.workflow.engine.WfVotingEventAudit;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignment;
import wt.workflow.work.WfAssignmentState;

@SuppressWarnings("serial")
public class StandardSyncProcessService extends StandardManager implements SyncProcessService {
    private static final String CLASSNAME = StandardSyncProcessService.class.getName();
    private ServiceEventListenerAdapter listener;

    private static Logger LOGGER = Logger.getLogger(StandardSyncProcessService.class);

    public StandardSyncProcessService() {
    }

    public String getConceptualClassname() {
        return CLASSNAME;
    }

    public static StandardSyncProcessService newStandardSyncProcessService() throws WTException {
        StandardSyncProcessService instance = new StandardSyncProcessService();
        instance.initialize();
        return instance;
    }

    class SyncProcessServiceEventListener extends ServiceEventListenerAdapter {
        public SyncProcessServiceEventListener(String manager_name) {
            super(manager_name);
        }

        public void notifyVetoableEvent(Object obj) throws Exception {
            LOGGER.debug(">>>" + CLASSNAME + ".notifyVetoableEvent()");

            KeyedEvent keyedEvent = (KeyedEvent) obj;
            if (obj instanceof PersistenceManagerEvent) {
                PersistenceManagerEvent event = (PersistenceManagerEvent) obj;
                Object target = event.getEventTarget();
                String eventType = keyedEvent.getEventType();
                LOGGER.debug(">>>" + CLASSNAME + ".eventType:::::" + eventType);
                if (eventType.equals(PersistenceManagerEvent.POST_STORE)) {
                    if (target instanceof WfProcess) {
                        WfProcess process = (WfProcess) target;
                        LOGGER.debug("WfProcess create==============" + process.getDisplayIdentifier());
//                        JSONObject json = SyncProcessHelper.startProcess(process);
//                        String jsonStr = json.toJSONString();
//                        LOGGER.debug("json=>"+jsonStr);
//                        SyncProcessHelper.sendProcessMQ(jsonStr);
                        // 调用流程实例创建接口
                    } else if (target instanceof WfAssignmentEventAudit) {
                        WfAssignmentEventAudit audit = (WfAssignmentEventAudit) target;
                        LOGGER.debug("WfAssignmentEventAudit create==============" + audit.getDisplayIdentifier());
                        
                        // 调用创建待办接口
                    } else if (target instanceof WfVotingEventAudit) {
                        WfVotingEventAudit audit = (WfVotingEventAudit) target;
                        LOGGER.debug("WfVotingEventAudit create==============" + audit.getDisplayIdentifier());
                        
                        // 调用修改待办接口
                    }
                } else if (eventType.equals(PersistenceManagerEvent.POST_MODIFY)) {
                	LOGGER.debug(target.toString());
                	LOGGER.debug(target.getClass().getName());
                    if (target instanceof WfProcess) {
                        WfProcess process = (WfProcess) target;
                        LOGGER.debug("WfProcess modify==============" + process.getDisplayIdentifier());
                        
                        // 调用流程实例修改接口
                    } else if (target instanceof WfAssignedActivity) {
                        WfAssignedActivity activity = (WfAssignedActivity) target;
                        LOGGER.debug("WfAssignedActivity modify==============" + activity.getDisplayIdentifier());
                        String state = activity.getState().toString();
                        if(WfState.CLOSED_COMPLETED_EXECUTED.equals(state)) {
                            LOGGER.debug("Activity Completed.....");
                        }
                    } else if (target instanceof WfAssignment) {
                        WfAssignment assignment = (WfAssignment) target;
                        LOGGER.debug("WfAssignment modify==============" + assignment.getDisplayIdentifier());
                        String state = assignment.getStatus().toString();
                        if(WfAssignmentState.COMPLETED.toString().equals(state)) {
                            WfAssignedActivity activity = (WfAssignedActivity)assignment.getSource().getObject();
                            String eventStr = activity.getContext().getValue("WfUserEventList").toString();
                            LOGGER.debug("Activity Completed.....");
                        }
                    }
                } else if (eventType.equals(PersistenceManagerEvent.POST_DELETE)) {
                    if (target instanceof WfAssignmentEventAudit) {
                        WfProcess process = (WfProcess) target;
                        LOGGER.debug("WfProcess delete==============" + process.getDisplayIdentifier());
                    } else if (target instanceof WfAssignment) {
                        WfAssignment assignment = (WfAssignment) target;
                        LOGGER.debug("WfAssignment modify==============" + assignment.getDisplayIdentifier());
                        String state = assignment.getStatus().toString();
                        if(WfAssignmentState.COMPLETED.toString().equals(state)) {
                            LOGGER.debug("Activity Completed.....");
                        }
                    }
                }
            }
        }
    }

  
    /**
     * 启动监听服务
     */
    protected void performStartupProcess() throws ManagerException {
        System.out.println("Starting WorkItem service......");
        listener = new SyncProcessServiceEventListener(getConceptualClassname());

        // Add Listener 为监听服务注册
        getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_STORE));
        getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_MODIFY));
        getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_DELETE));
        getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.PREPARE_FOR_MODIFICATION));
    }

}
