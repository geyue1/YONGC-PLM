/**
 * ext.rdc.standard.datautility.ResultDataUtility.java
 * @Author yge
 * 2019年10月8日下午1:45:25
 * Comment 
 */
package ext.rdc.standard.datautility;

import wt.fc.ReferenceFactory;
import wt.part.WTPart;
import wt.util.WTException;

import com.ptc.carambola.rendering.HTMLComponent;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.UrlDisplayComponent;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.misc.NmAction;
import com.ptc.netmarkets.util.misc.NmActionServiceHelper;

public class ResultDataUtility extends AbstractDataUtility{
	 public Object getDataValue(String component_id, Object datum, ModelContext mc) throws WTException {
		 if(datum instanceof WTPart){
			 WTPart part = (WTPart)datum;
			 GUIComponentArray guiComponentArray = new GUIComponentArray();
			 if("recommendedUsage".equalsIgnoreCase(component_id)){
				 StringBuffer sb = new StringBuffer("");
				 sb.append("<font  color=green>推荐使用</font>");
				 sb.append("<font  color=yellow>受限使用</font>");
				 sb.append("<font  color=red>禁止使用</font>");
				 HTMLComponent html = new HTMLComponent(sb.toString());
				 guiComponentArray.addGUIComponent(html);
			 }
			 if("number".equalsIgnoreCase(component_id)){
				 ReferenceFactory rf = new ReferenceFactory();
				 String oid = rf.getReferenceString(part);
				 NmAction anAction = NmActionServiceHelper.service.getAction( "object", "view" );
				 anAction.setAction( oid );
				 NmOid nmOid = NmOid.newNmOid(oid);
				 anAction.setContextObject( nmOid );
				 UrlDisplayComponent number = null;
				 try {
					String url = anAction.getActionUrlExternal();
					number = new UrlDisplayComponent(part.getNumber(), url);
					number.setLabelForTheLink(part.getNumber());
					number.setTarget("_blank");
					guiComponentArray.addGUIComponent(number);
				} catch (Exception e) {
					e.printStackTrace();
				}
				 
			 }
			 return guiComponentArray;
		 }
		
		 return null;
	 }
}
