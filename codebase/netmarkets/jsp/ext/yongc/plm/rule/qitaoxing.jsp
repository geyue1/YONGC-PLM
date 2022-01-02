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
		<td class="STYLE3" nowrap="nowrap">齐套性检查</td>
	</tr>
</table>


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
		<td><w:button name="checkQitaoxing_button" id="checkQitaoxing_button" value="齐套性检查" onclick="checkQitaoxing()" /></td>
		<td></td>
	</tr>
</table>


<iframe id="exportFrame" name="exportFrame" height="0" width="0" ></iframe>
<div id="table_check_result" class="frame_outer">

<script language='JavaScript'>
//js 字符串去左右空格和回车
function iGetInnerText(testStr) {
    var resultStr = testStr.replace(/\ +/g, ""); //去掉空格
    resultStr = testStr.replace(/[ ]/g, "");    //去掉空格
    resultStr = testStr.replace(/[\r\n]/g, ""); //去掉回车换行
    return resultStr;
}
    function checkQitaoxing(){
    	var objNumberInput = document.getElementById('objNumberInput');
		var objNumberInputValue = objNumberInput.value;
		
		if(objNumberInputValue==null || objNumberInputValue == ''){
			alert("请输入图纸代号");
			return false;
		}
    	Ext.Ajax.request({
    		url : getBaseHref()+"netmarkets/jsp/ext/yongc/plm/rule/checkQitaoxing.jsp",
    		params : {"objNumberInput":objNumberInputValue},
    		success:function(x){
    			
    			  if(x.responseText!=null){
    				 // alert(iGetInnerText(x.responseText));
    				 var validationResultStore = new Ext.data.JsonStore({
    					  fields: [ 'message' ],
    					  autoLoad: true,
    					  data:Ext.util.JSON.decode(iGetInnerText(x.responseText))
    					});
    				var grid = Ext.getDom("result_grid");
    				
    				if(grid){
    					//alert(grid.store);
    					grid.parentNode.removeChild(grid);
    				}
    					grid = new Ext.grid.GridPanel({
    					    id:"result_grid",
    						renderTo: Ext.getDom("table_check_result"),
    						frame: true,
    						//header: false,
    						viewConfig: {
    							forceFit:true
    						},
    						title: '<span><font color="#000000">齐套性检查结果</font></span>',
    						width:1200,
    						//height:500,
    						loadMask:true,
    						autoHeight:true,
    						store: validationResultStore,
    						columns: [
    						  {header: "序号", width: 10, dataIndex:"index",sortable:true},
    						  {header: "文档显示名称", width: 60, dataIndex:"displayName",sortable:true},
    						  {header: "Δ表示必须具备 ", width: 20, dataIndex:"isForce",sortable:true},
    						  {header: "检查结果", width: 20, dataIndex:"checkResult",sortable:true},
    						  {header: "文档编号,名称", width: 120, dataIndex:"docName",sortable:true},
    						  {header: "部件名称", width: 60, dataIndex:"partName",sortable:true},
    						  {header: "错误信息", width: 120, dataIndex:"message",sortable:true}
    						]
    					})
    				
    				 
    			  }
    		}
        });
    }
	
	
</script>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>