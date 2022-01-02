/**
 * ext.yongc.plm.change.YJChangeService.java
 * @Author yge
 * 2017年8月15日上午9:17:04
 * Comment 
 */
package ext.yongc.plm.change;

import java.util.List;

import wt.fc.Persistable;
import wt.method.RemoteInterface;
import wt.util.WTException;


@RemoteInterface
public interface YJChangeService {
      public void reviseAffectedObjects(Persistable pbo) throws WTException;
      public void setResultObjectsState(Persistable pbo,String state) throws WTException;
      public void removeResultObjectsIBA(Persistable pbo) throws WTException;
      public void cancelECN(Persistable pbo)throws WTException;
      public String checkGongYiType(Persistable pbo)throws WTException;
      public boolean getGongyiTypeNode(Persistable pbo,String str)throws WTException;
      public boolean isAllObjectsPassed(Persistable pbo)throws WTException ;
      public boolean hasReturnDesign(Persistable pbo)throws WTException ;
  	 public boolean hasReturnGongYi(Persistable pbo)throws WTException ;
 	public String validateHasRolePrincioal(Persistable pbo,List<String> roleList) throws WTException;
 	public String validateCheckOut(Persistable pbo)throws WTException;
 	public boolean getGongyiTypeNode(Persistable pbo,String str,boolean isMBOM)throws WTException;
 	public void signature(Persistable pbo, String signatureRole,String signatureDate) throws WTException;
 	public void released3DChangeNotice(Persistable pbo)throws WTException;
}
