<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ page import="com.ptc.mvc.util.MVCUrlHelper,com.ptc.windchill.csm.util.CLFAccessUtils,
                com.ptc.windchill.csm.client.utils.CSMUtils,wt.access.NotAuthorizedException"%>




<jsp:include page="${mvc:getComponentURL('ext.rdc.standard.mvc.ClassificationTreeBuilder')}"/>

 <script type="text/javascript">
   document.title='标准件高级搜索';
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>