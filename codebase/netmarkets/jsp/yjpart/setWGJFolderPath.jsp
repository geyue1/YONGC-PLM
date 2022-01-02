
<%@page import="wt.workflow.work.WorkItem"%>
<%@page import="wt.workflow.engine.WfProcess"%>
<%@page import="ext.yongc.plm.util.WorkflowUtil"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%
String folderPath = request.getParameter("folderPath");
String workitemOid = request.getParameter("workitemOid");
System.out.println("------ setWGJFolderPath------folderPath="+folderPath);
System.out.println("------ workitemOid------workitemOid="+workitemOid);
ReferenceFactory rf = new ReferenceFactory();
WorkItem item = (WorkItem)rf.getReference(workitemOid).getObject();
WfProcess process = WorkflowUtil.getProcessByObject(item);
WorkflowUtil.setGlobalVariable(process,"folderPath",folderPath);

%>