package com.inthinc.pro.model.assets;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.BaseEntity;

/**
 * Bean for WarrantyList report records.
 */
@XmlRootElement
public class AssetWarrantyRecord extends BaseEntity {

    private static final long serialVersionUID = -1113714253212715494L;

    private String vehicleName;
    private String imei;
    private String groupId;
    private Date startDate;
    private Date endDate;
    private boolean expired;

    /**
     * The vehicleName getter.
     * 
     * @return the vehicleName
     */
    public String getVehicleName() {
        return this.vehicleName;
    }

    /**
     * The IMEI getter.
     * 
     * @return the imei
     */
    public String getImei() {
        return this.imei;
    }

    /**
     * The groupId getter.
     * 
     * @return the groupId
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * The startDate getter.
     * 
     * @return the startDate
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * The endDate getter.
     * 
     * @return the endDate
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * The expired getter.
     * 
     * @return the expired
     */
    public boolean isExpired() {
        return this.expired;
    }

    /**
     * The vehicleName setter.
     * 
     * @param vehicleName
     *            the vehicleName to set
     */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    /**
     * The IMEI setter.
     * 
     * @param imei
     *            the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * The groupId setter.
     * 
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(String groupName) {
        this.groupId = groupName;
    }

    /**
     * The startDate setter.
     * 
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * The endDate setter.
     * 
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * The expired setter.
     * 
     * @param expired
     *            the expired to set
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
