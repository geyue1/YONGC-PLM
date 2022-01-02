<%@page import="wt.session.SessionServerHelper"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="ext.yongc.plm.constants.IBAConstants"%>
<%@page import="ext.yongc.plm.util.IBAUtil"%>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%> 
<%@page pageEncoding="UTF-8" %>
<%@page import="ext.yongc.plm.part.link.PartMaterialLink"%>
<%@page import="com.ptc.netmarkets.model.NmOid"%>
<%@page import="java.util.ArrayList"%>
<%
boolean force = SessionServerHelper.manager.setAccessEnforced(false);
ArrayList list=commandBean.getSelectedOidForPopup();
if(list==null || list.size()==0){
	%>
	 <script>
	 alert("请选择数据");
	 window.close();
	 </script>
	<%
}
NmOid nmoid=(NmOid)list.get(0);
PartMaterialLink link =(PartMaterialLink)nmoid.getRefObject();
String number = link.getRoleBnumber();
IBAUtil iba = new IBAUtil(link);
String shuliang = iba.getIBAValue(IBAConstants.SHULIANG);
String LLDWDM = iba.getIBAValue(IBAConstants.LLDWDM);
String LLDWMC = iba.getIBAValue(IBAConstants.LLDWMC);
String GONGC = iba.getIBAValue(IBAConstants.GONGC);
String JBJLDW = iba.getIBAValue(IBAConstants.JBJLDW);
String MJSL = iba.getIBAValue(IBAConstants.MJSL);
String GYLX = iba.getIBAValue(IBAConstants.GYLX);
String SLDW = iba.getIBAValue(IBAConstants.SLDW);
String BANBENHAO = iba.getIBAValue(IBAConstants.BANBENHAO);
String FUZHU = iba.getIBAValue(IBAConstants.FUZHU);

ReferenceFactory rf = new ReferenceFactory();
String oid = rf.getReferenceString(link);
SessionServerHelper.manager.setAccessEnforced(force);
%>
<input type="hidden" id="linkoid" name = "linkoid" value="<%=oid%>"/>
<table class="attributePanel-group-panel"><tbody>
 <tr/><tr/>
  <tr>
  		<td class="attributePanel-asterisk">*</td><td class="attributePanel-label">原材料编码:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="materialPartNumber" type="text" name="materialPartNumber" value="<%=number %>" size="60" maxlength="160" readOnly="true">
  		
  		</td>
  </tr>
  <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">数量:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="SHULIANG" type="text" name="SHULIANG" value="<%=shuliang %>" size="60" maxlength="160" ></td>
  </tr>
  
  <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">领料单位代码:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="LLDWDM" type="text" name="LLDWDM" value="<%=LLDWDM %>" size="60" maxlength="160" ></td>
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
  		<td class="attributePanel-value" colspan="1"> <input id="LLDWMC" type="text" name="LLDWMC" value="<%=LLDWMC %>" size="60" maxlength="160" ></td>
  </tr>
 
  <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">工厂:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="GONGC" type="text" name="GONGC" value="<%=GONGC %>" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">基本计量单位:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="JBJLDW" type="text" name="JBJLDW" value="<%=JBJLDW %>" size="60" maxlength="160" ></td>
  </tr>
   
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">母件数量:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="MJSL" type="text" name="MJSL" value="<%=MJSL %>" size="60" maxlength="160" ></td>
  </tr>
  
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">工艺路线:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="GYLX" type="text" name="GYLX" value="<%=GYLX %>" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">数量单位:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="SLDW" type="text" name="SLDW" value="<%=SLDW %>" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">版本号:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="BANBENHAO" type="text" name="BANBENHAO" value="<%=BANBENHAO %>" size="60" maxlength="160" ></td>
  </tr>
   <tr>
  		<td class="attributePanel-asterisk"></td><td class="attributePanel-label">附注:</td>
  		<td class="attributePanel-value" colspan="1"> <input id="FUZHU" type="textarea" name="FUZHU" value="<%=FUZHU %>" size="60" maxlength="160" ></td>
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
  
  <%@ include file="/netmarkets/jsp/util/end.jspf"%>