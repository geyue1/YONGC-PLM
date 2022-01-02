<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<script language='JavaScript' type="text/javascript" src='netmarkets/javascript/promotionRequest/promotionRequest.js' ></script>
<div id="MessageComponent"></div>

<%
   String partOid = request.getParameter("oid");
   System.out.println(" partOid----------->"+partOid);
%>
<input type="hidden" id="partOid" name="partOid" value="<%=partOid%>"/>
<input type="hidden" id="maturityState" name="maturityState" value="RELEASED"/>

<div id="selectPromotionState" style="display: none" >
   <%@ include file="/netmarkets/jsp/promotionRequest/promotionStateSelection.jspf"%>
</div>
<script>
	Ext.ComponentMgr.onAvailable("promotionRequest.promotionObjects", PTC.promotion.attachEvents, PTC.promotion);
</script>

<jsp:include page="${mvc:getComponentURL('yjpromotion.CollectReviewDocStepTableBuilder')}"/>


<jca:describeTableTree var="treeDescriptor" id="mytreetable" label="MBOM" nodeColumn="number" expansion="full">
	<jca:describeColumn id="type_icon"/>		
	<jca:describeColumn id="number"/>
	<jca:describeColumn id="infoPageAction"/>
	<jca:describeColumn id="name"/>
	
	<jca:describeColumn id="state"/>
	<jca:describeColumn id="creator"/>
	<jca:describeColumn id="thePersistInfo.modifyStamp"/>
</jca:describeTableTree>

<jca:getModel var="treeModel" descriptor="${treeDescriptor}" treeHandler="MBOMPromotionListTreeHandler"/>

<jca:renderTableTree model="${treeModel}" showCount="true"/>

<script>
	PTC.promotion.init("promotionRequest.promotionObjects");
</script>


<script>
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