package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;
import java.util.List;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.WordConverterEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.SeleniumValueEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.automation.utils.Xpath;

public class SelectableObject extends Text implements Selectable {
    public SelectableObject(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }

    @Override
    public SelectableObject select(String desiredOption) {

        select(desiredOption, 1);
        String selected = getSelenium().getSelectedLabel(myEnum);
        assertEquals(desiredOption, selected);
        return this;
    }

    public SelectableObject select(TextEnum value) {
        if (value instanceof SeleniumValueEnums) {
            return select(((SeleniumValueEnums) value).getPosition() + 1);
        }
        return select(value.getText());
    }
    
    @Override
    public String getText() {
        return getSelenium().getSelectedLabel(myEnum);
    }

    @Override
    public String getText(Integer optionNumber) {
        return getSelenium().getSelectOptions(myEnum)[--optionNumber];
    }

    public SelectableObject selectRandom() {
        String[] allOptions = getSelenium().getSelectOptions(myEnum);
        int randomIndex = RandomValues.newOne().getInt(allOptions.length);
        return select(randomIndex);
    }

    @Override
    public SelectableObject select(Integer optionNumber) {
//        optionNumber--;
        getSelenium().select(myEnum, "index=" + optionNumber);
        pause(2, "Wait for propogation");
        String selected = getSelenium().getSelectedIndex(myEnum);
        assertEquals(optionNumber.toString(), selected);
        return this;
    }

    @Override
    public SelectableObject select(String desiredOption, Integer matchNumber) {
//        matchNumber--;
        String xpath = getSelectIDAsXpath();
        if (xpath == null) {
            xpath = getSelectXpath();
        }
        getSelenium().click(xpath, Id.text(desiredOption), matchNumber);
        return this;
    }

    @Override
    public SelectableObject selectPartMatch(String partialMatch, Integer matchNumber) {
        matchNumber--;
        String xpath = getSelectIDAsXpath();
        if (xpath == null) {
            xpath = getSelectXpath();
        }
        getSelenium().click(xpath, Id.contains(Id.text(), partialMatch), matchNumber);
        return this;
    }

    private String getSelectIDAsXpath() {
        List<String> ids = myEnum.getLocators();
        for (String locator : ids) {
            if (!locator.contains("=") || !locator.startsWith("//")) {
                return Xpath.start().select(Id.id(locator)).toString();
            }
        }
        return null;
    }

    private String getSelectXpath() {
        List<String> ids = myEnum.getLocators();
        for (String locator : ids) {
            if (!locator.contains("=") && locator.startsWith("//")) {
                return locator;
            }
        }
        return null;
    }

    @Override
    public SelectableObject selectPartMatch(String partialMatch) {
        return selectPartMatch(partialMatch, 1);
    }
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        String stepAsString = step.stepAsString();
        
        // TODO: dtanner: need a way to handle overloaded methods.
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            if (next.isAssignableFrom(String.class)){
                String lastOfStep = stepAsString.substring(stepAsString.indexOf("\"")+1);
                String toType = lastOfStep.substring(0, lastOfStep.indexOf("\""));
                passParameters[i] = toType;    
            } else if (next.isAssignableFrom(Integer.class)) {
                passParameters[i] = WordConverterEnum.getNumber(stepAsString);
            }
            
            
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }
}
