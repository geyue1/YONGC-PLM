package ext.rdc.standard.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;

import wt.util.WTProperties;

public class PropertiesUtil {
    private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
    private Map<String, String> hmConfig = new HashMap<String, String>();
    private String filepath = "";

    /**
     * constructor
     * 
     * <br>
     * <b>Revision History</b><br>
     * 
     * @param configFilePath
     */
    public PropertiesUtil(String configFilePath) {
        this.filepath = configFilePath;
        readConfig();
    }

    public PropertiesUtil(FileInputStream fis){
    	readConfig(fis);
    }
    
    public void readConfig(FileInputStream fis) {
        Properties pro = new Properties();
        try {
          
            pro.load(fis);
            Enumeration enuPro = pro.propertyNames();
            while (enuPro.hasMoreElements()) {
                String proName = (String) enuPro.nextElement();
                String proValue = pro.getProperty(proName);
             
                proName=new String(proName.getBytes("ISO-8859-1"), "utf-8");
                logger.debug("proName>>>"+proName+">>>proValue>>>"+proValue);
                hmConfig.put(proName, proValue);
            }
            fis.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    /**
     * 
     * init read property file.
     * 
     * <br>
     * <b>Revision History</b><br>
     * <b>Comment:</b>
     * 
     * 
     * 
     */
    public void readConfig() {
        Properties pro = new Properties();
        try {
            String wthome = (WTProperties.getLocalProperties()).getProperty("wt.home", "");
            FileInputStream fis = new FileInputStream(wthome + File.separator + filepath);
            pro.load(fis);
            Enumeration enuPro = pro.propertyNames();
            while (enuPro.hasMoreElements()) {
                String proName = (String) enuPro.nextElement();
                String proValue = pro.getProperty(proName);
               /* try {
                	if(proValue!=null){
                		 proValue = new String(proValue.getBytes("ISO-8859-1"), "utf-8");
                	}
                   
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }*/
                proName=new String(proName.getBytes("ISO-8859-1"), "utf-8");
                logger.debug("proName>>>"+proName+">>>proValue>>>"+proValue);
                hmConfig.put(proName, proValue);
            }
            fis.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    /**
     * 
     * Get value by key.
     * 
     * <br>
     * <b>Revision History</b><br>
     * <b>Comment:</b>
     * 
     * @param key
     * @return String
     * @throws UnsupportedEncodingException
     * 
     * 
     */
    public String getValueByKey(String key) {
        String strValue = hmConfig.get(key);
        if (strValue == null || "".equals(strValue)) {
            return "";
        } else {
            try {
                strValue = new String(strValue.getBytes("ISO-8859-1"), "utf-8");
                strValue = strValue.trim();
                return strValue;
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return "";
    }

    /**
     * 
     * Get values by key and delimiter.
     * <br>
     * 
     * <b>Revision History</b><br>
     * <b>Comment:</b>
     * 
     * @param key
     * @param delimiter
     * @return List<String>
     * 
     * 
     */
    public List<String> getValuesByKeyAndDelimiter(String key, String delimiter) {
        List<String> list = new ArrayList<String>();
        String strValue = hmConfig.get(key);
        if (strValue == null || "".equals(strValue.trim())) {
            return list;
        } else {
            strValue = strValue.trim();
            String[] strValues = strValue.split(delimiter);
            for (String value : strValues) {
                list.add(value);
            }
            return list;
        }
    }

    /**
     * 
     * Get all keys values.
     * 
     * <br>
     * <b>Revision History</b><br>
     * <b>Comment:</b>
     * 
     * @return Map<String, String>
     * 
     * 
     */
    public Map<String, String> getAllKeysValues() {
        Map<String, String> allKeysValues = new HashMap<String, String>();
        allKeysValues = hmConfig;
        return allKeysValues;
    }
    
    public static String getPropertyValue(String prop,String propertyFilePath) {
        String value="";
        try {
            PropertyResourceBundle prBundle=(PropertyResourceBundle)PropertyResourceBundle.getBundle(propertyFilePath);
            byte[] tempvalue = null;
            tempvalue = prop.getBytes("GB2312");
            prop = new String(tempvalue,"ISO-8859-1");
            tempvalue = prBundle.getString(prop).getBytes("ISO-8859-1");
            value = new String(tempvalue,"GB2312");
            value.trim();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
