package ext.rdc.standard.portal.util;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ptc.core.meta.type.mgmt.server.impl.WTTypeDefinition;

import ext.rdc.standard.portal.bean.DocItem;
import ext.rdc.standard.portal.bean.PortalItem;
import ext.rdc.standard.portal.constant.StringConstant;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.FormatContentHolder;
import wt.doc.WTDocument;
import wt.fc.PagingQueryResult;
import wt.fc.QueryResult;
import wt.folder.IteratedFolderMemberLink;
import wt.folder.SubFolder;
import wt.inf.library.WTLibrary;
import wt.method.RemoteAccess;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;

public class DocumentUtil implements RemoteAccess, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<PortalItem> getNoticePortalItems() throws WTException {
		List<PortalItem> list = new ArrayList<PortalItem>();
		List<WTDocument>  docs  = getDocumentsByFolderAndLibrary4Paging(StringConstant.ContainerName.LIBRARY_STANDARD
				,StringConstant.FolderName.NOTICE_AND_DOWNLOAD
				,StringConstant.TypeName.TYPE_NOTICE);
		for(WTDocument doc : docs) {
			list.add(PortalItem.newPortalItem(doc));
		}
		CommonUtil.defaultFeedEmptyPortalItem(list);
		return list;
	}
	
	public static List<PortalItem> getDownloadPortalItems() throws WTException, PropertyVetoException {
		List<PortalItem> list = new ArrayList<PortalItem>();
		List<WTDocument>  docs  = getDocumentsByFolderAndLibrary4Paging(StringConstant.ContainerName.LIBRARY_STANDARD
				,StringConstant.FolderName.NOTICE_AND_DOWNLOAD
				,StringConstant.TypeName.TYPE_DOWNLOAD);
		for(WTDocument doc : docs) {
			list.add(PortalItem.newPortalItem4Primary(doc));
		}
		CommonUtil.defaultFeedEmptyPortalItem(list);
		return list;
	}
	public static List<DocItem> getNoticeDocItems() throws WTException, PropertyVetoException {
		List<DocItem> list = new ArrayList<DocItem>();
		List<WTDocument>  docs  = getDocumentsByFolderAndLibrary(StringConstant.ContainerName.LIBRARY_STANDARD
				,StringConstant.FolderName.NOTICE_AND_DOWNLOAD
				,StringConstant.TypeName.TYPE_NOTICE);
		for(WTDocument doc : docs) {
			list.add(DocItem.newDocItem(doc));
		}
		return list;
	}
	
	public static List<DocItem> getDownloadDocItems() throws WTException, PropertyVetoException {
		List<DocItem> list = new ArrayList<DocItem>();
		List<WTDocument>  docs  = getDocumentsByFolderAndLibrary(StringConstant.ContainerName.LIBRARY_STANDARD
				,StringConstant.FolderName.NOTICE_AND_DOWNLOAD
				,StringConstant.TypeName.TYPE_DOWNLOAD);
		for(WTDocument doc : docs) {
			list.add(DocItem.newDocItem(doc));
		}
		return list;
	}
	
	public static ApplicationData getPrimaryApplicationData(WTDocument doc) throws WTException, PropertyVetoException {
        @SuppressWarnings("deprecation")
		wt.content.ContentItem contentItem = ContentHelper.service.getPrimary((FormatContentHolder) doc);
        if (contentItem != null) {
            ApplicationData applicationdataPrimary = (ApplicationData) contentItem;
            return applicationdataPrimary;
        }
        return null;
    }
	
	//Notice
	@SuppressWarnings("deprecation")
	public static List<WTDocument> getDocumentsByFolderAndLibrary4Paging(String libraryName,String folderName,String typeName) throws WTException{
		List<WTDocument> result = new ArrayList<WTDocument>();
    	QuerySpec qs = new QuerySpec(WTDocument.class);
    	int docIndx = qs.getFromClause().getPosition(WTDocument.class);
		int lIndex = qs.appendClassList(IteratedFolderMemberLink.class, false);
		int fIndex = qs.appendClassList(SubFolder.class, false);
		int libIndex = qs.appendClassList(WTLibrary.class, false);
		int typeIndx = qs.addClassList(WTTypeDefinition.class, false);
		
        SearchCondition searchcondition = new SearchCondition(SubFolder.class, SubFolder.NAME, SearchCondition.EQUAL, folderName);
        qs.appendWhere(searchcondition,new int[] {fIndex});
        qs.appendAnd();
        searchcondition = new SearchCondition(WTLibrary.class, WTLibrary.NAME, SearchCondition.EQUAL, libraryName);
        qs.appendWhere(searchcondition,new int[] {libIndex});
        qs.appendAnd();
        searchcondition = new SearchCondition(SubFolder.class, StringConstant.DBMapping.CONTAINER_KEY_ID, WTLibrary.class, StringConstant.DBMapping.THE_INFO_ID);
        qs.appendWhere(searchcondition,new int[] {fIndex,libIndex});
        qs.appendAnd();
        searchcondition = new SearchCondition(SubFolder.class, StringConstant.DBMapping.THE_INFO_ID, IteratedFolderMemberLink.class, StringConstant.DBMapping.ROLE_A_ID);
        qs.appendWhere(searchcondition,new int[] {fIndex,lIndex});
        qs.appendAnd();
        searchcondition = new SearchCondition(IteratedFolderMemberLink.class, StringConstant.DBMapping.ROLE_B_BRANCH_ID,WTDocument.class,StringConstant.DBMapping.BRANCH_ID);
        qs.appendWhere(searchcondition,new int[] {lIndex,docIndx});
        qs.appendAnd();
		qs.appendWhere(new SearchCondition(WTDocument.class, "typeDefinitionReference.key.id",
				WTTypeDefinition.class, WTAttributeNameIfc.ID_NAME), new int[] { docIndx, typeIndx });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(WTTypeDefinition.class, WTTypeDefinition.NAME, SearchCondition.EQUAL,
				typeName), new int[] { typeIndx });
		qs.appendAnd();
        searchcondition = new SearchCondition(WTDocument.class,WTDocument.LATEST_ITERATION,SearchCondition.IS_TRUE, true);
        qs.appendWhere(searchcondition,new int[] {docIndx});
        //按修改时间排序,最新修改的在第一位
        qs.appendOrderBy(WTDocument.class,StringConstant.DBMapping.MODIFY_STAMP,true);
        PagingQueryResult qr = CommonUtil.pageQuery(StringConstant.IntConstant.ZERO, StringConstant.IntConstant.SIX, qs);
        if(qr != null) {
        	while (qr.hasMoreElements()) {
            	Object obj = qr.nextElement();
            	if(obj instanceof Object[]) {
            		result.add((WTDocument) ((Object[])obj)[0]);
            	}else {
            		result.add((WTDocument) obj);
            	}
    		}
        }
        return result;
	}
	@SuppressWarnings("deprecation")
	public static List<WTDocument> getDocumentsByFolderAndLibrary(String libraryName,String folderName,String typeName) throws WTException{
		List<WTDocument> result = new ArrayList<WTDocument>();
		QuerySpec qs = new QuerySpec(WTDocument.class);
		int docIndx = qs.getFromClause().getPosition(WTDocument.class);
		int lIndex = qs.appendClassList(IteratedFolderMemberLink.class, false);
		int fIndex = qs.appendClassList(SubFolder.class, false);
		int libIndex = qs.appendClassList(WTLibrary.class, false);
		int typeIndx = qs.addClassList(WTTypeDefinition.class, false);
		
		SearchCondition searchcondition = new SearchCondition(SubFolder.class, SubFolder.NAME, SearchCondition.EQUAL, folderName);
		qs.appendWhere(searchcondition,new int[] {fIndex});
		qs.appendAnd();
		searchcondition = new SearchCondition(WTLibrary.class, WTLibrary.NAME, SearchCondition.EQUAL, libraryName);
		qs.appendWhere(searchcondition,new int[] {libIndex});
		qs.appendAnd();
		searchcondition = new SearchCondition(SubFolder.class, StringConstant.DBMapping.CONTAINER_KEY_ID, WTLibrary.class, StringConstant.DBMapping.THE_INFO_ID);
		qs.appendWhere(searchcondition,new int[] {fIndex,libIndex});
		qs.appendAnd();
		searchcondition = new SearchCondition(SubFolder.class, StringConstant.DBMapping.THE_INFO_ID, IteratedFolderMemberLink.class, StringConstant.DBMapping.ROLE_A_ID);
		qs.appendWhere(searchcondition,new int[] {fIndex,lIndex});
		qs.appendAnd();
		searchcondition = new SearchCondition(IteratedFolderMemberLink.class, StringConstant.DBMapping.ROLE_B_BRANCH_ID,WTDocument.class,StringConstant.DBMapping.BRANCH_ID);
		qs.appendWhere(searchcondition,new int[] {lIndex,docIndx});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(WTDocument.class, "typeDefinitionReference.key.id",
				WTTypeDefinition.class, WTAttributeNameIfc.ID_NAME), new int[] { docIndx, typeIndx });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(WTTypeDefinition.class, WTTypeDefinition.NAME, SearchCondition.EQUAL,
				typeName), new int[] { typeIndx });
		qs.appendAnd();
		searchcondition = new SearchCondition(WTDocument.class,WTDocument.LATEST_ITERATION,SearchCondition.IS_TRUE, true);
		qs.appendWhere(searchcondition,new int[] {docIndx});
		//按修改时间排序,最新修改的在第一位
		qs.appendOrderBy(WTDocument.class,StringConstant.DBMapping.MODIFY_STAMP,true);
		QueryResult qr = wt.fc.PersistenceHelper.manager.find(qs);
		while (qr.hasMoreElements()) {
			Object obj = qr.nextElement();
			if(obj instanceof Object[]) {
				result.add((WTDocument) ((Object[])obj)[0]);
			}else {
				result.add((WTDocument) obj);
			}
		}
		return result;
	}
	
	
	
}