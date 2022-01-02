/**
 * ext.yongc.plm.promotion.forms.processors.YJPromotionObjectsFormDelegate.java
 * @Author yge
 * 2017年7月18日下午2:51:14
 * Comment 
 */
package ext.yongc.plm.promotion.forms.processors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTSet;
import wt.identity.IdentityFactory;
import wt.inf.container.WTContainerRef;
import wt.log4j.LogR;
import wt.maturity.MaturityBaseline;
import wt.maturity.MaturityHelper;
import wt.maturity.MaturityService;
import wt.maturity.PromotionNotice;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.baseline.BaselineHelper;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfProcessTemplate;

import com.ptc.core.components.beans.CreateAndEditWizBean;
import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.maturity.forms.delegates.PromotionObjectsFormDelegate;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.promotion.YJPromotionHelpler;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PromotionUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WorkflowUtil;

public class YJPromotionObjectsFormDelegate extends PromotionObjectsFormDelegate{
	
	static final Logger logger = LogR.getLogger(YJPromotionObjectsFormDelegate.class.getName());
	
	 public FormResult postProcess(NmCommandBean paramNmCommandBean, List<ObjectBean> paramList)
			    throws WTException {
		 logger.debug(" --------------- enter postProcess ----------------------");
		 
		 FormResult result= super.postProcess(paramNmCommandBean, paramList);
		 String partOid = paramNmCommandBean.getTextParameter("partOid");
		 logger.debug("partOid--------->"+partOid);
		 WfProcessTemplate template = null;
		 WTContainerRef containerRef = CreateAndEditWizBean.getContainerRef(paramNmCommandBean);
		 logger.debug("containerRef  " + containerRef);
		 QueryResult qr= WfDefinerHelper.service.getEnabledTemplates(containerRef);
		
		 PromotionNotice promotion = null;
		 for (ObjectBean ob : paramList) {
			 logger.debug("object =======>"+ob.getObject());
			 if(ob.getObject()!=null && (ob.getObject() instanceof PromotionNotice)){
				 promotion = (PromotionNotice)ob.getObject();
				 if(StringUtil.isNotEmpty(partOid)){
					 IBAUtil iba = new IBAUtil(promotion);
					 iba.setIBAAnyValue(promotion, IBAConstants.PARTOID, partOid);
				 }
				 try {
					processPromotion(promotion,qr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }
		 }
		 return result;
	  }

	 private void addBaseline(PromotionNotice promotion) throws WTException{
		 logger.debug(" ----------------- enter addBaseline -------------");
		 
		  QueryResult qr = PromotionUtil.getPromotionTargets(promotion);
		   List list = new ArrayList();
		   while(qr.hasMoreElements()){
			   list.add(qr.nextElement());
		   }
		   logger.debug("list------------> "+list.size());
		 
		   QueryResult q = MaturityHelper.service.getPromotionTargets(promotion);
			  List baselineList = new ArrayList();
			  while(q.hasMoreElements()){
				  baselineList.add(q.nextElement());
			  }
			  for(int i=0;i<baselineList.size();i++){
				   if(list.contains(baselineList.get(i))){
					   list.remove(baselineList.get(i));
				   }
			   }
			  logger.debug(" list size ========="+list.size());
			  if(list.size()>0){
				  WTSet promotableSet = new WTHashSet(); 
		             promotableSet.addAll(list);
		             try {
						MaturityHelper.service.savePromotionTargets(promotion, promotableSet);
					} catch (WTPropertyVetoException e) {
						e.printStackTrace();
					}
		             promotion = (PromotionNotice) PersistenceHelper.manager.refresh(promotion);
			  }
			
		 
		 logger.debug(" ----------------- exit addBaseline -------------");
	 }
	 private void processPromotion( PromotionNotice promotion,QueryResult qr) throws Exception{
			logger.debug(" ----------------- enter processPromotion -------------");
			String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(promotion).getTypename();
			logger.debug("type------------>"+type);
			WfProcessTemplate template = null;
			
			if(type.indexOf(TypeConstants.THREEDDesignPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_3DDesignDoc.equals(temp.getName())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.MBOMPromotionNotice)>-1){
			      QueryResult qqr = PromotionUtil.getPromotionTargets(promotion);
			      List<WTPart> list = new ArrayList<WTPart>();
			      while(qqr.hasMoreElements()){
			    	  Object obj = qqr.nextElement();
			    	  if(obj instanceof WTPart){
			    		  list.add((WTPart)obj);
			    	  }
			      }
			      List tempList = new ArrayList();
			      tempList.addAll(list);
			      logger.debug(" list size -------->"+list.size());
			      List bomList = YJPromotionHelpler.service.getMBOMParts(tempList);
			      logger.debug(" bomList size = ---------->"+bomList.size());
			      logger.debug("--------->"+promotion.getConfiguration());
			     
			      logger.debug("-tempList size-------->"+tempList.size());
			      for(WTPart part :list){
			    	  logger.debug("--------"+part.getName()+" , "+part.getNumber());
			    	  if(bomList.contains(part)){
			    		  bomList.remove(part);
			    	  }
			      }
			      //再一次过滤BOM中的重复数据
			      List<WTPart> tempL = new ArrayList<WTPart>();
			      for(int i=0;i<bomList.size();i++){
			    	  WTPart part = (WTPart) bomList.get(i);
			    	  //去掉已发布的标准件
			    	  if(part.getState().toString().equalsIgnoreCase("RELEASED")){
			    		  continue;
			    	  }
			    	  if(!tempL.contains(part)){
			    		  tempL.add(part);
			    	  }
			      }
			      logger.debug(" temp size = ---------->"+tempL.size());
			      if(tempL.size()>0){
			    	  PromotionUtil.addPromotable(promotion, new WTArrayList(tempL));
			      }
			      while(qr.hasMoreElements()){
						 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
						 if(WorkflowConstants.WF_MBOM.equals(temp.getName())){
							 template = temp;
							 break;
						 }
					 }
			}else if(type.indexOf(TypeConstants.BASEDESIGNDOCPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_BASEDESIGNDOC.equals(temp.getName())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.GYDOCPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_GYDOC.trim().equals(temp.getName().trim())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.GZDOCPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_GZDOC.equals(temp.getName())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.FX1DOCPromotionNotice)>-1 || type.indexOf(TypeConstants.ZYDFJPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_FX1DOC.equals(temp.getName())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.FX2DOCPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_FX2DOC.equals(temp.getName())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.MB2DOCPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_MB2DOC.equals(temp.getName())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.MB1DOCPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_MB1DOC.equals(temp.getName())){
						 template = temp;
						 break;
					 }
				 }
				 addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.TYWJMLPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_TYWJML.equals(temp.getName())){
						 template = temp;
						 break;
					 }
			    }
			   addBaseline(promotion);
			}else if(type.indexOf(TypeConstants.JSTZSPromotionNotice)>-1){
				 while(qr.hasMoreElements()){
					 WfProcessTemplate temp =(WfProcessTemplate) qr.nextElement();
					 if(WorkflowConstants.WF_JSTZS.equals(temp.getName())){
						 template = temp;
						 break;
					 }
			   }
			   addBaseline(promotion);
			}
			
			 logger.debug(" ----------- template = "+template);
			 if(template!=null){
				 WorkflowUtil.startWorkflow(promotion, template);
				// MaturityHelper.service.startPromotionProcess(promotion, template);
			 }
			 
			 logger.debug(" ----------------- exit processPromotion -------------");
		}
}
