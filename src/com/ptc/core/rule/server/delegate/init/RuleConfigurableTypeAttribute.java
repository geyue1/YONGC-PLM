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

package com.ptc.core.rule.server.delegate.init;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.Identifier;
import com.ptc.core.meta.common.IllegalFormatException;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.impl.LogicalAttributeFormatException;

import wt.log4j.LogR;
import wt.util.WTContext;
import wt.util.WTException;

/**
 * In memory representation of RuleConfigurableTypeAttribute.properties file.
 *
 * <BR>
 * <BR>
 * <B>Supported API: </B>false <BR>
 * <BR>
 * <B>Extendable: </B>false
 *
 * @version 1.0
 **/

public final class RuleConfigurableTypeAttribute {

   // --- Attribute Section ---
   private static HashMap<TypeIdentifier, AttributeTypeIdentifier[]> attributeCache = new HashMap<TypeIdentifier, AttributeTypeIdentifier[]>();

   // WARNING: Fields placed in this section will not be generated into externalization methods.

   private static HashMap<AttributeTypeIdentifier, String> idCache = new HashMap<AttributeTypeIdentifier, String>();

   private static final String CLASSNAME = RuleConfigurableTypeAttribute.class.getName();

   private final static Logger logger = LogR.getLogger(CLASSNAME);

   static {
      try {
         Properties configurableAttributes = new Properties();
         try ( InputStream in = WTContext.getContext().getResourceAsStream(
                 "/com/ptc/core/rule/server/delegate/init/RuleConfigurableTypeAttribute.properties" ) )
         {
           configurableAttributes.load( in );
         }
         createTypeAttributesCache(configurableAttributes);
         if (Constants.VERBOSE_CONFIGURABLELIST) {
            dump();
         }
      } catch (Throwable t) {
         t.printStackTrace(System.err);
         throw new ExceptionInInitializerError(t);
      }
   }

   /**
    *
    * <BR>
    * <BR>
    * <B>Supported API: </B>false
    *
    * @param Properties
    *           props
    **/

   private static void createTypeAttributesCache(Properties props) throws WTException {

      if (logger.isDebugEnabled()) {
          logger.debug("====> createTypeAttributesCache starts");
      }
      AttributeTypeIdentifier[] atis = null;
      Set<AttributeTypeIdentifier> set = new HashSet<AttributeTypeIdentifier>();
      Iterator entries = props.entrySet().iterator();

      while (entries.hasNext())
      {
         Map.Entry entry = (Map.Entry) entries.next();
         String typeName = (String) entry.getKey();
         TypeIdentifier typeId = (TypeIdentifier) Constants.IDENTIFIER_FACTORY.get(typeName);
         String attributeName = (String) entry.getValue();

         if ((attributeName != null) && (attributeName.length() != 0))
         {
            // get type
            String[] attributeNameArray = attributeName.split(",");
            try
            {
               Identifier[] ids = Constants.IDENTIFIER_FACTORY.get(attributeNameArray, typeId);
               for (int i = 0; i < ids.length; i++) {
                  idCache.put((AttributeTypeIdentifier) ids[i], attributeNameArray[i]);
                  set.add((AttributeTypeIdentifier)ids[i]);
               }
            }
            catch (LogicalAttributeFormatException e)
            {
                logger.error ("createTypeAttributesCache: failed to get attribute (ok if installation)");
            }
            catch (IllegalFormatException e)
            {
               if (logger.isDebugEnabled())
               {
                   logger.debug ("createTypeAttributesCache: no identifiers found for " + attributeName);
               }
               throw new WTException(e);
            }

            atis = (AttributeTypeIdentifier[]) set.toArray(new AttributeTypeIdentifier[set.size()]);

            // put atis into cache
            attributeCache.put(typeId, atis);
         }
         else
         {
            // An empty attribute list for an object type means that the object type is OIR supported
            // but OIR cannot have any UI display constrains for any attribute of this object type.
            attributeCache.put(typeId, null);
         }
      }

      if (logger.isDebugEnabled())
      {
            logger.debug("<==== createTypeAttributesCache ends");
      }
   }

   /**
    *
    * <BR>
    * <BR>
    * <B>Supported API: </B>false
    *
    * @param Properties
    *           props
    * @return AttributeTypeIdentifier[]
    **/

   public static Map getConstrainableAttributes() throws WTException {

      if (logger.isDebugEnabled()) {
         logger.debug("====> getConstrainableAttributes starts");
      }

      return attributeCache;

   }

   /**
    *
    * <BR>
    * <BR>
    * <B>Supported API: </B>false
    *
    * @param String
    *           typeName
    * @return AttributeTypeIdentifier[]
    **/

   public static AttributeTypeIdentifier[] getAttributeTypeIdentifiers(TypeIdentifier typeId) throws WTException {
      return (AttributeTypeIdentifier[]) attributeCache.get(typeId);

   }

   /**
    *
    * <BR>
    * <BR>
    * <B>Supported API: </B>false
    *
    * @param String
    *           typeName
    * @return AttributeTypeIdentifier[]
    **/

   public static AttributeTypeIdentifier[] getAttributeTypeIdentifiers(String typeName) throws WTException {
      return (AttributeTypeIdentifier[]) attributeCache.get(typeName);

   }

   /**
   * Get the name of the attribute for given an AttributeTypeIdentifier
   * <BR>
   * <BR>
   * <B>Supported API: </B>false
   *
   * @param AttributeTypeIdentifier the attribute type identifier
   *           ati
   * @return String the name of the attribute, null if the attribute doesn't exist in the configuration file
   **/

   public static String getID(AttributeTypeIdentifier ati) throws WTException {

      Iterator iterator = idCache.keySet().iterator();

      while (iterator.hasNext()) {
         AttributeTypeIdentifier attrTypeId = (AttributeTypeIdentifier) iterator.next();

         if (ati.isDescendedFrom(attrTypeId)){
            return (String) idCache.get(attrTypeId);
         }
      }

      return null;

   }

   protected static void dump() {
      Iterator entries = attributeCache.entrySet().iterator();
      StringBuffer sb = new StringBuffer();
      sb.append("\n@@@ PRINTING CONTENTS OF ConfiguratbleAttributesCache:\n");
      int j = 0;
      while (entries.hasNext()) {
         Map.Entry entry = (Map.Entry) entries.next();
         TypeIdentifier key = (TypeIdentifier) entry.getKey();
         AttributeTypeIdentifier[] values = (AttributeTypeIdentifier[]) entry.getValue();
         sb.append("@@@ key = " + key + "\n");

         if (values != null) {
            for (int i = 0; i < values.length; i++) {
               if (values[i] != null) {
                  sb.append("@@@ AttributeTypeIdentifier value[ " + j + " ] = " +
                        values[i].toExternalForm() + "\n");
                  ++j;
               } else {
                  sb.append("@@@ AttributeTypeIdentifier value[ " + j + " ] = NULL.\n");
                  ++j;
               }
            }
         } else {
            sb.append("@@@ AttributeTypeIdentifier value = NULL.\n");
         }
      }
      logger.info(sb.toString());
   }

}
