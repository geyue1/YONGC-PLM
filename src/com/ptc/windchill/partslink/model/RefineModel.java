package com.ptc.windchill.partslink.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;

import com.ptc.core.lwc.common.view.TypeDefinitionReadView;

/**
 * The Class RefineModel.
 */
public class RefineModel {

	/** The class internal name. */
	private String classInternalName;

	/** The type def rv. */
	private TypeDefinitionReadView typeDefRV;

	/** The refine bean set. */
	private SortedSet<RefineBean> refineBeanSet;	

	/** The node display name. */
	private String nodeDisplayName;

	/** The exact matches. */
	private long exactMatches;

	/** The is error. */
	private boolean isError = false;

	/** The error message. */
	private String errorMessage;

	/** The refine schematic URL */
	private String refineSchematicURL;

	/** The selected sourcing context. */
	private String sourceContext;

	/** The sourcing context. */
	private Map<String, String> sourceContextValues = new HashMap<String, String>(); 

	/** The locale. */
	private Locale locale;
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the class internal name.
	 *
	 * @return the class internal name
	 */
	public String getClassInternalName() {
		return classInternalName;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the class internal name.
	 *
	 * @param classInternalName the new class internal name
	 */
	public void setClassInternalName(String classInternalName) {
		this.classInternalName = classInternalName;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the type def rv.
	 *
	 * @return the type def rv
	 */
	public TypeDefinitionReadView getTypeDefRV() {
		return typeDefRV;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the type def rv.
	 *
	 * @param typeDefRV the new type def rv
	 */
	public void setTypeDefRV(TypeDefinitionReadView typeDefRV) {
		this.typeDefRV = typeDefRV;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the refine bean set.
	 *
	 * @return the refine bean set
	 */
	public SortedSet<RefineBean> getRefineBeanSet() {
		return refineBeanSet;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the refine bean set.
	 *
	 * @param refineBeanSet the new refine bean set
	 */
	public void setRefineBeanSet(SortedSet<RefineBean> refineBeanSet) {
		this.refineBeanSet = refineBeanSet;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the node display name.
	 *
	 * @return the node display name
	 */
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the node display name.
	 *
	 * @param nodeDisplayName the new node display name
	 */
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the exact matches.
	 *
	 * @return the exact matches
	 */
	public long getExactMatches() {
		return exactMatches;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the exact matches.
	 *
	 * @param exactMatches the new exact matches
	 */
	public void setExactMatches(long exactMatches) {
		this.exactMatches = exactMatches;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Checks if is error.
	 *
	 * @return true, if is error
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the error.
	 *
	 * @param isError the new error
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the schematic URL for node.
	 *
	 * @return the schematic URL for node.
	 */
	public String getRefineSchematicURL() {
		return refineSchematicURL;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the schematic URL for node.
	 *
	 * @param refineSchematicURL the new schematic URL
	 */
	public void setRefineSchematicURL(String refineSchematicURL) {
		this.refineSchematicURL = refineSchematicURL;
	}

	/**
	 * Gets the selected sourcing context.
	 * @return
	 */
	public String getSourceContext() {
		return sourceContext;
	}

	/**
	 * Sets the selected sourcing context.
	 * @param sourceContext
	 */
	public void setSourceContext(String sourceContext) {
		this.sourceContext = sourceContext;
	}

	/**
	 * Gets the sourcing context values
	 * @return
	 */
	public Map<String, String> getSourceContextValues() {
		return sourceContextValues;
	}

	/**
	 * Sets the sourcing context values.
	 * @param sourceContextValues
	 */
	public void setSourceContextValues(Map<String, String> sourceContextValues) {
		this.sourceContextValues = sourceContextValues;
	}

	/**
	 * Gets the locale.
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the locale.
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
