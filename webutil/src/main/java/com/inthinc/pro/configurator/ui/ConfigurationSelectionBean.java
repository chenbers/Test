package com.inthinc.pro.configurator.ui;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.configurator.model.Configuration;
import com.inthinc.pro.model.configurator.VehicleSetting;

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
	//Helpers
	public Map<Integer,String> getVehicleDifferences(Integer vehicleID, Configuration selectedConfiguration){
	    
	    Map<Integer, String> differenceMap = new HashMap<Integer, String>();
	    VehicleSetting vehicleSetting = getVehicleSettings().getVehicleSettings().get(vehicleID);
	    
	    Map<Integer, String> combinedVehicleSettings = vehicleSetting.getCombinedSettings();
	    Map<Integer, String> configurationSettings  = selectedConfiguration.getLatestDesiredValues();
	    
	    for (Integer settingID : configurationSettings.keySet()){
	        
	        if ((configurationSettings.get(settingID) != null) &&
	             !configurationSettings.get(settingID).equals(combinedVehicleSettings.get(settingID))){
	            
	            differenceMap.put(settingID, configurationSettings.get(settingID));
	        }
	    }
	    return differenceMap;
	}
}
