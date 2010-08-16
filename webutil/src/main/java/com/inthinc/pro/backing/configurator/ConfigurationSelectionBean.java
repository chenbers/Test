package com.inthinc.pro.backing.configurator;

import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;

public class ConfigurationSelectionBean {

    private String selectedGroupId;
    @SuppressWarnings("unused")
	private Integer productTypeCode; 
	private ProductType productType;
    private TreeNavigationBean treeNavigationBean;
    
    public void init(){
    	
    	selectedGroupId = null;
		productType = ProductType.TIWIPRO_R74;
    }
    
	public TreeNavigationBean getTreeNavigationBean() {
		return treeNavigationBean;
	}
	public void setTreeNavigationBean(TreeNavigationBean treeNavigationBean) {
		this.treeNavigationBean = treeNavigationBean;
	}
	public String getSelectedGroupId() {
		return selectedGroupId;
	}
	public void setSelectedGroupId(String selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}
    public Integer getSelectedGroupIdValue() {
        return Integer.parseInt(selectedGroupId);
    }
    public Integer getProductTypeCode() {
        return productType.getCode();
    }
    public void setProductTypeCode(Integer productTypeCode) {
        productType = ProductType.valueOfByCode(productTypeCode);
    }
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
}
