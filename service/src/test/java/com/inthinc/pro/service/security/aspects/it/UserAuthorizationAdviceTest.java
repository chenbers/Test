package com.inthinc.pro.service.security.aspects.it;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.adapters.UserDAOAdapter;
import com.inthinc.pro.service.test.mock.GroupDaoStub;
import com.inthinc.pro.service.test.mock.PersonDaoStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;
import com.inthinc.pro.service.test.mock.UserDaoStub;

public class UserAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private UserDAOAdapter userAdapter;
    private TiwiproPrincipalStub tiwiproPrincipal;
    private GroupDaoStub groupDaoStub;
    private PersonDaoStub personDaoStub;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext(new String[] {"/spring/testAspects-security.xml"});
    }

    @Before
    public void setUpTests() throws Exception {
        userAdapter = (UserDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, UserDAOAdapter.class);
        groupDaoStub = (GroupDaoStub) BeanFactoryUtils.beanOfType(applicationContext, GroupDaoStub.class);
        personDaoStub = (PersonDaoStub) BeanFactoryUtils.beanOfType(applicationContext, PersonDaoStub.class);
        tiwiproPrincipal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipalStub.class);
    }

    @Test
    public void testJoinoints() {
        userAdapter.setUserDAO(new UserDaoStub());

        User user = new User();
        Group group = new Group();
        Person person = new Person();

        tiwiproPrincipal.setAccountID(10);
        group.setAccountID(10);
        person.setAcctID(10);

        personDaoStub.setExpectedPerson(person);
        groupDaoStub.setExpectedGroup(group);

        // Manual test of the aspects. These calls should all route through the DriverAuthorizationAdvice.
        userAdapter.create(user);
        userAdapter.delete(1);
        userAdapter.findByID(1);
        userAdapter.findByUserName("");
        userAdapter.login("", "");
        userAdapter.update(user);
    }
}
