/**
 * ext.yongc.plm.change.StandardYJChangeService.java
 * @Author yge
 * 2017年8月15日上午9:19:15
 * Comment 
 */
package ext.yongc.plm.change;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifierHelper;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.windchill.enterprise.part.commands.PartDocServiceCommand;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.IBAValueConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.signature.SignatureHelpler;
import ext.yongc.plm.util.ChangeUtil;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PromotionUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WCUtil;
import ext.yongc.plm.util.WorkflowUtil;
import wt.change2.WTChangeOrder2;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.epm.build.EPMBuildRule;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.identity.IdentityFactory;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.log4j.LogR;
import wt.maturity.PromotionNotice;
import wt.part.WTPart;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionServerHelper;
import wt.team.Team;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;

public class StandardYJChangeService extends StandardManager implements YJChangeService{
	private static final long serialVersionUID = 1L;
    private static Logger log = LogR.getLogger(StandardYJChangeService.class.getName());
    
    public static StandardYJChangeService newStandardYJChangeService()
            throws WTException {
    	StandardYJChangeService instance = new StandardYJChangeService();
        instance.initialize();
        return instance;
   }

	/* (non-Javadoc)
	 * @see ext.yongc.plm.change.YJChangeService#reviseAffectedObjects(wt.fc.Persistable)
	 */
	@Override
	public void reviseAffectedObjects(Persistable pbo) throws WTException {
		log.debug(" enter reviseAffectedObjects ------------->");
		WTChangeOrder2 order = null;
		if(pbo instanceof WTChangeOrder2){
			order = (WTChangeOrder2)pbo;
		}
		if(order == null){
			log.debug(" pbo is not WTChangeOrder2, exit reviseAffectedObjects ");
			return;
		}
		boolean forced = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(order).getTypename();
			List list = ChangeUtil.getChangeBefore(order);
			List temp = new ArrayList();
			Object obj = null;
			for(int i=0;i<list.size();i++){
				obj = list.get(i);
				if(type.indexOf(TypeConstants.ECN_3DDesign)>-1 && (obj instanceof EPMDocument)){
					EPMDocument epm = (EPMDocument)obj;
					log.debug(" before revise epm---------->"+IdentityFactory.getDisplayIdentifier(epm));
					 QueryResult qr=PersistenceHelper.manager.navigate(epm, EPMBuildRule.BUILD_TARGET_ROLE, EPMBuildRule.class);
					
					 
					 EPMDocument epm2 = (EPMDocument)VersionControlHelper.service.newVersion(epm);
					epm2 = (EPMDocument)PersistenceHelper.manager.save(epm2);
					 while(qr.hasMoreElements()){
						 WTPart part = (WTPart)qr.nextElement();
						 log.debug("part---------->"+IdentityFactory.getDisplayIdentifier(part));
							part = (WTPart)WCUtil.doCheckOut(part);
							EPMBuildRule rule = getEPMBuildRule(epm, part);
							if(rule!=null){
								PersistenceHelper.manager.delete(rule);
							}
							
						 EPMBuildRule rule2 = EPMBuildRule.newEPMBuildRule(epm2, part);
						 rule2 = (EPMBuildRule) PersistenceHelper.manager
									.save(rule2);
						 rule2 = (EPMBuildRule) PersistenceHelper.manager
									.refresh(rule2);
						 part = (WTPart)WCUtil.doCheckIn(part,"关联EPMDocument "+epm.getName());
					 }
					temp.add(epm2);
				}else if(type.indexOf(TypeConstants.ECN_MBOM)>-1 && (obj instanceof WTPart)){
					WTPart part = (WTPart)obj;
					log.debug(" before revise part---------->"+IdentityFactory.getDisplayIdentifier(part));
					 QueryResult qr=PersistenceHelper.manager.navigate(part, EPMBuildRule.BUILD_SOURCE_ROLE, EPMBuildRule.class);
					part = (WTPart)VersionControlHelper.service.newVersion(part);
					part = (WTPart)PersistenceHelper.manager.save(part);
					//part = (WTPart)WCUtil.doCheckOut(part);
					 while(qr.hasMoreElements()){
						 EPMDocument epm = (EPMDocument)qr.nextElement();
						 log.debug("epm---------->"+IdentityFactory.getDisplayIdentifier(epm));
						// EPMBuildRule rule = EPMBuildRule.newEPMBuildRule(epm, part);
						// rule = (EPMBuildRule) PersistenceHelper.manager
						//			.save(rule);
						// rule = (EPMBuildRule) PersistenceHelper.manager
						//			.refresh(rule);
					 }
					//part = (WTPart)WCUtil.doCheckIn(part);
					 temp.add(part);
				}else if(obj instanceof WTDocument ){
					WTDocument doc = (WTDocument)obj;
					doc = (WTDocument)VersionControlHelper.service.newVersion(doc);
					doc = (WTDocument)PersistenceHelper.manager.save(doc);
					temp.add(doc);
				}
			}
			if(temp.size()>0){
				ChangeUtil.addChangeableAfterToChangeNotice(order, temp, null);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (WTPropertyVetoException e) {
			e.printStackTrace();
		}finally{
			SessionServerHelper.manager.setAccessEnforced(forced);
		}
		
		
		log.debug(" exit reviseAffectedObjects ------------->");
	}

	/* (non-Javadoc)
	 * @see ext.yongc.plm.change.YJChangeService#setResulObjectsState(wt.fc.Persistable)
	 */
	@Override
	public void setResultObjectsState(Persistable pbo,String state) throws WTException {
		log.debug(" enter setResulObjectsState ------------->");
		WTChangeOrder2 order = null;
		if(pbo instanceof WTChangeOrder2){
			order = (WTChangeOrder2)pbo;
		}
		if(order == null){
			return;
		}
		List list = ChangeUtil.getChangeAfter(order);
		log.debug("list size ------->"+list.size());
		Object obj = null;
		for(int i=0;i<list.size();i++){
			obj = list.get(i);
			  if(obj instanceof Persistable){
			 LifeCycleManaged p = (LifeCycleManaged) obj;
			 log.debug(p.getState().toString());
			  if(p.getState().toString().equalsIgnoreCase("RELEASED")){
   			   continue;
   		   }
       		   LifeCycleHelper.service.setLifeCycleState(p, State.toState(state));
       		   PersistenceHelper.manager.refresh(p);
       	   }
		}
		log.debug(" exit setResulObjectsState ------------->");
	}

	/* (non-Javadoc)
	 * @see ext.yongc.plm.change.YJChangeService#removeResultObjectsIBA(wt.fc.Persistable)
	 */
	@Override
	public void removeResultObjectsIBA(Persistable pbo) throws WTException {
		log.debug(" enter  removeResultObjectsIBA ------------->");
		WTChangeOrder2 order = null;
		if(pbo instanceof WTChangeOrder2){
			order = (WTChangeOrder2)pbo;
		}
		if(order == null){
			return;
		}
		List list = ChangeUtil.getChangeAfter(order);
		Object obj = null;
		for(int i=0;i<list.size();i++){
			obj = list.get(i);
			if(obj instanceof EPMDocument){
				EPMDocument epm = (EPMDocument)obj;
				 IBAUtil iba= new IBAUtil(epm);
				  iba.removeIBAValue(epm, IBAConstants.GONGYI_TYPE);
				  iba.removeIBAValue(epm, IBAConstants.VOTE);
			}else if(obj instanceof WTPart){
				WTPart part = (WTPart)obj;
				  IBAUtil iba= new IBAUtil(part);
				  iba.removeIBAValue(part, IBAConstants.VOTE);
			}
		}
		log.debug(" exit  removeResultObjectsIBA ------------->");
	}

	/* (non-Javadoc)
	 * @see ext.yongc.plm.change.YJChangeService#cancelECN(wt.fc.Persistable)
	 */
	@Override
	public void cancelECN(Persistable pbo) throws WTException {
		log.debug(" enter  cancelECN ------------->");
		WTChangeOrder2 order = null;
		if(pbo instanceof WTChangeOrder2){
			order = (WTChangeOrder2)pbo;
		}
		if(order == null){
			return;
		}
		boolean forced = SessionServerHelper.manager.setAccessEnforced(false);
		List list = ChangeUtil.getChangeAfter(order);
		ChangeUtil.deleteChangeAfter(order);
		Object obj = null;
		for(int i=0;i<list.size();i++){
			obj = list.get(i);
			if(obj instanceof Persistable){
				PersistenceHelper.manager.delete((Persistable)obj);
				log.debug(" ------ delete ---------------");
			}
		}
		SessionServerHelper.manager.setAccessEnforced(forced);
		log.debug(" exit  cancelECN ------------->");
	}
	/**
	 *   检查工艺分配，如果文件没有分配，则提示用户
	 * @Author yge
	 * 2017年7月10日下午12:35:24
	 */
	 public String checkGongYiType(Persistable pbo)throws WTException{
		 log.debug(" --------- enter  checkGongYiType ---------------------");		
		 StringBuffer errorMsg = new StringBuffer();
			WTChangeOrder2 order = null;
			if(pbo instanceof WTChangeOrder2){
				order = (WTChangeOrder2)pbo;
			}
			if(order == null){
				return "";
			}
			List list = ChangeUtil.getChangeAfter(order);
		 Object obj = null;
		 String type = null;
		 for(int i=0;i<list.size();i++){
			 obj = list.get(i);
			 if(obj instanceof EPMDocument){
				 EPMDocument epm = (EPMDocument)obj;
				 IBAUtil iba= new IBAUtil(epm);
				 type = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
				 if(StringUtil.isEmpty(type)){
					 errorMsg.append("文件["+epm.getNumber()+" , "+epm.getName()+"] 没有设置工艺审核类型 ！\n");
				 }
			 }
		 }
		 
		 log.debug(" --------- exit  checkGongYiType ---------------------"+errorMsg.toString());
		 return errorMsg.toString();
	 }
	 public boolean getGongyiTypeNode(Persistable pbo,String str)throws WTException{
		  log.debug(" --------- enter  getGongyiTypeNode ---------------------");
			WTChangeOrder2 order = null;
			if(pbo instanceof WTChangeOrder2){
				order = (WTChangeOrder2)pbo;
			}
			if(order == null){
				return false;
			}
			List list = ChangeUtil.getChangeAfter(order);
			 Object obj = null;
			 String type = null;
			 for(int j=0;j<list.size();j++){
				 obj = list.get(j);
				 if(obj instanceof EPMDocument){
					 EPMDocument epm = (EPMDocument)obj;
					 IBAUtil iba= new IBAUtil(epm);
				
					 type = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
					 if(StringUtil.isNotEmpty(type)){
						 String[] array = type.split(",");
						 for(int i=0;i<array.length;i++){
							 type = array[i];
							 if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("钢结构焊接") && "gangjiegouhanjie".equalsIgnoreCase(str)){
									return true;
									 
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("钢结构") && "gangjiegou".equalsIgnoreCase(str)){
									 return true;
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("机加") && "jijia".equalsIgnoreCase(str)){
									 return true;
							
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("布线") && "buxian".equalsIgnoreCase(str)){
									 return true;
									
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("组装") && "zuzhuang".equalsIgnoreCase(str)){
									 return true;
									
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("表面") &&"biaomian".equalsIgnoreCase(str)){
									 return true;
									 
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("绝缘工艺") &&"jueyuangongyi".equalsIgnoreCase(str)){
									 return true;
								
								 }
						 }
						 
					 }
					
				 }
			 }
			 log.debug(" --------- exit  getGongyiTypeNode ---------------------");
		  return false;
	  }
	 /**
	   * 
	   * @description 判断工艺分配
	   * @param promotion
	   * @param str  工艺分配类型
	   * @param isMBOM 应用于MBOM维护流程 true
	   * @return
	   * @throws WTException
	   * @author yge  2017年7月19日上午9:30:56
	   */
	  public boolean getGongyiTypeNode(Persistable pbo,String str,boolean isMBOM)throws WTException{
		  log.debug(" --------- enter getGongyiTypeNode --------------");
		  WTChangeOrder2 order = null;
			if(pbo instanceof WTChangeOrder2){
				order = (WTChangeOrder2)pbo;
			}
			if(order == null){
				return false;
			}
			List list = ChangeUtil.getChangeAfter(order);
			 Object obj = null;
			 String type = null;
			 WTPart part = null;
			 for(int j=0;j<list.size();j++){
				 obj = list.get(j);
				 if(obj instanceof WTPart){
					 part  = (WTPart)obj;
					 log.debug("part ----------->"+IdentityFactory.getDisplayIdentifier(part));
					 IBAUtil iba= new IBAUtil(part);
						
					 type = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
					 if(StringUtil.isNotEmpty(type)){
						 String[] array = type.split(",");
						 for(int i=0;i<array.length;i++){
							 type = array[i];
							 if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("钢结构焊接") && "gangjiegouhanjie".equalsIgnoreCase(str)){
									return true;
									 
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("钢结构") && "gangjiegou".equalsIgnoreCase(str)){
									 return true;
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("机加") && "jijia".equalsIgnoreCase(str)){
									 return true;
							
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("布线") && "buxian".equalsIgnoreCase(str)){
									 return true;
									
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("组装") && "zuzhuang".equalsIgnoreCase(str)){
									 return true;
									
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("表面") &&"biaomian".equalsIgnoreCase(str)){
									 return true;
									 
								 }else  if(!StringUtil.isEmpty(type) && type.equalsIgnoreCase("绝缘工艺") &&"jueyuangongyi".equalsIgnoreCase(str)){
									 return true;
								
								 }
						 }
					 }
					 }
				 }
			 log.debug(" --------- exit getGongyiTypeNode --------------");
		  return false;
	  }
	 
		public boolean isAllObjectsPassed(Persistable pbo)
				throws WTException {
			
			 log.debug(" --------- enter  isAllObjectsPassed ---------------------");
				Boolean flag = true;
//				 Object obj = null;
				 WfProcess process = WorkflowUtil.getProcessByPBO(pbo);
			 List<WorkItem> workItemList = WorkflowUtil.getAllNotCompleteWorkItem(process);
			 for(int i=0;i<workItemList.size();i++){
				 WorkItem item = workItemList.get(i);
				 WfAssignedActivity wffa = (WfAssignedActivity)item.getSource().getObject();
				 if(WorkflowConstants.ACTIVITY_biaomian.equals(wffa.getName())){
					 flag = false;
					  break;
				 }else if(WorkflowConstants.ACTIVITY_buxian.equals(wffa.getName())){
					 flag = false;
					  break;
				 }else if(WorkflowConstants.ACTIVITY_gangjiegou.equals(wffa.getName())){
					 flag = false;
					  break;
				 }else if(WorkflowConstants.ACTIVITY_gangjiegouhanjie.equals(wffa.getName())){
					 flag = false;
					  break;
				 }else if(WorkflowConstants.ACTIVITY_jijia.equals(wffa.getName())){
					 flag = false;
					  break;
				 }else if(WorkflowConstants.ACTIVITY_jueyuangongyi.equals(wffa.getName())){
					 flag = false;
					  break;
				 }else if(WorkflowConstants.ACTIVITY_zuzhuang.equals(wffa.getName())){
					 flag = false;
					  break;
				 }
			 }
			  log.debug(" --------- exit  isAllObjectsPassed ---------------------"+flag);
			return flag;
		}
		public boolean hasReturnDesign(Persistable pbo)
				throws WTException {
			 log.debug(" --------- enter  hasReturnDesign ---------------------");
				Boolean flag = false;
				WTChangeOrder2 order = null;
				if(pbo instanceof WTChangeOrder2){
					order = (WTChangeOrder2)pbo;
				}
				if(order == null){
					return flag;
				}
				List list = ChangeUtil.getChangeAfter(order);
				 Object obj = null;
				 for(int j=0;j<list.size();j++){
					 obj = list.get(j);
				  if(obj instanceof EPMDocument){
					  EPMDocument epm = (EPMDocument)obj;
					  IBAUtil iba= new IBAUtil(epm);
					  String vote = iba.getIBAValue(epm, IBAConstants.VOTE);
					  if(StringUtil.isNotEmpty(vote)&& vote.contains(IBAValueConstants.VOTE_RETURN_DESIGN)){
						  flag = true;
						  break;
					  }
				  }
			  }
			  log.debug(" --------- exit  hasReturnDesign ---------------------"+flag);
			return flag;
		}
		public boolean hasReturnGongYi(Persistable pbo)
				throws WTException {
			 log.debug(" --------- enter  hasReturnDesign ---------------------");
				Boolean flag = false;
				WTChangeOrder2 order = null;
				if(pbo instanceof WTChangeOrder2){
					order = (WTChangeOrder2)pbo;
				}
				if(order == null){
					return flag;
				}
				List list = ChangeUtil.getChangeAfter(order);
				 Object obj = null;
				 for(int j=0;j<list.size();j++){
					 obj = list.get(j);
				  if(obj instanceof EPMDocument){
					  EPMDocument epm = (EPMDocument)obj;
					  IBAUtil iba= new IBAUtil(epm);
					  String vote = iba.getIBAValue(epm, IBAConstants.VOTE);
					  if(StringUtil.isNotEmpty(vote) && vote.contains(IBAValueConstants.VOTE_RETURN_GONGYI)){
						  flag = true;
						  break;
					  }
				  }
			  }
			  log.debug(" --------- exit  hasReturnDesign ---------------------"+flag);
			return flag;
		}
		
		public String validateHasRolePrincioal(Persistable pbo,
				List<String> roleList) throws WTException {
			log.debug(" --------------- enter validateHasRolePrincioal ----------------");
			WTChangeOrder2 order = null;
			if(pbo instanceof WTChangeOrder2){
				order = (WTChangeOrder2)pbo;
			}
			if(order == null){
				return "";
			}
			StringBuffer msg = new StringBuffer();
			try {
				WfProcess process = WorkflowUtil.getProcessByPBO(order);
				Team team = (Team) process.getTeamId().getObject();
				Map map = team.getRolePrincipalMap();
				for(int i=0;i<roleList.size();i++){
					String role = roleList.get(i);
					log.debug(" role is ------------>"+role);
					for(Object obj : map.keySet()){
						String key = (String)obj.toString();
						if(key.equals(role))
						{
							List value = (List)map.get(obj);
							if(value!=null && value.size()==0){
								msg.append(validateHasRoleErrorMsg(role)+"!\n");
								break;
							}
						
						}
					}
				}
			} catch (WTException e) {
				return "";
			}
			log.debug(" --------------- exit validateHasRolePrincioal ----------------"+msg.toString());
			return msg.toString();
		}
		private String validateHasRoleErrorMsg(String role){
			
			if("YJDJ_Auditing".equalsIgnoreCase(role)){
				return "请设置审核人员";
			}else if("YJDJ_Approval".equalsIgnoreCase(role)){
				return "请设置批准人员";
			}else if("YJDJ_Standardization".equalsIgnoreCase(role)){
				return "请设置标准化人员";
			}else if("YJDJ_check".equalsIgnoreCase(role)){
				return "请设置校核人员";
			}else if("YJDJ_Supervisor_design".equalsIgnoreCase(role)){
				return "请设置主管设计人员";
			}else if("YJDJ_LCC_countersign".equalsIgnoreCase(role)){
				return "请设置可靠性会签人员";
			}
			
			return "";
		}
		public String validateCheckOut(Persistable pbo)throws WTException {
			 log.debug(" ---------- enter validateCheckOut ---------");
			 StringBuffer msg = new StringBuffer(); 
			 WTChangeOrder2 order = null;
				if(pbo instanceof WTChangeOrder2){
					order = (WTChangeOrder2)pbo;
				}
				if(order == null){
					return "";
				}
				List list = ChangeUtil.getChangeAfter(order);
			 Object obj = null;
			 WTDocument doc = null;
			 EPMDocument epm = null;
			 WTPart part = null;
			 for(int i=0;i<list.size();i++){
				  obj = list.get(i);
				  if(obj instanceof Workable){
					  if( WorkInProgressHelper.isCheckedOut((Workable)obj) || 
							  WorkInProgressHelper.isWorkingCopy((Workable)obj)){
						  String name="";
						  String number="";
						  String type="";
						  TypeIdentifier identifier = TypeIdentifierHelper.getType(obj);
						  try {
							type = TypedUtilityServiceHelper.service.getLocalizedTypeName(identifier, Locale.CHINA);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						  if(obj instanceof WTDocument){
								 doc = (WTDocument)obj;
//								 type = doc.getDisplayType().toString();
								 name = doc.getName();
								 number = doc.getNumber();
							 }else if(obj instanceof WTPart){
								 part = (WTPart)obj;
//								 type = part.getDisplayType().toString();
								 name = part.getName();
								 number = part.getNumber();
							 }else if(obj instanceof EPMDocument){
								 epm = (EPMDocument)obj;
//								 type = epm.getDisplayType().toString();
								 name = epm.getName();
								 number = epm.getNumber();
							 }
						
						  msg.append("[ "+type+" , 编号 "+number+" , 名称 "+name+"] \n");
					  }
				  }
			 }
			 
			 if(StringUtil.isNotEmpty(msg.toString())){
				 msg.append("以上对象被检出，请检入后再完成任务！");
			 }
			 log.debug(" ---------- exit validateCheckOut ---------"+msg.toString());
			return msg.toString();
		}
		public void signature(Persistable pbo, String signatureRole,
				String signatureDate) throws WTException {
			log.debug(" enter signature");
			
			SignatureHelpler helper = new SignatureHelpler(pbo);
			helper.signature(signatureRole, signatureDate);
			
			log.debug(" exit signature");
		}

		/* (non-Javadoc)
		 * 将三维文件上的工艺分配设置到对应的部件上
		 * @see ext.yongc.plm.change.YJChangeService#released3DChangeNotice(wt.fc.Persistable)
		 */
		@Override
		public void released3DChangeNotice(Persistable pbo) throws WTException {
			 log.debug(">>>>>>>>>>>>>>>>>> enter released3DPromotionNotice");
			  try {
				  WTChangeOrder2 order = null;
					if(pbo instanceof WTChangeOrder2){
						order = (WTChangeOrder2)pbo;
					}
					if(order == null){
						return ;
					}
				
				String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(order).getTypename();
				if(type.indexOf(TypeConstants.ECN_3DDesign)>-1){
					List list = ChangeUtil.getChangeAfter(order);
					 Object obj = null;
					 for(int i=0;i<list.size();i++){
						 obj = list.get(i);
						 if(obj instanceof EPMDocument){
							 EPMDocument epm = (EPMDocument) obj;
							 IBAUtil iba = new IBAUtil(epm);
							 String gongyi = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
							 QueryResult qr=PersistenceHelper.manager.navigate(epm, EPMBuildRule.BUILD_TARGET_ROLE, EPMBuildRule.class);
						      log.debug(" qr------------->"+qr.size());
						      while(qr.hasMoreElements()){
						    	  obj =qr.nextElement();
						    	  if(obj instanceof WTPart){
						    		  WTPart part = (WTPart)obj;
						    		  log.debug(IdentityFactory.getDisplayIdentifier(part));
						    		  part = (WTPart) WCUtil.doCheckOut(part);
						    		  iba = new IBAUtil(part);
						    		  iba.setIBAAnyValue(part, IBAConstants.GONGYI_TYPE, gongyi);
						    		  part = (WTPart) WCUtil.doCheckIn(part, "设置工艺分配属性值为:"+gongyi);
						    	  }
						      }
						 }
					 }
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			  
			 log.debug(" >>>>>>>>>>>>>>>>exit released3DPromotionNotice");
			
		}
		private EPMBuildRule getEPMBuildRule(EPMDocument epm,WTPart part){
			long identifier_epm =epm.getBranchIdentifier();
			long identifier_part = part.getBranchIdentifier();
					
			QuerySpec qs;
			try {

				qs = new QuerySpec(EPMBuildRule.class);
				qs.appendWhere(new SearchCondition(EPMBuildRule.class,
						"roleAObjectRef.key.branchId", SearchCondition.EQUAL,
						identifier_epm));

				qs.appendAnd();
				qs.appendWhere(new SearchCondition(EPMBuildRule.class,
						"roleBObjectRef.key.branchId", SearchCondition.EQUAL,
						identifier_part));
				//System.out.println(qs);
				QueryResult qr = PersistenceHelper.manager.find(qs);
				while (qr.hasMoreElements()) {
					Object object = (Object) qr.nextElement();
					if (object instanceof EPMBuildRule) {
						return (EPMBuildRule) object;
					}
				}
			} catch (QueryException e) {
				e.printStackTrace();
			} catch (WTException e) {
				e.printStackTrace();
			}
			return null;
		}
}
