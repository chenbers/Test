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
    private static final List<String>                    AVAILABLE_COLUMNS;
    private static final int[]                           DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 6, 19 };

    private static final TreeMap<String, Gender>         GENDERS;
    private static final LinkedHashMap<String, Integer>  HEIGHTS;
    private static final int                             MIN_HEIGHT             = 48;
    private static final int                             MAX_HEIGHT             = 86;
    private static final TreeMap<String, Integer>        WEIGHTS;
    private static final int                             MIN_WEIGHT             = 75;
    private static final int                             MAX_WEIGHT             = 300;
    private static final LinkedHashMap<String, TimeZone> TIMEZONES;
    private static final int                             MILLIS_PER_MINUTE      = 1000 * 60;
    private static final int                             MILLIS_PER_HOUR        = MILLIS_PER_MINUTE * 60;
    private static final TreeMap<String, String>         LICENSE_CLASSES;
    private static final TreeMap<String, State>          STATES;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("empid");
        AVAILABLE_COLUMNS.add("fullName");
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
        AVAILABLE_COLUMNS.add("user_active");
        AVAILABLE_COLUMNS.add("user_username");
        AVAILABLE_COLUMNS.add("user_role");
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
        TIMEZONES = new LinkedHashMap<String, TimeZone>();
        final NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(2);
        for (final String id : timeZones)
        {
            final TimeZone timeZone = TimeZone.getTimeZone(id);
            final int offsetHours = timeZone.getRawOffset() / MILLIS_PER_HOUR;
            final int offsetMinutes = Math.abs((timeZone.getRawOffset() % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE);
            if (offsetHours < 0)
                TIMEZONES.put(timeZone.getDisplayName() + " (GMT" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
            else
                TIMEZONES.put(timeZone.getDisplayName() + " (GMT+" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
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

    private PersonDAO                                    personDAO;
    private GroupDAO                                     groupDAO;
    private TreeMap<String, Integer>                     groups;
    private TreeMap<String, Integer>                     reportsToOptions;

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
        personView.setUserSelected(person.getUser() != null);
        personView.setDriverSelected(person.getDriver() != null);
        personView.setSelected(false);
        if (person.getUser() != null)
        {
            personView.getUser().setPerson(personView);
            personView.setConfirmPassword(person.getUser().getPassword());
        }

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
        person.setUserSelected(true);
        person.setDriverSelected(true);
        return person;
    }

    @Override
    public PersonView getEditItem()
    {
        final PersonView item = super.getEditItem();
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
        return item;
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
        {
            if (!person.isUserSelected())
                person.setUser(null);
            if (!person.isDriverSelected())
                person.setDriver(null);

            personDAO.update(person);
        }
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "go_adminPerson";
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

    public LinkedHashMap<String, Integer> getHeights()
    {
        return HEIGHTS;
    }

    public TreeMap<String, Integer> getWeights()
    {
        return WEIGHTS;
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

    public TreeMap<String, Integer> getReportsToOptions()
    {
        if (reportsToOptions == null)
        {
            // find all people in the same group and in all parent groups
            reportsToOptions = new TreeMap<String, Integer>();
            Integer groupID = getEditItem().getGroupID();
            while ((groupID != null) && (groupID > 0))
            {
                for (final PersonView person : items)
                    if (groupID.equals(person.getGroupID()) && !person.getPersonID().equals(getEditItem().getPersonID()))
                        reportsToOptions.put(person.getFirst() + " " + person.getLast(), person.getPersonID());
                final Group group = groupDAO.findByID(groupID);
                if (group != null)
                    groupID = group.getParentID();
            }
        }
        return reportsToOptions;
    }

    public LinkedHashMap<String, TimeZone> getTimeZones()
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

    public class PersonView extends Person implements EditItem
    {
        private Group   group;
        private Person  reportsToPerson;
        private String  confirmPassword;
        private boolean userSelected;
        private boolean driverSelected;
        private boolean selected;

        public Integer getId()
        {
            return getPersonID();
        }

        @Override
        public void setGroupID(Integer groupID)
        {
            super.setGroupID(groupID);
            group = null;
            reportsToOptions = null;
        }

        public Group getGroup()
        {
            if (group == null)
                group = groupDAO.findByID(getGroupID());
            return group;
        }

        @Override
        public void setReportsTo(Integer reportsTo)
        {
            super.setReportsTo(reportsTo);
            reportsToPerson = null;
        }

        public Person getReportsToPerson()
        {
            if (reportsToPerson == null)
                reportsToPerson = personDAO.findByID(getReportsTo());
            return reportsToPerson;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword)
        {
            this.confirmPassword = confirmPassword;
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
