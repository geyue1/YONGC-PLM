<%@page pageEncoding="UTF-8" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="wctags"%>
<%@include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>

<!--  
<wctags:contextPicker id="contextPicker" label="MyContextPicker" pickerTitle="ContextPicker" /> 

<input name="selectedFolderFromFolderContext" value="wt.folder.SubFolder:230201" type="hidden">
<input id="LocationPicker" name="tcomp$attributesTable$OR:wt.folder.Cabinet:76336$___Location___textbox" oncontextmenu="return false;" style="color:#9d9da1;" onkeydown="if(event.keyCode==Event.KEY_TAB){return true;}else{return false;}" value="/产品测试"  class="required location-picker " size="25/" type="text">
<a id="newlocation_loc_img" href="javascript:launchFolderPicker ('http://plm.yongc.com/Windchill/servlet/WindchillAuthGW/wt.enterprise.URLProcessor/invokeAction?action=cadxBrowseLocations&amp;oid=wt.pdmlink.PDMLinkProduct:76298&amp;containerVisibilityMask=OR:wt.pdmlink.PDMLinkProduct:76298&amp;accessPermission=modify&amp;displayHotlinks=false&amp;displayCreateFolder=false',document.getElementsByName('tcomp$attributesTable$OR:wt.folder.Cabinet:76336$___Location___textbox')[0],'selectedFolderFromFolderContext')">
<img title="为此对象设置新的文件夹位置。" alt="为此对象设置新的文件夹位置。" src="com/ptc/core/htmlcomp/images/location_set.gif" border="0" align="middle">
</a>
</input>
-->
	<wctags:itemPicker id="addPartItemPicker" 
                     pickerTitle="查找部件"
                     pickerCallback="selectCallback"
                     inline="true"
                     objectType="WCTYPE|wt.part.WTPart"
                     componentId="addPartItemPicker"
                     defaultVersionValue="LATEST"
                     defaultIterationValue="LATEST"
                     baseWhereClause="(state.state='RELEASED')"
                     pickedAttributes="*"
                     multiSelect="true"
                   
                     />



<%@include file="/netmarkets/jsp/util/end.jspf" %>
