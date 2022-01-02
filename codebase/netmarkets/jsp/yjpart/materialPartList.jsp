<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@page pageEncoding="UTF-8" %>

<jsp:include page="${mvc:getComponentURL('yjpart.YJMaterialPartListTableBuilder')}"/>



<script type="text/javascript">
  function validateRemove(){
	  var table = tableUtils.getTable("YJMaterialPartListTableBuilder.table");
      var selections = table.getSelectionModel().getSelections();
      if(selections==null || selections.length==0){
     	 alert("请选择数据！");
     	 return false;
      }
  }
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>