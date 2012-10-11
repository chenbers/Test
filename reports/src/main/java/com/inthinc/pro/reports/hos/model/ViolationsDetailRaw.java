package com.inthinc.pro.reports.hos.model;

public class ViolationsDetailRaw extends ViolationsDetail {
    private String violationsHeader_0;
    private String violationsHeader_1;
    private String violationsHeader_2;
    private String violationsHeader_3;
    private String violationsHeader_4;
    private String violationsHeader_5;
    private String violationsHeader_6;
    private Long violations_0;
    private Long violations_1;
    private Long violations_2;
    private Long violations_3;
    private Long violations_4;
    private Long violations_5;
    private Long violations_6;
    
    
    public String getViolationsHeader_0() {
        return getHeader(0);
    }
    public String getViolationsHeader_1() {
        return getHeader(1);
    }
    public String getViolationsHeader_2() {
        return getHeader(2);
    }
    public String getViolationsHeader_3() {
        return getHeader(3);
    }
    public String getViolationsHeader_4() {
        return getHeader(4);
    }
    public String getViolationsHeader_5() {
        return getHeader(5);
    }
    public String getViolationsHeader_6() {
        return getHeader(6);
    }
    public Long getViolations_0() {
        return getMinutes(0);
    }
    public Long getViolations_1() {
        return getMinutes(1);
    }
    public Long getViolations_2() {
        return getMinutes(2);
    }
    public Long getViolations_3() {
        return getMinutes(3);
    }
    public Long getViolations_4() {
        return getMinutes(4);
    }
    public Long getViolations_5() {
        return getMinutes(5);
    }
    public Long getViolations_6() {
        return getMinutes(6);
    }
    private String getHeader(int idx) {
        if (getViolationsList().size() > idx) {
            return getViolationsList().get(idx).getType().toString();
        }
        return "";
    }
    private Long getMinutes(int idx) {
        if (getViolationsList().size() > idx)
            return getViolationsList().get(idx).getMinutes();
        return 0l;
    }

}
