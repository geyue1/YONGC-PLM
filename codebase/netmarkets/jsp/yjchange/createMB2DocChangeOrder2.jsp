<%@page import="wt.part.WTPart"%>
<%@page import="wt.change2.WTChangeOrder2"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="com.ptc.windchill.enterprise.change2.commands.RelatedChangesQueryCommands"%>
<%@page import="com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="wt.doc.WTDocument"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page pageEncoding="UTF-8" %>
<%@page import="ext.yongc.plm.constants.TypeConstants"%>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" 
%><%@ taglib prefix="cwiz" uri="http://www.ptc.com/windchill/taglib/changeWizards" 
%><%@ taglib uri="http://www.ptc.com/windchill/taglib/attachments" prefix="attachments"
%><%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>

<%@include file="/netmarkets/jsp/components/includeWizBean.jspf"%> 
<%@include file="/netmarkets/jsp/change/propagationConfiguration.jspf"%>

<%
     String changeType = TypeConstants.ECN_MB2;
     String baseName = "WCTYPE|wt.change2.WTChangeOrder2|"+changeType;
     String oid = request.getParameter("oid");
     ReferenceFactory rf = new ReferenceFactory();
     WTDocument doc = (WTDocument)rf.getReference(oid).getObject();
     String doc_type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
     System.out.println("doc_type------->"+doc_type);
 boolean flag = false;
     String number = "";
    QueryResult qr= RelatedChangesQueryCommands.getRelatedAffectingChangeNotices(doc);
   
    while(qr.hasMoreElements()){
    	WTChangeOrder2 ecn = (WTChangeOrder2)qr.nextElement();
    	String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
    	String state = ecn.getState().toString();
    	if(type.indexOf(changeType)>-1 && !state.equalsIgnoreCase("CANCELLED")){
    		flag = true;
    		number = ecn.getNumber();
    		break;
    	}
    }
   
    if(flag){
    	%>
    	<script >
    	   var n = '<%=number%>';
           alert("该文件已经存在正在走流程的变更单:"+n+",不能继续创建新的变更单！");
           window.close();
       </script>
    	
    	<%
    }
%>
<input type="hidden" id="changeType" name="changeType" value="<%=changeType%>"/>
<input type="hidden" id="doc_type" name="doc_type" value="<%=doc_type%>"/>
<jca:initializeItem operation="${createBean.create}" attributePopulatorClass="com.ptc.windchill.enterprise.change2.forms.populators.ChangeNoticeAttributePopulator" baseTypeName="<%=baseName%>"/>

<%@include file="/netmarkets/jsp/change/changeWizardConfig.jspf"%>
<%@include file="/netmarkets/jsp/attachments/initAttachments.jspf"%>


<cwiz:initializeChangeWizard changeMode="CREATE" annotationUIContext="change" changeItemClass="wt.change2.ChangeOrderIfc" />
<cwiz:initializeSelectedItems />

<SCRIPT LANGUAGE="JavaScript">
	var storeIframes = true;
	var iframeTableId = "changeNotice.wizardImplementationPlan.table"; 
	var changeNotice = true;
	PTC.wizardIframes.initStoreIframes();
	
	function prvalidate(){
		var first_name = document.getElementById("first_name").value;
		var second_name = document.getElementById("second_name").value;
		var third_name = document.getElementById("third_name").value;
		if(second_name==null ||second_name==""){
			alert("名称中间字段为产品型号，需要手动输入，请输入产品型号");
			return false;
		}
		
		var input=window.document.getElementsByTagName('input');
		for(var i=0;i<input.length;i++){   
		    if(input[i].type =='text' && input[i].name.indexOf('___name_col_name___textbox')>0){ 
					input[i].value=first_name+"-"+second_name+"-"+third_name;
			       return true;
		    }
		}
		return true;
	}
</SCRIPT>

<jca:wizard helpSelectorKey="change_createChangeNotice" buttonList="DefaultWizardButtonsWithSubmitPrompt" formProcessorController="com.ptc.windchill.enterprise.change2.forms.controllers.ChangeTaskTemplatedFormProcessorController" wizardSelectedOnly="true">
	<%-->Create Change Notice<--%>
 	<jca:wizardStep action="setChangeContextWizStep" type="change"/>	
	<jca:wizardStep action="yjdefineItemAttributesWizStep" type="yjchange"/>
	<jca:wizardStep action="securityLabelStep" type="securityLabels"/>
	
	<jca:wizardStep action="create_wizardImplementationPlanStep" type="yjchange" />
	<jca:wizardStep action="attachments_step" type="yjchange" />
	<jca:wizardStep action="associatedChangeRequestsStep" type="changeNotice" />
</jca:wizard>

<attachments:fileSelectionAndUploadApplet/>
<%@ include file="/netmarkets/jsp/yjchange/nameRule.jsp"%>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>