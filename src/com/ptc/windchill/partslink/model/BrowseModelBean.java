package com.ptc.windchill.partslink.model;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class BrowseModelBean.
 */
public class BrowseModelBean {
	

	/** The node internal name. */
	private String nodeInternalName;
	
	/** The node internal name. */
	private String nodeDisplayName;

	
	/** The node count. */
	private int nodeCount;
	
	private String imageURL;
	
	/** The immediate childs. */
	private List<BrowseModelBean> immediateChilds = new ArrayList<BrowseModelBean>();
	

	/**
	 * Gets the node count.
	 *
	 * @return the node count
	 */
	public int getNodeCount() {
		return nodeCount;
	}

	/**
	 * Sets the node count.
	 *
	 * @param nodeCount the new node count
	 */
	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}

	/**
	 * Gets the immediate childs.
	 *
	 * @return the immediate childs
	 */
	public List<BrowseModelBean> getImmediateChilds() {
		return immediateChilds;
	}

	/**
	 * Sets the immediate childs.
	 *
	 * @param immediateChilds the new immediate childs
	 */
	public void setImmediateChilds(List<BrowseModelBean> immediateChilds) {
		this.immediateChilds = immediateChilds;
	}

	/**
	 * Gets the node internal name.
	 *
	 * @return the node internal name
	 */
	public String getNodeInternalName() {
		return nodeInternalName;
	}

	/**
	 * Sets the node internal name.
	 *
	 * @param nodeInternalName the new node internal name
	 */
	public void setNodeInternalName(String nodeInternalName) {
		this.nodeInternalName = nodeInternalName;
	}
	
	
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}

	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((nodeInternalName == null) ? 0 : nodeInternalName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrowseModelBean other = (BrowseModelBean) obj;
		if (nodeInternalName == null) {
			if (other.nodeInternalName != null)
				return false;
		} else if (!nodeInternalName.equals(other.nodeInternalName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BrowseModelBean [nodeInternalName="
				+ nodeInternalName + ", nodeCount=" + nodeCount
				+ ", immediateChilds=" + immediateChilds + "]";
	}

	


}
