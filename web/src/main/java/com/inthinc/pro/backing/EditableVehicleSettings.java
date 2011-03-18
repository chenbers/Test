package com.inthinc.pro.backing;

import java.util.Map;

import javax.faces.context.FacesContext;

import com.inthinc.pro.model.configurator.ProductType;

public abstract class EditableVehicleSettings {// extends BaseBean{  cj: removed this extends because it breaks the batch editing


    //This is for the different settings that have to be displayed for different products on the Admin->vehicle pages
    //sub classes should just have instance variables/getters/setters for the settings from the vehicle settings record that
    //a customer can change on the vehicle page.
	
    private Integer     vehicleID;
    private ProductType productType;
    
    private String      ephone;
    
    
    public EditableVehicleSettings() {
        super();
        
    }

    protected EditableVehicleSettings(Integer vehicleID, ProductType productType, String ephone) {

        this.vehicleID = vehicleID;
        this.productType = productType;
        this.ephone = ephone;
    }
    
    public void init(){}
    
    public Integer getVehicleID() {

    	return vehicleID;
    }
    public ProductType getProductType(){
        
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getEphone() {
        return ephone;
    }

    public void setEphone(String ephone) {
        this.ephone = ephone;
    }

    public boolean validateSaveItems(FacesContext context, boolean isBatchEdit, Map<String, Boolean> updateField){
    	
    	return true;
    }
    
    
}
