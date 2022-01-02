/**
 * ext.yongc.plm.report.TestReport.java
 * @Author yge
 * 2017年11月13日下午3:55:37
 * Comment 
 */
package ext.yongc.plm.report;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ptc.carambola.tags.DataHelper;
import com.ptc.core.components.beans.DataUtilityBean;
import com.ptc.core.components.factory.dataUtilities.InfoPageTabSetDataUtility;
import com.ptc.core.components.factory.dataUtilities.NmHTMLActionModelDataUtility;
import com.ptc.core.components.util.OidHelper;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.AffectedItemsTableBuilder;
import com.ptc.windchill.enterprise.part.validators.RelatedItemsPartsValidator;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.util.ExcelUtility;
import ext.yongc.plm.util.IBAUtil;
import wt.change2.WTChangeOrder2;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.dataops.containermove.ContainerMoveHelper;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.collections.WTCollection;
import wt.fc.collections.WTValuedHashMap;
import wt.fc.collections.WTValuedMap;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.generic.GenericHelper;
import wt.identity.IdentityFactory;
import wt.inf.container.WTContainerRef;
import wt.inf.library.WTLibrary;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.rule.init.StandardInitRuleEvalService;
import wt.util.WTException;

public class TestReport implements RemoteAccess{
 
	  public static void testTuyangmulu(EPMDocument epm) throws WTException{
		  TuyangmuluReportHelper helper = new TuyangmuluReportHelper();
		  List<EPMDocument> list = helper.getChildEPMDocument(epm);
		  DataHelper h=null;
		  for(int i=0;i<list.size();i++){
			  EPMDocument e = (EPMDocument) list.get(i);
			  System.out.println("e------>"+IdentityFactory.getDisplayIdentifier(e));
		  }
	  }
	  public static RemoteMethodServer getRemoteMethodServer(String hostUrl, String userName,
		      String passwd) throws MalformedURLException, WTException {
		 DataUtilityBean db = null ;
		 GenericHelper gh = null;
		 InfoPageTabSetDataUtility op=null;
		 NmHTMLActionModelDataUtility am = null;
		    RemoteMethodServer rms = null;
		    StandardInitRuleEvalService sr = null;
		    AffectedItemsTableBuilder b = null;
		    RelatedItemsPartsValidator pv = null;
		    if(hostUrl==null){
		      rms = RemoteMethodServer.getDefault();
		    }else{
		      rms = RemoteMethodServer.getInstance(new URL(hostUrl));
		      rms.setUserName(userName);
		      rms.setPassword(passwd);
		    }
		  
		    return rms;
		  }

	/**
	 * @description TODO
	 * @param args
	 * @author yge  2017年9月23日下午5:42:54
	 * 
	 */
	public static void main(String[] args) throws Exception{
	 	String hostUrl = "http://plm.yongc.com/Windchill/";
			String userName="wcadmin";
			String passwd = "wcadmin";
			RemoteMethodServer rs = getRemoteMethodServer(hostUrl, userName, passwd);
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> start ");
			String oid = "OR:wt.change2.WTChangeOrder2:651725";
			System.out.println("oid==="+oid);
			ReferenceFactory rf = new ReferenceFactory();
			
			WTChangeOrder2 ecn = (WTChangeOrder2)rf.getReference(oid).getObject();
			  System.out.println("ecn------>"+IdentityFactory.getDisplayIdentifier(ecn));
			Class[] c = {WTChangeOrder2.class};
			Object[] o = {ecn};
			 rs.invoke("getSecondary", TestReport.class.getName(), null, c, o);
			  
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> end ");
			
				                      


	}
	  public static void updateConetent(WTChangeOrder2 ecn,InputStream is ,String path) throws WTException{
	    	System.out.println(" start updateConetent");
//	    	String path = "D:\\ptc\\Windchill_10.2\\Windchill\\temp\\";
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
	public static void getSecondary(WTChangeOrder2 ecn) throws WTException, FileNotFoundException, PropertyVetoException, IOException{
		 QueryResult qr= ContentHelper.service.getContentsByRole(ecn,ContentRoleType.SECONDARY);
    	 System.out.println(" qr.size---------------->"+qr.size());
    	 ContentItem contentitem = null;
    	 ApplicationData applicationdata = null;
    		String path = "D:\\ptc\\Windchill_10.2\\Windchill\\temp\\";
    	 while(qr.hasMoreElements()){
    		 contentitem =(ContentItem) qr.nextElement();
    		 if(contentitem instanceof ApplicationData){
    			 applicationdata = (ApplicationData)contentitem;
    			 String fileName = applicationdata.getFileName();
    			 System.out.println(fileName);
    			 if(fileName.toUpperCase().endsWith("XLS") || fileName.toUpperCase().endsWith("XLSX")){
    					InputStream is = ContentServerHelper.service.findContentStream(applicationdata);
    					path +=fileName;
    					updateConetent(ecn,is,path);
    			     
    			     ContentServerHelper.service.updateContent(ecn,applicationdata, path);
    			     PersistenceServerHelper.manager.restore(ecn);
    			 }
    		 }
    	 }
    	 System.out.println("exit updateSecondaryConetent");
	}
	public static WTLibrary getLibraryByName() throws WTException{
		 QuerySpec qs = new QuerySpec(WTLibrary.class);
		    SearchCondition sc = new SearchCondition(WTLibrary.class,WTLibrary.NAME,"=",TypeConstants.LIBRARY_WGJ);
		    qs.appendWhere(sc);
		    QueryResult qr = PersistenceHelper.manager.find(qs);
		    WTLibrary ll = null;
		    while(qr.hasMoreElements()){
		    	ll = (WTLibrary)qr.nextElement();
		    }
		    return ll;
	}
	public static void moveContent() throws WTException{
		String path = "/外购件库/外购件1";
		WTLibrary ll= getLibraryByName();
		if(ll!=null){
			path = path.replace(TypeConstants.LIBRARY_WGJ, "Default");
			System.out.println(" pp path------->"+path);
			WTContainerRef c_ref = WTContainerRef.newWTContainerRef(ll);
			Folder folder = FolderHelper.service.getFolder(path, c_ref);
			//ObjectIdentifier oidDoc =  ObjectIdentifier.newObjectIdentifier("wt.doc.WTDocument:591205");
			ReferenceFactory rf = new ReferenceFactory();
			WTDocument doc = (wt.doc.WTDocument)  rf.getReference("VR:wt.doc.WTDocument:591205").getObject();
			WTValuedMap objFolderMap = new WTValuedHashMap(1);
			objFolderMap.put(doc, folder);
			WTCollection col = ContainerMoveHelper.service.moveAllVersions(objFolderMap);
		}
	}

}
