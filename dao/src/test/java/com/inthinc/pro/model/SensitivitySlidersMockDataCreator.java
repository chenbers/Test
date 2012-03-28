package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SliderType;

public class SensitivitySlidersMockDataCreator {
    private SensitivitySliders sensitivitySliders;

    public SensitivitySlidersMockDataCreator(){
        
        List<SensitivitySliderValues>  sensitivitySliderValuesList = new ArrayList<SensitivitySliderValues>();
        //temporarily set up the proper values
        //Hard Acceleration
        SensitivitySliderValues sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(157);
        sfcm.setSliderType(SliderType.HARD_ACCEL_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[] valuesHARD_ACCEL = {"3000 40 1","1000 25 2","975 25 3","950 25 4","925 25 5","900 25 6","850 25 7","825 25 8","800 25 9","750 25 10","700 25 11","665 25 12","610 25 13","555 25 14","500 25 15"};
        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL));
        sfcm.setDefaultValueIndex(4);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.TIWIPRO);
        sensitivitySliderValuesList.add(sfcm);
        
        //Hard Brake
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(158);
        sfcm.setSliderType(SliderType.HARD_BRAKE_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[] valuesHARD_BRAKE = {"2250 100 1","1400 60 2","1175 60 3","1100 50 4","1025 50 5","950 50 6","875 40 7","800 40 8","750 35 9","700 25 10"};
        sfcm.setValues(Arrays.asList(valuesHARD_BRAKE));
        sfcm.setDefaultValueIndex(4);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.TIWIPRO);
        sensitivitySliderValuesList.add(sfcm);
        //HARD_TURN
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(159);
        sfcm.setSliderType(SliderType.HARD_TURN_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[] valuesHARD_TURN = {"2250 100 1","1375 55 2","1175 45 3","1100 40 4","1025 35 5","950 30 6","875 25 7","800 25 8","700 25 9","600 25 10 "};
        sfcm.setValues(Arrays.asList(valuesHARD_TURN));
        sfcm.setDefaultValueIndex(4);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.TIWIPRO);
        sensitivitySliderValuesList.add(sfcm);
        
        //HARD_BUMP
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(160);
        sfcm.setSliderType(SliderType.HARD_BUMP_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[] valuesHARD_BUMP = {"3000 900 1 300","3000 80 2 300","2200 70 3 275","2000 70 4 250","1800 65 5 225","1500 60 6 188","1300 55 7 163","1200 50 8 150",
                                    "1000 50 9 125","950 45 10 119","900 45 11 113","850 45 12 106","750 35 13 94","650 30 14 81","500 25 15 63"};
        sfcm.setValues(Arrays.asList(valuesHARD_BUMP));
        sfcm.setDefaultValueIndex(9);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.TIWIPRO);
        sensitivitySliderValuesList.add(sfcm);
        //Hard brake
        //DVX
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1231);
        sfcm.setSliderType(SliderType.HARD_BRAKE_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[] valuesdvx = {"225","140","118","110","103","95","88","80","75","70"};
        sfcm.setValues(Arrays.asList(valuesdvx));
        sfcm.setDefaultValueIndex(4);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);

        //X_ACCEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1229);
        sfcm.setSliderType(SliderType.HARD_BRAKE_SLIDER);
        sfcm.setSensitivitySubtype(1);
        String[]valuesX_ACCEL = {"10","6","6","5","5","5","4","4","4","3"};
        sfcm.setValues(Arrays.asList(valuesX_ACCEL));
        sfcm.setDefaultValueIndex(null);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);

        //Hard turn
        //DVY
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1228);
        sfcm.setSliderType(SliderType.HARD_TURN_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[]valuesdvy = {"225","137","117","110","102","95","87","80","70","60"};
        sfcm.setValues(Arrays.asList(valuesdvy));
        sfcm.setDefaultValueIndex(4);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);

        //Y_LEVEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1226);
        sfcm.setSliderType(SliderType.HARD_TURN_SLIDER);
        sfcm.setSensitivitySubtype(1);
        String[]valuesY_LEVEL = {"10","6","5","4","4","3","3","3","3","3"};
        sfcm.setValues(Arrays.asList(valuesY_LEVEL));
        sfcm.setDefaultValueIndex(null);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);

        //hard accel
        //HARD_ACCEL_DELTAV
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1234);
        sfcm.setSliderType(SliderType.HARD_ACCEL_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[]valuesHARD_ACCEL_DELTAV = {"300","100","98","95","93","90","85","83","80","75","70","67","61","56","50"};
        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_DELTAV));
        sfcm.setDefaultValueIndex(8);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);
        
        //HARD_ACCEL_LEVEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1232);
        sfcm.setSliderType(SliderType.HARD_ACCEL_SLIDER);
        sfcm.setSensitivitySubtype(1);
        String[]valuesHARD_ACCEL_LEVEL = {"4","3","3","3","3","3","3","3","3","3","3","3","3","3","3"};
        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_LEVEL));
        sfcm.setDefaultValueIndex(null);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);

        //Hard verticals
        //HARDVERT_DMM_PEAKTOPEAK
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1225);
        sfcm.setSliderType(SliderType.HARD_BUMP_SLIDER);
        sfcm.setSensitivitySubtype(1);
        String[]valuesHARDVERT_DMM_PEAKTOPEAK = {"600","450","400","350","300","220","200","180","150","130","120","100","95","90","85","75","65","50"};
        sfcm.setValues(Arrays.asList(valuesHARDVERT_DMM_PEAKTOPEAK));
        sfcm.setDefaultValueIndex(null);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);

        //rms_level
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1224);
        sfcm.setSliderType(SliderType.HARD_BUMP_SLIDER);
        sfcm.setSensitivitySubtype(0);
        String[]valuesRMS_LEVEL = {"25","12","10","9","8","7","7","7","6","6","5","5","5","5","5","4","3"};
        sfcm.setValues(Arrays.asList(valuesRMS_LEVEL));
        sfcm.setDefaultValueIndex(12);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);

        //SEVERE_HARDVERT_LEVEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1165);
        sfcm.setSliderType(SliderType.HARD_BUMP_SLIDER);
        sfcm.setSensitivitySubtype(2);
        String[]valuesSEVERE_HARDVERT_LEVEL = {"750","563","500","438", "375","275","250","225","188","163","150","125","119","113","106","94","81","63"};
        sfcm.setValues(Arrays.asList(valuesSEVERE_HARDVERT_LEVEL));
        sfcm.setDefaultValueIndex(null);
        sfcm.setMaxFirmwareVersion(1000000);
        sfcm.setMinFirmwareVersion(0);
        sfcm.setProductType(ProductType.WAYSMART);
        sensitivitySliderValuesList.add(sfcm);
        
        sensitivitySliders = new SensitivitySliders();
        sensitivitySliders.buildSliders(sensitivitySliderValuesList);
    }

    public SensitivitySliders getSensitivitySliders(){
        return sensitivitySliders;
    }
}
