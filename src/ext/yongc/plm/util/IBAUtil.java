/**
 * ext.yongc.plm.util.IBAUtil.java
 * @Author yge
 * 2017年7月9日下午11:33:20
 * Comment 
 */
package ext.yongc.plm.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import wt.csm.businessentity.BusinessEntity;
import wt.csm.navigation.litenavigation.ClassificationStructDefaultView;
import wt.csm.navigation.service.ClassificationHelper;
import wt.csm.navigation.service.ClassificationService;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.Folder;
import wt.iba.constraint.AttributeConstraint;
import wt.iba.constraint.ConstraintGroup;
import wt.iba.constraint.IBAConstraintException;
import wt.iba.definition.BooleanDefinition;
import wt.iba.definition.DefinitionLoader;
import wt.iba.definition.litedefinition.AbstractAttributeDefinizerView;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.litedefinition.AttributeDefNodeView;
import wt.iba.definition.litedefinition.BooleanDefView;
import wt.iba.definition.litedefinition.FloatDefView;
import wt.iba.definition.litedefinition.IntegerDefView;
import wt.iba.definition.litedefinition.RatioDefView;
import wt.iba.definition.litedefinition.ReferenceDefView;
import wt.iba.definition.litedefinition.StringDefView;
import wt.iba.definition.litedefinition.TimestampDefView;
import wt.iba.definition.litedefinition.URLDefView;
import wt.iba.definition.litedefinition.UnitDefView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.definition.service.StandardIBADefinitionService;
import wt.iba.value.AbstractValue;
import wt.iba.value.AttributeContainer;
import wt.iba.value.BooleanValue;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAContainerException;
import wt.iba.value.IBAHolder;
import wt.iba.value.IBAReferenceable;
import wt.iba.value.IBAValueException;
import wt.iba.value.IBAValueUtility;
import wt.iba.value.StringValue;
import wt.iba.value.litevalue.AbstractContextualValueDefaultView;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.litevalue.BooleanValueDefaultView;
import wt.iba.value.litevalue.DefaultLiteIBAReferenceable;
import wt.iba.value.litevalue.FloatValueDefaultView;
import wt.iba.value.litevalue.IntegerValueDefaultView;
import wt.iba.value.litevalue.RatioValueDefaultView;
import wt.iba.value.litevalue.ReferenceValueDefaultView;
import wt.iba.value.litevalue.StringValueDefaultView;
import wt.iba.value.litevalue.TimestampValueDefaultView;
import wt.iba.value.litevalue.URLValueDefaultView;
import wt.iba.value.litevalue.UnitValueDefaultView;
import wt.iba.value.service.IBAValueDBService;
import wt.iba.value.service.IBAValueHelper;
import wt.iba.value.service.LoadValue;
import wt.iba.value.service.StandardIBAValueService;
import wt.lite.AbstractLiteObject;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.DateHelper;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.units.service.QuantityOfMeasureDefaultView;
import wt.util.NumericToolkitUtil;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;
import wt.vc.VersionControlHelper;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

import com.ptc.core.lwc.server.LWCNormalizedObject;
import com.ptc.core.meta.common.UpdateOperationIdentifier;

public class IBAUtil implements RemoteAccess, Serializable{
	private static final long serialVersionUID = 1L;


	private static Logger logger = LogR.getLogger(IBAUtil.class.getName());
    

    Map<String,Object[]> ibaContainer;
    Map<String,Object[]> ibaOrigContainer;
    private static final String UNITS = "SI";

    // Can not be called directly by the end user
    public IBAUtil() {
        ibaContainer = new Hashtable<String,Object[]>();
    }

    public String getBooleanValue(Persistable ibaHolder, String ibaName) throws WTException{
        /*
         * select a0.value2 from StringValue A0, StringDefinition A1 where
         * a0.ida3a6 = a1.ida2a2 and a0.ida3a4 = 'iba holder oid" and a1.name =
         * 'Definition'
         */

        if (ibaHolder == null){
            return "false";
        }
        if (ibaName == null || ibaName.length() == 0){
            return "fasle";
        }
        return Boolean.toString(getBooleanIBAValue(ibaHolder, ibaName));

    }

    @SuppressWarnings("deprecation")
    public boolean getBooleanIBAValue(Persistable ibaHolder,String ibaName) throws WTException{
        boolean result = false;
        try {
            QuerySpec adQs = new QuerySpec();
            adQs.setAdvancedQueryEnabled(true);

            int ibaStringValueIndex = adQs.appendClassList(BooleanValue.class,false);
            int ibaStringDefinitionIndex = adQs.appendClassList(BooleanDefinition.class, false);

            SearchCondition scStringDefinitionName = new SearchCondition(BooleanDefinition.class, BooleanDefinition.NAME,SearchCondition.EQUAL, ibaName);
            SearchCondition scJoinStringValueStringDefinition = new SearchCondition(BooleanValue.class, "definitionReference.key.id",BooleanDefinition.class, WTAttributeNameIfc.ID_NAME);
            SearchCondition scStringValuePart = new SearchCondition(BooleanValue.class, "theIBAHolderReference.key.id",SearchCondition.EQUAL, ibaHolder.getPersistInfo().getObjectIdentifier().getId());
            adQs.appendWhere(scStringValuePart, ibaStringValueIndex);
            adQs.appendAnd();
            adQs.appendWhere(scJoinStringValueStringDefinition,ibaStringValueIndex, ibaStringDefinitionIndex);
            adQs.appendAnd();
            adQs.appendWhere(scStringDefinitionName, ibaStringDefinitionIndex);
            adQs.appendSelectAttribute(BooleanValue.VALUE, ibaStringValueIndex,false);

            QueryResult qr = PersistenceHelper.manager.find(adQs);

            while (qr != null && qr.hasMoreElements()) {
                Object[] obj = (Object[]) qr.nextElement();
                if (obj[0].toString().equals("1")) {
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new WTException(e);
        }
        return result;
    }
    
    @SuppressWarnings("deprecation")
    public boolean getBooleanIBAValueForBlank(Persistable ibaHolder,String ibaName) throws WTException{
        boolean result = true;
        try {
            QuerySpec adQs = new QuerySpec();
            adQs.setAdvancedQueryEnabled(true);

            int ibaStringValueIndex = adQs.appendClassList(BooleanValue.class,false);
            int ibaStringDefinitionIndex = adQs.appendClassList(BooleanDefinition.class, false);

            SearchCondition scStringDefinitionName = new SearchCondition(BooleanDefinition.class, BooleanDefinition.NAME,SearchCondition.EQUAL, ibaName);
            SearchCondition scJoinStringValueStringDefinition = new SearchCondition(BooleanValue.class, "definitionReference.key.id",BooleanDefinition.class, WTAttributeNameIfc.ID_NAME);
            SearchCondition scStringValuePart = new SearchCondition(BooleanValue.class, "theIBAHolderReference.key.id",SearchCondition.EQUAL, ibaHolder.getPersistInfo().getObjectIdentifier().getId());
            adQs.appendWhere(scStringValuePart, ibaStringValueIndex);
            adQs.appendAnd();
            adQs.appendWhere(scJoinStringValueStringDefinition,ibaStringValueIndex, ibaStringDefinitionIndex);
            adQs.appendAnd();
            adQs.appendWhere(scStringDefinitionName, ibaStringDefinitionIndex);
            adQs.appendSelectAttribute(BooleanValue.VALUE, ibaStringValueIndex,false);

            QueryResult qr = PersistenceHelper.manager.find(adQs);

            while (qr != null && qr.hasMoreElements()) {
                Object[] obj = (Object[]) qr.nextElement();
                if (obj[0].toString().equals("0")) {
                    result = false;
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return result;
    }

    /**
     * IBAUtility PS. The only constrator can be called by the end user
     * 
     * @param ibaHolder
     *            : IBAHolder
     * @exception WTException
     */
    public IBAUtil(IBAHolder ibaHolder) throws WTException {
        super();
        try {
            initializeIBAValue(ibaHolder);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }


    public String toString(){

        StringBuffer tempString = new StringBuffer();
        Iterator<String> iter = ibaContainer.keySet().iterator();
        try {
            while (iter.hasNext()) {
                String theKey =  iter.next();
                AbstractValueView theValue = (AbstractValueView) ((Object[]) ibaContainer.get(theKey))[1];
                tempString.append(theKey+ " - "+ IBAValueUtility.getLocalizedIBAValueDisplayString(theValue, SessionHelper.manager.getLocale()));
                tempString.append('\n');
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(),e);
        }
        return (tempString.toString());
    }

    /**
     * Get atribute definitions
     * 
     * @return Enumeration
     */

    public Iterator<String> getAttributeDefinitions() {
        return ibaContainer.keySet().iterator();
    }

    /**
     * Remove all attributes
     * 
     * @exception WTException
     *                , WTPropertyVetoException
     */
    public void removeAllAttributes() throws WTException,WTPropertyVetoException {
        ibaContainer.clear();
    }

    /**
     * Remove attribute by name
     * 
     * @param name
     *            : iba name
     * @exception WTException
     *                , WTPropertyVetoException
     */
    public void removeAttribute(String name) throws WTException,WTPropertyVetoException {
        ibaContainer.remove(name);
    }

    /**
     * Get IBA value (single)
     * 
     * @param name
     *            : iba name
     * @return String
     */
    public String getIBAValue(String name) throws WTException{
        String value = null;
        try {
            if (ibaContainer.get(name) != null) {
                AbstractValueView theValue = (AbstractValueView) ((Object[]) ibaContainer.get(name))[1];
                value = (IBAValueUtility.getLocalizedIBAValueDisplayString(theValue, SessionHelper.manager.getLocale()));
            }
        } catch (WTException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return value == null ? "" : value;
    }


    /**
     * Get IBA values (multi)
     * 
     * @param name
     *            : iba name
     * @return List<String>
     */
    public List<String> getIBAValues(String name) throws WTException{
        List<String> vList = new ArrayList<String>();
        try {
            if (ibaContainer.get(name) != null) {
                Object[] objs = (Object[]) ibaContainer.get(name);
                for (int i = 1; i < objs.length; i++) {
                    AbstractValueView theValue = (AbstractValueView) objs[i];
                    vList.add(IBAValueUtility.getLocalizedIBAValueDisplayString(theValue,SessionHelper.manager.getLocale()));
                    //vector.addElement(IBAValueUtility.getLocalizedIBAValueDisplayString(theValue,SessionHelper.manager.getLocale()));
                }
            }
        } catch (WTException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return vList;
    }
    
    /**
     * Get IBA values (multi)
     * 
     * @param name
     *            : iba name
     * @return List<Object>
     */
    public List<Object> getMultiIBAValues(String name) throws WTException{
        List<Object> vList = new ArrayList<Object>();
        try {
            if (ibaContainer.get(name) != null) {

                Object[] objs = (Object[]) ibaContainer.get(name);
                
                AttributeDefDefaultView defView = getAttributeDefinition(name);
                if (defView instanceof BooleanDefView) {
                    for (int i = 1; i < objs.length; i++) {
                        BooleanValueDefaultView theValue = (BooleanValueDefaultView) ((Object[]) ibaContainer.get(name))[i];
                        vList.add(theValue.isValue());
                    }
                } else if (defView instanceof FloatDefView) {
                    for (int i = 1; i < objs.length; i++) {
                        FloatValueDefaultView theValue = (FloatValueDefaultView) ((Object[]) ibaContainer.get(name))[i];
                        double val = theValue.getValue();
                        DecimalFormat df = new DecimalFormat("0.######");
                        vList.add(df.format(val));
                    }
                } else {
                    for (int i = 1; i < objs.length; i++) {
                        AbstractValueView theValue = (AbstractValueView) objs[i];
                        vList.add(IBAValueUtility.getLocalizedIBAValueDisplayString(theValue,SessionHelper.manager.getLocale()));
                    }
                }
            }
        } catch (WTException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return vList;
    }

    /**
     * Return multiple IBA values & dependency relationship
     * 
     * @param name
     *            : iba name
     * @return List<String[]>
     */
    public List<String[]> getIBAValuesWithDependency(String name) throws WTException{
        List<String[]> vList = new ArrayList<String[]>();
        try {
            if (ibaContainer.get(name) != null) {
                Object[] objs = (Object[]) ibaContainer.get(name);
                for (int i = 1; i < objs.length; i++) {
                    AbstractValueView theValue = (AbstractValueView) objs[i];
                    String[] temp = new String[3];
                    temp[0] = IBAValueUtility.getLocalizedIBAValueDisplayString(theValue,SessionHelper.manager.getLocale());
                    if ((theValue instanceof AbstractContextualValueDefaultView)
                            && ((AbstractContextualValueDefaultView) theValue).getReferenceValueDefaultView() != null) {
                        temp[1] = ((AbstractContextualValueDefaultView) theValue).getReferenceValueDefaultView().getReferenceDefinition().getName();
                        temp[2] = ((AbstractContextualValueDefaultView) theValue).getReferenceValueDefaultView().getLocalizedDisplayString();
                    } else {
                        temp[1] = null;
                        temp[2] = null;
                    }
                    vList.add(temp);
                }
            }
        } catch (WTException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return vList;
    }


    /**
     * @throws RemoteException
     * @annotator jgao use the following method replace the old getIBAValue It
     *            is more efficient to query an iba value directly than get
     *            attribute container first then get iba one by one.
     */
    public String getIBAValue(IBAHolder holder, String ibaName) throws WTException {
        AttributeDefDefaultView addv = null;
        try {
            addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(ibaName);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        if (addv == null) {
            throw new WTException("undefined iba name :" + ibaName);
        }
        long ibaDefId = addv.getObjectID().getId();

        QuerySpec qs = new QuerySpec(StringValue.class);
        qs.appendWhere(new SearchCondition(StringValue.class,
                AbstractValue.IBAHOLDER_REFERENCE + "." + ObjectReference.KEY+ "." + ObjectIdentifier.ID, SearchCondition.EQUAL,
                PersistenceHelper.getObjectIdentifier((Persistable) holder).getId()), new int[] { 0 });
        qs.appendAnd();
        qs.appendWhere(new SearchCondition(StringValue.class,StringValue.DEFINITION_REFERENCE + "." + ObjectReference.KEY
                        + "." + ObjectIdentifier.ID, SearchCondition.EQUAL,ibaDefId), new int[] { 0 });

        QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
        StringValue sv = null;
        if (qr.hasMoreElements()){
            sv = (StringValue) qr.nextElement();
        }

        return sv == null ? null : sv.getValue();
    }

    /**
     * Get IBA value with business entity
     * 
     * @param name
     *            : iba name
     * @return List<String[]>
     */
    public List<Object[]> getIBAValuesWithBusinessEntity(String name) throws WTException{
        
        List<Object[]> vList = new ArrayList<Object[]>();
        if (ibaContainer.get(name) != null) {
            Object[] objs = (Object[]) ibaContainer.get(name);
            for (int i = 1; i < objs.length; i++) {
                AbstractValueView theValue = (AbstractValueView) objs[i];
                Object[] temp = new Object[2];
                temp[0] = IBAValueUtility.getLocalizedIBAValueDisplayString(theValue,SessionHelper.manager.getLocale());
                if ((theValue instanceof AbstractContextualValueDefaultView)
                        && ((AbstractContextualValueDefaultView) theValue).getReferenceValueDefaultView() != null) {
                    ReferenceValueDefaultView referencevaluedefaultview = ((AbstractContextualValueDefaultView) theValue).getReferenceValueDefaultView();
                    ObjectIdentifier objectidentifier = ((wt.iba.value.litevalue.DefaultLiteIBAReferenceable) referencevaluedefaultview.getLiteIBAReferenceable()).getObjectID();
                    Persistable persistable = ObjectReference.newObjectReference(objectidentifier).getObject();
                    temp[1] = (BusinessEntity) persistable;
                } else {
                    temp[1] = null;
                }
                vList.add(temp);
            }
        }

        return vList;
    }

    /**
     * Get IBA BusinessEntity
     * 
     * @param name
     *            : iba name
     * @return BusinessEntity
     */
    public BusinessEntity getIBABusinessEntity(String name) throws WTException{
        BusinessEntity value = null;

        if (ibaContainer.get(name) != null) {
            AbstractValueView theValue = (AbstractValueView) ((Object[]) ibaContainer.get(name))[1];
            ReferenceValueDefaultView referencevaluedefaultview = (ReferenceValueDefaultView) theValue;
            ObjectIdentifier objectidentifier = ((wt.iba.value.litevalue.DefaultLiteIBAReferenceable) referencevaluedefaultview.getLiteIBAReferenceable()).getObjectID();
            Persistable persistable = ObjectReference.newObjectReference(objectidentifier).getObject();
            value = (BusinessEntity) persistable;
        }

        return value;
    }

    /**
     * Get IBA BusinessEntities
     * 
     * @param name
     *            : iba name
     * @return List<Persistable>
     * @throws WTException 
     * @throws  
     */
    public List<Persistable> getIBABusinessEntities(String name) throws WTException {
        List<Persistable> vList = new ArrayList<Persistable>();
        if (ibaContainer.get(name) != null) {
            Object[] objs = (Object[]) ibaContainer.get(name);
            for (int i = 1; i < objs.length; i++) {
                AbstractValueView theValue = (AbstractValueView) objs[i];
                ReferenceValueDefaultView referencevaluedefaultview = (ReferenceValueDefaultView) theValue;
                ObjectIdentifier objectidentifier = ((wt.iba.value.litevalue.DefaultLiteIBAReferenceable) referencevaluedefaultview.getLiteIBAReferenceable()).getObjectID();
                Persistable persistable = ObjectReference.newObjectReference(objectidentifier).getObject();
                vList.add(persistable);
            }
        }

        return vList;
    }

    /**
     * Get abstract value view
     * 
     * @param theDef
     * @param value
     * @return AbstractValueView
     * @exception WTException
     *                , WTPropertyVetoException
     */
    private AbstractValueView getAbstractValueView(AttributeDefDefaultView theDef, String value) throws WTException {
        
        if (value == null || value.trim().equals("null")|| value.trim().equals("")) {
            throw new WTException("Error : IBA Name = " + theDef.getName()+ ", value is null.Add IBA value failed!!");
        }
        String name = theDef.getName();
        String value2 = null;
        AbstractValueView ibaValue = null;

        if (theDef instanceof UnitDefView) {
            if(value.indexOf(getDisplayUnits((UnitDefView) theDef, UNITS)) < 0){
                value = value + " " + getDisplayUnits((UnitDefView) theDef, UNITS);
            }
        } else if (theDef instanceof ReferenceDefView) {
            value2 = value;
            value = ((ReferenceDefView) theDef).getReferencedClassname();
        }

        ibaValue = internalCreateValue(theDef, value, value2);
        if (ibaValue == null) {
            throw new WTException("Error : IBA Name = " + theDef.getName()+ ", value is null.Add IBA value failed!!");
        }

        if (ibaValue instanceof ReferenceValueDefaultView) {
            ibaValue = getOriginalReferenceValue(name, ibaValue);
        }
        try {
            ibaValue.setState(AbstractValueView.NEW_STATE);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return ibaValue;
    }

    /**
     * Get abstract value view
     * 
     * @param theDef
     * @param value
     * @return AbstractValueView
     * @exception WTException
     *                , WTPropertyVetoException
     */
    private AbstractValueView getAbstractValueViewForEsiFixData(AttributeDefDefaultView theDef, String value) throws WTException {
        
        if (value == null || value.trim().equals("null")|| value.trim().equals("")) {
            throw new WTException("Error : IBA Name = " + theDef.getName()+ ", value is null.Add IBA value failed!!");
        }
        String name = theDef.getName();
        String value2 = null;
        AbstractValueView ibaValue = null;

        if (theDef instanceof ReferenceDefView) {
            value2 = value;
            value = ((ReferenceDefView) theDef).getReferencedClassname();
        }

        ibaValue = internalCreateValue(theDef, value, value2);
        if (ibaValue == null) {
            throw new WTException("Error : IBA Name = " + theDef.getName()+ ", value is null.Add IBA value failed!!");
        }

        if (ibaValue instanceof ReferenceValueDefaultView) {
            ibaValue = getOriginalReferenceValue(name, ibaValue);
        }
        try {
            ibaValue.setState(AbstractValueView.NEW_STATE);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return ibaValue;
    }
    
    /**
     * Get original referece value
     * 
     * @param name
     * @param ibaValue
     * @return AbstractValueView
     * @exception IBAValueException
     */
    private AbstractValueView getOriginalReferenceValue(String name,AbstractValueView ibaValue) throws IBAValueException {
        Object[] objs = (Object[]) ibaOrigContainer.get(name);
        if (objs != null && (ibaValue instanceof ReferenceValueDefaultView)) {
            int businessvaluepos = 1;
            for (businessvaluepos = 1; businessvaluepos < objs.length; businessvaluepos++) {
                if (((AbstractValueView) objs[businessvaluepos]).compareTo(ibaValue) == 0) {
                    ibaValue = (AbstractValueView) objs[businessvaluepos];
                    break;
                }
            }
        }
        return ibaValue;
    }

    /**
     * Get DefDefaultValue
     * 
     * @param name
     * @return AttributeDefDefaultView
     * @exception WTException
     */
    private AttributeDefDefaultView getDefDefaultView(String name)throws WTException {
        AttributeDefDefaultView theDef = null;
        Object[] obj = (Object[]) ibaContainer.get(name);
        if (obj != null) {
            theDef = (AttributeDefDefaultView) obj[0];
        } else {
            theDef = getAttributeDefinition(name);
        }
        if (theDef == null) {
            throw new WTException("Error : IBA Name = " + name+ " not existed .Add IBA value failed!!");

        }
        return theDef;
    }

    /**
     * Get attribute definition
     * 
     * @param s
     * @param flag
     * @return AttributeDefDefaultView
     */
    public AttributeDefDefaultView getAttributeDefinition(String s,boolean flag) throws WTException{
        
        AttributeDefDefaultView attributedefdefaultview = null;
        try {
            attributedefdefaultview = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(s);
            if (attributedefdefaultview == null) {
                AbstractAttributeDefinizerView abstractattributedefinizerview = DefinitionLoader.getAttributeDefinition(s);
                if (abstractattributedefinizerview != null) {
                    attributedefdefaultview = IBADefinitionHelper.service.getAttributeDefDefaultView((AttributeDefNodeView) abstractattributedefinizerview);
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }


        return attributedefdefaultview;
    }

    /**
     * Get IBA Value view
     * 
     * @param dac
     *            : DefaultAttributeContainer
     * @param ibaName
     *            : iba name
     * @param ibaClass
     * @return AbstractValueView
     * @exception WTException
     */
    public AbstractValueView getIBAValueView(DefaultAttributeContainer dac, String ibaName, String ibaClass) throws WTException {
        AbstractValueView aabstractvalueview[] = null;
        AbstractValueView avv = null;
        aabstractvalueview = dac.getAttributeValues();
        for (int j = 0; j < aabstractvalueview.length; j++) {
            String thisIBAName = aabstractvalueview[j].getDefinition().getName();
            
            String thisIBAClass = (aabstractvalueview[j].getDefinition()).getAttributeDefinitionClassName();
            if (thisIBAName.equals(ibaName) && thisIBAClass.equals(ibaClass)) {
                avv = aabstractvalueview[j];
                break;
            }
        }
        return avv;
    }

    /**
     * Get IBA Value view
     * 
     * @param dac
     *            : DefaultAttributeContainer
     * @param ibaName
     *            : iba name
     * @param ibaClass
     * @return Vector
     * @exception WTException
     */
    public List<AbstractValueView> getIBAValueViews(DefaultAttributeContainer dac,String ibaName, String ibaClass) throws WTException {
        AbstractValueView aabstractvalueview[] = null;
        AbstractValueView avv = null;
        List<AbstractValueView> vResult = new ArrayList<AbstractValueView>();
        aabstractvalueview = dac.getAttributeValues();
        for (int j = 0; j < aabstractvalueview.length; j++) {
            String thisIBAName = aabstractvalueview[j].getDefinition().getName();
            String thisIBAClass = (aabstractvalueview[j].getDefinition()).getAttributeDefinitionClassName();
            if (thisIBAName.equals(ibaName) && thisIBAClass.equals(ibaClass)) {
                avv = aabstractvalueview[j];
                vResult.add(avv);
            }
        }
        return vResult;
    }

    public DefaultAttributeContainer getContainer(IBAHolder ibaHolder) throws WTException {
        try {
            ibaHolder = IBAValueHelper.service.refreshAttributeContainerWithoutConstraints(ibaHolder);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
        return defaultattributecontainer;
    }

    public void setIBAValue(IBAHolder ibaHolder, String def, String newv1) throws WTException {
        IBAHolder ibaholder;
        try {
            ibaholder = IBAValueHelper.service.refreshAttributeContainerWithoutConstraints((IBAHolder) ibaHolder);
            String oldValue = getIBAValue(ibaholder, def);

            Object[] obj = (Object[]) ibaContainer.get(def);
            if (obj == null) {
                addIBAValue(def, newv1, ibaholder);
            } else {
                replaceIBAValue(def, oldValue, newv1);
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }

    public void setIBAValue(String name, String value) throws WTException {
        AttributeDefDefaultView theDef = getDefDefaultView(name);
        Object theValue = getAbstractValueView(theDef, value);

        Object[] temp = new Object[2];
        temp[0] = theDef;
        temp[1] = theValue;
        ibaContainer.put(name, temp);
    }
    
    public void setIBAValueForEsiFixData(String name, String value) throws WTException {
        AttributeDefDefaultView theDef = getDefDefaultView(name);
        Object theValue = getAbstractValueViewForEsiFixData(theDef, value);

        Object[] temp = new Object[2];
        temp[0] = theDef;
        temp[1] = theValue;
        ibaContainer.put(name, temp);
    }
    
    /**
     * Set the attribute with multiple values from the list
     * 
     * @param name
     * @param values
     * @throws WTPropertyVetoException
     * @throws WTException
     */
    public void setIBAValues(String name, List<Object> values)throws WTException {
        AttributeDefDefaultView theDef = getDefDefaultView(name);
        Object[] temp = new Object[values.size() + 1];
        temp[0] = theDef;
        for (int i = 0; i < values.size(); i++) {
            String value = (String) values.get(i);
            Object theValue = getAbstractValueView(theDef, value);
            temp[i + 1] = theValue;
        }
        ibaContainer.put(name, temp);
    }

    /**
     * Add IBA value
     * 
     * @param name
     * @param value
     * @exception WTException
     *                , WTPropertyVetoException
     */
    public void addIBAValue(String name, String value) throws WTException {
        Object[] obj = (Object[]) ibaContainer.get(name);
        AttributeDefDefaultView theDef = getDefDefaultView(name);
        Object theValue = getAbstractValueView(theDef, value);

        Object[] temp;
        if (obj == null) {
            temp = new Object[2];
            temp[0] = theDef;
            temp[1] = theValue;
        } else {
            temp = new Object[obj.length + 1];
            int i;
            for (i = 0; i < obj.length; i++) {
                temp[i] = obj[i];
            }
            temp[i] = theValue;
        }
        ibaContainer.put(name, temp);
    }

    /**
     * Add IBA value
     * 
     * @param name
     *            : iba name
     * @param value
     *            : iba value
     * @param ibaholder
     * @exception WTException
     *                ,WTPropertyVetoException
     */
    public void addIBAValue(String name, String value, IBAHolder ibaholder)throws WTException {
        AttributeContainer attributecontainer = ibaholder.getAttributeContainer();
        AttributeDefDefaultView theDef = null;
        AbstractValueView ibanewValue = null;
        AbstractAttributeDefinizerView abstractattributedefinizerview = null;

        Object[] obj = (Object[]) ibaContainer.get(name);

        if (!"PartType".equals(name)) {
            // Object[] obj = (Object[]) ibaContainer.get(name);
            if (obj != null) {
                theDef = (AttributeDefDefaultView) obj[0];
            }

            if (theDef == null) {
                theDef = getAttributeDefinition(name);
            }
            if (theDef == null) {

                throw new WTException("Trace.. Cannot find AbstractAttributeDefinizerNodeView ! class = <"
                                + name + ">, identifier = <" + value+ "> not found.");
            }
        } else {
            // Object[] obj = (Object[]) ibaContainer.get(name);
            abstractattributedefinizerview = wt.iba.value.service.LoadValue.getCachedAttributeDefinition(name);

        }
        if (!("PartType".equals(name))) {
            ibanewValue = internalCreateValue(theDef, value);
        } else {
            ibanewValue = internalCreateValue(abstractattributedefinizerview,"ClassificationNode", value);
        }

        if (ibanewValue == null) {

            throw new WTException("Trace.. class = <" + name+ ">, identifier = <" + value + "> not found.");
        }
        if (!"PartType".equals(name)) {

            try {
                ibanewValue.setState(AbstractValueView.NEW_STATE);
            } catch (WTPropertyVetoException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }

            Object[] temp;
            if (obj == null) {
                temp = new Object[2];
                temp[0] = theDef;
                temp[1] = ibanewValue;
            } else {
                temp = new Object[obj.length + 1];
                int i;
                for (i = 0; i < obj.length; i++) {
                    temp[i] = obj[i];
                }

                temp[i] = ibanewValue;
            }
            ibaContainer.put(theDef.getDisplayName(), temp);
        } else {
            wt.iba.value.service.LoadValue.createOrUpdateAttributeValueInContainer((DefaultAttributeContainer) attributecontainer,ibanewValue);
        }
    }

    /**
     * Repace IBA value
     * 
     * @param name
     *            : iba name
     * @param oldvalue
     *            : old iba value
     * @param newvalue
     *            : new iba value
     * @exception IBAValueException
     *                , WTPropertyVetoException, WTException
     */
    public void replaceIBAValue(String name, String oldvalue, String newvalue)throws WTException {
        if (oldvalue != null && oldvalue.equals(newvalue)) {
            return;
        }

        AttributeDefDefaultView theDef = null;
        AbstractValueView ibaoldValue = null;
        AbstractValueView ibanewValue = null;
        Object[] obj = (Object[]) ibaContainer.get(name);
        if (obj != null) {
            theDef = (AttributeDefDefaultView) obj[0];
        } else {

            return;
        }
        if (theDef == null) {

            return;
        }

        int oldvaluepos = 1;
        if (oldvalue != null) {

            ibaoldValue = internalCreateValue(theDef, oldvalue);
            if (ibaoldValue == null) {

                return;
            }

            for (oldvaluepos = 1; oldvaluepos < obj.length; oldvaluepos++) {
                if (((AbstractValueView) obj[oldvaluepos]).compareTo(ibaoldValue) == 0) {
                    ibanewValue = (AbstractValueView) obj[oldvaluepos];
                    break;
                }
            }

            if (oldvaluepos == obj.length) {

                return;
            }
        } else {
            ibanewValue = (AbstractValueView) obj[oldvaluepos];
        }

        ibanewValue = internalUpdateValue(ibanewValue, newvalue);
        if (ibanewValue == null) {

            return;
        }

        try {
            ibanewValue.setState(AbstractValueView.CHANGED_STATE);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        obj[oldvaluepos] = ibanewValue;

        ibaContainer.put(theDef.getDisplayName(), obj);
    }

    /**
     * Internal create value
     * 
     * @param theDef
     *            : AbstractAttributeDefinizerView
     * @param theValue
     * @return AbstractValueView
     */
    public AbstractValueView internalCreateValue(AbstractAttributeDefinizerView theDef, String theValue) {
        AbstractValueView theView = null;
        if (theDef instanceof FloatDefView) {
            theView = LoadValue.newFloatValue(theDef, theValue, null);
        } else if (theDef instanceof StringDefView) {
            theView = LoadValue.newStringValue(theDef, theValue);
        } else if (theDef instanceof IntegerDefView) {
            theView = LoadValue.newIntegerValue(theDef, theValue);
        } else if (theDef instanceof RatioDefView) {
            theView = LoadValue.newRatioValue(theDef, theValue, null);
        } else if (theDef instanceof TimestampDefView) {
            theValue = theValue + ".000";
            theView = LoadValue.newTimestampValue(theDef, theValue);
        } else if (theDef instanceof BooleanDefView) {
            theView = LoadValue.newBooleanValue(theDef, theValue);
        } else if (theDef instanceof URLDefView) {
            theView = LoadValue.newURLValue(theDef, theValue, null);
        } else if (theDef instanceof ReferenceDefView) {
            String referencedclassname = ((ReferenceDefView) theDef).getReferencedClassname();

            theView = LoadValue.newReferenceValue(theDef, referencedclassname,theValue);
        } else if (theDef instanceof UnitDefView) {
            theView = LoadValue.newUnitValue(theDef, theValue, null);
        }
        return theView;
    }

    /**
     * Sate iba holder
     * 
     * @param ibaHolder
     * @return
     * @throws WTException
     */
    public IBAHolder saveIBAHolder(IBAHolder ibaHolder)throws WTException {
        if (ibaHolder == null){
            return ibaHolder;
        }
        DefaultAttributeContainer defaultAttributeContainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
        IBAValueDBService ibavaluedbservice = new IBAValueDBService();
        defaultAttributeContainer = (DefaultAttributeContainer) ibavaluedbservice.updateAttributeContainer(ibaHolder,
                        defaultAttributeContainer != null ? defaultAttributeContainer.getConstraintParameter() : null, null, null);
        ibaHolder.setAttributeContainer(defaultAttributeContainer);
        return ibaHolder;
    }

    /**
     * Internal update value
     * 
     * @param theView
     * @param theValue
     * @return AbstractValueView
     * @exception WTPropertyVetoException
     *                , IBAValueException
     */
    public AbstractValueView internalUpdateValue(AbstractValueView theView,String theValue) throws WTException {
        try {
            if (theView instanceof FloatValueDefaultView) {
                double d = Double.valueOf(theValue).doubleValue();
                int i = NumericToolkitUtil.countSigFigs(theValue);
                ((FloatValueDefaultView) theView).setValue(d);
                ((FloatValueDefaultView) theView).setPrecision(i);
    
            } else if (theView instanceof UnitValueDefaultView) {
                double d = Double.valueOf(theValue).doubleValue();
                int i = Integer.valueOf(theValue).intValue();
    
                ((UnitValueDefaultView) theView).setValue(d);
                ((UnitValueDefaultView) theView).setPrecision(i);
            } else if (theView instanceof RatioValueDefaultView) {
                double d = Double.valueOf(theValue).doubleValue();
                // double d1 = Double.valueOf(s1).doubleValue();
                double d1 = 1.0D;
    
                ((RatioValueDefaultView) theView).setValue(d);
                ((RatioValueDefaultView) theView).setDenominator(d1);
            } else if (theView instanceof ReferenceValueDefaultView) {
                AttributeDefDefaultView theDef = theView.getDefinition();
                if (!(theDef instanceof ReferenceDefView)) {
    
                    return null;
                }
    
                String referencedclassname = ((ReferenceDefView) theDef).getReferencedClassname();
                IBAReferenceable ibareferenceable = LoadValue.getIBAReferenceable(referencedclassname, theValue);
                if (ibareferenceable == null) {
    
                    return null;
                }
                DefaultLiteIBAReferenceable defaultliteibareferenceable = new DefaultLiteIBAReferenceable(ibareferenceable);
                ((ReferenceValueDefaultView) theView).setLiteIBAReferenceable(defaultliteibareferenceable);
            } else if (theView instanceof StringValueDefaultView) {
                if (theValue == null) {
                    theValue = "";
                }
                ((StringValueDefaultView) theView).setValue(theValue);
            } else if (theView instanceof TimestampValueDefaultView) {
                Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
                if (theValue != null) {
                    DateHelper datehelper = new DateHelper(theValue, "time");
                    timestamp1 = new Timestamp(datehelper.getDate().getTime());
                }
                ((TimestampValueDefaultView) theView).setValue(timestamp1);
            } else if (theView instanceof BooleanValueDefaultView) {
                boolean flag = false;
                if (theValue != null) {
                    //if (theValue.equals("0") || theValue.equalsIgnoreCase("FALSE")) {
                    if ("0".equals(theValue) || "FALSE".equalsIgnoreCase(theValue)) {    
                        flag = false;
                    } else if ("1".equals(theValue) || "TRUE".equalsIgnoreCase(theValue)) {
                        flag = true;
                    } else {
    
                        return null;
                    }
                }
                ((BooleanValueDefaultView) theView).setValue(flag);
            } else if (theView instanceof URLValueDefaultView) {
                if (theValue == null) {
                    theValue = "";
                }
                ((URLValueDefaultView) theView).setValue(theValue);
            } else if (theView instanceof IntegerValueDefaultView) {
                long l = 0L;
                try {
                    if (theValue != null) {
                        l = Long.valueOf(theValue).longValue();
                    }
                } catch (NumberFormatException numberformatexception) {
    
                    return null;
                }
                ((IntegerValueDefaultView) theView).setValue(l);
            }
                
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }

        return theView;
    }

    /**
     * Set dependency
     * 
     * @param sourceDef
     * @param sourceValue
     * @param businessDef
     * @param businessValue
     * @return AbstractValueView
     * @exception WTPropertyVetoException
     *                , WTException
     */
    private AbstractValueView setDependency(AbstractValueView sourceValue, AttributeDefDefaultView businessDef,AbstractValueView businessValue) throws WTException {

        String businessname = businessDef.getName();

        if (businessValue == null) {
            throw new WTException("This Business Entity:"+ businessname+ " value doesn't exist in System Business Entity. Add IBA dependancy failed!!");
        }
        Object[] businessobj = (Object[]) ibaContainer.get(businessname);
        if (businessobj == null) {
            throw new WTException("Part IBA:" + businessname+ " Value is null. Add IBA dependancy failed!!");
        }

        int businessvaluepos = 1;
        for (businessvaluepos = 1; businessvaluepos < businessobj.length; businessvaluepos++) {
            if (((AbstractValueView) businessobj[businessvaluepos])
                    .compareTo(businessValue) == 0) {
                businessValue = (AbstractValueView) businessobj[businessvaluepos];
                break;
            }
        }
        if (businessvaluepos == businessobj.length) {
            throw new WTException("This Business Entity:"+ businessname+ " value:"
                            + businessValue.getLocalizedDisplayString()+ " is not existed in Part IBA values. Add IBA dependancy failed!!");
        }

        if (!(businessValue instanceof ReferenceValueDefaultView)) {
            throw new WTException("This Business Entity:"+ businessname+ " value:"
                            + businessValue.getLocalizedDisplayString()+ " is not a ReferenceValueDefaultView. Add IBA dependancy failed!!");
        }
        try {
            ((AbstractContextualValueDefaultView) sourceValue).setReferenceValueDefaultView((ReferenceValueDefaultView) businessValue);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }

        return sourceValue;
    }

    /**
     * Set IBA value
     * 
     * @param sourcename
     * @param sourcevalue
     * @param businessname
     * @param businessvalue
     * @exception IBAValueException
     *                , WTPropertyVetoException, WTException
     */
    public void setIBAValue(String sourcename, String sourcevalue,String businessname, String businessvalue) throws WTException {

        AttributeDefDefaultView sourceDef = getDefDefaultView(sourcename);
        AttributeDefDefaultView businessDef = getDefDefaultView(businessname);
        AbstractValueView sourceValue = getAbstractValueView(sourceDef,sourcevalue);
        AbstractValueView businessValue = getAbstractValueView(businessDef,businessvalue);
        sourceValue = setDependency(sourceValue, businessDef,businessValue);
        Object[] temp = new Object[2];
        temp[0] = sourceDef;
        temp[1] = sourceValue;
        ibaContainer.put(sourcename, temp);
    }

    /**
     * Set iba attribute value
     * 
     * @param obj
     *            object
     * @param ibaName
     *            attribute name
     * @param newValue
     *            attribute value
     * @return void
     * @exception WTException
     */
    public void setIBAStringValue(WTObject obj, String ibaName,String newValue) throws WTException {
        String ibaClass = "wt.iba.definition.StringDefinition";


            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                StringValueDefaultView abstractvaluedefaultview = (StringValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    try {
                        abstractvaluedefaultview.setValue(newValue);
                    } catch (WTPropertyVetoException e) {
                        logger.error(e.getLocalizedMessage(),e);
                        throw new WTException(e);
                    }
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    StringValueDefaultView abstractvaluedefaultview1 = new StringValueDefaultView((StringDefView) attributedefdefaultview, newValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                try {
                    ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
                } catch (RemoteException e) {
                    logger.error(e.getLocalizedMessage(),e);
                    throw new WTException(e);
                }
            }

    }

    /**
     * Set IBA string values
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : new value array
     * @exception WTException
     */
    public void setIBAStringValues(WTObject obj, String ibaName,String[] newValue) throws WTException {
        String oneNewValue = "";

        if (obj instanceof IBAHolder) {
            for (int i = 0; i < newValue.length; i++) {
                oneNewValue = newValue[i];
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }

                AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                StringValueDefaultView abstractvaluedefaultview1 = new StringValueDefaultView((StringDefView) attributedefdefaultview,oneNewValue);
                defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);

                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null,null);
                try {
                    ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null,null);
                } catch (RemoteException e) {
                    logger.error(e.getLocalizedMessage(),e);
                    throw new WTException(e);
                }
            }
        }

    }

    /**
     * Set IBA Boolean Value
     * 
     * @param obj
     * @param ibaName: iba name
     * @param new Value: TRUE or FALSE
     * @exception WTException
     */
    public void setIBABooleanValue(WTObject obj, String ibaName,boolean newValue) throws WTException {
        String ibaClass = "wt.iba.definition.BooleanDefinition";

        if (obj instanceof IBAHolder) {
            IBAHolder ibaHolder = (IBAHolder) obj;
            DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
            if (defaultattributecontainer == null) {
                defaultattributecontainer = new DefaultAttributeContainer();
                ibaHolder.setAttributeContainer(defaultattributecontainer);
            }
            BooleanValueDefaultView abstractvaluedefaultview = (BooleanValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);
            if (abstractvaluedefaultview != null) {
                try {
                    abstractvaluedefaultview.setValue(newValue);
                } catch (WTPropertyVetoException e) {
                    logger.error(e.getLocalizedMessage(),e);
                    throw new WTException(e);
                }
                defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
            } else {
                AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                BooleanValueDefaultView abstractvaluedefaultview1 = new BooleanValueDefaultView((BooleanDefView) attributedefdefaultview, newValue);
                defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
            }
            ibaHolder.setAttributeContainer(defaultattributecontainer);
            StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
            try {
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            } catch (RemoteException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
        }


    }

    /**
     * Set IBA Integer Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : iba value
     * @exception WTException
     */
    public void setIBAIntegerValue(WTObject obj, String ibaName,int newValue) throws WTException {
        String ibaClass = "wt.iba.definition.IntegerDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                IntegerValueDefaultView abstractvaluedefaultview = (IntegerValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {

                        abstractvaluedefaultview.setValue(newValue);

                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    IntegerValueDefaultView abstractvaluedefaultview1 = new IntegerValueDefaultView((IntegerDefView) attributedefdefaultview, newValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }

    }

    /**
     * Set IBA Float Value
     * 
     * @param theObject
     * @param theAttribute
     * @param theValue
     * @exception WTException
     */
    public void setIBAFloatValue(WTObject theObject,String theAttribute, String theValue) throws WTException {
        IBAHolder ibaHolder = null;
        theValue = theValue.trim();
        double theFloatValue = 0.0D;
        theFloatValue = Double.valueOf(theValue).doubleValue();

        try {
            // get attribute container
            ibaHolder = (IBAHolder) theObject;
            DefaultAttributeContainer attributeContainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();

            if (attributeContainer == null) {
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, null, null);
                attributeContainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
            }

            // Get attribute definition
            StandardIBADefinitionService defService = new StandardIBADefinitionService();
            AttributeDefDefaultView attributeDefinition = null;
            FloatDefView floatAttrDefinition = null;
            attributeDefinition = defService.getAttributeDefDefaultViewByPath(theAttribute);
            if (!(attributeDefinition instanceof FloatDefView)) {
                throw new WTException("IBA " + theAttribute+ " is not of type Float");
            }

            floatAttrDefinition = (FloatDefView) attributeDefinition;

            // Check if the attribute is already defined
            AbstractValueView[] abstractValueView = null;
            abstractValueView = attributeContainer.getAttributeValues(floatAttrDefinition);
            if (abstractValueView.length == 0) {
                // Add new attribute value
                //Add on 2014/5/15 for float value between 0 and 1
                int digit = 0;
                if(0 < theFloatValue && theFloatValue < 1){
                    double temValue = theFloatValue; 
                    while(temValue<1){
                        temValue = temValue*10;
                        digit++;
                    }
                    digit = theValue.length() - 1 - digit;
                } else {
                    digit = theValue.length() - 1;
                }
                //Add end
                FloatValueDefaultView attrValue = new FloatValueDefaultView(floatAttrDefinition, theFloatValue, digit);
                attributeContainer.addAttributeValue(attrValue);
            } else {
                // Update current attribute value
                FloatValueDefaultView attrValue = (FloatValueDefaultView) abstractValueView[0];
                attrValue.setValue(theFloatValue);
                attributeContainer.updateAttributeValue(attrValue);
            }
            // Update IBAHolder
            ibaHolder.setAttributeContainer(attributeContainer);
            StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
            ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }

    }


    /**
     * Set IBA Ratio Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : double value
     * @exception WTException
     */
    public void setIBARatioValue(WTObject obj, String ibaName,double newValue) throws WTException {
        String ibaClass = "wt.iba.definition.RatioDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                RatioValueDefaultView abstractvaluedefaultview = (RatioValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);

                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    RatioValueDefaultView abstractvaluedefaultview1 = new RatioValueDefaultView((RatioDefView) attributedefdefaultview);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }
    
    /**
     * Set IBA Timestamp Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : timestamp value
     * @exception WTException
     */
    public void updateIBATimestampOrStringValues(WTObject obj, Map<String, Object> ibaValues) throws WTException {
        if(ibaValues == null || ibaValues.isEmpty()){
            return;
        }
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                for (Iterator<String> it = ibaValues.keySet().iterator(); it.hasNext();) {
                     String ibaName = it.next();
                     Object newValue = ibaValues.get(ibaName);
                     if(newValue instanceof Timestamp){
                         TimestampValueDefaultView abstractvaluedefaultview = (TimestampValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, "wt.iba.definition.TimestampDefinition");
                         if (abstractvaluedefaultview != null) {
                             abstractvaluedefaultview.setValue((Timestamp)newValue);
                             defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                         } else {
                             AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                             TimestampValueDefaultView abstractvaluedefaultview1 = new TimestampValueDefaultView((TimestampDefView) attributedefdefaultview,(Timestamp)newValue);
                             defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                         }
                     }
                     if(newValue instanceof String){
                         StringValueDefaultView abstractvaluedefaultview = (StringValueDefaultView) getIBAValueView(
                                    defaultattributecontainer, ibaName, "wt.iba.definition.StringDefinition");
                            if (abstractvaluedefaultview != null) {
                                abstractvaluedefaultview.setValue((String)newValue);
                                defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                            } else {
                                AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                                StringValueDefaultView abstractvaluedefaultview1 = new StringValueDefaultView((StringDefView) attributedefdefaultview, (String)newValue);
                                defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                            }
                     }                   
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }

    /**
     * Set IBA Timestamp Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : timestamp value
     * @exception WTException
     */
    public void setIBATimestampValue(WTObject obj, String ibaName, Timestamp newValue) throws WTException {
        String ibaClass = "wt.iba.definition.TimestampDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                TimestampValueDefaultView abstractvaluedefaultview = (TimestampValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);

                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    TimestampValueDefaultView abstractvaluedefaultview1 = new TimestampValueDefaultView((TimestampDefView) attributedefdefaultview,newValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }
    
    /**
     * Set IBA Timestamp Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : timestamp value
     * @exception WTException
     */
    public void removeIBAValue(WTObject obj, String ibaName) throws WTException {
        String ibaClass1 = "wt.iba.definition.TimestampDefinition";
        String ibaClass2 = "wt.iba.definition.StringDefinition";
        String ibaClass3 = "wt.iba.definition.BooleanDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                AbstractValueView abstractvaluedefaultview = getIBAValueView(defaultattributecontainer, ibaName, ibaClass1);
                if(abstractvaluedefaultview==null){
                    abstractvaluedefaultview = getIBAValueView(defaultattributecontainer, ibaName, ibaClass2);
                }
                if(abstractvaluedefaultview==null){
                	 abstractvaluedefaultview = getIBAValueView(defaultattributecontainer, ibaName, ibaClass3);
                }
                if (abstractvaluedefaultview != null) {
                    defaultattributecontainer.deleteAttributeValue(abstractvaluedefaultview);
                } 
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }

    /**
     * Set IBA URL Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : URL string
     * @exception WTException
     */
    public void setIBAURLValue(WTObject obj, String ibaName,String newValue) throws WTException {
        String ibaClass = "wt.iba.definition.URLDefinition";

        try {
            StringTokenizer st = new StringTokenizer(newValue, "$$$");
            String urlValue = "";
            String urlDesc = "";
            while (st.hasMoreElements()) {
                urlValue = st.nextToken();
                if (st.hasMoreElements()) {
                    urlDesc = st.nextToken();
                }
            }
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                URLValueDefaultView abstractvaluedefaultview = (URLValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(urlValue);
                    abstractvaluedefaultview.setDescription(urlDesc);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(
                            ibaName, false);
                    URLValueDefaultView abstractvaluedefaultview1 = new URLValueDefaultView((URLDefView) attributedefdefaultview, urlValue,urlDesc);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }

    /**
     * Set IBA URL Values
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : URL string array
     * @exception WTException
     * @author Allen Wang
     */
    public void setIBAURLValues(WTObject obj, String ibaName,String[] newValue) throws WTException {
        String oneNewValue = "";
        try {
            for (int i = 0; i < newValue.length; i++) {
                oneNewValue = newValue[i];
                StringTokenizer st = new StringTokenizer(oneNewValue, "$$$");
                String urlValue = "";
                String urlDesc = "";
                while (st.hasMoreElements()) {
                    urlValue = st.nextToken();
                    if (st.hasMoreElements()) {
                        urlDesc = st.nextToken();
                    }
                }
                if (obj instanceof IBAHolder) {
                    IBAHolder ibaHolder = (IBAHolder) obj;
                    DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                    if (defaultattributecontainer == null) {
                        defaultattributecontainer = new DefaultAttributeContainer();
                        ibaHolder.setAttributeContainer(defaultattributecontainer);
                    }

                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    URLValueDefaultView abstractvaluedefaultview1 = new URLValueDefaultView(
                            (URLDefView) attributedefdefaultview, urlValue,
                            urlDesc);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);

                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                    StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null,null);
                    ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null,null);
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }

    /**
     * Set IBA Unit Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : double value
     * @exception WTException
     */
    public void setIBAUnitValue(WTObject obj, String ibaName,double newValue) throws WTException {
        String ibaClass = "wt.iba.definition.UnitDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                UnitValueDefaultView abstractvaluedefaultview = (UnitValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);

                String strFloatValue = String.valueOf(newValue);
                StringTokenizer st = new StringTokenizer(strFloatValue, ".");

                int iFloatLength = 0;
                if (st.hasMoreElements()) {
                    st.nextElement();
                    if (st.hasMoreElements()) {
                        iFloatLength = ((String) st.nextElement()).length();
                    }
                }
                iFloatLength = iFloatLength + 1;
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);
                    abstractvaluedefaultview.setPrecision(iFloatLength);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    UnitValueDefaultView abstractvaluedefaultview1 = new UnitValueDefaultView((UnitDefView) attributedefdefaultview, newValue,iFloatLength);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }

    /**
     * Set IBA Any Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @param newValue
     *            : iba value
     * @exception WTException
     *                , RemoteException, WTPropertyVetoException, ParseException
     */
    public void setIBAAnyValue(WTObject obj, String ibaName,String newValue) throws WTException{

        AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
        IBAHolder ibaholder = (IBAHolder) obj;
        try{
            String ibaClass = "";
            if (attributedefdefaultview instanceof FloatDefView) {
                ibaClass = "wt.iba.definition.FloatDefinition";
            } else if (attributedefdefaultview instanceof StringDefView) {
                ibaClass = "wt.iba.definition.StringDefinition";
            } else if (attributedefdefaultview instanceof IntegerDefView) {
                ibaClass = "wt.iba.definition.IntegerDefinition";
            } else if (attributedefdefaultview instanceof RatioDefView) {
                ibaClass = "wt.iba.definition.RatioDefinition";
            } else if (attributedefdefaultview instanceof TimestampDefView) {
                ibaClass = "wt.iba.definition.TimestampDefinition";
            } else if (attributedefdefaultview instanceof BooleanDefView) {
                ibaClass = "wt.iba.definition.BooleanDefinition";
            } else if (attributedefdefaultview instanceof URLDefView) {
                ibaClass = "wt.iba.definition.URLDefinition";
            } else if (attributedefdefaultview instanceof ReferenceDefView) {
                ibaClass = "wt.iba.definition.ReferenceDefinition";
            } else if (attributedefdefaultview instanceof UnitDefView) {
                ibaClass = "wt.iba.definition.UnitDefinition";
            }
    
            // store the new iteration (this will copy forward the obsolete set of
            // IBA values in the database)
            // ibaholder = (IBAHolder)PersistenceHelper.manager.store(
            // (Persistable)ibaholder );
    
            // load IBA values from DB (because obsolete IBA values have
            // been copied forward to new iteration by IBA persistence event
            // handlers)
            ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder,"CSM", null, null);
    
            // clear the container to remove all obsolete IBA values and persist
            // this
            // to remove IBA values from database
            // *deleteAllIBAValues(ibaholder );
            ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder,null, SessionHelper.manager.getLocale(), null);
            DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) (ibaholder).getAttributeContainer();
    
    
            // AbstractValueView abstractvalueview =
            // getIBAValueView(defaultattributecontainer,ibaName,ibaClass);
            List<AbstractValueView> vAbstractvalueview = getIBAValueViews(defaultattributecontainer,ibaName, ibaClass);
            // if (abstractvalueview != null){
            for (int i = 0; i < vAbstractvalueview.size(); i++) {
                AbstractValueView abstractvalueview = (AbstractValueView) vAbstractvalueview.get(i);
                defaultattributecontainer.deleteAttributeValue(abstractvalueview);
                // save the new iteration with the updated set of IBA values
                // ibaholder = (IBAHolder)PersistenceHelper.manager.save(
                // (Persistable)ibaholder );
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaholder, null, null, null);
                ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, "CSM", null, null);
            }
    
            if (!"".equals(newValue)) {
                if (attributedefdefaultview instanceof FloatDefView) {
                    // setIBAFloatValue(obj, ibaName, Float.parseFloat(newValue));
                    setIBAFloatValue(obj, ibaName, newValue);
    
                } else if (attributedefdefaultview instanceof StringDefView) {
                    if (newValue.contains("||")) {
                        String[] newMultiString = newValue.split("\\|\\|");
                        setIBAStringValues(obj, ibaName, newMultiString);
    
                    } else {
                        setIBAStringValue(obj, ibaName, newValue);
    
                    }
                } else if (attributedefdefaultview instanceof IntegerDefView) {
                    setIBAIntegerValue(obj, ibaName, Integer.parseInt(newValue));
    
                } else if (attributedefdefaultview instanceof RatioDefView) {
                    setIBARatioValue(obj, ibaName, Double.parseDouble(newValue));
    
                } else if (attributedefdefaultview instanceof TimestampDefView) {
                    if (!newValue.contains(":")) {
                        newValue = newValue + " 00:00:00";
                    }
    
                    String format = "yyyy-MM-dd HH:mm:ss";
                    if (SessionHelper.manager.getLocale().toString().equals("zh_CN")
                            || SessionHelper.manager.getLocale().toString().equals("zh_TW")) {
                        format = "yyyy/MM/dd HH:mm:ss";
                    }
    
                    java.text.SimpleDateFormat formats = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.text.SimpleDateFormat formatSource = new java.text.SimpleDateFormat(format);
                    setIBATimestampValue(obj, ibaName, Timestamp.valueOf(formats.format(formatSource.parse(newValue))));
    
    
                } else if (attributedefdefaultview instanceof BooleanDefView) {
                    setIBABooleanValue(obj, ibaName, Boolean.parseBoolean(newValue));
    
                } else if (attributedefdefaultview instanceof URLDefView) {
                    setIBAURLValue(obj, ibaName, newValue);
                  
                } else if (attributedefdefaultview instanceof ReferenceDefView) {
    
                } else if (attributedefdefaultview instanceof UnitDefView) {
                    setIBAUnitValue(obj, ibaName, Double.parseDouble(newValue));
    
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }

    /**
     * Add an IBA value with dependency relation
     * 
     * @param sourcename
     * @param sourcevalue
     * @param businessname
     * @param businessvalue
     * @throws IBAValueException
     * @throws WTPropertyVetoException
     * @throws WTException
     */
    public void addIBAValue(String sourcename, String sourcevalue,String businessname, String businessvalue) throws WTException {
        AttributeDefDefaultView sourceDef = getDefDefaultView(sourcename);
        AttributeDefDefaultView businessDef = getDefDefaultView(businessname);
        AbstractValueView sourceValue = getAbstractValueView(sourceDef,sourcevalue);
        AbstractValueView businessValue = getAbstractValueView(businessDef,businessvalue);
        sourceValue = setDependency(sourceValue, businessDef,businessValue);

        Object[] obj = (Object[]) ibaContainer.get(sourcename);
        Object[] temp;
        if (obj == null) {
            temp = new Object[2];
            temp[0] = sourceDef;
            temp[1] = sourceValue;
        } else {
            temp = new Object[obj.length + 1];
            int i;
            for (i = 0; i < obj.length; i++) {
                temp[i] = obj[i];
            }
            temp[i] = sourceValue;
        }
        ibaContainer.put(sourcename, temp);
    }

    /**
     * initializePart() with this signature is designed to pre-populate values
     * from an existing IBA holder.
     * 
     * @exception WTException
     *                , RemoteException
     */
    private void initializeIBAValue(IBAHolder ibaHolder) throws WTException {
        ibaContainer = new Hashtable<String,Object[]>();
        ibaOrigContainer = new Hashtable<String,Object[]>();
        if (ibaHolder.getAttributeContainer() == null) {
            try {
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, SessionHelper.manager.getLocale(), null);
            } catch (RemoteException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
        }
        DefaultAttributeContainer theContainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
        if (theContainer != null) {
            AttributeDefDefaultView[] theAtts = theContainer.getAttributeDefinitions();
            for (int i = 0; i < theAtts.length; i++) {
                AbstractValueView[] theValues = theContainer.getAttributeValues(theAtts[i]);
                if (theValues != null) {
                    // Add by Somesh
                    Object[] temp = new Object[theValues.length + 1];
                    temp[0] = theAtts[i];
                    for (int j = 1; j <= theValues.length; j++) {
                        temp[j] = theValues[j - 1];
                    }
                    // End Add by Somesh
                    ibaContainer.put(theAtts[i].getName(), temp);
                    ibaOrigContainer.put(theAtts[i].getName(), temp);
                }
            }
        }

    }

    /**
     * suppressCSMConstraint
     * 
     * @param theContainer
     * @param s
     * @return DefaultAttributeContainer
     * @exception WTException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private DefaultAttributeContainer suppressCSMConstraint(DefaultAttributeContainer theContainer, String s) throws WTException {

        ClassificationStructDefaultView defStructure = null;
        defStructure = getClassificationStructDefaultViewByName(s);
        if (defStructure != null) {
            Vector cgs = theContainer.getConstraintGroups();
            Vector newCgs = new Vector();
            // AttributeConstraint immutable = null;
            try {

                for (int i = 0; i < cgs.size(); i++) {
                    ConstraintGroup cg = (ConstraintGroup) cgs.elementAt(i);
                    if (cg != null) {

                        if (!cg.getConstraintGroupLabel().equals(wt.csm.constraint.CSMConstraintFactory.CONSTRAINT_GROUP_LABEL)) {
                            newCgs.addElement(cg);
                        } else {
                            // Enumeration enum = cg.getConstraints();
                            ConstraintGroup newCg = new ConstraintGroup();
                            newCg.setConstraintGroupLabel(cg.getConstraintGroupLabel());

                            newCgs.addElement(newCg);
                        }
                    }
                }
                theContainer.setConstraintGroups(newCgs);
            } catch (WTPropertyVetoException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
        }

        return theContainer;
    }

    /**
     * removeCSMConstraint
     * 
     * @param attributecontainer
     * @return DefaultAttributeContainer
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private DefaultAttributeContainer removeCSMConstraint(DefaultAttributeContainer attributecontainer) throws WTException{
        Object obj = attributecontainer.getConstraintParameter();
        if (obj == null) {
            obj = new String("CSM");
        } else if (obj instanceof Vector) {
            ((Vector) obj).addElement(new String("CSM"));
        } else {
            Vector vector1 = new Vector();
            vector1.addElement(obj);
            obj = vector1;
            ((Vector) obj).addElement(new String("CSM"));
        }
        try {
            attributecontainer.setConstraintParameter(obj);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);

        }
        return attributecontainer;
    }

    /**
     * Update the IBAHolder's attribute container from the hashtable
     * 
     * @param ibaHolder
     * @return
     * @throws WTException
     * @throws WTPropertyVetoException
     * @throws RemoteException
     */
    public IBAHolder updateAttributeContainer(IBAHolder ibaHolder) throws WTException {
        try{    
            if (ibaHolder.getAttributeContainer() == null) {
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, SessionHelper.manager.getLocale(), null);
            }
            DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
    
            defaultattributecontainer = suppressCSMConstraint(defaultattributecontainer, getIBAHolderClassName(ibaHolder));
    
            AttributeDefDefaultView[] theAtts = defaultattributecontainer.getAttributeDefinitions();
            // Delete existed iba if they aren't in the hashtable of this class
            for (int i = 0; i < theAtts.length; i++) {
                AttributeDefDefaultView theDef = theAtts[i];
                if (ibaContainer.get(theDef.getName()) == null) {
                    createOrUpdateAttributeValuesInContainer(defaultattributecontainer, theDef, null);
                }
            }
    
            //Enumeration<Object[]> enum1 = ibaContainer.entrySet();
            Iterator<Entry<String,Object[]>> iter = ibaContainer.entrySet().iterator();
            while (iter.hasNext()) {
                Object[] temp = (Object[]) iter.next().getValue();
                AttributeDefDefaultView theDef = (AttributeDefDefaultView) temp[0];
                AbstractValueView abstractvalueviews[] = new AbstractValueView[temp.length - 1];
                for (int i = 0; i < temp.length - 1; i++) {
                    abstractvalueviews[i] = (AbstractValueView) temp[i + 1];
                }
                createOrUpdateAttributeValuesInContainer(defaultattributecontainer,theDef, abstractvalueviews);
            }
    
            defaultattributecontainer = removeCSMConstraint(defaultattributecontainer);
            ibaHolder.setAttributeContainer(defaultattributecontainer);
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } 
        return ibaHolder;
    }

    /**
     * updateAttributeContainer
     * 
     * @param ibaHolder
     * @param flag1
     * @return IBAHolder
     * @exception WTException
     *                , WTPropertyVetoException, RemoteException
     */
    public IBAHolder updateAttributeContainer(IBAHolder ibaHolder, boolean flag1) throws WTException{
        
        if (ibaHolder.getAttributeContainer() == null) {
            try {
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, SessionHelper.manager.getLocale(), null);
            } catch (RemoteException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
        }
        DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
        defaultattributecontainer = suppressCSMConstraint(defaultattributecontainer, getIBAHolderClassName(ibaHolder));
        //Enumeration<Object[]> enum1 = ibaContainer.elements();
        Iterator<Entry<String,Object[]>> iter = ibaContainer.entrySet().iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next().getValue();
            for (int j = 1; j < temp.length; j++) {
                AbstractValueView abstractvalueview = (AbstractValueView) temp[j];
                int state = abstractvalueview.getState();

                if (state == AbstractValueView.NEW_STATE || state == AbstractValueView.CHANGED_STATE) {
                    LoadValue.createOrUpdateAttributeValueInContainer(defaultattributecontainer, abstractvalueview);
                } else {
                    if (state == AbstractValueView.DELETED_STATE) {
                        if (defaultattributecontainer.isDeleteValid(abstractvalueview)) {
                            defaultattributecontainer.deleteAttributeValue(abstractvalueview);
                        }
                    }
                }
            }
        }
        defaultattributecontainer = removeCSMConstraint(defaultattributecontainer);
        ibaHolder.setAttributeContainer(defaultattributecontainer);
        return ibaHolder;
    }

    /**
     * Update without checkout/checkin
     * 
     * @param ibaholder
     * @return
     */
    public boolean updateIBAHolder(IBAHolder ibaholder) throws WTException {
        IBAValueDBService ibavaluedbservice = new IBAValueDBService();
        boolean flag = true;

        PersistenceServerHelper.manager.update((Persistable) ibaholder);
        wt.iba.value.AttributeContainer attributecontainer = (wt.iba.value.AttributeContainer) ibaholder.getAttributeContainer();
        Object obj = ((DefaultAttributeContainer) attributecontainer).getConstraintParameter();
        wt.iba.value.AttributeContainer attributecontainer1 = (wt.iba.value.AttributeContainer) ibavaluedbservice.updateAttributeContainer(ibaholder, obj, null, null);
        ibaholder.setAttributeContainer((wt.iba.value.AttributeContainer) attributecontainer1);

        return flag;
    }

    /**
     * update IBAHolder
     * 
     * @param ibaholder
     * @param flag
     * @return IBAHolder
     * @exception Exception
     */
    public IBAHolder updateIBAHolder(IBAHolder ibaholder, boolean flag) throws WTException {
        
        if (flag) {
            return LoadValue.applySoftAttributes(ibaholder);
        } else {
            // false : Update IBAHolder Check Constraints
            IBAValueDBService ibavaluedbservice = new IBAValueDBService();

            PersistenceServerHelper.manager.update((Persistable) ibaholder);
            wt.iba.value.AttributeContainer attributecontainer = (wt.iba.value.AttributeContainer) ibaholder.getAttributeContainer();
            Object obj = ((DefaultAttributeContainer) attributecontainer).getConstraintParameter();
            wt.iba.value.AttributeContainer attributecontainer1 = (wt.iba.value.AttributeContainer) ibavaluedbservice.updateAttributeContainer(ibaholder, obj, null, null);
            ibaholder.setAttributeContainer((wt.iba.value.AttributeContainer) attributecontainer1);

            return ibaholder;
        }
    }

    /**
     * suppressCSMConstraint
     * 
     * @param theContainer
     * @return DefaultAttributeContainer
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public DefaultAttributeContainer suppressCSMConstraint(DefaultAttributeContainer theContainer) throws WTException{

        String classToUpdate = "wt.part.WTPart";
        ClassificationStructDefaultView defStructure = null;
        try{
            defStructure = ClassificationHelper.service.getClassificationStructDefaultView(classToUpdate);
    
            if (defStructure != null) {
                ReferenceDefView ref = defStructure.getReferenceDefView();
                Vector cgs = theContainer.getConstraintGroups();
                Vector newCgs = new Vector();
                for (int i = 0; i < cgs.size(); i++) {
                    ConstraintGroup cg = (ConstraintGroup) cgs.elementAt(i);
                    if (cg != null) {
                        if (!cg.getConstraintGroupLabel().equals(wt.csm.constraint.CSMConstraintFactory.CONSTRAINT_GROUP_LABEL)) {
                            newCgs.addElement(cg);
                        } else {
                            Enumeration enume = cg.getConstraints();
                            ConstraintGroup newCg = new ConstraintGroup();
                            newCg.setConstraintGroupLabel(cg.getConstraintGroupLabel());
                            while (enume.hasMoreElements()) {
                                AttributeConstraint ac = (AttributeConstraint) enume.nextElement();
                                if ((ac.appliesToAttrDef(ref))&& (ac.getValueConstraint() instanceof wt.iba.constraint.Immutable)) {

                                } else {
                                    newCg.addConstraint(ac);
                                }
                            }
                            newCgs.addElement(newCg);
                        }
                    }
                }
                theContainer.setConstraintGroups(newCgs);
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } 
        // end of CSM constraint
        return theContainer;
    }

    /**
     * Referenced from method "createOrUpdateAttributeValueInContainer" of
     * wt.iba.value.service.LoadValue.java -> modified to have multi-values
     * support
     * 
     * @param defaultattributecontainer
     * @param theDef
     * @param abstractvalueviews
     * @throws WTException
     */
    private void createOrUpdateAttributeValuesInContainer(DefaultAttributeContainer defaultattributecontainer,
            AttributeDefDefaultView theDef,AbstractValueView[] abstractvalueviews) throws WTException {
       
        if (defaultattributecontainer == null) {
            throw new IBAContainerException("wt.iba.value.service.LoadValue.createOrUpdateAttributeValueInContainer :  DefaultAttributeContainer passed in is null!");
        }
        AbstractValueView abstractvalueviews0[] = defaultattributecontainer.getAttributeValues(theDef);

        if (abstractvalueviews0 == null || abstractvalueviews0.length == 0) {
            // Original valus is empty
            for (int j = 0; j < abstractvalueviews.length; j++) {
                AbstractValueView abstractvalueview = abstractvalueviews[j];
                defaultattributecontainer.addAttributeValue(abstractvalueview);
                
            }
        } else if (abstractvalueviews == null
                || abstractvalueviews.length == 0) {
            // New value is empty, so delete all existed values
            for (int j = 0; j < abstractvalueviews0.length; j++) {
                AbstractValueView abstractvalueview = abstractvalueviews0[j];
                defaultattributecontainer.deleteAttributeValue(abstractvalueview);
            }
        } else if (abstractvalueviews0.length <= abstractvalueviews.length) {

            // More new valuss than (or equal to) original values,
            // So update existed values and add new values
            for (int j = 0; j < abstractvalueviews0.length; j++) {
                abstractvalueviews0[j] = LoadValue.cloneAbstractValueView(abstractvalueviews[j], abstractvalueviews0[j]);
                // abstractvalueviews0[j] = abstractvalueviews[j];
                abstractvalueviews0[j] = cloneReferenceValueDefaultView(abstractvalueviews[j], abstractvalueviews0[j]);

                defaultattributecontainer.updateAttributeValue(abstractvalueviews0[j]);
            }
            for (int j = abstractvalueviews0.length; j < abstractvalueviews.length; j++) {
                AbstractValueView abstractvalueview = abstractvalueviews[j];
                // abstractvalueview.setState(AbstractValueView.CHANGED_STATE);
                defaultattributecontainer.addAttributeValue(abstractvalueview);
            }
        } else if (abstractvalueviews0.length > abstractvalueviews.length) {
            // Less new values than original values,
            // So delete some values
            for (int j = 0; j < abstractvalueviews.length; j++) {
                abstractvalueviews0[j] = LoadValue.cloneAbstractValueView(abstractvalueviews[j], abstractvalueviews0[j]);
                abstractvalueviews0[j] = cloneReferenceValueDefaultView(abstractvalueviews[j], abstractvalueviews0[j]);
                // abstractvalueviews0[j] = abstractvalueviews[j];
                defaultattributecontainer.updateAttributeValue(abstractvalueviews0[j]);
            }
            for (int j = abstractvalueviews.length; j < abstractvalueviews0.length; j++) {
                AbstractValueView abstractvalueview = abstractvalueviews0[j];
                defaultattributecontainer.deleteAttributeValue(abstractvalueview);
            }
        }

    }

    // For dependency used.
    AbstractValueView cloneReferenceValueDefaultView(AbstractValueView abstractvalueview,AbstractValueView abstractvalueview1) throws WTException {
        if (abstractvalueview instanceof AbstractContextualValueDefaultView) {
            try {
                ((AbstractContextualValueDefaultView) abstractvalueview1).setReferenceValueDefaultView(((AbstractContextualValueDefaultView) abstractvalueview).getReferenceValueDefaultView());
            } catch (WTPropertyVetoException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
         }
        return abstractvalueview1;
    }

    /**
     * another "black-box": pass in a string, and get back an IBA value object.
     * Copy from wt.iba.value.service.LoadValue.java -> please don't modify this
     * method
     * 
     * @param abstractattributedefinizerview
     * @param s
     * @param s1
     * @return
     */
    private AbstractValueView internalCreateValue(AbstractAttributeDefinizerView abstractattributedefinizerview,String s, String s1) {
        AbstractValueView abstractvalueview = null;
        if (abstractattributedefinizerview instanceof FloatDefView) {
            abstractvalueview = LoadValue.newFloatValue(abstractattributedefinizerview, s, s1);
        } else if (abstractattributedefinizerview instanceof StringDefView) {
            abstractvalueview = LoadValue.newStringValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof IntegerDefView) {
            abstractvalueview = LoadValue.newIntegerValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof RatioDefView) {
            abstractvalueview = LoadValue.newRatioValue(abstractattributedefinizerview, s, s1);
        } else if (abstractattributedefinizerview instanceof TimestampDefView) {
            abstractvalueview = LoadValue.newTimestampValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof BooleanDefView) {
            abstractvalueview = LoadValue.newBooleanValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof URLDefView) {
            abstractvalueview = LoadValue.newURLValue(abstractattributedefinizerview, s, s1);
        } else if (abstractattributedefinizerview instanceof ReferenceDefView) {
            abstractvalueview = LoadValue.newReferenceValue(abstractattributedefinizerview, s, s1);
        } else if (abstractattributedefinizerview instanceof UnitDefView) {
            abstractvalueview = LoadValue.newUnitValue(abstractattributedefinizerview, s, s1);
        }

        return abstractvalueview;
    }

    /**
     * newTimestampValue
     * 
     * @param def
     * @param dateTime
     * @return AbstractValueView
     */
    public AbstractValueView newTimestampValue(AbstractAttributeDefinizerView def, Timestamp dateTime) throws WTException{

        AbstractValueView value = new TimestampValueDefaultView((TimestampDefView) def,dateTime);
        return value;

    }

    // //////////////////////////////////////////////////////////////////////////////
    /**
     * This method is a "black-box": pass in a string, like
     * "Electrical/Resistance/ ResistanceRating" and get back a IBA definition
     * object.
     * 
     * @param ibaPath
     * @return
     */
    public AttributeDefDefaultView getAttributeDefinition(String ibaPath) throws WTException{

        AttributeDefDefaultView ibaDef = null;

        try {
            ibaDef = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(ibaPath);
            if (ibaDef == null) {
                AbstractAttributeDefinizerView ibaNodeView = DefinitionLoader.getAttributeDefinition(ibaPath);
                if (ibaNodeView != null) {
                    ibaDef = IBADefinitionHelper.service.getAttributeDefDefaultView((AttributeDefNodeView) ibaNodeView);
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }

        return ibaDef;
    }

    /**
     * getDisplayUnits
     * 
     * @param unitedfview
     * @return String
     */
    public String getDisplayUnits(UnitDefView unitdefview) {
        return getDisplayUnits(unitdefview, UNITS);
    }

    /**
     * getDisplayUnits
     * 
     * @param unitdefview
     * @param s
     * @return String
     */
    public String getDisplayUnits(UnitDefView unitdefview, String s) {
        QuantityOfMeasureDefaultView quantityofmeasuredefaultview = unitdefview.getQuantityOfMeasureDefaultView();
        String s1 = quantityofmeasuredefaultview.getBaseUnit();
        if (s != null) {
            String s2 = unitdefview.getDisplayUnitString(s);
            if (s2 == null) {
                s2 = quantityofmeasuredefaultview.getDisplayUnitString(s);
            }
            if (s2 == null) {
                s2 = quantityofmeasuredefaultview.getDefaultDisplayUnitString(s);
            }
            if (s2 != null) {
                s1 = s2;
            }
        }
        if (s1 == null) {
            return "";
        } else {
            return s1;
        }
    }

    /**
     * Please refer to the method "getIBAHolderClassName" of class
     * "wt.csm.constraint.CSMConstraintFactory"
     * 
     * @param ibaholder
     * @return
     */
    private String getIBAHolderClassName(IBAHolder ibaholder) {
        String s = null;
        if (ibaholder instanceof AbstractLiteObject) {
            s = ((AbstractLiteObject) ibaholder).getHeavyObjectClassname();
        } else {
            s = ibaholder.getClass().getName();
        }
        return s;
    }

    /**
     * Please refer to the method "getClassificationStructDefaultViewByName" of
     * class "wt.csm.constraint.CSMConstraintFactory"
     * 
     * @param s
     * @return
     * @throws IBAConstraintException
     */
    @SuppressWarnings("rawtypes")
    private ClassificationStructDefaultView getClassificationStructDefaultViewByName(String s) throws WTException {
        ClassificationService classificationservice = ClassificationHelper.service;
        ClassificationStructDefaultView aclassificationstructdefaultview[] = null;
        try {
            aclassificationstructdefaultview = classificationservice.getAllClassificationStructures();

            for (int i = 0; aclassificationstructdefaultview != null
                    && i < aclassificationstructdefaultview.length; i++) {
                if (s.equals(aclassificationstructdefaultview[i].getPrimaryClassName())) {
                    return aclassificationstructdefaultview[i];
                }
            }

            for (Class class1 = Class.forName(s).getSuperclass(); !class1
                    .getName().equals((wt.fc.WTObject.class).getName())
                    && !class1.getName().equals((java.lang.Object.class).getName()); class1 = class1.getSuperclass()) {
                for (int j = 0; aclassificationstructdefaultview != null
                        && j < aclassificationstructdefaultview.length; j++) {
                    if (class1.getName().equals(aclassificationstructdefaultview[j].getPrimaryClassName())) {
                        return aclassificationstructdefaultview[j];
                    }
                }

            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (ClassNotFoundException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }

        return null;
    }

    /**
     * Get IBA URL Value
     * 
     * @param obj
     * @param ibaName
     *            : iba name
     * @return String[0]: URL
     * @return String[1]: URL Label
     * @exception WTException
     * @author Allen Wang
     */
    public String[] getIBAURLValue(WTObject obj, String ibaName) throws WTException {
        String[] result = new String[2];
        String ibaClass = "wt.iba.definition.URLDefinition";

        if (obj instanceof IBAHolder) {
            IBAHolder ibaHolder = (IBAHolder) obj;
            DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
            if (defaultattributecontainer == null) {
                defaultattributecontainer = new DefaultAttributeContainer();
                ibaHolder.setAttributeContainer(defaultattributecontainer);
            }
            URLValueDefaultView abstractvaluedefaultview = (URLValueDefaultView) getIBAValueView(defaultattributecontainer, ibaName, ibaClass);
            if (abstractvaluedefaultview != null) {
                result[0] = abstractvaluedefaultview.getValue();
                result[1] = abstractvaluedefaultview.getDescription();
            }
        }

        return result;
    }


    public void updateIBAStringValues(WTObject holder, String ibaName, List<String> values) throws WTException {
        if (holder instanceof IBAHolder) {
            IBAHolder ibaHolder = (IBAHolder) holder;
            DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
            if (defaultattributecontainer == null) {
                defaultattributecontainer = new DefaultAttributeContainer();
                ibaHolder.setAttributeContainer(defaultattributecontainer);
            }
            AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
            // del first
            defaultattributecontainer.deleteAttributeValues(attributedefdefaultview);

            String oneNewValue = "";
            try{
                for (int i = 0; i < values.size(); i++) {
                    oneNewValue = (String) values.get(i);
                    StringValueDefaultView abstractvaluedefaultview1 = new StringValueDefaultView((StringDefView) attributedefdefaultview, oneNewValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                    StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                    ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
    
                }
            }catch (RemoteException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
        }
    }


    /**
     * get All Ibas of the ibaholder
     * 
     * @return Map<String, Object[]>
     * 
     *         Author: cvp-lifanying Date: Apr 28, 2012
     */
    public Map<String, Object[]> getAllIbas() {
        return ibaContainer;
    }

    public Map<String, String> getIbaContainer() throws WTException {
        Map<String, String> ibaKeyValue = new Hashtable<String, String>();
        //Enumeration<String> enumeration = ibaContainer.keys();
        Iterator<String> iter = ibaContainer.keySet().iterator();
        try {
            while (iter.hasNext()) {
                String s = iter.next();
                AbstractValueView abstractvalueview = (AbstractValueView) ((Object[]) ibaContainer.get(s))[1];
                ibaKeyValue.put(s, IBAValueUtility.getLocalizedIBAValueDisplayString(abstractvalueview,SessionHelper.manager.getLocale()));
            }
        } catch (WTException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return ibaKeyValue;
    }

    public IBAHolder updateIBAPart(IBAHolder ibaholder) throws WTException {
        
        DefaultAttributeContainer defaultAttributeContainer;
        try {
            defaultAttributeContainer = (DefaultAttributeContainer) (IBAValueHelper.service.refreshAttributeContainerWithoutConstraints(ibaholder)).getAttributeContainer();
            Iterator<Entry<String,Object[]>> iter = ibaContainer.entrySet().iterator();
            while (iter.hasNext()) {
                Object aobj[] = (Object[]) iter.next().getValue();
                AbstractValueView abstractvalueview = (AbstractValueView) aobj[1];
                AttributeDefDefaultView attributedefdefaultview = (AttributeDefDefaultView) aobj[0];
                if (abstractvalueview.getState() == 1) {
                    defaultAttributeContainer.deleteAttributeValues(attributedefdefaultview);
                    abstractvalueview.setState(3);
                    defaultAttributeContainer.addAttributeValue(abstractvalueview);
                }
            }
/*            
            for (Enumeration<Object[]> enumeration = ibaContainer.elements(); enumeration.hasMoreElements();) {
                Object aobj[] = (Object[]) enumeration.nextElement();
                AbstractValueView abstractvalueview = (AbstractValueView) aobj[1];
                AttributeDefDefaultView attributedefdefaultview = (AttributeDefDefaultView) aobj[0];
                if (abstractvalueview.getState() == 1) {
                    defaultAttributeContainer.deleteAttributeValues(attributedefdefaultview);
                    abstractvalueview.setState(3);
                    defaultAttributeContainer.addAttributeValue(abstractvalueview);
                }
            }
*/
            defaultAttributeContainer.setConstraintParameter(new String("CSM"));
            ibaholder.setAttributeContainer(defaultAttributeContainer);
            
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        } catch (WTPropertyVetoException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }

        return ibaholder;
    }
    
    public  Timestamp getIBATimeValue(IBAHolder ibaHolder,String name) throws WTException {

        if (ibaHolder.getAttributeContainer() == null) {
            try {
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, SessionHelper.manager.getLocale(), null);
            } catch (RemoteException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
        }
        DefaultAttributeContainer theContainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
        if (theContainer != null) {
            AttributeDefDefaultView[] theAtts = theContainer.getAttributeDefinitions();
            for (int i = 0; i < theAtts.length; i++) {
                AbstractValueView[] theValues = theContainer.getAttributeValues(theAtts[i]);
                if (theValues != null) {
                    // Add by Somesh
                    Object[] temp = new Object[theValues.length + 1];
                    temp[0] = theAtts[i];
                    for (int j = 1; j <= theValues.length; j++) {
                        temp[j] = theValues[j - 1];
                    }
                    // End Add by Somesh
                    ibaContainer.put(theAtts[i].getName(), temp);
                    ibaOrigContainer.put(theAtts[i].getName(), temp);
                }
            }
        }
        Timestamp value = null;
        if (ibaContainer.get(name) != null) {
            TimestampValueDefaultView theValue = (TimestampValueDefaultView) ((Object[]) ibaContainer.get(name))[1];
            value = theValue.getValue();
        }
        return value;
    }
    
    public Timestamp getIBATimeValue(String name) throws WTException {
        Timestamp value = null;
        if (ibaContainer.get(name) != null) {
            TimestampValueDefaultView theValue = (TimestampValueDefaultView) ((Object[]) ibaContainer.get(name))[1];
            value = theValue.getValue();
        }
        return value;
    }
    
    public Boolean getIBABooleanValue(String name) throws WTException {
        Boolean value = null;
        if (ibaContainer.get(name) != null) {
            BooleanValueDefaultView theValue = (BooleanValueDefaultView) ((Object[]) ibaContainer.get(name))[1];
            value = theValue.isValue();
        }
        return value;
    }
    
    public void setIBAStringValueFromObjToObj(WTObject fromHolder ,WTObject toHolder ,String ibaName) throws WTException {
        String value = getIBAValue((IBAHolder) fromHolder, ibaName);
        setIBAStringValue(toHolder, ibaName, value);
    }

    public void setIBAValueNoException(String name, String value) throws WTException {
        AttributeDefDefaultView theDef = getDefDefaultViewNoException(name);
        if(theDef!=null){
            Object theValue = getAbstractValueView(theDef, value);

            Object[] temp = new Object[2];
            temp[0] = theDef;
            temp[1] = theValue;
            ibaContainer.put(name, temp);
        }
    }
    
    /**
     * Get DefDefaultValue
     * 
     * @param name
     * @return AttributeDefDefaultView
     * @exception WTException
     */
    private AttributeDefDefaultView getDefDefaultViewNoException(String name) throws WTException {
        AttributeDefDefaultView theDef = null;
        Object[] obj = (Object[]) ibaContainer.get(name);
        if (obj != null) {
            theDef = (AttributeDefDefaultView) obj[0];
        } else {
            theDef = getAttributeDefinition(name);
        }
        if (theDef == null) {

            return null;
        }
        return theDef;
    }
    
    public IBAHolder updateIBAValueByMap(IBAHolder ibaholder1, Map<String, Object> ibaMap, Map<String, AttributeDefDefaultView> attrDefMap) throws WTException{
        IBAHolder ibaholder = ibaholder1;
        if (ibaholder == null || ibaMap == null || ibaMap.isEmpty()){
            return ibaholder;
        }
        try {
            ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, SessionHelper.manager.getLocale(), null);
            DefaultAttributeContainer ibaContainer = (DefaultAttributeContainer) ibaholder.getAttributeContainer();
            if (ibaContainer != null) {
                AbstractValueView[] valueViews = ibaContainer.getAttributeValues();
                HashSet<String> temp = new HashSet<String>();
                for (int i = 0; i < valueViews.length; i++) {
                    AbstractValueView valueView = valueViews[i];
                    AttributeDefDefaultView defView = valueView.getDefinition();
                    String name = defView.getName();
                    temp.add(name);
                    if (ibaMap.containsKey(name)) {
                        ibaContainer.deleteAttributeValues(defView);
                    }
                }
                Iterator<String> it = ibaMap.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Object value = ibaMap.get(key);
                    if (value == null || "".equals(value)){
                        continue;
                    }
                    
                    AttributeDefDefaultView defView = null;
                    if(attrDefMap != null){
                         defView = attrDefMap.get(key);
                    }
                    if(defView == null){
                        defView = getAttributeDefinition(key, false);
                        if(attrDefMap != null){
                            attrDefMap.put(key, defView);
                        }
                    }
                    
                    if (defView instanceof StringDefView) {
                        String tempValue = null;
                        if(value instanceof String){
                            tempValue = (String) value;
                        }else{
                            tempValue = value.toString();
                        }
                        if(tempValue != null){
                            StringDefView stringDefView = (StringDefView) defView;
                            StringValueDefaultView valueView = new StringValueDefaultView(stringDefView, tempValue);
                            ibaContainer.addAttributeValue(valueView);
                        }
                    } else if (defView instanceof BooleanDefView) {
                        Boolean tempValue = null;
                        if(value instanceof Boolean){
                            tempValue = (Boolean) value;
                        }else if(value instanceof String){
                            if ("Y".equalsIgnoreCase(String.valueOf(value)) || "YES".equalsIgnoreCase(String.valueOf(value))|| "TRUE".equalsIgnoreCase(String.valueOf(value))) {
                                tempValue = true;
                            } else {
                                tempValue = false;
                            }
                        }
                        if(tempValue != null){
                            BooleanDefView booleanDefView = (BooleanDefView) defView;
                            BooleanValueDefaultView valueView = new BooleanValueDefaultView(booleanDefView, tempValue);
                            ibaContainer.addAttributeValue(valueView);
                        }
                    } else if (defView instanceof IntegerDefView) {
                        Long tempValue = null;
                        if(value instanceof String){
                            tempValue = Long.valueOf((String) value);
                        }else if(value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float){
                            tempValue = Long.valueOf(String.valueOf(value));
                        }
                        if(tempValue != null){
                            IntegerDefView integerDefView = (IntegerDefView) defView;
                            IntegerValueDefaultView valueView = new IntegerValueDefaultView(integerDefView, tempValue);
                            ibaContainer.addAttributeValue(valueView);
                        }
                        
                    }else if(defView instanceof TimestampDefView){
                        Timestamp tempValue = null;
                        if(value instanceof Timestamp){
                            tempValue = (Timestamp) value;
                        }else if(value instanceof Date){
                            Date date = (Date) value;
                            tempValue = new Timestamp(date.getTime());
                        }else if(value instanceof String){
                            Date date = TimeZoneUtil.parseDateFromString((String) value,"EST");
                            tempValue = new Timestamp(date.getTime());
                        }
                        if(tempValue!=null){
                            TimestampDefView timestampDef = (TimestampDefView) defView;
                            TimestampValueDefaultView valueView = new TimestampValueDefaultView(timestampDef,tempValue);
                            ibaContainer.addAttributeValue(valueView);
                        }
                    }else if (defView instanceof FloatDefView) {
                    	Float tempValue = null;
                    	String tempStr = null;
                        if(value instanceof String){
                        	tempStr = (String)value;
                            tempValue = Float.valueOf(tempStr);
                        }else if(value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float){
                        	tempStr = String.valueOf(value);
                            tempValue = Float.valueOf(tempStr);
                        }
                        
                        
                        if(tempValue != null){
                            FloatDefView floatDefView = (FloatDefView) defView;
                            int digit = 0;
                            if(0 < tempValue && tempValue < 1){
                                double temValue = tempValue; 
                                while(temValue<1){
                                    temValue = temValue*10;
                                    digit++;
                                }
                                digit = tempStr.length() - 1 - digit;
                            } else {
                                digit = tempStr.length() - 1;
                            }
                            FloatValueDefaultView valueView = new FloatValueDefaultView(floatDefView, tempValue,digit);
                            ibaContainer.addAttributeValue(valueView);
                        }
                        
                    }
                }
            }
            ibaholder.setAttributeContainer(ibaContainer);
            if (PersistenceHelper.isPersistent(ibaholder)) {
                AttributeContainer attrContainer = ibaholder.getAttributeContainer();
                IBAValueDBService ibaService = new IBAValueDBService();
                Object obj = ((DefaultAttributeContainer) attrContainer).getConstraintParameter();
                AttributeContainer ibaContainer0 = ibaService.updateAttributeContainer(ibaholder, obj, null, null);
                ibaholder.setAttributeContainer(ibaContainer0);
            }
            return ibaholder;
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
    }
    
    /**
     * updateIBAValue
     * 
     * @param IBAHolder
     * @param String
     *            ibaName
     * @param String
     *            value
     * @return IBAHolder
     */
    public  IBAHolder updateIBAValue(IBAHolder ibaholder, String ibaName, String value) throws WTException {
        IBAHolder ibaHolder = null;
        if (ibaholder != null && ibaName != null && !"".equals(ibaName.trim())) {
            HashMap<String, String> ibaMap = new HashMap<String, String>();
            ibaMap.put(ibaName, value);
            ibaHolder = updateIBAValue(ibaholder, ibaMap);
        }
        return ibaHolder;
    }
    
    /**
     * updateIBAValue
     * 
     * @param IBAHolder
     * @param HashMap
     *            <String, String> ibaMap
     * @return IBAHolder
     */
    public  IBAHolder updateIBAValue(IBAHolder ibaholder1,Map<String, String> ibaMap) throws WTException {
        IBAHolder ibaholder = ibaholder1;
        if (ibaholder != null && ibaMap != null && !ibaMap.isEmpty()) {
            try {
                ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, SessionHelper.manager.getLocale(),null);
                DefaultAttributeContainer ibaContainer = (DefaultAttributeContainer) ibaholder.getAttributeContainer();
                if (ibaContainer != null) {
                    AbstractValueView[] valueViews = ibaContainer.getAttributeValues();
                    HashSet<String> temp = new HashSet<String>();
                    for (int i = 0; i < valueViews.length; i++) {
                        AbstractValueView valueView = valueViews[i];
                        AttributeDefDefaultView defView = valueView.getDefinition();
                        String name = defView.getName();
                        temp.add(name);
                        if (ibaMap.containsKey(name)) {
                            ibaContainer.deleteAttributeValues(defView);
                        }
                    }
                    Iterator<String> it = ibaMap.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        String value = ibaMap.get(key);
                        if (value == null || "".equals(value.trim())) {
                            continue;
                        }
                        AttributeDefDefaultView defView = getAttributeDefinition(key, false);
                        if (defView instanceof StringDefView) {
                            StringDefView stringDefView = (StringDefView) defView;
                            StringValueDefaultView valueView = new StringValueDefaultView(stringDefView, value);
                            ibaContainer.addAttributeValue(valueView);
                        } else if (defView instanceof BooleanDefView) {
                            BooleanDefView booleanDefView = (BooleanDefView) defView;
                            if ("Y".equalsIgnoreCase(value)
                                    || "YES".equalsIgnoreCase(value)
                                    || "TRUE".equalsIgnoreCase(value)) {
                                BooleanValueDefaultView valueView = new BooleanValueDefaultView(booleanDefView, true);
                                ibaContainer.addAttributeValue(valueView);
                            } else {
                                BooleanValueDefaultView valueView = new BooleanValueDefaultView(booleanDefView, false);
                                ibaContainer.addAttributeValue(valueView);
                            }
                        } else if (defView instanceof IntegerDefView) {
                            IntegerDefView integerDefView = (IntegerDefView) defView;
                            long l = Long.parseLong(value);
                            IntegerValueDefaultView valueView = new IntegerValueDefaultView(integerDefView, l);
                            ibaContainer.addAttributeValue(valueView);
                        }
                        //add update float type iba value by chris 20140318
                        else if (defView instanceof FloatDefView) {
                            // add by WuLingzi 20140520 begin
//                            FloatDefView floatDefView = (FloatDefView) defView;
//                            double d = Double.parseDouble(value);
//                            int digit = 0;
//                            if (0 < d && d < 1) {
//                                double temValue = d;
//                                while (temValue < 1) {
//                                    temValue = temValue * 10;
//                                    digit++;
//                                }
//                                digit = value.length() - 1 - digit;
//                            } else {
//                                digit = value.length() - 1;
//                            }
                            // add by WuLingzi 201405202 end
//                            FloatValueDefaultView valueView = new FloatValueDefaultView(floatDefView, d, digit);
//                            ibaContainer.addAttributeValue(valueView);    
                          //end by chris 20140318
                            // add by hezhihui(20150723)---start-----
                            IBAUtil iba = new IBAUtil(ibaholder);
                            if (!StringUtil.isEmpty(value)) {
                                iba.setIBAValue(key, value);
                            }
                            logger.debug("update float attributes:key:" + key + ",value:" + value);
                            ibaholder = iba.updateAttributeContainer(ibaholder);
                            // add by hezhihui(2015723)---end-----  
                        }else if (defView instanceof TimestampDefView) {
                            TimestampDefView timestampDef = (TimestampDefView) defView;
                            Timestamp ts = convertTimestamp(value);
                            if (ts != null) {
                                TimestampValueDefaultView valueView = new TimestampValueDefaultView(timestampDef, ts);
                                ibaContainer.addAttributeValue(valueView);
                            }
                        }else if(defView instanceof UnitDefView){
//                            UnitDefView unitDef = (UnitDefView)defView;
//                            String strFloatValue = String.valueOf(value);
//                            StringTokenizer st = new StringTokenizer(strFloatValue, ".");
//                            
//                            int iFloatLength = 0;
//                            if (st.hasMoreElements()) {
//                                st.nextElement();
//                                if (st.hasMoreElements()) {
//                                    iFloatLength = ((String) st.nextElement()).length();
//                                }
//                            }
//                            iFloatLength = iFloatLength + 1;
//                            
//                            UnitValueDefaultView abstractvaluedefaultview = new UnitValueDefaultView(unitDef, Double.valueOf(value),iFloatLength);
//                            ibaContainer.addAttributeValue(abstractvaluedefaultview);
                            // add by hezhihui(20150501)---start-----
                            IBAUtil iba = new IBAUtil(ibaholder);
                            if (!StringUtil.isEmpty(value)) {
                                iba.setIBAValue(key, value);
                            }
                            logger.debug("update unit attributes:key:" + key + ",value:" + value);
                            ibaholder = iba.updateAttributeContainer(ibaholder);
                            // add by hezhihui(20150501)---end-----
                        }
                    }
                }
                ibaholder.setAttributeContainer(ibaContainer);
                if (PersistenceHelper.isPersistent(ibaholder)) {
                    AttributeContainer attrContainer = ibaholder.getAttributeContainer();
                    IBAValueDBService ibaService = new IBAValueDBService();
                    Object obj = ((DefaultAttributeContainer) attrContainer).getConstraintParameter();
                    AttributeContainer ibaContainer0 = ibaService.updateAttributeContainer(ibaholder, obj, null,null);
                    ibaholder.setAttributeContainer(ibaContainer0);
                }
            } catch (RemoteException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            } 
        }
        return ibaholder;
    }

    /**
     * Convert Timestamp by String
     * 
     * @param String
     *            timestampStr
     * @return Timestamp
     */
    @SuppressWarnings("static-access")
    private  Timestamp convertTimestamp(String timestampStr)throws WTException {
        boolean flag = true;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTimestamp = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        String regex = "\\d{4}-\\d{1,2}-\\d{1,2}";
        Date date = null;
        Pattern p = Pattern.compile(regex);
        if (!"".equals(timestampStr) && timestampStr != null) {
            if (p.matches(regex, timestampStr)) {
                try {
                    date = sdfDate.parse(timestampStr);
                } catch (ParseException e) {
                    flag = false;
                }
            } else {
                try {
                    date = sdfTimestamp.parse(timestampStr);
                } catch (ParseException e) {
                    flag = false;
                }
            }
        }
        if (date != null) {
            Timestamp tt = new Timestamp(date.getTime());
            return tt;
        } else if (flag) {
            throw new WTException("Input date [" + timestampStr+ "] can not parsing.");
        }
        return null;
    }    
    
    /**
     * 
     * @param p
     * @param loc
     * @param dataMap
     * @return
     * @throws WTException
     */
    public Persistable setValueForFloat(Persistable p, Locale loc,
            Map<String, Object> dataMap,WTPrincipalReference wtprincipalreference) throws WTException {
        p = checkedOut((WTPart)p);
        LWCNormalizedObject lwcObject = new LWCNormalizedObject(p, null, loc,
                new UpdateOperationIdentifier());
        Iterator<String> keyIt = dataMap.keySet().iterator();
        String key = null;
        lwcObject.load(dataMap.keySet());
        while (keyIt.hasNext()) {
            key = keyIt.next();
            lwcObject.set(key, dataMap.get(key));
        }
        
        lwcObject.apply();
        //if (PersistenceHelper.isPersistent(p)) {
            try {
                p = WorkInProgressHelper.service.checkin((Workable) p, "Updated IBA");
                VersionControlHelper.setIterationModifier((Iterated) p, wtprincipalreference);
            } catch (WTPropertyVetoException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
            p = PersistenceHelper.manager.save(p);
            
//        }else{
//            p = PersistenceHelper.manager.save(p);
//        }
        return p;
    }
    
    private WTPart checkedOut(WTPart part) throws WTException {
        if (WorkInProgressHelper.isCheckedOut(part)) {
            if (!WorkInProgressHelper.isWorkingCopy(part)) {
                part = (WTPart) WorkInProgressHelper.service.workingCopyOf(part);
            }
        } else {
            Folder folder = WorkInProgressHelper.service.getCheckoutFolder();
            CheckoutLink checkoutLink = null;
            try {
                checkoutLink = WorkInProgressHelper.service.checkout(part, folder, "Updated IBA");
            } catch (WTPropertyVetoException e) {
                logger.error(e.getLocalizedMessage(),e);
                throw new WTException(e);
            }
            part = (WTPart) checkoutLink.getWorkingCopy();
        }
        return part;
    }
    /**
     * 
     * @param p
     * @param key
     * @return
     * @throws WTException
     */
    public static Object getValueForFloat(Persistable targetObj, Locale loc,String ibaName) throws WTException {
        Object ibaValue = null;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Object) RemoteMethodServer.getDefault().invoke(
                        "getValueForFloat",
                        IBAUtil.class.getName(),
                        null,
                        new Class[] { Persistable.class, Locale.class,
                                String.class },
                        new Object[] { targetObj, loc, ibaName });
            } else {
                LWCNormalizedObject obj = null;
                try {
                    obj = new LWCNormalizedObject(targetObj, null, loc, null);
                    obj.load(ibaName);
                    ibaValue = obj.get(ibaName);
                } catch (WTException e) {
                    logger.error(e.getLocalizedMessage(),e);
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
            
        } catch (InvocationTargetException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return ibaValue;
    }
    
    /**
     * get IBA Object Value
     * 
     * @param targetObj
     * @param ibaName
     * @return
     * @throws WTException
     */
    public Object getIBAObjectValue(Persistable targetObj, String ibaName) throws WTException {
        Locale loc = SessionHelper.getLocale();
        Object ibaValue = null;
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Object) RemoteMethodServer.getDefault().invoke(
                        "getIBAObjectValue",
                        IBAUtil.class.getName(),
                        null,
                        new Class[] { Persistable.class, String.class },
                        new Object[] { targetObj, ibaName });
            } else {
                LWCNormalizedObject obj = null;
                try {
                    obj = new LWCNormalizedObject(targetObj, null, loc, null);
                    obj.load(ibaName);
                    ibaValue = obj.get(ibaName);
                } catch (WTException e) {
                    logger.error(e.getLocalizedMessage(),e);
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
            
        } catch (InvocationTargetException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new WTException(e);
        }
        return ibaValue;
    }
    
}

