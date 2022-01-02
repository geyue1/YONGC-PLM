/**
 * ext.yongc.plm.signature.TYMLLocation.java
 * @Author yge
 * 2017年9月13日上午10:35:50
 * Comment 
 */
package ext.yongc.plm.signature;

import ext.yongc.plm.constants.SigConstants;

public class TYMLLocation {
	 public static String getSignatureLocation(String str){
		   String location = "";
		   if(SigConstants.SIG_Designed.equals(str)){
			   //设计签字
			   location = "F2";
		   }else if(SigConstants.DATE_Designed.equals(str)){
			   //设计签字日期
			   location = "G2";
		   }else if(SigConstants.SIG_Checked.equals(str)){
			   //校核签字
			   location = "F3";
		   }else if(SigConstants.DATE_Checked.equals(str)){
			   //校核签字日期
			   location = "G3";
		   }else if(SigConstants.SIG_ChiefDesigner.equals(str)){
			   //主管签字
			   location = "F4";
		   }else if(SigConstants.DATE_ChiefDesigner.equals(str)){
			   //主管签字日期
			   location = "G4";
		   }else if(SigConstants.SIG_Standardized.equals(str)){
			   //标准化签字
			   location = "F5";
		   }else if(SigConstants.DATE_Standardized.equals(str)){
			   //标准化签字日期
			   location = "G5";
		   }
		   
		   
		   return location;
	   }
}
