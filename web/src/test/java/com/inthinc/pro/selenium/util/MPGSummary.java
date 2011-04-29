package com.inthinc.pro.selenium.util;

import java.util.ArrayList;
import java.util.List;

public class MPGSummary {
    private List<MPGData> mpgSet;
    
    public MPGSummary() {
        this.mpgSet = new ArrayList<MPGData>();
    }

    public List<MPGData> getMpgSet() {
        return mpgSet;
    }

    public void setMpgSet(List<MPGData> mpgSet) {
        this.mpgSet = mpgSet;
    }
}
