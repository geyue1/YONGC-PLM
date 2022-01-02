package com.ptc.windchill.partslink.fqp;

import com.ptc.windchill.partslink.model.RefineModel;

public abstract class AbstractFilterQueryProducer implements FilterQueryProducer {

	protected RefineModel refineModel;

	public RefineModel getRefineModel() {
		return refineModel;
	}

	public void setRefineModel(RefineModel refineModel) {
		this.refineModel = refineModel;
	}

}
