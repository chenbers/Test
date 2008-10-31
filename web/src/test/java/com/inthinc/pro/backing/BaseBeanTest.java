package com.inthinc.pro.backing;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.dao.ScoreDAO;

@RunWith(SpringJUnit4ClassRunner.class)

// any classes that extend this one will also have access to these configurations
@ContextConfiguration(locations={"file:./src/main/config/appcontext/applicationContext-mockdao.xml",
                                 "file:./src/main/config/appcontext/applicationContext-daoBeans.xml",
                                 "file:./src/main/config/appcontext/applicationContext-beans.xml",
                                 "file:./src/main/config/appcontext/applicationContext-security.xml"})

                                 
// implementing ApplicationContextAware give the test class access to the ApplicationContext                            
public class BaseBeanTest implements ApplicationContextAware
{
    
    private static final Logger logger = Logger.getLogger(BaseBeanTest.class);
    protected ApplicationContext applicationContext;
    
    @BeforeClass
    public static void runOnceBeforeAllTests() throws Exception
    {
        System.setProperty("log4j.configuration", "file:./src/test/resources/log4j.properties");
        
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
        
    }
    
    protected ScoreDAO getScoreDAO()
    {
        return (ScoreDAO)applicationContext.getBean("scoreDAO");
    }

    @Test
    public void dummy()
    {
        assertEquals("", 1,1);
    }
    
    

}
