package com.inthinc.pro.backing;

import com.inthinc.pro.backing.RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView;
import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.util.MiscUtil;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.TestingAuthenticationToken;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.replay;

public class RedFlagOrZoneAlertsBeanTest extends BaseAdminBeanTest<RedFlagOrZoneAlertView> {

    @BeforeClass
    public static void runOnceBeforeAllTests() {
        // We need to mock a login user or getting the bean will fail.
        mockLoginUser(true);
    }

    @Override
    protected RedFlagOrZoneAlertsBean getAdminBean() {
        return (RedFlagOrZoneAlertsBean) applicationContext.getBean("redFlagOrZoneAlertsBean");
    }

    @Override
    protected String getFilterValue() {
        return "";
    }

    @Override
    protected void populate(RedFlagOrZoneAlertView editItem, BaseAdminBean<RedFlagOrZoneAlertView> adminBean) {
        editItem.setCreated(new Date());
        editItem.setName("RedFlagZoneAlert");
        editItem.setDescription("New alert");
        final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>(7);
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(new Boolean(MiscUtil.randomInt(0, 1) == 1));
        editItem.setDayOfWeek(dayOfWeek);
        final List<SelectItem> pickedGroups = new ArrayList<SelectItem>();
        pickedGroups.add(new SelectItem("group101"));
        ((RedFlagOrZoneAlertsBean) adminBean).getAssignPicker().setPicked(pickedGroups);
    }

    @Override
    protected String[] getBatchUpdateFields() {
        return new String[]{"name", "description", "assignTo"};
    }

    /**
     * Mocks login user so beans that rely on it don't fail to initialise.
     *
     * @param isSuperuser if the user is admin (superuser)
     */
    protected static void mockLoginUser(Boolean isSuperuser) {
        User user = new User(1, 1, null, Status.ACTIVE, "test", "testpassword", 1);
        user.setPerson(new Person(1, 1, TimeZone.getDefault(), 0, "pri@example.com", null, null, null,
                null, null, 0, 0, 0, null, null, null, null, "first", "m",
                "last", null, Gender.FEMALE, null, null, null, Status.ACTIVE, MeasurementType.ENGLISH,
                FuelEfficiencyType.MPG_US, Locale.US));
        ProUser proUser = new ProUser(user, true, true, new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMIN")});
        TestingAuthenticationToken testToken = new TestingAuthenticationToken(
                proUser, proUser.getPassword(),
                proUser.getAuthorities());

        SecurityContext securityContext = new SecurityContextImpl();
        testToken.setAuthenticated(true);
        securityContext.setAuthentication(testToken);
        SecurityContextHolder.setContext(securityContext);

        MockData mockData = MockData.getInstance();
        mockData.storeObject(user);
    }

    /**
     * Tests users on the same level sharing visibility of flags.
     */
    @Test
    public void testMultipleUsersSiblingFlagsImpact() {
        final int NUM_USERS_IN_GROUP = 10;

        // Create a red flags bean
        RedFlagOrZoneAlertsBean redFlagsBean = getAdminBean();

        /**
         * Mock data
         */
        RedFlagAlertDAO mockRedFlagDao = EasyMock.createMock(RedFlagAlertDAO.class);
        UserDAO mockUserDao = EasyMock.createMock(UserDAO.class);
        List<RedFlagAlert> mockRedFlagsList = new ArrayList<RedFlagAlert>();
        mockRedFlagsList.add(new RedFlagAlert());
        List<User> mockUserList = new ArrayList<User>();
        for (int i = 2; i <= NUM_USERS_IN_GROUP + 1; i++) {
            User user = new User();
            user.setUserID(i);
            mockUserList.add(user);
        }

        expect(mockRedFlagDao.getRedFlagAlertsByUserID(EasyMock.isA(Integer.class))).andReturn(mockRedFlagsList).anyTimes();
        replay(mockRedFlagDao);

        expect(mockUserDao.getUsersByGroupId(EasyMock.isA(Integer.class))).andReturn(mockUserList).anyTimes();
        replay(mockUserDao);

        // exercise the code
        redFlagsBean.setUserDAO(mockUserDao);
        redFlagsBean.setRedFlagAlertsDAO(mockRedFlagDao);

        int numFound = redFlagsBean.loadItems().size();
        assert (numFound == NUM_USERS_IN_GROUP + 1);
    }
}
