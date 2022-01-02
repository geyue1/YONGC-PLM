<%@page import="ext.rdc.standard.util.SearchUtil"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="com.ptc.core.lwc.common.view.PropertyHolderHelper"%>
<%@page import="com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper"%>
<%@page import="com.ptc.core.lwc.common.view.TypeDefinitionReadView"%>
<%@page import="com.ptc.core.lwc.server.LWCStructEnumAttTemplate"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%
String oidStr =(String) request.getParameter("oidStr");

ReferenceFactory rf = new ReferenceFactory();
StringBuffer result = new StringBuffer();
if(oidStr!=null && !"".equals(oidStr.trim())){
	  String[] array = oidStr.split(",");
	      for(String oid:array){
	    	  if(oid==null || oid.trim().length()==0){
	    		  continue;
	    	  }
	    	  LWCStructEnumAttTemplate lwc = (LWCStructEnumAttTemplate)rf.getReference(oid).getObject();
	    	  if(lwc==null){
	    		  continue;
	    	  }
	    	  String name = SearchUtil.getClassNodeDisplayName(lwc,true);
	    	  result.append(name+";");
	      }
	      
	      response.getWriter().print(result.toString());
}

%>