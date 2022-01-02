<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"%>
<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<jca:describeTableTree var="treeDescriptor" id="promotionRequest.promotionItems.table" label="MBOM" nodeColumn="number" expansion="full">
	<jca:describeColumn id="statusFamily_Share"/>
	<jca:describeColumn id="statusFamily_Change"/>
	<jca:describeColumn id="statusFamily_General"/>
	<jca:describeColumn id="extFlag" dataUtilityId="PromotionTaskObjectsDataUtility" label=""/>
	<jca:describeColumn id="type_icon"/>		
	<jca:describeColumn id="number"/>
	<jca:describeColumn id="version"/>
	<jca:describeColumn id="infoPageAction"/>
	<jca:describeColumn id="name"/>
	<jca:describeColumn id="nmActions" >
	 <jca:setComponentProperty key="actionModel" value="MBOM.mvcTable.rowAction"/>
    </jca:describeColumn>
     <jca:describeColumn id="gongyi_type" label="工艺路线"/>
    <jca:describeColumn id="CNAME"/>
    <jca:describeColumn id="DRAWINGNO"/>
    <jca:describeColumn id="MATERIAL"/>
	<jca:describeColumn id="state"/>
	<jca:describeColumn id="thePersistInfo.modifyStamp"/>
	<jca:describeColumn id="modifier.name"/>
	<jca:describeColumn id="creator"/>
	
</jca:describeTableTree>

<jca:getModel var="treeModel" descriptor="${treeDescriptor}" treeHandler="MBOMPromotionListTreeHandler"/>

<jca:renderTableTree model="${treeModel}" showCount="true"/>


<%@ include file="/netmarkets/jsp/util/end.jspf"%>