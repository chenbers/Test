package com.inthinc.pro.backing.configurator;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.ui.AccountSelectItems;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;

public class ConfigurationSelectionBean extends UsesBaseBean{

	private Integer accountID;
	private String selectedGroupId;
    @SuppressWarnings("unused")
	private Integer productTypeCode; 
	private ProductType productType;
    private TreeNavigationBean treeNavigationBean;
    private AccountSelectItems accountSelectItems;
    
	public void init(){
    	
    	selectedGroupId = null;
		productType = ProductType.TIWIPRO_R74;
		accountID = getBaseBean().getAccountID();
    }
	
    public AccountSelectItems getAccountSelectItems() {
		return accountSelectItems;
	}

	public void setAccountSelectItems(AccountSelectItems accountSelectItems) {
		this.accountSelectItems = accountSelectItems;
	}
    
    public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
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
	
	public Object refreshNavigationTree(){
		
		treeNavigationBean.refresh(accountID);
		return null;
	}
}
