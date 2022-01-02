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
package com.ptc.windchill.partslink.client.mvc.dataUtilities;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTException;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultNmObjectUtility;
import com.ptc.netmarkets.model.NmObject;
import com.ptc.netmarkets.model.NmSimpleOid;
import com.ptc.windchill.partslink.model.FindSimilarBean;

public class FindSimilarNMObjectUtililty  extends DefaultNmObjectUtility
{
   private static final Logger LOGGER = LogR.getLogger(FindSimilarNMObjectUtililty.class.getName());

   /**
     * Not supported/implemented.
     * @param datum Ignored.
     * @param mc Ignored.
     * @return null.
     * @throws WTException Not thrown.
     */
 
    /**
     * Gets the nm object for the datum provided.  This handles the case where the datum is an
     * 
     * @param datum The object to get the NmObject for.  Must be an FindSimilarBean  for it to be handled in here.
     *    
     * @param mc The model context.
     * @return NmObject with the constraint definition data.  Will not return null if an FindSimilarBean is passed in.
     *     
     * @throws WTException If there were any errors.
     */
    @Override
    public NmObject getNmObject(final Object datum, final ModelContext mc) throws WTException
    {
    			LOGGER.debug("IN ==> FindSimilarNMObjectUtililty:: getNmObject");
    			
    			NmObject nmObj = new NmObject();
         		NmSimpleOid nsoid = new NmSimpleOid();
         		if(datum instanceof FindSimilarBean)
         		{
         			final FindSimilarBean fndSimilarBean = (FindSimilarBean) datum;
         			nsoid.setInternalName(fndSimilarBean.getAttrId());
                  			
         		}
         		nmObj = NmObject.newNmObject(nsoid);
         		
         		LOGGER.debug("OUT ==> FindSimilarNMObjectUtililty:: getNmObject");
         		
         		return nmObj;
         		
    }

}


