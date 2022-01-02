/**
 * ext.yongc.plm.change.YJChangeHelper.java
 * @Author yge
 * 2017年8月15日上午9:17:49
 * Comment 
 */
package ext.yongc.plm.change;

import wt.services.ServiceFactory;

public class YJChangeHelper {
	 public static final YJChangeService service = (YJChangeService)ServiceFactory.getService(YJChangeService.class);


	 public static YJChangeService getService(){
	    return service;
	  }

}
