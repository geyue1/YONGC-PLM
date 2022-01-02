/**
 * ext.yongc.plm.part.forms.processors.PartMaterialLinkCommand.java
 * @Author yge
 * 2017年9月14日上午10:46:22
 * Comment 
 */
package ext.yongc.plm.part.forms.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTPropertyVetoException;
import wt.util.WTRuntimeException;

import com.ptc.cat.gxt.client.action.AdvancedAddAction;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.forms.FormResultAction;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.misc.NmContext;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.part.link.PartMaterialLink;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WCUtil;

public class PartMaterialLinkCommand {
	
	static Logger logger = LogR.getLogger(PartMaterialLinkCommand.class.getName());
	static ReferenceFactory rf = new ReferenceFactory();
	
	public static FormResult CreatePartMaterialLink(NmCommandBean commandBean) throws WTException, WTPropertyVetoException{
		logger.debug(" ----------------> enter CreatePartMaterialLink");
		boolean force = SessionServerHelper.manager.setAccessEnforced(false);
		  FormResult result=new FormResult(FormProcessingStatus.SUCCESS);
		WTPart part =(WTPart) commandBean.getActionOid().getRefObject();
		logger.debug("part ---------->"+IdentityFactory.getDisplayIdentifier(part));
		String id = rf.getReferenceString(part);AdvancedAddAction aa = null;
		String materailPartNumber = commandBean.getTextParameter("materialPartNumber");
		logger.debug("materailPartNumber--->"+materailPartNumber);
		WTPart materialPart = PartUtil.getPartByName(materailPartNumber);
		logger.debug("materialPart ---------->"+IdentityFactory.getDisplayIdentifier(materialPart));
		String SHULIANG = commandBean.getTextParameter(IBAConstants.SHULIANG);
		String LLDWDM = commandBean.getTextParameter(IBAConstants.LLDWDM);
		String LLDWMC = commandBean.getTextParameter(IBAConstants.LLDWMC);
		String GONGC = commandBean.getTextParameter(IBAConstants.GONGC);
		String JBJLDW = commandBean.getTextParameter(IBAConstants.JBJLDW);
		String MJSL = commandBean.getTextParameter(IBAConstants.MJSL);
		String GYLX = commandBean.getTextParameter(IBAConstants.GYLX);
		String SLDW = commandBean.getTextParameter(IBAConstants.SLDW);
		String BANBENHAO = commandBean.getTextParameter(IBAConstants.BANBENHAO);
		String FUZHU = commandBean.getTextParameter(IBAConstants.FUZHU);
		logger.debug("SHULIANG--->"+SHULIANG);
		logger.debug("LLDWDM--->"+LLDWDM);
		logger.debug("LLDWMC--->"+LLDWMC);
		logger.debug("GONGC--->"+GONGC);
		
		PartMaterialLink link = PartMaterialLink.newPartMaterialLink();
		link.setRoleAida2a2(id);
		link.setRoleBmasterida2a2(rf.getReferenceString(materialPart.getMaster()));
		link.setRoleAname(part.getName());
		link.setRoleAnumber(part.getNumber());
		link.setRoleBname(materialPart.getName());
		link.setRoleBnumber(materialPart.getNumber());
		PersistenceHelper.manager.save(link);
		IBAUtil iba = new IBAUtil(link);
		if(StringUtil.isNotEmpty(SHULIANG)){
			iba.setIBAAnyValue(link, IBAConstants.SHULIANG, SHULIANG);
		}
		if(StringUtil.isNotEmpty(LLDWDM)){
			iba.setIBAAnyValue(link, IBAConstants.LLDWDM, LLDWDM);
		}
		if(StringUtil.isNotEmpty(LLDWMC)){
			iba.setIBAAnyValue(link, IBAConstants.LLDWMC, LLDWMC);
		}
		if(StringUtil.isNotEmpty(GONGC)){
			iba.setIBAAnyValue(link, IBAConstants.GONGC, GONGC);
		}
		if(StringUtil.isNotEmpty(JBJLDW)){
			iba.setIBAAnyValue(link, IBAConstants.JBJLDW, JBJLDW);
		}
		if(StringUtil.isNotEmpty(MJSL)){
			iba.setIBAAnyValue(link, IBAConstants.MJSL, MJSL);
		}
		if(StringUtil.isNotEmpty(GYLX)){
			iba.setIBAAnyValue(link, IBAConstants.GYLX, GYLX);
		}
		if(StringUtil.isNotEmpty(SLDW)){
			iba.setIBAAnyValue(link, IBAConstants.SLDW, SLDW);
		}
		if(StringUtil.isNotEmpty(BANBENHAO)){
			iba.setIBAAnyValue(link, IBAConstants.BANBENHAO, BANBENHAO);
		}
		if(StringUtil.isNotEmpty(FUZHU)){
			iba.setIBAAnyValue(link, IBAConstants.FUZHU, FUZHU);
		}
		SessionServerHelper.manager.setAccessEnforced(force);
	WTMessage message = new WTMessage(
			null,
			"test", (Object[]) null);
//	FeedbackMessage msg = new FeedbackMessage(FeedbackType.SUCCESS,
//			(Locale) null, message, (ArrayList) null,
//			new LocalizableMessage[] { message });
	FeedbackMessage msg = new  FeedbackMessage(FeedbackType.SUCCESS, Locale.getDefault(), "", null, "原材料链接已成功创建");
   result.addFeedbackMessage(msg);
	

	result.setNextAction(FormResultAction.REFRESH_OPENER_AND_SUBMIT_IFRAMES);
	return result;
	
//		List list=commandBean.getSelected();
//		for(int i=0;i<list.size();i++){
//			NmContext ctext = (NmContext) list.get(i);
//			WTPart materialPart = (WTPart) ctext.getTargetOid().getWtRef().getObject();
//			
//			logger.debug("materialPart ---------------> "+IdentityFactory.getDisplayIdentifier(materialPart));
//			
//		}
	}
	
	public static FormResult DeletePartMaterialLink(NmCommandBean commandBean) throws  WTException{
		logger.debug(" ----------------> enter DeletePartMaterialLink");
		boolean force = SessionServerHelper.manager.setAccessEnforced(false);
		List list=commandBean.getSelected();
		
		for(int i=0;i<list.size();i++){
			NmContext ctext = (NmContext) list.get(i);
			
			PartMaterialLink link = (PartMaterialLink) ctext.getTargetOid().getWtRef().getObject();
			
			logger.debug("link ---------------> "+IdentityFactory.getDisplayIdentifier(link));
			
			PersistenceHelper.manager.delete(link);
			
		}
		SessionServerHelper.manager.setAccessEnforced(force);
		 FormResult result=new FormResult(FormProcessingStatus.SUCCESS);
		 FeedbackMessage msg = new  FeedbackMessage(FeedbackType.SUCCESS, Locale.getDefault(), "", null, "原材料链接已成功删除"); 
		 if(list==null || list.size()==0){
			  msg = new  FeedbackMessage(FeedbackType.SUCCESS, Locale.getDefault(), "", null, "没有选择原材料链接");
		 }
		
		   result.addFeedbackMessage(msg);
		   result.setExtraData(new HashMap());
		    logger.debug("-------------->"+result);
			return result;
	}
   public static FormResult EditPartMaterialLink(NmCommandBean commandBean) throws WTException{
	   logger.debug(" ------------> enter EditPartMaterialLink");
	   boolean force = SessionServerHelper.manager.setAccessEnforced(false);
	   String oid = commandBean.getTextParameter("linkoid");
	   logger.debug("oid---------->"+oid);
	   PartMaterialLink link = (PartMaterialLink) new ReferenceFactory().getReference(oid).getObject();
	   logger.debug(" link------------> "+link);
	   
	   String SHULIANG = commandBean.getTextParameter(IBAConstants.SHULIANG)==null?"":commandBean.getTextParameter(IBAConstants.SHULIANG);
		String LLDWDM = commandBean.getTextParameter(IBAConstants.LLDWDM)==null?"":commandBean.getTextParameter(IBAConstants.LLDWDM);
		String LLDWMC = commandBean.getTextParameter(IBAConstants.LLDWMC)==null?"":commandBean.getTextParameter(IBAConstants.LLDWMC);
		String GONGC = commandBean.getTextParameter(IBAConstants.GONGC)==null?"":commandBean.getTextParameter(IBAConstants.GONGC);
		String JBJLDW = commandBean.getTextParameter(IBAConstants.JBJLDW)==null?"":commandBean.getTextParameter(IBAConstants.JBJLDW);
		String MJSL = commandBean.getTextParameter(IBAConstants.MJSL)==null?"":commandBean.getTextParameter(IBAConstants.MJSL);
		String GYLX = commandBean.getTextParameter(IBAConstants.GYLX)==null?"":commandBean.getTextParameter(IBAConstants.GYLX);
		String SLDW = commandBean.getTextParameter(IBAConstants.SLDW)==null?"":commandBean.getTextParameter(IBAConstants.SLDW);
		String BANBENHAO = commandBean.getTextParameter(IBAConstants.BANBENHAO)==null?"":commandBean.getTextParameter(IBAConstants.BANBENHAO);
		String FUZHU = commandBean.getTextParameter(IBAConstants.FUZHU)==null?"":commandBean.getTextParameter(IBAConstants.FUZHU);
		logger.debug("SHULIANG--->"+SHULIANG);
		logger.debug("LLDWDM--->"+LLDWDM);
		logger.debug("LLDWMC--->"+LLDWMC);
		logger.debug("GONGC--->"+GONGC);
		
		IBAUtil iba = new IBAUtil(link);
			iba.setIBAAnyValue(link, IBAConstants.SHULIANG, SHULIANG);
			iba.setIBAAnyValue(link, IBAConstants.LLDWDM, LLDWDM);
			iba.setIBAAnyValue(link, IBAConstants.LLDWMC, LLDWMC);
			iba.setIBAAnyValue(link, IBAConstants.GONGC, GONGC);
			iba.setIBAAnyValue(link, IBAConstants.JBJLDW, JBJLDW);
			iba.setIBAAnyValue(link, IBAConstants.MJSL, MJSL);
			iba.setIBAAnyValue(link, IBAConstants.GYLX, GYLX);
			iba.setIBAAnyValue(link, IBAConstants.SLDW, SLDW);
			iba.setIBAAnyValue(link, IBAConstants.BANBENHAO, BANBENHAO);
			iba.setIBAAnyValue(link, IBAConstants.FUZHU, FUZHU);
			SessionServerHelper.manager.setAccessEnforced(force);
		 FormResult result=new FormResult(FormProcessingStatus.SUCCESS);
		 FeedbackMessage msg = new  FeedbackMessage(FeedbackType.SUCCESS, Locale.getDefault(), "", null, "原材料链接已成功编辑");
		   result.addFeedbackMessage(msg);
		   result.setExtraData(new HashMap());
		    logger.debug("-------------->"+result);
			return result;
   }
}
