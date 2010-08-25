package com.inthinc.pro.backing.configurator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.MakeModelYear;
import com.inthinc.pro.configurator.model.VehicleSettingsDAO;
import com.inthinc.pro.configurator.ui.AccountSelectItems;
import com.inthinc.pro.model.configurator.ProductType;

public class ConfigurationSelectionBean extends UsesBaseBean{

	private VehicleSettingsDAO vehicleSettingsDAO;
	
	public void setVehicleSettingsDAO(VehicleSettingsDAO vehicleSettingsDAO) {
		this.vehicleSettingsDAO = vehicleSettingsDAO;
	}
	private Integer accountID;
	private String selectedGroupId;
    @SuppressWarnings("unused")
	private Integer productTypeCode; 
	private ProductType productType;
    private TreeNavigationBean treeNavigationBean;
    private AccountSelectItems accountSelectItems;
    private String make;
    private String model;
    private Integer year;
    private List<SelectItem> makeList;
    private List<SelectItem> modelList;
    private List<SelectItem> yearList;
    private MakeModelYear makeModelYearStructure;
    
    
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void init(){
    	
    	selectedGroupId = null;
		productType = ProductType.TIWIPRO_R74;
		accountID = getBaseBean().getAccountID();
		makeList = new ArrayList<SelectItem>();
		modelList = new ArrayList<SelectItem>(); 
		yearList =  new ArrayList<SelectItem>();
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
		makeList.clear();
		modelList.clear();
		yearList.clear();
		
		return null;
	}
	public Object groupChanged(){
		
		getMakes();
		return null;
	}
	private void getMakes(){
		makeList.clear();
		makeModelYearStructure = new MakeModelYear(vehicleSettingsDAO.getVehicles(Integer.parseInt(selectedGroupId)));
		
		makeList = makeModelYearStructure.getMakes();
	}

	 public void makeChanged(ValueChangeEvent event){
		 
		 make = (String)event.getNewValue();
		 
         modelList.clear();
         yearList.clear();
		
         modelList = makeModelYearStructure.getModels(make);
     }
	 public void modelChanged(ValueChangeEvent event){
		 
		 model = (String)event.getNewValue();
		 
         yearList.clear();
		 
         yearList = makeModelYearStructure.getYears(make, model);
     }
	 public List<Integer> getVehicleList(){
		 
		 return makeModelYearStructure.getVehicleIDs(make, model, year);
		 
	 }
}
