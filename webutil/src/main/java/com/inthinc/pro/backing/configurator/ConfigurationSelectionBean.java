package com.inthinc.pro.backing.configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.MakeModelYear;
import com.inthinc.pro.configurator.model.VehicleSettings;
import com.inthinc.pro.configurator.model.VehicleSettingsDAO;
import com.inthinc.pro.configurator.ui.AccountSelectItems;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class ConfigurationSelectionBean extends UsesBaseBean{

	private VehicleSettingsDAO vehicleSettingsDAO;
	
	public void setVehicleSettingsDAO(VehicleSettingsDAO vehicleSettingsDAO) {
		this.vehicleSettingsDAO = vehicleSettingsDAO;
	}
	private Integer accountID;
	private Integer selectedGroupId;
	private String selectedGroupHierarchy;
    @SuppressWarnings("unused")
	private Integer productTypeCode; 
	private ProductType productType;
    private TreeNavigationBean treeNavigationBean;
    private AccountSelectItems accountSelectItems;
    private String make;
    private String model;
    private String year;
    private List<SelectItem> makeList;
    private List<SelectItem> modelList;
    private List<SelectItem> yearList;
    private MakeModelYear makeModelYear;
    private VehicleSettings vehicleSettings;
    
	public List<SelectItem> getMakeList() {
		return makeList;
	}

	public List<SelectItem> getModelList() {
		return modelList;
	}

	public List<SelectItem> getYearList() {
		return yearList;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void init(){
    	
    	selectedGroupId = null;
		productType = ProductType.TIWIPRO_R74;
		accountID = getBaseBean().getAccountID();
		makeList = new ArrayList<SelectItem>();
		modelList = new ArrayList<SelectItem>(); 
		yearList =  new ArrayList<SelectItem>();
		vehicleSettings = new VehicleSettings();
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
	public Integer getSelectedGroupId() {
		return selectedGroupId;
	}
	public void setSelectedGroupId(Integer selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
		
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
    public String getSelectedGroupHierarchy(){
    	
    	return treeNavigationBean.getSelectedGroupHierarchy(selectedGroupId);
    }
	
	public Object refreshNavigationTree(){
		
		treeNavigationBean.refresh(accountID);
		makeList.clear();
		modelList.clear();
		yearList.clear();
		
		return null;
	}
	public Object groupChanged(){
		
		getGroupsVehicleSettings();
		getMakes();
		return null;
	}
	private void getGroupsVehicleSettings(){
		
	    vehicleSettings.filterSettings(vehicleSettingsDAO.getVehicleSettings(selectedGroupId),productType);
	}

	public List<Integer> getVehicleIDs() {
		return vehicleSettings.getVehicleIDs();
	}

	public void setVehicleSettings(VehicleSettings vehicleSettings) {
		this.vehicleSettings = vehicleSettings;
	}

	private void getMakes(){
		
		List<Vehicle> vehiclesInGroup = vehicleSettingsDAO.getVehicles(selectedGroupId);
		
		vehiclesInGroup = removeUnselectedVehicles(vehiclesInGroup);
		
		makeModelYear = new MakeModelYear(vehiclesInGroup);
		
		modelList.clear();
		yearList.clear();
		makeList = makeModelYear.getMakes();
		make = MakeModelYear.ANY_MAKE;
	}
	public VehicleSettings getVehicleSettings() {
		return vehicleSettings;
	}

	private List<Vehicle> removeUnselectedVehicles(List<Vehicle> vehiclesInGroup){
		
		Iterator<Vehicle> it = vehiclesInGroup.iterator();
		while (it.hasNext()){
			
			Vehicle vehicle = it.next();
			
			if (!vehicleSettings.getVehicleIDs().contains(vehicle.getVehicleID())){
				
				it.remove();
			}
		}
		return vehiclesInGroup;
	}
	public List<VehicleSetting> filterMakeModelYear(){
		
		if (getVehicleList().isEmpty()){
			
			return vehicleSettings.getVehicleSettings();
		}
		List<VehicleSetting> filteredVehicleSettings = new ArrayList<VehicleSetting>();
		for (VehicleSetting vs:vehicleSettings.getVehicleSettings()){
			
			if (getVehicleList().contains(vs.getVehicleID())){
				
				filteredVehicleSettings.add(vs);
			}
		}
		return filteredVehicleSettings;
	}

	 public void makeChanged(ValueChangeEvent event){
		 
		 make = (String)event.getNewValue();
		 
         yearList.clear();
		
         modelList = makeModelYear.getModels(make);
     }
	 public void modelChanged(ValueChangeEvent event){
		 
		 model = (String)event.getNewValue();
		 		 
         yearList = makeModelYear.getYears(make, model);
     }
	 public List<Integer> getVehicleList(){
		 
		 if(makeModelYear == null) return Collections.emptyList();
		 
		 return makeModelYear.getVehicleIDs(make, model, year);
	 }
}
