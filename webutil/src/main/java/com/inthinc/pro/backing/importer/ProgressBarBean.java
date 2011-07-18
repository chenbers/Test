package com.inthinc.pro.backing.importer;

public class ProgressBarBean {
    private boolean buttonRendered = true;
    private boolean enabled=false;
    private Long currentValue;
    
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
        setCurrentValue(1l);
    }
    public void stopProcess() {
//        setEnabled(false);
//        setButtonRendered(true);
////        setCurrentValue(null);
        setCurrentValue(101l);
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
