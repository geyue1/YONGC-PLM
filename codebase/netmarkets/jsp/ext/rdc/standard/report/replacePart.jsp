<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%>

<script>
document.title='智能选型-搜索标准件替代方案';
</script>

<jsp:include page="${mvc:getComponentURL('ext.rdc.standard.mvc.ReplacePartPanelBuilder')}"/>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>