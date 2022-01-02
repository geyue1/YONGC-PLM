<%@page import="com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper"%>
<%@page import="wt.doc.WTDocument"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page pageEncoding="UTF-8" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="wctags"%>
<%@include file="/netmarkets/jsp/components/beginWizard.jspf"%>

<%
String oid = request.getParameter("oid");
ReferenceFactory rf = new ReferenceFactory();
WTDocument doc = (WTDocument)rf.getReference(oid).getObject();
String doc_type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
doc_type = "WCTYPE|"+doc_type;
System.out.println("doc_type------->"+doc_type);
%>
<wctags:itemPicker id="addDocItemPicker" 
                     pickerTitle="查找文件"
                     pickerCallback="select3DDocCallback"
                     inline="true"
                     objectType="<%=doc_type %>"
                     componentId="addDocItemPicker"
                     defaultVersionValue="LATEST"
                     defaultIterationValue="LATEST"
                     baseWhereClause="(state.state='INWORK')"
                     pickedAttributes="*"
                     multiSelect="true"
                    searchResultsViewId="YJSearchDocTableView"
                     />

<%@include file="/netmarkets/jsp/util/end.jspf" %>
  <!--  
                     baseWhereClause="(state.state='INWORK')"
                     -->