<%@page import="com.ptc.windchill.partslink.PartslinkConstants"%>
<%@page import="com.ptc.windchill.partslink.model.RefineBean"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="com.ptc.windchill.partslink.model.RefineModel"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>




<jsp:include page="${mvc:getComponentURL('ext.rdc.standard.mvc.SearchResultTableBuilder')}"/>

<script type="text/javascript">
  function selectAllData(){
	  var tableId = 'rdc.standard.resultTable1';
	  var table = PTC.jca.table.Utils.getTable(tableId);
	  if(table){
		  var flag =  PTC.jca.table.Utils.isAllDataLoaded(table);
		  if(flag){
			  var tableRows = PTC.jca.table.Utils.getTableRows(tableId);
			  var tables = PTC.jca.table.Utils.getTables();
			  for(var obj in tables){
				  console.log('obj='+obj+',=>'+tables[obj]);
			  }
			 
			  alert('tableRows='+tableRows.length);
			  for(var i=0;i<tableRows.length;i++){
				  var row = tableRows.get(i);
				  PTC.jca.table.Utils.selectRow(tableId, row, true);
			  }
			  return;
		  }
	  }
	  setTimeout("selectAllData()", 500); 
	  return;
  }
  Ext.onReady(function(){
	  selectAllData();
  });
</script>


<%@ include file="/netmarkets/jsp/util/end.jspf"%>