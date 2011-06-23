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

import com.inthinc.pro.model.Account;
import com.inthinc.pro.service.adapters.AccountDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.test.mock.AccountDAOStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;

/**
 * @author dfreitas
 * 
 */
public class AccountAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private AccountDAOStub accountDaoStub;
    private AccountDAOAdapter adapter;
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
        accountDaoStub = new AccountDAOStub();
        adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
        principal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        adapter.setAccountDAO(accountDaoStub);
    }

    @Test
    public void testGrantsAccessGetAll() {
        // Only Inthinc users are allowed to access all accounts.
        principal.setInthincUser(true);
        adapter.getAll();
    }

    @Test
    public void testDeniesAccessGetAll() {
        try {
            principal.setInthincUser(false);
            adapter.getAll();
            fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Expected exception thrown.
        }
    }

    @Test
    public void testGrantsFindById() {
        Account account = new Account();
        accountDaoStub.setExpectedaccount(account);

        // Test acceptance by same account ID.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            account.setAccountID(10); // Make address account id same as principal account id.

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by Inthinc user.
        try {
            principal.setAccountID(99);
            account.setAccountID(88); // Make address account id different than principal account id.
            principal.setInthincUser(true); // But flags principal as Inthinc user

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }
    }

    @Test
    public void testDeniesFindById() {
        Account account = new Account();
        accountDaoStub.setExpectedaccount(account);

        // Test rejects if different ID and not Inthinc user.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            account.setAccountID(11); // Make address account id same as principal account id.

            adapter.findByID(23);
            fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Exception is expected.
        }
    }

    @Test
    public void testGrantsAccessToCreate() {

        // Test against same accountId
        principal.setAccountID(10);
        principal.setInthincUser(false);
        Account account = new Account();
        account.setAccountID(10); // Set account id the same as the principal.

        adapter.create(account);

        // Test against Inthinc user
        principal.setAccountID(12);
        account.setAccountID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.create(account);
    }

    @Test
    public void testDeniesAccessToCreate() {
        try {
            principal.setAccountID(12);

            Account account = new Account();
            account.setAccountID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.create(account);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + account.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsAccessToUpdate() {

        // Test against same accountId
        principal.setAccountID(10);
        principal.setInthincUser(false);
        Account account = new Account();
        account.setAccountID(10); // Set account id the same as the principal.

        adapter.create(account);

        // Test against Inthinc user
        principal.setAccountID(12);
        account.setAccountID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.update(account);
    }

    @Test
    public void testDeniesAccessToUpdate() {
        try {
            principal.setAccountID(12);

            Account account = new Account();
            account.setAccountID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.update(account);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + account.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsAccessToDelete() {

        // Only inthinc users can delete accounts.
        principal.setAccountID(10);
        principal.setInthincUser(true);
        Account address = new Account();
        accountDaoStub.setExpectedaccount(address);
        address.setAccountID(11); // Set account id to be different than the principal.

        adapter.delete(11);
    }

    @Test
    public void testDeniesAccessToDelete() {
        // Test with different IDs and not Inthinc user.
        try {
            principal.setAccountID(12);

            Account address = new Account();
            accountDaoStub.setExpectedaccount(address);

            address.setAccountID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.delete(11);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + address.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }

        // Test with ***same*** IDs and not Inthinc user to make sure only the Inthinc condition is taken into account.
        try {
            principal.setAccountID(10);

            Account address = new Account();
            accountDaoStub.setExpectedaccount(address);

            address.setAccountID(10); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.delete(11);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + address.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }
}
