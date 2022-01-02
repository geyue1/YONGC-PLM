<%@page import="ext.yongc.plm.util.StringUtil"%>
<%@page import="wt.part.WTPart"%>
<%@page import="ext.yongc.plm.constants.IBAConstants"%>
<%@page import="ext.yongc.plm.util.IBAUtil"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="wt.httpgw.URLFactory"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w"%>

<style type="text/css">
<!--
table {
	font-size: 14px;
}

font {
	font-weight: bolder;
}
.STYLE3 {
	color: #000000;
	font-size: 20px;
	font-weight: bold;
}
--> 
</style>

<%
URLFactory urlFactory = new URLFactory();
String strBaseURL = urlFactory.getBaseHREF();
String oid = request.getParameter("oid");
System.out.println("oid---------->"+oid);
String objNumber = "";
boolean flag = false;
if(oid!=null && oid.contains("wt.part.WTPart")){
	WTPart part =(WTPart) new ReferenceFactory().getReference(oid).getObject();
	IBAUtil iba = new IBAUtil(part);
	objNumber = iba.getIBAValue(IBAConstants.DRAWINGNO);
	flag = true;
}
%>

<table>
	<tr>
		<td class="STYLE3" nowrap="nowrap">产品BOM信息汇总</td>
	</tr>
</table>

<form name="dataForm" method="post" enctype="multipart/mixed">
<table border="0">
	<tr>
		<td nowrap="nowrap" colspan="3"><br/></td>
	</tr>
	<%if(flag){
		%>
		<tr>
		<input type="hidden" id="oid" name="oid" value="<%=oid%>"/>
	</tr>
		<%
	} %>
	<tr>
		<input type="hidden" id="reportid" name="reportid" value="BOMReport"/>
	</tr>
	<tr>
		<td nowrap="nowrap">
			<label for="numberFrom_input">
			<font>图纸代号:</font>
			<br/>
			</label>
		</td>
		<td>
			<input type="text" name="objNumberInput" id="objNumberInput" value="<%=objNumber%>"></textarea>
		</td>
	</tr>
	<tr><td nowrap="nowrap" colspan="3"><br/></td></tr>
	
	<tr><td nowrap="nowrap" colspan="3"><br/></td></tr>
	<tr>	
		<td></td>
		<td><w:button name="Generate Report" value="生成报表" onclick="downloadReport();" /></td>
		<td></td>
	</tr>
</table>

</form>

<iframe id="exportFrame" name="exportFrame" height="0" width="0" ></iframe>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>

<script language='JavaScript'>
	function downloadReport(){
		var objNumberInput = document.getElementById('objNumberInput');
		var objNumberInputValue = objNumberInput.value;
		
		if(objNumberInputValue==null || objNumberInputValue == ''){
			alert("请输入图纸代号");
			return false;
		}

		
		var url = getBaseHref() + "netmarkets/jsp/ext/yongc/plm/report/downloadBOMReport.jsp";
		
		document.forms[0].action = url;
		document.forms[0].target = exportFrame;
        document.forms[0].method = "post";
		document.forms[0].submit();
	}
	
</script>