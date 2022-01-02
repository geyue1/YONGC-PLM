/**
 * ext.rdc.shiyandagang.model.RDCPartBasicInfoMasterIdentity.java
 * @Author yge
 * 2019年8月19日下午5:18:19
 * Comment 
 */
package ext.rdc.shiyandagang.model;

import java.io.Externalizable;

import wt.fc.IdentificationObject;
import wt.fc.Identified;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import com.ptc.windchill.annotations.metadata.GenAsUnPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

@GenAsUnPersistable(superClass = IdentificationObject.class, interfaces = { Externalizable.class },
properties = {
    @GeneratedProperty(name = "number", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),
    @GeneratedProperty(name = "name", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)) })

public class RDCPartBasicInfoMasterIdentity extends _RDCPartBasicInfoMasterIdentity{
	 static final long serialVersionUID = 1;

	    public static RDCPartBasicInfoMasterIdentity newRDCPartBasicInfoMasterIdentity(String number, String name) throws WTException {
	    	RDCPartBasicInfoMasterIdentity instance = new RDCPartBasicInfoMasterIdentity();
	        instance.initialize(number, name);
	        return instance;
	    }
	    
	    protected void initialize( String number, String name )
	            throws WTException {
	      try {
	         setNumber(number);
	         setName(name);
	      }
	      catch (WTPropertyVetoException wtpve) {
	         throw new WTException(wtpve);
	      }
	   }
	   
	    @Override
	    protected String getKeyClassName() {
	       return RDCPartBasicInfoMasterKey.class.getName();
	    }

	    
	    @Override
	    public String getIdentity() {
	       return getNumber();
	    }

	    @Override
	    protected void setToObject( Identified obj )
	             throws WTException {
	      ((RDCPartBasicInfoMaster) obj).setIdentificationObject(this);
	    }

}
