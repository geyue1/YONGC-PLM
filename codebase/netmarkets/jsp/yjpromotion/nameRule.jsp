<%@page import="ext.yongc.plm.constants.TypeConstants"%>
<%@page pageEncoding="UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.ptc.core.components.rendering.guicomponents.TextBox"%>
<%@page import="com.ptc.core.components.rendering.guicomponents.Label"%>
<%@page import="ext.yongc.plm.constants.NameRuleConstants"%>
<%@page import="com.ptc.core.components.rendering.guicomponents.GUIComponentArray"%>
<%

StringBuffer sb = new StringBuffer();
sb.append("<select id=first_name name=first_name>");

for(int i=0;i<NameRuleConstants.DEP_NO.length;i++){
	String s = NameRuleConstants.DEP_NO[i];
	String s2= NameRuleConstants.DEP_NO_zh_CN[i];
	sb.append("<option value="+s+">"+s2+"</option>");
}

sb.append("</select>");
String workType = "";
if(TypeConstants.THREEDDesignPromotionNotice.equals(promotionType)){
	workType = "CPTZ";
}else if(TypeConstants.MBOMPromotionNotice.equals(promotionType)){
	workType = "JGWH";
}else if(TypeConstants.BASEDESIGNDOCPromotionNotice.equals(promotionType)){
	workType = "SJWJ";
}else if(TypeConstants.GYDOCPromotionNotice.equals(promotionType)){
	workType = "GYWJ";
}else if(TypeConstants.GZDOCPromotionNotice.equals(promotionType)){
	workType = "GZWJ";
}
sb.append(workType);
sb.append(" - ");
sb.append("<input type=text id=second_name name=second_name onBlur=getSecondName()></input>");
sb.append(" - ");
//DateFormat df = new SimpleDateFormat("yyyy");

String third_name = "";
sb.append("<input type=text id=third_name name=third_name readonly=true value="+third_name+"></input>");
%>
<input type="hidden" id="workType" name="workType" value="<%=workType%>"></input>
<script>
//js 字符串去左右空格和回车
function iGetInnerText(testStr) {
    var resultStr = testStr.replace(/\ +/g, ""); //去掉空格
    resultStr = testStr.replace(/[ ]/g, "");    //去掉空格
    resultStr = testStr.replace(/[\r\n]/g, ""); //去掉回车换行
    return resultStr;
}
function setNameInputFormat(){
	var checkTbl = document.getElementById('NameInputFormat_DIV');
	if(checkTbl){
		return;
	}
	var input=window.document.getElementsByTagName('input');
	var htmltext = "<%=sb.toString()%>";
	for(var i=0;i<input.length;i++){   
	    if(input[i].type =='text' && input[i].name.indexOf('___name_col_name___textbox')>0){ 
				var obj=input[i];
				obj.readOnly=true;
				obj.className = "hiddenInput";
				obj.style.display="none";
				obj.parentNode.innerHTML += "<div id=\"NameInputFormat_DIV\" class=\"frame_outer\">"+htmltext+"</div>";
		       return;
	    }
	}
		
}		
function getSecondName(){
	var name = document.getElementById("second_name").value;
	Ext.Ajax.request({
		url : getBaseHref()+"netmarkets/jsp/yjpromotion/getCount.jsp",
		params : {"name":name},
		success:function(x){
			  if(x.responseText!=null){
				  var btn=document.getElementById("third_name");
	          		if(btn){
	          			var third_name = "<%=third_name%>";
	          			btn.value=third_name+iGetInnerText(x.responseText);
	          		}
			  }
		}
    });
}
function afterRefresh(){
	  setInterval(setNameInputFormat, 1000);
}

afterRefresh();
</script>