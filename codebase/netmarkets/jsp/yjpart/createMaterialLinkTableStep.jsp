<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.part.WTPart"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%> 
<%@page import="ext.yongc.plm.constants.*"%>
<%@page pageEncoding="UTF-8" %>

<%
String baseHref = new wt.httpgw.URLFactory().getBaseHREF();
WTPart part =(WTPart) commandBean.getActionOid().getRefObject();
System.out.println("part--------->"+part.getName());
ReferenceFactory rf = new ReferenceFactory();
String oid = rf.getReferenceString(part);
%>

<table class="attributePanel-group-panel"><tbody>
 <tr/><tr/>
  <tr>
  		<td class="attributePanel-asterisk">*</td><td class="attributePanel-label">原材料编码:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="materialPartNumber" type="text" name="materialPartNumber" value="" size="60" maxlength="160" readOnly="true">
  		<input type="button" id="searchMaterialButton" value="查找原材料" onclick="searchMaterialPart()"/>
  		</td>
  </tr>
  <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">数量:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="SHULIANG" type="text" name="SHULIANG" value="" size="60" maxlength="160" ></td>
  </tr>
  
  <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">领料单位代码:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="LLDWDM" type="text" name="LLDWDM" value="" size="60" maxlength="160" ></td>
  </tr>
  <!-- 
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">领料单位名称:</td>
  		<td class="attributePanel-value" colspan="1">
  		  	<w:dateInputComponent name="lastDate" required="false" dateValueType="DATE_ONLY"/>
  			<input type="hidden" name="lastDate" id="lastDate" size="30" >
  		</td>
  </tr>
   -->
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">领料单位名称::</td>
  		<td class="attributePanel-value" colspan="1"> <input id="LLDWMC" type="text" name="LLDWMC" value="" size="60" maxlength="160" ></td>
  </tr>
 
  <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">工厂:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="GONGC" type="text" name="GONGC" value="" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">基本计量单位:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="JBJLDW" type="text" name="JBJLDW" value="" size="60" maxlength="160" ></td>
  </tr>
   
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">母件数量:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="MJSL" type="text" name="MJSL" value="" size="60" maxlength="160" ></td>
  </tr>
  
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">工艺路线:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="GYLX" type="text" name="GYLX" value="" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">数量单位:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="SLDW" type="text" name="SLDW" value="" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">版本号:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="BANBENHAO" type="text" name="BANBENHAO" value="" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">附注:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="FUZHU" type="textarea" name="FUZHU" value="" size="60" maxlength="160" ></td>
  </tr>
  <!--  
   <tr>
  		<td class="attributePanel-asterisk">*</td><td class="attributePanel-label">状态:</td>
  		<td class="attributePanel-value" colspan="1"> <select name="state" id="state">
                   <option value="Noapplicable">Noapplicable</option>
         </select></td>
  </tr>
  -->
  </tbody></table>

<script>
   function searchMaterialPart(){
	   var url = "<%=request.getContextPath()%>/netmarkets/jsp/yjpart/addMaterialPart.jsp";
  	    window.open(url,"Search", "width=800,height=600,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,location=no,status=yes");
   }
   function selectMaterialPartCallback(objects){
	  
	   var pickedObjects = objects.pickedObject;
	   if(pickedObjects.length==1){
		   document.getElementById("materialPartNumber").value=pickedObjects[0]["name"];
	   }
	   var number=document.getElementById("materialPartNumber").value;
		var myAjax = new Ajax.Request(
		        "<%=baseHref%>netmarkets/jsp/yjpart/materialPartNumberCheck.jsp",
		        {
		            method:"post",       //表单提交方式 
		            parameters:"number="+number+"&oid=<%=oid%>",   //提交的表单数据
		            setRequestHeader:{"If-Modified-Since":"0"},     //禁止读取缓存数据
		            onComplete:function(x){    //提交成功回调 
		              if(x.responseText!=null && x.responseText.indexOf("fail")>-1){
		            	  var btn=document.getElementById("searchMaterialButton");
		          		if(btn){
		          				var ext=document.createElement("p");
		          				ext.name="btn_extType";
		          				ext.id="btn_extType";
		          				ext.innerHTML="该原材料链接已存在,不能重复创建.";
		          				ext.style.color='red';
		          				if(btn.parentNode){
		          					if(btn.nextSibling){  
		          						btn.parentNode.insertBefore(ext,btn.nextSibling);
		          					}else{  
		          						btn.parentNode.appendChild(ext);
		          					} 
		          				}
		          			
		          		}
		            	  flag = false;
		            	  return false;
					   }else{
						   var btn=document.getElementById("btn_extType");
						   if(btn){
							   flag = true;
							   btn.innerHTML="";
						   }
					   }
		            },
		            onError:function(x){          //提交失败回调
		                    alert(x.statusText);
		            } 
		        } 
		   	); 
	   
   }
   function remove(){
	   var tableId = "PartMaterialLinkTableBuilder.table";
		var table = tableUtils.getTable(tableId);
		var selections=table.getSelectionModel().getSelections();
		   if(selections==null || selections.length==0){
	        	 alert("请选择数据！");
	        	 return false;
	         }
		var selectedOids = getSelectedOidString(tableId);
		var oids = [];
		oids.push( selectedOids );
		PTC.jca.table.Utils.removeRows(tableId,oids);
   }
   function displayProp(obj){   
	   var names="";    
	   for(var name in obj){    
	     names+=name+": "+obj[name]+", ";  
	   }  
	   alert(names);  
	 } 
   function processCreateHiddenInput(id){
	   var v = document.getElementById(id);
	   var aBtn=document.createElement("input");
		aBtn.type="hidden";
		aBtn.value=v.value;
		aBtn.name=id;
		aBtn.id= id;
		v.parentNode.appendChild(aBtn);
   }
   function createHiddenInput(table){
	   var selections=table.getSelectionModel().getSelections();
		for(var i=0;i<selections.length;i++){
			var number = selections[i].get("roleBNumber");
			
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.SHULIANG%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.LLDWDM%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.LLDWMC%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.GONGC%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.JBJLDW%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.MJSL%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.GYLX%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.SLDW%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.BANBENHAO%>");
			processCreateHiddenInput(number+"_"+"<%=IBAConstants.FUZHU%>");
		}
   }
   function selectAll(){
	  
		//var table = PTC.jca.table.Utils.getTable('PartMaterialLinkTableBuilder.table'); 
		
		//table.getSelectionModel().selectAll();
		//var selections=table.getSelectionModel().getSelections();
		////if(selections.length==0){
		//	alert("没有数据");
		//	return false;
		//}
		// createHiddenInput(table);
		return true;
	}
  
   function afterClick(formResult){
	  PTC.jca.table.Utils.reload('YJMaterialPartListTableBuilder.table');
   }
   var flag = true;
   PTC.action.on ('objectsAffected',afterClick); 
   setUserSubmitFunction(submitCheck);
   function submitCheck(){
   	
   	if(!flag){
   		alert("数据没有通过验证，不能创建.");
   		return false;
   	}
   }
</script>


<%@ include file="/netmarkets/jsp/util/end.jspf"%>