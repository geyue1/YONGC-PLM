/**
 * ext.yongc.plm.report.WenjianmuluReportHelper.java
 * @Author yge
 * 2017年11月17日下午2:03:36
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import wt.doc.WTDocument;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartStandardConfigSpec;
import wt.part.WTPartUsageLink;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.views.ViewHelper;
import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.util.ExcelUtility;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.StringUtil;

public class WenjianmuluReportHelper {
	Logger log = LogR.getLogger(WenjianmuluReportHelper.class.getName());
	private static WTPartStandardConfigSpec standardConfigSpec = null;
			
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
				standardConfigSpec = 
						WTPartStandardConfigSpec.newWTPartStandardConfigSpec(
								ViewHelper.service.getView("Design"), null);
		} catch (IOException e) {
			e.printStackTrace();
			}catch (WTException e) {
			e.printStackTrace();
		}
		
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
                 result.add("请输入图纸代号.");
             }else{
          	   Map<String,String> ibaMap = new HashMap<String,String>();
          	   ibaMap.put(IBAConstants.DRAWINGNO, objNumber.trim());
          	   Vector vec=PartUtil.getPartsByTypeAndIBA("wt.part.WTPart", ibaMap, true);
                 if(vec==null || vec.size()==0){
              	   result.add("系统中没有找到图纸代号为："+objNumber.trim()+"的部件.");
                 }
             }
            
         } catch (Exception e) {
             throw new WTException(e);
         } finally {
             SessionServerHelper.manager.setAccessEnforced(flag);
         }

         return result;
     }
	  public void downloadReport(HttpServletResponse response, Map<String, String> formDataMap) throws WTException {
	   	   try {

	              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	              // get system current time add to the report name
	              String currentDate = dateFormat.format((Date) new Timestamp(System.currentTimeMillis()));
	              String fileName = "Wenjianmulu__Report_" + currentDate + ".xls";
	              Workbook workbook =  generateReport(formDataMap);

	              response.reset();  
	              response.setContentType("application/msexcel; charset=UTF-8");
	              response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
//	              OutputStream out = response.getOutputStream();
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
  		   String objNumber = formDataMap.get("objNumber");
  		   String oid = formDataMap.get("oid");
  		   WTPart part = null;
  		   if(StringUtil.isNotEmpty(oid)){
  			     part = (WTPart) rf.getReference(oid).getObject();
  		   }else{
  			   Map<String,String> ibaMap = new HashMap<String,String>();
         	   ibaMap.put(IBAConstants.DRAWINGNO, objNumber.trim());
         	   Vector vec=PartUtil.getPartsByTypeAndIBA("wt.part.WTPart", ibaMap, true);
         	   part =(WTPart) vec.get(0);
  		   }
  		  String reportType = formDataMap.get("objType");
    	  ExcelUtility eu = null;
    	  if("CN".equalsIgnoreCase(reportType)){
    		  eu = new ExcelUtility(TEMPLATE+File.separator+"wenjianmulu_CN.xls");
    	  }else{
    		  eu = new ExcelUtility(TEMPLATE+File.separator+"wenjianmulu_EN.xls");
    	  }
  		  List<WTPart> resultData =new ArrayList<WTPart>();
  		  resultData.add(part);
  		  resultData.addAll(getAllChildPart(part)) ;
  	
  	    log.debug("resultData size----->"+resultData.size());
  	    List<WTDocument> referenceList = null;
  		int index=0;
  	  for(int i =0;i<resultData.size();i++){
  		WTPart  it = resultData.get(i);
  		 log.debug("WTPart----->"+IdentityFactory.getDisplayIdentifier(it));
  		referenceList = PartUtil.getDescDocByPart(it);
  		 log.debug("referenceList size----->"+referenceList.size());
  		for(int j=0;j<referenceList.size();j++){
  			index++;
  			WTDocument doc = referenceList.get(j);
  			eu.setStringValue(index+6, 0, index+"");
  			eu.setStringValue(index+6, 1, doc.getVersionIdentifier().getValue());
  			eu.setStringValue(index+6, 2, doc.getNumber());
  			eu.setStringValue(index+6, 4, doc.getName());
  		}
  	  }
  	   
  	   
  	   return eu.wb;
     }
	  private List<WTPart> getAllChildPart(WTPart part) throws WTException{
		  QueryResult qs = WTPartHelper.service.getUsesWTParts(part, standardConfigSpec);
		  List<WTPart> result = new ArrayList<WTPart>();
		  while (qs.hasMoreElements()) {
			  Object obj[] = (Object[]) qs.nextElement();
			  if (obj[1] instanceof WTPart) {
				  WTPart child = (WTPart) obj[1];
				  WTPartUsageLink alink = (WTPartUsageLink) obj[0];
				  result.add(child);
				  result.addAll(getAllChildPart(child));
			  }
		  }
		  return result;
	  }
}
