package ext.rdc.standard.portal.util;
import java.io.Serializable;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;


import ext.rdc.standard.portal.constant.StringConstant;
import ext.rdc.standard.portal.resource.PortalResource;
import wt.method.RemoteAccess;
import wt.session.SessionHelper;
import wt.util.WTException;

public class ResourceUtil implements RemoteAccess, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public static String getValue(String key) throws WTException {
		 key = key.trim();
		 int index = key.lastIndexOf(StringConstant.SPOT);
		 String className = key.substring(0, index);
		 String resuorceKey = key.substring(index+1);
		 ResourceBundle resource = ResourceBundle.getBundle(className,SessionHelper.getLocale());
		 return StringUtils.defaultString(resource.getString(resuorceKey));
	 }
	 public static String getPortalResourceValue(String key) throws WTException {
		 String className = PortalResource.class.getName();
		 ResourceBundle resource = ResourceBundle.getBundle(className,SessionHelper.getLocale());
		 return StringUtils.defaultString(resource.getString(key));
	 }
	 
   
}
