package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.configurator.SensitivityType;

public enum WaySmartSettingsAndSliders {

    HARD_ACCEL_SETTING(){
        SettingsAndSliders initSettingList(){
            
            SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
            
            List<SensitivityType> sensitivityTypes = new ArrayList<SensitivityType>();
            
            sensitivityTypes.add(SensitivityType.HARD_ACCEL_LEVEL);
            sensitivityTypes.add(SensitivityType.HARD_ACCEL_DELTAV);
            
            settingsAndSliders.setSensitivityTypes(sensitivityTypes);
            
            return settingsAndSliders;
        }
    },
    HARD_BRAKE_SETTING{

        SettingsAndSliders initSettingList(){
            
            SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
            
            List<SensitivityType> sensitivityTypes = new ArrayList<SensitivityType>();
            sensitivityTypes.add(SensitivityType.X_ACCEL);
            sensitivityTypes.add(SensitivityType.DVX);
            
            settingsAndSliders.setSensitivityTypes(sensitivityTypes);
            
            return settingsAndSliders;
        }
    },
    HARD_TURN_SETTING{

        SettingsAndSliders initSettingList(){
            
            SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
            
            List<SensitivityType> sensitivityTypes = new ArrayList<SensitivityType>();
            sensitivityTypes.add(SensitivityType.Y_LEVEL);
            sensitivityTypes.add(SensitivityType.DVY);
            
            settingsAndSliders.setSensitivityTypes(sensitivityTypes);
            
            return settingsAndSliders;
        }
    },
    HARD_VERT_SETTING{

        SettingsAndSliders initSettingList(){
            
            SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
            
            List<SensitivityType> sensitivityTypes = new ArrayList<SensitivityType>();
            sensitivityTypes.add(SensitivityType.RMS_LEVEL);
            sensitivityTypes.add(SensitivityType.SEVERE_HARDVERT_LEVEL);
            sensitivityTypes.add(SensitivityType.HARDVERT_DMM_PEAKTOPEAK);
            
            settingsAndSliders.setSensitivityTypes(sensitivityTypes);
            
            return settingsAndSliders;
        }
    };
    
    private SettingsAndSliders settingsAndSliders;
    
    abstract SettingsAndSliders initSettingList();

    private WaySmartSettingsAndSliders(){
        
        this.settingsAndSliders = initSettingList();
    }
    public SettingsAndSliders getSettingsAndSliders(){
        return settingsAndSliders;
    }
//  -- hard bumps
  //1224 rms_level
  //1225 hardvert_dmm_peaktopeak
  //1165 severe_hardvert_level
  //
  //-- turns
  //1226 y_level
  //1228 dvy
  //
  //-- brakes
  //1229 x_accel
  //1231 dvx
  //
  //-- accel
  //1232 hard_accel_level
  //1234 hard_accel_deltav
}