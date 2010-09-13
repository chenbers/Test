package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.richfaces.event.NodeSelectedEvent;

import com.inthinc.pro.backing.model.BaseTreeNodeImpl;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class MessagesBean extends OrganizationBean {
    private List<MessageItem> messageList;
    private List<MessageItem> sendMessageList;
    private Boolean selectAll;
    private String messageToSend;
    private String mailingList;
    private List<MessageItem> sentMessages;
    private Integer pageNumber;

    public MessagesBean()
    {
        super();
        initMessages();
        mailingList = new String();
        sendMessageList = new ArrayList<MessageItem>();
        sentMessages = new ArrayList<MessageItem>();
        pageNumber = 1;
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

    public void doSelectAll() {
        for ( MessageItem mi: messageList ) {
            mi.setSelected(selectAll);
        }
    }
    
    private void initMessages() {
        messageList = new ArrayList<MessageItem>();
        
        MessageItem mi = new MessageItem();
        
        mi.setToFrom("Device 1117");
        mi.setMessage("Battery low.");
        mi.setSendDate(new Date(2010,6,23));
        mi.setTimeZone(TimeZone.getDefault());
        mi.setSelected(Boolean.FALSE);
        messageList.add(mi);
        mi = new MessageItem();
        
        mi.setToFrom("User 001");
        mi.setMessage("Need to change e-mail.");
        mi.setSendDate(new Date(2010,7,4));
        mi.setTimeZone(TimeZone.getDefault());
        mi.setSelected(Boolean.FALSE);
        messageList.add(mi);
        mi = new MessageItem();
        
        mi.setToFrom("Driver 7891");
        mi.setMessage("Out of hours.");
        mi.setSendDate(new Date(2010,8,28));
        mi.setTimeZone(TimeZone.getDefault());
        mi.setSelected(Boolean.FALSE);
        messageList.add(mi);
        mi = new MessageItem(); 
        
        mi.setToFrom("Device 6701");
        mi.setMessage("Crash detected.");
        mi.setSendDate(new Date(2010,9,1));
        mi.setTimeZone(TimeZone.getDefault()); 
        mi.setSelected(Boolean.FALSE);
        messageList.add(mi);
        mi = new MessageItem();     
        
        mi.setToFrom("User 710");
        mi.setMessage("Please send user manual.");
        mi.setSendDate(new Date(2010,4,8));
        mi.setTimeZone(TimeZone.getDefault());
        mi.setSelected(Boolean.FALSE);
        messageList.add(mi);
        mi = new MessageItem();  
        
        mi.setToFrom("Driver 9810");
        mi.setMessage("Send emergency services.");
        mi.setSendDate(new Date(2010,1,14));
        mi.setTimeZone(TimeZone.getDefault());
        mi.setSelected(Boolean.FALSE);
        messageList.add(mi);
        mi = new MessageItem();     
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
        initMessages();
        this.selectAll=Boolean.FALSE;
    }
    
    public void sendMessage() {
        this.sentMessages.clear();
        
        StringTokenizer st = new StringTokenizer(this.mailingList,";");
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            
            // Last check for duplicates, in case they used a cut/paste buffer
            if ( !alreadySent(tok) ) {
                MessageItem mi = new MessageItem();
                mi.setSendDate(new Date());
                mi.setEntity(tok);
                mi.setResult("Successfully sent");
                mi.setToFrom(tok);
                this.sentMessages.add(mi);
            }
        }
        
        this.pageNumber = 1;
        
        // After send, reset 
        resetMailingList();
        this.mailingList="";
        this.messageToSend="";
    }
    
    private boolean alreadySent(String tok) {
        for ( MessageItem mi: this.sentMessages ) {
            if ( mi.getToFrom().equalsIgnoreCase(tok) ) {
                return true;
            }
        }
        
        return false;
    }
    
    private void createList( BaseTreeNodeImpl btni) {
        Object obj = btni.getBaseEntity();
        MessageItem mi = new MessageItem();
        
        if (        obj instanceof Driver ) {
            mi.setEntity("Driver: " + btni.getLabel());
            mi.setResult("Driver>Vehicle>Device");
            mi.setToFrom(btni.getLabel());
            
        } else if ( obj instanceof Vehicle ) {
            mi.setEntity("Vehicle: " + btni.getLabel());
            mi.setResult("Vehicle>Device");
            mi.setToFrom(btni.getLabel());            
            
        } else if ( obj instanceof Device ) {
            mi.setEntity("Device: "+ btni.getLabel());
            mi.setResult("Done");
            mi.setToFrom(btni.getLabel());            
            
        } else if ( obj instanceof User ) {
            mi.setEntity("User: " + btni.getLabel());
            mi.setResult("User>Driver>Vehicle>Device");
            mi.setToFrom(btni.getLabel());            
            
        } else if ( obj instanceof Group ) {
            List<BaseTreeNodeImpl> kids = btni.getChildrenNodes();
            for ( BaseTreeNodeImpl kid: kids ) {
                createList(kid);
            }
        }
        
        mi.setSendDate(new Date());
        
        // Only load non-group "things"
        boolean group = obj instanceof Group;
        if ( !group ) {
            sendMessageList.add(mi);
        }
    }
    
    public void selectNode(NodeSelectedEvent event) {
        super.selectNode(event);
        
        this.sendMessageList.clear();

        // Create a list of "things" to consider
        createList(this.getSelectedTreeNode()); 
        
        // Create list of current selections so as not to duplicate
        List<MessageItem> current = getCurrentSelections(this.mailingList);
        
        // Create string of additions to current list
        StringBuffer sb = new StringBuffer();
        for(MessageItem mi: sendMessageList) {
            if ( !currentlySelected(mi,current) ) {
                sb.append(mi.getToFrom().trim());
                sb.append(";");
            }
        }
        
        mailingList += sb.toString(); 
    }
    
    public List <MessageItem> getCurrentSelections(String currentSelections) {
        List<MessageItem> tmp = new ArrayList<MessageItem>();
        
        StringTokenizer st = new StringTokenizer(currentSelections,";");
        while (st.hasMoreTokens()) {
            MessageItem mi = new MessageItem();
            mi.setToFrom(st.nextToken().trim());
            tmp.add(mi);
        }
        
        return tmp;
    }
    
    public boolean currentlySelected(MessageItem candidate,List<MessageItem> current) {
        for ( MessageItem mi: current ) {
            if ( mi.getToFrom().trim().equalsIgnoreCase(candidate.getToFrom().trim()) ) {
                return true;
            }
        }
        return false;
    }
    
    public void resetMailingList() {
        // This will clear-out the list and make all the selected messages not selected
        mailingList = "";
        
        for ( MessageItem mi: this.messageList ) {
            mi.setSelected(Boolean.FALSE);
        }
        
        this.sendMessageList.clear(); 
        this.selectAll = Boolean.FALSE;
    }
    
    public void loadMailingList() {
        this.mailingList = new String();
        
        // Anything selected from the inbox?  If so, load as selected drivers.
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
    }
    
    public List<String> findPeople(Object event) {
        final List<String> results = new ArrayList<String>();
        
        // Get the last typed-in value
        String name = event.toString().trim().toLowerCase();
       
        // Any suggestions?
        if (name.length() > 0) {

            for ( MessageItem mi: this.messageList ) {
                if ( mi.getToFrom().toLowerCase().contains(name) ) {
                    results.add(mi.getToFrom() + ";");
                }
            }
        }
        
        return results;
    }    
}