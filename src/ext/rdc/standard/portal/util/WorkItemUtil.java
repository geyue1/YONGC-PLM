package ext.rdc.standard.portal.util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ext.rdc.standard.portal.bean.PortalItem;
import ext.rdc.standard.portal.constant.StringConstant;
import wt.fc.PagingQueryResult;
import wt.method.RemoteAccess;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;

public class WorkItemUtil implements RemoteAccess, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<PortalItem> getMyTaskPortalItems() throws WTException {
		List<PortalItem> list = new ArrayList<PortalItem>();
		List<WorkItem>  items  = getMyTasks();
		for(WorkItem item : items) {
			list.add(PortalItem.newPortalItem(item));
		}
		CommonUtil.defaultFeedEmptyPortalItem(list);
		return list;
	}
	
	@SuppressWarnings("deprecation")
	public static List<WorkItem> getMyTasks() throws WTException{
		List<WorkItem> result = new ArrayList<WorkItem>();
		WTUser user = (WTUser)SessionHelper.getPrincipal();
		//COMPLETED
    	QuerySpec queryspec = new QuerySpec();
        queryspec = new QuerySpec(WorkItem.class);
        SearchCondition searchcondition = new SearchCondition(WorkItem.class, StringConstant.DBMapping.OWNER_KEY_ID, SearchCondition.EQUAL, user.getPersistInfo().getObjectIdentifier().getId());
        queryspec.appendWhere(searchcondition,new int[] {0});
        queryspec.appendAnd();
        searchcondition = new SearchCondition(WorkItem.class, WorkItem.STATUS, SearchCondition.NOT_EQUAL, WfAssignmentState.COMPLETED);
        queryspec.appendWhere(searchcondition,new int[] {0});
        //按修改时间排序,最新修改的在第一位
        queryspec.appendOrderBy(WorkItem.class,StringConstant.DBMapping.MODIFY_STAMP,true);
        PagingQueryResult qr = CommonUtil.pageQuery(StringConstant.IntConstant.ZERO, StringConstant.IntConstant.SIX, queryspec);
        while (qr.hasMoreElements()) {
        	Object obj = qr.nextElement();
        	if(obj instanceof Object[]) {
        		result.add((WorkItem) ((Object[])obj)[0]);
        	}else {
        		result.add((WorkItem) obj);
        	}
        	
		}
        return result;
	}
	
}