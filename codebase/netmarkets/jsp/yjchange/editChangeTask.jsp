<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"
%><%@ taglib prefix="cwiz" uri="http://www.ptc.com/windchill/taglib/changeWizards"
%><%@taglib uri="http://www.ptc.com/windchill/taglib/effectivity"  prefix="eff"
%><%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>

<%@include file="/netmarkets/jsp/components/includeWizBean.jspf"%>

<jca:initializeItem operation="${createBean.edit}"/>

<%@include file="/netmarkets/jsp/change/changeWizardConfig.jspf" %>
<%@include file="/netmarkets/jsp/annotation/wizardConfig.jspf" %>

<cwiz:initializeChangeWizard changeMode="EDIT" annotationUIContext="change" changeItemClass="wt.change2.ChangeActivityIfc"/>

<eff:copyEffectivityLists />

<jca:wizard helpSelectorKey="change_editChangeTask" buttonList="DefaultWizardButtonsNoApply" formProcessorController="com.ptc.windchill.enterprise.change2.forms.controllers.EffectivityAwareIframeFormProcessorController">
	<%-->Create Change Task<--%>
	<jca:wizardStep action="editAttributesWizStep" type="object" />
	 <jca:wizardStep objectHandle="task" action="affectedAndResultingItemsStep" type="yjchange" />
    <jca:wizardStep action="associatedChangeIssuesStep" type="changeTask" />
</jca:wizard>

<cwiz:initializeConcurrentUpdateSupport callbackFunction="validateCount" />

<script language='Javascript'>
    PTC.onReady(changeTaskLoadHandler.edit);
    PTC.wizard.fireOnAfterAction = PTC.wizard.fireOnAfterAction.wrap(PTC.change.handleFormResult);
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>