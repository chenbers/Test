package com.inthinc.pro.backing.paging.filters;

import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.ui.DeviceStatusSelectItems;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.pagination.FilterableEnumFactory;

public class DeviceStatusFilter implements ColumnSelectItemsFilter {
    
    private String value;
    private FilterableEnumFactory filterValue;

    @Override
    public List<SelectItem>  getSelectItems() {
        return DeviceStatusSelectItems.getSelectItems();
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (value != null && !value.isEmpty()) {
            setFilterValue(new FilterableEnumFactory(DeviceStatus.valueOf(value)));
        }
        else {
            setFilterValue(null);
        }
    }
    
    public FilterableEnumFactory getFilterValue() {
        
        return filterValue;
    }

    public void setFilterValue(FilterableEnumFactory filterValue) {
        this.filterValue = filterValue;
    }


}
