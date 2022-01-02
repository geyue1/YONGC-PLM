<%@ include file="/netmarkets/jsp/util/begin.jspf"%>
<%@ page import="com.ptc.windchill.enterprise.attachments.attachmentsResource" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="com.ptc.core.components.util.PropagationHelper" %>
<%@ page import="com.ptc.netmarkets.model.NmOid" %>
<%@ page import="wt.content.ContentHolder" %>
<%@ page import="wt.preference.PreferenceHelper" %>
<%@ page import="wt.preference.PreferenceClient" %>
<%@ page import="com.ptc.jca.json.table.TableConfigHolder" %>
<%@ page import="com.ptc.mvc.components.FindInTableMode" %>
<%@ page import="wt.workflow.work.WorkItem" %>
<%@ page import="wt.org.WTUser" %>
<%@ include file="/netmarkets/jsp/attachments/initAttachments.jspf"%>
<%@ include file="/netmarkets/jsp/attachments/initPTCAttachments.jspf"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/core" prefix="core"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>

<c:if test="${param.role != null}">
   <c:set var="role" value="${param.role}"/>
</c:if>

<%   String paraCheckinOID = request.getParameter("checkinOid");   %>
<c:if test="${param.checkinOid != null}">
   <c:set var="checkinOid" value="<%= paraCheckinOID %>" />
</c:if>
<c:if test="${checkinOid != null}">
  <jsp:setProperty name="commandBean" property="elemAddress" value="${checkinOid}" />
</c:if>


<!-- Get the localized strings from the resource bundle -->
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="com.ptc.windchill.enterprise.attachments.attachmentsResource" />

<fmt:message var="att_label"          key="<%= attachmentsResource.ATTACHMENT_LABEL %>" />
<fmt:message var="att_number"         key="<%= attachmentsResource.ATTACHMENT_NUMBER %>" />
<fmt:message var="att_name"           key="<%= attachmentsResource.ATTACHMENT_NAME %>" />

<%
wt.org.WTUser user = (wt.org.WTUser)wt.session.SessionHelper.getPrincipal();

String uploadMechanism = (String) PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/uploadMechanism", null, user);

%>

<c:set var="uploadMechanism"   value="<%= uploadMechanism %>" />
<c:choose>
	<c:when test='${uploadMechanism == "APPLET"}'>
	<fmt:message var="att_location"       key="<%= attachmentsResource.ATTACHMENT_LOCATION_FOR_APPLET %>" />
	</c:when>

    <c:otherwise>
	<fmt:message var="att_location"       key="<%= attachmentsResource.ATTACHMENT_LOCATION %>" />
	</c:otherwise>
</c:choose>
<fmt:message var="att_description"    key="<%= attachmentsResource.ATTACHMENT_DESCRIPTION %>" />
<fmt:message var="att_comments"       key="<%= attachmentsResource.ATTACHMENT_COMMENTS %>" />
<fmt:message var="att_distributable"  key="<%= attachmentsResource.ATTACHMENT_EXTERNALDISTRIBUTION %>" />
<fmt:message var="att_authoredBy"     key="<%= attachmentsResource.ATTACHMENT_AUTHOREDBY %>" />
<fmt:message var="att_lastAuthored"   key="<%= attachmentsResource.ATTACHMENT_LASTAUTHORED %>" />
<fmt:message var="att_fileVersion"    key="<%= attachmentsResource.ATTACHMENT_FILEVERSION %>" />
<fmt:message var="att_toolName"       key="<%= attachmentsResource.ATTACHMENT_TOOLNAME %>" />
<fmt:message var="att_toolVersion"    key="<%= attachmentsResource.ATTACHMENT_TOOLVERSION %>" />

<!-- get optional column preferences -->
<%
    Boolean prefSortNumber    = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/sortNumber",    PreferenceClient.WINDCHILL_CLIENT_NAME);
    Boolean prefComments      = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/comments",      PreferenceClient.WINDCHILL_CLIENT_NAME);
    Boolean prefDistributable = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/distributable", PreferenceClient.WINDCHILL_CLIENT_NAME);
    Boolean prefAuthoredBy    = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/authoredBy",    PreferenceClient.WINDCHILL_CLIENT_NAME);
    Boolean prefLastAuthored  = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/lastAuthored",  PreferenceClient.WINDCHILL_CLIENT_NAME);
    Boolean prefFileVersion   = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/fileVersion",   PreferenceClient.WINDCHILL_CLIENT_NAME);
    Boolean prefToolName      = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/toolName",      PreferenceClient.WINDCHILL_CLIENT_NAME);
    Boolean prefToolVersion   = (Boolean)PreferenceHelper.service.getValue("/com/ptc/windchill/enterprise/attachments/optionalAttributes/toolVersion",   PreferenceClient.WINDCHILL_CLIENT_NAME);
%>
<c:set var="cSortNumber"    value="<%= prefSortNumber    %>" />
<c:set var="cComments"      value="<%= prefComments      %>" />
<c:set var="cDistributable" value="<%= prefDistributable %>" />
<c:set var="cAuthoredBy"    value="<%= prefAuthoredBy    %>" />
<c:set var="cLastAuthored"  value="<%= prefLastAuthored  %>" />
<c:set var="cFileVersion"   value="<%= prefFileVersion   %>" />
<c:set var="cToolName"      value="<%= prefToolName      %>" />
<c:set var="cToolVersion"   value="<%= prefToolVersion   %>" />


<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>

<input type="hidden" name="newFiles" id="newFiles" value="" />
<input type="hidden" name="fileSep" id="fileSep" value="\" />
<input type="hidden" name="fileAttachmentCount" id="fileAttachmentCount" value="0" />
<input type="hidden" name="scmResults" value="" id="scmResults">

<%
String scmiPickerAddress ="";
if( wt.facade.scm.ScmFacade.getInstance().isInstalled())
{
    String paraoid = request.getParameter("oid");
    if (paraoid != null && paraoid.length() > 0) 
    {
         NmOid oid = NmOid.newNmOid(paraoid);
         paraoid = oid.getOid().toString();
         Object refObject = oid.getRefObject();
         // SPR 2099982. Added a check for WorkItem
         // SPR 2173767. Added a check for WTUser
         if(! (refObject instanceof WorkItem || refObject instanceof WTUser))
         {
        	 scmiPickerAddress = wt.facade.scm.gui.ScmGuiHelper.getBrowserURL(paraoid);
         }
    }
}
%>
<script Language="JavaScript">
      scmpickerlocation = '<%=scmiPickerAddress%>';
</script>
<c:choose>
    <c:when test='${role == "WP_EXP_SECONDARY"}'>
         <c:set var="tableId" value="${param.tableId}"/>
         <c:set var="actionName" value="${param.actionName}"/>
         <c:set var="tableLabel" value="${param.tableLabel}"/>
                 <c:set var="roleType" value="<%= wt.content.ContentRoleType.WP_EXP_SECONDARY %>"/>
                 <fmt:message var="att_location"       key="<%= attachmentsResource.WP_EXP_ATTACHMENT_LOCATION %>" />
                 <fmt:message var="att_name"           key="<%= attachmentsResource.WP_EXP_ATTACHMENT_NAME %>" />
         <c:set var="helpFileName" value="ObjectOviewInfoTablesGeneral"/>                 
    </c:when>
    <c:otherwise>
         <c:set var="tableId" value="attachments.list.editable"/>
         <c:set var="actionName" value="attachments wizard table toolbar actions"/>
         <c:set var="tableLabel" value="${att_label}"/>
                 <c:set var="roleType" value="<%= wt.content.ContentRoleType.SECONDARY %>"/>
         <c:set var="helpFileName" value="DocMgmtAttachmentAbout"/>
                 
    </c:otherwise>

</c:choose>

<!-- * means required -->
<jca:describeTable var="tableDescriptor" id="${tableId}" type="wt.content.ContentItem" label="${tableLabel}" mode="${createBean.operation}" scope="request">
   <jca:setComponentProperty key="actionModel" value="${actionName}"/>
   <jca:setComponentProperty key="variableRowHeight" value="true"/>
   <jca:setComponentProperty key="<%=TableConfigHolder.FIND_IN_TABLE_MODE%>" value="<%=FindInTableMode.DISABLED%>"/>   
   
   <%-- the gridfileinputhandler plugin will disable many grid features, so that using a browser input field in the ext grid will work--%>
   <jca:setTablePlugin ptype="gridfileinputhandler"/>

   <jca:describeColumn id="type_icon"                 sortable="false" />
   <c:if test="${cSortNumber}">
    <jca:describeColumn id="contentLineNumber"    sortable="false" />
   </c:if>
  
   <jca:describeColumn id="contentName"               sortable="false"  label="*${att_name}">
      <jca:setComponentProperty key="useExact" value="true"/>
   </jca:describeColumn>
     
   <jca:describeColumn id="contentLocation"           sortable="false"  label="*${att_location}">
      <jca:setComponentProperty key="useExact" value="true"/>
   </jca:describeColumn>

   <jca:describeColumn id="contentDescription"        sortable="false"  label="${att_description}" />

    <c:if test="${cComments}">
        <jca:describeColumn id="contentComments"      sortable="false"  label="${att_comments}" />
    </c:if>
    <c:if test="${cDistributable}">
        <jca:describeColumn id="contentDistributable" sortable="false"  label="${att_distributable}" />
    </c:if>
    <c:if test="${cAuthoredBy}">
        <jca:describeColumn id="contentAuthoredBy"    sortable="false"  label="${att_authoredBy}" />
    </c:if>
    <c:if test="${cLastAuthored}">
    <jca:describeColumn id="contentLastAuthored"  sortable="false"  label="${att_lastAuthored}" />
    </c:if>
    <c:if test="${cFileVersion}">
        <jca:describeColumn id="contentFileVersion"   sortable="false"  label="${att_fileVersion}" />
    </c:if>
    <c:if test="${cToolName}">
         <jca:describeColumn id="contentToolName"     sortable="false"  label="${att_toolName}" />
    </c:if>
    <c:if test="${cToolVersion}">
         <jca:describeColumn id="contentToolVersion"  sortable="false"  label="${att_toolVersion}" />
    </c:if>
</jca:describeTable>

<c:set target="${tableDescriptor.properties}" property="selectable" value="true"/>

<jca:getModel var="tableModel" descriptor="${tableDescriptor}"
               serviceName="com.ptc.windchill.enterprise.attachments.commands.AttachmentQueryCommands"
               methodName="getAttachments">
    <jca:addServiceArgument value="${commandBean}" type="com.ptc.netmarkets.util.beans.NmCommandBean" />
    <jca:addServiceArgument value="${roleType}"/>
</jca:getModel>

<jca:renderTable model="${tableModel}" helpContext="${helpFileName}" scroll="true"/>

<!-- Below script is added for auto resizing of the table column -->
<script Language="JavaScript">
    //This attachment_step table is being used in differnet functionalities like create document, edit, checkout and edit
    //This table many be empty or have some rows when its launched. This is based on where its used. Hence it requires both 'add' and 'datachanged' callbacks
    //In some cases, if table is already available, 'onAvailable' is not applicable. And in other case, table will be availabe, and here it requires onAvailable
    var grid =Ext.getCmp('${tableId}');
    if(grid)
    {
		grid.getStore().on('add', function(store) {
			grid.clearStickyConfig(); // reset values
			PTC.jca.ColumnUtils.resizeAllColumns(grid);
			return true;
		}, null, {single: true, delay:100}); // only run this function one time
	
		grid.getStore().on('datachanged', function(store) {
			grid.clearStickyConfig(); // reset values
			PTC.jca.ColumnUtils.resizeAllColumns(grid);
			return true;
		}, null, {single: true, delay:100}); // only run this function one time
    }
    else
    {
        Ext.ComponentMgr.onAvailable('${tableId}',function (){
            var grid =Ext.getCmp('${tableId}');
            if(grid){
                grid.getStore().on('add', function(store) {
                    grid.clearStickyConfig(); // reset values
                    PTC.jca.ColumnUtils.resizeAllColumns(grid);
                    return true;
                }, null, {single: true, delay:100}); // only run this function one time
                
                grid.getStore().on('datachanged', function(store) {
                    grid.clearStickyConfig(); // reset values
                    PTC.jca.ColumnUtils.resizeAllColumns(grid);
                    return true;
                }, null, {single: true, delay:100}); // only run this function one time
            }
        });
    }
</script>

<input type="hidden" name="scmi_picker" id="scmi_picker" value="<%=scmiPickerAddress%>"/>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>
