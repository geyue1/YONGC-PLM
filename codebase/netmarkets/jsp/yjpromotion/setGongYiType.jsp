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
   String oids = request.getParameter("oid");
   String type = request.getParameter("type");
   System.out.println("setGongYiType.jsp-----oids---------->"+oids);
	System.out.println("setGongYiType.jsp----type---------->"+type);
	String[] arrayOid = oids.split(",");
	ReferenceFactory reference = new ReferenceFactory();
	Persistable p = null;
	IBAUtil iba= new IBAUtil();
	for(String oid:arrayOid){
		if(!StringUtil.isEmpty(oid)){
			p = reference.getReference(oid).getObject();
			if(p instanceof EPMDocument){
				EPMDocument epm = (EPMDocument)p;
				if(StringUtil.isEmpty(type)){
					iba.setIBAAnyValue(epm, IBAConstants.GONGYI_TYPE, "");
					continue;
				}
				String value = iba.getIBAValue(epm, IBAConstants.GONGYI_TYPE);
				String s = type;
				if(StringUtil.isNotEmpty(value) && !value.contains(type)){
					s = value+","+type;
				}
				iba.setIBAAnyValue(epm, IBAConstants.GONGYI_TYPE, s);
				System.out.println("setGongYiType.jsp");
			}
		}
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