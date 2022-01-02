/**
 * ext.yongc.plm.report.TuyangmuluReportHelper.java
 * @Author yge
 * 2017年11月13日下午2:52:14
 * Comment 
 */
package ext.yongc.plm.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;











import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.util.EPMUtil;
import ext.yongc.plm.util.ExcelUtility;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.WCUtil;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.structure.EPMMemberLink;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.session.SessionServerHelper;
import wt.util.WTContext;
import wt.util.WTException;
import wt.util.WTProperties;

public class TuyangmuluReportHelper {
	
	Logger log = LogR.getLogger(TuyangmuluReportHelper.class.getName());
	static String CODEBASE = "";
	static String TEMPLATE = "";
	private static ReferenceFactory rf = new ReferenceFactory();
	static{
		WTProperties wtproperties;
		try {
			wtproperties = WTProperties.getLocalProperties();
			CODEBASE = wtproperties.getProperty("wt.codebase.location");
			TEMPLATE = CODEBASE+File.separator+"netmarkets"+File.separator+"jsp"+File.separator+"ext"+
			                     File.separator+"yongc"+File.separator+"plm"+File.separator+"template";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
       public void downloadReport(HttpServletResponse response, Map<String, String> formDataMap) throws WTException {
    	   try {

               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
               // get system current time add to the report name
               String currentDate = dateFormat.format((Date) new Timestamp(System.currentTimeMillis()));
               String fileName = "图样目录_" + currentDate + ".xls";
               Workbook workbook =  generateReport(formDataMap);

               response.reset();  
               response.setContentType("application/msexcel; charset=UTF-8");
               response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
//               OutputStream out = response.getOutputStream();
               OutputStream out =new BufferedOutputStream(response.getOutputStream());  
               workbook.write(out);

               out.flush();
               out.close();

               response.setStatus(HttpServletResponse.SC_OK);
               response.flushBuffer();

           } catch (Exception e) {
               throw new WTException(e);
           }
       }
       
       private Workbook generateReport(Map<String, String> formDataMap)throws Exception{
    	   List<EPMDocument> resultData = null;
    	   
    	   if(formDataMap.containsKey("oid")){
    		   String oid = formDataMap.get("oid");
    		   EPMDocument epm = (EPMDocument) rf.getReference(oid).getObject();
    		   resultData = new ArrayList<EPMDocument>(); 
    		   resultData.add(epm);
    		   resultData.addAll(getChildEPMDocument(epm));
    	   }else{
    		   String objNumber = formDataMap.get("objNumber");
    		   resultData = getAllEPMDocument(objNumber);
    	   }
    	  String reportType = formDataMap.get("objType");
    	  ExcelUtility eu = null;
    	  if("CN".equalsIgnoreCase(reportType)){
    		  eu = new ExcelUtility(TEMPLATE+File.separator+"tuyangmulu_CN.xls");
    	  }else{
    		  eu = new ExcelUtility(TEMPLATE+File.separator+"tuyangmulu_EN.xls");
    	  }
    	  EPMDocument epm = null;
    	  String version = null;
    	  String tuyangdaihao = null;
    	  String tuzhimingcheng = null;
    	  String beizhu = null;
    	  IBAUtil iba= null;
    	  log.debug("resultData size----->"+resultData.size());
    	  for(int i =0;i<resultData.size();i++){
    		  epm = resultData.get(i);
    		  version = epm.getVersionIdentifier().getValue();
    		  iba = new IBAUtil(epm);
    		  tuyangdaihao = iba.getIBAValue(IBAConstants.DRAWINGNO);
    		  tuzhimingcheng = iba.getIBAValue(IBAConstants.CNAME);
    		  beizhu =  iba.getIBAValue(IBAConstants.NOTE);
    		  log.debug("version----------->"+version);
    		  log.debug("tuzhimingcheng----------->"+tuzhimingcheng);
    		  eu.setStringValue(i+7, 0, i+1+"");
    		  eu.setStringValue(i+7, 1, version);
    		  eu.setStringValue(i+7, 2, tuyangdaihao);
    		  eu.setStringValue(i+7, 4, tuzhimingcheng);
    		  eu.setStringValue(i+7, 8, "");
    		  eu.setStringValue(i+7, 9, beizhu);
    	  }
    	   
    	   
    	   return eu.wb;
       }
       private List<EPMDocument> getAllEPMDocument(String number) throws WTException{
    	   List<EPMDocument> result = new ArrayList<EPMDocument>();
    	   
    	   Map<String,String> ibaMap = new HashMap<String,String>();
    	   ibaMap.put(IBAConstants.DRAWINGNO, number.trim());
    	   Vector vector = EPMUtil.getEPMDocumentsByTypeAndIBA("com.yongc.DefaultEPMDocument", ibaMap, true);
    	   if(vector==null || vector.size()==0){
    		   return result;
    	   }
    	   EPMDocument rootEPM =(EPMDocument) vector.get(0);
    	   log.debug(" rootEPM ----------->"+IdentityFactory.getDisplayIdentifier(rootEPM));
    	   result.add(rootEPM);
    	   result.addAll(getChildEPMDocument(rootEPM));
    	   
    	   List<EPMDocument> temp = new ArrayList<EPMDocument>();
    	   for(EPMDocument epm : result){
    		   if(!temp.contains(epm)){
    			   temp.add(epm);
    		   }
    	   }
    	   return temp;
       }
       
       public List<EPMDocument> getChildEPMDocument(EPMDocument epm) throws WTException{
    	   List<EPMDocument> result = new ArrayList<EPMDocument>();
    	   QueryResult qr=PersistenceHelper.manager.navigate(epm, EPMMemberLink.ROLE_BOBJECT_ROLE, EPMMemberLink.class);
    	   while(qr.hasMoreElements()){
    		   EPMDocumentMaster master =(EPMDocumentMaster) qr.nextElement();
    		   EPMDocument childEpm = EPMUtil.getEPMDocmentByNumber(master.getNumber());
    		   log.debug(" childEpm ----------->"+IdentityFactory.getDisplayIdentifier(childEpm));
    		   result.add(childEpm);
    		   result.addAll(getChildEPMDocument(childEpm));
    	   }
    	   return result;
       }
       
       public List<String> validateFormData(Map<String, String> formData) throws WTException {

           List<String> result = new ArrayList<String>();

           String objType = formData.get("objType");
           String objNumber = formData.get("objNumber");

           boolean flag = SessionServerHelper.manager.setAccessEnforced(false);

           try {

               if (StringUtils.isEmpty(objType)) {
                   result.add("请选择报表模板.");
               }
               if (StringUtils.isEmpty(objNumber)) {
                   result.add("请输入图样代号.");
               }else{
            	   Map<String,String> ibaMap = new HashMap<String,String>();
            	   ibaMap.put(IBAConstants.DRAWINGNO, objNumber.trim());
            	   Vector vec=EPMUtil.getEPMDocumentsByTypeAndIBA("com.yongc.DefaultEPMDocument", ibaMap, true);
                   if(vec==null || vec.size()==0){
                	   result.add("系统中没有找到图样代号为："+objNumber.trim()+"的设计文件.");
                   }
               }
              
           } catch (Exception e) {
               throw new WTException(e);
           } finally {
               SessionServerHelper.manager.setAccessEnforced(flag);
           }

           return result;
       }
     
}
