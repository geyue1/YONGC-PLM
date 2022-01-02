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
public final class searchClientResource extends WTListResourceBundle {
   @RBEntry("Remember object selection across session.")
   @RBComment("Decscription for preference 'remember object selection'.")
   public static final String PRIVATE_CONSTANT_0 = "STICKY_PICKER_SHORT_DESCRIPTION";

   @RBEntry("Remember object selection across session.")
   @RBComment("Decscription for preference 'remember object selection'.")
   public static final String PRIVATE_CONSTANT_1 = "STICKY_PICKER_DESCRIPTION";

   @RBEntry("Remember object selection")
   @RBComment("Preference Remember object selection.")
   public static final String PRIVATE_CONSTANT_2 = "STICKY_PICKER";

   @RBEntry("This preference is used to apply access control to search results.")
   @RBComment("This preference is used to apply access control on search results.")
   public static final String PRIVATE_CONSTANT_3 = "ACCESS_CONTROL_FLAG_SHORT_DESCRIPTION";

   @RBEntry("This preference is used to apply access control to search results.")
   @RBComment("This preference is used to apply access control on search results.")
   public static final String PRIVATE_CONSTANT_4 = "ACCESS_CONTROL_FLAG_DESCRIPTION";

   @RBEntry("Apply Access Control to Search Results")
   @RBComment("Apply Access Control to Search Results")
   public static final String PRIVATE_CONSTANT_5 = "ACCESS_CONTROL_FLAG";

   @RBEntry("This preference is used to apply access control to search results.")
   @RBComment("This preference is used to apply access control on search results.")
   public static final String PRIVATE_CONSTANT_6 = "ACCESS_CONTROL_FLAG_LONGDESCRIPTION";

   @RBEntry("Use a * as a wildcard to return more results.")
   @RBComment("Wildcard suggestion for keyword field")
   public static final String PRIVATE_CONSTANT_7 = "WILDCARD_SUGGESSTION";

   @RBEntry("Help")
   @RBComment("Search page help icon tooltip")
   public static final String PRIVATE_CONSTANT_8 = "HELP";

   @RBEntry("Search")
   @RBComment("Label for the page title of the search page.")
   public static final String SEARCH_LABEL = "SEARCH_LABEL";

   @RBEntry("Clear")
   @RBComment("Label for the clear button .")
   public static final String PRIVATE_CONSTANT_9 = "CLEAR_BUTTON";

   @RBEntry("Search For")
   @RBComment("Label for the type property on the search page.")
   public static final String PRIVATE_CONSTANT_10 = "SEARCH_FOR";

   @RBEntry("Preferences")
   @RBComment("Label for the \"Preferences\" link on search page.")
   public static final String PRIVATE_CONSTANT_11 = "PREFERENCES_LNK";

   @RBEntry("Open user preferences")
   @RBComment("Tooltip for the \"Preferences\" link on search page.")
   public static final String PRIVATE_CONSTANT_12 = "PREFERENCES_LNK_TT";

   @RBEntry("Find")
   @RBComment("Label for the \"Show Results\" input field")
   public static final String PRIVATE_CONSTANT_13 = "RANGE_OF_SEARCH_PROPTY";

   @RBEntry("With any of these criteria")
   @RBComment("Label for one of the values in the \"Show Results\" pull down list")
   public static final String PRIVATE_CONSTANT_14 = "RANGE_OF_SEARCH_VALUE_OR";

   @RBEntry("With all of these criteria")
   @RBComment("Label for one of the values in the \"Show Results\" pull down list")
   public static final String PRIVATE_CONSTANT_15 = "RANGE_OF_SEARCH_VALUE_AND";

   @RBEntry("Define Scope of Search")
   @RBComment("Label for search scope <HR> separator of the search criteria")
   public static final String PRIVATE_CONSTANT_16 = "SCOPE_SEPARATOR_LABEL";

   @RBEntry("Select Search Criteria")
   @RBComment("Label for search criteria <HR> separator of the search criteria")
   public static final String PRIVATE_CONSTANT_17 = "CRITERIA_SEPARATOR_LABEL";

   @RBEntry("All Contexts")
   @RBComment("The default element in the container drop down list on search page.")
   public static final String PRIVATE_CONSTANT_18 = "ALL_CONTAINERS";

   @RBEntry("All Projects")
   @RBComment("Label for All Projects.")
   public static final String PRIVATE_CONSTANT_19 = "ALL_PROJECTS";

   @RBEntry("All Products")
   @RBComment("Label for All Products.")
   public static final String PRIVATE_CONSTANT_20 = "ALL_PRODUCTS";

   @RBEntry("All Libraries")
   @RBComment("Label for All Libraries.")
   public static final String PRIVATE_CONSTANT_21 = "ALL_LIBRARIES";

   @RBEntry("No relationship")
   @RBComment("The default value for relationship on search in network page.")
   public static final String PRIVATE_CONSTANT_22 = "NO_RELATIONS";

   @RBEntry("Context")
   @RBComment("Label for the property \"Search In:\" on the search page.")
   public static final String PRIVATE_CONSTANT_23 = "CONTAINER_TYPE_PROPTY";

   @RBEntry("I am member of")
   @RBComment("Label for the membership checkbox on the search page")
   public static final String PRIVATE_CONSTANT_24 = "MEMBER_OF_CONTAINER_CHKBX";

   @RBEntry("In my organization")
   @RBComment("Label for the search only in contexts in my organization checkbox on the search page")
   public static final String PRIVATE_CONSTANT_25 = "SEARCH_IN_USER_ORG_CHKBX";

   @RBEntry("Results Per Page")
   @RBComment("Label for the \"Results Per Page:\" input field")
   public static final String PRIVATE_CONSTANT_26 = "ADHOC_PAGE_COUNT_PROPTY";

   @RBEntry("Classification Search")
   @RBComment("Label for the \"Classification Search\" link on search page.")
   public static final String PRIVATE_CONSTANT_27 = "CLASSIFICATION_SEARCH_LNK";

   @RBEntry("Open Classification Search")
   @RBComment("Tooltip for the \"Classification Search\" link on search page.")
   public static final String PRIVATE_CONSTANT_28 = "CLASSIFICATION_SEARCH_LNK_TT";

   @RBEntry("Advanced Search")
   @RBComment("Label for the page title of the advanced search page.")
   public static final String ADVANCED_SEARCH_LABEL = "ADVANCED_SEARCH_LABEL";

   @RBEntry("Customize...")
   @RBComment("Label for the link to the customize saved search table.")
   public static final String PRIVATE_CONSTANT_29 = "CUSTOMIZE_LINK_LABEL";

   @RBEntry("Customize the saved search list")
   @RBComment("Tool tip for the link to the customize saved search table.")
   public static final String PRIVATE_CONSTANT_30 = "CUSTOMIZE_LINK_TT";

   @RBEntry("Groups")
   @RBComment("Label for header of groups table in saved search create clerk.")
   public static final String PRIVATE_CONSTANT_31 = "GROUP_TABLE_HEADER";

   @RBEntry("Add")
   @RBComment("Label for 'Add' action on the Group Access table of saved search create clerk.")
   public static final String PRIVATE_CONSTANT_32 = "ADD_GROUP_LABEL";

   @RBEntry("Add groups for saved search")
   @RBComment("Tool tip for 'Add' action on the Group Access table of saved search create clerk.")
   public static final String PRIVATE_CONSTANT_33 = "ADD_GROUP_TT";

   @RBEntry("Group Access")
   @RBComment("Label for the Group Access Tab in the saved search create clerk.")
   public static final String PRIVATE_CONSTANT_34 = "GROUP_ACCESS_TAB_LABEL";

   @RBEntry("Set Group Access for this search")
   @RBComment("Tool tip for the Group Access Tab in the saved search create clerk.")
   public static final String PRIVATE_CONSTANT_35 = "GROUP_ACCESS_TAB_TT";

   @RBEntry("- Pick an Action -")
   @RBComment("Label for table action dropdown list.")
   public static final String PRIVATE_CONSTANT_36 = "TABLE_ACTION_LIST_PROMPT";

   @RBEntry("Delete this Search")
   @RBComment("Label for delete action in customize saved search table.")
   public static final String PRIVATE_CONSTANT_37 = "DELETE_LABEL";

   @RBEntry("Show")
   @RBComment("Label for show action in customize saved search table.")
   public static final String PRIVATE_CONSTANT_38 = "SHOW_LABEL";

   @RBEntry("Hide")
   @RBComment("Label for hide action in customize saved search table.")
   public static final String PRIVATE_CONSTANT_39 = "HIDE_LABEL";

   @RBEntry("Saved Searches")
   @RBComment("Label for header of customize saved search table.")
   public static final String PRIVATE_CONSTANT_40 = "CUSTOMIZE_SAVED_SEARCH_TABLE_LABEL";

   @RBEntry("Show in Saved Search list")
   @RBComment("Label for display search check box on saved search create clerk.")
   public static final String PRIVATE_CONSTANT_41 = "DISPLAY_SEARCH_LABEL";

   @RBEntry("Export to a file")
   @RBComment("Label for the export button.")
   public static final String PRIVATE_CONSTANT_42 = "EXPORT_TO_FILE_LABEL";

   @RBEntry("Export to a file")
   @RBComment("Tool tip for the export button.")
   public static final String PRIVATE_CONSTANT_43 = "EXPORT_TO_FILE_TT";

   @RBEntry("Save Exported File As")
   @RBComment("Label for the File name in the export wizard")
   public static final String PRIVATE_CONSTANT_44 = "EXPORT_FILE_SAVE";

   @RBEntry("Format:")
   @RBComment("Label for the File format in the export wizard")
   public static final String PRIVATE_CONSTANT_45 = "EXPORT_FILE_FORMAT";

   @RBEntry("csv")
   @RBComment("Label for csv format to export")
   public static final String PRIVATE_CONSTANT_46 = "EXPORT_FORMAT_CSV";

   @RBEntry("xml")
   @RBComment("Label for xml format to export")
   public static final String PRIVATE_CONSTANT_47 = "EXPORT_FORMAT_XML";

   @RBEntry("html report")
   @RBComment("Label for html format to export")
   public static final String PRIVATE_CONSTANT_48 = "EXPORT_FORMAT_HTML";

   @RBEntry("xls report")
   @RBComment("Label for xls format to export")
   public static final String PRIVATE_CONSTANT_49 = "EXPORT_FORMAT_XLS";

   @RBEntry("xls")
   @RBComment("Label for xls format to SUMA export")
   public static final String PRIVATE_CONSTANT_50 = "EXPORT_FORMAT_SUMA_XLS";

   @RBEntry("Downloading the export file...")
   @RBComment("Label for downloading the export file")
   public static final String PRIVATE_CONSTANT_51 = "ACT_LBL_DOWNLOAD";

   @RBEntry("Please close the window after you are done.")
   @RBComment("Close window message")
   public static final String PRIVATE_CONSTANT_52 = "ACT_LBL_CLOSE_MSG";

   @RBEntry("Equals")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_53 = "OP_EQ";

   @RBEntry("Not equals")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_54 = "OP_NE";

   @RBEntry("Less than")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_55 = "OP_LT";

   @RBEntry("Less than equal to")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_56 = "OP_LE";

   @RBEntry("Greater than")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_57 = "OP_GT";

   @RBEntry("Greater than equal to")
   @RBComment("Label for the operator pulldown on the advanced search, search criteria table")
   public static final String PRIVATE_CONSTANT_58 = "OP_GE";

   @RBEntry("Find Saved Searches for Deletion")
   @RBComment("Label for the \"Administrative Delete\" action for saved searches.")
   public static final String PRIVATE_CONSTANT_59 = "ADMIN_DELETE";

   @RBEntry("Choose Object Types")
   @RBComment("Label for the object type picker on the simple search page.")
   public static final String PRIVATE_CONSTANT_60 = "CHANGE_OBJECT_TYPES";

   @RBEntry("Select")
   @RBComment("Button to select a saved search and populate criteria")
   public static final String PRIVATE_CONSTANT_61 = "SELECT_BTN";

   @RBEntry("-- Select --")
   @RBComment("Label for the default element for the \"Criteria\" pulldown on the Advanced Search page")
   public static final String PRIVATE_CONSTANT_62 = "SELECT_CRITERION";

   @RBEntry("Close")
   @RBComment("Label for an action button that closes a window")
   public static final String PRIVATE_CONSTANT_63 = "CLOSE_BUTTON";

   @RBEntry("Find Saved Searches for Deletion")
   @RBComment("Label for the saved search administrative delete clerk")
   public static final String ADMIN_DELETE_PICKER_LABEL = "ADMIN_DELETE_PICKER_LABEL";

   @RBEntry("Creator")
   @RBComment("Label for the saved search admin delete picker for created by")
   public static final String PRIVATE_CONSTANT_64 = "ADMIN_DELETE_CREATED_BY_LABEL";

   @RBEntry("Created On")
   @RBComment("Label for the saved search admin delete picker for created on")
   public static final String PRIVATE_CONSTANT_65 = "ADMIN_DELETE_CREATED_ON_LABEL";

   @RBEntry("Last Modified")
   @RBComment("Label for the saved search admin delete picker for last modified criteria")
   public static final String PRIVATE_CONSTANT_66 = "ADMIN_DELETE_LAST_UPDATED_LABEL";

   @RBEntry("Created By")
   @RBComment("Label for the saved search customize table \"created by\" column")
   public static final String PRIVATE_CONSTANT_67 = "CREATED_BY_LABEL";

   @RBEntry("Owning Client")
   @RBComment("Label for the saved search customize table owningclient (owner) column")
   public static final String PRIVATE_CONSTANT_68 = "QUERY_OWNING_CLIENT";

   @RBEntry("Group Access")
   @RBComment("Label for the saved search customize table group access column")
   public static final String PRIVATE_CONSTANT_69 = "QUERY_GROUP_ACCESS";

   @RBEntry("Scope")
   @RBComment("Label for the saved search table scope column")
   public static final String PRIVATE_CONSTANT_70 = "SAVED_QUERY_SCOPE";

   @RBEntry("Name")
   @RBComment("Label for the saved search customize table name column")
   public static final String PRIVATE_CONSTANT_71 = "NAME_LABEL";

   @RBEntry("Show")
   @RBComment("Label for the saved search customize table show column")
   public static final String PRIVATE_CONSTANT_72 = "QUERY_SHOW";

   @RBEntry("All Applicable Object Types")
   @RBComment("Label for the type property")
   public static final String PRIVATE_CONSTANT_73 = "ALL_ITEMS";

   @RBEntry("Name")
   @RBComment("Label for the name attribute")
   public static final String PRIVATE_CONSTANT_74 = "NAME_ATTRIBUTE";

   @RBEntry("Number")
   @RBComment("Label for the number attribute")
   public static final String PRIVATE_CONSTANT_75 = "NUMBER_ATTRIBUTE";

   @RBEntry("Last Modified")
   @RBComment("Label for the last modified attribute")
   public static final String PRIVATE_CONSTANT_76 = "LAST_UPDATED_ATTRIBUTE";

   @RBEntry("From")
   @RBComment("Label for the From date attribute")
   public static final String PRIVATE_CONSTANT_77 = "FROM_DATE_ATTRIBUTE";

   @RBEntry("To")
   @RBComment("Label for the To date attribute")
   public static final String PRIVATE_CONSTANT_78 = "TO_DATE_ATTRIBUTE";

   @RBEntry("Created On")
   @RBComment("Label for the created attribute")
   public static final String PRIVATE_CONSTANT_79 = "CREATED_ATTRIBUTE";

   @RBEntry("All")
   @RBComment("All")
   public static final String PRIVATE_CONSTANT_80 = "VERSION_ALL";

   @RBEntry("Latest")
   @RBComment("Latest")
   public static final String PRIVATE_CONSTANT_81 = "VERSION_LATEST";

   @RBEntry("Keyword")
   @RBComment("Keyword")
   public static final String PRIVATE_CONSTANT_82 = "KEYWORD_LABEL";

   @RBEntry("Saved Searches")
   @RBComment("Saved Searches")
   public static final String PRIVATE_CONSTANT_83 = "SAVED_SEARCH_LABEL";

   @RBEntry("-- Select --")
   @RBComment("-- Select a Search --")
   public static final String PRIVATE_CONSTANT_84 = "SELECT_SEARCH_LABEL";

   @RBEntry("Revision")
   @RBComment("Version")
   public static final String PRIVATE_CONSTANT_85 = "VERSION_LABEL";

   @RBEntry("Select")
   @RBComment("Select")
   public static final String PRIVATE_CONSTANT_86 = "SELECT_LABEL";

   @RBEntry("Specify")
   @RBComment("Specify")
   public static final String PRIVATE_CONSTANT_87 = "SPECIFY_LABEL";

   @RBEntry("Iteration")
   @RBComment("Iteration")
   public static final String PRIVATE_CONSTANT_88 = "ITERATION_LABEL";

   @RBEntry("Criteria:")
   @RBComment("Criteria")
   public static final String PRIVATE_CONSTANT_89 = "CRITERIA_LABEL";

   /**
    * Picker Related localized values
    **/
   @RBEntry("User Name")
   @RBComment("User Name")
   public static final String PRIVATE_CONSTANT_90 = "USER_NAME_LABEL";

   @RBEntry("Full Name")
   @RBComment("Full Name")
   public static final String PRIVATE_CONSTANT_91 = "FULL_NAME_LABEL";

   @RBEntry("Email")
   @RBComment("Email")
   public static final String PRIVATE_CONSTANT_92 = "EMAIL_LABEL";

   @RBEntry("Organization Name")
   @RBComment("Organization Name")
   public static final String PRIVATE_CONSTANT_93 = "ORG_NAME_LABEL";

   @RBEntry("Organization ID")
   @RBComment("Organization ID")
   public static final String PRIVATE_CONSTANT_94 = "ORG_ID_LABEL";

   @RBEntry("Group Name")
   @RBComment("Group Name")
   public static final String PRIVATE_CONSTANT_95 = "GROUP_NAME_LABEL";

   @RBEntry("Description")
   @RBComment("Description")
   public static final String PRIVATE_CONSTANT_96 = "DESCRIPTION_LABEL";

   @RBEntry("Context Name")
   @RBComment("Context Name")
   public static final String PRIVATE_CONSTANT_97 = "CONTEXT_NAME_LABEL";

   @RBEntry("State")
   @RBComment("State")
   public static final String PRIVATE_CONSTANT_98 = "STATE_LABEL";

   @RBEntry("Context")
   @RBComment("Context")
   public static final String PRIVATE_CONSTANT_99 = "CONTEXT_LABEL";

   @RBEntry("Number")
   @RBComment("Number")
   public static final String PRIVATE_CONSTANT_100 = "NUMBER_LABEL";

   @RBEntry("Participant Name")
   @RBComment("Participant Name")
   public static final String PRIVATE_CONSTANT_101 = "PARTICIPANT_NAME_LABEL";

   @RBEntry("Type the full name of the user , name of group (Blue Group), or name of organization (XYZ Organization). Separate multiple entries with a semicolon (;). An asterisk (*) may be used as a wildcard; for example: J* Doe; Jane Smith; Blue Group; * Organization.")
   @RBComment("Principal Picker Help Text")
   public static final String PRIVATE_CONSTANT_102 = "PRINCIPAL_PICKER_HELP_TEXT";

   @RBEntry("Type the full name of the user. Separate multiple entries with a semicolon (;). An asterisk (*) may be used as a wildcard; for example, *, Mike; Smith, Jane; J*, Doe.")
   @RBComment("User Picker Help Text")
   public static final String PRIVATE_CONSTANT_103 = "USER_PICKER_HELP_TEXT";

   @RBEntry("e.g. Mary Smith; *Smith; Mary*")
   @RBComment("User Picker Short Help Text")
   public static final String PRIVATE_CONSTANT_104 = "USER_PICKER_SHORT_HELP_TEXT";

   @RBEntry("Type the name of the user (User Name). Separate multiple entries with a semicolon.  * wildcard can be included to represent characters.  Example: Admin*; Mike")
   @RBComment("User Picker User Name Help Text")
   public static final String PRIVATE_CONSTANT_105 = "USER_PICKER_NAME_HELP_TEXT";

   @RBEntry("e.g. Admin*; Mike")
   @RBComment("User Picker User Name Short Help Text")
   public static final String PRIVATE_CONSTANT_106 = "USER_PICKER_NAME_SHORT_HELP_TEXT";

   @RBEntry("Type the name of the organization for which you are searching.  Separate multiple entries with a semicolon.  * wildcard can be included to represent characters.  Example: XYZ Organization; * Organization;  ABC Org*")
   @RBComment("Org Picker Help Text")
   public static final String PRIVATE_CONSTANT_107 = "ORG_PICKER_HELP_TEXT";

   @RBEntry("Type the name of the group for which you are searching. Separate multiple entries with a semicolon.  * wildcard can be included to represent characters.  Example: Red Group; * Group; Yellow * ")
   @RBComment("Group Picker Help Text")
   public static final String PRIVATE_CONSTANT_108 = "GROUP_PICKER_HELP_TEXT";

   @RBEntry("Object Name")
   @RBComment("Object Name lable comment")
   public static final String PRIVATE_CONSTANT_109 = "ITEM_NAME_LABEL";

   @RBEntry("Object Version")
   @RBComment("Object Version comment")
   public static final String PRIVATE_CONSTANT_110 = "ITEM_VERSION_LABEL";

   @RBEntry("Object Number")
   @RBComment("Object Number label")
   public static final String PRIVATE_CONSTANT_111 = "ITEM_NUMBER_LABEL";

   @RBEntry("Object Iteration")
   @RBComment("Object Iteration label")
   public static final String PRIVATE_CONSTANT_112 = "ITEM_ITERATION_LABEL";

   @RBEntry("Object CAD File Name")
   @RBComment("Object CAD File Name label")
   public static final String PRIVATE_CONSTANT_113 = "ITEM_CAD_FILE_NAME_LABEL";

   @RBEntry("Object State")
   @RBComment("Object State label")
   public static final String PRIVATE_CONSTANT_114 = "ITEM_STATE_LABEL";

   @RBEntry("Object Context")
   @RBComment("Object Context Path label")
   public static final String PRIVATE_CONSTANT_115 = "ITEM_CONTEXT_PATH_LABEL";

   @RBEntry("Object View")
   @RBComment("Object View label")
   public static final String PRIVATE_CONSTANT_116 = "ITEM_VIEW_LABEL";

   @RBEntry("Object Creator")
   @RBComment("Object Creator label")
   public static final String PRIVATE_CONSTANT_117 = "ITEM_CREATOR_LABEL";

   @RBEntry("All Context Types")
   @RBComment("Text to be displayed in case of context picker for all object types")
   public static final String PRIVATE_CONSTANT_118 = "ALL_CONTEXT_ITEMS";

   /**
    * Picker Titles
    **/
   @RBEntry("Find User")
   @RBComment("User Picker Title")
   public static final String PRIVATE_CONSTANT_119 = "USER_PICKER_TITLE";

   @RBEntry("Find Organization")
   @RBComment("Organization Picker Title")
   public static final String PRIVATE_CONSTANT_120 = "ORG_PICKER_TITLE";

   /**
    * Query Builder table column headings
    **/
   @RBEntry("Search Criteria")
   @RBComment("Search Criteria")
   public static final String QB_TABLE_HEADING_LABEL = "QB_TABLE_HEADING_LABEL";

   @RBEntry("Name")
   @RBComment("Name")
   public static final String QB_NAME_LABEL = "QB_NAME_LABEL";

   @RBEntry("Operator")
   @RBComment("Operator")
   public static final String PRIVATE_CONSTANT_121 = "QB_OPERATOR_LABEL";

   @RBEntry("Value")
   @RBComment("Value")
   public static final String PRIVATE_CONSTANT_122 = "QB_VALUE_LABEL";

   @RBEntry("Search Results")
   @RBComment("Search Results")
   public static final String PRIVATE_CONSTANT_123 = "SEARCH_RESULTS_TABLE";

   @RBEntry("Your session data has expired.Please repeat your action")
   @RBComment("Session Expired")
   public static final String SESSION_EXPIRED_MSG = "SESSION_EXPIRED_MSG";

   @RBEntry("This page has expired which may result in incorrect search results, please perform your search again.")
   @RBComment("Back Button Clicked before Search")
   public static final String PRIVATE_CONSTANT_124 = "CHECK_BACKBUTTON_CLICKED";

   @RBEntry("Add")
   @RBComment("Add text for attribute menu drop down")
   public static final String PRIVATE_CONSTANT_125 = "ADD_CRITERIA";

   @RBEntry("Clear")
   @RBComment("Clear criteria link")
   public static final String PRIVATE_CONSTANT_126 = "CLEAR_CRITERIA";

   @RBEntry("Export List to File")
   public static final String PRIVATE_CONSTANT_127 = "search.exportSearchResults.description";

   @RBEntry("Export List to File")
   public static final String PRIVATE_CONSTANT_128 = "search.exportSearchResults.tooltip";

   @RBEntry("../../com/ptc/core/ui/images/export.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_129 = "search.exportSearchResults.icon";

   @RBEntry("height=300,width=600")
   @RBPseudo(false)
   @RBComment("DO NOT TRANSLATE")
   public static final String PRIVATE_CONSTANT_130 = "search.exportSearchResults.moreurlinfo";

   @RBEntry("Save This Search")
   public static final String PRIVATE_CONSTANT_131 = "search.saveThisSearch.description";

   @RBEntry("Save This Search")
   public static final String PRIVATE_CONSTANT_132 = "search.saveThisSearch.tooltip";

   @RBEntry("Select")
   public static final String PRIVATE_CONSTANT_133 = "search.selectSearch.description";

   @RBEntry("Search")
   public static final String PRIVATE_CONSTANT_134 = "search.search.description";

   @RBEntry("Remove")
   public static final String PRIVATE_CONSTANT_135 = "search.remove.description";

   @RBEntry("Remove")
   public static final String PRIVATE_CONSTANT_136 = "search.remove.tooltip";

   @RBEntry("../../wtcore/images/com/ptc/core/ca/web/misc/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_137 = "search.remove.icon";

   @RBEntry("Search in Network")
   public static final String PRIVATE_CONSTANT_138 = "search.networkSearch.description";

   @RBEntry("Search in Network")
   public static final String PRIVATE_CONSTANT_139 = "search.networkSearch.tooltip";

   @RBEntry("netmarkets/images/search_tbar16.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_140 = "search.networkSearch.icon";

   @RBEntry("Search in node")
   public static final String PRIVATE_CONSTANT_141 = "search.searchInNode.description";

   @RBEntry("search in node")
   public static final String PRIVATE_CONSTANT_142 = "search.searchInNode.tooltip";

   @RBEntry("../../netmarkets/images/search.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_143 = "search.searchInNode.icon";

   @RBEntry("Search for Related Objects")
   public static final String PRIVATE_CONSTANT_144 = "search.relatedItemSearch.description";

   @RBEntry("Search for Related Objects")
   public static final String PRIVATE_CONSTANT_145 = "search.relatedItemSearch.tooltip";

   @RBEntry("netmarkets/images/related_objects_search.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_146 = "search.relatedItemSearch.icon";

   @RBEntry("Customize...")
   public static final String PRIVATE_CONSTANT_147 = "search.relationPicker.description";

   @RBEntry("Customize...")
   public static final String PRIVATE_CONSTANT_148 = "search.relationPicker.tooltip";

   @RBEntry("Choose Relations for search")
   public static final String PRIVATE_CONSTANT_149 = "search.relationPicker.title";

   @RBEntry("height=600,width=600")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_150 = "search.relationPicker.moreurlinfo";

   @RBEntry("Select one or more relations to search")
   public static final String PRIVATE_CONSTANT_151 = "search.relationPickerStep.title";

   @RBEntry("Select the relations to search")
   public static final String PRIVATE_CONSTANT_152 = "search.relationPickerStep.tooltip";

   @RBEntry("Select relations")
   public static final String PRIVATE_CONSTANT_153 = "search.relationPickerStep.description";

   @RBEntry("Delete this Search")
   public static final String PRIVATE_CONSTANT_154 = "SavedQuery.deleteQuery.description";

   @RBEntry("Delete this Search")
   public static final String PRIVATE_CONSTANT_155 = "SavedQuery.deleteQuery.tooltip";

   @RBEntry("netmarkets/images/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_156 = "SavedQuery.deleteQuery.icon";

   @RBEntry("Show")
   public static final String PRIVATE_CONSTANT_157 = "SavedQuery.showQuery.description";

   @RBEntry("Show")
   public static final String PRIVATE_CONSTANT_158 = "SavedQuery.showQuery.tooltip";

   @RBEntry("../../wtcore/images/search_show.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_159 = "SavedQuery.showQuery.icon";

   @RBEntry("Hide")
   public static final String PRIVATE_CONSTANT_160 = "SavedQuery.hideQuery.description";

   @RBEntry("Hide")
   public static final String PRIVATE_CONSTANT_161 = "SavedQuery.hideQuery.tooltip";

   @RBEntry("../../wtcore/images/search_hide.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_162 = "SavedQuery.hideQuery.icon";

   @RBEntry("Delete")
   public static final String PRIVATE_CONSTANT_163 = "SavedQuery.deleteRowQuery.description";

   @RBEntry("Delete")
   public static final String PRIVATE_CONSTANT_164 = "SavedQuery.deleteRowQuery.tooltip";

   @RBEntry("../../wtcore/images/com/ptc/core/ca/web/misc/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_165 = "SavedQuery.deleteRowQuery.icon";

   @RBEntry("Show")
   public static final String PRIVATE_CONSTANT_166 = "SavedQuery.showRowQuery.description";

   @RBEntry("Show")
   public static final String PRIVATE_CONSTANT_167 = "SavedQuery.showRowQuery.tooltip";

   @RBEntry("../../wtcore/images/search_show.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_168 = "SavedQuery.showRowQuery.icon";

   @RBEntry("Hide")
   public static final String PRIVATE_CONSTANT_169 = "SavedQuery.hideRowQuery.description";

   @RBEntry("Hide")
   public static final String PRIVATE_CONSTANT_170 = "SavedQuery.hideRowQuery.tooltip";

   @RBEntry("../../wtcore/images/search_hide.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_171 = "SavedQuery.hideRowQuery.icon";

   @RBEntry("Administrative Delete")
   public static final String PRIVATE_CONSTANT_172 = "search.adminDelete.description";

   @RBEntry("Administrative Delete")
   public static final String PRIVATE_CONSTANT_173 = "search.adminDelete.tooltip";

   @RBEntry("../../wtcore/images/search_deletesaved.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_174 = "search.adminDelete.icon";

   /**
    * Entries for simplesearch typepicker "Customize" link.
    * Copied from \wcEnterprise\EnterpriseUI\src\com\ptc\windchill\enterprise\picker\type\typePickerResource.rbInfo
    **/
   @RBEntry("Add/Update")
   @RBComment("Used as the label for the find action for a SimpleSearch page Type Picker.")
   public static final String PRIVATE_CONSTANT_175 = "search.typePicker.description";

   @RBEntry("Find Type")
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
   @RBEntry("Add/Update...")
   @RBComment("Used as the label for the find action for a SimpleSearch page Type Picker.")
   public static final String PRIVATE_CONSTANT_178 = "search.newtypePicker.description";

   @RBEntry("Find Type")
   @RBComment("Used as the title for the find action pop-up wizard for a Type Picker.")
   public static final String PRIVATE_CONSTANT_179 = "search.newtypePicker.title";

   @RBEntry("height=600,width=375")
   @RBPseudo(false)
   @RBComment("DO NOT TRANSLATE")
   public static final String PRIVATE_CONSTANT_180 = "search.newtypePicker.moreurlinfo";

   @RBEntry("Full Result List")
   @RBComment("Full Result List option in the picker dropdown")
   public static final String FULL_RESULT_LIST = "FULL_RESULT_LIST";

   @RBEntry("Administrative Delete")
   public static final String PRIVATE_CONSTANT_181 = "SavedQuery.finalDelete.description";

   @RBEntry("Administrative Delete")
   public static final String PRIVATE_CONSTANT_182 = "SavedQuery.finalDelete.tooltip";

   @RBEntry("netmarkets/images/delete.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_183 = "SavedQuery.finalDelete.icon";

   @RBEntry("isAdminDelete=true")
   @RBPseudo(false)
   @RBComment("DO NOT TRANSLATE")
   public static final String PRIVATE_CONSTANT_184 = "SavedQuery.finalDelete.moreurlinfo";

   @RBEntry("Export")
   public static final String PRIVATE_CONSTANT_185 = "SavedQuery.savedQueryExport.description";

   @RBEntry("netmarkets/images/export.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_186 = "SavedQuery.savedQueryExport.icon";

   @RBEntry("Export Saved Queries")
   public static final String PRIVATE_CONSTANT_187 = "SavedQuery.savedQueryExport.title";

   @RBEntry("Export Saved Searches")
   public static final String PRIVATE_CONSTANT_188 = "SavedQuery.savedQueryExport.tooltip";

   @RBEntry("height=300,width=500")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_189 = "SavedQuery.savedQueryExport.moreurlinfo";

   @RBEntry("Import")
   public static final String PRIVATE_CONSTANT_190 = "SavedQuery.savedQueryImport.description";

   @RBEntry("netmarkets/images/import.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_191 = "SavedQuery.savedQueryImport.icon";

   @RBEntry("Import Saved Queries")
   public static final String PRIVATE_CONSTANT_192 = "SavedQuery.savedQueryImport.title";

   @RBEntry("Import Saved Searches")
   public static final String PRIVATE_CONSTANT_193 = "SavedQuery.savedQueryImport.tooltip";

   @RBEntry("height=300,width=500")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_194 = "SavedQuery.savedQueryImport.moreurlinfo";

   @RBEntry("Import")
   public static final String PRIVATE_CONSTANT_195 = "search.import.description";

   @RBEntry("Import Saved Searches")
   public static final String PRIVATE_CONSTANT_196 = "search.import.title";

   @RBEntry("Import")
   public static final String PRIVATE_CONSTANT_197 = "search.import_step.description";

   @RBEntry("Import Saved Searches")
   public static final String PRIVATE_CONSTANT_198 = "search.import_step.title";

   @RBEntry("Go")
   public static final String PRIVATE_CONSTANT_199 = "search.typeChange.description";

   @RBEntry("Clear All")
   public static final String PRIVATE_CONSTANT_200 = "search.clearSearch.description";

   @RBEntry("Find...")
   public static final String PRIVATE_CONSTANT_201 = "search.callSearchPicker.description";

   @RBEntry("Find...")
   public static final String PRIVATE_CONSTANT_202 = "search.callSearchPicker.tooltip";

   @RBEntry("height=768,width=550")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_203 = "search.callSearchPicker.moreurlinfo";

   @RBEntry("Add Groups")
   public static final String PRIVATE_CONSTANT_204 = "search.callSavedSearchGroupPicker.description";

   @RBEntry("Add Groups")
   public static final String PRIVATE_CONSTANT_205 = "search.callSavedSearchGroupPicker.title";

   @RBEntry("Add Groups")
   public static final String PRIVATE_CONSTANT_206 = "search.callSavedSearchGroupPicker.tooltip";

   @RBEntry("height=550,width=400")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_207 = "search.callSavedSearchGroupPicker.moreurlinfo";

   @RBEntry("netmarkets/images/add16x16.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_208 = "search.callSavedSearchGroupPicker.icon";

   @RBEntry("Remove Groups")
   public static final String PRIVATE_CONSTANT_209 = "search.removeSavedSearchGroups.description";

   @RBEntry("Remove Groups")
   public static final String PRIVATE_CONSTANT_210 = "search.removeSavedSearchGroups.title";

   @RBEntry("Remove Groups")
   public static final String PRIVATE_CONSTANT_211 = "search.removeSavedSearchGroups.tooltip";

   @RBEntry("netmarkets/images/remove16x16.gif")
   @RBPseudo(false)
   public static final String PRIVATE_CONSTANT_212 = "search.removeSavedSearchGroups.icon";

   @RBEntry("Advanced Options")
   public static final String PRIVATE_CONSTANT_213 = "search.saveThisSearchNew.description";

   @RBEntry("Advanced Options")
   public static final String PRIVATE_CONSTANT_214 = "search.saveThisSearchNew.tooltip";

   @RBEntry("Advanced Options")
   public static final String PRIVATE_CONSTANT_215 = "search.saveThisSearchNew.title";

   @RBEntry("Set Saved Search Name")
   public static final String PRIVATE_CONSTANT_216 = "search.saveThisSearch_saveName.description";

   @RBEntry("Set Saved Search Name")
   public static final String PRIVATE_CONSTANT_217 = "search.saveThisSearch_saveName.tooltip";

   @RBEntry("Define Details")
   public static final String PRIVATE_CONSTANT_218 = "search.saveThisSearch_saveName.title";

   @RBEntry("Set Required Attributes ")
   public static final String PRIVATE_CONSTANT_219 = "search.saveThisSearch_saveRequired.description";

   @RBEntry("Set Required Attributes ")
   public static final String PRIVATE_CONSTANT_220 = "search.saveThisSearch_saveRequired.tooltip";

   @RBEntry("Set Required Attributes ")
   public static final String PRIVATE_CONSTANT_221 = "search.saveThisSearch_saveRequired.title";

   @RBEntry("Set Table View ")
   public static final String PRIVATE_CONSTANT_222 = "search.saveThisSearch_saveTableView.description";

   @RBEntry("Set Table View ")
   public static final String PRIVATE_CONSTANT_223 = "search.saveThisSearch_saveTableView.tooltip";

   @RBEntry("Set Table View ")
   public static final String PRIVATE_CONSTANT_224 = "search.saveThisSearch_saveTableView.title";

   @RBEntry("Set Group Access ")
   public static final String PRIVATE_CONSTANT_225 = "search.saveThisSearch_saveAccess.description";

   @RBEntry("Set Group Access ")
   public static final String PRIVATE_CONSTANT_226 = "search.saveThisSearch_saveAccess.tooltip";

   @RBEntry("Set Group Access ")
   public static final String PRIVATE_CONSTANT_227 = "search.saveThisSearch_saveAccess.title";

   /**
    * Saved Search related fields
    **/
   @RBEntry("Name")
   @RBComment("Search Name")
   public static final String PRIVATE_CONSTANT_228 = "SAVED_SEARCH_NAME";

   @RBEntry("Show in Saved Search list")
   @RBComment("Show in Saved Search list")
   public static final String PRIVATE_CONSTANT_229 = "SAVED_SEARCH_SHOW";

   @RBEntry("Please enter Name")
   @RBComment("Please enter Name")
   public static final String PRIVATE_CONSTANT_230 = "PLEASE_ENTER_NAME";

   @RBEntry("Create Table View")
   @RBComment("Create Table View")
   public static final String PRIVATE_CONSTANT_231 = "SAVED_SEARCH_CREATE_VIEW";

   @RBEntry("Table View")
   @RBComment("Use Existing Table View")
   public static final String PRIVATE_CONSTANT_232 = "SAVED_SEARCH_EXISTING_VIEW";

   @RBEntry("- Pick a View -")
   @RBComment("- Pick a View -")
   public static final String PRIVATE_CONSTANT_233 = "PICK_VIEW";

   @RBEntry("Available Attributes:")
   @RBComment("Available Attributes:")
   public static final String PRIVATE_CONSTANT_234 = "AVAILABLE_ATTRIBUTES";

   @RBEntry("Required Attributes:")
   @RBComment("Required Attributes:")
   public static final String PRIVATE_CONSTANT_235 = "REQUIRED_ATTRIBUTES";

   @RBEntry("Add")
   @RBComment("Add")
   public static final String PRIVATE_CONSTANT_236 = "ADD_ATTRIBUTE";

   @RBEntry("Remove")
   @RBComment("Remove")
   public static final String PRIVATE_CONSTANT_237 = "REMOVE_ATTRIBUTE";

   @RBEntry("Default CAD Document View")
   @RBComment("Default CAD Document View")
   public static final String PRIVATE_CONSTANT_238 = "CADDOC_TABLEVIEW_NAME";

   @RBEntry("Default EPM Document Search Table View")
   @RBComment("Default EPM Document Search Table View")
   public static final String PRIVATE_CONSTANT_239 = "CADDOC_TABLEVIEW_DESC";

   @RBEntry("EPM Document")
   @RBComment("EPM Document")
   public static final String PRIVATE_CONSTANT_240 = "CADDOC_TABLEVIEW_LABEL";

   @RBEntry("Default Part View")
   @RBComment("Default Part View")
   public static final String PRIVATE_CONSTANT_241 = "WTPART_TABLEVIEW_NAME";

   @RBEntry("Default Part Search Table View")
   @RBComment("Default Part Search Table View")
   public static final String PRIVATE_CONSTANT_242 = "WTPART_TABLEVIEW_DESC";

   @RBEntry("Part ")
   @RBComment("Part ")
   public static final String PRIVATE_CONSTANT_243 = "WTPART_TABLEVIEW_LABEL";

   @RBEntry("Default Document View")
   @RBComment("Default Document View")
   public static final String PRIVATE_CONSTANT_244 = "WTDOCUMENT_TABLEVIEW_NAME";

   @RBEntry("Default Document Search Table View")
   @RBComment("Default Document Search Table View")
   public static final String PRIVATE_CONSTANT_245 = "WTDOCUMENT_TABLEVIEW_DESC";

   @RBEntry("Document")
   @RBComment("Document")
   public static final String PRIVATE_CONSTANT_246 = "WTDOCUMENT_TABLEVIEW_LABEL";

   @RBEntry("Default Work Set View")
   @RBComment("Default Work Set View")
   public static final String PRIVATE_CONSTANT_247 = "WTWORKSET_TABLEVIEW_NAME";

   @RBEntry("Default Work Set Search Table View")
   @RBComment("Default Work Set Search Table View")
   public static final String PRIVATE_CONSTANT_248 = "WTWORKSET_TABLEVIEW_DESC";

   @RBEntry("Work Set")
   @RBComment("Work Set")
   public static final String PRIVATE_CONSTANT_249 = "WTWORKSET_TABLEVIEW_LABEL";

   @RBEntry("Default Archive View")
   @RBComment("Default Archive View")
   public static final String PRIVATE_CONSTANT_250 = "ARCHIVE_TABLEVIEW_NAME";

   @RBEntry("Default Archive Search Table View")
   @RBComment("Default Archive Search Table View")
   public static final String PRIVATE_CONSTANT_251 = "ARCHIVE_TABLEVIEW_DESC";

   @RBEntry("Archive")
   @RBComment("Archive")
   public static final String PRIVATE_CONSTANT_252 = "ARCHIVE_TABLEVIEW_LABEL";

   @RBEntry("Default Change Notice View")
   @RBComment("Default Change Notice View")
   public static final String PRIVATE_CONSTANT_253 = "CHANGENOTICE_TABLEVIEW_NAME";

   @RBEntry("Default Change Notice Search Table View")
   @RBComment("Default Change Notice Search Table View")
   public static final String PRIVATE_CONSTANT_254 = "CHANGENOTICE_TABLEVIEW_DESC";

   @RBEntry("Change Notice")
   @RBComment("Change Notice")
   public static final String PRIVATE_CONSTANT_255 = "CHANGENOTICE_TABLEVIEW_LABEL";

   @RBEntry("Default Change Request View")
   @RBComment("Default Change Request View")
   public static final String PRIVATE_CONSTANT_256 = "CHANGEREQUEST_TABLEVIEW_NAME";

   @RBEntry("Default Change Request Search Table View")
   @RBComment("Default Change Request Search Table View")
   public static final String PRIVATE_CONSTANT_257 = "CHANGEREQUEST_TABLEVIEW_DESC";

   @RBEntry("Change Request")
   @RBComment("Change Request")
   public static final String PRIVATE_CONSTANT_258 = "CHANGEREQUEST_TABLEVIEW_LABEL";

   @RBEntry("Default Change Directive View")
   @RBComment("Default Change Directive View")
   public static final String PRIVATE_CONSTANT_259 = "CHANGEDIRECTIVE_TABLEVIEW_NAME";

   @RBEntry("Default Change Directive Search Table View")
   @RBComment("Default Change Directive Search Table View")
   public static final String PRIVATE_CONSTANT_260 = "CHANGEDIRECTIVE_TABLEVIEW_DESC";

   @RBEntry("Change Directive")
   @RBComment("Change Directive")
   public static final String PRIVATE_CONSTANT_261 = "CHANGEDIRECTIVE_TABLEVIEW_LABEL";

   @RBEntry("Default Problem Report View")
   @RBComment("Default Problem Report View")
   public static final String PRIVATE_CONSTANT_262 = "WTCHANGEISSUE_TABLEVIEW_NAME";

   @RBEntry("Default Problem Report Search Table View")
   @RBComment("Default Problem Report Search Table View")
   public static final String PRIVATE_CONSTANT_263 = "WTCHANGEISSUE_TABLEVIEW_DESC";

   @RBEntry("Problem Report")
   @RBComment("Problem Report")
   public static final String PRIVATE_CONSTANT_264 = "WTCHANGEISSUE_TABLEVIEW_LABEL";

   @RBEntry("Default Deliverable View")
   @RBComment("Default Deliverable View")
   public static final String PRIVATE_CONSTANT_265 = "DELIVERABLE_TABLEVIEW_NAME";

   @RBEntry("Default Deliverable Search Table View")
   @RBComment("Default Deliverable Search Table View")
   public static final String PRIVATE_CONSTANT_266 = "DELIVERABLE_TABLEVIEW_DESC";

   @RBEntry("Deliverable")
   @RBComment("Deliverable")
   public static final String PRIVATE_CONSTANT_267 = "DELIVERABLE_TABLEVIEW_LABEL";

   @RBEntry("Default Manufacturer View")
   @RBComment("Default Manufacturer View")
   public static final String PRIVATE_CONSTANT_268 = "MANUFACTURER_TABLEVIEW_NAME";

   @RBEntry("Default Manufacturer Search Table View")
   @RBComment("Default Manufacturer Search Table View")
   public static final String PRIVATE_CONSTANT_269 = "MANUFACTURER_TABLEVIEW_DESC";

   @RBEntry("Manufacturer")
   @RBComment("Manufacturer")
   public static final String PRIVATE_CONSTANT_270 = "MANUFACTURER_TABLEVIEW_LABEL";

   @RBEntry("Default Organization View")
   @RBComment("Default Organization View")
   public static final String PRIVATE_CONSTANT_271 = "ORGNIZATION_TABLEVIEW_NAME";

   @RBEntry("Default Organization Search Table View")
   @RBComment("Default Organization Search Table View")
   public static final String PRIVATE_CONSTANT_272 = "ORGNIZATION_TABLEVIEW_DESC";

   @RBEntry("Organization")
   @RBComment("Organization")
   public static final String PRIVATE_CONSTANT_273 = "ORGNIZATION_TABLEVIEW_LABEL";

   @RBEntry("Default Project View")
   @RBComment("Default Project View")
   public static final String PRIVATE_CONSTANT_274 = "PROJECT2_TABLEVIEW_NAME";

   @RBEntry("Default Project Search Table View")
   @RBComment("Default Project Search Table View")
   public static final String PRIVATE_CONSTANT_275 = "PROJECT2_TABLEVIEW_DESC";

   @RBEntry("Project")
   @RBComment("Project")
   public static final String PRIVATE_CONSTANT_276 = "PROJECT2_TABLEVIEW_LABEL";

   @RBEntry("Default Part Configuration View")
   @RBComment("Default Part Configuration View")
   public static final String PRIVATE_CONSTANT_277 = "PARTCONFIGURATION_TABLEVIEW_NAME";

   @RBEntry("Default Part Configuration Search Table View")
   @RBComment("Default Part Configuration Search Table View")
   public static final String PRIVATE_CONSTANT_278 = "PARTCONFIGURATION_TABLEVIEW_DESC";

   @RBEntry("Part Configuration")
   @RBComment("Part Configuration")
   public static final String PRIVATE_CONSTANT_279 = "PARTCONFIGURATION_TABLEVIEW_LABEL";

   @RBEntry("Default Part Instance View")
   @RBComment("Default Part Instance View")
   public static final String PRIVATE_CONSTANT_280 = "PARTINSTANCE_TABLEVIEW_NAME";

   @RBEntry("Default Part Instance Search Table View")
   @RBComment("Default Part Instance Search Table View")
   public static final String PRIVATE_CONSTANT_281 = "PARTINSTANCE_TABLEVIEW_DESC";

   @RBEntry("Part Instance")
   @RBComment("Part Instance")
   public static final String PRIVATE_CONSTANT_282 = "PARTINSTANCE_TABLEVIEW_LABEL";

   @RBEntry("Default Vendor View")
   @RBComment("Default Vendor View")
   public static final String PRIVATE_CONSTANT_283 = "VENDOR_TABLEVIEW_NAME";

   @RBEntry("Default Vendor Search Table View")
   @RBComment("Default Vendor Search Table View")
   public static final String PRIVATE_CONSTANT_284 = "VENDOR_TABLEVIEW_DESC";

   @RBEntry("Vendor")
   @RBComment("Vendor")
   public static final String PRIVATE_CONSTANT_285 = "VENDOR_TABLEVIEW_LABEL";

   @RBEntry("Default Managed Collection View")
   @RBComment("Default Managed Collection View")
   public static final String PRIVATE_CONSTANT_286 = "MANAGEDCOLLECTION_TABLEVIEW_NAME";

   @RBEntry("Default Managed Collection Search Table View")
   @RBComment("Default Managed Collection Search Table View")
   public static final String PRIVATE_CONSTANT_287 = "MANAGEDCOLLECTION_TABLEVIEW_DESC";

   @RBEntry("Managed Collection")
   @RBComment("Managed Collection")
   public static final String PRIVATE_CONSTANT_288 = "MANAGEDCOLLECTION_TABLEVIEW_LABEL";

   @RBEntry("Modified By")
   @RBComment("Modified By")
   public static final String MC_MODIFIED_BY = "MC_MODIFIED_BY";

   @RBEntry("Default Package View")
   @RBComment("Default Package View")
   public static final String PRIVATE_CONSTANT_289 = "WORKPACKAGE_TABLEVIEW_NAME";

   @RBEntry("Default Package Search Table View")
   @RBComment("Default Package Search Table View")
   public static final String PRIVATE_CONSTANT_290 = "WORKPACKAGE_TABLEVIEW_DESC";

   @RBEntry("Package")
   @RBComment("Package")
   public static final String PRIVATE_CONSTANT_291 = "WORKPACKAGE_TABLEVIEW_LABEL";

   /** The Constant LOCKED_ATTR_TABLE_LABEL. */
   @RBEntry("Lock Status")
   public static final String LOCKED_ATTR_TABLE_LABEL = "LOCKED_ATTR_TABLE_LABEL";

   @RBEntry("Default Part Request View")
   @RBComment("Default Part Request View")
   public static final String PRIVATE_CONSTANT_292 = "WTPARTREQUEST_TABLEVIEW_NAME";

   @RBEntry("Default Part Request Search Table View")
   @RBComment("Default Part Request Search Table View")
   public static final String PRIVATE_CONSTANT_293 = "WTPARTREQUEST_TABLEVIEW_DESC";

   @RBEntry("Part Request")
   @RBComment("Part Request")
   public static final String PRIVATE_CONSTANT_294 = "WTPARTREQUEST_TABLEVIEW_LABEL";

   @RBEntry("Default Variance View")
   @RBComment("Default Variance View")
   public static final String PRIVATE_CONSTANT_295 = "WTVARIANCE_TABLEVIEW_NAME";

   @RBEntry("Default Variance Search Table View")
   @RBComment("Default Variance Search Table View")
   public static final String PRIVATE_CONSTANT_296 = "WTVARIANCE_TABLEVIEW_DESC";

   @RBEntry("Variance")
   @RBComment("Variance")
   public static final String PRIVATE_CONSTANT_297 = "WTVARIANCE_TABLEVIEW_LABEL";

   @RBEntry("Default Variant Spec View")
   @RBComment("Default Variant Spec View")
   public static final String PRIVATE_CONSTANT_298 = "VARIANTSPEC_TABLEVIEW_NAME";

   @RBEntry("Default Variant Spec Search Table View")
   @RBComment("Default Variant Spec Search Table View")
   public static final String PRIVATE_CONSTANT_299 = "VARIANTSPEC_TABLEVIEW_DESC";

   @RBEntry("Variant Spec")
   @RBComment("Variant Spec")
   public static final String PRIVATE_CONSTANT_300 = "VARIANTSPEC_TABLEVIEW_LABEL";

   @RBEntry("Default User View")
   @RBComment("Default User View")
   public static final String PRIVATE_CONSTANT_301 = "WTUSER_TABLEVIEW_NAME";

   @RBEntry("Default User Search Table View")
   @RBComment("Default User Search Table View")
   public static final String PRIVATE_CONSTANT_302 = "WTUSER_TABLEVIEW_DESC";

   @RBEntry("User")
   @RBComment("User")
   public static final String PRIVATE_CONSTANT_303 = "WTUSER_TABLEVIEW_LABEL";

   @RBEntry("Default Action Item View")
   @RBComment("Default Action Item View")
   public static final String PRIVATE_CONSTANT_304 = "DISCRETEACTIONITEM_TABLEVIEW_NAME";

   @RBEntry("Default Action Item Search Table View")
   @RBComment("Default Action Item Search Table View")
   public static final String PRIVATE_CONSTANT_305 = "DISCRETEACTIONITEM_TABLEVIEW_DESC";

   @RBEntry("Action Item")
   @RBComment("Action Item")
   public static final String PRIVATE_CONSTANT_306 = "DISCRETEACTIONITEM_TABLEVIEW_LABEL";

   @RBEntry("Default EPM Document Master View")
   @RBComment("Default EPM Document Master View")
   public static final String PRIVATE_CONSTANT_307 = "EPMDOCUMENTMASTER_TABLEVIEW_NAME";

   @RBEntry("Default EPM Document Master Search Table View")
   @RBComment("Default EPM Document Master Search Table View")
   public static final String PRIVATE_CONSTANT_308 = "EPMDOCUMENTMASTER_TABLEVIEW_DESC";

   @RBEntry("EPM Document Master")
   @RBComment("EPM Document Master")
   public static final String PRIVATE_CONSTANT_309 = "EPMDOCUMENTMASTER_TABLEVIEW_LABEL";

   @RBEntry("Default Document Master View")
   @RBComment("Default Document Master View")
   public static final String PRIVATE_CONSTANT_310 = "WTDOCUMENTMASTER_TABLEVIEW_NAME";

   @RBEntry("Default Document Master Search Table View")
   @RBComment("Default Document Master Search Table View")
   public static final String PRIVATE_CONSTANT_311 = "WTDOCUMENTMASTER_TABLEVIEW_DESC";

   @RBEntry("Document Master")
   @RBComment("Document Master")
   public static final String PRIVATE_CONSTANT_312 = "WTDOCUMENTMASTER_TABLEVIEW_LABEL";

   @RBEntry("Default Part Master View")
   @RBComment("Default Part Master View")
   public static final String PRIVATE_CONSTANT_313 = "WTPARTMASTER_TABLEVIEW_NAME";

   @RBEntry("Default Part Master Search Table View")
   @RBComment("Default Part Master Search Table View")
   public static final String PRIVATE_CONSTANT_314 = "WTPARTMASTER_TABLEVIEW_DESC";

   @RBEntry("Part Master")
   @RBComment("Part Master")
   public static final String PRIVATE_CONSTANT_315 = "WTPARTMASTER_TABLEVIEW_LABEL";

   @RBEntry("Default Format View")
   @RBComment("Default Format View")
   public static final String PRIVATE_CONSTANT_316 = "FORMAT_TABLEVIEW_NAME";

   @RBEntry("Default Format Search Table View")
   @RBComment("Default Format Search Table View")
   public static final String PRIVATE_CONSTANT_317 = "FORMAT_TABLEVIEW_DESC";

   @RBEntry("Format")
   @RBComment("Format")
   public static final String PRIVATE_CONSTANT_318 = "FORMAT_TABLEVIEW_LABEL";

   @RBEntry("Default Group View")
   @RBComment("Default Group View")
   public static final String PRIVATE_CONSTANT_319 = "GROUP_TABLEVIEW_NAME";

   @RBEntry("Default Group Search Table View")
   @RBComment("Default Group Search Table View")
   public static final String PRIVATE_CONSTANT_320 = "GROUP_TABLEVIEW_DESC";

   @RBEntry("Group")
   @RBComment("Group")
   public static final String PRIVATE_CONSTANT_321 = "GROUP_TABLEVIEW_LABEL";

   @RBEntry("Default Organization View")
   @RBComment("Default Organization View")
   public static final String PRIVATE_CONSTANT_322 = "ORG_TABLEVIEW_NAME";

   @RBEntry("Default Organization Search Table View")
   @RBComment("Default Organization Search Table View")
   public static final String PRIVATE_CONSTANT_323 = "ORG_TABLEVIEW_DESC";

   @RBEntry("Organization")
   @RBComment("Organization")
   public static final String PRIVATE_CONSTANT_324 = "ORG_TABLEVIEW_LABEL";

   @RBEntry("Default Reference Attachment View")
   @RBComment("Default Reference Attachment View")
   public static final String PRIVATE_CONSTANT_325 = "IMPORTEDBOOKMARK_TABLEVIEW_NAME";

   @RBEntry("Default Reference Attachment Search Table View")
   @RBComment("Default Reference Attachment Search Table View")
   public static final String PRIVATE_CONSTANT_326 = "IMPORTEDBOOKMARK_TABLEVIEW_DESC";

   @RBEntry("Reference Attachment")
   @RBComment("Reference Attachment")
   public static final String PRIVATE_CONSTANT_327 = "IMPORTEDBOOKMARK_TABLEVIEW_LABEL";

   @RBEntry("Default Sub Project/Program View")
   @RBComment("Default Sub Project/Program View")
   public static final String PRIVATE_CONSTANT_328 = "PROJECTPROXY_TABLEVIEW_NAME";

   @RBEntry("Default Sub Project/Program Search Table View")
   @RBComment("Default Sub Project/Program Search Table View")
   public static final String PRIVATE_CONSTANT_329 = "PROJECTPROXY_TABLEVIEW_DESC";

   @RBEntry("Sub Project/Program")
   @RBComment("Sub Project/Program")
   public static final String PRIVATE_CONSTANT_330 = "PROJECTPROXY_TABLEVIEW_LABEL";

   @RBEntry("Default Cabinet View")
   @RBComment("Default Cabinet View")
   public static final String PRIVATE_CONSTANT_331 = "CABINET_TABLEVIEW_NAME";

   @RBEntry("Default Cabinet Search Table View")
   @RBComment("Default Cabinet Search Table View")
   public static final String PRIVATE_CONSTANT_332 = "CABINET_TABLEVIEW_DESC";

   @RBEntry("Cabinet")
   @RBComment("Cabinet")
   public static final String PRIVATE_CONSTANT_333 = "CABINET_TABLEVIEW_LABEL";

   @RBEntry("Default Managed Baseline View")
   @RBComment("Default Managed Baseline View")
   public static final String PRIVATE_CONSTANT_334 = "MANAGEDBASELINE_TABLEVIEW_NAME";

   @RBEntry("Default Managed Baseline Search Table View")
   @RBComment("Default Managed Baseline Search Table View")
   public static final String PRIVATE_CONSTANT_335 = "MANAGEDBASELINE_TABLEVIEW_DESC";

   @RBEntry("Managed Baseline")
   @RBComment("Managed Baseline")
   public static final String PRIVATE_CONSTANT_336 = "MANAGEDBASELINE_TABLEVIEW_LABEL";

   @RBEntry("Default Process Plan View")
   @RBComment("Default Process Plan View")
   public static final String PRIVATE_CONSTANT_337 = "MPMPROCESSPLAN_TABLEVIEW_NAME";

   @RBEntry("Default Process Plan Search Table View")
   @RBComment("Default Process Plan Search Table View")
   public static final String PRIVATE_CONSTANT_338 = "MPMPROCESSPLAN_TABLEVIEW_DESC";

   @RBEntry("Process Plan")
   @RBComment("Process Plan")
   public static final String PRIVATE_CONSTANT_339 = "MPMPROCESSPLAN_TABLEVIEW_LABEL";

   @RBEntry("Default Sequence View")
   @RBComment("Default Sequence View")
   public static final String PRIVATE_CONSTANT_340 = "MPMSEQUENCE_TABLEVIEW_NAME";

   @RBEntry("Default Sequence Search Table View")
   @RBComment("Default Sequence Search Table View")
   public static final String PRIVATE_CONSTANT_341 = "MPMSEQUENCE_TABLEVIEW_DESC";

   @RBEntry("Sequence")
   @RBComment("Sequence")
   public static final String PRIVATE_CONSTANT_342 = "MPMSEQUENCE_TABLEVIEW_LABEL";

   @RBEntry("Default Operation View")
   @RBComment("Default Operation View")
   public static final String PRIVATE_CONSTANT_343 = "MPMOPERATION_TABLEVIEW_NAME";

   @RBEntry("Default Operation Search Table View")
   @RBComment("Default Operation Search Table View")
   public static final String PRIVATE_CONSTANT_344 = "MPMOPERATION_TABLEVIEW_DESC";

   @RBEntry("Operation")
   @RBComment("Operation")
   public static final String PRIVATE_CONSTANT_345 = "MPMOPERATION_TABLEVIEW_LABEL";

   @RBEntry("Default Manufacturing Standard Group View")
   @RBComment("Default Manufacturing Standard Group View")
   public static final String PRIVATE_CONSTANT_346 = "MPMMFGSTANDARDGROUP_TABLEVIEW_NAME";

   @RBEntry("Default Manufacturing Standard Group Search Table View")
   @RBComment("Default Manufacturing Standard Group Search Table View")
   public static final String PRIVATE_CONSTANT_347 = "MPMMFGSTANDARDGROUP_TABLEVIEW_DESC";

   @RBEntry("Manufacturing Standard Group")
   @RBComment("Manufacturing Standard Group")
   public static final String PRIVATE_CONSTANT_348 = "MPMMFGSTANDARDGROUP_TABLEVIEW_LABEL";

   @RBEntry("Default Manufacturing Process View")
   @RBComment("Default Manufacturing Process View")
   public static final String PRIVATE_CONSTANT_349 = "MPMMFGPROCESS_TABLEVIEW_NAME";

   @RBEntry("Default Manufacturing Process Search Table View")
   @RBComment("Default Manufacturing Process Search Table View")
   public static final String PRIVATE_CONSTANT_350 = "MPMMFGPROCESS_TABLEVIEW_DESC";

   @RBEntry("Manufacturing Process")
   @RBComment("Manufacturing Process")
   public static final String PRIVATE_CONSTANT_351 = "MPMMFGPROCESS_TABLEVIEW_LABEL";

   @RBEntry("Default Resource View")
   @RBComment("Default Resource View")
   public static final String PRIVATE_CONSTANT_352 = "MPMRESOURCE_TABLEVIEW_NAME";

   @RBEntry("Default Resource Search Table View")
   @RBComment("Default Resource Search Table View")
   public static final String PRIVATE_CONSTANT_353 = "MPMRESOURCE_TABLEVIEW_DESC";

   @RBEntry("Resource")
   @RBComment("Resource")
   public static final String PRIVATE_CONSTANT_354 = "MPMRESOURCE_TABLEVIEW_LABEL";

   @RBEntry("Default Plant View")
   @RBComment("Default Plant View")
   public static final String PRIVATE_CONSTANT_355 = "MPMPLANT_TABLEVIEW_NAME";

   @RBEntry("Default Plant Search Table View")
   @RBComment("Default Plant Search Table View")
   public static final String PRIVATE_CONSTANT_356 = "MPMPLANT_TABLEVIEW_DESC";

   @RBEntry("Plant")
   @RBComment("Plant")
   public static final String PRIVATE_CONSTANT_357 = "MPMPLANT_TABLEVIEW_LABEL";

   @RBEntry("Default Resource Group View")
   @RBComment("Default Resource Group View")
   public static final String PRIVATE_CONSTANT_358 = "MPMRESOURCEGROUP_TABLEVIEW_NAME";

   @RBEntry("Default Resource Group Search Table View")
   @RBComment("Default Resource Group Search Table View")
   public static final String PRIVATE_CONSTANT_359 = "MPMRESOURCEGROUP_TABLEVIEW_DESC";

   @RBEntry("Resource Group")
   @RBComment("Resource Group")
   public static final String PRIVATE_CONSTANT_360 = "MPMRESOURCEGROUP_TABLEVIEW_LABEL";

   @RBEntry("Default Work Item View")
   @RBComment("Default Work Item View")
   public static final String PRIVATE_CONSTANT_361 = "WORKITEM_TABLEVIEW_NAME";

   @RBEntry("Default Work Item Search Table View")
   @RBComment("Default Work Item Search Table View")
   public static final String PRIVATE_CONSTANT_362 = "WORKITEM_TABLEVIEW_DESC";

   @RBEntry("Work Item")
   @RBComment("Work Item")
   public static final String PRIVATE_CONSTANT_363 = "WORKITEM_TABLEVIEW_LABEL";

   @RBEntry("Default Report View")
   @RBComment("Default Report View")
   public static final String PRIVATE_CONSTANT_364 = "REPORT_TABLEVIEW_NAME";

   @RBEntry("Default Report Search Table View")
   @RBComment("Default Report Search Table View")
   public static final String PRIVATE_CONSTANT_365 = "REPORT_TABLEVIEW_DESC";

   @RBEntry("Report")
   @RBComment("Report")
   public static final String PRIVATE_CONSTANT_366 = "REPORT_TABLEVIEW_LABEL";

   @RBEntry("Default Saved Search View")
   @RBComment("Default Saved Search View")
   public static final String PRIVATE_CONSTANT_367 = "SAVEDQUERY_TABLEVIEW_NAME";

   @RBEntry("Default Saved Search Search Table View")
   @RBComment("Default Saved Search Table View")
   public static final String PRIVATE_CONSTANT_368 = "SAVEDQUERY_TABLEVIEW_DESC";

   @RBEntry("Default Saved Search Picker View")
   @RBComment("Default Saved Search Picker View")
   public static final String PRIVATE_CONSTANT_369 = "SAVEDQUERY_PICKER_TABLEVIEW_NAME";

   @RBEntry("Default Saved Search Picker Table View")
   @RBComment("Default Saved Search Picker Table View")
   public static final String PRIVATE_CONSTANT_370 = "SAVEDQUERY_PICKER_TABLEVIEW_DESC";

   @RBEntry("Saved Search")
   @RBComment("Saved Search")
   public static final String PRIVATE_CONSTANT_371 = "SAVEDQUERY_TABLEVIEW_LABEL";

   @RBEntry("Default")
   @RBComment("Default View")
   public static final String PRIVATE_CONSTANT_372 = "PERSISTABLE_TABLEVIEW_NAME";

   @RBEntry("Default Table View")
   @RBComment("Default Table View")
   public static final String PRIVATE_CONSTANT_373 = "PERSISTABLE_TABLEVIEW_DESC";

   @RBEntry("Simple Search")
   @RBComment("Simple Search")
   public static final String PRIVATE_CONSTANT_374 = "PERSISTABLE_TABLEVIEW_LABEL";

   @RBEntry("Default Discussion Posting View")
   @RBComment("Default Discussion Posting View")
   public static final String PRIVATE_CONSTANT_375 = "DISCUSSIONPOSTING_TABLEVIEW_NAME";

   @RBEntry("Default Discussion Posting Search Table View")
   @RBComment("Default Discussion Posting Search Table View")
   public static final String PRIVATE_CONSTANT_376 = "DISCUSSIONPOSTING_TABLEVIEW_DESC";

   @RBEntry("Discussion Posting")
   @RBComment("Discussion Posting")
   public static final String PRIVATE_CONSTANT_377 = "DISCUSSIONPOSTING_TABLEVIEW_LABEL";

   @RBEntry("Default Meeting View")
   @RBComment("Default Meeting View")
   public static final String PRIVATE_CONSTANT_378 = "MEETING_TABLEVIEW_NAME";

   @RBEntry("Default Meeting Search Table View")
   @RBComment("Default Meeting Search Table View")
   public static final String PRIVATE_CONSTANT_379 = "MEETING_TABLEVIEW_DESC";

   @RBEntry("Meeting")
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
   @RBEntry("Default Report Template View")
   @RBComment("Default Report Template View")
   public static final String PRIVATE_CONSTANT_381 = "REPORTTEMPLATE_TABLEVIEW_NAME";

   @RBEntry("Default Report Template Search Table View")
   @RBComment("Default Report Template Search Table View")
   public static final String PRIVATE_CONSTANT_382 = "REPORTTEMPLATE_TABLEVIEW_DESC";

   @RBEntry("Report Template")
   @RBComment("Report Template")
   public static final String PRIVATE_CONSTANT_383 = "REPORTTEMPLATE_TABLEVIEW_LABEL";

   @RBEntry("Default Workflow Process View")
   @RBComment("Default Workflow Process View")
   public static final String PRIVATE_CONSTANT_384 = "WORKFLOWPROCESS_TABLEVIEW_NAME";

   @RBEntry("Default Workflow Process Search Table View")
   @RBComment("Default Workflow Process Search Table View")
   public static final String PRIVATE_CONSTANT_385 = "WORKFLOWPROCESS_TABLEVIEW_DESC";

   @RBEntry("Workflow Process")
   @RBComment("Workflow Process")
   public static final String PRIVATE_CONSTANT_386 = "WORKFLOWPROCESS_TABLEVIEW_LABEL";

   @RBEntry("Default Library View")
   @RBComment("Default Library View")
   public static final String PRIVATE_CONSTANT_387 = "WTLIBRARY_TABLEVIEW_NAME";

   @RBEntry("Default Library Search Table View")
   @RBComment("Default Library Search Table View")
   public static final String PRIVATE_CONSTANT_388 = "WTLIBRARY_TABLEVIEW_DESC";

   @RBEntry("Library")
   @RBComment("Library")
   public static final String PRIVATE_CONSTANT_389 = "WTLIBRARY_TABLEVIEW_LABEL";

   @RBEntry("Default Import Job View")
   @RBComment("Default Import Job View")
   public static final String PRIVATE_CONSTANT_390 = "IMPORTJOB_TABLEVIEW_NAME";

   @RBEntry("Default Import Job Search Table View")
   @RBComment("Default Import Job Search Table View")
   public static final String PRIVATE_CONSTANT_391 = "IMPORTJOB_TABLEVIEW_DESC";

   @RBEntry("Import Job")
   @RBComment("Import Job")
   public static final String PRIVATE_CONSTANT_392 = "IMPORTJOB_TABLEVIEW_LABEL";

   @RBEntry("Default Milestone View")
   @RBComment("Default Milestone View")
   public static final String PRIVATE_CONSTANT_393 = "MILESTONE_TABLEVIEW_NAME";

   @RBEntry("Default Milestone Search Table View")
   @RBComment("Default Milestone Search Table View")
   public static final String PRIVATE_CONSTANT_394 = "MILESTONE_TABLEVIEW_DESC";

   @RBEntry("Milestone")
   @RBComment("Milestone")
   public static final String PRIVATE_CONSTANT_395 = "MILESTONE_TABLEVIEW_LABEL";

   @RBEntry("Default Project Resource View")
   @RBComment("Default Project Resource View")
   public static final String PRIVATE_CONSTANT_396 = "PROJECTRESOURCE_TABLEVIEW_NAME";

   @RBEntry("Default Project Resource Search Table View")
   @RBComment("Default Project Resource Search Table View")
   public static final String PRIVATE_CONSTANT_397 = "PROJECTRESOURCE_TABLEVIEW_DESC";

   @RBEntry("Project Resource")
   @RBComment("Project Resource")
   public static final String PRIVATE_CONSTANT_398 = "PROJECTRESOURCE_TABLEVIEW_LABEL";

   @RBEntry("Default Project Activity View")
   @RBComment("Default Project Activity View")
   public static final String PRIVATE_CONSTANT_399 = "PROJECTACTIVITY_TABLEVIEW_NAME";

   @RBEntry("Default Project Activity Search Table View")
   @RBComment("Default Project Activity Search Table View")
   public static final String PRIVATE_CONSTANT_400 = "PROJECTACTIVITY_TABLEVIEW_DESC";

   @RBEntry("Project Activity")
   @RBComment("Project Activity")
   public static final String PRIVATE_CONSTANT_401 = "PROJECTACTIVITY_TABLEVIEW_LABEL";

   @RBEntry("Default Promotion Request View")
   @RBComment("Default Promotion Request View")
   public static final String PRIVATE_CONSTANT_402 = "PROMOTIONNOTICE_TABLEVIEW_NAME";

   @RBEntry("Default Promotion Request Search Table View")
   @RBComment("Default Promotion Request Search Table View")
   public static final String PRIVATE_CONSTANT_403 = "PROMOTIONNOTICE_TABLEVIEW_DESC";

   @RBEntry("Promotion Request")
   @RBComment("Promotion Request")
   public static final String PRIVATE_CONSTANT_404 = "PROMOTIONNOTICE_TABLEVIEW_LABEL";

   @RBEntry("Default Summary Activity View")
   @RBComment("Default Summary Activity View")
   public static final String PRIVATE_CONSTANT_405 = "SUMMARYACTIVITY_TABLEVIEW_NAME";

   @RBEntry("Default Summary Activity Search Table View")
   @RBComment("Default Summary Activity Search Table View")
   public static final String PRIVATE_CONSTANT_406 = "SUMMARYACTIVITY_TABLEVIEW_DESC";

   @RBEntry("Summary Activity")
   @RBComment("Summary Activity")
   public static final String PRIVATE_CONSTANT_407 = "SUMMARYACTIVITY_TABLEVIEW_LABEL";

   @RBEntry("Default Supplier View")
   @RBComment("Default Supplier View")
   public static final String PRIVATE_CONSTANT_408 = "SUPPLIER_TABLEVIEW_NAME";

   @RBEntry("Default Supplier Search Table View")
   @RBComment("Default Supplier Search Table View")
   public static final String PRIVATE_CONSTANT_409 = "SUPPLIER_TABLEVIEW_DESC";

   @RBEntry("Supplier")
   @RBComment("Supplier")
   public static final String PRIVATE_CONSTANT_410 = "SUPPLIER_TABLEVIEW_LABEL";

   @RBEntry("Default Supplier Part View")
   @RBComment("Default Supplier Part View")
   public static final String PRIVATE_CONSTANT_411 = "SUPPLIERPART_TABLEVIEW_NAME";

   @RBEntry("Default Supplier Part Search Table View")
   @RBComment("Default Supplier Part Search Table View")
   public static final String PRIVATE_CONSTANT_412 = "SUPPLIERPART_TABLEVIEW_DESC";

   @RBEntry("Supplier Part")
   @RBComment("Supplier Part")
   public static final String PRIVATE_CONSTANT_413 = "SUPPLIERPART_TABLEVIEW_LABEL";

   @RBEntry("Default Product View")
   @RBComment("Default Product View")
   public static final String PRIVATE_CONSTANT_414 = "PDMLINKPRODUCT_TABLEVIEW_NAME";

   @RBEntry("Default Product Search Table View")
   @RBComment("Default Product Search Table View")
   public static final String PRIVATE_CONSTANT_415 = "PDMLINKPRODUCT_TABLEVIEW_DESC";

   @RBEntry("Product")
   @RBComment("Product")
   public static final String PRIVATE_CONSTANT_416 = "PDMLINKPRODUCT_TABLEVIEW_LABEL";

   @RBEntry("Default Context View")
   @RBComment("Default Context View")
   public static final String PRIVATE_CONSTANT_417 = "CONTAINER_TABLEVIEW_NAME";

   @RBEntry("Default Context Search Table View")
   @RBComment("Default Context Search Table View")
   public static final String PRIVATE_CONSTANT_418 = "CONTAINER_TABLEVIEW_DESC";

   @RBEntry("Context")
   @RBComment("Context")
   public static final String PRIVATE_CONSTANT_419 = "CONTAINER_TABLEVIEW_LABEL";

   @RBEntry("Yes")
   @RBComment("Label for True")
   public static final String PRIVATE_CONSTANT_420 = "TRUE_LABEL";

   @RBEntry("No")
   @RBComment("Label for False")
   public static final String PRIVATE_CONSTANT_421 = "FALSE_LABEL";

   @RBEntry("Invalid Saved Query XML Format")
   @RBComment("Invalid Saved Query XML Format")
   public static final String PRIVATE_CONSTANT_422 = "INVALID_SAVED_QUERY_FORMAT";

   @RBEntry("- Select -")
   @RBComment("Label for No Selection")
   public static final String PRIVATE_CONSTANT_423 = "NO_SELECTION_LABEL";

   @RBEntry("Set this search as a global search")
   @RBComment("Mark as Global Search")
   public static final String PRIVATE_CONSTANT_424 = "GLOBAL_SEARCH_SHOW";

   @RBEntry("Overwrite Existing Saved Search")
   @RBComment("Overwrite Existing Saved Search")
   public static final String PRIVATE_CONSTANT_425 = "OVERWRITE_EXISTING";

   @RBEntry("Global Search Scope:")
   @RBComment("Set Scope of Global Search")
   public static final String PRIVATE_CONSTANT_426 = "SCOPE_OF_GLOBAL_SEARCH";

   @RBEntry("Search in Network")
   @RBComment("Label for search in network")
   public static final String PRIVATE_CONSTANT_427 = "SEARCH_IN_NETWORK_LABEL";

   @RBEntry("Search for Related Objects")
   @RBComment("Label for search related to objects in a particular context")
   public static final String PRIVATE_CONSTANT_428 = "SEARCH_FOR_ITEMS_IN_CONTEXT_LABEL";

   @RBEntry("Network Context(s):")
   @RBComment("Label for the property \"Search In:\" on the search page when the request comes from Program tab.")
   public static final String PRIVATE_CONSTANT_429 = "SEARCH_IN_NETWORK_CONTEXT";

   @RBEntry("Search For Type(s):")
   @RBComment("Label for the type property on the search page when the request comes from Program tab.")
   public static final String PRIVATE_CONSTANT_430 = "SEARCH_FOR_TYPE";

   @RBEntry("Relationship(s):")
   @RBComment("Label for the relationship property on the search page when the request comes from Program tab.")
   public static final String PRIVATE_CONSTANT_431 = "SEARCH_FOR_RELATIONSHIP";

   @RBEntry("File:")
   @RBComment("Select jar or zip to import")
   public static final String PRIVATE_CONSTANT_432 = "SELECT_FILE_IMPORT";

   @RBEntry("(.jar, .zip)")
   @RBComment("File types allowed for Import Saved Query wizard")
   public static final String PRIVATE_CONSTANT_433 = "IMPORT_FILE_TYPES";

   @RBEntry("The file must be of type .jar or .zip")
   @RBComment("Error message displayed when user tries to import a file other than a jar or zip file.")
   public static final String PRIVATE_CONSTANT_434 = "EXPORT_IMPORT_FILE_ERROR";

   @RBEntry("Resolve Overridable Conflicts")
   @RBComment("Label for the resolve overridable conflicts checkbox")
   public static final String PRIVATE_CONSTANT_435 = "RESOLVE_OVERRIDABLE_CONFLICTS";

   @RBEntry("File:")
   @RBComment("File name into which the savedquery should be exported")
   public static final String PRIVATE_CONSTANT_436 = "SELECT_FILE_EXPORT";

   @RBEntry("(Provide a file path+name with extension .jar or .zip)")
   @RBComment("File types allowed for Export Saved Query wizard")
   public static final String PRIVATE_CONSTANT_437 = "EXPORT_FILE_TYPES";

   @RBEntry("Detailed Log")
   @RBComment("Label for detailed log link")
   public static final String PRIVATE_CONSTANT_438 = "EXPORT_IMPORT_DETAILED_LOG";

   @RBEntry("The following selected item(s) could not be deleted: {0}")
   @RBComment("Message showing list of queries couldn't be deleted")
   public static final String PRIVATE_CONSTANT_439 = "DELETE_QUERY_MESSAGE";

   @RBEntry("Default Folder Search View")
   @RBComment("Default Folder Search View")
   public static final String PRIVATE_CONSTANT_440 = "FOLDER_TABLEVIEW_NAME";

   @RBEntry("Default Folder Search Table")
   @RBComment("Default Folder Search Table")
   public static final String PRIVATE_CONSTANT_441 = "FOLDER_TABLEVIEW_DESC";

   @RBEntry("Folder")
   @RBComment("Folder")
   public static final String PRIVATE_CONSTANT_442 = "FOLDER_TABLEVIEW_LABEL";

   @RBEntry("ATTENTION: Required Fields Can Not be Removed\n\nThe following required fields can not be removed:\n{0}")
   @RBComment("Message for alert when one tries to remove criteria which is marked required.")
   public static final String PRIVATE_CONSTANT_443 = "REMOVE_QUERYBUILDER_ROW";

   @RBEntry("ATTENTION: Required Information Is Missing \n\n{0} is a required field. \nYou must specify valid information for all required fields indicated by an asterisk (*).")
   @RBComment("Message for alert if the required attributes are not filled.")
   public static final String REQ_INFO_MISSING = "REQ_INFO_MISSING";

   @RBEntry("ATTENTION: Required information is missing. \n\n You must specify valid information for all required fields indicated by an asterisk (*).")
   public static final String SAVED_SEARCH_NAME_MISSING = "SAVED_SEARCH_NAME_MISSING";

   @RBEntry("Filename")
   @RBComment("CADName")
   public static final String PRIVATE_CONSTANT_444 = "CADNAME";

   @RBEntry("Search instead for: ")
   @RBComment("The query suggestion message.")
   public static final String QUERY_SUGGESTION_MESSAGE = "QUERY_SUGGESTION_MESSAGE";

   @RBEntry("Owner")
   @RBComment("Owner")
   public static final String PRIVATE_CONSTANT_445 = "OWNER_LABEL";

   @RBEntry("Checked Out By")
   @RBComment("Checked Out By")
   public static final String PRIVATE_CONSTANT_446 = "CHECKED_OUT_BY";

   @RBEntry("Updated By")
   @RBComment("Updated By")
   public static final String PRIVATE_CONSTANT_447 = "UPDATED_BY";

   @RBEntry("Note Text")
   @RBComment("Note Text")
   public static final String PRIVATE_CONSTANT_448 = "PTC_NOTE_TEXT";

   @RBEntry("Less than")
   @RBComment("Less than")
   public static final String PRIVATE_CONSTANT_449 = "OP_LESS";

   @RBEntry("Less than equal to")
   @RBComment("Less than equal to")
   public static final String PRIVATE_CONSTANT_450 = "OP_LESS_THAN_EQUAL_TO";

   @RBEntry("Greater than")
   @RBComment("Greater than")
   public static final String PRIVATE_CONSTANT_451 = "OP_GREATOR";

   @RBEntry("Greater than equal to")
   @RBComment("Greater than equal to")
   public static final String PRIVATE_CONSTANT_452 = "OP_GREATOR_THAN_EQUAL_TO";

   @RBEntry("Role Name")
   @RBComment("Role Name")
   public static final String PRIVATE_CONSTANT_453 = "ROLE_NAME";

   @RBEntry("Role ")
   @RBComment("Role")
   public static final String PRIVATE_CONSTANT_454 = "ROLE";

   @RBEntry("Refine search")
   @RBComment("Refine search")
   public static final String PRIVATE_CONSTANT_455 = "REFINE_SEARCH";

   @RBEntry("Find Object")
   @RBComment("Item Picker Title")
   public static final String PRIVATE_CONSTANT_456 = "ITEM_PICKER_TITLE";

   @RBEntry("Object Picker")
   @RBComment("Item Picker Label")
   public static final String PRIVATE_CONSTANT_457 = "ITEM_PICKER_LABEL";

   @RBEntry("Find Context")
   @RBComment("Context Picker Title")
   public static final String PRIVATE_CONSTANT_458 = "CONTEXTS_PICKER_TITLE";

   @RBEntry("Context Picker")
   @RBComment("Context Picker Label")
   public static final String PRIVATE_CONSTANT_459 = "CONTEXTS_PICKER_LABEL";

   @RBEntry("Find")
   @RBComment("Find")
   public static final String PRIVATE_CONSTANT_460 = "FIND_PICKER_LABEL";

   @RBEntry("View")
   @RBComment("View")
   public static final String PRIVATE_CONSTANT_461 = "VIEW_PICKER_LABEL";

   @RBEntry("Default Sourcing Context Search View")
   @RBComment("Default Sourcing Context Search View")
   public static final String PRIVATE_CONSTANT_462 = "AXLCONTEXT_TABLEVIEW_NAME";

   @RBEntry("Default Sourcing Context Search Table")
   @RBComment("Default Sourcing Context Search Table")
   public static final String PRIVATE_CONSTANT_463 = "AXLCONTEXT_TABLEVIEW_DESC";

   @RBEntry("Sourcing Context")
   @RBComment("Sourcing Context")
   public static final String PRIVATE_CONSTANT_464 = "AXLCONTEXT_TABLEVIEW_LABEL";

   @RBEntry("Default Note View")
   @RBComment("Default Note View")
   public static final String PRIVATE_CONSTANT_465 = "NOTE_TABLEVIEW_NAME";

   @RBEntry("Default Note Search Table")
   @RBComment("Default Note Search Table")
   public static final String PRIVATE_CONSTANT_466 = "NOTE_TABLEVIEW_DESC";

   @RBEntry("Exported Search Results")
   @RBComment("Exported search results header")
   public static final String PRIVATE_CONSTANT_467 = "EXPORTED_SEARCH_RESULTS";

   @RBEntry("Note")
   @RBComment("Note")
   public static final String PRIVATE_CONSTANT_468 = "NOTE_TABLEVIEW_LABEL";

   @RBEntry("Search")
   public static final String PRIVATE_CONSTANT_469 = "search.pickerSearch.description";

   @RBEntry("Clear")
   public static final String PRIVATE_CONSTANT_470 = "search.pickerClear.description";

   @RBEntry("It seems that you have not defined the callback function or the there is some error in it:")
   @RBComment("Error on picker call back")
   public static final String PICKER_CALL_BACK_ERROR = "PICKER_CALL_BACK_ERROR";

   @RBEntry("Name")
   @RBComment("Name")
   public static final String SAVED_QUERY_NAME = "SAVED_QUERY_NAME";

   @RBEntry("Creator")
   @RBComment("Creator")
   public static final String SAVED_QUERY_CREATOR = "SAVED_QUERY_CREATOR";

   @RBEntry("Owning Page")
   @RBComment("Owning Page")
   public static final String SAVED_QUERY_OWNINGPAGE = "SAVED_QUERY_OWNINGPAGE";

   @RBEntry("Created On")
   @RBComment("Created")
   public static final String SAVED_QUERY_CREATEDON = "SAVED_QUERY_CREATEDON";

   @RBEntry("Last Updated")
   @RBComment("Last Updated")
   public static final String SAVED_QUERY_UPDATEDON = "SAVED_QUERY_UPDATEDON";

   @RBEntry("Owner")
   @RBComment("Owner")
   public static final String SAVED_QUERY_OWNER = "SAVED_QUERY_OWNER";

   @RBEntry("Show")
   @RBComment("Show")
   public static final String SAVED_QUERY_SHOW = "SAVED_QUERY_SHOW";

   @RBEntry("It seems that opener window is closed")
   @RBComment("Message thrown when picker opener is closed")
   public static final String OPENER_WINDOW_CLOSED = "OPENER_WINDOW_CLOSED";

   @RBEntry("Nothing has been selected")
   @RBComment("Nothing has been selected")
   public static final String NOTHING_SELECTED = "NOTHING_SELECTED";

   @RBEntry("Please specify search criteria or enter a valid keyword.")
   @RBComment("Please specify search criteria or enter a valid keyword.")
   public static final String EMPTY_CRITERAI_MESSAGE = "EMPTY_CRITERAI_MESSAGE";

   @RBEntry("Include All Parts")
   @RBComment("Include All Parts")
   public static final String SEARCH_CRITERIA_INCLUDE_ALL_PARTS = "SEARCH_CRITERIA_INCLUDE_ALL_PARTS";

   @RBEntry("Include Only")
   @RBComment("Include Only")
   public static final String SEARCH_CRITERIA_INCLUDE_ONLY = "SEARCH_CRITERIA_INCLUDE_ONLY";

   @RBEntry("Default Trace Code")
   @RBComment("Default Trace Code")
   public static final String DEFAULT_TRACE_CODE = "DEFAULT_TRACE_CODE";
 
   @RBEntry("End Item")
   @RBComment("End Item")
   public static final String DDL_OPTIONS_ENDITEM = "DDL_OPTIONS_ENDITEM";

   @RBEntry("Lot")
   @RBComment("Lot")
   public static final String DDL_OPTIONS_LOT = "DDL_OPTIONS_LOT";

   @RBEntry("Serial Number ")
   @RBComment("serialNumber")
   public static final String DDL_OPTIONS_SERIALNUMBER = "DDL_OPTIONS_SERIALNUMBER";

   @RBEntry("Lot / Serial Number")
   @RBComment("lotSerialNumber")
   public static final String DDL_OPTIONS_LOTSERIALNUMBER = "DDL_OPTIONS_LOTSERIALNUMBER";

   @RBEntry("Standard")
   @RBComment("standard")
   public static final String DDL_OPTIONS_STANDARD = "DDL_OPTIONS_STANDARD";

   @RBEntry("Generic")
   @RBComment("genericParts")
   public static final String DDL_OPTIONS_GENERICPARTS = "DDL_OPTIONS_GENERICPARTS";

   @RBEntry("Configurable Generic")
   @RBComment("configurableGenericParts")
   public static final String DDL_OPTIONS_CONFIGURABLEGENERICPARTS = "DDL_OPTIONS_CONFIGURABLEGENERICPARTS";

   @RBEntry("Variant")
   @RBComment("variantParts")
   public static final String DDL_OPTIONS_VARIANTPARTS = "DDL_OPTIONS_VARIANTPARTS";

   @RBEntry("Advanced Configurable")
   @RBComment("advancedConfigurableParts")
   public static final String DDL_OPTIONS_ADVANCEDCONFIGURABLEPARTS = "DDL_OPTIONS_ADVANCEDCONFIGURABLEPARTS";

   @RBEntry("Advanced Configurable End Item")
   @RBComment("advanced Configurable End Items")
   public static final String DDL_OPTIONS_ADVANCEDCONFIGURABLEENDITEMS = "DDL_OPTIONS_ADVANCEDCONFIGURABLEENDITEMS";

   @RBEntry("Configurable")
   @RBComment("configurableParts")
   public static final String DDL_OPTIONS_CONFIGURABLEPARTS = "DDL_OPTIONS_CONFIGURABLEPARTS";
   
   @RBEntry("Configurable Module")
   @RBComment("genericType")
   public static final String GENERIC_TYPE = "GENERIC_TYPE";
  
   @RBEntry("FileName Field on Simple Search Page")
   @RBComment("Property need to set to Search for CAD Name from Simple search page.")
   public static final String CADNAME_FILENAME_ENABLED = "CADNAME_FILENAME_ENABLED";

   @RBEntry("Showing FileName Field on Simple Search Page with which user can search CAD Dcoument with CAD Name.")
   @RBComment("Property need to set to Search for CAD Name from Simple search page.")
   public static final String CADNAME_FILENAME_ENABLED_DESCRIPTION = "CADNAME_FILENAME_ENABLED_DESCRIPTION";

   @RBEntry("Showing FileName Field on Simple Search Page with which user can search CAD Dcoument with CAD Name.")
   @RBComment("Property need to set to Search for CAD Name from Simple search page.")
   public static final String CADNAME_FILENAME_ENABLED_LONGDESCRIPTION = "CADNAME_FILENAME_ENABLED_LONGDESCRIPTION";

   @RBEntry("Thumbnail")
   @RBComment("Thumbnail column label")
   public static final String THUMBNAIL = "THUMBNAIL";

   @RBEntry("Results Per Page requires valid numeric value")
   @RBComment("Message thrown when user enters invalid number for result per page.")
   public static final String MESSAGE_RESULT_PER_PAGE = "MESSAGE_RESULT_PER_PAGE";

   @RBEntry("Global Search Default Types List")
   @RBComment("display name for global search type list in preference manager")
   public static final String GLOBAL_SEARCH_TYPE_LIST_NAME = "GLOBAL_SEARCH_TYPE_LIST_NAME";

   @RBEntry("This preference specifies the object types displayed in the global search drop-down list.")
   @RBComment("short description of global search type list in preference manager")
   public static final String GLOBAL_SEARCH_TYPE_LIST_DESC = "GLOBAL_SEARCH_TYPE_LIST_DESC";

   @RBEntry("This preference specifies the object types displayed in the global search drop-down list.")
   @RBComment("long description of global search type list in preference manager")
   public static final String GLOBAL_SEARCH_TYPE_LIST_LONG_DESC = "GLOBAL_SEARCH_TYPE_LIST_LONG_DESC";

   @RBEntry("Thumbnail Display in Search Results")
   @RBComment("Display name for thumbnailsOnWCSearch option in Preferences client")
   public static final String THUMBNAILS_ON_WC_SEARCH = "THUMBNAILS_ON_WC_SEARCH";

   @RBEntry("Chooses whether thumbnail images are displayed on search results pages.")
   @RBComment("Description for thumbnailsOnWCSearch option in the Preferences client")
   public static final String THUMBNAILS_ON_WC_SEARCH_DESCRIPTION = "THUMBNAILS_ON_WC_SEARCH_DESCRIPTION";

   @RBEntry("This preference is used by search client. Only objects viewable in Creo View have thumbnail images available. Note that the search results load more quickly if thumbnail images are not displayed. The system default is to not display the thumbnails.")
   @RBComment("Long description for thumbnailsOnWCSearch option in the Preferences client")
   public static final String THUMBNAILS_ON_WC_SEARCH_LONGDESCRIPTION = "THUMBNAILS_ON_WC_SEARCH_LONGDESCRIPTION";

   @RBEntry("Reset GlobalType Selection")
   @RBComment("Display name for ResetGlobalTypeSelection option in Preferences client")
   public static final String RESET_GLOBAL_TYPESELECTION = "RESET_GLOBAL_TYPESELECTION";

   @RBEntry("Allow user to reset global type")
   @RBComment("Description for ResetGlobalTypeSelection option in the Preferences client")
   public static final String RESET_GLOBAL_TYPESELECTION_DESCRIPTION = "RESET_GLOBAL_TYPESELECTION_DESCRIPTION";

   @RBEntry("Allow user to reset global type")
   @RBComment("Long description for ResetGlobalTypeSelection option in the Preferences client")
   public static final String RESET_GLOBAL_TYPESELECTION_LONGDESCRIPTION = "RESET_GLOBAL_TYPESELECTION_LONGDESCRIPTION";

   @RBEntry("All Applicable Object Types Search")
   @RBComment("Display name for allSearchTypes option in Preferences client")
   public static final String ALLSEARCHTYPES = "ALLSEARCHTYPES";

   @RBEntry("List of types shown in Type Picker in Search pages. Also the list of objects that are searched when user selects \"All Applicable Object Types\" as object type on Simple search page.")
   @RBComment("Description for allSearchTypes option in the Preferences client.When you search within a specific context, the object types listed are set by the context administrator and may differ from the object types that you select for this preference.")
   public static final String ALLSEARCHTYPES_DESCRIPTION = "ALLSEARCHTYPES_DESCRIPTION";

   @RBEntry("List of types shown in the Type window on search pages. Also the list of objects that are searched when user selects All Applicable Object Types as the object type on a simple search page. When the Windchill server is installed the All Applicable Object Types Search preference does not have a value. When the Windchill Search page is accessed, the preference default value is set according to the SearchableTypes.properties file. Search page should be accessed at least once, before changing the search preference. ")
   @RBComment("Long description for allSearchTypes option in the Preferences client.When you search within a specific context, the object types listed are set by the context administrator and may differ from the object types that you select for this preference.")
   public static final String ALLSEARCHTYPES_LONGDESCRIPTION = "ALLSEARCHTYPES_LONGDESCRIPTION";

   @RBEntry("Global Type Selection")
   @RBComment("Display name for globalTypeSelection option in Preferences client")
   public static final String GLOBAL_TYPE_SELECTION = "GLOBAL_TYPE_SELECTION";

   @RBEntry("This preference holds the type selection on Simple Search page. When user selects different object types, this preference gets changed.")
   @RBComment("Description for Default Type Selection option in the Preferences client")
   public static final String GLOBAL_TYPE_SELECTION_DESCRIPTION = "GLOBAL_TYPE_SELECTION_DESCRIPTION";

   @RBEntry("This preference holds the type selection on Simple Search page. When user selects different object types, this preference gets changed.")
   @RBComment("Long description for Default Type Selection option in the Preferences client")
   public static final String GLOBAL_TYPE_SELECTION_LONGDESCRIPTION = "GLOBAL_TYPE_SELECTION_LONGDESCRIPTION";

   @RBEntry("Advanced Search Default Types List")
   @RBComment("Display name for searchTypeSelection ")
   public static final String SEARCH_TYPE_SELECTION = "SEARCH_TYPE_SELECTION";

   @RBEntry("This preference specifies the object types displayed by default on the advanced search page.")
   @RBComment("Description for Default Type Selection option in the Preferences client")
   public static final String SEARCH_TYPE_SELECTION_DESCRIPTION = "SEARCH_TYPE_SELECTION_DESCRIPTION";

   @RBEntry("This preference specifies the object types displayed by default on the advanced search page.")
   @RBComment("Long description for Default Type Selection option in the Preferences client")
   public static final String SEARCH_TYPE_SELECTION_LONGDESCRIPTION = "SEARCH_TYPE_SELECTION_LONGDESCRIPTION";

   @RBEntry("Internal names of saved searches for user.")
   @RBComment("Display name for savedSearches option in Preferences client")
   public static final String SAVEDSEARCHES = "SAVEDSEARCHES";

   @RBEntry("Internal names of saved searches for user.")
   @RBComment("Description for savedSearches option in the Preferences client")
   public static final String SAVEDSEARCHES_DESCRIPTION = "SAVEDSEARCHES_DESCRIPTION";

   @RBEntry("Internal names of saved searches for user.")
   @RBComment("Long description for savedSearches option in the Preferences client")
   public static final String SAVEDSEARCHES_LONGDESCRIPTION = "SAVEDSEARCHES_LONGDESCRIPTION";

   /**
    * added for fixing SPR 1350174
    **/
   @RBEntry("An error has occurred while performing this search.  Please inform your system administrator and tell them what you were doing when the problem occurred.")
   @RBComment("This value is will be displayed as a warning alert for exceptions which are thrown from Server side without proper embedded messages ")
   public static final String GENERIC_ERROR_MESSAGE = "GENERIC_EXCEPTION_MESSAGE";

   @RBEntry("Warning: The number of search results has reached the limit set by your administrator. When the limit is reached, the search stops and applies any filters that are enforced at your site. These filters are applied only to the objects returned before the limit was reached. To ensure a complete list of results, edit your search to apply more restrictive criteria. For more information, see the online help.")
   @RBComment("Message to display to a user when there are possible additional search results in Rware and they need to make their criteria more specific")
   public static final String INCOMPLETE_RESULT_SET_WARNING = "INCOMPLETE_RESULT_SET_WARNING";

   @RBEntry("Warning: The number of search results has reached the limit set by your administrator. When the limit is reached, the search stops and applies any filters that are enforced at your site. These filters are applied only to the objects returned before the limit was reached. To ensure a complete list of results, edit your search to apply more restrictive criteria. For more information, see the online help.")
   @RBComment("Message to display to a user when there are possible additional search results at DB and they need to make their criteria more specific")
   public static final String INCOMPLETE_DB_RESULT_SET_WARNING = "INCOMPLETE_DB_RESULT_SET_WARNING";

   @RBEntry("Results per page")
   @RBComment("Display name for result per page in Preferences client")
   public static final String SEARCHTABLELIMIT = "SEARCHTABLELIMIT";

   @RBEntry("This preference sets default value for result per page.By default it is 15.")
   @RBComment("Description for Results per page option in the Preferences client")
   public static final String RESULT_PER_PAGE_DESCRIPTION = "SEARCHTABLELIMIT_DESCRIPTION";

   @RBEntry("This preference sets default value for result per page.By default it is 15.")
   @RBComment("Long description for Results per page option in the Preferences client")
   public static final String RESULT_PER_PAGE_LONGDESCRIPTION = "SEARCHTABLELIMIT_LONGDESCRIPTION";

   @RBEntry("Attention: Item(s) already in Selected object list...\n\nThe Selected list already contains some of the items you are attempting to move:\n{0}\n\nItems you indicated that are not already in the Selected list will be added.")
   @RBComment("Attention: Item(s) already in Selected object list...\n\nThe Selected list already contains some of the items you are attempting to move:\n{0}\n\nItems you indicated that are not already in the Selected list will be added.")
   public static final String SOME_ITEMS_ALREADY_SELECTED = "SOME_ITEMS_ALREADY_SELECTED";

   @RBEntry("Attention: Item(s) already in Selected object list...\n\nThe Selected list already contains the items you are attempting to move:\n{0}")
   @RBComment("Attention: Item(s) already in Selected object list...\n\nThe Selected list already contains the items you are attempting to move:\n{0}")
   public static final String ALL_ITEMS_ALREADY_SELECTED = "ALL_ITEMS_ALREADY_SELECTED";

   @RBEntry("Untraced")
   @RBComment("untracedParts")
   public static final String DDL_OPTIONS_UUNTRACED = "DDL_OPTIONS_UUNTRACED";

   @RBEntry("Include Only option requires at least one option selected.")
   @RBComment("Alert message if user selects no checkbox from include only radio.")
   public static final String DDLALERT = "DDLALERT";

   @RBEntry("Value already exists in the system.\n\nThe name you have entered on this page already exists.Please click OK to overwrite the existing name, or click Cancel to return the page and enter a new name.")
   @RBComment("Alert message for same saved search name")
   public static final String SAVEDSEARCHNAMEALERT = "SAVEDSEARCHNAMEALERT";

   @RBEntry("Contract Line Item Number")
   @RBComment("search attribute for CDRLBasePackage")
   public static final String CONTRACT_LINE_ITEM = "CONTRACT_LINE_ITEM";

   @RBEntry("Contract Number")
   @RBComment("search attribute for CDRLBasePackage")
   public static final String CONTRACT_NUMBER = "CONTRACT_NUMBER";

   @RBEntry("ATTENTION: Ineligible objects selected for Delete action. \n\n Ineligible objects will not get deleted.")
   @RBComment("Delete action is invalid for one or more selected objects")
   public static final String DELETE_ACTION_INVALID = "DELETE_ACTION_INVALID";

   @RBEntry("Folder")
   @RBComment("Label for folder search picker.")
   public static final String LOCATION_PICKER_LABEL = "LOCATION_PICKER_LABEL";

   @RBEntry("Last Search")
   @RBComment("Last Search")
   public static final String LAST_SEARCH = "LAST_SEARCH";

   @RBEntry("Authoring Application")
   @RBComment("Label for EPMDocument authoring application")
   public static final String EPM_AUTHORING_APPLICATION = "EPM_AUTHORING_APPLICATION";

   @RBEntry("Document Category")
   @RBComment("Label for EPMDocument document category")
   public static final String EPM_DOCUMENT_TYPE = "EPM_DOCUMENT_TYPE";

   @RBEntry("Default Agreement View")
   @RBComment("Default Agreement View")
   public static final String PRIVATE_CONSTANT_471 = "AUTHORIZATIONAGREEMENT_TABLEVIEW_NAME";

   @RBEntry("Default Agreement Search Table View")
   @RBComment("Default Agreement Search Table View")
   public static final String PRIVATE_CONSTANT_472 = "AUTHORIZATIONAGREEMENT_TABLEVIEW_DESC";

   @RBEntry("Agreement")
   @RBComment("Agreement")
   public static final String PRIVATE_CONSTANT_473 = "AUTHORIZATIONAGREEMENT_TABLEVIEW_LABEL";

   @RBEntry("Resource Type")
   @RBComment("Label for Resource User Type dropdown having values Windchill User and Other")
   public static final String PLAN_RESOURCE_NAME_LABEL = "PLAN_RESOURCE_NAME_LABEL";

   @RBEntry("CAGE Code")
   @RBComment("Org field name on search page when wadam is installed")
   public static final String WADMORG = "WADMORG";

   @RBEntry("Part Configuration Name")
   @RBComment("Label for configuration name in the configuration compare search picker")
   public static final String PART_CONFIGURATION_NAME_LABEL = "PART_CONFIGURATION_NAME_LABEL";

   @RBEntry("Base Part Name")
   @RBComment("Label for base part name in the configuration compare search picker")
   public static final String BASE_PART_NAME_LABEL = "BASE_PART_NAME_LABEL";

   @RBEntry("Base Part Number")
   @RBComment("Label for base part number in the configuration compare search picker")
   public static final String BASE_PART_NUMBER_LABEL = "BASE_PART_NUMBER_LABEL";

   @RBEntry("Contact")
   @RBComment("Label for the saved search customize table contact column")
   public static final String PRIVATE_CONSTANT_474 = "CONTACT_LABEL";

   @RBEntry("Show Advanced Search")
   @RBComment("Label used at picker")
   public static final String ADVANCED_SEARCH_LBL = "ADVANCED_SEARCH_LBL";

   @RBEntry("Hide Advanced Search")
   @RBComment("Label used at picker")
   public static final String BASIC_SEARCH_LBL = "BASIC_SEARCH_LBL";

   @RBEntry("Type")
   @RBComment("Label used at object type picker")
   public static final String OBJECT_TYPE_LBL = "OBJECT_TYPE_LBL";

   @RBEntry("Type")
   @RBComment("Label used at object type picker on advanced search page")
   public static final String TYPE_LBL = "TYPE_LBL";

   @RBEntry("Code")
   @RBComment("Label for the code field in Choice Search")
   public static final String PRIVATE_CONSTANT_475 = "CODE_LABEL";

   @RBEntry("Option")
   @RBComment("Label for the option field in Choice Search")
   public static final String PRIVATE_CONSTANT_476 = "OPTION_LABEL";

   @RBEntry("Group")
   @RBComment("Label for the group field in Choice Search")
   public static final String PRIVATE_CONSTANT_477 = "GROUP_LABEL";

   @RBEntry("Specific Context(s)")
   @RBComment("Static Find Specific Context option added in context picker dropdown")
   public static final String PRIVATE_CONSTANT_478 = "FIND_CONTEXT";

   @RBEntry("Format Icon")
   @RBComment("Label for the column \"Format Icon\" in tables")
   public static final String FORMAT_ICON = "FORMAT_ICON";

   @RBEntry("Result per page must be less than ")
   @RBComment("Max result per page cap.")
   public static final String RESULT_PER_PAGEMAX_CAP = "RESULT_PER_PAGEMAX_CAP";

   @RBEntry("Choose Date Range")
   @RBComment("Choose Date Range")
   public static final String DATE_DATE_RANGE = "DATE_DATE_RANGE";

   @RBEntry("Choose Days Range")
   @RBComment("Choose Day Range")
   public static final String DATE_DAY_RANGE = "DATE_DAY_RANGE";

   @RBEntry("Yesterday")
   @RBComment("Yesterday")
   public static final String DATE_YESTERDAY = "DATE_YESTERDAY";

   @RBEntry("Today")
   @RBComment("Today")
   public static final String DATE_TODAY = "DATE_TODAY";

   @RBEntry("Tomorrow")
   @RBComment("Tomorrow")
   public static final String DATE_TOMORROW = "DATE_TOMORROW";

   @RBEntry("days ago")
   @RBComment("days ago")
   public static final String DAYS_AGO = "DAYS_AGO";

   @RBEntry("days from now")
   @RBComment("days from now")
   public static final String DAYS_NOW = "DAYS_NOW";

   @RBEntry("New View")
   public static final String PRIVATE_CONSTANT_479 = "search.newView.description";

   @RBEntry("Obsolete criteria.")
   @RBComment("Message for alert when some of the criteria's are missing after upgrade.")
   public static final String MISSING_QUERYBUILDER_ROW = "MISSING_QUERYBUILDER_ROW";

   @RBEntry("This criteria has become obsolete after upgrade to latest release. Delete this criteria and add fresh criteria from criteria list and save search again. In Case of Administrator assigned saved search,ask administrator to recreate saved search.")
   @RBComment("Message for alert when some of the criteria's are missing after upgrade.")
   public static final String INVALID_QUERYBUILDER_ROW = "INVALID_QUERYBUILDER_ROW";

   @RBEntry("ID")
   @RBComment("Label for Project Plan Activity ID.")
   public static final String PLAN_ACTIVITY_ID = "PLAN_ACTIVITY_ID";

   @RBEntry("Invalid type for advanced search.")
   @RBComment("wt.fc.Persistable is not a valid type for advanced search")
   public static final String INVALID_TYPE_FOR_ADVANCED_SEARCH = "INVALID_TYPE_FOR_ADVANCED_SEARCH";

   @RBEntry("New View")
   @RBComment("new view")
   public static final String NEWVIEW = "NEWVIEW";

   @RBEntry("Close")
   public static final String PRIVATE_CONSTANT_480 = "search.searchCloseButton.description";

   @RBEntry("Make")
   public static final String PRIVATE_CONSTANT_481 = "MAKE_FACET";

   @RBEntry("Buy")
   public static final String PRIVATE_CONSTANT_482 = "BUY_FACET";

   @RBEntry("In Work")
   public static final String PRIVATE_CONSTANT_483 = "INWORK_FACET";

   @RBEntry("Released")
   public static final String PRIVATE_CONSTANT_484 = "RELEASED_FACET";

   @RBEntry("Parts")
   public static final String PRIVATE_CONSTANT_485 = "WTPART_FACET";

   @RBEntry("Documents")
   public static final String PRIVATE_CONSTANT_486 = "WTDOCUMENT_FACET";

   @RBEntry("Recipient Name")
   @RBComment("Field name for recipent name in delta delivery picker")
   public static final String RECIPIENT_NAME = "RECIPIENT_NAME";

   @RBEntry("Manage")
   public static final String PRIVATE_CONSTANT_487 = "search.savedSearchTable.description";

   @RBEntry("Manage")
   public static final String PRIVATE_CONSTANT_488 = "search.savedSearchTable.tooltip";

   @RBEntry("Manage Saved Searches")
   public static final String PRIVATE_CONSTANT_489 = "search.savedSearchTable.title";

   @RBEntry("height=450,width=920")
   public static final String PRIVATE_CONSTANT_523 = "search.savedSearchTable.moreurlinfo";

   @RBEntry("Save this search")
   public static final String PRIVATE_CONSTANT_490 = "search.saveThisSearchDefaultName.description";

   @RBEntry("height=250,width=450")
   public static final String PRIVATE_CONSTANT_491 = "search.saveThisSearchDefaultName.moreurlinfo";

   @RBEntry("Save This Search")
   public static final String PRIVATE_CONSTANT_492 = "search.saveThisSearchDefaultName.title";

   @RBEntry("Name")
   public static final String SAVED_SEARCH_DISPLAY_NAME = "SAVED_SEARCH_DISPLAY_NAME";

   @RBEntry("Close")
   public static final String PRIVATE_CONSTANT_493 = "search.savedSearchLightBoxCloseButton.description";

   @RBEntry("Search history and Saved searches")
   public static final String PRIVATE_CONSTANT_494 = "search.searchHistSavedSearch.description";

   @RBEntry("Advanced search")
   public static final String PRIVATE_CONSTANT_495 = "search.advancedSearch.description";

   @RBEntry("Classification search")
   public static final String PRIVATE_CONSTANT_496 = "search.classificationSearch.description";

   @RBEntry("Search History")
   @RBComment("Heading for Search History Display")
   public static final String SEARCH_HISTORY_HEADING = "SEARCH_HISTORY_HEADING";

   @RBEntry("Saved Searches")
   @RBComment("Heading for Saved Search Display")
   public static final String SAVED_SEARCH_HEADING = "SAVED_SEARCH_HEADING";

   @RBEntry("Created by Me")
   @RBComment("Saved Search section for queries saved by current user")
   public static final String SAVED_SEARCH_CREATED_BY_ME = "SAVED_SEARCH_CREATED_BY_ME";

   @RBEntry("Shared with Me")
   @RBComment("Saved Search section for queries saved by current user")
   public static final String SAVED_SEARCH_SHARED_WITH_ME = "SAVED_SEARCH_SHARED_WITH_ME";

   @RBEntry("Today")
   @RBComment(" Search History for today's searches.")
   public static final String SEARCH_HISTORY_TODAY = "SEARCH_HISTORY_TODAY";

   @RBEntry("Older ")
   @RBComment(" Search History for older searches.")
   public static final String SEARCH_HISTORY_OLDER = "SEARCH_HISTORY_OLDER";

   @RBEntry("No searches executed today")
   @RBComment("label when no records found in todays history")
   public static final String SEARCH_HISTORY_TODAY_NO_RECORDS_LABEL = "SEARCH_HISTORY_TODAY_NO_RECORDS_LABEL";

   @RBEntry("None")
   @RBComment("label when no records found in Saved Searches.")
   public static final String SAVED_SEARCH_NO_RECORDS_LABEL = "SAVED_SEARCH_NO_RECORDS_LABEL";

   @RBEntry("Edit criteria")
   @RBComment("Label for Edit action in saved search & search history display.")
   public static final String PRIVATE_CONSTANT_497 = "EDIT_LABEL";

   @RBEntry("Keyword")
   @RBComment("Being used in the search history display page in search history link.")
   public static final String KEYWORD_FOR_SEARCH_HISTORY_LINK = "KEYWORD_FOR_SEARCH_HISTORY_LINK";

   @RBEntry("Type")
   @RBComment("Being used in the search history display page in search history link.")
   public static final String TYPE_FOR_SEARCH_HISTORY_LINK = "TYPE_FOR_SEARCH_HISTORY_LINK";

   @RBEntry("Default Option Set View")
   @RBComment("Default Option Set View")
   public static final String PRIVATE_CONSTANT_498 = "OPTIONSET_TABLEVIEW_NAME";

   @RBEntry("Default Option Set Search Table View")
   @RBComment("Default Option Set Search Table View")
   public static final String PRIVATE_CONSTANT_499 = "OPTIONSET_TABLEVIEW_DESC";

   @RBEntry("Option Set  ")
   @RBComment("Option Set ")
   public static final String PRIVATE_CONSTANT_500 = "OPTIONSET_TABLEVIEW_LABEL";

   @RBEntry("OK")
   @RBComment("Label for Ok button in Ext Window for lightbix.")
   public static final String PRIVATE_CONSTANT_501 = "Ok_LABEL";

   @RBEntry("Cancel")
   @RBComment("Label for Cancel button in Ext Window for lightbix.")
   public static final String PRIVATE_CONSTANT_502 = "Cancel_LABEL";

   @RBEntry("Additional Criteria for Parts:")
   @RBComment("Label for Additonal Criteria for Parts")
   public static final String PRIVATE_CONSTANT_503 = "PART_ADDITIONAL_CRITERIA";

   @RBEntry("Save")
   @RBComment("Label for Save button in Ext Window for lightbix.")
   public static final String PRIVATE_CONSTANT_504 = "Save_LABEL";

   @RBEntry("Options")
   @RBComment("Caption text at type picker launch point at search page")
   public static final String TYPE_PICKER_MENU_CAPTION = "TYPE_PICKER_MENU_CAPTION";

   @RBEntry("Add")
   @RBComment("Caption text at type picker launch point at search page")
   public static final String TYPE_PICKER_MENU_ADD_UPDATE = "TYPE_PICKER_MENU_ADD_UPDATE";

   @RBEntry("Restore Defaults")
   @RBComment("Caption text at type picker launch point at search page")
   public static final String TYPE_PICKER_MENU_RESTORE_DEFAULT = "TYPE_PICKER_MENU_RESTORE_DEFAULT";

   @RBEntry("All Types")
   @RBComment("display name for persistable type")
   public static final String ALL_TYPES = "ALL_TYPES";

   @RBEntry("Select Relations")
   @RBComment("label for relations")
   public static final String SELECT_RELATIONS = "SELECT_RELATIONS";

   @RBEntry("Default Choice View")
   @RBComment("Default Choice View")
   public static final String PRIVATE_CONSTANT_505 = "CHOICE_TABLEVIEW_NAME";

   @RBEntry("Default Choice Search Table View")
   @RBComment("Default Choice Search Table View")
   public static final String PRIVATE_CONSTANT_506 = "CHOICE_TABLEVIEW_DESC";

   @RBEntry("Choice ")
   @RBComment("Choice ")
   public static final String PRIVATE_CONSTANT_507 = "CHOICE_TABLEVIEW_LABEL";

   @RBEntry("Could not load saved search as one or more related objects are deleted.")
   @RBComment(" Error message to be shown when user selects a deleted saved query for edit or execute.")
   public static final String SAVED_SEARCH_DELETED_MESSAGE = "SAVED_SEARCH_DELETED_MESSAGE";

   /**
    * special column labels
    **/
   @RBEntry("Name")
   public static final String PACKAGE_NAME = "PACKAGE_NAME";

   @RBEntry("Package Number")
   public static final String PACKAGE_NUMBER = "PACKAGE_NUMBER";

   @RBEntry("Package Version")
   public static final String PACKAGE_VERSION = "PACKAGE_VERSION";

   @RBEntry("All")
   public static final String ALL_VIEW = "ALL_VIEW";

   @RBEntry("All")
   public static final String ALL_VIEW_DESCRIPTION = "ALL_VIEW_DESCRIPTION";

   @RBEntry("Saved Search Name ")
   @RBComment("Label of the saved search name being displayed on advanced search screen.")
   public static final String SAVED_SEARCH_NAME_LABEL = "SAVED_SEARCH_NAME_LABEL";

   @RBEntry("Start a New Search")
   @RBComment("label for start a new search link on the search page")
   public static final String START_A_NEW_SEARCH_LINK = "START_A_NEW_SEARCH_LINK";

   @RBEntry("Save This Search")
   @RBComment("label for save this search link on the search page ")
   public static final String SAVE_THIS_SEARCH_LINK = "SAVE_THIS_SEARCH_LINK";

   @RBEntry("Edit Search Criteria  ")
   @RBComment(" label for edit search criteria link on the search page ")
   public static final String EDIT_SEARCH_CRITERIA_LINK = "EDIT_SEARCH_CRITERIA_LINK";

   @RBEntry("View Search History and Saved Searches")
   @RBComment("Label of the view saved search option")
   public static final String VIEW_SAVED_SEARCH_LABEL = "VIEW_SAVED_SEARCH_LABEL";

   @RBEntry("Choose a type")
   @RBComment("Label of the choose type option")
   public static final String CHOOSE_TYPE_LABEL = "CHOOSE_TYPE_LABEL";

   @RBEntry("All Object Types")
   @RBComment("Label of the all type option")
   public static final String ALL_TYPE_LABEL = "ALL_TYPE_LABEL";

   @RBEntry("Search History and Saved Searches")
   @RBComment("Tab label for history & saved searches page")
   public static final String PRIVATE_CONSTANT_508 = "navigation.historySaved.description";

   @RBEntry("Advanced Search")
   @RBComment("Tab label for Advanced search page")
   public static final String PRIVATE_CONSTANT_509 = "navigation.advanced.description";

   @RBEntry("Classification Search")
   @RBComment("Tab label for Classification search page")
   public static final String PRIVATE_CONSTANT_510 = "navigation.classification.description";

   @RBEntry("Additional Criteria")
   @RBComment("Label for Additional Criteria on search info page for search history and saved searches")
   public static final String ADDITIONAL_CRITERIA_LABEL = "ADDITIONAL_CRITERIA_LABEL";


   @RBEntry("Last performed on")
   @RBComment("Label for Last Performed on search info page for search history and saved searches")
   public static final String LAST_PERFORMED_ON_LABEL = "LAST_PERFORMED_ON_LABEL";


   @RBEntry("Recently visited Context Picker list size")
   @RBComment("Determines maximum number of items in recently visited Context Picker drop-down.")
   public static final String MULTISELECT_CONTEXTPICKER_LIMIT = "MULTISELECT_CONTEXTPICKER_LIMIT";

   @RBEntry("You can choose max {0} contexts from the results. Deselect some of the contexts to proceed. ")
   @RBComment("Alert to be shown for user if selects more contexts than preference set for search page.")
   public static final String MULTISELECT_CONTEXTPICKER_LIMIT_ALERT = "MULTISELECT_CONTEXTPICKER_LIMIT_ALERT";

   @RBEntry("Network Relationships")
   @RBComment("Label for the network realtionship display in search criteria string ")
   public static final String NETWORK_RELATIONSHIP_LABEL = "NETWORK_RELATIONSHIP_LABEL";


   @RBEntry("Network context")
   @RBComment("Label for the network context display in search criteria string ")
   public static final String NETWORK_CONTEXT_LABEL = "NETWORK_CONTEXT_LABEL";


   @RBEntry("Edit Criteria")
   @RBComment("Link for edit criteria on search info page for search history and saved searches")
   public static final String EDIT_CRITERIA_LINK = "EDIT_CRITERIA_LINK";

   @RBEntry("DDL criteria")
   @RBComment("DDL  Criteria label on search info page for search history and saved searches")
   public static final String DDL_CRITERIA_LABEL = "DDL_CRITERIA_LABEL";

   @RBEntry("Related object")
   @RBComment("Related Object label on search info page for search history and saved searches")
   public static final String RELATED_OBJECT_LABEL = "RELATED_OBJECT_LABEL";


   @RBEntry("Archive panel")
   @RBComment("Archive Panel label on search info page for search history and saved searches")
   public static final String ARCHIVE_PANEL_LABEL = "ARCHIVE_PANEL_LABEL";

   @RBEntry("Default Skill View")
   @RBComment("Default Skill View")
   public static final String PRIVATE_CONSTANT_511 = "MPMPSKILL_TABLEVIEW_NAME";

   @RBEntry("Default Skill Search Table View")
   @RBComment("Default Skill Search Table View")
   public static final String PRIVATE_CONSTANT_512 = "MPMPSKILL_TABLEVIEW_DESC";

   @RBEntry("Skill")
   @RBComment("Skill")
   public static final String PRIVATE_CONSTANT_513 = "MPMPSKILL_TABLEVIEW_LABEL";

   @RBEntry("Default Tooling View")
   @RBComment("Default Tooling View")
   public static final String PRIVATE_CONSTANT_514 = "MPMTOOLING_TABLEVIEW_NAME";

   @RBEntry("Default Tooling Search Table View")
   @RBComment("Default Tooling Search Table View")
   public static final String PRIVATE_CONSTANT_515 = "MPMTOOLING_TABLEVIEW_DESC";

   @RBEntry("Tooling")
   @RBComment("Tooling")
   public static final String PRIVATE_CONSTANT_516 = "MPMTOOLING_TABLEVIEW_LABEL";

   @RBEntry("Default WorkCenter View")
   @RBComment("Default WorkCenter View")
   public static final String PRIVATE_CONSTANT_517 = "MPMWORKCENTER_TABLEVIEW_NAME";

   @RBEntry("Default WorkCenter Search Table View")
   @RBComment("Default WorkCenter Search Table View")
   public static final String PRIVATE_CONSTANT_518 = "MPMWORKCENTER_TABLEVIEW_DESC";

   @RBEntry("WorkCenter")
   @RBComment("WorkCenter")
   public static final String PRIVATE_CONSTANT_519 = "MPMWORKCENTER_TABLEVIEW_LABEL";

   @RBEntry("Default Process Material View")
   @RBComment("Default Process Material View")
   public static final String PRIVATE_CONSTANT_520 = "MPMPROCESSMATERIAL_TABLEVIEW_NAME";

   @RBEntry("Default Process Material Search Table View")
   @RBComment("Default Process Material Search Table View")
   public static final String PRIVATE_CONSTANT_521 = "MPMPROCESSMATERIAL_TABLEVIEW_DESC";

   @RBEntry("Process Material")
   @RBComment("Process Material")
   public static final String PRIVATE_CONSTANT_522 = "MPMPROCESSMATERIAL_TABLEVIEW_LABEL";



   @RBEntry("Save This Search")
   public static final String saveThisSearchProEDesc = "search.saveThisSearchProE.description";

   @RBEntry("Save This Search")
   public static final String saveThisSearchProEToolT = "search.saveThisSearchProE.tooltip";

   @RBEntry("Save This Search")
   public static final String saveThisSearchProETitle = "search.saveThisSearchProE.title";

   @RBEntry("Up to")
   public static final String uoToLabel = "UP_TO_ATTRIBUTE";

   @RBEntry("{0} days ago")
   public static final String daysAgoAttrib = "DAYS_AGO_TO_ATTRIBUTE";

   @RBEntry("{0} days from now")
   public static final String daysFromNowAttrib = "DAYS_FROM_NOW_ATTRIBUTE";

   @RBEntry("More")
   @RBComment("More lable in query builder")
   public static final String moreQBLabel = "MORE_QB_LABEL";


   @RBEntry("Options")
   public static final String options = "OPTIONS";

   @RBEntry("Add Context")
   public static final String addContext = "ADD_CONTEXT";

   @RBEntry("More Options")
   public static final String moreOptions = "MORE_OPTIONS";

   @RBEntry("OK")
   public static final String ok = "OK";

   @RBEntry("Cancel")
   public static final String cancel = "CANCEL";

   @RBEntry("Search In")
   public static final String searchIn = "SEARCH_IN";

   @RBEntry("Membership")
   public static final String membership = "MEMBERSHIP";

   @RBEntry("I am a member of")
   public static final String iAmAMemberOf = "I_AM_A_MEMBER_OF";

   @RBEntry("Are in my organization")
   public static final String areInMyOrganization = "ARE_IN_MY_ORGANIZATION";

   @RBEntry("Please enter a valid numeric value.")
   @RBComment("Msg for invalid numeric entry.")
   public static final String INVALID_NUMBER = "INVALID_NUMBER";

   @RBEntry("New")
   @RBComment("Label of submenu New")
   public static final String new_submenu = "object.new_submenu.description";

   @RBEntry("Add to")
   @RBComment("Label of submenu Add to")
   public static final String add_to_submenu = "object.add_to_submenu.description";

   @RBEntry("Compare")
   @RBComment("Label of submenu Compare")
   public static final String compare_submenu = "object.compare_submenu.description";

   @RBEntry("Process")
   @RBComment("Label of submenu Process")
   public static final String process_submenu = "object.process_submenu.description";

   @RBEntry("Undefined")
   public static final String UNDEFINED_LABEL = "UNDEFINED_LABEL";

   @RBEntry("Select Archive Criteria")
   @RBComment("Label of link to launch archive panel on search page")
   public static final String ARCHIVE_LINK_LEVEL = "ARCHIVE_LINK_LEVEL";

   @RBEntry("None of the selected object support this action")
   @RBComment("Warning messafe to user when invalid objects are selected to perform Email object owners action")
   public static final String INVALID_OBJECT_EMAIL = "INVALID_OBJECT_EMAIL";

   @RBEntry("Few of the selected object do not support this action.Please press ok to continue")
   @RBComment("Warning messafe to user when invalid objects are selected to perform Email object owners action")
   public static final String FEW_INVALID_OBJECT_EMAIL = "FEW_INVALID_OBJECT_EMAIL";


   @RBEntry("{0} cannot be searched with other types.")
   @RBComment("Message alerting the individual type cannot be searched with other types. ")
   public static final String INDIVIDUAL_SEARCH_TYPE = "INDIVIDUAL_SEARCH_TYPE";


   @RBEntry("Alternate Identifier")
   @RBComment("Field name for user record key in Quality People or Places")
   public static final String ALTERNATE_IDENTIFIER = "ALTERNATE_IDENTIFIER";

   @RBEntry("Phone Number")
   @RBComment("Field name for Phone number in Quality People or Places")
   public static final String PHONE_NUMBER = "PHONE_NUMBER";

   @RBEntry("E-mail")
   @RBComment("Field name for E-mail address in Quality People or Places")
   public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";

   @RBEntry("Postal Code")
   @RBComment("Field name for Postal code in Quality People or Places")
   public static final String POSTAL_CODE = "POSTAL_CODE";

   @RBEntry("Save Query: ")
   @RBComment("Saved Search Create Message")
   public static final String SAVED_SEARCH_CREATED = "SAVED_SEARCH_CREATED";

   @RBEntry("Confirmation: Create Successful")
   @RBComment("Saved Search Create Message Title")
   public static final String SAVED_SEARCH_CREATED_TITLE = "SAVED_SEARCH_CREATED_TITLE";

   @RBEntry("Default Workflow Process Template View")
   @RBComment("Default Workflow Process Template View")
   public static final String PRIVATE_CONSTANT_530 = "WFTEMPLATE_TABLEVIEW_NAME";

   @RBEntry("Default Workflow Process Template Search Table View")
   @RBComment("Default Workflow Process Template Search Table View")
   public static final String PRIVATE_CONSTANT_531 = "WFTEMPLATE_TABLEVIEW_DESC";

   @RBEntry("Workflow Process Template")
   @RBComment("Workflow Process Template")
   public static final String PRIVATE_CONSTANT_532 = "WFTEMPLATE_TABLEVIEW_LABEL";
   @RBEntry("ATTENTION: For iterated objects, all iterations of the selected revision will be deleted.")
   @RBComment("If one or more of the selected objects are iterated. All iterations for the selected object revisions will be deleted.")
   public static final String CONFIRM_DELETE = "CONFIRM_DELETE";

   @RBEntry("Default Received Delivery View")
   @RBComment("Default Received Delivery View")
   public static final String PRIVATE_CONSTANT_533 = "RECEIVEDDELIVERY_TABLEVIEW_NAME";

   @RBEntry("Default Received Delivery Search Table View")
   @RBComment("Default Received Delivery Search Table View")
   public static final String PRIVATE_CONSTANT_534 = "RECEIVEDDELIVERY_TABLEVIEW_DESC";

   @RBEntry("Received Delivery")
   @RBComment("Received Delivery")
   public static final String PRIVATE_CONSTANT_535 = "RECEIVEDDELIVERY_TABLEVIEW_LABEL";

   @RBEntry("View Name")
   @RBComment("view name label")
   public static final String PRIVATE_CONSTANT_536 = "VIEW_NAME_LABEL";

   // resources for Clear icon in Structured Enumeration Picker
   @RBEntry("Clear...")
   public static final String PRIVATE_CONSTANT_537 = "search.callSearchPickerClear.description";

   // resources for Clear icon in Structured Enumeration Picker
   @RBEntry("Clear...")
   public static final String PRIVATE_CONSTANT_538 = "search.callSearchPickerClear.tooltip";

   @RBEntry("Number")
   public static final String PRIVATE_CONSTANT_539 = "2";

   @RBEntry("Name")
   public static final String PRIVATE_CONSTANT_540 = "1";

   @RBEntry("Please specify a search criteria")
   @RBComment("Alert shown to the user when empty search is executed with mulitple object types or multiple object types.")
   public static final String MULTI_TYPE_EMPTY_SEARCH_MESSAGE_TITLE = "MULTI_TYPE_EMPTY_SEARCH_MESSAGE_TITLE";

   @RBEntry("The date is either not in the required format dd/MM/yyyy or it is out of range.")
   public static final String DATE_ERROR = "DATE_ERROR";

   @RBEntry("The search did not complete successfully. This can be caused by a problem with the search criteria or because you do not have access to the objects found by this search. Edit the search criteria and try again, or contact your system administrator for further assistance.")
   @RBComment("Message to display to a user when there is there is an invalid search criteria.")
   public static final String INVALID_CRITERIA_ERROR = "INVALID_CRITERIA_ERROR";

   /*
    * ***********************************************************
    * Search in Folder Resources
    *************************************************************
    */
   @RBEntry("Search in selected folder")
   @RBComment("Empty text for the Search in Folder component")
   public static final String SEARCH_IN_FOLDER_PROMPT = "SEARCH_IN_FOLDER_PROMPT";

   @RBEntry("Clear")
   @RBComment("Clear text in Search in Folder component. Reset the text to Empty text string")
   public static final String CLEAR_SEARCH_IN_FOLDER = "CLEAR_SEARCH_IN_FOLDER";

   @RBEntry("Index Server Down")
   @RBComment("Index Server down title")
   public static final String WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEAR_TITLE = "WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEARCH_TITLE";

   @RBEntry("The index search function is not available, an attribute search has been used.  The search results may not contain all of the objects you expected.  Please contact your Windchill system administrator.")
   @RBComment("Index Server down. Fall back to DB Search.")
   public static final String WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEARCH = "WARN_INDEX_SERER_DOWN_FALLBACK_TO_DB_SEARCH";

   /*
    ************************************************************
    * Structured Enumeration Picker Resources
    ************************************************************
    */
   @RBEntry("Classification Tree")
   @RBComment("Title for Classification Tree")
   public static final String CLASSIFICATION_TREE_TITLE = "csm.clfTree.title";

   @RBEntry("Class")
   @RBComment("Title for the column in Classification Tree")
   public static final String CLASSIFICATION_TREE_COLUMN_NAME = "csm.clfTreeColumnName.title";

   // following entries are added as a part of story B-100525
   @RBEntry("Please review selected object types and re-run the search")
   @RBComment("The message to be shown to user when the parent and child types both are selected and parent types should be removed")
   public static final String PARENT_TYPE_SELECTED_MESSAGE = "PARENT_TYPE_SELECTED_MESSAGE";

   @RBEntry("Search cannot be executed for objects in hierarchy.")
   @RBComment("The title to be shown to user when the parent and child types both are selected and parent types should be removed")
   public static final String PARENT_TYPE_SELECTED_MESSAGE_TITLE = "PARENT_TYPE_SELECTED_MESSAGE_TITLE";

   @RBEntry("My Favorite Contexts")
   @RBComment("Message for My Context Display")
   public static final String MY_FAVOURITE_CONTEXT_LBL = "MY_FAVOURITE_CONTEXT_LBL";

   @RBEntry("Examples for Query Mode Syntax")
   @RBComment("Examples for query mode syntax")
   public static final String EXAMPLES_FOR_QUERY_MODE_SYNTAX = "EXAMPLES_FOR_QUERY_MODE_SYNTAX";

   @RBEntry("My Favorite Types")
   @RBComment("Label for My Favorite Type checkbox shown on the search page")
   public static final String  MY_FAVOURITE_TYPE_LBL = "MY_FAVOURITE_TYPE_LBL";

   @RBEntry("Criteria")
   @RBComment("Criteria label for the criteria fieldset on the advanced search page.")
   public static final String CRITERIA_LABEL = "CRITERIA_LBL";

   @RBEntry("Multiple Folder Selection")
   @RBComment("Warning Heading message when My Fav checkbox selected and only folders present inside it.")
   public static final String MULTIPLE_FOLDER_SELECTION_HEADING = "MULTIPLE_FOLDER_SELECTION_HEADING";

   @RBEntry("You can only select one folder at one time.")
   @RBComment("Warning message when My Fav checkbox selected and only folders present inside it.")
   public static final String MULTIPLE_FOLDER_SELECTION_MSG = "MULTIPLE_FOLDER_SELECTION_MSG";

    @RBEntry("in")
    @RBComment("Description has format 'Type in Organization', localizing word 'in' ")
    public static final String SAVE_SEARCH_COMBO_BOX_TOOLTIP_DESCRIPTION = "SAVE_SEARCH_COMBO_BOX_TOOLTIP_DESCRIPTION";

    @RBEntry("Site")
    @RBComment("Site")
    public static final String PRIVATE_CONSTANT_541 = "Site";
    
    /*Adding Secondary Attachments column*/
    @RBEntry("Attachments")
    @RBComment("Label for Secondary Attachments column")
    public static final String SECONDARY_ATTACHMENTS = "SECONDARY_ATTACHMENTS";

    @RBEntry("Tag")
    @RBComment("Label for Tag column")
    public static final String TAG_LABEL = "TAG_LABEL";
    
    @RBEntry("Filter")
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
