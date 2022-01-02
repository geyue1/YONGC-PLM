package ext.rdc.standard.portal.util;
import java.beans.PropertyVetoException;
import java.io.Serializable;

import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifierHelper;

import ext.rdc.standard.portal.constant.StringConstant;
import ext.rdc.standard.portal.resource.PortalResource;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.method.RemoteAccess;
import wt.util.WTException;

public class ALinkUtil implements RemoteAccess, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String  getItemLink(String name ,String url) {
		return "<a href='"+url+ "' class='portal-link' target='_blank' style='text-decoration: none;'>"
		+ name+ "</a>";
    }
	public static String getItemLink(String name ,Persistable per ) {
		String url = StringConstant.HttpUrl.INFO_PAGE+CommonUtil.getOid(per);
		return getItemLink(name, url);
	}
	public static String getPrimaryLink(WTDocument doc) throws WTException, PropertyVetoException {
		String name  = doc.getName();
		String linkUrl = StringConstant.HttpUrl.ATTACHMENTS_DOWNLOAD_DIRECTION_SERVLET+"?oid="+CommonUtil.getOid(doc)+"&amp;oid="+CommonUtil.getOid(DocumentUtil.getPrimaryApplicationData(doc))+"&amp;role=PRIMARY";
		return getItemLink(name, linkUrl);
	}
	
	
	public static String getTaskMoreLink() throws WTException{
		return getMoreLink(StringConstant.HttpUrl.WINDCHILL_APP,true);
	}
	
	public static String getNoticeMoreLink() throws WTException{
		return getMoreLink(StringConstant.JAVASCRIPT_VOID,false);
	}
	public static String getDownloadMoreLink() throws WTException{
		return getMoreLink(StringConstant.JAVASCRIPT_VOID,false);
	}
	public static String getStandardOrderTrackReportLink() throws WTException{
		//标准件申请跟踪表报表
		return getItemLink(ResourceUtil.getPortalResourceValue(PortalResource.STANDARD_ORDER_TRACK_REPORT),"www.baidu.com");
	}
	public static String getSelectionPrincipleLink() throws WTException{
		//选型原则  www.baidu.com 替换codebase/netmarkets/jsp/ext/rdc/standard/portal/src/images/selectionPrinciple.png
		return getItemLink(ResourceUtil.getPortalResourceValue(PortalResource.SELECTION_PRINCIPLE),StringConstant.HttpUrl.PORTAL_SELECTION_PRINCIPLE);
	}
	public static String getSearchAlternativesSchemeLink() throws WTException{
		//搜索替代方案
		return getItemLink(ResourceUtil.getPortalResourceValue(PortalResource.SEARCH_ALTERNATIVES_SCHEME),"replacePart1.jsp");
	}
	public static String getModelAnalysisReporLink() throws WTException{
		//车型分析报告
		return getItemLink(ResourceUtil.getPortalResourceValue(PortalResource.MODEL_ANALYSIS_REPOR),"www.baidu.com");
	}
	
	public static String getDocCreateUrl(String docType) throws WTException{
		TypeIdentifier ti = TypeIdentifierHelper.getTypeIdentifier(docType);
		String fullDocType = ti==null?docType:ti.toExternalForm().replace("WCTYPE|", "").replace(StringConstant.OR_CHAR, StringConstant.OR_CHAR_URL);
		String cOid = CommonUtil.getOid(CommonUtil.getLibrary(StringConstant.ContainerName.LIBRARY_STANDARD));
		return StringConstant.HttpUrl.PORTAL_DOC_CREATE+"&ContainerOid="+cOid+"&oid="+cOid+"&type="+fullDocType;
	}
	
	
	public static String getMoreLink(String url ,boolean isBlank) throws WTException {
		String moreName = ResourceUtil.getPortalResourceValue(PortalResource.VIEW_MORE);
		return "<a href='"+url+"' "+(isBlank?"target='_blank'":"")+" class='look-more el-link el-link--warning is-underline'><span class='el-link--inner'>&gt;&gt;&gt;"+moreName+"</span></a>";
	}
	public static String getFolderLink(String libraryName ,String folderName ) throws WTException {
		String url = StringConstant.HttpUrl.INFO_PAGE+CommonUtil.getOid(CommonUtil.getSubFolder(libraryName, folderName))
		+"&ContainerOid="+CommonUtil.getOid(CommonUtil.getLibrary(libraryName));
		return url;
	}
	
}