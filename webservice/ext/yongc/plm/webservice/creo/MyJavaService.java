/**
 * ext.yongc.plm.webservice.MyJavaService.java
 * @Author yge
 * 
 * Comment 
 */
package ext.yongc.plm.webservice.creo;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.ptc.jws.servlet.JaxWsWebService;

@WebService()
public class MyJavaService extends JaxWsWebService{
	@WebMethod(operationName="add")
	public int add ( int a, int b ){
		  System.out.println(" >>>>>>>>>>>>>> start MyJavaService>>>>>>>>>> a="+a+",b="+b);
	       return a+b;
	}
}
