package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.Typeable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextField extends TextObject implements Typeable {
    
    public TextField(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    
    public TextField(SeleniumEnums anEnum, String prefix, TextEnum replacement) {
        super(anEnum, prefix + replacement.getText());
    }

    @Override
    public TextField clear() {
        type("");
        return this;
    }

    @Override
    public TextField type(Object inputText) {
        getSelenium().type(myEnum, inputText.toString());
        return this;
    }

    @Override
    public String getText(){
        return getSelenium().getValue(myEnum);
    }
    
    @Override
    public Boolean assertEquals() {
        return assertEquals(myEnum, getText(), myEnum);
    }

    @Override
    public Boolean assertEquals(String compareAgainst) {
        return assertEquals(compareAgainst, getText());
    }

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
        return assertNotEquals(compareAgainst, getText());
    }
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        String stepAsString = step.stepAsString();
        
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
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
