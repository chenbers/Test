package com.inthinc.pro.model.zone;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.BaseEntity;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;

    
@XmlRootElement
public class ZonePublish extends BaseEntity implements Cloneable
{
    @Column(updateable = false)
    private static final long serialVersionUID = 7505601232108995094L;

    @ID
    private Integer           zonePublishID;
    
    private Integer acctID;
    private ZoneVehicleType zoneVehicleType;
    private byte[] publishZoneData;
    private Date zonePublishDate;
    
    public ZonePublish() {
        super();
    }
    
    public Integer getZonePublishID() {
        return zonePublishID;
    }
    public void setZonePublishID(Integer zonePublishID) {
        this.zonePublishID = zonePublishID;
    }
    public Integer getAcctID() {
        return acctID;
    }
    public void setAcctID(Integer acctID) {
        this.acctID = acctID;
    }
    public ZoneVehicleType getZoneVehicleType() {
        return zoneVehicleType;
    }
    public void setZoneVehicleType(ZoneVehicleType zoneVehicleType) {
        this.zoneVehicleType = zoneVehicleType;
    }
    public byte[] getPublishZoneData() {
        return publishZoneData;
    }
    public void setPublishZoneData(byte[] publishZoneData) {
        this.publishZoneData = publishZoneData;
    }

    public Date getZonePublishDate() {
        return zonePublishDate;
    }

    public void setZonePublishDate(Date zonePublishDate) {
        this.zonePublishDate = zonePublishDate;
    }
}
