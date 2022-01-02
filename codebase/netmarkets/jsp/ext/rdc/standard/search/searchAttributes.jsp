<%@page import="com.ptc.windchill.partslink.PartslinkConstants"%>
<%@page import="com.ptc.windchill.partslink.model.RefineBean"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="ext.rdc.standard.util.SearchUtil"%>
<%@page import="com.ptc.windchill.partslink.model.RefineModel"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>


 
 
<jsp:include page="${mvc:getComponentURL('ext.rdc.standard.mvc.SearchCriteriaPanelBuilder')}"/>

<input type="hidden" id="check" name="check" value = "check"/>
<script type="text/javascript">
//selectOperatorValue 用户点击页面上可选值列表时改版可选值的背景颜色，同时将
//选中的值显示在可编辑的文本框里

function selectOperatorValue(event,attrId,operator){
	attrId = attrId+'_RDC';
	var value = document.getElementById(attrId).value;
	var fontTag =event.target; 
	if('FONT'===fontTag.tagName){
		//选中  color:#03447E是初始样式
		//outerHTML=<font id="font_IBA138126_t" style="color:#03447E">上海</font>
		if(fontTag.outerHTML.indexOf('color:#03447E')>-1){
			fontTag.outerHTML = fontTag.outerHTML.replace('color:#03447E','color:#fff;background-color:#f60');
			//fontTag.outerHTML='color:#fff;background-color:#f60';
			if(value===''){
				value = operator;
			}else if(value.indexOf(operator)<0){
				value = value+'|'+operator;
			}
			document.getElementById(attrId).value=value;
		}else if(fontTag.outerHTML.indexOf('color:#fff;background-color:#f60')>-1){
			fontTag.outerHTML = fontTag.outerHTML.replace('color:#fff;background-color:#f60','color:#03447E');
			if(value.indexOf(operator)==0){
				value = value.replace(operator,'');
			}else if(value.indexOf(operator)>0){
				value = value.replace('|'+operator,'');
			}else if(value.indexOf(operator+'|')==0){
				value = value.replace(operator+'|','');
			}
			if(value.indexOf('|')==0){
				value = value.replace('|','');
			}
			document.getElementById(attrId).value=value;
		}
	}
	
}
var tableId = 'rdc.CLF.tree';
//viewSelectValue 将用户选择的搜索条件显示在页面中
function viewSelectValue(){
	var displayNames = document.getElementsByName('displayName_RefineBean');
	var criteria='';
	for(var i=0;i<displayNames.length;i++){
		var display = displayNames[i];
		var name = display.value;
		var attrId = display.id.replace('displayName_','');
		attrId = attrId+'_RDC';
		var value = document.getElementById(attrId).value;
		var rangeLow = document.getElementById('rangeLow_'+attrId);
		var rangeHi = document.getElementById('rangeHi_'+attrId);
		var hasRange = false;
		if(rangeLow && rangeLow.value!=null && rangeLow.value!=''){
			criteria =criteria+ name+'>='+rangeLow.value+';';
			hasRange = true;
		}
		if(rangeHi && rangeHi.value!=null && rangeHi.value!=''){
			criteria =criteria+ name+'<='+rangeHi.value+';';
			hasRange = true;
		}
		
		var operator = document.getElementById('csmSearchOperator_'+attrId);
		var operatorValue='=';
		if(value!=null && value!=='' && !hasRange){
			if(operator){
				if(operator.value!=null && operator.value!='' && operator.value!='Range'){
					operatorValue = operator.value;
				}
			}
			criteria = criteria+name+operatorValue+value+';'
		}
		
	}
	var span = document.getElementById('view_criteria');
	if(span){
		span.innerHTML=criteria;
	}
}



</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>
