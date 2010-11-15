/**
 * 
 */
package com.inthinc.pro.service.security.aspects;

import static junit.framework.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.service.adapters.AccountDAOAdapter;

/**
 * @author dfreitas
 * 
 */
public class AccountAuthorizationAdviceTest {

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
    public void testGrantsAccessDelete() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            TiwiproPrincipal principal = (TiwiproPrincipal) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
            principal.setInthincUser(true);
            adapter.setAccountDAO(new AccountDAOStub());
            adapter.delete(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown AccessDeniedException");
        }
    }

    @Test
    // TODO Finish this test when final pieces are in place.
    public void testDeniesAccessDelete() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            TiwiproPrincipal principal = (TiwiproPrincipal) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
            principal.setInthincUser(false);
            adapter.setAccountDAO(new AccountDAOStub());
            adapter.delete(23);
            fail("Expected exception " + AccessDeniedException.class + " not thrown.");
        } catch (AccessDeniedException e) {
            // Ok. Expected Exception was thrown.
        }
    }
    @Test
    // TODO Finish this test when final pieces are in place.
    public void testGrantsAccessGetAll() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            TiwiproPrincipal principal = (TiwiproPrincipal) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
            principal.setInthincUser(true);
            adapter.setAccountDAO(new AccountDAOStub());
            adapter.getAll();
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown AccessDeniedException");
        }
    }
    
    @Test
    // TODO Finish this test when final pieces are in place.
    public void testDeniesAccessGetAll() {
        try {
            AccountDAOAdapter adapter = (AccountDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AccountDAOAdapter.class);
            TiwiproPrincipal principal = (TiwiproPrincipal) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
            principal.setInthincUser(false);
            adapter.setAccountDAO(new AccountDAOStub());
            adapter.getAll();
            fail("Expected exception " + AccessDeniedException.class + " not thrown.");
        } catch (AccessDeniedException e) {
            // Ok. Expected Exception was thrown.
        }
    }
}
