package ext.rdc.standard.portal.bean;

import ext.rdc.standard.portal.util.ALinkUtil;
import ext.rdc.standard.portal.util.ResourceUtil;
import wt.util.WTException;

public class LinkItem {
	private String itemLink;
	public String getItemLink() {
		return itemLink;
	}
	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}
	public static LinkItem newEmptyLinkItem() {
		LinkItem result = new LinkItem();
		result.setItemLink("");
		return result;
	}
	public static LinkItem newDocCreateLink(String resourceKey,String docType) throws WTException {
		LinkItem result = new LinkItem();
		result.setItemLink(ALinkUtil.getItemLink(ResourceUtil.getPortalResourceValue(resourceKey),ALinkUtil.getDocCreateUrl(docType)));
		return result;
	}
	public static LinkItem newLinkItem(String itemLink) throws WTException {
		LinkItem result = new LinkItem();
		result.setItemLink(itemLink);
		return result;
	}
	
	
	
	
	
}
