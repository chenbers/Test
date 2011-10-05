package com.inthinc.pro.configurator.model;

public class SliderKey {
    
    private volatile int hashCode;
    
    // The slider values in the database are uniquely keyed on the following fields.
    private SliderType sliderType;
    private ProductType productType;
    private Integer minFirmwareVersion;
    private Integer maxFirmwareVersion;

    public SliderKey(SliderType sliderType, ProductType productType, Integer minFirmwareVersion, Integer maxFirmwareVersion) {

        this.sliderType = sliderType;
        this.productType = productType;
        this.minFirmwareVersion = minFirmwareVersion;
        this.maxFirmwareVersion = maxFirmwareVersion;
    }
    
    public ProductType getProductType() {
        return productType;
    }
    public Integer getMinFirmwareVersion() {
        return minFirmwareVersion;
    }
    public Integer getMaxFirmwareVersion() {
        return maxFirmwareVersion;
    }
    public SliderType getSliderType() {
        return sliderType;
    }

    public boolean equals(SliderKey otherSliderKey){
        return (sliderType.equals(otherSliderKey.sliderType) &&
                productType.equals(otherSliderKey.productType) &&
                minFirmwareVersion.equals(otherSliderKey.minFirmwareVersion) &&
                maxFirmwareVersion.equals(otherSliderKey.maxFirmwareVersion));       
    }
    @Override
    public boolean equals(Object obj) {
        
        if (! (obj instanceof SliderKey)) return false;
        
        SliderKey otherSliderKey = (SliderKey) obj;
        
        return (sliderType.equals(otherSliderKey.sliderType) &&
                productType.equals(otherSliderKey.productType) &&
                minFirmwareVersion.equals(otherSliderKey.minFirmwareVersion) &&
                maxFirmwareVersion.equals(otherSliderKey.maxFirmwareVersion));
    }
    @Override
    public int hashCode() {
        
        int result = hashCode;
        if(hashCode==0){
            result = 17;
            result = 31*result + sliderType.hashCode();
            result = 31*result + productType.hashCode();
            result = 31*result + minFirmwareVersion;
            result = 31*result + maxFirmwareVersion;
        }
        return result;
    }

}
