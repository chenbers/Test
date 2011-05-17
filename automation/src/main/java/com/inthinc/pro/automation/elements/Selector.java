package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class Selector extends SelectableObject implements Selectable {
	public Selector(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public Selector(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public Selector(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public Selector(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
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
