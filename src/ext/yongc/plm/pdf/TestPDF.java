
package ext.yongc.plm.pdf;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


public class TestPDF {

	
//	public static List matchPage(String fileName,String keyword) throws Exception {
//		List items = new ArrayList();
//		PdfReader reader = new PdfReader(fileName);
//		int pageSize = reader.getNumberOfPages();
//		for(int page = 1;page <= pageSize;page++){
//		items.addAll(matchPage(reader,page,keyword));
//		}
//		return items;
//		}
//	public static List matchPage(PdfReader reader, Integer pageNumber,String keyword) throws Exception {
//		KeyWordPositionListener renderListener = new KeyWordPositionListener();
//		renderListener.setKeyword(keyword);
//		PdfReaderContentParser parse = new PdfReaderContentParser(reader);
//		Rectangle rectangle = reader.getPageSize(pageNumber);
//		renderListener.setPageNumber(pageNumber);
//		renderListener.setCurPageSize(rectangle);
//		parse.processContent(pageNumber, renderListener);
//		return findKeywordItems(renderListener,keyword);
//		} 
	
	/**
	 * @description TODO
	 * @param args
	 * @author yge  2018年7月3日上午9:22:35
	 */
	
	public static void main(String[] args) throws Exception {
		PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);//中文设置
		String src = "C:\\Users\\yge\\Desktop\\加工工艺过程卡片1.pdf";
		String src2 = "C:\\Users\\yge\\Desktop\\TEST1工艺文件.pdf";
		String dest = "C:\\Users\\yge\\Desktop\\1111.pdf";
		File file = new File(dest);
        file.getParentFile().mkdirs();
        
		PdfDocument pdfDoc =
			    new PdfDocument(new PdfReader(src), new PdfWriter(dest));
		PdfDocument pdfDoc2 = new PdfDocument(new PdfReader(src2));
		pdfDoc.setDefaultPageSize(PageSize.A4);
		pdfDoc.removePage(1);
		pdfDoc2.copyPagesTo(1, 1, pdfDoc, 1);
	
		PdfPage page = pdfDoc.getPage(1);
		PdfNumber  rotate = page.getPdfObject().getAsNumber(PdfName.Rotate);
		System.out.println("rotate=>"+rotate);
		 if (rotate == null) {
			 page.setPageRotationInverseMatrixWritten();
			 System.out.println(page.isPageRotationInverseMatrixWritten());
//             page.put(PdfName.Rotate, new PdfNumber(90));
         }
         else {
        	 System.out.println("rotate.intValue()="+rotate.intValue());
//             page.put(PdfName.Rotate, new PdfNumber((rotate.intValue() + 90) % 360));
         }
		
		Document document = new Document(pdfDoc);
		
		
	
		
		
		System.out.println(pdfDoc.getNumberOfPages());
		Text text = new Text("文件名称签名测试");
		text.setBackgroundColor(Color.WHITE); 
		Paragraph p = new Paragraph(text);
//      p.setRotationAngle(180);
		
	
		p.setFont(font).setFontSize(17);
		document.add(p.setFixedPosition(1, 290, 550, 600));
		
		Text text2 = new Text("编制签名测试");
		
		Paragraph p2 = new Paragraph(text2);
		p2.setFont(font).setFontSize(17);
		document.add(p2.setFixedPosition(1, 290, 495, 600));
		
		Text text3 = new Text("会签签名测试");
		Paragraph p3 = new Paragraph(text3);
		p3.setFont(font).setFontSize(17);
		document.add(p3.setFixedPosition(1, 290, 437, 600));
		
		Text text4 = new Text("审核签名测试");
		Paragraph p4 = new Paragraph(text4);
		p4.setFont(font).setFontSize(17); 
		document.add(p4.setFixedPosition(1, 290, 380, 600));
		
		Text text5 = new Text("文件编号签名测试");
		Paragraph p5 = new Paragraph(text5);
		p5.setFont(font).setFontSize(17);
		document.add(p5.setFixedPosition(1, 750, 550, 600));
		
		Text text6 = new Text("校对签名测试");
		Paragraph p6 = new Paragraph(text6);
		p6.setFont(font).setFontSize(17);
		document.add(p6.setFixedPosition(1, 750, 495, 600));
		
		Text text7 = new Text("标审签名测试");
		Paragraph p7 = new Paragraph(text7);
		
		p7.setFont(font).setFontSize(17);
		document.add(p7.setFixedPosition(1, 750, 437, 600));
		
		Text text8 = new Text("批准签名测试");
		Paragraph p8 = new Paragraph(text8);
		p8.setFont(font).setFontSize(17);
		document.add(p8.setFixedPosition(1, 750, 380, 600));
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Text text9 = new Text(df.format(new Date()));
		Paragraph p9 = new Paragraph(text9);
		
		p9.setFont(font).setFontSize(17);
		document.add(p9.setFixedPosition(1, 520, 230, 600));
		
	
		 
		
		pdfDoc.close();
		document.close();
	}
	 /**setWidths()函数举例*/
    public static PdfPTable createTable1()throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(288/ 5.23f);
        table.setWidths(new int[]{2, 1, 1});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 1"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    /**setWidths()函数举例*/
    public static PdfPTable createTable2()throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(288);
        table.setLockedWidth(true);
        table.setWidths(new float[]{2, 1, 1});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 2"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    /**合并单元格setColspan()函数举例*/
    public static PdfPTable createTable3()throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{ 2, 1, 1});
        table.setWidthPercentage(85f);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 3"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    /**setWidthPercentage()方法举例*/
    public static PdfPTable createTable4()throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        Rectangle rect = new Rectangle(523, 770);
        //rect表示PageSize页面的大小，主要用于检测各列宽度之各是否超过边界，如果超过，则按比例重新赋值
        table.setWidthPercentage(new float[]{ 144, 72, 72}, rect);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 4"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    /**setTotalWidth()方法举例*/
    public static PdfPTable createTable5()throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(new float[]{ 144, 72, 72});
        table.setLockedWidth(true);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 5"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
}
