package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

public class SupportBean extends BaseBean {
    
    private String defaultSupportContacts;
    private static final String DELIM = "~";
    public List<String> getSupportContacts() {
        
        List<String> contacts = new ArrayList<String>();
        
        if (isProUserLoggedIn() && getProUser().getAccountAttributes() != null && getProUser().getAccountAttributes().getSupportContacts() != null) {
            // TODO: possibly change e-mails into links
            for (String contact : getProUser().getAccountAttributes().getSupportContacts()) {
                if (contact != null && !contact.isEmpty()) {
                    contacts.add(contact);
                }
            }
                
        }
        
        if (contacts.size() == 0) {
            if (defaultSupportContacts != null) {
                String[] contactArray = defaultSupportContacts.split(DELIM);
                for (String contact : contactArray) {
                    if (contact != null && !contact.isEmpty()) {
                        contacts.add(contact);
                    }
                }
            }
            else {
                // should not happen but fall back
                contacts.add("1-888-888TIWI");
            }
        }
        return contacts;
        
    }
    
    
    public String getDefaultSupportContacts() {
        return defaultSupportContacts;
    }

    public void setDefaultSupportContacts(String defaultSupportContacts) {
        this.defaultSupportContacts = defaultSupportContacts;
    }


}
