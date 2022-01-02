/**
 * ext.yongc.plm.part.StandardYJWGJService.java
 * @Author yge
 * 2017年11月23日上午9:33:10
 * Comment 
 */
package ext.yongc.plm.part;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifierHelper;

import ext.yongc.plm.change.StandardYJChangeService;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.part.link.WGJPartLink;
import ext.yongc.plm.util.ChangeUtil;
import ext.yongc.plm.util.StringUtil;
import wt.change2.WTChangeOrder2;
import wt.dataops.containermove.ContainerMoveHelper;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.collections.WTCollection;
import wt.fc.collections.WTValuedHashMap;
import wt.fc.collections.WTValuedMap;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerRef;
import wt.inf.library.WTLibrary;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

public class StandardYJWGJService extends StandardManager implements YJWGJService {
	private static final long serialVersionUID = 1L;
	private Logger logger = LogR
			.getLogger(StandardYJWGJService.class.getName());

	  public static StandardYJWGJService newStandardYJWGJService()
	            throws WTException {
		  StandardYJWGJService instance = new StandardYJWGJService();
	        instance.initialize();
	        return instance;
	   }
	private WTDocument validatePBO(Persistable pbo) throws WTException {
		WTDocument doc = null;
		if (pbo instanceof WTDocument) {
			doc = (WTDocument) pbo;
		}
		if (doc == null) {
			throw new WTException("外购件申请流程中的主业务对象不是外购件申请单，请联系管理员。");
		}
		return doc;
	}
/**
 * 通過外購件申請單獲取所有外購件
 * @description TODO
 * @param doc
 * @return
 * @author yge  2017年11月23日上午9:41:53
 * @throws WTException 
 */
	private List getAllWGJ(WTDocument doc) throws WTException {
		List list = new ArrayList();
		boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
		ReferenceFactory rf = new ReferenceFactory();
    	QuerySpec qs = new QuerySpec(WGJPartLink.class);
    	SearchCondition sc = new SearchCondition(WGJPartLink.class,WGJPartLink.ROLE_AMASTERIDA2A2,
    			"=",rf.getReferenceString(doc.getMaster()));
    	qs.appendWhere(sc);
    	QueryResult qr = PersistenceHelper.manager.find(qs);
    	while(qr.hasMoreElements()){
    		WGJPartLink link = (WGJPartLink)qr.nextElement();
    		list.add(rf.getReference(link.getRoleBida2a2()).getObject());
    	}
    	
    	  wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
		return list;
	}

	/*
	 * 取消外购件申请单
	 * 
	 * @see ext.yongc.plm.part.YJWGJService#cancelWGJ(wt.fc.Persistable)
	 */
	@Override
	public void cancelWGJ(Persistable pbo) throws WTException {
		WTDocument doc = validatePBO(pbo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ext.yongc.plm.part.YJWGJService#setState(wt.fc.Persistable,
	 * java.lang.String)
	 */
	@Override
	public void setState(Persistable pbo, String state) throws WTException {
		logger.debug(">>>>>>>>>>>>> enter setState");
		logger.debug(">>>>>>>>>>>>> state====>"+state);
		WTDocument doc = validatePBO(pbo);
		List list = getAllWGJ(doc);
		Object obj = null;
		for(int i=0;i<list.size();i++){
			obj = list.get(i);
			  if(obj instanceof Persistable){
					 LifeCycleManaged p = (LifeCycleManaged) obj;
		       		   LifeCycleHelper.service.setLifeCycleState(p, State.toState(state));
		       		   PersistenceHelper.manager.refresh(p);
			  }
		}

	}
	public String validateCheckOut(Persistable pbo)throws WTException {
		 logger.debug(" ---------- enter validateCheckOut ---------");
		 StringBuffer msg = new StringBuffer(); 
		 WTDocument doc = validatePBO(pbo);
		  if( WorkInProgressHelper.isCheckedOut((Workable)doc) || 
				  WorkInProgressHelper.isWorkingCopy((Workable)doc)){
			  msg.append("[ 外购件申请单 , 编号 "+doc.getNumber()+" , 名称 "+doc.getName()+"] \n");
		  }
			List list = getAllWGJ(doc);
		 Object obj = null;
		 EPMDocument epm = null;
		 WTPart part = null;
		 for(int i=0;i<list.size();i++){
			  obj = list.get(i);
			  if(obj instanceof Workable){
				  if( WorkInProgressHelper.isCheckedOut((Workable)obj) || 
						  WorkInProgressHelper.isWorkingCopy((Workable)obj)){
					  String name="";
					  String number="";
					  String type="";
					  TypeIdentifier identifier = TypeIdentifierHelper.getType(obj);
					  try {
						type = TypedUtilityServiceHelper.service.getLocalizedTypeName(identifier, Locale.CHINA);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					 if(obj instanceof WTPart){
							 part = (WTPart)obj;
							 name = part.getName();
							 number = part.getNumber();
						 }else if(obj instanceof EPMDocument){
							 epm = (EPMDocument)obj;
							 name = epm.getName();
							 number = epm.getNumber();
						 }
					
					  msg.append("[ "+type+" , 编号 "+number+" , 名称 "+name+"] \n");
				  }
			  }
		 }
		 
		 if(StringUtil.isNotEmpty(msg.toString())){
			 msg.append("以上对象被检出，请检入后再完成任务！");
		 }
		 logger.debug(" ---------- exit validateCheckOut ---------"+msg.toString());
		return msg.toString();
	}

	/*
	 * 将外购件所有的版本移到指定的文件夹下
	 * 
	 * @see ext.yongc.plm.part.YJWGJService#moveWGJ(wt.fc.Persistable)
	 */
	@Override
	public void moveWGJ(Persistable pbo,String folderPath) throws WTException {
		 logger.debug("--------------------- enter moveWGJ");
		 logger.debug("folderPath--------------------- "+folderPath);
		 WTDocument doc = validatePBO(pbo);
		 boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
		 WTValuedMap objFolderMap = new WTValuedHashMap(1);
		 WTLibrary ll = getLibraryByName(TypeConstants.LIBRARY_WGJ);
		 if(ll==null){
			 throw new WTException("存储库："+TypeConstants.LIBRARY_WGJ+" 在系统中不存在，请联系管理员。");
		 }
			WTContainerRef c_ref = WTContainerRef.newWTContainerRef(ll);
			folderPath = folderPath.replace(TypeConstants.LIBRARY_WGJ, "Default");
			logger.debug("folderPath2--------------------- "+folderPath);
		  Folder folder = FolderHelper.service.getFolder(folderPath,c_ref);
		 logger.debug("folder--------------------- "+folder);
			List list = getAllWGJ(doc);
			 for(int i=0;i<list.size();i++){
				  Persistable p =(Persistable) list.get(i);
				  objFolderMap.put(p, folder);
			 }
			 
			 WTCollection col = ContainerMoveHelper.service.moveAllVersions(objFolderMap);
			 logger.debug("WTCollection size--------------------- "+col.size());
			 wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
			 logger.debug("--------------------- exit moveWGJ");
	}
	private WTLibrary getLibraryByName(String name) throws WTException{
		 QuerySpec qs = new QuerySpec(WTLibrary.class);
		    SearchCondition sc = new SearchCondition(WTLibrary.class,WTLibrary.NAME,"=",name);
		    qs.appendWhere(sc);
		    QueryResult qr = PersistenceHelper.manager.find(qs);
		    WTLibrary ll = null;
		    while(qr.hasMoreElements()){
		    	ll = (WTLibrary)qr.nextElement();
		    }
		    return ll;
	}
}
