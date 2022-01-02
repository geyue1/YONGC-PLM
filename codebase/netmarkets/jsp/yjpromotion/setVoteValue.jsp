<%@page import="wt.part.WTPart"%>
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
   String vote = request.getParameter("vote");
   System.out.println("setVoteValue.jsp-----oids---------->"+oids);
	System.out.println("setVoteValue.jsp----vote---------->"+vote);
	
	//for MBOM
	if(StringUtil.isEmpty(vote)){
		vote = "完成";
	}
	String[] arrayOid = oids.split(",");
	ReferenceFactory reference = new ReferenceFactory();
	Persistable p = null;
	IBAUtil iba= new IBAUtil();
	for(String oid:arrayOid){
		if(!StringUtil.isEmpty(oid)){
			p = reference.getReference(oid).getObject();
			if(p instanceof EPMDocument){
				EPMDocument epm = (EPMDocument)p;
				String value = iba.getIBAValue(epm, IBAConstants.VOTE);
				System.out.println("value------------------------>"+value);
				String s = vote;
				if(StringUtil.isNotEmpty(value) && !value.contains(vote)){
					s = value+","+vote;
				}
				System.out.println("s------------------------>"+s);
					iba.setIBAAnyValue(epm, IBAConstants.VOTE, s);
					System.out.println("---------- end EPM setVoteValue.jsp-------------->");
			}else if(p instanceof WTPart){
				WTPart part = (WTPart)p;
				iba.setIBAAnyValue(part, IBAConstants.VOTE, vote);
				System.out.println("---------- end part setVoteValue.jsp-------------->");
			}
		}
	}
  
%>