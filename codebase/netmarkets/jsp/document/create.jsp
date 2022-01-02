<%@ taglib uri="http://www.ptc.com/windchill/taglib/delegates" prefix="delegates"%>
<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>

<%@ include file="/netmarkets/jsp/document/create.jspf"%>
<delegates:loadDelegates/>
<%
String baseHref = new wt.httpgw.URLFactory().getBaseHREF();
%>
<script type="text/javascript">
function checkFile(){
	var fileIDText = document.getElementById('fileID');
	var fileID = fileIDText.value;
	var myAjax = new Ajax.Request(
	        "<%=baseHref%>netmarkets/jsp/yjdoc/checkFileID.jsp",
	        {
	            method:"post",       //表单提交方式 
	            parameters:"fileID="+fileID,   //提交的表单数据
	            setRequestHeader:{"If-Modified-Since":"0"},     //禁止读取缓存数据
	            onComplete:function(x){    //提交成功回调 
	              if(x.responseText!=null && x.responseText.indexOf("fail")>-1){
		          				var p=document.createElement("p");
		          				p.name="btn_extType";
		          				p.id="btn_extType";
		          				p.innerHTML="文件代号在系统中已存在";
		          				p.style.color='red';
		          				if(fileIDText.parentNode){
		          					if(fileIDText.nextSibling){  
		          						fileIDText.parentNode.insertBefore(p,fileIDText.nextSibling);
		          					}else{  
		          						fileIDText.parentNode.appendChild(p);
		          					} 
		          				}
		          				
				   }else{
					  
					   var a = document.getElementById("btn_extType");
					   if(a){
						   a.innerHTML="";
					   }
				   }
	            },
	            onError:function(x){          //提交失败回调
	                    alert(x.statusText);
	            } 
	        } 
	   	); 
}
function checkFileID(){
	//alert('checkFileID');
	var fileID = document.getElementById('fileID');
	if(!fileID){
		setTimeout(checkFileID,500)
	}else{
		fileID.onblur=checkFile;
		return;
	}
}
Ext.onReady(function(){
	checkFileID();
});


</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>
