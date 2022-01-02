package ext.rdc.standard.portal.bean;

import java.beans.PropertyVetoException;

import ext.rdc.standard.portal.util.ALinkUtil;
import ext.rdc.standard.portal.util.CommonUtil;
import wt.doc.WTDocument;
import wt.util.WTException;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;

public class PortalItem {
	private String itemLink;
	private String dateString;
	public String getItemLink() {
		return itemLink;
	}
	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public static PortalItem newEmptyPortalItem() {
		PortalItem result = new PortalItem();
		result.setItemLink("");
		result.setDateString("");
		return result;
	}
	public static PortalItem newPortalItem(WorkItem item) {
		WfAssignedActivity wfassignedActivity = (WfAssignedActivity)item.getSource().getObject();
		String taskName  = wfassignedActivity.getName();
		PortalItem result = new PortalItem();
		result.setItemLink(ALinkUtil.getItemLink(taskName, item));
		result.setDateString(CommonUtil.getDayFormat(item.getModifyTimestamp()));
		return result;
	}
	public static PortalItem newPortalItem(WTDocument doc) {
		String taskName  = doc.getName();
		PortalItem result = new PortalItem();
		result.setItemLink(ALinkUtil.getItemLink(taskName, doc));
		result.setDateString(CommonUtil.getDayFormat(doc.getModifyTimestamp()));
		return result;
	}
	
	public static PortalItem newPortalItem4Primary(WTDocument doc) throws WTException, PropertyVetoException {
		
		PortalItem result = new PortalItem();
		result.setItemLink(ALinkUtil.getPrimaryLink(doc));
		result.setDateString(CommonUtil.getDayFormat(doc.getModifyTimestamp()));
		return result;
	}
	
	
	
	
}
