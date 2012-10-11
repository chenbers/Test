package com.inthinc.pro.model;

import com.inthinc.pro.model.configurator.ProductType;

public class VehicleIdentifiers {
    Integer vehicleID;
    ProductType productType;
    
    public VehicleIdentifiers() {
        
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public ProductType getProductType() {
        return productType;
    }
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    
        

}
