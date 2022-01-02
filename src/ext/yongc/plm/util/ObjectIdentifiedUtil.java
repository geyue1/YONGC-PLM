/**
 * ext.yongc.plm.util.ObjectIdentifiedUtil.java
 * @Author yge
 * 2017年8月21日下午10:56:32
 * Comment 
 */
package ext.yongc.plm.util;

import java.lang.reflect.Method;

import wt.fc.ReferenceFactory;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.vc.Iterated;
import wt.vc._IterationInfo;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine._WfProcess;

public class ObjectIdentifiedUtil {
	public static void setCreator(Iterated obj ,WTUser urer) throws Exception{
		try {
			ReferenceFactory rf = new ReferenceFactory();
			WTPrincipalReference uref =(WTPrincipalReference) rf.getReference(urer);
			Class[] pp = new Class[] { WTPrincipalReference.class };
			Method setCreator = _IterationInfo.class.getDeclaredMethod("setCreator", pp);
			setCreator.setAccessible(true);
			setCreator.invoke(obj.getIterationInfo(), new Object[] { uref });
		} catch (Exception e) {
			// System.out.println(">>>Error:" + e.toString());
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void setCreator(WfProcess process ,WTPrincipalReference uref) throws Exception{
		try {
		
			Class[] pp = new Class[] { WTPrincipalReference.class };
			Method setCreator = _WfProcess.class.getDeclaredMethod("setCreator", pp);
			setCreator.setAccessible(true);
			setCreator.invoke(process, new Object[] { uref });
		} catch (Exception e) {
			// System.out.println(">>>Error:" + e.toString());
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void setModifier(Iterated obj, WTPrincipalReference uref) throws Exception {
			try {
				obj.getCreator();
				Class[] pp = new Class[] { WTPrincipalReference.class };
				Method setCreator = _IterationInfo.class.getDeclaredMethod("setModifier", pp);
				setCreator.setAccessible(true);
				setCreator.invoke(obj.getIterationInfo(), new Object[] { uref });
			} catch (Exception e) {
				// System.out.println(">>>Error:" + e.toString());
				e.printStackTrace();
				throw e;
			}

		}
}
