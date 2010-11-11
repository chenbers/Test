package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class MessagesBean extends BaseBean {
    
    protected final static String BLANK_SELECTION = "&#160;";    
    
    private List<MessageItem> messageList;
    private List<MessageItem> sentMessageList;
    private List<String> sendMessageList;
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
    private Date startDate;
    private Date endDate;    
    
    private PersonDAO personDAO;
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private DeviceDAO deviceDAO;
    private VehicleDAO vehicleDAO;
    private TextMsgAlertDAO textMsgAlertDAO;
    
    private ResourceBundle resourceMessages = ResourceBundle.getBundle("com.inthinc.pro.resources.Messages", getLocale());
    
    public MessagesBean()
    {
        mailingList = new String();
        messageList = new ArrayList<MessageItem>();
        sendMessageList = new ArrayList<String>();
        sentMessageList = new ArrayList<MessageItem>();
        sentMessages = new ArrayList<String>();
        pageNumber = 1;
        
        driverSelectFromList = new ArrayList<SelectItem>();
        driverSelectedList = new ArrayList<Integer>();
        
        vehicleSelectFromList = new ArrayList<SelectItem>();
        vehicleSelectedList = new ArrayList<Integer>();
        
        groupSelectFromList = new ArrayList<SelectItem>();
        groupSelectedList = new ArrayList<Integer>();  
        
        this.setEndDate(endDate != null? endDate: new Date());
        this.setStartDate(startDate!=null? startDate: DateUtil.getDaysBackDate(new Date(), 7));
    }
    
    public Integer getMessageListCount(){
        return (messageList!=null)?messageList.size():0;
    }
    
    public Integer getSentMessageListCount(){
        return (sentMessageList!=null)?sentMessageList.size():0;
    }
    
    public List<MessageItem> getMessageList() {
        return messageList;
    }
    
    public void setMessageList(List<MessageItem> messageList) {
        this.messageList = messageList;
    }
    
    public List<String> getSendMessageList() {
        return sendMessageList;
    }

    public void setSendMessageList(List<String> sendMessageList) {
        this.sendMessageList = sendMessageList;
    }
    
    public List<MessageItem> getSentMessageList() {
        return sentMessageList;
    }

    public void setSentMessageList(List<MessageItem> sentMessageList) {
        this.sentMessageList = sentMessageList;
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

        // Do filter here to check for associated device to be waysmart?
        // TODO: jwimmer: question: do we want to filter the list so that non-textMsg drivers and vehicles don't even appear?
        if (this.driverSelectFromList.size() == 0) {
            List<Driver> drivers = driverDAO.getAllDrivers(this.getProUser().getGroupHierarchy().getTopGroup().getGroupID());

            for (Driver d : drivers) {
                if (d != null) {
                    Vehicle v = vehicleDAO.findByDriverID(d.getDriverID());
                    if (v != null && deviceDAO.findByID(v.getDeviceID()).isTextMsgReceiveCapable()) {
                        SelectItem si = new SelectItem();
                        si.setLabel(d.getPerson().getFullName() != null ? d.getPerson().getFullName() : resourceMessages.getString("unknown_driver"));
                        si.setValue(d.getDriverID());
                        driverSelectFromList.add(si);
                    }
                }
            }
        }
        return driverSelectFromList;
    }

    public void setDriverSelectFromList(List<SelectItem> driverSelectFromList) {
        this.driverSelectFromList = driverSelectFromList;
    }

    public List<SelectItem> getVehicleSelectFromList() {

        // Do filter here to check for associated device to be waysmart?
        // TODO: jwimmer: question: do we want to filter the list so that non-textMsg drivers and vehicles don't even appear?
        if (this.vehicleSelectFromList.size() == 0) {
            List<Vehicle> vehicles = vehicleDAO.getVehiclesInGroupHierarchy(this.getProUser().getGroupHierarchy().getTopGroup().getGroupID());

            for (Vehicle v : vehicles) {
                if (v != null) {
                    Device d = deviceDAO.findByID(v.getDeviceID());
                    if (d!=null && d.isTextMsgReceiveCapable()) {
                        SelectItem si = new SelectItem();
                        si.setLabel(v.getFullName() != null ? v.getFullName() : resourceMessages.getString("unknown_vehicle"));
                        si.setValue(v.getVehicleID());
                        vehicleSelectFromList.add(si);
                    }
                }
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
                si.setLabel(g.getName()!=null?g.getName():resourceMessages.getString("unknown_group"));
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
        for (final MessageItem mi : messageList)
            mi.setSelected(selectAll);
    }
    
    public void refreshInbox() { 
        this.messageList.clear();
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        PageParams pageParams = new PageParams(); 
        if(selectedGroupID != null)
            this.messageList.addAll(textMsgAlertDAO.getTextMsgPage(selectedGroupID, startDate, endDate, filterList, pageParams));
        this.selectAll=Boolean.FALSE;
    }
    
    /**
     * Removes the filter from the current view.
     */
    public void removeFilter() {
        this.sentMessageList.clear();
        this.messageList.clear();
        this.selectAll=Boolean.FALSE;
    }
    
    public void refreshSent() {
        this.sentMessageList.clear();        
        this.sentMessageList.addAll(textMsgAlertDAO.getSentTextMsgsByGroupID(selectedGroupID, startDate, endDate));
        this.selectAll=Boolean.FALSE;
    }
       
    private Set<Integer> getDevicesForDrivers() {
        Set<Integer> results = new HashSet<Integer>();
        for (Integer d : this.driverSelectedList) {
            Vehicle v = vehicleDAO.findByDriverID(d);
            if (v != null && v.getDeviceID() != null) {
                results.add(v.getDeviceID());
            }
            else
            {   
                this.sendMessageList.add(String.format(resourceMessages.getString("txtMsg_sendMsgDriverNoDevice"), driverDAO.findByID(d).getPerson().getFullName()));
            }
        }
        return results;
    }

    private Set<Integer> getDevicesForVehicles() {
        Set<Integer> results = new HashSet<Integer>();
        for (Integer vID : this.vehicleSelectedList) {
            Vehicle v = vehicleDAO.findByID(vID);
            if (v != null && v.getDeviceID() != null) {
                results.add(v.getDeviceID());
            }
            else
            {
                this.sendMessageList.add(String.format(resourceMessages.getString("txtMsg_sendMsgVehicleNoDevice"), v.getFullName()));
            }
        }
        return results;
    }

    private Set<Integer> getDevicesForGroups() {
        Set<Integer> results = new HashSet<Integer>();
        for (Integer grp : this.groupSelectedList) {
            List<Group> sub = groupDAO.getGroupHierarchy(this.getProUser().getUser().getPerson().getAcctID(), grp);
            for (Group subGrp : sub) {

                // Drivers
                List<Driver> grpDrv = driverDAO.getAllDrivers(subGrp.getGroupID());
                for (Driver d : grpDrv) {
                    Vehicle v = vehicleDAO.findByDriverID(d.getDriverID());
                    if (v != null && v.getDeviceID() != null) {
                        results.add(v.getDeviceID());
                    }
                }
                // Vehicles
                List<Vehicle> grpVeh = vehicleDAO.getVehiclesInGroup(subGrp.getGroupID());
                for (Vehicle v : grpVeh) {
                    if (v != null && v.getDeviceID() != null) {
                        results.add(v.getDeviceID());
                    }
                }
            }
        }
        return results;
    }

    /**
     * Sends <code>this.messageToSend</code> once to each device for all selected drivers/vehicles/groups.
     */
    public void sendMessage() {
        Set<Integer> deviceSendList = new HashSet<Integer>();

        // Send the message by way of forward command to the device
        this.sendMessageList.clear();

        // Drivers
        deviceSendList.addAll(getDevicesForDrivers());

        // Vehicles
        deviceSendList.addAll(getDevicesForVehicles());

        // Groups, careful here, need to recurse the group hierarchy
        deviceSendList.addAll(getDevicesForGroups());
        // send to each device
        for (Integer devID : deviceSendList) {
            sendDevice(devID);
        }

        // Prep for next interaction
        this.messageToSend = "";
        this.driverSelectedList = new ArrayList<Integer>();
        this.vehicleSelectedList = new ArrayList<Integer>();
        this.groupSelectedList = new ArrayList<Integer>();
    }
     
    /**
     * Sends <code>this.messageToSend</code> to the device with <code>devID</code>. Adds <code>this.sendMessageList</code> notifications for each device.
     * 
     * @param devID
     */
    private void sendDevice(Integer devID) {
        boolean success = false;
        if (devID != null) {
            Device dev = deviceDAO.findByID(devID);
            if (dev != null) {
                Vehicle v = vehicleDAO.findByID(dev.getVehicleID());
                Driver d = null;
                if(v != null){
                    d = driverDAO.findByID(v.getDriverID());
                }
                if (dev.isTextMsgReceiveCapable()) {
                    ForwardCommand fwdCmd = new ForwardCommand(0, ForwardCommandID.SEND_TEXT_MESSAGE, this.messageToSend, ForwardCommandStatus.STATUS_QUEUED, getUser().getPersonID(), v.getDriverID(), v.getVehicleID());
                    deviceDAO.queueForwardCommand(devID, fwdCmd);
                    
                    this.sendMessageList.add(String.format(resourceMessages.getString("txtMsg_sendMsgSuccess"), (d!=null)?d.getPerson().getFullName():resourceMessages.getString("unknown_driver"), (v!=null)?v.getFullName():resourceMessages.getString("unknown_vehicle"), dev.getName() ));
                } else {
                    this.sendMessageList.add(String.format(resourceMessages.getString("txtMsg_sendMsgNotCapable"), dev.getName() , (d!=null)?d.getPerson().getFullName():resourceMessages.getString("unknown_driver"), (v!=null)?v.getFullName():resourceMessages.getString("unknown_vehicle")));
                }
                success = true;
            }
        }

        if (!success) {
            this.sendMessageList.add(resourceMessages.getString("txtMsg_noDevice")); 
        }
    }
       
    /**
     * Takes the addresses from the currently selected list of messages and pre-populates them into the sendTo list.
     */
    public void loadMailingList() {
        // ensure that [driver/vehicle]SelectedList(s) are empty
        driverSelectedList = new ArrayList<Integer>();
        vehicleSelectedList = new ArrayList<Integer>();

        for (MessageItem mi : messageList) {
            if (mi.isSelected()) {
                driverSelectedList.add(mi.getFromDriverID());
                vehicleSelectedList.add(mi.getFromVehicleID());
            }
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
        boolean defaultMissing = true;
        for (final Group group : getGroupHierarchy().getGroupList()) {
            String fullName = getGroupHierarchy().getFullGroupName(
                    group.getGroupID());
            if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
                fullName = fullName.substring(0, fullName.length()
                        - GroupHierarchy.GROUP_SEPERATOR.length());
            }

            teams.add(new SelectItem(group.getGroupID(), fullName));
            
            if(defaultMissing) {
                selectedGroupID = group.getGroupID();
                defaultMissing = false;
            }
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}