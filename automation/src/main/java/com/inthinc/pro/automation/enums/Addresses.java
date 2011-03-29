package com.inthinc.QA.enums;

import java.util.EnumSet;
import java.util.HashMap;

public enum Addresses {
    
    DEV_PORTAL("dev-pro.inthinc.com"),
    DEV_PORT("8099"),
    DEV_MCM("dev-pro.inthinc.com"),
    DEV_MCM_PORT("8090"),
    
    
    EC2_PORTAL("204.236.172.41"),
    EC2_PORT("8099"),
    EC2_MCM("stage.inthinc.com"),
    EC2_MCM_PORT("8090"),

    QA_PORTAL("qa.tiwipro.com"),
    QA_PORT("8199"),
    QA_MCM("qa.tiwipro.com"),
    QA_MCM_PORT("8190"),
    
    QA2_PORTAL("qa2.tiwipro.com"),
    QA2_PORT("8299"),
    QA2_MCM("qa2.tiwipro.com"),
    QA2_MCM_PORT("8290"),

    TEEN_MCM_QA("192.168.1.215"),
    TEEN_MCM_PORT_QA("8090"),
    TEEN_PORTAL_QA("192.168.1.215"),
    TEEN_PORT_QA("8099"),

    PROD_MCM("my.inthinc.com"),
    PROD_MCM_PORT("8090"),
    PROD_PORTAL("my.inthinc.com"),
    PROD_PORT("8099"),
    
    CHEVRON_MCM("chevron.inthinc.com"),
    CHEVRON_MCM_PORT("8090"),
    CHEVRON_PORTAL("chevron.inthinc.com"),
    CHEVRON_PORT("8099"),
    
    SLB_MCM("schlumberger.inthinc.com"),
    SLB_MCM_PORT("8090"),
    SLB_PORTAL("schlumberger.inthinc.com"),
    SLB_PORT("8099");
    
    
    
//    PROD_MCM_EC2("my.inthinc.com"),
//    PROD_MCM_PORT_EC2("8090"),
//    PROD_PORTAL_EC2("50.16.201.215"),
//    PROD_PORT_EC2("8099");

    
    private String code;

    private Addresses(String c) {
      code = c;
    }

    public String getCode() {
      return code;
    }

}
