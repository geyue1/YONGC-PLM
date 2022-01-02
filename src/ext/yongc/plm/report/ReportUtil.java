/**
 * ext.yongc.plm.report.ReportUtil.java
 * @Author yge
 * 2017年11月13日下午2:23:25
 * Comment 
 */
package ext.yongc.plm.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;












import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.uwgm.common.container.OrganizationHelper;

import wt.epm.EPMDocument;
import wt.fc.ReferenceFactory;
import wt.inf.library.WTLibrary;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;


public class ReportUtil {
	
   private static Logger log = LogR.getLogger(ReportUtil.class.getName());
   private static ReferenceFactory rf  = new ReferenceFactory();
   
	public static void  downloadTuyangmulu(HttpServletResponse response, Map<String, String> formDataMap) throws WTException{
		 new TuyangmuluReportHelper().downloadReport(response, formDataMap);
	}
	public static void  downloadBOMReport(HttpServletResponse response, Map<String, String> formDataMap) throws WTException{
		 new BOMReportHelper().downloadReport(response, formDataMap);
	}
	public static void downloadWenjianmulu(HttpServletResponse response, Map<String, String> formDataMap)throws WTException{
		new WenjianmuluReportHelper().downloadReport(response, formDataMap);
	}
	
	public static List<String> validateFormData(Map<String, String> formData) throws WTException{
		String reportId = formData.get("reportId");
		if("tuyangmuluReport".equalsIgnoreCase(reportId)){
			return new TuyangmuluReportHelper().validateFormData(formData);
		}else if("BOMReport".equalsIgnoreCase(reportId)){
			return new BOMReportHelper().validateFormData(formData);
		}else if("wenjianmuluReport".equalsIgnoreCase(reportId)){
			return new WenjianmuluReportHelper().validateFormData(formData);
		}
		return null;
	}
	 public static Map<String, String> getFormData(HttpServletRequest request) {

		 Map<String, String> formDataMap = new HashMap<String, String>();
		
		
		 String reportId = request.getParameter("reportid");
		 String objType =  request.getParameter("objType");
		 String objNumberInput = request.getParameter("objNumberInput");
		 String oid = request.getParameter("oid");
         formDataMap.put("reportId", reportId);
		 formDataMap.put("objType", objType);
		 formDataMap.put("objNumber", objNumberInput.trim());
		 formDataMap.put("oid", oid);
		 log.debug("formDataMap--------->"+formDataMap);
		 return formDataMap;
    }
	  public static String toJson4Msg(List<String> result) {
	        List<Map<String, String>> list = new ArrayList<>();
	        for (String msg : result) {
	            Map<String, String> messages = new HashMap<>();
	            messages.put("message", "<font color=\"#B22222\">"+msg+"</font>");
	            list.add(messages);
	        }
	        return JsonParser.toJson(list);
	    }
	  
	  public static String getFolderPath(){
		  
		  StringBuffer sb = new StringBuffer();
		  sb.append("<input name=\"selectedFolderFromFolderContext\" value=\"wt.folder.SubFolder:230201\" type=\"hidden\">");
		  sb.append("<input id=\"LocationPicker\" name=\"tcomp$attributesTable$OR:wt.folder.Cabinet:76336$___Location___textbox\" oncontextmenu=\"return false;\" style=\"color:#9d9da1;\" onkeydown=\"if(event.keyCode==Event.KEY_TAB){return true;}else{return false;}\" value=\"\"  type=\"text\"><a id=\"newlocation_loc_img\" href=\"javascript:launchFolderPicker ('http://plm.yongc.com/Windchill/servlet/WindchillAuthGW/wt.enterprise.URLProcessor/invokeAction?action=cadxBrowseLocations&amp;oid=wt.pdmlink.PDMLinkProduct:76298&amp;containerVisibilityMask=OR:wt.pdmlink.PDMLinkProduct:76298&amp;accessPermission=modify&amp;displayHotlinks=false&amp;displayCreateFolder=false',document.getElementsByName('tcomp$attributesTable$OR:wt.folder.Cabinet:76336$___Location___textbox')[0],'selectedFolderFromFolderContext')\"><img title=\"为此对象设置新的文件夹位置。\" alt=\"为此对象设置新的文件夹位置。\" src=\"com/ptc/core/htmlcomp/images/location_set.gif\" border=\"0\" align=\"middle\"></a></input>");
		  return sb.toString();
	  }
}
