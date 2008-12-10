package com.inthinc.pro.backing.ui;

import java.util.List;

import javax.faces.model.SelectItem;

public interface AutocompletePicker
{
    public List<SelectItem> autocomplete(Object value);
    public Object getItemValue();
    public void setItemValue(Object value);
    public void addItem();
    public void deleteItem();
}
