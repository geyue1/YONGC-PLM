package com.ptc.windchill.csm.client.mvc.builders;

import wt.util.WTException;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;

@ComponentBuilder("clf.tree")
public class ClassificationTreeBuilder extends AbstractTreeBuilder {

	private static final String CLASSIFICATION_TREE_HELP_KEY = "WCClassReuseClassTree";

	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {

		TableConfig table = (TableConfig)super.buildComponentConfig(arg0);

		ColumnConfig actionColumn = getComponentConfigFactory().newColumnConfig(
				DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
		actionColumn.setActionModel("csm_clfnodetree_row_toolbar");
		table.addComponent(actionColumn);
		table.setActionModel("csm_clfnodetree_actions_toolbar");
		table.setHelpContext(CLASSIFICATION_TREE_HELP_KEY); // Added as part of Story : B-108624 /Help links: Map the help URL provided by Pubs in the actual UI

		return table;
	}

	public String getDataUtilityId(String columnId) {
		return "clfTreeNode";
	}
}
