package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.ForwardCommandStatus;

public class MessageDeliveryTypeList implements SelectList{
    List<SelectItem> selectList;

    @Override
    public List<SelectItem> getSelectList() {
        if (selectList == null) {
            selectList = new ArrayList<SelectItem> ();

            for(AlertMessageDeliveryType d : EnumSet.allOf(AlertMessageDeliveryType.class))
                selectList.add(new SelectItem(Integer.valueOf(d.getCode()), d.toString()));
        }
        
        return selectList;
    }

    @Override
    public Object valueOf(Object value) {
        return AlertMessageDeliveryType.valueOf(Integer.valueOf(value+""));

    }

}
