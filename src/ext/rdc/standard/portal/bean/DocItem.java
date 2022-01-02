package ext.rdc.standard.portal.bean;

import java.beans.PropertyVetoException;

import ext.rdc.standard.portal.util.CommonUtil;
import wt.doc.WTDocument;
import wt.util.WTException;

public class DocItem {
	private String docNumber;
	private String docName;
	private String docPrimary;
	private String docVersion;
	private String docState;
	private String docDesc;
	private String docModifyTime;
	private String docCreateTime;
	
	
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocPrimary() {
		return docPrimary;
	}
	public void setDocPrimary(String docPrimary) {
		this.docPrimary = docPrimary;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public String getDocState() {
		return docState;
	}
	public void setDocState(String docState) {
		this.docState = docState;
	}
	public String getDocDesc() {
		return docDesc;
	}
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}
	public String getDocModifyTime() {
		return docModifyTime;
	}
	public void setDocModifyTime(String docModifyTime) {
		this.docModifyTime = docModifyTime;
	}
	public String getDocCreateTime() {
		return docCreateTime;
	}
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	public static DocItem newDocItem(WTDocument doc) throws WTException, PropertyVetoException {
		DocItem result = new DocItem();
		result.docNumber = CommonUtil.getNumberAction(doc);
		result.docName = doc.getName();
		result.docDesc = doc.getDescription();
		result.docPrimary = CommonUtil.getPrimaryAction(doc);
		result.docCreateTime = CommonUtil.getDayFormat(doc.getCreateTimestamp());
		result.docModifyTime = CommonUtil.getDayFormat(doc.getModifyTimestamp());
		result.docState = CommonUtil.getStateDisplay(doc);
		result.docVersion = CommonUtil.getVersion(doc);
		return result;
	}
	
	
	
	
	
}
