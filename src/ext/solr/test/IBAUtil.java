

package ext.solr.test;



import java.io.PrintStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Hashtable;

import wt.doc.WTDocument;
import wt.fc.*;
import wt.feedback.StatusFeedback;
import wt.iba.definition.DefinitionLoader;
import wt.iba.definition.litedefinition.*;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.definition.service.IBADefinitionService;
import wt.iba.value.*;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.litevalue.IntegerValueDefaultView;
import wt.iba.value.litevalue.RatioValueDefaultView;
import wt.iba.value.litevalue.ReferenceValueDefaultView;
import wt.iba.value.litevalue.StringValueDefaultView;
import wt.iba.value.litevalue.BooleanValueDefaultView;
import wt.iba.value.litevalue.FloatValueDefaultView;
import wt.iba.value.litevalue.TimestampValueDefaultView;
import wt.iba.value.litevalue.URLValueDefaultView;
import wt.iba.value.litevalue.UnitValueDefaultView;
import wt.iba.value.service.*;
import wt.part.WTPart;
import wt.query.*;
import wt.session.*;
import wt.method.MethodContext;
import wt.method.RemoteMethodServer;
import wt.method.RemoteAccess;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.util.WTException;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.util.WTProperties;
import wt.folder.FolderHelper;
import wt.util.WTPropertyVetoException;

import java.util.Enumeration;

import wt.vc.wip.*;
import wt.locks.*;
import wt.pom.Transaction;



public class IBAUtil implements RemoteAccess

{



    Hashtable ibaContainer;

    public static WTProperties wtproperties;

    public static boolean VERBOSE;

    static

    {

        wtproperties = null;

        try

        {

            wtproperties = WTProperties.getLocalProperties();

            VERBOSE = wtproperties.getProperty("ext.rdc.iba.verbose", false);

        }

        catch(Throwable t)

        {

            throw new ExceptionInInitializerError(t);

        }

    }

    

    private IBAUtil()

    {

        ibaContainer = new Hashtable();

    }



    public IBAUtil(IBAHolder ibaholder)

    {

        initializeIBAPart(ibaholder);

    }



    public Hashtable getIbaContainer()

    {

        Hashtable ibaKeyValue = new Hashtable();

        Enumeration enumeration = ibaContainer.keys();

        try

        {

            while(enumeration.hasMoreElements()) 

            {

                String s = (String)enumeration.nextElement();

                AbstractValueView abstractvalueview = (AbstractValueView)((Object[])ibaContainer.get(s))[1];

                ibaKeyValue.put(s, IBAValueUtility.getLocalizedIBAValueDisplayString(abstractvalueview, SessionHelper.manager.getLocale()));

            }

        }

        catch(Exception exception)

        {

            exception.printStackTrace();

        }

        return ibaKeyValue;

    }

    

    public String toString()

    {

        StringBuffer stringbuffer = new StringBuffer();

        Enumeration enumeration = ibaContainer.keys();

        try

        {

            while(enumeration.hasMoreElements()) 

            {

                String s = (String)enumeration.nextElement();

                AbstractValueView abstractvalueview = (AbstractValueView)((Object[])ibaContainer.get(s))[1];

                stringbuffer.append(s + " - " + IBAValueUtility.getLocalizedIBAValueDisplayString(abstractvalueview, SessionHelper.manager.getLocale()));

                stringbuffer.append('\n');

            }

        }

        catch(Exception exception)

        {

            exception.printStackTrace();

        }

        return stringbuffer.toString();

    }



    public String getIBAValue(String s)

    {

        try

        {

            return getIBAValue(s, SessionHelper.manager.getLocale());

        }

        catch(WTException wte)

        {

            wte.printStackTrace();

        }

        return null;

    }

    

    //moderified by wanlichao at 2012 11 11 start

    /**

	 * 构造IBA属性值

	 * @author wanlichao moderify at 20111108

	 * @param valueView IBA属性值对象

	 * @param value IBA属性值

	 */

	public static Object getInternalValue(AbstractValueView valueView) {

		if (valueView instanceof BooleanValueDefaultView)

			return new Boolean(((BooleanValueDefaultView) valueView).isValue()).toString();

		if (valueView instanceof FloatValueDefaultView)

			return ((FloatValueDefaultView) valueView).getValueAsString();

		if (valueView instanceof IntegerValueDefaultView)

			return ((IntegerValueDefaultView) valueView).getValueAsString();

		if (valueView instanceof RatioValueDefaultView)

			return ((RatioValueDefaultView) valueView).getLocalizedDisplayString();

		if (valueView instanceof ReferenceValueDefaultView)

			return ((ReferenceValueDefaultView) valueView).getLiteIBAReferenceable();

		if (valueView instanceof StringValueDefaultView)

			return ((StringValueDefaultView) valueView).getValue();

		if (valueView instanceof TimestampValueDefaultView)

			return ((TimestampValueDefaultView) valueView).getValue(); // 返回

		// Timestamp对象

		if (valueView instanceof UnitValueDefaultView)

			return ((UnitValueDefaultView) valueView).getValueAsString();

		if (valueView instanceof URLValueDefaultView)

			return ((URLValueDefaultView) valueView).getValue();

		else

			return null;

	}

    

    /**

	 * 获取IBA属性值表

	 * @author wanlichao moderify at 20111108

	 * @param ibaHolder:IBA储存器，例如文档、零部件、图档等

	 * @return

	 * @throws WTException

	 * @throws java.rmi.RemoteException

	 */

	public static Hashtable getIBAValues(IBAHolder ibaHolder) throws WTException, java.rmi.RemoteException {

		ibaHolder = IBAValueHelper.service.refreshAttributeContainerWithoutConstraints(ibaHolder);

		DefaultAttributeContainer dac = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();

		AbstractValueView valueViews[] = dac.getAttributeValues();



		Hashtable values = new Hashtable();



		for (int j = 0; j < valueViews.length; j++) {

			AbstractValueView avv = valueViews[j];

			Object value = getInternalValue(avv);

			if (value != null)

				values.put(avv.getDefinition().getName(), value);

		}

		return values;

	}

	//moderified by wanlichao at 2012 11 11 end

    /***

     * 获取IBA属性值

     */

    public String getIBAValue(String s, Locale locale)

    {

        //added by Eric Lin, 2004/10/26

        if (ibaContainer.get(s) == null)

        {

            if (VERBOSE)

                System.out.println("    >>> Attribute '"+s+"' is null.");

            return null;

        }

        //end of added by Eric.

    	

        AbstractValueView abstractvalueview = (AbstractValueView)((Object[])ibaContainer.get(s))[1];

        try

        {

            return IBAValueUtility.getLocalizedIBAValueDisplayString(abstractvalueview, locale);

        }

        catch(WTException wte)

        {

            wte.printStackTrace();

        }

        return null;

    }



    /***

     * added by Eric Lin, 2005/04/18

     * 获取布尔型的IBA属性值

     */

    public boolean getIBABooleanValue(String attrName)

    {

        boolean value=false;

        if (ibaContainer.get(attrName) == null)

        {

            if (VERBOSE)

                System.out.println(">> Attribute '"+attrName+"' is null.");

            return value;

        }

    	

        AbstractValueView abstractvalueview = (AbstractValueView)((Object[])ibaContainer.get(attrName))[1];

        String thisIBAClass = (abstractvalueview.getDefinition()).getAttributeDefinitionClassName();

        if ( thisIBAClass.equals("wt.iba.definition.BooleanDefinition") ) 

        {

            value = (boolean)((BooleanValueDefaultView)abstractvalueview).isValue();

            if (VERBOSE)

                System.out.println(">>Boolean value: "+value);

        }

        return value;

    }

    //end of added by Eric Lin

    

    /***

     * added by Eric Lin, 2004/12/08

     * 获取浮点数型的IBA属性值

     */

    public float getIBAFloatValue(String attrName)

    {

        float value = 0;

        if (ibaContainer.get(attrName) == null)

        {

            if (VERBOSE)

                System.out.println(">> Attribute '"+attrName+"' is null.");

            return value;

        }

        

        AbstractValueView abstractvalueview = (AbstractValueView)((Object[])ibaContainer.get(attrName))[1];

        String thisIBAClass = (abstractvalueview.getDefinition()).getAttributeDefinitionClassName();

        if ( thisIBAClass.equals("wt.iba.definition.FloatDefinition") ) 

            value = (float)((FloatValueDefaultView)abstractvalueview).getValue();

        return value;

    }    

    //end of added by Eric Lin

    

    private void initializeIBAPart(IBAHolder ibaholder)

    {

        ibaContainer = new Hashtable();

        try

        {

            ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, SessionHelper.manager.getLocale(), null);

            DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer)ibaholder.getAttributeContainer();

            if(defaultattributecontainer != null)

            {

                AttributeDefDefaultView aattributedefdefaultview[] = defaultattributecontainer.getAttributeDefinitions();

                for(int i = 0; i < aattributedefdefaultview.length; i++)

                {

                    AbstractValueView aabstractvalueview[] = defaultattributecontainer.getAttributeValues(aattributedefdefaultview[i]);

                    if(aabstractvalueview != null)

                    {

                        Object aobj[] = new Object[2];

                        aobj[0] = aattributedefdefaultview[i];

                        aobj[1] = aabstractvalueview[0];

                        ibaContainer.put(aattributedefdefaultview[i].getName(), ((Object) (aobj)));

                    }

                }



            }

        }

        catch(Exception exception)

        {

            exception.printStackTrace();

        }

    }



    public IBAHolder updateIBAPart(IBAHolder ibaholder)

        throws Exception

    {

        ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, SessionHelper.manager.getLocale(), null);

        DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer)ibaholder.getAttributeContainer();

        for(Enumeration enumeration = ibaContainer.elements(); enumeration.hasMoreElements();)

            try

            {

                Object aobj[] = (Object[])enumeration.nextElement();

                AbstractValueView abstractvalueview = (AbstractValueView)aobj[1];

                AttributeDefDefaultView attributedefdefaultview = (AttributeDefDefaultView)aobj[0];

                if(abstractvalueview.getState() == 1)

                {

                    defaultattributecontainer.deleteAttributeValues(attributedefdefaultview);

                    abstractvalueview.setState(3);

                    defaultattributecontainer.addAttributeValue(abstractvalueview);

                }

            }

            catch(Exception exception)

            {

                exception.printStackTrace();

            }



        ibaholder.setAttributeContainer(defaultattributecontainer);

        return ibaholder;

    }



    public void setIBAValue(String s, String s1)

        throws WTPropertyVetoException

    {

        AbstractValueView abstractvalueview = null;

        AttributeDefDefaultView attributedefdefaultview = null;

        Object aobj[] = (Object[])ibaContainer.get(s);

        if(aobj != null)

        {

            abstractvalueview = (AbstractValueView)aobj[1];

            attributedefdefaultview = (AttributeDefDefaultView)aobj[0];

        }

        if(abstractvalueview == null)

            attributedefdefaultview = getAttributeDefinition(s);

        if(attributedefdefaultview == null)

        {

            System.out.println("definition is null ...");

            return;

        }

        else

        {

            System.out.println("attributedefdefaultview is "+attributedefdefaultview.getName());

        }

        abstractvalueview = internalCreateValue(attributedefdefaultview, s1);

        if(abstractvalueview == null)

        {

            System.out.println("@@@@@@@@after creation, iba value is null ...,String Value="+s1);

            return;

        } 

        else

        {

            if(VERBOSE)

                System.out.println("@@@@@@@@after creation, iba value is ..."+abstractvalueview+"...String Value="+s1);

            abstractvalueview.setState(1);

            Object aobj1[] = new Object[2];

            aobj1[0] = attributedefdefaultview;

            aobj1[1] = abstractvalueview;

            ibaContainer.put(attributedefdefaultview.getName(), ((Object) (aobj1)));

            return;

        }

    }

    

    public static void setIBAStringValue( WTObject obj, String ibaName,String newValue )

	        throws WTException

	{

		String ibaClass = "wt.iba.definition.StringDefinition";

		try

		{

			if(obj instanceof IBAHolder)

			{

				IBAHolder ibaHolder = (IBAHolder)obj;

				DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);

				if(defaultattributecontainer != null)

				{

					System.out.println("	got the DefaultAttributeContainer!");

				}

				else

				{

					defaultattributecontainer = new DefaultAttributeContainer();

					ibaHolder.setAttributeContainer(defaultattributecontainer);

				}

				StringValueDefaultView stringvaluedefaultview = (StringValueDefaultView)getIBAValueView(defaultattributecontainer, ibaName, ibaClass);

				if(stringvaluedefaultview != null)

				{

					stringvaluedefaultview.setValue(newValue);

					defaultattributecontainer.updateAttributeValue(stringvaluedefaultview);

				}

				else

				{

					AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName);

					StringValueDefaultView stringvaluedefaultview1 = new StringValueDefaultView((StringDefView)attributedefdefaultview, newValue);

					defaultattributecontainer.addAttributeValue(stringvaluedefaultview1);

				}

				ibaHolder.setAttributeContainer(defaultattributecontainer);

				wt.iba.value.service.LoadValue.applySoftAttributes(ibaHolder);

			}

		}

		catch(Exception exception)

		{

			exception.printStackTrace();

		}

	}

	public static AttributeDefDefaultView getAttributeDefinition(String s)

	{

		AttributeDefDefaultView attributedefdefaultview = null;

		try

		{

			attributedefdefaultview = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(s);

			if(attributedefdefaultview == null)

			{

				AbstractAttributeDefinizerView abstractattributedefinizerview = DefinitionLoader.getAttributeDefinition(s);

				if(abstractattributedefinizerview != null)

					attributedefdefaultview = IBADefinitionHelper.service.getAttributeDefDefaultView((AttributeDefNodeView)abstractattributedefinizerview);

			}

		}

		catch(Exception exception)

		{

			exception.printStackTrace();

		}

		return attributedefdefaultview;

	}

	public static AbstractValueView getIBAValueView( DefaultAttributeContainer dac, String ibaName, String ibaClass )

		throws WTException

	{

		AbstractValueView aabstractvalueview[] = null;

		AbstractValueView avv = null;

		aabstractvalueview = dac.getAttributeValues();

		for(int j = 0; j < aabstractvalueview.length; j++) {

			String thisIBAName = aabstractvalueview[j].getDefinition().getName();

			String thisIBAValue = IBAValueUtility.getLocalizedIBAValueDisplayString(aabstractvalueview[j], Locale.CHINA);

			String thisIBAClass = (aabstractvalueview[j].getDefinition()).getAttributeDefinitionClassName();

			if ( thisIBAName.equals(ibaName) && thisIBAClass.equals(ibaClass) ) {

				avv=aabstractvalueview[j];

				break;

			}

		}

		return avv;

	}

	public static DefaultAttributeContainer getContainer(IBAHolder ibaHolder)

		throws WTException, java.rmi.RemoteException

	{

		ibaHolder = IBAValueHelper.service.refreshAttributeContainerWithoutConstraints(ibaHolder);

		DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();

		return defaultattributecontainer;

	}



    private AbstractValueView internalCreateValue(AbstractAttributeDefinizerView abstractattributedefinizerview, String s)

    {

        AbstractValueView abstractvalueview = null;

        if(abstractattributedefinizerview instanceof FloatDefView)

            abstractvalueview = LoadValue.newFloatValue(abstractattributedefinizerview, s, null);

        else

        if(abstractattributedefinizerview instanceof StringDefView)

            abstractvalueview = LoadValue.newStringValue(abstractattributedefinizerview, s);

        else

        if(abstractattributedefinizerview instanceof IntegerDefView)

            abstractvalueview = LoadValue.newIntegerValue(abstractattributedefinizerview, s);

        else

        if(abstractattributedefinizerview instanceof RatioDefView)

            abstractvalueview = LoadValue.newRatioValue(abstractattributedefinizerview, s, null);

        else

        if(abstractattributedefinizerview instanceof TimestampDefView)

            abstractvalueview = LoadValue.newTimestampValue(abstractattributedefinizerview, s);

        else

        if(abstractattributedefinizerview instanceof BooleanDefView)

            abstractvalueview = LoadValue.newBooleanValue(abstractattributedefinizerview, s);

        else

        if(abstractattributedefinizerview instanceof URLDefView)

            abstractvalueview = LoadValue.newURLValue(abstractattributedefinizerview, s, null);

        else

        if(abstractattributedefinizerview instanceof ReferenceDefView)

            abstractvalueview = LoadValue.newReferenceValue(abstractattributedefinizerview, "ClassificationNode", s);

        else

        if(abstractattributedefinizerview instanceof UnitDefView)

            abstractvalueview = LoadValue.newUnitValue(abstractattributedefinizerview, s, null);

        return abstractvalueview;

    }
    
    public static void test() throws WTException{
    	String oid = "VR:wt.part.WTPart:196339";
    	ReferenceFactory rf = new ReferenceFactory();
    	WTPart part = (WTPart)rf.getReference(oid).getObject();
    	IBAUtil iba = new IBAUtil(part);
    	String value = iba.getIBAValue("recommendedUsage");
    	MethodContext.getContext().sendFeedback(new StatusFeedback("value="+value));
    }
    
    public static void main(String[] args){
    	RemoteMethodServer server = RemoteMethodServer.getDefault();
		server.setUserName("wcadmin");
		server.setPassword("wcadmin");
//		Class[] classes = { String.class };
//		Object[] objs = { args[0]};

		try {
			server.invoke("test", IBAUtil.class.getName(), null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
   }

}

