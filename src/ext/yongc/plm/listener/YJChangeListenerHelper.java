/**
 * ext.yongc.plm.listener.YJChangeListenerHelper.java
 * @Author yge
 * 2017年8月21日下午9:05:26
 * Comment 
 */
package ext.yongc.plm.listener;


import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.util.ExcelUtility;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.WorkflowUtil;
import wt.change2.ChangeHelper2;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.engine.WfProcess;

public class YJChangeListenerHelper {
	
     private WTChangeOrder2 ecn;
     Logger logger = LogR.getLogger(YJChangeListenerHelper.class.getName());
 	private static String TEMP = "";
 	 static {
	        try {
	            WTProperties wtproperties = wt.util.WTProperties
	                    .getLocalProperties();
	            TEMP = wtproperties.getProperty("wt.temp");
	        } catch (Throwable throwable) {
	            throw new ExceptionInInitializerError(throwable);
	        }
	    }
     public YJChangeListenerHelper(WTChangeOrder2 ecn){
    	 this.ecn =ecn;
     }
    
     public void updateSecondaryConetent() throws WTException, FileNotFoundException, PropertyVetoException, IOException{
    	 logger.debug("enter updateSecondaryConetent");
    	 if(ecn==null){
    		 logger.debug(" ecn is null , exit.");
    		 return;
    	 }
    	 QueryResult qr= ContentHelper.service.getContentsByRole(ecn,ContentRoleType.SECONDARY);
    	 logger.debug(" qr.size---------------->"+qr.size());
    	 ContentItem contentitem = null;
    	 ApplicationData applicationdata = null;
    	 while(qr.hasMoreElements()){
    		 contentitem =(ContentItem) qr.nextElement();
    		 if(contentitem instanceof ApplicationData){
    			 applicationdata = (ApplicationData)contentitem;
    			 String fileName = applicationdata.getFileName();
    			 logger.debug(fileName);
    			 if(fileName.toUpperCase().endsWith("XLS") || fileName.toUpperCase().endsWith("XLSX")){
    					InputStream is = ContentServerHelper.service.findContentStream(applicationdata);
    					String path =TEMP+File.separator+"signature"+File.separator+fileName;
    					updateConetent(is,path);
    			     ContentServerHelper.service.updateContent(ecn,applicationdata, path);
    			     applicationdata.setFileName(fileName);
    			     PersistenceHelper.manager.modify(applicationdata);
    			     PersistenceServerHelper.manager.restore(ecn);
    			 }
    		 }
    	 }
    	 logger.debug("exit updateSecondaryConetent");
     }
     private void updateConetent(InputStream is ,String path ) throws WTException{
    	 try {
    			Workbook wb = WorkbookFactory.create(is);
				ExcelUtility eu = new ExcelUtility();
				eu.setWb(wb);
				IBAUtil iba = new IBAUtil(ecn);
				String ztdh = iba.getIBAValue(IBAConstants.DRAWINGNO);
				String xhmc = iba.getIBAValue(IBAConstants.PRODUCTNAME);
				String unit = iba.getIBAValue(IBAConstants.UNIT);
				String changeResion = iba.getIBAValue(IBAConstants.NOTE);
				String fsdw = iba.getIBAValue(IBAConstants.LLDWMC);
				eu.setStringValue(3, 0, ztdh);
				eu.setStringValue(3, 2, xhmc);
				eu.setStringValue(0, 10, ecn.getName());
				eu.setStringValue(1, 10, unit);
				eu.setStringValue(2, 10, unit);
				eu.setStringValue(4, 10, changeResion);
				eu.setStringValue(7, 4, fsdw);
			     FileOutputStream out = new FileOutputStream(path); 
		         wb.write(out); 
		         out.flush();
		         out.close();  
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
     }
     public void process() throws Exception{
    	 if(ecn==null){
    		 logger.debug(" ecn is null , exit.");
    		 return;
    	 }
    	 boolean flag =false;
    	 String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
    	 WfProcessTemplate template = null;
    	 QueryResult templateQR= WfDefinerHelper.service.getEnabledTemplates(ecn.getContainerReference());
    	 logger.debug("type=============>"+type);
    	 if(type.indexOf(TypeConstants.ECN_3DDesign)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_3DDesignDoc.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_MBOM)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_MBOM.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_YFDOC)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_YFDOC.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_MB1)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_MB1.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_MB2)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_MB2.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_TYWJML)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_TYWJML.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_FX1)>-1 || type.indexOf(TypeConstants.ECN_ZYDFJ)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_FX1.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_FX2)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_FX2.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_GY)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_GY.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_GZ)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_GZ.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }else if(type.indexOf(TypeConstants.ECN_JSTZS)>-1){
    		 flag = true;
			 while(templateQR.hasMoreElements()){
				 WfProcessTemplate temp =(WfProcessTemplate) templateQR.nextElement();
				 if(WorkflowConstants.WF_ECN_JSTZS.equals(temp.getName())){
					 template = temp;
					 break;
				 }
			 }
    	 }
    	// delete ca process
			QueryResult qr = ChangeHelper2.service.getChangeActivities(ecn);
			logger.debug("flag============>"+flag);
			while(flag && qr.hasMoreElements()){
				WTChangeActivity2 ca = (WTChangeActivity2)qr.nextElement();
				logger.debug("ca name is -------->"+ca.getName());
				WfProcess caprocess = WorkflowUtil.getProcessByObject(ca);
				logger.debug("caprocess is -------->"+caprocess);
				if(caprocess!=null){
					PersistenceHelper.manager.delete(caprocess);
				}
			}
			
			//delete ecn process
			WfProcess ecnprocess = WorkflowUtil.getProcessByObject(ecn);
			if(flag && ecnprocess!=null){
				PersistenceHelper.manager.delete(ecnprocess);
			}
			 logger.debug("template ----------------->"+template);
          // start customization process
			if(template!=null){
				WorkflowUtil.startWorkflow(ecn, template);
			}
     }
     
  
}
