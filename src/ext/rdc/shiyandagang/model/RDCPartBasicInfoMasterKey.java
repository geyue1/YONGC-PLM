/**
 * ext.rdc.shiyandagang.model.RDCPartBasicInfoMasterKey.java
 * @Author yge
 * 2019年8月19日下午5:21:03
 * Comment 
 */
package ext.rdc.shiyandagang.model;

import wt.fc.SemanticKey;
import wt.inf.container.WTContainerNamespace;
import wt.org.OrganizationOwnedIdentificationObjectNamespace;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.OracleTableSize;
import com.ptc.windchill.annotations.metadata.TableProperties;

@GenAsPersistable(
        superClass = SemanticKey.class,
        interfaces = { OrganizationOwnedIdentificationObjectNamespace.class, WTContainerNamespace.class },
        extendable = true,
        tableProperties = @TableProperties(tableName = "RDCPartBasicInfoMasterKey",oracleTableSize = OracleTableSize.HUGE))

public class RDCPartBasicInfoMasterKey extends _RDCPartBasicInfoMasterKey{
	 static final long serialVersionUID = 1;

	    public static RDCPartBasicInfoMasterKey newRDCPartBasicInfoMasterKey(String identity) throws WTException {
	    	RDCPartBasicInfoMasterKey instance = new RDCPartBasicInfoMasterKey();
	        instance.initialize(identity);
	        return instance;
	    }

	    protected void initialize(String identity) throws WTException {
	        super.initialize(identity);
	    }
}
