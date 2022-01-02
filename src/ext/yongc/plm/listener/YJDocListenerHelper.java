/**
 * ext.yongc.plm.listener.YJDocListenerHelper.java
 * @Author yge
 * 2017年8月8日上午9:02:03
 * Comment 
 */
package ext.yongc.plm.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.SigConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.signature.FX1Location;
import ext.yongc.plm.signature.FZFANSMSLocation;
import ext.yongc.plm.util.DocUtil;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WorkflowUtil;
import wt.content.ApplicationData;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.QueryResult;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfProcessTemplate;

public class YJDocListenerHelper {
	private static final Logger logger = LogR.getLogger(YJDocListenerHelper.class.getName());
	
	private Dispatch word = null; //当前word对象
	private  Dispatch selection = null;
	private ActiveXComponent wordApp = null;   //word运行程序对象
	// 所有word文档集合
    private Dispatch documents = null;
    
    private ActiveXComponent excel = null;   //excel 运行程序对象 
	 private  Dispatch workbooks = null;  //工作簿对象  
	 private Dispatch workbook = null; //具体工作簿  
	 
	 private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 
     private WTDocument doc;
     private String appDataName;
     private String filePath = "";
     private String savePath = "";
 	 private static String TEMP = "";
 	 private String fileName = ""; //文件名称
 	 private String fileID = "";    //文件代号
 	 private String productName = "";  // 产品名称
 	 private String productModel = "";  //产品型号
 	
 	 static {
 	        try {
 	            WTProperties wtproperties = wt.util.WTProperties
 	                    .getLocalProperties();
 	            TEMP = wtproperties.getProperty("wt.temp");
 	       
 	        } catch (Throwable throwable) {
 	            throw new ExceptionInInitializerError(throwable);
 	        }
 	    }
     public YJDocListenerHelper(WTDocument doc){
		  this.doc = doc;
	  }
     
     private void initWordApp(){
     	ComThread.InitSTA(); //初始化
 		  if (wordApp == null || wordApp.m_pDispatch==0) {
 			  wordApp = new ActiveXComponent("Word.Application");
 			  wordApp.setProperty("Visible", new Variant("false"));
 		     }
 		   if (documents == null || documents.m_pDispatch==0)
          documents = wordApp.getProperty("Documents").toDispatch();
     }
     private void initExcelApp(){
 		  ComThread.InitSTA();  
 		    if(excel==null || excel.m_pDispatch==0)  
 		    	excel = new ActiveXComponent("Excel.Application"); //Excel对象  
 		    excel.setProperty("Visible", new Variant("false"));//设置是否显示打开excel  
 		    if(workbooks==null || workbooks.m_pDispatch==0)  
               workbooks = excel.getProperty("Workbooks").toDispatch(); //打开具体工作簿  
               
 	  }
     private void closedWordApp(){
     	 if (word != null) {
              Dispatch.call(word, "Save");
              Dispatch.call(word, "Close");
              word = null;
         }
        if (wordApp != null) {
            Dispatch.call(wordApp, "Quit");
            wordApp = null;
          }
             ComThread.Release();
     }
     private void closedExcel(){
 		  if (workbook != null) {
 	             Dispatch.call(workbook, "Save");
 	             Dispatch.call(workbook, "Close");
 	             workbook = null;
 	        }
 	       if (excel != null) {
 	           Dispatch.call(excel, "Quit");
 	           excel = null;
 	         }
 	            ComThread.Release();
 	  }
	
	  
	  public void process()throws Exception{
			logger.debug(" ---------- enter process");
			logger.debug("input doc is -------->"+IdentityFactory.getDisplayIdentifier(doc));
			doc = DocUtil.getLatestDocByMaster((WTDocumentMaster) doc.getMaster());
			logger.debug("latest doc is -------->"+IdentityFactory.getDisplayIdentifier(doc));
			ApplicationData appData = DocUtil.getPrimaryContent(doc);
			if(appData==null){
				logger.debug(" primary content is null ---------- exit process");
				return;
			}
			 appDataName = appData.getFileName();
			InputStream is = ContentServerHelper.service.findContentStream(appData);
			String folder = TEMP+File.separator+"signature";
			File f = new File(folder);
			if(!f.exists()){
				f.mkdirs();
			}
			filePath = folder+File.separator+"listener-"+System.currentTimeMillis()+"-"+appData.getFileName();
			 saveToDisk(is,filePath);
			 is.close();
			 savePath = folder+File.separator+doc.getNumber()+"-"+System.currentTimeMillis()+"-"+appData.getFileName();
			 
			 IBAUtil iba = new IBAUtil(doc);
			 fileName = iba.getIBAValue(IBAConstants.FILENAME);
			 fileID = iba.getIBAValue(IBAConstants.FILEID);
			 productName = iba.getIBAValue(IBAConstants.PRODUCTNAME);
			 productModel = iba.getIBAValue(IBAConstants.PRODUCTMODEL);
			 logger.debug("fileName------>"+fileName);
			 logger.debug("fileID------>"+fileID);
			 logger.debug("productName------>"+productName);
			 logger.debug("productModel------>"+productModel);
			 
			
			String fileType = appDataName.toUpperCase();
			if(fileType.endsWith("DOC") || fileType.endsWith("DOCX")){
				  processWord();
			}else if(fileType.endsWith("XLS") || fileType.endsWith("XLSX")){
				processExcel();
			}
			
			logger.debug(" ---------- exit process");
	  }
	  private  void saveToDisk(InputStream is, String filename)
				throws FileNotFoundException, IOException {
		
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = is.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			fos.close();
		}
	  
	  public boolean find(String toFindText) {
		  
          if (toFindText == null || toFindText.equals(""))
              return false;
          // 从selection所在位置开始查询
           Dispatch find = wordApp.call(selection, "Find").toDispatch();
           // 设置要查找的内容
          Dispatch.put(find, "Text", toFindText);
         // 向前查找
          Dispatch.put(find, "Forward", "True");
           // 设置格式
           Dispatch.put(find, "Format", "True");
           // 大小写匹配
           Dispatch.put(find, "MatchCase", "True");
           // 全字匹配
           Dispatch.put(find, "MatchWholeWord", "True");
          // 第一次查找
           boolean flag = Dispatch.call(find, "Execute").getBoolean();
           if(!flag){
        	   //第二次查找
        	   Dispatch.call(selection, "HomeKey", new Variant(6));
        	   flag = Dispatch.call(find, "Execute").getBoolean();
           }
           return flag;
       }
	  private void processWord(){
		  try{
			  initWordApp();//初始化
			  word = Dispatch.call(documents, "Open",filePath).toDispatch();
			  
			  selection = Dispatch.get(wordApp, "Selection").toDispatch();
			  
			  String name = SigConstants.START_MARK+SigConstants.FILE_NAME+SigConstants.END_MARK;
			  String id = SigConstants.START_MARK+SigConstants.FILE_CODE+SigConstants.END_MARK;
			  String cpmc = SigConstants.START_MARK+SigConstants.PRODUCT_NAME+SigConstants.END_MARK;
			  String cpxh = SigConstants.START_MARK+SigConstants.PRODUCT_MODEL+SigConstants.END_MARK;
			  //设计日期
			  String date = SigConstants.START_MARK+SigConstants.DATE_Designed+SigConstants.END_MARK;
			  String design = SigConstants.START_MARK+SigConstants.SIG_Designed+SigConstants.END_MARK;
			 //校核
			  String checked = SigConstants.START_MARK+SigConstants.SIG_Checked+SigConstants.END_MARK;
			  String checkedDate = SigConstants.START_MARK+SigConstants.DATE_Checked+SigConstants.END_MARK;
			  //主管设计
			  String chief = SigConstants.START_MARK+SigConstants.SIG_ChiefDesigner+SigConstants.END_MARK;
			  String chiefDate = SigConstants.START_MARK+SigConstants.DATE_ChiefDesigner+SigConstants.END_MARK;
			  //标准化
			  String standard = SigConstants.START_MARK+SigConstants.SIG_Standardized+SigConstants.END_MARK;
			  String standardDate = SigConstants.START_MARK+SigConstants.DATE_Standardized+SigConstants.END_MARK;
			  //审核
			  String review = SigConstants.START_MARK+SigConstants.SIG_Reviewed+SigConstants.END_MARK;
			  String reviewDate = SigConstants.START_MARK+SigConstants.DATE_Reviewed+SigConstants.END_MARK;
			  //批准
			  String approve = SigConstants.START_MARK+SigConstants.SIG_Approved+SigConstants.END_MARK;
			  String approveDate = SigConstants.START_MARK+SigConstants.DATE_Approved+SigConstants.END_MARK;
			  
			  Dispatch bookMarks = wordApp.call(word, "Bookmarks").toDispatch(); //查找所有书签
			
			  if(find(name)){
				   //创建书签
				  Dispatch.call(bookMarks, "Add", SigConstants.FILE_NAME, selection);
			  }
			  if(find(name)){
				   //创建书签
				  Dispatch.call(bookMarks, "Add", SigConstants.FILE_NAME+"1", selection);
			  }
			  if(find(id)){
				  Dispatch.call(bookMarks, "Add", SigConstants.FILE_CODE, selection);
			  }
			  if(find(cpmc)){
				  Dispatch.call(bookMarks, "Add", SigConstants.PRODUCT_NAME, selection);
			  }
			  if(find(cpmc)){
				  Dispatch.call(bookMarks, "Add", SigConstants.PRODUCT_NAME+"1", selection);
			  }
			  if(find(cpxh)){
				  Dispatch.call(bookMarks, "Add", SigConstants.PRODUCT_MODEL, selection);
			  }
			  if(find(cpxh)){
				  Dispatch.call(bookMarks, "Add", SigConstants.PRODUCT_MODEL+"1", selection);
			  }
				if(find(date)){
					 Dispatch.call(bookMarks, "Add", SigConstants.DATE_Designed, selection);		  
				  }
				if(find(date)){
					 Dispatch.call(bookMarks, "Add", SigConstants.DATE_Designed+"1", selection);		  
				  }
			  
			  String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
			  logger.debug(" type----->"+type);
//			  if(type.indexOf(TypeConstants.DOC_YF)>-1){
					if(find(design)){
						 Dispatch.call(bookMarks, "Add", SigConstants.SIG_Designed, selection);		  
					  }
					if(find(checked)){
						 Dispatch.call(bookMarks, "Add", SigConstants.SIG_Checked, selection);		  
					  }
					if(find(checkedDate)){
						 Dispatch.call(bookMarks, "Add", SigConstants.DATE_Checked, selection);		  
					  }
					if(find(chief)){
						 Dispatch.call(bookMarks, "Add", SigConstants.SIG_ChiefDesigner, selection);		  
					  }
					if(find(chiefDate)){
						 Dispatch.call(bookMarks, "Add", SigConstants.DATE_ChiefDesigner, selection);		  
					  }
					if(find(standard)){
						 Dispatch.call(bookMarks, "Add", SigConstants.SIG_Standardized, selection);		  
					  }
					if(find(standardDate)){
						 Dispatch.call(bookMarks, "Add", SigConstants.DATE_Standardized, selection);		  
					  }
					if(find(review)){
						 Dispatch.call(bookMarks, "Add", SigConstants.SIG_Reviewed, selection);		  
					  }
					if(find(reviewDate)){
						 Dispatch.call(bookMarks, "Add", SigConstants.DATE_Reviewed, selection);		  
					  }
					if(find(approve)){
						 Dispatch.call(bookMarks, "Add", SigConstants.SIG_Approved, selection);		  
					  }
					if(find(approveDate)){
						 Dispatch.call(bookMarks, "Add", SigConstants.DATE_Approved, selection);		  
					  }
//			  }
			  
			   int count = Dispatch.get(bookMarks, "Count").getInt();  //获取书签数
			   logger.debug("count---------->"+count);
//			   for (int i = 1; i <= count; i++){     
//		             Dispatch items = Dispatch.call(bookMarks, "Item", i).toDispatch();   
//		             String bookMarkKey = String.valueOf(Dispatch.get(items, "Name").getString()).replaceAll("null", "");   //读取书签命名
//		             Dispatch range = Dispatch.get(items, "Range").toDispatch();
//		             String bookMarkValue = String.valueOf(Dispatch.get(range, "Text").getString()).replaceAll("null", ""); //读取书签文本
//		             String MarkKey=bookMarkKey;
//		             String MarkValue=bookMarkValue;
//		             logger.debug("MarkKey----->"+MarkKey);
//		             logger.debug("MarkValue----->"+MarkValue);
//		         }
			   
			   //替换文件名称
				   replaceBookmarkValue(bookMarks, SigConstants.FILE_NAME, fileName);
				   replaceBookmarkValue(bookMarks, SigConstants.FILE_NAME+"1", fileName);
			  
			   //替换文件代号
				   replaceBookmarkValue(bookMarks, SigConstants.FILE_CODE, fileID);
			 
			   //替换产品名称
				   replaceBookmarkValue(bookMarks, SigConstants.PRODUCT_NAME, productName);
				   replaceBookmarkValue(bookMarks, SigConstants.PRODUCT_NAME+"1", productName);
			 
			   //替换产品型号
				   replaceBookmarkValue(bookMarks, SigConstants.PRODUCT_MODEL+"1", productModel);
				   replaceBookmarkValue(bookMarks, SigConstants.PRODUCT_MODEL, productModel);
			 
			 
			   Dispatch.call(word, "SaveAs",savePath);
			   DocUtil.addPrimaryContent(doc, savePath, appDataName);
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  closedWordApp();
		  }
	  }
	  private void replaceBookmarkValue(Dispatch bookMarks,String bookMarkName,String value){
		  logger.debug("bookMarkName------------->"+bookMarkName);
		  logger.debug("value------------->"+value);
		  boolean bookMarkExist = word.call(bookMarks, "Exists", bookMarkName)
                   .toBoolean();
           if (bookMarkExist) {
               Dispatch rangeItem = Dispatch.call(bookMarks, "Item", bookMarkName)
                       .toDispatch();
               Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
//               String temp = String.valueOf(Dispatch.get(range, "Text").getString());
             
               Dispatch.put(range, "Text", value);
             //添加书签
             bookMarkExist = word.call(bookMarks, "Exists", bookMarkName).toBoolean();
             logger.debug("replaceBookmarkValue bookMarkExist------->"+bookMarkExist);
             if(!bookMarkExist){
            	  Dispatch.call(bookMarks, "Add", bookMarkName, range);
             }
              
           }
	  }
	  private void processExcel(){
		  try{
			  initExcelApp();//初始化
			  workbook = Dispatch.call(workbooks, "Open",filePath).toDispatch();
			  String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
			  logger.debug(" type----->"+type);
			  
			  if(type.indexOf(TypeConstants.DOC_FX1)>-1){
				 String location = FX1Location.getSignatureLocation(SigConstants.FILE_NAME);
				 writeExcel(1, location, fileName);
				 location = FX1Location.getSignatureLocation(SigConstants.FILE_CODE);
				 writeExcel(1, location, fileID);
				 location = FX1Location.getSignatureLocation(SigConstants.PRODUCT_NAME);
				 writeExcel(1, location, productName);
				 writeExcel(2, "A8", productName);
				 location = FX1Location.getSignatureLocation(SigConstants.PRODUCT_MODEL);
				 writeExcel(1, location, productModel);
				 writeExcel(2, "A7", productModel);
				 String date = df.format(new Date());
				 location = FX1Location.getSignatureLocation(SigConstants.DATE_Designed1);
				 writeExcel(1, location, date);
			  }else if(type.indexOf(TypeConstants.DOC_YF_TYML)>-1 || type.indexOf(TypeConstants.DOC_YF_WJML)>-1){
				  writeExcel(1, "A4", productModel+" "+productName);
			  }else if(type.indexOf(TypeConstants.DOC_YF_GJXB)>-1 || type.indexOf(TypeConstants.DOC_YF_FAIB)>-1 ||
					     type.indexOf(TypeConstants.DOC_YF_AQGJJB)>-1 || type.indexOf(TypeConstants.DOC_YF_BSJB)>-1){
				  writeExcel(1, "A5", productModel+" "+productName);
			  }else if(type.indexOf(TypeConstants.DOC_YF_ZYDFJ)>-1){
				  writeExcel(1, "A6", productModel+" "+productName);
			  }else if(type.indexOf(TypeConstants.DOC_FX2)>-1){
				  writeExcel(1, "A8", productModel);
				  writeExcel(1, "A9", productName);
			  }else if(type.indexOf(TypeConstants.DOC_YF_JSTZS)>-1){
				  writeExcel(1, "A4", fileID);
				  writeExcel(1, "C4", productName);
				  writeExcel(1, "Q2", productModel);
			  }
			  
			  Dispatch.call(workbook, "SaveAs",savePath);  
			  
              DocUtil.addPrimaryContent(doc, savePath, appDataName);
            
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  closedExcel();
		  }
	  }
	  
	  private void writeExcel(int sheetIndex,String location1,String value){
		  logger.debug("enter writeExcel");
		  logger.debug("sheetIndex------>"+sheetIndex);
		  logger.debug("location1---------->"+location1);
		  logger.debug("value--------------->"+value);
    	  Dispatch.put(workbook, "CheckCompatibility", false); //取消兼容性检查，在保存或者另存为时改检查会导致弹窗 
		  Dispatch sheets = Dispatch.get(workbook, "sheets").toDispatch();  
		  //从第二个sheet页 （sheet index从1开始）
		  Dispatch sheet= Dispatch.invoke(sheets, "Item", Dispatch.Get, new Object[]{sheetIndex}, new int[1]).toDispatch();
		 logger.debug("sheet name----->"+Dispatch.get(sheet, "name").toString());
		 
		  //向单元格设签名
		  Dispatch cell = Dispatch.invoke(sheet, "Range",  
	                Dispatch.Get, new Object[] {location1}, new int[1])  
	                .toDispatch();  
		
        	 Dispatch.put(cell, "Value", value);  
        	 
        	 logger.debug("exit writeExcel");
    }
	  
	  public void startWGJProcess() throws Exception{
		  logger.debug(" ----------------> enter startWGJProcess");
		  QueryResult templateQR= WfDefinerHelper.service.getEnabledTemplates(doc.getContainerReference());
		  WfProcessTemplate template = null;
		  while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_WGJ.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
			 logger.debug("template ----------------->"+template);
	          // start customization process
				if(template!=null){
					WorkflowUtil.startWorkflow(doc, template);
				}
				  logger.debug(" ----------------> exit startWGJProcess");
	  }
	  
	  public void startDeliveryPackageProcess()throws Exception{
		  logger.debug(" ----------------> enter startDeliveryPackageProcess");
		  QueryResult templateQR= WfDefinerHelper.service.getEnabledTemplates(doc.getContainerReference());
		  WfProcessTemplate template = null;
		  while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_DP.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
			 logger.debug("template ----------------->"+template);
	          // start customization process
				if(template!=null){
					WorkflowUtil.startWorkflow(doc, template);
				}
				  logger.debug(" ----------------> exit startDeliveryPackageProcess");
	  }
}
