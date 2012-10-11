package com.inthinc.pro.service.it;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-beans.xml", "classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-sbs.xml",
        "classpath:spring/applicationContext-security.xml" })
@Ignore
public class ServiceSecuritySmokeTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Test
    public void dummyTest() {
      
    }
    
    /*
     * Used for manual smoke testing of secured services.
     */
    // @Test
    public void testAdvisingBeans() {
        ProUser principal = new ProUser("TEST_4846", "password", false, false, false, false, new GrantedAuthorityImpl[] {});
       
        User user = new User();
        user.setGroupID(1);
        
        Person person = new Person();
        person.setAcctID(1);
        person.setPersonID(1);
        
        Address address = new Address();
        address.setAccountID(1);
        address.setAddrID(1);
        
        person.setAddress(address);
        user.setPerson(person);
        principal.setUser(user);
        
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, "password"));
        
        UserService driverService = (UserService) applicationContext.getBean("userService");
        driverService.delete(1);
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
