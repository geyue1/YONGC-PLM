package com.ptc.windchill.partslink;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.WTException;

import com.ptc.core.components.rendering.guicomponents.BooleanInputComponent.BooleanVal;

// TODO: Auto-generated Javadoc
/**
 * The Interface PartslinkConstants.
 */
public interface PartslinkConstants {

    /** The Constant RESOURCE. */
    public static final String RESOURCE = partslinkClientResource.class.getName();

    /** The Constant logger. */
    public static final Logger logger = LogR.getLogger(PartslinkConstants.class.getName());

    /** To check classification render access */
    public static final String CLASSIFICATION_SEARCH_ACTION_NAME = "classificationSearch";

    /*
     * All view names
     */
    /**
     * The Interface Views.
     */
    public interface Views {

        /** The Constant START_URL. */
        public static final String START_URL = "/ptc1/websearch/start";

        /** The Constant BROWSE_PAGE_VIEW. */
        public static final String BROWSE_PAGE_VIEW = "/WEB-INF/jsp/partslink/start.jsp";

        /** The Constant REFINE_PAGE_VIEW. */
        public static final String REFINE_PAGE_VIEW = "/WEB-INF/jsp/partslink/refine.jsp";

        /** The Constant RESULT_PAGE_VIEW. */
        public static final String RESULT_PAGE_VIEW = "/netmarkets/jsp/partslink/results.jsp";

        /** The Constant REDIRECT_PAGE_VIEW. */
        public static final String REDIRECT_PAGE_VIEW = "/WEB-INF/jsp/partslink/redirect.jsp";

        /** The Constant FIND_SIMILAR__PAGE_VIEW. */
        public static final String FIND_SIMILAR_PAGE_VIEW = "/WEB-INF/jsp/partslink/findSimilar.jsp";

        /** The Constant FIND_SIMILAR__PAGE_VIEW. */
        public static final String NO_CLASSIFICATION_ACCESS_VIEW = "/WEB-INF/jsp/partslink/actionProfile.jsp";

    }

    /**
     * All Request parameter names.
     */
    public interface RequestParameters {

        /** The Constant PARAM_CLASS_INTERNAL_NAME. */
        public static final String PARAM_CLASS_INTERNAL_NAME = "class";

        /** The Constant PARAM_OLD_CLASS_INTERNAL_NAME. */
        public static final String PARAM_OLD_CLASS_INTERNAL_NAME = "oldclass";

        /** The Constant PARAM_SHOW_IMAGES. */
        public static final String PARAM_SHOW_IMAGES = "showImages";

        /** The Constant PARAM_NAMESPACE. */
        public static final String PARAM_NAMESPACE = "ns";

        /** The Constant PARAM_OBJECT_TYPE. */
        public static final String PARAM_OBJECT_TYPE = "objType";

        /** The Constant PARAM_EXECUTE_SEARCH. */
        public static final String PARAM_EXECUTE_SEARCH = "executeSearch";

        /** The Constant PARAM_ACTION. */
        public static final String PARAM_ACTION = "action";

        /** The constant part ID **. */
        public static final String PARAM_PART_OID = "oid";

        /** The constant Search count **. */
        public static final String PARAM_SEARCH_COUNT = "count";

        /** The Constant PARAM_FREEFORM_TEXT. */
        public static final String PARAM_FREEFORM_TEXT = "freeFormSearchBox";

        /** The Constant SELECTED_SOURCING_CONTEXT. */
        public static final String SELECTED_SOURCING_CONTEXT = "selectedSourcingContext";

        /** The Constant PARAM_FREEFORM_TEXT. */
        public static final String CSM_SUMA_PART_TYPES = "partType";

        /** The Constant PARAM_OBJECT_REF. Used for findSimilar link. */
        public static final String PARAM_OBJECT_REF = "objectRef";

    }

    /**
     * The Interface RequestAttributes.
     */
    public interface RequestAttributes {

        /** The Constant FREE_FORM_PARSE_ERROR. */
        public static final String FREEFORM_PARSE_ERROR = "parseError";

        /** The Constant FREE_FORM_NO_RECORDS. */
        public static final String FREEFORM_NO_RECORDS = "noRecords";
    }

    /**
     * All Session Attributes names.
     *
     */
    public interface SessionAttributes {

        /** The Constant SESSION_ATTR_LAST_VIEWED_BROWSE_NODE. */
        public static final String SESSION_ATTR_LAST_VIEWED_BROWSE_NODE = "browseNode";
    }

    /**
     * All Model IDS.
     */
    public interface Model_IDS {

        /** The Constant BROWSE_MODEL. */
        public static final String BROWSE_MODEL = "browse";

        /** The Constant PROPERTY_MODEL. */
        public static final String PROPERTY_MODEL = "properties";

        /** The Constant REFINE_MODEL. */
        public static final String REFINE_MODEL = "refine";

        /** The Constant RESULT_MODEL. */
        public static final String RESULT_MODEL = "result";

        /** The Constant FIND_SIMILAR_MODEL. */
        public static final String FINDSIMILAR_MODEL = "findSimilar";

        /** The Constant VIEW_ALL_MODEL. */
        public static final String VIEW_ALL_MODEL = "viewAll";

        /** The Constant FIND_SIMILAR_MODEL_SESSION. */
        public static final String FIND_SIMILAR_MODEL_SESSION = "findSimilarModelSession";

        /** The Constant FREEFORM_MODEL. */
        public static final String FREEFORM_MODEL = "freeform";
    }

    /**
     * All WT Property Keys.
     */
    public interface PROPERTY_KEYS {
        /** The Constant KEY_PREFIX. */
        public static final String DEFAULT_COLUMN_LIST_PREFIX = "classificationTable.defaultColumns.";

        /** The maximum number of columns*. */
        public static final String MAX_BROWSE_COLUMN_COUNT = "com.ptc.windchill.partslink.maxBrowseColumnCount";

        /** The maximum number of columns*. */
        public static final String DEFAULT_BROWSE_ROOT_NODE = "com.ptc.windchill.partslink.defaultBrowseRootNode";

        /** The Constant FLOATING_POINT_TOLERANCE. */
        public static final String FLOATING_POINT_PRECISION_OFFSET = "com.ptc.windchill.partslink.floatingPointPrecisionOffset";

        /** The Constant REFINE_COUNT_THRESHOLD. */
        public static final String REFINE_COUNT_THRESHOLD = "com.ptc.windchill.partslink.refineCountThreshold";

        /** The Constant ENABLE_COMMON_VALUE_ATTRIBUTE. */
        public static final String ENABLE_COMMON_VALUE_ATTRIBUTE = "com.ptc.windchill.partslink.enableCommonValueAttribute";

        /** Display Name and Number for Access Controlled Objects property. */
        public static final String DISPLAY_NAME_NUMBER_ACL = "com.ptc.windchill.partslink.displayNameNumberOfACLs";

        /** Free Form Search Wild-card enable. */
        public static final String FREE_FORM_WILDCARD_ENABLE = "com.ptc.windchill.partslink.freeform.wildcard.enabled";
    }

    /**
     * Classification Search Service Constants.
     */
    public interface ClfSearchConstants {

        /** The Constant CLF_CORE. */
        public static final String CLF_CORE = "clflib";

        /** The Constant CLF_CORE. */
        public static final String CLF_STRUCTURE_CORE = "clfstructurelib";
    }

    /**
     * The Interface NumericOperators.
     */
    public interface NumericOperators {

        /** The Constant BLANK. */
        public static final String BLANK = " ";

        /** The Constant EQUAL. */
        public static final String EQUAL = "=";

        /** The Constant GREATER_THAN_EQUAL. */
        public static final String GREATER_THAN_EQUAL = ">=";

        /** The Constant LESS_THAN_EQUAL. */
        public static final String LESS_THAN_EQUAL = "<=";

        /** The Constant RANGE. */
        public static final String RANGE = "Range";

        /** The Constant ABSOLUTE. */
        public static final String ABSOLUTE = "Absolute";

        /** The Constant PERCENT. */
        public static final String PERCENT = "Percent";

        /** The Constant MORE_LESS. */
        public static String MORE_LESS = "+/-";
    }

    /**
     * AttributeDataType Data Type Enum.
     */
    public enum AttributeDataType {

        /** The boolean. */
        BOOLEAN,
        /** The timestamp. */
        TIMESTAMP,
        /** The long. */
        LONG,
        /** The floating point. */
        FLOATING_POINT,
        /** The floating point with units. */
        FLOATING_POINT_WITH_UNITS,
        /** The reference. */
        REFERENCE,
        /** The string. */
        STRING,
        /** The hyperlink. */
        HYPERLINK,
        /** The notype. */
        NOTYPE;

        /**
         * Checks if is numeric type.
         *
         * @return true, if is numeric type
         */
        public boolean isNumericType() {
            boolean result = false;
            if (this == LONG || this == FLOATING_POINT ||
                    this == FLOATING_POINT_WITH_UNITS) {
                result = true;
            }
            return result;
        }
    }

    /**
     * TextCompare Type Enum.
     */
    public enum TextCompareType {

        /** The wildcard. */
        WILDCARD,
        /** The startswith. */
        STARTSWITH,
        /** The equals. */
        EQUALS;
    }

    /**
     * Query Type Enum.
     */
    public enum QueryType {

        /** The wildcard. */
        WILDCARD,
        /** The implicit wildcard only. */
        IMPLICIT_WILDCARD_ONLY,
        /** The non wildcard. */
        NON_WILDCARD;

        /**
         * <BR>
         * <BR>
         * <B>Supported API: </B>false
         *
         * Gets the type.
         *
         * @param wildcard
         *            the wildcard
         * @return the type
         */
        public static final QueryType getType(boolean wildcard) {
            return wildcard ? WILDCARD : IMPLICIT_WILDCARD_ONLY;
        }
    }

    /** The Constant FQ_SPCL_CHARS. */
    public static final char[] FQ_SPCL_CHARS = { '+', '-', '!', '(', ')', '{', '}', '[', ']', '^', '"', '~', ':', '\\' };

    /** The Constant FQ_WC_CHARS. */
    public static final char[] FQ_WC_CHARS = { '*', '?' };

    /**
     * The Interface IndexFields.
     */
    public interface IndexFields {

        /** The Constant PTC_CLASSIFICATION. */
        public static final String PTC_CLASSIFICATION = "ptcClassification";

        /** The Constant SOURCING_CONTEXT. */
        public static final String SOURCING_CONTEXT = "sourcingContext";

        /** The Constant SRC_CONTEXT_AND_STATUS_DELIMITER. */
        public static final String SRC_CONTEXT_AND_STATUS_DELIMITER = "#";

        /** The Constant SOURCING_STATUS. */
        public static final String SOURCING_STATUS = "sourcingStatus";

        /** The Constant CSM_SUMA_TYPE. */
        public static final String CSM_SUMA_TYPE = "csmSUMAType";
    }

    /**
     * RefineSearch constants.
     */
    public interface RefineSearch {

        /** The Constant ID_PREFIX_RANGE_HI. */
        public static final String ID_PREFIX_RANGE_HI = "rangeHi_";

        /** The Constant ID_PREFIX_RANGE_LOW. */
        public static final String ID_PREFIX_RANGE_LOW = "rangeLow_";

        /** The Constant ID_PREFIX_RANGE_COL. */
        public static final String ID_PREFIX_RANGE_COL = "rangeCol_";

        /** The Constant ID_PREFIX_UNIT_COL. */
        public static final String ID_PREFIX_UNIT_COL = "unitsCol_";

        /** The Constant ID_PREFIX_OPERATOR. */
        public static final String ID_PREFIX_OPERATOR = "csmSearchOperator_";
    }

    /**
     * FindSimilar constants.
     */
    public interface findSimilar {

        /** The Constant ID_PREFIX_Numeric Modifier. */
        public static final String ID_PREFIX_NUMERIC_MODIFIER = "csmfindSimilarNumericModifier_";

    }

    /**
     * ResultTable constants.
     */
    public interface ResultTable {

        /** The Column ID of Small Thumbnail Icon. */
        public static final String SMALL_THUMBNAIL = "partslink.resultsTable.smallThumbnail";

        /** The Column ID of Find Similar Icon. */
        public static final String FINDSIMILAR_ICON = "partslink.resultsTable.FindSimilarIcon";

        /** The Column ID of SUMA Sourcing Status Icon. */
        public static final String SOURCING_STATUS = "oemPreference";

        /** The Column ID of Revision. */
        public static final String REVISION = "partslink.resultsTable.revision";

        /** The Column ID of View. */
        public static final String VIEW = "partslink.resultsTable.view";

        /** The Constant META_IS_SECURED. */
        public static final String META_IS_SECURED = "isSecured";

        /** The Constant KEY_OBEJCTID. */
        public static final String KEY_OBEJCTID = "objectId";

        /** The Constant KEY_OBID. */
        public static final String KEY_OBID = "obid";

    }

    /**
     * CSS Styles.
     */
    public interface CSSStyles {

        /** The Constant PARTSLINK_PPDATA_STYLE. */
        public static final String PARTSLINK_PPDATA_STYLE = "ppdata";
    }

    /**
     * FreeFormSearch constants.
     */
    public interface FreeFormSearch {

        /** The Constant OMC_INTERNAL_NAME for other matched class internal name. */
        public static final String OMC_INTERNAL_NAME = "omc";

    }

    /**
     * SUMA constants.
     */
    public interface SUMA {

        /** The Constant SOURCING_CONTEXT_CLASSNAME. */
        public static final String SOURCING_CONTEXT_CLASSNAME = "com.ptc.windchill.suma.axl.AXLContext";
    }

    /**
     * Enum constants for Boolean operators.
     */
    public enum BooleanOperators {

        /** The true. */
        TRUE("1"),
        /** The false. */
        FALSE("0");

        /** The value. */
        private String value;

        /**
         * Instantiates a new boolean operators.
         *
         * @param val
         *            the val
         */
        BooleanOperators(String val) {
            this.value = val;
        }

        /**
         * Method to return internal value for a boolean constant.
         *
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * Method to return display value for a boolean constant.
         *
         * @return the display value
         */
        public String getDisplayValue() {
            String displayValue = value;
            try {
                if (value.equals("1")) {
                    displayValue = BooleanVal.TRUE.stringVal(SessionHelper.getLocale());
                } else if (value.equals("0")) {
                    displayValue = BooleanVal.FALSE.stringVal(SessionHelper.getLocale());
                }
            } catch (WTException e) {
                logger.error("Exception occurred while retrieving the display value for a boolean constant", e);
            }

            return displayValue;
        }

    }

    /**
     * Enum constants for the Search types. This can be used to indicate on which page the search is invoked.
     */
    public enum SearchType {

        /** Search fired from freeform search component */
        FREEFORM_SEARCH,

        /** Search fired from refine search page */
        REFINE_SEARCH,

        /** Search fired from find similar search page */
        FINDSIMILAR_SEARCH,

        /** Search fired from browse search component */
        BROWSE_SEARCH,

        /** For cases, like in customization, where the search page may not be defined or none of the above */
        UNSPECIFIED;
    }
}
