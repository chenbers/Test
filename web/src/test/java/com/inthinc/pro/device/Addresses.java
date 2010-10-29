package com.inthinc.pro.device;

public enum Addresses {
    
    DEV_PORTAL("dev-pro.inthinc.com"),
    DEV_PORT("8099"),
    DEV_MCM("dev-pro.inthinc.com"),
    DEV_MCM_PORT("8090"),

    QA_PORTAL("qa.tiwipro.com"),
    QA_PORT("8199"),
    QA_MCM("qa.tiwipro.com"),
    QA_MCM_PORT("8190"),

    TEEN_MCM_QA("192.168.1.215"),
    TEEN_MCM_PORT_QA("8090"),
    TEEN_PORTAL_QA("192.168.1.215"),
    TEEN_PORT_QA("8099"),

    PROD_MCM("www.tiwipro.com"),
    PROD_MCM_PORT("8090"),
    PROD_PORTAL("67.208.138.210"),
    PROD_PORT("8099");

    
    private String code;

    private Addresses(String c) {
      code = c;
    }

    public String getCode() {
      return code;
    }



}
