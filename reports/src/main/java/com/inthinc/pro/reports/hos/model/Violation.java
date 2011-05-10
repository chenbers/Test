package com.inthinc.pro.reports.hos.model;

import java.util.Locale;
import java.util.ResourceBundle;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;

import com.inthinc.hos.model.ViolationsDetails;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.util.MessageUtil;

public class Violation {
    RuleViolationTypes type;
    Long minutes;
    RuleSetType ruleSetType;
    
    ResourceBundle resourceBundle = ReportType.HOS_VIOLATIONS_DETAIL_REPORT.getResourceBundle(Locale.getDefault());

    
    public Violation(RuleSetType ruleSetType, RuleViolationTypes type, Long minutes) {
        this.type = type;
        this.minutes = minutes;
        this.ruleSetType = ruleSetType;
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

    public RuleSetType getRuleSetType() {
        return ruleSetType;
    }
    public void setRuleSetType(RuleSetType ruleSetType) {
        this.ruleSetType = ruleSetType;
    }
    
    public String getDetails() {
        String details = ViolationsDetails.getDetails(ruleSetType, type);
        if (details == null || details.isEmpty()) {
            details = MessageUtil.getBundleString(resourceBundle, "violation." + getType().getName()); 
        }
        return details;
    }

    public String getCfr() {
        return ViolationsDetails.getCFR(ruleSetType, type);
    }
}
