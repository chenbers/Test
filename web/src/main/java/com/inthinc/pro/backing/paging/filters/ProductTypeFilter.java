package com.inthinc.pro.backing.paging.filters;

import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.ui.ProductTypeSelectItems;

public class ProductTypeFilter implements ColumnSelectItemsFilter {
    
    private String value;

    @Override
    public List<SelectItem>  getSelectItems() {
        return ProductTypeSelectItems.getSelectItems();
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
