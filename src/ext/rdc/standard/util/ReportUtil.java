/**
 * ext.rdc.standard.util.ReportUtil.java
 * @Author yge
 * 2019年10月16日下午1:50:01
 * Comment 
 */
package ext.rdc.standard.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.infoengine.object.factory.Element;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.partslink.PartslinkConstants.SearchType;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.RefineModelServiceImpl;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;

import ext.rdc.standard.bean.LengthReplaceBean;
import ext.rdc.standard.bean.NumberReplaceBean;
import ext.solr.test.IBAUtil;
import wt.fc.ReferenceFactory;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.util.WTException;

public class ReportUtil {
	
	   private static Logger log = LogR.getLogger(ReportUtil.class.getName());
	   
	   /**
	    * 标准件替代方案
	    * @description TODO
	    * @param partList
	    * @param attrMap
	    * @author yge  2019年10月16日下午1:37:38
	 * @throws WTException 
	    */
       public static List<WTPart> getReplacePart(WTPart part,Map<String,String> attrMap) throws WTException{
    	    //根据部件的分类查找
    	   RefineModelServiceImpl refineModelService = new RefineModelServiceImpl();
    	   ResultModelServiceImpl resultModelService = new ResultModelServiceImpl();
			List<WTPart> result = new ArrayList<WTPart>();
			
			String classInternalName  = getCLFInternalName(part);
			RefineModel model = refineModelService.getRefineModel(classInternalName, null);
			log.debug("model = >"+model);
			if(model!=null){
				//添加查询条件
				NmCommandBean commandBean = new NmCommandBean();
				Iterator<String> it = attrMap.keySet().iterator();
			    while(it.hasNext()){
			    	String attrId = it.next();
			    	String value = attrMap.get(attrId);
			    	RefineBean rb = SearchUtil.createWTPartIBARefineBean(commandBean, attrId, false);
			    	rb.setOperator("=");
			    	 rb.setValue(value);
	  				 model.getRefineBeanSet().add(rb);
			    }
	    	    
	    	    //调用solr查询
	    	    ResultModel resultModel = new ResultModel();
	            resultModel.setClassInternalName(model.getClassInternalName());
	            resultModel.setFilterQueries(refineModelService.generateFilterQueries(model, resultModel, "WTPart"));
	            resultModel.setSearchType(SearchType.REFINE_SEARCH);
	            resultModel = resultModelService.query(resultModel);
	            
	            List<Element>  searchResults = new ResultModelServiceImpl().getResults(resultModel);
	            ReferenceFactory rf = new ReferenceFactory();
	            if (searchResults != null) {
	            	for (Element eleResult : searchResults) {
	            		String oid = (String) eleResult.getValue("obid");
	            		WTPart resultPart = (WTPart) rf.getReference(oid).getObject();
	            		result.add(resultPart);
	            	}
	            }
	            	
			}
			//用替代方案中编号过滤
			filterReplaceByNumber(part, partList, numberList);
			//用替代方案中长度过滤
			
			
    	    return result;
    	    
       }
       
       /**
        * 根据替代方案中可替换的产品型式，部件编号过滤标准件
        * @description TODO
        * @param sourcePart
        * @param partList
        * @param numberList
        * @author yge  2019年10月18日上午10:02:54
        */
       public static void filterReplaceByNumber(WTPart sourcePart,List<WTPart> partList,List<NumberReplaceBean> numberList){
		  List<WTPart> temp = new ArrayList<WTPart>();
    	   for(NumberReplaceBean numberBean:numberList){
			   String number = numberBean.getNumber();
			   List<String> replaceNumbers = numberBean.getReplace();
			   
			   if(sourcePart.getNumber().startsWith(number)){
				   for(String num:replaceNumbers){
					   for(WTPart part:partList){
						   if(part.getNumber().startsWith(num)){
							   temp.add(part);
						   }
					   }
				   }
			   }
		   }
    	   
    	   partList.removeAll(partList);
    	   partList.addAll(temp);
       }
       /**
        * 根据替代方案中长度尺寸属性过滤可替代的标准件
        * @description TODO
        * @param attrValue
        * @param partList
        * @param attrList
        * @author yge  2019年10月18日上午10:18:54
     * @throws WTException 
        */
       public static void filterReplaceByLength(String attrValue,List<WTPart> partList,List<LengthReplaceBean> attrList) throws WTException{
    	   List<WTPart> temp = new ArrayList<WTPart>();
    	   
    	   for(LengthReplaceBean attrBean :attrList){
    		   String value = attrBean.getValue();
    		   List<String> replaceLengths = attrBean.getReplace();
    		   if(attrValue.equals(value)){
    			   for(WTPart part:partList){
    				   IBAUtil iba = new IBAUtil(part);
    				   String length = iba.getIBAValue(attrBean.getAttrId());
    				   if(replaceLengths.contains(length)){
    					   temp.add(part);
    				   }
    			   }
    		   }
    	   }
    	   partList.removeAll(partList);
    	   partList.addAll(temp);
       }
       public static String getCLFInternalName(WTPart part) throws WTException{
    	   PersistableAdapter obj = new PersistableAdapter(part, null, SessionHelper.getLocale(), null);
			obj.load("CSMPartClassification");
			String classInternalName = (String) obj.get("CSMPartClassification");
			log.debug("classInternalName=>"+classInternalName);
			return classInternalName;
       }
      public static void getReplaceConfig(){
    	  
      }
}
