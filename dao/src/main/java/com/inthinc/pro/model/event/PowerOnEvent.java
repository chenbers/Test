package com.inthinc.pro.model.event;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

@XmlRootElement
public class PowerOnEvent extends Event
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @EventAttrID(name="RESET_REASON")
    private Integer resetReason;
    @EventAttrID(name="MANUAL_RESET_REASON")
    private Integer manualResetReason;
    @EventAttrID(name="FIRMWARE_VERSION")
    private Integer firmwareVersion;

    // Attributes ATTR_RESET_REASON, ATTR_MANUAL_RESET_REASON, ATTR_FIRMWARE_VERSION, ATTR_GPS_LOCK_TIME, [ATTR_DMM_VERSION], [ATTR_DMM_ORIENTATION], [ATTR_DMM_CAL_STATUS], ATTR_PRODUCT_VERSION, [ATTR_VIOLATION_FLAGS]
    private static EventAttr[] eventAttrList = {
        EventAttr.RESET_REASON,
        EventAttr.MANUAL_RESET_REASON,
        EventAttr.FIRMWARE_VERSION,
        EventAttr.GPS_LOCK_TIME
    };
    
    public PowerOnEvent()
    {
        super();
    }


    public Integer getResetReason()
    {
        return resetReason;
    }


    public void setResetReason(Integer resetReason)
    {
        this.resetReason = resetReason;
    }


    public Integer getManualResetReason()
    {
        return manualResetReason;
    }


    public void setManualResetReason(Integer manualResetReason)
    {
        this.manualResetReason = manualResetReason;
    }


    public Integer getFirmwareVersion()
    {
        return firmwareVersion;
    }


    public void setFirmwareVersion(Integer firmwareVersion)
    {
        this.firmwareVersion = firmwareVersion;
    }

    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }
}
