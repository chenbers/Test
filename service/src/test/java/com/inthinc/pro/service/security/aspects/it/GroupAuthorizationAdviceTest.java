package com.inthinc.pro.service.security.aspects.it;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.test.mock.GroupDaoStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;

/**
 * GroupAuthorizationAdvice Test.
 */
public class GroupAuthorizationAdviceTest {
    private static final String FAIL_MSG = AccessDeniedException.class +
        " exception expected: Account IDs do not match.";
    private static final Integer GROUP_ID = 1;
    private static ApplicationContext applicationContext;
    private GroupDaoStub groupDaoStub;
    private GroupDAOAdapter adapter;
    private TiwiproPrincipalStub principal;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("/spring/testAspects-security.xml");
    }

    @Before
    public void setUpTests() throws Exception {
        groupDaoStub = new GroupDaoStub();
        adapter = (GroupDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, GroupDAOAdapter.class);
        principal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        adapter.setGroupDAO(groupDaoStub);
        adapter.setGroupReportDAO(groupDaoStub);
    }

    @Test
    public void testGrantsAccessToCreate() {

        // Test against Inthinc user
        principal.setAccountID(12);
        principal.setInthincUser(true); 
        // Set account id different than the principal's.
        Group group = this.createGroup(13);

        Integer result = adapter.create(group);
        assertEquals(group.getGroupID(), result);
    }

    @Test(expected=AccessDeniedException.class)
    public void testDeniesAccessToCreate() {
        // Test against not Inthinc user
        principal.setAccountID(12);
        principal.setInthincUser(false); 
        // Set account id different than the principal's.
        Group group = this.createGroup(13);

        adapter.create(group);
        
        fail(FAIL_MSG);
    }

    @Test
    public void testGrantsAccessToDelete() {

        // Checks for same account ids.
        principal.setAccountID(10);
        principal.setInthincUser(false);

        Group group = createGroup(10);
        groupDaoStub.setExpectedGroup(group);

        adapter.delete(10);

        // Checks for different account ids but user is Inthinc.
        principal.setAccountID(11);
        principal.setInthincUser(true);

        adapter.delete(10);
    }

    @Test(expected=AccessDeniedException.class)
    public void testDeniesAccessToDelete() {

        Group group = createGroup(11);
        groupDaoStub.setExpectedGroup(group);
        principal.setAccountID(10);
        principal.setInthincUser(false); 

        adapter.delete(11);
        
        fail(FAIL_MSG);
    }

    @Test
    public void testGrantsFindById() {
        Group group = createGroup(10);
        groupDaoStub.setExpectedGroup(group);

        // Test acceptance by same account ID.
        principal.setInthincUser(false);
        principal.setAccountID(10);

        try {
            adapter.findByID(10);        
        } catch (AccessDeniedException e) {
            fail("findByID() should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by different account ID but Inthinc user.
        principal.setInthincUser(true);
        principal.setAccountID(11);

        try {
            adapter.findByID(10);        
        } catch (AccessDeniedException e) {
            fail("findByID() should not have thrown exception " + AccessDeniedException.class);
        }
    }

    @Test(expected=AccessDeniedException.class)
    public void testDeniesFindById() {
        groupDaoStub.setExpectedGroup(createGroup(10));

        principal.setInthincUser(false);
        principal.setAccountID(11);
        
        // Test rejects if different ID and not Inthinc user.
        adapter.findByID(23);
        
        fail(FAIL_MSG);
    }

    @Test
    public void testGrantsAccessToUpdate() {

        // Test against same accountId
        principal.setInthincUser(false);
        principal.setAccountID(10);

        Group group = createGroup(10);
        adapter.update(group);

        // Test against Inthinc user and different account id
        principal.setInthincUser(true); 
        group.setAccountID(13);

        adapter.update(group);
    }

    @Test(expected=AccessDeniedException.class)
    public void testDeniesAccessToUpdate() {
        Group group = createGroup(13);
        principal.setAccountID(12);
        principal.setInthincUser(false); // user is not Inthinc

        adapter.update(group);
        
        fail(FAIL_MSG);
    }
    
    @Test
    public void testGrantsAccessToGetScores() {

        groupDaoStub.setExpectedGroup(createGroup(11));
        // Test against same accountId
        principal.setInthincUser(false);
        principal.setAccountID(11);
        
        adapter.getDriverScores(GROUP_ID, Duration.THREE);
        adapter.getVehicleScores(GROUP_ID, Duration.THREE);
        adapter.getChildGroupsDriverTrends(GROUP_ID, Duration.THREE);
        adapter.getChildGroupsDriverScores(GROUP_ID, Duration.THREE);

        // Test against Inthinc user and different account id
        principal.setInthincUser(true); 
        principal.setAccountID(12);

        adapter.getDriverScores(GROUP_ID, Duration.THREE);
        adapter.getVehicleScores(GROUP_ID, Duration.THREE);
        adapter.getChildGroupsDriverTrends(GROUP_ID, Duration.THREE);
        adapter.getChildGroupsDriverScores(GROUP_ID, Duration.THREE);
    }

    @Test(expected=AccessDeniedException.class)
    public void testDeniesAccessToGetScores() {
        
        groupDaoStub.setExpectedGroup(createGroup(11));
        
        principal.setAccountID(12);
        principal.setInthincUser(false); // user is not Inthinc

        try {
            adapter.getDriverScores(GROUP_ID, Duration.THREE);
            fail(FAIL_MSG);
        } catch (AccessDeniedException e) {
            // It's OK.
        }
        
        try {
            adapter.getVehicleScores(GROUP_ID, Duration.THREE);
            fail(FAIL_MSG);
        } catch (AccessDeniedException e) {
            // It's OK.
        }
        
        try {
            adapter.getChildGroupsDriverTrends(GROUP_ID, Duration.THREE);
            fail(FAIL_MSG);
        } catch (AccessDeniedException e) {
            // It's OK.
        }
        
        adapter.getChildGroupsDriverScores(GROUP_ID, Duration.THREE);
        
        fail(FAIL_MSG);
    }
    
    /**
     * Create an instance of Group.
     * @param id the expected AccountID
     * @return new Group
     */
    private Group createGroup(Integer id) {
        Group group = new Group();
        group.setGroupID(GROUP_ID);
        group.setAccountID(id); 
        return group;
    }
}
