package com.inthinc.pro.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.configurator.ProductType;

@XmlRootElement
public class SensitivitySliderValues {
	
	private Integer sensitivityType;
	private Integer sensitivitySubtype;
	private Integer settingID;
	private ProductType productType;
	private Integer minFirmwareVersion;
	private Integer maxFirmwareVersion;
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
	public Integer getSensitivityType() {
		return sensitivityType;
	}
	public void setSensitivityType(Integer sensitivityType) {
		this.sensitivityType = sensitivityType;
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
}
