/**
 * 
 */
package com.inthinc.pro.service.security.aspects.it;

import static junit.framework.Assert.fail;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.test.mock.PersonDaoStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;

/**
 * @author dfreitas
 * 
 */

public class PersonAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private PersonDaoStub personDaoStub;
    private PersonDAOAdapter adapter;
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
        personDaoStub = new PersonDaoStub();
        adapter = (PersonDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, PersonDAOAdapter.class);
        principal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        adapter.setPersonDAO(personDaoStub);
    }

    @Test
    public void testGrantsAccessToCreateWithAccountId() {

        // Test against Inthinc user
        Person person = new Person();
        principal.setAccountID(12);
        person.setAcctID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.create(1, person);
    }

    @Test
    public void testDeniesAccessToCreateWithAccountId() {
        // Test against different IDs.
        try {

            Person person = new Person();
            principal.setAccountID(12);
            person.setAcctID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.create(1, person);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + person.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }

        // Test against same ID.
        try {

            Person person = new Person();
            principal.setAccountID(12);
            person.setAcctID(12); // Set account id to be the same.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.create(1, person);
            // To create a person into another account, a Inthinc user is required, even if the person has the same account id as the user.
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + person.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsAccessToCreate() {

        // First batch of tests. Address is null.
        // -------------------------------------------------------------------------------------------------------------

        // Test against same accountId
        principal.setInthincUser(false);
        Person person = new Person();
        principal.setAccountID(10);
        person.setAcctID(10); // Set account id the same as the principal.

        adapter.create(person);

        // Test against Inthinc user
        principal.setAccountID(12);
        person.setAcctID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.create(person);

        // If address is present, need to check access as well for address.
        // -------------------------------------------------------------------------------------------------------------
        // Test against same accountId
        principal.setInthincUser(false);

        person = new Person();

        person.setAcctID(10); // Set account id the same as the principal.
        principal.setAccountID(10);

        adapter.create(person);

        // Test against Inthinc user
        principal.setAccountID(12);
        person.setAcctID(13); // Set account id different than the principal's.

        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.create(person);

    }

    @Test
    public void testDeniesAccessToCreate() {
         
        try {

            Person person = new Person();
            principal.setAccountID(12);
            person.setAcctID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.create(person);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + person.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }

    }

    @Test
    public void testGrantsAccessToDelete() {

        // -------------------------------------------------------------------------------------------------------------
        // Checks for same account ids.
        principal.setInthincUser(false);
        Person person = new Person();
        personDaoStub.setExpectedPerson(person);

        principal.setAccountID(10);
        person.setAcctID(10); // Set account id to be different than the principal.

        adapter.delete(11);

        // Checks for different account ids.
        person = new Person();
        personDaoStub.setExpectedPerson(person);

        principal.setAccountID(11);
        person.setAcctID(10); // Set account id to be different than the principal.

        principal.setInthincUser(true); // But user is Inthinc.

        adapter.delete(11);

        // -------------------------------------------------------------------------------------------------------------
        // Checks for same account ids.
        principal.setInthincUser(false);
        person = new Person();
        personDaoStub.setExpectedPerson(person);

        principal.setAccountID(10);
        person.setAcctID(10); // Set account id to be different than the principal.

        adapter.delete(11);

        // Checks for different account ids.
        person = new Person();
        personDaoStub.setExpectedPerson(person);
        principal.setInthincUser(true); // But user is Inthinc.

        principal.setAccountID(11);
        person.setAcctID(12); // Set account id to be different than the principal.

        adapter.delete(11);
    }

    @Test
    public void testDeniesAccessToDelete() {


        Person person = new Person();
        personDaoStub.setExpectedPerson(person);
        principal.setAccountID(10);
        person.setAcctID(11); // Set account id to be different than the principal.
        principal.setInthincUser(false); // And user is not Inthinc

        try {
            adapter.delete(11);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + person.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok. Expectd.
        }


    }

    @Test
    public void testGrantsFindById() {
        Person person = new Person();
        personDaoStub.setExpectedPerson(person);

        // Test acceptance by same account ID.
        try {
            principal.setInthincUser(false);

            principal.setAccountID(10);
            person.setAcctID(10); // Make address account id same as principal account id.

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by Inthinc user.
        try {
            principal.setAccountID(99);
            person.setAcctID(88); // Make address account id different than principal account id.
            principal.setInthincUser(true); // But flags principal as Inthinc user

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }
    }

    @Test
    public void testDeniesFindById() {
        Person person = new Person();
        personDaoStub.setExpectedPerson(person);

        // Test rejects if different ID and not Inthinc user.
        try {
            principal.setInthincUser(false);
            principal.setAccountID(10);
            person.setAcctID(11);

            adapter.findByID(23);
            fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Exception is expected.
        }
    }

    @Test
    public void testGrantsAccessToUpdate() {

        // Test against same accountId
        principal.setInthincUser(false);

        Person person = new Person();

        person.setAcctID(10); // Set account id the same as the principal.
        principal.setAccountID(10);

        adapter.update(person);

        // Test against Inthinc user
        principal.setAccountID(12);
        person.setAcctID(13); // Set account id different than the principal's.

        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.update(person);

    }

    @Test
    public void testDeniesAccessToUpdate() {
        try {
            // -------------------------------------------------------------------------------------------------------------

            Person person = new Person();
            principal.setAccountID(12);
            person.setAcctID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.update(person);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + person.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }
}
