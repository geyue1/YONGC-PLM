/**
 * ext.rdc.standard.bean.AttrReplaceBean.java
 * @Author yge
 * 2019年10月18日上午9:57:41
 * Comment 
 */
package ext.rdc.standard.bean;

import java.util.ArrayList;
import java.util.List;

public class LengthReplaceBean {
      private String node;
      private String attrId;
      private String value;
      private List<String> replace  = new ArrayList<String>();
	/**
	 * @return the node
	 */
	public String getNode() {
		return node;
	}
	/**
	 * @param node the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}
	/**
	 * @return the attrId
	 */
	public String getAttrId() {
		return attrId;
	}
	/**
	 * @param attrId the attrId to set
	 */
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the replace
	 */
	public List<String> getReplace() {
		return replace;
	}
	/**
	 * @param replace the replace to set
	 */
	public void setReplace(List<String> replace) {
		this.replace = replace;
	}

	
      
      
}
