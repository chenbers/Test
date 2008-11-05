/**
 * 
 */
package com.inthinc.pro.backing;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.User;
import com.inthinc.pro.util.MessageUtil;

/**
 * @author David Gileadi
 */
public class PersonBean extends BaseAdminBean<PersonBean.PersonView>
{
    private static final List<String>                  AVAILABLE_COLUMNS;
    private static final int[]                         DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2 };

    private static final TreeMap<String, Gender>       GENDERS;
    private static final TreeMap<String, Integer>      HEIGHTS;
    private static final int                           MIN_HEIGHT             = 48;
    private static final int                           MAX_HEIGHT             = 86;
    private static final TreeMap<String, Integer>      WEIGHTS;
    private static final int                           MIN_WEIGHT             = 75;
    private static final int                           MAX_WEIGHT             = 300;
    private static final LinkedHashMap<String, String> TIMEZONES;
    private static final int                           MILLIS_PER_MINUTE      = 1000 * 60;
    private static final int                           MILLIS_PER_HOUR        = MILLIS_PER_MINUTE * 60;
    private static final TreeMap<String, String>       LICENSE_CLASSES;
    private static final TreeMap<String, State>        STATES;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("empid");
        AVAILABLE_COLUMNS.add("fullName");
        AVAILABLE_COLUMNS.add("user_active");
        AVAILABLE_COLUMNS.add("user_username");
        AVAILABLE_COLUMNS.add("user_role");
        AVAILABLE_COLUMNS.add("workPhone");
        AVAILABLE_COLUMNS.add("homePhone");
        AVAILABLE_COLUMNS.add("email");
        AVAILABLE_COLUMNS.add("timeZone");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("reportsTo");
        AVAILABLE_COLUMNS.add("title");
        AVAILABLE_COLUMNS.add("department");
        AVAILABLE_COLUMNS.add("dob");
        AVAILABLE_COLUMNS.add("gender");
        AVAILABLE_COLUMNS.add("height");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("address_addr1");
        AVAILABLE_COLUMNS.add("address_addr2");
        AVAILABLE_COLUMNS.add("address_city");
        AVAILABLE_COLUMNS.add("address_state");
        AVAILABLE_COLUMNS.add("address_zip");
        AVAILABLE_COLUMNS.add("driver_active");
        AVAILABLE_COLUMNS.add("driver_license");
        AVAILABLE_COLUMNS.add("driver_licenseClass");
        AVAILABLE_COLUMNS.add("driver_state");
        AVAILABLE_COLUMNS.add("driver_expiration");

        // genders
        Gender[] genders = Gender.values();
        GENDERS = new TreeMap<String, Gender>();
        for (final Gender gender : genders)
            GENDERS.put(gender.getDescription(), gender);

        // heights
        HEIGHTS = new TreeMap<String, Integer>();
        for (int i = MIN_HEIGHT; i < MAX_HEIGHT; i++)
            if ((i % 12) != 0)
                HEIGHTS.put((i / 12) + "' " + (i % 12) + '"', i);
            else
                HEIGHTS.put((i / 12) + "'", i);

        // weights
        WEIGHTS = new TreeMap<String, Integer>();
        for (int i = MIN_WEIGHT; i < MAX_WEIGHT; i++)
            WEIGHTS.put(String.valueOf(i), i);

        // get all time zones
        final List<String> timeZones = new ArrayList<String>();
        for (final String id : TimeZone.getAvailableIDs())
            if (!id.startsWith("Etc/"))
                timeZones.add(id);
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
        TIMEZONES = new LinkedHashMap<String, String>();
        final NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(2);
        for (final String id : timeZones)
        {
            final TimeZone timeZone = TimeZone.getTimeZone(id);
            final int offsetHours = timeZone.getRawOffset() / MILLIS_PER_HOUR;
            final int offsetMinutes = Math.abs((timeZone.getRawOffset() % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE);
            if (offsetHours < 0)
                TIMEZONES.put(timeZone.getDisplayName() + " (GMT" + offsetHours + ':' + format.format(offsetMinutes) + ')', id);
            else
                TIMEZONES.put(timeZone.getDisplayName() + " (GMT+" + offsetHours + ':' + format.format(offsetMinutes) + ')', id);
        }

        // license classes
        LICENSE_CLASSES = new TreeMap<String, String>();
        for (char c = 'A'; c <= 'P'; c++)
            LICENSE_CLASSES.put(String.valueOf(c), String.valueOf(c));

        // states
        final State[] states = State.values();
        STATES = new TreeMap<String, State>();
        for (final State state : states)
            STATES.put(state.getName(), state);
    }

    private PersonDAO                                  personDAO;
    private GroupDAO                                   groupDAO;
    private TreeMap<String, Integer>                   groups;
    private TreeMap<String, Integer>                   reportsToOptions;

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    @Override
    protected List<PersonView> loadItems()
    {
        // get the people
        final List<Person> plainPeople = personDAO.getPeopleInGroupHierarchy(getUser().getPerson().getGroupID());

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
        BeanUtils.copyProperties(person, personView);

        if (personView.getAddress() == null)
            personView.setAddress(new Address());
        personView.setGroup(groupDAO.findByID(person.getGroupID()));
        personView.setReportsToPerson(personDAO.findByID(person.getReportsTo()));
        personView.setSelected(false);

        return personView;
    }

    @Override
    protected boolean matchesFilter(PersonView person, String filterWord)
    {
        for (final String column : columns.keySet())
            if (columns.get(column))
            {
                boolean matches = false;
                if (column.equals("fullName"))
                    matches = person.getFirst().toLowerCase().startsWith(filterWord) || person.getLast().toLowerCase().startsWith(filterWord);
                else if (column.equals("user_active"))
                    matches = (person.getUser() != null)
                            && (person.getUser().getActive() != null)
                            && ((person.getUser().getActive() && MessageUtil.getMessageString("active").toLowerCase().startsWith(filterWord)) || ((!person.getUser().getActive() && MessageUtil
                                    .getMessageString("inactive").toLowerCase().startsWith(filterWord))));
                else if (column.equals("driver_active"))
                    matches = (person.getDriver() != null)
                            && (person.getDriver().getActive() != null)
                            && ((person.getDriver().getActive() && MessageUtil.getMessageString("active").toLowerCase().startsWith(filterWord)) || ((!person.getDriver()
                                    .getActive() && MessageUtil.getMessageString("inactive").toLowerCase().startsWith(filterWord))));
                else if (column.equals("group"))
                    matches = (person.getGroup() != null) && person.getGroup().getName().toLowerCase().startsWith(filterWord);
                else if (column.equals("reportsTo"))
                    matches = (person.getReportsToPerson() != null)
                            && (person.getReportsToPerson().getFirst().toLowerCase().startsWith(filterWord) || person.getReportsToPerson().getLast().toLowerCase().startsWith(
                                    filterWord));
                else
                    try
                    {
                        matches = String.valueOf(org.apache.commons.beanutils.BeanUtils.getProperty(person, column.replace('_', '.'))).toLowerCase().startsWith(filterWord);
                    }
                    catch (Exception e)
                    {
                        logger.error("Error filtering on column " + column, e);
                    }

                if (matches)
                    return true;
            }

        return false;
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
    public void saveColumns()
    {
        // TODO: save the columns
    }

    @Override
    protected PersonView createAddItem()
    {
        final PersonView person = new PersonView();
        person.setUser(new User());
        // TODO: maybe use the browser's time zone instead, if possible...
        person.setTimeZone(TimeZone.getDefault());
        person.setAddress(new Address());
        person.getUser().setPerson(person);
        person.setDriver(new Driver());
        return person;
    }

    /**
     * @return Whether the person is also a user.
     */
    public boolean isUser()
    {
        final PersonView editItem = getEditItem();
        if (editItem != null)
            return editItem.getUser() != null;
        return false;
    }

    /**
     * Sets whether the person is also a user--creating or nulling a User object for the person as necessary.
     * 
     * @param user
     *            Whether the person is also a user.
     */
    public void setUser(boolean user)
    {
        final PersonView editItem = getEditItem();
        if (editItem != null)
        {
            if (user && (editItem.getUser() == null))
            {
                editItem.setUser(new User());
                editItem.getUser().setPerson(editItem);
            }
            else if (!user && (editItem.getUser() != null))
                editItem.setUser(null);
        }
    }

    /**
     * @return Whether the person is also a driver.
     */
    public boolean isDriver()
    {
        final PersonView editItem = getEditItem();
        if (editItem != null)
            return editItem.getDriver() != null;
        return false;
    }

    /**
     * Sets whether the person is also a driver--creating or nulling a Driver object for the person as necessary.
     * 
     * @param driver
     *            Whether the person is also a driver.
     */
    public void setDriver(boolean driver)
    {
        final PersonView editItem = getEditItem();
        if (editItem != null)
        {
            if (driver && (editItem.getDriver() == null))
            {
                editItem.setDriver(new Driver());
                editItem.getDriver().setPersonID(editItem.getPersonID());
            }
            else if (!driver && (editItem.getDriver() != null))
                editItem.setDriver(null);
        }
    }

    @Override
    protected void doDelete(List<PersonView> deleteItems)
    {
        reportsToOptions = null;

        for (final PersonView person : deleteItems)
            personDAO.deleteByID(person.getPersonID());
    }

    @Override
    protected void doSave(List<PersonView> saveItems)
    {
        // if adding a user, reset the potential supervisor list
        if (isAdd())
            reportsToOptions = null;

        for (final PersonView person : saveItems)
            personDAO.update(person);
    }

    @Override
    protected String getEditRedirect()
    {
        return "go_adminEditPerson";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "go_adminPeople";
    }

    public TreeMap<String, Gender> getGenders()
    {
        return GENDERS;
    }

    public TreeMap<String, Integer> getHeights()
    {
        return HEIGHTS;
    }

    public TreeMap<String, Integer> getWeights()
    {
        return WEIGHTS;
    }

    public TreeMap<String, Integer> getReportsToOptions()
    {
        if (reportsToOptions == null)
        {
            reportsToOptions = new TreeMap<String, Integer>();
            for (final PersonView person : items)
                reportsToOptions.put(person.getFirst() + " " + person.getLast(), person.getPersonID());
        }
        return reportsToOptions;
    }

    public TreeMap<String, Integer> getGroups()
    {
        if (groups == null)
        {
            groups = new TreeMap<String, Integer>();
            final GroupHierarchy hierarchy = getProUser().getGroupHierarchy();
            for (final Group group : hierarchy.getGroupList())
                groups.put(group.getName(), group.getGroupID());
        }
        return groups;
    }

    public LinkedHashMap<String, String> getTimeZones()
    {
        return TIMEZONES;
    }

    public TreeMap<String, String> getLicenseClasses()
    {
        return LICENSE_CLASSES;
    }

    public TreeMap<String, State> getStates()
    {
        return STATES;
    }

    public static class PersonView extends Person implements Selectable
    {
        private Group   group;
        private Person  reportsToPerson;
        private String  confirmPassword;
        private boolean selected;

        public Group getGroup()
        {
            return group;
        }

        public void setGroup(Group group)
        {
            this.group = group;
        }

        public Person getReportsToPerson()
        {
            return reportsToPerson;
        }

        public void setReportsToPerson(Person reportsToPerson)
        {
            this.reportsToPerson = reportsToPerson;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword)
        {
            this.confirmPassword = confirmPassword;
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
