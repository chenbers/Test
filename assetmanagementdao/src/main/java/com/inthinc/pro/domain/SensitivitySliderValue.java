package com.inthinc.pro.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.inthinc.pro.model.configurator.SliderType;

@Entity
@Table(name="sensitivitySliderValues",
	    uniqueConstraints = {@UniqueConstraint(columnNames={"sensitivityType","sensitivitySubtype", "productType","minFirmwareVersion","maxFirmwareVersion"})})
public class SensitivitySliderValue {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer pk;
	private Integer settingID;
	private SliderType sensitivityType;
	private Integer sensitivitySubtype;
	private Integer productType;
	private Integer minFirmwareVersion;
	private Integer maxFirmwareVersion;
	private Integer fwdCmd;
	private Integer defaultValueIndex;
	private String v0;
	private String v1;
	private String v2;
	private String v3;
	private String v4;
	private String v5;
	private String v6;
	private String v7;
	private String v8;
	private String v9;
	private String v10;
	private String v11;
	private String v12;
	private String v13;
	private String v14;
	private String v15;
	private String v16;
	private String v17;
	private String v18;
	private String v19;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getSettingID() {
		return settingID;
	}
	public void setSettingID(Integer settingID) {
		this.settingID = settingID;
	}
	public SliderType getSensitivityType() {
		return sensitivityType;
	}
	public void setSensitivityType(SliderType sensitivityType) {
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
	public Integer getFwdCmd() {
		return fwdCmd;
	}
	public void setFwdCmd(Integer fwdCmd) {
		this.fwdCmd = fwdCmd;
	}
	public Integer getDefaultValueIndex() {
		return defaultValueIndex;
	}
	public void setDefaultValueIndex(Integer defaultValueIndex) {
		this.defaultValueIndex = defaultValueIndex;
	}
	public String getV0() {
		return v0;
	}
	public void setV0(String v0) {
		this.v0 = v0;
	}
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public String getV3() {
		return v3;
	}
	public void setV3(String v3) {
		this.v3 = v3;
	}
	public String getV4() {
		return v4;
	}
	public void setV4(String v4) {
		this.v4 = v4;
	}
	public String getV5() {
		return v5;
	}
	public void setV5(String v5) {
		this.v5 = v5;
	}
	public String getV6() {
		return v6;
	}
	public void setV6(String v6) {
		this.v6 = v6;
	}
	public String getV7() {
		return v7;
	}
	public void setV7(String v7) {
		this.v7 = v7;
	}
	public String getV8() {
		return v8;
	}
	public void setV8(String v8) {
		this.v8 = v8;
	}
	public String getV9() {
		return v9;
	}
	public void setV9(String v9) {
		this.v9 = v9;
	}
	public String getV10() {
		return v10;
	}
	public void setV10(String v10) {
		this.v10 = v10;
	}
	public String getV11() {
		return v11;
	}
	public void setV11(String v11) {
		this.v11 = v11;
	}
	public String getV12() {
		return v12;
	}
	public void setV12(String v12) {
		this.v12 = v12;
	}
	public String getV13() {
		return v13;
	}
	public void setV13(String v13) {
		this.v13 = v13;
	}
	public String getV14() {
		return v14;
	}
	public void setV14(String v14) {
		this.v14 = v14;
	}
	public String getV15() {
		return v15;
	}
	public void setV15(String v15) {
		this.v15 = v15;
	}
	public String getV16() {
		return v16;
	}
	public void setV16(String v16) {
		this.v16 = v16;
	}
	public String getV17() {
		return v17;
	}
	public void setV17(String v17) {
		this.v17 = v17;
	}
	public String getV18() {
		return v18;
	}
	public void setV18(String v18) {
		this.v18 = v18;
	}
	public String getV19() {
		return v19;
	}
	public void setV19(String v19) {
		this.v19 = v19;
	}
	public Integer getSensitivitySubtype() {
		return sensitivitySubtype;
	}
	public void setSensitivitySubtype(Integer sensitivitySubtype) {
		this.sensitivitySubtype = sensitivitySubtype;
	}
	@Override
	public String toString() {
		return "SensitivitySliderValue [pk=" + pk + ", settingID=" + settingID
				+ ", sensitivityType=" + sensitivityType
				+ ", sensitivitySubtype=" + sensitivitySubtype
				+ ", productType=" + productType + ", minFirmwareVersion="
				+ minFirmwareVersion + ", maxFirmwareVersion="
				+ maxFirmwareVersion + ", fwdCmd=" + fwdCmd
				+ ", defaultValueIndex=" + defaultValueIndex + ", v0=" + v0
				+ ", v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + ", v4=" + v4
				+ ", v5=" + v5 + ", v6=" + v6 + ", v7=" + v7 + ", v8=" + v8
				+ ", v9=" + v9 + ", v10=" + v10 + ", v11=" + v11 + ", v12="
				+ v12 + ", v13=" + v13 + ", v14=" + v14 + ", v15=" + v15
				+ ", v16=" + v16 + ", v17=" + v17 + ", v18=" + v18 + ", v19="
				+ v19 + "]";
	}
	
}
