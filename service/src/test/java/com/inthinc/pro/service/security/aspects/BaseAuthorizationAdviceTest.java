/**
 * 
 */
package com.inthinc.pro.service.security.aspects;

import static junit.framework.Assert.assertNotNull;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.service.adapters.AccountDAOAdapter;

/**
 * @author dfreitas
 * 
 */
public class BaseAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("/spring/testAspects-security.xml");
    }

    @Test
    // TODO Finish this test when final pieces are in place.
    public void testAspectIntantiation() {
        TiwiproPrincipal principal = (TiwiproPrincipal) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        assertNotNull(principal);

        BaseAuthorizationAdvice baseAdvice = (BaseAuthorizationAdvice) BeanFactoryUtils.beanOfType(applicationContext, BaseAuthorizationAdvice.class);
        assertNotNull(baseAdvice);

        AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
        assertNotNull(adapter);
    }

    @Test
    // TODO Finish this test when final pieces are in place.
    public void testGrantsAccessToUpdate() {
        AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
        adapter.setAccountDAO(new AccountDAOStub());
        Account account = new Account();
        // TODO Change this test once principal class is ready.
        // The principal stub id is 10.
        account.setAcctID(10);

        adapter.update(account);
    }

    @Test
    // TODO Finish this test when final pieces are in place.
    public void testDeniesAccessToUpdate() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            BaseAuthorizationAdvice advice = (BaseAuthorizationAdvice) BeanFactoryUtils.beanOfType(applicationContext, BaseAuthorizationAdvice.class);
            adapter.setAccountDAO(new AccountDAOStub());
            Account account = new Account();
            // TODO Change this test once principal class is ready.
            // The principal stub id is 10.
            account.setAcctID(99);

            adapter.update(account);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal id = " + advice.getPrincipal().getAccountID() + " resource id = "
                    + account.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }
    
    @Test
    // TODO Finish this test when final pieces are in place.
    public void testGrantsAccessToCreate() {
        AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
        adapter.setAccountDAO(new AccountDAOStub());
        Account account = new Account();
        // TODO Change this test once principal class is ready.
        // The principal stub id is 10.
        account.setAcctID(10);
        
        adapter.create(account);
    }
    
    @Test
    // TODO Finish this test when final pieces are in place.
    public void testDeniesAccessToCreate() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            BaseAuthorizationAdvice advice = (BaseAuthorizationAdvice) BeanFactoryUtils.beanOfType(applicationContext, BaseAuthorizationAdvice.class);
            adapter.setAccountDAO(new AccountDAOStub());
            Account account = new Account();
            // TODO Change this test once principal class is ready.
            // The principal stub id is 10.
            account.setAcctID(99);
            
            adapter.create(account);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal id = " + advice.getPrincipal().getAccountID() + " resource id = "
                    + account.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    // TODO Finish this test when final pieces are in place.
    public void testGrantsAccessToFindById() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            adapter.setAccountDAO(new AccountDAOStub());
            Account account = adapter.findByID(25);
            
            assertNotNull(account);
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    // TODO Finish this test when final pieces are in place.
    public void testDeniesAccessToFindById() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            adapter.setAccountDAO(new AccountDAOStub());
            adapter.findByID(23);

            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ".");
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }
}
