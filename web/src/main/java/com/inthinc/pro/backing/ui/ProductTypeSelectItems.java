package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.configurator.ProductType;

public enum ProductTypeSelectItems {
    
    INSTANCE();

    private final static String BLANK_SELECTION = "&#160;";
    private List<SelectItem> selectItems;
    
    private ProductTypeSelectItems() {
   
        
        List<SelectItem> productTypesSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
        blankItem.setEscape(false);
        productTypesSelectItems.add(blankItem);

        for (ProductType e : EnumSet.allOf(ProductType.class))
        {
            productTypesSelectItems.add(new SelectItem(e.getName(),e.getName()));
        }
        selectItems = productTypesSelectItems;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

}
