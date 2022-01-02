/**
 * ext.solr.test.TestProcess.java
 * @Author yge
 * 2019年9月4日下午3:13:35
 * Comment 
 */
package ext.solr.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import com.ptc.netmarkets.util.misc.NmActionServiceHelper;

import wt.fc.ReferenceFactory;
import wt.feedback.StatusFeedback;
import wt.httpgw.URLFactory;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.util.WTException;
import wt.workflow.engine.WfProcess;

public class TestProcess1 implements RemoteAccess{
    public static ReferenceFactory rf = new ReferenceFactory();
    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void test() throws WTException, JSONException{
    	WfProcess process = (WfProcess) rf.getReference("OR:wt.workflow.engine.WfProcess:105725").getObject();
    	
    	 JSONObject json = new org.json.JSONObject();
         json.put("sysCode", "faw_pdm");
         json.put("procCode", process.getPersistInfo().getObjectIdentifier().getId());
         json.put("procInstId", process.getPersistInfo().getObjectIdentifier().getStringValue());
         json.put("folio", "");//folio为空则数据库中同procInstId
         json.put("title", process.getName());
         WTUser creator = (WTUser)process.getCreator().getPrincipal();
         json.put("organizerId", creator.getEMail());
         json.put("organizerName", creator.getFullName());
         json.put("organizerTel", creator.getMobilePhoneNumber());
         json.put("organizerEmail", creator.getEMail());
         json.put("sourceType", "email");//pc,app,email,sms
         json.put("pcUrl", getInfoPageURL(process.getBusinessObjReference()));//PBO url
         json.put("appUrl", "");
         json.put("startTime", formatTime(process.getCreateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
         json.put("statusDesc", process.getState().toString());
         json.put("operationId", creator.getEMail());
         json.put("operationTime", formatTime(process.getCreateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
         MethodContext.getContext().sendFeedback(new StatusFeedback(json.toJSONString()));
    }
    public static String formatTime(Object obj, String format) {
        String timeStr = "";

        if (obj != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            if (obj instanceof Date) {
                Date date = (Date) obj;
                timeStr = sdf.format(date);
            } else if (obj instanceof Timestamp) {
                Timestamp ts = (Timestamp) obj;
                timeStr = sdf.format(ts);
            }
        }

        return timeStr;
    }
    private static String getInfoPageURL(String oid) throws WTException{
   	 HashMap<String, String> params = new HashMap<String, String>();
	     params.put("oid", oid);
	     String viewActionPath = NmActionServiceHelper.service.getAction("object", "view").getUrl();
       URLFactory urlFactory = new URLFactory();
       String url =  urlFactory.getHREF(viewActionPath, params, true);
       
       return url;
   }
    public static void main(String[] args){
    	RemoteMethodServer server = RemoteMethodServer.getDefault();
		server.setUserName("wcadmin");
		server.setPassword("wcadmin");
//		Class[] classes = { String.class };
//		Object[] objs = { args[0]};

		try {
			server.invoke("test", TestProcess1.class.getName(), null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
}
