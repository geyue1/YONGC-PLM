/**
 * ext.yongc.plm.signature.ChangeListLocation.java
 * @Author yge
 * 2017年12月14日下午1:58:19
 * Comment 
 */
package ext.yongc.plm.signature;

import ext.yongc.plm.constants.SigConstants;

public class ChangeListLocation {
	public static String getSignatureLocation(String str){
		   String location = "";
		   if(SigConstants.SIG_Designed.equals(str)){
			   //设计签字
			   location = "E1";
		   }else if(SigConstants.DATE_Designed.equals(str)){
			   //设计签字日期
			   location = "K3";
		   }else if(SigConstants.SIG_Checked.equals(str)){
			   //校核签字
			   location = "E2";
		   }else if(SigConstants.SIG_ChiefDesigner.equals(str)){
			   //主管签字
			   location = "E3";
		   }else if(SigConstants.SIG_JY.equals(str)){
			   //绝缘签字
			   location = "E4";
		   }else if(SigConstants.SIG_HUIQIAN.equals(str)){
			   //会签签字
			   location = "K6";
		   }else if(SigConstants.SIG_Standardized.equals(str)){
			   //标准化签字
			   location = "G6";
		   }else if(SigConstants.SIG_Reviewed.equals(str)){
			   //审核签字
			   location = "G5";
		   }else if(SigConstants.SIG_Approved.equals(str)){
			   //批准签字
			   location = "G7";
		   }else if(SigConstants.DATE_Approved.equals(str)){
			   //批准签字日期
			   location = "K4";
			   return location;
		   }else if(SigConstants.SIG_DE.equals(str)){
			   location = "E5";
			   return location;
		   }else if(SigConstants.SIG_GJG.equals(str)){
			   location = "E6";
			   return location;
		   }else if(SigConstants.SIG_GJGHJ.equals(str)){
			   location = "E7";
			   return location;
		   }else if(SigConstants.SIG_JJ.equals(str)){
			   location = "G2";
			   return location;
		   }else if(SigConstants.SIG_BX.equals(str)){
			   location = "G3";
			   return location;
		   }
		   
		   
		   return location;
	   }
}
