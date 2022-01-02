/**
 * ext.rdc.standard.bean.NumberReplaceBean.java
 * @Author yge
 * 2019年10月18日上午9:55:06
 * Comment 
 */
package ext.rdc.standard.bean;

import java.util.ArrayList;
import java.util.List;

public class NumberReplaceBean {
       private String number;
       private List<String> replace  = new ArrayList<String>();
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the replace
	 */
	public List<String> getReplace() {
		return replace;
	}
	/**
	 * @param replace the replace to set
	 */
	public void setReplace(List<String> replace) {
		this.replace = replace;
	}
       
       
}
