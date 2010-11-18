/**
 * 
 */
package com.inthinc.pro.service.security.aspects.it;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.security.stubs.DriverDaoStub;
import com.inthinc.pro.service.security.stubs.DriverReportDaoStub;
import com.inthinc.pro.service.security.stubs.EventDaoStub;
import com.inthinc.pro.service.security.stubs.GroupDaoStub;
import com.inthinc.pro.service.security.stubs.PersonDaoStub;
import com.inthinc.pro.service.security.stubs.TiwiproPrincipalStub;

/**
 * @author dfreitas
 * 
 */
public class DriverAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private DriverDAOAdapter driverAdapter;
    private GroupDAOAdapter groupAdapter;
    private PersonDAOAdapter personAdapter;
    private TiwiproPrincipalStub tiwiproPrincipal;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("/spring/testAspects-security.xml");
    }

    @Before
    public void setUpTests() throws Exception {
        driverAdapter = (DriverDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, DriverDAOAdapter.class);
        groupAdapter = (GroupDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, GroupDAOAdapter.class);
        personAdapter = (PersonDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, PersonDAOAdapter.class);
        tiwiproPrincipal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipalStub.class);
    }

    @Test
    public void testJoinoints() {
        driverAdapter.setDriverDAO(new DriverDaoStub());
        driverAdapter.setDriverReportDAO(new DriverReportDaoStub());
        EventDaoStub eventDAO = new EventDaoStub();
        driverAdapter.setEventDAO(eventDAO);
        PersonDaoStub personDAO = new PersonDaoStub();
        personAdapter.setPersonDAO(personDAO);
        GroupDaoStub groupDAO = new GroupDaoStub();
        groupAdapter.setGroupDAO(groupDAO);

        Driver driver = new Driver();
        Group group = new Group();
        Person person = new Person();

        tiwiproPrincipal.setAccountID(10);
        group.setAccountID(10);
        person.setAcctID(10);

        personDAO.setExpectedPerson(person);
        groupDAO.setExpectedGroup(group);

        // Manual test of the aspects. These calls should all route through the DriverAuthorizationAdvice.
        driverAdapter.create(driver);
        driverAdapter.delete(1);
        driverAdapter.findByID(1);
        driverAdapter.getScore(1, null);
        driverAdapter.getSpeedingEvents(1);
        driverAdapter.getTrend(1, null);
        driverAdapter.update(driver);
    }
}
