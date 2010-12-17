package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.util.MessageUtil;

public enum ProductTypeSelectItems {
    
    INSTANCE();

    private final static String BLANK_SELECTION = "&#160;";
    private List<SelectItem> selectItems;
    
    private ProductTypeSelectItems() {
   
        
        List<SelectItem> productTypesSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem("", BLANK_SELECTION+MessageUtil.getMessageString("vehiclesHeader_productType"));
        blankItem.setEscape(false);
        productTypesSelectItems.add(blankItem);

        for (ProductType e : ProductType.getSet())
        {
            productTypesSelectItems.add(new SelectItem(e,e.getDescription()));
        }
        selectItems = productTypesSelectItems;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

}
