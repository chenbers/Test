/**
 * 
 */
package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static final List<String>            AVAILABLE_COLUMNS;
    private static final int[]                   DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2 };

    private static final TreeMap<String, String> TIMEZONES;
    private static final TreeMap<String, State>  STATES;

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
        AVAILABLE_COLUMNS.add("address_street1");
        AVAILABLE_COLUMNS.add("address_street2");
        AVAILABLE_COLUMNS.add("address_city");
        AVAILABLE_COLUMNS.add("address_state");
        AVAILABLE_COLUMNS.add("address_zip");
        AVAILABLE_COLUMNS.add("driver_active");
        AVAILABLE_COLUMNS.add("driver_license");
        AVAILABLE_COLUMNS.add("driver_licenseClass");
        AVAILABLE_COLUMNS.add("driver_state");
        AVAILABLE_COLUMNS.add("driver_expiration");

        // time zones
        final String[] timezones = TimeZone.getAvailableIDs();
        TIMEZONES = new TreeMap<String, String>();
        for (int i = 0; i < timezones.length; i++)
            TIMEZONES.put(TimeZone.getTimeZone(timezones[i]).getDisplayName(), timezones[i]);

        // states
        final State[] states = State.values();
        STATES = new TreeMap<String, State>();
        for (int i = 0; i < states.length; i++)
            STATES.put(states[i].getName(), states[i]);
    }

    private PersonDAO                            personDAO;
    private GroupDAO                             groupDAO;
    private TreeMap<String, Integer>             groups;

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
        person.setAddress(new Address());
        person.getUser().setPerson(person);
        person.setDriver(new Driver());
        return person;
    }

    @Override
    protected void doDelete(List<PersonView> deleteItems)
    {
        for (final PersonView person : deleteItems)
            personDAO.deleteByID(person.getPersonID());
    }

    @Override
    protected void doSave(List<PersonView> saveItems)
    {
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

    public TreeMap<String, String> getTimeZones()
    {
        return TIMEZONES;
    }

    public TreeMap<String, State> getStates()
    {
        return STATES;
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

    public static class PersonView extends Person implements Selectable
    {
        private Group   group;
        private Person  reportsToPerson;
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

        /**
         * @return the selected
         */
        public boolean isSelected()
        {
            return selected;
        }

        /**
         * @param selected
         *            the selected to set
         */
        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
    }
}
