package com.inthinc.pro.backing.importer;

import java.util.List;

public class ProgressBarBean {
    private boolean buttonRendered = true;
    private boolean enabled=false;
    private Long currentValue;
    private List<String> errorList;
    
    public ProgressBarBean() {
    }
    
    public void reset() {
        setEnabled(false);
        setButtonRendered(true);
        setCurrentValue(null);
    }
    public void startProcess() {
        setEnabled(true);
        setButtonRendered(false);
        setCurrentValue(0l);
    }
    public void stopProcess() {
        setEnabled(false);
        setButtonRendered(true);
        setCurrentValue(101l);
    }
    public void stopProcess(List<String> errorList) {
        this.errorList = errorList;
        stopProcess();
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public Long getCurrentValue(){
        if (currentValue == null)
            return -1l;
        return currentValue;
    }
    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    
    public boolean getEnabled() {
        return enabled;
    }
   

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isButtonRendered() {
        return buttonRendered;
    }

    public void setButtonRendered(boolean buttonRendered) {
        this.buttonRendered = buttonRendered;
    }
}
