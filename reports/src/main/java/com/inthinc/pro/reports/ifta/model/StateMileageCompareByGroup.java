package com.inthinc.pro.reports.ifta.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean for MileageByVehicle report.
 */
@XmlRootElement(name="stateComparison")
public class StateMileageCompareByGroup {

    private String groupName;
    private String state;
    private String month;
    private Double total;

    /**
     * Default constructor.
     */
    public StateMileageCompareByGroup() {}

    /**
     * The distance getter.
     * 
     * @return the distance
     */
    public Double getTotal() {
        return this.total;
    }

    /**
     * The state getter.
     * 
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * The groupName getter.
     * 
     * @return the groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * The month getter.
     * 
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * The state setter.
     * 
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * The distance setter.
     * 
     * @param total
     *            the distance to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * The groupName setter.
     * 
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * The month setter.
     * 
     * @param month
     *            the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

}
