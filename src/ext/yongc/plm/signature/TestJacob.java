/**
 * ext.yongc.plm.signature.TestJacob.java
 * @Author yge
 * 2017年8月2日下午6:55:03
 * Comment 
 */
package ext.yongc.plm.signature;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import ext.yongc.plm.constants.SigConstants;


public class TestJacob {
	
	private ActiveXComponent excel = null;   //excel 运行程序对象 
	 private  Dispatch workbooks = null;  //工作簿对象  
	 private Dispatch workbook = null; //具体工作簿  
	 private Dispatch sheets = null;// 获得sheets集合对象  
	 private Dispatch currentSheet = null;// 当前sheet  
	 
	// word文档
	 private Dispatch doc = null;
	// word运行程序对象
	 private ActiveXComponent word = null;
	    // 所有word文档集合
	     private Dispatch documents = null;
	     private  Dispatch selection = null;
	  public TestJacob(){
		
	  }
	  
	  public boolean find(String toFindText) {
		          if (toFindText == null || toFindText.equals(""))
		              return false;
		          // 从selection所在位置开始查询
		           Dispatch find = word.call(selection, "Find").toDispatch();
		           // 设置要查找的内容
		          Dispatch.put(find, "Text", toFindText);
		         // 向前查找
		          Dispatch.put(find, "Forward", "True");
		           // 设置格式
		           Dispatch.put(find, "Format", "True");
		           // 大小写匹配
		           Dispatch.put(find, "MatchCase", "True");
		           // 全字匹配
		           Dispatch.put(find, "MatchWholeWord", "True");
		          // 查找并选中
		           return Dispatch.call(find, "Execute").getBoolean();
		       }
		   
		
		      public boolean replaceText(String toFindText, String newText) {
		          if (!find(toFindText))
		              return false;
		          Dispatch.put(selection, "Text", newText);
		          return true;
		      }
	  public void test2(){
		  try{
			  ComThread.InitSTA();
		      if (word == null) {
		        word = new ActiveXComponent("Word.Application");
	             word.setProperty("Visible", new Variant(false));
		           }
		           if (documents == null)
		               documents = word.getProperty("Documents").toDispatch();
		           
		  Dispatch doc = Dispatch.call(documents, "Open", "C:\\Users\\yge\\Desktop\\设计文件.doc").toDispatch();
		  selection = Dispatch.get(word, "Selection").toDispatch();
		  
		  String addString = "<**"+SigConstants.FILE_NAME+"**>";
		
		  
		  
		  Dispatch bookMarks = word.call(doc, "Bookmarks").toDispatch();
		  if(find(addString)){
			  System.out.println("--------find ---------");
//			  Dispatch.call(bookMarks, "Add", SigConstants.FILE_NAME, selection);
		  }
		  System.out.println(find(addString));
	        int bCount = Dispatch.get(bookMarks, "Count").getInt();  //获取书签数
	        System.out.println("bCount----->"+bCount);
	      //将书签列表存放到list + map 结构中    
	         for (int i = 1; i <= bCount; i++){     
	        	 System.out.println("i----->"+i);
	             Map bookMark = new HashMap();   //创建Map()
	             Dispatch items = Dispatch.call(bookMarks, "Item", i).toDispatch();   
	             String bookMarkKey = String.valueOf(Dispatch.get(items, "Name").getString()).replaceAll("null", "");   //读取书签命名
	             Dispatch range = Dispatch.get(items, "Range").toDispatch();
	             String bookMarkValue = String.valueOf(Dispatch.get(range, "Text").getString()).replaceAll("null", ""); //读取书签文本
	             String MarkKey=bookMarkKey;
	             String MarkValue=bookMarkValue;
	             System.out.println("MarkKey----->"+MarkKey);
	             System.out.println("MarkValue----->"+MarkValue);
	         }
	         
	               
	           
	            
	             boolean bookMarkExist = word.call(bookMarks, "Exists", SigConstants.FILE_NAME)
	                     .toBoolean();
	             System.out.println("bookMarkExist---->"+bookMarkExist);
	             if (bookMarkExist) {
	                 
	                 Dispatch rangeItem = Dispatch.call(bookMarks, "Item", SigConstants.FILE_NAME)
	                         .toDispatch();
	                 Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
	           
                     Dispatch.put(range, "Text", "343434");
	                
                     //添加书签
//		             Dispatch.call(bookMarks, "Add", SigConstants.FILE_NAME, range);
		             
	             }else{
	            	// replaceText(addString, "232323");
	            	
	            	// Dispatch.call(bookMarks, "Add", SigConstants.FILE_NAME, selection);
	             }
	             
		  
		  Dispatch.call(doc, "SaveAs", "C:\\Users\\yge\\Desktop\\3333.doc");
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  if (doc != null) {
	              Dispatch.call(doc, "Save");
	              Dispatch.call(doc, "Close");
	               doc = null;
	         }
  if (word != null) {
	            Dispatch.call(word, "Quit");
	             word = null;
	          }
	
        ComThread.Release();
        System.out.println(" ------- closed all --------------");
		  }
	  }
	  public void test(){
		  try{
			  ComThread.InitSTA();
		      if (word == null) {
		        word = new ActiveXComponent("Word.Application");
	             word.setProperty("Visible", new Variant(false));
		           }
		           if (documents == null)
		               documents = word.getProperty("Documents").toDispatch();
		           
		  Dispatch doc = Dispatch.call(documents, "Open", "C:\\Users\\yge\\Desktop\\2222.doc").toDispatch();
		  Dispatch bookMarks = word.call(doc, "Bookmarks").toDispatch();
	        int bCount = Dispatch.get(bookMarks, "Count").getInt();  //获取书签数
	        System.out.println("bCount----->"+bCount);
	      //将书签列表存放到list + map 结构中    
	         for (int i = 1; i <= bCount; i++){     
	        	 System.out.println("i----->"+i);
	             Map bookMark = new HashMap();   //创建Map()
	             Dispatch items = Dispatch.call(bookMarks, "Item", i).toDispatch();   
	             String bookMarkKey = String.valueOf(Dispatch.get(items, "Name").getString()).replaceAll("null", "");   //读取书签命名
	             Dispatch range = Dispatch.get(items, "Range").toDispatch();
	             String bookMarkValue = String.valueOf(Dispatch.get(range, "Text").getString()).replaceAll("null", ""); //读取书签文本
	             String MarkKey=bookMarkKey;
	             String MarkValue=bookMarkValue;
	             System.out.println("MarkKey----->"+MarkKey);
	             System.out.println("MarkValue----->"+MarkValue);
	           //书签名并替换的内容
	                if( MarkKey.equals("设计签字"))//书签名为xm
	                  {
	                      MarkValue ="张三";  //该xm书签处插入张三；
	                      
	                      
//	                      Dispatch.put(range, "Text", MarkValue);
	                  }
	                if( MarkKey.equals("lxdh")){//书签名为lxdh
	                    MarkValue ="10086";  //在lxdh书签处插入10086
	                }
	                if( MarkKey.equals("****")){  //书签名自定义
	                    MarkValue ="*****";   //在该书签插入自定义内容
	                }
	                   
	         }
	         
	               
	            
	            
	             boolean bookMarkExist = word.call(bookMarks, "Exists", "校核签字")
	                     .toBoolean();
	             System.out.println("bookMarkExist---->"+bookMarkExist);
	             if (bookMarkExist) {
	                 
	                 Dispatch rangeItem = Dispatch.call(bookMarks, "Item", "校核签字")
	                         .toDispatch();
	                 Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
//	                 Dispatch.put(range, "AddPicture", "E:\\Work\\eclipse\\workspase\\YONGC-PLM\\codebase\\ext\\yongc\\plm\\signature\\chenxj.bmp");
	               
	                 Dispatch temp = Dispatch.get(range, "InLineShapes").toDispatch();
	                 System.out.println("temp----->"+temp);
	                 if(temp!=null){
//	                	 Dispatch shape = Dispatch.call(temp, "Item",1).toDispatch(); 
//                         Dispatch imageRange = Dispatch.get(shape, "Range").toDispatch(); 
                         Dispatch.call(range, "Delete"); 
                         Dispatch.put(range, "Text", "343434");
	                 }
//	                 File imageFile = new File("E:\\Work\\eclipse\\workspase\\YONGC-PLM\\codebase\\ext\\yongc\\plm\\signature\\陈洪旭.bmp");
//	                 Dispatch picture= Dispatch.call(Dispatch.get(range, "InLineShapes").toDispatch(), "AddPicture", "E:\\Work\\eclipse\\workspase\\YONGC-PLM\\codebase\\ext\\yongc\\plm\\signature\\陈洪旭.bmp").toDispatch();
//	                 Dispatch.put(picture, "Width", 35);
//	                 Dispatch.put(picture, "Height", 20);
	                 
	                 bookMarkExist = word.call(bookMarks, "Exists", "校核签字")
		                     .toBoolean();
		             System.out.println("bookMarkExist222---->"+bookMarkExist);
		             if(!bookMarkExist){
		            	 Dispatch.call(bookMarks, "Add", "校核签字", range);
		             }
	                 
	             }
	             
		  
		  Dispatch.call(doc, "SaveAs", "C:\\Users\\yge\\Desktop\\3333.doc");
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  if (doc != null) {
	              Dispatch.call(doc, "Save");
	              Dispatch.call(doc, "Close");
	               doc = null;
	         }
  if (word != null) {
	            Dispatch.call(word, "Quit");
	             word = null;
	          }
	
        ComThread.Release();
        System.out.println(" ------- closed all --------------");
		  }
	  }
	  private void initExcelApp(){
		  ComThread.InitSTA();  
		    if(excel==null)  
		    	excel = new ActiveXComponent("Excel.Application"); //Excel对象  
		    excel.setProperty("Visible", new Variant("false"));//设置是否显示打开excel  
		    if(workbooks==null)  
                workbooks = excel.getProperty("Workbooks").toDispatch(); //打开具体工作簿  
                
	  }
	  public void testExcel(){
		  initExcelApp();
		   
		  workbook = Dispatch.call(workbooks, "Open","C:\\Users\\yge\\Desktop\\分析文件1-RAMS&LCC分析报告.xls").toDispatch();
		  Dispatch.put(workbook, "CheckCompatibility", false); //取消兼容性检查，在保存或者另存为时改检查会导致弹窗 
		  sheets = Dispatch.get(workbook, "sheets").toDispatch();  
		  Dispatch sheet= Dispatch.invoke(sheets, "Item", Dispatch.Get, new Object[]{2}, new int[1]).toDispatch();
		  System.out.println("sheet name----->"+Dispatch.get(sheet, "name").toString());
		  Dispatch Shapes = Dispatch.get(sheet,"Shapes").toDispatch();
//		  Dispatch Shapes = Dispatch.invoke(sheet, "Shapes",Dispatch.Get,new Object[]{false,true,"C:\\Users\\yge\\Desktop\\陈洪旭.bmp"},new int[1]).toDispatch();
//		  Dispatch.call(Shapes, "AddPicture");
		  //		  Dispatch.call(Shapes, "AddPicture","C:\\Users\\yge\\Desktop\\陈洪旭.bmp");
		  
		  System.out.println("Shapes----->"+Shapes);
//		  Dispatch d= Dispatch.call(Shapes, "AddPicture", "C:\\Users\\yge\\Desktop\\陈洪旭.bmp",true,true,225.8,37,45,15).toDispatch();
//		  Dispatch.put(d, "Name", 225.8+37);
		  //		  System.out.println("d-->"+d);
		  
		  //删除
		  try{
		  Dispatch delete= Dispatch.invoke(Shapes, "Range", Dispatch.Get, new Object[]{"262.8"}, new int[1]).toDispatch();
		  System.out.println("delete------>"+delete);
		  if(delete!=null){
			  Dispatch.call(delete, "Delete");
		  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  //向单元格设值
		  Dispatch cell = Dispatch.invoke(sheet, "Range",  
	                Dispatch.Get, new Object[] { "F2" }, new int[1])  
	                .toDispatch();  
	        Dispatch.put(cell, "Value", "张三");  
	        cell = Dispatch.invoke(sheet, "Range",  
	                Dispatch.Get, new Object[] { "G2" }, new int[1])  
	                .toDispatch();  
	        Dispatch.put(cell, "Value", "2017-8-5");  
	        
	        //插入图片
	        Dispatch.call(sheet, "Select");
	        cell = Dispatch.invoke(sheet, "Range",  
	                Dispatch.Get, new Object[] { "F3" }, new int[1])  
	                .toDispatch();  
	        Dispatch.put(cell, "Value", "");  
	        Dispatch.call(cell, "Select"); //定位插入图片的具体位置
	        Dispatch select = Dispatch.call(sheet, "Pictures").toDispatch();
	       System.out.println("top---->"+Dispatch.get(cell, "Top"));
	       System.out.println("Left---->"+Dispatch.get(cell, "Left"));
	        System.out.println("select-------->"+select);
//	        if(select!=null){
//	        	Dispatch.call(select, "Delete"); 
//	        	  Dispatch.put(cell, "Value", "李四");  
//	        }
	        
//	        Dispatch picture = Dispatch.call(select, "Insert","C:\\Users\\yge\\Desktop\\陈洪旭.bmp",true).toDispatch();
//	        Dispatch.put(picture, "Width", 30);
//            Dispatch.put(picture, "Height", 15);
	        Dispatch.call(workbook, "SaveAs","C:\\Users\\yge\\Desktop\\1121212.xls");  
	        closedExcel();
	  }
	  private void closedExcel(){
		  if (workbook != null) {
	             Dispatch.call(workbook, "Save");
	             Dispatch.call(workbook, "Close");
	             workbook = null;
	        }
	       if (excel != null) {
	           Dispatch.call(excel, "Quit");
	           excel = null;
	         }
	            ComThread.Release();
	  }
       public static void main(String[] args){
    	   System.out.println("------- start ------------");
    	   TestJacob test = new TestJacob();
    	   test.test2();
    	   System.out.println("------- end ------------");
       }
}
