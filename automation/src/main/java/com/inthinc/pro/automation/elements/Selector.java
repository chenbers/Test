package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import org.jbehave.core.steps.StepCreator.PendingStep;

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
    	getSelenium().addSelection(myEnum, item);
    	return this;
    }
    
    public Selector removeSelection(String item){
    	getSelenium().removeSelection(myEnum, item);
    	return this;
    }
    
    public Selector removeAllSelections(){
    	getSelenium().removeAllSelections(myEnum);
    	return this;
    }
}
