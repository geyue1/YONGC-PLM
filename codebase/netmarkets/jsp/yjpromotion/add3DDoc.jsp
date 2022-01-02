<%@page pageEncoding="UTF-8" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="wctags"%>
<%@include file="/netmarkets/jsp/components/beginWizard.jspf"%>

<wctags:itemPicker id="add3DDocItemPicker" 
                     pickerTitle="查找三维文件"
                     pickerCallback="select3DDocCallback"
                     inline="true"
                     objectType="WCTYPE|wt.epm.EPMDocument|com.yongc.DefaultEPMDocument|com.yongc.DesignEPMDoc"
                     componentId="add3DDocItemPicker"
                     defaultVersionValue="LATEST"
                     defaultIterationValue="LATEST"
                     baseWhereClause="(state.state='INWORK')"
                     pickedAttributes="*"
                     multiSelect="true"
                     
                     />

<%@include file="/netmarkets/jsp/util/end.jspf" %>
  <!--  
                     baseWhereClause="(state.state='INWORK')"
                     showTypePicker="false"
                     -->