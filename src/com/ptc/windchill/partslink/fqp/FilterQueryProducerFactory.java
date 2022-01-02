package com.ptc.windchill.partslink.fqp;

import wt.util.InstalledProperties;

import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.model.RefineModel;

/**
 * The FilterQueryProducer Factory.
 */
public class FilterQueryProducerFactory {

    /**
     * Creates a new FilterQueryProducer object.
     * 
     * @param bean
     *            the bean
     * @return the filter query producer
     */
    public FilterQueryProducer createFilterQueryProducer(FQPBean bean, RefineModel refineModel) {

        AbstractFilterQueryProducer fqp = null;

        AttributeDataType attrDataType = bean.getAttrDataType();
        if (attrDataType == AttributeDataType.FLOATING_POINT
                || attrDataType == AttributeDataType.FLOATING_POINT_WITH_UNITS) {
            fqp = new FloatingPointFilterQueryProducer();
        } else if (attrDataType == AttributeDataType.LONG) {
            fqp = new IntegerFilterQueryProducer();
        } else if (attrDataType == AttributeDataType.TIMESTAMP) {
            fqp = new TimeStampFilterQueryProducer();
        } else if (InstalledProperties.isInstalled(InstalledProperties.SUMA)
                && bean.getAttrId().equals(PartslinkConstants.IndexFields.SOURCING_STATUS)) {
            fqp = new SourcingStatusFilterQueryProducer();
        } else if (attrDataType == AttributeDataType.BOOLEAN) {
            fqp = new BooleanFilterQueryProducer();
        } else if (attrDataType == AttributeDataType.HYPERLINK) {
            fqp = new UrlFilterQueryProducer();
        }
        else {
            fqp = new StringFilterQueryProducer();
        }

        fqp.setRefineModel(refineModel);

        return fqp;
    }

}
