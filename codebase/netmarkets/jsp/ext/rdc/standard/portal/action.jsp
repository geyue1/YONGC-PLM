<%@page import="ext.rdc.standard.portal.util.JspUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="ext.rdc.standard.portal.constant.StringConstant"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.session.SessionServerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String action = StringUtils.defaultString(request.getParameter("action"));
	String result = "";
	if(StringConstant.JspAction.GET_MYTASK_DATA.equalsIgnoreCase(action)){
		result = JspUtil.getMyTaskJSONString();
	}else if(StringConstant.JspAction.GET_NOTICE_DATA.equalsIgnoreCase(action)){
		result = JspUtil.getNoticeJSONString();
	}else if(StringConstant.JspAction.GET_DOWNLOAD_DATA.equalsIgnoreCase(action)){
		result = JspUtil.getDownloadJSONString();
	}else if(StringConstant.JspAction.GET_NOTICE_DATA.equalsIgnoreCase(action)){
		result = JspUtil.getNoticeJSONString();
	}else if(StringConstant.JspAction.GET_RESOURCES.equalsIgnoreCase(action)){
		result = JspUtil.getResourceJSONString();
	}else if(StringConstant.JspAction.GET_MORE_NOTICE.equalsIgnoreCase(action)){
		result = JspUtil.getMoreNoticeJSONString();
	}else if(StringConstant.JspAction.GET_MORE_DOWNLOAD.equalsIgnoreCase(action)){
		result = JspUtil.getMoreDownloadJSONString();
	}else if(StringConstant.JspAction.SEARCH_PART.equalsIgnoreCase(action)){
		result = JspUtil.getSeartPartJSONString(StringUtils.defaultString(request.getParameter("searchInput")));
	}else if(StringConstant.JspAction.CREATE_APPLY.equalsIgnoreCase(action)){
		result = JspUtil.getCreateApplyJSONString();
	}else if(StringConstant.JspAction.REPORT_DATA.equalsIgnoreCase(action)){
		result = JspUtil.getReportJSONString();
	}else if(StringConstant.JspAction.SELECTION_MODEL_DATA.equalsIgnoreCase(action)){
		result = JspUtil.getSelectionModelJSONString();
	}
%>    
	    <%=result%>
