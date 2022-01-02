/**
 * ext.yongc.plm.promotion.mvc.MBOMPromotionListTreeHandler.java
 * @Author yge
 * 2017年7月16日下午1:42:33
 * Comment 
 */
package ext.yongc.plm.promotion.mvc;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;

import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.fc.collections.WTArrayList;
import wt.identity.IdentityFactory;
import wt.lifecycle.State;
import wt.log4j.LogR;
import wt.maturity.PromotionNotice;
import wt.part.WTPart;
import wt.part.WTPartConfigSpec;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.WTPartStandardConfigSpec;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.ViewHelper;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.engine.WfActivity;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WorkItem;

import com.ptc.core.components.beans.TreeHandlerAdapter;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.mvc.components.ComponentParams;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.PromotionUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WorkflowUtil;

public class MBOMPromotionListTreeHandler extends TreeHandlerAdapter {

	private String oid;
	private String view;
	private String state;
	List<WTPart> promotionList = new ArrayList<WTPart>();
	List<WTPart> gongyiList = new ArrayList<WTPart>();
	Logger logger = LogR
			.getLogger(MBOMPromotionListTreeHandler.class.getName());

	public MBOMPromotionListTreeHandler() {

	}

	public MBOMPromotionListTreeHandler(ComponentParams params) {
		oid = (String) params.getParameter("oid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.core.components.beans.TreeHandler#getNodes(java.util.List)
	 */
	@Override
	public Map<Object, List> getNodes(List arg0) throws WTException {

		Map<Object, List> resultMap = new HashMap<Object, List>();
		WTPartStandardConfigSpec standardConfig = WTPartStandardConfigSpec
				.newWTPartStandardConfigSpec();
		standardConfig = WTPartStandardConfigSpec.newWTPartStandardConfigSpec();
		if (StringUtil.isNotEmpty(view)) {
			try {
				standardConfig.setView(ViewHelper.service.getView(view));
			} catch (WTPropertyVetoException e) {
				e.printStackTrace();
			}
		}
		if (StringUtil.isNotEmpty(state)) {
			standardConfig.setLifeCycleState(State.toState(state));
		}

		WTPartConfigSpec config = WTPartConfigSpec
				.newWTPartConfigSpec(standardConfig);

		Persistable[][][] allChildren = WTPartHelper.service.getUsesWTParts(
				new WTArrayList(arg0), config);

		for (ListIterator i = arg0.listIterator(); i.hasNext();) {
			WTPart parent = (WTPart) i.next();
			Persistable[][] oneParentNode = allChildren[i.previousIndex()];
			if (oneParentNode == null) {
				continue;
			} else {
				List<WTPart> children = new ArrayList<WTPart>(
						oneParentNode.length);
				for (int j = 0; j < oneParentNode.length; j++) {
					Object obj = oneParentNode[j][1];
					if (obj instanceof WTPart) {
						children.add((WTPart) obj);
					} else if (obj instanceof WTPartMaster) {
						WTPartMaster master = (WTPartMaster) obj;
						children.add(PartUtil.getLatestPartByMaster(master));
					}

				}
				List<WTPart> temp = new ArrayList<WTPart>();
				temp.addAll(children);
				// 过滤不在工艺类型中的零部件
				for (int k = 0; k < temp.size(); k++) {
					if (gongyiList.size()>0 && !gongyiList.contains(temp.get(k))) {
						children.remove(temp.get(k));
					}
				}
				resultMap.put(parent, children);
			}
		}

		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.core.components.beans.TreeHandler#getRootNodes()
	 */
	@Override
	public List<Object> getRootNodes() throws WTException {
		List<Object> list = new ArrayList();
		Object actionObj = getModelContext().getNmCommandBean().getActionOid()
				.getRefObject();
		Object root;
		try {
			root = getRootObject(actionObj);
			if (root instanceof WTPart) {
				list.add((WTPart) root);
			} else if (root instanceof List) {
				list.addAll((List) root);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return list;
	}

	private Object getRootObject(Object actionObj) throws RemoteException,
			WTException {
		if (actionObj instanceof WTPart) {
			WTPart root = (WTPart) actionObj;
			view = root.getViewName();
			state = root.getState().toString();
			return root;
		} else if (actionObj instanceof WorkItem) {
			List rootList = new ArrayList();
			WorkItem item = (WorkItem) actionObj;
			WfActivity wa = (WfActivity) item.getSource().getObject();
			String waName = wa.getName();
			logger.debug(" waName ----------->"+waName);
			WfProcess process = WorkflowUtil.getProcessByObject(item);
			Persistable p = (Persistable) process.getContext().getValue(
					WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
			if (p instanceof PromotionNotice) {
				PromotionNotice promotion = (PromotionNotice) p;
				String type = TypeIdentifierUtilityHelper.service
						.getTypeIdentifier(promotion).getTypename();
				if (type.indexOf(TypeConstants.MBOMPromotionNotice) > -1) {
					IBAUtil iba = new IBAUtil(promotion);
					String partOid = iba.getIBAValue(IBAConstants.PARTOID);
					ReferenceFactory rf = new ReferenceFactory();
					WTPart origPart = (WTPart) rf.getReference(partOid)
							.getObject();
					List<WTPart> tempList = new ArrayList<WTPart>();

					List  qr = PromotionUtil.getLatestPromotionTargets(promotion);
					for (int j=0;j<qr.size();j++) {
						WTPart part = (WTPart) qr.get(j);
						part = PartUtil.getLatestPartByMaster((WTPartMaster)part.getMaster());
						logger.debug("part------->"+IdentityFactory.getDisplayIdentifier(part));
						promotionList.add(part);
						IBAUtil iba2 = new IBAUtil(part);
						String gongyi = iba2.getIBAValue(IBAConstants.GONGYI_TYPE);
						logger.debug("gongyi------->"+gongyi);
						if (!StringUtil.isEmpty(gongyi)&& !StringUtil.isEmpty(waName)&&containWA(waName,gongyi)){
							tempList.add(part);
							gongyiList.add(part);
						}
					}
					// 如果是钢结构焊接、钢结构、机加、布线、组装、表面、绝缘工艺节点
					if (tempList.size() > 0) {
						return tempList;
					}
					return origPart;
				}
			}
		}
		return null;
	}
	private boolean containWA(String wa,String gongyi){
		if(StringUtil.isEmpty(gongyi)){
			return false;
		}else if(gongyi.contains(",")){
			String[] array = gongyi.split(",");
			for(String s : array){
				if(StringUtil.isNotEmpty(s) && s.trim().equals(wa)){
					return true;
				}
			}
		}else if(!gongyi.contains(",")){
			return gongyi.trim().equals(wa.trim());
		}
		
		return false;
	}
}
