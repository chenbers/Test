package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.util.MessageUtil;

public class AlertTypeSelectItems {
    
    public static List<SelectItem> getAlertTypeSelectItems(Boolean hosEnabled, Boolean waySmartEnabled) {
        
        List<SelectItem> alertTypeSelectItems = new ArrayList<SelectItem>();

        alertTypeSelectItems = addDefaultTypes(alertTypeSelectItems);
        alertTypeSelectItems = addHosTypes(alertTypeSelectItems,hosEnabled);
        alertTypeSelectItems = addWaySmartTypes(alertTypeSelectItems,waySmartEnabled);
      
        return alertTypeSelectItems;
    }
    private static List<SelectItem> addDefaultTypes(List<SelectItem> alertTypeSelectItems){
        
        Set<EventSubCategory> defaultSet = EnumSet.of(EventSubCategory.COMPLIANCE,
                                                      EventSubCategory.DRIVING_STYLE,
                                                      EventSubCategory.INSTALLATION,
                                                      EventSubCategory.EMERGENCY,
                                                      EventSubCategory.SPEED,
                                                      EventSubCategory.VEHICLE,
                                                      EventSubCategory.ZONES);
        for (EventSubCategory e : defaultSet)
        {
            alertTypeSelectItems.add(new SelectItem(e.name(),MessageUtil.getMessageString(e.toString())));
        }
        return alertTypeSelectItems;
    }
    private static List<SelectItem> addHosTypes(List<SelectItem> alertTypeSelectItems, Boolean hosEnabled){
        
        if (hosEnabled){
            alertTypeSelectItems.add(new SelectItem(EventSubCategory.HOS.name(),
                                                    MessageUtil.getMessageString(EventSubCategory.HOS.toString())));
        }
        return alertTypeSelectItems;
    }
    private static List<SelectItem> addWaySmartTypes(List<SelectItem> alertTypeSelectItems, Boolean waySmartEnabled){
        
        if(waySmartEnabled){
            
            Set<EventSubCategory> waySmartSet = EnumSet.of(EventSubCategory.WIRELINE,
                                                           EventSubCategory.FATIGUE,
                                                           EventSubCategory.TEXTMESSAGE);
            
            for (EventSubCategory e : waySmartSet)
            {
                alertTypeSelectItems.add(new SelectItem(e.name(),MessageUtil.getMessageString(e.toString())));
            }
        }
        return alertTypeSelectItems;
    }
}
