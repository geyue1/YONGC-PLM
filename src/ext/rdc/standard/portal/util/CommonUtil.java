package ext.rdc.standard.portal.util;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ptc.windchill.mpml.processplan.MPMProcessPlan;
import com.ptc.windchill.mpml.processplan.MPMProcessPlanMaster;

import ext.rdc.standard.portal.bean.PortalItem;
import ext.rdc.standard.portal.constant.StringConstant;
import ext.rdc.standard.portal.resource.PortalResource;
import wt.change2.VersionableChangeItem;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.content.ApplicationData;
import wt.doc.WTDocument;
import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.fc.ObjectNoLongerExistsException;
import wt.fc.PagingQueryResult;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.SubFolder;
import wt.inf.library.WTLibrary;
import wt.lifecycle.State;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.query.BasicPageableQuerySpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import wt.vc.Iterated;
import wt.vc.VersionControlHelper;
import wt.vc._IterationInfo;
import wt.vc.views.Variation2;
import wt.vc.views.View;
import wt.vc.views.ViewException;
import wt.vc.views.ViewHelper;

public class CommonUtil implements RemoteAccess, Serializable{

	/**
	 * 
	 */
	private static SimpleDateFormat  TIME_DATE_FORMAT  =  new  SimpleDateFormat(StringConstant.TIME_DATE_FORMAT);
	private static SimpleDateFormat  DAY_DATE_FORMAT  =  new  SimpleDateFormat(StringConstant.DAY_DATE_FORMAT);
	static {
		TIME_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(StringConstant.GMT_8));
		DAY_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(StringConstant.GMT_8));
	}
	public static String getDayFormat(Timestamp dateTime){
			String time=DAY_DATE_FORMAT.format(dateTime);
			return time;
	}
	public static String getTimeFormat(Timestamp dateTime){
		String time=TIME_DATE_FORMAT.format(dateTime);
		return time;
	}
	
	private static ReferenceFactory refFactory = new ReferenceFactory();
	private static final long serialVersionUID = 1L;
    public static String getVersion(WTPart part) {
    	if(part == null) {
    		return "";
    	}
    	return part.getVersionIdentifier().getValue() + StringConstant.SPOT
				+ part.getIterationIdentifier().getValue();
    }
    public static String getVersion(WTDocument doc) {
    	if(doc == null) {
    		return "";
    	}
    	return doc.getVersionIdentifier().getValue() + StringConstant.SPOT
    			+ doc.getIterationIdentifier().getValue();
    }
    public static String getVersion(MPMProcessPlan pp) {
    	if(pp == null) {
    		return "";
    	}
    	return pp.getVersionIdentifier().getValue() + StringConstant.SPOT
    			+ pp.getIterationIdentifier().getValue();
    }
    public static String getBackupNumber(WTPart part) {
    	Variation2 v2 = part.getVariation2();
    	return v2 == null ? "" :v2.toString();
    }
    public static String getVersionAndView(WTPart part) {
    	return getVersionAndView(getVersion(part),part.getViewName());
    }
    public static String getVersionAndView(String version,String viewName) {
    	if(!viewName.isEmpty()) {
    		return version+StringConstant.LEFT_BRACKETS+viewName+StringConstant.RIGHT_BRACKETS;
    	}else {
    		return version;
    	}
    }
    public static String getDoubleString(Double number) {
    	if(number%1==0){
    		return String.valueOf(number.longValue());
	    }else{
	    	return String.valueOf(number);
    	}
    }
    public static String getFloatString(Float number) {
    	if(number%1==0){
    		return String.valueOf(number.longValue());
    	}else{
    		return String.valueOf(number);
    	}
    }
    public static String getVersionAndViewAndBackupNumber(WTPart part) {
    	String backupNumber = getBackupNumber(part);
    	return getVersionAndViewAndBackupNumber(getVersion(part) , part.getViewName(),backupNumber);
    }
    public static String getVersionAndViewAndBackupNumber(String version,String viewName,String backupNumber) {
    	if(backupNumber != null && !backupNumber.isEmpty()) {
    		backupNumber = StringConstant.COMMA+backupNumber;
    	}
    	return version+StringConstant.LEFT_BRACKETS+viewName+backupNumber+StringConstant.RIGHT_BRACKETS;
    }
    public static String getIdentifyAndName(WTPart part) {
    	String str = "";
    	Variation2 var2 = part.getVariation2();
    	if(var2!=null) {
    		str = StringConstant.COMMA+var2.toString();
    	}
    	return part.getNumber()+StringConstant.COMMA+part.getName()+StringConstant.COMMA+getVersion(part)+StringConstant.LEFT_BRACKETS+part.getViewName()+str+StringConstant.RIGHT_BRACKETS;
    }
    public static String getIdentifyAndNameAction(WTPart part) {
    	String display = getIdentifyAndName(part);
    	return getAction(part,display);
    }
    public static String getAction(Persistable obj , String display) {
    	String oid = "OR:"+obj.getPersistInfo().getObjectIdentifier().getStringValue();
    	return getAction(oid, display);
    }
    public static String getAction(String oid , String display) {
    	String url = StringConstant.HttpUrl.INFO_PAGE+oid;
    	return "<a href='"+url+"' target='_blank' >"+display+"</a>";
    }
    public static String getNumberAction(WTDocument doc) {
    	String oid = CommonUtil.getOid(doc);
    	String display = doc.getNumber();
    	String url = StringConstant.HttpUrl.INFO_PAGE+oid;
    	return "<a href='"+url+"' target='_blank' >"+display+"</a>";
    }
    public static String getPrimaryAction(WTDocument doc) throws WTException, PropertyVetoException {
    	ApplicationData app = DocumentUtil.getPrimaryApplicationData(doc);
    	if(app == null) {
    		return "";
    	}
    	String linkUrl = StringConstant.HttpUrl.ATTACHMENTS_DOWNLOAD_DIRECTION_SERVLET+"?oid="+CommonUtil.getOid(doc)+"&amp;oid="+CommonUtil.getOid(app)+"&amp;role=PRIMARY";
    	String display = app.getFileName();
    	return "<a href='"+linkUrl+"' target='_blank' >"+display+"</a>";
    }
    public static String getStateDisplay(RevisionControlled rev) throws WTException, PropertyVetoException {
    	return State.toState(rev.getLifeCycleState().toString()).getDisplay(SessionHelper.getLocale());
    }
    public static String getNumberAction(WTPart part) {
    	String oid = CommonUtil.getOid(part);
    	String display = part.getNumber();
    	String url = StringConstant.HttpUrl.INFO_PAGE+oid;
    	return "<a href='"+url+"' target='_blank' >"+display+"</a>";
    }
    
    public static String getIdentifyAndNameAction(MPMProcessPlan processPlan) {
    	String display = getIdentifyAndName(processPlan);
    	return getAction(processPlan,display);
    }
    public static String getIdentifyAndName(MPMProcessPlan processPlan) {
    	return processPlan.getNumber()+StringConstant.COMMA+processPlan.getName()+StringConstant.COMMA+getVersion(processPlan);
    }
    public static String getIdentifyAndName(MPMProcessPlan processPlan , String projectCode  , int columnVal) {
    	return getIdentifyAndName(processPlan)+StringConstant.COMMA+projectCode+StringConstant.COMMA+columnVal;
    }
    public static String getIdentifyAndNameAction(MPMProcessPlan plan , String projectCode  , int columnVal) throws WTException {
    	String display = getIdentifyAndName(plan,projectCode,columnVal);
    	return getAction(plan,display);
    }
    public static String getIdentifyAndName(MPMProcessPlanMaster master , String projectCode  , int columnVal) {
    	return master.getNumber()+StringConstant.COMMA+master.getName()+StringConstant.COMMA+projectCode+StringConstant.COMMA+columnVal;
    }
    public static String getIdentifyAndName(WTPart part , String projectCode ,String purpose , int columnVal) {
    	return getIdentifyAndName(part)+StringConstant.COMMA+projectCode+StringConstant.COMMA+purpose+StringConstant.COMMA+columnVal;
    }
    public static String getIdentifyAndName(WTPartMaster master , String projectCode ,String purpose , int columnVal) {
    	return master.getNumber()+StringConstant.COMMA+master.getName()+StringConstant.COMMA+projectCode+StringConstant.COMMA+purpose+StringConstant.COMMA+columnVal;
    }
    
    /**
     * 获取工艺视图
     */
	public static Set<String> getProcessViewSet() throws ViewException, WTException {
		Set<String> processViewSet = new HashSet<String>();
		View[] views = ViewHelper.service.getAllViews();
		for (View v : views) {
			if ("Manufacturing".equals(v.getName())) {
				View[] processView = ViewHelper.service.getAllChildren(v);
				for (View subV : processView) {
					processViewSet.add(subV.getName());
				}
				break;
			}

		}
		return processViewSet;

	}
   
    /**
     * Get ObjcetReference by oid.
     * 
     * @param oid
     *            String
     * @return WTReference
     * @throws WTException
     */
    public static Persistable getPersistableByOid(String oid) throws WTException {
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Persistable) RemoteMethodServer.getDefault().invoke("getPersistableByOid", CommonUtil.class.getName(), null,
                        new Class[] { String.class }, new Object[] { oid });
            } else {
                boolean accessEnforced = SessionServerHelper.manager.setAccessEnforced(false);
                Persistable persistable = null;
                try {
                    ReferenceFactory referencefactory = new ReferenceFactory();
                    persistable = referencefactory.getReference(oid).getObject();
                } finally {
                    SessionServerHelper.manager.setAccessEnforced(accessEnforced);
                }
                return persistable;
            }
        } catch (RemoteException re) {
        	re.printStackTrace();
        } catch (InvocationTargetException ie) {
        	ie.printStackTrace();
        }
        return null;
    }
    
    
    

	public static void setCreatorOrModifier(Iterated obj, WTPrincipalReference uref, String methodName)
			throws NoSuchMethodException, SecurityException, ObjectNoLongerExistsException, WTException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?>[] pp = new Class[] { WTPrincipalReference.class };
		Method setCreatorOrModifier = _IterationInfo.class.getDeclaredMethod(methodName, pp);// setCreator;setModifier
		setCreatorOrModifier.setAccessible(true);
		obj = (Iterated) PersistenceHelper.manager.refresh((Persistable) obj, false, true);
		setCreatorOrModifier.invoke(obj.getIterationInfo(), new Object[] { uref });
		PersistenceServerHelper.manager.update(obj);

	}
	public static void setCreatorAndModifier(Iterated obj, WTPrincipal uref)
			throws NoSuchMethodException, SecurityException, ObjectNoLongerExistsException, WTException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		CommonUtil.setCreatorOrModifier(obj, WTPrincipalReference.newWTPrincipalReference(uref), "setCreator");
		CommonUtil.setCreatorOrModifier(obj, WTPrincipalReference.newWTPrincipalReference(uref), "setModifier");
		
	}
	
	
	public static String getOid(Persistable per) {
	 	if(per == null) {
	 		return "";
	 	}
	    return StringConstant.OID_OR_PREFIX+per.getPersistInfo().getObjectIdentifier().getStringValue();
	}
	public static String getVid(RevisionControlled per) {
		if(per == null) {
			return "";
		}
		return StringConstant.VID_VR_PREFIX+per.getClass().getName()+StringConstant.COLON+per.getBranchIdentifier();
	}
	
	 @SuppressWarnings("unchecked")
	public static <T extends Persistable> T getPersistable(String oidOrVid , Class<T> clazz) throws WTRuntimeException, WTException {
	 	if(oidOrVid == null || oidOrVid.trim().isEmpty()) {
	 		return null;
	 	}
	    return (T)refFactory.getReference(oidOrVid).getObject();
	}
	 public static <T extends Persistable> T getPersistableByOR(long ida2a2 , Class<T> clazz) throws WTRuntimeException, WTException {
		 String oid = StringConstant.OID_OR_PREFIX+clazz.getName()+StringConstant.COLON+ida2a2;
		 return getPersistable(oid , clazz);
	 }
	 public static <T extends Persistable> T getPersistableByVR(long branchId , Class<T> clazz) throws WTRuntimeException, WTException {
		 String vid = StringConstant.VID_VR_PREFIX+clazz.getName()+StringConstant.COLON+branchId;
		 return getPersistable(vid , clazz);
	 }
    
    public static String getNumber(VersionableChangeItem changeItem) {
    	if(changeItem instanceof WTChangeRequest2) {
    		return((WTChangeRequest2)changeItem).getNumber();
    	}
    	if(changeItem instanceof WTChangeOrder2) {
    		return((WTChangeOrder2)changeItem).getNumber();
    	}
    	if(changeItem instanceof WTChangeActivity2) {
    		return((WTChangeActivity2)changeItem).getNumber();
    	}
    	return "";
    }
    public static String getJSONString(List<? extends Object> list) {
    	JSONArray result = new JSONArray();
    	if(list!=null && !list.isEmpty()) {
    		for(Object obj : list) {
    			result.put(new JSONObject(obj));
    		}
    	}
    	return result.toJSONString();
    }
    
    public static void feedEmptyPortalItem(List<PortalItem> list , int max) {
    	if(list.size()<max) {
			int addCount = max-list.size();
			for(int i = 0;i<addCount;i++) {
				list.add(PortalItem.newEmptyPortalItem());
			}
		}
    }
    public static void defaultFeedEmptyPortalItem(List<PortalItem> list) {
    	feedEmptyPortalItem(list,StringConstant.IntConstant.SIX);
    }
    
    public static PagingQueryResult pageQuery(int paramInt1, int paramInt2,
			StatementSpec paramStatementSpec) {
		BasicPageableQuerySpec localBasicPageableQuerySpec = new BasicPageableQuerySpec();
		try {
			int paramInt3 = 0;
			boolean paramBoolean = true;

			localBasicPageableQuerySpec.setPrimaryStatement(paramStatementSpec);
			localBasicPageableQuerySpec.setOffset(paramInt1);
			localBasicPageableQuerySpec.setRange(paramInt2);
			if (!(paramBoolean))
				localBasicPageableQuerySpec.setBackgroundThreadEnabled(false);
			if (paramInt3 != 0) {
				localBasicPageableQuerySpec.setPageThreshold(paramInt3);
			}
			return (PagingQueryResult) PersistenceHelper.manager
					.find(localBasicPageableQuerySpec);

		} catch (Exception localWTPropertyVetoException) {
			localWTPropertyVetoException.printStackTrace();
		}
		return null;
	}
    public static WTLibrary getLibrary(String libraryName) throws WTException{
    	QuerySpec qs1 = new QuerySpec(WTLibrary.class);
		WTLibrary library = null;
		SearchCondition sc1 = new SearchCondition(WTLibrary.class, WTLibrary.NAME, SearchCondition.EQUAL, libraryName);
		qs1.appendWhere(sc1,new int[] {0});
		QueryResult qr1 = wt.fc.PersistenceHelper.manager.find(qs1);
		while (qr1.hasMoreElements()) {
			library = (WTLibrary) qr1.nextElement();
		}
		return library;
	}
    public static SubFolder getSubFolder(String libraryName,String folderName) throws WTException{
    	QuerySpec qs = new QuerySpec(SubFolder.class);
    	int fIndex = qs.getFromClause().getPosition(SubFolder.class);
    	int libIndex = qs.appendClassList(WTLibrary.class, false);
    	
    	SearchCondition searchcondition = new SearchCondition(SubFolder.class, SubFolder.NAME, SearchCondition.EQUAL, folderName);
    	qs.appendWhere(searchcondition,new int[] {fIndex});
    	qs.appendAnd();
    	searchcondition = new SearchCondition(WTLibrary.class, WTLibrary.NAME, SearchCondition.EQUAL, libraryName);
    	qs.appendWhere(searchcondition,new int[] {libIndex});
    	qs.appendAnd();
    	searchcondition = new SearchCondition(SubFolder.class, StringConstant.DBMapping.CONTAINER_KEY_ID, WTLibrary.class, StringConstant.DBMapping.THE_INFO_ID);
    	qs.appendWhere(searchcondition,new int[] {fIndex,libIndex});
    	QueryResult qr = wt.fc.PersistenceHelper.manager.find(qs);
    	while (qr.hasMoreElements()) {
    		Object obj = qr.nextElement();
    		if(obj instanceof Object[]) {
    			return (SubFolder) ((Object[])obj)[0];
    		}else {
    			return (SubFolder) obj;
    		}
    	}
    	return null;
    }
	
    
    public static JSONObject getPortalResourceJSON() throws JSONException, WTException {
    	JSONObject result = new JSONObject();
    	result.put(StringConstant.JSONKey.MY_TASK_MORE_URL, ALinkUtil.getTaskMoreLink());
    	result.put(StringConstant.JSONKey.NOTICE_MORE_URL, ALinkUtil.getNoticeMoreLink());
    	result.put(StringConstant.JSONKey.DOWNLOAD_MORE_URL, ALinkUtil.getDownloadMoreLink());
    	
    	
    	result.put(StringConstant.JSONKey.CREATE_FORM,ResourceUtil.getPortalResourceValue(PortalResource.CREATE_FORM));
    	result.put(StringConstant.JSONKey.DOWNLOAD_CENTRE,ResourceUtil.getPortalResourceValue(PortalResource.DOWNLOAD_CENTRE));
    	result.put(StringConstant.JSONKey.MY_TASK,ResourceUtil.getPortalResourceValue(PortalResource.MY_TASK));
    	result.put(StringConstant.JSONKey.NOTICE_CENTRE,ResourceUtil.getPortalResourceValue(PortalResource.NOTICE_CENTRE));
    	result.put(StringConstant.JSONKey.REPORT_CENTRE,ResourceUtil.getPortalResourceValue(PortalResource.REPORT_CENTRE));
    	result.put(StringConstant.JSONKey.MODEL_SELECTION_GUIDE,ResourceUtil.getPortalResourceValue(PortalResource.MODEL_SELECTION_GUIDE));
    	
    	result.put(StringConstant.JSONKey.REFRESH_BTN_TOOL_TIP,ResourceUtil.getPortalResourceValue(PortalResource.REFRESH_BTN_TOOL_TIP));
    	
    	result.put(StringConstant.JSONKey.ADVANCED_SEARCH_BTN_TEXT,ResourceUtil.getPortalResourceValue(PortalResource.ADVANCED_SEARCH_BTN_TEXT));
    	result.put(StringConstant.JSONKey.SEARCH_BTN_TEXT,ResourceUtil.getPortalResourceValue(PortalResource.SEARCH_BTN_TEXT));
    	result.put(StringConstant.JSONKey.SEARCH_TOOLTIP,ResourceUtil.getPortalResourceValue(PortalResource.SEARCH_TOOLTIP));
    	
    	
    	
    	result.put(StringConstant.JSONKey.NUMBER,ResourceUtil.getPortalResourceValue(PortalResource.NUMBER));
    	result.put(StringConstant.JSONKey.NAME,ResourceUtil.getPortalResourceValue(PortalResource.NAME));
    	result.put(StringConstant.JSONKey.PRIMARY,ResourceUtil.getPortalResourceValue(PortalResource.PRIMARY));
    	result.put(StringConstant.JSONKey.VERSION,ResourceUtil.getPortalResourceValue(PortalResource.VERSION));
    	result.put(StringConstant.JSONKey.STATE,ResourceUtil.getPortalResourceValue(PortalResource.STATE));
    	result.put(StringConstant.JSONKey.DESC,ResourceUtil.getPortalResourceValue(PortalResource.DESC));
    	result.put(StringConstant.JSONKey.MODIFY_TIME,ResourceUtil.getPortalResourceValue(PortalResource.MODIFY_TIME));
    	result.put(StringConstant.JSONKey.CREATE_TIME,ResourceUtil.getPortalResourceValue(PortalResource.CREATE_TIME));
    	result.put(StringConstant.JSONKey.TABLE_EMPTY_TEXT,ResourceUtil.getPortalResourceValue(PortalResource.TABLE_EMPTY_TEXT));
    	result.put(StringConstant.JSONKey.CONTAINER_NAME,ResourceUtil.getPortalResourceValue(PortalResource.CONTAINER_NAME));
    	
    	return  result;
    }
    @SuppressWarnings("unchecked")
	public static <T extends RevisionControlled> T getLatestObject(Master master, Class<T> clazz) throws WTException {
		QueryResult queryResult = VersionControlHelper.service.allVersionsOf(master);
		return (T) queryResult.nextElement();
	}
}