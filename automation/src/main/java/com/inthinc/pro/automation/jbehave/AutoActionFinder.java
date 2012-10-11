package com.inthinc.pro.automation.jbehave;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.elements.TableNavigator;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.MasterTest;

public class AutoActionFinder {
    
    private AutoStepCreator stepCreator;
    
    public void setStepCreator(AutoStepCreator stepCreator){
        this.stepCreator = stepCreator;
    }
    
    public AutoStepCreator getStepCreator(){
        return stepCreator;
    }
    
    public Step findAction(Object elementInstance, String elementType, String elementName, PendingStep step) throws SecurityException, NoSuchMethodException {
        if (stepCreator == null){ 
            throw new NullPointerException("We need a copy of the StepCreator");
        }
        String stepAsString = step.stepAsString();
        try {
            if (elementInstance instanceof MasterTest){ 
                String save = RegexTerms.getMatch(RegexTerms.saveAlias, stepAsString);
                if (!save.equals("")){
                    Method method = AutoMethodParser.parseStep(stepAsString.replace(save, "get text"), elementType, elementInstance.getClass());
                    Object[] parameters = AutoMethodParser.getParameters(step.stepAsString(), method);
                    
                    return stepCreator.createSaveVariableStep(step, elementInstance, method, parameters);
                
                } else if (stepAsString.contains("validate") || stepAsString.contains("assert")){
                    Map<Method, Object[]> methods = AutoMethodParser.parseValidationStep(step, elementInstance.getClass());
                    Entry<Method, Object[]> method = methods.entrySet().iterator().next();

                    return stepCreator.createValidationStep(step, elementInstance, method.getKey(), method.getValue());
                }
                    
                    
                Method method = AutoMethodParser.parseStep(stepAsString, elementType, elementInstance.getClass());
                Object[] parameters = AutoMethodParser.getParameters(step.stepAsString(), method);
                return stepCreator.createPageStep(step, elementInstance, method, parameters);
            } else if (elementInstance instanceof TableBased<?>){
                @SuppressWarnings({ "unchecked", "rawtypes" })
                TableNavigator<?> navigator = new TableNavigator((TableBased<?>) elementInstance, stepAsString);
                if (navigator.isLoopingStep()){
                    navigator.loopForRow();
                    return stepCreator.createEmptyStep(stepAsString);
                } else if (navigator.isMethodStep()){
                    return stepCreator.createPageStep(step, navigator.getInstance(), navigator.getMethod());
                } else {
                    return findAction(navigator.getRow(), elementType, elementName, step);
                }
            }
        } catch (StepException e){
            Log.info(e.toString());
        }

        throw new NoSuchMethodException("Cannot find an action for " + step.stepAsString());
    }

}
