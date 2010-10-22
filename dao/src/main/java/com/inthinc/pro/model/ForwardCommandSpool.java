package com.inthinc.pro.model;

import java.util.Date;

public class ForwardCommandSpool extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer forwardCommandSpoolID;
    private Integer deviceID;
    private String address;
    private Integer forwardCommandID;
    private Integer command;
    private byte[] data;
    private String strData;
    private Integer intData;
    Boolean processed;
    Date timeSubmitted;
    Date timeProcessed;

    
    public ForwardCommandSpool() 
    {
    }
    
    public ForwardCommandSpool(Integer forwardCommandID, String strData, Integer command, String address) 
    {
        this.forwardCommandID = forwardCommandID;
        this.command = command;
        this.address = address;
        this.strData = strData;
        this.intData = null;
        
        if (strData != null)
        {
            int len = strData.getBytes().length;
            byte[] dataBytes = new byte[len];
            
            byte[] strDataBytes = strData.getBytes(); 
            for (int i = 0; i < len; i++)
            {
                dataBytes[i] = strDataBytes[i];
            }
            dataBytes[len] = 0x0;
            
            this.data = dataBytes;
            
        }
    }

    public ForwardCommandSpool(Integer forwardCommandID, Integer intData, Integer command, String address) 
    {
        this.forwardCommandID = forwardCommandID;
        this.command = command;
        this.address = address;
        this.intData = intData;
        this.strData = null;
        
        if (intData != null)
        {
            byte[] dataBytes = new byte[4];
            dataBytes[3] = (byte) (intData & 0x000000FF);
            intData = intData >>> 8;
            dataBytes[2] = (byte) (intData & 0x000000FF);
            intData = intData >>> 8;
            dataBytes[1] = (byte) (intData & 0x000000FF);
            intData = intData >>> 8;
            dataBytes[0] = (byte) (intData & 0x000000FF);
            this.data = dataBytes;
        }
    }

    public ForwardCommandSpool(Integer forwardCommandID, byte[] data, Integer command, String address) 
    {
        this.forwardCommandID = forwardCommandID;
        this.command = command;
        this.address = address;
        this.intData = null;
        this.strData = null;
        this.data = data;
    }

    
    public Integer getForwardCommandSpoolID() {
        return forwardCommandSpoolID;
    }

    public void setForwardCommandSpoolID(Integer forwardCommandSpoolID) {
        this.forwardCommandSpoolID = forwardCommandSpoolID;
    }
    
    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }

    public Integer getIntData() {
        return intData;
    }

    public void setIntData(Integer intData) {
        this.intData = intData;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getForwardCommandID() {
        return forwardCommandID;
    }

    public void setForwardCommandID(Integer forwardCommandID) {
        this.forwardCommandID = forwardCommandID;
    }

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Date getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Date timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public Date getTimeProcessed() {
        return timeProcessed;
    }

    public void setTimeProcessed(Date timeProcessed) {
        this.timeProcessed = timeProcessed;
    }
}
