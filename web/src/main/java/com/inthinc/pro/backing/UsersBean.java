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

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.User;

/**
 * @author David Gileadi
 */
public class UsersBean extends BaseAdminBean<UsersBean.UserView>
{
    private static final Logger       logger                 = LogManager.getLogger(UsersBean.class);

    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[]        DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 3 };

    static
    {
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("userID");
        AVAILABLE_COLUMNS.add("score");
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
    }

    public UsersBean()
    {
        super();

        List<User> plainUsers = new LinkedList<User>();
        // TODO: get the users from some place real
        for (int i = 0; i < 120; i++)
            plainUsers.add(createDummyUser());

        // convert the users to UserViews
        items = new LinkedList<UserView>();
        for (final User user : plainUsers)
            try
            {
                // TODO: get the score from some place real
                items.add(createUserView(user, (int)(Math.random() * 50)));
            }
            catch (Exception e)
            {
                logger.error("Error converting User to UserView", e);
            }

        // init the filtered users
        filteredItems.addAll(items);
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
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private UserView createUserView(User user, Integer score) throws IllegalAccessException, InvocationTargetException
    {
        final UserView userView = new UserView();

        BeanUtils.copyProperties(userView, user);

        userView.setScore(score);
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
    public String edit()
    {
        return "go_adminUserEdit";
    }

    @Override
    public String delete()
    {
        return "go_adminUsersDelete";
    }

    public static class UserView extends User implements Selectable
    {
        private Integer  score;
        private String  scoreStyle;
        private boolean selected;

        /**
         * @return the score
         */
        public Integer getScore()
        {
            return score;
        }

        /**
         * @param score
         *            the score to set
         */
        public void setScore(Integer score)
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
