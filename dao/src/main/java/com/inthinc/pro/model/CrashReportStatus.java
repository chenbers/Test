package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum CrashReportStatus {
    NEW(0),
    CONFIRMED(1);

    private int code;

    private CrashReportStatus(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, CrashReportStatus> lookup = new HashMap<Integer, CrashReportStatus>();
    static
    {
        for (CrashReportStatus p : EnumSet.allOf(CrashReportStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static CrashReportStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }


}
