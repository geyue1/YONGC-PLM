<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.part.WTPart"%>
<%@page import="ext.yongc.plm.constants.TypeConstants"%>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib prefix="docmgnt" uri="http://www.ptc.com/windchill/taglib/docmgnt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>
<fmt:setBundle basename="com.ptc.windchill.enterprise.doc.documentResource"/>
<fmt:message var="defineDocWizStepLabel" key="document.create.DEFINE_ITEM_WIZ_STEP_LABEL" />

<%
     String type = TypeConstants.DOC_DP;
     String baseName = "WCTYPE|wt.doc.WTDocument|"+type;
     
     request.setAttribute("show_primary_content", "false");
     request.setAttribute("show_template_picker", "false");
     
     
%>

<%-- insert revision javascript --%>
<script language="JavaScript" src='netmarkets/javascript/util/revisionLabelPicker.js'></script>

    <c:choose>
        <%--  used for DTI to prepopulate attributes in the attributes wizard step --%>
      <c:when test='${param.externalFormData != null}'>
         <jca:initializeItem operation="${createBean.create}" attributePopulatorClass="com.ptc.windchill.enterprise.nativeapp.msoi.forms.ExternalFormDataPopulator" baseTypeName="<%=baseName%>"/>
      </c:when>
      <c:otherwise>
         <%-- populate number attrubute for insert revision action only --%>
         <jca:initializeItem operation="${createBean.create}"
           attributePopulatorClass="com.ptc.windchill.enterprise.doc.forms.DocAttributePopulator"
           baseTypeName="<%=baseName%>"/>
      </c:otherwise>
   </c:choose>
   
	<c:choose>
	<c:when test='${param.typePickerSeedObj != null}'>
		<input type="hidden" name="typePickerSeedObj" value="${param.typePickerSeedObj}">
	</c:when>
	</c:choose>

   <% 
        String userAgent = commandBean.getTextParameter("ua");
        boolean isDTI = false;
        if(userAgent != null && userAgent.equals("DTI"))
        isDTI=true;
   %>
   
   <c:set var="isDTI" value="<%=isDTI%>" />
   <input type="hidden" id="isDTI" name="isDTI" value="<%=isDTI%>">
   <input type="hidden" id="revisionMode" name="revisionMode" value="create">

   <c:choose>
      <c:when test='${param.showContextStep != null}'>
         <c:set var="helpKey" value="createSharedDoc" scope="page"/>
         <c:set var="buttonList" value="DefaultWizardButtons" scope="page"/>
         <!-- Exclude Program contexts -->
         <jsp:setProperty name="createBean" property="contextPickerExcludeTypes" value="WCTYPE|wt.projmgmt.admin.Project2|com.ptc.Program"/>
      </c:when>
      
      <c:when test='${isDTI}'>
        <%-- Set the DTI doc management help --%>
        <c:set var="helpKey" value="DTIDocMgmtDocCreate" scope="page"/>
        <c:set var="buttonList" value="DefaultWizardButtonsNoApply" scope="page"/>
      </c:when>      
      
      <c:otherwise>
         <%-- Set the default doc management help --%>
         <c:set var="helpKey" value="DocMgmtDocCreate" scope="page"/>
         <c:set var="buttonList" value="DefaultWizardButtonsNoApply" scope="page"/>
      </c:otherwise>
   </c:choose>

<%
    if(isDTI){
        request.setAttribute("show_template_picker", "false");
%>
        <input type="hidden" value="false" name="show_template_picker"/>
<%
    }
%>

   <%--  renders javascript to be used by the duplicate name validation on the attributes step --%>
   <docmgnt:validateNameJSTag/>

    <%--  Define attributes of the wizard and define the wizard steps --%>
    <jca:wizard title="${param.titleString}"
     buttonList="${buttonList}"
     helpSelectorKey="${helpKey}">
      <%-- defines the context when coming in from DTI or edacompare (or if no context is set) --%>
            <jca:wizardStep action="setContextWizStep" type="object"/>

      <%-- contains the type picker and the org picker --%>

      <%-- doc management specific attributes step. also contains primary attachment --%>
      <jca:wizardStep action="createDocumentSetTypeAndAttributesWizStep" type="document" />
      
      <%-- Set Security Label Step --%>
      <jca:wizardStep action="securityLabelStep" type="securityLabels"/>

      <%-- todo needs documentation --%>
      <jca:wizardStep action="typeRefWizStep"    type="object"/>
      
      <jca:wizardStep action="createDeliveryPackage_step"  type="yjdoc" />

       <%-- adds attachments to the document --%>
      <jca:wizardStep action="attachments_step"  type="attachments" />

    </jca:wizard>

   <%--- If we are not DTI then add the applet for doing file browsing and file uploads --%>
   <wctags:fileSelectionAndUploadAppletUnlessMSOI forceApplet='${param.forcedFilePath != null }'/>

        <%--- Vertical padding to take care of above applet, otherwise a vertical scroll will be visible, which dose not look logical --%>
        <SCRIPT>
      PTC.wizard.getContentAreaPaddingHeight = PTC.wizard.getContentAreaPaddingHeight.wrap(function(orig) {
      return orig.call(this) + 12;
      });
      
    </SCRIPT>
    
     
    
    <%@ include file="/netmarkets/jsp/util/end.jspf"%>
