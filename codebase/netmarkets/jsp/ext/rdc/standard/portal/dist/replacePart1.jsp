<%@page import="wt.httpgw.URLFactory"%>
<%
String redirectPageUrl = new URLFactory().getBaseHREF();
System.out.println("redirectPageUrl>>>"+redirectPageUrl);
response.sendRedirect(redirectPageUrl+"app/#ptc1/ext/rdc/standard/report/replacePart");
%>