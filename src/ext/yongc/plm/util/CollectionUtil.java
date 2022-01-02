/**
 * ext.yongc.plm.util.A.java
 * @Author yge
 * 2017年8月15日上午9:49:15
 * Comment 
 */
package ext.yongc.plm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * The class <code>com.lenovo.plm.midh.common.util.CollectionUtil</code>
 * is a utility class for <code>java.util.Collection</code>.
 * @author yge
 */
public class CollectionUtil {

    /**
     * constructor
     */
    private CollectionUtil() {
        
    }
    
    /**
     * Convert list to a map from the index map just like <"1", value>, <"2", value>...
     *
     * @param list     list
     * @param indexMap index map
     * @return new map
     */
    public static Map<Object, Object> convertListFromIndexMap(List<String> list, Map<String, String> indexMap) {
        Map<Object, Object> newMap = new HashMap<Object, Object>();
        int listSize = list.size();
        int keySize = indexMap.keySet().size();
        int loopSize = Math.max(listSize, keySize);
        Object key;
        Object value;
        String strIndex;
        for (int i = 0; i < loopSize; i++) {
            strIndex = String.valueOf(i);
            key = indexMap.get(strIndex);
            if (key != null) {
                if (i < listSize) {
                    value = list.get(i);
                } else {
                    value = "";
                }
                newMap.put(key, value);
            }
        }
        return newMap;
    }
    
    /**
     * Convents a list to Vector instance to apply some  windchill instances.
     *
     * @param list a list
     * @return a vector instance.
     */
    public static <T> Vector<T> toVector(Set<T> set) {//NOPMD BY PETER
        Vector<T> result = new Vector<T>(); 
        if (set != null) {
            result.addAll(set);
        }
        return result;
    }

    /**
     * Convents a list to Vector instance to apply some  windchill instances.
     *
     * @param list a list
     * @return a vector instance.
     */
    public static <T> Vector<T> toVector(List<T> list) {//NOPMD BY PETER
        Vector<T> result = new Vector<T>(); 
        if (list != null) {
            result.addAll(list);
        }
        return result;
    }

    /**
     * Convents a list to ArrayList instance to apply some  windchill instances.
     *
     * @param list a list
     * @return an ArrayList instance.
     */
    public static List<Object> toArrayList(List<Object> list) { 
        if (list instanceof ArrayList) {
            return (ArrayList<Object>) list;
        }

        List<Object> result = new ArrayList<Object>();
        if (list != null) {
            result.addAll(list);
        }
        return result;
    }

    /**
     * Convents a map to HashMap instance to apply some windchill instances.
     *
     * @param map the given map
     * @return an HashMap instance.
     */
    public static <T> HashMap<T,T> toHashMap(Map<T,T> map) {//NOPMD BY PETER 
        HashMap<T,T> result = new HashMap<T,T>();
        if (map instanceof HashMap) {
            result = (HashMap<T,T>)map;
        }
        if (map != null) {
            result.putAll(map);
        }
        return result;
    }
    
    /**
     * Convents a map to HashMap instance to apply some windchill instances.
     *
     * @param map the given map
     * @return an HashMap instance.
     */
    public static <T> Hashtable<T,T> toHashtable(Map<T,T> map) {//NOPMD BY PETER 
        Hashtable<T,T> result = new Hashtable<T,T>();//NOPMD BY PETER 
        if (map instanceof HashMap && map != null) {
            result.putAll(map);
        }
        return result;
    }

    /**
     * Judge if the given list contains all the elements in the given array
     *
     * @param list  list
     * @param array array
     * @return is contain or not
     */
    public static boolean containsAll(List<String> list, String[] array) {
        boolean isContainsAll = true;
        if (list != null && array != null && list.size() >= array.length) {
            int size = array.length;
            String str;
            for (int i = 0; i < size; i++) {
                str = array[i];
                if (!list.contains(str)) {
                    isContainsAll = false;
                    break;
                }
            }
        } else {
            isContainsAll = false;
        }
        return isContainsAll;
    }

    /**
     * Remove duplicate and convert to set
     *
     * @param array array
     * @return set
     */
    public static Set<Object> convertArrayToSet(Object[] array) {
        Set<Object> set = new HashSet<Object>();
        if (array != null) {
            int size = array.length;
            Object obj;
            for (int i = 0; i < size; i++) {
                obj = array[i];
                set.add(obj);
            }
        }
        return set;
    }
    
}
