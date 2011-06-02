package com.inthinc.pro.model.app;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.SliderKey;
import com.inthinc.pro.model.configurator.SliderType;

public class SensitivitySliderValuesMapping {

    private static Map<SliderKey,Slider> sensitivitySliders;
    private ConfiguratorDAO     configuratorDAO;
    
    public static Map<SliderKey,Slider> getSensitivitySliders() {
        return sensitivitySliders;
    }

    public ConfiguratorDAO getConfiguratorDAO() {
        return configuratorDAO;
    }

    public void init(){
        //Get the sensitivity values table data and build a map of sliders
        List<SensitivitySliderValues> sensitivitySliderValuesList = configuratorDAO.getSensitivitySliderValues();
        if (!sensitivitySliderValuesList.isEmpty()){
            buildSliders(sensitivitySliderValuesList);
        }
    }        
        //temporarily set up the proper values
        //Hard brake
        //DVX
//        SensitivitySliderValues sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1231);
//        sfcm.setSensitivityType(22);
//        sfcm.setSensitivitySubtype(1);
//        String[] valuesdvx = {"225","140","118","110","103","95","88","80","75","70"};
//        sfcm.setValues(Arrays.asList(valuesdvx));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//
//        //X_ACCEL
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1229);
//        sfcm.setSensitivityType(22);
//        sfcm.setSensitivitySubtype(0);
//        String[]valuesX_ACCEL = {"10","6","6","5","5","5","4","4","4","3"};
//        sfcm.setValues(Arrays.asList(valuesX_ACCEL));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//
//        //Hard turn
//        //DVY
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1228);
//        sfcm.setSensitivityType(23);
//        sfcm.setSensitivitySubtype(1);
//        String[]valuesdvy = {"225","138","118","110","103","95","88","80","70","60"};
//        sfcm.setValues(Arrays.asList(valuesdvy));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//
//        //Y_LEVEL
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1226);
//        sfcm.setSensitivityType(23);
//        sfcm.setSensitivitySubtype(0);
//        String[]valuesY_LEVEL = {"10","6","5","4","4","3","3","3","3","3"};
//        sfcm.setValues(Arrays.asList(valuesY_LEVEL));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//
//        //hard accel
//        //HARD_ACCEL_DELTAV
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1234);
//        sfcm.setSensitivityType(21);
//        sfcm.setSensitivitySubtype(1);
//        String[]valuesHARD_ACCEL_DELTAV = {"300","100","98","95","93","90","85","83","80","75","70","67","61","56","50"};
//        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_DELTAV));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//        
//        //HARD_ACCEL_LEVEL
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1232);
//        sfcm.setSensitivityType(21);
//        sfcm.setSensitivitySubtype(0);
//        String[]valuesHARD_ACCEL_LEVEL = {"4","3","3","3","3","3","3","3","3","3","3","3","3","3","3"};
//        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_LEVEL));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//
//        //Hard verticals
//        //HARDVERT_DMM_PEAKTOPEAK
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1225);
//        sfcm.setSensitivityType(24);
//        sfcm.setSensitivitySubtype(1);
//        String[]valuesHARDVERT_DMM_PEAKTOPEAK = {"400","500","600","700"};
//        sfcm.setValues(Arrays.asList(valuesHARDVERT_DMM_PEAKTOPEAK));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//
//        //rms_level
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1224);
//        sfcm.setSensitivityType(24);
//        sfcm.setSensitivitySubtype(1);
//        String[]valuesRMS_LEVEL = {"400","500","600","700"};
//        sfcm.setValues(Arrays.asList(valuesRMS_LEVEL));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//
//        //SEVERE_HARDVERT_LEVEL
//        sfcm = new SensitivitySliderValues();
//        sfcm.setSettingID(1165);
//        sfcm.setSensitivityType(24);
//        sfcm.setSensitivitySubtype(1);
//        String[]valuesSEVERE_HARDVERT_LEVEL = {"400","500","600","700"};
//        sfcm.setValues(Arrays.asList(valuesSEVERE_HARDVERT_LEVEL));
//        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
//        
//    }

    private static void buildSliders(List<SensitivitySliderValues> sensitivitySliderValuesList){
        
        Map<SliderKey, Slider> sliders = createSliders(sensitivitySliderValuesList);
        setSliderPositionCounts(sliders);
        sensitivitySliders = sliders;
    }
    private static Map<SliderKey, Slider> createSliders(List<SensitivitySliderValues> sensitivitySliderValuesList){
        
        Map<SliderKey, Slider> sliders = new HashMap<SliderKey, Slider>();
        for (SensitivitySliderValues sensitivitySliderValues : sensitivitySliderValuesList){
            if(EnumSet.of(ProductType.TIWIPRO_R74,ProductType.TIWIPRO_R74).contains(sensitivitySliderValues.getProductType()) && 
                   sensitivitySliderValues.getSettingID()==1225)
                continue;
            SliderKey sliderKey = createSliderKey(sensitivitySliderValues);
            Slider slider = findSlider(sliders, sliderKey);
            slider.addSetting(sensitivitySliderValues);
        }
        return sliders;
    }
    private static SliderKey createSliderKey(SensitivitySliderValues sensitivitySliderValues){
        
        return new SliderKey(sensitivitySliderValues.getSensitivityType(),
                               sensitivitySliderValues.getProductType().getCode(),
                                sensitivitySliderValues.getMinFirmwareVersion(),
                                 sensitivitySliderValues.getMaxFirmwareVersion());
    }
    private static Slider findSlider(Map<SliderKey, Slider> sliders,SliderKey sliderKey){
        
        Slider slider = sliders.get(sliderKey);
        if (slider == null){
            
             slider = new Slider(sliderKey);
             sliders.put(sliderKey, slider);
        }
        return slider;
    }
    
    private static void setSliderPositionCounts( Map<SliderKey, Slider> sliders){
        
        Collection<Slider> sliderValues = sliders.values();
        for(Slider slider:sliderValues){
            slider.setSliderPositionCount();
        }
    }
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    
    public static Slider getSlider(SliderType sliderType, ProductType productType, Integer minFirmwareVersion, Integer maxFirmwareVersion){
        
        SliderKey sliderKey = new SliderKey(sliderType.getCode(),productType.getCode(),minFirmwareVersion,maxFirmwareVersion);
        
        return sensitivitySliders.get(sliderKey);
    }
}
