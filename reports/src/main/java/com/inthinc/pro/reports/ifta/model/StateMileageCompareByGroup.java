package com.inthinc.pro.reports.ifta.model;

/**
 * Bean for MileageByVehicle report.
 */
public class StateMileageCompareByGroup {

    private String groupName;
    private String state;
    private Double total;
    /**
     * Default constructor.
     */
    public StateMileageCompareByGroup() {
    }

    /**
     * The distance getter.
     * @return the distance
     */
    public Double getTotal() {
        return this.total;
    }
    /**
     * The state getter.
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * The groupName getter.
     * @return the groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * The state setter.
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * The distance setter.
     * @param total the distance to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * The groupName setter.
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
