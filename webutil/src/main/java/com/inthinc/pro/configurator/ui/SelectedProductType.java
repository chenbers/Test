package com.inthinc.pro.configurator.ui;

import com.inthinc.pro.model.configurator.ProductType;

public class SelectedProductType {
	
	private ProductType productType;
	@SuppressWarnings("unused")
	private Integer productTypeCode;

	public SelectedProductType() {
		
		productType = ProductType.TIWIPRO;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public void setProductTypeCode(Integer productTypeCode) {
	   productType = ProductType.valueOfByCode(productTypeCode);
	}

	public Integer getProductTypeCode() {
		return productType.getCodes()[0];
	}
}
