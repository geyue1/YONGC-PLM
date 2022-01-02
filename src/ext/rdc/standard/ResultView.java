/**
 * ext.rdc.standard.View.java
 * @Author yge
 * 2019年9月23日下午3:48:35
 * Comment 
 */
package ext.rdc.standard;

public enum ResultView {
	LUOZHU("luozhu","",
			new String[]{"surface","yiqi","material"},
			new String[]{"surface","yiqi","material","effectDate","supplier"}),
	
	LUOSHUAN_LUODING("luoding","",
			new String[]{"垫圈厚度","surface"},
			new String[]{"垫圈厚度","surface","yiqi","material","supplier","effectDate"}),
	
	LUOMU("","",
			new String[]{""},new String[]{""}),
	
	DIANQUAN("","",
			new String[]{""},new String[]{""});
	
   private String name;
   private String tableId;
   private String[] defaultColumn;
   private String[] allColumn;
   
   private ResultView(String name,String tableId,String[] defaultColumn,String[] allColumn){
	   this.name = name;
	   this.tableId = tableId;
	   this.defaultColumn = defaultColumn;
	   this.allColumn = allColumn;
   }
   public String toString(){
	   return this.name;
   }
   public String getName(){
	   return this.name;
   }
   public String getTableId(){
	   return this.tableId;
   }
   public String[] getDefaultColumn(){
	   return this.defaultColumn;
   }
   public String[] getAllColumn(){
	   return this.allColumn;
   }
   
   public ResultView getViewByName(String name){
	   for (ResultView view : ResultView.values()) {  
		   if(view.getName().equals(name)){
			   return view;
		   }
	   }
	   return null;
   }
   public ResultView getViewByTableId(String tableId){
	   for (ResultView view : ResultView.values()) {  
		   if(view.getTableId().equals(tableId)){
			   return view;
		   }
	   }
	   return null;
   }
}
