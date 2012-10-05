package com.inthinc.pro.backing.paging.filters;

import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.ui.VehicleTypeSelectItems;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.pagination.FilterableEnumFactory;

public class VehicleTypeFilter implements ColumnSelectItemsFilter {
    
    private String value;
    private FilterableEnumFactory filterValue;

    @Override
    public List<SelectItem>  getSelectItems() {
        return VehicleTypeSelectItems.getSelectItems();
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (value != null && !value.isEmpty()) {
            setFilterValue(new FilterableEnumFactory(VehicleType.valueOf(value)));
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
