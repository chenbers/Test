package com.inthinc.pro.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.configurator.DeviceSettingDefinition;

public class ChoiceSelectItems {
    
    private Map<Integer,List<SelectItem>> selectItems;
    
    public void init(List<DeviceSettingDefinition> allDeviceSettingDefinitions){
        
        selectItems = new HashMap<Integer,List<SelectItem>>();
        for(DeviceSettingDefinition dsd: allDeviceSettingDefinitions){
            
            if (dsd.getHasChoices()){
                
                List<SelectItem> selectItemsForDevice = new ArrayList<SelectItem>();
                
                for(String choice : dsd.getChoices()){
                    
                    selectItemsForDevice.add(new SelectItem(choice));
                }
                selectItems.put(dsd.getSettingID(), selectItemsForDevice);
            }
        }
    }

    public Map<Integer, List<SelectItem>> getSelectItems() {
        return selectItems;
    }
}
