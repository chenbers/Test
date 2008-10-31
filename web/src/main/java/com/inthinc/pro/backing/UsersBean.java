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
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.User;

/**
 * @author David Gileadi
 */
public class UsersBean extends BaseAdminBean<UsersBean.UserView>
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
        AVAILABLE_COLUMNS.add("primaryPhone");
        AVAILABLE_COLUMNS.add("secondaryPhone");
        AVAILABLE_COLUMNS.add("primarySms");
        AVAILABLE_COLUMNS.add("secondarySms");
        AVAILABLE_COLUMNS.add("email");
        AVAILABLE_COLUMNS.add("timeZone");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("manager");
        AVAILABLE_COLUMNS.add("title");
        AVAILABLE_COLUMNS.add("department");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("dob");
        AVAILABLE_COLUMNS.add("address.street1");
        AVAILABLE_COLUMNS.add("address.street2");
        AVAILABLE_COLUMNS.add("address.city");
        AVAILABLE_COLUMNS.add("address.state");
        AVAILABLE_COLUMNS.add("address.zip");
        AVAILABLE_COLUMNS.add("gender");
        AVAILABLE_COLUMNS.add("height");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("driversLicense.number");
        AVAILABLE_COLUMNS.add("driversLicense.class");
        AVAILABLE_COLUMNS.add("driversLicense.expiration");
        AVAILABLE_COLUMNS.add("certifications");
        AVAILABLE_COLUMNS.add("dot");
        AVAILABLE_COLUMNS.add("citations");
        AVAILABLE_COLUMNS.add("points");

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

    private UserDAO                              userDAO;
    private GroupDAO                             groupDAO;
    private TreeMap<String, Integer>             groups;

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    @Override
    protected List<UserView> loadItems()
    {
        // get the users
        List<User> plainUsers = new LinkedList<User>();
        for (int i = 0; i < 120; i++)
            plainUsers.add(createDummyUser());
        // TODO: use the commented line below instead
        // final List<Vehicle> plainUsers = userDAO.getUsersInGroupHierarchy(getUser().getGroupID());

        // convert the users to UserViews
        final LinkedList<UserView> items = new LinkedList<UserView>();
        for (final User user : plainUsers)
            items.add(createUserView(user));

        return items;
    }

    @Deprecated
    private User createDummyUser()
    {
        final User user = new User();
        user.setUserID((int) (Math.random() * Integer.MAX_VALUE));
        final Role[] roles = Role.values();
        user.setRole(roles[randomInt(roles.length)]);
        user.setActive(Math.random() < .75);
        user.setFirst(createDummyName());
        user.setLast(createDummyName());
        user.setUsername((user.getFirst().charAt(0) + user.getLast()).toLowerCase());
        user.setPrimaryPhone(createDummyPhone());
        user.setSecondaryPhone(createDummyPhone());
        user.setPrimarySms(createDummyPhone());
        user.setSecondarySms(createDummyPhone());
        user.setEmail(user.getFirst() + '.' + user.getLast() + "@nowhere.com");
        final String[] timeZones = TimeZone.getAvailableIDs();
        user.setTimeZone(timeZones[randomInt(timeZones.length)]);
        return user;
    }

    @Deprecated
    private String createDummyName()
    {
        return createDummyString("abcdefghijklmnoprstuvwyz", randomInt(9) + 2);
    }

    @Deprecated
    private String createDummyPhone()
    {
        final String numbers = "1234567890";
        return createDummyString(numbers, 3) + '-' + createDummyString(numbers, 3) + '-' + createDummyString(numbers, 4);
    }

    @Deprecated
    private String createDummyString(final String letters, final int length)
    {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            final char letter = letters.charAt(randomInt(letters.length()));
            if (i == 0)
                sb.append(String.valueOf(letter).toUpperCase());
            else
                sb.append(letter);
        }
        return sb.toString();
    }

    @Deprecated
    private int randomInt(int limit)
    {
        return (int) (Math.random() * limit);
    }

    /**
     * Creates a UserView object from the given User object and score.
     * 
     * @param user
     *            The user.
     * @param score
     *            The user's overall score.
     * @return The new UserView object.
     */
    private UserView createUserView(User user)
    {
        final UserView userView = new UserView();
        BeanUtils.copyProperties(user, userView);

        userView.setGroup(groupDAO.findByID(user.getGroupID()));
        userView.setSelected(false);

        return userView;
    }

    @Override
    protected boolean matchesFilter(UserView user, String filterWord)
    {
        return user.getFirst().toLowerCase().startsWith(filterWord) || user.getLast().toLowerCase().startsWith(filterWord)
                || String.valueOf(user.getUserID()).startsWith(filterWord);
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
    protected UserView createAddItem()
    {
        // TODO: if User has child objects, create them too
        return createUserView(new User());
    }

    @Override
    protected void doDelete(List<UserView> deleteItems)
    {
        // TODO delete the items
    }

    @Override
    protected void doSave(List<UserView> saveItems)
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

    public static class UserView extends User implements Selectable
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
