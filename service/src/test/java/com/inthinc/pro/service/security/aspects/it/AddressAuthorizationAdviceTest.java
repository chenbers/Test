/**
 * 
 */
package com.inthinc.pro.service.security.aspects.it;

import static junit.framework.Assert.fail;
import junit.framework.Assert;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.service.adapters.AddressDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.test.mock.AddressDAOStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;

/**
 * @author dfreitas
 * 
 */
public class AddressAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private AddressDAOStub addressDaoStub;
    private AddressDAOAdapter adapter;
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
        addressDaoStub = new AddressDAOStub();
        adapter = (AddressDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, AddressDAOAdapter.class);
        principal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        adapter.setAddressDAO(addressDaoStub);
    }

    @Test
    public void testGrantsAccessGetAll() {
        try {
            adapter.getAll();
            fail("Test should have thrown exception " + NotImplementedException.class);
        } catch (NotImplementedException e) {
            // Ok. Method is not yet implemented and no authorization rules defined.
        }
    }

    @Test
    public void testDeniesAccessGetAll() {
        try {
            adapter.getAll();
            fail("Test should have thrown exception " + NotImplementedException.class);
        } catch (NotImplementedException e) {
            // Ok. Method is not yet implemented and no authorization rules defined.
        }
    }

    @Test
    public void testGrantsFindById() {
        Address address = new Address();
        addressDaoStub.setExpectedAddress(address);

        // Test acceptance by same account ID.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            address.setAccountID(10); // Make address account id same as principal account id.

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by Inthinc user.
        try {
            principal.setAccountID(99);
            address.setAccountID(88); // Make address account id different than principal account id.
            principal.setInthincUser(true); // But flags principal as Inthinc user

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }
    }

    @Test
    public void testDeniesFindById() {
        Address address = new Address();
        addressDaoStub.setExpectedAddress(address);

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

    @Test
    public void testGrantsAccessToCreate() {

        // Test against same accountId
        principal.setAccountID(10);
        principal.setInthincUser(false);
        Address address = new Address();
        address.setAccountID(10); // Set account id the same as the principal.

        adapter.create(address);

        // Test against Inthinc user
        principal.setAccountID(12);
        address.setAccountID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.create(address);
    }

    @Test
    public void testDeniesAccessToCreate() {
        try {
            principal.setAccountID(12);

            Address address = new Address();
            address.setAccountID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.create(address);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + address.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsAccessToUpdate() {

        // Test against same accountId
        principal.setAccountID(10);
        principal.setInthincUser(false);
        Address address = new Address();
        address.setAccountID(10); // Set account id the same as the principal.

        adapter.create(address);

        // Test against Inthinc user
        principal.setAccountID(12);
        address.setAccountID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.update(address);
    }

    @Test
    public void testDeniesAccessToUpdate() {
        try {
            principal.setAccountID(12);

            Address address = new Address();
            address.setAccountID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.update(address);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + address.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsAccessToDelete() {
        
        // Test against same accountId
        principal.setAccountID(10);
        principal.setInthincUser(false);
        Address address = new Address();
        addressDaoStub.setExpectedAddress(address);
        address.setAccountID(10); // Set account id the same as the principal.
        
        adapter.delete(11);
        
        // Test against Inthinc user
        principal.setAccountID(12);
        address.setAccountID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc
        
        adapter.delete(11);
    }
    
    @Test
    public void testDeniesAccessToDelete() {
        try {
            principal.setAccountID(12);
            
            Address address = new Address();
            addressDaoStub.setExpectedAddress(address);
            
            address.setAccountID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc
            
            adapter.delete(11);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + address.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }
}
