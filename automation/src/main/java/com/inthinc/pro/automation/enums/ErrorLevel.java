package com.inthinc.pro.automation.enums;

import com.inthinc.pro.rally.TestCaseResult.Verdicts;


public enum ErrorLevel {
    FATAL_ERROR(Verdicts.INCONCLUSIVE),
    FATAL(Verdicts.FAIL),
    FAIL(Verdicts.FAIL),
    ERROR(Verdicts.ERROR),
    INCONCLUSIVE(Verdicts.INCONCLUSIVE),
    WARN,
    COMPARE,
    PASS,
    ;
    
    private Verdicts verdict;
    
    private ErrorLevel(){
        verdict = Verdicts.PASS;
    }
    
    private ErrorLevel(Verdicts verdict){
        this.verdict = verdict;
    }
    
    public Verdicts getVerdict(){
        return verdict;
    }
}
