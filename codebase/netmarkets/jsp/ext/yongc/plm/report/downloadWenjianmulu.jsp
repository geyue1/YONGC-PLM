<%@page import="ext.yongc.plm.report.ReportUtil"%>
<%@page import="ext.yongc.plm.util.StringUtil"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@ include file="/netmarkets/jsp/util/beginPopup.jspf"%>

<%@ page import="java.util.*"%>
<%@ page import="org.json.JSONArray"%>

<head>

<style type="text/css">
.head-class {
    color: #333333;
    font-family: tahoma,arial,verdana,sans-serif;
    font-size: 14px;
    font-weight: bold;
    line-height: 17px;
}
.x-panel-header-text {
    color: #333333;
    font-family: tahoma,arial,verdana,sans-serif;
    font-size: 11px;
    font-weight: bold;
    line-height: 17px;
}
.x-grid3-cell-inner { white-space:normal !important; }

.divCls {
	margin-top: 20px;
	margin-bottom: 20px;
}
p {
	margin-left: 8px;
	margin-bottom: 5px;
	font-weight: bold;
}
</style>

<title>Export to Excel</title>
</head>
<body>
<%
		Map formDataMap = ReportUtil.getFormData(request);
		
		List<String> validationResult = ReportUtil.validateFormData(formDataMap);
		
		if(validationResult!=null && validationResult.size()>0){
			
			String validationResultJSON  = ReportUtil.toJson4Msg(validationResult);
%>

<div id="veSec" class="divCls">
</div>

<script>

var validationResultStore = new Ext.data.JsonStore({
  fields: [ 'message' ],
  autoLoad: true,
  data:  <%=StringUtil.replace(validationResultJSON, "\\n", "<br/>")%>
});

var onReadyFunction = function(){
	
	var grid = new Ext.grid.GridPanel({
		renderTo: Ext.getDom("veSec"),
		frame: true,
		//header: false,
		viewConfig: {
			forceFit:true
		},
		title: 'Validation Error',
		width:1200,
		//height:500,
		autoHeight:true,
		store: validationResultStore,
		columns: [
		  {header: "Message", width: 1200, dataIndex:"message"}
		]
	});
}

Ext.onReady(function () {
	
	onReadyFunction();
	
	});
	
</script>
<p>
<input type=button onclick="window.close()" value=" Close ">  </input>
</p>
<%			
		}else{
			ReportUtil.downloadWenjianmulu(response, formDataMap);
			out.clear();
			out = pageContext.pushBody();
		}
					
%>


</body>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>