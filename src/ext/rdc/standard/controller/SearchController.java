/**
 * ext.rdc.standard.controller.SearchController.java
 * @Author yge
 * 2019年8月30日下午3:00:09
 * Comment 
 */
package ext.rdc.standard.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.LWCStructEnumAttTemplate;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.RefineModelService;
import com.ptc.windchill.partslink.service.RefineModelServiceImpl;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

import ext.rdc.standard.util.SearchUtil;
import wt.fc.ReferenceFactory;
import wt.httpgw.URLFactory;
import wt.log4j.LogR;

@Controller
@RequestMapping({ "/ext.rdc.standard.search" })
public class SearchController {
	
	private static final Logger logger = LogR.getLogger(SearchController.class.getName());
	 private static ReferenceFactory rf = new ReferenceFactory();
	
	private RefineModelServiceImpl refineModelService = new RefineModelServiceImpl();
    private ResultModelServiceImpl resultModelService = new ResultModelServiceImpl();
	
	 @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	 public void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 logger.debug("enter search ");
		 
		  RefineModel model = null;
		  
		 String oidStr =(String) request.getParameter("oidStr");
		 boolean executeSearch = getExecuteSearch(request);
		 logger.debug("oidStr=>"+oidStr);
		  logger.debug("executeSearch=>"+executeSearch);
		  
		  boolean hasMultiple = false;
		  int index =0;
		  StringBuffer internalName = new StringBuffer();
		 if(oidStr==null || "".equals(oidStr.trim())){
			 response.setHeader("Content-Type", "text/html");
	           response.setCharacterEncoding("utf-8");
	           response.getWriter().print("<font color='#FF0000'>没有选择分类</font>");
	           return;
		  }
   		     String[] array = oidStr.split(",");
   		      for(String oid:array){
   		    	  if(oid==null || oid.trim().length()==0){
   		    		  continue;
   		    	  }
   		    	  index++;
   		    	LWCStructEnumAttTemplate lwc = (LWCStructEnumAttTemplate)rf.getReference(oid).getObject();
   		    	TypeDefinitionReadView rv = CSMTypeDefHelper.getRV(lwc);
   		    	String classInternalName = PropertyHolderHelper.getName(rv);
   		    	if(index==1){
   		    		internalName.append(classInternalName);
   		    	}else{
   		    		internalName.append(","+classInternalName);
   		    	}
   		    	
   		    	model = refineModelService.getRefineModel(classInternalName, request);
   		    	
   		        NmCommandBean commandBean = PartsLinkUtils.getNmCommandBean(request);
   		        
   		       HashMap text = commandBean.getText();
   			   HashMap combo = commandBean.getComboBox();
   			   HashMap checkBoxs = commandBean.getChecked();
   			   logger.debug("text=>"+text);
   			   logger.debug("combo=>"+combo);
   			   logger.debug("checkBoxs=>"+checkBoxs);
   			   if(checkBoxs!=null){
   				 Iterator it = checkBoxs.keySet().iterator();
   				 while(it.hasNext()){
  	    		   String key =(String) it.next();
  	    		   List<String> strArray =(List)  checkBoxs.get(key);
  	    		   for(String s :strArray){
  	    			   logger.debug(s);
  	    		   }
  	    	   }
   			   }
   			   //添加零部件类型条件
   				   RefineBean rb = SearchUtil.createWTPartIBARefineBean(commandBean, "partTypes", false);
   				   rb.setOperator("=");
   				   rb.setValue("标准件|专业标准件");
   				   model.getRefineBeanSet().add(rb);
   		     
   		 	    RefineModel oldModel =(RefineModel) request.getAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL);
   		 	    logger.debug("oldModel=>"+oldModel);
   		 	    if(oldModel!=null){
   			    	refineModelService.mergeRefineModel(model, oldModel);
   			    }
   		 	    request.setAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL, model);
   		      }
   		      
   		      if(index>1){
   		    	  hasMultiple = true;
   		      }
		 
	   	   if(model!=null){
	 	   		  ResultModel resultModel = refineModelService.prepareResultModel(model, request);
	 	   		  logger.debug("resultModel =>"+resultModel);
	 	   		  long count = 0;
	 	   		  count = resultModel.getResultCount();
	 	   		  logger.debug("count =>"+count);
	 	   		  resultModel = resultModelService.query(resultModel);
	 	   		  count = resultModel.getResultCount();
	 	   		  logger.debug("query count =>"+count);
	 	   		  if(count>0){
	 	   		    refineModelService.updateRefineModel(model, resultModel);
	                 // put updated refineModel in session
	                 request.getSession().setAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL, model);
	 	   		  }
	 	   		  
	 	   		 String redirectPageUrl = new URLFactory().getBaseHREF();
	 		   	   if(executeSearch){
	 		   		 request.getSession().setAttribute(PartslinkConstants.Model_IDS.RESULT_MODEL,resultModel);
	 		   	     request.getSession().setAttribute("hasMultiple",hasMultiple);
	 		   	     request.getSession().setAttribute("internalName",internalName.toString());
	 		   		 response.sendRedirect(redirectPageUrl+"netmarkets/jsp/ext/rdc/standard/search/searchResult.jsp?hasMultiple="+hasMultiple);
	 		   	   }else{
	 		   		   response.sendRedirect(redirectPageUrl+"netmarkets/jsp/ext/rdc/standard/search/searchAttributes.jsp");
	 		   	   }
	 	   	   }
	 }
	 private boolean getExecuteSearch(HttpServletRequest request) {
	        boolean result = false;
	        String executeSearch = (String) request.getParameter("executeSearch");
	        if ("true".equals(executeSearch)) {
	            result = true;
	        }
	        return result;
	    }
}
