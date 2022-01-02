/**
 * ext.yongc.plm.listener.YJEPMListenerHelper.java
 * @Author yge
 * 2017年11月21日下午3:25:31
 * Comment 
 */
package ext.yongc.plm.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.util.EPMUtil;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.StringUtil;
import wt.conflict.ConflictElement;
import wt.conflict.ConflictException;
import wt.conflict.ConflictType;
import wt.epm.EPMDocument;
import wt.fc.collections.WTArrayList;
import wt.identity.IdentityFactory;
import wt.log4j.LogR;
import wt.util.LocalizableMessage;
import wt.util.WTException;
import wt.util.WTMessage;

public class YJEPMListenerHelper {
	private static final Logger logger = LogR.getLogger(YJEPMListenerHelper.class.getName());
	 private EPMDocument epm;
	 
	 public YJEPMListenerHelper(EPMDocument epm){
		 this.epm=epm;
	 }
	 
	 public void epmCheckin() throws WTException{
		 logger.debug(" >>>>>>>>>>> enter epmCheckin");
		 logger.debug("epm>>>>>>>>>>>>>>>>>>"+IdentityFactory.getDisplayIdentifier(epm));
		 
		 IBAUtil iba= new IBAUtil(epm);
		 String num = iba.getIBAValue(IBAConstants.DRAWINGNO);
		 logger.debug("DRAWINGNO------------->"+num);
		 if(StringUtil.isEmpty(num)){
			 return;
		 }
		 Map<String,String> ibaMap =new  HashMap<String,String>();
		 ibaMap.put(IBAConstants.DRAWINGNO, num);
		 Vector vec= EPMUtil.getEPMDocumentsByTypeAndIBA("com.yongc.DefaultEPMDocument", ibaMap, true);
		logger.debug("vec size==========="+vec.size());
		 ArrayList conflictElements = new ArrayList();
		for(int i=0;i<vec.size();i++){
			 EPMDocument e =(EPMDocument) vec.get(i);
			 if(!e.getNumber().equalsIgnoreCase(epm.getNumber())){
			
				 WTArrayList permissionLst = new WTArrayList();
				 permissionLst.add(epm);
				 Object[] params = new Object[1];
				 params[0] = num;
				 WTMessage mes = new WTMessage("ext.yongc.plm.resource.YJDocRB","failed_checkin",params);
				
				   ConflictElement ce = new ConflictElement(ConflictType.toConflictType("CONFLICT"), null, wt.conflict.ResolutionType.RETRY, permissionLst, mes);
				   conflictElements.add(ce);
				
			 }
		}
		logger.debug("conflictElements size==========="+conflictElements.size());
		 if(conflictElements.size()>0){
			   ConflictElement conElemsArray[] = new ConflictElement[conflictElements.size()];
			   conElemsArray = (ConflictElement[])conflictElements.toArray(conElemsArray);
			   ConflictException conException = new ConflictException(conElemsArray);
			   throw conException;
		 }
	 }
}
