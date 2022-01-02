/**
 * ext.yongc.plm.report.BOMReportHelper.java
 * @Author yge
 * 2017年11月15日下午2:22:02
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

import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
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
import ext.yongc.plm.util.EPMUtil;
import ext.yongc.plm.util.ExcelUtility;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.StringUtil;

public class BOMReportHelper {
	
	Logger log = LogR.getLogger(BOMReportHelper.class.getName());
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

         String objNumber = formData.get("objNumber");

         boolean flag = SessionServerHelper.manager.setAccessEnforced(false);

         try {

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
              String fileName = "BOM_Information_Report_" + currentDate + ".xls";
              Workbook workbook =  generateReport(formDataMap);

              response.reset();  
              response.setContentType("application/msexcel; charset=UTF-8");
              response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
//              OutputStream out = response.getOutputStream();
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
   		  List<BOMReportItem> resultData =new ArrayList<BOMReportItem>();
   		  IBAUtil iba = new IBAUtil(part);
   		  BOMReportItem  item = new BOMReportItem();
   		  item.setChildNum(iba.getIBAValue(IBAConstants.DRAWINGNO));
		  item.setChildDescription(iba.getIBAValue(IBAConstants.CNAME));
		  item.setChildProcess(iba.getIBAValue(IBAConstants.GONGYI_TYPE));
		  
		  item.setJbjl(iba.getIBAValue(IBAConstants.JBJLDW));
		  item.setWxgys(iba.getIBAValue(IBAConstants.WXGYS));
		  resultData.add(item);
   		  resultData.addAll(getAllItem(part)) ;
   	      ExcelUtility eu = new ExcelUtility(TEMPLATE+File.separator+"BOM_Report.xls");
   		  
   	
   	  log.debug("resultData size----->"+resultData.size());
   	  for(int i =0;i<resultData.size();i++){
   		 BOMReportItem  it = resultData.get(i);
   		 
   		  eu.setStringValue(i+1, 0, i+1+"");
   		  eu.setStringValue(i+1, 1, it.getParentNum());
   		  eu.setStringValue(i+1, 2, it.getParentERPNum());
   		  eu.setStringValue(i+1, 3, it.getParentName());
   		  eu.setStringValue(i+1, 4, it.getParentProcess());
   		  eu.setStringValue(i+1, 5, it.getParentQty());
   		  eu.setStringValue(i+1, 6, it.getFactory());
   		  eu.setStringValue(i+1, 7, it.getChildNum());
   		  eu.setStringValue(i+1, 8, it.getChildERPNum());
   		  eu.setStringValue(i+1, 9, it.getChildDescription());
   		  eu.setStringValue(i+1, 10, it.getChildProcess());
   		  eu.setStringValue(i+1, 11, it.getChildQty());
   		  eu.setStringValue(i+1, 12, it.getJbjl());
   		  eu.setStringValue(i+1, 13, it.getWxgys());
   		  eu.setStringValue(i+1, 14, it.getGypz());
   	  }
   	   
   	   
   	   return eu.wb;
      }
	  private List<BOMReportItem> getAllItem(WTPart part) throws WTException{
		  List<BOMReportItem> result = new ArrayList<BOMReportItem>();
		  QueryResult qs = WTPartHelper.service.getUsesWTParts(part, standardConfigSpec);
		  while (qs.hasMoreElements()) {
			  Object obj[] = (Object[]) qs.nextElement();
			  if (obj[1] instanceof WTPart) {
				  WTPart child = (WTPart) obj[1];
				  WTPartUsageLink alink = (WTPartUsageLink) obj[0];
				  
				  BOMReportItem item = new BOMReportItem();
				  IBAUtil iba = new IBAUtil(part);
				  item.setParentNum(iba.getIBAValue(IBAConstants.DRAWINGNO));
				  item.setParentName(iba.getIBAValue(IBAConstants.CNAME));
				  item.setParentProcess(iba.getIBAValue(IBAConstants.GONGYI_TYPE));
				  item.setParentQty(iba.getIBAValue(IBAConstants.MJSL));
				  item.setFactory(iba.getIBAValue(IBAConstants.GONGC));
				  
				  iba = new IBAUtil(child);
				  item.setChildNum(iba.getIBAValue(IBAConstants.DRAWINGNO));
				  item.setChildDescription(iba.getIBAValue(IBAConstants.CNAME));
				  item.setChildProcess(iba.getIBAValue(IBAConstants.GONGYI_TYPE));
				  
				  item.setJbjl(iba.getIBAValue(IBAConstants.JBJLDW));
				  item.setWxgys(iba.getIBAValue(IBAConstants.WXGYS));
//				  item.setGypz(iba.getIBAValue(IBAConstants.G));
				  
				  result.add(item);
				  result.addAll(getAllItem(child));
			  }
		  }
		  return result;
	  }
}
