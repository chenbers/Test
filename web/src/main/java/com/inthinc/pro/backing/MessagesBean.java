package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.Vehicle;

public class MessagesBean extends BaseBean {
    
    protected final static String BLANK_SELECTION = "&#160;";    
    
    private List<MessageItem> messageList;
    private List<MessageItem> sendMessageList;
    private Boolean selectAll;
    private String messageToSend;
    private String mailingList;
    private List<String> sentMessages;
    private Integer pageNumber;
    private String sendTo;
    private List<SelectItem> driverSelectFromList;
    private List<SelectItem> vehicleSelectFromList; 
    private List<SelectItem> groupSelectFromList;    
    private List<Integer> driverSelectedList;
    private List<Integer> vehicleSelectedList;
    private List<Integer> groupSelectedList;    
    private Integer selectedGroupID;
    
    private PersonDAO personDAO;
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private DeviceDAO deviceDAO;
    private VehicleDAO vehicleDAO;
    private TextMsgAlertDAO textMsgAlertDAO;

    public MessagesBean()
    {
        mailingList = new String();
        messageList = new ArrayList<MessageItem>();
        sendMessageList = new ArrayList<MessageItem>();
        sentMessages = new ArrayList<String>();
        pageNumber = 1;
        
        driverSelectFromList = new ArrayList<SelectItem>();
        driverSelectedList = new ArrayList<Integer>();
        
        vehicleSelectFromList = new ArrayList<SelectItem>();
        vehicleSelectedList = new ArrayList<Integer>();
        
        groupSelectFromList = new ArrayList<SelectItem>();
        groupSelectedList = new ArrayList<Integer>();        
    }

    public List<MessageItem> getMessageList() {
        return messageList;
    }
    
    public void setMessageList(List<MessageItem> messageList) {
        this.messageList = messageList;
    }
    
    public List<MessageItem> getSendMessageList() {
        return sendMessageList;
    }

    public void setSendMessageList(List<MessageItem> sendMessageList) {
        this.sendMessageList = sendMessageList;
    }

    public Boolean getSelectAll() {
        return selectAll;
    }

    public void setSelectAll(Boolean selectAll) {
        this.selectAll = selectAll;
    }

    public String getMessageToSend() {
        return messageToSend;
    }

    public void setMessageToSend(String messageToSend) {
        this.messageToSend = messageToSend;
    }

    public String getMailingList() {
        return mailingList;
    }

    public void setMailingList(String mailingList) {
        this.mailingList = mailingList;
    }

    public List<String> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<String> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public List<SelectItem> getDriverSelectFromList() {
        
        if ( this.driverSelectFromList.size() == 0 ) {
            List<Driver> drivers = driverDAO.getAllDrivers(this.getProUser().getGroupHierarchy().getTopGroup().getGroupID());
            
            for ( Driver d: drivers ) {
                SelectItem si = new SelectItem();
                si.setLabel(d.getPerson().getFullName());
                si.setValue(d.getDriverID());
                driverSelectFromList.add(si);
            }
        }
        return driverSelectFromList;
    }

    public void setDriverSelectFromList(List<SelectItem> driverSelectFromList) {
        this.driverSelectFromList = driverSelectFromList;
    }

    public List<SelectItem> getVehicleSelectFromList() {
        
        if ( this.vehicleSelectFromList.size() == 0 ) {
            List<Vehicle> vehicles = vehicleDAO.getVehiclesInGroupHierarchy(this.getProUser().getGroupHierarchy().getTopGroup().getGroupID());
            
            for (Vehicle v: vehicles ) {
                SelectItem si = new SelectItem();
                si.setLabel(v.getFullName());
                si.setValue(v.getVehicleID());
                vehicleSelectFromList.add(si);
            }
        }
        return vehicleSelectFromList;
    }

    public void setVehicleSelectFromList(List<SelectItem> vehicleSelectFromList) {
        this.vehicleSelectFromList = vehicleSelectFromList;
    }

    public List<SelectItem> getGroupSelectFromList() {
        
        if ( this.groupSelectFromList.size() == 0 ) {
            List<Group> groups = groupDAO.getGroupHierarchy(this.getProUser().getUser().getPerson().getAcctID(), 
                    this.getProUser().getUser().getGroupID());
            
            for ( Group g: groups ) {
                SelectItem si = new SelectItem();
                si.setLabel(g.getName());
                si.setValue(g.getGroupID());
                groupSelectFromList.add(si);
            }
        }
        return groupSelectFromList;
    }

    public void setGroupSelectFromList(List<SelectItem> groupSelectFromList) {
        this.groupSelectFromList = groupSelectFromList;
    }    

    public List<Integer> getDriverSelectedList() {
        return driverSelectedList;
    }

    public void setDriverSelectedList(List<Integer> driverSelectedList) {
        this.driverSelectedList = driverSelectedList;
    }

    public List<Integer> getVehicleSelectedList() {
        return vehicleSelectedList;
    }

    public void setVehicleSelectedList(List<Integer> vehicleSelectedList) {
        this.vehicleSelectedList = vehicleSelectedList;
    }

    public List<Integer> getGroupSelectedList() {
        return groupSelectedList;
    }

    public void setGroupSelectedList(List<Integer> groupSelectedList) {
        this.groupSelectedList = groupSelectedList;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public TextMsgAlertDAO getTextMsgAlertDAO() {
        return textMsgAlertDAO;
    }

    public void setTextMsgAlertDAO(TextMsgAlertDAO textMsgAlertDAO) {
        this.textMsgAlertDAO = textMsgAlertDAO;
    }

    public void doSelectAll() {
        for ( MessageItem mi: messageList ) {
            mi.setSelected(selectAll);
        }
    }
    
    public void refreshList() {
//        The select of inbox values goes here
        
        this.selectAll=Boolean.FALSE;
    }
        
    public void sendMessage() {
        this.sentMessages.clear();
        
        // Drivers
        for ( Integer d: this.driverSelectedList ) {
            Vehicle v = vehicleDAO.findByDriverID(d);
            if ( v.getDeviceID() != null ) {
                Device dev = deviceDAO.findByID(v.getDeviceID());
                
                // Populate this with id and payload
//                ForwardCommand fwdCmd = new ForwardCommand();
//                deviceDAO.queueForwardCommand(dev.getDeviceID(), fwdCmd);
                this.sentMessages.add(dev.getName());
            } else {
                this.sentMessages.add("No device found");
            }
        }
        
        // Vehicles
        for ( Integer v: this.vehicleSelectedList ) {
            Vehicle veh = vehicleDAO.findByID(v);
            if ( veh.getDeviceID() != null ) {
                Device dev = deviceDAO.findByID(veh.getDeviceID());
                
                // Populate this with id and payload
//                ForwardCommand fwdCmd = new ForwardCommand();
//                deviceDAO.queueForwardCommand(dev.getDeviceID(), fwdCmd);                
                this.sentMessages.add(dev.getName());
            } else {
                this.sentMessages.add("No device found");
            }            
        }
        
        // Groups, careful here, need to recurse the group hierarchy for a selected group across
        //  drivers and vehicles and devices or?
        for ( Integer g: this.groupSelectedList ) {
            
            // Populate this with id and payload
//            ForwardCommand fwdCmd = new ForwardCommand();
//            deviceDAO.queueForwardCommand(dev.getDeviceID(), fwdCmd);            
        }

        this.messageToSend = "";
        this.driverSelectedList = new ArrayList<Integer>();
        this.vehicleSelectedList = new ArrayList<Integer>();
        this.groupSelectedList = new ArrayList<Integer>();
    }
       
    public void resetMailingList() {
        // This will clear-out the list and make all the selected messages not selected
        mailingList = "";
        messageToSend = "";
        
        for ( MessageItem mi: this.messageList ) {
            mi.setSelected(Boolean.FALSE);
        }
        
        this.sendMessageList.clear(); 
        this.selectAll = Boolean.FALSE;
    }
       
    public void loadMailingList() {

        // Anything selected from the inbox?  If so, load as selected drivers.
        if ( this.messageList.size() > 0 ) {
            StringBuffer sb = new StringBuffer();
            for ( MessageItem mi: this.messageList ) {
                if ( mi.getSelected() ) {
                    sb.append(mi.getToFrom().trim());
                    sb.append(";");
                }
            }

            if ( !sb.toString().isEmpty() ) {
                this.mailingList = sb.toString();
            }
        } else {
            this.mailingList = new String();
        }
    }

    protected static void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return o1.getLabel().toLowerCase().compareTo(
                        o2.getLabel().toLowerCase());
            }
        });
    }

    public List<SelectItem> getTeams() {
        final List<SelectItem> teams = new ArrayList<SelectItem>();
        SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
        blankItem.setEscape(false);
        teams.add(blankItem);
        for (final Group group : getGroupHierarchy().getGroupList()) {
            String fullName = getGroupHierarchy().getFullGroupName(
                    group.getGroupID());
            if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
                fullName = fullName.substring(0, fullName.length()
                        - GroupHierarchy.GROUP_SEPERATOR.length());
            }

            teams.add(new SelectItem(group.getGroupID(), fullName));

        }
        sort(teams);

        return teams;
    }

    public Integer getSelectedGroupID() {
        return selectedGroupID;
    }

    public void setSelectedGroupID(Integer selectedGroupID) {
        this.selectedGroupID = selectedGroupID;
    }
}