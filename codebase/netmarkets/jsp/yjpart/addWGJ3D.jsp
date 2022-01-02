<%@page import="wt.part.WTPart"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.epm.build.EPMBuildRule"%>
<%@page import="ext.yongc.plm.constants.IBAConstants"%>
<%@page import="ext.yongc.plm.util.IBAUtil"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="wt.epm.util.EPMDebug"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="ext.yongc.plm.util.StringUtil"%>
<%@page import="wt.session.SessionServerHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="wt.workflow.work.WfAssignedActivity"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="org.json.JSONObject"%>

<%@page import="java.io.BufferedReader"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
   String oid = request.getParameter("oid");
   System.out.println("addWGJ3D.jsp-----oid---------->"+oid);
	ReferenceFactory reference = new ReferenceFactory();
	WTPart part = (WTPart)reference.getReference(oid).getObject();
	 QueryResult qr=PersistenceHelper.manager.navigate(part, EPMBuildRule.BUILD_SOURCE_ROLE, EPMBuildRule.class);
	 while(qr.hasMoreElements()){
		 EPMDocument epm = (EPMDocument)qr.nextElement();
		 String epmoid = reference.getReferenceString(epm);
		 System.out.println("addWGJ3D.jsp-----epm oid---------->"+epmoid);
		 response.getWriter().print(epmoid);
	 }

  /*
		BufferedReader in=request.getReader();
		StringBuffer jsonStr=new StringBuffer("");
		String str="";
		while((str=in.readLine())!=null){
		    jsonStr.append(str);
		}
		JSONObject jsonObj=new JSONObject(jsonStr.toString());
		String records=jsonObj.getString("oid");
		String type=jsonObj.getString("type");
		System.out.println("records---------->"+records);
		System.out.println("type---------->"+type);
		*/
%>