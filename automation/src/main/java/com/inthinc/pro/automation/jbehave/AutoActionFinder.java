package com.inthinc.pro.automation.jbehave;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementBase;
import com.inthinc.pro.automation.utils.MasterTest;

public class AutoActionFinder {
    
    private AutoStepCreator stepCreator;
    
    public void setStepCreator(AutoStepCreator stepCreator){
        this.stepCreator = stepCreator;
    }
    
    
    public AutoActionFinder(){
    }
    

    public Step findAction(ElementBase elementClass, String elementType, String elementName, PendingStep step) throws SecurityException, NoSuchMethodException {
        if (stepCreator == null){ 
            throw new NullPointerException("We need a copy of the StepCreator");
        }
        String stepAsString = step.stepAsString();
        try {
            if (elementClass instanceof MasterTest){ 
                for (String save : RegexTerms.saveAlias){ 
                    if (stepAsString.contains(save)){
                        Method method = elementClass.parseStep(stepAsString.replace(save, "get text"), elementType);
                        Object[] parameters = elementClass.getParameters(step, method);
                        return stepCreator.createSaveVariableStep(step, elementClass, method, parameters);
                    }
                }
                if (stepAsString.contains("validate") || stepAsString.contains("assert")){
                    Map<Method, Object[]> methods = elementClass.parseValidationStep(step, elementType);
                    Entry<Method, Object[]> method = methods.entrySet().iterator().next();

                    return stepCreator.createValidationStep(step, elementClass, method.getKey(), method.getValue());
                }
                    
                    
                Method method = elementClass.parseStep(stepAsString, elementType);
                Object[] parameters = elementClass.getParameters(step, method);
                return stepCreator.createPageStep(step, elementClass, method, parameters);
            }
        } catch (Exception e){
            MasterTest.print(e);
        }
        
        
        throw new NoSuchMethodError("Cannot find an action for " + step.stepAsString());
    }

}
