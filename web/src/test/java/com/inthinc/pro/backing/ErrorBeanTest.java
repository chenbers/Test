package com.inthinc.pro.backing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ErrorBeanTest extends BaseBeanTest
{

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void errorMessages()
    {
        ErrorBean errorBean = (ErrorBean)applicationContext.getBean("errorBean");
        
        assertNotNull(errorBean);
        
        errorBean.setErrorMessage("TEST ERROR");
        
        assertEquals("TEST ERROR", errorBean.getErrorMessage());
        
        errorBean.clearErrorAction();
        
        assertNull(errorBean.getErrorMessage());
    }

}
