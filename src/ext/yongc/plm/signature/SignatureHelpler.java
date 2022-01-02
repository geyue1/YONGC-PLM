/**
 * ext.yongc.plm.signature.SignatureHelpler.java
 * @Author yge
 * 2017年7月28日上午10:46:44
 * Comment 
 */
package ext.yongc.plm.signature;

import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.SigConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.util.ChangeUtil;
import ext.yongc.plm.util.DocUtil;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PromotionUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WCUtil;
import ext.yongc.plm.util.WorkItemInfoBean;
import ext.yongc.plm.util.WorkflowUtil;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.maturity.PromotionNotice;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.wip.Workable;
import wt.workflow.engine.WfProcess;

public class SignatureHelpler {
	
	private Logger logger = LogR.getLogger(SignatureHelpler.class.getName());
	
	private Dispatch word = null; //当前word对象
	private ActiveXComponent wordApp = null;   //word运行程序对象
	// 所有word文档集合
    private Dispatch documents = null;
    
    private ActiveXComponent excel = null;   //excel 运行程序对象 
	 private  Dispatch workbooks = null;  //工作簿对象  
	 private Dispatch workbook = null; //具体工作簿  
    
    
	private Persistable pbo;
	private PromotionNotice promotion;
	private WTChangeOrder2 ecn;
	private WorkItemInfoBean info;
	private String signatureRole="";
	private String signatureDate = "";
	private static String TEMP = "";
	private static String CODEBASE = "";
	private static String IMAGE = "";
	private static String PDF_TEMPLATE = "";
	
	 static {
	        try {
	            WTProperties wtproperties = wt.util.WTProperties
	                    .getLocalProperties();
	            TEMP = wtproperties.getProperty("wt.temp");
	            CODEBASE = wtproperties.getProperty("wt.home")+File.separator+"codebase";
	            IMAGE = CODEBASE+File.separator+"ext"+File.separator+"yongc"+File.separator+"plm"+File.separator+
	            		 "signature";
	            PDF_TEMPLATE = IMAGE+File.separator+"pdfTemplate"+File.separator+"pdfTemplate.pdf";
	        } catch (Throwable throwable) {
	            throw new ExceptionInInitializerError(throwable);
	        }
	    }
  
    public SignatureHelpler(Persistable pbo){
    	
    	this.pbo = pbo;
    }
    
    public void signature(String signatureRole,String signatureDate){
    	logger.debug(" --------- enter signature");
    	if(signatureRole.contains("<**")){
    		signatureRole = signatureRole.replace("<**", "");
    	}
    	if(signatureRole.contains("**>")){
    		signatureRole = signatureRole.replace("**>", "");
    	}
    	if(signatureDate.contains("<**")){
    		signatureDate = signatureDate.replace("<**", "");
    	}
    	if(signatureDate.contains("**>")){
    		signatureDate = signatureDate.replace("**>", "");
    	}
    	logger.debug(" signatureRole--------- "+signatureRole);
    	logger.debug(" signatureDate--------- "+signatureDate);
    	this.signatureRole = signatureRole;
    	this.signatureDate = signatureDate;
    	
    	if(pbo==null){
    		return;
    	}
    	if(pbo instanceof PromotionNotice){
    		promotion = (PromotionNotice)pbo;
    	}else if(pbo instanceof WTChangeOrder2){
    		ecn = (WTChangeOrder2)pbo;
    	}
    	logger.debug("promotion=============>"+promotion);
    	logger.debug("ecn=============>"+ecn);
    	try {
    		List list = new ArrayList();
    		List targetList = new ArrayList();
    		if(promotion!=null){
    			 WfProcess process = WorkflowUtil.getProcessByPBO(promotion);
    			 list = WorkflowUtil.getProcessRoutingHistory(process);
    			  targetList = PromotionUtil.getLatestPromotionTargets(promotion);
    		}else if(ecn!=null){
    			WfProcess process = WorkflowUtil.getProcessByPBO(ecn);
    			list = WorkflowUtil.getProcessRoutingHistory(process);
    			targetList = ChangeUtil.getChangeAfter(ecn);
    		}
			
			logger.debug(" list size ----------->"+list.size());
			if(list!=null && list.size()>0){
				 info =(WorkItemInfoBean) list.get(0);
			}
			
			//更改单签名
			if(ecn!=null){
				signatureChangeList(ecn);
			}
			
			logger.debug(" targetList size ----------->"+targetList.size());
			Object obj = null;
			for(int i=0;i<targetList.size();i++){
				obj = targetList.get(i);
				if( obj instanceof WTDocument){
					WTDocument doc = (WTDocument)targetList.get(i);
					logger.debug(" doc ----->"+IdentityFactory.getDisplayIdentifier(doc));
					ApplicationData appData = DocUtil.getPrimaryContent(doc);
					if(appData==null){
						continue;
					}
					
					doc =(WTDocument) WCUtil.doCheckOut((Workable)doc);
					logger.debug(appData.getFormatName());
					logger.debug(appData.getFileName());
					String fileName = appData.getFileName().toUpperCase();
					if(fileName.endsWith("DOC") || fileName.endsWith("DOCX")){
						signatureWord(doc);
					}else if(fileName.endsWith("XLS") || fileName.endsWith("XLSX")){
						 signatureExcel(doc);
					}else if(fileName.toUpperCase().endsWith("PDF")){
						signaturePDF(doc);
					}
					WCUtil.doCheckIn((Workable)doc, info.getWorkName()+" "+info.getWorkCompletedBy()+"签名 "+info.getWorkCompletedDate());
				}
			}
		} catch (WTException e) {
			e.printStackTrace();
		}
    	logger.debug(" --------- exit signature");
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
    private void signatureWord(WTDocument doc){
    	logger.debug(" enter signatureWord");
    	try {
			ApplicationData appData = DocUtil.getPrimaryContent(doc);
			InputStream is = ContentServerHelper.service.findContentStream(appData);
			String folder = TEMP+File.separator+"signature";
			File f = new File(folder);
			if(!f.exists()){
				f.mkdirs();
			}
			String file = folder+File.separator+"orginal-"+appData.getFileName();
			 saveToDisk(is,file);
			String filePath = TEMP+File.separator+"signature"+File.separator+appData.getFileName();
			
			initWordApp();
			word = Dispatch.call(documents, "Open",file).toDispatch();
			Dispatch bookMarks = wordApp.call(word, "Bookmarks").toDispatch();
			boolean bookMarkExist = wordApp.call(bookMarks, "Exists", signatureRole).toBoolean();
			logger.debug(signatureRole+" bookMarkExist ------->"+bookMarkExist);
	         if(bookMarkExist){
	        	 Dispatch rangeItem = Dispatch.call(bookMarks, "Item", signatureRole)
                         .toDispatch();
                 Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
                 Dispatch temp = Dispatch.get(range, "InLineShapes").toDispatch();
                 if(temp!=null){
                	 Dispatch.call(range, "Delete"); 
                 }
                 
                 File imageFile = new File(IMAGE+File.separator+info.getWorkCompletedBy()+".bmp");
            	 if(imageFile.exists()){
            		   Dispatch.put(range, "Text", "");
                       Dispatch picture= Dispatch.call(Dispatch.get(range, "InLineShapes").toDispatch(), 
                    		                        "AddPicture",IMAGE+File.separator+info.getWorkCompletedBy()+".bmp" ).toDispatch();
                       Dispatch.put(picture, "Width", 35);
                       Dispatch.put(picture, "Height", 20);
            	 }else{
            		  Dispatch.put(range, "Text", info.getWorkCompletedBy());
            	 }
            	 
            	 //重新添加书签
            	 bookMarkExist = word.call(bookMarks, "Exists", signatureRole)
	                     .toBoolean();
	             if(!bookMarkExist){
	            	 Dispatch.call(bookMarks, "Add", signatureRole, range);
	             }
	         }
	         
	         bookMarkExist = wordApp.call(bookMarks, "Exists", signatureDate).toBoolean();
				logger.debug(signatureDate+" bookMarkExist ------->"+bookMarkExist);
		         if(bookMarkExist){
		        	 Dispatch rangeItem = Dispatch.call(bookMarks, "Item", signatureDate)
	                         .toDispatch();
	                 Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
	                 Dispatch.put(range, "Text", info.getWorkCompletedDate());
	                 
	                 //重新添加书签
	            	 bookMarkExist = word.call(bookMarks, "Exists", signatureDate)
		                     .toBoolean();
		             if(!bookMarkExist){
		            	 Dispatch.call(bookMarks, "Add", signatureDate, range);
		             }
		         }
		         bookMarkExist = wordApp.call(bookMarks, "Exists", signatureDate+"2").toBoolean();
					logger.debug(signatureDate+"2 bookMarkExist ------->"+bookMarkExist);
					if(!bookMarkExist){
						   bookMarkExist = wordApp.call(bookMarks, "Exists", signatureDate+"1").toBoolean();
							logger.debug(signatureDate+"1 bookMarkExist ------->"+bookMarkExist);
					}
			         if(bookMarkExist){
			        	 Dispatch rangeItem = Dispatch.call(bookMarks, "Item", signatureDate+"1")
		                         .toDispatch();
		                 Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
		                 Dispatch.put(range, "Text", info.getWorkCompletedDate());
			         }
			    Dispatch.call(word, "SaveAs",filePath);
	            if(is!=null){
	            	is.close();
	            }
	            DocUtil.addPrimaryContent(doc, filePath, appData.getFileName());
	            deleteTempleFile(file);
				  deleteTempleFile(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closedWordApp();
			logger.debug(" exit signatureWord");
		}
    }
    /**
     * 变更单签名
     * @description TODO
     * @param ecn
     * @author yge  2017年12月14日下午1:49:12
     */
   private void signatureChangeList(WTChangeOrder2 ecn) throws WTException{
	   QueryResult qr= ContentHelper.service.getContentsByRole(ecn,ContentRoleType.SECONDARY);
	   String folder = TEMP+File.separator+"signature";
		File f = new File(folder);
		if(!f.exists()){
			f.mkdirs();
		}
	   ContentItem contentitem = null;
  	 ApplicationData appData = null;
  	 
  	while(qr.hasMoreElements()){
		 contentitem =(ContentItem) qr.nextElement();
		 if(contentitem instanceof ApplicationData){
			 appData = (ApplicationData)contentitem;
			 String fileName = appData.getFileName();
			 System.out.println(fileName);
			 if(fileName.toUpperCase().endsWith("XLS") || fileName.toUpperCase().endsWith("XLSX")){
			     try {
			    	 String file = folder+File.separator+"orginal-"+appData.getFileName();
					 InputStream is = ContentServerHelper.service.findContentStream(appData);
					 saveToDisk(is,file);
					 is.close();
					String filePath = TEMP+File.separator+"signature"+File.separator+System.currentTimeMillis()+"-"+appData.getFileName();
					 initExcelApp();
					  workbook = Dispatch.call(workbooks, "Open",file).toDispatch();
				 String location1 = ChangeListLocation.getSignatureLocation(signatureRole);
				 String location2 = ChangeListLocation.getSignatureLocation(signatureDate);
				 writeExcel(1, location1, location2);	
				
				 Dispatch.call(workbook, "SaveAs",filePath);  
					ContentServerHelper.service.updateContent(ecn,appData, filePath);
					appData.setFileName(fileName);
					appData = (ApplicationData) PersistenceHelper.manager.modify(appData);
					  PersistenceServerHelper.manager.restore(ecn);
					  deleteTempleFile(file);
					  deleteTempleFile(filePath);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					  closedExcel();
				}
			 }
		 }
  	}
   }
  	private void deleteTempleFile(String path){
  		File file = new File(path);
  		if(file.exists()){
  			file.delete();
  		}
  	}
  	private void signaturePDF(WTDocument doc){
  		logger.debug(" enter signaturePDF");
  		try {
    		String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
    		ApplicationData appData = DocUtil.getPrimaryContent(doc);
			InputStream is = ContentServerHelper.service.findContentStream(appData);
			String folder = TEMP+File.separator+"signature";
			File f = new File(folder);
			if(!f.exists()){
				f.mkdirs();
			}
		
			String file = folder+File.separator+"orginal-"+appData.getFileName();
			 saveToDisk(is,file);
			 is.close();
			String filePath = TEMP+File.separator+"signature"+File.separator+doc.getNumber()+"-"+System.currentTimeMillis()+"-"+appData.getFileName();
			 signaturePDF(doc,file,filePath);
              DocUtil.addPrimaryContent(doc, filePath, appData.getFileName());
              deleteTempleFile(file);
			  deleteTempleFile(filePath);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		  logger.debug(" exit signaturePDF");
    	}
  	}
  	private void signaturePDF(Persistable object,String src,String dest){
  		File file = new File(dest);
        file.getParentFile().mkdirs();
        PdfDocument pdfDoc = null;
        PdfDocument template = null;
        Document document = null;
        Text text = null;
        Paragraph p = null;
        String name=null;
        String id = null;
        IBAUtil iba = null;
        try{
        	if(object instanceof WTDocument){
        		iba = new IBAUtil((WTDocument)object);
        		name = iba.getIBAValue(IBAConstants.FILENAME);
            	id = iba.getIBAValue(IBAConstants.FILEID);	
        	}else{
        		name = "";
            	id = "";	
        	}
        	
        	
        	PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);//中文设置
        	 pdfDoc =new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        	 template = new PdfDocument(new PdfReader(PDF_TEMPLATE));
    		 document = new Document(pdfDoc);
    		 if(SigConstants.FILE_NAME.equals(signatureRole)){
    			    text = new Text(name);
    				text.setBackgroundColor(Color.WHITE); 
    				p = new Paragraph(text);
    				p.setFont(font).setFontSize(17);
    				document.add(p.setFixedPosition(1, 290, 550, 600));
		   }else if(SigConstants.SIG_Designed.equals(signatureRole)){
			   pdfDoc.removePage(1);
			   template.copyPagesTo(1, 1, pdfDoc, 1);
				text = new Text(info.getWorkCompletedBy());
				text.setBackgroundColor(Color.WHITE); 
				 p = new Paragraph(text);
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 290, 495, 600));
		   }else if(SigConstants.SIG_Checked.equals(signatureRole)){
			   text = new Text(info.getWorkCompletedBy());
				text.setBackgroundColor(Color.WHITE); 
			   p = new Paragraph(text);
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 750, 495, 600));
		   }else if(SigConstants.SIG_HUIQIAN.equals(signatureRole)){
			   text = new Text(info.getWorkCompletedBy());
			   p = new Paragraph(text);
				text.setBackgroundColor(Color.WHITE); 
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 290, 437, 600));
		   }else if(SigConstants.SIG_Standardized.equals(signatureRole)){
			   text = new Text(info.getWorkCompletedBy());
				text.setBackgroundColor(Color.WHITE); 
			   p = new Paragraph(text);
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 750, 437, 600));
		   }else if(SigConstants.SIG_ChiefDesigner.equals(signatureRole)){
			   text = new Text(info.getWorkCompletedBy());
				text.setBackgroundColor(Color.WHITE); 
			   p = new Paragraph(text);
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 290, 380, 600));
		   }else if(SigConstants.SIG_Approved.equals(signatureRole)){
			   text = new Text(info.getWorkCompletedBy());
				text.setBackgroundColor(Color.WHITE); 
			   p = new Paragraph(text);
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 750, 380, 600));
		   }
    		 
    		 if(SigConstants.DATE_Designed.equals(signatureDate)){
			     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				text = new Text(df.format(new Date()));
				text.setBackgroundColor(Color.WHITE); 
				 p = new Paragraph(text);
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 520, 230, 600));
		  } else if(SigConstants.FILE_CODE.equals(signatureDate)){
				text = new Text(id);
				text.setBackgroundColor(Color.WHITE); 
				 p = new Paragraph(text);
				p.setFont(font).setFontSize(17);
				document.add(p.setFixedPosition(1, 750, 550, 600));
		  }
        }catch(Exception e){
        	
        }finally{
        	if(pdfDoc!=null){
        		pdfDoc.close();
        	}
        	if(document!=null){
        		document.close();
        	}
        }
	
  	}
    private void signatureExcel(WTDocument doc){
    	logger.debug(" enter signatureExcel");
    	try {
    		String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
    		ApplicationData appData = DocUtil.getPrimaryContent(doc);
			InputStream is = ContentServerHelper.service.findContentStream(appData);
			String folder = TEMP+File.separator+"signature";
			File f = new File(folder);
			if(!f.exists()){
				f.mkdirs();
			}
		
			String file = folder+File.separator+"orginal-"+appData.getFileName();
			 saveToDisk(is,file);
			 is.close();
			String filePath = TEMP+File.separator+"signature"+File.separator+doc.getNumber()+"-"+System.currentTimeMillis()+"-"+appData.getFileName();
			 initExcelApp();
			  workbook = Dispatch.call(workbooks, "Open",file).toDispatch();
			if(type.indexOf(TypeConstants.DOC_FX1)>-1){
				String location1 = FX1Location.getSignatureLocation(signatureRole);
				String location2 = FX1Location.getSignatureLocation(signatureDate);
				  writeExcel(2, location1, location2);
			}else if(type.indexOf(TypeConstants.DOC_YF_AQGJJB)>-1 || type.indexOf(TypeConstants.DOC_YF_BSJB)>-1 ||
				        type.indexOf(TypeConstants.DOC_YF_GJXB)>-1 ||type.indexOf(TypeConstants.DOC_YF_FAIB)>-1){
				String location1 = AQGJJBLocation.getSignatureLocation(signatureRole);
				String location2 = AQGJJBLocation.getSignatureLocation(signatureDate);
				  writeExcel(1, location1, location2);
			}else if(type.indexOf(TypeConstants.DOC_YF_TYML)>-1 || type.indexOf(TypeConstants.DOC_YF_WJML)>-1){
				String location1 = TYMLLocation.getSignatureLocation(signatureRole);
				String location2 = TYMLLocation.getSignatureLocation(signatureDate);
				writeExcel(1, location1, location2);
			}else if(type.indexOf(TypeConstants.DOC_FX2)>-1){
				String location1 = FX2Location.getSignatureLocation(signatureRole);
				String location2 = FX2Location.getSignatureLocation(signatureDate);
				writeExcel(1, location1, location2);
			}else if(type.indexOf(TypeConstants.DOC_YF_JSTZS)>-1){
				String location1 = JSTZSLocation.getSignatureLocation(signatureRole);
				writeExcel(1, location1, null);
			}else if(type.indexOf(TypeConstants.DOC_YF_ZYDFJ)>-1){
				String location1 = ZYDFJLocation.getSignatureLocation(signatureRole);
				String location2 = ZYDFJLocation.getSignatureLocation(signatureDate);
				  writeExcel(1, location1, location2);
			}
			
			 Dispatch.call(workbook, "SaveAs",filePath);  
              DocUtil.addPrimaryContent(doc, filePath, appData.getFileName());
              deleteTempleFile(file);
			  deleteTempleFile(filePath);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		  closedExcel();
    		  logger.debug(" exit signatureExcel");
    	}
    }
    private void writeExcel(int sheetIndex,String location1,String location2){
    	if(StringUtil.isEmpty(location1)){
    		return;
    	}
    	  Dispatch.put(workbook, "CheckCompatibility", false); //取消兼容性检查，在保存或者另存为时改检查会导致弹窗 
		  Dispatch sheets = Dispatch.get(workbook, "sheets").toDispatch();  
		  //从第二个sheet页 （sheet index从1开始）
		  Dispatch sheet= Dispatch.invoke(sheets, "Item", Dispatch.Get, new Object[]{sheetIndex}, new int[1]).toDispatch();
		 logger.debug("sheet name----->"+Dispatch.get(sheet, "name").toString());
		 
		  //向单元格设签名
		  Dispatch cell = Dispatch.invoke(sheet, "Range",  
	                Dispatch.Get, new Object[] {location1}, new int[1])  
	                .toDispatch();  
		  //插入图片
	        Dispatch.call(sheet, "Select");
	        Dispatch.call(cell, "Select"); //定位插入图片的具体位置
	        Dispatch select = Dispatch.call(sheet, "Pictures").toDispatch();
//	        if(select!=null){
//	        	Dispatch.call(select, "Delete"); 
//	        }
	        
	     float top = Dispatch.get(cell, "Top").toFloat();
   		 float left = Dispatch.get(cell, "Left").toFloat();
   		 String shapeName = (top+left+3)+"";
   		Dispatch Shapes = Dispatch.get(sheet,"Shapes").toDispatch();
   		try{
		   		 Dispatch deleteShape= Dispatch.invoke(Shapes, "Range", Dispatch.Get, new Object[]{shapeName}, new int[1]).toDispatch();
		   		logger.debug("deleteShape------>"+deleteShape);
				  if(deleteShape!=null){
					  Dispatch.call(deleteShape, "Delete");
				  }
   		}catch(Exception e){
   			e.printStackTrace();
   		}
	     
		  File imageFile = new File(IMAGE+File.separator+info.getWorkCompletedBy()+".bmp");
		  logger.debug("imageFile.exists()-------->"+imageFile.exists());
        	 if(imageFile.exists()){
        		 Dispatch.put(cell, "Value", "");  
        		 Dispatch shape = Dispatch.call(Shapes, "AddPicture", IMAGE+File.separator+info.getWorkCompletedBy()+".bmp",
        				 true,true,left+2,top+1,45,15).toDispatch();
        		 Dispatch.put(shape, "Name", shapeName);
//     	        Dispatch picture = Dispatch.call(select, "Insert",IMAGE+File.separator+info.getWorkCompletedBy()+".bmp").toDispatch();
//     	        Dispatch.put(picture, "Width", 30);
//                 Dispatch.put(picture, "Height", 15);
        	 }else{
        		 Dispatch.put(cell, "Value", info.getWorkCompletedBy());  
        	 }
        	 
        	 //设置日期
        	 if(StringUtil.isNotEmpty(location2)){
        		 cell = Dispatch.invoke(sheet, "Range",  
      	                Dispatch.Get, new Object[] {location2}, new int[1])  
      	                .toDispatch();  
             	 Dispatch.put(cell, "Value", info.getWorkCompletedDate());  
        	 }
    }
    private void setBmpPicture(Workbook wb,Sheet sheet,int rowIndex,int columnIndex,String path){
    	logger.debug(" ------------>enter setBmpPicture");
    	if(sheet==null){
    		return;
    	}
    	Row row = sheet.getRow(rowIndex);
    	if(row ==null){
    		row = sheet.createRow(rowIndex);
    	}
    	Cell cell = row.getCell(columnIndex);
    	if(cell==null){
    		cell = row.createCell(columnIndex);
    	}
    	
         BufferedImage bufferImg =null; 
         try{
        	 ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
        	 bufferImg = ImageIO.read(new File(path)); 
        	 ImageIO.write(bufferImg,"bmp",byteArrayOut); 
        	 
        	 
        	 Drawing patriarch = sheet.createDrawingPatriarch();
        	 ClientAnchor anchor = new HSSFClientAnchor(0,0,800,250,(short)columnIndex,rowIndex,(short)columnIndex,rowIndex); 
             patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),Workbook.PICTURE_TYPE_PNG)); 
             
          }catch (Exception e) {
 			// TODO: handle exception
 		}
     	logger.debug(" ------------>exit setBmpPicture");
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
    
    
}
