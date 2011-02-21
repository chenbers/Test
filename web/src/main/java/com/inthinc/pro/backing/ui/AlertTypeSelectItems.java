package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.util.MessageUtil;

public class AlertTypeSelectItems {
    
    public static List<SelectItem> getAlertTypeSelectItems(Boolean hosEnabled, Boolean waySmartEnabled, Boolean hasZones) {
        
        List<SelectItem> alertTypeSelectItems = new ArrayList<SelectItem>();

        alertTypeSelectItems = addDefaultTypes(alertTypeSelectItems);
        alertTypeSelectItems = addHosTypes(alertTypeSelectItems,hosEnabled);
        alertTypeSelectItems = addWaySmartTypes(alertTypeSelectItems,waySmartEnabled);
        alertTypeSelectItems = addZones(alertTypeSelectItems,hasZones);
        
        return alertTypeSelectItems;
    }
    private static List<SelectItem> addDefaultTypes(List<SelectItem> alertTypeSelectItems){
        
        Set<EventSubCategory> defaultSet = EnumSet.of(EventSubCategory.COMPLIANCE,
                                                      EventSubCategory.DRIVING_STYLE,
                                                      EventSubCategory.INSTALLATION,
                                                      EventSubCategory.EMERGENCY,
                                                      EventSubCategory.SPEED,
                                                      EventSubCategory.VEHICLE);
        return addSet(alertTypeSelectItems,defaultSet);
    }
    private static List<SelectItem> addHosTypes(List<SelectItem> alertTypeSelectItems, Boolean hosEnabled){
        
        if (hosEnabled){
            alertTypeSelectItems = addSet(alertTypeSelectItems,EnumSet.of(EventSubCategory.HOS));
        }
        return alertTypeSelectItems;
    }
    private static List<SelectItem> addWaySmartTypes(List<SelectItem> alertTypeSelectItems, Boolean waySmartEnabled){
        
        if(waySmartEnabled){
            Set<EventSubCategory> waySmartSet = EnumSet.of(EventSubCategory.WIRELINE,
                                                           EventSubCategory.FATIGUE,
                                                           EventSubCategory.TEXTMESSAGE);
            
            alertTypeSelectItems = addSet(alertTypeSelectItems,waySmartSet);
        }
        return alertTypeSelectItems;
    }
    private static List<SelectItem> addZones(List<SelectItem> alertTypeSelectItems, Boolean hasZones){
        
        if (hasZones){
            alertTypeSelectItems = addSet(alertTypeSelectItems,EnumSet.of(EventSubCategory.ZONES));
        }
        return alertTypeSelectItems;
    }
    private static List<SelectItem> addSet(List<SelectItem> alertTypeSelectItems,Set<EventSubCategory> setOfAlertTypes){
        
        for (EventSubCategory e : setOfAlertTypes)
        {
            alertTypeSelectItems.add(new SelectItem(e,MessageUtil.getMessageString(e.toString())));
        }

        return alertTypeSelectItems;
    }
}
