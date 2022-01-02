/* bcwti
 *
 * Copyright (c) 2012 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package com.ptc.core.lwc.server;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.ColumnProperties;

/**
 * Represents a layout element that represents a group of attributes within a layout.  
 *
 * <BR><BR><B>Supported API: </B>false
 * <BR><BR><B>Extendable: </B>false
 *
 * @version   1.0
 **/
@GenAsPersistable(superClass=LWCLayoutComponentDefinition.class, 
   foreignKeys={   
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="style", type=com.ptc.core.lwc.server.LWCGroupDisplayStyle.class, columnProperties = @ColumnProperties(columnName="B")),
      myRole=@MyRole(name="theLWCGroupDefinition", cardinality=Cardinality.ONE))
   }, depthLock=4)
public abstract class LWCGroupDefinition extends _LWCGroupDefinition {
   static final long serialVersionUID = 1;
}
