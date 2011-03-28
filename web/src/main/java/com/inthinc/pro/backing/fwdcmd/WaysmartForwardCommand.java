package com.inthinc.pro.backing.fwdcmd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWSIridiumJDBCDAO;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.ForwardCommandType;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.util.MessageUtil;

public abstract class WaysmartForwardCommand {
    
    protected ForwardCommandType selected;
    protected String sentDescription;
    protected String receivedDescription;
    protected String address;
    protected Integer deviceID;
    private FwdCmdSpoolWSIridiumJDBCDAO fcsIridiumDAO;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss (a) z");

    public WaysmartForwardCommand()
    {
    }
    public WaysmartForwardCommand(Integer deviceID, String address, FwdCmdSpoolWSIridiumJDBCDAO fcsIridiumDAO) {
        this.deviceID = deviceID;
        this.address = address;
        this.fcsIridiumDAO = fcsIridiumDAO;
    }

    public abstract List<SelectItem> getSelectItems();
    public List<Integer> getForwardCommandIDs() 
    {
        if (selected == null)
            return null;
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(selected.getCode());
        return idList;

    }
    
    private DateTimeZone getUserTimeZone() {
        ProUser proUser = (ProUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return DateTimeZone.forTimeZone(proUser.getUser().getPerson().getTimeZone());
    } 

    
    
    public String getSentDescription() {
        if (sentDescription == null) {
            initDescriptions();
        }
        return sentDescription;
    }

    private void clearDescriptions() {
        sentDescription = null;
        receivedDescription = null;
    }
    
    private void initDescriptions() {
        ForwardCommandSpool latest = null;
        if (getForwardCommandIDs() != null) {
            List<ForwardCommandSpool> spoolList = fcsIridiumDAO.getForDevice(getDeviceID(), getForwardCommandIDs());
            for (ForwardCommandSpool spoolItem : spoolList) {
                 System.out.println(spoolItem.getCommand() + " " + spoolItem.getProcessed() + " " + spoolItem.getCreated() + " " + spoolItem.getModified());
                 if (latest == null || latest.getCreated().before(spoolItem.getCreated())) 
                        latest = spoolItem;
            }
        }
        if (latest != null) {
            sentDescription = MessageUtil.getMessageString(ForwardCommandType.getForwardCommandType(latest.getCommand()).toString()) + 
                                "<br/>"  + 
                                formatDate(latest.getCreated());
            if (latest.getStatus().equals(ForwardCommandStatus.STATUS_RECEIVED))
                receivedDescription = formatDate(latest.getModified());
            else if (latest.getStatus().equals(ForwardCommandStatus.STATUS_BAD_DATA))
                receivedDescription = formatDate(latest.getModified()) + "<br/>" +  MessageUtil.formatMessageString("forwardCommandReceivedError", latest.getIridiumStatus().getDescription());
            else receivedDescription = MessageUtil.getMessageString("forwardCommandNoneReceived");
        }
        else {

            sentDescription = MessageUtil.getMessageString("forwardCommandNoneSent");
            receivedDescription = MessageUtil.getMessageString("forwardCommandNoneReceived");
        }
    }

    private String formatDate(Date date) {
        return dateTimeFormatter.withZone(getUserTimeZone()).print(date.getTime());
    }

    public void setSentDescription(String sentDescription) {
        this.sentDescription = sentDescription;
    }

    public String getReceivedDescription() {
        return receivedDescription;
    }

    public void setReceivedDescription(String receivedDescription) {
        this.receivedDescription = receivedDescription;
    }

    public ForwardCommandType getSelected() {
        return selected;
    }

    public void setSelected(ForwardCommandType selected) {
        this.selected = selected;
        clearDescriptions();
    }

    public void sendCommand() {
        queueForwardCommand(getAddress(), null, selected.getCode());
        System.out.println("queueForwardCommand() " + getAddress() + " cmdID: " + selected.getCode());
        clearDescriptions();
    }
    
    private void queueForwardCommand(String address, byte[] data, int command) 
    {
        ForwardCommandSpool fcs = new ForwardCommandSpool(data, command, address);
        if (fcsIridiumDAO.add(fcs) == -1)
            throw new ProDAOException("Iridium Forward command spool failed.");
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

}
