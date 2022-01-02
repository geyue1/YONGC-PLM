/**
 * ext.solr.test.TestC.java
 * @Author yge
 * 2020年8月28日下午3:04:58
 * Comment 
 */
package ext.solr.test;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import wt.feedback.StatusFeedback;
import wt.httpgw.GatewayAuthenticator;
import wt.intersvrcom.SysInfo;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;

import com.ptc.core.lwc.client.commands.LWCCommands;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.ConstraintRuleDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.StandardBaseDefinitionService;


public class TestC2  implements RemoteAccess{
	 
   public static void test(String attrId) throws WTException{
	   AttributeDefinitionReadView rv1 = (AttributeDefinitionReadView)LWCCommands.getReadViewObject(attrId);
//	   TypeDefinitionReadView tdrv = LWCCommands.getAttributeContainingType(rv1);
	   Set set = StandardBaseDefinitionService.newStandardBaseDefinitionService().getAllConstraintRuleDefViews();
//	   Set set = StandardBaseDefinitionService.newStandardBaseDefinitionService().getAllConstraintRuleDefViewsForAttributeTemplate(tdrv);
	   
	   for (Iterator it = set.iterator(); it.hasNext(); ){
		   ConstraintRuleDefinitionReadView rule = (ConstraintRuleDefinitionReadView)it.next();
		   // logger.debug("...checking this rule: " + ((ConstraintRuleDefinitionReadView)localObject3).getRuleClassname() + ", datatype: " + ((ConstraintRuleDefinitionReadView)localObject3).getDatatype().getName());
		   MethodContext.getContext().sendFeedback(new StatusFeedback(" this rule:"+rule.getRuleClassname()+",datatype:"+rule.getDatatype().getName()));
	       
		   String id = rule.getId().toString();
		   String displayName =  PropertyHolderHelper.getDisplayName(rule, Locale.CHINESE);
		   MethodContext.getContext().sendFeedback(new StatusFeedback(" id:"+id+",displayName:"+displayName));
	      System.out.println(" this rule:"+rule.getRuleClassname()+",datatype:"+rule.getDatatype().getName());
	      System.out.println(" id:"+id+",displayName:"+displayName);
	      System.out.println("*********************************************");
	   }
   }
   public static void main(String[] args){
 		RemoteMethodServer rms = RemoteMethodServer.getDefault();
 		GatewayAuthenticator auth = new GatewayAuthenticator();
 		auth.setRemoteUser("wcadmin");
 		rms.setAuthenticator(auth);
		Class[] classes = { String.class };
		Object[] objs = { args[0]};

		try {
			rms.invoke("test", TestC2.class.getName(), null, classes, objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
  }
}
