/**
 * com.midea.ocm.platform.pdf.MatchItem.java
 * @Author yge
 * 2018年7月3日上午9:35:33
 * Comment 
 */
package ext.yongc.plm.pdf;

public class MatchItem {
	private Integer pageNum;
	private Float x;
	private Float y;
	private Float pageWidth;
	private Float pageHeight;
	private String content;
	
	public Integer getPageNum() {
	    return pageNum;
	}
	public void setPageNum(Integer pageNum) {
	    this.pageNum = pageNum;
	}
	public Float getX() {
	    return x;
	}
	public void setX(Float x) {
	    this.x = x;
	}
	public Float getY() {
	    return y;
	}
	public void setY(Float y) {
	    this.y = y;
	}
	public Float getPageWidth() {
	    return pageWidth;
	}
	public void setPageWidth(Float pageWidth) {
	    this.pageWidth = pageWidth;
	}
	public Float getPageHeight() {
	    return pageHeight;
	}
	public void setPageHeight(Float pageHeight) {
	    this.pageHeight = pageHeight;
	}
	public String getContent() {
	    return content;
	}
	public void setContent(String content) {
	    this.content = content;
	}

	public String toString() {
	    return "MatchItem [pageNum=" + pageNum + ", x=" + x + ", y=" + y
	            + ", pageWidth=" + pageWidth + ", pageHeight=" + pageHeight
	            + ", content=" + content + "]";
	}
}
