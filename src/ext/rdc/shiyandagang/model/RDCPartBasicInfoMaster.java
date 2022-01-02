/**
 * ext.rdc.shiyandagang.model.RDCPartBasicInfoMaster.java
 * @Author yge
 * 2019年8月19日下午5:13:53
 * Comment 
 */
package ext.rdc.shiyandagang.model;

import wt.enterprise.Master;
import wt.fc.IdentificationObject;
import wt.fc.UniquelyIdentified;
import wt.iba.value.IBAHolder;
import wt.inf.container.WTContained;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Changeable;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.IconProperties;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.StringCase;
import com.ptc.windchill.annotations.metadata.SupportedAPI;
import com.ptc.windchill.annotations.metadata.TableProperties;

@GenAsPersistable(superClass=Master.class, interfaces={UniquelyIdentified.class, IBAHolder.class,WTContained.class},
properties={
@GeneratedProperty(name="number", type=String.class, supportedAPI=SupportedAPI.PUBLIC,      
   constraints=@PropertyConstraints(stringCase=StringCase.UPPER_CASE, changeable=Changeable.VIA_OTHER_MEANS, required=true),
   columnProperties=@ColumnProperties(index=true, columnName="PartNumber")),
@GeneratedProperty(name="name", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
   constraints=@PropertyConstraints(changeable=Changeable.VIA_OTHER_MEANS, upperLimit=4000,required=true))
,@GeneratedProperty(name="typeInthid", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
constraints=@PropertyConstraints(upperLimit=4000, required=false))
},
tableProperties = @TableProperties(tableName = "RDCPartBasicInfoMaster"),
iconProperties=@IconProperties(standardIcon="wtcore/images/part_master.gif", openIcon="wtcore/images/part_master.gif")
)

public class RDCPartBasicInfoMaster extends _RDCPartBasicInfoMaster{

	static final long serialVersionUID = 1;
	
	 protected void setIdentificationObject( RDCPartBasicInfoMasterIdentity identity ) {
	      number = identity.getNumber();
	      name = identity.getName();
	   }

	   public IdentificationObject getIdentificationObject()
	            throws WTException {
	      return RDCPartBasicInfoMasterIdentity.newRDCPartBasicInfoMasterIdentity(getNumber(), getName());
	   }

	   @Override
	   public String getIdentity() {
	      return getNumber() + " - " + getName();
	   }

	   public static RDCPartBasicInfoMaster newRDCPartBasicInfoMaster()
	            throws WTException {
		   RDCPartBasicInfoMaster instance = new RDCPartBasicInfoMaster();
	      instance.initialize();
	      return instance;
	   }

}
