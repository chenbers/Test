package com.inthinc.pro.backing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountBeanTest extends BaseBeanTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void bean()
    {
         
        // team level login
        loginUser("custom101");
        
        AccountBean accountBean = (AccountBean) applicationContext.getBean("accountBean");
        assertNotNull(accountBean);

    }
}
