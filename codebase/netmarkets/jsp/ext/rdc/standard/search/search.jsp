<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@taglib uri="http://www.ptc.com/windchill/taglib/wrappers"  prefix="w"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>标准件高级搜索</title>
</head>
<body>
<%
    String headPaneUrl="/netmarkets/jsp/ext/rdc/standard/search/searchHeaderPane.jsp";
    String leftPaneUrl="/netmarkets/jsp/ext/rdc/standard/search/searchLeftPane.jsp";
    String rightPaneUrl="/netmarkets/jsp/ext/rdc/standard/search/searchRightPane.jsp";
%>
<div id="searchMainDIV"/>
</body>
</html>
<script type="text/javascript">

PTC.navigation.loadViewPort = function(pageItems){
	  new Ext.Panel({
		  title:'标准件123',
          layout:'fit',
          id: 'jca_viewport',
          renderTo: 'searchMainDIV',
          width :document.documentElement.clientWidth*0.95,
          height: document.documentElement.clientHeight*0.78,
          items: pageItems
      });

}
PTC.navigation.loadShell(PTC.navigation.POPUP_SHELL);

getChildNodeByTag = function (parentElement, tagName) {
    if(parentElement) {
        if(parentElement.tagName === tagName) {
            return parentElement;
        } else {
            var childNodes = parentElement.childNodes;
            for(var i=0; i < childNodes.length; i++) {
                return getChildNodeByTag(childNodes[i], tagName);
            }
        }
    }

    return undefined;
};
function renderAttrPane(oidStr){
	 var options = {
             asynchronous: false,
             method: 'get',
             parameters: {
            	 oidStr: oidStr,
            	 executeSearch:false
             }
         };
	var transport = requestHandler.doRequest(getBaseHref() + 'netmarkets/jsp/ext/rdc/standard/search/getClassNodeDisplayName.jsp', options);
	
	var span = document.getElementById('view_ClassNode');
	span.innerHTML=transport.responseText;
	var span_criteria = document.getElementById('view_criteria');
	span_criteria.innerHTML='';
	
	var cp = PTC.getCmp(PTC.navigation.cardManager);
    var renderToDiv = 'searchCriteriaDiv';
    var contentPanel = PTC.getCmp(PTC.navigation.contentTabID + '_twoPanes_bottom');
   
	
    if(contentPanel !== undefined) {
        contentPanel.clearCardContents();
    }
    var resultPanel = PTC.getCmp('searchResultPanel');
    if(resultPanel !== undefined) {
    	resultPanel.clearCardContents();
    }
    var html = requestHandler.doRequest(getBaseHref() + 'ptc1/ext.rdc.standard.search', options);
    var content = html.responseText.replace('</FORM>','');
      var panel = new Ext.Panel ({
          id: 'searchCriteriaPanel',
          html:content,
          renderTo: renderToDiv
      });
     
    // Ext.getCmp('searchCriteriaPanel').body.update(''');
     panel.load({
		url:'netmarkets/jsp/ext/rdc/standard/search/123.html',
		params:getFormData(),
		scope : this, // 范围
	    discardUrl : true, // 丢弃url
	    nocache : true, // 不缓存
	    timeout : 1000, // 延时0.1秒
	    scripts : true//是否有js脚本
	});
   
    //panel.doLayout();
   
}
var length = tableUtils.getTableSelectedRowsById('rdc.CLF.tree').length;
function onClickHandler(evt){
	var tableId = 'rdc.CLF.tree';
	var selected = tableUtils.getTableSelectedRowsById(tableId);
	var label = getChildNodeByTag(evt.target, 'LABEL');
	if(label && label.htmlFor.indexOf('com.ptc.core.lwc.server.LWCStructEnumAttTemplate')>-1){
		var isChecked = true;
		for(var i=0;i<selected.length;i++){
			  var oid = getOidFromRowValue(selected[i].value);
			  if(label.htmlFor==oid){
				  var row = PTC.jca.table.Utils.getTableRowFromOid(tableId, label.htmlFor);
				  PTC.jca.table.Utils.selectRow(tableId, row, false);
				  isChecked = false;
				  break;
			  }
		}
		if(isChecked){
			var row = PTC.jca.table.Utils.getTableRowFromOid(tableId, label.htmlFor);
			  PTC.jca.table.Utils.selectRow(tableId, row, true);
		}
		
		selected = tableUtils.getTableSelectedRowsById(tableId);
		var oidStr='';
		for(var i=0;i<selected.length;i++){
			 var oid = getOidFromRowValue(selected[i].value);
			 oidStr=oidStr+oid+',';
		}
		renderAttrPane(oidStr);	
	}
	var div = getChildNodeByTag(evt.target, 'DIV');
	if(div){
		selected = tableUtils.getTableSelectedRowsById(tableId);
		var length2 = selected.length;
		if(length!=length2){
			length = length2;
			var oidStr='';
			for(var i=0;i<selected.length;i++){
				 var oid = getOidFromRowValue(selected[i].value);
				 oidStr=oidStr+oid+',';
			}
			renderAttrPane(oidStr);	
		}
	}
	
}

</script>
<jca:renderTwoPanes
   headerPane="<%=headPaneUrl %>"
   headerPaneHeight="1"
   leftPane="<%=leftPaneUrl%>"
   leftPaneSize="30"
   rightPane="<%=rightPaneUrl%>"
   rightPaneSize="70"
   orientation="horizontal"
   onClick="onClickHandler"/>
   
<%@ include file="/netmarkets/jsp/util/end.jspf"%>