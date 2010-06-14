package com.inthinc.pro.backing;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.validators.EmailValidator;

public class AccountBean extends BaseAdminBean<AccountBean.AccountView> {   

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Map<String, TimeZone> TIMEZONES;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;    
    private static final String REQUIRED_KEY = "required";
    private static final char [] INVALID = {'(', ')', '-', '~', '`', '!',
        '@', '#', '$', '%', '^', '&', '*', '_', '+', '=', '|', '\\'};
    
    static {
        // time zones
        final List<String> timeZones = SupportedTimeZones.getSupportedTimeZones();
        // sort by offset from GMT
        Collections.sort(timeZones, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                final TimeZone t1 = TimeZone.getTimeZone(o1);
                final TimeZone t2 = TimeZone.getTimeZone(o2);
                return t1.getRawOffset() - t2.getRawOffset();
            }
        });
        TIMEZONES = new LinkedHashMap<String, TimeZone>();
        final NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(2);
        for (final String id : timeZones) {
            final TimeZone timeZone = TimeZone.getTimeZone(id);
            final int offsetHours = timeZone.getRawOffset() / MILLIS_PER_HOUR;
            final int offsetMinutes = Math.abs((timeZone.getRawOffset() % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE);
            if (offsetHours < 0)
                TIMEZONES.put(timeZone.getID() + " (GMT" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
            else
                TIMEZONES.put(timeZone.getID() + " (GMT+" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
        }
    }      
    private PersonDAO personDAO;
    private WMSConfigurationBean wmsConfigurationBean;
//    private ExternalConfigBean externalConfigBean;
//    private AccountOptionsBean accountOptionsBean;

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public WMSConfigurationBean getWmsConfigurationBean() {
        return wmsConfigurationBean;
    }

    public void setWmsConfigurationBean(WMSConfigurationBean wmsConfigurationBean) {
        this.wmsConfigurationBean = wmsConfigurationBean;
    }

//    public ExternalConfigBean getExternalConfigBean() {
//        return externalConfigBean;
//    }
//
//    public void setExternalConfigBean(ExternalConfigBean externalConfigBean) {
//        this.externalConfigBean = externalConfigBean;
//    }

//    public AccountOptionsBean getAccountOptionsBean() {
//        return accountOptionsBean;
//    }
//
//    public void setAccountOptionsBean(AccountOptionsBean accountOptionsBean) {
//        this.accountOptionsBean = accountOptionsBean;
//    }

    @Override
    protected Boolean authorizeAccess(AccountView item) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected AccountView createAddItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void doDelete(List<AccountView> deleteItems) {
        // TODO Auto-generated method stub        
    }

    @Override
    protected void doSave(List<AccountView> saveItems, boolean create) {

        for (final AccountView a : saveItems) {
            // Update                            
            getAccountDAO().update(a);           
            personDAO.update(a.getPerson());
            
            // Communicate to other aspects of the system
            getUnknownDriver().setPerson(a.getPerson()); 
            
            // Map server
            wmsConfigurationBean.setLayerQueryParam(a.getProps().getWmsLayerQueryParam());
            wmsConfigurationBean.setLayers(a.getProps().getWmsLayers());
            wmsConfigurationBean.setQuery(a.getProps().getWmsQuery());
            wmsConfigurationBean.setUrl(a.getProps().getWmsURL());  
            
            // Support numbers
//            externalConfigBean.setPhoneData();
            
            // Alerts on
//            accountOptionsBean.setEnablePhoneAlerts(
//                    a.getProps().getPhoneAlertsActive().equalsIgnoreCase(MessageUtil.getMessageString("editAccount_yes"))?true:false);
            
            getProUser().setAccountAttributes(a.getProps());
        }  
    }

    @Override
    public List<String> getAvailableColumns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getDisplayRedirect() {
        //  Reload the view page       
        return "pretty:adminAccount";
    }

    @Override
    protected String getEditRedirect() {
        // TODO Auto-generated method stub
        return "pretty:adminEditAccount";
    }

    @Override
    protected String getFinishedRedirect() {
        return "pretty:adminAccount";
    }

    public Map<String, TimeZone> getTimeZones() {
        return TIMEZONES;
    }
    
    @Override
    protected List<AccountView> loadItems() {       
        final List<AccountView> items = new ArrayList<AccountView>();
        
        Person udp = getUnknownDriver().getPerson();
        Account a = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());        
        items.add(createAccountView(a,udp));
        
        return items;
    }

    private AccountView createAccountView(Account a,Person udp) {
        final AccountView accountView = new AccountView();

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
        
        Person udp = getUnknownDriver().getPerson();
        Account a = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());  
        
        return createAccountView(a,udp);
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
        
        // look for http:// or https:// in the url, should it be provided
        if ( saveItem.getProps().getWmsURL().trim().length() != 0 ) {
            
            if ( (saveItem.getProps().getWmsURL().indexOf("https://") == -1) &&
                 (saveItem.getProps().getWmsURL().indexOf("http://") == -1) ) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    MessageUtil.getMessageString("editAccount_bad_url"), null);
                context.addMessage("edit-form:editAccount-url", message);
            }
            
        }
        
        // check that phone alerts are on/off
        if ( saveItem.getProps().getPhoneAlertsActive() == null ) {            
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                MessageUtil.getMessageString("editAccount_bad_phone_alerts"), null);
            context.addMessage("edit-form:editAccount-phoneAlerts", message);            
        }
/*        
        // check that certain characters aren't present
        if ( !digitCheck(saveItem.getProps().getSupportPhone1()) ) {            
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                MessageUtil.getMessageString("editAccount_bad_phone_characters"), null);
            context.addMessage("edit-form:editAccount-supportPhone1", message);            
        }
        
        // check that certain characters aren't present, if data is provided
        if ( (saveItem.getProps().getSupportPhone2().trim().length() != 0) && 
             (!digitCheck(saveItem.getProps().getSupportPhone2())) ) {            
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                MessageUtil.getMessageString("editAccount_bad_phone_characters"), null);
            context.addMessage("edit-form:editAccount-supportPhone2", message);            
        }      
*/        
        // even number of layers?
        if ( !parseLayers(saveItem.getProps().getWmsLayers()) ) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                MessageUtil.getMessageString("editAccount_bad_number_of_layers"), null);
            context.addMessage("edit-form:editAccount-layers", message);                  
        }
        
        return valid;
    }
/*    
    private boolean digitCheck(String str) {
        
        // It can't contain only numbers and letters if it's null or empty...
        if (str == null || str.length() == 0) {
            return false;
        }

        // Any baddies?
        for ( int i = 0; i < str.length(); i++ ) {
            for ( int j = 0; j < INVALID.length; j++ ) {
                if ( str.charAt(i) == INVALID[j] ) {
                    return false;
                }
            }
        }
        
        return true;
    }
*/    
    private boolean parseLayers(String layers) {
        StringTokenizer st = new StringTokenizer(layers," ");
        
        if ( (st.countTokens() % 2) == 0 ) {
            return true;
        }
        
        return false;
    }
    
    public void validateEmail(FacesContext context, UIComponent component, Object value)
    {
        String valueStr = (String) value;
        if (valueStr != null && valueStr.length() > 0)
        {
            new EmailValidator().validate(context, component, value);
        }
    }    

    @Override
    public String getColumnLabelPrefix() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TableType getTableType() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static class AccountView extends Account implements EditItem {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private Person person;

        @Column(updateable = false)        
        private boolean selected;
        
        public AccountView() {
            super();
            this.person = new Person();
        }
        
        @Override
        public Integer getId() {
            return getAcctID();
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public void setSelected(boolean selected) {
            // TODO Auto-generated method stub
            this.selected = selected;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

    }    
}
