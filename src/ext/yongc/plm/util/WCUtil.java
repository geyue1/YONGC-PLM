/**
 * ext.yongc.plm.util.WCUtil.java
 * @Author yge
 * 2017年7月5日上午10:53:18
 * Comment 
 */
package ext.yongc.plm.util;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.FormatContentHolder;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.fc.WTReference;
import wt.folder.CabinetBased;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.httpgw.URLFactory;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.projmgmt.admin.Project2;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.WhereExpression;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.type.ClientTypedUtility;
import wt.type.TypeDefinitionReference;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;
import wt.vc.Mastered;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.vc._IterationInfo;
import wt.vc.config.LatestConfigSpec;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewManageable;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

import com.ptc.core.query.common.QueryException;
import com.ptc.netmarkets.util.misc.NmActionServiceHelper;
import com.ptc.windchill.keystore.WTKeyStoreUtil;
import com.ptc.wpcfg.doc.VariantSpec;



public class WCUtil implements RemoteAccess
{

    private final static Logger LOGGER = LogR.getLogger(WCUtil.class.getName());
    public static final String SET_CREATOR_METHOD_NAME = "setCreator";
    public static final String SET_MODIFIER_METHOD_NAME = "setModifier";
    private static WTProperties wtprop = null;
    private static String wthome = "";

    static {
        try {
            wtprop = WTProperties.getLocalProperties();
            wthome = wtprop.getProperty("wt.home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the value of the specified property from "wt.properties" file.
     * 
     * @param key
     *            the specified property key, e.g. "com.lenovo.plm.elois.integration.elois2pi.username".
     * @return the value of the specified property.
     */
    public static String getWTProperty(String key) {
        String value = wtprop.getProperty(key);
        return (value != null) ? value : "";
    }

    /**
     * Gets the decrypted value of the specified property (e.g. password) from "wt.properties" file.
     * 
     * @param key
     *            the specified property key, e.g. "com.lenovo.plm.elois.integration.elois2pi.password".
     * @return the decrypted value of the specified property.
     */
    public static String getDecryptProperty(String key) {
        return getDecryptProperty(key, "");
    }

    /**
     * Gets the decrypted value of the specified property (e.g. password) from "wt.properties" file.
     * 
     * @param key
     *            the specified property key, e.g. "com.lenovo.plm.elois.integration.elois2pi.password".
     * @param defaultValue
     *            if the decrypted value of the specified property is null, returns the given default value.
     * @return the decrypted value of the specified property.
     */
    public static String getDecryptProperty(String key, String defaultValue) {
        String value = wtprop.getProperty(key);
        String decryptedValue = (value != null) ? WTKeyStoreUtil.decryptProperty(value, wthome) : defaultValue;
        return decryptedValue;
    }

    /**
     * 
     * @description get object by number/name/version/iteration/state 
     * @author jgao
     * @date Aug 18, 2015
     * @param clz
     * @param number
     * @param name
     * @param state
     * @param version
     * @param iteration
     * @return
     * @throws WTException
     */
    public static QueryResult queryObject(Class clz, String number, String name, String state, String version, String iteration)
            throws WTException {
        try {
            QuerySpec queryspec = new QuerySpec(clz);
            WhereExpression where = null;
            // Specifies the iteration of WTDocument object.
            if (iteration != null && !iteration.trim().isEmpty()) {
                where = new SearchCondition(clz, "iterationInfo.identifier.iterationId",
                        SearchCondition.EQUAL, iteration);
                queryspec.appendWhere(where, new int[] { 0 });
            }
            // Specifies the version of WTDocument object.
            if (version != null && !version.trim().isEmpty()) {
                where = new SearchCondition(clz, "versionInfo.identifier.versionId",
                        SearchCondition.EQUAL, version);
                if (queryspec.getConditionCount() > 0 && queryspec.getWhere().endsWith(")")) {
                    queryspec.appendAnd();
                }
                queryspec.appendWhere(where, new int[] { 0 });
            }
            // Specifies the life cycle state of WTDocument object.
            if (state != null && !state.trim().isEmpty()) {
                where = new SearchCondition(clz, (String) clz.getField("LIFE_CYCLE_STATE").get(clz), SearchCondition.EQUAL, state);
                if (queryspec.getConditionCount() > 0 && queryspec.getWhere().endsWith(")")) {
                    queryspec.appendAnd();
                }
                queryspec.appendWhere(where, new int[] { 0 });
            }
            // Specifies the name of WTDocument object.
            if (name != null && !name.trim().isEmpty()) {
                where = new SearchCondition(clz, (String) clz.getField("NAME").get(clz), SearchCondition.EQUAL, name);
                if (queryspec.getConditionCount() > 0 && queryspec.getWhere().endsWith(")")) {
                    queryspec.appendAnd();
                }
                queryspec.appendWhere(where, new int[] { 0 });
            }
            // Specifies the number of WTDocument object.
            if (number != null && !number.trim().isEmpty()) {
                where = new SearchCondition(clz, (String) clz.getField("NUMBER").get(clz), SearchCondition.EQUAL, number);
                if (queryspec.getConditionCount() > 0 && queryspec.getWhere().endsWith(")")) {
                    queryspec.appendAnd();
                }
                queryspec.appendWhere(where, new int[] { 0 });
            }
            // Sorted by modifying time in descending order.
            OrderBy orderby = new OrderBy(new ClassAttribute(clz, "thePersistInfo.modifyStamp"), true);
            queryspec.appendOrderBy(orderby, new int[] { 0 });
            return PersistenceHelper.manager.find((StatementSpec) queryspec);
        } catch (Exception e) {
            String errorMsg = MessageFormat.format(
                    "Failed to query WTDocument objects with the given parameters: number-[{0}],"
                            + " name-[{1}], life cycle state-[{2}], version-[{3}], iteration-[{4}].", number, name,
                    state, version, iteration);
            LOGGER.error(errorMsg, e);
            LOGGER.debug("[J] " + errorMsg, e);
            throw new WTException(e, errorMsg);
        }
    }

    public static Persistable getPersistableByOid(String oid) throws WTException {
        Persistable p = null;
        if (!RemoteMethodServer.ServerFlag) {
            try {
                Class aclass[] = { String.class };
                Object aobj[] = { oid };
                p = (Persistable) RemoteMethodServer.getDefault().invoke("getPersistableByOid", WCUtil.class.getName(),
                        null, aclass, aobj);
            } catch (Exception e) {
                // e.printStackTrace();
                LOGGER.error(e.getMessage(), e);
                throw new WTException(e);
            }

        }
        else {
            WTUser previous = null;
            WTPrincipal wtadministrator = null;
            try {
                // session exchange
                previous = (WTUser) SessionHelper.manager.getPrincipal();
                wtadministrator = SessionHelper.manager.getAdministrator();
                SessionContext.setEffectivePrincipal(wtadministrator);
                SessionHelper.manager.setAdministrator();

                ReferenceFactory rf = new ReferenceFactory();
                WTReference wtr = rf.getReference(oid);
                if (wtr != null) {
                    p = wtr.getObject();
                }
            } catch (WTException e) {
                LOGGER.error("getPersistableByOid2():", e);
                throw new WTException(e);
            } finally {
                SessionHelper.manager.setPrincipal(previous.getName());
                SessionContext.setEffectivePrincipal(previous);
            }
        }
        return p;
    }

    /**
     * Get Object VersionInfo ex:A.1
     * 
     * @param wto
     * @return
     */
    public static String getVersionInfo(WTObject wto) {
        String versionInfo = "";
        if (wto instanceof Versioned) {
            Versioned ved = (Versioned) wto;
            String version = ved.getVersionIdentifier().getValue();
            String iteration = ved.getIterationIdentifier().getValue();
            if (version != null && iteration != null) {
                versionInfo = version + "." + iteration;
            }
        }
        else {
            LOGGER.debug("the wto is not versioned, return null ");
        }
        return versionInfo;
    }

    /**
     * 
     * @param number
     * @param thisClass
     * @return
     */
    @SuppressWarnings(
    { "deprecation", "unchecked" })
    public static <T extends Persistable> T getLatestPersistableByNumber(String number, Class<T> thisClass)
            throws WTException {
        T persistable = null;
        try {
            int[] index = { 0 };
            QuerySpec qs = new QuerySpec(thisClass);
            String attribute = (String) thisClass.getField("NUMBER").get(thisClass);
            qs.appendWhere(new SearchCondition(thisClass, attribute, SearchCondition.EQUAL, number), index);

            QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

            LatestConfigSpec configSpec = new LatestConfigSpec();
            qr = configSpec.process(qr);

            if (qr != null && qr.hasMoreElements()) {
                persistable = (T) qr.nextElement();
            }
        } catch (QueryException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (WTException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (SecurityException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (NoSuchFieldException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        }
        return persistable;
    }
    
    public static <T> List<T> getLinksByRoleA(Collection<? extends Persistable>  roleAs  , Class<T> clazz , String softType) throws WTException, RemoteException{
    	List<T> returnList = new ArrayList<T>();
    		boolean enforce = SessionServerHelper.manager
    				.setAccessEnforced(false);
		try{
			
			List< ? extends Persistable> [] roleAArray = WCUtil.list2SqlIn(roleAs);
			for(List<? extends Persistable> roleAs2 : roleAArray){
				QuerySpec qs = new QuerySpec();
				qs.setAdvancedQueryEnabled(true);
				int indexScrl = qs.appendClassList(clazz, true);
				 TypeDefinitionReference tdr = ClientTypedUtility.getTypeDefinitionReference(softType);
			       qs.appendWhere( new SearchCondition(clazz, "typeDefinitionReference.key.branchId" ,SearchCondition.EQUAL, tdr
			               .getKey().getBranchId()), new int []{ indexScrl});
			    qs.appendAnd();
	            qs.appendOpenParen();
				 for(int v =0;v<roleAs2.size();v++ ){
					 Persistable p =  roleAs2.get(v);
					 qs.appendWhere(new SearchCondition(clazz, "roleAObjectRef.key.id",
							 SearchCondition.EQUAL, p.getPersistInfo().getObjectIdentifier().getId()), new int[]{indexScrl});
					 if(v != (roleAs2.size() - 1)){
						 qs.appendOr();
					 }
				 }
				 qs.appendCloseParen();	
	
				 QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
					Persistable[] obj = null;
					while(qr.hasMoreElements()){
						obj = (Persistable[])qr.nextElement();
						returnList.add((T)obj[0]);
					}
            }
			
		}finally{
			SessionServerHelper.manager.setAccessEnforced(enforce);
		}
    	return returnList;
    }
    
    
    /**
     * 
     * @param number
     * @param thisClass
     * @return
     * @throws WTException 
     */
    @SuppressWarnings(
    { "deprecation", "unchecked" })
    public static <T extends RevisionControlled> Map<String, T> getLatestPersistableByNumber(Collection<String> numbers, Class<T> thisClass)
            throws WTException {
    	Map<String, T> returnMap = new HashMap<String, T>();
        try {
            int[] index = { 0 };
            List<String> [] numbsersArray = WCUtil.list2SqlIn(numbers);
	        for(List<String> numbers2 : numbsersArray){
	            QuerySpec qs = new QuerySpec(thisClass);
	            String attribute = (String) thisClass.getField("NUMBER").get(thisClass);
	            qs.appendOpenParen();
				 for(int v =0;v<numbers2.size();v++ ){
					 String number =  numbers2.get(v);
					 qs.appendWhere(new SearchCondition(thisClass, attribute, SearchCondition.EQUAL, number), index);
					 if(v != (numbers2.size() - 1)){
						 qs.appendOr();
					 }
				 }
				 qs.appendCloseParen();	
	
	            QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
	
	            LatestConfigSpec configSpec = new LatestConfigSpec();
	            qr = configSpec.process(qr);
	            T persistable = null;
	            Method th = thisClass.getMethod("getNumber" , new Class[]{});
	            String number = "";
	            while (qr != null && qr.hasMoreElements()) {
	            	persistable  = (T) qr.nextElement();
	            	number = (String) th.invoke(persistable, new Object[]{});
	            	returnMap.put(number, persistable);
	            }
            }
        }
        catch (InvocationTargetException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } 
        catch (NoSuchMethodException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (QueryException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (WTException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (SecurityException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        } catch (NoSuchFieldException e) {
            LOGGER.error(".getLatestPersistableByNumber():", e);
            throw new WTException(e);
        }
        return returnMap;
    }
    
	public static <T> List<T> [] list2SqlIn(Collection<T> coll){
		 return list2SqlIn(coll , 1000);
	 }
    
    @SuppressWarnings("unchecked")
	public static <T> List<T> [] list2SqlIn(Collection<T> coll , int batchSize){
		 int size = 1;
		 List<T> list = new ArrayList<T>(coll);
		 int listSize = list.size();
		 while((batchSize * size) < listSize){
			 size++;
		 }
		 List<T> [] returnLists = new ArrayList [size];
		 int index = 0;
		 for(int i = 0; i< returnLists.length;i++){
			 List<T> child = new ArrayList<T>();
			for( int f = index ;  ( f < (index + batchSize) && f < listSize ) ;f++){
				child.add(list.get(f));
			}
			returnLists[i]=child;
			index += batchSize;
		 }
		 return returnLists;
	 }

    /**
     * get OID
     * 
     * @param wto
     * @return
     */
    public static String getOid(Persistable persistable) throws WTException {
        String oid = null;
        try {
            ReferenceFactory referencefactory = new ReferenceFactory();
            oid = referencefactory.getReferenceString(persistable);
        } catch (WTException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        }
        return oid;
    }

    /**
     * Get container by name
     * 
     * @param name
     *            container name
     * @return container
     * @throws WTException
     *             Windchill exception
     */
    public static WTContainer getWtContainerByName(String name) throws WTException {

        WTContainer obj = null;
        if (!RemoteMethodServer.ServerFlag) {
            try {
                Class aclass[] = { String.class };
                Object aobj[] = { name };
                obj = (WTContainer) RemoteMethodServer.getDefault().invoke("getWtContainerByName",
                        WCUtil.class.getName(), null, aclass, aobj);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new WTException(e);
            }
        }
        else {
            QuerySpec qs = new QuerySpec(WTContainer.class);
            QueryResult qr = null;
            SearchCondition sc = new SearchCondition(WTContainer.class, WTContainer.NAME, "=", name);
            qs.appendWhere(sc);
            qr = PersistenceServerHelper.manager.query(qs);

            while (qr.hasMoreElements()) {
                obj = (WTContainer) qr.nextElement();
            }
        }
        return obj;
    }

    /**
     * view change
     * 
     * @param part
     * @return
     * @throws Exception
     */
    public static WTPart changeView(WTPart apart, String viewName, String folderStr, WTUser creator) throws WTException {
        WTPart newpart = null;
        WTPart part = apart;
        try {
            if (part != null) {

                View view = ViewHelper.service.getView(viewName);

                part = (WTPart) VersionControlHelper.getLatestIteration(part);
                newpart = (WTPart) ViewHelper.service.newBranchForView((ViewManageable) part, view);
                newpart.setContainer(part.getContainer());

                Folder folder = getFolderRef("/Default/" + folderStr, part.getContainer(), true);
                FolderHelper.assignLocation((FolderEntry) newpart, folder);

                newpart = (WTPart) PersistenceHelper.manager.save(newpart);
                wt.ownership.OwnershipHelper.service.assignOwnership(newpart, creator);
                newpart = (WTPart) PersistenceHelper.manager.refresh(newpart);
                newpart = (WTPart) PersistenceHelper.manager.save(newpart);

            }
        } catch (Exception e) {
            // e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        }
        return newpart;
    }

    /**
     * 
     * @param folderPath
     * @param wtcontainer
     * @param isCreate
     * @return
     * @throws WTException
     */
    public static Folder getFolderRef(String folderPath, WTContainer wtcontainer, boolean isCreate) throws WTException {
        if (folderPath == null) {
            return null;
        }
        if (StringUtil.isEmpty(folderPath)) {
            return FolderHelper.service.getFolder("/Default", WTContainerRef.newWTContainerRef(wtcontainer));
        }
        Folder subfolder = null;
        String tempPath = folderPath;
        String splitMark = "/";
        String defaultMark = "/Default";
        if (!tempPath.startsWith(splitMark)) {
            tempPath = splitMark + tempPath;
        }

        if (!tempPath.equalsIgnoreCase(defaultMark) && !tempPath.startsWith(defaultMark)) {
            tempPath = defaultMark + tempPath;
        }

        String nextfolder[] = tempPath.split(splitMark);
        // LOGGER.debug("nextfolder[]==="+nextfolder.length);
        ArrayList<String> list = new ArrayList<String>();
        for (int p = 0; p < nextfolder.length; p++) {
            if (!StringUtil.isEmpty(nextfolder[p]) && !nextfolder[p].trim().equals("Default")) {
                list.add(nextfolder[p]);
            }
        }

        if (isCreate) {
            createMultiLevelDirectory(list, WTContainerRef.newWTContainerRef(wtcontainer));
        }

        try {
            subfolder = FolderHelper.service.getFolder(tempPath, WTContainerRef.newWTContainerRef(wtcontainer));
        } catch (WTException e) {
            LOGGER.error("No found the folder " + tempPath);
        }
        if (subfolder == null) {
            tempPath = "/Default";
            subfolder = FolderHelper.service.getFolder(tempPath, WTContainerRef.newWTContainerRef(wtcontainer));
        }
        return subfolder;
    }

    public static boolean isLocationFolder(String location, WTContainer container) {
        boolean result = true;
        // check parameter
        if (!"".equals(location) && container != null) {
            String folder = location;
            // check location
            try {
                if (!(folder.startsWith("/"))) {
                    folder = "/" + folder;
                }
                if (folder.indexOf("Default") == -1) {
                    folder = "/Default" + folder;
                }
                FolderHelper.service.getFolder(folder, WTContainerRef.newWTContainerRef(container));
                result = true;
            } catch (WTException e) {
                LOGGER.error("Location not found: ", e);
                result = false;
            }
        }
        return result;
    }

    /**
     * Create multiple level directory
     * 
     * @param list
     * @param wtContainerRef
     * @return
     * @throws WTException
     */
    public static SubFolder createMultiLevelDirectory(List<String> list, WTContainerRef wtContainerRef)
            throws WTException {
        SubFolder subFolder = null;
        String path = ((WTContainer) wtContainerRef.getObject()).getDefaultCabinet().getFolderPath();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Folder folder = null;
            try {
                folder = FolderHelper.service.getFolder(path, wtContainerRef);
                path = path + "/" + list.get(i);
                QueryResult result = FolderHelper.service.findSubFolders(folder);
                if (!checkFolderExits(result, list.get(i))) {
                    subFolder = FolderHelper.service.createSubFolder(path, wtContainerRef);
                }
            } catch (WTException e) {
                // e.printStackTrace();
                LOGGER.error(e.getMessage(), e);
                throw new WTException(e);
            }
        }

        if (subFolder == null) {
            try {
                Folder folder = FolderHelper.service.getFolder(path, wtContainerRef);
                subFolder = (SubFolder) folder;
            } catch (WTException e) {
                // e.printStackTrace();
                LOGGER.error(e.getMessage(), e);
                throw new WTException(e);
            }
        }
        return subFolder;
    }

    /**
     * Gets the main content of a WTObject
     * 
     * @param object
     *            WTObject instance.
     * @return an ApplicationData instance wrapping the main content.
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static ApplicationData getPrimaryContent(WTObject object) throws WTException {
        if (object == null) {
            return null;
        }
        Persistable primary = object;
        ApplicationData applicationdata = null;
        ContentHolder contentHolder;
        try {
            contentHolder = ContentHelper.service.getContents((ContentHolder) primary);
        } catch (PropertyVetoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        }
        ContentItem contentitem = ContentHelper.getPrimary((FormatContentHolder) contentHolder);
        applicationdata = (ApplicationData) contentitem;
        return applicationdata;
    }

    /**
     * 
     * @param result
     * @param str
     * @return
     */
    private static boolean checkFolderExits(QueryResult result, String str) {
        if (result == null) {
            return false;
        }
        while (result.hasMoreElements()) {
            Object obj = result.nextElement();
            if (obj instanceof SubFolder) {
                SubFolder subFolder = (SubFolder) obj;
                if (subFolder.getName().equals(str)) {
                    return true;
                }
            }
            else {
                break;
            }
        }
        return false;
    }

    /**
     * checkout Workable
     * 
     * @param workable
     * @return object which has been checked out.
     * 
     * @author fxie
     */
    public static Workable doCheckOut(Workable workable) throws WTException {
        Workable newObj = null;
        try {
            if (!wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable, wt.session.SessionHelper.manager.getPrincipal()))
            {
                if (!FolderHelper.inPersonalCabinet((CabinetBased) workable)
                        && !WorkInProgressHelper.isWorkingCopy(workable)) {
                    Folder folder = WorkInProgressHelper.service.getCheckoutFolder();
                    CheckoutLink checkoutLink = WorkInProgressHelper.service.checkout(workable, folder, "");
                    if (checkoutLink != null) {
                        newObj = checkoutLink.getWorkingCopy();
                    }
                }
            }
            else {
                if (WorkInProgressHelper.isWorkingCopy(workable)) {
                    newObj = workable;
                }
                else {
                    newObj = WorkInProgressHelper.service.workingCopyOf(workable);
                }
            }
        } catch (WTPropertyVetoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        }
        return newObj;
    }

    /**
     * Checks out the given workable object and gets the working copy of it.
     * 
     * @param workable
     *            A Workable object, e.g. a WTPart object.
     * @param note
     *            Note for the working copy.
     * @return the working copy.
     * @throws WTException
     *             If failed to check out the given workable object and get the working copy of it.
     * @author Luo Jiao
     */
    public static Workable doCheckOut(Workable workable, String note) throws WTException {
        if (workable == null) {
            return null;
        }
        if (note == null || note.trim().isEmpty()) {
            note = "Checked out by system.";
        }
        Workable workingCopy = null;
        try {
            // Gets the latest iteration of the given workable object.
            Workable latestWorkable = (Workable) VersionControlHelper.service.getLatestIteration(workable, false);
            if (WorkInProgressHelper.isCheckedOut(latestWorkable)) {
                WTPrincipal currentUser = SessionHelper.manager.getPrincipal();
                if (WorkInProgressHelper.isCheckedOut(latestWorkable, currentUser)) {
                    if (WorkInProgressHelper.isWorkingCopy(latestWorkable)) {
                        workingCopy = latestWorkable;
                    } else {
                        workingCopy = (Workable) WorkInProgressHelper.service.workingCopyOf(latestWorkable);
                    }
                } else {
                    throw new WTException(
                            MessageFormat
                                    .format("The object-[{0}] has already been checked out by another user and cannot be checked out more than once.",
                                            (latestWorkable instanceof WTObject) ? ((WTObject) latestWorkable)
                                                    .getDisplayIdentity() : latestWorkable));
                }
            } else {
                // Checks out the object and gets the working copy.
                Folder folder = WorkInProgressHelper.service.getCheckoutFolder();
                CheckoutLink link = WorkInProgressHelper.service.checkout(latestWorkable, folder, note);
                workingCopy = link.getWorkingCopy();
            }
        } catch (Exception e) {
            String errorMsg = MessageFormat.format(
                    "Failed to check out the given workable object-[{0}] and get the working copy of it.",
                    (workable instanceof WTObject) ? ((WTObject) workable).getDisplayIdentity() : workable);
            LOGGER.error(">>>>> " + errorMsg, e);
            throw new WTException(e);
        }
        return workingCopy;
    }

    public static boolean isCheckedOut(Workable workable) throws WTException {
        return WorkInProgressHelper.isCheckedOut(workable);
    }

    /**
     * checkin Workable
     * 
     * @param workable
     * @return object which has been checked in.
     * @author fxie
     */
    public static Workable doCheckIn(Workable workable) throws WTException {
        return doCheckIn(workable, "Automatically checked in");
    }

    /**
     * checkin Workable
     * 
     * @param workable
     * @return object which has been checked in.
     * @author fxie
     */
    public static Workable doCheckIn(Workable workable, String comments) throws WTException {
        Workable newObj = workable;
        try {
            if (wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable, wt.session.SessionHelper.manager.getPrincipal())) {
                newObj = (Workable) WorkInProgressHelper.service.checkin(workable, comments);
            }
        } catch (WTPropertyVetoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        }
        return newObj;
    }

    /**
     * Queries the Project2 object by specifying its name.
     * 
     * @author liuhaiquan
     * @date 2014-2-18
     * 
     * @param projectName
     *            The name of Project2 object.
     * @return A Project2 object that matches the given name, if no matched object queried or some exception occurs,
     *         returns null.
     * @throws WTException
     *             If an error occurs when executes query.
     */
    public static Project2 getProjectByName(String projectName) throws WTException {
        QuerySpec querySpec = new QuerySpec(Project2.class);
        QueryResult queryResult = null;
        WhereExpression where = new SearchCondition(Project2.class, Project2.NAME, SearchCondition.EQUAL, projectName);
        querySpec.appendWhere(where, new int[] { 0 });
        queryResult = PersistenceHelper.manager.find((StatementSpec) querySpec);
        if (queryResult.hasMoreElements()) {
            return (Project2) queryResult.nextElement();
        }
        return null;
    }

    /**
     * revise a Versioned object
     * 
     * @param currVer
     * @return
     * @throws WTException
     * @throws WTPropertyVetoException
     * @author fxie
     */
    public static Versioned doRevise(Versioned currVer) throws WTException {
        Versioned newVer = null;
        try {
            Versioned newVersionObj = VersionControlHelper.service.newVersion(currVer);
            FolderHelper.assignLocation((FolderEntry) newVersionObj,
                    FolderHelper.service.getFolder((FolderEntry) currVer));
            newVer = (Versioned) PersistenceHelper.manager.store(newVersionObj);
        } catch (WTPropertyVetoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        }
        return newVer;
    }

    /**
     * revise a Versioned object
     * 
     * @param currVer
     * @return
     * @throws WTException
     * @throws WTPropertyVetoException
     * @author fxie
     */
    public static Versioned doRevise(Versioned currVer,String comment) throws WTException {
        Versioned newVer = null;
        try {
            Versioned newVersionObj = VersionControlHelper.service.newVersion(currVer);
            FolderHelper.assignLocation((FolderEntry) newVersionObj,
                    FolderHelper.service.getFolder((FolderEntry) currVer));
            VersionControlHelper.setNote(newVersionObj, comment==null?"":comment);
            newVer = (Versioned) PersistenceHelper.manager.store(newVersionObj);
        } catch (WTPropertyVetoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        }
        return newVer;
    }
    /**
     * 
     * get info page link
     * 
     * @param persistable
     * @return
     * @throws WTException
     * 
     */
    public static String getInfoPageURL(Persistable persistable) throws WTException {
        ReferenceFactory referencefactory = new ReferenceFactory();
        String oid = referencefactory.getReferenceString(persistable);
        URLFactory urlfactory = new URLFactory();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("oid", oid);
        String action = NmActionServiceHelper.service.getAction("object", "view").getUrl();
        return urlfactory.getHREF(action, map, true);
    }

    /**
     * @param object
     * @return
     */
    public static String getWTObjectNumber(WTObject object) {
        if (object instanceof WTPart) {
            return ((WTPart) object).getNumber();
        }
        else if (object instanceof WTDocument) {
            return ((WTDocument) object).getNumber();
        }
        else if (object instanceof VariantSpec) {
            return ((VariantSpec) object).getNumber();
        }
        else if (object instanceof WTChangeRequest2) {
            return ((WTChangeRequest2) object).getNumber();
        }
        else if (object instanceof WTChangeOrder2) {
            return ((WTChangeOrder2) object).getNumber();
        }
        else if (object instanceof WTChangeActivity2) {
            return ((WTChangeActivity2) object).getNumber();
        }
        else if(object instanceof EPMDocument)
        {
            return ((EPMDocument) object).getNumber();
        }else if(object instanceof WTPartMaster){
            return ((WTPartMaster)object).getNumber();
        }else if(object instanceof WTDocumentMaster){
            return ((WTDocumentMaster)object).getNumber();
        }else if(object instanceof EPMDocumentMaster){
            return ((EPMDocumentMaster)object).getNumber();
        }
        return null;
    }

    /**
     * @param object
     * @return
     */
    public static String getWTObjectName(WTObject object) {
        if (object instanceof WTPart) {
            return ((WTPart) object).getName();
        }
        else if (object instanceof WTDocument) {
            return ((WTDocument) object).getName();
        }
        else if (object instanceof VariantSpec) {
            return ((VariantSpec) object).getName();
        }
        else if (object instanceof WTChangeRequest2) {
            return ((WTChangeRequest2) object).getName();
        }
        else if (object instanceof WTChangeOrder2) {
            return ((WTChangeOrder2) object).getName();
        }
        else if (object instanceof WTChangeActivity2) {
            return ((WTChangeActivity2) object).getName();
        }
        else if(object instanceof EPMDocument)
        {
            return ((EPMDocument) object).getName();
        }else if(object instanceof WTPartMaster){
            return ((WTPartMaster)object).getName();
        }else if(object instanceof WTDocumentMaster){
            return ((WTDocumentMaster)object).getName();
        }else if(object instanceof EPMDocumentMaster){
            return ((EPMDocumentMaster)object).getName();
        }
        return null;
    }
    
    /**
     * get latest object by master
     * 
     * @author jgao
     * @date 20150605
     * @param partMaster
     * @return
     * @throws WTException
     */
	public static Persistable getLatestByMaster(Mastered master)
	        throws WTException
	{
		Persistable latest = null;
		QueryResult qr = VersionControlHelper.service.allIterationsOf(master);
		if (qr.hasMoreElements())
		{
			latest = (Persistable) qr.nextElement();
		}
		return latest;
	}
	
	public static String getFormattedDateString(Date time) {
        if (time == null) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return sdf.format(time);
        } catch (Exception e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return sdf.format(time);
        }
    }
	
	 public static String getFormattedDateStringWithSeconds(Date time) {
	        if (time == null) {
	            return "";
	        }
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	            return sdf.format(time);
	        } catch (Exception e) {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	            return sdf.format(time);
	        }
	    }
	
    public static void setCreatorOrModifier(Iterated obj, WTPrincipalReference uref, String methodName) {
        try {
            Class[] pp = new Class[] { WTPrincipalReference.class };
            Method setCreatorOrModifier = _IterationInfo.class.getDeclaredMethod(methodName, pp);// setCreator;setModifier
            setCreatorOrModifier.setAccessible(true);
            obj = (Iterated) PersistenceHelper.manager.refresh((Persistable) obj, false, true);
            setCreatorOrModifier.invoke(obj.getIterationInfo(), new Object[] { uref });
            PersistenceServerHelper.manager.update(obj);

        } catch (Exception e) {
            LOGGER.error(LOGGER, e);
        }
    }
}

