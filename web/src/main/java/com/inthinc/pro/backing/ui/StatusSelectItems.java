package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.util.MessageUtil;

public enum StatusSelectItems {

    INSTANCE();
    private List<SelectItem> selectItems;

    private StatusSelectItems() {
        List<SelectItem> statusSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem("", MessageUtil.getMessageString("vehiclesHeader_status"));
        blankItem.setEscape(false);
        statusSelectItems.add(blankItem);

        for (DeviceStatus e : EnumSet.allOf(DeviceStatus.class)) {
            statusSelectItems.add(new SelectItem(e.getCode(), e.getDescription()));
        }
        selectItems = statusSelectItems;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
}
