<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>



<jsp:include page="${mvc:getComponentURL('yjdoc.YJGYLXDocTableBuilder')}"/>


<script type="text/javascript">
function afterClick(formResult){
	  PTC.jca.table.Utils.reload('YJGYLXDocTableBuilder.table');
 }
 PTC.action.on ('objectsAffected',afterClick); 
</script>
 

<%@ include file="/netmarkets/jsp/util/end.jspf"%>