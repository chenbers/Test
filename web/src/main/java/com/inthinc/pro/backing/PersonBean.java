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
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;

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
        AVAILABLE_COLUMNS.add("userID");
        AVAILABLE_COLUMNS.add("fullName");
        AVAILABLE_COLUMNS.add("active");
        AVAILABLE_COLUMNS.add("username");
        AVAILABLE_COLUMNS.add("role");
        AVAILABLE_COLUMNS.add("workPhone");
        AVAILABLE_COLUMNS.add("homePhone");
        AVAILABLE_COLUMNS.add("email");
        AVAILABLE_COLUMNS.add("timeZone");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("manager");
        AVAILABLE_COLUMNS.add("title");
        AVAILABLE_COLUMNS.add("department");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("dob");
        AVAILABLE_COLUMNS.add("gender");
        AVAILABLE_COLUMNS.add("height");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("address.street1");
        AVAILABLE_COLUMNS.add("address.street2");
        AVAILABLE_COLUMNS.add("address.city");
        AVAILABLE_COLUMNS.add("address.state");
        AVAILABLE_COLUMNS.add("address.zip");
        AVAILABLE_COLUMNS.add("driver.license");
        AVAILABLE_COLUMNS.add("driver.licenseClass");
        AVAILABLE_COLUMNS.add("driver.state");
        AVAILABLE_COLUMNS.add("driver.expiration");

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
        personView.setSelected(false);

        return personView;
    }

    @Override
    protected boolean matchesFilter(PersonView person, String filterWord)
    {
        // TODO: match by visible columns instead
        return person.getFirst().toLowerCase().startsWith(filterWord) || person.getLast().toLowerCase().startsWith(filterWord)
                || String.valueOf(person.getUser().getUserID()).startsWith(filterWord);
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
        // TODO: if Person has child objects, create them too
        return createPersonView(new Person());
    }

    @Override
    protected void doDelete(List<PersonView> deleteItems)
    {
        // TODO delete the items
    }

    @Override
    protected void doSave(List<PersonView> saveItems)
    {
        // TODO save the items
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
        private boolean selected;

        public Group getGroup()
        {
            return group;
        }

        public void setGroup(Group group)
        {
            this.group = group;
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
