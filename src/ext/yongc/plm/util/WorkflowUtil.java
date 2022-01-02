/**
 * ext.yongc.plm.util.WorkflowUtil.java
 * @Author yge
 * 2017年7月4日下午9:01:58
 * Comment 
 */
package ext.yongc.plm.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import wt.change2.WTChangeOrder2;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.maturity.PromotionNotice;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;
import wt.util.WTInvalidParameterException;
import wt.util.WTProperties;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfProcessDefinition;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfActivity;
import wt.workflow.engine.WfBlock;
import wt.workflow.engine.WfConnector;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfState;
import wt.workflow.engine.WfTransition;
import wt.workflow.engine.WfVariable;
import wt.workflow.engine.WfVotingEventAudit;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignment;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;

import com.ptc.core.components.rendering.guicomponents.DateDisplayComponent;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifierHelper;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.netmarkets.workflow.NmWorkflowHelper;
import com.ptc.windchill.enterprise.workflow.WorkflowDataUtility;

public class WorkflowUtil implements RemoteAccess {
    public final static String CLASSNAME = WorkflowUtil.class.getName();
    public final static Logger LOGGER = Logger.getLogger(CLASSNAME);

    /**
     * get process by pbo
     * 
     * @param p
     *            Persistable
     * @return WfProcess
     * @throws Exception
     */
    public static WfProcess getProcessByPBO(Persistable p) throws WTException {
        WfProcess process = null;
        QueryResult qr = NmWorkflowHelper.service.getAssociatedProcesses(p, null, null);
        if (qr.hasMoreElements()) {
            process = (WfProcess) qr.nextElement();
            LOGGER.debug("Chris===> process of object[" + p + "] : " + process);
        }
        return process;
    }

    /**
     * Get Process
     * 
     * @param obj
     * @return WfProcess
     */
    public static WfProcess getProcessByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            Persistable persistable = null;
            if (obj instanceof Persistable) {
                persistable = (Persistable) obj;
            } else if (obj instanceof ObjectIdentifier) {
                persistable = PersistenceHelper.manager
                        .refresh((ObjectIdentifier) obj);
            } else if (obj instanceof ObjectReference) {
                persistable = ((ObjectReference) obj).getObject();
            }
            if (persistable == null) {
                return null;
            }
            if (persistable instanceof WorkItem) {
                persistable = ((WorkItem) persistable).getSource().getObject();
            }
            if (persistable instanceof WfActivity) {
                persistable = ((WfActivity) persistable).getParentProcess();
            }
            if (persistable instanceof WfConnector) {
                persistable = ((WfConnector) persistable).getParentProcessRef()
                        .getObject();
            }
            if (persistable instanceof WfBlock) {
                persistable = ((WfBlock) persistable).getParentProcess();
            }
            if (persistable instanceof WfProcess) {
                return (WfProcess) persistable;
            }
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * 
     * @param process
     * @param vname
     * @return
     */
    public static Object getGlobalVariable(WfProcess process, String vname) {
        if (process == null) {
            return null;
        }
        ProcessData processdata = process.getContext();
        WfVariable wfvariable = processdata.getVariable(vname);
        if (wfvariable != null) {
            LOGGER.debug("Value of variable[" + vname + "] : "
                    + wfvariable.getValue());
            return wfvariable.getValue();
        } else {
            return null;
        }
    }

    /**
     * Get the Activity variant value
     * 
     * @param activity
     * @param vname
     * @return
     */
    public static Object getLocaleVariable(WfActivity activity, String vname) {
        if (activity == null) {
            return null;
        }
        ProcessData processdata = activity.getContext();
        WfVariable wfvariable = processdata.getVariable(vname);
        if (wfvariable != null) {
            LOGGER.debug("Chris===> value of variable[" + vname + "] : "
                    + wfvariable.getValue());
            return wfvariable.getValue();
        } else {
            return null;
        }
    }

    /**
     * 
     * @param self
     * @param vname
     * @param value
     */
    public synchronized static void setGlobalVariable(ObjectReference self,
            String vname, Object value) {
        if (self == null) {
            return;
        }
        WfProcess process = getProcessByObject(self);
        setGlobalVariable(process, vname, value);
    }

    /**
     * 
     * @param process
     * @param vname
     * @param value
     */
    public synchronized static void setGlobalVariable(WfProcess process, String vname, Object value) {
        if (process == null) {
            return;
        }
        try {
            ProcessData processdata = process.getContext();
            WfVariable wfvariable = processdata.getVariable(vname);
            if (wfvariable != null) {
                wfvariable.setValue(value);
                PersistenceHelper.manager.save(process);
            }
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Complete workflow activity
     * 
     * @param process
     * @param activity
     * @param flag
     * @return "Y" or null, "Y" is activity executed or change "OPEN_RUNNING" to COMPLETE
     * @throws WTException
     */
    public static String completeWfActivity(WfProcess process, String activity) throws WTException {
        if (process == null || activity == null
                || activity.trim().length() == 0) {
            throw new WTException("process or  activity is null");
        }
        try {
            WfAssignedActivity wfactivity = getWfAssignedActivity(process, activity);
            LOGGER.debug("completeWfActivity-0: " + wfactivity);
            if (wfactivity != null) {
                LOGGER.debug("completeWfActivity-1: " + wfactivity);
                if (WfState.CLOSED_COMPLETED_EXECUTED.equals(wfactivity.getState())) {
                    return "Y";
                }
                if (WfState.OPEN_RUNNING.equals(wfactivity.getState())) {
                    WfEngineHelper.service.changeState(wfactivity,
                            WfTransition.COMPLETE);
                    return "Y";
                }
            }
        } catch (WTInvalidParameterException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * 
     * @param wfprocess
     * @param activity
     * @return
     */
    public static WfAssignedActivity getWfAssignedActivity(WfProcess wfprocess,
            String activity) {
        if (wfprocess == null || activity == null
                || activity.trim().length() == 0) {
            LOGGER.debug("---getWfAssignedActivity---->>>>return null->");
            return null;
        }
        try {
            QueryResult qr = wfprocess.getContainerNodes();
            while (qr.hasMoreElements()) {
                Object obj = qr.nextElement();
                if (obj instanceof WfAssignedActivity) {
                    WfAssignedActivity wfaa = (WfAssignedActivity) obj;
                    if (activity.equals(wfaa.getName().trim())) {
                        return wfaa;
                    }
                }
            }
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * 
     * @param wfaa
     * @return
     */
    public static WfAssignment getLatestWfAssignment(WfAssignedActivity wfaa) {
        if (wfaa == null) {
            LOGGER.debug("---getLatestWfAssignment---->>>>return null->");
            return null;
        }
        WfAssignment wfassignment = null;
        try {
            QuerySpec queryspec = new QuerySpec(WfAssignment.class);
            queryspec.appendWhere(
                    new SearchCondition(WfAssignment.class, "source.key", "=",
                            PersistenceHelper.getObjectIdentifier(wfaa)),
                    new int[] { 0 });
            queryspec.appendAnd();
            queryspec.appendWhere(new SearchCondition(WfAssignment.class, "status",
                    "=", WfAssignmentState.COMPLETED),
                    new int[] { 0 });
            QueryResult qr = PersistenceHelper.manager
                    .find((StatementSpec) queryspec);
            while (qr.hasMoreElements()) {
                WfAssignment item = (WfAssignment) qr.nextElement();
                if (wfassignment == null) {
                    wfassignment = item;
                } else if (wfassignment.getPersistInfo().getObjectIdentifier()
                        .getId() < item.getPersistInfo().getObjectIdentifier()
                        .getId()) {
                    wfassignment = item;
                }
            }
        } catch (WTInvalidParameterException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (QueryException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return wfassignment;
    }

    /**
     * Get latest workitem
     * 
     * @param wfaa
     * @return Workitem
     */
    public static WorkItem getLatestWorkItem(WfAssignedActivity wfaa) {
        if (wfaa == null) {
            LOGGER.debug("---getLatestWorkItem---->>>>return null->");
            return null;
        }
        WorkItem workitem = null;
        try {
            QuerySpec queryspec = new QuerySpec(WorkItem.class); // COMPLETED
            queryspec.appendWhere(new SearchCondition(WorkItem.class, "source.key",
                    "=", PersistenceHelper.getObjectIdentifier(wfaa)),
                    new int[] { 0 });
            queryspec.appendAnd();
            queryspec.appendWhere(new SearchCondition(WorkItem.class, "status",
                    "=", WfAssignmentState.COMPLETED),
                    new int[] { 0 });
            QueryResult qr = PersistenceHelper.manager
                    .find((StatementSpec) queryspec);

            while (qr.hasMoreElements()) {
                WorkItem item = (WorkItem) qr.nextElement();
                if (workitem == null) {
                    workitem = item;
                } else if (workitem.getPersistInfo().getObjectIdentifier().getId() < item
                        .getPersistInfo().getObjectIdentifier().getId()) {
                    workitem = item;
                }
            }
        } catch (WTInvalidParameterException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (QueryException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return workitem;
    }

    /**
     * Get latest workitem
     * 
     * @param wfaa
     * @return Workitem
     */
    public static WorkItem getWorkItemByWTUser(WfAssignedActivity wfaa,WTUser user) {
        if (wfaa == null) {
            LOGGER.debug("---getLatestWorkItem---->>>>return null->");
            return null;
        }
        WorkItem workitem = null;
        try {
            QuerySpec queryspec = new QuerySpec(WorkItem.class); // COMPLETED
            queryspec.appendWhere(new SearchCondition(WorkItem.class, "source.key",
                    "=", PersistenceHelper.getObjectIdentifier(wfaa)),
                    new int[] { 0 });
            queryspec.appendAnd();
            queryspec.appendWhere(new SearchCondition(WorkItem.class, "ownership.owner.key.id",
                    "=", PersistenceHelper.getObjectIdentifier(user).getId()),
                    new int[] { 0 });
            QueryResult qr = PersistenceHelper.manager
                    .find((StatementSpec) queryspec);

            while (qr.hasMoreElements()) {
                WorkItem item = (WorkItem) qr.nextElement();
                return item;
            }
        } catch (WTInvalidParameterException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (QueryException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    public static List<WorkItem> getAllNotCompleteWorkItem(WfProcess process){
    	 List<WorkItem> workItemList = new ArrayList<WorkItem>();
    	 if(process==null){
    		  LOGGER.error("---getLatestWorkItem---->>>>return null->process is null");
              return workItemList;
    	 }
    	 try {
             QueryResult qr = process.getContainerNodes();
             while (qr.hasMoreElements()) {
                 Object obj = qr.nextElement();
                 if (obj instanceof WfAssignedActivity) {
                     WfAssignedActivity wfaa = (WfAssignedActivity) obj;
                     workItemList.addAll(getAllNotCompleteWorkItem(wfaa));
                 }
             }
             
             
         } catch (WTException e) {
             LOGGER.error(e.getLocalizedMessage(), e);
         }
    	 
    	 return workItemList;
    }
    /**
     * Get all not complete workitem
     * 
     * @param wfaa
     * @return List
     */
    public static List<WorkItem> getAllNotCompleteWorkItem(WfAssignedActivity wfaa) {
        List<WorkItem> workItemList = new ArrayList<WorkItem>();
        if (wfaa == null) {
            LOGGER.error("---getLatestWorkItem---->>>>return null->");
            return workItemList;
        }
        try {
            QuerySpec queryspec = new QuerySpec(WorkItem.class); // COMPLETED
            queryspec.appendWhere(new SearchCondition(WorkItem.class, "source.key",
                    "=", PersistenceHelper.getObjectIdentifier(wfaa)),
                    new int[] { 0 });
            QueryResult qr = PersistenceHelper.manager
                    .find((StatementSpec) queryspec);

            while (qr.hasMoreElements()) {
                WorkItem item = (WorkItem) qr.nextElement();
                if (!item.isComplete()) {
                    workItemList.add(item);
                }
            }
        } catch (WTInvalidParameterException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (QueryException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return workItemList;
    }

    /**
     * Get voting event audit form workflow
     * 
     * @param wfprocess
     * @param activity
     * @return
     */
    public static WfVotingEventAudit getWfVotingEventAudit(WfProcess wfprocess,
            WfAssignedActivity activity) {
        if (wfprocess == null || activity == null) {
            LOGGER.debug("---getWfVotingEventAudit---->>>>return null->");
            return null;
        }
        WfVotingEventAudit wfvea = null;
        try {
            QuerySpec queryspec = new QuerySpec(WfVotingEventAudit.class);
            queryspec.appendWhere(new SearchCondition(WfVotingEventAudit.class,
                    "processKey", "=", wfprocess.getKey()), new int[] { 0 });
            queryspec.appendAnd();
            queryspec.appendWhere(new SearchCondition(WfVotingEventAudit.class,
                    "activityKey", "=", activity.getKey()), new int[] { 0 });
            QueryResult qr = PersistenceHelper.manager
                    .find((StatementSpec) queryspec);

            while (qr.hasMoreElements()) {
                WfVotingEventAudit item = (WfVotingEventAudit) qr.nextElement();
                if (wfvea == null) {
                    wfvea = item;
                } else if (wfvea.getPersistInfo().getObjectIdentifier().getId() < item
                        .getPersistInfo().getObjectIdentifier().getId()) {
                    wfvea = item;
                }
            }
        } catch (QueryException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (WTException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return wfvea;
    }

    /**
     * start workflow
     * 
     * @param workFlowName
     *            workflow name
     */
    public static WfProcess startWorkflow(String workFlowName) {
        WfProcess wfprocess = null;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                RemoteMethodServer.getDefault().invoke("startWorkflow",
                        CLASSNAME, null, new Class[] { String.class },
                        new Object[] { workFlowName });
            } else {
                boolean enforce = SessionServerHelper.manager
                        .setAccessEnforced(false);
                try {
                    WTContainerRef containerRef = null;
                    try {
                        containerRef = WTContainerHelper.service
                                .getExchangeRef();
                    } catch (WTException e1) {
                        LOGGER.error(e1.getLocalizedMessage(), e1);
                    }

                    long workflowPriority = 1;
                    try {
                        WTProperties wtproperties = WTProperties
                                .getLocalProperties();
                        workflowPriority = Long
                                .parseLong(wtproperties
                                        .getProperty(
                                                "wt.lifecycle.defaultWfProcessPriority",
                                                "1"));

                        WfProcessDefinition wfprocessdefinition = WfDefinerHelper.service
                                .getProcessDefinition(workFlowName,
                                        containerRef);
                        if (wfprocessdefinition == null) {
                            return wfprocess;
                        }

                        wfprocess = WfEngineHelper.service.createProcess(
                                wfprocessdefinition, null, containerRef);

                        ProcessData processdata = wfprocess.getContext();
                        WfEngineHelper.service.startProcessImmediate(wfprocess,
                                processdata, workflowPriority);
                    } catch (Exception e) {
                        LOGGER.error(e.getLocalizedMessage(), e);
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                } finally {
                    SessionServerHelper.manager.setAccessEnforced(enforce);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return wfprocess;
    }

    /**
     * get process by pbo
     * 
     * @param p
     *            Persistable
     * @return WfProcess
     * @throws Exception
     */
    public static WfProcess getProcessByPBO(Persistable p,WfState wfState) throws WTException {
        //WfState.OPEN_RUNNING
        WfProcess process = null;
        QueryResult qr = WfEngineHelper.service.getAssociatedProcesses(p,wfState,
                null);      
        if (qr.hasMoreElements()) {
            process = (WfProcess) qr.nextElement();
            LOGGER.debug("Chris===> process of object[" + p + "] : " + process);
        }
        return process;
    }
    /**
	 * This method is for getting all the routing history of the specified
	 * process, including Process Name, Process Creator, Process Date, Activity
	 * Name, Activity Assignee, Activity Role, Activity Vote Result, Activity
	 * Comments, Activity Deadline and Activity Compelete Date.
	 * 
	 * @param proc
	 * @return An arraylist contains a structured hashmap. The hasmap's keys are
	 *         CSCProcess.PROCESS_NAME: Process Name CSCProcess.PROCESS_CREATOR:
	 *         Process Creator CSCProcess.PROCESS_DATE: Process Date
	 *         CSCProcess.WORK_NAME: Activity Name CSCProcess.WORK_ASSIGNEE:
	 *         Activity Assignee CSCProcess.WORK_ROLE: Activity Role
	 *         CSCProcess.WORK_VOTE: Activity Vote CSCProcess.WORK_COMMENTS:
	 *         Activity Comments CSCProcess.WORK_DEADLINE: Activity Deadline
	 *         CSCProcess.WORK_COMPLETEDDATE: Activity Compelete Date
	 * @throws WTException
	 */

	public static ArrayList getProcessRoutingHistory(WfProcess proc)
			throws WTException {
		WorkflowDataUtility wdu = new WorkflowDataUtility();

		ArrayList aProcess = new ArrayList();
		QueryResult qsVoteEvent = NmWorkflowHelper.service
				.getVotingEventsForProcess(proc); // Get all completed
		// VotingEvents

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		while (qsVoteEvent.hasMoreElements()) {
			WfVotingEventAudit voteEvent = (WfVotingEventAudit) qsVoteEvent
					.nextElement();
			// --- Process Information ---
			String strProcessName = ((TextDisplayComponent) wdu.getDataValue(
					"procName", proc, null)).getValue();
			String strProcessCreator = proc.getCreator().getDisplayName();
			String strProcessInitialDate = proc.getCreateTimestamp()
					.toLocaleString();

			// --- WorkItem Information ---
			String strWorkName = (String) wdu.getDataValue("workName",
					voteEvent, null);
			String strWorkAssignee = ((TextDisplayComponent) wdu.getDataValue(
					"workAssignee", voteEvent, null)).getValue();
			String workCompletedBy = ((TextDisplayComponent) wdu.getDataValue(
					"workCompletedBy", voteEvent, null)).getValue();
			
			LOGGER.debug("strWorkName----"+strWorkName);
			
			
			String strWorkRole = ((TextDisplayComponent) wdu.getDataValue(
					"workRole", voteEvent, null)).getValue();
			String strWorkVote = ((TextDisplayComponent) wdu.getDataValue(
					"workVote", voteEvent, null)).getValue();
			if (strWorkVote.equals("&nbsp;"))
				strWorkVote = "";

			DateDisplayComponent ddcDeadline = (DateDisplayComponent) wdu
					.getDataValue("workDeadline", voteEvent, null);
			String strDeadline = "";
			if (ddcDeadline == null) {
				strDeadline = "";
			} else {

				strDeadline = sdf.format(ddcDeadline.getValue());
			}

			DateDisplayComponent ddcCompletedDate = (DateDisplayComponent) wdu
					.getDataValue("workCompletedDate", voteEvent, null);

			String strCompletedDate = "";
			if (ddcCompletedDate == null) {
				strCompletedDate = "";
			} else {
				strCompletedDate = sdf.format(ddcCompletedDate.getValue());
			}

			// --- VotingEvent Information --
			String strWorkComments = ((TextDisplayComponent) wdu.getDataValue(
					"workComments", voteEvent, null)).getFormattedValue();

			// --- Envelop the Data into a HashMap ----
			WorkItemInfoBean wii = new WorkItemInfoBean();

			wii.setProcName(strProcessName);
			wii.setProcCreator(strProcessCreator);
			wii.setProcDate(strProcessInitialDate);
			wii.setWorkName(strWorkName);
			wii.setWorkAssignee(strWorkAssignee);
			wii.setWorkCompletedBy(workCompletedBy);
			wii.setWorkRole(strWorkRole);
			wii.setWorkVote(strWorkVote);
			wii.setWorkComments(strWorkComments);
			wii.setWorkDeadline(strDeadline);
			wii.setWorkCompletedDate(strCompletedDate);
			wii.setCompletedDate(ddcCompletedDate.getValue());
			wii.setTripCount(voteEvent.getTripCount());

			if (aProcess.contains(wii)) {
				WorkItemInfoBean wii_old = (WorkItemInfoBean) aProcess
						.get(aProcess.indexOf(wii));
				// 如果为同一人，且tripCount不同，表明是第n次走此节点，取最后一次签审信息

				if ((wii_old.getTripCount() != wii.getTripCount())
						&& wii_old.getWorkRole().equals(wii.getWorkRole())) {
					if (wii.getCompletedDate()
							.after(wii_old.getCompletedDate()))
						aProcess.set(aProcess.indexOf(wii), wii);
				} else {
					// 相同角色合并人员信息
					// wii.setWorkAssignee(wii_old.getWorkAssignee() + ","
					// + wii.getWorkAssignee());
					// aProcess.set(aProcess.indexOf(wii), wii);

					aProcess.add(wii);
				}

			} else {
				aProcess.add(wii);
			}

		}
		return aProcess;
	}	
	public static void startWorkflow(WTObject pbo, WfProcessTemplate paramWfProcessTemplate) throws Exception{
		boolean enforce = SessionServerHelper.manager
                .setAccessEnforced(false);
		 try
		    {
			 WTContainerRef  containerRef = WTContainerHelper.service .getExchangeRef();
			   String processName = "";
			   String type = null;
			   String displayName = null;
			   WTPrincipalReference wtpr = null;
			   if(pbo instanceof WTDocument){
				   WTDocument doc = (WTDocument)pbo;
				   processName = doc.getName();
//				   containerRef = doc.getContainerReference();
				   wtpr = doc.getCreator();
				   type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
			   }else if(pbo instanceof WTPart){
				   WTPart part  = (WTPart)pbo;
				   processName = part.getName();
				   containerRef = part.getContainerReference();
				   wtpr = part.getCreator();
				   type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(part).getTypename();
			   }else if(pbo instanceof EPMDocument){
				   EPMDocument epm = (EPMDocument)pbo;
				   processName = epm.getName();
				   containerRef = epm.getContainerReference();
				   wtpr = epm.getCreator();
				   type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(epm).getTypename();
			   }else if(pbo instanceof WTChangeOrder2){
				   WTChangeOrder2 ecn = (WTChangeOrder2)pbo;
				   processName = ecn.getName();
				   containerRef = ecn.getContainerReference();
				   wtpr = ecn.getCreator();
				   type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
				
			   }else if(pbo instanceof PromotionNotice){
				   PromotionNotice pn = (PromotionNotice)pbo;
				   processName = pn.getName();
				   containerRef = pn.getContainerReference();
				   wtpr = pn.getCreator();
				   type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(pn).getTypename();
			   }
			   if(StringUtil.isNotEmpty(type)){
				   type = type.substring(type.lastIndexOf("|")+1);
				   LOGGER.debug("type-------->"+type);
				   TypeIdentifier identifier = TypeIdentifierHelper.getTypeIdentifier(type);
				    displayName = TypedUtilityServiceHelper.service.getLocalizedTypeName(identifier, Locale.CHINA);
			   }
			   if(StringUtil.isNotEmpty(displayName)){
//				   processName = displayName+"-"+processName;
			   }
			   LOGGER.debug("processName -------------->"+processName);
			   processName = paramWfProcessTemplate.getName()+"-"+processName;
			   LOGGER.debug("wtpr -------------->"+wtpr);
			   LOGGER.debug("containerRef -------------->"+containerRef);
			   WfProcess process = WfProcess.newWfProcess();
		       process = WfEngineHelper.service.createProcess(paramWfProcessTemplate, null, containerRef);
		      if(wtpr!=null){
		    	  ObjectIdentifiedUtil.setCreator(process,   wtpr);
			        PersistenceServerHelper.manager.update(process);
		      } 
		    
		      //process.setName(new StringBuilder().append(processName).append("-").append(System.currentTimeMillis()).toString());
		      process.setName(processName);
//		      process.setTeamTemplateId(paramPromotionNotice.getTeamTemplateId());
		      ProcessData processData = process.getContext();
		      processData.setValue("primaryBusinessObject", pbo);
		      WfProcess process2 = WfEngineServerHelper.service.setPrimaryBusinessObject(process, pbo);

		    
		        LOGGER.debug(new StringBuilder().append("<<<< Process created: ").append(process2.getName()).toString());
		      
		        
		        if(wtpr!=null){
			    	  ObjectIdentifiedUtil.setCreator(process2,   wtpr);
				        PersistenceServerHelper.manager.update(process2);
			      } 
		      WfEngineHelper.service.startProcess(process2, processData, 1);
		    
		      LOGGER.debug("<<<< Process started.");
		   
		    }catch (WTException exception) {
		      throw new WTException();
		    }finally{
		    	SessionServerHelper.manager
                .setAccessEnforced(enforce);
		    }

		  }
	
}
