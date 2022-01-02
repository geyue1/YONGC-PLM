<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<script>
var TABLE_ID = "changeTask_resultingItems_table";
var tableDataManager = PTC.util.tableDataManager;

	 function renderTable() {
	       // setTimeout('renderTable()', 1);
		 var table = tableUtils.getTable(TABLE_ID);
         var v = table.view;
         v._cache = {};
         v.doUpdate(true);
	    }
	 function promoteObject(table, record,type) {
		 
		 var oid = record.get('oid');
		 var promotionStatus = record.get('gongyi_type');
		 if(promotionStatus && promotionStatus.gui) {
			 var gui = promotionStatus.gui;
			 if(type==null || type==""){
				 gui.value = "";
			 }else{
				 var value =gui.value;
				 if(value!="&nbsp;" && value!=""){
					 if(value.indexOf(type)>-1){
						 
					 }else{
						 gui.value = value+","+type;
					 }
					
				 }else{
					 gui.value = type;
				 }
			 }
			 
             record.commit();
         
          }
	 }
	
	function ajaxSetGongYiType(records,type){
		var oid ="";
		 for(var i=0,l=records.length; i<l; i++) {
			 oid = oid+records[i].get("oid")+","; 
		       }
		
		Ext.Ajax.request({
			url : getBaseHref()+"netmarkets/jsp/yjpromotion/setGongYiType.jsp",
			params : {"oid":oid,"type":type},
			success:function(x){
				// do nothing
			}
		});
		
	}
	function setGongYiType(type){
		if(type === null || ""===type){
			return false;
		}
		
		 var table = tableUtils.getTable(TABLE_ID);
         var selections = table.getSelectionModel().getSelections();
         if(selections==null || selections.length==0){
        	 alert("请选择数据！");
        	 return false;
         }
         var store = table.getStore();
         ajaxSetGongYiType(selections,type);
         store.suspendEvents();  
         for(var i=0,l=selections.length; i<l; i++) {
            promoteObject(table, selections[i],type); 
         }
         store.resumeEvents();                
         renderTable();
	}
	function removeGongYi(){
		var type = "";
		 var table = tableUtils.getTable(TABLE_ID);
         var selections = table.getSelectionModel().getSelections();
         if(selections==null || selections.length==0){
        	 alert("请选择数据！");
        	 return false;
         }
         var store = table.getStore();
         ajaxSetGongYiType(selections,type);
         store.suspendEvents();  
         for(var i=0,l=selections.length; i<l; i++) {
            promoteObject(table, selections[i],type); 
         }
         store.resumeEvents();                
         renderTable();
	}

</script>

<jsp:include page="${mvc:getComponentURL('yjchange.YJResultingDataTable')}"/>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>


