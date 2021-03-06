package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;
import com.inthinc.pro.validators.EmailValidator;

public class AccountBean extends BaseAdminBean<AccountBean.AccountView> {   

    /**
     * 
     */
    private AddressDAO addressDAO;
    private static final long serialVersionUID = 1L;
    private static final Map<String, State> STATES;
    private static final String REQUIRED_KEY = "required";
    
    static {
        // states
        STATES = new TreeMap<String, State>();
        for (final State state : States.getStates().values())
            STATES.put(state.getName(), state);
    }
        
          
    private PersonDAO personDAO;
    

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @Override
    protected Boolean authorizeAccess(AccountView item) {
        return null;
    }

    @Override
    protected AccountView createAddItem() {
        return null;
    }

    @Override
    protected void doDelete(List<AccountView> deleteItems) {
        // TODO Auto-generated method stub        
    }

    @Override
    protected void doSave(List<AccountView> saveItems, boolean create) {

        for (final AccountView a : saveItems) {
            if (a.getAddressID() == null) {
                Integer addrID = getAddressDAO().create(a.getAccountID(), a.getAddress());
                a.setAddressID(addrID);
            }
            else {
                getAddressDAO().update(a.getAddress());
            }
            // Update                            
            getAccountDAO().update(a);   
            personDAO.update(a.getPerson());
            
            // Communicate to other aspects of the system
            getUnknownDriver().setPerson(a.getPerson()); 

            getProUser().setAccountAttributes(a.getProps());
            getProUser().setAccountHOSType(a.getHos());
        }  
    }

    @Override
    public List<String> getAvailableColumns() {
        return null;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        return null;
    }

    @Override
    protected String getDisplayRedirect() {
        //  Reload the view page       
        return "pretty:adminAccount";
    }

    @Override
    protected String getEditRedirect() {
        return "pretty:adminEditAccount";
    }

    @Override
    protected String getFinishedRedirect() {
        return "pretty:adminAccount";
    }

    public Map<String, TimeZone> getTimeZones() {
        return getTimeZonesBean().getTimeZones();
    }
    
    public Map<String, State> getStates() {
        return STATES;
    }
//    public List<CellProviderType> getCellProviderTypes(){
//        
//        return CellProviderType.getAll();
//    }
    @Override
    protected List<AccountView> loadItems() {       
        final List<AccountView> items = new ArrayList<AccountView>();
        
        AccountView accountView = loadAccountView();
        items.add(accountView);
        
        return items;
    }

    private AccountView loadAccountView() {
        Person udp = getUnknownDriver().getPerson();
        Account a = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());  
        
        Integer addressID = a.getAddressID();
        Address address = null;
        if (addressID == null)
            address = new Address();
        else address = getAddressDAO().findByID(addressID);
        a.setAddress(address);

        AccountView accountView = createAccountView(a,udp);
        return accountView;
    }

    private AccountView createAccountView(Account a,Person udp) {
        final AccountView accountView = new AccountView(this);

        // Set the normal values
        BeanUtils.copyProperties(a, accountView);
       
        // Person info
        accountView.getPerson().setPersonID(udp.getPersonID());
        accountView.getPerson().setFirst(udp.getFirst());
        accountView.getPerson().setLast(udp.getLast());
        accountView.getPerson().setTimeZone(udp.getTimeZone());

        accountView.setSelected(true);
        
        return accountView;
    }    

    @Override
    protected AccountView revertItem(AccountView editItem) {
        return loadAccountView();
    }

    @Override
    protected boolean validateSaveItem(AccountView saveItem) {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
       
        // Time Zone
        if ( saveItem.getPerson().getTimeZone() == null) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    MessageUtil.getMessageString(REQUIRED_KEY), null);
            context.addMessage("edit-form:editAccount-timeZone", message);
        }
        // check that phone alerts are on/off
        if ( saveItem.getProps().getPhoneAlertsActive() == null ) {            
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                MessageUtil.getMessageString("editAccount_bad_phone_alerts"), null);
            context.addMessage("edit-form:editAccount-phoneAlerts", message);            
        }
        return valid;
    }

    public void validateEmail(FacesContext context, UIComponent component, Object value)
    {
        String valueStr = (String) value;
        if (valueStr != null && valueStr.length() > 0)
        {
            new EmailValidator().validate(context, component, value);
        }
    }    

    public List<SelectItem> getHOSTypes() {
        return SelectItemUtil.toList(AccountHOSType.class, false, AccountHOSType.EUROPEAN_HOS_SUPPORT);
    }
    public List<SelectItem> getCellProviderSelectItems() {
        List<CellProviderType> providers = CellProviderType.getAll();
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        for (CellProviderType e : providers)
        {
            selectItemList.add(new SelectItem(e,MessageUtil.getMessageString(e.toString())));
        }
        
        return selectItemList;
    }

    
    @Override
    public String getColumnLabelPrefix() {
        return null;
    }

    @Override
    public TableType getTableType() {
        return null;
    }
    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    
    public static class AccountView extends Account implements EditItem {
        @Column(updateable = false)        
        private static final long serialVersionUID = 1L;

        @Column(updateable = false)        
        private Person person;
        
        @Column(updateable = false)        
        private AccountBean bean;

        @Column(updateable = false)        
        private boolean selected;
        
        public AccountView(AccountBean bean) {
            super();
            this.person = new Person();
            this.bean = bean;
        }
        
        @Override
        public Integer getId() {
            return getAccountID();
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public String getTimeZoneDisplay() {
            return bean.getTimeZoneDisplayName(person.getTimeZone());
        }
    }


}
