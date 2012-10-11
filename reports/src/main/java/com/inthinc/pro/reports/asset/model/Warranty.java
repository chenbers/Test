package com.inthinc.pro.reports.asset.model;

import java.util.Date;

/**
 * Bean for warranty report.
 */
public class Warranty {

    private String vehicleName;
    private String imei;
    private String warrantyStartDate;
    private String warrantyEndDate;
    private Boolean expired;
    private String groupName;
    private String customerName;
    private String customerId;

    /**
     * Default constructor.
     */
    public Warranty() {}

    /**
     * @return the vehicleName
     */
    public String getVehicleName() {
        return vehicleName;
    }

    /**
     * @param vehicleName
     *            the vehicleName to set
     */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    /**
     * @return the imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei
     *            the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return the warrantyStartDate
     */
    public String getWarrantyStartDate() {
        return warrantyStartDate;
    }

    /**
     * @param warrantyStartDate
     *            the warrantyStartDate to set
     */
    public void setWarrantyStartDate(String warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    /**
     * @return the warrantyEndDate
     */
    public String getWarrantyEndDate() {
        return warrantyEndDate;
    }

    /**
     * @param warrantyEndDate
     *            the warrantyEndDate to set
     */
    public void setWarrantyEndDate(String warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    /**
     * @return the expired
     */
    public Boolean getExpired() {
        return expired;
    }

    /**
     * @param expired
     *            the expired to set
     */
    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
