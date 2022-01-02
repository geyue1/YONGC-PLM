/**
 * ext.yongc.plm.util.ChangeUtil.java
 * @Author yge
 * 2017年8月10日下午5:16:55
 * Comment 
 */
package ext.yongc.plm.util;

import java.util.ArrayList;
import java.util.List;


import java.util.Vector;

import wt.change2.ChangeException2;
import wt.change2.ChangeHelper2;
import wt.change2.ChangeRecord2;
import wt.change2.Changeable2;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.util.WTException;

public class ChangeUtil {
    
	 /**
     * get all affected object from ECN
     * 
     * @param ecn
     * @return
     * @throws ChangeException2
     * @throws WTException
     */
    public static List<WTObject> getChangeBefore(WTChangeOrder2 ecn) throws ChangeException2, WTException {
        List<WTObject> list = new ArrayList<WTObject>();
        QueryResult result = ChangeHelper2.service.getChangeActivities(ecn);
        while (result.hasMoreElements())
        {
            WTChangeActivity2 activity = (WTChangeActivity2) result.nextElement();
            QueryResult res = ChangeHelper2.service.getChangeablesBefore(activity);
            while (res.hasMoreElements())
            {
                WTObject object = (WTObject) res.nextElement();
                list.add(object);
            }
        }
        return list;
    }
    
    /**
     * get all resulting object from ECN
     * 
     * @param ecn
     * @return
     * @throws WTException
     */
    public static List<WTObject> getChangeAfter(WTChangeOrder2 ecn) throws WTException
    {
        List<WTObject> list = new ArrayList<WTObject>();
        QueryResult result = ChangeHelper2.service.getChangeActivities(ecn);
        while (result.hasMoreElements())
        {
            WTChangeActivity2 activity = (WTChangeActivity2) result.nextElement();
            QueryResult res = ChangeHelper2.service.getChangeablesAfter(activity);
            while (res.hasMoreElements())
            {
                WTObject object = (WTObject) res.nextElement();
                list.add(object);
            }
        }
        return list;
    }
    /**
     * add Changeable After To ChangeNotice
     * 
     * @param cn
     * @param changeable
     * @param caName
     * @return
     * @throws WTException
     * @throws
     */
    public static WTChangeOrder2 addChangeableAfterToChangeNotice(WTChangeOrder2 cn, List<Changeable2> changeables,
            String caName) throws WTException
    {
        QueryResult qr = ChangeHelper2.service.getChangeActivities(cn);
        WTChangeActivity2 toBeAddedCA = null;
        while (qr.hasMoreElements())
        {
            WTChangeActivity2 ca = (WTChangeActivity2) qr.nextElement();
            if (caName == null || caName.equals(""))
            {
                toBeAddedCA = ca;
                break;
            } else
            {
                if (caName.trim().equals(ca.getName()))
                {
                    toBeAddedCA = ca;
                    break;
                }
            }
            toBeAddedCA = ca;
        }
        if (toBeAddedCA != null)
        {
            ChangeHelper2.service.storeAssociations(ChangeRecord2.class, toBeAddedCA,
                    CollectionUtil.toVector(changeables));
        }
        cn = (WTChangeOrder2) PersistenceHelper.manager.refresh(cn);
        return cn;
    }
    
    /**
     * @param changeOrder
     * @throws WTException
     */
    public static void deleteChangeAfter(WTChangeOrder2 changeOrder) throws WTException
    {
        QueryResult ecas = ChangeHelper2.service.getChangeActivities(changeOrder);
        while (ecas.hasMoreElements())
        {
            WTChangeActivity2 eca = (WTChangeActivity2) ecas.nextElement();
            QueryResult afterItems = ChangeHelper2.service.getChangeablesAfter(eca);
            while (afterItems.hasMoreElements())
            {
                WTObject object = (WTObject) afterItems.nextElement();
                QueryResult qrt = PersistenceHelper.manager.find(ChangeRecord2.class, eca,
                        ChangeRecord2.CHANGE_ACTIVITY2_ROLE, object);
                Vector<Persistable> vector = new Vector<Persistable>();
                if (qrt.hasMoreElements())
                {
                    vector = qrt.getObjectVector().getVector();
                    for (int i = 0; i < vector.size(); i++)
                    {
                        PersistenceHelper.manager.delete((Persistable) vector.get(i));
                    }
                }
            }
        }
    }
}
