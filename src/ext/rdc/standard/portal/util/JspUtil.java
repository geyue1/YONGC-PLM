package ext.rdc.standard.portal.util;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ext.rdc.standard.portal.bean.DocItem;
import ext.rdc.standard.portal.bean.LinkItem;
import ext.rdc.standard.portal.bean.PartItem;
import ext.rdc.standard.portal.bean.PortalItem;
import ext.rdc.standard.portal.constant.StringConstant;
import wt.method.RemoteAccess;
import wt.util.WTException;

public class JspUtil implements RemoteAccess, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String getMyTaskJSONString() throws WTException {
		List<PortalItem> list = WorkItemUtil.getMyTaskPortalItems();
		return CommonUtil.getJSONString(list);
	}
	
	public static String getNoticeJSONString() throws WTException {
		List<PortalItem> list = DocumentUtil.getNoticePortalItems();
		return CommonUtil.getJSONString(list);
	}
	
	public static String getDownloadJSONString() throws WTException, PropertyVetoException {
		List<PortalItem> list = DocumentUtil.getDownloadPortalItems();
		return CommonUtil.getJSONString(list);
	}
	public static String getMoreNoticeJSONString() throws WTException, PropertyVetoException {
		List<DocItem> list = DocumentUtil.getNoticeDocItems();
		return CommonUtil.getJSONString(list);
	}
	
	public static String getMoreDownloadJSONString() throws WTException, PropertyVetoException {
		List<DocItem> list = DocumentUtil.getDownloadDocItems();
		return CommonUtil.getJSONString(list);
	}
	public static String getResourceJSONString() throws WTException, PropertyVetoException, JSONException {
		JSONObject json = CommonUtil.getPortalResourceJSON();
		return json.toJSONString();
	}
	public static String getSeartPartJSONString(String searchKey) throws WTException, PropertyVetoException, JSONException {
		List<PartItem> list = PartUtil.searchWTParts(searchKey);
		return CommonUtil.getJSONString(list);
	}
	public static String getCreateApplyJSONString() throws WTException, PropertyVetoException, JSONException {
		List<LinkItem> list = new ArrayList<LinkItem>();
		list.add(LinkItem.newDocCreateLink(StringConstant.JSONKey.STANDARD_ORDER_CREATE, StringConstant.TypeName.TYPE_SQD_STANDARD_PARTS));
		list.add(LinkItem.newDocCreateLink(StringConstant.JSONKey.MAJOR_STANDARD_ORDER_CREATE, StringConstant.TypeName.TYPE_PROFESSIONAL_STANDARD_PARTS_SQD));
		list.add(LinkItem.newDocCreateLink(StringConstant.JSONKey.STANDARD_TEST_OUTLINE_ORDER_CREATE, StringConstant.TypeName.TYPE_APPLICATION_STANDARD_PARTS));
		list.add(LinkItem.newDocCreateLink(StringConstant.JSONKey.STANDARD_TEST_REPORT_ORDER_CREATE, StringConstant.TypeName.TYPE_TEST_REPORT));
		list.add(LinkItem.newEmptyLinkItem());
		list.add(LinkItem.newEmptyLinkItem());
		return CommonUtil.getJSONString(list);
	}
	public static String getReportJSONString() throws WTException, PropertyVetoException, JSONException {
		List<LinkItem> list = new ArrayList<LinkItem>();
		list.add(LinkItem.newLinkItem(ALinkUtil.getStandardOrderTrackReportLink()));
		list.add(LinkItem.newEmptyLinkItem());
		list.add(LinkItem.newEmptyLinkItem());
		list.add(LinkItem.newEmptyLinkItem());
		list.add(LinkItem.newEmptyLinkItem());
		list.add(LinkItem.newEmptyLinkItem());
		return CommonUtil.getJSONString(list);
	}
	public static String getSelectionModelJSONString() throws WTException, PropertyVetoException, JSONException {
		List<LinkItem> list = new ArrayList<LinkItem>();
		list.add(LinkItem.newLinkItem(ALinkUtil.getSelectionPrincipleLink()));
		list.add(LinkItem.newLinkItem(ALinkUtil.getSearchAlternativesSchemeLink()));
		list.add(LinkItem.newLinkItem(ALinkUtil.getModelAnalysisReporLink()));
		list.add(LinkItem.newEmptyLinkItem());
		list.add(LinkItem.newEmptyLinkItem());
		list.add(LinkItem.newEmptyLinkItem());
		return CommonUtil.getJSONString(list);
	}
	
}