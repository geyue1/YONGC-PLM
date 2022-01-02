/* bcwti
 *
 * Copyright (c) 2010 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package com.ptc.core.lwc.client.factory.dataUtilities;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.components.descriptor.ColumnDescriptor;
import com.ptc.core.components.descriptor.ColumnDescriptorFactory;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.core.lwc.common.PropertyDefinitionConstants;
import com.ptc.core.lwc.common.view.GroupDefinitionReadView;
import com.ptc.core.lwc.common.view.LayoutDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.LWCGroupDefinition;
import com.ptc.core.ui.tableRB;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.misc.NmAction;
import com.ptc.netmarkets.util.misc.NmActionServiceHelper;

/**
 * Data utility to create GUI components for rendering the attributes of
 * AttributeDefinitionReadView objects, the client read view of persisted
 * AttributeDefinition objects.
 *
 * <BR>
 * <BR>
 * <B>Supported API: </B>false <BR>
 * <BR>
 * <B>Extendable: </B>false
 *
 * @version 1.0
 */
public final class LWCGroupDataUtility extends LWCDataUtility
{
   private static final Logger LOGGER = LogR.getLogger(LWCGroupDataUtility.class.getName());
   //temp
   protected GroupDefinitionReadView temporaryGroupDef = null;

   /**
    * TODO Remove this method - it is a hack to get to a GroupDefinitionReadView object
    * until there is a service method to go from the persistable to the ReadView.
    * This method will return the same GroupDefinition no matter what was specified
    * on the URL.
    *
    * This function overrides the parent class' setModelData function so we
    * can get the AttributeDefinitionReadView object in cases where we might have been
    * handed other references to the AttributeDefinition.  This happens in tree views &
    * info pages where the param for the page was an oid string of the persisted
    * AttributeDefinition object.
    *
    * @param component_id
    * @param objects
    * @param mc
    * @throws WTException
    */
   @Override
   public void setModelData(String component_id, java.util.List<?> objects, ModelContext mc) throws WTException
   {
       LOGGER.debug("setModelData: Entering method");

       ArrayList<GroupDefinitionReadView> groupDefArray = new ArrayList<GroupDefinitionReadView>();

       final boolean debug = LOGGER.isDebugEnabled();
       // Convert the array list of Objects to a collection of AttributeDefinitionReadView objects
       for (Object rowObject : objects)
       {
           LWCGroupDefinition groupDef = (LWCGroupDefinition)rowObject;
           // todo: this shouldn't be inflating the type context, the context's reference id should be sufficient
           com.ptc.core.lwc.server.LWCAbstractAttributeTemplate typeDef = groupDef.getContext();

           TypeDefinitionReadView typeDefReadView = null;
           typeDefReadView = TYPE_DEF_SERVICE.getTypeDefView(typeDef.getName());

           Collection<LayoutDefinitionReadView> typeLayouts = typeDefReadView.getAllLayouts();
           if ( debug )
               LOGGER.debug(".....Hacking the get GroupDefReadView. Found " + typeLayouts.size() + " layouts.");

           ArrayList<LayoutDefinitionReadView> typeLayoutsArray = new ArrayList<LayoutDefinitionReadView>(typeLayouts);
           //Just pick the first layout
           LayoutDefinitionReadView layoutReadView = typeLayoutsArray.get(0);

           ArrayList<GroupDefinitionReadView> groupsArray = new ArrayList<GroupDefinitionReadView>(layoutReadView.getAllGroups());
           //Just pick the first group
           GroupDefinitionReadView groupReadView = groupsArray.get(0);
           temporaryGroupDef = groupReadView;

           groupDefArray.add(groupReadView);
       }

       if ( debug )
           LOGGER.debug("... Calling the super method.  GroupDefs stuck in array: " + groupDefArray.size());
       super.setModelData(component_id, groupDefArray, mc);
       LOGGER.debug("setModelData: Leaving method");
   }

  /**
   *
   * Get a label for the column. All columns except the View information column use the super method, The View information
   * needs a column label specified, otherwise the component id is returned and used as the column name for the
   * column freeze name in the filtered view component
   *
   * @param component_id
   *            The component to get a label for
   * @param mc
   *            The model context
   * @return label for the component
   */
   @Override
   public String getLabel(String component_id, ModelContext mc) throws WTException
   {
       if("lwcGroupInfo".equals(component_id)) {
          return WTMessage.getLocalizedMessage("com.ptc.core.ui.tableRB", tableRB.INFO_ACTION, null);
      }
      return super.getLabel(component_id, mc);
   }

   /**
   * Get the column descriptor. For most columns use the default.  For the View Information
   * columns get the IconColumn from the ColumnDescriptorFactory which will specify the correct size
   * and make the column not resizable.
   *
   * @param mc
   *            The model context is used for getting the component descriptor.
   * @return A column descriptor that is suitable for this column.
   */
   @Override
   public ColumnDescriptor getColumnDescriptor(String component_id, ModelContext mc)
   {
      if("lwcGroupInfo".equals(component_id)) {
	     return ColumnDescriptorFactory.getInstance().newIconColumn(mc.getDescriptor());
	  }
      return super.getColumnDescriptor(component_id, mc);
   }

   /**
    * Return a GUI DisplayComponent for attributes of an AttributeDefintionReadView.
    *
    * @param component_id
    *           The id to build a view object for
    * @param datum
    *           The object to extract model data from
    * @param mc
    *           The context in which the component model data is being extracted
    * @return An object that can be sent to a view for rendering
    * @throws wt.util.WTException
    *            If a problem occurs while extracting the data
    */
    @Override
   public Object getDataValue(final String component_id, final Object datum, final ModelContext mc)
       throws WTException
   {
      final TextDisplayComponent returnText = new TextDisplayComponent(component_id);
      if (LOGGER.isDebugEnabled())
      {
          LOGGER.debug("getDataValue: Entering method");
          LOGGER.debug("...component_id: " + component_id + " datum is type: " + datum.getClass().getName());
      }


      Object result = null;
      GroupDefinitionReadView groupDef = temporaryGroupDef;

      //Group internal name
      if ("lwcGroupName".equals(component_id))
      {
          returnText.setValue(groupDef.getName() );
          result = returnText;
      }

      //Group display name
      if ("lwcGroupDisplayName".equals(component_id))
      {
          returnText.setValue(PropertyHolderHelper.getPropertyValue(groupDef, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY));

          result = returnText;
      }

      //Group row
      if ("lwcGroupRow".equals(component_id))
      {
          if ( LOGGER.isDebugEnabled() )
              LOGGER.debug("   ...getRow is: " + groupDef.getRow());
          returnText.setValue((groupDef.getRow() == null ? "" : groupDef.getRow().toString()));
          result = returnText;
      }

      //Group row span
      if ("lwcGroupRowSpan".equals(component_id))
      {
          returnText.setValue((groupDef.getRowspan() == null ? "" :groupDef.getRowspan().toString()));
          result = returnText;
      }

      //Group column
      if ("lwcGroupColumn".equals(component_id))
      {
          returnText.setValue((groupDef.getCol() == null ? "" : groupDef.getCol().toString()));
          result = returnText;
      }

      //Group column span
      if ("lwcGroupColumnSpan".equals(component_id))
      {
          returnText.setValue((groupDef.getRowspan() == null ? "" : groupDef.getColspan().toString()));
          result = returnText;
      }

      //Layout view information action
      if ("lwcLayoutInfo".equals(component_id))
      {
          NmOid oid = null;
          oid = NmOid.newNmOid(groupDef.getOid());
          NmAction lwcLayoutInfoAction = NmActionServiceHelper.service.getAction("lwcType", "layoutInfo");
          lwcLayoutInfoAction.setContextObject(oid);
          result = lwcLayoutInfoAction;
      }

      //Layout groups
      /*if ("lwcLayoutGroups".equals(component_id))
      {
          Collection<GroupDefinitionReadView> layoutGroups = groupDef.getAllGroups();
          GroupDefinitionReadView layoutGroup;
          GUIComponentArray allGroupsArray = new GUIComponentArray();
          for (Iterator<GroupDefinitionReadView> iterator = layoutGroups.iterator(); iterator.hasNext();)
          {
              layoutGroup = iterator.next();
              //TODO this should get the display name
              //returnText.setValue(getStringValueForProperty(layoutDef, PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY, true));
              returnText.setValue(layoutGroup.getName());

              NmOid oid = null;
              oid = NmOid.newNmOid(layoutGroup.getOid());
              NmAction lwcGroupInfoAction = NmActionServiceHelper.service.getAction("lwcType", "groupInfo");
              lwcGroupInfoAction.setContextObject(oid);

              allGroupsArray.addGUIComponent(returnText);
              allGroupsArray.addGUIComponent(new NmActionGuiComponent(lwcGroupInfoAction));
          }

          result = allGroupsArray;
      }*/

      //Layout edit display style
      if ("lwcLayoutEditDisplayStyle".equals(component_id))
      {
          String displayStyle = groupDef.getEditDisplayStyle();
          returnText.setValue(displayStyle);
          result = returnText;
      }

      if (result == null)
      {
         result = TextDisplayComponent.NBSP;
      }

      LOGGER.debug("getDataValue: Leaving method");
      return result;
   }
}