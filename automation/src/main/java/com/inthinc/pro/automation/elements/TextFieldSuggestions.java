package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.TextFieldWithSuggestions;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.WordConverterEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;

public class TextFieldSuggestions extends TextField implements TextFieldWithSuggestions {

    private SeleniumEnums suggestionBox;

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, Object ...objects) {
        super(anEnum, objects);
        this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    @Override
    public TextFieldSuggestions type(Object toType) {
        getSelenium().type(myEnum, "");
        getSelenium().typeKeys(myEnum, toType.toString());
        return this;
    }

    @Override
    public TextLink getSuggestion(Integer row) {
        return new TextLink(setIds("["+row+"]/td"));
    }
    
    private SeleniumEnumWrapper setIds(String qualifier){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(suggestionBox);
        List<String> newIds = new ArrayList<String>();
        String downToTr = "/tbody/tr" + qualifier;
        for (int i = 0; i < temp.getIDs().length; i++) {
            String newId = "";
            String id = temp.getIDs()[i];
            if (id.startsWith("//")) {
                newId = id + downToTr;
            } else if (!id.contains("=")) {
                newId = "//table[@id='" + id + "']" + downToTr;
            }
            if (!newId.contains("/span")){
                newIds.add(newId + "/span");
            }
            newIds.add(newId);
        }
        temp.setID(newIds.toArray(new String[]{}));
        return temp;
    }

    @Override
    public TextLink getSuggestion(String fullName) {
        return new TextLink(setIds("/td[2]/span["+Id.text(fullName)+"]"));
    }
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        String stepAsString = step.stepAsString();
        
        // TODO: dtanner: need a way to handle overloaded methods.
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            if (next.isAssignableFrom(Integer.class)) {
                passParameters[i] = WordConverterEnum.getNumber(stepAsString);
            } else {
                String lastOfStep = stepAsString.substring(stepAsString.indexOf("\"")+1);
                String toType = lastOfStep.substring(0, lastOfStep.indexOf("\""));
                passParameters[i] = toType;    
            }
            
            
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }

}
