package com.inthinc.pro.backing.paging.filters;

import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.ui.ProductTypeSelectItems;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.FilterableEnumFactory;

public class ProductTypeFilter implements ColumnSelectItemsFilter {
    
    private String value;
    private FilterableEnumFactory filterValue;

    @Override
    public List<SelectItem>  getSelectItems() {
        return ProductTypeSelectItems.getSelectItems();
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (value != null && !value.isEmpty()) {
            setFilterValue(new FilterableEnumFactory(ProductType.valueOf(value)));
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
