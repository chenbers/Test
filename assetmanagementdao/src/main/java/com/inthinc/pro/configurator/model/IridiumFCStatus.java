package com.inthinc.pro.configurator.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum IridiumFCStatus {
    
    SUCCESS(1, "Success"),
    SUCCESS_NO_PAYLOAD(0, "Success No Payload"),
    ERROR_INVALID_IMEI(-1, "Invalid IMEI"),
    ERROR_UNKNOWN_IMEI(-2, "Unknown IMEI"),
    ERROR_PAYLOAD_TOO_BIG(-3, "Payload size exceeded maximum allowed"),
    ERROR_PAYLOAD_EXPECTED(-4, "Payload expected, non received"),
    ERROR_MESSAGE_QUEUE_FULL(-5, "MT message queue full"),
    ERROR_MESSAGE_QUEUE_UNAVAILABLE(-6, "MT resource unavailable"),
    ERROR_PROTOCOL_VIOLATION(-7, "Violation of MT DirectIP Protocol"),
    ERROR_IMEI_RING_ALERTS_DISABLED(-8, "Ring alerts to the given IMEI are disabled"),
    ERROR_IMEI_NOT_ATTACHED(-9, "The given IMEI is not attached (not set to receive ring alerts)"),
    ERROR_UNKNOWN(-10, "Unknown error");



    private int code;
    private String description;
    
    private IridiumFCStatus(int code, String description)
    {
        this.code = code;
        this.description = description;
    }
    
    
    private static final Map<Integer, IridiumFCStatus> lookup = new HashMap<Integer, IridiumFCStatus>();
    static
    {
        for (IridiumFCStatus p : EnumSet.allOf(IridiumFCStatus.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static IridiumFCStatus valueOf(Integer code)
    {
        if (code > 0)
            return IridiumFCStatus.SUCCESS;
        if (code < -9)
            return IridiumFCStatus.ERROR_UNKNOWN;
        return lookup.get(code);
    }
    
    public Integer getCode()
    {
        return code;
    }
    public String getDescription()
    {
        return description;
    }


}
