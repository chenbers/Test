package com.inthinc.pro.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SliderKey;
import com.inthinc.pro.model.configurator.SliderType;

@XmlRootElement
public class SensitivitySliderValues {
	
    //SensitivitySlider values from the database
    //These four fields uniquely key a set of slider values
    @Column (name="sensitivityType")
    private SliderType sliderType;
	private ProductType productType;
	private Integer minFirmwareVersion;
	private Integer maxFirmwareVersion;
	
	//Some slider values have multiple values
	private Integer sensitivitySubtype;
	//This matches the settingIDs in the settingDefs table
	private Integer settingID;
	@Column (name="fwdCmd")
	private Integer forwardCommand;
	@Column (name="fwdcmdname")
	private String forwardCommandName;
    private List<String> values;
    private Integer defaultValueIndex;
	
    public List<String> getValues() {
        return values;
    }
    public void setValues(List<String> values) {
        this.values = values;
    }
    public Integer getSettingID() {
		return settingID;
	}
	public void setSettingID(Integer settingID) {
		this.settingID = settingID;
	}
	public Integer getSensitivitySubtype() {
		return sensitivitySubtype;
	}
	public void setSensitivitySubtype(Integer sensitivitySubtype) {
		this.sensitivitySubtype = sensitivitySubtype;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
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
	public Integer getForwardCommand() {
		return forwardCommand;
	}
	public void setForwardCommand(Integer forwardCommand) {
		this.forwardCommand = forwardCommand;
	}
    public Integer getDefaultValueIndex() {
        return defaultValueIndex;
    }
    public void setDefaultValueIndex(Integer defaultValueIndex) {
        this.defaultValueIndex = defaultValueIndex;
    }
    public String getForwardCommandName() {
        return forwardCommandName;
    }
    public void setForwardCommandName(String forwardCommandName) {
        this.forwardCommandName = forwardCommandName;
    }
    public SliderType getSliderType() {
        return sliderType;
    }
    public void setSliderType(SliderType sliderType) {
        this.sliderType = sliderType;
    }
    public SliderKey getSliderKey(){
        
        return new SliderKey(sliderType,productType,minFirmwareVersion,maxFirmwareVersion);
    }

}
