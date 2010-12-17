package com.inthinc.pro.configurator.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.configurator.ProductType;

public class ProductSelectItems {
	
	private static List<SelectItem> productTypeSelectItems;
	
	static{
		
    	productTypeSelectItems = new ArrayList<SelectItem>();
        for (ProductType productType : ProductType.getSet()){
        	
        	SelectItem selectItem = new SelectItem(productType.getCode(),productType.getDescription());
        	productTypeSelectItems.add(selectItem);
        }
	}
    public List<SelectItem> getSelectItems(){
    	
		return productTypeSelectItems;
    }

}
