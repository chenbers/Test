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

    
    public ForwardCommand()
    {
        super();
    }
    public ForwardCommand(Integer fwdID, Integer cmd, Object data, ForwardCommandStatus status)
    {
        super();
        this.fwdID = fwdID;
        this.cmd = cmd;
        this.data = data;
        this.status = status;
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

}
