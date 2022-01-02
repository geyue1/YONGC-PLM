/**
 * ext.yongc.plm.part.rule.QitaoxingHelper.java
 * @Author yge
 * 2017年11月29日上午11:05:23
 * Comment 
 */
package ext.yongc.plm.part.rule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ptc.core.meta.common.TypeIdentifierHelper;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;

import wt.content.ApplicationData;
import wt.content.ContentServerHelper;
import wt.content.ContentService;
import wt.doc.WTDocument;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartStandardConfigSpec;
import wt.part.WTPartUsageLink;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.views.ViewException;
import wt.vc.views.ViewHelper;
import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.report.BOMReportHelper;
import ext.yongc.plm.util.DocUtil;
import ext.yongc.plm.util.ExcelUtility;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.StringUtil;

public class QitaoxingHelper {
	Logger log = LogR.getLogger(QitaoxingHelper.class.getName());
	
	private static WTPartStandardConfigSpec standardConfigSpec = null;
	private static  final String QITAOXING_CONFIG_NAME = "齐套性检查配置文件";
	private static  final String CONFIG_COLUMN1 = "序号";
	private static  final String CONFIG_COLUMN2 = "文件内部名称";
	private static  final String CONFIG_COLUMN3 = "文件显示名称";
	private static  final String CONFIG_COLUMN4 = "说明";
	private static  final String CONFIG_COLUMN5 = "完整性";
	private static final String END_TAG = "#EOF";
	
	static{
				try {
					standardConfigSpec = 
							WTPartStandardConfigSpec.newWTPartStandardConfigSpec(
									ViewHelper.service.getView("Design"), null);
				} catch (ViewException e) {
					e.printStackTrace();
				} catch (WTException e) {
					e.printStackTrace();
				}
		}
	private List<String> checkConfigFile() throws WTException, InvalidFormatException, IOException{
		List<String> result = new ArrayList<String>();
		WTDocument doc = DocUtil.getDocumentByName(QITAOXING_CONFIG_NAME);
		if(doc==null){
			result.add("系统中没有齐套性检查配置文件。请上传齐套性检查配置文件，并且其名称应为:"+QITAOXING_CONFIG_NAME);
		}else{
			ApplicationData appData = DocUtil.getPrimaryContent(doc);
			if(appData==null){
				result.add("系统中没有齐套性检查配置文件。请上传齐套性检查配置文件，并且其名称应为:"+QITAOXING_CONFIG_NAME+"。"
						+ "该配置文件没有主内容，请上传主内容。");
			}
			if(appData!=null){
				String fileName = appData.getFileName();
				if(!fileName.toUpperCase().endsWith("XLS") && !fileName.toUpperCase().endsWith("XLSX")){
					result.add("系统中没有齐套性检查配置文件。<br>请上传齐套性检查配置文件，并且其名称应为:"+QITAOXING_CONFIG_NAME+"。"
							+ "<br>该配置文件主内容格式应该为Excel，请重新上传。");
				}else{
					InputStream is = ContentServerHelper.service.findContentStream(appData);
					Workbook wb = WorkbookFactory.create(is);
					ExcelUtility eu = new ExcelUtility();
					eu.setWb(wb);
					String c1= eu.getCellContent(1, 0);
					String c2 = eu.getCellContent(1, 1);
					String c3 = eu.getCellContent(1, 2);
					String c4 = eu.getCellContent(1, 3);
					String c5 = eu.getCellContent(1, 4);
					if(!CONFIG_COLUMN1.equals(c1) || !CONFIG_COLUMN2.equals(c2)||!CONFIG_COLUMN3.equals(c3)
							||!CONFIG_COLUMN4.equals(c4)||!CONFIG_COLUMN5.equals(c5)){
						result.add("系统中没有齐套性检查配置文件。<br>请上传齐套性检查配置文件，并且其名称应为:"+QITAOXING_CONFIG_NAME+"。"
								+ "<br>该配置文件主内容格式错误，请重新上传。<br>正确格式应该为第二行为标题，<br>第一列标题为："+CONFIG_COLUMN1
								+",<br>第二列标题为："+CONFIG_COLUMN2+",<br>第三列标题为："+CONFIG_COLUMN3+",<br>第四列标题为："+CONFIG_COLUMN4
								+",<br>第五列标题为："+CONFIG_COLUMN5);
					}
				}
			}
		}
		return result;
	}
	public List<Map<String,String>> processCheck(Map<String, String> formData) throws WTException, InvalidFormatException, IOException{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		WTDocument doc = DocUtil.getDocumentByName(QITAOXING_CONFIG_NAME);
		ApplicationData appData = DocUtil.getPrimaryContent(doc);
		InputStream is = ContentServerHelper.service.findContentStream(appData);
		Workbook wb = WorkbookFactory.create(is);
		ExcelUtility eu = new ExcelUtility();
		eu.setWb(wb);
		int rowCount = eu.getRows();
		String innerName = null;
		String displayName = null;
		String isForce = null;
		String index = null;
	
		
		String partNumber = formData.get("objNumber");
		Map<String,List<DocItem>> docItemMap = getDocs(partNumber);
		Iterator it = docItemMap.keySet().iterator();
		for(int i=0;i<rowCount;i++){
			index = eu.getCellContent(i+2, 0);
			innerName = eu.getCellContent(i+2, 1);
			displayName = eu.getCellContent(i+2, 2);
			log.debug("displayName-------->"+displayName);
			isForce = eu.getCellContent(i+2, 4);
			if(END_TAG.equals(index)){
				break;
			}
			if(StringUtil.isEmpty(innerName)){
				continue;
			}
			StringBuffer docName  = new StringBuffer();
			StringBuffer partName = new StringBuffer();
			while(it.hasNext()){
				String key =(String) it.next();
				if(key.contains(innerName)){
					List<DocItem> itemList = docItemMap.get(key);
					for(int j=0;j<itemList.size();j++){
						DocItem item = itemList.get(j);
						docName.append(item.getNumber()+","+item.getName()+"<br>");
						if(!partName.toString().contains(item.getPartName())){
							partName.append(item.getPartName()+"<br>");
						}
					}
					break;
				}
			}
		
			Map<String,String> map = new HashMap<String,String>();
			map.put("index", i+1+"");
			map.put("displayName", displayName);
			if("1".equals(isForce)){
				map.put("isForce","Δ");
			}else{
				map.put("isForce","");
			}
			
			if(StringUtil.isNotEmpty(docName.toString())){
				map.put("checkResult","有");
				map.put("docName",docName.toString());
				map.put("partName",partName.toString());
			}else{
				map.put("checkResult","<font color=\"#B22222\">无</font>");
				map.put("docName","");
				map.put("partName","");
			}
			
			result.add(map);
		}
		
		return result;
	}
	/**
	 * 
	 * @description TODO
	 * @param partNumber
	 * @return map :key是文档内部名称
	 * @author yge  2017年12月16日下午3:54:49
	 * @throws RemoteException 
	 */
	private Map<String,List<DocItem>> getDocs(String partNumber) throws WTException, RemoteException{
	     Map<String,List<DocItem>> resultMap = new HashMap<String,List<DocItem>>();
		   Map<String,String> ibaMap = new HashMap<String,String>();
    	   ibaMap.put(IBAConstants.DRAWINGNO, partNumber.trim());
    	   Vector vec=PartUtil.getPartsByTypeAndIBA("wt.part.WTPart", ibaMap, true);
    	   WTPart  part =(WTPart) vec.get(0);
           
    	   List<WTPart> partList =new ArrayList<WTPart>();
    	   partList.add(part);
    	   partList.addAll(getAllChildPart(part)) ;
    	   
    	   List<DocItem> docList = new ArrayList<DocItem>();
    	   WTDocument doc = null;
    	   String docType = null;
    	   for(int i=0;i<partList.size();i++){
    		   part = partList.get(i);
    		   List<WTDocument> partDocList = PartUtil.getDescDocByPart(part);
    		   for(int k=0;k<partDocList.size();k++){
    			   doc = partDocList.get(k);
    			   docType = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
    			   DocItem item = new DocItem();
    			   item.setName(doc.getName());
    			   item.setNumber(doc.getNumber());
    			   item.setPartName(part.getName());
    			   item.setDocType(docType);
    			   docList.add(item);
    		   }
    	   }
    	   log.debug("docList size------------------>"+docList.size());
    	
    	   for(int i=0;i<docList.size();i++){
    		   DocItem item = docList.get(i);
    		   docType = item.getDocType();
    		   if(!resultMap.containsKey(docType)){
    			   List<DocItem> itemList = new ArrayList<DocItem>();
    			   itemList.add(item);
    			   resultMap.put(docType, itemList);
    		   }else{
    			   resultMap.get(docType).add(item);
    		   }
    	   }
	     
	     return resultMap;
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
            
             //检查系统中是否有齐套性配置文件
             result.addAll(checkConfigFile());
         } catch (Exception e) {
             throw new WTException(e);
         } finally {
             SessionServerHelper.manager.setAccessEnforced(flag);
         }

         return result;
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
