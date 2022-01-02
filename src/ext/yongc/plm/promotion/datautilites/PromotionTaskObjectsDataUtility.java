/**
 * ext.yongc.plm.promotion.datautilites.PromotionTaskObjectsDataUtility.java
 * @Author yge
 * 2017年7月10日上午8:41:18
 * Comment 
 */
package ext.yongc.plm.promotion.datautilites;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.epm.util.EPMDebug;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.IconComponent;
import com.ptc.core.components.rendering.guicomponents.Label;
import com.ptc.core.components.rendering.guicomponents.TextBox;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.part.link.PartMaterialLink;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.StringUtil;

public class PromotionTaskObjectsDataUtility extends AbstractDataUtility {

	private Logger logger = LogR.getLogger(PromotionTaskObjectsDataUtility.class.getName());
	static ReferenceFactory rf = new ReferenceFactory();
	/* (non-Javadoc)
	 * @see com.ptc.core.components.descriptor.DataUtility#getDataValue(java.lang.String, java.lang.Object, com.ptc.core.components.descriptor.ModelContext)
	 */
	@Override
	public Object getDataValue(String arg0, Object arg1, ModelContext arg2)
			throws WTException {
		logger.debug("------------ enter getDataValue -------------");
		Object return_value = null;
		
		if(arg1 instanceof EPMDocument){
			GUIComponentArray componentArray = new GUIComponentArray();
			TextDisplayComponent nInput = new TextDisplayComponent("&nbsp;");
			nInput.setId(IBAConstants.GONGYI_TYPE);
			nInput.setName(IBAConstants.GONGYI_TYPE);
			String value = "";
			EPMDocument epm = (EPMDocument)arg1;
			IBAUtil iba= new IBAUtil(epm);
			String type = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
			if(!StringUtil.isEmpty(type)){
				value =type;
			}
			nInput.setValue(value);
			componentArray.addGUIComponent(nInput);
			return_value = componentArray;
		}else if((arg1 instanceof WTPart) && "extFlag".equals(arg0)){
			GUIComponentArray componentArray = new GUIComponentArray();
			WTPart part = (WTPart)arg1;
			QueryResult qr1= findMaterialPart(part);
			if(qr1!=null && qr1.size()>0){
				 IconComponent image = new IconComponent();
				 image.setSrc("netmarkets/images/variance_9x9.gif");
				 image.setTooltip("有原材料链接");
				 componentArray.addGUIComponent(image);
			}
			List<WTDocument> list = findGYLXByPart(part);
			if(list.size()>0){
				 IconComponent image = new IconComponent();
				 image.setSrc("netmarkets/images/attribute_diff_9x9.png");
				 image.setTooltip("有工艺路线");
				 componentArray.addGUIComponent(image);
			}
			return_value = componentArray;
		}
		
		
		 
		logger.debug("------------ exit getDataValue -------------");
		return return_value;
	}
	
	private QueryResult findMaterialPart(WTPart part) throws WTException{
		QuerySpec qs = new QuerySpec(PartMaterialLink.class);
    	SearchCondition sc = new SearchCondition(PartMaterialLink.class,PartMaterialLink.ROLE_AIDA2A2,
    			"=",rf.getReferenceString(part));
    	qs.appendWhere(sc);
    	return PersistenceHelper.manager.find(qs);
	}
	private List<WTDocument> findGYLXByPart(WTPart part) throws WTException{
		  List<WTDocument> resultData = new ArrayList<WTDocument>();
		  try {
					  List<WTDocument> list = PartUtil.getDescDocByPart(part);
			      	  for(int i=0;i<list.size();i++){
			      		WTDocument doc = list.get(i);
			      		String type;
							type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
			      		if(type.indexOf(TypeConstants.DOC_GYLX)>-1){
			      			//获取最新对象
			      			QueryResult qr = VersionControlHelper.service.allVersionsOf(doc.getMaster());
			          		while(qr.hasMoreElements()){
			          			doc = (WTDocument)qr.nextElement();
			          			break;
			          		}
			      			resultData.add(doc);
			      		}
			      	}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		  return resultData;
	}

}
