<%@page import="ext.yongc.plm.report.JsonParser"%>
<%@page import="ext.yongc.plm.util.StringUtil"%>
<%@page import="ext.yongc.plm.report.ReportUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="ext.yongc.plm.part.rule.QitaoxingUtil"%>
<%
  String objNumber = request.getParameter("objNumberInput");
System.out.println("checkQitaoxing objNumber------->"+objNumber);
Map<String,String> formData = QitaoxingUtil.getFormData(request);
List<String> validationResult = QitaoxingUtil.validateFormData(formData);
if(validationResult!=null && validationResult.size()>0){
	String validationResultJSON  = ReportUtil.toJson4Msg(validationResult);
	System.out.println("checkQitaoxing validationResultJSON------->"+validationResultJSON);
	response.getWriter().print(StringUtil.replace(validationResultJSON, "\\n", "<br/>"));
}else{
	List<Map<String,String>> list =QitaoxingUtil.processCheck(formData);
	String msg = JsonParser.toJson(list);
	response.getWriter().print(StringUtil.replace(msg, "\\n", "<br/>"));
}

%>