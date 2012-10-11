package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ForwardCommand extends BaseEntity implements Comparable<ForwardCommand>
{
    @ID
    private Integer fwdID;
    private Integer cmd;
    private Object data;        // can be integer or string
    private ForwardCommandStatus status; 
    private Integer personID;
    private Integer driverID;
    private Integer vehicleID;
    
    public ForwardCommand()
    {
        super();
    }
    public ForwardCommand(Integer fwdId, Integer cmd, Object data, ForwardCommandStatus status)
    {
        super();
        this.fwdID = fwdId;
        this.cmd = cmd;
        this.data = data;
        this.status = status;
    }
    public ForwardCommand(Integer fwdId, Integer cmd, Object data, ForwardCommandStatus status, Integer sendingPersonId, Integer receivingDriverId, Integer receivingVehicleId){
        super();
        this.fwdID = fwdId;
        this.cmd = cmd;
        this.data = data;
        this.status = status;
        this.personID = sendingPersonId;
        this.driverID = receivingDriverId;
        this.vehicleID = receivingVehicleId;
    }
    public Integer getCmd()
    {
        return cmd;
    }
    public void setCmd(Integer cmd)
    {
        this.cmd = cmd;
    }
    public Object getData()
    {
        return data;
    }
    public void setData(Object data)
    {
        this.data = data;
    }
    public int compareTo(ForwardCommand forwardCommand)
    {
        return forwardCommand.getFwdID()-getFwdID();
    }
    public ForwardCommandStatus getStatus()
    {
        return status;
    }
    public void setStatus(ForwardCommandStatus status)
    {
        this.status = status;
    }
    public Integer getFwdID()
    {
        return fwdID;
    }
    public void setFwdID(Integer fwdID)
    {
        this.fwdID = fwdID;
    }
    public Integer getPersonID() {
        return personID;
    }
    public void setPersonID(Integer personID) {
        this.personID = personID;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

}
