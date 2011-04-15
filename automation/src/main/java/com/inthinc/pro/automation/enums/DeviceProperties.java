package com.inthinc.pro.automation.enums;

import java.util.Map;

public interface DeviceProperties extends DeviceTypesUnique {
    public abstract String getDefaultSetting();
    
    public Map<?, String> getDefaultProps();
}
