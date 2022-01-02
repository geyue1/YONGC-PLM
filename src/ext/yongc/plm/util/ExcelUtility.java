package ext.yongc.plm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import wt.log4j.LogR;


/**
 * Excel Utility
 *
 */
public class ExcelUtility {
	private static final Logger cscDebug =LogR.getLogger(ExcelUtility.class.getName());

	/** POI Workbook */
	public Workbook wb = null;

	/** Excel filePath */
	private File excelFile = null;

	/** Sheet */
	private Sheet sheet;
	private Row row;
	private Cell cell;
	private Sheet currentSheet = null;
	private FileInputStream fis = null;

	public ExcelUtility() {
	}

	public ExcelUtility(String filePathName) throws Exception {
		POIFSFileSystem fs = null;
		excelFile = new File(filePathName);
		if (excelFile.exists()) {
			fis = new FileInputStream(excelFile);
			fs = new POIFSFileSystem(fis);
		}else{
			throw new Exception("Can not find template file:"+filePathName);
		}
		if (fs != null) {
			wb = WorkbookFactory.create(fs);
			currentSheet = wb.getSheetAt(0);
		}
		
	}

	/**
	 * @return the fis
	 */
	public FileInputStream getFis() {
		return fis;
	}

	public static void closeStream(ExcelUtility eu) {
		try {
			eu.getFis().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return
	 */
	public boolean exists() {
		return wb != null;
	}

	/**
	 *
	 * @return
	 */
	public String getParent() {
		return excelFile.getParent();
	}

	/**
	 *
	 * @return
	 */
	public String getFileName() {
		return excelFile.getName();
	}

	/**
	 *
	 * @return
	 * @throws IOException
	 */
	public boolean createNewFile() throws IOException {
//		wb = new Workbook();

		if (excelFile != null) {
			boolean dirResult = excelFile.getParentFile().mkdirs();

			if (dirResult || excelFile.getParentFile().exists()) {
				FileOutputStream fileOut = new FileOutputStream(getParent()
						+ File.separator + getFileName());
				wb.write(fileOut);
				fileOut.close();
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public boolean createNewSheet(String sheetName) throws IOException {
		if (wb == null)
			return false;
		Sheet sheet = wb.createSheet(sheetName);
		// ����Ϊ��ǰSheet
		this.currentSheet = sheet;

		if (sheet != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * @param sheetName
	 */
	public void switchCurrentSheet(String sheetName) {
		if (wb == null)
			return;
		Sheet sheet = wb.getSheet(sheetName);

		if (sheet != null) {
			this.currentSheet = sheet;
		}
	}

	public void switchCurrentSheet(int sheetId) {
		if (wb == null)
			return;
		Sheet sheet = wb.getSheetAt(sheetId);

		if (sheet != null) {
			this.currentSheet = sheet;
		}
	}

	public int getSheetRowCount() {
		return this.currentSheet.getPhysicalNumberOfRows();
	}

	public int getSheetColCount() {
		int cols = this.currentSheet.getRow(0).getLastCellNum();
		return cols;
	}
	public int getSheetColCount(int row) {
		int cols = this.currentSheet.getRow(row).getLastCellNum();
		return cols;
	}

	/**
	 *
	 * @param sheetName
	 * @return
	 */
	public boolean isExistSheet(String sheetName) {
		if (wb == null)
			return false;
		Sheet sheet = wb.getSheet(sheetName);

		if (sheet == null) {
			return false;
		}

		return true;
	}

	/**
	 *
	 * @param row
	 * @param col
	 * @param value
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean setStringValue(int row, int col, String value) {
		if (wb == null)
			return false;
		Row Row = currentSheet.getRow(row);
		if (Row == null) {
			Row = currentSheet.createRow(row);
		}
		Cell cell = Row.getCell(col);
		if (cell == null) {
			cell = Row.createCell( col);
		}
		// cell.setEncoding(Cell.ENCODING_UTF_16);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		
		//强制换行
		//CellStyle cellStyle=wb.createCellStyle(); 
		//cellStyle.setWrapText(true);
		//cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
		return true;
	}
	public boolean setStringValue(int row, int col, String value,CellStyle style) {
		if (wb == null)
			return false;
		Row Row = currentSheet.getRow(row);
		if (Row == null) {
			Row = currentSheet.createRow(row);
		}
		Cell cell = Row.getCell(col);
		if (cell == null) {
			cell = Row.createCell((short) col);
		}
		// cell.setEncoding(Cell.ENCODING_UTF_16);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		
		//强制换行
		//CellStyle cellStyle=wb.createCellStyle(); 
		//cellStyle.setWrapText(true);
		
		if(style!=null){
			cell.setCellStyle(style);
		}
		
		
		cell.setCellValue(value);
		return true;
	}
	/**
	 *
	 * @param row
	 * @param col
	 * @param value
	 * @return
	 */

	@SuppressWarnings("deprecation")
	public boolean setNumericValue(int row, int col, double value) {
		if (wb == null)
			return false;

		Row Row = currentSheet.createRow(row);
		Cell cell = Row.createCell((short) col);
		// cell.setEncoding(Cell.ENCODING_UTF_16);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);

		return true;
	}

	/**
	 *
	 * @param row
	 * @param col
	 * @param value
	 * @param fomat
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean setDateValue(int row, int col, Date value, String fomat) {
		if (wb == null)
			return false;

		Row Row = currentSheet.createRow(row);
		Cell cell = Row.createCell((short) col);
		// cell.setEncoding(Cell.ENCODING_UTF_16);
		cell.setCellType(Cell.CELL_TYPE_STRING);

		SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(fomat);
		cell.setCellValue(dateFormat.format(value));

		return true;
	}

	/**
	 *
	 * @param row
	 * @param col
	 * @param value
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean setBooleanValue(int row, int col, boolean value) {
		if (wb == null)
			return false;

		Row Row = currentSheet.createRow(row);
		Cell cell = Row.createCell((short) col);
		// cell.setEncoding(Cell.ENCODING_UTF_16);
		cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
		cell.setCellValue(value);

		return true;
	}

	/**
	 *
	 * @param rowFrom
	 * @param colFrom
	 * @param rowTo
	 * @param colTo
	 * @return
	 */
	public boolean mergeCells(int rowFrom, int colFrom, int rowTo, int colTo) {
		if (wb == null)
			return false;
		
		

		currentSheet.addMergedRegion(new CellRangeAddress(rowFrom, (short) colFrom,
				rowTo, (short) colTo));

		return true;
	}
	public boolean mergeCells(int rowFrom, int colFrom, int rowTo, int colTo,CellStyle style) {
		if (wb == null)
			return false;
		for(int i=rowFrom;i<=rowTo;i++){
			Row row = currentSheet.getRow(i);
			for(int k=colFrom;k<=colTo;k++){
				Cell cell = row.getCell((short) k);
				if(cell!=null && style!=null){
					cell.setCellStyle(style);
				}
			}
		}
		
		
		currentSheet.addMergedRegion(new CellRangeAddress(rowFrom, (short) colFrom,
				rowTo, (short) colTo));

		return true;
	}
	
	public void setCellStyle(int row,int col,CellStyle style){
		if (wb == null)
			return ;
	

		Row row2 = currentSheet.getRow(row);
		if (row2 == null) {
			return ;
		}
		Cell cell = row2.getCell((short) col);
		if (cell == null) {
			return ;
		}
		
		if(style!=null){
			cell.setCellStyle(style);
		}
	}

	/**
	 *
	 * @param row
	 * @param col
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getValue(int row, int col) {
		if (wb == null)
			return "";
		String value = "";

		Row row2 = currentSheet.getRow(row);
		if (row2 == null) {
			return "";
		}
		Cell cell = row2.getCell((short) col);
		if (cell == null) {
			return "";
		}
		int type = cell.getCellType();

		if (type == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		}
//		else if (DateUtil.isCellDateFormatted(cell)) {
//			double d = cell.getNumericCellValue();
//			if (d == 0.0) {
//				value = "";
//			} else {
//				Date date = DateUtil.getJavaDate(d);
//				SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
//						"yyyy/MM/dd");
//				value = dateFormat.format(date);
//			}
//		} 
		else if (type == Cell.CELL_TYPE_NUMERIC) {
			double dvalue = cell.getNumericCellValue();
			value = String.valueOf(dvalue);
		} else if (type == Cell.CELL_TYPE_BOOLEAN) {
			value = cell.getBooleanCellValue() + "";
		} else if (type == Cell.CELL_TYPE_BLANK) {
			value = "";
		} else {
			value = cell.getStringCellValue();
		}

		if (value == null) {
			return "";
		}

		return value.trim();
	}

	/**
	 *
	 * @return
	 * @throws IOException
	 */
	public boolean saveChanges() throws IOException {
		if (wb == null || excelFile == null)
			return false;
		FileOutputStream fileOut = new FileOutputStream(getParent()
				+ File.separator + getFileName());
		wb.write(fileOut);
		fileOut.close();

		return true;
	}

	/**
	 *
	 * @param response
	 * @throws IOException
	 */
	public void downloadExcel(HttpServletResponse response) throws IOException {
		OutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();

		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
		currentSheet = wb.getSheetAt(0);
	}

	public void setCellWithStyle(int a, int b, String s) {
		row = currentSheet.getRow(a);
		if (row == null)
			row = currentSheet.createRow(a);
		cell = row.getCell((short) b);
		if (cell == null) {
			cell = row.createCell((short) b);
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}

		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		s = s == null ? "" : s;

		cell.setCellValue(s);
	}

	public int getRows() {
		return currentSheet.getPhysicalNumberOfRows();
	}

	public int getCols(int row) {
		int cols = 0;
		if (row < 0) {
			cols = currentSheet.getRow(0).getLastCellNum();
		} else {
			cols = currentSheet.getRow(row).getLastCellNum();
		}
		return cols;
	}

	public String getCellContent(int a, int b) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (currentSheet != null) {
			row = currentSheet.getRow(a);
		} else {
			System.out
					.println("****************sheet is not exsist!!!**************8");
		}
		String cellvalue = null;
		if (row != null) {
			cell = row.getCell(b);
		}
		if (cell == null) {
			cellvalue = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {// �Բ���ֵ�Ĵ���
			cellvalue = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {// ������ֵ�Ĵ���
//			if (DateUtil.isCellDateFormatted(cell)) {      
//				//System.out.println("===========DateUtil=============="+DateUtil.isCellDateFormatted(cell));
//				double d = cell.getNumericCellValue();      
//				Date date = DateUtil.getJavaDate(d);      
//				return formatter.format(date);
//				}
			cellvalue = cell.getNumericCellValue() + "";
		} else {
			cellvalue = cell.getStringCellValue();
		}
		if(StringUtil.isNotEmpty(cellvalue)){
			cellvalue = cellvalue.trim();
		}
		// System.out.println("cellvalue====" + cellvalue);
		return cellvalue.replace(".0", "");
	}

	/**
	 * Use to get ExcelUtility.if excel file exists, return,else create and
	 * return
	 *
	 * @param filePath
	 *            excel filePath
	 * @return
	 * @throws Exception 
	 */
	public static ExcelUtility getExcelUtility(String filePath, String sheetName) throws Exception {
		ExcelUtility excelUtil = null;
			excelUtil = new ExcelUtility(filePath);
			if (excelUtil.exists()) {
				return excelUtil;
			}
			if (!excelUtil.exists()) {
				excelUtil.createNewFile();
				if (!excelUtil.isExistSheet(sheetName)) {
					excelUtil.createNewSheet(sheetName);
				} else {
					excelUtil.switchCurrentSheet(sheetName);
				}
				excelUtil.saveChanges();
				return excelUtil;
			}
		return excelUtil;
	}
	/**
	 * set excel title
	 *
	 * @param row
	 * 		   row line
	 * @param col
	 * 		   col line
	 * @param value
	 * 			value
	 */
	public void setTitle(int row,int col,String value){
		if (wb == null)
			return ;
		CellStyle style = wb.createCellStyle();
		Font  font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		Row Row = currentSheet.getRow(row);
		if (Row == null) {
			Row = currentSheet.createRow(row);
		}
		Cell cell = Row.getCell(col);
		if (cell == null) {
			cell = Row.createCell((short) col);
		}
		cell.setCellStyle(style);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}
	/**
	 * set cell background color
	 *
	 * @param row
	 *
	 * @param col
	 *
	 * @param value
	 *
	 */
	public void setColor(int row,int col,String value,short color){
		if (wb == null)
			return ;
		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(color);
		Row Row = currentSheet.getRow(row);
		if (Row == null) {
			Row = currentSheet.createRow(row);
		}
		Cell cell = Row.getCell(col);
		if (cell == null) {
			cell = Row.createCell((short) col);
		}
		cell.setCellStyle(style);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}
}
