<%@page import="wt.doc.WTDocument"%>
<%@page import="com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="ext.yongc.plm.constants.TypeConstants"%>
<%@taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"%>
<%@taglib prefix="pwiz" uri="http://www.ptc.com/windchill/taglib/promotionRequestWizards"%>
<%@include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@page pageEncoding="UTF-8" %>
<%@include file="/netmarkets/jsp/components/includeWizBean.jspf"%>
<%@page import="com.ptc.windchill.enterprise.maturity.PromotionRequestHelper"%>
<script language='JavaScript' type="text/javascript" src='netmarkets/javascript/promotionRequest/promotionRequest.js' ></script>
<script language='JavaScript' type="text/javascript" src='netmarkets/javascript/wizardParticipant/participants.js' ></script>
<script language='JavaScript' type="text/javascript" src='netmarkets/javascript/promotionRequest/promotionParticipants.js' ></script>

<%
     String promotionType = TypeConstants.MB1DOCPromotionNotice;
     String baseName = "WCTYPE|wt.maturity.PromotionNotice|"+promotionType;
     
     String oid = request.getParameter("oid");
     ReferenceFactory rf = new ReferenceFactory();
     WTDocument doc = (WTDocument)rf.getReference(oid).getObject();
     String doc_type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
     System.out.println("doc_type------->"+doc_type);
%>
<input type="hidden" id="doc_type" name="doc_type" value="<%=doc_type%>"/>
<input type="hidden" id="promotionType" name="promotionType" value="<%=promotionType%>"/>

<jca:initializeItem operation="${createBean.create}" attributePopulatorClass="com.ptc.windchill.enterprise.maturity.forms.populators.PromotionNameAttributePopulator" baseTypeName="<%=baseName%>"/>

<jsp:setProperty name="createBean" property="contextPickerTypeComponentId" value="<%=PromotionRequestHelper.getPromotablePickerContainerTypeId()%>"/>

<pwiz:initializePromoteWizard />

<script type="text/javascript">
function prvalidate(){
	var first_name = document.getElementById("first_name").value;
	var second_name = document.getElementById("second_name").value;
	var third_name = document.getElementById("third_name").value;
	var workType = document.getElementById("workType").value;
	if(second_name==null ||second_name==""){
		alert("名称中间字段为产品型号，需要手动输入，请输入产品型号");
		return false;
	}
	
	var input=window.document.getElementsByTagName('input');
	for(var i=0;i<input.length;i++){   
	    if(input[i].type =='text' && input[i].name.indexOf('___name_col_name___textbox')>0){ 
				input[i].value=first_name+workType+"-"+second_name+"-"+third_name;
		       return true;
	    }
	}
	return true;
}
</script>
<jca:wizard helpSelectorKey="maturity_createPromotionRequest" buttonList="DefaultWizardButtonsNoApply" wizardSelectedOnly="true">
   <jca:wizardStep action="setContextWizStep" type="object"/>
    <jca:wizardStep action="yjdefineItemAttributesWizStep" type="yjchange"/> 
    <jca:wizardStep action="collectReviewDocStep" type="yjpromotion" />
</jca:wizard>
<%@ include file="/netmarkets/jsp/yjpromotion/nameRule.jsp"%>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>