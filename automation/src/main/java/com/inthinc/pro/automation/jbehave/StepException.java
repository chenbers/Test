package com.inthinc.pro.automation.jbehave;

import com.inthinc.pro.automation.logging.Log;

@SuppressWarnings("serial")
public class StepException extends RuntimeException {
    
    private String stepAsString;
    private String errorMessage;
    private Throwable e;
    
    /**
     * This exception should never be thrown unless we provide a step.
     */
    @Deprecated
    public StepException(){
        super();
    }
    
    public StepException(String stepAsString, String errorMessage, Throwable e){
        this.stepAsString = stepAsString;
        this.errorMessage = errorMessage;
        this.e = e;
        Log.debug("%s\n%s", errorMessage, e);
    }
    
    public String getStep(){
        return stepAsString;
    }

    public String getError(){
        return errorMessage;
    }
    
    public Throwable getTrace(){
        return e;
    }
    
    @Override
    public String toString(){
        return String.format("Step:%s\nMessage:%s\n", stepAsString, errorMessage);
    }
}
