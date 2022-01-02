/**
 * ext.solr.test.DbSearchServiceTest.java
 * @Author yge
 * 2019年8月27日下午4:16:05
 * Comment 
 */
package ext.solr.test;

import com.ptc.core.query.command.common.BasicQueryCommand;
import com.ptc.windchill.enterprise.search.mvc.service.DbSearchResultProcessor;
import com.ptc.windchill.enterprise.search.mvc.service.DbSearchService;
import com.ptc.windchill.enterprise.search.server.SearchInfo;

import wt.method.RemoteAccess;

public class DbSearchServiceTest implements RemoteAccess{
    public static void test(){
    	DbSearchService dbService = new DbSearchService();
    	BasicQueryCommand cmd = new BasicQueryCommand();
    	SearchInfo searchInfo = new SearchInfo();
    	dbService.set
    	DbSearchResultProcessor resultProcessor = new DbSearchResultProcessor(searchInfo, dbService);
        
    }
}
