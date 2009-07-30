package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.util.MessageUtil;

public class FuelEfficiencyBean extends BaseBean {
    private List<SelectItem> fuelEfficiencyTypeItems = new ArrayList<SelectItem>();

    public void init() {
        for (FuelEfficiencyType type : FuelEfficiencyType.values()) {
            fuelEfficiencyTypeItems.add(new SelectItem(type, MessageUtil.getMessageString(type.toString(), getUser().getLocale())));
        }
    }

    public List<SelectItem> getFuelEfficiencyTypeItems() {
        return fuelEfficiencyTypeItems;
    }
}
