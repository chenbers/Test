package com.inthinc.pro.model.configurator;

public class SliderKey {
    
    private volatile int hashCode;
    
    // The slider values in the database are uniquely keyed on the following fields.
    private Integer sensitivityType;
    private Integer productType;
    private Integer minFirmwareVersion;
    private Integer maxFirmwareVersion;

    public SliderKey(Integer sensitivityType, Integer productType, Integer minFirmwareVersion, Integer maxFirmwareVersion) {

        this.sensitivityType = sensitivityType;
        this.productType = productType;
        this.minFirmwareVersion = minFirmwareVersion;
        this.maxFirmwareVersion = maxFirmwareVersion;
    }
    
    public Integer getSensitivityType() {
        return sensitivityType;
    }
    public void setSensitivityType(Integer sensitivityType) {
        this.sensitivityType = sensitivityType;
    }
    public Integer getProductType() {
        return productType;
    }
    public void setProductType(Integer productType) {
        this.productType = productType;
    }
    public Integer getMinFirmwareVersion() {
        return minFirmwareVersion;
    }
    public void setMinFirmwareVersion(Integer minFirmwareVersion) {
        this.minFirmwareVersion = minFirmwareVersion;
    }
    public Integer getMaxFirmwareVersion() {
        return maxFirmwareVersion;
    }
    public void setMaxFirmwareVersion(Integer maxFirmwareVersion) {
        this.maxFirmwareVersion = maxFirmwareVersion;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (! (obj instanceof SliderKey)) return false;
        
        SliderKey otherSliderKey = (SliderKey) obj;
        
        return (sensitivityType.equals(otherSliderKey.sensitivityType) &&
                productType.equals(otherSliderKey.productType) &&
                minFirmwareVersion.equals(otherSliderKey.minFirmwareVersion) &&
                maxFirmwareVersion.equals(otherSliderKey.maxFirmwareVersion));
    }
    @Override
    public int hashCode() {
        
        int result = hashCode;
        if(hashCode==0){
            result = 17;
            result = 31*result + sensitivityType;
            result = 31*result + productType;
            result = 31*result + minFirmwareVersion;
            result = 31*result + maxFirmwareVersion;
        }
        return result;
    }
}
