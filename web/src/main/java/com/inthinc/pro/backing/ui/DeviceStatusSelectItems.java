package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.util.MessageUtil;

public class DeviceStatusSelectItems {
    private static final String BLANK_SELECTION = "&#160;";

    public static List<SelectItem> getSelectItems() {
        List<SelectItem> deviceStatusSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem(null, MessageUtil.getMessageString("vehiclesHeader_status"));
        blankItem.setEscape(false);
        deviceStatusSelectItems.add(blankItem);
        for (DeviceStatus e : EnumSet.complementOf(EnumSet.of(DeviceStatus.DELETED))) {
            deviceStatusSelectItems.add(new SelectItem(e.getDescription(), MessageUtil.getMessageString(e.toString())));
        }
        return deviceStatusSelectItems;
    }
}
