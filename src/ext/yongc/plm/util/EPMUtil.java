/**
 * ext.yongc.plm.util.EPMUtil.java
 * @Author yge
 * 2017年11月13日下午3:08:15
 * Comment 
 */
package ext.yongc.plm.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.ptc.core.logging.Log;
import com.ptc.core.logging.LogFactory;

import wt.epm.EPMDocument;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.iba.definition.StringDefinition;
import wt.iba.definition.TimestampDefinition;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.litedefinition.StringDefView;
import wt.iba.definition.litedefinition.TimestampDefView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.StringValue;
import wt.iba.value.TimestampValue;
import wt.method.RemoteMethodServer;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;
import wt.vc.config.LatestConfigSpec;

public class EPMUtil {
	private static Log log = LogFactory.getLog(EPMUtil.class);

	public static StringDefinition getStringDefByPath(String ibaKey) {
		if (!RemoteMethodServer.ServerFlag) {
			try {
				return (StringDefinition) RemoteMethodServer.getDefault()
						.invoke("getStringDefByPath", EPMUtil.class.getName(),
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
	public static EPMDocument getEPMDocmentByNumber(String num) throws WTException {
        EPMDocument document = null;
        QuerySpec qs = new QuerySpec(EPMDocument.class);
        SearchCondition sc = new SearchCondition(EPMDocument.class, EPMDocument.NUMBER, SearchCondition.EQUAL, num);
        qs.appendWhere(sc);
        qs.appendAnd();
        qs.appendWhere(new SearchCondition(new KeywordExpression("A0.latestiterationinfo"), SearchCondition.EQUAL, new KeywordExpression("1")));
        qs.appendOrderBy(EPMDocument.class, "thePersistInfo.createStamp", true);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        if (qr.hasMoreElements()) {
            document = (EPMDocument) qr.nextElement();
            document = (EPMDocument) wt.vc.VersionControlHelper.service.getLatestIteration(document, false);
        }
        return document;
    }
	   public static EPMDocument getEPMDocumentByCADName(String cadname) throws Exception {
	        EPMDocument doc = null;
	        QuerySpec qs = new QuerySpec(EPMDocument.class);
	        SearchCondition sc = new SearchCondition(EPMDocument.class, EPMDocument.CADNAME, SearchCondition.EQUAL, cadname);
	        qs.appendWhere(sc);
	        qs.appendAnd();
	        qs.appendWhere(new SearchCondition(new KeywordExpression("A0.latestiterationinfo"), SearchCondition.EQUAL, new KeywordExpression("1")));
	        qs.appendOrderBy(EPMDocument.class, "thePersistInfo.createStamp", true);
	        QueryResult qr = PersistenceHelper.manager.find(qs);
	        if (qr.hasMoreElements()) {
	            doc = (EPMDocument) qr.nextElement();
	            doc =(EPMDocument) wt.vc.VersionControlHelper.service.getLatestIteration(doc, false);
	        }
	        return doc;
	    }
	   
	   /**
		 * get the latest iterated epmdocument by the softtype the method will be
		 * run at server side
		 * 
		 * @param softType
		 * @param ibaConditionMap
		 * @param isLatest
		 */
		public static Vector getEPMDocumentsByTypeAndIBA(String softType,
				Map ibaConditionMap, boolean isLatest) {
			if (!RemoteMethodServer.ServerFlag) {
				try {
					return (Vector) RemoteMethodServer.getDefault().invoke(
							"getEPMDocumentsByTypeAndIBA", EPMUtil.class.getName(),
							null,
							new Class[] { String.class, Map.class, Boolean.class },
							new Object[] { softType, ibaConditionMap, isLatest });
				} catch (Exception e) {
					log.error(ExceptionUtils.getStackTrace(e));
				}
			}
			try {
				Vector vec = new Vector();
				QuerySpec querySpec = new QuerySpec(EPMDocument.class);
				TypeDefinitionReference softTypeDefRef = TypedUtilityServiceHelper.service
						.getTypeDefinitionReference(softType);
				SearchCondition typeCondition = new SearchCondition(
						EPMDocument.class, EPMDocument.TYPE_DEFINITION_REFERENCE
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
				querySpec.appendOrderBy(EPMDocument.class,
						EPMDocument.MODIFY_TIMESTAMP, true);
				log.debug("querySpec=" + querySpec);
				QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
				System.out.println("**** querySpec = "+querySpec);
				while (queryResult.hasMoreElements()) {
					Object obj = queryResult.nextElement();
					if (obj instanceof Persistable) {
						EPMDocument doc = (EPMDocument) obj;
						if (!doc.getLocation().startsWith("/Default"))
							continue;
						vec.add(obj);
					} else if (obj instanceof Persistable[]) {
						EPMDocument doc = (EPMDocument) (((Persistable[]) obj)[0]);
						if (!doc.getLocation().startsWith("/Default"))
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
		/**
		 * 
		 * @param querySpec
		 * @param ibaPath
		 * @param operator
		 * @param ibaVal
		 * @return
		 * @throws WTException
		 */
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
									EPMUtil.class.getName(), null,
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
