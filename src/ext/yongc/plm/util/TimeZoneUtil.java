/**
 * ext.yongc.plm.util.A.java
 * @Author yge
 * 2017年7月9日下午11:34:14
 * Comment 
 */
package ext.yongc.plm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import wt.log4j.LogR;


public class TimeZoneUtil {

    /**
     * Constant for China date format
     */
    public static final String CHINA_DATE_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";

    public static final String CHINA_DATE_FORMAT_HYPHEN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Constant for Western date format
     */
    public static final String WESTERN_DATE_FORMAT_SLASH = "MM/dd/yyyy HH:mm:ss";

    public static final String WESTERN_DATE_FORMAT_HYPHEN = "MM-dd-yyyy HH:mm:ss";

    /**
     * Constant for Default time
     */
    public static final String DEFAULT_TIME = "00:00:00";

    private static Logger logger = LogR.getLogger(TimeZoneUtil.class.getName());


    /**
     * constructor
     */
    private TimeZoneUtil() {
    }
    
    /**
     * check string as date type
     * @param inputDate
     * @param dateFormat
     * @return
     */
    public static boolean checkDate(String inputDate, String dateFormat) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            sdf.setLenient(false);
            sdf.parse(inputDate);
            result = true;
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage(),e);
            result = false;
        }

        return result;
    }

    /**
     * parse string to date format
     * @param input
     * @return
     */
    public static String parseDate(String input) {
        String result = input;

        ArrayList<String> arrayDateFormat = new ArrayList<String>();
        arrayDateFormat.add("yyyy/M/d HH:mm:ss");
        arrayDateFormat.add("yyyy-MM-dd HH:mm:ss");
        arrayDateFormat.add("yyyy/MM/dd HH:mm:ss");
        arrayDateFormat.add("MM/dd/yyyy HH:mm:ss");
        arrayDateFormat.add("yyyy/M/d");
        arrayDateFormat.add("yyyy-MM-dd");
        arrayDateFormat.add("yyyy/MM/dd");
        arrayDateFormat.add("MM/dd/yyyy");

        Date dateStandard = null;
        SimpleDateFormat sdfStandard = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            dateStandard = sdfStandard.parse(input);
            return input;
        } catch (ParseException e) {
            for (int i = 0; i < arrayDateFormat.size(); i++) {
                if (checkDate(input, arrayDateFormat.get(i))) {
                    SimpleDateFormat sdfTemp = new SimpleDateFormat(arrayDateFormat.get(i));
                    try {
                        dateStandard = sdfTemp.parse(input);
                    } catch (ParseException e1) {
                        Log.error(e1.getMessage());
                    }
                    break;
                }
            }
        }

        result = sdfStandard.format(dateStandard);
        return result;
    }
    
    /**
     * parse string to date format ,need time zone
     * @param dateStr
     * @param timezone
     * @return
     */
    public static Date parseDateFromString(String dateStr,String timezone) {
        if(dateStr == null || "".equals(dateStr)){
            return null;
        }
        
        ArrayList<String> arrayDateFormat = new ArrayList<String>();
        arrayDateFormat.add("yyyy/MM/dd HH:mm:ss");
        arrayDateFormat.add("yyyy-MM-dd HH:mm:ss");
        arrayDateFormat.add("MM/dd/yyyy HH:mm:ss");
        arrayDateFormat.add("MM-dd-yyyy HH:mm:ss");
        arrayDateFormat.add("yyyy/MM/dd");
        arrayDateFormat.add("yyyy-MM-dd");
        arrayDateFormat.add("MM/dd/yyyy");
        arrayDateFormat.add("MM-dd-yyyy");

        Date date = null;
        SimpleDateFormat sdfStandard = new SimpleDateFormat("yyyy/MM/dd");
        sdfStandard.setTimeZone(TimeZone.getTimeZone(timezone));
        try {
            date = sdfStandard.parse(dateStr);
            return date;
        } catch (ParseException e) {
            for (int i = 0; i < arrayDateFormat.size(); i++) {
                if (checkDate(dateStr, arrayDateFormat.get(i))) {
                    SimpleDateFormat sdfTemp = new SimpleDateFormat(arrayDateFormat.get(i));
                    sdfTemp.setTimeZone(TimeZone.getTimeZone(timezone));
                    try {
                        date = sdfTemp.parse(dateStr);
                        break;
                    } catch (ParseException e1) {
                        Log.error(e1.getMessage());
                    }
                }
            }
        }

        return date;
    }

    /**
     * get current standard time 
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sdfStandard = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdfStandard.format(new Date());
        
    }

}

