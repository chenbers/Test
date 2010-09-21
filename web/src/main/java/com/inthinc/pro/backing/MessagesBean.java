package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;

public class MessagesBean extends BaseBean {
    private List<MessageItem> messageList;
    private List<MessageItem> sendMessageList;
    private Boolean selectAll;
    private String messageToSend;
    private String mailingList;
    private List<MessageItem> sentMessages;
    private Integer pageNumber;
    private List<String> autoComplete;
    private String sendTo;
    private List<SelectItem> selectFromList;
    private List<Integer> selectedList;
    private List<Driver> drivers;
    
    private PersonDAO personDAO;
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private DeviceDAO deviceDAO;
    private VehicleDAO vehicleDAO;

    public MessagesBean()
    {
//        initMessages();
        
        mailingList = new String();
        messageList = new ArrayList<MessageItem>();
        sendMessageList = new ArrayList<MessageItem>();
        sentMessages = new ArrayList<MessageItem>();
        autoComplete = new ArrayList<String>();
        pageNumber = 1;
        selectFromList = new ArrayList<SelectItem>();
        selectedList = new ArrayList<Integer>();
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

    public List<MessageItem> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<MessageItem> sentMessages) {
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

    public List<SelectItem> getSelectFromList() {
        
        // Determine the logged-in users team, then grab their mates
        if ( this.selectFromList.size() == 0 ) {
            Person p = personDAO.findByID(this.getProUser().getUser().getPersonID());
            drivers = driverDAO.getAllDrivers(p.getDriver().getGroupID());
            
            // Add the device, if one associated, "None" if not
            for ( Driver d: drivers ) {
                SelectItem si = new SelectItem();
                si.setLabel(getName(d));
                si.setValue(d.getDriverID());
                selectFromList.add(si);
            }
        }
        return selectFromList;
    }
    
    private String getName(Driver d) {
        Vehicle v = null;
        try {
            v = vehicleDAO.findByDriverID(d.getDriverID());
        } catch (Exception e) {
            System.out.println("Exception is: " + e.getLocalizedMessage());
        }

        String name = d.getPerson().getFullName();
        if ( v != null && v.getDeviceID() != null ) {
            Device dev = deviceDAO.findByID(v.getDeviceID());
            name +=  "(" + dev.getName() + ")";
        } else {
            name += "(None)";
        }
        
        return name;
    }

    public void setSelectFromList(List<SelectItem> selectFromList) {
        this.selectFromList = selectFromList;
    }

    public List<Integer> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<Integer> selectedList) {
        this.selectedList = selectedList;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
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

    public void doSelectAll() {
        for ( MessageItem mi: messageList ) {
            mi.setSelected(selectAll);
        }
    }
    
    public void removeSelected() {
        List<MessageItem> tmp = new ArrayList<MessageItem>();
        
        for ( MessageItem mi: messageList ) {
            if ( mi.getSelected().booleanValue() ) {
                continue;
            } else {
                tmp.add(mi);
            }
        }
        
        messageList.clear();
        messageList.addAll(tmp);
        sendMessageList.clear();
        sendMessageList.addAll(tmp);
        
        // If this was a delete of all, reset the global indicator to false
        if ( selectAll ) {
            selectAll = Boolean.FALSE;
        }
    }
    
    public void refreshList() {
//        initMessages();
        this.selectAll=Boolean.FALSE;
    }
        
    public void sendMessage() {
        this.sentMessages.clear();
        
        for ( Integer dID: this.selectedList ) {
            MessageItem mi = new MessageItem();
            mi.setSendDate(new Date());
            Driver d = getDriver(dID);
            mi.setEntity(d.getPerson().getFullName());
        
            // We would try the forward command here.  Add any error that happened while trying the forward command
            //  to augment the error of no device associated.
            mi.setResult((getName(d).indexOf("None") == -1)?"Successful":"No device associated, not sent");
            this.sentMessages.add(mi);
        }
    }
    
    private Driver getDriver(Integer id) {
        
        for ( Driver d: this.drivers ) {
            if( d.getDriverID().equals(id) ) {
                return d;
            }
        }
        
        return null;
    }
    
    private boolean alreadySent(String tok) {
        for ( MessageItem mi: this.sentMessages ) {
            if ( mi.getToFrom().equalsIgnoreCase(tok) ) {
                return true;
            }
        }
        
        return false;
    }
    
    private void createSentMessageList(String list) {
        this.sentMessages.clear();
        
        StringTokenizer st = new StringTokenizer(list,";");
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            
            // Last check for duplicates
            if ( (!alreadySent(tok)) && (tok.trim().length() != 0) ) {
                MessageItem mi = new MessageItem();
                mi.setSendDate(new Date());
                mi.setEntity(tok);
                mi.setResult("Successfully sent");
                mi.setToFrom(tok);
                this.sentMessages.add(mi);
            }
        }        
    }

    public List <MessageItem> getCurrentSelections(String currentSelections) {
        List<MessageItem> tmp = new ArrayList<MessageItem>();
        
        if ( currentSelections != null && currentSelections.length() != 0 ) {
            StringTokenizer st = new StringTokenizer(currentSelections,";");
            while (st.hasMoreTokens()) {
                MessageItem mi = new MessageItem();
                mi.setToFrom(st.nextToken().trim());
                tmp.add(mi);
            }
        }
        
        return tmp;
    }
       
    public boolean currentlySelected(String candidate,List<MessageItem> current) {
        for ( MessageItem mi: current ) {
            if ( mi.getToFrom().trim().equalsIgnoreCase(candidate.trim()) ) {
                return true;
            }
        }
        return false;
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
    
    public List<String> autoComplete(Object suggest) {
        
        // Initialize the complete suggestion list
        if ( autoComplete.size() == 0 ) {
            autoComplete = loadAutoComplete();
        }
        
        String pref = (String)suggest;
        ArrayList<String> result = new ArrayList<String>();
        
        // Create list of current selections so as not to duplicate
        List<MessageItem> current = getCurrentSelections(this.sendTo);        
        
        for ( String email: autoComplete ) {
            if ( email.toLowerCase().indexOf(pref) == 0 && !currentlySelected(email.toLowerCase(),current) ) {
                result.add(email + ";");
            }
        }
        
        return result;
    }
                
    public void resetSendTo() {
        this.sendTo = "";
    }

    public void forwardMessage() {
        
        // the "create..." will prepare a no duplicate, no blank list to send
        this.createSentMessageList(this.sendTo);
        
        // prep to start again
        this.sendTo = "";
    }
    
    private List<String> loadAutoComplete() {
        List<String> tmp = new ArrayList<String>();
        
        // Get all the groups then find the top level one
        Integer grpID = 0;
        List<Group> grps = groupDAO.getGroupsByAcctID(this.getProUser().getUser().getPerson().getAcctID());
        for ( Group g: grps ) {
            if ( g.getParentID() == 0 ) {
                grpID = g.getGroupID();
                break;
            }
        }
        
        // Get all the people in the account
        List<Person> people = personDAO.getPeopleInGroupHierarchy(grpID);
        for (Person person : people) {
            if ( person.getPriEmail() != null && person.getPriEmail().length() != 0 )
                tmp.add(person.getPriEmail());
        }  
        
        return tmp;
    }
}