/**
 * ext.yongc.plm.signature.ZYDFJLocation.java
 * @Author yge
 * 2017年9月21日上午11:30:15
 * Comment 
 */
package ext.yongc.plm.signature;

import ext.yongc.plm.constants.SigConstants;

public class ZYDFJLocation {
	  public static String getSignatureLocation(String str){
		   String location = "";
		   if(SigConstants.SIG_Designed.equals(str)){
			   //设计签字
			   location = "G2";
		   }else if(SigConstants.DATE_Designed.equals(str)){
			   //设计签字日期
			   location = "H2";
		   }else if(SigConstants.SIG_Checked.equals(str)){
			   //校核签字
			   location = "G3";
		   }else if(SigConstants.DATE_Checked.equals(str)){
			   //校核签字日期
			   location = "H3";
		   }else if(SigConstants.SIG_ChiefDesigner.equals(str)){
			   //主管签字
			   location = "G4";
		   }else if(SigConstants.DATE_ChiefDesigner.equals(str)){
			   //主管签字日期
			   location = "H4";
		   }else if(SigConstants.SIG_HUIQIAN.equals(str)){
			   //会签签字
			   location = "G5";
		   }else if(SigConstants.DATE_HUIQIAN.equals(str)){
			   //会签签字日期
			   location = "H5";
		   }else if(SigConstants.SIG_Standardized.equals(str)){
			   //标准化签字
			   location = "G6";
		   }else if(SigConstants.DATE_Standardized.equals(str)){
			   //标准化签字日期
			   location = "H6";
		   }else if(SigConstants.SIG_Reviewed.equals(str)){
			   //审核签字
			   location = "G7";
		   }else if(SigConstants.DATE_Reviewed.equals(str)){
			   //审核签字日期
			   location = "H7";
		   }else if(SigConstants.SIG_Approved.equals(str)){
			   //批准签字
			   location = "G8";
		   }else if(SigConstants.DATE_Approved.equals(str)){
			   //批准签字日期
			   location = "H8";
			   return location;
		   }
		   
		   
		   return location;
	   }
}
