package ext.rdc.standard.portal.util;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ptc.core.meta.type.mgmt.server.impl.WTTypeDefinition;

import ext.rdc.standard.portal.bean.PartItem;
import ext.rdc.standard.portal.constant.StringConstant;
import wt.enterprise.Master;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;

public class PartUtil implements RemoteAccess, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static List<PartItem> searchWTParts(String searchKey) throws WTException, PropertyVetoException {
		List<PartItem> list = new ArrayList<PartItem>();
		List<WTPart>  parts  = searchWTParts(searchKey 
				,StringConstant.TypeName.TYPE_WTPART);//标准件搜索(,号)隔开，传递软类型即可.， www.baidu.com
		for(WTPart part : parts) {
			list.add(PartItem.newPartItem(part));
		}
		return list;
	}
	
	@SuppressWarnings("deprecation")
	public static List<WTPart> searchWTParts(String searchKey,String... typeNames) throws WTException{
		List<WTPart> result = new ArrayList<WTPart>();
		QuerySpec qs = new QuerySpec(WTPart.class);
		int partIndx = qs.getFromClause().getPosition(WTPart.class);
		qs.setAdvancedQueryEnabled(true);
		int typeIndx = qs.addClassList(WTTypeDefinition.class, false);
		
		SearchCondition searchcondition = new SearchCondition(WTPart.class, WTPart.NAME, SearchCondition.EQUAL, searchKey);
		qs.appendOpenParen();
		qs.appendWhere(searchcondition,new int[] {partIndx});
		qs.appendOr();
		searchcondition = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, searchKey);
		qs.appendWhere(searchcondition,new int[] {partIndx});
		qs.appendCloseParen();
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(WTPart.class, "typeDefinitionReference.key.id",
				WTTypeDefinition.class, WTAttributeNameIfc.ID_NAME), new int[] { partIndx, typeIndx });
		qs.appendAnd();
		ArrayExpression ae = new ArrayExpression(typeNames);
		qs.appendWhere(new SearchCondition(
				new ClassAttribute(WTTypeDefinition.class, WTTypeDefinition.NAME),SearchCondition.IN, ae)
				, new int[] { typeIndx });
		qs.appendAnd();
		searchcondition = new SearchCondition(WTPart.class,WTPart.LATEST_ITERATION,SearchCondition.IS_TRUE, true);
		qs.appendWhere(searchcondition,new int[] {partIndx});
		//按修改时间排序,最新修改的在第一位
		qs.appendOrderBy(WTPart.class,StringConstant.DBMapping.MODIFY_STAMP,true);
		QueryResult qr = PersistenceHelper.manager.find(qs);
		Set<String> numbers = new HashSet<String>();
		while (qr.hasMoreElements()) {
			WTPart part = null;
			Object obj = qr.nextElement();
			if(obj instanceof Object[]) {
				part = (WTPart) ((Object[])obj)[0];
			}else {
				part = (WTPart) obj;
			}
			if(part!=null && !numbers.contains(part.getNumber())) {
				result.add(CommonUtil.getLatestObject((Master)part.getMaster(), WTPart.class));
			}
			
		}
		return result;
	}
	
	
	
}