/**
 * ext.yongc.plm.util.MBAUtil.java
 * @Author yge
 * 2017年7月5日上午10:52:18
 * Comment 
 */
package ext.yongc.plm.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.WTObject;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionHelper;
import wt.util.WTException;

import java.text.MessageFormat;
import com.ptc.core.foundation.type.server.impl.TypeHelper;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.ConstraintDefinitionReadView;
import com.ptc.core.lwc.common.view.ConstraintDefinitionReadView.RuleDataObject;
import com.ptc.core.lwc.common.view.EnumerationDefinitionReadView;
import com.ptc.core.lwc.common.view.EnumerationEntryReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.lwc.server.StandardTypeDefinitionService;
import com.ptc.core.lwc.server.TypeDefinitionServiceHelper;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.DiscreteSet;
import com.ptc.core.meta.common.FloatingPoint;
import com.ptc.core.meta.common.IllegalFormatException;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.UpdateOperationIdentifier;
import com.ptc.core.meta.server.TypeIdentifierUtility;

public class MBAUtil implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 150882489242046185L;

    private static final Logger LOGGER = LogR.getLogger(MBAUtil.class.getName());



    /**
     * @param p
     * @param dataMap
     * @return
     * @throws WTException
     */
    public static Persistable setValue(Persistable p, Map<String, Object> dataMap) throws WTException {
        Locale loc = null;
        loc = SessionHelper.getLocale();
        return setValue(p, loc, dataMap);
    }

    /**
     * @param p
     * @param loc
     * @param dataMap
     * @return
     * @throws WTException
     */
    public static Persistable setValue(Persistable p, Locale loc, Map<String, Object> dataMap) throws WTException {
    	PersistableAdapter lwcObject = new PersistableAdapter(p, null, loc, new UpdateOperationIdentifier());
        Iterator<String> keyIt = dataMap.keySet().iterator();
        String key = null;
        lwcObject.load(dataMap.keySet());
        while (keyIt.hasNext()) {
            key = keyIt.next();
            lwcObject.set(key, dataMap.get(key));
        }
        lwcObject.apply();
        Persistable newP = PersistenceHelper.manager.modify(p);
        return newP;
    }


    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getValue(Persistable p, String key)  {
        Locale loc = null;
        try {
            loc = SessionHelper.getLocale();
        } catch (WTException e) {
            LOGGER.debug("Get IBA Value Fail" + e.getMessage());
            return null;
        }
        return getValue(p, loc, key);
    }
    
    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     * @throws InvocationTargetException 
     * @throws RemoteException 
     */
    public static Object getMBAValue(Persistable p, String key) throws WTException  {
        Locale loc = null;
        try {
            loc = SessionHelper.getLocale();
        } catch (WTException e) {
            LOGGER.debug("Get IBA Value Fail" + e.getMessage());
            throw new WTException(e);
        }
        return getMBAValue(p, loc, key);
    }
    
    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getMBAValue(Persistable targetObj, Locale locale, String ibaName) throws WTException {
        Object ibaValue = null;     
        if (!RemoteMethodServer.ServerFlag) {
            try {
                return (Object) RemoteMethodServer.getDefault().invoke("getMBAValue", MBAUtil.class.getName(), null, new Class[] { Persistable.class, Locale.class, String.class },new Object[] { targetObj, locale, ibaName });
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new WTException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new WTException(e);
            }
        } else {
            try {
            	PersistableAdapter    obj = new PersistableAdapter(targetObj, null, locale, null);
                obj.load(ibaName);
                ibaValue = obj.get(ibaName);
            } catch (WTException e) {
                e.printStackTrace();
                throw new WTException(e);
            }
       }
      return ibaValue;
    }

    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getValue(Persistable targetObj, Locale locale, String ibaName) {
        Object ibaValue = null;
        if (!RemoteMethodServer.ServerFlag) {
            try {
                return (Object) RemoteMethodServer.getDefault().invoke("getValue", MBAUtil.class.getName(), null,
                        new Class[] { Persistable.class, Locale.class, String.class },
                        new Object[] { targetObj, locale, ibaName });
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PersistableAdapter obj = new PersistableAdapter(targetObj, null, locale, null);
                obj.load(ibaName);
                ibaValue = obj.get(ibaName);
            } catch (WTException e) {
                String exceptionMsg = e.getMessage();
                String errorMsg = MessageFormat
                        .format("Failed to get attribute-[{0}] value from object-[{1}] (Locale=[{2}]). Please check the exception message: {3}.",
                                ibaName, ((targetObj instanceof WTObject) ? ((WTObject) targetObj).getDisplayIdentity()
                                        : targetObj), locale, exceptionMsg);
                if (exceptionMsg != null && exceptionMsg.toUpperCase().contains("ATTRIBUTE")
                        && exceptionMsg.toUpperCase().contains("NOT LOADED!")) {
                    LOGGER.error(errorMsg);
                } else {
                    LOGGER.error(errorMsg, e);
                }
            }
        }
        return ibaValue;
    }

    /**
     * set Object Value
     * 
     * @param per
     *            : Persistable
     * @param dataMap
     *            : Map<String, Object>
     * @throws WTException
     *             : windchill exception
     */
    public static void setObjectValue(Persistable per, Map<String, Object> dataMap) throws WTException {
        PersistableAdapter obj = new PersistableAdapter(per, null, SessionHelper.getLocale(), new UpdateOperationIdentifier());
        Iterator<String> keyIt = dataMap.keySet().iterator();
        String key = null;
        obj.load(dataMap.keySet());
        while (keyIt.hasNext()) {
            key = keyIt.next();
            obj.set(key, dataMap.get(key));
        }
        obj.apply();
        PersistenceServerHelper.manager.update(per);
    }
    /**
     * set Object Value before persisting to DB
     * @param per
     * @param key
     * @param value
     * @throws WTException
     */
    public static void setValueBeforeStore(Persistable per, String key, Object value) throws WTException {
        PersistableAdapter obj = new PersistableAdapter(per, null, SessionHelper.getLocale(), new UpdateOperationIdentifier());
        obj.load(key);
        obj.set(key,value);
        obj.apply();
    }
    
    
    /**
     * set Object Value
     * @param per
     * @param key
     * @param value
     * @throws WTException
     */
    public static void setObjectValue(Persistable per, String key, Object value) throws WTException {
    	PersistableAdapter obj = new PersistableAdapter(per, null, SessionHelper.getLocale(), new UpdateOperationIdentifier());
    	obj.load(key);
    	obj.set(key,value);
    	obj.apply();
        PersistenceServerHelper.manager.update(per);
    }

    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getObjectValue(Persistable persistable, String key){
        Locale locale=null;
        try {
            locale = SessionHelper.getLocale();
        } catch (WTException e) {
            e.printStackTrace();
        }
        return getObjectValue(persistable, locale, key);
    }
    
    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getObjectMBAValue(Persistable persistable, String key) throws WTException{
        Locale locale=null;
        try {
            locale = SessionHelper.getLocale();
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return getObjectMBAValue(persistable, locale, key);
    }
    
    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getObjectMBAValue(Persistable targetObj, Locale locale, String ibaName) throws WTException {
        Object value = null;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Object) RemoteMethodServer.getDefault().invoke("getObjectMBAValue", MBAUtil.class.getName(), null, new Class[] { Persistable.class, Locale.class, String.class },
                        new Object[] { targetObj, locale, ibaName });
            } else {
                PersistableAdapter obj = new PersistableAdapter(targetObj, null, locale, null);
                obj.load(ibaName);
                value = obj.get(ibaName);
            }
        } catch (RemoteException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WTException(e);
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return value;
    }

    /**
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getObjectValue(Persistable targetObj, Locale locale, String ibaName) {
        Object value = null;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Object) RemoteMethodServer.getDefault().invoke("getObjectValue", MBAUtil.class.getName(), null, new Class[] { Persistable.class, Locale.class, String.class },
                        new Object[] { targetObj, locale, ibaName });
            } else {
                PersistableAdapter obj = new PersistableAdapter(targetObj, null, locale, null);
                obj.load(ibaName);
                value = obj.get(ibaName);
            }
        } catch (RemoteException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (WTException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    /**
     * 
     * @description Get all attributes from a persistable.
     * @author jgao
     * @date Jun 9, 2015 
     * @param p
     * @return
     * @throws WTException
     */
	public static Map<String, Object> getAllAttributes(Persistable p) throws WTException {
		Locale loc = null;
		try
		{
			loc = SessionHelper.getLocale();
		} catch (WTException e)
		{
			LOGGER.debug("Get IBA Value Fail" + e.getMessage());
			return null;
		}
		PersistableAdapter lwcObj = new PersistableAdapter(p, null, loc, null);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try
		{

			TypeIdentifier typeIden = TypeIdentifierUtility.getTypeIdentifier(p);
			TypeDefinitionReadView tv = TypeDefinitionServiceHelper.service
			        .getTypeDefView(typeIden);
			Collection<AttributeDefinitionReadView> coll = tv.getAllAttributes();

			Iterator<AttributeDefinitionReadView> iter = coll.iterator();
			Set<String> attrNameSet = new HashSet<String>();
			while (iter.hasNext())
			{
				AttributeDefinitionReadView av = (AttributeDefinitionReadView) iter.next();
				if (!av.isModeled())
					attrNameSet.add(av.getName());
			}
			lwcObj.load(attrNameSet);

			for (String attrName : attrNameSet)
			{
				Object obj = lwcObj.get(attrName);

				if (obj != null)
				{
					LOGGER.debug("value:" + obj.toString());
				}
				dataMap.put(attrName, obj);
			}

		} catch (IllegalFormatException e)
		{

			e.printStackTrace();
			throw new WTException(e);
		}
		return dataMap;
	}

	/**
	 * 
	 * @description
	 * @author jgao
	 * @date Jun 11, 2015
	 * @param p
	 * @param attName
	 * @return
	 * @throws WTException
	 */
	public static HashMap<String, String> getEnumSetByInstance(Persistable p, String attName)
	        throws WTException {
		HashMap<String, String> mapInfo = new HashMap<String, String>();
		try
		{
			TypeIdentifier typeIden = TypeIdentifierUtility.getTypeIdentifier(p);
			TypeDefinitionReadView tv = TypeDefinitionServiceHelper.service
			        .getTypeDefView(typeIden);
			AttributeDefinitionReadView av = tv.getAttributeByName(attName);
			Collection<ConstraintDefinitionReadView> constraints = av.getAllConstraints();
			for (ConstraintDefinitionReadView constraint : constraints)
			{
				String rule = constraint.getRule().getKey().toString();
				if (rule.indexOf("com.ptc.core.lwc.server.LWCEnumerationBasedConstraint") > -1)
				{
					RuleDataObject rdo = constraint.getRuleDataObj();
					if (rdo != null)
					{
						Collection coll = rdo.getEnumDef().getAllEnumerationEntries().values();
						Iterator<EnumerationEntryReadView> it = coll.iterator();
						while (it.hasNext())
						{
							EnumerationEntryReadView view = it.next();
							mapInfo.put(view.getName(), view.getPropertyValueByName("displayName")
							        .getValueAsString());
						}
					}
				}
			}
		} catch (IllegalFormatException e)
		{

			e.printStackTrace();
			throw new WTException(e);
		}
		return mapInfo;
	}

    /**
     * 
     * @description
     * @author jgao
     * @date Jul 2, 2015
     * @param type
     * @param attName
     * @return
     * @throws WTException
     */
    public static Map<String, String> getEnumSetByType(String type, String attName)
            throws WTException {
        Map<String, String> mapInfo = new TreeMap<String, String>();
        try
        {
            TypeIdentifier typeIden = TypeIdentifierUtility.getTypeIdentifier(type);
            TypeDefinitionReadView tv = TypeDefinitionServiceHelper.service
                    .getTypeDefView(typeIden);
            AttributeDefinitionReadView av = tv.getAttributeByName(attName);
            Collection<ConstraintDefinitionReadView> constraints = av.getAllConstraints();
            for (ConstraintDefinitionReadView constraint : constraints)
            {
                String rule = constraint.getRule().getKey().toString();
                if (rule.indexOf("com.ptc.core.lwc.server.LWCEnumerationBasedConstraint") > -1)
                {
                    RuleDataObject rdo = constraint.getRuleDataObj();
                    if (rdo != null)
                    {
                        Collection coll = rdo.getEnumDef().getAllEnumerationEntries().values();
                        Iterator<EnumerationEntryReadView> it = coll.iterator();
                        while (it.hasNext())
                        {
                            EnumerationEntryReadView view = it.next();
                            mapInfo.put(view.getName(), view.getPropertyValueByName("displayName")
                                    .getValueAsString());
                        }
                    }
                }
            }
        } catch (IllegalFormatException e)
        {

            e.printStackTrace();
            throw new WTException(e);
        }
        return mapInfo;
    }
    
    public static Set<String> getLegalSetByType(String type, String attName)
            throws WTException {
        Set<String> valueSet = new TreeSet<String>();
        LOGGER.debug("[J] Get legal set for type : " + type + ", attrName : " + attName);
        try
        {
            TypeIdentifier typeIden = TypeIdentifierUtility.getTypeIdentifier(type);
            TypeDefinitionReadView tv = TypeDefinitionServiceHelper.service
                    .getTypeDefView(typeIden);
            AttributeDefinitionReadView av = tv.getAttributeByName(attName);
            if(av == null)
            {
                throw new WTException("Type ["+type+"] does not have attribute ["+attName+"]");
            }
            Collection<ConstraintDefinitionReadView> constraints = av.getAllConstraints();
            for (ConstraintDefinitionReadView constraint : constraints)
            {
                String rule = constraint.getRule().getKey().toString();
                if (rule.indexOf("com.ptc.core.meta.container.common.impl.DiscreteSetConstraint") > -1) {
                    RuleDataObject rdo = constraint.getRuleDataObj();
                    if (rdo != null) {
                        DiscreteSet set =  (DiscreteSet) rdo.getRuleData();
                        Object[] elements = set.getElements();
                        for(Object anElement : elements)
                        {
                            valueSet.add((String)anElement);
                        }
                    }
                }
            }
        } catch (IllegalFormatException e)
        {
            LOGGER.error("", e);
            throw new WTException(e);
        }
        if(valueSet.isEmpty())
        {
            LOGGER.error("Value set of attribute: " + attName + "on type: " + type + " is not configured correctly.");
        }
        return valueSet;
    }
	
	/**
	 * 
	 * @description
	 * @author jgao
	 * @date Jun 11, 2015
	 * @param enumAttributeName
	 * @param includeAll
	 * @return
	 * @throws WTException
	 */
	public static Map<String, String> getEnumSetByInternalName(String enumAttributeName,
	        boolean includeAll) throws WTException {
		Map<String, String> result = new TreeMap<String, String>();
		try
		{
			EnumerationDefinitionReadView edr = TypeDefinitionServiceHelper.service
			        .getEnumDefView(enumAttributeName);
			Map<String, EnumerationEntryReadView> views = edr.getAllEnumerationEntries();
			Set<String> keysOfView = views.keySet();
			for (String key : keysOfView)
			{
				EnumerationEntryReadView view = views.get(key);
				String enumKey = view.getName();
				String enumName = view.getPropertyValueByName("displayName").getValue().toString();
				String selectable = view.getPropertyValueByName("selectable").getValue().toString();
				if (includeAll || "true".equals(selectable))
				{
					result.put(enumKey, enumName);
				}
			}
		} catch (Exception e)
		{
			LOGGER.error("enumAttributeName:" + enumAttributeName, e);
			throw new WTException(e, "Get Enumeration [" + enumAttributeName + "] failed.");
		}
		return result;
	}
	/**
	 * @author PengTao
	 * @param attrValue
	 * @return
	 */
	private static String parseSingleValue(Object attrValue) {
        String value = "";
        if (attrValue == null) {
            return "";
        }
        if (attrValue instanceof Timestamp) {
            value = WCUtil.getFormattedDateString((Timestamp) attrValue);
        }
        if (attrValue instanceof Long) {
            Long tmpQty = (Long) attrValue;
            value = String.valueOf(tmpQty.intValue());
        } else if (attrValue instanceof Float) {
            Float tmpQty = (Float) attrValue;
            value = String.valueOf(tmpQty.intValue());
        } else if (attrValue instanceof String) {
            value = (String) attrValue;
        } else if (attrValue instanceof FloatingPoint) {
            FloatingPoint tmpQty = (FloatingPoint) attrValue;
            value = String.valueOf(tmpQty.intValue());
        }else{
        	value = attrValue.toString();
        }
        return value;
    }
	/**
	 * parse object value (include object array value) after you invoke getValue() method.
	 * @author PengTao
	 * @param attrValue
	 * @return String
	 */
    public static String parseValue(Object attrValue) {
        String value = "";
        if (attrValue instanceof Object[]) {
            Object[] objects = (Object[]) attrValue;
            for (Object object : objects) {
                String singleValue = parseSingleValue(object);
                if (StringUtils.isNotEmpty(singleValue)) {
                    if (value.length() > 0) {
                        value = value + ",";
                    }
                    value = value + singleValue;
                }
            }
        }else{
            value = parseSingleValue(attrValue);
        }
        return value;
    }
    /**@author huangqibiao
	 * @param type
	 * @param attrName
	 * @return String
	 */
    
	public static String queryTypeMBADBColName(String type, String attrName) throws WTException {
		 String attributeName = null;
//	        TypeDefinitionReadView tdrv = TypeDefinitionServiceHelper.service.getTypeDefView(type);
		 TypeDefinitionReadView tdrv = StandardTypeDefinitionService.newStandardTypeDefinitionService().getTypeDefView(type);
	        if (tdrv != null) {
	            AttributeDefinitionReadView attView = tdrv.getAttributeByName(attrName);
	            if (attView != null) {
	                AttributeTypeIdentifier attIden = attView.getAttributeTypeIdentifier();
	                attributeName = attIden.getAttributeName();
	            }
	        }
	        return attributeName;
	}

}


