<%@page import="ext.yongc.plm.constants.TypeConstants"%>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib prefix="docmgnt" uri="http://www.ptc.com/windchill/taglib/docmgnt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>
<%@page pageEncoding="UTF-8" %>






 <jca:wizard title="编辑原材料链接">
 
    <jca:wizardStep action="editMaterialLinkTableStep" type="yjpart"/>

    </jca:wizard>


<%@ include file="/netmarkets/jsp/util/end.jspf"%>