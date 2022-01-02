package ext.rdc.standard.portal.constant;

public class StringConstant {
	
	public static  String CONNECTSTR = "-";
	public static  String UNDERLINE = "_";
	public static  String ADD = "+";
	public static  String COMMA = ",";
	public static  String LEFT_BRACKETS = "(";
	public static  String RIGHT_BRACKETS = ")";
	public static  String SPOT = ".";
	public static  String Y = "Y";
	public static  String N = "N";
	public static  String SPRIT = "/";
	public static  String DISPLAY_NAME = "displayName";
	public static  String TILDE = "~";
	public static  String OR_CHAR = "|";
	public static  String OR_CHAR_URL = "%7C";
	public static  String OID_OR_PREFIX = "OR:";
	public static  String VID_VR_PREFIX = "VR:";
	public static  String COLON = ":";
	public static  String GMT_8 = "GMT+8:00";
	public static  String TIME_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static  String DAY_DATE_FORMAT = "yyyy-MM-dd";
	public static  String A = "A";
	public static  String E = "E";
	public static  String F = "F";
	public static String JAVASCRIPT_VOID = "javascript:void(0);";
	
	
	public static String STRING_SPLIT_STR=";;;qqq";
	public static class HttpUrl{
		public static  String WINDCHILL="Windchill";
		
		public static  String INFO_PAGE = "/"+WINDCHILL+"/app/#ptc1/tcomp/infoPage?u8=1&oid=";
		public static  String ATTACHMENTS_DOWNLOAD_DIRECTION_SERVLET = "/"+WINDCHILL+"/servlet/AttachmentsDownloadDirectionServlet";
		public static  String WINDCHILL_APP = "/"+WINDCHILL+"/app";
		public static  String PORTAL_DOC_CREATE = "/"+WINDCHILL+"/ptc1/ext/rdc/standard/portal/docCreate?u8=1&wizardActionClass=com.ptc.windchill.enterprise.doc.forms.CreateDocFormProcessor&wizardActionMethod=execute&actionName=create&portlet=poppedup";
		public static  String PORTAL_SELECTION_PRINCIPLE = "/"+WINDCHILL+"/netmarkets/jsp/ext/rdc/standard/portal/selectionPrinciple.jsp";
	} 
	public static class DBMapping{
		public static String OWNER_KEY_ID="ownership.owner.key.id";
		public static String CONTAINER_KEY_ID="containerReference.key.id";
		public static String MODIFY_STAMP="thePersistInfo.modifyStamp";
		public static String VIEW_ID = "view.key.id";
		public static String BACKUP_NUMBER = "variation2";
		public static String ROLE_A_ID = "roleAObjectRef.key.id";
		public static String ROLE_B_ID = "roleBObjectRef.key.id";
		public static String ROLE_B_BRANCH_ID = "roleBObjectRef.key.branchId";
		public static String THE_INFO_ID = "thePersistInfo.theObjectIdentifier.id";
		public static String ROLE_A_CLASSNAME = "roleAObjectRef.key.classname";
		public static String ROLE_B_CLASSNAME = "roleBObjectRef.key.classname";
		public static String MASTER_ID = "masterReference.key.id";
		public static String BRANCH_ID = "iterationInfo.branchId";
		
	}
	public static class JSONKey{
		public static String MY_TASK_MORE_URL="myTaskMoreUrl";
		public static String NOTICE_MORE_URL="noticekMoreUrl";
		public static String DOWNLOAD_MORE_URL="downloadMoreUrl";
		
		public  static   String MY_TASK = "MY_TASK";
		public  static   String CREATE_FORM = "CREATE_FORM";
		public  static   String MODEL_SELECTION_GUIDE = "MODEL_SELECTION_GUIDE";
		public  static   String NOTICE_CENTRE = "NOTICE_CENTRE";
		public  static   String DOWNLOAD_CENTRE = "DOWNLOAD_CENTRE";
		public  static   String REPORT_CENTRE = "REPORT_CENTRE";
		
		public  static   String REFRESH_BTN_TOOL_TIP = "refreshBtnToolTip";
		
		public  static   String ADVANCED_SEARCH_BTN_TEXT = "advancedSearchBtnText";
		public  static   String SEARCH_BTN_TEXT = "searchBtnText";
		public  static   String SEARCH_TOOLTIP = "searchTooltip";
		
		
		public  static   String NUMBER = "NUMBER";
		public  static   String NAME = "NAME";
		public  static   String PRIMARY = "PRIMARY";
		public  static   String VERSION = "VERSION";
		public  static   String CONTAINER_NAME = "CONTAINER_NAME";
		public  static   String STATE = "STATE";
		public  static   String DESC = "DESC";
		public  static   String MODIFY_TIME = "MODIFY_TIME";
		public  static   String CREATE_TIME = "CREATE_TIME";
		public  static   String TABLE_EMPTY_TEXT = "TABLE_EMPTY_TEXT";
		
		
		public  static   String STANDARD_ORDER_CREATE= "STANDARD_ORDER_CREATE";
		public  static   String MAJOR_STANDARD_ORDER_CREATE= "MAJOR_STANDARD_ORDER_CREATE";
		public  static   String STANDARD_TEST_OUTLINE_ORDER_CREATE= "STANDARD_TEST_OUTLINE_ORDER_CREATE";
		public  static   String STANDARD_TEST_REPORT_ORDER_CREATE= "STANDARD_TEST_REPORT_ORDER_CREATE";
		
		public  static   String STANDARD_ORDER_TRACK_REPORT= "STANDARD_ORDER_TRACK_REPORT";
		
	}
	public static class IntConstant{
		public static int ZERO = 0;
		public static int SIX = 6;
	}
	public static class ContainerName{
		public static String LIBRARY_STANDARD = "标准件库";
	}
	
	public static class FolderName{
		public static String NOTICE_AND_DOWNLOAD = "通知及下载";
	}
	
	public static class JspAction{
		public static  String GET_MYTASK_DATA = "GET_MYTASK_DATA";
		public static  String GET_NOTICE_DATA = "GET_NOTICE_DATA";
		public static  String GET_DOWNLOAD_DATA = "GET_DOWNLOAD_DATA";
		public static  String GET_RESOURCES = "GET_RESOURCES";
		public static  String GET_MORE_DOWNLOAD = "GET_MORE_DOWNLOAD";
		public static  String GET_MORE_NOTICE = "GET_MORE_NOTICE";
		public static  String SEARCH_PART = "SEARCH_PART";
		public static  String CREATE_APPLY = "CREATE_APPLY";
		public static  String REPORT_DATA = "REPORT_DATA";
		public static  String SELECTION_MODEL_DATA = "SELECTION_MODEL_DATA";
	}
	public static class TypeName{
		private static String  TYPE_PREFIX = "com.fawcar.";//""
		public static String TYPE_NOTICE = TYPE_PREFIX+"Notice";
		public static String TYPE_DOWNLOAD = TYPE_PREFIX+"Download";
		public static String TYPE_WTPART = "wt.part.WTPart";
		
		//wt.doc.WTDocument|com.ptc.ReferenceDocument|com.fawcar.RDCReferenceDocument|
		public static String TYPE_WTDOCUMENT = "wt.doc.WTDocument";
		public static String TYPE_REFERENCEDOCUMENT = "com.ptc.ReferenceDocument";
		public static String TYPE_RDCReferenceDocument = "com.fawcar.RDCReferenceDocument";
		public static String TYPE_PROFESSIONAL_STANDARD_PARTS_SQD = "com.fawcar.ProfessionalStandardPartsSQD";
		public static String TYPE_TEST_REPORT = "com.fawcar.TestReport";
		public static String TYPE_SQD_STANDARD_PARTS = "com.fawcar.SQDStandardParts";
		public static String TYPE_APPLICATION_STANDARD_PARTS = "com.fawcar.ApplicationStandardParts";
	}
	
}
