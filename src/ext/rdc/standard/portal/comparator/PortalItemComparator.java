package ext.rdc.standard.portal.comparator;

import java.util.Comparator;

import ext.rdc.standard.portal.bean.PortalItem;


public class PortalItemComparator implements Comparator<PortalItem>{
	@Override
	public int compare(PortalItem arg0, PortalItem arg1) {
		    String v1 = arg0.getDateString();
		    String v2 = arg1.getDateString();
		    int result = v2.compareTo(v1);
		    return result;
	}
}
