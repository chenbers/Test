package com.inthinc.pro.model.hos;

import com.inthinc.pro.model.BaseEntity;

public class HOSOccupantInfo extends BaseEntity {
    
    private String fullName;
    private String empId;
    
    public HOSOccupantInfo(String fullName, String empId) {
        super();
        this.fullName = fullName;
        this.empId = empId;
    }

    public HOSOccupantInfo() {
        super();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
