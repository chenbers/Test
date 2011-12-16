package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class Selector extends SelectableObject implements Selectable {

	public Selector(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    public Selector(SeleniumEnums anEnum, String page, String type) {
        super(anEnum, page);
        myEnum.replaceOldWithNew("*type*", type);
    }
    
    public Selector addSelection(String item){
    	selenium.addSelection(myEnum, item);
    	return this;
    }
    
    public Selector removeSelection(String item){
    	selenium.removeSelection(myEnum, item);
    	return this;
    }
    
    public Selector removeAllSelections(){
    	selenium.removeAllSelections(myEnum);
    	return this;
    }
    
}
