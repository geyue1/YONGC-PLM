

<%@page import="wt.maturity.PromotionNotice"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.change2.WTChangeOrder2"%>
<%@page import="wt.query.QuerySpec"%>
<%
  String name = request.getParameter("name");
  System.out.println("getCount.jsp----------name="+name);
  QuerySpec qs = new QuerySpec(PromotionNotice.class);
  QueryResult qr = PersistenceHelper.manager.find(qs);
  int count = 1;
  while(qr.hasMoreElements()){
	  PromotionNotice pn = (PromotionNotice)qr.nextElement();
	  String ecnName = pn.getName();
	  String[] arrayName=pn.getName().split("-");
	  if(arrayName.length==3 && arrayName[1].trim().equalsIgnoreCase(name)){
		  count++;
	  }
  }
  
  if(count<10){
	  response.getWriter().print("00"+count);
  }else if(count>=10 && count<100){
	  response.getWriter().print("0"+count);
  }else if(count>=100){
	  response.getWriter().print(count);
  }
%>