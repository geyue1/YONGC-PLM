package com.ptc.windchill.partslink.client.mvc.builders;

import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.KEY_OBEJCTID;
import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.KEY_OBID;
import static com.ptc.windchill.partslink.PartslinkConstants.ResultTable.META_IS_SECURED;
import static wt.index.IndexConstants.IDENTIFIER_FACTORY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wt.access.AccessControlHelper;
import wt.access.AccessPermission;
import wt.access.NotAuthorizedException;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.fc.collections.CollectionsHelper;
import wt.fc.collections.RefreshSpec;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedHashMap;
import wt.fc.collections.WTKeyedMap;
import wt.inf.container.WTContainerException;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTPropertyVetoException;

import com.infoengine.object.factory.Att;
import com.infoengine.object.factory.Element;
import com.ptc.core.command.common.bean.entity.PrepareEntityCommand;
import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.DisplayOperationIdentifier;
import com.ptc.core.meta.common.IllegalFormatException;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeInstanceIdentifier;
import com.ptc.core.meta.container.common.AttributeContainerSpec;
import com.ptc.core.meta.server.TypeIdentifierUtility;
import com.ptc.core.meta.type.common.TypeInstance;
import com.ptc.core.meta.type.server.TypeInstanceUtility;
import com.ptc.jca.mvc.components.AbstractJcaDataSourceComponentDataBuilder;
import com.ptc.jca.mvc.components.JcaComponentConfig;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.client.feedback.ClientFeedback;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentBuilderType;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentData;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.ComponentResultProcessor;
import com.ptc.mvc.ds.client.ClientFeedbackGenerator;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.csm.client.utils.CSMUtils;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;

/**
 * The Class PartslinkResultsDataBuilder.
 */
@ComponentBuilder(value = "partslink.resultsTable", type = ComponentBuilderType.DATA_ONLY)
public class PartslinkResultsDataBuilder extends AbstractJcaDataSourceComponentDataBuilder
{

	/** The Constant logger. */
	private static final Logger logger = LogR.getLogger(PartslinkResultsDataBuilder.class.getName());

	/** The Constant ATTR_STATE. */
	private static final String ATTR_STATE = "state";

	/** The Constant ATTR_STATE_STATE. */
	private static final String ATTR_STATE_STATE = "state.state";

	/** The Constant ATTR_VERSION. */
	private static final String ATTR_VERSION = "version";

	/** The Constant ATTR_VERSION_ID. */
	private static final String ATTR_VERSION_ID = "versionInfo.identifier.versionId";

	/** The Constant ATTR_ITERATION_ID. */
	private static final String ATTR_ITERATION_ID = "iterationInfo.identifier.iterationId";

	/** The count. Holds count of objects given to processor. */
	private int count = 0;

	/** The access count. */
	private int accessCount = 0;

	/** The no access count. */
	private int noAccessCount = 0;

	// Factory for reference creation.
	/** The ref factory. */
	private ReferenceFactory REF_FACTORY = new ReferenceFactory();

	/** The Constant ACCESS_RESOURCE. */
	private static final String ACCESS_RESOURCE = "wt.access.accessResource";

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ds.AbstractDataSourceComponentDataBuilder#buildRawData(com.ptc.mvc.components.ComponentConfig, com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	protected Object buildRawData(ComponentConfig config, ComponentParams params)
			throws Exception {

		// create processor
		PartslinkSynchronousComponentResultProcessor  processor = new PartslinkSynchronousComponentResultProcessor(config, params);
		buildRawDataAsync(processor);

		// return objects collected in processor
		return processor.getObjects();
	}

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ds.AbstractDataSourceComponentDataBuilder#buildRawDataAsync(com.ptc.mvc.components.ComponentResultProcessor)
	 */
	@Override
	protected void buildRawDataAsync(ComponentResultProcessor  processor) throws Exception {

		long buildCompDataTime = 0;
		if (logger.isDebugEnabled()) {
			buildCompDataTime = System.currentTimeMillis();
		}

		count=0;
		processor.setMultiThreadedProcessingSafe(true);

		JcaComponentConfig config = (JcaComponentConfig) processor.getConfig();
		JcaComponentParams params = (JcaComponentParams) processor.getParams();

		HttpServletRequest request = (HttpServletRequest)params.getHelperBean().getRequest();

		// get attribute list once
		List<String> attributeList = getAttributeList(request);

		try
		{
			PartslinkResultsStream resultsStream = getPartslinkResultStream(request);

			List<Element> searchResults = null;

			while(resultsStream.hasNext()) {
				// get results from stream
				searchResults = resultsStream.nextPage();
				if(searchResults==null) {
					logger.debug("Reached end of results");
					break;
				}
				else {
					logger.trace("searchResults.size="+searchResults.size());
				}

				boolean isOpen = processResults(searchResults, processor, attributeList);
				// break is processor is closed
				if(!isOpen) break;


			} // end while

			if(logger.isDebugEnabled()) {
				int totalCount = accessCount + noAccessCount;
				if(totalCount==0) {
					logger.debug("partslink searchResults is empty.");
				} else {
					logger.debug("access count="+accessCount+", securedCount="+noAccessCount+", total="+totalCount);
					logger.debug("objects given to datasource="+count);
				}			
			}

		} catch (WTException wexp) {
			logger.error("Exception occurred while building classification search result table data");
			throw wexp;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Classification result table component data built in time "+ (System.currentTimeMillis() - buildCompDataTime));
		}

	}

	/**
	 * Process search results.
	 *
	 * @param searchResults the search results
	 * @param processor the processor
	 * @param attributeList the attribute list
	 * @return true, if processor is open
	 * @throws WTException the wT exception
	 * @throws WTPropertyVetoException 
	 */
	protected boolean processResults(List<Element> searchResults, ComponentResultProcessor  processor, List<String> attributeList) throws WTException, WTPropertyVetoException {

		if (searchResults != null) {

			Map<String, Element> elementMap = new HashMap<String, Element>(searchResults.size());

			// bulid versionRefs
			WTArrayList versionRefs = new WTArrayList(100, CollectionsHelper.VERSION_FOREIGN_KEY);

			for (Element eleResult : searchResults) {
				String partKeys = (String) eleResult.getValue(KEY_OBID);
				String objectId = (String) eleResult.getValue(KEY_OBEJCTID).toString();
				elementMap.put(objectId, eleResult);
				try {
					WTReference wtRef = REF_FACTORY.getReference(partKeys);
					versionRefs.add(wtRef);
				}
				catch (Exception e) {
					logger.debug("Exception while getting WTReference from string="+partKeys);
				}                    
			}

			// remove deleted
			removeDeleted(versionRefs);
						
			// check access
			// getAccess returns two lists. One for secured objects and second for secured objects.
			WTArrayList[] accessList = getAccess(versionRefs);

			// process accessible objects
			if(accessList[1]!=null) {
				accessCount = accessCount + accessList[1].size();
				// process and add accessible objects to processor
				boolean isOpen = processAccessibleObjects(processor, accessList[1], attributeList);
				// return false if processor is closed
				if(!isOpen) return false;
			}

			// process non accessible objects
			if(accessList[0]!=null){
				noAccessCount = noAccessCount + accessList[0].size();
				// process and add secured objects
				boolean isOpen = processSecuredObjects(processor, accessList[0], elementMap, attributeList);
				// return false if processor is closed
				if(!isOpen) return false;
			}
		}

		return true;

	}

	/**
	 * Removes the delete.
	 *
	 * @param versionRefs the version refs
	 * @throws WTPropertyVetoException the wT property veto exception
	 * @throws WTException the wT exception
	 */
	private void removeDeleted(WTArrayList versionRefs) throws WTPropertyVetoException, WTException {
		RefreshSpec rs = new RefreshSpec();
		rs.setDisableAccess(true); // do not remove non-accessible objects!
		rs.setForceRefresh(true);
		rs.setDeleteAction(RefreshSpec.REMOVE_DELETE);
		CollectionsHelper.manager.refresh(versionRefs, rs);
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the partslink result stream.
	 *
	 * @param request the request
	 * @return the partslink result stream
	 * @throws WTException the wT exception
	 */
	protected PartslinkResultsStream getPartslinkResultStream(
			HttpServletRequest request) throws WTException {
		return new PartslinkResultsStream(request);
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the attribute list.
	 *
	 * @param request the request
	 * @return the attribute list
	 * @throws NotAuthorizedException the not authorized exception
	 * @throws WTContainerException the wT container exception
	 * @throws WTException the wT exception
	 */
	protected List<String> getAttributeList(HttpServletRequest request) throws NotAuthorizedException, WTContainerException, WTException {

		List<String> attributeList = new ArrayList<String>();

		String clfNode = getParameterValue(request, PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME);
		String objectType = getParameterValue(request, PartslinkConstants.RequestParameters.PARAM_OBJECT_TYPE);

		// add default attributes from property
		addDefaultAttributes(attributeList, objectType);
		
		// add classification attributes
		TypeDefinitionReadView clfNodeReadView = CSMTypeDefHelper.getClassificationTypeDefView(clfNode);

		if (clfNodeReadView == null) {
			throw new WTException("Could not fetch classification node for " + clfNode);
		}

		TypeIdentifier ti = TypedUtility.getTypeIdentifier(objectType);
		Collection<AttributeDefinitionReadView> typeAttributes = clfNodeReadView.getAllAttributes();

		for (AttributeDefinitionReadView attributeDefReadView : typeAttributes) {
			if (!attributeDefReadView.isSystemHidden()) {

				String attributeId = attributeDefReadView.getIBARefView().getLogicalIdentifier();
				if (attributeId == null || attributeId.isEmpty()) {
					AttributeTypeIdentifier ati = attributeDefReadView.getAttributeTypeIdentifier();
					ati = ati.getWithNewContext(ti);
					attributeId = ati.toExternalForm();
				}

				attributeList.add(attributeId);

			}
		}

		return attributeList;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Adds the default attributes.
	 *
	 * @param attributeList the attribute list
	 * @param objectType the object type
	 */
	private void addDefaultAttributes(List<String> attributeList, String objectType) {
		List<String> defaultColumns = PartslinkPropertyModel.getInstance().getDefaultColumns(objectType);        
		attributeList.addAll(defaultColumns);

		// handle special attributes, so related information is fetched in TypeInstance
		if(attributeList.contains(ATTR_STATE)) {
			attributeList.add(ATTR_STATE_STATE);
		}
		if(attributeList.contains(ATTR_VERSION)){
			attributeList.add(ATTR_VERSION_ID);
			attributeList.add(ATTR_ITERATION_ID);			
		}
	}

	/**
	 * Gets the specified parameter value from request.
	 *
	 * @param request the request
	 * @param paramName the parameter name
	 * @return parameter value
	 */
	private String getParameterValue(HttpServletRequest request, String paramName) {
		String paramValue = "";
		Object reqAttribute = request.getAttribute(paramName);

		if (reqAttribute instanceof String) {
			paramValue = (String) reqAttribute;
		}

		if (paramValue == null || "".equals(paramValue.trim())) {
			paramValue = request.getParameter(paramName);
		}

		return paramValue;
	}

	/**
	 * Builds Element for secured(non accessible object) object
	 * Adds secured element to processor.
	 *
	 * @param processor the processor
	 * @param wtArrayList the wt array list
	 * @param elementMap the element map
	 * @param attributeList the attribute list
	 * @return true, if processor is open
	 * @throws WTException the wT exception
	 */
	private boolean processSecuredObjects(ComponentResultProcessor processor, WTArrayList wtArrayList, Map<String, Element> elementMap, List<String> attributeList) throws WTException {

		for(Object obj:wtArrayList){
			ObjectReference wtref = (ObjectReference) obj;
			String objectId = ""+wtref.getObjectId().getId();
			Element element = elementMap.get(objectId);
			Element secElement = getSecuredElement(element, attributeList);
			boolean isOpen = addElementToProcessor(processor, secElement);
			if(!isOpen) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Checks access for given VersionRefs. Returns two lists. One for secured objects and second for accessible objects.
	 * Returns Array of WTArrayList
	 * WTArrayList[0] - WTArrayList of objectRef to which user does not have Read access
	 * WTArrayList[1] - WTArrayList of objectRef to which user has Read access.
	 *
	 * @param versionRefs the version refs
	 * @return the access
	 * @throws WTException the wT exception
	 */
	private WTArrayList[] getAccess(WTArrayList versionRefs) throws WTException {

		WTArrayList noAccessObjects = new WTArrayList(100);
		WTArrayList accessibleObjects = new WTArrayList(100);

		WTKeyedHashMap accessMap = AccessControlHelper.manager.getAccess(versionRefs, AccessPermission.READ);
		Set<WTKeyedMap.WTEntry> entrySet = accessMap.entrySet();
		for(WTKeyedMap.WTEntry e:entrySet) {
			if((Boolean)e.getValue()){
				accessibleObjects.add(e.getKey());
			}else {
				noAccessObjects.add(e.getKey());
			}
		}        

		WTArrayList[] result = new WTArrayList[2];
		if(noAccessObjects.size()>0) {
			result[0] = noAccessObjects;
		}
		if(accessibleObjects.size()>0) {
			result[1] = accessibleObjects;
		}

		return result;
	}

	/**
	 * Adds classification binding attribute to the list of filter attributes (classification attributes)
	 * 
	 * @param typeId
	 * @param attributeList
	 * @return
	 * @throws WTException
	 */
	private List<String> getFilterAttributes(TypeIdentifier typeId,
			List<String> attributeList) throws WTException {
	    
	    // build new list of filterAttributes so the original list will not change
		List<String> filterAttributes = new ArrayList<>(attributeList);
		
		// get binding attributes
		Set<AttributeTypeIdentifier> clfBindingAttributes = new HashSet<AttributeTypeIdentifier>();
		clfBindingAttributes.addAll(CSMUtils.getClassificationConstraintAttributes(typeId));
		
		// add binding attributes to filter attributes
		if (clfBindingAttributes != null && !clfBindingAttributes.isEmpty()) {
			for (AttributeTypeIdentifier thisATI : clfBindingAttributes) {
				if (!filterAttributes.contains(thisATI.toExternalForm())) {
					filterAttributes.add(thisATI.toExternalForm());
				}
			}
		}

		return filterAttributes;
	}

	/**
	 * Brings TypeInstance from ObjectReference
	 * Builds Element form TypeInstance
	 * Adds Element to processor.
	 *
	 * @param processor the processor
	 * @param wtArrayList the wt array list
	 * @param attributeList the attribute list
	 * @return true, if processor is open
	 * @throws WTException the wT exception
	 */
	private boolean processAccessibleObjects(ComponentResultProcessor processor, WTArrayList wtArrayList, List<String> attributeList) throws WTException {

		Map<TypeIdentifier, List<TypeInstanceIdentifier>> typeIdWiseMap = getTypeIdWiseMap(wtArrayList);

		for(Map.Entry<TypeIdentifier, List<TypeInstanceIdentifier>> entry: typeIdWiseMap.entrySet()){
			TypeIdentifier typeId = entry.getKey();

			// get update filter attribute with binding attributes added to it
			// the binding attributes are for the given type, so case of subtype specific binding attribute will get handled.
			List<String> filterAttributes = getFilterAttributes(typeId, attributeList);

			List<TypeInstanceIdentifier> tiiList = entry.getValue();
			TypeInstanceIdentifier[] tiiArr = new TypeInstanceIdentifier[tiiList.size()];
			tiiArr = tiiList.toArray(tiiArr);
			TypeInstance[] tiArr = getTypeInstances(tiiArr, getFilter(typeId, filterAttributes));
			for(TypeInstance ti:tiArr) {
				Element element = convert2Element(ti);
				boolean isOpen = addElementToProcessor(processor, element);
				if(!isOpen) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Adds the element to processor.
	 *
	 * @param processor the processor
	 * @param element the element
	 * @return true, if successful
	 * @throws WTException the wT exception
	 */
	protected boolean addElementToProcessor(ComponentResultProcessor processor,
			Element element) throws WTException {
		if(!processor.isOpen()) {
			return false;
		}				
		processor.addElement(element);
		count++;
		return true;
	}

	/**
	 * Convert TypeInstance to element.
	 *
	 * @param ti the ti
	 * @return the element
	 */
	protected Element convert2Element(TypeInstance ti) {
		// get objectId
		String objectid = getObjectId(ti);		
		if(!objectid.startsWith("OR")) {
			objectid = "OR:"+objectid;
		}

		Element element = ti2Element(ti);

		//SPR 2173848
		addCreatedByAttributeToElement(element);
		addModifiedByAttributeToElement(element);

		// set OR (object reference) as ufid instead of VR. This avoid table component issue.
		element.setUfid(objectid);

		// set fti as null to avoid table component issues
		element.setFti(null);

		// add isSecured false, since this is accessible object
		element.addMeta(META_IS_SECURED, "false");

		element.addAtt(new Att(KEY_OBEJCTID, objectid));

		return element;
	}

	/**
	 * Method to add "Created By" attribute to the input element.
	 * @param element
	 */
	private void addCreatedByAttributeToElement (Element element) {
		Att attribute = element.getAtt("iterationInfo.creator");
		if (attribute != null) {
			element.addAtt(new Att("creator", attribute.getRawValue()));
		}
	}

	/**
	 * Method to add "Modified By" attribute to the input element.
	 * @param element
	 */
	private void addModifiedByAttributeToElement (Element element) {
		Att attribute = element.getAtt("iterationInfo.modifier");
		if (attribute != null) {
			element.addAtt(new Att("modifier", attribute.getRawValue()));
		}
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the object id.
	 *
	 * @param ti the ti
	 * @return the object id
	 */
	protected String getObjectId(TypeInstance ti) {
		return TypeInstanceUtility.getObjectReference(ti).toString();
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Ti2 element.
	 *
	 * @param ti the ti
	 * @return the element
	 */
	protected Element ti2Element(TypeInstance ti) {
		return new Element(ti);
	}

	/**
	 * Returns the type instance for the specified TypeInstanceIdentifier and AttributeContainerSpec.
	 *
	 * @param tiIds the ti ids
	 * @param filter the filter
	 * @return TypeInstance - type instance
	 * @throws IllegalFormatException If there were problems with command format.
	 * @throws WTException If there were problems while getting type instance.
	 */
	private TypeInstance[] getTypeInstances(TypeInstanceIdentifier[] tiIds, AttributeContainerSpec filter)
			throws IllegalFormatException, WTException {
		// get a type instance
		PrepareEntityCommand p_command = new PrepareEntityCommand();

		long typeInstanceTime = 0;
		if (logger.isDebugEnabled()) {
			typeInstanceTime = System.currentTimeMillis();
		}

		try {
			p_command.setSources(tiIds);
			p_command.setFilter(filter);
			p_command.setLocale(SessionHelper.getLocale());
		} catch (WTPropertyVetoException e) {
		    logger.error("Exception occured while getting type instance", e);
		    throw new WTException(e);
		}

		p_command = (PrepareEntityCommand) p_command.execute();
		TypeInstance[] ti = p_command.getResults();

		if (logger.isDebugEnabled()) {
			logger.debug("TypeInstance retrieved in time " + (System.currentTimeMillis() - typeInstanceTime));
		}

		return ti;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the type id wise map.
	 *
	 * @param wtArrayList the wt array list
	 * @return the type id wise map
	 * @throws WTException the wT exception
	 */
	private Map<TypeIdentifier, List<TypeInstanceIdentifier>> getTypeIdWiseMap(
			WTArrayList wtArrayList) throws WTException {

		Map<TypeIdentifier, List<TypeInstanceIdentifier>> result = new HashMap<TypeIdentifier, List<TypeInstanceIdentifier>>();

		wtArrayList.inflate();

		Iterator persistables = wtArrayList.persistableIterator();
		while(persistables.hasNext()) {
			Persistable persistable = (Persistable)persistables.next();

			TypeIdentifier type_id = TypeIdentifierUtility.getTypeIdentifier(persistable);
			TypeInstanceIdentifier tii = TypeIdentifierUtility.getTypeInstanceIdentifier(persistable);

			List<TypeInstanceIdentifier> tiiList = result.get(type_id);
			if(tiiList==null) {
				tiiList = new ArrayList<TypeInstanceIdentifier>();
				result.put(type_id, tiiList);
			}
			tiiList.add(tii);
		}
		return result;
	}

	/**
	 * Returns AttributeContainerSpec for the specified TypeIdentifier and attribute list.
	 *
	 * @param id the id
	 * @param attributeList the attribute list
	 * @return AttributeContainerSpec - Attribute Container Specification
	 * @throws IllegalFormatException If there were problems with command format.
	 * @throws WTException If there were problems while getting Attribute Container Specification.
	 */
	private AttributeContainerSpec getFilter(TypeIdentifier id, List<String> attributeList) throws IllegalFormatException, WTException {
		AttributeContainerSpec filter = null;

		if (filter == null) {
			filter = new AttributeContainerSpec();
			filter.setIncludeArgumentsDefault(true);
			filter.setIncludeConstraintsDefault(true);
			filter.setIncludeDescriptorsDefault(true);
			filter.setNextOperation(new DisplayOperationIdentifier());

			for(String attribute:attributeList) {
				AttributeTypeIdentifier ati = null;
				try{
					ati = (AttributeTypeIdentifier) IDENTIFIER_FACTORY.get(attribute, id);
				} catch(Exception e) {
				    logger.debug("Exception occured while getting filter "+e.getMessage());
				}
				if(ati!=null) {
					filter.putEntry(ati);
				}
				else {
				    // Fix for spr 2226824. If ati is null then get this ati using get(String Identifier_external_form) method of IDENTIFIER_FACTORY
				    try {
				        ati = (AttributeTypeIdentifier) IDENTIFIER_FACTORY.get(attribute);
				        if (logger.isDebugEnabled()) {
				            logger.debug("The ATI from IDENTIFIER_FACTORY "+ati);
				        }
				        if (ati != null) {
				            filter.putEntry(ati);
				        }
				    } catch (Exception ex) {
				            logger.debug("Exception occured while getting filter "+ex.getMessage());
				    }
				}
			}
		}

		return filter;

	}

	/**
	 * Returns Info Engine element with secured information as the value for all the columns specified.
	 *
	 * @param eleResult the ele result
	 * @param attributes the attributes
	 * @return Element - IE Element populated with (Secured Information) as the value
	 * @throws WTException If there were problems creating IE Element with (Secured Information).
	 */
	private Element getSecuredElement(Element eleResult, List<String> attributes) throws WTException {

		long aclEleCreationTime = 0;
		if (logger.isDebugEnabled()) {
			aclEleCreationTime = System.currentTimeMillis();
		}
		Element eleSecured = new Element();

		try {
			eleSecured.addMeta(META_IS_SECURED, "true");

			String strSecuredInformation;
			try {
				strSecuredInformation = (new WTMessage(ACCESS_RESOURCE, wt.access.accessResource.SECURED_INFORMATION, null))
						.getLocalizedMessage(SessionHelper.getLocale());
			} catch (Exception exception) {
				logger.error(exception, exception);
				strSecuredInformation = "(Secured information)";
			}

			if(attributes!=null) {
				for(String attribute: attributes){
					if (PartslinkPropertyModel.getInstance().getDisplayNameNumber()
							&& DescriptorConstants.ColumnIdentifiers.NAME.equalsIgnoreCase(attribute)) {
						eleSecured.addAtt(eleResult.getAtt(DescriptorConstants.ColumnIdentifiers.NAME));
					}
					else if (PartslinkPropertyModel.getInstance().getDisplayNameNumber()
							&& DescriptorConstants.ColumnIdentifiers.NUMBER.equalsIgnoreCase(attribute)) {
						eleSecured.addAtt(eleResult.getAtt(DescriptorConstants.ColumnIdentifiers.NUMBER));
					}
					else if (DescriptorConstants.ColumnIdentifiers.ICON.equalsIgnoreCase(attribute)) {
						eleSecured.addAtt(new Att(DescriptorConstants.ColumnIdentifiers.ICON, ""));
						eleSecured.addAtt(new Att("objectIcon", "netmarkets/images/warning_16x16.gif"));
						eleSecured.addAtt(new Att("objectTooltip", strSecuredInformation));
					}
					else {
						eleSecured.addAtt(new Att(attribute, strSecuredInformation));
					}

				}
			}                        
		} catch (Exception e) {
			logger.error("Exception occurred while populating ie element with secured information.", e);
			throw (new WTException("Exception occurred while populating ie element with secured information."));
		}

		if (logger.isDebugEnabled()) {
			logger.trace("ACL secure information element creation time " + (System.currentTimeMillis() - aclEleCreationTime));
		}

		return eleSecured;
	}


	/**
	 * The Class PartslinkSynchronousComponentResultProcessor.
	 * Collects objects added with addElement
	 */
	class PartslinkSynchronousComponentResultProcessor implements ComponentResultProcessor {

		/** The objects. */
		private List<Object> objects = new ArrayList<Object>();

		/** The config. */
		private ComponentConfig config; 

		/** The params. */
		private ComponentParams params;

		/** The b presorted. */
		private boolean bPresorted = false;

		/** The generator. */
		private ClientFeedbackGenerator generator;

		/** The b multi threaded processing safe. */
		private boolean bMultiThreadedProcessingSafe = false;

		/**
		 * Instantiates a new partslink synchronous component result processor.
		 *
		 * @param config the config
		 * @param params the params
		 */
		protected PartslinkSynchronousComponentResultProcessor(ComponentConfig config, ComponentParams params){
			this.config = config;
			this.params = params;			
		}

		/* (non-Javadoc)
		 * @see wt.fc.ResultProcessor#addElement(java.lang.Object)
		 */
		@Override
		public void addElement(Object obj) throws WTException {
			// add to objects
			objects.add(obj);
		}

		/**
		 * <BR><BR><B>Supported API: </B>false
		 * 
		 * Gets the objects.
		 *
		 * @return the objects
		 */
		public List<Object> getObjects(){
			// return list of collected objects
			return objects;
		}

		/* (non-Javadoc)
		 * @see wt.fc.ResultProcessor#size()
		 */
		@Override
		public int size() {
			return objects.size();
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#getConfig()
		 */
		@Override
		public ComponentConfig getConfig() {
			return config;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#getParams()
		 */
		@Override
		public ComponentParams getParams() {
			return params;
		}

		///////////////// All methods below have dummy implementation /////////////////
		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#getInitialData()
		 */
		@Override
		public ComponentData getInitialData() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#addElements(java.lang.Iterable)
		 */
		@Override
		public void addElements(Iterable<?> elements) throws WTException {
			for(Object obj:elements){
				objects.add(obj);
			}
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#setPresorted(boolean)
		 */
		@Override
		public void setPresorted(boolean preSorted) {
			this.bPresorted = preSorted;			
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#isPresorted()
		 */
		@Override
		public boolean isPresorted() {
			return bPresorted;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#isOpen()
		 */
		@Override
		public boolean isOpen() {
			return true;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#setFeedbackGenerator(com.ptc.mvc.ds.client.ClientFeedbackGenerator)
		 */
		@Override
		public void setFeedbackGenerator(ClientFeedbackGenerator generator) {
			this.generator = generator;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#addFeedback(java.util.List)
		 */
		@Override
		public void addFeedback(List<? extends ClientFeedback> feedback) {
			// TODO Auto-generated method stub
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#setOffSetData(boolean)
		 */
		@Override
		public void setOffSetData(boolean offSetData) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#isOffSetData()
		 */
		@Override
		public boolean isOffSetData() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#getChunkSize()
		 */
		@Override
		public int getChunkSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#isMultiThreadedProcessingSafe()
		 */
		@Override
		public boolean isMultiThreadedProcessingSafe() {
			return bMultiThreadedProcessingSafe;
		}

		/* (non-Javadoc)
		 * @see com.ptc.mvc.components.ComponentResultProcessor#setMultiThreadedProcessingSafe(boolean)
		 */
		@Override
		public void setMultiThreadedProcessingSafe(boolean b) {
			this.bMultiThreadedProcessingSafe = b;
		}

	}
}
