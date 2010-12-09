package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.util.MessageUtil;

public enum VehicleTypeSelectItems {

    INSTANCE();
    private List<SelectItem> selectItems;

    private VehicleTypeSelectItems() {
        List<SelectItem> vehicleTypesSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem("", MessageUtil.getMessageString("vehiclesHeader_vtype"));
        blankItem.setEscape(false);
        vehicleTypesSelectItems.add(blankItem);

        for (VehicleType e : EnumSet.allOf(VehicleType.class)) {
            vehicleTypesSelectItems.add(new SelectItem(e.getDescription(), e.getDescription()));
        }
        selectItems = vehicleTypesSelectItems;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
}
