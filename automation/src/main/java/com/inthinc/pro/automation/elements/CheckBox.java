package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.WordConverterEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class CheckBox extends ClickableObject implements Checkable, Clickable {

    public CheckBox(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    

    @Override
    public CheckBox check() {
        if (!isChecked()) {
            click();
        }
        return this;
    }

    @Override
    public CheckBox uncheck() {
        if (isChecked()) {
            click();
        }
        return this;
    }

    @Override
    public Boolean isChecked() {
        return getSelenium().isChecked(myEnum);
    }

    @Override
    public Boolean validateChecked(Boolean checked) {
        return validateEquals(checked, isChecked());
    }

    @Override
    public Boolean assertChecked(Boolean checked) {
        return assertEquals(checked, isChecked());
    }
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        String stepAsString = step.stepAsString();
        
        // TODO: dtanner: need a way to handle overloaded methods.
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            passParameters[i] = checkBoolean(stepAsString);
            
            
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }
}
