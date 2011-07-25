package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.util.MessageUtil;

public class VehicleTypeSelectItems {

    public static List<SelectItem> getSelectItems() {
        List<SelectItem> vehicleTypesSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem(null, MessageUtil.getMessageString("vehiclesHeader_vtype"));
        blankItem.setEscape(false);
        vehicleTypesSelectItems.add(blankItem);

        for (VehicleType e : EnumSet.allOf(VehicleType.class)) {
            vehicleTypesSelectItems.add(new SelectItem(e.name(), MessageUtil.getMessageString(e.toString())));
        }
        return vehicleTypesSelectItems;
    }

}
