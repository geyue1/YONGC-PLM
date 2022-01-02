/* bcwti
 *
 * Copyright (c) 2013 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */
package com.ptc.windchill.enterprise.search.client;

import wt.util.resource.RBComment;
import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * searchResource message resource bundle [English/US]
 */
@RBUUID("com.ptc.windchill.enterprise.search.client.searchClientResource")
public final class searchClientResource_zh_CN extends WTListResourceBundle {
   @RBEntry("在会话期间记住选定的对象。")
   @RBComment("Decscription for preference 'remember object selection'.")
   public static final String PRIVATE_CONSTANT_0 = "STICKY_PICKER_SHORT_DESCRIPTION";

   @RBEntry("在会话期间记住选定的对象。")
   @RBComment("Decscription for preference 'remember object selection'.")
   public static final String PRIVATE_CONSTANT_1 = "STICKY_PICKER_DESCRIPTION";

   @RBEntry("记住选定的对象")
   @RBComment("Preference Remember object selection.")
   public static final String PRIVATE_CONSTANT_2 = "STICKY_PICKER";

   @RBEntry("此首选项用来将访问控制权限应用于搜索结果。")
   @RBComment("This preference is used to apply access control on search results.")
   public static final String PRIVATE_CONSTANT_3 = "ACCESS_CONTROL_FLAG_SHORT_DESCRIPTION";

   @RBEntry("此首选项用来将访问控制权限应用于搜索结果。")
   @RBComment("This preference is used to apply access control on search results.")
   public static final String PRIVATE_CONSTANT_4 = "ACCESS_CONTROL_FLAG_DESCRIPTION";

   @RBEntry("将访问控制权限应用于搜索结果")
   @RBComment("Apply Access Control to Search Results")
   public static final String PRIVATE_CONSTANT_5 = "ACCESS_CONTROL_FLAG";

   @RBEntry("此首选项用来将访问控制权限应用于搜索结果。")
   @RBComment("This preference is used to apply access control on search results.")
   public static final String PRIVATE_CONSTANT_6 = "ACCESS_CONTROL_FLAG_LONGDESCRIPTION";

   @RBEntry("使用 * 作为通配符返回更多结果。")
   @RBComment("Wildcard suggestion for keyword field")
   public static final String PRIVATE_CONSTANT_7 = "WILDCARD_SUGGESSTION";

   @RBEntry("帮助")
   @RBComment("Search page help icon tooltip")
   public static final String PRIVATE_CONSTANT_8 = "HELP";

   @RBEntry("搜索")
   @RBComment("Label for the page title of the search page.")
   public static final String SEARCH_LABEL = "SEARCH_LABEL";

   @RBEntry("清除")
   @RBComment("Label for the clear button .")
   public static final String PRIVATE_CONSTANT_9 = "CLEAR_BUTTON";

   @RBEntry("搜索")
   @RBComment("Label for the type property on the search page.")
   public static final String PRIVATE_CONSTANT_10 = "SEARCH_FOR";

   @RBEntry("首选项")
   @RBComment("Label for the \"Preferences\" link on search page.")
   public static final String PRIVATE_CONSTANT_11 = "PREFERENCES_LNK";

   @RBEntry("打开用户首选项")
   @RBComment("Tooltip for the \"Preferences\" link on search page.")
   public static final String PRIVATE_CONSTANT_12 = "PREFERENCES_LNK_TT";

   @RBEntry("查找")
   @RBComment("Label for the \"Show Results\" input field")
   public static final String PRIVATE_CONSTANT_13 = "RANGE_OF_SEARCH_PROPTY";

   @RBEntry("符合以上任一条件")
   @RBComment("Label for one of the values in the \"Show Results\" pull down list")
   public static final String PRIVATE_CONSTANT_14 = "RANGE_OF_SEARCH_VALUE_OR";

   @RBEntry("符合以上所有条件")
   @RBComment("Label for one of the values in the \"Show Results\" pull down list")
   public static final String PRIVATE_CONSTANT_15 = "RANGE_OF_SEARCH_VALUE_AND";

   @RBEntry("定义搜索范围")
   @RBComment("Label for search scope <HR> separator of the search criteria")
   public static final String PRIVATE_CONSTANT_16 = "SCOPE_SEPARATOR_LABEL";

   @RBEntry("选择搜索条件")
   @RBComment("Label for search criteria <HR> separator of the search criteria")
   public static final String PRIVATE_CONSTANT_17 = "CRITERIA_SEPARATOR_LABEL";

   @RBEntry("所有上下文")
   @RBComment("The default element in the container drop down list on search page.")
   public static final String PRIVATE_CONSTANT_18 = "ALL_CONTAINERS";

   @RBEntry("所有项目")
   @RBComment("Label for All Projects.")
   public static final String PRIVATE_CONSTANT_19 = "ALL_PROJECTS";

   @RBEntry("所有产品")
   @RBComment("Label for All Products.")
   public static final String PRIVATE_CONSTANT_20 = "ALL_PRODUCTS";

   @RBEntry("所有存储库")
   @RBComment("Label for All Libraries.")
   public static final String PRIVATE_CONSTANT_21 = "ALL_LIBRARIES";

   @RBEntry("任何关系")
   @RBComment("The default value for relationship on search in network page.")
   public static final String PRIVATE_CONSTANT_22 = "NO_RELATIONS";

   @RBEntry("上下文")
   @RBComment("Label for the property \"Search In:\" on the search page.")
   public static final String PRIVATE_CONSTANT_23 = "CONTAINER_TYPE_PROPTY";

   @RBEntry("我是成员")
   @RBComment("Label for the membership checkbox on the search page")
   public static final String PRIVATE_CONSTANT_24 = "MEMBER_OF_CONTAINER_CHKBX";

   @RBEntry("在我的组织中")
   @RBComment("Label for the search only in contexts in my organization checkbox on the search page")
   public static final String PRIVATE_CONSTANT_25 = "SEARCH_IN_USER_ORG_CHKBX";

   @RBEntry("每页结果数")
   @RBComment("Label for the \"Results Per Page:\" input field")
   public static final String PRIVATE_CONSTANT_26 = "ADHOC_PAGE_COUNT_PROPTY";

   @RBEntry("分类搜索")
   @RBComment("Label for the \"Classification Search\" link on search page.")
   public static final String PRIVATE_CONSTANT_27 = "CLASSIFICATION_SEARCH_LNK";

   @RBEntry("打开分类搜索")
   @RBComment("Tooltip for the \"Classification Search\" link on search page.")
   public static final String PRIVATE_CONSTANT_28 = "CLASSIFICATION_SEARCH_LNK_TT";

   @RBEntry("高级搜索")
   @RBComment("Label for the page title of the advanced search page.")
   public static final String ADVANCED_SEARCH_LABEL = "ADVANCED_SEARCH_LABEL";

   @RBEntry("自定义...")
   @RBComment("Label for the link to the customize saved search table.")
   public static final String PRIVATE_CONSTANT_29 = "CUSTOMIZE_LINK_LABEL";

   @RBEntry("自定义保存的搜索列表")
   @RBComment("Tool tip for the link to the customize saved search table.")
   public static final String PRIVATE_CONSTANT_30 = "CUSTOMIZE_LINK_TT";

   @RBEntry("组")
   @RBComment("Label for header of groups table in saved search create clerk.")
   public static final String PRIVATE_CONSTANT_31 = "GROUP_TABLE_HEADER";

   @RBEntry("添加")
   @RBComment("Label for 'Add' action on the Group Access table of saved search create clerk.")
   public static final String PRIVATE_CONSTANT_32 = "ADD_GROUP_LABEL";

   @RBEntry("为保存的搜索添加组")
   @RBComment("Tool tip for 'Add' action on the Group Access table of saved search create clerk.")
   public static final String PRIVATE_CONSTANT_33 = "ADD_GROUP_TT";

   @RBEntry("组访问权限")
   @RBComment("Label for the Group Access Tab in the saved search create clerk.")
   public static final String PRIVATE_CONSTANT_34 = "GROUP_ACCESS_TAB_LABEL";

   @RBEntry("设置此搜索的组访问权限")
   @RBComment("Tool tip for the Group Access Tab in the saved search create clerk.")
   public static final String PRIVATE_CONSTANT_35 = "GROUP_ACCESS_TAB_TT";

   @RBEntry("- 选取一个操作 -")
   @RBComment("Label for table action dropdown list.")
   public static final String PRIVATE_CONSTANT_36 = "TABLE_ACTION_LIST_PROMPT";

   @RBEntry("删除此搜索")
   @RBComment("Label for delete action in customize saved search table.")
   public static final String PRIVATE_CONSTANT_37 = "DELETE_LABEL";

   @RBEntry("显示")
   @RBComment("Label for show action in customize saved search table.")
   public static final String PRIVATE_CONSTANT_38 = "SHOW_LABEL";

   @RBEntry("隐藏")
   @RBComment("Label for hide action in customize saved search table.")
   public static final String PRIVATE_CONSTANT_39 = "HIDE_LABEL";

   @RBEntry("保存的搜索")
   @RBComment("Label for header of customize saved search table.")
   public static final String PRIVATE_CONSTANT_40 = "CUSTOMIZE_SAVED_SEARCH_TABLE_LABEL";

   @RBEntry("在“保存的搜索”列表中显示")
   @RBComment("Label for display search check box on saved search create clerk.")
   public static final String PRIVATE_CONSTANT_41 = "DISPLAY_SEARCH_LABEL";

   @RBEntry("导出至文件")
   @RBComment("Label for the export button.")
   public static final String PRIVATE_CONSTANT_42 = "EXPORT_TO_FILE_LABEL";

   @RBEntry("导出至文件")
   @RBComment("Tool tip for the export button.")
   public static final String PRIVATE_CONSTANT_43 = "EXPORT_TO_FILE_TT";

   @RBEntry("将导出的文件另存为")
   @RBComment("Label for the File name in the export wizard")
   public static final String PRIVATE_CONSTANT_44 = "EXPORT_FILE_SAVE";

   @RBEntry("格式:")
   @RBComment("Label for the File format in the export wizard")
   public static final String PRIVATE_CONSTANT_45 = "EXPORT_FILE_FORMAT";

   @RBEntry("csv")
   @RBComment("Label for csv format to export")
   public static final String PRIVATE_CONSTANT_46 = "EXPORT_FORMAT_CSV";

   @RBEntry("xml")
   @RBComment("Label for xml format to export")
   public static final String PRIVATE_CONSTANT_47 = "EXPORT_FORMAT_XML";

   @RBEntry("html 报告")
   @RBComment("Label for html format to export")
   public static final String PRIVATE_CONSTANT_48 = "EXPORT_FORMAT_HTML";

   @RBEntry("xls 报告")
   @RBComment("Label for xls format to export")
   public static final String PRIVATE_CONSTANT_49 = "EXPORT_FORMAT_XLS";

   @RBEntry("xls")
   @RBComment("Label for xls format to SUMA export")
   public static final String PRIVATE_CONSTANT_50 = "EXPORT_FORMAT_SUMA_XLS";

   @RBEntry("正在下载导出文件...")
   @RBComment("Label for downloading the export file")
   public static final String PRIVATE_CONSTANT_51 = "ACT_LBL_DOWNLOAD";

   @RBEntry("完成后请关闭窗口。")
   @RBComment("Close window message")
   public static final String PRIVATE_CONSTANT_52 = "ACT_LBL_CLOSE_MSG";

   @RBEntry("等于")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_53 = "OP_EQ";

   @RBEntry("不等于")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_54 = "OP_NE";

   @RBEntry("小于")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_55 = "OP_LT";

   @RBEntry("小于等于")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_56 = "OP_LE";

   @RBEntry("大于")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_57 = "OP_GT";

   @RBEntry("大于等于")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_58 = "OP_GE";

   @RBEntry("查找要删除的已保存搜索")
   @RBComment("Label for the \"Administrative Delete\" action for saved searches.")
   public static final String PRIVATE_CONSTANT_59 = "ADMIN_DELETE";

   @RBEntry("选取对象类型")
   @RBComment("Label for the object type picker on the simple search page.")
   public static final String PRIVATE_CONSTANT_60 = "CHANGE_OBJECT_TYPES";

   @RBEntry("选择")
   @RBComment("Button to select a saved search and populate criteria")
   public static final String PRIVATE_CONSTANT_61 = "SELECT_BTN";

   @RBEntry("-- 选择 --")
   @RBComment("Label for the default element for the \"Criteria\" pulldown on the Advanced Search page")
   public static final String PRIVATE_CONSTANT_62 = "SELECT_CRITERION";

   @RBEntry("关闭")
   @RBComment("Label for an action button that closes a window")
   public static final String PRIVATE_CONSTANT_63 = "CLOSE_BUTTON";

   @RBEntry("查找要删除的已保存搜索")
   @RBComment("Label for the saved search administrative delete clerk")
   public static final String ADMIN_DELETE_PICKER_LABEL = "ADMIN_DELETE_PICKER_LABEL";

   @RBEntry("创建者")
   @RBComment("Label for the saved search admin delete picker for created by")
   public static final String PRIVATE_CONSTANT_64 = "ADMIN_DELETE_CREATED_BY_LABEL";

   @RBEntry("创建时间")
   @RBComment("Label for the saved search admin delete picker for created on")
   public static final String PRIVATE_CONSTANT_65 = "ADMIN_DELETE_CREATED_ON_LABEL";

   @RBEntry("上次修改时间")
   @RBComment("Label for the saved search admin delete picker for last modified criteria")
   public static final String PRIVATE_CONSTANT_66 = "ADMIN_DELETE_LAST_UPDATED_LABEL";

   @RBEntry("创建者")
   @RBComment("Label for the saved search customize table \"created by\" column")
   public static final String PRIVATE_CONSTANT_67 = "CREATED_BY_LABEL";

   @RBEntry("具有所有权的客户端")
   @RBComment("Label for the saved search customize table owningclient (owner) column")
   public static final String PRIVATE_CONSTANT_68 = "QUERY_OWNING_CLIENT";

   @RBEntry("组访问权限")
   @RBComment("Label for the saved search customize table group access column")
   public static final String PRIVATE_CONSTANT_69 = "QUERY_GROUP_ACCESS";

   @RBEntry("范围")
   @RBComment("Label for the saved search table scope column")
   public static final String PRIVATE_CONSTANT_70 = "SAVED_QUERY_SCOPE";

   @RBEntry("名称")
   @RBComment("Label for the saved search customize table name column")
   public static final String PRIVATE_CONSTANT_71 = "NAME_LABEL";

   @RBEntry("显示")
   @RBComment("Label for the saved search customize table show column")
   public static final String PRIVATE_CONSTANT_72 = "QUERY_SHOW";

   @RBEntry("所有适用对象类型")
   @RBComment("Label for the type property")
   public static final String PRIVATE_CONSTANT_73 = "ALL_ITEMS";

   @RBEntry("名称")
   @RBComment("Label for the name attribute")
   public static final String PRIVATE_CONSTANT_74 = "NAME_ATTRIBUTE";

   @RBEntry("编号")
   @RBComment("Label for the number attribute")
   public static final String PRIVATE_CONSTANT_75 = "NUMBER_ATTRIBUTE";

   @RBEntry("上次修改时间")
   @RBComment("Label for the last modified attribute")
   public static final String PRIVATE_CONSTANT_76 = "LAST_UPDATED_ATTRIBUTE";

   @RBEntry("自")
   @RBComment("Label for the From date attribute")
   public static final String PRIVATE_CONSTANT_77 = "FROM_DATE_ATTRIBUTE";

   @RBEntry("至")
   @RBComment("Label for the To date attribute")
   public static final String PRIVATE_CONSTANT_78 = "TO_DATE_ATTRIBUTE";

   @RBEntry("创建时间")
   @RBComment("Label for the created attribute")
   public static final String PRIVATE_CONSTANT_79 = "CREATED_ATTRIBUTE";

   @RBEntry("全部")
   @RBComment("All")
   public static final String PRIVATE_CONSTANT_80 = "VERSION_ALL";

   @RBEntry("最新")
   @RBComment("Latest")
   public static final String PRIVATE_CONSTANT_81 = "VERSION_LATEST";

   @RBEntry("关键字")
   @RBComment("Keyword")
   public static final String PRIVATE_CONSTANT_82 = "KEYWORD_LABEL";

   @RBEntry("保存的搜索")
   @RBComment("Saved Searches")
   public static final String PRIVATE_CONSTANT_83 = "SAVED_SEARCH_LABEL";

   @RBEntry("-- 选择 --")
   @RBComment("-- Select a Search --")
   public static final String PRIVATE_CONSTANT_84 = "SELECT_SEARCH_LABEL";

   @RBEntry("修订版本")
   @RBComment("Version")
   public static final String PRIVATE_CONSTANT_85 = "VERSION_LABEL";

   @RBEntry("选择")
   @RBComment("Select")
   public static final String PRIVATE_CONSTANT_86 = "SELECT_LABEL";

   @RBEntry("指定")
   @RBComment("Specify")
   public static final String PRIVATE_CONSTANT_87 = "SPECIFY_LABEL";

   @RBEntry("小版本")
   @RBComment("Iteration")
   public static final String PRIVATE_CONSTANT_88 = "ITERATION_LABEL";

   @RBEntry("条件:")
   @RBComment("Criteria")
   public static final String PRIVATE_CONSTANT_89 = "CRITERIA_LABEL";

   /**
    * Picker Related localized values
    **/
   @RBEntry("用户名")
   @RBComment("User Name")
   public static final String PRIVATE_CONSTANT_90 = "USER_NAME_LABEL";

   @RBEntry("全名")
   @RBComment("Full Name")
   public static final String PRIVATE_CONSTANT_91 = "FULL_NAME_LABEL";

   @RBEntry("电子邮件")
   @RBComment("Email")
   public static final String PRIVATE_CONSTANT_92 = "EMAIL_LABEL";

   @RBEntry("组织名称")
   @RBComment("Organization Name")
   public static final String PRIVATE_CONSTANT_93 = "ORG_NAME_LABEL";

   @RBEntry("组织 ID")
   @RBComment("Organization ID")
   public static final String PRIVATE_CONSTANT_94 = "ORG_ID_LABEL";

   @RBEntry("组名称")
   @RBComment("Group Name")
   public static final String PRIVATE_CONSTANT_95 = "GROUP_NAME_LABEL";

   @RBEntry("说明")
   @RBComment("Description")
   public static final String PRIVATE_CONSTANT_96 = "DESCRIPTION_LABEL";

   @RBEntry("上下文名称")
   @RBComment("Context Name")
   public static final String PRIVATE_CONSTANT_97 = "CONTEXT_NAME_LABEL";

   @RBEntry("状态")
   @RBComment("State")
   public static final String PRIVATE_CONSTANT_98 = "STATE_LABEL";

   @RBEntry("上下文")
   @RBComment("Context")
   public static final String PRIVATE_CONSTANT_99 = "CONTEXT_LABEL";

   @RBEntry("编号")
   @RBComment("Number")
   public static final String PRIVATE_CONSTANT_100 = "NUMBER_LABEL";

   @RBEntry("参与者名称")
   @RBComment("Participant Name")
   public static final String PRIVATE_CONSTANT_101 = "PARTICIPANT_NAME_LABEL";

   @RBEntry("输入用户全名、组名 (蓝组) 或组织名称 (XYZ 组织) 的完整名称。使用分号 (;) 分隔多个条目。可使用星号 (*) 作为通配符；例如，J* Doe; Jane Smith; Blue Group; * Organization。")
   @RBComment("Principal Picker Help Text")
   public static final String PRIVATE_CONSTANT_102 = "PRINCIPAL_PICKER_HELP_TEXT";

   @RBEntry("输入用户全名。以分号 (;) 分隔多个条目。可使用星号 (*) 作为通配符；例如，*, Mike; Smith, Jane; J*, Doe。")
   @RBComment("User Picker Help Text")
   public static final String PRIVATE_CONSTANT_103 = "USER_PICKER_HELP_TEXT";

   @RBEntry("例如: Mary Smith; *Smith; Mary*")
   @RBComment("User Picker Short Help Text")
   public static final String PRIVATE_CONSTANT_104 = "USER_PICKER_SHORT_HELP_TEXT";

   @RBEntry("输入用户的名称 (用户名)。请使用分号分隔多个条目。可以包含 * 通配符表示字符。例如: Admin*; Mike")
   @RBComment("User Picker User Name Help Text")
   public static final String PRIVATE_CONSTANT_105 = "USER_PICKER_NAME_HELP_TEXT";

   @RBEntry("例如: Admin*; Mike")
   @RBComment("User Picker User Name Short Help Text")
   public static final String PRIVATE_CONSTANT_106 = "USER_PICKER_NAME_SHORT_HELP_TEXT";

   @RBEntry("输入您要搜索的组织名称。以分号分隔多个条目。可以包含 * 通配符来表示字符。例如: XYZ Organization; * Organization;  ABC Org*")
   @RBComment("Org Picker Help Text")
   public static final String PRIVATE_CONSTANT_107 = "ORG_PICKER_HELP_TEXT";

   @RBEntry("输入您要搜索的组名称。以分号分隔多个条目。可以包含 * 通配符来表示字符。例如: Red Group; * Group; Yellow * ")
   @RBComment("Group Picker Help Text")
   public static final String PRIVATE_CONSTANT_108 = "GROUP_PICKER_HELP_TEXT";

   @RBEntry("对象名称")
   @RBComment("Object Name lable comment")
   public static final String PRIVATE_CONSTANT_109 = "ITEM_NAME_LABEL";

   @RBEntry("对象版本")
   @RBComment("Object Version comment")
   public static final String PRIVATE_CONSTANT_110 = "ITEM_VERSION_LABEL";

   @RBEntry("对象编号")
   @RBComment("Object Number label")
   public static final String PRIVATE_CONSTANT_111 = "ITEM_NUMBER_LABEL";

   @RBEntry("对象小版本")
   @RBComment("Object Iteration label")
   public static final String PRIVATE_CONSTANT_112 = "ITEM_ITERATION_LABEL";

   @RBEntry("对象 CAD 文件名")
   @RBComment("Object CAD File Name label")
   public static final String PRIVATE_CONSTANT_113 = "ITEM_CAD_FILE_NAME_LABEL";

   @RBEntry("对象状态")
   @RBComment("Object State label")
   public static final String PRIVATE_CONSTANT_114 = "ITEM_STATE_LABEL";

   @RBEntry("对象上下文")
   @RBComment("Object Context Path label")
   public static final String PRIVATE_CONSTANT_115 = "ITEM_CONTEXT_PATH_LABEL";

   @RBEntry("对象视图")
   @RBComment("Object View label")
   public static final String PRIVATE_CONSTANT_116 = "ITEM_VIEW_LABEL";

   @RBEntry("对象创建者")
   @RBComment("Object Creator label")
   public static final String PRIVATE_CONSTANT_117 = "ITEM_CREATOR_LABEL";

   @RBEntry("所有上下文类型")
   @RBComment("Text to be displayed in case of context picker for all object types")
   public static final String PRIVATE_CONSTANT_118 = "ALL_CONTEXT_ITEMS";

   /**
    * Picker Titles
    **/
   @RBEntry("查找用户")
   @RBComment("User Picker Title")
   public static final String PRIVATE_CONSTANT_119 = "USER_PICKER_TITLE";

   @RBEntry("查找组织")
   @RBComment("Organization Picker Title")
   public static final String PRIVATE_CONSTANT_120 = "ORG_PICKER_TITLE";

   /**
    * Query Builder table column headings
    **/
   @RBEntry("搜索条件")
   @RBComment("Search Criteria")
   public static final String QB_TABLE_HEADING_LABEL = "QB_TABLE_HEADING_LABEL";

   @RBEntry("名称")
   @RBComment("Name")
   public static final String QB_NAME_LABEL = "QB_NAME_LABEL";

   @RBEntry("运算符")
   @RBComment("Operator")
   public static final String PRIVATE_CONSTANT_121 = "QB_OPERATOR_LABEL";

   @RBEntry("值")
   @RBComment("Value")
   public static final String PRIVATE_CONSTANT_122 = "QB_VALUE_LABEL";

   @RBEntry("搜索结果")
   @RBComment("Search Results")
   public static final String PRIVATE_CONSTANT_123 = "SEARCH_RESULTS_TABLE";

   @RBEntry("您的会话数据已经过期。请重复您的操作")
   @RBComment("Session Expired")
   public static final String SESSION_EXPIRED_MSG = "SESSION_EXPIRED_MSG";

   @RBEntry("此页已过期，它可能导致不正确的搜索结果。请重新执行搜索。")
   @RBComment("Back Button Clicked before Search")
   public static final String PRIVATE_CONSTANT_124 = "CHECK_BACKBUTTON_CLICKED";

   @RBEntry("添加")
   @RBComment("Add text for attribute menu drop down")
   public static final String PRIVATE_CONSTANT_125 = "ADD_CRITERIA";

   @RBEntry("清除")
   @RBComment("Clear criteria link")
   public static final String PRIVATE_CONSTANT_126 = "CLEAR_CRITERIA";

   @RBEntry("导出列表至文件")
   public static final String PRIVATE_CONSTANT_127 = "search.exportSearchResults.description";

   @RBEntry("导出列表至文件")
   public static final String PRIVATE_CONSTANT_128 = "search.exportSearchResults.tooltip";

   @RBEntry("../../com/ptc/core/ui/images/export.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_129 = "search.exportSearchResults.icon";

   @RBEntry("height=300,width=600")
   @RBPseudo(false)
   @RBComment("DO NOT TRANSLATE")
   public static final String PRIVATE_CONSTANT_130 = "search.exportSearchResults.moreurlinfo";

   @RBEntry("保存此搜索")
   public static final String PRIVATE_CONSTANT_131 = "search.saveThisSearch.description";

   @RBEntry("保存此搜索")
   public static final String PRIVATE_CONSTANT_132 = "search.saveThisSearch.tooltip";

   @RBEntry("选择")
   public static final String PRIVATE_CONSTANT_133 = "search.selectSearch.description";

   @RBEntry("搜索")
   public static final String PRIVATE_CONSTANT_134 = "search.search.description";

   @RBEntry("移除")
   public static final String PRIVATE_CONSTANT_135 = "search.remove.description";

   @RBEntry("移除")
   public static final String PRIVATE_CONSTANT_136 = "search.remove.tooltip";

   @RBEntry("../../wtcore/images/com/ptc/core/ca/web/misc/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_137 = "search.remove.icon";

   @RBEntry("在网络中搜索")
   public static final String PRIVATE_CONSTANT_138 = "search.networkSearch.description";

   @RBEntry("在网络中搜索")
   public static final String PRIVATE_CONSTANT_139 = "search.networkSearch.tooltip";

   @RBEntry("netmarkets/images/search_tbar16.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_140 = "search.networkSearch.icon";

   @RBEntry("在节点中搜索")
   public static final String PRIVATE_CONSTANT_141 = "search.searchInNode.description";

   @RBEntry("在节点中搜索")
   public static final String PRIVATE_CONSTANT_142 = "search.searchInNode.tooltip";

   @RBEntry("../../netmarkets/images/search.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_143 = "search.searchInNode.icon";

   @RBEntry("搜索相关对象")
   public static final String PRIVATE_CONSTANT_144 = "search.relatedItemSearch.description";

   @RBEntry("搜索相关对象")
   public static final String PRIVATE_CONSTANT_145 = "search.relatedItemSearch.tooltip";

   @RBEntry("netmarkets/images/related_objects_search.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_146 = "search.relatedItemSearch.icon";

   @RBEntry("自定义...")
   public static final String PRIVATE_CONSTANT_147 = "search.relationPicker.description";

   @RBEntry("自定义...")
   public static final String PRIVATE_CONSTANT_148 = "search.relationPicker.tooltip";

   @RBEntry("选取要搜索的关系")
   public static final String PRIVATE_CONSTANT_149 = "search.relationPicker.title";

   @RBEntry("height=600,width=600")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_150 = "search.relationPicker.moreurlinfo";

   @RBEntry("选择一个或多个要搜索的关系")
   public static final String PRIVATE_CONSTANT_151 = "search.relationPickerStep.title";

   @RBEntry("选择要搜索的关系")
   public static final String PRIVATE_CONSTANT_152 = "search.relationPickerStep.tooltip";

   @RBEntry("选择关系")
   public static final String PRIVATE_CONSTANT_153 = "search.relationPickerStep.description";

   @RBEntry("删除此搜索")
   public static final String PRIVATE_CONSTANT_154 = "SavedQuery.deleteQuery.description";

   @RBEntry("删除此搜索")
   public static final String PRIVATE_CONSTANT_155 = "SavedQuery.deleteQuery.tooltip";

   @RBEntry("netmarkets/images/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_156 = "SavedQuery.deleteQuery.icon";

   @RBEntry("显示")
   public static final String PRIVATE_CONSTANT_157 = "SavedQuery.showQuery.description";

   @RBEntry("显示")
   public static final String PRIVATE_CONSTANT_158 = "SavedQuery.showQuery.tooltip";

   @RBEntry("../../wtcore/images/search_show.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_159 = "SavedQuery.showQuery.icon";

   @RBEntry("隐藏")
   public static final String PRIVATE_CONSTANT_160 = "SavedQuery.hideQuery.description";

   @RBEntry("隐藏")
   public static final String PRIVATE_CONSTANT_161 = "SavedQuery.hideQuery.tooltip";

   @RBEntry("../../wtcore/images/search_hide.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_162 = "SavedQuery.hideQuery.icon";

   @RBEntry("删除")
   public static final String PRIVATE_CONSTANT_163 = "SavedQuery.deleteRowQuery.description";

   @RBEntry("删除")
   public static final String PRIVATE_CONSTANT_164 = "SavedQuery.deleteRowQuery.tooltip";

   @RBEntry("../../wtcore/images/com/ptc/core/ca/web/misc/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_165 = "SavedQuery.deleteRowQuery.icon";

   @RBEntry("显示")
   public static final String PRIVATE_CONSTANT_166 = "SavedQuery.showRowQuery.description";

   @RBEntry("显示")
   public static final String PRIVATE_CONSTANT_167 = "SavedQuery.showRowQuery.tooltip";

   @RBEntry("../../wtcore/images/search_show.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_168 = "SavedQuery.showRowQuery.icon";

   @RBEntry("隐藏")
   public static final String PRIVATE_CONSTANT_169 = "SavedQuery.hideRowQuery.description";

   @RBEntry("隐藏")
   public static final String PRIVATE_CONSTANT_170 = "SavedQuery.hideRowQuery.tooltip";

   @RBEntry("../../wtcore/images/search_hide.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_171 = "SavedQuery.hideRowQuery.icon";

   @RBEntry("管理删除")
   public static final String PRIVATE_CONSTANT_172 = "search.adminDelete.description";

   @RBEntry("管理删除")
   public static final String PRIVATE_CONSTANT_173 = "search.adminDelete.tooltip";

   @RBEntry("../../wtcore/images/search_deletesaved.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_174 = "search.adminDelete.icon";

   /**
    * Entries for simplesearch typepicker "Customize" link.
    * Copied from \wcEnterprise\EnterpriseUI\src\com\ptc\windchill\enterprise\picker\type\typePickerResource.rbInfo
    **/
   @RBEntry("添加/更新")
   @RBComment("Used as the label for the find action for a SimpleSearch page Type Picker.")
   public static final String PRIVATE_CONSTANT_175 = "search.typePicker.description";

   @RBEntry("查找类型")
   @RBComment("Used as the title for the find action pop-up wizard for a Type Picker.")
   public static final String PRIVATE_CONSTANT_176 = "search.typePicker.title";

   @RBEntry("height=600,width=500")
   @RBPseudo(false)
   @RBComment("DO NOT TRANSLATE")
   public static final String PRIVATE_CONSTANT_177 = "search.typePicker.moreurlinfo";

   /**
    * Entries for simplesearch newtypepicker "Customize" link.
    * Copied from \wcEnterprise\EnterpriseUI\src\com\ptc\windchill\enterprise\picker\type\typePickerResource.rbInfo
    **/
   @RBEntry("添加/更新...")
   @RBComment("Used as the label for the find action for a SimpleSearch page Type Picker.")
   public static final String PRIVATE_CONSTANT_178 = "search.newtypePicker.description";

   @RBEntry("查找类型")
   @RBComment("Used as the title for the find action pop-up wizard for a Type Picker.")
   public static final String PRIVATE_CONSTANT_179 = "search.newtypePicker.title";

   @RBEntry("height=600,width=375")
   @RBPseudo(false)
   @RBComment("DO NOT TRANSLATE")
   public static final String PRIVATE_CONSTANT_180 = "search.newtypePicker.moreurlinfo";

   @RBEntry("完整结果列表")
   @RBComment("Full Result List option in the picker dropdown")
   public static final String FULL_RESULT_LIST = "FULL_RESULT_LIST";

   @RBEntry("管理删除")
   public static final String PRIVATE_CONSTANT_181 = "SavedQuery.finalDelete.description";

   @RBEntry("管理删除")
   public static final String PRIVATE_CONSTANT_182 = "SavedQuery.finalDelete.tooltip";

   @RBEntry("netmarkets/images/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_183 = "SavedQuery.finalDelete.icon";

   @RBEntry("isAdminDelete=true")
   @RBPseudo(false)
   @RBComment("DO NOT TRANSLATE")
   public static final String PRIVATE_CONSTANT_184 = "SavedQuery.finalDelete.moreurlinfo";

   @RBEntry("导出")
   public static final String PRIVATE_CONSTANT_185 = "SavedQuery.savedQueryExport.description";

   @RBEntry("netmarkets/images/export.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_186 = "SavedQuery.savedQueryExport.icon";

   @RBEntry("导出保存的查询")
   public static final String PRIVATE_CONSTANT_187 = "SavedQuery.savedQueryExport.title";

   @RBEntry("导出保存的搜索")
   public static final String PRIVATE_CONSTANT_188 = "SavedQuery.savedQueryExport.tooltip";

   @RBEntry("height=300,width=500")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_189 = "SavedQuery.savedQueryExport.moreurlinfo";

   @RBEntry("导入")
   public static final String PRIVATE_CONSTANT_190 = "SavedQuery.savedQueryImport.description";

   @RBEntry("netmarkets/images/import.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_191 = "SavedQuery.savedQueryImport.icon";

   @RBEntry("导入保存的查询")
   public static final String PRIVATE_CONSTANT_192 = "SavedQuery.savedQueryImport.title";

   @RBEntry("导入保存的搜索")
   public static final String PRIVATE_CONSTANT_193 = "SavedQuery.savedQueryImport.tooltip";

   @RBEntry("height=300,width=500")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_194 = "SavedQuery.savedQueryImport.moreurlinfo";

   @RBEntry("导入")
   public static final String PRIVATE_CONSTANT_195 = "search.import.description";

   @RBEntry("导入保存的搜索")
   public static final String PRIVATE_CONSTANT_196 = "search.import.title";

   @RBEntry("导入")
   public static final String PRIVATE_CONSTANT_197 = "search.import_step.description";

   @RBEntry("导入保存的搜索")
   public static final String PRIVATE_CONSTANT_198 = "search.import_step.title";

   @RBEntry("转至")
   public static final String PRIVATE_CONSTANT_199 = "search.typeChange.description";

   @RBEntry("全部清除")
   public static final String PRIVATE_CONSTANT_200 = "search.clearSearch.description";

   @RBEntry("查找...")
   public static final String PRIVATE_CONSTANT_201 = "search.callSearchPicker.description";

   @RBEntry("查找...")
   public static final String PRIVATE_CONSTANT_202 = "search.callSearchPicker.tooltip";

   @RBEntry("height=768,width=550")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_203 = "search.callSearchPicker.moreurlinfo";

   @RBEntry("添加组")
   public static final String PRIVATE_CONSTANT_204 = "search.callSavedSearchGroupPicker.description";

   @RBEntry("添加组")
   public static final String PRIVATE_CONSTANT_205 = "search.callSavedSearchGroupPicker.title";

   @RBEntry("添加组")
   public static final String PRIVATE_CONSTANT_206 = "search.callSavedSearchGroupPicker.tooltip";

   @RBEntry("height=550,width=400")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_207 = "search.callSavedSearchGroupPicker.moreurlinfo";

   @RBEntry("netmarkets/images/add16x16.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_208 = "search.callSavedSearchGroupPicker.icon";

   @RBEntry("移除组")
   public static final String PRIVATE_CONSTANT_209 = "search.removeSavedSearchGroups.description";

   @RBEntry("移除组")
   public static final String PRIVATE_CONSTANT_210 = "search.removeSavedSearchGroups.title";

   @RBEntry("移除组")
   public static final String PRIVATE_CONSTANT_211 = "search.removeSavedSearchGroups.tooltip";

   @RBEntry("netmarkets/images/remove16x16.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_212 = "search.removeSavedSearchGroups.icon";

   @RBEntry("高级选项")
   public static final String PRIVATE_CONSTANT_213 = "search.saveThisSearchNew.description";

   @RBEntry("高级选项")
   public static final String PRIVATE_CONSTANT_214 = "search.saveThisSearchNew.tooltip";

   @RBEntry("高级选项")
   public static final String PRIVATE_CONSTANT_215 = "search.saveThisSearchNew.title";

   @RBEntry("设置保存的搜索名称")
   public static final String PRIVATE_CONSTANT_216 = "search.saveThisSearch_saveName.description";

   @RBEntry("设置保存的搜索名称")
   public static final String PRIVATE_CONSTANT_217 = "search.saveThisSearch_saveName.tooltip";

   @RBEntry("定义详细信息")
   public static final String PRIVATE_CONSTANT_218 = "search.saveThisSearch_saveName.title";

   @RBEntry("设置必需属性 ")
   public static final String PRIVATE_CONSTANT_219 = "search.saveThisSearch_saveRequired.description";

   @RBEntry("设置必需属性 ")
   public static final String PRIVATE_CONSTANT_220 = "search.saveThisSearch_saveRequired.tooltip";

   @RBEntry("设置必需属性 ")
   public static final String PRIVATE_CONSTANT_221 = "search.saveThisSearch_saveRequired.title";

   @RBEntry("设置表格视图 ")
   public static final String PRIVATE_CONSTANT_222 = "search.saveThisSearch_saveTableView.description";

   @RBEntry("设置表格视图 ")
   public static final String PRIVATE_CONSTANT_223 = "search.saveThisSearch_saveTableView.tooltip";

   @RBEntry("设置表格视图 ")
   public static final String PRIVATE_CONSTANT_224 = "search.saveThisSearch_saveTableView.title";

   @RBEntry("设置组访问权限 ")
   public static final String PRIVATE_CONSTANT_225 = "search.saveThisSearch_saveAccess.description";

   @RBEntry("设置组访问权限 ")
   public static final String PRIVATE_CONSTANT_226 = "search.saveThisSearch_saveAccess.tooltip";

   @RBEntry("设置组访问权限 ")
   public static final String PRIVATE_CONSTANT_227 = "search.saveThisSearch_saveAccess.title";

   /**
    * Saved Search related fields
    **/
   @RBEntry("名称")
   @RBComment("Search Name")
   public static final String PRIVATE_CONSTANT_228 = "SAVED_SEARCH_NAME";

   @RBEntry("在“保存的搜索”列表中显示")
   @RBComment("Show in Saved Search list")
   public static final String PRIVATE_CONSTANT_229 = "SAVED_SEARCH_SHOW";

   @RBEntry("请输入名称")
   @RBComment("Please enter Name")
   public static final String PRIVATE_CONSTANT_230 = "PLEASE_ENTER_NAME";

   @RBEntry("创建表格视图")
   @RBComment("Create Table View")
   public static final String PRIVATE_CONSTANT_231 = "SAVED_SEARCH_CREATE_VIEW";

   @RBEntry("表格视图")
   @RBComment("Use Existing Table View")
   public static final String PRIVATE_CONSTANT_232 = "SAVED_SEARCH_EXISTING_VIEW";

   @RBEntry("- 选取一个视图 -")
   @RBComment("- Pick a View -")
   public static final String PRIVATE_CONSTANT_233 = "PICK_VIEW";

   @RBEntry("可用属性:")
   @RBComment("Available Attributes:")
   public static final String PRIVATE_CONSTANT_234 = "AVAILABLE_ATTRIBUTES";

   @RBEntry("必需属性:")
   @RBComment("Required Attributes:")
   public static final String PRIVATE_CONSTANT_235 = "REQUIRED_ATTRIBUTES";

   @RBEntry("添加")
   @RBComment("Add")
   public static final String PRIVATE_CONSTANT_236 = "ADD_ATTRIBUTE";

   @RBEntry("移除 ")
   @RBComment("Remove")
   public static final String PRIVATE_CONSTANT_237 = "REMOVE_ATTRIBUTE";

   @RBEntry("默认 CAD 文档视图")
   @RBComment("Default CAD Document View")
   public static final String PRIVATE_CONSTANT_238 = "CADDOC_TABLEVIEW_NAME";

   @RBEntry("默认 EPM 文档搜索表格视图")
   @RBComment("Default EPM Document Search Table View")
   public static final String PRIVATE_CONSTANT_239 = "CADDOC_TABLEVIEW_DESC";

   @RBEntry("EPM 文档")
   @RBComment("EPM Document")
   public static final String PRIVATE_CONSTANT_240 = "CADDOC_TABLEVIEW_LABEL";

   @RBEntry("默认部件视图")
   @RBComment("Default Part View")
   public static final String PRIVATE_CONSTANT_241 = "WTPART_TABLEVIEW_NAME";

   @RBEntry("默认部件搜索表格视图")
   @RBComment("Default Part Search Table View")
   public static final String PRIVATE_CONSTANT_242 = "WTPART_TABLEVIEW_DESC";

   @RBEntry("部件 ")
   @RBComment("Part ")
   public static final String PRIVATE_CONSTANT_243 = "WTPART_TABLEVIEW_LABEL";

   @RBEntry("默认文档视图")
   @RBComment("Default Document View")
   public static final String PRIVATE_CONSTANT_244 = "WTDOCUMENT_TABLEVIEW_NAME";

   @RBEntry("默认文档搜索表格视图")
   @RBComment("Default Document Search Table View")
   public static final String PRIVATE_CONSTANT_245 = "WTDOCUMENT_TABLEVIEW_DESC";

   @RBEntry("文档")
   @RBComment("Document")
   public static final String PRIVATE_CONSTANT_246 = "WTDOCUMENT_TABLEVIEW_LABEL";

   @RBEntry("默认工作集视图")
   @RBComment("Default Work Set View")
   public static final String PRIVATE_CONSTANT_247 = "WTWORKSET_TABLEVIEW_NAME";

   @RBEntry("默认认工作集搜索表格视图")
   @RBComment("Default Work Set Search Table View")
   public static final String PRIVATE_CONSTANT_248 = "WTWORKSET_TABLEVIEW_DESC";

   @RBEntry("工作集")
   @RBComment("Work Set")
   public static final String PRIVATE_CONSTANT_249 = "WTWORKSET_TABLEVIEW_LABEL";

   @RBEntry("默认存档视图")
   @RBComment("Default Archive View")
   public static final String PRIVATE_CONSTANT_250 = "ARCHIVE_TABLEVIEW_NAME";

   @RBEntry("默认存档搜索表格视图")
   @RBComment("Default Archive Search Table View")
   public static final String PRIVATE_CONSTANT_251 = "ARCHIVE_TABLEVIEW_DESC";

   @RBEntry("存档")
   @RBComment("Archive")
   public static final String PRIVATE_CONSTANT_252 = "ARCHIVE_TABLEVIEW_LABEL";

   @RBEntry("默认更改通告视图")
   @RBComment("Default Change Notice View")
   public static final String PRIVATE_CONSTANT_253 = "CHANGENOTICE_TABLEVIEW_NAME";

   @RBEntry("默认更改通告搜索表格视图")
   @RBComment("Default Change Notice Search Table View")
   public static final String PRIVATE_CONSTANT_254 = "CHANGENOTICE_TABLEVIEW_DESC";

   @RBEntry("更改通告")
   @RBComment("Change Notice")
   public static final String PRIVATE_CONSTANT_255 = "CHANGENOTICE_TABLEVIEW_LABEL";

   @RBEntry("默认更改请求视图")
   @RBComment("Default Change Request View")
   public static final String PRIVATE_CONSTANT_256 = "CHANGEREQUEST_TABLEVIEW_NAME";

   @RBEntry("默认更改请求搜索表格视图")
   @RBComment("Default Change Request Search Table View")
   public static final String PRIVATE_CONSTANT_257 = "CHANGEREQUEST_TABLEVIEW_DESC";

   @RBEntry("更改请求")
   @RBComment("Change Request")
   public static final String PRIVATE_CONSTANT_258 = "CHANGEREQUEST_TABLEVIEW_LABEL";

   @RBEntry("默认更改指令视图")
   @RBComment("Default Change Directive View")
   public static final String PRIVATE_CONSTANT_259 = "CHANGEDIRECTIVE_TABLEVIEW_NAME";

   @RBEntry("默认更改指令搜索表格视图")
   @RBComment("Default Change Directive Search Table View")
   public static final String PRIVATE_CONSTANT_260 = "CHANGEDIRECTIVE_TABLEVIEW_DESC";

   @RBEntry("更改指令")
   @RBComment("Change Directive")
   public static final String PRIVATE_CONSTANT_261 = "CHANGEDIRECTIVE_TABLEVIEW_LABEL";

   @RBEntry("默认问题报告视图")
   @RBComment("Default Problem Report View")
   public static final String PRIVATE_CONSTANT_262 = "WTCHANGEISSUE_TABLEVIEW_NAME";

   @RBEntry("默认问题报告搜索表格视图")
   @RBComment("Default Problem Report Search Table View")
   public static final String PRIVATE_CONSTANT_263 = "WTCHANGEISSUE_TABLEVIEW_DESC";

   @RBEntry("问题报告")
   @RBComment("Problem Report")
   public static final String PRIVATE_CONSTANT_264 = "WTCHANGEISSUE_TABLEVIEW_LABEL";

   @RBEntry("默认可交付结果视图")
   @RBComment("Default Deliverable View")
   public static final String PRIVATE_CONSTANT_265 = "DELIVERABLE_TABLEVIEW_NAME";

   @RBEntry("默认可交付结果搜索表格视图")
   @RBComment("Default Deliverable Search Table View")
   public static final String PRIVATE_CONSTANT_266 = "DELIVERABLE_TABLEVIEW_DESC";

   @RBEntry("可交付结果")
   @RBComment("Deliverable")
   public static final String PRIVATE_CONSTANT_267 = "DELIVERABLE_TABLEVIEW_LABEL";

   @RBEntry("默认制造商视图")
   @RBComment("Default Manufacturer View")
   public static final String PRIVATE_CONSTANT_268 = "MANUFACTURER_TABLEVIEW_NAME";

   @RBEntry("默认制造商搜索表格视图")
   @RBComment("Default Manufacturer Search Table View")
   public static final String PRIVATE_CONSTANT_269 = "MANUFACTURER_TABLEVIEW_DESC";

   @RBEntry("制造商")
   @RBComment("Manufacturer")
   public static final String PRIVATE_CONSTANT_270 = "MANUFACTURER_TABLEVIEW_LABEL";

   @RBEntry("默认组织视图")
   @RBComment("Default Organization View")
   public static final String PRIVATE_CONSTANT_271 = "ORGNIZATION_TABLEVIEW_NAME";

   @RBEntry("默认组织搜索表格视图")
   @RBComment("Default Organization Search Table View")
   public static final String PRIVATE_CONSTANT_272 = "ORGNIZATION_TABLEVIEW_DESC";

   @RBEntry("组织")
   @RBComment("Organization")
   public static final String PRIVATE_CONSTANT_273 = "ORGNIZATION_TABLEVIEW_LABEL";

   @RBEntry("默认项目视图")
   @RBComment("Default Project View")
   public static final String PRIVATE_CONSTANT_274 = "PROJECT2_TABLEVIEW_NAME";

   @RBEntry("默认项目搜索表格视图")
   @RBComment("Default Project Search Table View")
   public static final String PRIVATE_CONSTANT_275 = "PROJECT2_TABLEVIEW_DESC";

   @RBEntry("项目")
   @RBComment("Project")
   public static final String PRIVATE_CONSTANT_276 = "PROJECT2_TABLEVIEW_LABEL";

   @RBEntry("默认部件配置视图")
   @RBComment("Default Part Configuration View")
   public static final String PRIVATE_CONSTANT_277 = "PARTCONFIGURATION_TABLEVIEW_NAME";

   @RBEntry("默认部件配置搜索表格视图")
   @RBComment("Default Part Configuration Search Table View")
   public static final String PRIVATE_CONSTANT_278 = "PARTCONFIGURATION_TABLEVIEW_DESC";

   @RBEntry("部件配置")
   @RBComment("Part Configuration")
   public static final String PRIVATE_CONSTANT_279 = "PARTCONFIGURATION_TABLEVIEW_LABEL";

   @RBEntry("默认部件实例视图")
   @RBComment("Default Part Instance View")
   public static final String PRIVATE_CONSTANT_280 = "PARTINSTANCE_TABLEVIEW_NAME";

   @RBEntry("默认部件实例搜索表格视图")
   @RBComment("Default Part Instance Search Table View")
   public static final String PRIVATE_CONSTANT_281 = "PARTINSTANCE_TABLEVIEW_DESC";

   @RBEntry("部件实例")
   @RBComment("Part Instance")
   public static final String PRIVATE_CONSTANT_282 = "PARTINSTANCE_TABLEVIEW_LABEL";

   @RBEntry("默认厂商视图")
   @RBComment("Default Vendor View")
   public static final String PRIVATE_CONSTANT_283 = "VENDOR_TABLEVIEW_NAME";

   @RBEntry("默认厂商搜索表格视图")
   @RBComment("Default Vendor Search Table View")
   public static final String PRIVATE_CONSTANT_284 = "VENDOR_TABLEVIEW_DESC";

   @RBEntry("厂商")
   @RBComment("Vendor")
   public static final String PRIVATE_CONSTANT_285 = "VENDOR_TABLEVIEW_LABEL";

   @RBEntry("默认受管理的集合视图")
   @RBComment("Default Managed Collection View")
   public static final String PRIVATE_CONSTANT_286 = "MANAGEDCOLLECTION_TABLEVIEW_NAME";

   @RBEntry("默认受管理的集合搜索表格视图")
   @RBComment("Default Managed Collection Search Table View")
   public static final String PRIVATE_CONSTANT_287 = "MANAGEDCOLLECTION_TABLEVIEW_DESC";

   @RBEntry("受管理的集合")
   @RBComment("Managed Collection")
   public static final String PRIVATE_CONSTANT_288 = "MANAGEDCOLLECTION_TABLEVIEW_LABEL";

   @RBEntry("修改者")
   @RBComment("Modified By")
   public static final String MC_MODIFIED_BY = "MC_MODIFIED_BY";

   @RBEntry("默认包视图")
   @RBComment("Default Package View")
   public static final String PRIVATE_CONSTANT_289 = "WORKPACKAGE_TABLEVIEW_NAME";

   @RBEntry("默认包搜索表格视图")
   @RBComment("Default Package Search Table View")
   public static final String PRIVATE_CONSTANT_290 = "WORKPACKAGE_TABLEVIEW_DESC";

   @RBEntry("包")
   @RBComment("Package")
   public static final String PRIVATE_CONSTANT_291 = "WORKPACKAGE_TABLEVIEW_LABEL";

   /** The Constant LOCKED_ATTR_TABLE_LABEL. */
   @RBEntry("锁定状况")
   public static final String LOCKED_ATTR_TABLE_LABEL = "LOCKED_ATTR_TABLE_LABEL";

   @RBEntry("默认部件请求视图")
   @RBComment("Default Part Request View")
   public static final String PRIVATE_CONSTANT_292 = "WTPARTREQUEST_TABLEVIEW_NAME";

   @RBEntry("默认部件请求表格视图")
   @RBComment("Default Part Request Search Table View")
   public static final String PRIVATE_CONSTANT_293 = "WTPARTREQUEST_TABLEVIEW_DESC";

   @RBEntry("部件请求")
   @RBComment("Part Request")
   public static final String PRIVATE_CONSTANT_294 = "WTPARTREQUEST_TABLEVIEW_LABEL";

   @RBEntry("默认超差视图")
   @RBComment("Default Variance View")
   public static final String PRIVATE_CONSTANT_295 = "WTVARIANCE_TABLEVIEW_NAME";

   @RBEntry("默认超差搜索表格视图")
   @RBComment("Default Variance Search Table View")
   public static final String PRIVATE_CONSTANT_296 = "WTVARIANCE_TABLEVIEW_DESC";

   @RBEntry("超差")
   @RBComment("Variance")
   public static final String PRIVATE_CONSTANT_297 = "WTVARIANCE_TABLEVIEW_LABEL";

   @RBEntry("默认变型规范视图")
   @RBComment("Default Variant Spec View")
   public static final String PRIVATE_CONSTANT_298 = "VARIANTSPEC_TABLEVIEW_NAME";

   @RBEntry("默认变型规范搜索表格视图")
   @RBComment("Default Variant Spec Search Table View")
   public static final String PRIVATE_CONSTANT_299 = "VARIANTSPEC_TABLEVIEW_DESC";

   @RBEntry("变型规范")
   @RBComment("Variant Spec")
   public static final String PRIVATE_CONSTANT_300 = "VARIANTSPEC_TABLEVIEW_LABEL";

   @RBEntry("默认用户视图")
   @RBComment("Default User View")
   public static final String PRIVATE_CONSTANT_301 = "WTUSER_TABLEVIEW_NAME";

   @RBEntry("默认用户搜索表格视图")
   @RBComment("Default User Search Table View")
   public static final String PRIVATE_CONSTANT_302 = "WTUSER_TABLEVIEW_DESC";

   @RBEntry("用户")
   @RBComment("User")
   public static final String PRIVATE_CONSTANT_303 = "WTUSER_TABLEVIEW_LABEL";

   @RBEntry("默认措施项视图")
   @RBComment("Default Action Item View")
   public static final String PRIVATE_CONSTANT_304 = "DISCRETEACTIONITEM_TABLEVIEW_NAME";

   @RBEntry("默认措施项搜索表格视图")
   @RBComment("Default Action Item Search Table View")
   public static final String PRIVATE_CONSTANT_305 = "DISCRETEACTIONITEM_TABLEVIEW_DESC";

   @RBEntry("措施项")
   @RBComment("Action Item")
   public static final String PRIVATE_CONSTANT_306 = "DISCRETEACTIONITEM_TABLEVIEW_LABEL";

   @RBEntry("默认 EPM 文档主数据视图")
   @RBComment("Default EPM Document Master View")
   public static final String PRIVATE_CONSTANT_307 = "EPMDOCUMENTMASTER_TABLEVIEW_NAME";

   @RBEntry("默认 EPM 文档主数据搜索表格视图")
   @RBComment("Default EPM Document Master Search Table View")
   public static final String PRIVATE_CONSTANT_308 = "EPMDOCUMENTMASTER_TABLEVIEW_DESC";

   @RBEntry("EPM 文档主数据")
   @RBComment("EPM Document Master")
   public static final String PRIVATE_CONSTANT_309 = "EPMDOCUMENTMASTER_TABLEVIEW_LABEL";

   @RBEntry("默认文档主数据视图")
   @RBComment("Default Document Master View")
   public static final String PRIVATE_CONSTANT_310 = "WTDOCUMENTMASTER_TABLEVIEW_NAME";

   @RBEntry("默认文档主数据搜索表格视图")
   @RBComment("Default Document Master Search Table View")
   public static final String PRIVATE_CONSTANT_311 = "WTDOCUMENTMASTER_TABLEVIEW_DESC";

   @RBEntry("文档主数据")
   @RBComment("Document Master")
   public static final String PRIVATE_CONSTANT_312 = "WTDOCUMENTMASTER_TABLEVIEW_LABEL";

   @RBEntry("默认部件主数据视图")
   @RBComment("Default Part Master View")
   public static final String PRIVATE_CONSTANT_313 = "WTPARTMASTER_TABLEVIEW_NAME";

   @RBEntry("默认部件主数据搜索表格视图")
   @RBComment("Default Part Master Search Table View")
   public static final String PRIVATE_CONSTANT_314 = "WTPARTMASTER_TABLEVIEW_DESC";

   @RBEntry("部件主数据")
   @RBComment("Part Master")
   public static final String PRIVATE_CONSTANT_315 = "WTPARTMASTER_TABLEVIEW_LABEL";

   @RBEntry("默认格式视图")
   @RBComment("Default Format View")
   public static final String PRIVATE_CONSTANT_316 = "FORMAT_TABLEVIEW_NAME";

   @RBEntry("默认格式搜索表格视图")
   @RBComment("Default Format Search Table View")
   public static final String PRIVATE_CONSTANT_317 = "FORMAT_TABLEVIEW_DESC";

   @RBEntry("格式")
   @RBComment("Format")
   public static final String PRIVATE_CONSTANT_318 = "FORMAT_TABLEVIEW_LABEL";

   @RBEntry("默认组视图")
   @RBComment("Default Group View")
   public static final String PRIVATE_CONSTANT_319 = "GROUP_TABLEVIEW_NAME";

   @RBEntry("默认组搜索表格视图")
   @RBComment("Default Group Search Table View")
   public static final String PRIVATE_CONSTANT_320 = "GROUP_TABLEVIEW_DESC";

   @RBEntry("组")
   @RBComment("Group")
   public static final String PRIVATE_CONSTANT_321 = "GROUP_TABLEVIEW_LABEL";

   @RBEntry("默认组织视图")
   @RBComment("Default Organization View")
   public static final String PRIVATE_CONSTANT_322 = "ORG_TABLEVIEW_NAME";

   @RBEntry("默认组织搜索表格视图")
   @RBComment("Default Organization Search Table View")
   public static final String PRIVATE_CONSTANT_323 = "ORG_TABLEVIEW_DESC";

   @RBEntry("组织")
   @RBComment("Organization")
   public static final String PRIVATE_CONSTANT_324 = "ORG_TABLEVIEW_LABEL";

   @RBEntry("默认参考附件视图")
   @RBComment("Default Reference Attachment View")
   public static final String PRIVATE_CONSTANT_325 = "IMPORTEDBOOKMARK_TABLEVIEW_NAME";

   @RBEntry("默认参考附件搜索表格视图")
   @RBComment("Default Reference Attachment Search Table View")
   public static final String PRIVATE_CONSTANT_326 = "IMPORTEDBOOKMARK_TABLEVIEW_DESC";

   @RBEntry("参考附件")
   @RBComment("Reference Attachment")
   public static final String PRIVATE_CONSTANT_327 = "IMPORTEDBOOKMARK_TABLEVIEW_LABEL";

   @RBEntry("默认子项目/项目群视图")
   @RBComment("Default Sub Project/Program View")
   public static final String PRIVATE_CONSTANT_328 = "PROJECTPROXY_TABLEVIEW_NAME";

   @RBEntry("默认子项目/项目群搜索表格视图")
   @RBComment("Default Sub Project/Program Search Table View")
   public static final String PRIVATE_CONSTANT_329 = "PROJECTPROXY_TABLEVIEW_DESC";

   @RBEntry("子项目/项目群")
   @RBComment("Sub Project/Program")
   public static final String PRIVATE_CONSTANT_330 = "PROJECTPROXY_TABLEVIEW_LABEL";

   @RBEntry("默认文件柜视图")
   @RBComment("Default Cabinet View")
   public static final String PRIVATE_CONSTANT_331 = "CABINET_TABLEVIEW_NAME";

   @RBEntry("默认文件柜搜索表格视图")
   @RBComment("Default Cabinet Search Table View")
   public static final String PRIVATE_CONSTANT_332 = "CABINET_TABLEVIEW_DESC";

   @RBEntry("文件柜")
   @RBComment("Cabinet")
   public static final String PRIVATE_CONSTANT_333 = "CABINET_TABLEVIEW_LABEL";

   @RBEntry("默认受管理的基线视图")
   @RBComment("Default Managed Baseline View")
   public static final String PRIVATE_CONSTANT_334 = "MANAGEDBASELINE_TABLEVIEW_NAME";

   @RBEntry("默认受管理的基线搜索表格视图")
   @RBComment("Default Managed Baseline Search Table View")
   public static final String PRIVATE_CONSTANT_335 = "MANAGEDBASELINE_TABLEVIEW_DESC";

   @RBEntry("受管理的基线")
   @RBComment("Managed Baseline")
   public static final String PRIVATE_CONSTANT_336 = "MANAGEDBASELINE_TABLEVIEW_LABEL";

   @RBEntry("默认工艺计划视图")
   @RBComment("Default Process Plan View")
   public static final String PRIVATE_CONSTANT_337 = "MPMPROCESSPLAN_TABLEVIEW_NAME";

   @RBEntry("默认工艺计划搜索表格视图")
   @RBComment("Default Process Plan Search Table View")
   public static final String PRIVATE_CONSTANT_338 = "MPMPROCESSPLAN_TABLEVIEW_DESC";

   @RBEntry("工艺计划")
   @RBComment("Process Plan")
   public static final String PRIVATE_CONSTANT_339 = "MPMPROCESSPLAN_TABLEVIEW_LABEL";

   @RBEntry("默认序列视图")
   @RBComment("Default Sequence View")
   public static final String PRIVATE_CONSTANT_340 = "MPMSEQUENCE_TABLEVIEW_NAME";

   @RBEntry("默认序列搜索表格视图")
   @RBComment("Default Sequence Search Table View")
   public static final String PRIVATE_CONSTANT_341 = "MPMSEQUENCE_TABLEVIEW_DESC";

   @RBEntry("工步")
   @RBComment("Sequence")
   public static final String PRIVATE_CONSTANT_342 = "MPMSEQUENCE_TABLEVIEW_LABEL";

   @RBEntry("默认操作视图")
   @RBComment("Default Operation View")
   public static final String PRIVATE_CONSTANT_343 = "MPMOPERATION_TABLEVIEW_NAME";

   @RBEntry("默认操作搜索表格视图")
   @RBComment("Default Operation Search Table View")
   public static final String PRIVATE_CONSTANT_344 = "MPMOPERATION_TABLEVIEW_DESC";

   @RBEntry("操作")
   @RBComment("Operation")
   public static final String PRIVATE_CONSTANT_345 = "MPMOPERATION_TABLEVIEW_LABEL";

   @RBEntry("默认制造商标准组视图")
   @RBComment("Default Manufacturing Standard Group View")
   public static final String PRIVATE_CONSTANT_346 = "MPMMFGSTANDARDGROUP_TABLEVIEW_NAME";

   @RBEntry("默认制造商标准组搜索表格视图")
   @RBComment("Default Manufacturing Standard Group Search Table View")
   public static final String PRIVATE_CONSTANT_347 = "MPMMFGSTANDARDGROUP_TABLEVIEW_DESC";

   @RBEntry("制造标准组")
   @RBComment("Manufacturing Standard Group")
   public static final String PRIVATE_CONSTANT_348 = "MPMMFGSTANDARDGROUP_TABLEVIEW_LABEL";

   @RBEntry("默认制造过程视图")
   @RBComment("Default Manufacturing Process View")
   public static final String PRIVATE_CONSTANT_349 = "MPMMFGPROCESS_TABLEVIEW_NAME";

   @RBEntry("默认制造过程搜索表格视图")
   @RBComment("Default Manufacturing Process Search Table View")
   public static final String PRIVATE_CONSTANT_350 = "MPMMFGPROCESS_TABLEVIEW_DESC";

   @RBEntry("制造过程")
   @RBComment("Manufacturing Process")
   public static final String PRIVATE_CONSTANT_351 = "MPMMFGPROCESS_TABLEVIEW_LABEL";

   @RBEntry("默认资源视图")
   @RBComment("Default Resource View")
   public static final String PRIVATE_CONSTANT_352 = "MPMRESOURCE_TABLEVIEW_NAME";

   @RBEntry("默认资源搜索表格视图")
   @RBComment("Default Resource Search Table View")
   public static final String PRIVATE_CONSTANT_353 = "MPMRESOURCE_TABLEVIEW_DESC";

   @RBEntry("资源")
   @RBComment("Resource")
   public static final String PRIVATE_CONSTANT_354 = "MPMRESOURCE_TABLEVIEW_LABEL";

   @RBEntry("默认工厂视图")
   @RBComment("Default Plant View")
   public static final String PRIVATE_CONSTANT_355 = "MPMPLANT_TABLEVIEW_NAME";

   @RBEntry("默认工厂搜索表格视图")
   @RBComment("Default Plant Search Table View")
   public static final String PRIVATE_CONSTANT_356 = "MPMPLANT_TABLEVIEW_DESC";

   @RBEntry("工厂")
   @RBComment("Plant")
   public static final String PRIVATE_CONSTANT_357 = "MPMPLANT_TABLEVIEW_LABEL";

   @RBEntry("默认资源组视图")
   @RBComment("Default Resource Group View")
   public static final String PRIVATE_CONSTANT_358 = "MPMRESOURCEGROUP_TABLEVIEW_NAME";

   @RBEntry("默认资源组搜索表格视图")
   @RBComment("Default Resource Group Search Table View")
   public static final String PRIVATE_CONSTANT_359 = "MPMRESOURCEGROUP_TABLEVIEW_DESC";

   @RBEntry("资源组")
   @RBComment("Resource Group")
   public static final String PRIVATE_CONSTANT_360 = "MPMRESOURCEGROUP_TABLEVIEW_LABEL";

   @RBEntry("默认工作项视图")
   @RBComment("Default Work Item View")
   public static final String PRIVATE_CONSTANT_361 = "WORKITEM_TABLEVIEW_NAME";

   @RBEntry("默认认工作项搜索表格视图")
   @RBComment("Default Work Item Search Table View")
   public static final String PRIVATE_CONSTANT_362 = "WORKITEM_TABLEVIEW_DESC";

   @RBEntry("工作项")
   @RBComment("Work Item")
   public static final String PRIVATE_CONSTANT_363 = "WORKITEM_TABLEVIEW_LABEL";

   @RBEntry("默认报告视图")
   @RBComment("Default Report View")
   public static final String PRIVATE_CONSTANT_364 = "REPORT_TABLEVIEW_NAME";

   @RBEntry("默认报告搜索表格视图")
   @RBComment("Default Report Search Table View")
   public static final String PRIVATE_CONSTANT_365 = "REPORT_TABLEVIEW_DESC";

   @RBEntry("报告")
   @RBComment("Report")
   public static final String PRIVATE_CONSTANT_366 = "REPORT_TABLEVIEW_LABEL";

   @RBEntry("默认已保存搜索视图")
   @RBComment("Default Saved Search View")
   public static final String PRIVATE_CONSTANT_367 = "SAVEDQUERY_TABLEVIEW_NAME";

   @RBEntry("默认已保存搜索的搜索表格视图")
   @RBComment("Default Saved Search Table View")
   public static final String PRIVATE_CONSTANT_368 = "SAVEDQUERY_TABLEVIEW_DESC";

   @RBEntry("默认已保存搜索选取器视图")
   @RBComment("Default Saved Search Picker View")
   public static final String PRIVATE_CONSTANT_369 = "SAVEDQUERY_PICKER_TABLEVIEW_NAME";

   @RBEntry("默认已保存搜索选取器表格视图")
   @RBComment("Default Saved Search Picker Table View")
   public static final String PRIVATE_CONSTANT_370 = "SAVEDQUERY_PICKER_TABLEVIEW_DESC";

   @RBEntry("保存的搜索")
   @RBComment("Saved Search")
   public static final String PRIVATE_CONSTANT_371 = "SAVEDQUERY_TABLEVIEW_LABEL";

   @RBEntry("默认值")
   @RBComment("Default View")
   public static final String PRIVATE_CONSTANT_372 = "PERSISTABLE_TABLEVIEW_NAME";

   @RBEntry("默认表格视图")
   @RBComment("Default Table View")
   public static final String PRIVATE_CONSTANT_373 = "PERSISTABLE_TABLEVIEW_DESC";

   @RBEntry("简单搜索")
   @RBComment("Simple Search")
   public static final String PRIVATE_CONSTANT_374 = "PERSISTABLE_TABLEVIEW_LABEL";

   @RBEntry("默认讨论区电子公告视图")
   @RBComment("Default Discussion Posting View")
   public static final String PRIVATE_CONSTANT_375 = "DISCUSSIONPOSTING_TABLEVIEW_NAME";

   @RBEntry("默认讨论区电子公告搜索表格视图")
   @RBComment("Default Discussion Posting Search Table View")
   public static final String PRIVATE_CONSTANT_376 = "DISCUSSIONPOSTING_TABLEVIEW_DESC";

   @RBEntry("讨论区电子公告")
   @RBComment("Discussion Posting")
   public static final String PRIVATE_CONSTANT_377 = "DISCUSSIONPOSTING_TABLEVIEW_LABEL";

   @RBEntry("默认会议视图")
   @RBComment("Default Meeting View")
   public static final String PRIVATE_CONSTANT_378 = "MEETING_TABLEVIEW_NAME";

   @RBEntry("默认会议搜索表格视图")
   @RBComment("Default Meeting Search Table View")
   public static final String PRIVATE_CONSTANT_379 = "MEETING_TABLEVIEW_DESC";

   @RBEntry("会议")
   @RBComment("Meeting")
   public static final String PRIVATE_CONSTANT_380 = "MEETING_TABLEVIEW_LABEL";

   /**
    * L10N CHANGE BEGIN: comment out duplicate resource id
    * PDMLINKPRODUCT_TABLEVIEW_NAME.comment=Default Product View
    * PDMLINKPRODUCT_TABLEVIEW_NAME.value=Default Product View
    * L10N CHANGE END
    * L10N CHANGE BEGIN: comment out duplicate resource id
    * PDMLINKPRODUCT_TABLEVIEW_DESC.comment=Default Product Search Table View
    * PDMLINKPRODUCT_TABLEVIEW_DESC.value=Default Product Search Table View
    * L10N CHANGE END
    * L10N CHANGE BEGIN: comment out duplicate resource id
    * PDMLINKPRODUCT_TABLEVIEW_LABEL.comment=Product
    * PDMLINKPRODUCT_TABLEVIEW_LABEL.value=Product
    * L10N CHANGE END
    **/
   @RBEntry("默认报告模板视图")
   @RBComment("Default Report Template View")
   public static final String PRIVATE_CONSTANT_381 = "REPORTTEMPLATE_TABLEVIEW_NAME";

   @RBEntry("默认报告模板搜索表格视图")
   @RBComment("Default Report Template Search Table View")
   public static final String PRIVATE_CONSTANT_382 = "REPORTTEMPLATE_TABLEVIEW_DESC";

   @RBEntry("报告模板")
   @RBComment("Report Template")
   public static final String PRIVATE_CONSTANT_383 = "REPORTTEMPLATE_TABLEVIEW_LABEL";

   @RBEntry("默认工作流进程视图")
   @RBComment("Default Workflow Process View")
   public static final String PRIVATE_CONSTANT_384 = "WORKFLOWPROCESS_TABLEVIEW_NAME";

   @RBEntry("默认工作流进程搜索表格视图")
   @RBComment("Default Workflow Process Search Table View")
   public static final String PRIVATE_CONSTANT_385 = "WORKFLOWPROCESS_TABLEVIEW_DESC";

   @RBEntry("工作流进程")
   @RBComment("Workflow Process")
   public static final String PRIVATE_CONSTANT_386 = "WORKFLOWPROCESS_TABLEVIEW_LABEL";

   @RBEntry("默认存储库视图")
   @RBComment("Default Library View")
   public static final String PRIVATE_CONSTANT_387 = "WTLIBRARY_TABLEVIEW_NAME";

   @RBEntry("默认存储库搜索表格视图")
   @RBComment("Default Library Search Table View")
   public static final String PRIVATE_CONSTANT_388 = "WTLIBRARY_TABLEVIEW_DESC";

   @RBEntry("存储库")
   @RBComment("Library")
   public static final String PRIVATE_CONSTANT_389 = "WTLIBRARY_TABLEVIEW_LABEL";

   @RBEntry("默认导入作业视图")
   @RBComment("Default Import Job View")
   public static final String PRIVATE_CONSTANT_390 = "IMPORTJOB_TABLEVIEW_NAME";

   @RBEntry("默认导入作业搜索表格视图")
   @RBComment("Default Import Job Search Table View")
   public static final String PRIVATE_CONSTANT_391 = "IMPORTJOB_TABLEVIEW_DESC";

   @RBEntry("导入作业")
   @RBComment("Import Job")
   public static final String PRIVATE_CONSTANT_392 = "IMPORTJOB_TABLEVIEW_LABEL";

   @RBEntry("默认里程碑视图")
   @RBComment("Default Milestone View")
   public static final String PRIVATE_CONSTANT_393 = "MILESTONE_TABLEVIEW_NAME";

   @RBEntry("默认里程碑搜索表格视图")
   @RBComment("Default Milestone Search Table View")
   public static final String PRIVATE_CONSTANT_394 = "MILESTONE_TABLEVIEW_DESC";

   @RBEntry("里程碑")
   @RBComment("Milestone")
   public static final String PRIVATE_CONSTANT_395 = "MILESTONE_TABLEVIEW_LABEL";

   @RBEntry("默认项目资源视图")
   @RBComment("Default Project Resource View")
   public static final String PRIVATE_CONSTANT_396 = "PROJECTRESOURCE_TABLEVIEW_NAME";

   @RBEntry("默认项目资源搜索表格视图")
   @RBComment("Default Project Resource Search Table View")
   public static final String PRIVATE_CONSTANT_397 = "PROJECTRESOURCE_TABLEVIEW_DESC";

   @RBEntry("项目资源")
   @RBComment("Project Resource")
   public static final String PRIVATE_CONSTANT_398 = "PROJECTRESOURCE_TABLEVIEW_LABEL";

   @RBEntry("默认项目活动视图")
   @RBComment("Default Project Activity View")
   public static final String PRIVATE_CONSTANT_399 = "PROJECTACTIVITY_TABLEVIEW_NAME";

   @RBEntry("默认项目活动搜索表格视图")
   @RBComment("Default Project Activity Search Table View")
   public static final String PRIVATE_CONSTANT_400 = "PROJECTACTIVITY_TABLEVIEW_DESC";

   @RBEntry("项目活动")
   @RBComment("Project Activity")
   public static final String PRIVATE_CONSTANT_401 = "PROJECTACTIVITY_TABLEVIEW_LABEL";

   @RBEntry("默认升级请求视图")
   @RBComment("Default Promotion Request View")
   public static final String PRIVATE_CONSTANT_402 = "PROMOTIONNOTICE_TABLEVIEW_NAME";

   @RBEntry("默认升级请求搜索表格视图")
   @RBComment("Default Promotion Request Search Table View")
   public static final String PRIVATE_CONSTANT_403 = "PROMOTIONNOTICE_TABLEVIEW_DESC";

   @RBEntry("升级请求")
   @RBComment("Promotion Request")
   public static final String PRIVATE_CONSTANT_404 = "PROMOTIONNOTICE_TABLEVIEW_LABEL";

   @RBEntry("默认汇总活动视图")
   @RBComment("Default Summary Activity View")
   public static final String PRIVATE_CONSTANT_405 = "SUMMARYACTIVITY_TABLEVIEW_NAME";

   @RBEntry("默认汇总活动搜索表格视图")
   @RBComment("Default Summary Activity Search Table View")
   public static final String PRIVATE_CONSTANT_406 = "SUMMARYACTIVITY_TABLEVIEW_DESC";

   @RBEntry("汇总活动")
   @RBComment("Summary Activity")
   public static final String PRIVATE_CONSTANT_407 = "SUMMARYACTIVITY_TABLEVIEW_LABEL";

   @RBEntry("默认供应商视图")
   @RBComment("Default Supplier View")
   public static final String PRIVATE_CONSTANT_408 = "SUPPLIER_TABLEVIEW_NAME";

   @RBEntry("默认供应商搜索表格视图")
   @RBComment("Default Supplier Search Table View")
   public static final String PRIVATE_CONSTANT_409 = "SUPPLIER_TABLEVIEW_DESC";

   @RBEntry("供应商")
   @RBComment("Supplier")
   public static final String PRIVATE_CONSTANT_410 = "SUPPLIER_TABLEVIEW_LABEL";

   @RBEntry("默认的供应商部件视图")
   @RBComment("Default Supplier Part View")
   public static final String PRIVATE_CONSTANT_411 = "SUPPLIERPART_TABLEVIEW_NAME";

   @RBEntry("默认的供应商部件搜索表格视图")
   @RBComment("Default Supplier Part Search Table View")
   public static final String PRIVATE_CONSTANT_412 = "SUPPLIERPART_TABLEVIEW_DESC";

   @RBEntry("供应商部件")
   @RBComment("Supplier Part")
   public static final String PRIVATE_CONSTANT_413 = "SUPPLIERPART_TABLEVIEW_LABEL";

   @RBEntry("默认产品视图")
   @RBComment("Default Product View")
   public static final String PRIVATE_CONSTANT_414 = "PDMLINKPRODUCT_TABLEVIEW_NAME";

   @RBEntry("默认产品搜索表格视图")
   @RBComment("Default Product Search Table View")
   public static final String PRIVATE_CONSTANT_415 = "PDMLINKPRODUCT_TABLEVIEW_DESC";

   @RBEntry("产品")
   @RBComment("Product")
   public static final String PRIVATE_CONSTANT_416 = "PDMLINKPRODUCT_TABLEVIEW_LABEL";

   @RBEntry("默认上下文视图")
   @RBComment("Default Context View")
   public static final String PRIVATE_CONSTANT_417 = "CONTAINER_TABLEVIEW_NAME";

   @RBEntry("默认上下文搜索表格视图")
   @RBComment("Default Context Search Table View")
   public static final String PRIVATE_CONSTANT_418 = "CONTAINER_TABLEVIEW_DESC";

   @RBEntry("上下文")
   @RBComment("Context")
   public static final String PRIVATE_CONSTANT_419 = "CONTAINER_TABLEVIEW_LABEL";

   @RBEntry("是")
   @RBComment("Label for True")
   public static final String PRIVATE_CONSTANT_420 = "TRUE_LABEL";

   @RBEntry("否")
   @RBComment("Label for False")
   public static final String PRIVATE_CONSTANT_421 = "FALSE_LABEL";

   @RBEntry("无效的已保存查询 XML 格式")
   @RBComment("Invalid Saved Query XML Format")
   public static final String PRIVATE_CONSTANT_422 = "INVALID_SAVED_QUERY_FORMAT";

   @RBEntry("- 选择 -")
   @RBComment("Label for No Selection")
   public static final String PRIVATE_CONSTANT_423 = "NO_SELECTION_LABEL";

   @RBEntry("设置此搜索作为全局搜索")
   @RBComment("Mark as Global Search")
   public static final String PRIVATE_CONSTANT_424 = "GLOBAL_SEARCH_SHOW";

   @RBEntry("覆盖现有已保存搜索")
   @RBComment("Overwrite Existing Saved Search")
   public static final String PRIVATE_CONSTANT_425 = "OVERWRITE_EXISTING";

   @RBEntry("全局搜索范围:")
   @RBComment("Set Scope of Global Search")
   public static final String PRIVATE_CONSTANT_426 = "SCOPE_OF_GLOBAL_SEARCH";

   @RBEntry("在网络中搜索")
   @RBComment("Label for search in network")
   public static final String PRIVATE_CONSTANT_427 = "SEARCH_IN_NETWORK_LABEL";

   @RBEntry("搜索相关对象")
   @RBComment("Label for search related to objects in a particular context")
   public static final String PRIVATE_CONSTANT_428 = "SEARCH_FOR_ITEMS_IN_CONTEXT_LABEL";

   @RBEntry("网络上下文:")
   @RBComment("Label for the property \"Search In:\" on the search page when the request comes from Program tab.")
   public static final String PRIVATE_CONSTANT_429 = "SEARCH_IN_NETWORK_CONTEXT";

   @RBEntry("搜索类型:")
   @RBComment("Label for the type property on the search page when the request comes from Program tab.")
   public static final String PRIVATE_CONSTANT_430 = "SEARCH_FOR_TYPE";

   @RBEntry("关系:")
   @RBComment("Label for the relationship property on the search page when the request comes from Program tab.")
   public static final String PRIVATE_CONSTANT_431 = "SEARCH_FOR_RELATIONSHIP";

   @RBEntry("文件:")
   @RBComment("Select jar or zip to import")
   public static final String PRIVATE_CONSTANT_432 = "SELECT_FILE_IMPORT";

   @RBEntry("(.jar, .zip)")
   @RBComment("File types allowed for Import Saved Query wizard")
   public static final String PRIVATE_CONSTANT_433 = "IMPORT_FILE_TYPES";

   @RBEntry("文件必须为 .jar  或 .zip 类型。")
   @RBComment("Error message displayed when user tries to import a file other than a jar or zip file.")
   public static final String PRIVATE_CONSTANT_434 = "EXPORT_IMPORT_FILE_ERROR";

   @RBEntry("解决可忽视冲突")
   @RBComment("Label for the resolve overridable conflicts checkbox")
   public static final String PRIVATE_CONSTANT_435 = "RESOLVE_OVERRIDABLE_CONFLICTS";

   @RBEntry("文件:")
   @RBComment("File name into which the savedquery should be exported")
   public static final String PRIVATE_CONSTANT_436 = "SELECT_FILE_EXPORT";

   @RBEntry("(请提供文件路径和扩展名为 .jar 或 .zip 的文件名)")
   @RBComment("File types allowed for Export Saved Query wizard")
   public static final String PRIVATE_CONSTANT_437 = "EXPORT_FILE_TYPES";

   @RBEntry("详细信息日志")
   @RBComment("Label for detailed log link")
   public static final String PRIVATE_CONSTANT_438 = "EXPORT_IMPORT_DETAILED_LOG";

   @RBEntry("无法删除下列选定项: {0}")
   @RBComment("Message showing list of queries couldn't be deleted")
   public static final String PRIVATE_CONSTANT_439 = "DELETE_QUERY_MESSAGE";

   @RBEntry("默认文件夹搜索视图")
   @RBComment("Default Folder Search View")
   public static final String PRIVATE_CONSTANT_440 = "FOLDER_TABLEVIEW_NAME";

   @RBEntry("默认文件夹搜索表格")
   @RBComment("Default Folder Search Table")
   public static final String PRIVATE_CONSTANT_441 = "FOLDER_TABLEVIEW_DESC";

   @RBEntry("文件夹")
   @RBComment("Folder")
   public static final String PRIVATE_CONSTANT_442 = "FOLDER_TABLEVIEW_LABEL";

   @RBEntry("注意: 无法移除标记为必填字段 \n\n以下字段无法移除:\n{0}")
   @RBComment("Message for alert when one tries to remove criteria which is marked required.")
   public static final String PRIVATE_CONSTANT_443 = "REMOVE_QUERYBUILDER_ROW";

   @RBEntry("注意: 缺少必需的信息 \n\n{0} 为必填字段。\n您必须为所有带星号 (*) 的必填字段，指定有效的信息。")
   @RBComment("Message for alert if the required attributes are not filled.")
   public static final String REQ_INFO_MISSING = "REQ_INFO_MISSING";

   @RBEntry("注意: 缺少必需的信息。\n\n 您必须对所有标有星号 (*) 的必填字段指定有效的信息。")
   public static final String SAVED_SEARCH_NAME_MISSING = "SAVED_SEARCH_NAME_MISSING";

   @RBEntry("文件名")
   @RBComment("CADName")
   public static final String PRIVATE_CONSTANT_444 = "CADNAME";

   @RBEntry("反过来搜索: ")
   @RBComment("The query suggestion message.")
   public static final String QUERY_SUGGESTION_MESSAGE = "QUERY_SUGGESTION_MESSAGE";

   @RBEntry("所有者")
   @RBComment("Owner")
   public static final String PRIVATE_CONSTANT_445 = "OWNER_LABEL";

   @RBEntry("检出者")
   @RBComment("Checked Out By")
   public static final String PRIVATE_CONSTANT_446 = "CHECKED_OUT_BY";

   @RBEntry("更新者")
   @RBComment("Updated By")
   public static final String PRIVATE_CONSTANT_447 = "UPDATED_BY";

   @RBEntry("注解文本")
   @RBComment("Note Text")
   public static final String PRIVATE_CONSTANT_448 = "PTC_NOTE_TEXT";

   @RBEntry("小于")
   @RBComment("Less than")
   public static final String PRIVATE_CONSTANT_449 = "OP_LESS";

   @RBEntry("小于等于")
   @RBComment("Less than equal to")
   public static final String PRIVATE_CONSTANT_450 = "OP_LESS_THAN_EQUAL_TO";

   @RBEntry("大于")
   @RBComment("Greater than")
   public static final String PRIVATE_CONSTANT_451 = "OP_GREATOR";

   @RBEntry("大于等于")
   @RBComment("Greater than equal to")
   public static final String PRIVATE_CONSTANT_452 = "OP_GREATOR_THAN_EQUAL_TO";

   @RBEntry("角色名")
   @RBComment("Role Name")
   public static final String PRIVATE_CONSTANT_453 = "ROLE_NAME";

   @RBEntry("角色")
   @RBComment("Role")
   public static final String PRIVATE_CONSTANT_454 = "ROLE";

   @RBEntry("优化搜索")
   @RBComment("Refine search")
   public static final String PRIVATE_CONSTANT_455 = "REFINE_SEARCH";

   @RBEntry("查找对象")
   @RBComment("Item Picker Title")
   public static final String PRIVATE_CONSTANT_456 = "ITEM_PICKER_TITLE";

   @RBEntry("对象选取器")
   @RBComment("Item Picker Label")
   public static final String PRIVATE_CONSTANT_457 = "ITEM_PICKER_LABEL";

   @RBEntry("查找上下文")
   @RBComment("Context Picker Title")
   public static final String PRIVATE_CONSTANT_458 = "CONTEXTS_PICKER_TITLE";

   @RBEntry("上下文选取器")
   @RBComment("Context Picker Label")
   public static final String PRIVATE_CONSTANT_459 = "CONTEXTS_PICKER_LABEL";

   @RBEntry("查找")
   @RBComment("Find")
   public static final String PRIVATE_CONSTANT_460 = "FIND_PICKER_LABEL";

   @RBEntry("视图")
   @RBComment("View")
   public static final String PRIVATE_CONSTANT_461 = "VIEW_PICKER_LABEL";

   @RBEntry("默认采购环境搜索视图")
   @RBComment("Default Sourcing Context Search View")
   public static final String PRIVATE_CONSTANT_462 = "AXLCONTEXT_TABLEVIEW_NAME";

   @RBEntry("默认采购环境搜索表格")
   @RBComment("Default Sourcing Context Search Table")
   public static final String PRIVATE_CONSTANT_463 = "AXLCONTEXT_TABLEVIEW_DESC";

   @RBEntry("采购环境")
   @RBComment("Sourcing Context")
   public static final String PRIVATE_CONSTANT_464 = "AXLCONTEXT_TABLEVIEW_LABEL";

   @RBEntry("默认注解视图")
   @RBComment("Default Note View")
   public static final String PRIVATE_CONSTANT_465 = "NOTE_TABLEVIEW_NAME";

   @RBEntry("默认注解搜索表格")
   @RBComment("Default Note Search Table")
   public static final String PRIVATE_CONSTANT_466 = "NOTE_TABLEVIEW_DESC";

   @RBEntry("导出的搜索结果")
   @RBComment("Exported search results header")
   public static final String PRIVATE_CONSTANT_467 = "EXPORTED_SEARCH_RESULTS";

   @RBEntry("注解")
   @RBComment("Note")
   public static final String PRIVATE_CONSTANT_468 = "NOTE_TABLEVIEW_LABEL";

   @RBEntry("搜索")
   public static final String PRIVATE_CONSTANT_469 = "search.pickerSearch.description";

   @RBEntry("清除")
   public static final String PRIVATE_CONSTANT_470 = "search.pickerClear.description";

   @RBEntry("您可能尚未定义回调函数，或者其中存在错误:")
   @RBComment("Error on picker call back")
   public static final String PICKER_CALL_BACK_ERROR = "PICKER_CALL_BACK_ERROR";

   @RBEntry("名称")
   @RBComment("Name")
   public static final String SAVED_QUERY_NAME = "SAVED_QUERY_NAME";

   @RBEntry("创建者")
   @RBComment("Creator")
   public static final String SAVED_QUERY_CREATOR = "SAVED_QUERY_CREATOR";

   @RBEntry("所拥有页面")
   @RBComment("Owning Page")
   public static final String SAVED_QUERY_OWNINGPAGE = "SAVED_QUERY_OWNINGPAGE";

   @RBEntry("创建时间")
   @RBComment("Created")
   public static final String SAVED_QUERY_CREATEDON = "SAVED_QUERY_CREATEDON";

   @RBEntry("上次更新")
   @RBComment("Last Updated")
   public static final String SAVED_QUERY_UPDATEDON = "SAVED_QUERY_UPDATEDON";

   @RBEntry("所有者")
   @RBComment("Owner")
   public static final String SAVED_QUERY_OWNER = "SAVED_QUERY_OWNER";

   @RBEntry("显示")
   @RBComment("Show")
   public static final String SAVED_QUERY_SHOW = "SAVED_QUERY_SHOW";

   @RBEntry("开启者窗口可能已关闭")
   @RBComment("Message thrown when picker opener is closed")
   public static final String OPENER_WINDOW_CLOSED = "OPENER_WINDOW_CLOSED";

   @RBEntry("未选定任何对象")
   @RBComment("Nothing has been selected")
   public static final String NOTHING_SELECTED = "NOTHING_SELECTED";

   @RBEntry("请指定搜索条件或输入有效关键字。")
   @RBComment("Please specify search criteria or enter a valid keyword.")
   public static final String EMPTY_CRITERAI_MESSAGE = "EMPTY_CRITERAI_MESSAGE";

   @RBEntry("包含所有部件")
   @RBComment("Include All Parts")
   public static final String SEARCH_CRITERIA_INCLUDE_ALL_PARTS = "SEARCH_CRITERIA_INCLUDE_ALL_PARTS";

   @RBEntry("仅包含")
   @RBComment("Include Only")
   public static final String SEARCH_CRITERIA_INCLUDE_ONLY = "SEARCH_CRITERIA_INCLUDE_ONLY";

   @RBEntry("默认追踪代码")
   @RBComment("Default Trace Code")
   public static final String DEFAULT_TRACE_CODE = "DEFAULT_TRACE_CODE";
 
   @RBEntry("成品")
   @RBComment("End Item")
   public static final String DDL_OPTIONS_ENDITEM = "DDL_OPTIONS_ENDITEM";

   @RBEntry("批号")
   @RBComment("Lot")
   public static final String DDL_OPTIONS_LOT = "DDL_OPTIONS_LOT";

   @RBEntry("序列号")
   @RBComment("serialNumber")
   public static final String DDL_OPTIONS_SERIALNUMBER = "DDL_OPTIONS_SERIALNUMBER";

   @RBEntry("批号/序列号")
   @RBComment("lotSerialNumber")
   public static final String DDL_OPTIONS_LOTSERIALNUMBER = "DDL_OPTIONS_LOTSERIALNUMBER";

   @RBEntry("标准")
   @RBComment("standard")
   public static final String DDL_OPTIONS_STANDARD = "DDL_OPTIONS_STANDARD";

   @RBEntry("类属")
   @RBComment("genericParts")
   public static final String DDL_OPTIONS_GENERICPARTS = "DDL_OPTIONS_GENERICPARTS";

   @RBEntry("可配置的类属")
   @RBComment("configurableGenericParts")
   public static final String DDL_OPTIONS_CONFIGURABLEGENERICPARTS = "DDL_OPTIONS_CONFIGURABLEGENERICPARTS";

   @RBEntry("变型")
   @RBComment("variantParts")
   public static final String DDL_OPTIONS_VARIANTPARTS = "DDL_OPTIONS_VARIANTPARTS";

   @RBEntry("高级可配置")
   @RBComment("advancedConfigurableParts")
   public static final String DDL_OPTIONS_ADVANCEDCONFIGURABLEPARTS = "DDL_OPTIONS_ADVANCEDCONFIGURABLEPARTS";

   @RBEntry("高级可配置成品")
   @RBComment("advanced Configurable End Items")
   public static final String DDL_OPTIONS_ADVANCEDCONFIGURABLEENDITEMS = "DDL_OPTIONS_ADVANCEDCONFIGURABLEENDITEMS";

   @RBEntry("可配置")
   @RBComment("configurableParts")
   public static final String DDL_OPTIONS_CONFIGURABLEPARTS = "DDL_OPTIONS_CONFIGURABLEPARTS";
   
   @RBEntry("可配置模块")
   @RBComment("genericType")
   public static final String GENERIC_TYPE = "GENERIC_TYPE";
  
   @RBEntry("“简单搜索”页面上的“文件名”字段")
   @RBComment("Property need to set to Search for CAD Name from Simple search page.")
   public static final String CADNAME_FILENAME_ENABLED = "CADNAME_FILENAME_ENABLED";

   @RBEntry("显示“简单搜索”页面上的“文件名”字段，通过它用户可按 CAD 名称搜索 CAD 文档。")
   @RBComment("Property need to set to Search for CAD Name from Simple search page.")
   public static final String CADNAME_FILENAME_ENABLED_DESCRIPTION = "CADNAME_FILENAME_ENABLED_DESCRIPTION";

   @RBEntry("显示“简单搜索”页面上的“文件名”字段，通过它用户可按 CAD 名称搜索 CAD 文档。")
   @RBComment("Property need to set to Search for CAD Name from Simple search page.")
   public static final String CADNAME_FILENAME_ENABLED_LONGDESCRIPTION = "CADNAME_FILENAME_ENABLED_LONGDESCRIPTION";

   @RBEntry("缩略图\t")
   @RBComment("Thumbnail column label")
   public static final String THUMBNAIL = "THUMBNAIL";

   @RBEntry("每一页的结果要求为有效数值")
   @RBComment("Message thrown when user enters invalid number for result per page.")
   public static final String MESSAGE_RESULT_PER_PAGE = "MESSAGE_RESULT_PER_PAGE";

   @RBEntry("全局搜索默认类型列表")
   @RBComment("display name for global search type list in preference manager")
   public static final String GLOBAL_SEARCH_TYPE_LIST_NAME = "GLOBAL_SEARCH_TYPE_LIST_NAME";

   @RBEntry("此首选项用于指定在全局搜索下拉列表中显示的对象类型。")
   @RBComment("short description of global search type list in preference manager")
   public static final String GLOBAL_SEARCH_TYPE_LIST_DESC = "GLOBAL_SEARCH_TYPE_LIST_DESC";

   @RBEntry("此首选项用于指定在全局搜索下拉列表中显示的对象类型。")
   @RBComment("long description of global search type list in preference manager")
   public static final String GLOBAL_SEARCH_TYPE_LIST_LONG_DESC = "GLOBAL_SEARCH_TYPE_LIST_LONG_DESC";

   @RBEntry("缩略图显示在搜索结果内")
   @RBComment("Display name for thumbnailsOnWCSearch option in Preferences client")
   public static final String THUMBNAILS_ON_WC_SEARCH = "THUMBNAILS_ON_WC_SEARCH";

   @RBEntry("选取是否要将缩略图像显示在搜索结果页面内。")
   @RBComment("Description for thumbnailsOnWCSearch option in the Preferences client")
   public static final String THUMBNAILS_ON_WC_SEARCH_DESCRIPTION = "THUMBNAILS_ON_WC_SEARCH_DESCRIPTION";

   @RBEntry("此首选项由搜索客户端使用。只有在 Creo View 内可视项带有可用缩略图像。注意: 如果不显示缩略图像，搜索结果加载速度会更快。系统默认不显示缩略图。")
   @RBComment("Long description for thumbnailsOnWCSearch option in the Preferences client")
   public static final String THUMBNAILS_ON_WC_SEARCH_LONGDESCRIPTION = "THUMBNAILS_ON_WC_SEARCH_LONGDESCRIPTION";

   @RBEntry("重置 GlobalType 选择")
   @RBComment("Display name for ResetGlobalTypeSelection option in Preferences client")
   public static final String RESET_GLOBAL_TYPESELECTION = "RESET_GLOBAL_TYPESELECTION";

   @RBEntry("允许用户重置全局类型")
   @RBComment("Description for ResetGlobalTypeSelection option in the Preferences client")
   public static final String RESET_GLOBAL_TYPESELECTION_DESCRIPTION = "RESET_GLOBAL_TYPESELECTION_DESCRIPTION";

   @RBEntry("允许用户重置全局类型")
   @RBComment("Long description for ResetGlobalTypeSelection option in the Preferences client")
   public static final String RESET_GLOBAL_TYPESELECTION_LONGDESCRIPTION = "RESET_GLOBAL_TYPESELECTION_LONGDESCRIPTION";

   @RBEntry("所有适用对象类型搜索")
   @RBComment("Display name for allSearchTypes option in Preferences client")
   public static final String ALLSEARCHTYPES = "ALLSEARCHTYPES";

   @RBEntry("类型列表显示在“搜索”页面的类型选取器中。同时，当用户在“简单搜索”页面中选择“所有适用对象类型”作为对象类型时，还显示搜索对象列表。")
   @RBComment("Description for allSearchTypes option in the Preferences client.When you search within a specific context, the object types listed are set by the context administrator and may differ from the object types that you select for this preference.")
   public static final String ALLSEARCHTYPES_DESCRIPTION = "ALLSEARCHTYPES_DESCRIPTION";

   @RBEntry("“搜索”页面上“类型”窗口中显示的类型列表。还有用户在简单搜索页面上选择“所有适用对象类型”作为对象类型时搜索的对象列表。安装 Windchill 服务器时，“所有适用对象类型搜索”首选项中均无值。访问 Windchill “搜索”页面时，根据 SearchableTypes.properties 文件设置首选项默认值。在更改搜索首选项之前至少应访问搜索页面一次。")
   @RBComment("Long description for allSearchTypes option in the Preferences client.When you search within a specific context, the object types listed are set by the context administrator and may differ from the object types that you select for this preference.")
   public static final String ALLSEARCHTYPES_LONGDESCRIPTION = "ALLSEARCHTYPES_LONGDESCRIPTION";

   @RBEntry("全局类型选择")
   @RBComment("Display name for globalTypeSelection option in Preferences client")
   public static final String GLOBAL_TYPE_SELECTION = "GLOBAL_TYPE_SELECTION";

   @RBEntry("此首选项将保存在“简单搜索”页面中选择的类型。当用户选择其他对象类型时，此首选项将发生更改。")
   @RBComment("Description for Default Type Selection option in the Preferences client")
   public static final String GLOBAL_TYPE_SELECTION_DESCRIPTION = "GLOBAL_TYPE_SELECTION_DESCRIPTION";

   @RBEntry("此首选项将保存在“简单搜索”页面中选择的类型。当用户选择其他对象类型时，此首选项将发生更改。")
   @RBComment("Long description for Default Type Selection option in the Preferences client")
   public static final String GLOBAL_TYPE_SELECTION_LONGDESCRIPTION = "GLOBAL_TYPE_SELECTION_LONGDESCRIPTION";

   @RBEntry("高级搜索默认类型列表")
   @RBComment("Display name for searchTypeSelection ")
   public static final String SEARCH_TYPE_SELECTION = "SEARCH_TYPE_SELECTION";

   @RBEntry("此首选项用于指定默认情况下在高级搜索页面中显示的对象类型。")
   @RBComment("Description for Default Type Selection option in the Preferences client")
   public static final String SEARCH_TYPE_SELECTION_DESCRIPTION = "SEARCH_TYPE_SELECTION_DESCRIPTION";

   @RBEntry("此首选项用于指定默认情况下在高级搜索页面中显示的对象类型。")
   @RBComment("Long description for Default Type Selection option in the Preferences client")
   public static final String SEARCH_TYPE_SELECTION_LONGDESCRIPTION = "SEARCH_TYPE_SELECTION_LONGDESCRIPTION";

   @RBEntry("用户已保存搜索的内部名称。")
   @RBComment("Display name for savedSearches option in Preferences client")
   public static final String SAVEDSEARCHES = "SAVEDSEARCHES";

   @RBEntry("用户已保存搜索的内部名称。")
   @RBComment("Description for savedSearches option in the Preferences client")
   public static final String SAVEDSEARCHES_DESCRIPTION = "SAVEDSEARCHES_DESCRIPTION";

   @RBEntry("用户已保存搜索的内部名称。")
   @RBComment("Long description for savedSearches option in the Preferences client")
   public static final String SAVEDSEARCHES_LONGDESCRIPTION = "SAVEDSEARCHES_LONGDESCRIPTION";

   /**
    * added for fixing SPR 1350174
    **/
   @RBEntry("执行此搜索时出错。请与系统管理员联络，通知他出错时您所执行的操作。")
   @RBComment("This value is will be displayed as a warning alert for exceptions which are thrown from Server side without proper embedded messages ")
   public static final String GENERIC_ERROR_MESSAGE = "GENERIC_EXCEPTION_MESSAGE";

   @RBEntry("警告: 搜索结果的数目已达到管理员设置的上限。当达到上限时，搜索会停止并会应用在您的站点强制执行的所有筛选器。这些筛选器仅应用于在达到上限之前返回的对象。为确保获得完整的结果列表，请编辑搜索以应用限制性更强的条件。详情请参阅联机帮助。")
   @RBComment("Message to display to a user when there are possible additional search results in Rware and they need to make their criteria more specific")
   public static final String INCOMPLETE_RESULT_SET_WARNING = "INCOMPLETE_RESULT_SET_WARNING";

   @RBEntry("警告: 搜索结果的数目已达到管理员设置的上限。当达到上限时，搜索会停止并会应用在您的站点强制执行的所有筛选器。这些筛选器仅应用于在达到上限之前返回的对象。为确保获得完整的结果列表，请编辑搜索以应用限制性更强的条件。详情请参阅联机帮助。")
   @RBComment("Message to display to a user when there are possible additional search results at DB and they need to make their criteria more specific")
   public static final String INCOMPLETE_DB_RESULT_SET_WARNING = "INCOMPLETE_DB_RESULT_SET_WARNING";

   @RBEntry("每页结果数")
   @RBComment("Display name for result per page in Preferences client")
   public static final String SEARCHTABLELIMIT = "SEARCHTABLELIMIT";

   @RBEntry("此首选项设置每页结果数的默认值。默认情况下该值为 15。")
   @RBComment("Description for Results per page option in the Preferences client")
   public static final String RESULT_PER_PAGE_DESCRIPTION = "SEARCHTABLELIMIT_DESCRIPTION";

   @RBEntry("此首选项设置每页结果数的默认值。默认情况下该值为 15。")
   @RBComment("Long description for Results per page option in the Preferences client")
   public static final String RESULT_PER_PAGE_LONGDESCRIPTION = "SEARCHTABLELIMIT_LONGDESCRIPTION";

   @RBEntry("注意: 项已位于选定对象列表中...\n\n该选定列表已包含某些您要移动的项:\n{0}\n\n将添加那些已标为未包含在选定列表中的项。")
   @RBComment("Attention: Item(s) already in Selected object list...\n\nThe Selected list already contains some of the items you are attempting to move:\n{0}\n\nItems you indicated that are not already in the Selected list will be added.")
   public static final String SOME_ITEMS_ALREADY_SELECTED = "SOME_ITEMS_ALREADY_SELECTED";

   @RBEntry("注意: 项已位于选定对象列表中...\n\n该选定列表已包含您要移动的项:\n{0}")
   @RBComment("Attention: Item(s) already in Selected object list...\n\nThe Selected list already contains the items you are attempting to move:\n{0}")
   public static final String ALL_ITEMS_ALREADY_SELECTED = "ALL_ITEMS_ALREADY_SELECTED";

   @RBEntry("未追踪")
   @RBComment("untracedParts")
   public static final String DDL_OPTIONS_UUNTRACED = "DDL_OPTIONS_UUNTRACED";

   @RBEntry("“仅包含”选项要求至少选择一个选项。")
   @RBComment("Alert message if user selects no checkbox from include only radio.")
   public static final String DDLALERT = "DDLALERT";

   @RBEntry("值已存在于系统中。\n\n您在此页输入的名称已存在。请单击“确定”覆盖现有名称，或单击“取消”返回以输入新名称。")
   @RBComment("Alert message for same saved search name")
   public static final String SAVEDSEARCHNAMEALERT = "SAVEDSEARCHNAMEALERT";

   @RBEntry("合约单项产品编号")
   @RBComment("search attribute for CDRLBasePackage")
   public static final String CONTRACT_LINE_ITEM = "CONTRACT_LINE_ITEM";

   @RBEntry("合同编号")
   @RBComment("search attribute for CDRLBasePackage")
   public static final String CONTRACT_NUMBER = "CONTRACT_NUMBER";

   @RBEntry("注意: 要\"删除\"的对象不符合条件。\n\n 不符合条件的对象将不被删除。")
   @RBComment("Delete action is invalid for one or more selected objects")
   public static final String DELETE_ACTION_INVALID = "DELETE_ACTION_INVALID";

   @RBEntry("文件夹")
   @RBComment("Label for folder search picker.")
   public static final String LOCATION_PICKER_LABEL = "LOCATION_PICKER_LABEL";

   @RBEntry("上次搜索")
   @RBComment("Last Search")
   public static final String LAST_SEARCH = "LAST_SEARCH";

   @RBEntry("创作应用程序")
   @RBComment("Label for EPMDocument authoring application")
   public static final String EPM_AUTHORING_APPLICATION = "EPM_AUTHORING_APPLICATION";

   @RBEntry("文档类别")
   @RBComment("Label for EPMDocument document category")
   public static final String EPM_DOCUMENT_TYPE = "EPM_DOCUMENT_TYPE";

   @RBEntry("默认协议视图")
   @RBComment("Default Agreement View")
   public static final String PRIVATE_CONSTANT_471 = "AUTHORIZATIONAGREEMENT_TABLEVIEW_NAME";

   @RBEntry("默认协议搜索表格视图")
   @RBComment("Default Agreement Search Table View")
   public static final String PRIVATE_CONSTANT_472 = "AUTHORIZATIONAGREEMENT_TABLEVIEW_DESC";

   @RBEntry("协议")
   @RBComment("Agreement")
   public static final String PRIVATE_CONSTANT_473 = "AUTHORIZATIONAGREEMENT_TABLEVIEW_LABEL";

   @RBEntry("资源类型")
   @RBComment("Label for Resource User Type dropdown having values Windchill User and Other")
   public static final String PLAN_RESOURCE_NAME_LABEL = "PLAN_RESOURCE_NAME_LABEL";

   @RBEntry("CAGE 代码")
   @RBComment("Org field name on search page when wadam is installed")
   public static final String WADMORG = "WADMORG";

   @RBEntry("部件配置名称")
   @RBComment("Label for configuration name in the configuration compare search picker")
   public static final String PART_CONFIGURATION_NAME_LABEL = "PART_CONFIGURATION_NAME_LABEL";

   @RBEntry("基础部件名称")
   @RBComment("Label for base part name in the configuration compare search picker")
   public static final String BASE_PART_NAME_LABEL = "BASE_PART_NAME_LABEL";

   @RBEntry("基础部件编号")
   @RBComment("Label for base part number in the configuration compare search picker")
   public static final String BASE_PART_NUMBER_LABEL = "BASE_PART_NUMBER_LABEL";

   @RBEntry("联系人")
   @RBComment("Label for the saved search customize table contact column")
   public static final String PRIVATE_CONSTANT_474 = "CONTACT_LABEL";

   @RBEntry("显示高级搜索")
   @RBComment("Label used at picker")
   public static final String ADVANCED_SEARCH_LBL = "ADVANCED_SEARCH_LBL";

   @RBEntry("隐藏高级搜索")
   @RBComment("Label used at picker")
   public static final String BASIC_SEARCH_LBL = "BASIC_SEARCH_LBL";

   @RBEntry("类型")
   @RBComment("Label used at object type picker")
   public static final String OBJECT_TYPE_LBL = "OBJECT_TYPE_LBL";

   @RBEntry("类型")
   @RBComment("Label used at object type picker on advanced search page")
   public static final String TYPE_LBL = "TYPE_LBL";

   @RBEntry("代码")
   @RBComment("Label for the code field in Choice Search")
   public static final String PRIVATE_CONSTANT_475 = "CODE_LABEL";

   @RBEntry("选项")
   @RBComment("Label for the option field in Choice Search")
   public static final String PRIVATE_CONSTANT_476 = "OPTION_LABEL";

   @RBEntry("组")
   @RBComment("Label for the group field in Choice Search")
   public static final String PRIVATE_CONSTANT_477 = "GROUP_LABEL";

   @RBEntry("特定上下文")
   @RBComment("Static Find Specific Context option added in context picker dropdown")
   public static final String PRIVATE_CONSTANT_478 = "FIND_CONTEXT";

   @RBEntry("格式图标")
   @RBComment("Label for the column \"Format Icon\" in tables")
   public static final String FORMAT_ICON = "FORMAT_ICON";

   @RBEntry("每页的结果必须小于")
   @RBComment("Max result per page cap.")
   public static final String RESULT_PER_PAGEMAX_CAP = "RESULT_PER_PAGEMAX_CAP";

   @RBEntry("选择日期范围")
   @RBComment("Choose Date Range")
   public static final String DATE_DATE_RANGE = "DATE_DATE_RANGE";

   @RBEntry("选取天数范围")
   @RBComment("Choose Day Range")
   public static final String DATE_DAY_RANGE = "DATE_DAY_RANGE";

   @RBEntry("昨天")
   @RBComment("Yesterday")
   public static final String DATE_YESTERDAY = "DATE_YESTERDAY";

   @RBEntry("今天")
   @RBComment("Today")
   public static final String DATE_TODAY = "DATE_TODAY";

   @RBEntry("明天")
   @RBComment("Tomorrow")
   public static final String DATE_TOMORROW = "DATE_TOMORROW";

   @RBEntry("天前")
   @RBComment("days ago")
   public static final String DAYS_AGO = "DAYS_AGO";

   @RBEntry("天 (从现在起)")
   @RBComment("days from now")
   public static final String DAYS_NOW = "DAYS_NOW";

   @RBEntry("新建视图")
   public static final String PRIVATE_CONSTANT_479 = "search.newView.description";

   @RBEntry("过时的条件。")
   @RBComment("Message for alert when some of the criteria's are missing after upgrade.")
   public static final String MISSING_QUERYBUILDER_ROW = "MISSING_QUERYBUILDER_ROW";

   @RBEntry("升级到最新版本之后，此条件已经过时。删除此条件并从条件列表中添加新条件，然后保存搜索。如果是管理员分配的保存的搜索，则请求管理员重新创建保存的搜索。")
   @RBComment("Message for alert when some of the criteria's are missing after upgrade.")
   public static final String INVALID_QUERYBUILDER_ROW = "INVALID_QUERYBUILDER_ROW";

   @RBEntry("ID")
   @RBComment("Label for Project Plan Activity ID.")
   public static final String PLAN_ACTIVITY_ID = "PLAN_ACTIVITY_ID";

   @RBEntry("无效的高级搜索类型。")
   @RBComment("wt.fc.Persistable is not a valid type for advanced search")
   public static final String INVALID_TYPE_FOR_ADVANCED_SEARCH = "INVALID_TYPE_FOR_ADVANCED_SEARCH";

   @RBEntry("新建视图")
   @RBComment("new view")
   public static final String NEWVIEW = "NEWVIEW";

   @RBEntry("关闭")
   public static final String PRIVATE_CONSTANT_480 = "search.searchCloseButton.description";

   @RBEntry("制造")
   public static final String PRIVATE_CONSTANT_481 = "MAKE_FACET";

   @RBEntry("购买")
   public static final String PRIVATE_CONSTANT_482 = "BUY_FACET";

   @RBEntry("正在工作")
   public static final String PRIVATE_CONSTANT_483 = "INWORK_FACET";

   @RBEntry("已发布")
   public static final String PRIVATE_CONSTANT_484 = "RELEASED_FACET";

   @RBEntry("部件")
   public static final String PRIVATE_CONSTANT_485 = "WTPART_FACET";

   @RBEntry("文档")
   public static final String PRIVATE_CONSTANT_486 = "WTDOCUMENT_FACET";

   @RBEntry("收件人姓名")
   @RBComment("Field name for recipent name in delta delivery picker")
   public static final String RECIPIENT_NAME = "RECIPIENT_NAME";

   @RBEntry("管理")
   public static final String PRIVATE_CONSTANT_487 = "search.savedSearchTable.description";

   @RBEntry("管理")
   public static final String PRIVATE_CONSTANT_488 = "search.savedSearchTable.tooltip";

   @RBEntry("管理保存的搜索")
   public static final String PRIVATE_CONSTANT_489 = "search.savedSearchTable.title";

   @RBEntry("height=450,width=920")
   public static final String PRIVATE_CONSTANT_523 = "search.savedSearchTable.moreurlinfo";

   @RBEntry("保存此搜索")
   public static final String PRIVATE_CONSTANT_490 = "search.saveThisSearchDefaultName.description";

   @RBEntry("height=250,width=450")
   public static final String PRIVATE_CONSTANT_491 = "search.saveThisSearchDefaultName.moreurlinfo";

   @RBEntry("保存此搜索")
   public static final String PRIVATE_CONSTANT_492 = "search.saveThisSearchDefaultName.title";

   @RBEntry("名称")
   public static final String SAVED_SEARCH_DISPLAY_NAME = "SAVED_SEARCH_DISPLAY_NAME";

   @RBEntry("关闭")
   public static final String PRIVATE_CONSTANT_493 = "search.savedSearchLightBoxCloseButton.description";

   @RBEntry("搜索历史记录和保存的搜索")
   public static final String PRIVATE_CONSTANT_494 = "search.searchHistSavedSearch.description";

   @RBEntry("高级搜索")
   public static final String PRIVATE_CONSTANT_495 = "search.advancedSearch.description";

   @RBEntry("分类搜索")
   public static final String PRIVATE_CONSTANT_496 = "search.classificationSearch.description";

   @RBEntry("搜索历史记录")
   @RBComment("Heading for Search History Display")
   public static final String SEARCH_HISTORY_HEADING = "SEARCH_HISTORY_HEADING";

   @RBEntry("保存的搜索")
   @RBComment("Heading for Saved Search Display")
   public static final String SAVED_SEARCH_HEADING = "SAVED_SEARCH_HEADING";

   @RBEntry("由我创建")
   @RBComment("Saved Search section for queries saved by current user")
   public static final String SAVED_SEARCH_CREATED_BY_ME = "SAVED_SEARCH_CREATED_BY_ME";

   @RBEntry("与我共享")
   @RBComment("Saved Search section for queries saved by current user")
   public static final String SAVED_SEARCH_SHARED_WITH_ME = "SAVED_SEARCH_SHARED_WITH_ME";

   @RBEntry("今天")
   @RBComment(" Search History for today's searches.")
   public static final String SEARCH_HISTORY_TODAY = "SEARCH_HISTORY_TODAY";

   @RBEntry("更旧的")
   @RBComment(" Search History for older searches.")
   public static final String SEARCH_HISTORY_OLDER = "SEARCH_HISTORY_OLDER";

   @RBEntry("今天未执行任何搜索")
   @RBComment("label when no records found in todays history")
   public static final String SEARCH_HISTORY_TODAY_NO_RECORDS_LABEL = "SEARCH_HISTORY_TODAY_NO_RECORDS_LABEL";

   @RBEntry("无")
   @RBComment("label when no records found in Saved Searches.")
   public static final String SAVED_SEARCH_NO_RECORDS_LABEL = "SAVED_SEARCH_NO_RECORDS_LABEL";

   @RBEntry("编辑条件")
   @RBComment("Label for Edit action in saved search & search history display.")
   public static final String PRIVATE_CONSTANT_497 = "EDIT_LABEL";

   @RBEntry("关键字")
   @RBComment("Being used in the search history display page in search history link.")
   public static final String KEYWORD_FOR_SEARCH_HISTORY_LINK = "KEYWORD_FOR_SEARCH_HISTORY_LINK";

   @RBEntry("类型")
   @RBComment("Being used in the search history display page in search history link.")
   public static final String TYPE_FOR_SEARCH_HISTORY_LINK = "TYPE_FOR_SEARCH_HISTORY_LINK";

   @RBEntry("默认选项集视图")
   @RBComment("Default Option Set View")
   public static final String PRIVATE_CONSTANT_498 = "OPTIONSET_TABLEVIEW_NAME";

   @RBEntry("默认选项集搜索表格视图")
   @RBComment("Default Option Set Search Table View")
   public static final String PRIVATE_CONSTANT_499 = "OPTIONSET_TABLEVIEW_DESC";

   @RBEntry("选项集")
   @RBComment("Option Set ")
   public static final String PRIVATE_CONSTANT_500 = "OPTIONSET_TABLEVIEW_LABEL";

   @RBEntry("确定")
   @RBComment("Label for Ok button in Ext Window for lightbix.")
   public static final String PRIVATE_CONSTANT_501 = "Ok_LABEL";

   @RBEntry("取消")
   @RBComment("Label for Cancel button in Ext Window for lightbix.")
   public static final String PRIVATE_CONSTANT_502 = "Cancel_LABEL";

   @RBEntry("部件的附加条件:")
   @RBComment("Label for Additonal Criteria for Parts")
   public static final String PRIVATE_CONSTANT_503 = "PART_ADDITIONAL_CRITERIA";

   @RBEntry("保存")
   @RBComment("Label for Save button in Ext Window for lightbix.")
   public static final String PRIVATE_CONSTANT_504 = "Save_LABEL";

   @RBEntry("选项")
   @RBComment("Caption text at type picker launch point at search page")
   public static final String TYPE_PICKER_MENU_CAPTION = "TYPE_PICKER_MENU_CAPTION";

   @RBEntry("添加")
   @RBComment("Caption text at type picker launch point at search page")
   public static final String TYPE_PICKER_MENU_ADD_UPDATE = "TYPE_PICKER_MENU_ADD_UPDATE";

   @RBEntry("恢复默认值")
   @RBComment("Caption text at type picker launch point at search page")
   public static final String TYPE_PICKER_MENU_RESTORE_DEFAULT = "TYPE_PICKER_MENU_RESTORE_DEFAULT";

   @RBEntry("所有类型")
   @RBComment("display name for persistable type")
   public static final String ALL_TYPES = "ALL_TYPES";

   @RBEntry("选择关系")
   @RBComment("label for relations")
   public static final String SELECT_RELATIONS = "SELECT_RELATIONS";

   @RBEntry("默认选择视图")
   @RBComment("Default Choice View")
   public static final String PRIVATE_CONSTANT_505 = "CHOICE_TABLEVIEW_NAME";

   @RBEntry("默认选择搜索表格视图")
   @RBComment("Default Choice Search Table View")
   public static final String PRIVATE_CONSTANT_506 = "CHOICE_TABLEVIEW_DESC";

   @RBEntry("选择 ")
   @RBComment("Choice ")
   public static final String PRIVATE_CONSTANT_507 = "CHOICE_TABLEVIEW_LABEL";

   @RBEntry("无法加载保存的搜索，因为一个或多个相关对象已删除。")
   @RBComment(" Error message to be shown when user selects a deleted saved query for edit or execute.")
   public static final String SAVED_SEARCH_DELETED_MESSAGE = "SAVED_SEARCH_DELETED_MESSAGE";

   /**
    * special column labels
    **/
   @RBEntry("名称")
   public static final String PACKAGE_NAME = "PACKAGE_NAME";

   @RBEntry("包编号")
   public static final String PACKAGE_NUMBER = "PACKAGE_NUMBER";

   @RBEntry("包版本")
   public static final String PACKAGE_VERSION = "PACKAGE_VERSION";

   @RBEntry("全部")
   public static final String ALL_VIEW = "ALL_VIEW";

   @RBEntry("全部")
   public static final String ALL_VIEW_DESCRIPTION = "ALL_VIEW_DESCRIPTION";

   @RBEntry("保存的搜索名称")
   @RBComment("Label of the saved search name being displayed on advanced search screen.")
   public static final String SAVED_SEARCH_NAME_LABEL = "SAVED_SEARCH_NAME_LABEL";

   @RBEntry("启动新搜索")
   @RBComment("label for start a new search link on the search page")
   public static final String START_A_NEW_SEARCH_LINK = "START_A_NEW_SEARCH_LINK";

   @RBEntry("保存此搜索")
   @RBComment("label for save this search link on the search page ")
   public static final String SAVE_THIS_SEARCH_LINK = "SAVE_THIS_SEARCH_LINK";

   @RBEntry("编辑搜索条件")
   @RBComment(" label for edit search criteria link on the search page ")
   public static final String EDIT_SEARCH_CRITERIA_LINK = "EDIT_SEARCH_CRITERIA_LINK";

   @RBEntry("查看搜索历史记录和保存的搜索")
   @RBComment("Label of the view saved search option")
   public static final String VIEW_SAVED_SEARCH_LABEL = "VIEW_SAVED_SEARCH_LABEL";

   @RBEntry("选取类型")
   @RBComment("Label of the choose type option")
   public static final String CHOOSE_TYPE_LABEL = "CHOOSE_TYPE_LABEL";

   @RBEntry("所有对象类型")
   @RBComment("Label of the all type option")
   public static final String ALL_TYPE_LABEL = "ALL_TYPE_LABEL";

   @RBEntry("搜索历史记录和保存的搜索")
   @RBComment("Tab label for history & saved searches page")
   public static final String PRIVATE_CONSTANT_508 = "navigation.historySaved.description";

   @RBEntry("高级搜索")
   @RBComment("Tab label for Advanced search page")
   public static final String PRIVATE_CONSTANT_509 = "navigation.advanced.description";

   @RBEntry("分类搜索")
   @RBComment("Tab label for Classification search page")
   public static final String PRIVATE_CONSTANT_510 = "navigation.classification.description";

   @RBEntry("附加条件")
   @RBComment("Label for Additional Criteria on search info page for search history and saved searches")
   public static final String ADDITIONAL_CRITERIA_LABEL = "ADDITIONAL_CRITERIA_LABEL";


   @RBEntry("上次执行时间")
   @RBComment("Label for Last Performed on search info page for search history and saved searches")
   public static final String LAST_PERFORMED_ON_LABEL = "LAST_PERFORMED_ON_LABEL";


   @RBEntry("最近访问的上下文选取器列表大小")
   @RBComment("Determines maximum number of items in recently visited Context Picker drop-down.")
   public static final String MULTISELECT_CONTEXTPICKER_LIMIT = "MULTISELECT_CONTEXTPICKER_LIMIT";

   @RBEntry("您最多只能从结果中选取 {0} 个上下文。请取消选取一些上下文以继续操作。")
   @RBComment("Alert to be shown for user if selects more contexts than preference set for search page.")
   public static final String MULTISELECT_CONTEXTPICKER_LIMIT_ALERT = "MULTISELECT_CONTEXTPICKER_LIMIT_ALERT";

   @RBEntry("网络关系")
   @RBComment("Label for the network realtionship display in search criteria string ")
   public static final String NETWORK_RELATIONSHIP_LABEL = "NETWORK_RELATIONSHIP_LABEL";


   @RBEntry("网络上下文")
   @RBComment("Label for the network context display in search criteria string ")
   public static final String NETWORK_CONTEXT_LABEL = "NETWORK_CONTEXT_LABEL";


   @RBEntry("编辑条件")
   @RBComment("Link for edit criteria on search info page for search history and saved searches")
   public static final String EDIT_CRITERIA_LINK = "EDIT_CRITERIA_LINK";

   @RBEntry("DDL 条件")
   @RBComment("DDL  Criteria label on search info page for search history and saved searches")
   public static final String DDL_CRITERIA_LABEL = "DDL_CRITERIA_LABEL";

   @RBEntry("相关对象")
   @RBComment("Related Object label on search info page for search history and saved searches")
   public static final String RELATED_OBJECT_LABEL = "RELATED_OBJECT_LABEL";


   @RBEntry("存档面板")
   @RBComment("Archive Panel label on search info page for search history and saved searches")
   public static final String ARCHIVE_PANEL_LABEL = "ARCHIVE_PANEL_LABEL";

   @RBEntry("默认技能视图")
   @RBComment("Default Skill View")
   public static final String PRIVATE_CONSTANT_511 = "MPMPSKILL_TABLEVIEW_NAME";

   @RBEntry("默认技能搜索表格视图")
   @RBComment("Default Skill Search Table View")
   public static final String PRIVATE_CONSTANT_512 = "MPMPSKILL_TABLEVIEW_DESC";

   @RBEntry("技能")
   @RBComment("Skill")
   public static final String PRIVATE_CONSTANT_513 = "MPMPSKILL_TABLEVIEW_LABEL";

   @RBEntry("默认刀具视图")
   @RBComment("Default Tooling View")
   public static final String PRIVATE_CONSTANT_514 = "MPMTOOLING_TABLEVIEW_NAME";

   @RBEntry("默认刀具搜索表格视图")
   @RBComment("Default Tooling Search Table View")
   public static final String PRIVATE_CONSTANT_515 = "MPMTOOLING_TABLEVIEW_DESC";

   @RBEntry("刀具")
   @RBComment("Tooling")
   public static final String PRIVATE_CONSTANT_516 = "MPMTOOLING_TABLEVIEW_LABEL";

   @RBEntry("默认工作中心视图")
   @RBComment("Default WorkCenter View")
   public static final String PRIVATE_CONSTANT_517 = "MPMWORKCENTER_TABLEVIEW_NAME";

   @RBEntry("默认工作中心搜索表格视图")
   @RBComment("Default WorkCenter Search Table View")
   public static final String PRIVATE_CONSTANT_518 = "MPMWORKCENTER_TABLEVIEW_DESC";

   @RBEntry("工作中心")
   @RBComment("WorkCenter")
   public static final String PRIVATE_CONSTANT_519 = "MPMWORKCENTER_TABLEVIEW_LABEL";

   @RBEntry("默认工艺材料视图")
   @RBComment("Default Process Material View")
   public static final String PRIVATE_CONSTANT_520 = "MPMPROCESSMATERIAL_TABLEVIEW_NAME";

   @RBEntry("默认工艺材料搜索表格视图")
   @RBComment("Default Process Material Search Table View")
   public static final String PRIVATE_CONSTANT_521 = "MPMPROCESSMATERIAL_TABLEVIEW_DESC";

   @RBEntry("工艺材料")
   @RBComment("Process Material")
   public static final String PRIVATE_CONSTANT_522 = "MPMPROCESSMATERIAL_TABLEVIEW_LABEL";



   @RBEntry("保存此搜索")
   public static final String saveThisSearchProEDesc = "search.saveThisSearchProE.description";

   @RBEntry("保存此搜索")
   public static final String saveThisSearchProEToolT = "search.saveThisSearchProE.tooltip";

   @RBEntry("保存此搜索")
   public static final String saveThisSearchProETitle = "search.saveThisSearchProE.title";

   @RBEntry("最多至")
   public static final String uoToLabel = "UP_TO_ATTRIBUTE";

   @RBEntry("{0} 天前")
   public static final String daysAgoAttrib = "DAYS_AGO_TO_ATTRIBUTE";

   @RBEntry("{0} 天 (从现在起)")
   public static final String daysFromNowAttrib = "DAYS_FROM_NOW_ATTRIBUTE";

   @RBEntry("其他")
   @RBComment("More lable in query builder")
   public static final String moreQBLabel = "MORE_QB_LABEL";


   @RBEntry("选项")
   public static final String options = "OPTIONS";

   @RBEntry("添加上下文")
   public static final String addContext = "ADD_CONTEXT";

   @RBEntry("更多选项")
   public static final String moreOptions = "MORE_OPTIONS";

   @RBEntry("确定")
   public static final String ok = "OK";

   @RBEntry("取消")
   public static final String cancel = "CANCEL";

   @RBEntry("搜索范围")
   public static final String searchIn = "SEARCH_IN";

   @RBEntry("成员资格")
   public static final String membership = "MEMBERSHIP";

   @RBEntry("我是成员")
   public static final String iAmAMemberOf = "I_AM_A_MEMBER_OF";

   @RBEntry("位于我的组织内")
   public static final String areInMyOrganization = "ARE_IN_MY_ORGANIZATION";

   @RBEntry("请输入一个有效数值。")
   @RBComment("Msg for invalid numeric entry.")
   public static final String INVALID_NUMBER = "INVALID_NUMBER";

   @RBEntry("新建")
   @RBComment("Label of submenu New")
   public static final String new_submenu = "object.new_submenu.description";

   @RBEntry("添加至")
   @RBComment("Label of submenu Add to")
   public static final String add_to_submenu = "object.add_to_submenu.description";

   @RBEntry("比较")
   @RBComment("Label of submenu Compare")
   public static final String compare_submenu = "object.compare_submenu.description";

   @RBEntry("处理")
   @RBComment("Label of submenu Process")
   public static final String process_submenu = "object.process_submenu.description";

   @RBEntry("未定义")
   public static final String UNDEFINED_LABEL = "UNDEFINED_LABEL";

   @RBEntry("选择存档条件")
   @RBComment("Label of link to launch archive panel on search page")
   public static final String ARCHIVE_LINK_LEVEL = "ARCHIVE_LINK_LEVEL";

   @RBEntry("选定的对象无一支持此操作")
   @RBComment("Warning messafe to user when invalid objects are selected to perform Email object owners action")
   public static final String INVALID_OBJECT_EMAIL = "INVALID_OBJECT_EMAIL";

   @RBEntry("选定对象几乎都支持此操作。请按“确定”继续")
   @RBComment("Warning messafe to user when invalid objects are selected to perform Email object owners action")
   public static final String FEW_INVALID_OBJECT_EMAIL = "FEW_INVALID_OBJECT_EMAIL";


   @RBEntry("不能同时搜索 {0} 和其他类型。")
   @RBComment("Message alerting the individual type cannot be searched with other types. ")
   public static final String INDIVIDUAL_SEARCH_TYPE = "INDIVIDUAL_SEARCH_TYPE";


   @RBEntry("备用标识符")
   @RBComment("Field name for user record key in Quality People or Places")
   public static final String ALTERNATE_IDENTIFIER = "ALTERNATE_IDENTIFIER";

   @RBEntry("电话号码")
   @RBComment("Field name for Phone number in Quality People or Places")
   public static final String PHONE_NUMBER = "PHONE_NUMBER";

   @RBEntry("电子邮件")
   @RBComment("Field name for E-mail address in Quality People or Places")
   public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";

   @RBEntry("邮政编码")
   @RBComment("Field name for Postal code in Quality People or Places")
   public static final String POSTAL_CODE = "POSTAL_CODE";

   @RBEntry("保存查询: ")
   @RBComment("Saved Search Create Message")
   public static final String SAVED_SEARCH_CREATED = "SAVED_SEARCH_CREATED";

   @RBEntry("确认: 创建成功")
   @RBComment("Saved Search Create Message Title")
   public static final String SAVED_SEARCH_CREATED_TITLE = "SAVED_SEARCH_CREATED_TITLE";

   @RBEntry("默认工作流进程模板视图")
   @RBComment("Default Workflow Process Template View")
   public static final String PRIVATE_CONSTANT_530 = "WFTEMPLATE_TABLEVIEW_NAME";

   @RBEntry("默认工作流进程模板搜索表格视图")
   @RBComment("Default Workflow Process Template Search Table View")
   public static final String PRIVATE_CONSTANT_531 = "WFTEMPLATE_TABLEVIEW_DESC";

   @RBEntry("工作流进程模板")
   @RBComment("Workflow Process Template")
   public static final String PRIVATE_CONSTANT_532 = "WFTEMPLATE_TABLEVIEW_LABEL";
   @RBEntry("注意: 对于小版本对象，将删除选定修订版本的所有小版本。")
   @RBComment("If one or more of the selected objects are iterated. All iterations for the selected object revisions will be deleted.")
   public static final String CONFIRM_DELETE = "CONFIRM_DELETE";

   @RBEntry("默认收到的交付视图")
   @RBComment("Default Received Delivery View")
   public static final String PRIVATE_CONSTANT_533 = "RECEIVEDDELIVERY_TABLEVIEW_NAME";

   @RBEntry("默认收到的交付搜索表格视图")
   @RBComment("Default Received Delivery Search Table View")
   public static final String PRIVATE_CONSTANT_534 = "RECEIVEDDELIVERY_TABLEVIEW_DESC";

   @RBEntry("收到的交付")
   @RBComment("Received Delivery")
   public static final String PRIVATE_CONSTANT_535 = "RECEIVEDDELIVERY_TABLEVIEW_LABEL";

   @RBEntry("视图名称")
   @RBComment("view name label")
   public static final String PRIVATE_CONSTANT_536 = "VIEW_NAME_LABEL";

   // resources for Clear icon in Structured Enumeration Picker
   @RBEntry("清除...")
   public static final String PRIVATE_CONSTANT_537 = "search.callSearchPickerClear.description";

   // resources for Clear icon in Structured Enumeration Picker
   @RBEntry("清除...")
   public static final String PRIVATE_CONSTANT_538 = "search.callSearchPickerClear.tooltip";

   @RBEntry("编号")
   public static final String PRIVATE_CONSTANT_539 = "2";

   @RBEntry("名称")
   public static final String PRIVATE_CONSTANT_540 = "1";

   @RBEntry("请指定搜索条件")
   @RBComment("Alert shown to the user when empty search is executed with mulitple object types or multiple object types.")
   public static final String MULTI_TYPE_EMPTY_SEARCH_MESSAGE_TITLE = "MULTI_TYPE_EMPTY_SEARCH_MESSAGE_TITLE";

   @RBEntry("日期不是必需的格式 yyyy/mm/dd，或者超出范围。")
   public static final String DATE_ERROR = "DATE_ERROR";

   @RBEntry("搜索未成功完成。此问题可能因搜索条件导致，也可能是您没有权限访问搜索返回的对象。请编辑搜索条件并重试，或者联系您的系统管理员以获得进一步协助。")
   @RBComment("Message to display to a user when there is there is an invalid search criteria.")
   public static final String INVALID_CRITERIA_ERROR = "INVALID_CRITERIA_ERROR";

   /*
    * ***********************************************************
    * Search in Folder Resources
    *************************************************************
    */
   @RBEntry("在选定文件夹内搜索")
   @RBComment("Empty text for the Search in Folder component")
   public static final String SEARCH_IN_FOLDER_PROMPT = "SEARCH_IN_FOLDER_PROMPT";

   @RBEntry("清除")
   @RBComment("Clear text in Search in Folder component. Reset the text to Empty text string")
   public static final String CLEAR_SEARCH_IN_FOLDER = "CLEAR_SEARCH_IN_FOLDER";

   @RBEntry("索引服务器停止运行")
   @RBComment("Index Server down title")
   public static final String WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEAR_TITLE = "WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEARCH_TITLE";

   @RBEntry("索引搜索功能不可用，已转而使用属性搜索。您预期的对象可能不包含在搜索结果中。请联系您的 Windchill 系统管理员。")
   @RBComment("Index Server down. Fall back to DB Search.")
   public static final String WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEARCH = "WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEARCH";

   /*
    ************************************************************
    * Structured Enumeration Picker Resources
    ************************************************************
    */
   @RBEntry("分类树")
   @RBComment("Title for Classification Tree")
   public static final String CLASSIFICATION_TREE_TITLE = "csm.clfTree.title";

   @RBEntry("类")
   @RBComment("Title for the column in Classification Tree")
   public static final String CLASSIFICATION_TREE_COLUMN_NAME = "csm.clfTreeColumnName.title";

   // following entries are added as a part of story B-100525
   @RBEntry("请检查选定的对象类型，然后重新搜索。")
   @RBComment("The message to be shown to user when the parent and child types both are selected and parent types should be removed")
   public static final String PARENT_TYPE_SELECTED_MESSAGE = "PARENT_TYPE_SELECTED_MESSAGE";

   @RBEntry("无法对层次结构内的对象执行搜索。")
   @RBComment("The title to be shown to user when the parent and child types both are selected and parent types should be removed")
   public static final String PARENT_TYPE_SELECTED_MESSAGE_TITLE = "PARENT_TYPE_SELECTED_MESSAGE_TITLE";

   @RBEntry("我收藏的上下文")
   @RBComment("Message for My Context Display")
   public static final String MY_FAVOURITE_CONTEXT_LBL = "MY_FAVOURITE_CONTEXT_LBL";

   @RBEntry("查询模式语法示例")
   @RBComment("Examples for query mode syntax")
   public static final String EXAMPLES_FOR_QUERY_MODE_SYNTAX = "EXAMPLES_FOR_QUERY_MODE_SYNTAX";

   @RBEntry("我收藏的类型")
   @RBComment("Label for My Favorite Type checkbox shown on the search page")
   public static final String  MY_FAVOURITE_TYPE_LBL = "MY_FAVOURITE_TYPE_LBL";

   @RBEntry("条件")
   @RBComment("Criteria label for the criteria fieldset on the advanced search page.")
   public static final String CRITERIA_LABEL = "CRITERIA_LBL";

   @RBEntry("多个文件夹选择")
   @RBComment("Warning Heading message when My Fav checkbox selected and only folders present inside it.")
   public static final String MULTIPLE_FOLDER_SELECTION_HEADING = "MULTIPLE_FOLDER_SELECTION_HEADING";

   @RBEntry("一次只能选择一个文件夹。")
   @RBComment("Warning message when My Fav checkbox selected and only folders present inside it.")
   public static final String MULTIPLE_FOLDER_SELECTION_MSG = "MULTIPLE_FOLDER_SELECTION_MSG";

    @RBEntry("位于")
    @RBComment("Description has format 'Type in Organization', localizing word 'in' ")
    public static final String SAVE_SEARCH_COMBO_BOX_TOOLTIP_DESCRIPTION = "SAVE_SEARCH_COMBO_BOX_TOOLTIP_DESCRIPTION";

    @RBEntry("站点")
    @RBComment("Site")
    public static final String PRIVATE_CONSTANT_541 = "Site";
    
    /*Adding Secondary Attachments column*/
    @RBEntry("附件")
    @RBComment("Label for Secondary Attachments column")
    public static final String SECONDARY_ATTACHMENTS = "SECONDARY_ATTACHMENTS";

    @RBEntry("标记")
    @RBComment("Label for Tag column")
    public static final String TAG_LABEL = "TAG_LABEL";
    
    @RBEntry("筛选器")
    @RBComment("Label for Filter column")
    public static final String FILTER_LABEL = "FILTER_LABEL";
    
    
    
    //customzation for yongji
    @RBEntry("图纸名称")
    public static final String CNAME_LABEL = "CNAME_LABEL";
    @RBEntry("图样代号")
    public static final String DRAWINGNO_LABEL = "DRAWINGNO_LABEL";
    @RBEntry("材料编码")
    public static final String MATERIAL_LABEL = "MATERIAL_LABEL";
    @RBEntry("文件代号")
    public static final String fileID_LABEL = "fileID_LABEL";
}
