/**
 * ext.yongc.plm.part.link.WGJPartLink.java
 * @Author yge
 * 2017年11月2日下午4:31:22
 * Comment 
 */
package ext.yongc.plm.part.link;

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
			},
			  tableProperties = @TableProperties(tableName = "WGJPartLink")
			)
	
	public class WGJPartLink extends _WGJPartLink{
		static final long serialVersionUID = 1;
		public static WGJPartLink newWGJPartLink()throws WTException {
			final WGJPartLink instance = new WGJPartLink();
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
