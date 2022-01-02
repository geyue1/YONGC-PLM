<%@page import="ext.yongc.plm.constants.TypeConstants"%>
<%@page import="com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper"%>
<%@page import="wt.change2.WTChangeOrder2"%>
<%@page import="wt.change2.ChangeHelper2"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.change2.WTChangeActivity2"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc"%>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%>

<wctags:collectItems tableId="changeTask_affectedItems_table" collectorId="CollectItemsFromChangeItem_AffectedItems" returnObjectReferences="false" returnOrigCopy="true" pickerCallback="PTC.change.changeable.collectorCallBack"/>
<wctags:collectItems tableId="changeTask_resultingItems_table" collectorId="CollectItemsFromChange_ResultingItems" returnObjectReferences="false" returnOrigCopy="true" pickerCallback="PTC.change.changeable.collectorCallBack"/>
<script src='netmarkets/javascript/hangingChanges/deferChange.js'></script>


<jsp:include page="${mvc:getTypeBasedComponentURL('yichange.YJAffectedItemsTableBuilder')}" flush="true"/>
<BR>
<jsp:include page="${mvc:getTypeBasedComponentURL('yjchange.YJResultingItemsTable')}" flush="true"/>
	  
<script>
var TABLE_ID = "changeTask_affectedItems_table";

   function selectAffectedCallback(objects){
	  
	   var pickedObjects = objects.pickedObject;
       var list = [];
       
       for (var i = 0, l = pickedObjects.length; i < l; i++) {
           list.push(pickedObjects[i]["oid"]);
       }
       addObjects(list);  
   }
   function addObjects(oids, tableId) {
       if(!tableId) {
            tableId = "changeTask_affectedItems_table";
        }
        var list = [];
        for(var i=0,l = oids.length; i<l;i++) {
            list.push(oids[i]);
        }
        setTimeout(function() { 
            var params = (list.length < 100) ? {doAjaxUpdate:true, preventDuplicates:true, onSuccess: ""} : {refreshTable:true, preventDuplicates:true};
            rowHandler.addRows(list,tableId,null,params);
        }, 1);
        
    }
   
</script>
	 
	 


<%@ include file="/netmarkets/jsp/util/end.jspf"%>


