/**
 * ext.yongc.plm.part.link.PartMaterialLink.java
 * @Author yge
 * 2017年9月17日下午6:55:29
 * Comment 
 */
package ext.yongc.plm.part.link;

import java.io.Externalizable;

import wt.fc.Item;
import wt.inf.container.WTContained;
import wt.type.TypeDefinitionInfo;
import wt.type.Typed;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.StringCase;
import com.ptc.windchill.annotations.metadata.TableProperties;

	@GenAsPersistable(
			superClass = Item.class, 
			interfaces = { WTContained.class,Externalizable.class,Typed.class },
			properties = {
				@GeneratedProperty(name = "roleAida2a2", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true), columnProperties = @ColumnProperties(index = true, columnName = "roleAida2a2")),
				@GeneratedProperty(name = "roleBmasterida2a2", type = String.class, constraints = @PropertyConstraints(upperLimit = 50,required = true),columnProperties = @ColumnProperties(index = true, columnName = "roleBmasterida2a2")),
				@GeneratedProperty(name = "roleAnumber", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)), 
				@GeneratedProperty(name = "roleAname", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)),
				@GeneratedProperty(name = "roleBnumber", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)), 
				@GeneratedProperty(name = "roleBname", type = String.class,constraints = @PropertyConstraints(upperLimit = 50, required = true)),
			},
			  tableProperties = @TableProperties(tableName = "PartMaterialLink")
			)
	
	public class PartMaterialLink extends _PartMaterialLink{
		static final long serialVersionUID = 1;
		public static PartMaterialLink newPartMaterialLink()throws WTException {
			final PartMaterialLink instance = new PartMaterialLink();
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
//		public void checkAttributes() throws InvalidAttributeException {
//			super.checkAttributes();
//			try {
//				nameValidate(name);
//			} catch (WTPropertyVetoException wtpve) {
//				throw new InvalidAttributeException(wtpve);
//			}
//		}

}
