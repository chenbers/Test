package com.inthinc.pro.automation.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.inthinc.pro.automation.models.Account;
import com.inthinc.pro.automation.models.Person;
import com.inthinc.pro.automation.utils.AutomationNumberManager;

public class RestFilters {
    
    private static final String masterUserName = "0001";
    private static final String defaultPassword = "password";

    
    private RestCommands rest;
    private Account acct;
    private Person firstLast;
    
    public RestFilters(){
        rest = new RestCommands(masterUserName, defaultPassword);
    }
    
    
    private List<Account> getAutomationAccounts(){
        List<Account> all = rest.getBulk(Account.class);
        List<Account> filtered = new ArrayList<Account>();
        Iterator<Account> itr = all.iterator();
        while(itr.hasNext()){
            Account next = itr.next();
            if (next.getAcctName().startsWith("Auto - ")){
                filtered.add(rest.getObject(Account.class, next.getAccountID()));
            }
        }
        return filtered;
    }
    
//    public User getUserBy
    
    private List<Person> getAccountPersons(){
        List<Person> list = rest.getBulk(Person.class);
        Iterator<Person> itr = list.iterator();
        List<Person> fullList = new ArrayList<Person>();
        while (itr.hasNext()){
            Person person = itr.next();
            Person full = rest.getObject(person.getClass(), person.getPersonID());
            list.add(full);
        }
        return fullList;
    }
    
    public void stopUsingAccount(){
        acct.setAcctName(acct.getAcctName().substring(1));
        rest.postObject(acct.getClass(), acct, null);
    }
    
    
    public void setAccount(){
        acct = getAutomationAccounts().get(0);
        Integer personID = AutomationNumberManager.extract(acct.getAcctName());
        acct.setAcctName(1 + acct.getAcctName());
        rest.postObject(acct.getClass(), acct, null);
        firstLast = rest.getObject(Person.class, personID);
        rest = new RestCommands(firstLast.getUser().getUsername(), defaultPassword);
    }
    
}
