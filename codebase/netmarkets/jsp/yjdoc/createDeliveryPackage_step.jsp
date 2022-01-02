<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>


<jsp:include page="${mvc:getComponentURL('yjdoc.CreateDeliveryPackageBuilder')}"/>


<script>
    var TABLE_ID = "CreateDeliveryPackageBuilder.table";
    
    
    //js 字符串去左右空格
    function trim(s){
    	return s.replace(/(^s*)|(s*$)/g, "");
    }
  //js 字符串去左右空格和回车
    function iGetInnerText(testStr) {
        var resultStr = testStr.replace(/\ +/g, ""); //去掉空格
        resultStr = testStr.replace(/[ ]/g, "");    //去掉空格
        resultStr = testStr.replace(/[\r\n]/g, ""); //去掉回车换行
        return resultStr;
    }
    function selectCallback(objects){
 	  
 	   var pickedObjects = objects.pickedObject;
        var list = [];
        var list2 = [];
        
        for (var i = 0, l = pickedObjects.length; i < l; i++) {
        	//alert(pickedObjects[i]["oid"]);
        	var oid = pickedObjects[i]["oid"];
        	list.push(oid);
        }
        addObjects(list);  
    }
    function addObjects(oids, tableId) {
        if(!tableId) {
             tableId = "CreateDeliveryPackageBuilder.table";
         }
         var list = [];
         for(var i=0,l = oids.length; i<l;i++) {
             list.push(oids[i]);
         }
         setTimeout(function() { 
             var params = (oids.length < 100) ? {doAjaxUpdate:true, preventDuplicates:true, onSuccess: ""} : {refreshTable:true, preventDuplicates:true};
             rowHandler.addRows(oids,TABLE_ID,null,params);
         }, 1);
         
     }
    function removePackageItem(){
    	var type = "";
		 var table = tableUtils.getTable(TABLE_ID);
        var selections = table.getSelectionModel().getSelections();
        if(selections==null || selections.length==0){
       	 alert("请选择数据！");
       	 return false;
        }
		var selectedOids = getSelectedOidString(TABLE_ID);
		var oids = [];
		oids.push( selectedOids );
		PTC.jca.table.Utils.removeRows(TABLE_ID,oids);
        var store = table.getStore();
      
        store.resumeEvents();                
        renderTable();
    }
    function selectAll(){
  	  
		var table = PTC.jca.table.Utils.getTable(TABLE_ID); 
		
		table.getSelectionModel().selectAll();
		var selections=table.getSelectionModel().getSelections();
	    if(selections.length==0){
			alert("没有数据");
			return false;
		}
		return true;
	}
    setUserSubmitFunction(selectAll);
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>