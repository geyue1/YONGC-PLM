/**
 * ext.yongc.plm.part.rule.QitaoxingUtil.java
 * @Author yge
 * 2017年11月29日上午11:04:08
 * Comment 
 */
package ext.yongc.plm.part.rule;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import wt.fc.ReferenceFactory;
import wt.log4j.LogR;
import wt.util.WTException;

public class QitaoxingUtil {
	private static Logger log = LogR.getLogger(QitaoxingUtil.class.getName());
	 private static ReferenceFactory rf  = new ReferenceFactory();
	   
	public static List<String> validateFormData(Map<String, String> formData) throws WTException{
		return new QitaoxingHelper().validateFormData(formData);
	}
	 public static Map<String, String> getFormData(HttpServletRequest request) {

		 Map<String, String> formDataMap = new HashMap<String, String>();
		
		 String objNumberInput = request.getParameter("objNumberInput");
		 String oid = request.getParameter("oid");
		 formDataMap.put("objNumber", objNumberInput.trim());
		 formDataMap.put("oid", oid);
		 log.debug("formDataMap--------->"+formDataMap);
		 return formDataMap;
    }
	 
	 public static List<Map<String,String>> processCheck(Map<String, String> formData) throws Exception{
		 return new QitaoxingHelper().processCheck(formData);
	 }
}
