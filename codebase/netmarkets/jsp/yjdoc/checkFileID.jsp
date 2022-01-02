<%@page import="wt.doc.WTDocument"%>
<%@page import="java.util.List"%>
<%@page import="ext.yongc.plm.util.StringUtil"%>
<%@page import="ext.yongc.plm.constants.IBAConstants"%>
<%@page import="ext.yongc.plm.util.DocUtil"%>
<%
    String fileID = request.getParameter("fileID");
System.out.println("fileID--------->"+fileID);
if(StringUtil.isNotEmpty(fileID)){
	List<WTDocument> list = DocUtil.getDocumentByTypeAndIBA(null, IBAConstants.FILEID, fileID);
	if(list!=null && list.size()>0){
		 response.getWriter().print("fail");
	}
}

    
%>