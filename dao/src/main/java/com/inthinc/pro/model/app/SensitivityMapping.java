package com.inthinc.pro.model.app;

import java.util.Arrays;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.SensitivityForwardCommandMapping;
import com.inthinc.pro.model.configurator.SettingType;

public class SensitivityMapping implements BaseAppEntity{

//    private static Map<SensitivityType, SensitivityForwardCommandMapping> sensitivityMapping;
//    private ConfiguratorDAO                       configuratorDAO;
//    
//    public void init(){
//        sensitivityMapping = configuratorDAO.getSensitivityMaps();
//        
//        // This will be removed once we have the real data
////        sensitivityMapping = new HashMap<SensitivityType, SensitivityForwardCommandMapping>();
//        SensitivityForwardCommandMapping sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.DVX);
//        String[] valuesdvx = {"10","6","6","5","5","5","4","4","4","3"};
//        sfcm.setValues(Arrays.asList(valuesdvx));
//        sensitivityMapping.put(SensitivityType.DVX,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.X_ACCEL);
//        String[]valuesX_ACCEL = {"225","140","118","110","103","95","88","80","75","70"};
//        sfcm.setValues(Arrays.asList(valuesX_ACCEL));
//        sensitivityMapping.put(SensitivityType.X_ACCEL,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.DVY);
//        String[]valuesdvy = {"10","6","5","4","4","3","3","3","3","3"};
//        sfcm.setValues(Arrays.asList(valuesdvy));
//        sensitivityMapping.put(SensitivityType.DVY,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.Y_LEVEL);
//        String[]valuesY_LEVEL = {"225","138","118","110","103","95","88","80","70","60"};
//        sfcm.setValues(Arrays.asList(valuesY_LEVEL));
//        sensitivityMapping.put(SensitivityType.Y_LEVEL,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.HARD_ACCEL_DELTAV);
//        String[]valuesHARD_ACCEL_DELTAV = {"4","3","3","3","3","3","3","3","3","3","3","3","3","3","3"};
//        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_DELTAV));
//        sensitivityMapping.put(SensitivityType.HARD_ACCEL_DELTAV,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.HARD_ACCEL_LEVEL);
//        String[]valuesHARD_ACCEL_LEVEL = {"300","100","98","95","93","90","85","83","80","75","70","67","61","56","50"};
//        sfcm.setValues(Arrays.asList(valuesHARD_ACCEL_LEVEL));
//        sensitivityMapping.put(SensitivityType.HARD_ACCEL_LEVEL,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.HARDVERT_DMM_PEAKTOPEAK);
//        String[]valuesHARDVERT_DMM_PEAKTOPEAK = {"400","500","600","700"};
//        sfcm.setValues(Arrays.asList(valuesHARDVERT_DMM_PEAKTOPEAK));
//        sensitivityMapping.put(SensitivityType.HARDVERT_DMM_PEAKTOPEAK,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.RMS_LEVEL);
//        String[]valuesRMS_LEVEL = {"400","500","600","700"};
//        sfcm.setValues(Arrays.asList(valuesRMS_LEVEL));
//        sensitivityMapping.put(SensitivityType.RMS_LEVEL,sfcm);
//
//        sfcm = new SensitivityForwardCommandMapping();
//        sfcm.setSetting(SensitivityType.SEVERE_HARDVERT_LEVEL);
//        String[]valuesSEVERE_HARDVERT_LEVEL = {"400","500","600","700"};
//        sfcm.setValues(Arrays.asList(valuesSEVERE_HARDVERT_LEVEL));
//        sensitivityMapping.put(SensitivityType.SEVERE_HARDVERT_LEVEL,sfcm);
//}
//
//    public static Map<SensitivityType, SensitivityForwardCommandMapping> getSensitivityMapping() {
//        return sensitivityMapping;
//    }
//    
//    public static SensitivityForwardCommandMapping getSensitivityForwardCommandMapping(SensitivityType sensitivityType){
//        
//        return sensitivityMapping.get(sensitivityType);
//    }
//    public ConfiguratorDAO getConfiguratorDAO() {
//        return configuratorDAO;
//    }
//    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
//        this.configuratorDAO = configuratorDAO;
//    }

}
