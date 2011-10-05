package com.inthinc.pro.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.configurator.model.ProductType;
import com.inthinc.pro.configurator.model.SensitivitySliderValues;
import com.inthinc.pro.domain.settings.SensitivitySliderValue;
import com.inthinc.pro.service.SensitivitySliderSettingService;

@Component
public class SensitivitySliderHelper {
    @Autowired
	private SensitivitySliderSettingService sensitivitySliderSettingService;

	public List<SensitivitySliderValues> getSensitivitySliderValues() {
		List<SensitivitySliderValue> sensitivitySliderValues = sensitivitySliderSettingService.getSensitivitySliderSettings();
		//Testing
		for(SensitivitySliderValue sensitivitySliderValue : sensitivitySliderValues){
			System.out.println(sensitivitySliderValue.toString());
		}
		List<SensitivitySliderValues> convertedSensitivitySliderValues= convertSensitivitySliderValues(sensitivitySliderValues);
		return convertedSensitivitySliderValues;
	}

	private List<SensitivitySliderValues> convertSensitivitySliderValues(List<SensitivitySliderValue> sensitivitySliderValues){
		List<SensitivitySliderValues> convertedSensitivitySliderValues = new ArrayList<SensitivitySliderValues>();
		for(SensitivitySliderValue sensitivitySliderValue : sensitivitySliderValues){
			SensitivitySliderValues convertedSensitivitySliderValue = convertSensitivitySliderValue(sensitivitySliderValue);
			convertedSensitivitySliderValues.add(convertedSensitivitySliderValue);
		}
		return convertedSensitivitySliderValues;
	}
	private SensitivitySliderValues convertSensitivitySliderValue(SensitivitySliderValue sensitivitySliderValue){
		SensitivitySliderValues sensitivitySliderValues = new SensitivitySliderValues();
		
		sensitivitySliderValues.setDefaultValueIndex(sensitivitySliderValue.getDefaultValueIndex());
		String[] ignoreProperties={"forwardCommandName","productType","values"};
		BeanUtils.copyProperties(sensitivitySliderValue,sensitivitySliderValues,ignoreProperties);
		sensitivitySliderValues.setForwardCommandName(null);
		sensitivitySliderValues.setProductType(convertProductType(sensitivitySliderValue.getProductType()));
		sensitivitySliderValues.setValues(getSliderValues(sensitivitySliderValue));
		return sensitivitySliderValues;
	}
	private ProductType convertProductType(Integer productType){
		return ProductType.getProductTypeFromVersion(productType);
	}
	private List<String> getSliderValues(SensitivitySliderValue sensitivitySliderValue){
		List<String> sliderValues = new ArrayList<String>();
		sliderValues.add(sensitivitySliderValue.getV0());
		sliderValues.add(sensitivitySliderValue.getV1());
		sliderValues.add(sensitivitySliderValue.getV2());
		sliderValues.add(sensitivitySliderValue.getV3());
		sliderValues.add(sensitivitySliderValue.getV4());
		sliderValues.add(sensitivitySliderValue.getV5());
		sliderValues.add(sensitivitySliderValue.getV6());
		sliderValues.add(sensitivitySliderValue.getV7());
		sliderValues.add(sensitivitySliderValue.getV8());
		sliderValues.add(sensitivitySliderValue.getV9());
		if(sensitivitySliderValue.getV10() != null)
			sliderValues.add(sensitivitySliderValue.getV10());
		if(sensitivitySliderValue.getV11() != null)
			sliderValues.add(sensitivitySliderValue.getV11());
		if(sensitivitySliderValue.getV12() != null)
			sliderValues.add(sensitivitySliderValue.getV12());
		if(sensitivitySliderValue.getV13() != null)
			sliderValues.add(sensitivitySliderValue.getV13());
		if(sensitivitySliderValue.getV14() != null)
			sliderValues.add(sensitivitySliderValue.getV14());
		if(sensitivitySliderValue.getV15() != null)
			sliderValues.add(sensitivitySliderValue.getV15());
		if(sensitivitySliderValue.getV16() != null)
			sliderValues.add(sensitivitySliderValue.getV16());
		if(sensitivitySliderValue.getV17() != null)
			sliderValues.add(sensitivitySliderValue.getV17());
		if(sensitivitySliderValue.getV18() != null)
			sliderValues.add(sensitivitySliderValue.getV18());
		if(sensitivitySliderValue.getV19() != null)
			sliderValues.add(sensitivitySliderValue.getV19());
		return sliderValues;
	}

}
