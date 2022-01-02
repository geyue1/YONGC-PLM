/**
 * ext.yongc.plm.doc.link.DocItemLink.java
 * @Author yge
 * 2017年12月26日上午10:48:52
 * Comment 
 */
package ext.yongc.plm.doc.link;

import java.io.Externalizable;
import java.sql.Timestamp;

import wt.fc.Item;
import wt.inf.container.WTContained;
import wt.type.TypeDefinitionInfo;
import wt.type.Typed;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.TableProperties;

import ext.yongc.plm.part.link.WGJPartLink;

@GenAsPersistable(
		superClass = Item.class, 
		interfaces = { WTContained.class,Externalizable.class,Typed.class },
		properties = {
			@GeneratedProperty(name = "roleAmasterida2a2", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true), columnProperties = @ColumnProperties(index = true, columnName = "roleAida2a2")),
			@GeneratedProperty(name = "roleBida2a2", type = String.class, constraints = @PropertyConstraints(upperLimit = 50,required = true),columnProperties = @ColumnProperties(index = true, columnName = "roleBmasterida2a2")),
			@GeneratedProperty(name = "roleAnumber", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)), 
			@GeneratedProperty(name = "roleAname", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)),
			@GeneratedProperty(name = "roleBnumber", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)), 
			@GeneratedProperty(name = "roleBname", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)),
			@GeneratedProperty(name = "createBy", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = false)),
			@GeneratedProperty(name = "createTime", type = Timestamp.class,constraints = @PropertyConstraints(upperLimit = 50, required = false)),
			@GeneratedProperty(name = "linkType", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = false)),
			@GeneratedProperty(name = "str1", type = String.class,constraints = @PropertyConstraints(upperLimit = 100, required = false)),
			@GeneratedProperty(name = "str2", type = String.class,constraints = @PropertyConstraints(upperLimit = 100, required = false)),
			@GeneratedProperty(name = "str3", type = String.class,constraints = @PropertyConstraints(upperLimit = 100, required = false)),
			@GeneratedProperty(name = "str4", type = String.class,constraints = @PropertyConstraints(upperLimit = 100, required = false)),
			@GeneratedProperty(name = "str5", type = String.class,constraints = @PropertyConstraints(upperLimit = 100, required = false)),
			@GeneratedProperty(name = "stamp1", type = Timestamp.class,constraints = @PropertyConstraints(upperLimit = 50, required = false)),
			@GeneratedProperty(name = "stamp2", type = Timestamp.class,constraints = @PropertyConstraints(upperLimit = 50, required = false)),
		},
		  tableProperties = @TableProperties(tableName = "DocItemLink")
		)
public class DocItemLink extends _DocItemLink{
	static final long serialVersionUID = 1;
	public static final String LINK_TYPE_DeliveryPackage = "DeliveryPackage"; //文件分发包
	
	
	public static DocItemLink newDocItemLink()throws WTException {
		final DocItemLink instance = new DocItemLink();
		instance.initialize();
		return instance;
	}
	@Override
    public String getFlexTypeIdPath() {
		return null;
	}

	@Override
    public TypeDefinitionInfo getTypeDefinitionInfo() {
		return null;
	}

	@Override
    public Object getValue() {
		return null;
	}

	@Override
    public void setValue(String arg0, String arg1) {

	}
}
