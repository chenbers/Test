package com.inthinc.pro.backing.configurator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.Configuration;
import com.inthinc.pro.configurator.model.MakeModelYear;
import com.inthinc.pro.configurator.model.VehicleSettings;
import com.inthinc.pro.configurator.model.VehicleSettingsDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.ProductType;

public class ConfigurationApplyBean extends UsesBaseBean{
	
	private VehicleSettingsDAO vehicleSettingsDAO;
	
	private Integer selectedGroupId;
    @SuppressWarnings("unused")
	private Integer productTypeCode; 
	private ProductType productType;
    private TreeNavigationBean treeNavigationBean;
    private String make;
    private String model;
    private String year;
    private List<SelectItem> makeList;
    private List<SelectItem> modelList;
    private List<SelectItem> yearList;
    private MakeModelYear makeModelYear;
    private VehicleSettings vehicleSettings;
    private Integer applyToConfigurationID;
	private Integer selectedVehicleID;
    
//    private List<Integer> vehicleSelectionSource;
//    private List<Integer> vehicleSelectionTarget;
//    
//    private List<Integer> filteredVehicleSelectionSource = new ArrayList<Integer>();
//    private List<Integer> filteredVehicleSelectionTarget = new ArrayList<Integer>();
        
	public Integer getSelectedVehicleID() {
		return selectedVehicleID;
	}
	public void setSelectedVehicleID(Integer selectedVehicleID) {
		this.selectedVehicleID = selectedVehicleID;
	}
	public void setVehicleSettingsDAO(VehicleSettingsDAO vehicleSettingsDAO) {
		this.vehicleSettingsDAO = vehicleSettingsDAO;
	}
//	public List<Integer> getFilteredVehicleSelectionSource() {
//		return filteredVehicleSelectionSource;
//	}
//
//	public List<Integer> getFilteredVehicleSelectionTarget() {
//		return filteredVehicleSelectionTarget;
//	}
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
		makeList = new ArrayList<SelectItem>();
		modelList = new ArrayList<SelectItem>(); 
		yearList =  new ArrayList<SelectItem>();
		vehicleSettings = new VehicleSettings();
    }
    public Integer getApplyToConfigurationID() {
		return applyToConfigurationID;
	}
	public void setApplyToConfigurationID(Integer applyToConfigurationID) {
		this.applyToConfigurationID = applyToConfigurationID;
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
	public void makeChanged(ValueChangeEvent event){
		 
		make = (String)event.getNewValue();
		 
	    yearList.clear();
		
	    modelList = makeModelYear.getModels(make);
	     
	}
	public void modelChanged(ValueChangeEvent event){
		 
		model = (String)event.getNewValue();
		 
		yearList = makeModelYear.getYears(make, model);
		 
	}
	public void yearChanged(ValueChangeEvent event){
		 
		 year = (String)event.getNewValue();
		 
    }
	public List<Integer> getVehicleList(){
		 
		 return makeModelYear.getVehicleIDs(make, model, year);
		 
	 }
//    public List<Integer> getVehicleSelectionSource() {
//		return vehicleSelectionSource;
//	}
//    public void setVehicleSelectionSource(List<Integer> vehicleSelectionSource) {
//		this.vehicleSelectionSource = vehicleSelectionSource;
//	}
//	public List<Integer> getSelectedVehicles() {
//		return vehicleSelectionSource;
//	}
//	public void setSelectedVehicles(List<Integer> selectedVehicles) {
//		this.vehicleSelectionSource = selectedVehicles;
//	}
//	   public List<Integer> getVehicleSelectionTarget() {
//			return vehicleSelectionTarget;
//	}
//	public void setVehicleSelectionTarget(List<Integer> vehicleSelectionTarget) {
//		this.vehicleSelectionTarget = vehicleSelectionTarget;
//	}
    public Object applySettingsToTargetVehicles(Configuration selectedConfiguration){
    	
       	for(Integer vehicleID : getVehicleList()){
    		
			vehicleSettingsDAO.updateVehicleSettings(vehicleID, selectedConfiguration.getLatestDesiredValues(), 
													 getBaseBean().getProUser().getUser().getUserID(), 
													 selectedConfiguration.getReason());
    	}
       	return null;
    }

	public Object prepareVehicleSelection(ProductType productType/*,List<Integer> vehicleSource, List<Integer> vehicleTarget*/) {

		this.productType = productType;
//		vehicleSelectionSource = new ArrayList<Integer>(vehicleSource);
//		vehicleSelectionTarget = new ArrayList<Integer>();
//       	for(Integer vehicleID : vehicleTarget){
//    		
//       		vehicleSelectionTarget.add(vehicleID);
//       		vehicleSelectionSource.remove(vehicleID);
//    	}
//		filteredVehicleSelectionSource = new ArrayList<Integer>(vehicleSource);
//		filteredVehicleSelectionTarget = new ArrayList<Integer>();
//       	for(Integer vehicleID : vehicleTarget){
//    		
//       		filteredVehicleSelectionTarget.add(vehicleID);
//       		filteredVehicleSelectionSource.remove(vehicleID);
//    	}
		return null;
	}
    public Object updateVehicle(Configuration selectedConfiguration){
    	
		vehicleSettingsDAO.updateVehicleSettings(selectedVehicleID, selectedConfiguration.getLatestDesiredValues(), 
				 getBaseBean().getProUser().getUser().getUserID(), 
				 selectedConfiguration.getReason());

		return null;
    }
    public Object updateVehicle(Integer vehicleID, Configuration selectedConfiguration){
    	
		vehicleSettingsDAO.updateVehicleSettings(vehicleID, selectedConfiguration.getLatestDesiredValues(), 
				 getBaseBean().getProUser().getUser().getUserID(), 
				 selectedConfiguration.getReason());

		return null;
    }


}
