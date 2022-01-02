/**
 * ext.yongc.plm.promotion.YJPromotionHelpler.java
 * @Author yge
 * 2017年7月7日下午12:48:30
 * Comment 
 */
package ext.yongc.plm.promotion;


import wt.services.ServiceFactory;

public class YJPromotionHelpler {
	
	 public static final YJPromotionService service = (YJPromotionService)ServiceFactory.getService(YJPromotionService.class);


	 public static YJPromotionService getService(){
	    return service;
	  }

}
