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

import com.inthinc.pro.model.Address;
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
        Address address = new Address();

        person.setAcctID(10); // Set account id the same as the principal.
        principal.setAccountID(10);
        address.setAccountID(10);

        person.setAddress(address);

        adapter.create(person);

        // Test against Inthinc user
        address = new Address();
        principal.setAccountID(12);
        person.setAcctID(13); // Set account id different than the principal's.
        address.setAccountID(10);
        person.setAddress(address);

        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.create(person);

    }

    @Test
    public void testDeniesAccessToCreate() {
        try {
            // First batch of tests. Address is null.
            // -------------------------------------------------------------------------------------------------------------

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

        try {
            // If address is present, need to check access as well for address.
            // -------------------------------------------------------------------------------------------------------------

            Person person = new Person();
            principal.setInthincUser(false); // And user is not Inthinc

            Address address = new Address();
            principal.setAccountID(12);
            person.setAcctID(12); // Set account id to be the same.
            address.setAccountID(13); // But address has different account id.
            person.setAddress(address);

            adapter.create(person);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + person.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsAccessToDelete() {

        // First batch of tests. Address is null.
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

        // If address is present, need to check access as well for address.
        // -------------------------------------------------------------------------------------------------------------
        // Checks for same account ids.
        principal.setInthincUser(false);
        person = new Person();
        personDaoStub.setExpectedPerson(person);

        Address address = new Address();

        principal.setAccountID(10);
        person.setAcctID(10); // Set account id to be different than the principal.
        address.setAccountID(10);
        person.setAddress(address);

        adapter.delete(11);

        // Checks for different account ids.
        person = new Person();
        personDaoStub.setExpectedPerson(person);
        principal.setInthincUser(true); // But user is Inthinc.

        address = new Address();
        principal.setAccountID(11);
        person.setAcctID(12); // Set account id to be different than the principal.
        address.setAccountID(10);
        person.setAddress(address);

        adapter.delete(11);
    }

    @Test
    public void testDeniesAccessToDelete() {

        // First batch of tests. Address is null.
        // -------------------------------------------------------------------------------------------------------------
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

        // If address is present, need to check access as well for address.
        // -------------------------------------------------------------------------------------------------------------
        person = new Person();
        personDaoStub.setExpectedPerson(person);
        principal.setInthincUser(false); // And user is not Inthinc

        Address address = new Address();
        principal.setAccountID(10);
        person.setAcctID(10); // Set account id to be the same as the principal.
        address.setAccountID(11); // But address account id is different.
        person.setAddress(address);

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
        Address address = new Address();

        // Test acceptance by same account ID.
        try {
            principal.setInthincUser(false);
            person.setAddress(address);

            principal.setAccountID(10);
            address.setAccountID(10);
            person.setAcctID(10); // Make address account id same as principal account id.

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by Inthinc user.
        try {
            principal.setAccountID(99);
            person.setAcctID(88); // Make address account id different than principal account id.
            address.setAccountID(77);
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
        Address address = new Address();
        person.setAddress(address);

        // Test rejects if different ID and not Inthinc user.
        try {
            principal.setInthincUser(false);
            principal.setAccountID(10);
            person.setAcctID(10);
            address.setAccountID(11);// Make address account id diff from principal account id.

            adapter.findByID(23);
            fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Exception is expected.
        }
    }

    @Test
    public void testGrantsAccessToUpdate() {

        // If address is present, need to check access as well for address.
        // -------------------------------------------------------------------------------------------------------------
        // Test against same accountId
        principal.setInthincUser(false);

        Person person = new Person();
        Address address = new Address();

        person.setAcctID(10); // Set account id the same as the principal.
        principal.setAccountID(10);
        address.setAccountID(10);

        person.setAddress(address);

        adapter.update(person);

        // Test against Inthinc user
        address = new Address();
        principal.setAccountID(12);
        person.setAcctID(13); // Set account id different than the principal's.
        address.setAccountID(10);
        person.setAddress(address);

        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.update(person);

    }

    @Test
    public void testDeniesAccessToUpdate() {
        try {
            // First batch of tests. Address is null.
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

        try {
            // If address is present, need to check access as well for address.
            // -------------------------------------------------------------------------------------------------------------

            Person person = new Person();
            principal.setInthincUser(false); // And user is not Inthinc

            Address address = new Address();
            principal.setAccountID(12);
            person.setAcctID(12); // Set account id to be the same.
            address.setAccountID(13); // But address has different account id.
            person.setAddress(address);

            adapter.create(person);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + person.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }
}
