package ext.yongc.plm.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.method.RemoteMethodServer;
import wt.util.WTException;

/**
 * 
 * @author yge
 */
public class ReflectionUtil {

    private static Logger logger = LogR.getLogger(ReflectionUtil.class.getName());
    

    /**
     * Invoke method on method server, if no return value for the invoking method, this method always return
     * <code>null</code>
     * 
     * @param methodName
     *            method name
     * @param cls
     *            class
     * @param instance
     *            instance
     * @param argTypes
     *            arrays of arguments' types
     * @param argValues
     *            arrays of arguments' values
     * @return method return value
     * @throws WTException
     *             Windchill exception
     */
    public static Object invokeOnMethodServer(String methodName, Class cls, Object instance, Class[] argTypes, Object[] argValues) throws WTException {
        Object retValue = null;
        RemoteMethodServer methodServer = RemoteMethodServer.getDefault();
        if (hasReturnValue(methodName, cls, argTypes)) {
            try {
                retValue = methodServer.invoke(methodName, cls.getName(), instance, argTypes, argValues);
            } catch (RemoteException e) {
                logger.error(e);
                throw new WTException(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
                throw new WTException(e);
            }
        } else {
            try {
                methodServer.invoke(methodName, cls.getName(), instance, argTypes, argValues);
            } catch (RemoteException e) {
                logger.error(e);
                throw new WTException(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
                throw new WTException(e);
            }
        }
        return retValue;
    }


    /**
     * Judge if the method has return value
     * 
     * @param methodName
     *            method name
     * @param cls
     *            class
     * @param argTypes
     *            arrays of arguments' types
     * @return has return value or not
     * @throws WTException
     *             Windchill exception
     */
    private static boolean hasReturnValue(String methodName, Class cls, Class[] argTypes) throws WTException {
        boolean hasReturnValue = true;
        try {
            Method method = cls.getMethod(methodName, argTypes);
            Class returnType = method.getReturnType();
            if (void.class.equals(returnType)) {
                hasReturnValue = false;
            }
        } catch (NoSuchMethodException e) {
            logger.error(e);
            throw new WTException(e);
        }
        return hasReturnValue;
    }

    
}
