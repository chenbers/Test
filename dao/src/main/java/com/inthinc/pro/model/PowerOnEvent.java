package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PowerOnEvent extends Event
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer resetReason;
    private Integer manualResetReason;
    private Integer firmwareVersion;
    
    
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
}
