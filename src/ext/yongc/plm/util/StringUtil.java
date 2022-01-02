/**
 * ext.yongc.plm.util.StringUtil.java
 * @Author yge
 * 2017年7月5日上午10:54:44
 * Comment 
 */
package ext.yongc.plm.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class StringUtil extends StringUtils {
    private static final String DECIMAL_FORMAT = "0.000";
    private static DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);

    /**
     * Get list from tokens, method <code>split()</code> is not used here for the reason that the argument is
     * <code>Regular Expression</code>.
     * 
     * @param src
     *            string source
     * @param split
     *            split
     * @return list of tokens
     * @see java.util.List
     */
    public static List<String> getListFromTokens(String src, String split) {
        List<String> tokenList = null;
        if (isNotEmpty(src) && isNotEmpty(split)) {
            String[] strArray = StringUtils.split(src, split);
            tokenList = Arrays.asList(strArray);
        }
        return tokenList;
    }

    /**
     * Get index of the given string from the given string array
     * 
     * @param array
     *            string array
     * @param str
     *            string
     * @return index
     */
    public static int getIndex(String[] array, String str) {
        int index = -1;
        if (array != null && str != null) {
            int size = array.length;
            String temp;
            for (int i = 0; i < size; i++) {
                temp = array[i];
                if (str.equals(temp)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * Filter invalid character
     * 
     * @param str
     *            string source
     * @return new string
     */
    public static String filterInvalidChar(String str) {
        String newStr = null;
        char[] newArray = null;
        if (!isEmpty(str)) {
            int length = str.length();
            newArray = new char[length];
            int codePointCount = str.codePointCount(0, length);
            int codePoint;
            for (int i = 0, j = 0; i < codePointCount; j++) {
                codePoint = str.codePointAt(i);
                if (Character.isSupplementaryCodePoint(codePoint)) {
                    i += 2;
                } else {
                    i++;
                }
                newArray[j] = (char) codePoint;
            }
            newStr = new String(newArray);
        }

        return newStr;
    }

    /**
     * compare version ,need be: XX
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static int compareVersion(String str1, String str2) {

        int leg1 = str1.length();
        int leg2 = str2.length();
        if (leg1 > leg2) {
            return 1;
        } else if (leg1 == leg2) {
            int asc1 = 0;
            int asc2 = 0;
            for (int i = 0; i < leg1; i++) {
                asc1 = +str1.charAt(i);
                asc2 = +str2.charAt(i);
                if (asc1 > asc2) {
                    return 1;
                } else if (asc1 == asc2) {
                    continue;
                } else {
                    return -1;
                }
            }
        } else {
            return -1;
        }

        return 0;
    }

    /**
     * compare full version ,need be : XX.XX or XX.XX.XX
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static int compareFullVersion(String str1, String str2) {

        String reg = "[,.\\s++]";
        String[] version1 = str1.split(reg);
        String[] version2 = str2.split(reg);
        if (compareVersion(version1[0], version2[0]) > 0) {
            return 1;
        } else if (compareVersion(version1[0], version2[0]) == 0) {
            if (compareVersion(version1[1], version2[1]) > 0) {
                return 1;
            } else if (compareVersion(version1[1], version2[1]) == 0) {
                if (version1.length > 2 || version2.length > 2) {
                    if (version1.length > 2 && version2.length > 2) {
                        return compareVersion(version1[2], version2[2]);
                    } else if (version1.length > 2) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    return 0;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static String removeInternalBlank(String s) {
        // LOGGER.debug("bb:" + s);
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        char str[] = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == ' ') {
                sb.append(' ');
            } else {
                break;
            }
        }
        String after = m.replaceAll("");
        StringUtils.remove(s, ' ');
        return sb.toString() + after;
    }

    /**
     * trim all white space
     * 
     * @param str
     * @return
     */
    public static String trimAllWhitespace(String str) {

        return StringUtils.remove(str, ' ');
    }

    public static String format3thDecimal(String num) {
        String result = "";
        if (!isEmpty(num)) {
            format.setRoundingMode(RoundingMode.DOWN);
            result = format.format(Double.parseDouble(num));
        }
        return result;
    }
    
    public static boolean isEmpty(String str){
    	if(str==null || "".equals(str.trim())){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isNotEmpty(String str){
    	return !isEmpty(str);
    }

    /**
     * constructor
     */
    public StringUtil() {
    }

}
