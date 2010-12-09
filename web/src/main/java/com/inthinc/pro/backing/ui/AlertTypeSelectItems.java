package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.util.MessageUtil;

public enum AlertTypeSelectItems {
    INSTANCE;

    private List<SelectItem> selectItems;
    
    private AlertTypeSelectItems() {
        
        List<SelectItem> alertTypeSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem("", MessageUtil.getMessageString("editRedFlag_notype"));
        alertTypeSelectItems.add(blankItem);

        for (EventSubCategory e : EnumSet.allOf(EventSubCategory.class))
        {
            alertTypeSelectItems.add(new SelectItem(e.name(),MessageUtil.getMessageString(e.toString())));
        }
        selectItems = alertTypeSelectItems;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

}
