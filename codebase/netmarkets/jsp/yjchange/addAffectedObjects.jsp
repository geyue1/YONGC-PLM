<%@page pageEncoding="UTF-8" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="wctags"%>
<%@include file="/netmarkets/jsp/components/beginWizard.jspf"%>

<%
   String docType = commandBean.getTextParameter("doc_type");
System.out.println(" addAffectedObjects.jsp------docType-------------->"+docType);
docType = "WCTYPE|"+docType;
if(docType.indexOf("wt.epm.EPMDocument")>-1){
	%>
	<wctags:itemPicker id="add3DDocItemPicker" 
                     pickerTitle="查找受影响对象"
                     pickerCallback="selectAffectedCallback"
                     inline="true"
                     objectType="<%=docType%>"
                     componentId="add3DDocItemPicker"
                     defaultVersionValue="LATEST"
                     defaultIterationValue="LATEST"
                     baseWhereClause="(state.state='RELEASED')"
                     pickedAttributes="*"
                     multiSelect="true"
                   
                     />
	<%
}else if(docType.indexOf("wt.doc.WTDocument")>-1){
	System.out.println("wtdocument");
	%>
	<wctags:itemPicker id="addDocItemPicker" 
                     pickerTitle="查找受影响对象"
                     pickerCallback="selectAffectedCallback"
                     inline="true"
                     objectType="<%=docType %>"
                     componentId="addDocItemPicker"
                     defaultVersionValue="LATEST"
                     defaultIterationValue="LATEST"
                     baseWhereClause="(state.state='RELEASED')"
                     pickedAttributes="*"
                     multiSelect="true"
                    searchResultsViewId="YJSearchDocTableView"
                     />
	<%
}else if(docType.indexOf("wt.part.WTPart")>-1){
	%>
	<wctags:itemPicker id="addPartItemPicker" 
                     pickerTitle="查找受影响对象"
                     pickerCallback="selectAffectedCallback"
                     inline="true"
                     objectType="<%=docType %>"
                     componentId="addPartItemPicker"
                     defaultVersionValue="LATEST"
                     defaultIterationValue="LATEST"
                     baseWhereClause="(state.state='RELEASED')"
                     pickedAttributes="*"
                     multiSelect="true"
                   
                     />
	<%
}
%>


<%@include file="/netmarkets/jsp/util/end.jspf" %>
