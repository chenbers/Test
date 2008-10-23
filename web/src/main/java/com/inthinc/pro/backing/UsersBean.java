/**
 * 
 */
package com.inthinc.pro.backing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlDatascroller;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.User;

/**
 * @author David Gileadi
 */
public class UsersBean extends BaseBean
{
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 3 };

    private static final Logger logger = LogManager.getLogger(UsersBean.class);

    private List<UserView> users;
    private List<UserView> filteredUsers;
    private String filterValue;
    private Map<String, String> columns;
    private HtmlDatascroller scroller;

    public UsersBean()
    {
        super();

        List<User> plainUsers = new LinkedList<User>();
        // TODO: get the users from some place real
        for (int i = 0; i < 120; i++)
            plainUsers.add(createDummyUser());

        // convert the users to UserViews
        users = new LinkedList<UserView>();
        for (final User user : plainUsers)
            try
            {
                // TODO: get the score from some place real
                users.add(createUserView(user, Math.random() * 5));
            }
            catch (Exception e)
            {
                logger.error("Error converting User to UserView", e);
            }

        // init the filtered users
        filteredUsers = new LinkedList<UserView>();
        setFilterValue(null);

        // columns, scroller
        columns = getDefaultColumns();
        scroller = new HtmlDatascroller();
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
        return createDummyString(numbers, 3) + '-' + createDummyString(numbers, 3) + '-'
                + createDummyString(numbers, 4);
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
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private UserView createUserView(User user, double score) throws IllegalAccessException, InvocationTargetException
    {
        final UserView userView = new UserView();

        BeanUtils.copyProperties(userView, user);

        userView.setScore(score);
        userView.setSelected(false);

        return userView;
    }

    /**
     * @return the users
     */
    public List<UserView> getUsers()
    {
        return users;
    }

    /**
     * @return the filteredUsers
     */
    public List<UserView> getFilteredUsers()
    {
        return filteredUsers;
    }

    /**
     * @return the number of filtered users.
     */
    public int getUserCount()
    {
        return filteredUsers.size();
    }

    /**
     * @return the filterValue
     */
    public String getFilterValue()
    {
        return filterValue;
    }

    /**
     * @param filterValue
     *            the filterValue to set
     */
    public void setFilterValue(String filterValue)
    {
        filteredUsers.clear();
        if ((filterValue != null) && (filterValue.length() > 0))
        {
            this.filterValue = filterValue.toLowerCase();
            for (final UserView user : users)
                if (user.getFirst().toLowerCase().startsWith(this.filterValue)
                        || user.getLast().toLowerCase().startsWith(this.filterValue))
                    filteredUsers.add(user);
        }
        else
        {
            this.filterValue = null;
            filteredUsers.addAll(users);
        }
    }

    public static List<String> getAvailableColumns()
    {
        final ArrayList<String> columns = new ArrayList<String>();
        columns.add("userID");
        columns.add("score");
        columns.add("fullName");
        columns.add("active");
        columns.add("username");
        columns.add("role");
        columns.add("primaryPhone");
        columns.add("secondaryPhone");
        columns.add("primarySms");
        columns.add("secondarySms");
        columns.add("email");
        columns.add("timeZone");
        columns.add("group");
        columns.add("manager");
        columns.add("title");
        columns.add("department");
        columns.add("vehicle");
        columns.add("dob");
        columns.add("address.street1");
        columns.add("address.street2");
        columns.add("address.city");
        columns.add("address.state");
        columns.add("address.zip");
        columns.add("gender");
        columns.add("height");
        columns.add("weight");
        columns.add("driversLicense.number");
        columns.add("driversLicense.class");
        columns.add("driversLicense.expiration");
        columns.add("certifications");
        columns.add("dot");
        columns.add("citations");
        columns.add("points");
        return columns;
    }

    public static Map<String, String> getDefaultColumns()
    {
        final HashMap<String, String> columns = new HashMap<String, String>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), availableColumns.get(i));
        return columns;
    }

    /**
     * @return the columns
     */
    public Map<String, String> getColumns()
    {
        return columns;
    }

    /**
     * @param columns
     *            the columns to set
     */
    public void setColumns(Map<String, String> columns)
    {
        this.columns = columns;
    }

    /**
     * @return the scroller
     */
    public HtmlDatascroller getScroller()
    {
        return scroller;
    }

    /**
     * @param scroller
     *            the scroller to set
     */
    public void setScroller(HtmlDatascroller scroller)
    {
        this.scroller = scroller;
    }

    public class UserView extends User
    {
        private double score;
        private String scoreStyle;
        private boolean selected;

        /**
         * @return the score
         */
        public double getScore()
        {
            return score;
        }

        /**
         * @param score
         *            the score to set
         */
        public void setScore(double score)
        {
            this.score = score;
            this.scoreStyle = new ScoreBox(score, ScoreBoxSizes.SMALL).getScoreStyle();
        }

        /**
         * @return the scoreStyle
         */
        public String getScoreStyle()
        {
            return scoreStyle;
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
