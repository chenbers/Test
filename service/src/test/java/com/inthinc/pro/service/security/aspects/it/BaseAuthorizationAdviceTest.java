/**
 * 
 */
package com.inthinc.pro.service.security.aspects.it;

import static junit.framework.Assert.assertNotNull;
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
import com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice;
import com.inthinc.pro.service.test.mock.AccountDAOStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;

/**
 * @author dfreitas
 * 
 */
public class BaseAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private BaseAuthorizationAdvice baseAdvice;
    private AccountDAOAdapter adapter;
    private TiwiproPrincipalStub principal;
    private AccountDAOStub accountDaoStub;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("/spring/testAspects-security.xml");
    }

    @Before
    public void setUpTests() throws Exception {
        baseAdvice = (BaseAuthorizationAdvice) BeanFactoryUtils.beanOfType(applicationContext, BaseAuthorizationAdvice.class);
        adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
        principal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        accountDaoStub = new AccountDAOStub();
        adapter.setAccountDAO(accountDaoStub);
    }

    @Test
    public void testAspectInstantiation() {
        assertNotNull(principal);
        assertNotNull(baseAdvice);
        assertNotNull(adapter);
    }

    @Test
    public void testGrantsAccessToUpdate() {
        // Test against same accountId
        principal.setAccountID(10);
        principal.setInthincUser(false);
        Account account = new Account();
        account.setAccountID(10); // Set account id the same as the principal.

        adapter.update(account);

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

    /*
     * It seems odd, but the create access check for an account has the same logic as the update.
     */
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
    public void testGrantsFindById() {
        Account account = new Account();
        accountDaoStub.setExpectedaccount(account);

        // Test acceptance by same account ID.
        try {
            account.setAccountID(10); // Make account id same as principal account id.
            principal.setAccountID(10);
            principal.setInthincUser(false);

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
        Account address = new Account();
        accountDaoStub.setExpectedaccount(address);

        // Test rejects if different ID and not Inthinc user.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            address.setAccountID(11); // Make address account id same as principal account id.

            adapter.findByID(23);
            fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Exception is expected.
        }
    }
}
