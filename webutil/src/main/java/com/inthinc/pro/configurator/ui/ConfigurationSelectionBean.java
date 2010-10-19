package com.inthinc.pro.configurator.ui;


public class ConfigurationSelectionBean extends VehicleFilterBean{

	public Integer accountID;
	private AccountSelectItems accountSelectItems;
	
    @Override
	public void init(){
    	
    	super.init();
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

	//Actions
	public Object refreshNavigationTree() {
			
		treeNavigationBean.refresh(accountID);
		clearLists();
		
		return null;
	}
	public Object fetchConfigurations(){
		
		filteredVehicleSettings = filterMakeModelYear();
		
		return "go_configuratorConfigurations";
	}
}
