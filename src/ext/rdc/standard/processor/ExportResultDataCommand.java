/**
 * ext.rdc.standard.processor.ExportResultDataCommand.java
 * @Author yge
 * 2019年10月14日上午10:13:40
 * Comment 
 */
package ext.rdc.standard.processor;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wt.fc.ReferenceFactory;
import wt.log4j.LogR;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.util.HTMLEncoder;
import wt.util.WTException;

import com.infoengine.object.factory.Element;
import com.ptc.core.components.export.table.ExportException;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.server.TypeIdentifierUtility;
import com.ptc.netmarkets.model.NmObjectHelper;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.misc.TempFileCleaner;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;

import ext.solr.test.IBAUtil;

public class ExportResultDataCommand {
	
	private static Logger log = LogR.getLogger(ExportResultDataCommand.class.getName());
	private static ReferenceFactory rf = new ReferenceFactory();
	
	 public static FormResult export(NmCommandBean commandBean) throws Exception {
		 ExportResultDataCommand operation = new ExportResultDataCommand();
		 return operation.doExport(commandBean);
	 }
	 public FormResult doExport(NmCommandBean commandBean) throws Exception {
		 log.debug(" enter doExport");
		 FormResult result = new FormResult();
		 List<WTPart> partList = new ArrayList<WTPart>();
		 List<String> attrList = new ArrayList<String>();
		 List<String> attrNameList = new ArrayList<String>();
		 
		 log.debug("getSelected=>"+commandBean.getSelected());
		 log.debug("getSelectedOidForPopup=>"+commandBean.getSelectedOidForPopup());
		 log.debug("getNmOidSelected=>"+commandBean.getNmOidSelected());
		 log.debug("getNmOidSelectedInOpener=>"+commandBean.getNmOidSelectedInOpener());
		 
		 ResultModel resultModel = (ResultModel)commandBean.getRequest().getSession().getAttribute(PartslinkConstants.Model_IDS.RESULT_MODEL);
		 log.debug("resultModel>"+resultModel);
		 
		 if(resultModel!=null){
			 List<Element> searchResults = new ResultModelServiceImpl().getResults(resultModel);
				if (searchResults != null) {
					for (Element eleResult : searchResults) {
						String partKeys = (String) eleResult.getValue("obid");
						WTPart part = (WTPart)rf.getReference(partKeys).getObject();
						partList.add(part);
					}
				}
				
				RefineModel model =(RefineModel) commandBean.getRequest().getSession().getAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL);
				TypeDefinitionReadView clfNodeReadView = model.getTypeDefRV();
				Collection<AttributeDefinitionReadView> typeAttributes = clfNodeReadView.getAllAttributes();
				for (AttributeDefinitionReadView attributeDefReadView : typeAttributes) {
					if (!attributeDefReadView.isSystemHidden()) {
						String attributeId = attributeDefReadView.getIBARefView().getLogicalIdentifier();
						if (attributeId == null || attributeId.isEmpty()) {
							AttributeTypeIdentifier ati = attributeDefReadView.getAttributeTypeIdentifier();
							ati = ati.getWithNewContext(TypeIdentifierUtility.getTypeIdentifier("wt.part.WTPart"));
							attributeId = ati.toExternalForm();
						}
						attrList.add(attributeId);
						attrNameList.add(HTMLEncoder.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(attributeDefReadView,
								SessionHelper.getLocale())));
					}
				}
		 }
		 log.debug("partList size = >"+partList.size());
		 
		 File file = exportExcel(partList, attrList, attrNameList, commandBean);
		 WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 log.debug("file=>"+file.getPath());
		 String fileName = "数据导出_"+user.getFullName()+"_"+df.format(new Date().getTime()+8*3600*1000)+".xlsx";
		 String url = getDownloadUrl(commandBean, file, fileName).toExternalForm();
		   log.debug("url=>"+url);
		    Map<String, Object> extraData = new HashMap <String, Object> ();
	        extraData.put ("downloadNow", true);
	        extraData.put ("url", url);
	        result.setExtraData (extraData);
		 
		 log.debug(" exit doExport");
		 return result;
	 }
	 private File exportExcel(List<WTPart> partList,List<String> attrList,List<String> attrNameList,NmCommandBean commandBean) throws WTException, InvalidFormatException, IOException{
		 File file = getTemporaryFile(commandBean);
		 Workbook wb = new XSSFWorkbook();
		 Sheet sheet = wb.createSheet("Default");
		 Row row = sheet.createRow(0);
		 Cell cell = null;
		 writeHeader(row,attrNameList);
		 
		 for(int i=0;i<partList.size();i++){
			 row = sheet.createRow(i+1);
			 WTPart part = partList.get(i);
			 IBAUtil iba = new IBAUtil(part);
			 
			 cell = row.createCell(0);
			 cell.setCellValue(part.getNumber());
			 cell = row.createCell(1);
			 cell.setCellValue(part.getName());
			 cell = row.createCell(2);
			 cell.setCellValue(iba.getIBAValue("partTypes"));
			 
			 for(int j=0;j<attrList.size();j++){
				 cell = row.createCell(3+j);
				 cell.setCellValue(iba.getIBAValue(attrList.get(j)));
			 }
		 }
		 FileOutputStream os = new FileOutputStream(file);
		 wb.write(os);
		 close(os);
		 wb.close();
		 return file;
	 }
	 private void close(FileOutputStream os) throws ExportException {
	        try {
	        	if(os!=null){
	        		os.flush();
	        		os.close();
	        	}
	        } catch (IOException ioe) {
	            throw new ExportException(ioe);
	        }
	    }
	 private void writeHeader(Row row,List<String> attrNameList){
		  Cell cell = row.createCell(0);
		  cell.setCellValue("编号");
		  cell = row.createCell(1);
		  cell.setCellValue("名称");
		  cell = row.createCell(2);
		  cell.setCellValue("零部件类型");
		  for(int i=0;i<attrNameList.size();i++){
			  cell = row.createCell(3+i);
			  cell.setCellValue(attrNameList.get(i));
		  }
		  
	 }
	 
	 
	 
	  protected File getTemporaryFile(NmCommandBean commandBean) throws ExportException  {
	        File file = (File) commandBean.getRequest().getSession().getServletContext().getAttribute(
	                "javax.servlet.context.tempdir");
	        File f = null;
	        try {
	        	 f = File.createTempFile("exportList", ".tmp", file);
	            // temp file will be deleted by download code or this session
	            // listener if never completely downloaded.

	            TempFileCleaner.register(commandBean.getRequest().getSession(), f);
	        } catch (Exception e) {
	            throw new ExportException(e);
	        }
	        return f;
	    }
	  /**
	     * This gives the file path to where the browser can download the specific file. the default content type of
	     * application/octet-stream works for almost everything but not always for CSV/Text. In chrome frame 
	     * configuration it can cause duplicate requests and a resulting redirect error as the file ( the url ) is not present on second request 
	     */
	  protected URL getDownloadUrl(NmCommandBean commandBean,File f,String fileName) throws ExportException {
	        try {
		    		HashMap<String, String> map = new HashMap<String, String>();
		    		map.put("content-type", "application/msexcel;");
		    		return NmObjectHelper.constructOutputURL(f, fileName, map);
	        } catch (WTException e) {
	            throw new ExportException(e);
	        }
	    }
}
