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
import com.inthinc.pro.service.test.mock.GroupDaoStub;
import com.inthinc.pro.service.test.mock.PersonDaoStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;

public class DriverAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private DriverDAOAdapter driverAdapter;
    private TiwiproPrincipalStub tiwiproPrincipal;
    private PersonDaoStub personDaoStub;
    private GroupDaoStub groupDaoStub;

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
        groupDaoStub = (GroupDaoStub) BeanFactoryUtils.beanOfType(applicationContext, GroupDaoStub.class);
        personDaoStub = (PersonDaoStub) BeanFactoryUtils.beanOfType(applicationContext, PersonDaoStub.class);
        tiwiproPrincipal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipalStub.class);
    }

    @Test
    public void testJoinoints() {

        Driver driver = new Driver();
        driver.setDriverID(1);
        Group group = new Group();
        Person person = new Person();

        tiwiproPrincipal.setAccountID(10);
        group.setAccountID(10);
        person.setAcctID(10);

        personDaoStub.setExpectedPerson(person);
        groupDaoStub.setExpectedGroup(group);

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
