
<%@page import="wt.session.SessionServerHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="ext.yongc.plm.part.link.PartMaterialLink"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.QuerySpec"%>
<%
boolean force = SessionServerHelper.manager.setAccessEnforced(false);
String number = request.getParameter("number");
String oid = request.getParameter("oid");
System.out.println("===materialPartNumberCheck.jsp=== number="+number);
System.out.println("===materialPartNumberCheck.jsp=== oid="+oid);
QuerySpec qs = new QuerySpec(PartMaterialLink.class);
SearchCondition sc = new SearchCondition(PartMaterialLink.class,PartMaterialLink.ROLE_BNAME,
		"=",number.trim());
qs.appendWhere(sc);
qs.appendAnd();
SearchCondition sc1 = new SearchCondition(PartMaterialLink.class,PartMaterialLink.ROLE_AIDA2A2,
		"=",oid.trim());
qs.appendWhere(sc1);
System.out.println(qs);
QueryResult qr = PersistenceHelper.manager.find(qs);
SessionServerHelper.manager.setAccessEnforced(force);
System.out.println("===materialPartNumberCheck.jsp=== qr.size()="+qr.size());
if(qr!=null && qr.size()>0){
	response.getWriter().print("fail");
}


%>