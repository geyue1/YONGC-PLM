package ext.rdc.standard.portal.bean;

import java.beans.PropertyVetoException;

import ext.rdc.standard.portal.util.CommonUtil;
import wt.part.WTPart;
import wt.util.WTException;

public class PartItem {
	private String partNumber;
	private String partName;
	private String containerName;
	private String partVersion;
	private String partState;
	private String partModifyTime;
	private String partCreateTime;
	
	public String getPartNumber() {
		return partNumber;
	}



	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}



	public String getPartName() {
		return partName;
	}



	public void setPartName(String partName) {
		this.partName = partName;
	}



	public String getContainerName() {
		return containerName;
	}



	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}



	public String getPartVersion() {
		return partVersion;
	}



	public void setPartVersion(String partVersion) {
		this.partVersion = partVersion;
	}



	public String getPartState() {
		return partState;
	}



	public void setPartState(String partState) {
		this.partState = partState;
	}



	public String getPartModifyTime() {
		return partModifyTime;
	}



	public void setPartModifyTime(String partModifyTime) {
		this.partModifyTime = partModifyTime;
	}



	public String getPartCreateTime() {
		return partCreateTime;
	}



	public void setPartCreateTime(String partCreateTime) {
		this.partCreateTime = partCreateTime;
	}



	public static PartItem newPartItem(WTPart part) throws WTException, PropertyVetoException {
		PartItem result = new PartItem();
		result.partNumber = CommonUtil.getNumberAction(part);
		result.partName = part.getName();
		result.partCreateTime = CommonUtil.getDayFormat(part.getCreateTimestamp());
		result.partModifyTime = CommonUtil.getDayFormat(part.getModifyTimestamp());
		result.partState = CommonUtil.getStateDisplay(part);
		result.partVersion = CommonUtil.getVersion(part);
		result.containerName = part.getContainerName();
		return result;
	}
	
	
	
	
	
}
