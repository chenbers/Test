package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.util.MessageUtil;

public class ProductTypeSelectItems {
    
    private static final String BLANK_SELECTION = "&#160;";
    
    public static List<SelectItem> getSelectItems() {
   
        
        List<SelectItem> productTypesSelectItems = new ArrayList<SelectItem>();

        SelectItem blankItem = new SelectItem("", BLANK_SELECTION+MessageUtil.getMessageString("vehiclesHeader_productType"));
        blankItem.setEscape(false);
        productTypesSelectItems.add(blankItem);

        for (ProductType e : EnumSet.allOf(ProductType.class))
        {
        	String displayName = e.getMessageKey()!=null?MessageUtil.getMessageString(e.getMessageKey()):e.getDescription();
            productTypesSelectItems.add(new SelectItem(e,displayName));
        }
        return productTypesSelectItems;
    }
}
