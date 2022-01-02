<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<script language='JavaScript' type="text/javascript" src='netmarkets/javascript/promotionRequest/promotionRequest.js' ></script>
<div id="MessageComponent"></div>

<input type="hidden" id="maturityState" name="maturityState" value="RELEASED"/>

<div id="selectPromotionState" style="display: none" >
   <%@ include file="/netmarkets/jsp/promotionRequest/promotionStateSelection.jspf"%>
</div>
<wctags:collectItems tableId="promotionRequest.promotionObjects" collectorId="Promote" returnOrigCopy="true" pickerCallback="PTC.promotion.collectorPickerCallback"/>
<script>
	Ext.ComponentMgr.onAvailable("promotionRequest.promotionObjects", PTC.promotion.attachEvents, PTC.promotion);
</script>

<jsp:include page="${mvc:getComponentURL('yjpromotion.CollectReviewDocStepTableBuilder')}"/>



<script>
	PTC.promotion.init("promotionRequest.promotionObjects");
</script>


<script>
var TABLE_ID = "promotionRequest.promotionObjects";

   function select3DDocCallback(objects){
	  
	   var pickedObjects = objects.pickedObject;
       var list = [];
       
       for (var i = 0, l = pickedObjects.length; i < l; i++) {
           list.push(pickedObjects[i]["oid"]);
       }
       addObjects(list);  
   }
   function addObjects(oids, tableId) {
       if(!tableId) {
            tableId = "promotionRequest.promotionObjects";
        }
        var list = [];
        for(var i=0,l = oids.length; i<l;i++) {
            list.push(oids[i]);
        }
        setTimeout(function() { 
            var params = (list.length < 100) ? {doAjaxUpdate:true, preventDuplicates:true, onSuccess: PTC.promotion.refreshStateSelection} : {refreshTable:true, preventDuplicates:true};
            rowHandler.addRows(list,tableId,null,params);
        }, 1);
        
        
        
    }
   
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>