<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>



<jsp:include page="${mvc:getComponentURL('yjpart.YJMaterialPartListTableBuilder')}"/>



<%@ include file="/netmarkets/jsp/util/end.jspf"%>