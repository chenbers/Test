package com.inthinc.pro.reports.hos.model;

import com.inthinc.hos.model.RuleViolationTypes;

public class Violation {
    RuleViolationTypes type;
    Long minutes;
    
    public Violation(RuleViolationTypes type, Long minutes) {
        super();
        this.type = type;
        this.minutes = minutes;
    }
    public RuleViolationTypes getType() {
        return type;
    }
    public void setType(RuleViolationTypes type) {
        this.type = type;
    }
    public Long getMinutes() {
        return minutes;
    }
    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }
}
