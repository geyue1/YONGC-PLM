/**
 * ext.yongc.plm.part.YJWGJService.java
 * @Author yge
 * 2017年11月23日上午9:30:49
 * Comment 
 */
package ext.yongc.plm.part;

import wt.fc.Persistable;
import wt.util.WTException;

public interface YJWGJService {
      public void cancelWGJ(Persistable pbo)throws WTException;
      public void setState(Persistable pbo,String state)throws WTException;
      public void moveWGJ(Persistable pbo,String folderPath)throws WTException;
      public String validateCheckOut(Persistable pbo)throws WTException;
}
