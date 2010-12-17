package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.util.MessageUtil;

public enum DeviceStatusSelectItems {

    INSTANCE();
    private List<SelectItem> selectItems;

    private DeviceStatusSelectItems() {
        List<SelectItem> deviceStatusSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem("", MessageUtil.getMessageString("vehiclesHeader_status"));
        blankItem.setEscape(false);
        deviceStatusSelectItems.add(blankItem);

        for (DeviceStatus e : EnumSet.allOf(DeviceStatus.class)) {
            deviceStatusSelectItems.add(new SelectItem(e, initCap(e.getDescription())));
        }
        selectItems = deviceStatusSelectItems;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
    //TODO: jwimmer: there must be a better way to do this.  up against a deadline. 
    public static String initCap(String s) {
        StringBuilder results = new StringBuilder();
        String[] words = s.split(" ");
        for(String word: words) {
            results.append(Character.toUpperCase(word.charAt(0)));
            results.append(word.substring(1).toLowerCase());
        }
        return results.toString();
    }
}
