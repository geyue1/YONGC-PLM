/**
 * ext.yongc.plm.signature.Test2.java
 * @Author yge
 * 2017年7月31日上午9:25:51
 * Comment 
 */
package ext.yongc.plm.signature;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import wt.epm.EPMDocument;
import wt.epm.build.EPMBuildRule;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.generic.GenericHelper;
import wt.identity.IdentityFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.rule.init.StandardInitRuleEvalService;
import wt.services.applicationcontext.ApplicationContext;
import wt.services.applicationcontext.ApplicationContextFactory;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WorkItem;

import com.ptc.core.components.beans.DataUtilityBean;
import com.ptc.core.components.factory.dataUtilities.InfoPageTabSetDataUtility;
import com.ptc.core.components.factory.dataUtilities.NmHTMLActionModelDataUtility;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.AffectedItemsTableBuilder;
import com.ptc.windchill.enterprise.part.validators.RelatedItemsPartsValidator;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WCUtil;
import ext.yongc.plm.util.WorkItemInfoBean;
import ext.yongc.plm.util.WorkflowUtil;

public class Test2 implements RemoteAccess{
	 public static RemoteMethodServer getRemoteMethodServer(String hostUrl, String userName,
		      String passwd) throws MalformedURLException {
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
   public static void main(String[] args) throws Exception{
   	String hostUrl = "http://plm.yongc.com/Windchill/";
		String userName="wcadmin";
		String passwd = "wcadmin";
		RemoteMethodServer rs = getRemoteMethodServer(hostUrl, userName, passwd);
		if(StringUtil.isEmpty(args[0])){
			System.out.println("please input oid . . .");
			System.exit(0);
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> start ");
		Class[] c = {String.class};
		Object[] o = {args[0]};
		  rs.invoke("test2", Test2.class.getName(), null, c, o);
		  
		  
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> end ");
   }
   public static void test2(String oid)throws WTException{
	   ReferenceFactory rf = new ReferenceFactory();
	   EPMDocument epm = (EPMDocument)rf.getReference(oid).getObject();
	   IBAUtil iba = new IBAUtil(epm);
		 String gongyi = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
		 QueryResult qr=PersistenceHelper.manager.navigate(epm, EPMBuildRule.BUILD_TARGET_ROLE, EPMBuildRule.class);
//		 qr = PersistenceHelper.manager.navigate(parent, EPMBuildRule.BUILD_SOURCE_ROLE, EPMBuildRule.class);
//		 QueryResult res = PersistenceHelper.manager.find(wt.epm.build.EPMBuildRule.class,part,"buildTarget",doc);
	      System.out.println(" qr------------->"+qr.size());
	      while(qr.hasMoreElements()){
	    	  Object obj =qr.nextElement();
	    	  if(obj instanceof WTPart){
	    		  WTPart part = (WTPart)obj;
	    		  System.out.println(IdentityFactory.getDisplayIdentifier(part));
	    		  part = (WTPart) WCUtil.doCheckOut(part);
	    		  iba = new IBAUtil(part);
	    		  iba.setIBAAnyValue(part, IBAConstants.GONGYI_TYPE, gongyi);
	    		  part = (WTPart) WCUtil.doCheckIn(part, "设置工艺分配属性值为:"+gongyi);
	    	  }
	      }
   }
   public static void test(String oid) throws WTRuntimeException, WTException{
   	   ReferenceFactory rf = new ReferenceFactory();
   	   WorkItem item = (WorkItem) rf.getReference(oid).getObject();
   	   WfProcess p = WorkflowUtil.getProcessByObject(item);
   	   List list = WorkflowUtil.getProcessRoutingHistory(p);
   	   for(int i=0;i<list.size();i++){
   		   WorkItemInfoBean bean = (WorkItemInfoBean)list.get(i);
   		   System.out.println(bean);
   	   }
   	
   }
}