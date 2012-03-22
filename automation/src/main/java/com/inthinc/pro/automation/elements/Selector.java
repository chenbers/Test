package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.WordConverterEnum;
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
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        String stepAsString = step.stepAsString();
        
        // TODO: dtanner: need a way to handle overloaded methods.
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        
        for (int i=0;i<parameters.length;i++){
            String lastOfStep = stepAsString.substring(stepAsString.indexOf("\"")+1);
            String toType = lastOfStep.substring(0, lastOfStep.indexOf("\""));
            passParameters[i] = toType;    
            
            
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }
}
