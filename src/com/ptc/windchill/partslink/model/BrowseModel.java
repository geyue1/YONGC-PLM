package com.ptc.windchill.partslink.model;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class BrowseModel   {
	
	private String currentNodeInternalName;

	private String currentNodeDisplayName;

	private int matchCount;

	private Set<BrowseModelBean> browseBeans = new TreeSet<BrowseModelBean>(new BrowseModelBeanComparator());

	private Map<String,String> parentNodes = new TreeMap<String,String>();


	public String getCurrentNodeDisplayName() {
		return currentNodeDisplayName;
	}

	public void setCurrentNodeDisplayName(String currentNodeDisplayName) {
		this.currentNodeDisplayName = currentNodeDisplayName;
	}

	public String getCurrentNodeInternalName() {
		return currentNodeInternalName;
	}

	public void setCurrentNodeInternalName(String currentNodeInternalName) {
		this.currentNodeInternalName = currentNodeInternalName;
	}

	public Set<BrowseModelBean> getBrowseBeans() {
		return browseBeans;
	}

	public void setBrowseBeans(SortedSet<BrowseModelBean> browseBeans) {
		this.browseBeans = browseBeans;
	}

	public Map<String, String> getParentNodes() {
		return parentNodes;
	}

	public void setParentNodes(Map<String, String> parentNodes) {
		this.parentNodes = parentNodes;
	}

	public boolean isLeaf() {
		return !(browseBeans.size() > 0);

	}

	public int getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	@Override
	public String toString() {
		return "BrowseModel [currentNodeInternalName="
				+ currentNodeInternalName + ", currentNodeDisplayName="
				+ currentNodeDisplayName + ", matchCount=" + matchCount
				+ ", browseBeans=" + browseBeans + ", parentNodes="
				+ parentNodes + "]";
	}
}

class BrowseModelBeanComparator implements Comparator<BrowseModelBean>{
	@Override
	public int compare(BrowseModelBean browseModelBean1, BrowseModelBean browseModelBean2) {
		int result = (browseModelBean1.getNodeDisplayName().toUpperCase()).compareTo(browseModelBean2.getNodeDisplayName().toUpperCase());
		if(result == 0){
			return browseModelBean1.getNodeInternalName().compareTo(browseModelBean2.getNodeInternalName());
		}
		return result;
	}
}
