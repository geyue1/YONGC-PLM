/**
 * ext.yongc.plm.util.DocUtil.java
 * @Author yge
 * 2017年7月21日下午3:45:41
 * Comment 
 */
package ext.yongc.plm.util;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;





import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.FormatContentHolder;
import wt.content.StandardContentService;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.iba.definition.StringDefinition;
import wt.iba.value.StringValue;
import wt.log4j.LogR;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionServerHelper;
import wt.type.ClientTypedUtility;
import wt.type.TypeDefinitionForeignKey;
import wt.type.TypeDefinitionReference;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.config.LatestConfigSpec;
import wt.vc.wip.WorkInProgressHelper;

public class DocUtil {
	
	private static Logger LOGGER = LogR.getLogger(DocUtil.class.getName());
	
	 /**
     * get latest WTDocument by name
     * 
     * @param name
     *            document name
     * @return WTDocument
     * @throws WTException
     */
    public static WTDocument getDocumentByName(String name) throws WTException {
        if (StringUtil.isEmpty(name)) {
            LOGGER.error(" name is null  getDocumentByName");
            return null;
        }
        LOGGER.debug("param doc name is " + name);
        return getDocument(null, name, null);
    }
    /**
     * getLatestDocumentByNumber
     * 
     * @param number
     * @return
     * @throws WTException
     */
    public static WTDocument getDocumentByNumber(String number)
            throws WTException {
        if (StringUtil.isEmpty(number)) {
            LOGGER.error(" number is null getDocumentByNumber");
            return null;
        }
        return getDocument(number, null, null);
    }
    /**
     * Get Document by nummber and state
     * 
     * @param number
     * @param state
     * @return
     * @throws WTException
     */
    public static WTDocument getDocument(String number, String name,
            String state) throws WTException {
        WTDocument doc = null;
        QuerySpec querySpec = new QuerySpec(WTDocument.class);

        int conditionCount = 0;
        if (!StringUtil.isEmpty(number)) {
            SearchCondition searchCondi = new SearchCondition(WTDocument.class,
                    WTDocument.NUMBER, SearchCondition.EQUAL, number);
            querySpec.appendWhere(searchCondi, new int[] { 0 });
            conditionCount++;
        }

        if (!StringUtil.isEmpty(name)) {
            if (conditionCount > 0) {
                querySpec.appendAnd();
            }
            SearchCondition searchCondi = new SearchCondition(WTDocument.class,
                    WTDocument.NAME, SearchCondition.EQUAL, name);
            querySpec.appendWhere(searchCondi, new int[] { 0 });
            conditionCount++;
        }

        if (!StringUtil.isEmpty(state)) {
            if (conditionCount > 0)
            {
                querySpec.appendAnd();
            }
            SearchCondition searchCondi = new SearchCondition(WTDocument.class,
                    WTDocument.LIFE_CYCLE_STATE, SearchCondition.EQUAL, state);
            querySpec.appendWhere(searchCondi, new int[] { 0 });
        }
        QueryResult qr = PersistenceHelper.manager.find((StatementSpec) querySpec);
        qr = new LatestConfigSpec().process(qr);
        if (qr.hasMoreElements()) {
            doc = (WTDocument) qr.nextElement();
        }
        return doc;
    }
	
	 /**
     * Get the Latest WTDocument by WTDocumentMaster
     * 
     * @param master
     * @return
     * @throws WTException
     */
    public static WTDocument getLatestDocByMaster(WTDocumentMaster master) throws WTException {
        WTDocument latestDoc = null;
        QueryResult qr = VersionControlHelper.service.allIterationsOf(master);
        if (qr.hasMoreElements()) {
        	latestDoc = (WTDocument) qr.nextElement();
        }
        return latestDoc;
    }
    
	 /**
     * is doc checkout
     * @param doc
     * @return
     * @throws WTException 
     * @throws  
     */
    public static boolean isCheckout(WTDocument doc) throws InvocationTargetException,WTException{
    	if(doc == null){
    		return false;
    	}
    	 if (!doc.isLatestIteration())
         {
             doc = (WTDocument) VersionControlHelper.service.getLatestIteration(doc, false);
         }
         if (WorkInProgressHelper.isCheckedOut(doc) || WorkInProgressHelper.isWorkingCopy(doc))
         {
             return true;
         }
         return false;
    }
    /**
     * Add primary content for a document.
     * 
     * @param doc
     *            document instance
     * @param filePath
     *            file absolute path
     * @param fileName
     *            file name
     * @throws WTException
     *             Windchill exception
     */
    public static void addPrimaryContent(WTDocument doc, String filePath, String fileName) throws WTException
    {
        try
        {
            ContentHolder contentHolder = ContentHelper.service.getContents(doc);
            ApplicationData applicationData = (ApplicationData) ContentHelper
                    .getPrimary((FormatContentHolder) contentHolder);
            if (applicationData != null)
            {
                try
                {
                    ContentServerHelper.service.deleteContent(doc, applicationData);
                } catch (WTPropertyVetoException e)
                {
                    LOGGER.error(e.getLocalizedMessage());
                    throw new WTException(e);
                }
            }
        } catch (PropertyVetoException e)
        {
            LOGGER.error(e.getLocalizedMessage());
            throw new WTException(e);
        }
        //File ddd=new File(filePath);
        addContent(doc, filePath, fileName, ContentRoleType.PRIMARY);
    }

    private static void addContent(ContentHolder holder, String filePath, String fileName, ContentRoleType contentType)
            throws WTException
    {
        ApplicationData applicationData = ApplicationData.newApplicationData(holder);
        try
        {
            applicationData.setRole(contentType);
            applicationData = (ApplicationData) PersistenceHelper.manager.save(applicationData);
            applicationData = ContentServerHelper.service.updateContent(holder, applicationData, filePath);
            applicationData.setFileName(fileName);
            applicationData = (ApplicationData) PersistenceHelper.manager.modify(applicationData);
        } catch (IOException e)
        {
            LOGGER.error(e.getLocalizedMessage());
            throw new WTException(e);
        } catch (PropertyVetoException e)
        {
            LOGGER.error(e.getLocalizedMessage());
            throw new WTException(e);
        }
    }

    /**
     * addContent
     * 
     * @param WTDocument
     * @param String
     *            filePath
     * @param String
     *            fileName
     * @param ContentRoleType
     *            contentType
     * @return
     * @throws WTException
     */
    private static void addContent(WTDocument doc, String filePath, String fileName, ContentRoleType contentType)
            throws WTException
    {
        ApplicationData applicationData = ApplicationData.newApplicationData(doc);
        try
        {
            applicationData.setRole(contentType);
            applicationData = (ApplicationData) PersistenceHelper.manager.save(applicationData);
            StandardContentService.setFormat(fileName, applicationData);
            applicationData = ContentServerHelper.service.updateContent(doc, applicationData, filePath);
            applicationData.setFileName(fileName);
            applicationData = (ApplicationData) PersistenceHelper.manager.modify(applicationData);
            ContentServerHelper.service.updateHolderFormat(doc);
        } catch (IOException e)
        {
            LOGGER.error(e.getLocalizedMessage());
            throw new WTException(e);
        } catch (PropertyVetoException e)
        {
            LOGGER.error(e.getLocalizedMessage());
            throw new WTException(e);
        }
    }
    /**
     * @param WTDocument
     * @return ApplicationData
     * @throws Exception
     * @throws WTException
     * @description get ApplicationData by WTDocument
     */
    public static ApplicationData getPrimaryContent(WTDocument document) throws WTException
    {
        ContentHolder contentHolder = null;
        ApplicationData applicationdata = null;
        try
        {
            LOGGER.debug("get content of : " + document.getNumber());
            contentHolder = ContentHelper.service.getContents((ContentHolder) document);
            LOGGER.debug("contentHolder : " + contentHolder);
            ContentItem contentitem = ContentHelper.getPrimary((FormatContentHolder) contentHolder);
            LOGGER.debug("primary : " + contentitem);
            applicationdata = (ApplicationData) contentitem;
        } catch (WTException e)
        {
            LOGGER.error(DocUtil.class + ".getPrimaryContent():", e);
            throw new WTException(e, e.getLocalizedMessage());
        } catch (Exception e)
        {
            LOGGER.error(DocUtil.class + ".getPrimaryContent():", e);
            throw new WTException(e, e.getLocalizedMessage());
        }
        return applicationdata;
    }

    /**
     * @param WTDocument
     * @return boolean
     * @throws WTException
     * @description check if has ApplicationData in WTDocument
     */
    public static boolean hasPrimaryContent(WTDocument document) throws WTException
    {
        boolean result = true;
        ContentHolder contentHolder = null;
        try
        {
            LOGGER.debug("get content of : " + document.getNumber());
            contentHolder = ContentHelper.service.getContents((ContentHolder) document);
            LOGGER.debug("contentHolder : " + contentHolder);
            ContentItem contentItem = ContentHelper.getPrimary((FormatContentHolder) contentHolder);
            LOGGER.debug("primary : " + contentItem);
            if (contentItem == null){
                result = false;
            }
        } catch (PropertyVetoException e)
        {
            LOGGER.error(DocUtil.class + ".getPrimaryContent():", e);
            throw new WTException(e, e.getLocalizedMessage());
        }
        return result;
    }
    
    /**
	 * save primary conetent to local
	 * 
	 * @param contentHodler
	 * @param filename
	 * @throws Exception
	 */
	public static void saveToFile(FormatContentHolder contentHodler,
			String filename) throws Exception {
		ContentHolder holder = ContentHelper.service.getContents(contentHodler);
		ContentItem item = ContentHelper
				.getPrimary((FormatContentHolder) holder);
		if(item!=null){
			InputStream is = ContentServerHelper.service
					.findContentStream((ApplicationData) item);
			saveToDisk(is, filename);
			is.close();
		}
	}

	/**
	 * save primary conetent to local
	 * 
	 * @param is
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveToDisk(InputStream is, String filename)
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
	  public static WTPartDescribeLink associateDescDocToPart(WTPart partRoleA, WTDocument docRoleB) {
		  WTPartDescribeLink result = null;
	        try {
	            if (!RemoteMethodServer.ServerFlag) {
	                return (WTPartDescribeLink) RemoteMethodServer.getDefault().invoke("associateDescDocToPart",
	                        DocUtil.class.getName(), null, new Class[] { WTPart.class, WTDocument.class },
	                        new Object[] { partRoleA, docRoleB });
	            } else {
	                boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
	                try {
	                    // 检查是否文档间已经有连接，得到WTPartDescribeLink
	                    QueryResult queryresult = PersistenceHelper.manager.find(WTPartDescribeLink.class, partRoleA,
	                            WTPartDescribeLink.DESCRIBES_ROLE, docRoleB);
	                    if (queryresult == null || queryresult.size() == 0) {
	                        // 如果没有连接，则建立
	                        WTPartDescribeLink wtpartdescribelink = WTPartDescribeLink.newWTPartDescribeLink(partRoleA,
	                                docRoleB);
	                        PersistenceServerHelper.manager.insert(wtpartdescribelink);

	                        wtpartdescribelink = (WTPartDescribeLink) PersistenceHelper.manager.refresh(wtpartdescribelink);
	                        result = wtpartdescribelink;
	                    } else {
	                        // 如果有连接，则返回该连接
	                        return (WTPartDescribeLink) queryresult.nextElement();
	                    }
	                } catch (Exception e) {
	                    // TODO: handle exception
	                    e.printStackTrace();
	                } finally {
	                    SessionServerHelper.manager.setAccessEnforced(enforce);
	                }
	                return result;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	  }
	  /**
	     * 
	     * @param type
	     * @param ibaName
	     * @param ibaVaue
	     * @return
	     * @throws WTException
	     *             liuwei
	     */
	    public static List<WTDocument> getDocumentByTypeAndIBA(String type, String ibaName, String ibaVaue)
	            throws WTException
	    {
	        List<WTDocument> documentsList = new ArrayList<WTDocument>();
	        try
	        {
	            QuerySpec querySpec = new QuerySpec();

	            querySpec.setAdvancedQueryEnabled(true);

	            int docIndex = querySpec.appendClassList(WTDocument.class, true);
	            int ibaStringValueIndex = querySpec.appendClassList(StringValue.class, false);
	            int ibaStringDefinitionIndex = querySpec.appendClassList(StringDefinition.class, false);

	            // Type condition
	            if(StringUtil.isNotEmpty(type)){
	            	  TypeDefinitionReference typeDefinitionRef = ClientTypedUtility.getTypeDefinitionReference(type);
	  	            long branchId1 = typeDefinitionRef.getKey().getBranchId();
	  	            querySpec.appendWhere(new SearchCondition(WTDocument.class, WTDocument.TYPE_DEFINITION_REFERENCE + "."
	  	                    + TypeDefinitionReference.KEY + "." + TypeDefinitionForeignKey.BRANCH_ID, SearchCondition.EQUAL,
	  	                    branchId1), new int[]
	  	            { 0 });
	  	            querySpec.appendAnd();
	            }

	            // IBA Name condition
	            ibaName = ibaName.toUpperCase();
	            ClassAttribute caIbaName = new ClassAttribute(StringDefinition.class, StringDefinition.NAME);
	            SearchCondition scStringDefinitionName = new SearchCondition(SQLFunction.newSQLFunction(SQLFunction.UPPER,
	                    caIbaName), SearchCondition.EQUAL, new ConstantExpression((Object) ibaName));
	            querySpec.appendWhere(scStringDefinitionName, new int[]
	            { ibaStringDefinitionIndex });
	            querySpec.appendAnd();

	            // IBA b condition
	            ClassAttribute caIbaValue = new ClassAttribute(StringValue.class, StringValue.VALUE2);
	            SearchCondition scStringValue = new SearchCondition(caIbaValue, SearchCondition.EQUAL,
	                    new ConstantExpression((Object) ibaVaue));
	            querySpec.appendWhere(scStringValue, new int[]
	            { ibaStringValueIndex });
	            querySpec.appendAnd();

	            // StringValue and StringDefinition connection condition
	            SearchCondition scJoinStringValueStringDefinition = new SearchCondition(StringValue.class,
	                    "definitionReference.key.id", StringDefinition.class, WTAttributeNameIfc.ID_NAME);
	            querySpec.appendWhere(scJoinStringValueStringDefinition, new int[]
	            { ibaStringValueIndex, ibaStringDefinitionIndex });
	            querySpec.appendAnd();

	            // Document and StringValue condition
	            SearchCondition scStringValueDoc = new SearchCondition(StringValue.class, "theIBAHolderReference.key.id",
	                    WTDocument.class, WTAttributeNameIfc.ID_NAME);
	            querySpec.appendWhere(scStringValueDoc, new int[]
	            { ibaStringValueIndex, docIndex });

	            LOGGER.info("getDocumentByTypeAndIBA() querySpec=" + querySpec);
	            QueryResult queryResult = PersistenceHelper.manager.find((StatementSpec) querySpec);
	            while (queryResult != null && queryResult.hasMoreElements())
	            {
	                Object[] objs = (Object[]) queryResult.nextElement();
	                WTDocument doc = (WTDocument) objs[0];
	                documentsList.add(doc);
	            }
	        } catch (RemoteException e)
	        {
	            LOGGER.error(e.getLocalizedMessage());
	            throw new WTException(e, e.getLocalizedMessage());
	        }
	        return documentsList;
	    }
}
