package com.inthinc.pro.reports.ifta.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Bean for StateMileageByMonth report.
 */
@XmlRootElement(name="monthMileage")
public class StateMileageByMonth {

    private String groupName;
    private String state;
    private Double total;
    private String month;
    @XmlTransient
    private Date date;

    /**
     * Default constructor.
     */
    public StateMileageByMonth() {}

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
     * The month getter.
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * The month setter.
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
