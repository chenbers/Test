package com.inthinc.pro.backing;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class VersionBeanTest extends BaseBeanTest 
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
    public void version()
    {
        // this doesn't test much since there is no pom.xml in the application Context
        // but here for completeness/code coverage
        
        VersionBean versionBean = (VersionBean)applicationContext.getBean("versionBean");

        versionBean.setApplicationContext(applicationContext);
        
        String version = versionBean.getVersion();
        
        assertEquals("Unknown", version);

        versionBean.init();
        
        version = versionBean.getVersion();
        
        assertEquals("Unknown", version);
        
    }

}
