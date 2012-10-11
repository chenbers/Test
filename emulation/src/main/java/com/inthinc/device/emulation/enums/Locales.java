package com.inthinc.device.emulation.enums;

import com.inthinc.pro.automation.interfaces.TextEnum;

public enum Locales implements TextEnum {
    
    ROMANIAN("ro_RO", 1),
    ENGLISH_CANADIAN("en_CA", 2),
    ENGLISH_US("en_US", 0),
    
    ;
    
    private final String locale;
    private final int folder;
    
    private Locales(String locale, int folder){
        this.locale = locale;
        this.folder = folder;
    }
    
    @Override
    public String getText(){
        return locale;
    }
    
    public int getFolder(){
        return folder;
    }
    
    @Override
    public String toString(){
        return locale;
    }
    

}
