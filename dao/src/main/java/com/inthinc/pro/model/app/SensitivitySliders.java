package com.inthinc.pro.model.app;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivitySlider;
import com.inthinc.pro.model.configurator.SliderKey;
import com.inthinc.pro.model.configurator.SliderType;

public class SensitivitySliders {

    private static final Logger logger = Logger.getLogger(SensitivitySliders.class);
    private Map<SliderKey,SensitivitySlider> sensitivitySliders;
    private ConfiguratorDAO     configuratorDAO;
    
    public Map<SliderKey,SensitivitySlider> getSensitivitySliders() {
        return sensitivitySliders;
    }

    public void init(){
        //Get the sensitivity values table data and build a map of sliders
        List<SensitivitySliderValues> sensitivitySliderValuesList = configuratorDAO.getSensitivitySliderValues();
        if ((sensitivitySliderValuesList != null) && (!sensitivitySliderValuesList.isEmpty())){
            buildSliders(sensitivitySliderValuesList);
        }
    } 
    public void buildSliders(List<SensitivitySliderValues> sensitivitySliderValuesList){
        sensitivitySliders = new HashMap<SliderKey, SensitivitySlider>();
        for (SensitivitySliderValues sensitivitySliderValues : sensitivitySliderValuesList){
            logger.debug(sensitivitySliderValues.toString());
            if(ignoreThisSetting(sensitivitySliderValues)){
            	logger.debug("ignored");
            	continue;
            }
            
            addSettingToSlider(sensitivitySliderValues);
        }
    }
    private boolean ignoreThisSetting(SensitivitySliderValues sensitivitySliderValues){
        //Ignore setting 1225 which is already included in the hard bump multi part settings for tiwipro and shouldn't be in there anyway
        return EnumSet.of(ProductType.TIWIPRO_R71,ProductType.TIWIPRO_R74).contains(sensitivitySliderValues.getProductType()) && 
                        sensitivitySliderValues.getSettingID()==1225;
    }
    private void addSettingToSlider(SensitivitySliderValues sensitivitySliderValues){
        SensitivitySlider slider = getSlider(sensitivitySliderValues.getSliderKey());
        slider.addSetting(sensitivitySliderValues);
    }
    private SensitivitySlider getSlider(SliderKey sliderKey){
        
        SensitivitySlider slider = sensitivitySliders.get(sliderKey);
        if (slider == null){
             slider = new SensitivitySlider(sliderKey);
             sensitivitySliders.put(sliderKey, slider);
        }
        return slider;
    }
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    
    public SensitivitySlider getSensitivitySlider(SliderType sliderType, ProductType productType, Integer minFirmwareVersion, Integer maxFirmwareVersion){
        
        SliderKey sliderKey = new SliderKey(sliderType,productType,minFirmwareVersion,maxFirmwareVersion);
        
        return sensitivitySliders.get(sliderKey);
    }
}
