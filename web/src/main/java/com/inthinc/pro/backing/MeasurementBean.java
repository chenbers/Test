package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class MeasurementBean extends BaseBean {
    private List<SelectItem> measurementTypeItems = new ArrayList<SelectItem>();

    public void init() {
        for (MeasurementType type : MeasurementType.values()) {
            measurementTypeItems.add(new SelectItem(type, MessageUtil.getMessageString(type.toString(), getLocale())));
        }
    }

    public List<SelectItem> getMeasurementTypeItems() {
        return measurementTypeItems;
    }
}
