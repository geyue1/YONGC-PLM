/**
 * ext.yongc.plm.util.PartUtil.java
 * @Author yge
 * 2017年7月17日下午6:06:49
 * Comment 
 */
package ext.yongc.plm.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.ptc.core.logging.Log;
import com.ptc.core.logging.LogFactory;

import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.iba.definition.StringDefinition;
import wt.iba.definition.TimestampDefinition;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.litedefinition.StringDefView;
import wt.iba.definition.litedefinition.TimestampDefView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.StringValue;
import wt.iba.value.TimestampValue;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.part.WTPartMaster;
import wt.part.WTPartReferenceLink;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.WhereExpression;
import wt.session.SessionServerHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.config.LatestConfigSpec;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewManageable;
import wt.vc.wip.WorkInProgressHelper;

public class PartUtil implements RemoteAccess, Serializable{
	private static Log log = LogFactory.getLog(PartUtil.class);
	private static final int[] DEFAULT_CONDITION_ARRAY = new int[] { 0 };
	
	 /**
     * Get the Latest Part by PartMaster
     * 
     * @param partMaster
     * @return
     * @throws WTException
     */
    public static WTPart getLatestPartByMaster(WTPartMaster partMaster) throws WTException {
        WTPart latestPart = null;
        QueryResult qr = VersionControlHelper.service.allIterationsOf(partMaster);
        if (qr.hasMoreElements()) {
            latestPart = (WTPart) qr.nextElement();
        }
        return latestPart;
    }
    /**
     * Get part object by number,default as design view.
     * 
     * @param partNo
     *            part number
     * @return part object
     * @throws wt.util.WTException
     *             Windchill exception
     * @see wt.part.WTPart
     */
    public static WTPart getPartByNumber(String partNo) throws WTException {
        WTPart part = null;
        if (!StringUtil.isEmpty(partNo)) {
            part = getPartByNoAndView(partNo, "Design");
        }
        return part;
    }
    public static WTPart getPartByName(String partName) throws WTException {
        WTPart part = null;
        if (!StringUtil.isEmpty(partName)) {
            part = getPartByNameAndView(partName, "Design");
        }
        return part;
    }
    public static WTPart getPartByNameAndView(String partName, String viewName)
            throws WTException {
        WTPart part = null;
        if (!StringUtil.isEmpty(partName) && !StringUtil.isEmpty(viewName)) {
            if (RemoteMethodServer.ServerFlag) {
                StatementSpec stmtSpec = new QuerySpec(WTPart.class);
                WhereExpression where = new SearchCondition(WTPart.class,
                        WTPart.NAME, SearchCondition.EQUAL,
                        partName.trim());
                QuerySpec querySpec = (QuerySpec) stmtSpec;
                querySpec.appendWhere(where, DEFAULT_CONDITION_ARRAY);
                querySpec.appendAnd();
                View view = ViewHelper.service.getView(viewName);
                ObjectIdentifier objId = PersistenceHelper
                        .getObjectIdentifier(view);
                where = new SearchCondition(WTPart.class, ViewManageable.VIEW
                        + "." + ObjectReference.KEY, SearchCondition.EQUAL,
                        objId);
                querySpec.appendWhere(where, DEFAULT_CONDITION_ARRAY);
                QueryResult qr = PersistenceServerHelper.manager
                        .query(stmtSpec);
                if (qr.hasMoreElements()) {
                    LatestConfigSpec configSpec = new LatestConfigSpec();
                    qr = configSpec.process(qr);
                    part = (WTPart) qr.nextElement();
                }
            } else {
                ReflectionUtil.invokeOnMethodServer("getPartByNameAndView",
                        PartUtil.class, null, new Class[] { String.class,
                                String.class },
                        new Object[] { partName, viewName });
            }
        }
        return part;
    }
    /**
     * Get part by number and view name
     * 
     * @param partNo
     *            part number
     * @param viewName
     *            view name
     * @return part
     * @throws WTException
     *             Windchill exception
     */
    public static WTPart getPartByNoAndView(String partNo, String viewName)
            throws WTException {
        WTPart part = null;
        if (!StringUtil.isEmpty(partNo) && !StringUtil.isEmpty(viewName)) {
            if (RemoteMethodServer.ServerFlag) {
                StatementSpec stmtSpec = new QuerySpec(WTPart.class);
                WhereExpression where = new SearchCondition(WTPart.class,
                        WTPart.NUMBER, SearchCondition.EQUAL,
                        partNo.toUpperCase());
                QuerySpec querySpec = (QuerySpec) stmtSpec;
                querySpec.appendWhere(where, DEFAULT_CONDITION_ARRAY);
                querySpec.appendAnd();
                View view = ViewHelper.service.getView(viewName);
                ObjectIdentifier objId = PersistenceHelper
                        .getObjectIdentifier(view);
                where = new SearchCondition(WTPart.class, ViewManageable.VIEW
                        + "." + ObjectReference.KEY, SearchCondition.EQUAL,
                        objId);
                querySpec.appendWhere(where, DEFAULT_CONDITION_ARRAY);
                QueryResult qr = PersistenceServerHelper.manager
                        .query(stmtSpec);
                if (qr.hasMoreElements()) {
                    LatestConfigSpec configSpec = new LatestConfigSpec();
                    qr = configSpec.process(qr);
                    part = (WTPart) qr.nextElement();
                }
            } else {
                ReflectionUtil.invokeOnMethodServer("getPartByNoAndView",
                        PartUtil.class, null, new Class[] { String.class,
                                String.class },
                        new Object[] { partNo, viewName });
            }
        }
        return part;
    }

    
    /**
     * is doc checkout
     * @param doc
     * @return
     * @throws WTException 
     * @throws  
     */
    public static boolean isCheckout(WTPart part) throws InvocationTargetException,WTException{
    	if(part == null){
    		return false;
    	}
    	 if (!part.isLatestIteration())
         {
             part = (WTPart) VersionControlHelper.service.getLatestIteration(part, false);
         }
         if (WorkInProgressHelper.isCheckedOut(part) || WorkInProgressHelper.isWorkingCopy(part))
         {
             return true;
         }
         return false;
    }
    /**
     * 建立部件与文档之间的描述关联（不对小版本进行升级）
     * 
     * @param partRoleA
     *            部件A
     * @param docRoleB
     *            文档B
     * @return 关联后的WTPartDescribeLink对象
     */
    public static WTPartDescribeLink associateDescDocToPart(WTPart partRoleA, WTDocument docRoleB) {
        WTPartDescribeLink result = null;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (WTPartDescribeLink) RemoteMethodServer.getDefault().invoke("associateDescDocToPart",
                        PartUtil.class.getName(), null, new Class[] { WTPart.class, WTDocument.class },
                        new Object[] { partRoleA, docRoleB });
            } else {
                boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
                try {
                    // 检查是否文档间已经有连接，得到WTPartDescribeLink
                    QueryResult queryresult = PersistenceHelper.manager.find(WTPartDescribeLink.class, partRoleA,
                            WTPartDescribeLink.DESCRIBES_ROLE, docRoleB);
                    if (queryresult == null || queryresult.size() == 0) {
                        // 如果没有连接，则建立
                        WTPartDescribeLink wtpartdescribelink = WTPartDescribeLink.newWTPartDescribeLink(partRoleA,
                                docRoleB);
                        PersistenceServerHelper.manager.insert(wtpartdescribelink);

                        wtpartdescribelink = (WTPartDescribeLink) PersistenceHelper.manager.refresh(wtpartdescribelink);
                        result = wtpartdescribelink;
                    } else {
                        // 如果有连接，则返回该连接
                        return (WTPartDescribeLink) queryresult.nextElement();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    SessionServerHelper.manager.setAccessEnforced(enforce);
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 建立部件与文档之间的参考关联（不对小版本进行升级）
     * 
     * @param partRoleA
     *            部件A
     * @param docMasterRoleB
     *            文档B (WTDocumentMaster)
     * @return 关联后的WTPartReferenceLink对象
     */
    public static WTPartReferenceLink associateRefDocToPart(WTPart partRoleA, WTDocumentMaster docMasterRoleB) {
        WTPartReferenceLink result = null;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (WTPartReferenceLink) RemoteMethodServer.getDefault().invoke("associateRefDocToPart",
                        PartUtil.class.getName(), null, new Class[] { WTPart.class, WTDocumentMaster.class },
                        new Object[] { partRoleA, docMasterRoleB });
            } else {
                boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
                try {
                    // 检查是否文档间已经有连接，得到WTPartReferenceLink
                    QueryResult queryresult = PersistenceHelper.manager.find(WTPartReferenceLink.class, partRoleA,
                            WTPartReferenceLink.REFERENCED_BY_ROLE, docMasterRoleB);
                    if (queryresult == null || queryresult.size() == 0) {
                        // 如果没有连接，则建立
                        WTPartReferenceLink wtpartreferencelink = WTPartReferenceLink.newWTPartReferenceLink(partRoleA,
                                docMasterRoleB);
                        PersistenceServerHelper.manager.insert(wtpartreferencelink);

                        wtpartreferencelink = (WTPartReferenceLink) PersistenceHelper.manager
                                .refresh(wtpartreferencelink);
                        result = wtpartreferencelink;
                    } else {
                        // 如果有连接，则返回该连接
                        return (WTPartReferenceLink) queryresult.nextElement();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    SessionServerHelper.manager.setAccessEnforced(enforce);
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除部件与描述文档之间的关联（不对小版本进行升级）
     * 
     * @param partRoleA
     *            部件A
     * @param docRoleB
     *            文档B
     * @return 删除操作是否成功
     */
    public static boolean removeDescDocToPart(WTPart partRoleA, WTDocument docRoleB) {
        boolean result = false;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Boolean) RemoteMethodServer.getDefault().invoke("removeDescDocToPart", PartUtil.class.getName(),
                        null, new Class[] { WTPart.class, WTDocument.class }, new Object[] { partRoleA, docRoleB });
            } else {
                boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
                try {
                    // 检查是否文档间已经有连接，得到WTPartReferenceLink
                    QueryResult queryresult = PersistenceHelper.manager.find(WTPartDescribeLink.class, partRoleA,
                            WTPartDescribeLink.DESCRIBES_ROLE, docRoleB);
                    if (queryresult == null || queryresult.size() == 0) {
                        // 如果没有连接，则退出
                        return true;
                    } else {
                        // 如果有连接，则删除改连接
                        WTPartDescribeLink link = (WTPartDescribeLink) queryresult.nextElement();
                        PersistenceServerHelper.manager.remove(link);
                        result = true;
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    result = false;
                } finally {
                    SessionServerHelper.manager.setAccessEnforced(enforce);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 删除部件与描述文档之间的关联（不对小版本进行升级）
     * 
     * @param partRoleA
     *            部件A
     * @param docMasterRoleB
     *            文档B (WTDocumentMaster)
     * @return 删除操作是否成功
     */
    public static boolean removeRefDocToPart(WTPart partRoleA, WTDocumentMaster docMasterRoleB) {
        boolean result = false;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Boolean) RemoteMethodServer.getDefault().invoke("removeRefDocToPart", PartUtil.class.getName(),
                        null, new Class[] { WTPart.class, WTDocumentMaster.class },
                        new Object[] { partRoleA, docMasterRoleB });
            } else {
                boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
                try {
                    // 检查是否文档间已经有连接，得到WTPartReferenceLink
                    QueryResult queryresult = PersistenceHelper.manager.find(WTPartReferenceLink.class, partRoleA,
                            WTPartReferenceLink.REFERENCED_BY_ROLE, docMasterRoleB);
                    if (queryresult == null || queryresult.size() == 0) {
                        // 如果没有连接，则退出
                        return true;
                    } else {
                        // 如果有连接，则删除改连接
                        WTPartReferenceLink link = (WTPartReferenceLink) queryresult.nextElement();
                        PersistenceServerHelper.manager.remove(link);
                        result = true;
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    result = false;
                } finally {
                    SessionServerHelper.manager.setAccessEnforced(enforce);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    public static List<WTDocument> getDescDocByPart(WTPart part) throws WTException{
    	List<WTDocument> list = new ArrayList<WTDocument>();
    	boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
    	 QueryResult qr=PersistenceHelper.manager.navigate(part, WTPartDescribeLink.ROLE_BOBJECT_ROLE, WTPartDescribeLink.class);
    	 Object obj = null;
    	 while(qr.hasMoreElements()){
    		 obj = qr.nextElement();
    		 if(obj instanceof WTDocument){
    			 list.add((WTDocument)obj);
    		 }
    	 }
    	 wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
    	 return list;
    }
    public static List<WTDocument> getReferenceDocByPart(WTPart part) throws WTException{
    	List<WTDocument> list = new ArrayList<WTDocument>();
    	boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
    	 QueryResult qr=PersistenceHelper.manager.navigate(part, WTPartReferenceLink.ROLE_BOBJECT_ROLE, WTPartReferenceLink.class);
    	 Object obj = null;
    	 while(qr.hasMoreElements()){
    		 obj = qr.nextElement();
    		 if(obj instanceof WTDocumentMaster){
    			 WTDocumentMaster master = (WTDocumentMaster)obj;
    			 QueryResult qqr=VersionControlHelper.service.allIterationsOf(master);
    			 if(qqr.size()>0){
    				 list.add((WTDocument)qqr.nextElement());
    			 }
    			
    		 }
    	 }
    	 wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
    	 return list;
    }
    
	public static Vector getPartsByTypeAndIBA(String softType,
			Map ibaConditionMap, boolean isLatest) {
		if (!RemoteMethodServer.ServerFlag) {
			try {
				return (Vector) RemoteMethodServer.getDefault().invoke(
						"getPartsByTypeAndIBA", PartUtil.class.getName(),
						null,
						new Class[] { String.class, Map.class, Boolean.class },
						new Object[] { softType, ibaConditionMap, isLatest });
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
		try {
			Vector vec = new Vector();
			QuerySpec querySpec = new QuerySpec(WTPart.class);
			TypeDefinitionReference softTypeDefRef = TypedUtilityServiceHelper.service
					.getTypeDefinitionReference(softType);
			SearchCondition typeCondition = new SearchCondition(
					WTPart.class, WTPart.TYPE_DEFINITION_REFERENCE
							+ ".key.branchId", SearchCondition.EQUAL,
					softTypeDefRef.getKey().getBranchId());
			querySpec.appendWhere(typeCondition);
			if (BooleanUtils.isTrue(isLatest)) {
				querySpec = new LatestConfigSpec()
						.appendSearchCriteria(querySpec);
			}
			if (!MapUtils.isEmpty(ibaConditionMap)) {
				for (Iterator iter = ibaConditionMap.entrySet().iterator(); iter
						.hasNext();) {
					Entry entry = (Entry) iter.next();
					// log.debug("+++++++++++++++++entry.getKey():"+entry.getKey());
					// log.debug("+++++++++++++++++entry.getValue().toString().toUpperCase():"+entry
					// .getValue().toString().toUpperCase());
					// update by Ken Shao 2011/9/14, set value to upper case.
					if (entry.getValue().toString().indexOf("%") >= 0) {
						appendStringIBACondition(querySpec, entry.getKey()
								.toString(), SearchCondition.LIKE, entry
								.getValue().toString().toUpperCase());
					} else if (entry.getValue().toString().indexOf(">") >= 0) {
						String value = entry.getValue().toString()
								.toUpperCase();
						value = value.substring(0, value.indexOf(">"));
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Timestamp startTimeStamp = null;
						java.util.Date startTS = sdf.parse(value);
						startTimeStamp = Timestamp.valueOf(sdf.format(startTS));
						appendTimeStampIBACondition(querySpec, entry.getKey()
								.toString(),
								SearchCondition.GREATER_THAN_OR_EQUAL,
								startTimeStamp);
					} else if (entry.getValue().toString().indexOf("<") >= 0) {
						String value = entry.getValue().toString()
								.toUpperCase();
						value = value.substring(0, value.indexOf("<"));
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Timestamp endTimeStamp = null;
						java.util.Date endTS = sdf.parse(value);
						endTimeStamp = Timestamp.valueOf(sdf.format(endTS));
						appendTimeStampIBACondition(querySpec, entry.getKey()
								.toString(),
								SearchCondition.LESS_THAN_OR_EQUAL,
								endTimeStamp);
					} else {
						appendStringIBACondition(querySpec, entry.getKey()
								.toString(), SearchCondition.EQUAL, entry
								.getValue().toString().toUpperCase());
					}

				}
			}
			querySpec.appendOrderBy(WTPart.class,
					WTPart.MODIFY_TIMESTAMP, true);
			log.debug("querySpec=" + querySpec);
			QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
			System.out.println("**** querySpec = "+querySpec);
			while (queryResult.hasMoreElements()) {
				Object obj = queryResult.nextElement();
				if (obj instanceof Persistable) {
					WTPart part = (WTPart) obj;
					if (!part.getLocation().startsWith("/Default"))
						continue;
					vec.add(obj);
				} else if (obj instanceof Persistable[]) {
					WTPart part = (WTPart) (((Persistable[]) obj)[0]);
					if (!part.getLocation().startsWith("/Default"))
						continue;
					vec.add(((Persistable[]) obj)[0]);
				}
			}
			return vec;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}
	private static StringDefinition getStringDefByPath(String ibaKey) {
		if (!RemoteMethodServer.ServerFlag) {
			try {
				return (StringDefinition) RemoteMethodServer.getDefault()
						.invoke("getStringDefByPath", PartUtil.class.getName(),
								null, new Class[] { String.class },
								new Object[] { ibaKey });
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
		StringDefinition stringDef = null;
		try {
			AttributeDefDefaultView defView = IBADefinitionHelper.service
					.getAttributeDefDefaultViewByPath(ibaKey);
			// log.debug("DEFView=" + defView);
			if (defView instanceof StringDefView) {
				stringDef = (StringDefinition) ObjectReference
						.newObjectReference(
								((StringDefView) defView).getObjectID())
						.getObject();
			}
		} catch (Exception e) {
			log.error("Get IBA Definition [" + ibaKey + "] Error: "
					+ ExceptionUtils.getStackTrace(e));
		}
		return stringDef;
	}
	private static int appendStringIBACondition(QuerySpec querySpec,
			String ibaPath, String operator, String ibaVal) throws WTException {
		StringDefinition ibaDef = getStringDefByPath(ibaPath);
		// log.debug("++++++++++++++++++++++++ibaPath:"+ibaPath);
		// log.debug("++++++++++++++++++++++++ibaDef:"+ibaDef);
		int ibaIndex = querySpec.appendClassList(StringValue.class, false);
		querySpec.appendAnd();
		SearchCondition searchConditionHolder = new SearchCondition(querySpec
				.getPrimaryClass(), "thePersistInfo.theObjectIdentifier.id",
				StringValue.class, StringValue.IBAHOLDER_REFERENCE + ".key.id");
		querySpec.appendWhere(searchConditionHolder, new int[] { 0, ibaIndex });
		querySpec.appendAnd();
		// log.debug("+++++++++++++++PersistenceHelper.getObjectIdentifier(ibaDef):"+PersistenceHelper.getObjectIdentifier(ibaDef));
		SearchCondition searchConditionDef = new SearchCondition(
				StringValue.class, "definitionReference.key.id",
				SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(
						ibaDef).getId());
		querySpec.appendWhere(searchConditionDef, new int[] { ibaIndex });
		querySpec.appendAnd();
		SearchCondition searchConditionVal = new SearchCondition(
				StringValue.class, StringValue.VALUE, operator, ibaVal);
		querySpec.appendWhere(searchConditionVal, new int[] { ibaIndex });
		return ibaIndex;
	}
	private static int appendTimeStampIBACondition(QuerySpec querySpec,
			String ibaPath, String operator, Timestamp ibaVal)
			throws WTException {
		TimestampDefinition ibaDef = getTimeStampDefByPath(ibaPath);
		// log.debug("++++++++++++++++++++++++ibaPath:"+ibaPath);
		// log.debug("++++++++++++++++++++++++ibaDef:"+ibaDef);
		int ibaIndex = querySpec.appendClassList(TimestampValue.class, false);
		querySpec.appendAnd();
		SearchCondition searchConditionHolder = new SearchCondition(querySpec
				.getPrimaryClass(), "thePersistInfo.theObjectIdentifier.id",
				TimestampValue.class, TimestampValue.IBAHOLDER_REFERENCE
						+ ".key.id");
		querySpec.appendWhere(searchConditionHolder, new int[] { 0, ibaIndex });
		querySpec.appendAnd();
		// log.debug("+++++++++++++++PersistenceHelper.getObjectIdentifier(ibaDef):"+PersistenceHelper.getObjectIdentifier(ibaDef));
		SearchCondition searchConditionDef = new SearchCondition(
				TimestampValue.class, "definitionReference.key.id",
				SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(
						ibaDef).getId());
		querySpec.appendWhere(searchConditionDef, new int[] { ibaIndex });
		querySpec.appendAnd();
		SearchCondition searchConditionVal = new SearchCondition(
				TimestampValue.class, TimestampValue.VALUE, operator, ibaVal);
		querySpec.appendWhere(searchConditionVal, new int[] { ibaIndex });
		return ibaIndex;
	}
	public static TimestampDefinition getTimeStampDefByPath(String ibaKey) {
		if (!RemoteMethodServer.ServerFlag) {
			try {
				return (TimestampDefinition) RemoteMethodServer.getDefault()
						.invoke("getTimeStampDefByPath",
								PartUtil.class.getName(), null,
								new Class[] { Timestamp.class },
								new Object[] { ibaKey });
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
		TimestampDefinition tsDef = null;
		try {
			AttributeDefDefaultView defView = IBADefinitionHelper.service
					.getAttributeDefDefaultViewByPath(ibaKey);
			// log.debug("DEFView=" + defView);
			if (defView instanceof TimestampDefView) {
				tsDef = (TimestampDefinition) ObjectReference
						.newObjectReference(
								((TimestampDefView) defView).getObjectID())
						.getObject();
			}
		} catch (Exception e) {
			log.error("Get IBA Definition [" + ibaKey + "] Error: "
					+ ExceptionUtils.getStackTrace(e));
		}
		return tsDef;
	}
}
