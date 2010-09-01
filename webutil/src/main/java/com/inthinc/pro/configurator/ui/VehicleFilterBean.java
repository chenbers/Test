package com.inthinc.pro.configurator.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.VehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleFilterBean extends UsesBaseBean{

	protected VehicleDAO vehicleDAO;
	protected ConfiguratorDAO configuratorDAO;

	protected SelectedProductType selectedProductType;
	protected TreeNavigationBean treeNavigationBean;
	protected Integer selectedGroupId;
	protected VehicleSettings vehicleSettings;
	protected List<VehicleSetting> filteredVehicleSettings;
	protected MakeModelYearSelectItems makeModelYearSelectItems;

	public void init(){
		
    	selectedGroupId = null;
		vehicleSettings = new VehicleSettings();
		makeModelYearSelectItems = new MakeModelYearSelectItems();
	}
	public Object groupChanged() {
		
		List<Integer> vehicleIDs = fetchGroupsVehicleSettingsForProduct();
		fetchMakesModelsYearsForProduct(vehicleIDs);
		
		return null;
	}
	private List<Integer> fetchGroupsVehicleSettingsForProduct() {
		
		//Based on VehicleSettings records for group
	    vehicleSettings.filterSettings(configuratorDAO.getVehicleSettingsByGroupIDDeep(selectedGroupId),selectedProductType.getProductType());
	    
	    return vehicleSettings.getVehicleIDs();
	}
	private void fetchMakesModelsYearsForProduct(List<Integer> selectedVehicleIDs) {
		
		//Based on Vehicle records for group
		makeModelYearSelectItems.createMakeModelYear(filterVehiclesOnProductType(selectedVehicleIDs,vehicleDAO.getVehiclesInGroupHierarchy(selectedGroupId)));
	}
	private List<Vehicle> filterVehiclesOnProductType(List<Integer> selectedVehicleIDs, List<Vehicle> vehiclesInGroup) {
		
		Iterator<Vehicle> it = vehiclesInGroup.iterator();
		while (it.hasNext()){
			
			Vehicle vehicle = it.next();
			
			if (!selectedVehicleIDs.contains(vehicle.getVehicleID())){
				
				it.remove();
			}
		}
		return vehiclesInGroup;
	}
	public List<VehicleSetting> filterMakeModelYear() {
		
		List<Integer> makeModelYearVehicleIDs = makeModelYearSelectItems.getVehicleIDsForSelectedMakeModelYear();
		if (makeModelYearVehicleIDs.isEmpty()){
			
			return vehicleSettings.getVehicleSettings();
		}
		List<VehicleSetting> filteredVehicleSettings = new ArrayList<VehicleSetting>();
		for (VehicleSetting vs:vehicleSettings.getVehicleSettings()){
			
			if (makeModelYearVehicleIDs.contains(vs.getVehicleID())){
				
				filteredVehicleSettings.add(vs);
			}
		}
		return filteredVehicleSettings;
	}
	
	// Value change listeners
	public void makeChanged(ValueChangeEvent event) {
		
		makeModelYearSelectItems.makeChanged((String)event.getNewValue());
	}
	public void modelChanged(ValueChangeEvent event) {
		 
		makeModelYearSelectItems.modelChanged((String)event.getNewValue());
	}
	public void yearChanged(ValueChangeEvent event) {
		 
		 makeModelYearSelectItems.yearChanged((String)event.getNewValue());
	}
	
	//Other stuff
    protected void clearLists(){
    	
    	makeModelYearSelectItems.clearLists();
    }
	//Getters and setters
	
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
	public ProductType getProductType() {
		return selectedProductType.getProductType();
	}

	public void setSelectedProductType(SelectedProductType selectedProductType) {
		this.selectedProductType = selectedProductType;
	}

	public void setProductType(ProductType productType) {
		selectedProductType.setProductType(productType);
	}

	public void setProductTypeCode(Integer productTypeCode) {
		selectedProductType.setProductTypeCode(productTypeCode);
	}

	public Integer getProductTypeCode() {
		return selectedProductType.getProductTypeCode();
	}

	public void setVehicleSettings(VehicleSettings vehicleSettings) {
		this.vehicleSettings = vehicleSettings;
	}
	public MakeModelYearSelectItems getMakeModelYearSelectItems() {
		return makeModelYearSelectItems;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    public VehicleSettings getVehicleSettings() {
		return vehicleSettings;
	}

	public String getSelectedGroupHierarchy() {
		
		return treeNavigationBean.getSelectedGroupHierarchy(selectedGroupId);
	}
	
	public List<Integer> getVehicleIDsForMakeModelYear() {
		 
		 return makeModelYearSelectItems.getVehicleIDsForSelectedMakeModelYear();
	}
	public List<VehicleSetting> getFilteredVehicleSettings() {
		return filteredVehicleSettings;
	}
	
}
