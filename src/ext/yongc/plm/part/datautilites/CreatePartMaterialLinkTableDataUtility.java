/**
 * ext.yongc.plm.part.datautilites.CreatePartMaterialLinkTableDataUtility.java
 * @Author yge
 * 2017年9月13日下午5:13:29
 * Comment 
 */
package ext.yongc.plm.part.datautilites;

import org.apache.log4j.Logger;

import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.HTMLGuiComponent;
import com.ptc.core.components.rendering.guicomponents.NumberInputComponent;
import com.ptc.core.components.rendering.guicomponents.TextArea;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.part.link.PartMaterialLink;
import ext.yongc.plm.util.IBAUtil;

public class CreatePartMaterialLinkTableDataUtility extends AbstractDataUtility{
	
	Logger logger = LogR.getLogger(CreatePartMaterialLinkTableDataUtility.class.getName());

	/* (non-Javadoc)
	 * @see com.ptc.core.components.descriptor.DataUtility#getDataValue(java.lang.String, java.lang.Object, com.ptc.core.components.descriptor.ModelContext)
	 */
	@Override
	public Object getDataValue(String arg0, Object arg1, ModelContext arg2)
			throws WTException {
		logger.debug("arg0---------->"+arg0);
		logger.debug("arg1---------->"+arg1);
//		boolean force = SessionServerHelper.manager.setAccessEnforced(false);
		if(arg1 instanceof PartMaterialLink){
			PartMaterialLink link = (PartMaterialLink)arg1;
			IBAUtil iba = new IBAUtil(link);
			 if(IBAConstants.SHULIANG.equals(arg0)){
					return iba.getIBAValue(IBAConstants.SHULIANG);
			 }else if(IBAConstants.LLDWDM.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.LLDWDM);
			 }else if(IBAConstants.LLDWMC.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.LLDWMC);
			 }else if(IBAConstants.GONGC.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.GONGC);
			 }else if(IBAConstants.JBJLDW.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.JBJLDW);
			 }else if(IBAConstants.MJSL.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.MJSL);
			 }else if(IBAConstants.GYLX.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.GYLX);
			 }else if(IBAConstants.SLDW.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.SLDW);
			 }else if(IBAConstants.BANBENHAO.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.BANBENHAO);
			 }else if(IBAConstants.FUZHU.equals(arg0)){
				 return iba.getIBAValue(IBAConstants.FUZHU);
			 }
		}
//		SessionServerHelper.manager.setAccessEnforced(force);
		return null;
	}
}
