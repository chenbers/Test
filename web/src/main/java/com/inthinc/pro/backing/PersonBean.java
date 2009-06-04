/**
 * 
 */
package com.inthinc.pro.backing;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;
/**
 * @author David Gileadi
 */
public class PersonBean extends BaseAdminBean<PersonBean.PersonView> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 14, 24, 11 };
    private static final Map<String, Integer> HEIGHTS;
    private static final int MIN_HEIGHT = 48;
    private static final int MAX_HEIGHT = 86;
    private static final Map<String, Integer> WEIGHTS;
    private static final int MIN_WEIGHT = 75;
    private static final int MAX_WEIGHT = 300;
    private static final Map<String, TimeZone> TIMEZONES;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final Map<String, String> LICENSE_CLASSES;
    private static final Map<String, State> STATES;
    private static final String REQUIRED_KEY = "required";
    static
    {
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
        AVAILABLE_COLUMNS.add("height");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("address_addr1");
        AVAILABLE_COLUMNS.add("address_addr2");
        AVAILABLE_COLUMNS.add("address_city");
        AVAILABLE_COLUMNS.add("address_state");
        AVAILABLE_COLUMNS.add("address_zip");
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
        AVAILABLE_COLUMNS.add("driver_RFID");
        AVAILABLE_COLUMNS.add("driver_groupID");
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
        // time zones
        final List<String> timeZones = SupportedTimeZones.getSupportedTimeZones();
        // sort by offset from GMT
        Collections.sort(timeZones, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                final TimeZone t1 = TimeZone.getTimeZone(o1);
                final TimeZone t2 = TimeZone.getTimeZone(o2);
                return t1.getRawOffset() - t2.getRawOffset();
            }
        });
        TIMEZONES = new LinkedHashMap<String, TimeZone>();
        final NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(2);
        for (final String id : timeZones)
        {
            final TimeZone timeZone = TimeZone.getTimeZone(id);
            final int offsetHours = timeZone.getRawOffset() / MILLIS_PER_HOUR;
            final int offsetMinutes = Math.abs((timeZone.getRawOffset() % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE);
            if (offsetHours < 0)
                TIMEZONES.put(timeZone.getID() + " (GMT" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
            else
                TIMEZONES.put(timeZone.getID() + " (GMT+" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
        }
        // license classes
        LICENSE_CLASSES = new TreeMap<String, String>();
        for (char c = 'A'; c <= 'P'; c++)
            LICENSE_CLASSES.put(String.valueOf(c), String.valueOf(c));
        // states
        STATES = new TreeMap<String, State>();
        for (final State state : States.getStates().values())
            STATES.put(state.getName(), state);
       
        
    }
    private PersonDAO personDAO;
    private UserDAO userDAO;
    private DriverDAO driverDAO;
    private PasswordEncryptor passwordEncryptor;
    private List<PersonChangeListener> changeListeners;

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor)
    {
        this.passwordEncryptor = passwordEncryptor;
    }

    public void setPersonChangeListeners(List<PersonChangeListener> changeListeners)
    {
        this.changeListeners = changeListeners;
    }

    private void notifyChangeListeners()
    {
        if (changeListeners != null)
            for (final PersonChangeListener listener : changeListeners)
                listener.personListChanged();
    }

    @Override
    protected List<PersonView> loadItems()
    {
        // get the people
        final List<Person> plainPeople = personDAO.getPeopleInGroupHierarchy(getTopGroup().getGroupID(), getUser().getGroupID());
        // filter out people who don't belong
        for (final Iterator<Person> i = plainPeople.iterator(); i.hasNext();)
        {
            final Person person = i.next();
            if ((person.getDriver() == null) && (getGroupHierarchy().getGroup(person.getUser().getGroupID()) == null))
                i.remove();
        }
        // convert the people to PersonViews
        final LinkedList<PersonView> items = new LinkedList<PersonView>();
        for (final Person person : plainPeople)
            items.add(createPersonView(person));
        return items;
    }

    /**
     * Creates a PersonView object from the given Person object and score.
     * 
     * @param person
     *            The person.
     * @return The new PersonView object.
     */
    private PersonView createPersonView(Person person)
    {
        final PersonView personView = new PersonView();
        personView.bean = this;
        BeanUtils.copyProperties(person, personView);
        if (personView.getAddress() == null)
            personView.setAddress(new Address());
        personView.setUserSelected(person.getUser() != null);
        personView.setDriverSelected(person.getDriver() != null);
        personView.setSelected(false);
        if (person.getUser() != null)
            personView.getUser().setPerson(personView);
        if ((person.getDriver() != null) && (person.getDriver().getRFID() != null) && (person.getDriver().getRFID() == 0))
            person.getDriver().setRFID(null);
        return personView;
    }

    @Override
    public String fieldValue(PersonView person, String column)
    {
        if (column.equals("user_status"))
        {
            if ((person.getUser() != null) && (person.getUser().getStatus() != null))
                return MessageUtil.getMessageString(person.getUser().getStatus().getDescription().toLowerCase());
            return null;
        }
        else if (column.equals("driver_status"))
        {
            if ((person.getDriver() != null) && (person.getDriver().getStatus() != null))
                return MessageUtil.getMessageString(person.getDriver().getStatus().getDescription().toLowerCase());
            return null;
        }
        else if (column.equals("user_groupID"))
        {
            if (person.getGroup() != null)
                return person.getGroup().getName();
            return null;
        }
        else if (column.equals("driver_groupID"))
        {
            if (person.getTeam() != null)
                return person.getTeam().getName();
            return null;
        }
        else if (column.equals("timeZone"))
        {
            if (person.getTimeZone() != null)
                return person.getTimeZone().getDisplayName();
            return null;
        }
        else if (column.equals("info"))
        {
            Integer value = person.getInfo();
            if (value == null)
                value = 0;
            return MessageUtil.getMessageString("myAccount_alertText" + value);
        }
        else if (column.equals("warn"))
        {
            Integer value = person.getWarn();
            if (value == null)
                value = 0;
            return MessageUtil.getMessageString("myAccount_alertText" + value);
        }
        else if (column.equals("crit"))
        {
            Integer value = person.getCrit();
            if (value == null)
                value = 0;
            return MessageUtil.getMessageString("myAccount_alertText" + value);
        }
        else if (column.equals("driver_RFID"))
        {
            Long value = null;
            if (person.getDriver() != null)
                value = person.getDriver().getRFID();
            if (value == null)
                value = 0L;
            return Long.toHexString(value);
        }
        else
            return super.fieldValue(person, column);
    }

    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    public String getColumnLabelPrefix()
    {
        return "personHeader_";
    }

    @Override
    public TableType getTableType()
    {
        return TableType.ADMIN_PEOPLE;
    }

    @Override
    protected PersonView createAddItem()
    {
        final PersonView person = new PersonView();
        person.bean = this;
        person.setStatus(Status.ACTIVE);
        person.setUser(new User());
        // TODO: maybe use the browser's time zone instead, if possible...
        person.setTimeZone(TimeZone.getDefault());
        person.setAddress(new Address());
        person.getUser().setRole(Role.valueOf(2)); // normal user
        person.getUser().setPerson(person);
        
        Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        
        if(LocaleBean.supportedLocale(locale))
            person.getUser().setLocale(locale);
        else
            person.getUser().setLocale(Locale.US);
        
        person.setDriver(new Driver());
        person.setUserSelected(true);
        person.setDriverSelected(true);
        return person;
    }

    @Override
    public PersonView getItem()
    {
        final PersonView item = super.getItem();
        if (item.getUser() == null)
        {
            item.setUser(new User());
            item.getUser().setPerson(item);
        }
        if (item.getDriver() == null)
        {
            item.setDriver(new Driver());
            item.getDriver().setPersonID(item.getPersonID());
        }
        if ((item.getDriver().getRFID() != null) && (item.getDriver().getRFID() == 0))
            item.getDriver().setRFID(null);
        return item;
    }

    @Override
    public String display()
    {
        final String redirect = super.display();
        // if displaying the current user, reload from the DB in case changed elsewhere
        if (getItem().isUserSelected() && getItem().getUser().getUserID().equals(getUserID()))
        {
            item = revertItem(getItem());
        }
        return redirect;
    }
    
    @Override
    public void view()
    {
        super.view();
        if (getItem().isUserSelected() && getItem().getUser().getUserID().equals(getUserID()))
        {
            item = revertItem(getItem());
        }
    }

    @Override
    public String edit()
    {
        final String redirect = super.edit();
        // if editing the current user, reload from the DB in case changed elsewhere
        if (getItem().isUserSelected() && getItem().getUser().getUserID().equals(getUserID()))
        {
            item = revertItem(getItem());
        }
        return redirect;
    }

    @Override
    public String delete()
    {
        final String result = super.delete();
        notifyChangeListeners();
        return result;
    }

    @Override
    public String batchEdit()
    {
        String returnValue = super.batchEdit();
        if (item != null)
        {
            item.setUserSelected(true);
            item.setDriverSelected(true);
        }
        return returnValue;
    }

    @Override
    public String save()
    {
        if (isBatchEdit())
        {
            final boolean role = Boolean.TRUE.equals(getUpdateField().get("user.role"));
            getUpdateField().put("user.role.roleID", role);
            getUpdateField().put("user.role.name", role);
        }
        // see if we're partially editing one of the batch items
        boolean partialEdit = isBatchEdit() && getItem().isUserEditable();
        if (partialEdit)
        {
            partialEdit = false;
            for (final PersonView person : getSelectedItems())
                if (!person.isUserEditable())
                {
                    partialEdit = true;
                    break;
                }
        }
        final String result = super.save();
        // revert partial-edit changes if user editable
        if ((result != null) && partialEdit)
        {
            items = null;
            getItems();
            final String summary = MessageUtil.getMessageString("editPerson_partialUpdate");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        notifyChangeListeners();
        return result;
    }

    @Override
    protected void doDelete(List<PersonView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final PersonView person : deleteItems)
        {
            personDAO.delete(person);
            // add a message
            final String summary = MessageUtil.formatMessageString("person_deleted", person.getFirst(), person.getLast());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    protected boolean validateSaveItem(PersonView person)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        // required Time Zone
        if (person.getTimeZone() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("timeZone"))))
        {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
            context.addMessage("edit-form:timeZone", message);
        }
        // unique primary e-mail
        if (!isBatchEdit() && (person.getPriEmail() != null) && (person.getPriEmail().length() > 0))
        {
            final Person byEmail = personDAO.findByEmail(person.getPriEmail());
            if ((byEmail != null) && !byEmail.getPersonID().equals(person.getPersonID()))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editPerson_uniqueEmail");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:priEmail", message);
            }
        }
        // birth date
        if (!isBatchEdit() && (person.getDob() != null))
        {
            Calendar latest = Calendar.getInstance();
            latest.add(Calendar.YEAR, -16);
            if (person.getDob().after(latest.getTime()))
            {
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_dobTooLate"), null);
                context.addMessage("edit-form:dob", message);
                valid = false;
            }
        }
        // driver license expiration
        if (person.isDriverSelected())
        {
            // required team
            if (person.getDriver().getGroupID() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("driver.groupID"))))
            {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:driver_groupID", message);
            }
            // required user status
            if (person.getDriver().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("driver.status"))))
            {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:driver_status", message);
            }
            if ((person.getDriver().getExpiration() != null) && person.getDriver().getExpiration().before(new Date())
                    && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("driver.expiration"))))
            {
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_expirationTooSoon"), null);
                context.addMessage("edit-form:driver_expiration", message);
                valid = false;
            }
            // unique RFID
            if (!isBatchEdit() && (person.getDriver().getRFID() != null) && (person.getDriver().getRFID() != 0))
            {
                final Integer byRFID = driverDAO.getDriverIDForRFID(person.getDriver().getRFID());
                if ((byRFID != null) && !byRFID.equals(person.getDriver().getDriverID()))
                {
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_uniqueRFID"), null);
                    context.addMessage("edit-form:driver_RFID", message);
                    valid = false;
                }
            }
        }
        // unique username
        if (person.isUserSelected())
        {
            // required groupID
            if (person.getUser().getGroupID() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.groupID"))))
            {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:user_groupID", message);
            }
            // required user role
            if (person.getUser().getRole() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.role"))))
            {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:user_role", message);
            }
            // required user status
            if (person.getUser().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.status"))))
            {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:user_status", message);
            }
            
            // required locale
            if (person.getUser().getStatus() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("user.locale"))))
            {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
                context.addMessage("edit-form:user_locale", message);
            }
            
            if (!isBatchEdit())
            {
                final User byUsername = userDAO.findByUserName(person.getUser().getUsername());
                if ((byUsername != null) && !byUsername.getPersonID().equals(person.getPersonID()))
                {
                    valid = false;
                    final String summary = MessageUtil.getMessageString("editPerson_uniqueUsername");
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    context.addMessage("edit-form:user_username", message);
                }
                // matching passwords
                if ((person.getPassword() != null) && (person.getPassword().length() > 0) && !person.getPassword().equals(person.getConfirmPassword()))
                {
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_passwordsMismatched"), null);
                    context.addMessage("edit-form:confirmPassword", message);
                    valid = false;
                }
            }
            // required pri email
            if (!isBatchEdit() && (person.getPriEmail() == null || person.getPriEmail().equals("")))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString(REQUIRED_KEY);
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:priEmail", message);
            }
        }
        // must be a user or a driver or both while not in batch edit.
        else if (!person.isDriverSelected() && !isBatchEdit())
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editPerson_userOrDriver"), null);
            context.addMessage("edit-form:isUser", message);
            valid = false;
        }
        return valid;
    }

    @Override
    protected PersonView revertItem(PersonView editItem)
    {
        return createPersonView(personDAO.findByID(editItem.getPersonID()));
    }

    @Override
    protected void doSave(List<PersonView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final PersonView person : saveItems)
        {
            if ((person.getPassword() != null) && (person.getPassword().length() > 0))
                person.getUser().setPassword(passwordEncryptor.encryptPassword(person.getPassword()));
            // null out the user/driver before saving
            if (!person.isUserSelected() && (person.getUser() != null))
            {
                if (person.getUser().getUserID() != null)
                    userDAO.deleteByID(person.getUser().getUserID());
                person.setUser(null);
            }
            if (!person.isDriverSelected() && (person.getDriver() != null))
            {
                if (person.getDriver().getDriverID() != null)
                    driverDAO.deleteByID(person.getDriver().getDriverID());
                person.setDriver(null);
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
            if ((person.getDriver() != null) && (person.getDriver().getRFID() == null))
                person.getDriver().setRFID(0L);
            // insert or update
            if (create)
                person.setPersonID(personDAO.create(getAccountID(), person));
            else
            {
                if (person.isUserEditable())
                    personDAO.update(person);
                else if (person.isDriverSelected())
                    driverDAO.update(person.getDriver());
                // if updating the currently-logged-in person, update the proUser
                if ((person.getUser() != null) && person.getUser().getUserID().equals(getUserID()))
                    BeanUtil.deepCopy(person.getUser(), getUser());
            }
            // set zero RFID back to null
            if ((person.getDriver() != null) && (person.getDriver().getRFID() == 0))
                person.getDriver().setRFID(null);
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "person_added" : "person_updated", person.getFirst(), person.getLast());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "pretty:adminPerson";
    }

    @Override
    protected String getEditRedirect()
    {
        return "pretty:adminEditPerson";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "pretty:adminPeople";
    }

    public TimeZone getUtcTimeZone()
    {
        return TimeZone.getTimeZone("UTC");
    }

    public List<SelectItem> getGenders()
    {
        return SelectItemUtil.toList(Gender.class, false);
    }

    public Map<String, Integer> getHeights()
    {
        return HEIGHTS;
    }

    public Map<String, Integer> getWeights()
    {
        return WEIGHTS;
    }

    public Map<String, Integer> getAlertOptions()
    {
        // alert options
        LinkedHashMap<String, Integer> alertOptions = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 8; i++)
            if (i != 5) // skip cell phone
                alertOptions.put(MessageUtil.getMessageString("myAccount_alertText" + i), i);
        return alertOptions;
    }

    public List<String> findPeople(Object event)
    {
        final List<String> results = new ArrayList<String>();
        final String name = event.toString().trim().toLowerCase();
        if (name.length() > 0)
            for (final PersonView person : getItems())
                if (person.getFullName().toLowerCase().contains(name))
                    results.add(person.getFullName());
        return results;
    }

    public Map<String, Integer> getGroups()
    {
        final TreeMap<String, Integer> groups = new TreeMap<String, Integer>();
        for (final Group group : getGroupHierarchy().getGroupList())
            groups.put(group.getName(), group.getGroupID());
        return groups;
    }

    public Map<String, Integer> getTeams()
    {
        final TreeMap<String, Integer> teams = new TreeMap<String, Integer>();
        for (final Group group : getGroupHierarchy().getGroupList())
            if (group.getType() == GroupType.TEAM)
                teams.put(group.getName(), group.getGroupID());
        return teams;
    }

    public Map<String, TimeZone> getTimeZones()
    {
        return TIMEZONES;
    }

    public List<SelectItem> getRoles()
    {
        List<SelectItem> roleList = new ArrayList<SelectItem>();
        for (Role role : Roles.getRoleList())
        {
            roleList.add(new SelectItem(role, role.getName()));
        }
        roleList.add(0, new SelectItem(null, ""));
        return roleList;
    }

    public Map<String, String> getLicenseClasses()
    {
        return LICENSE_CLASSES;
    }

    public Map<String, State> getStates()
    {
        return STATES;
    }

    public List<SelectItem> getStatuses()
    {
        return SelectItemUtil.toList(Status.class, false, Status.DELETED);
    }

    public static class PersonView extends Person implements EditItem
    {
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
        private boolean selected;

        public Integer getId()
        {
            return getPersonID();
        }

        public String getName()
        {
            return getFirst() + ' ' + getLast();
        }

        @Override
        public Integer getHeight()
        {
            final Integer height = super.getHeight();
            if ((height != null) && (height == 0))
                return null;
            return height;
        }

        @Override
        public Integer getWeight()
        {
            final Integer weight = super.getWeight();
            if ((weight != null) && (weight == 0))
                return null;
            return weight;
        }

        @Override
        public Date getDob()
        {
            if(super.getDob() == null || DateUtil.convertDateToSeconds(super.getDob()) == 0)
                return null;
            else
                return super.getDob();
        }
        
        public Date getDriverExp()
        {
            if(getDriver() == null || super.getDriver().getExpiration() == null || DateUtil.convertDateToSeconds(super.getDriver().getExpiration()) == 0)
                return null;
            else
                return super.getDriver().getExpiration();
        }
        
        public void setDriverExp(Date driverExp)
        {
            if(getDriver() != null)
                super.getDriver().setExpiration(driverExp);
        }
        
        public Group getGroup()
        {
            if ((getUser() != null) && ((group == null) || (group.getGroupID() == null) || !group.getGroupID().equals(getUser().getGroupID())))
                group = bean.groupDAO.findByID(getUser().getGroupID());
            return group;
        }

        public Group getTeam()
        {
            if ((getDriver() != null) && ((team == null) || (team.getGroupID() == null) || !team.getGroupID().equals(getDriver().getGroupID())))
                team = bean.groupDAO.findByID(getDriver().getGroupID());
            return team;
        }

        public Double getCostPerHourDollars()
        {
            if (getCostPerHour() != null)
                return ((double) getCostPerHour()) / 100;
            return null;
        }

        public void setCostPerHourDollars(Double costPerHourDollars)
        {
            if ((costPerHourDollars != null) && (costPerHourDollars > 0))
                setCostPerHour((int) (costPerHourDollars * 100));
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword)
        {
            this.confirmPassword = confirmPassword;
        }

        public boolean isUserEditable()
        {
            if (userEditable == null)
            {
                // editable if the user is not selected, or if the user's group is within this admin's purview
                userEditable = !isUserSelected() || (getUser().getGroupID() == null);
                if (!userEditable)
                    for (final Group group : bean.getGroupHierarchy().getGroupList())
                        if (group.getGroupID().equals(getUser().getGroupID()))
                            userEditable = true;
            }
            return userEditable;
        }

        public boolean isUserSelected()
        {
            return userSelected;
        }

        public void setUserSelected(boolean userSelected)
        {
            this.userSelected = userSelected;
        }

        public boolean isDriverSelected()
        {
            return driverSelected;
        }

        public void setDriverSelected(boolean driverSelected)
        {
            this.driverSelected = driverSelected;
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
    }
}
