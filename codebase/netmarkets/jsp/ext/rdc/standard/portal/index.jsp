<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<html>
	<head>
		<title>FAW标准件管理</title>
	</head>
	<%
		long portalHtmlTime = new Date().getTime();
	%>
	<body>
		<iframe id="FAW-Standard-MGR-Iframe" src="netmarkets/jsp/ext/rdc/standard/portal/dist/index.html?t=<%=portalHtmlTime%>>"  width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" ></iframe>
		<script type="text/javascript">
			var fawIframe = document.getElementById("FAW-Standard-MGR-Iframe");
			fawIframe.height= window.innerHeight-100;
			fawIframe.width= window.innerWidth-70;
		</script>
	</body>
</html>