/**
 * ext.yongc.plm.promotion.StandardYJPromotionService.java
 * @Author yge
 * 2017年7月7日下午12:35:24
 * Comment 
 */
package ext.yongc.plm.promotion;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.PromotionUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WCUtil;
import ext.yongc.plm.util.WorkflowUtil;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentHelper;
import wt.epm.build.EPMBuildRule;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.iba.value.StringValue;
import wt.identity.IdentityFactory;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.log4j.LogR;
import wt.maturity.PromotionNotice;
import wt.part.WTPart;
import wt.part.WTPartConfigSpec;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.WTPartStandardConfigSpec;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.team.Team;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;

public class StandardYJPromotionService extends StandardManager implements YJPromotionService{
	
	private static final long serialVersionUID = 1L;
    private static Logger log = LogR.getLogger(StandardYJPromotionService.class.getName());
    
    
    public static StandardYJPromotionService newStandardYJPromotionService()
            throws WTException {
    	StandardYJPromotionService instance = new StandardYJPromotionService();
      instance.initialize();
      return instance;
   }
    
	/* (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#setPromotionTargetState(java.lang.String)
	 */
	@Override
	public void setPromotionTargetState(PromotionNotice promotion, String state) throws WTException {
           log.debug(" --------- enter setPromotionTargetState ---------------------");		
           log.debug(" promotion ------->"+IdentityFactory.getDisplayIdentifier(promotion));
           log.debug("state--------------->"+state);
           
           if(StringUtil.isEmpty(state)){
        	   throw new WTException(" 生命周期状态为空 ");
           }
           List list = PromotionUtil.getLatestPromotionTargets(promotion);
           LifeCycleManaged p =null;
           for(int i=0;i<list.size();i++){
        	   Object obj = list.get(i);
        	   if(obj instanceof Persistable){
        		   p = (LifeCycleManaged) obj;
        		   if(p.getState().toString().equalsIgnoreCase("RELEASED")){
        			   continue;
        		   }
        		   LifeCycleHelper.service.setLifeCycleState(p, State.toState(state));
        		   PersistenceHelper.manager.refresh(p);
        	   }
           }
           log.debug(" --------- exit setPromotionTargetState ---------------------");		
	}
	
	/**
	 *   检查工艺分配，如果文件没有分配，则提示用户
	 * @Author yge
	 * 2017年7月10日下午12:35:24
	 */
	 public String checkGongYiType(PromotionNotice promotion)throws WTException{
		 log.debug(" --------- enter  checkGongYiType ---------------------");		
		 StringBuffer errorMsg = new StringBuffer();
		 List list = PromotionUtil.getLatestPromotionTargets(promotion);
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
		 
//		 StringBuffer msg = new StringBuffer();
//		 if(!StringUtil.isEmpty(errorMsg.toString())){
//			 msg.append("<font size=3 color=red>");
//			 msg.append(errorMsg);
//			 msg.append("</font>");
//		 }
		 
		 log.debug(" --------- exit  checkGongYiType ---------------------"+errorMsg.toString());
		 return errorMsg.toString();
	 }
	 /**
		 *   检查工艺分配，如果文件没有分配，则提示用户
		 *   str
		 * @Author yge
		 * 2017年7月11日下午10:13:20
		 */
	  public boolean getGongyiTypeNode(PromotionNotice promotion,String str)throws WTException{
		  log.debug(" --------- enter  getGongyiTypeNode ---------------------");
	
		  List list = PromotionUtil.getLatestPromotionTargets(promotion);
			 Object obj = null;
			 String type = null;
			 for(int k=0;k<list.size();k++){
				 obj = list.get(k);
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
	  public boolean getGongyiTypeNode(PromotionNotice promotion,String str,boolean isMBOM)throws WTException{
		  log.debug(" --------- enter getGongyiTypeNode --------------");
		  List list = PromotionUtil.getLatestPromotionTargets(promotion);
			 Object obj = null;
			 String type = null;
			 WTPart part = null;
			 for(int k=0;k<list.size();k++){
				 obj = list.get(k);
				 if(obj instanceof WTPart){
					 part  = (WTPart)obj;
					 log.debug("part ----------->"+IdentityFactory.getDisplayIdentifier(part));
					 IBAUtil iba= new IBAUtil(part);
					 type = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
					 log.debug("gongyi--------->"+type);
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

	/**判读 钢结构焊接 钢结构 布线 组装 机加 表面 绝缘工艺任务都是否已经完成
	 *  
	 *  (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#isAllObjectsPassed(wt.maturity.PromotionNotice)
	 */
	@Override
	public boolean isAllObjectsPassed(PromotionNotice promotion)
			throws WTException {
		
		 log.debug(" --------- enter  isAllObjectsPassed ---------------------");
			Boolean flag = true;
//			 Object obj = null;
			 WfProcess process = WorkflowUtil.getProcessByPBO(promotion);
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
//		  List list = PromotionUtil.getLatestPromotionTargets(promotion);
//		  for(int i=0;i<list.size();i++){
//			  obj = list.get(i);
//			  if(obj instanceof EPMDocument){
//				  EPMDocument epm = (EPMDocument)obj;
//				  IBAUtil iba= new IBAUtil(epm);
//				  String vote = iba.getIBAValue(epm, IBAConstants.VOTE);
//				  log.debug(" epm vote----------->"+vote);
//				  if(StringUtil.isEmpty(vote)){
//					  flag = false;
//					  break;
//				  }
//			  }else if(obj instanceof WTPart){
//				  WTPart part = (WTPart)obj;
//				  IBAUtil iba= new IBAUtil(part);
//				  String vote = iba.getIBAValue(part, IBAConstants.VOTE);
//				  log.debug("part ---------------->"+IdentityFactory.getDisplayIdentifier(part));
//				  log.debug(" part vote----------->"+vote);
//				  if(StringUtil.isEmpty(vote)){
//					  flag = false;
//					  break;
//				  }
//			  }
//		  }
		  log.debug(" --------- exit  isAllObjectsPassed ---------------------"+flag);
		return flag;
	}

	/* (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#removePromotionTargetIBA(wt.maturity.PromotionNotice)
	 */
	@Override
	public void removePromotionTargetIBA(PromotionNotice promotion)
			throws WTException {
		log.debug(" --------- enter removePromotionTargetIBA -------------");
		 Object obj = null;
		  List list = PromotionUtil.getLatestPromotionTargets(promotion);
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
		  log.debug(" --------- end removePromotionTargetIBA -------------");
	}

	/* (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#hasReturnGongYi(wt.maturity.PromotionNotice)
	 */
	@Override
	public boolean hasReturnGongYi(PromotionNotice promotion)
			throws WTException {
		 log.debug(" --------- enter  hasReturnDesign ---------------------");
			Boolean flag = false;
			 Object obj = null;
		  QueryResult qr = PromotionUtil.getPromotionTargets(promotion);
		  while(qr.hasMoreElements()){
			  obj = qr.nextElement();
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

	/* (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#hasReturnDesign(wt.maturity.PromotionNotice)
	 */
	@Override
	public boolean hasReturnDesign(PromotionNotice promotion)
			throws WTException {
		 log.debug(" --------- enter  hasReturnDesign ---------------------");
			Boolean flag = false;
			 Object obj = null;
		  List list = PromotionUtil.getLatestPromotionTargets(promotion);
		  for(int i=0;i<list.size();i++){
			  obj = list.get(i);
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

	/* (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#getMBOMParts(java.util.List)
	 */
	@Override
	public List<WTPart> getMBOMParts(List<WTPart> list) throws WTException {
		WTPartStandardConfigSpec standardConfig = WTPartStandardConfigSpec.newWTPartStandardConfigSpec();
		standardConfig = WTPartStandardConfigSpec.newWTPartStandardConfigSpec();
        WTPartConfigSpec config = WTPartConfigSpec.newWTPartConfigSpec(standardConfig);
		
    	List temp = new ArrayList();
    	
		Persistable[][][] allChildren = WTPartHelper.service.getUsesWTParts(new WTArrayList(list), config);
		for(ListIterator i = list.listIterator();i.hasNext();){
			WTPart parent = (WTPart)i.next();
			Persistable[][] oneParentNode = allChildren[i.previousIndex()];
			if(oneParentNode==null){
				continue;
			}else{
				List children = new ArrayList(oneParentNode.length);
				for(int j=0;j<oneParentNode.length;j++){
					Object obj = oneParentNode[j][1];
					if(obj instanceof WTPart){
						children.add(obj);
					}else if(obj instanceof WTPartMaster){
						WTPartMaster master = (WTPartMaster)obj;
						children.add(PartUtil.getLatestPartByMaster(master));
					}
					
				}
				temp.addAll(getMBOMParts(children));
			}
		}
		list.addAll(temp);
		return list;
	}

	/* 验证升级目标对象中是否有对象检出
	 * (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#validateCheckOut(wt.maturity.PromotionNotice)
	 */
	@Override
	public String validateCheckOut(PromotionNotice promotion)
			throws WTException {
		 log.debug(" ---------- enter validateCheckOut ---------");
		 StringBuffer msg = new StringBuffer(); 
		 List list = PromotionUtil.getLatestPromotionTargets(promotion);
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
//							 type = doc.getDisplayType().toString();
							 name = doc.getName();
							 number = doc.getNumber();
						 }else if(obj instanceof WTPart){
							 part = (WTPart)obj;
//							 type = part.getDisplayType().toString();
							 name = part.getName();
							 number = part.getNumber();
						 }else if(obj instanceof EPMDocument){
							 epm = (EPMDocument)obj;
//							 type = epm.getDisplayType().toString();
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

	/* (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#validateHasRolePrincioal(wt.maturity.PromotionNotice, java.util.List)
	 */
	@Override
	public String validateHasRolePrincioal(PromotionNotice promotion,
			List<String> roleList) throws WTException {
		log.debug(" --------------- enter validateHasRolePrincioal ----------------");
		StringBuffer msg = new StringBuffer();
		try {
			WfProcess process = WorkflowUtil.getProcessByPBO(promotion);
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

	/* (non-Javadoc)
	 * @see ext.yongc.plm.promotion.YJPromotionService#signature(wt.fc.Persistable, java.lang.String, java.lang.String)
	 */
	@Override
	public void signature(Persistable pbo, String signatureRole,
			String signatureDate) throws WTException {
		log.debug(" enter signature");
		
		SignatureHelpler helper = new SignatureHelpler(pbo);
		helper.signature(signatureRole, signatureDate);
		
		log.debug(" exit signature");
	}

	/* (non-Javadoc)
	 * 将三维文件上的工艺分配设置到对应的部件上
	 * @see ext.yongc.plm.promotion.YJPromotionService#released(wt.maturity.PromotionNotice)
	 */
	@Override
	public void released3DPromotionNotice(PromotionNotice promotion) throws WTException {
		 log.debug(">>>>>>>>>>>>>>>>>> enter released3DPromotionNotice");
		  log.debug(" promotion ------->"+IdentityFactory.getDisplayIdentifier(promotion));
		  try {
			String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(promotion).getTypename();
			if(type.indexOf(TypeConstants.THREEDDesignPromotionNotice)>-1){
				 List list = PromotionUtil.getLatestPromotionTargets(promotion);
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
	
//	private List<WTPart> getPartsByEPMDocumet(EPMDocument epm) throws WTException{
//		List<WTPart> list = new ArrayList<WTPart>();
//		QuerySpec qs = new QuerySpec(WTPart.class);
//		int buildRuleIndex = qs.appendClassList(EPMBuildRule.class, false);
//		int epmIndex = qs.appendClassList(EPMDocument.class, false);
//		SearchCondition sc1 = new SearchCondition(WTPart.class, WTPart.BRANCH_IDENTIFIER, EPMBuildRule.class, EPMBuildRule.BUILD_TARGET_ROLE);
//		return list;
//	}
}
