/**
 * ext.yongc.plm.signature.TestExcel.java
 * @Author yge
 * 2017年8月4日下午9:38:50
 * Comment 
 */
package ext.yongc.plm.signature;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ext.yongc.plm.util.StringUtil;

public class TestExcel {
	 private void setBmpPicture(Workbook wb,Sheet sheet,int rowIndex,int columnIndex,String path){
	    	if(sheet==null){
	    		return;
	    	}
//	    	Row row = sheet.getRow(rowIndex);
//	    	if(row ==null){
	    	Row row = sheet.createRow(rowIndex);
//	    	}
//	    	Cell cell = row.getCell(columnIndex);
//	    	if(cell==null){
	    	Cell	cell = row.createCell(columnIndex);
//	    	}
	    	if(StringUtil.isNotEmpty(cell.getStringCellValue())){
	    		cell.setCellValue("");
	    	}
	    	
	         BufferedImage bufferImg =null; 
	         try{
	        	 ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
	        	 bufferImg = ImageIO.read(new File(path)); 
	        	 ImageIO.write(bufferImg,"bmp",byteArrayOut); 
	        	 
	        	 
	        	 Drawing patriarch = sheet.createDrawingPatriarch();
	        	 ClientAnchor anchor = new HSSFClientAnchor(0,0,800,250,(short)columnIndex,rowIndex,(short)columnIndex,rowIndex); 
	             patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),Workbook.PICTURE_TYPE_PNG)); 
	             
	          }catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 }
	 public void test() throws Exception{
		 String path1 = "C:\\Users\\yge\\Desktop\\分析文件1-RAMS&LCC分析报告.xls";
		 InputStream is = new FileInputStream(path1);
		 Workbook wb = WorkbookFactory.create(is);
		 Sheet sheet = wb.getSheetAt(1);
		 System.out.println(sheet.getSheetName());
//		 setBmpPicture(wb, sheet, 4, 5, "C:\\Users\\yge\\Desktop\\陈洪旭.bmp");
		 
		 String path2 = "C:\\Users\\yge\\Desktop\\excel.xls";
		 OutputStream os = new FileOutputStream(path2);
		 wb.write(os);
		 os.flush();
		 os.close();
		 is.close();
	 }
         public static void main(String[] args) throws Exception{
        	 System.out.println("---------- start ---------------");
        	 TestExcel te = new TestExcel();
        	 te.test();
        	 System.out.println("----------end --------------------");
         }
}
