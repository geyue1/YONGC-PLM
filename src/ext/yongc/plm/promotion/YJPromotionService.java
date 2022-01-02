/**
 * ext.yongc.plm.promotion.YJPromotionService.java
 * @Author yge
 * 2017年7月7日下午12:33:09
 * Comment 
 */
package ext.yongc.plm.promotion;

import java.util.List;

import wt.fc.Persistable;
import wt.maturity.PromotionNotice;
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.util.WTException;


@RemoteInterface
public interface YJPromotionService {
       public void setPromotionTargetState(PromotionNotice promotion, String state) throws WTException;
       public String checkGongYiType(PromotionNotice promotion)throws WTException;
       public boolean getGongyiTypeNode(PromotionNotice promotion,String str)throws WTException;
       public boolean isAllObjectsPassed(PromotionNotice promotion)throws WTException;
       public void removePromotionTargetIBA(PromotionNotice promotion)throws WTException;
       public boolean hasReturnGongYi(PromotionNotice promotion)throws WTException;
       public boolean hasReturnDesign(PromotionNotice promotion)throws WTException;
       public List<WTPart>  getMBOMParts(List<WTPart> list)throws WTException;
       public boolean getGongyiTypeNode(PromotionNotice promotion,String str,boolean isMBOM)throws WTException;
       public String validateCheckOut(PromotionNotice promotion)throws WTException;
       public String validateHasRolePrincioal(PromotionNotice promotion,List<String> roleList)throws WTException;
       public void signature(Persistable pbo,String signatureRole,String signatureDate)throws WTException;
       public void released3DPromotionNotice(PromotionNotice promotion)throws WTException;
}
