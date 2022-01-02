<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ page import="wt.util.WTMessage, com.ptc.netmarkets.model.modelResource" %>
<%! private static final String MODEL_RESOURCE = "com.ptc.netmarkets.model.modelResource"; %>


<script type="text/javascript">

</script>
<%
 /**
  * This will add the progress message.
  */
 String message = WTMessage.getLocalizedMessage(MODEL_RESOURCE, modelResource.EXPORT_STATUS, null);
%>
<jca:wizard progressMessage="<%= message %>" buttonList="ExportWizardButtons">
   <jca:wizardStep action="exportTable_step" type="object"/>
</jca:wizard>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>