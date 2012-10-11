package com.inthinc.pro.backing;

import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.UnitTestStats;

public class SupportBeanTest extends BaseBeanTest { 

    @After
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        logoutUser();
    }

    @Test
    public void notLoggedIn()
    {
        this.loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        this.logoutUser();
        
        SupportBean supportBean = (SupportBean)applicationContext.getBean("supportBean");
        
        List<String>contactList = supportBean.getSupportContacts();
        assertNotNull("contact list should never be null", contactList);

        TiwiProProperties properties = new TiwiProProperties();
        String defaultContacts = properties.getProperty("supportContact.default");
        
        String defaultSupportContacts[] = defaultContacts.split("~");
        
        int cnt = 0;
        for (String contact : defaultSupportContacts) {
            assertEquals("contact (from tiwipro.properties", contact, contactList.get(cnt++));
        }
    }


    @Test
    public void loggedIn()
    {
        this.loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        SupportBean supportBean = (SupportBean)applicationContext.getBean("supportBean");
        
        List<String>contactList = supportBean.getSupportContacts();
        assertNotNull("contact list should never be null", contactList);
        assertEquals("contact 1 (from mock data)", UnitTestStats.ACCOUNT_CONTACT1, contactList.get(0));
        assertEquals("contact 2 (from mock data)", UnitTestStats.ACCOUNT_CONTACT2, contactList.get(1));
    }
    
    class TiwiProProperties extends Properties {
        
        public TiwiProProperties() {
            try
            {
                
                load(Thread.currentThread().getContextClassLoader().getResourceAsStream("tiwipro.properties"));
            }
            catch (Exception e)
            {
                System.out.println("tiwipro.properties cannot be loaded.");
                e.printStackTrace();
            }
        }
    }

}
