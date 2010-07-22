package com.inthinc.pro.model.configurator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public class VehicleSetting {
    
    public enum ProductVersion implements BaseEnum{
        
        UNKNOWN(0), TEEN(1), WS820(2), TIWIPRO_R71(3), TIWIPRO_R74(5);
        
        private int code;
        
        private static final Map<Integer, ProductVersion> lookup = new HashMap<Integer, ProductVersion>();
        static
        {
            for (ProductVersion p : EnumSet.allOf(ProductVersion.class))
            {
                lookup.put(p.code, p);
            }
        }
       
        public static ProductVersion valueOf(Integer code)
        {
            return lookup.get(code);
        }

        ProductVersion(int code){
            this.code = code;
        }
        public Integer getCode(){
            
            return code;
        }
        public static EnumSet<ProductVersion> getSet(){
            
            return EnumSet.allOf(ProductVersion.class);
        }
    }
    private Integer vehicleID;
    private Integer deviceID;
    private ProductVersion productVer;
    private Map<Integer, String> desired;
    private Map<Integer, String> actual;
       
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public Integer getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    public ProductVersion getProductVer() {
        return productVer;
    }
    public void setProductVer(ProductVersion productVer) {
        this.productVer = productVer;
    }
    public Map<Integer, String> getDesired() {
        return desired;
    }
    public void setDesired(Map<Integer, String> desired) {
        this.desired = desired;
    }
    public Map<Integer, String> getActual() {
        return actual;
    }
    public void setActual(Map<Integer, String> actual) {
        this.actual = actual;
    }

}
