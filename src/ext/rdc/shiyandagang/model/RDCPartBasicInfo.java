/**
 * ext.rdc.shiyandagang.model.RDCPartBasicInfo.java
 * @Author yge
 * 2019年8月19日下午4:55:24
 * Comment 
 */
package ext.rdc.shiyandagang.model;

import wt.content.FormatContentHolder;
import wt.enterprise.RevisionControlled;
import wt.fc.ObjectReference;
import wt.folder.Foldered;
import wt.folder.history.Movable;
import wt.iba.value.IBAHolder;
import wt.identity.DisplayIdentification;
import wt.inf.container.WTContained;
import wt.lifecycle.LifeCycleManaged;
import wt.type.TypeDefinitionInfo;
import wt.type.TypeManaged;
import wt.type.Typed;
import wt.util.LocaleIndependentMessage;
import wt.util.LocalizableMessage;
import wt.util.WTException;
import wt.viewmarkup.Viewable;

import com.ptc.windchill.annotations.metadata.DerivedProperty;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.IconProperties;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.SupportedAPI;
import com.ptc.windchill.annotations.metadata.TableProperties;


@GenAsPersistable(superClass = RevisionControlled.class, interfaces = { WTContained.class, Viewable.class, Typed.class, TypeManaged.class, LifeCycleManaged.class, FormatContentHolder.class,
    IBAHolder.class, DisplayIdentification.class, Foldered.class },
    properties = {
            @GeneratedProperty(name = "rdcGroup", type = String.class, constraints = @PropertyConstraints(upperLimit = 100),javaDoc = "组"),
            @GeneratedProperty(name = "subGroup", type = String.class, constraints = @PropertyConstraints(upperLimit = 100),javaDoc = "分组"),
            @GeneratedProperty(name = "partBasicNumber", type = String.class, constraints = @PropertyConstraints(upperLimit = 100),javaDoc = "基础编号"),
            @GeneratedProperty(name = "partBasicName", type = String.class, constraints = @PropertyConstraints(upperLimit = 500),javaDoc = "部件名称"),
            @GeneratedProperty(name = "englishName", type = String.class, constraints = @PropertyConstraints(upperLimit = 500),javaDoc = "英文名称"),
            @GeneratedProperty(name = "rdcClassification", type = String.class, constraints = @PropertyConstraints(upperLimit = 500),javaDoc = "专业分类"),
            @GeneratedProperty(name = "field", type = String.class, constraints = @PropertyConstraints(upperLimit = 500),javaDoc = "领域"),
            @GeneratedProperty(name = "mark", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000),javaDoc = "备注"),
            
            @GeneratedProperty(name = "str0", type = String.class, javaDoc = "预留字段1",constraints = @PropertyConstraints(upperLimit = 4000)),
            @GeneratedProperty(name = "str1", type = String.class, javaDoc = "预留字段2",constraints = @PropertyConstraints(upperLimit = 4000)),
            @GeneratedProperty(name = "str2", type = String.class, javaDoc = "预留字段3",constraints = @PropertyConstraints(upperLimit = 4000))
            
         }, 
foreignKeys = { @GeneratedForeignKey(
                    foreignKeyRole = @ForeignKeyRole(name = "master", type = RDCPartBasicInfoMaster.class, supportedAPI = SupportedAPI.PUBLIC, cascade = false, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "iteration", supportedAPI = SupportedAPI.PUBLIC, cascade = false)) 
          },

derivedProperties = { @DerivedProperty(name = "number", derivedFrom = "master>number", supportedAPI = SupportedAPI.PUBLIC),
            @DerivedProperty(name = "name", derivedFrom = "master>name", supportedAPI = SupportedAPI.PUBLIC) 
          },
    tableProperties = @TableProperties(tableName = "RDCPartBasicInfo"), iconProperties = @IconProperties(standardIcon = "wtcore/images/part_master.gif", openIcon = "wtcore/images/part_master.gif"))

public class RDCPartBasicInfo extends _RDCPartBasicInfo{
	static final long serialVersionUID = 1;
	
	 public static RDCPartBasicInfo newRDCPartBasicInfo() throws WTException {
		 RDCPartBasicInfo instance = new RDCPartBasicInfo();
	        instance.initialize();
	        return instance;
	    }

	    @Override
	    public String getFlexTypeIdPath() {
	        // TODO Auto-generated method stub
	        return null;
	    }

	    @Override
	    public TypeDefinitionInfo getTypeDefinitionInfo() {
	        // TODO Auto-generated method stub
	        return null;
	    }

	    @Override
	    public Object getValue() {
	        // TODO Auto-generated method stub
	        return null;
	    }

	    @Override
	    public void setValue(String arg0, String arg1) {
	        // TODO Auto-generated method stub

	    }

	    @Override
	    public LocalizableMessage getDisplayIdentifier() {
	        StringBuilder displayIdentifier = new StringBuilder();
	        displayIdentifier.append(getNumber());
	        displayIdentifier.append(",");
	        displayIdentifier.append(getName());
	        displayIdentifier.append(",");
	        displayIdentifier.append(getVersionIdentifier().getValue()).append(".").append(getIterationIdentifier().getValue());
	        return new LocaleIndependentMessage(displayIdentifier.toString());
	    }

	    @Override
	    public String getIdentity() {
	        return getDisplayIdentifier().toString();
	    }
	    
}
