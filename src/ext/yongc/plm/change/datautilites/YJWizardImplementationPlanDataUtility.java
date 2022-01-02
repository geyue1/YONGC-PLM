/**
 * ext.yongc.plm.change.datautilites.WizardImplementationPlanDataUtility.java
 * @Author yge
 * 2017年8月14日上午11:02:12
 * Comment 
 */
package ext.yongc.plm.change.datautilites;

import java.util.List;

import org.apache.log4j.Logger;

import wt.change2.ChangeActivity2;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.components.descriptor.ColumnDescriptor;
import com.ptc.core.components.descriptor.ColumnDescriptorFactory;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.netmarkets.model.NmSimpleOid;
import com.ptc.netmarkets.util.misc.NmAction;
import com.ptc.netmarkets.util.misc.NmActionServiceHelper;
import com.ptc.windchill.enterprise.change2.ChangeManagementClientHelper;
import com.ptc.windchill.enterprise.change2.beans.ImplementationPlanDataUtilityBean;
import com.ptc.core.components.factory.dataUtilities.NmActionDataUtility;
import com.ptc.core.ui.resources.ComponentMode;

public class YJWizardImplementationPlanDataUtility extends AbstractDataUtility{
	 NmActionDataUtility actionsDataUtility;
	 Logger logger = LogR.getLogger(YJWizardImplementationPlanDataUtility.class.getName());
	 
	/* (non-Javadoc)
	 * @see com.ptc.core.components.descriptor.DataUtility#getDataValue(java.lang.String, java.lang.Object, com.ptc.core.components.descriptor.ModelContext)
	 */
	@Override
	public Object getDataValue(String arg0, Object arg1, ModelContext arg2)
			throws WTException {
		 ImplementationPlanDataUtilityBean bean = new ImplementationPlanDataUtilityBean(arg0, arg1, arg2);
		 if (arg0!=null && arg0.equals("changeTask_YJnmIconActions")){
			 String objecttype = arg2.getDescriptor().getProperty("objectType", "yjchange");
			 String actionName = "";
			 ComponentMode componentMode = ChangeManagementClientHelper.getMode(bean.getNmCommandBean());
			 logger.debug("componentMode-------->"+componentMode);
			 logger.debug("componentMode-------->"+bean.getDatum());
			 if ((ComponentMode.CREATE.equals(componentMode)) || ((bean.getDatum() instanceof NmSimpleOid))) {
			       actionName = arg2.getDescriptor().getProperty("actionName", "editOnCreateChangeTask");
			    }else if(bean.getDatum() instanceof ChangeActivity2){
			    	ChangeActivity2 ca = (ChangeActivity2)bean.getDatum();
			    	 actionName = arg2.getDescriptor().getProperty("actionName", "editChangeTask");
			    }
			
			  NmAction  action = NmActionServiceHelper.service.getAction(objecttype, actionName, bean.getLocale());
			  System.out.println("***action -------->"+action);
			 return action;
		 }
		       System.out.println("************ null");
		return null;
	}
	 public String getLabel(String paramString, ModelContext paramModelContext)
			    throws WTException{
			    if ("changeTask_YJnmIconActions".equals(paramString)){
			      return WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.change2.change2ClientResource", "IMPLEMENTATION_PLAN_TABLE_ICON_ACTIONS", null, paramModelContext.getLocale());
			    }
			    return super.getLabel(paramString, paramModelContext);
			  }
	 
	 public ColumnDescriptor getColumnDescriptor(String paramString, ModelContext paramModelContext)
	  {
	    ColumnDescriptor localColumnDescriptor = super.getColumnDescriptor(paramString, paramModelContext);

	    if ((paramString.equals("changeTask_type_icon")) || (paramString.equals("changeTask_YJnmIconActions")))
	    {
	      localColumnDescriptor = ColumnDescriptorFactory.getInstance().newIconColumn(paramModelContext.getDescriptor());
	    }
	    return localColumnDescriptor;
	  }
	 
	 public void setModelData(String paramString, List<?> paramList, ModelContext paramModelContext)
			    throws WTException
			  {
			    if(paramString!=null && paramString.equals("changeTask_YJnmIconActions")){
			    	 this.actionsDataUtility = new NmActionDataUtility();
				      this.actionsDataUtility.setModelData(paramString, paramList, paramModelContext);
			    }
			  }
}
