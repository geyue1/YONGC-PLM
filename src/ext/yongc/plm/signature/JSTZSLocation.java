/**
 * ext.yongc.plm.signature.JSTZSLocation.java
 * @Author yge
 * 2017年9月19日下午2:58:23
 * Comment 
 */
package ext.yongc.plm.signature;

import ext.yongc.plm.constants.SigConstants;

public class JSTZSLocation {
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
		   }else if(SigConstants.SIG_HUIQIAN.equals(str)){
			   //会签签字
			   location = "F5";
		   }else if(SigConstants.DATE_HUIQIAN.equals(str)){
			   //会签签字日期
			   location = "G5";
		   }else if(SigConstants.SIG_Standardized.equals(str)){
			   //标准化签字
			   location = "H2";
		   }else if(SigConstants.DATE_Standardized.equals(str)){
			   //标准化签字日期
			   location = "G6";
		   }else if(SigConstants.SIG_Reviewed.equals(str)){
			   //审核签字
			   location = "H3";
		   }else if(SigConstants.DATE_Reviewed.equals(str)){
			   //审核签字日期
			   location = "G7";
		   }else if(SigConstants.SIG_Approved.equals(str)){
			   //批准签字
			   location = "H4";
		   }else if(SigConstants.DATE_Approved.equals(str)){
			   //批准签字日期
			   location = "G8";
			   return location;
		   }
		   
		   
		   return location;
	   }
}
