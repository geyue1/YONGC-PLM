/**
 * ext.rdc.standard.model.RDCRefineBean.java
 * @Author yge
 * 2019年9月3日下午3:06:02
 * Comment 
 */
package ext.rdc.standard.model;

import com.ptc.core.lwc.server.LWCGroupDefinition;
import com.ptc.windchill.partslink.model.RefineBean;

public class RDCRefineBean extends RefineBean{
      private String groupName;
      private LWCGroupDefinition groupDef;
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the groupDef
	 */
	public LWCGroupDefinition getGroupDef() {
		return groupDef;
	}
	/**
	 * @param groupDef the groupDef to set
	 */
	public void setGroupDef(LWCGroupDefinition groupDef) {
		this.groupDef = groupDef;
	}
      
      
}
