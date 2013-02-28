/**
 * 
 */
package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.jasypt.util.password.PasswordEncryptor;
import org.jfree.util.Log;
import org.springframework.beans.BeanUtils;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.backing.ui.ListPicker;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.PersonIdentifiers;
import com.inthinc.pro.model.PreferenceLevelOption;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.Roles;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.util.SelectItemUtil;

/**
 * @author David Gileadi
 */
public class PersonBean extends BaseAdminBean<PersonBean.PersonView> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 14, 25, 11 };
    private static final Map<String, Integer> HEIGHTS;
    private static final int MIN_HEIGHT = 48;
    private static final int MAX_HEIGHT = 86;
    private static final Map<String, Integer> WEIGHTS;
    private static final int MIN_WEIGHT = 75;
    private static final int MAX_WEIGHT = 300;
    private static Map<String, TimeZone> TIMEZONES;
    private static final Map<String, State> STATES;
    private static final String REQUIRED_KEY = "required";
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("fullName");
        AVAILABLE_COLUMNS.add("priPhone");
        AVAILABLE_COLUMNS.add("secPhone");
        AVAILABLE_COLUMNS.add("priEmail");
        AVAILABLE_COLUMNS.add("secEmail");
        AVAILABLE_COLUMNS.add("priText");
        AVAILABLE_COLUMNS.add("secText");
        AVAILABLE_COLUMNS.add("info");
        AVAILABLE_COLUMNS.add("warn");
        AVAILABLE_COLUMNS.add("crit");
        AVAILABLE_COLUMNS.add("timeZone");
        AVAILABLE_COLUMNS.add("empid");
        AVAILABLE_COLUMNS.add("reportsTo");
        AVAILABLE_COLUMNS.add("title");
        AVAILABLE_COLUMNS.add("dob");
        AVAILABLE_COLUMNS.add("gender");
        AVAILABLE_COLUMNS.add("locale");
        AVAILABLE_COLUMNS.add("measurementType");
        AVAILABLE_COLUMNS.add("fuelEfficiencyType");
        AVAILABLE_COLUMNS.add("user_status");
        AVAILABLE_COLUMNS.add("user_username");
        AVAILABLE_COLUMNS.add("user_groupID");
        AVAILABLE_COLUMNS.add("user_role");
        AVAILABLE_COLUMNS.add("driver_status");
        AVAILABLE_COLUMNS.add("driver_license");
        AVAILABLE_COLUMNS.add("driver_licenseClass");
        AVAILABLE_COLUMNS.add("driver_state");
        AVAILABLE_COLUMNS.add("driver_expiration");
        AVAILABLE_COLUMNS.add("driver_certifications");
        AVAILABLE_COLUMNS.add("driver_dot");
        AVAILABLE_COLUMNS.add("barcode");
        AVAILABLE_COLUMNS.add("rfid1");
        AVAILABLE_COLUMNS.add("rfid2");
        AVAILABLE_COLUMNS.add("fobID");
        AVAILABLE_COLUMNS.add("driver_groupID");
//        AVAILABLE_COLUMNS.add("driver_provider");
//        AVAILABLE_COLUMNS.add("driver_providerUsername");
//        AVAILABLE_COLUMNS.add("driver_providerCellPhone");
        // heights
        HEIGHTS = new LinkedHashMap<String, Integer>();
        for (int i = MIN_HEIGHT; i < MAX_HEIGHT; i++)
            if ((i % 12) != 0)
                HEIGHTS.put((i / 12) + "' " + (i % 12) + '"', i);
            else
                HEIGHTS.put((i / 12) + "'", i);
        // weights
        WEIGHTS = new TreeMap<String, Integer>();
        for (int i = MIN_WEIGHT; i < MAX_WEIGHT; i++)
            WEIGHTS.put(String.valueOf(i), i);
        // states
        STATES = new TreeMap<String, State>();
        for (final State state : States.getStates().values())
            STATES.put(state.getName(), state);
    }
    private AccountDAO accountDAO;
    private PersonDAO personDAO;
    private UserDAO userDAO;
    private DriverDAO driverDAO;
    private PhoneControlDAO phoneControlDAO;
    private RoleDAO roleDAO;
    private PasswordEncryptor passwordEncryptor;
    private List<PersonChangeListener> changeListeners;
    
    
	private FuelEfficiencyBean fuelEfficiencyBean;
    private UpdateCredentialsBean updateCredentialsBean;
//    private AccountOptionsBean accountOptionsBean;
    private Roles accountRoles;
    private CacheBean cacheBean;
    private ListPicker         rolePicker;
    private Account account;
    private Map<Integer, Cellblock> cellblockMap;
    private boolean passwordUpdated = false;
    
    private List<PersonIdentifiers> personIdentifiersList;
	private Map<Integer, Boolean> selectedMap = new HashMap<Integer, Boolean>();
	private Integer passwordDaysRemaining;
	
	
    public void initBean()
    {
        super.initBean();
        TIMEZONES = initTimeZones();
    }

    public CacheBean getCacheBean() {
		return cacheBean;
	}

	public void setCacheBean(CacheBean cacheBean) {
		this.cacheBean = cacheBean;
	}

	public FuelEfficiencyBean getFuelEfficiencyBean() {
		return fuelEfficiencyBean;
	}

	public void setFuelEfficiencyBean(FuelEfficiencyBean fuelEfficiencyBean) {
		this.fuelEfficiencyBean = fuelEfficiencyBean;
	}

	public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    public void setPersonChangeListeners(List<PersonChangeListener> changeListeners) {
        this.changeListeners = changeListeners;
    }

    private void notifyChangeListeners() {
        if (changeListeners != null)
            for (final PersonChangeListener listener : changeListeners)
                listener.personListChanged();
    }
    public ListPicker getRolePicker()
    {
        if (rolePicker == null)
        	rolePicker = new ListPicker(getPickFrom(), getPicked());
        return rolePicker;
    }

    private List<SelectItem> getPickFrom()
    {
    	initAccountRoles();
        final LinkedList<SelectItem> pickFrom = new LinkedList<SelectItem>();
        for (final Role role:getAccountRoles().getRoleList()){
        	
        		if(!role.getName().equals("Normal")){
        			
        			pickFrom.add(new SelectItem(role.getName()));
        		}
        }
        
        return pickFrom;
   }
    private List<SelectItem> getPicked()
    {
        final LinkedList<SelectItem> picked = new LinkedList<SelectItem>();
        if(item.getUser()!=null && item.getUser().hasRoles()){
        	
           for (Integer id:item.getUser().getRoles()){
	    		
        	   if(!getAccountRoles().getRoleById(id).getName().equals("Normal")){
        		   
        		   picked.add(new SelectItem(getAccountRoles().getRoleById(id).getName()));
        	   }
	    	}
        }
        return picked;
    }
    public Account getAccount() {
        if(account == null)
            account = accountDAO.findByID(this.getPerson().getAccountID());
        return account;
    }
    public boolean isInitialLogin() {
        return this.getProUser().getPreviousLogin() == null;
    }
    public boolean isPasswordChangeRequired() {
        boolean results = PreferenceLevelOption.REQUIRE.getCode().toString().equalsIgnoreCase(getAccount().getProps().getPasswordChange()) && isInitialLogin() && !isPasswordUpdated();
        if(results)
            logger.debug("getPasswordChange(): "+getAccount().getProps().getPasswordChange()+"; isInitialLogin(): "+isInitialLogin()+"; isPasswordUpdated: "+isPasswordUpdated());
        return results;
    }
    public boolean isPasswordChangeWarn() { 
        return PreferenceLevelOption.WARN.getCode().toString().equalsIgnoreCase(getAccount().getProps().getPasswordChange());
    }
    public boolean isWaysmartSupported() {
        return getAccount().hasWaySmartSupport();
    }
    public Integer getLoginDaysRemaining() {
        return getLoginDaysRemaining(getAccount(), this.getUser());
    }
    public boolean isLoginExpired() {
        return isLoginExpired(getAccount(), this.getUser());
    }
    public static boolean isLoginExpired(Account account, User user) {
        return (getLoginDaysRemaining(account, user) <= 0);
    }
    public boolean isPasswordUpdated() {
        return passwordUpdated;
    }
    public void setPasswordUpdated(boolean passwordUpdated) {
        this.passwordUpdated = passwordUpdated;
    }
    public String ignorePasswordWarning() {
        this.passwordUpdated = true;
        return "Set password as updated (for this login) to avoid passwordReminder rendering on ";
    }
    public boolean isShowPasswordReminder() {
        boolean results = (((getPasswordDaysRemaining() < 16)  || ((isInitialLogin()) && (isPasswordChangeWarn())))  && !isPasswordUpdated());
        if(results)
            logger.debug("getPasswordDaysRemaining():"+getPasswordDaysRemaining()+"; isInitialLogin():"+isInitialLogin()+"; isPasswordChangeWarn():"+isPasswordChangeWarn()+"; isPasswordUpdated():"+isPasswordUpdated());
        return results;
    }
    public static Integer getLoginDaysRemaining(Account account, User user) {
        Integer loginExpire;
        Integer daysSinceLastLogin = (user.getLastLogin()!=null)?DateUtil.differenceInDays(user.getLastLogin(), new Date()):0;
        Integer NOT_SET_TO_EXPIRE = 1;//not set for this account, so any positive non-zero integer will do
        try {
            if(account.getProps().getLoginExpire() == null)
                return NOT_SET_TO_EXPIRE;
            loginExpire = Integer.parseInt(account.getProps().getLoginExpire());
        } catch (NumberFormatException nfe) {
            Log.error("NumberFormatException for account.props.loginExpire:"+account.getProps().getLoginExpire());
            return NOT_SET_TO_EXPIRE;
        }
        return (loginExpire!=0)? loginExpire - daysSinceLastLogin:NOT_SET_TO_EXPIRE;
    }
    public Integer getPasswordDaysRemaining() {
    	if(passwordDaysRemaining==null){
    		passwordDaysRemaining = getPasswordDaysRemaining(getAccount(),userDAO.findByID(this.getUser().getUserID()));
    	}
        return passwordDaysRemaining;
    }
    public static Integer getPasswordDaysRemaining(Account account, User user) {
        Integer passwordExpire;
        Integer EXPIRES_OUTSIDE_VIEWABLE_RANGE = 365;
        try {
            if(account.getProps().getPasswordExpire() == null)
                return EXPIRES_OUTSIDE_VIEWABLE_RANGE;
            passwordExpire= Integer.parseInt(account.getProps().getPasswordExpire());
        } catch (NumberFormatException nfe) {
            Log.error("NumberFormatException for account.props.passwordExpire:"+account.getProps().getPasswordExpire());
            return EXPIRES_OUTSIDE_VIEWABLE_RANGE;
        }
        Integer daysSinceModified = (user.getPasswordDT()!=null)?DateUtil.differenceInDays(user.getPasswordDT(), new Date()):0;
        return (passwordExpire!=0)?passwordExpire - daysSinceModified:EXPIRES_OUTSIDE_VIEWABLE_RANGE;
    }
    @Override
    protected List<PersonView> loadItems() {
/*        
        // get the people
        final List<Person> plainPeople = personDAO.getPeopleInGroupHierarchy(getTopGroup().getGroupID(), getUser().getGroupID());
        // filter out people who don't belong
        for (final Iterator<Person> i = plainPeople.iterator(); i.hasNext();) {
            final Person person = i.next();
            if ((person.getDriver() == null) && (getGroupHierarchy().getGroup(person.getUser().getGroupID()) == null)) {
                i.remove();
            }   
        }
        //get all the cellblocks for the account in one go so we don't have to get them individually
//        createCellblockMap();
        // convert the people to PersonViews
        final List<PersonView> items = new ArrayList<PersonView>();
        for (final Person person : plainPeople) {
            if (logger.isDebugEnabled())
                logger.debug("Person Loaded: " + person);
            items.add(createPersonView(person));
        }
        return items;
*/
        
        // don't want this method anymore due to pagination
        return null;
    }
    private void createCellblockMap(){
        final List<Cellblock> cellblocks = phoneControlDAO.getCellblocksByAcctID(getUser().getPerson().getAccountID());
        cellblockMap = new HashMap<Integer,Cellblock>();
        if(cellblocks != null){
            for(Cellblock cellblock :cellblocks){
                cellblockMap.put(cellblock.getDriverID(), cellblock);
            }
        }
    }
    /**
     * Creates a PersonView object from the given Person object and score.
     * 
     * @param person
     *            The person.
     * @return The new PersonView object.
     */
// CJ: MADE THIS PUBLIC    
    public PersonView createPersonView(Person person) {
        final PersonView personView = new PersonView();
        if (logger.isTraceEnabled())
            logger.trace("createPersonView: BEGIN " + person);
        personView.bean = this;
        BeanUtils.copyProperties(person, personView);
        if (personView.getAddress() == null)
            personView.setAddress(new Address());
        personView.setUserSelected(person.getUser() != null);
        personView.setDriverSelected(person.getDriver() != null);
        
        personView.setProviderInfoSelected(false);
        personView.setProviderInfoExists(false);
        
        if (person.getDriver() != null) {
// CJ: ADDED THIS IF STMT (could probably make this a session bean or something)
//if (cellblockMap == null)
//    createCellblockMap();
//            
//            Cellblock cellblock = cellblockMap.get(person.getDriverID());
//            if(cellblock != null) cellblock.setProviderPassword("");
//            personView.setProviderInfoSelected(cellblock != null);
//            personView.setProviderInfoExists(cellblock != null);
//            personView.setCellblock(personView.isProviderInfoExists()?cellblock:new Cellblock());
//            
//            personView.setConfirmProviderPassword("");
        }
        
        if (person.getUser() != null) {
            personView.getUser().setPerson(personView);
        }
        updateCredentialsBean.setUserID(person.getUserID());
        if (logger.isTraceEnabled())
            logger.trace("createPersonView: END " + personView);
        return personView;
    }

    @Override
    protected void initEditItem(Integer editID)
    {
        PersonView personView = createPersonView(personDAO.findByID(editID));
        
        items = new ArrayList<PersonView>();
        items.add(personView);

    }


    @Override
    public String fieldValue(PersonView person, String column) {
        if (column.equals("user_status")) {
            if ((person.getUser() != null) && (person.getUser().getStatus() != null))
                return MessageUtil.getMessageString(person.getUser().getStatus().getDescription().toLowerCase());
            return null;
        }
        else if (column.equals("driver_status")) {
            if ((person.getDriver() != null) && (person.getDriver().getStatus() != null))
                return MessageUtil.getMessageString(person.getDriver().getStatus().getDescription().toLowerCase());
            return null;
        }
        else if (column.equals("user_groupID")) {
            if (person.getGroup() != null)
                return person.getGroup().getName();
            return null;
        }
        else if (column.equals("driver_groupID")) {
            if (person.getTeam() != null)
                return person.getTeam().getName();
            return null;
        }
        else if (column.equals("timeZone")) {
            if (person.getTimeZone() != null)
                return person.getTimeZone().getID();
            return null;
        }
        else if (column.equals("info")) {
            Integer value = person.getInfo();
            if (value == null)
                value = 0;
            return MessageUtil.getMessageString("myAccount_alertText" + value);
        }
        else if (column.equals("warn")) {
            Integer value = person.getWarn();
            if (value == null)
                value = 0;
            return MessageUtil.getMessageString("myAccount_alertText" + value);
        }
        else if (column.equals("crit")) {
            Integer value = person.getCrit();
            if (value == null)
                value = 0;
            return MessageUtil.getMessageString("myAccount_alertText" + value);
        }
        else if (column.equals("driver_barcode")) {
            if (person.getDriver() != null)
                return person.getDriver().getBarcode();
            return null;
        }
        else if (column.equals("driver_rfid1")) {
            Long value = null;
            if (person.getDriver() != null)
                value = person.getDriver().getRfid1();
            if (value == null)
                value = 0L;
            return Long.toHexString(value);
        }
        else if (column.equals("driver_rfid2")) {
            Long value = null;
            if (person.getDriver() != null)
                value = person.getDriver().getRfid2();
            if (value == null)
                value = 0L;
            return Long.toHexString(value);
        }
        else if (column.equals("driver_fobID")) {
            if (person.getDriver() != null) {
                return person.getDriver().getFobID();
            }
            return null;
        }
        else if (column.equals("locale")) {
            if (person.getUser() != null && person.getLocale() != null)
                return person.getLocale().getDisplayName();
            else
                return null;
        }
        else if (column.equals("measurementType")) {
            return MessageUtil.getMessageString(getMeasurementType().toString(), getLocale());
        }
        else if (column.equals("fuelEfficiencyType")) {
            return MessageUtil.getMessageString(getFuelEfficiencyType().toString(), getLocale());
        }
        else if (column.equals("user_role")){
        	if (person.getUser() != null){
        		return person.getRolesString();
        	}
        	else return null;
        }
        else if (column.equals("driver_provider")) {
            if (person.getDriver() != null && person.getCellblock() != null) {
                return person.getCellblock().getProvider().toString();
            }
            
            return null;
        } else if (column.equals("driver_providerUsername")) {
            if (person.getDriver() != null && person.getCellblock() != null) {
                return person.getCellblock().getProviderUser();
            }
            
            return null;
        } else if (column.equals("driver_providerPassword")) {
            if (person.getDriver() != null && person.getCellblock() != null) {
                return person.getCellblock().getProviderPassword();
            }
            
            return null;
        } else if (column.equals("driver_providerCellPhone")) {
            if (person.getDriver() != null && person.getCellblock() != null) {
                return person.getCellblock().getCellPhone();
            }
            
            return null;
        }
        else
            return super.fieldValue(person, column);
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    public String getColumnLabelPrefix() {
        return "personHeader_";
    }

    @Override
	public List<String> getAvailableColumns() {
        List<String> cols = AVAILABLE_COLUMNS;
        if (!getAccountIsHOS()) {
            cols.remove("driver_dot");
        }
        return cols;
	}

	@Override
    public TableType getTableType() {
        return TableType.ADMIN_PEOPLE;
    }
	
    @Override
    protected PersonView createAddItem() {
        final PersonView person = new PersonView();
        person.bean = this;
        person.setStatus(Status.ACTIVE);
        person.setUser(new User());
        // TODO: maybe use the browser's time zone instead, if possible...
        person.setTimeZone(TimeZone.getDefault());
        person.setAddress(new Address());
        person.getUser().setPerson(person);
        Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        if (LocaleBean.supportedLocale(locale))
            person.setLocale(locale);
        else
            person.setLocale(Locale.US);
        person.setMeasurementType(MeasurementType.ENGLISH);
        person.setFuelEfficiencyType(FuelEfficiencyType.MPG_US);
        person.setDriver(new Driver());
        person.setCellblock(new Cellblock());
        person.setUserSelected(!isLoginInfoDisabled());
        person.setDriverSelected(!isDriverInfoDisabled());
        person.setProviderInfoSelected(false);
        person.setProviderInfoExists(false);
        person.setAcctID(getAccountID());
        return person;
    }

    

    @Override
    public PersonView getItem() {
        final PersonView item = super.getItem();
        if (item.getUser() == null) {
            item.setUser(new User());
            item.getUser().setPerson(item);

        }
        if (item.getDriver() == null) {
            item.setDriver(new Driver());
            item.getDriver().setPersonID(item.getPersonID());
        }

        if(item.getCellblock()==null){
            item.setCellblock(new Cellblock());
        }
        if (fuelEfficiencyBean == null)
        {
        	fuelEfficiencyBean = new FuelEfficiencyBean();
        }
    	fuelEfficiencyBean.init(item.getMeasurementType());
        return item;
    }

    @Override
    public String display() {
        final String redirect = super.display();
        // if displaying the current user, reload from the DB in case changed elsewhere
        if (getItem().isUserSelected() && getItem().getUser().getUserID().equals(getUserID())) {
            item = revertItem(getItem());
        }
        return redirect;
    }

    @Override
    public void view() {
        super.view();
        if (getItem().getUser().getUserID() == null) return;
        if (getItem() != null && getItem().isUserSelected() && getItem().getUser().getUserID().equals(getUserID())) {
            item = revertItem(getItem());
        }
    }

    @Override
    public String edit() {
        final String redirect = super.edit();
        // if editing the current user, reload from the DB in case changed elsewhere
        if (getItem() != null && getItem().isUserSelected() && getItem().getUser() !=null && getItem().getUser().getUserID() != null && getItem().getUser().getUserID().equals(getUserID())) {
            item = revertItem(getItem());
        }
        return redirect;
    }

    @Override
    public String delete() {
        final String result = super.delete();
        notifyChangeListeners();
        // forces the paginated table to reinit
        super.columnsChanged();
        return result;
    }

    @Override
    public String batchEdit() {
        String returnValue = super.batchEdit();
        if (item != null) {
            item.setUserSelected(true);
            item.setDriverSelected(true);
            item.setProviderInfoSelected(true);
        }
        return returnValue;
    }

    public void clearFob() {
        //set fobID to an empty string to ensure that updateDriver picks it up as a change
        getItem().getDriver().setFobID("");
        super.save();
    }
    
    @Override
    public String save() {
        // see if we're partially editing one of the batch items
    	boolean driverChange = getItem().isDriverSelected();
        boolean partialEdit = isBatchEdit() && getItem().isUserEditable();
        if (partialEdit) {
            partialEdit = false;
            for (final PersonView person : getSelectedItems())
                if (!person.isUserEditable()) {
                    partialEdit = true;
                    break;
                }
        }
        if(item.getUser()!= null){
        	
 	        List<Integer> roleIDs = new ArrayList<Integer>();
	        for (final SelectItem item : getRolePicker().getPicked())
	        {
	          	Integer id = getAccountRoles().getRoleByName(item.getValue().toString()).getRoleID();
	          	roleIDs.add(id);
	        }
	        roleIDs.add(getAccountRoles().getRoleByName("Normal").getRoleID());
	        item.getUser().setRoles(roleIDs);
	        rolePicker = null;
    	}

         final String result = super.save();
       // revert partial-edit changes if user editable
        if ((result != null) && partialEdit) {
            items = null;
            getItems();
            final String summary = MessageUtil.getMessageString("editPerson_partialUpdate");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        if (driverChange) {
	        cacheBean.setDriverMap(null);
	        notifyChangeListeners();
        }
        return result;
    }

    @Override
    protected void doDelete(List<PersonView> deleteItems) {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final PersonView person : deleteItems) {
            if(person.providerInfoExists){
                phoneControlDAO.deleteByID(person.getDriverID());
            }        
            personDAO.delete(person);
            // add a message
            final String summary = MessageUtil.formatMessageString("person_deleted", person.getFirst(), person.getLast());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }
    public static boolean validatePreferedNotification(FacesContext context, PersonBean personBean) {
        return validatePreferedNotifications(context, personBean.getItem());
    }
    public static boolean validatePreferedNotifications(FacesContext context, Person person){
        return validatePreferedNotifications(context, "edit-form:editPerson-info", person);
    }
    public static boolean validatePreferedNotifications(FacesContext context,String facesClientID, Person person){
        boolean valid = true;
        Boolean[] validNotifications = {true,//None
                MiscUtil.notEmpty(person.getPriEmail()),
                MiscUtil.notEmpty(person.getSecEmail()),
                MiscUtil.notEmpty(person.getPriPhone()),
                MiscUtil.notEmpty(person.getSecPhone()),
                false,//cellphone
                MiscUtil.notEmpty(person.getPriText()),
                MiscUtil.notEmpty(person.getSecText())};
        if(!validNotifications[person.getInfo()] || !validNotifications[person.getWarn()] || !validNotifications[person.getCrit()]) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_notificationPref"), null);
            context.addMessage(facesClientID, message);
        } 
        return valid;
    }
    
    public boolean isUserInfoDisabled(){
        List<String> allowedRoles = new ArrayList<String>();
        allowedRoles.add("ROLE_USEREDITINFO");
        allowedRoles.add("ROLE_DRIVEREDITINFO");
        boolean result = !isUserInRoles(allowedRoles);
        return result;
    }
    
    public boolean isDriverInfoDisabled(){
        return !isUserInRole("ROLE_DRIVEREDITINFO");
    }
    public boolean isUserAbleToCreate(){
        List<String> anyRoles = new ArrayList<String>();
        anyRoles.add("ROLE_USEREDITINFO");
        anyRoles.add("ROLE_DRIVEREDITINFO");
        return isUserInRoles(anyRoles);
    }
    public boolean isRfidInfoDisabled(){
        return !isUserInRole("ROLE_RFIDEDITINFO");
    }
    public boolean isEmployeeInfoDisabled(){
        List<String> allowedRoles = new ArrayList<String>();
        allowedRoles.add("ROLE_USEREDITINFO");
        allowedRoles.add("ROLE_DRIVEREDITINFO");
        return !isUserInRoles(allowedRoles);
    }
    public boolean isLoginInfoDisabled(){
        return !isUserInRole("ROLE_USEREDITINFO");
    }
    public boolean isNotificationsInfoDisabled(){
        return !isUserInRole("ROLE_USEREDITINFO");
    }
    
    @Override
    protected boolean validateSaveItem(PersonView person) {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        // required Time Zone
        if (person.getTimeZone() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("timeZone")))) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
            context.addMessage("edit-form:editPerson-timeZone", message);
        }
        //unique employee ID
        if (!isBatchEdit() && (person.getEmpid() != null) && (person.getEmpid().length() > 0)) {
            // when checking for duplicate employee id, use the logged-in user's groupID and check from there.
                Person lookupPerson = null;
                try {
                    lookupPerson = personDAO.findByEmpID(person.getAcctID(), person.getEmpid());
                }
                catch (EmptyResultSetException ex) {
                    lookupPerson = null;
                }
        
                if(lookupPerson != null && !lookupPerson.getPersonID().equals(person.getPersonID()) ){
                    valid = false;
                    final String summary = MessageUtil.getMessageString("editPerson_empidTaken");
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    context.addMessage("edit-form:editPerson-empId", message);
                }
        }
        // unique primary e-mail
        if (!isBatchEdit() && (person.getPriEmail() != null) && (person.getPriEmail().length() > 0)) {
            final Person byEmail = personDAO.findByEmail(person.getPriEmail());
            if ((byEmail != null) && !byEmail.getPersonID().equals(person.getPersonID())) {
                valid = false;
                final String summary = MessageUtil.getMessageString("editPerson_uniqueEmail");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:editPerson-priEmail", message);
            }
        }
        //selected notification option must be valid
        valid &= validatePreferedNotifications(context, person);
        
        // birth date
        if (!isBatchEdit() && (person.getDob() != null)) {
            Calendar latest = Calendar.getInstance();
            latest.add(Calendar.YEAR, -16);
            if (person.getDob().after(latest.getTime())) {
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_dobTooLate"), null);
                context.addMessage("edit-form:editPerson-dob", message);
                valid = false;
            }
        }
        // driver license expiration
        if (person.isDriverSelected()) {
            // required team
            if (person.getDriver().getGroupID() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("driver.groupID")))) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:editPerson-driver_groupID", message);
            }
            // required user status
            if (person.getDriver().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("driver.status")))) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:editPerson-driver_status", message);
            }
            if (!isBatchEdit() && (person.getDriver().getBarcode() != null) && !person.getDriver().getBarcode().isEmpty()) {
            	
            	List<Long> rfids = driverDAO.getRfidsByBarcode(person.getDriver().getBarcode());
            	if (rfids == null || rfids.isEmpty()){
            		
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_barcodeNotAvailable"), null);
                    context.addMessage("edit-form:editPerson-driver_barcode", message);
                    
                    person.getDriver().setBarcode("");
	            	person.getDriver().setRfid1(null);
	            	person.getDriver().setRfid2(null);
                    
                    valid = false;
            		
            	}
            	else {
            		
	            	Integer existingBarCodeDriverId = driverDAO.getDriverIDByBarcode(person.getDriver().getBarcode());
	            	if (existingBarCodeDriverId != null && !existingBarCodeDriverId.equals(person.getDriver().getDriverID()) ){
	            		
	    	    		Driver existingBarCodeDriver = driverDAO.findByID(existingBarCodeDriverId);
	                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.formatMessageString("editPerson_uniqueBarcode", person.getDriver().getBarcode(),existingBarCodeDriver.getPerson().getFullName()), null);
	                    context.addMessage("edit-form:editPerson-driver_barcode", message);
	                    
	                    person.getDriver().setBarcode("");
		            	person.getDriver().setRfid1(null);
		            	person.getDriver().setRfid2(null);
	                    
	                    valid = false;
	            	}
	            	else {
	            		
		            	person.getDriver().setRfid1(rfids.get(0));
		            	person.getDriver().setRfid2(rfids.size()>1?rfids.get(1):null);
	            	}
            	}
            
            } 
            
            if (person.isProviderInfoSelected()){
            	// mandatory provider type
            	if (person.getCellblock().getProvider() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("cellblock.provider")))) {
                    valid = false;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                    context.addMessage("edit-form:editPerson_driver_provider", message);
               	}
            	// mandatory provider username
            	if (StringUtils.isEmpty(person.getCellblock().getProviderUser()) && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("cellblock.providerUser")))) {
            	    valid = false;
            	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
            	    context.addMessage("edit-form:editPerson_driver_providerUsername", message);
            	}
            	// mandatory provider password
            	if (!person.isProviderInfoExists() && StringUtils.isEmpty(person.getCellblock().getProviderPassword()) && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("cellblock.providerPassword")))) {
            	 valid = false;
                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                 context.addMessage("edit-form:editPerson_driver_providerPassword", message);
            	}
            	
            	// mandatory provider confirm password
                if (!person.isProviderInfoExists() && StringUtils.isEmpty(person.getConfirmProviderPassword()) && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("cellblock.providerPassword")))) {
                    valid = false;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                    context.addMessage("edit-form:editPerson_driver_confirmProviderPassword", message);
                }
            	
            	// mandatory provider cell phone
            	if (StringUtils.isEmpty(person.getCellblock().getCellPhone()) && !isBatchEdit()) {
               	    valid = false;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                    context.addMessage("edit-form:editPerson_driver_providerCellPhone", message);
               	}
            	
                // matching passwords
                if ((!StringUtils.isEmpty(person.getCellblock().getProviderPassword())) && !StringUtils.isEmpty(person.getConfirmProviderPassword())
                        && !person.getCellblock().getProviderPassword().equals(person.getConfirmProviderPassword()) && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("driver.cellProviderInfo.providerPassword")))) {
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_passwordsMismatched"), null);
                    context.addMessage("edit-form:editPerson_driver_providerPassword", message);
                    valid = false;
                }
            }
        }
        // unique username
        if (person.isUserSelected()) {
            // required groupID
            if (person.getUser().getGroupID() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.groupID")))) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:editPerson-user_groupID", message);
            }
            // required user status
            if (person.getUser().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.status")))) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:editPerson-user_status", message);
            }
            // required locale
            if (person.getUser().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.person.locale")))) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:editPerson-user_locale", message);
            }
            // required measurementType
            if (person.getUser().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.person.measurementType")))) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:editPerson-user_person_measurementType", message);
            }
            // required fuelEfficiencyType
            if (person.getUser().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.person.fuelEfficiencyType")))) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:editPerson-user_person_fuelEfficiencyType", message);
            }
            if (!isBatchEdit()) {
                final User byUsername = userDAO.findByUserName(person.getUser().getUsername());
                if ((byUsername != null) && !byUsername.getPersonID().equals(person.getPersonID())) {
                    valid = false;
                    final String summary = MessageUtil.getMessageString("editPerson_uniqueUsername");
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    context.addMessage("edit-form:editPerson-user_username", message);
                }
                // matching passwords
                if ((person.getPassword() != null) && (person.getPassword().length() > 0) && !person.getPassword().equals(person.getConfirmPassword())) {
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_passwordsMismatched"), null);
                    context.addMessage("edit-form:editPerson-confirmPassword", message);
                    valid = false;
                }
                // validate password strength handled via f:validator tag reference to PasswordStrengthValidator
            }
            // required pri email
            if (!isBatchEdit() && (person.getPriEmail() == null || person.getPriEmail().equals(""))) {
                valid = false;
                final String summary = MessageUtil.getMessageString(REQUIRED_KEY);
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:editPerson-priEmail", message);
            }

        }
        // must be a user or a driver or both while not in batch edit.
        else if (!person.isDriverSelected() && !isBatchEdit()) {
            person.setDriverSelected(true);
            person.setUserSelected(true);
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_userOrDriver"), null);
            context.addMessage("edit-form:editPerson-isUser", message);
            valid = false;
        }
        return valid;
    }

    @Override
    protected Boolean authorizeAccess(PersonView item) {
        Integer acctID = item.getAcctID();
        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE; 
    }

    @Override
    protected PersonView revertItem(PersonView editItem) {
        if (logger.isTraceEnabled())
            logger.trace("revertItem" + editItem);
        return createPersonView(personDAO.findByID(editItem.getPersonID()));
    }
    



    @Override
    protected void doSave(List<PersonView> saveItems, boolean create) {
    	
    	if (isBatchEdit()) {
    		batchSave(saveItems);
    		return;
    	}

    	
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final PersonView person : saveItems) {
            if ((person.getPassword() != null) && (person.getPassword().length() > 0)) {
                person.getUser().setPassword(passwordEncryptor.encryptPassword(person.getPassword()));
                person.getUser().setPasswordDT(new Date());
                if(this.getPerson().getPersonID().equals(person.getPersonID())){
                    this.setPasswordUpdated(true);
                } else {
                    // reset the last login to null only if a user, and not self
                    if ( person.getUser() != null ) {
                        person.getUser().setLastLogin(null);
                    }
                }
            }
            // null out the user/driver before saving
            if (!person.isUserSelected() && (person.getUser() != null)) {
                if (person.getUser().getUserID() != null)
                    userDAO.deleteByID(person.getUser().getUserID());
                person.setUser(null);
            }
            if(person.getUser() != null){
//TODO            	person.setUsersRolesFromTargetRoles();
            }
            if (!person.isDriverSelected()) {
                if (person.getDriver() != null) {
                    if (person.getDriver().getDriverID() != null){
                        if (person.getCellblock() != null) {
                            phoneControlDAO.deleteByID(person.getDriverID());
                            person.setCellblock(null);
                        }
                        driverDAO.deleteByID(person.getDriver().getDriverID());
                    }
                    person.setDriver(null);
                }
            }
            else {
                if (!create && !person.isProviderInfoSelected() && person.isProviderInfoExists() ) {
                    phoneControlDAO.deleteByID(person.getDriverID());
                    person.setCellblock(null);
                }
            }
            // set null dropdown items to empty
            if (person.getSuffix() == null)
                person.setSuffix("");
            if ((person.getDriver() != null) && (person.getDriver().getLicenseClass() == null))
                person.getDriver().setLicenseClass("");
            // null numbers are zero for the DB
            if (person.getHeight() == null)
                person.setHeight(0);
            if (person.getWeight() == null)
                person.setWeight(0);
            // if create and no user info, check for "" on primary and secondary e-mail
            if ( create && !person.isUserSelected() ) {
                if ( (person.getPriEmail() != null) && (person.getPriEmail().trim().length() == 0) ) {
                    person.setPriEmail(null);
                }
                if ( (person.getSecEmail() != null) && (person.getSecEmail().trim().length() == 0) ) {
                    person.setSecEmail(null);
                }                
            }
            if(person.getDriver()!= null && (person.getDriver().getBarcode() == null || person.getDriver().getBarcode().isEmpty())){
           	
            	person.getDriver().setBarcode("");
            	person.getDriver().setRfid1(0l);
            	person.getDriver().setRfid2(0l);
            }

            // insert or update
            if (create)
                person.setPersonID(personDAO.create(getAccountID(), person));
            else {
//                if(isBatchEdit() && person.getDriver().getDriverID() == null) {
//                    person.setDriver(null);
//                }
                if (person.isUserEditable())
                    personDAO.update(person);
                if (person.isDriverSelected()){
                    driverDAO.update(person.getDriver());
                    if(person.isProviderInfoSelected()){
                        if(person.isProviderInfoExists()){
                            if (person.getCellblock().getProviderPassword().isEmpty()){
                                person.getCellblock().setProviderPassword(null);
                            }
                            phoneControlDAO.update(person.getCellblock());
                        }
                        else{
                            person.getCellblock().setDriverID(person.getDriverID());
                            person.getCellblock().setAcctID(person.getAcctID());
                            phoneControlDAO.create(person.getDriverID(),person.getCellblock());
                        }
                    }
                }
                // if updating the currently-logged-in person, update the proUser
                if ((person.getUser() != null) && person.getUser().getUserID().equals(getUserID()))
                    BeanUtil.deepCopy(person.getUser(), getUser());
            }
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "person_added" : "person_updated", person.getFirst(), person.getLast());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    private void batchSave(List<PersonView> saveItems) {
//        final FacesContext context = FacesContext.getCurrentInstance();

        Person updatePersonTemplate = null;
    	Driver updateDriverTemplate = null;
    	User updateUserTemplate = null;
        for (Map.Entry<String, Boolean> entry: getUpdateField().entrySet()) {
        	if (entry.getValue().equals(Boolean.TRUE))  {
	        	if (entry.getKey().startsWith("user.")) {
	        		User sourceUser = findSourceUser(saveItems);
	        		if (sourceUser == null)
	        			continue;
	        		if (updateUserTemplate == null) {
	        			updateUserTemplate = new User();
	        		}
	        		
	        		String propertyName = entry.getKey().substring(5);
	        		BeanUtil.copyProperty(sourceUser, updateUserTemplate, propertyName);
	        	}
	        	else if (entry.getKey().startsWith("driver.")) { 
	        		Driver sourceDriver = findSourceDriver(saveItems);
	        		if (sourceDriver == null)
	        			continue;
	        		if (updateDriverTemplate == null) {
	        			updateDriverTemplate = new Driver();
	        		}
	        		String propertyName = entry.getKey().substring(7);
	        		BeanUtil.copyProperty(sourceDriver, updateDriverTemplate, propertyName);
	        		// special case for clearing out the state
	        		if (propertyName.equals("state") && updateDriverTemplate.getState() == null) {
	        			updateDriverTemplate.setState(new State(0, "", ""));
	        		}
	        	}
	        	else {
	        		if (updatePersonTemplate == null) {
	        			updatePersonTemplate = new Person();
	        		}
	        		Person sourcePerson = saveItems.get(0);
	        		BeanUtil.copyProperty(sourcePerson, updatePersonTemplate, entry.getKey());
	        	}
        	}
        }
        
        for (Map.Entry<Integer, Boolean> entry : selectedMap.entrySet()) {
        	if (entry.getValue().equals(Boolean.TRUE)) {
            	Integer personID = entry.getKey();
        		if (updatePersonTemplate != null) {
        			updatePersonTemplate.setPersonID(personID);
        			personDAO.update(updatePersonTemplate);
        		}
        		
        		if (updateDriverTemplate != null) {
        			Integer driverID = findDriverIDForPersonID(personID);
        			if (driverID != null) {
        				updateDriverTemplate.setDriverID(driverID);
        				driverDAO.update(updateDriverTemplate);
        			}
        		}
        		
        		if (updateUserTemplate != null) {
        			Integer userID = findUserIDForPersonID(personID);
        			if (userID != null) {
        				updateUserTemplate.setUserID(userID);
        				userDAO.update(updateUserTemplate);
        			}
        		}
        		
        		// TODO: Do we need this?  if so should person first, last to the PersonIdentifier class
//                final String summary = MessageUtil.formatMessageString("person_updated", person.getFirst(), person.getLast());
//                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
//                context.addMessage(null, message);
        	}
        }

        
        // if updating the currently-logged-in person, update the proUser
        if (selectedMap.containsKey(getPerson().getPersonID()) && selectedMap.get(getPerson().getPersonID()).equals(Boolean.TRUE)) {
        	if (updatePersonTemplate != null) {
        		updatePersonTemplate.setPersonID(null);
        		BeanUtil.deepCopyNonNull(updatePersonTemplate, getPerson());
        		
        	}
        	if (updateUserTemplate != null) {
        		updateUserTemplate.setUserID(null);
        		BeanUtil.deepCopyNonNull(updateUserTemplate, getPerson().getUser());
        		
        	}
        }
	}

	private Integer findDriverIDForPersonID(Integer personID) {
		if (personIdentifiersList == null)
			return null;
		for (PersonIdentifiers personIdentifiers : personIdentifiersList) {
			if (personIdentifiers.getPersonID().equals(personID)) {
				return personIdentifiers.getDriverID();
			}
		}
		return null;
	}
	private Integer findUserIDForPersonID(Integer personID) {
		if (personIdentifiersList == null)
			return null;
		for (PersonIdentifiers personIdentifiers : personIdentifiersList) {
			if (personIdentifiers.getPersonID().equals(personID)) {
				return personIdentifiers.getUserID();
			}
		}
		return null;
	}

	private Driver findSourceDriver(List<PersonView> saveItems) {

		for (PersonView personView :saveItems) {
			if (personView.isDriverSelected() && personView.getDriver() != null)
				return personView.getDriver();
		}
			
		return null;
	}

	private User findSourceUser(List<PersonView> saveItems) {
		for (PersonView personView :saveItems) {
			if (personView.isUserSelected() && personView.getUser() != null)
				return personView.getUser();
		}
			
		return null;
	}

	@Override
    protected String getDisplayRedirect() {
        return "pretty:adminPerson";
    }

    @Override
    protected String getEditRedirect() {
        return "pretty:adminEditPerson";
    }

    @Override
    protected String getFinishedRedirect() {
        return "pretty:adminPeople";
    }

    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone("UTC");
    }

    public List<SelectItem> getGenders() {
        return SelectItemUtil.toList(Gender.class, false);
    }

    public Map<String, Integer> getHeights() {
        return HEIGHTS;
    }

    public Map<String, Integer> getWeights() {
        return WEIGHTS;
    }

    public Map<String, Integer> getAlertOptions() {
        // alert options
        LinkedHashMap<String, Integer> alertOptions = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 8; i++) {
            if (i == AlertText.PHONE_CELL.getCode() ||  // skip cell phone 
              (!isEnablePhoneAlerts() && (i == AlertText.PHONE_1.getCode() || i == AlertText.PHONE_2.getCode())))  // skip phone alerts if account is set to this
            	continue;
            alertOptions.put(MessageUtil.getMessageString("myAccount_alertText" + i), i);
        }
        return alertOptions;
    }

    public List<String> findPeople(Object event) {
        final List<String> results = new ArrayList<String>();
        final String name = event.toString().trim().toLowerCase();
        if (name.length() > 0)
            for (final PersonView person : getItems())
                if (person.getFullName().toLowerCase().contains(name))
                    results.add(person.getFullName());
        return results;
    }

    public Map<String, Integer> getGroups() {
        final TreeMap<String, Integer> groups = new TreeMap<String, Integer>();
	    for (final Group group : getGroupHierarchy().getGroupList()) {
    		String fullName = getGroupHierarchy().getFullGroupName(group.getGroupID());
	    	if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
	    			fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
	    	}
	    	groups.put(fullName, group.getGroupID());
	    }
	    return groups;

    
    }

    public TreeMap<String, Integer> getTeams(){
        return getGroupHierarchy().getTeams();
    }


    public Map<String, TimeZone> getTimeZones() {
        return TIMEZONES;
    }

    public List<SelectItem> getProviderTypes() {
        List<CellProviderType> providers = this.getProUser().getAccountAttributes().getPhoneControlProviders();
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        for (CellProviderType e : providers)
        {
            selectItemList.add(new SelectItem(e,MessageUtil.getMessageString(e.toString())));
        }
        
        return selectItemList;
    }
    public boolean isAccountPhoneControlEnabled(){
        return !getProviderTypes().isEmpty();
    }
    public void setAccountPhoneControlEnabled(boolean enable){
        // Nothing to do
    }
    public Map<String, State> getStates() {
        return STATES;
    }

    public List<SelectItem> getStatuses() {
        return SelectItemUtil.toList(Status.class, false, Status.DELETED);
    }

    public List<SelectItem> getCellStatuses() {
        return SelectItemUtil.toList(CellStatusType.class, false);
    }
    
    public List<SelectItem> getDotTypes() {
        return SelectItemUtil.toList(RuleSetType.class, false, RuleSetType.SLB_INTERNAL);
    }

    public void measurementTypeChosenAction(){
    	
    	fuelEfficiencyBean.init(getItem().getMeasurementType());
    }

    public static class PersonView extends Person implements EditItem {
        @Column(updateable = false)
        private static final long serialVersionUID = 8954277815270194338L;
        @Column(updateable = false)
        private PersonBean bean;
        @Column(updateable = false)
        private Boolean userEditable;
        @Column(updateable = false)
        private Group group;
        @Column(updateable = false)
        private Group team;
        @Column(updateable = false)
        private String password;
        @Column(updateable = false)
        private String confirmPassword;
        @Column(updateable = false)
        private boolean userSelected;
        @Column(updateable = false)
        private boolean driverSelected;
        @Column(updateable = false)
        private boolean providerInfoSelected;
        @Column(updateable = false)
        private boolean providerInfoExists;
        @Column(updateable = false)
        private String confirmProviderPassword;
        @Column(updateable = false)
        private Cellblock cellblock;

        public Integer getId() {
            return getPersonID();
        }

        public String getName() {
            return getFirst() + ' ' + getLast();
        }

        @Override
        public Integer getHeight() {
            final Integer height = super.getHeight();
            if ((height != null) && (height == 0))
                return null;
            return height;
        }

        @Override
        public Integer getWeight() {
            final Integer weight = super.getWeight();
            if ((weight != null) && (weight == 0))
                return null;
            return weight;
        }

        @Override
        public Date getDob() {
            if (super.getDob() == null || DateUtil.convertDateToSeconds(super.getDob()) == 0)
                return null;
            else
                return super.getDob();
        }

        public Date getDriverExp() {
            if (getDriver() == null || super.getDriver().getExpiration() == null || DateUtil.convertDateToSeconds(super.getDriver().getExpiration()) == 0)
                return null;
            else
                return super.getDriver().getExpiration();
        }

        public void setDriverExp(Date driverExp) {
            if (getDriver() != null)
                super.getDriver().setExpiration(driverExp);
        }

        public Group getGroup() {
            if ((getUser() != null) && ((group == null) || (group.getGroupID() == null) || !group.getGroupID().equals(getUser().getGroupID())))
                group = bean.groupDAO.findByID(getUser().getGroupID());
            return group;
        }

        public Group getTeam() {
            if ((getDriver() != null) && ((team == null) || (team.getGroupID() == null) || !team.getGroupID().equals(getDriver().getGroupID())))
                team = bean.groupDAO.findByID(getDriver().getGroupID());
            return team;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public boolean isUserEditable() {
            if (userEditable == null) {
                // editable if the user is not selected, or if the user's group is within this admin's purview
                userEditable = !isUserSelected() || (getUser().getGroupID() == null);
                if (!userEditable)
                    for (final Group group : bean.getGroupHierarchy().getGroupList())
                        if (group.getGroupID().equals(getUser().getGroupID()))
                            userEditable = true;
            }
            return userEditable;
        }

        public boolean isUserSelected() {
            return userSelected;
        }

        public void setUserSelected(boolean userSelected) {
            this.userSelected = userSelected;
        }

        public boolean isDriverSelected() {
            return driverSelected;
        }

        public void setDriverSelected(boolean driverSelected) {
            this.driverSelected = driverSelected;
            if(!this.driverSelected){
                setProviderInfoSelected(false);
            }
        }

        public boolean isSelected() {
            return bean.getSelectedMap().containsKey(getPersonID()) ? bean.getSelectedMap().get(getPersonID()) : false; 
        }

        public void setSelected(boolean selected) {
            bean.getSelectedMap().put(getPersonID(), selected);
            
        }
        
        @Override
        public Integer getInfo() {
        	return validAccountAlertValue(super.getInfo());
        }
        @Override
        public Integer getWarn() {
        	return validAccountAlertValue(super.getWarn());
        }
        @Override
        public Integer getCrit() {
        	return validAccountAlertValue(super.getCrit());
        }
        
    	private Integer validAccountAlertValue(Integer value) {
            if (value == null || value == AlertText.PHONE_CELL.getCode() ||  // skip cell phone 
               (!bean.isEnablePhoneAlerts() && (value == AlertText.PHONE_1.getCode() || value == AlertText.PHONE_2.getCode())))  // skip phone alerts if account is set to this
               return 0;
    		return value;
    	}
    	public void setLocale(Locale locale){
    		
    		if (locale == null) {
    			super.setLocale(LocaleBean.getCurrentLocale());
    		}
    		else {
    			super.setLocale(locale);
    		}
    	}
    	public List<String> getRoleNames(){
    		
    		if(bean.getAccountRoles() == null){
    			
    			bean.initAccountRoles();
    		}
    		List<String> roleNames = new ArrayList<String>();
    		
    		if (getUser() == null) return null;
    		
    		List<Integer> roleIDs = getUser().getRoles();
    		if (roleIDs == null){
    			
    			roleIDs = new ArrayList<Integer>();
    		}
    		if (roleIDs.isEmpty()){
    			
    			roleIDs.add(bean.getAccountRoles().getRoleByName("Normal").getRoleID());
    		}
    		for(Integer id:roleIDs){
    			
    			roleNames.add(bean.getAccountRoles().getRoleById(id).getName());
    		}
    		return roleNames;
    	}
    	public String getRolesString(){
    		
    		if ((getUser() != null) && getUser().hasRoles() && (getUser().getRoles().size()>0)){
    			
        		if(bean.getAccountRoles() == null){
        			
        			bean.initAccountRoles();
        		}

               	Roles roles =  bean.getAccountRoles();
                List<Integer> roleIDs = getUser().getRoles();
                StringBuffer rolesbuffer = new StringBuffer();
                for (Integer id:roleIDs){
                	
                	if(roles.getRoleById(id) != null){
	                	rolesbuffer.append(roles.getRoleById(id).getName());
	                	rolesbuffer.append(", ");
                	}
                }
                if (rolesbuffer.length() > 2)
                	rolesbuffer.setLength(rolesbuffer.length()-2);
        		return rolesbuffer.toString();
    		}
    		return "";
    	}

        public boolean isProviderInfoSelected() {
            return providerInfoSelected;
        }

        public void setProviderInfoSelected(boolean providerInfoSelected) {
            this.providerInfoSelected = providerInfoSelected;
        }

        public String getConfirmProviderPassword() {
            return confirmProviderPassword;
        }

        public void setConfirmProviderPassword(String confirmProviderPassword) {
            this.confirmProviderPassword = confirmProviderPassword;
        }

        public Cellblock getCellblock() {
            return cellblock;
        }

        public void setCellblock(Cellblock cellblock) {
            this.cellblock = cellblock;
        }

        public boolean isProviderInfoExists() {
            return providerInfoExists;
        }

        public void setProviderInfoExists(boolean providerInfoExists) {
            this.providerInfoExists = providerInfoExists;
        }


        public String getTimeZoneDisplay() {
            return bean.getTimeZoneDisplayName(getTimeZone());
        }
   }
		
	public Roles getAccountRoles(){
		
		return accountRoles;
	}
	
	public void initAccountRoles(){
		
        accountRoles = new Roles();
        accountRoles.setRoleDAO(roleDAO);
        accountRoles.init(getAccountID());
	}
	
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
    public enum AlertText {
        NONE(0),
        EMAIL_1(1),
        EMAIL_2(2),
        PHONE_1(3),
        PHONE_2(4),
        PHONE_CELL(5),
        TEXT_MSG_1(6),
        TEXT_MSG_2(7);

        private Integer code;

        private AlertText(Integer code) {
            this.code = code;
        };

        public Integer getCode() {
            return this.code;
        }


        private static final Map<Integer, AlertText> lookup = new HashMap<Integer, AlertText>();
        static {
            for (AlertText p : EnumSet.allOf(AlertText.class)) {
                lookup.put(p.code, p);
            }
        }

        public static AlertText getAlertText(Integer code) {
            return lookup.get(code);
        }
    }

    public UpdateCredentialsBean getUpdateCredentialsBean() {
        return updateCredentialsBean;
    }

    public void setUpdateCredentialsBean(UpdateCredentialsBean updateCredentialsBean) {
        this.updateCredentialsBean = updateCredentialsBean;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public void setPhoneControlDAO(PhoneControlDAO phoneControlDAO) {
        this.phoneControlDAO = phoneControlDAO;
    }

    // overriding because the pagination doesn't use the filtered list 

    @Override
    public List<PersonBean.PersonView> getFilteredItems() {
        filteredItems.clear();
        filteredItems.addAll(items);
        return filteredItems;
    }

    @Override
    protected void applyFilter(int page)
    {
        filteredItems.clear();
        filteredItems.addAll(items);
    }

    @Override
    public void setSelectAll(boolean selectAll)
    {
        this.selectAll = selectAll;
    }
    
    @Override
    public boolean isSelectAll() {
    	return this.selectAll;
    }


    @Override
    public void doSelectAll() {
		selectedMap = new HashMap<Integer, Boolean>();
		if (selectAll == true) {
			for (PersonIdentifiers personIdentifiers : personIdentifiersList) {
				selectedMap.put(personIdentifiers.getPersonID(),  Boolean.TRUE);
			}
		}
    }

    @Override
    public void setItems(List<PersonView> items )
    {
    	super.setItems(items);
    }
    
    
    public void initPersonIdentifierList(List<PersonIdentifiers> personIdentifiersList )
    {
    	this.personIdentifiersList = personIdentifiersList;
        this.selectAll = Boolean.FALSE;
    	selectedMap = new HashMap<Integer, Boolean>();
    }

    public void updateItemSelect(Integer id, Boolean selected) {
		selectedMap.put(id,  selected);
    	
    }

	public Map<Integer, Boolean> getSelectedMap() {
		return selectedMap;
	}

	public void setSelectedMap(Map<Integer, Boolean> selectedMap) {
		this.selectedMap = selectedMap;
	}

}
