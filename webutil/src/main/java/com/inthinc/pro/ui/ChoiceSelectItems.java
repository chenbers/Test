package com.inthinc.pro.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.inthinc.pro.configurator.model.DeviceSettingDefinitionBean;

public class ChoiceSelectItems {
    
    private Map<Integer,List<SelectItem>> selectItems;
    
    public ChoiceSelectItems(List<DeviceSettingDefinitionBean> settingsDefinitions){
    	
        selectItems = new HashMap<Integer,List<SelectItem>>();
        for(DeviceSettingDefinitionBean dsd: settingsDefinitions){
            
            if (dsd.getHasChoices()){
                
                selectItems.put(dsd.getSettingID(), getSelectItemsFromChoices(dsd.getChoices()));
            }
        }
    }
    private List<SelectItem> getSelectItemsFromChoices(List<String> choices){
    	
    	List<SelectItem> selectItemsForDevice = new ArrayList<SelectItem>();
        for(String choice : choices){
            
            selectItemsForDevice.add(new SelectItem(choice));
        }
    	return selectItemsForDevice;
    }

    public Map<Integer, List<SelectItem>> getSelectItems() {
        return selectItems;
    }
}
