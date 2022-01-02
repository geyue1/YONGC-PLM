/**
 * ext.rdc.standard.util.RefineSearchBeanComparator.java
 * @Author yge
 * 2019年9月6日下午5:39:52
 * Comment 
 */
package ext.rdc.standard.util;

import java.util.Comparator;

import com.ptc.windchill.partslink.model.RefineBean;

public class RefineSearchBeanComparator implements Comparator<RefineBean> {

    @Override
    public int compare(RefineBean bean1, RefineBean bean2) {
        int result = bean1.getAttrDisplayName().compareTo(bean2.getAttrDisplayName());
        if (result == 0) {
            result = bean1.getAttrId().compareTo(bean2.getAttrId());
        }
        return result;
    }

}
