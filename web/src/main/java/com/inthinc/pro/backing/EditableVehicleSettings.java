package com.inthinc.pro.backing;

import java.util.Map;

import javax.faces.context.FacesContext;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.model.configurator.ProductType;

public abstract class EditableVehicleSettings {


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
        if (productType == null) return ProductType.UNKNOWN;
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
    public String getProductDisplayName(){
//        if(getProductType().equals(ProductType.UNKNOWN)){
//        	return MessageUtil.getMessageString(getProductType().name());
//        }
        return getProductType().getDescription();

    }
    
    public abstract void dealWithSpecialSettings(VehicleView vehicle, VehicleView batchItem, Map<String, Boolean> updateField, Boolean isBatchEdit);
}
