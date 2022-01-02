<%@page pageEncoding="UTF-8" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="wctags"%>
<%@include file="/netmarkets/jsp/components/beginWizard.jspf"%>

<wctags:itemPicker id="add3DDocItemPicker" 
                     pickerTitle="查找原材料"
                     pickerCallback="selectMaterialPartCallback"
                     inline="true"
                     objectType="WCTYPE|wt.part.WTPart"
                     componentId="addPartItemPicker"
                     defaultVersionValue="LATEST"
                     defaultIterationValue="LATEST"
                     baseWhereClause="(state.state='RELEASED')"
                     pickedAttributes="*"
                     multiSelect="false"
                     
                     />

<%@include file="/netmarkets/jsp/util/end.jspf" %>
  <!--  
                     baseWhereClause="(state.state='INWORK')"
                     showTypePicker="false"
                     -->