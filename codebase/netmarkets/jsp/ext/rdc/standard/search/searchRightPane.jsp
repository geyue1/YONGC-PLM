<%@page import="java.util.ResourceBundle"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="java.util.Locale"%>
<%@page import="wt.httpgw.URLFactory"%>
<%@page language="java" session="true" pageEncoding="UTF-8"%>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%>
<%@ taglib prefix="wrap" uri="http://www.ptc.com/windchill/taglib/wrappers"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ taglib prefix="mvc" uri="http://www.ptc.com/windchill/taglib/mvc"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca"%>
<%@taglib uri="http://www.ptc.com/windchill/taglib/wrappers"  prefix="w"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>



<script type="text/javascript">
function startSearch(){
	   var check = document.getElementById('check');
	   if(check){
		   //自动执行onclick鼠标单击事件. 默认已点击
			if(document.all) {
				//IE
				check.click();
			}else{
				//其他浏览器
				var e = document.createEvent("MouseEvents");
				e.initEvent("click", true, true);
				check.dispatchEvent(e);
			}
	   }
	   var param = getFormData();
	   var selected = tableUtils.getTableSelectedRowsById('rdc.CLF.tree');
		var oidStr='';
		for(var i=0;i<selected.length;i++){
			 var oid = getOidFromRowValue(selected[i].value);
			 oidStr=oidStr+oid+',';
		}
	
	   var resultPanel = PTC.getCmp('searchResultPanel');
	    if(resultPanel !== undefined) {
	    	resultPanel.clearCardContents();
	    }
	    resultPanel = new PTC.Panel ({
	          id: 'searchResultPanel',
	          renderTo: 'searchResultDiv'
	      });
	   
		  
	    resultPanel.load({
			url:'ptc1/ext.rdc.standard.search?executeSearch=true&oidStr='+oidStr,
			params: param,
			scope : this, 
		    discardUrl : true, 
		    nocache : true, 
		    timeout : 1000, 
		    scripts : true
		});
	   
	    resultPanel.doLayout();
}
  
  
</script>

<fieldset class="x-fieldset x-form-label-left"
	id="Visualization_and_Attributes" >
	<legend>标准件高级搜索</legend>
<table class="attributePanel-group-panel" border="0"><tbody>
<tr>
<td  class="attributePanel-label">分类:</td>
<td class="attributePanel-value" width="600px">
 <span id="view_ClassNode"></span>
</td>

<td align="right" >
   <w:checkBox id = "standardPart1" name="standardPart1"  checked="true"/>检索企业标准件
   &nbsp;
   <w:checkBox id="standardPart2" name="standardPart2" />检索专业标准件
</td>

<tr>
<td  class="attributePanel-label">搜索条件:</td>
<td class="attributePanel-value" width="600px">
 <span id="view_criteria"></span>
</td>
</tr>

<tr>
<td  class="attributePanel-label"></td>
<td class="attributePanel-value" width="600px">
</td>
<td >
<w:button name="button" value="搜索" typeSubmit="false" toolTipText="搜索" onclick="startSearch()"/>
</td>
</tr>

</tbody></table>
</fieldset>

<div id="searchCriteriaDiv" >
	
</div>
<div id="searchResultDiv" >

</div>


<%@ include file="/netmarkets/jsp/util/end.jspf"%>