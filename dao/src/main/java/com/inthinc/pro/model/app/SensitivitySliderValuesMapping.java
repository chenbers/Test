package com.inthinc.pro.model.app;

import java.util.Arrays;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.SensitivitySliderValues;

public class SensitivitySliderValuesMapping {

    private static Map<Integer,SensitivitySliderValues> sensitivitySliderValues;
    private ConfiguratorDAO                      configuratorDAO;
    
    public void init(){
        
        sensitivitySliderValues = configuratorDAO.getSensitivitySliderValues();
        
        //temporarily set up the proper values
        //Hard brake
        //DVX
        SensitivitySliderValues sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1231);
        sfcm.setSensitivityType(22);
        sfcm.setSensitivitySubtype(1);
        String[] valuesdvx = {"10","6","6","5","5","5","4","4","4","3"};
        sfcm.setValues(Arrays.asList(valuesdvx));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);

        //X_ACCEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1229);
        sfcm.setSensitivityType(22);
        sfcm.setSensitivitySubtype(0);
        String[]valuesX_ACCEL = {"225","140","118","110","103","95","88","80","75","70"};
        sfcm.setValues(Arrays.asList(valuesX_ACCEL));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);

        //Hard turn
        //DVY
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1228);
        sfcm.setSensitivityType(23);
        sfcm.setSensitivitySubtype(1);
        String[]valuesdvy = {"10","6","5","4","4","3","3","3","3","3"};
        sfcm.setValues(Arrays.asList(valuesdvy));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);

        //Y_LEVEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1226);
        sfcm.setSensitivityType(23);
        sfcm.setSensitivitySubtype(0);
        String[]valuesY_LEVEL = {"225","138","118","110","103","95","88","80","70","60"};
        sfcm.setValues(Arrays.asList(valuesY_LEVEL));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);

        //hard accel
        //HARD_ACCEL_DELTAV
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1234);
        sfcm.setSensitivityType(21);
        sfcm.setSensitivitySubtype(1);
        String[]valuesHARD_ACCEL_DELTAV = {"4","3","3","3","3","3","3","3","3","3","3","3","3","3","3"};
        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_DELTAV));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
        
        //HARD_ACCEL_LEVEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1232);
        sfcm.setSensitivityType(21);
        sfcm.setSensitivitySubtype(0);
        String[]valuesHARD_ACCEL_LEVEL = {"300","100","98","95","93","90","85","83","80","75","70","67","61","56","50"};
        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_LEVEL));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);

        //Hard verticals
        //HARDVERT_DMM_PEAKTOPEAK
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1225);
        sfcm.setSensitivityType(24);
        sfcm.setSensitivitySubtype(1);
        String[]valuesHARDVERT_DMM_PEAKTOPEAK = {"400","500","600","700"};
        sfcm.setValues(Arrays.asList(valuesHARDVERT_DMM_PEAKTOPEAK));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);

        //rms_level
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1224);
        sfcm.setSensitivityType(24);
        sfcm.setSensitivitySubtype(1);
        String[]valuesRMS_LEVEL = {"400","500","600","700"};
        sfcm.setValues(Arrays.asList(valuesRMS_LEVEL));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);

        //SEVERE_HARDVERT_LEVEL
        sfcm = new SensitivitySliderValues();
        sfcm.setSettingID(1165);
        sfcm.setSensitivityType(24);
        sfcm.setSensitivitySubtype(1);
        String[]valuesSEVERE_HARDVERT_LEVEL = {"400","500","600","700"};
        sfcm.setValues(Arrays.asList(valuesSEVERE_HARDVERT_LEVEL));
        sensitivitySliderValues.put(sfcm.getSettingID(),sfcm);
        
    }

    public static Map<Integer,SensitivitySliderValues> getSensitivitySliderValues() {
        return sensitivitySliderValues;
    }

    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
}
