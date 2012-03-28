package com.inthinc.pro.backing;

import java.util.Map;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.util.MessageUtil;

public class UnknownEditableVehicleSettings extends EditableVehicleSettings {

    @Override
	public ProductType getProductType() {
		return ProductType.UNKNOWN;
	}

	@Override
	public String getProductDisplayName() {
    	return MessageUtil.getMessageString(ProductType.UNKNOWN.name());
	}

	public UnknownEditableVehicleSettings() {
    }

	@Override
	public void dealWithSpecialSettings(VehicleView vehicle,
			VehicleView batchItem, Map<String, Boolean> updateField, Boolean isBatchEdit) {
	}

}
