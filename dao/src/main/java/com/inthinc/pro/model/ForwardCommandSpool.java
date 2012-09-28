package com.inthinc.pro.model;

import java.util.Date;

public class ForwardCommandSpool extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer fwdID;
    ForwardCommandParamType dataType;
    private Integer deviceID;
    private String address;
    private Integer command;
    private byte[] data;
    private String strData;
    private Integer intData;
    private Integer processed;
    private Date created;
    private Date modified;
    ForwardCommandStatus status;
    IridiumFCStatus iridiumStatus;
    

    public ForwardCommandSpool() 
    {
    }
    
    public ForwardCommandSpool(String strData, Integer command, String address) 
    {
        this.command = command;
        this.address = address;
        this.strData = strData;
        this.intData = -1;
        this.dataType = ForwardCommandParamType.STRING; 
        
        if (strData != null)
        {
            int len = strData.getBytes().length;
            byte[] dataBytes = new byte[len + 1];
            
            byte[] strDataBytes = strData.getBytes(); 
            for (int i = 0; i < len; i++)
            {
                dataBytes[i] = strDataBytes[i];
            }
            dataBytes[len] = 0x0;
            
            this.data = dataBytes; 
            
        }
    }

    public ForwardCommandSpool(Integer intData, Integer command, String address) 
    {
        this.command = command;
        this.address = address;
        this.intData = intData;
        this.strData = null;
        this.dataType = ForwardCommandParamType.INTEGER; 
        
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

    public ForwardCommandSpool(byte[] data, Integer command, String address) 
    {
        this.command = command;
        this.address = address;
        this.intData = null;
        this.strData = null;
        this.data = data;
        this.dataType = ForwardCommandParamType.BINARY; 
    }

    
    public Integer getFwdID() {
        return fwdID;
    }

    public void setFwdID(Integer fwdID) {
        this.fwdID = fwdID;
    }
    
    public ForwardCommandParamType getDataType() {
        return dataType;
    }

    public void setDataType(ForwardCommandParamType dataType) {
        this.dataType = dataType;
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


    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
    public ForwardCommandStatus getStatus() {
        return status;
    }

    public void setStatus(ForwardCommandStatus status) {
        this.status = status;
    }
    public IridiumFCStatus getIridiumStatus() {
        return iridiumStatus;
    }

    public void setIridiumStatus(IridiumFCStatus iridiumStatus) {
        this.iridiumStatus = iridiumStatus;
    }

}
