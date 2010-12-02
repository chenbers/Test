package com.inthinc.pro.service.security;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.TestingAuthenticationToken;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.service.impl.BaseUnitTest;

/**
 * Test for TiwiproPrincipal.
 */
public class TiwiproPrincipalTest extends BaseUnitTest {

    private static final Integer USER_ID = 1;
    private static final Integer GROUP_ID = 2;
    private static final Integer ACCOUNT_ID = 3;
    
    private TiwiproPrincipal principalSUT = new TiwiproPrincipal();
    
    @BeforeClass
    public static void setProUser() {
        Person person = new Person();
        person.setAcctID(ACCOUNT_ID);
        
        User user = new User(USER_ID, 1, null, Status.ACTIVE, "test", "testpassword", GROUP_ID);
        user.setPerson(person);

        ProUser proUser = new ProUser(user, new GrantedAuthority[] {new GrantedAuthorityImpl("ROLE_ADMIN")});

        TestingAuthenticationToken testToken = new TestingAuthenticationToken(
                proUser, proUser.getPassword(), proUser.getAuthorities());
        
        SecurityContext securityContext = new SecurityContextImpl();
        testToken.setAuthenticated(true);
        securityContext.setAuthentication(testToken);
        SecurityContextHolder.setContext(securityContext);
    }
    
    /**
     * Test for getUser().
     */
    @Test
    public void testGetUser() {
        User user = principalSUT.getUser();
        assertEquals(USER_ID, user.getUserID());
    }
    
    /**
     * Test for getPerson().
     */
    @Test
    public void testGetPerson() {
        Person person = principalSUT.getPerson();
        assertEquals(ACCOUNT_ID, person.getAcctID()); 
    }
    
    /**
     * Test for getGroupID().
     */
    @Test
    public void testGetGroupID() {
        Integer groupId = principalSUT.getGroupID();
        assertEquals(GROUP_ID, groupId);
    }
    
    /**
     * Test for getAccountID().
     */
    @Test
    public void testGetAccountID() {
        Integer accId = principalSUT.getAccountID();
        assertEquals(ACCOUNT_ID, accId);
    }
    
    /**
     * Test for isInthincUser().
     */
    @Test
    public void testIsInthincUser() {
        boolean isInthinc = principalSUT.isInthincUser();
        assertTrue(isInthinc);
    }
}
