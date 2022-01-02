<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"
%><%@ taglib prefix="cwiz" uri="http://www.ptc.com/windchill/taglib/changeWizards"
%><%@ taglib prefix="security" uri="http://www.ptc.com/windchill/taglib/securitycomponents"
%><%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc"
%><%@ include file="/netmarkets/jsp/components/beginWizard.jspf"
%><%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>


<%
   String docType = commandBean.getTextParameter("doc_type");
%>
<input type="hidden" id="doc_type" name="doc_type" value="<%=docType%>"/>

<jca:initializeItem operation="${createBean.create}" objectHandle="task" baseTypeName="wt.change2.WTChangeActivity2" attributePopulatorClass="com.ptc.windchill.enterprise.change2.forms.populators.ChangeTaskAttributePopulator"/>

<%@include file="/netmarkets/jsp/change/changeWizardConfig.jspf" %>
<%@include file="/netmarkets/jsp/annotation/wizardConfig.jspf" %>

<cwiz:initializeChangeWizard changeMode="CREATE" annotationUIContext="change" changeItemClass="wt.change2.ChangeActivityIfc"/>
<cwiz:initializeDefaultChangeTaskType />
<security:getSecurityLabelsFromWizard clientData="${commandBean}"/>

<jca:wizard helpSelectorKey="change_createChangeTask" buttonList="DefaultWizardButtonsNoApply" formProcessorController="com.ptc.windchill.enterprise.change2.forms.controllers.EffectivityAwareIframeFormProcessorController" wizardSelectedOnly="true">
   <jca:wizardStep objectHandle="task" action="defineItemAttributesWizStep" type="object"/>
   <jca:wizardStep objectHandle="task" action="securityLabelStep" type="securityLabels"/>
   <jca:wizardStep objectHandle="task" action="affectedAndResultingItemsStep" type="yjchange" />
   <jca:wizardStep objectHandle="task" action="associatedChangeIssuesStep" type="changeTask" />
</jca:wizard>

<script language='Javascript'>
    PTC.wizardIframes.historyManager.readonlyDriverAttributeURL = "${mvc:getComponentURL('change.wizardPropertyPanel')}";
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>