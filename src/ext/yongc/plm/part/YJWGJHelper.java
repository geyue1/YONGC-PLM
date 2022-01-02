/**
 * ext.yongc.plm.part.YJWGJHelper.java
 * @Author yge
 * 2017年11月23日上午9:33:42
 * Comment 
 */
package ext.yongc.plm.part;

import wt.services.ServiceFactory;

public class YJWGJHelper {
	 public static final YJWGJService service = (YJWGJService)ServiceFactory.getService(YJWGJService.class);


	 public static YJWGJService getService(){
	    return service;
	  }
}
