package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.utils.MasterTest;

public class TextObject extends ElementBase implements TextBased {

    public TextObject(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }

    @Override
    public Boolean assertEquals() {
        return assertEquals(myEnum, myEnum, myEnum);
    }

    @Override
    public Boolean assertEquals(String compareAgainst) {
        return assertEquals(compareAgainst, myEnum);
    }

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
        return assertNotEquals(compareAgainst, myEnum);
    }

    @Override
    public Boolean compare() {
        return compare(myEnum.getText());
    }

    @Override
    public Boolean compare(String expected) {
        return compare(expected, getText());
    }

    @Override
    public String getText() {
        String toReturn = getSelenium().getText(myEnum);
        if (toReturn.isEmpty()){
            try {
                toReturn = getAttribute("value");
                if (toReturn == null){
                    toReturn = "";
                }
            } catch (Exception e ){
                MasterTest.print(e);
            }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public Boolean validate() {
        return validateEquals(myEnum.getText(), getText());
    }

    @Override
    public Boolean validate(String expected) {
        return validateEquals(expected, getText());
    }

    @Override
    public Boolean validate(TextEnum expected) {
        return validate(expected.getText());
    }

    @Override
    public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
        return validate(expected.getText().replace(replaceOld, withNew));
    }

    @Override
    public Boolean validateContains(String expectedPart) {
        String actualFullString = getText();
        return validateStringContains(expectedPart, actualFullString);
    }
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        String stepAsString = step.stepAsString();
        
        // TODO: dtanner: need a way to handle overloaded methods.
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            
            passParameters[i] = getComparator(stepAsString);
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }
}
