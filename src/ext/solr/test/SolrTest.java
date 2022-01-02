/**
 * ext.solr.test.SolrTest.java
 * @Author yge
 * 2019年8月28日下午5:27:01
 * Comment 
 */
package ext.solr.test;

import com.ptc.windchill.partslink.model.RefineModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.RefineModelServiceImpl;
import com.ptc.windchill.partslink.service.ResultModelService;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;

import wt.method.RemoteAccess;
import wt.util.WTException;

public class SolrTest implements RemoteAccess{
    public static void test() throws WTException{
    	String   classInternalName = "";//分类的内部名称
    	RefineModelServiceImpl refineModelService = new RefineModelServiceImpl();
    	//RefineBean RefineBean [attrDisplayName=供货厂家, attrID=IBA138126_t, attrDataType=STRING, attrUnits=, rangeHi=, rangeLow=, value=2, operatorColumnValues=[], operatorColumnDisplayValues=[], operator=]
    	RefineModel refineModel = refineModelService.getRefineModel(classInternalName, null);
    	ResultModel resultModel = refineModelService.prepareResultModel(refineModel, null);
    	ResultModelServiceImpl resultModelService = new ResultModelServiceImpl();//solr搜索服务类
    	 resultModel = resultModelService.query(resultModel);
    }
}
