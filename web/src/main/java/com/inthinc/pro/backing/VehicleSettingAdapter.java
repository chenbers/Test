package com.inthinc.pro.backing;

import java.util.Map;

import javax.faces.context.FacesContext;

import com.inthinc.pro.model.configurator.ProductType;

public abstract class VehicleSettingAdapter {


    //This is for the different settings that have to be displayed for different products on the Admin->vehicle pages
    //Implementors should just have instance variables/getters/setters for the settings from the vehicle settings record that
    //a customer can change on the vehicle page.
    private Integer     vehicleID;
    private ProductType productType;
    
    protected Map<String,Object> properties;
    
    public VehicleSettingAdapter() {
        super();
        
    }

    protected VehicleSettingAdapter(Integer vehicleID, ProductType productType) {

        this.vehicleID = vehicleID;
        this.productType = productType;
        
    }
    
    public void init(){}
    public Map<String, Object> getProperties() {
        return properties;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }
    public ProductType getProductType(){
        
        return productType;
    }

    public abstract boolean validateSaveItems(FacesContext context, boolean isBatchEdit, Map<String, Boolean> updateField);
}
