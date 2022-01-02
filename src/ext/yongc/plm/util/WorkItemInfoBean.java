/**
 * ext.yongc.plm.util.WorkItemInfoBean.java
 * @Author yge
 * 2017年7月31日上午9:19:55
 * Comment 
 */
package ext.yongc.plm.util;

import java.util.Date;


public class WorkItemInfoBean {
	
	private String procName = "";
	
	private String procCreator = "";
	
	private String procDate = "";
	
	private String workName = "";
	
	private String workAssignee = "";
	
	private String workCompletedBy = "";
	
	private String workRole = "";
	
	private String workVote = "";
	
	private String workComments = "";
	
	private String workDeadline = "";
	
	private String workCompletedDate = "";
	
	private Date completedDate = new Date();
	
	private int tripCount;//用于区分不同的任务是否有同一个活动节点实例分配
	
	public int getTripCount() {
		return tripCount;
	}
	public void setTripCount(int tripCount) {
		this.tripCount=tripCount;
	}	
	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public String getProcCreator() {
		return procCreator;
	}

	public void setProcCreator(String procCreator) {
		this.procCreator = procCreator;
	}

	public String getProcDate() {
		return procDate;
	}

	public void setProcDate(String procDate) {
		this.procDate = procDate;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkAssignee() {
		return workAssignee;
	}

	public void setWorkAssignee(String workAssignee) {
		this.workAssignee = workAssignee;
	}

	public String getWorkRole() {
		return workRole;
	}

	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}

	public String getWorkVote() {
		return workVote;
	}

	public void setWorkVote(String workVote) {
		this.workVote = workVote;
	}

	public String getWorkComments() {
		return workComments;
	}

	public void setWorkComments(String workComments) {
		this.workComments = workComments;
	}

	public String getWorkDeadline() {
		return workDeadline;
	}

	public void setWorkDeadline(String workDeadline) {
		this.workDeadline = workDeadline;
	}

	public String getWorkCompletedDate() {
		return workCompletedDate;
	}

	public void setWorkCompletedDate(String workCompletedDate) {
		this.workCompletedDate = workCompletedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((procCreator == null) ? 0 : procCreator.hashCode());
		result = prime * result
				+ ((procDate == null) ? 0 : procDate.hashCode());
		result = prime * result
				+ ((procName == null) ? 0 : procName.hashCode());
		result = prime * result
				+ ((workName == null) ? 0 : workName.hashCode());
//		result = prime * result
//				+ ((workRole == null) ? 0 : workRole.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkItemInfoBean other = (WorkItemInfoBean) obj;
		if (procCreator == null) {
			if (other.procCreator != null)
				return false;
		} else if (!procCreator.equals(other.procCreator))
			return false;
		if (procDate == null) {
			if (other.procDate != null)
				return false;
		} else if (!procDate.equals(other.procDate))
			return false;
		if (procName == null) {
			if (other.procName != null)
				return false;
		} else if (!procName.equals(other.procName))
			return false;
		if (workName == null) {
			if (other.workName != null)
				return false;
		} else if (!workName.equals(other.workName))
			return false;
//		if (workRole == null) {
//			if (other.workRole != null)
//				return false;
//		} else if (!workRole.equals(other.workRole))
//			return false;
		return true;
	}
	
	
	/**
	 * @return the workCompletedBy
	 */
	public String getWorkCompletedBy() {
		return workCompletedBy;
	}
	/**
	 * @param workCompletedBy the workCompletedBy to set
	 */
	public void setWorkCompletedBy(String workCompletedBy) {
		this.workCompletedBy = workCompletedBy;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkItemInfoBean [procName=" + procName + ", procCreator="
				+ procCreator + ", procDate=" + procDate + ", workName="
				+ workName + ", workAssignee=" + workAssignee + ", workCompletedBy="+workCompletedBy+", workRole="
				+ workRole + ", workVote=" + workVote + ", workComments="
				+ workComments + ", workDeadline=" + workDeadline
				+ ", workCompletedDate=" + workCompletedDate
				+ ", completedDate=" + completedDate + ", tripCount="
				+ tripCount + "]";
	}

	
}

