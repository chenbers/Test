package it.com.inthinc.pro;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

//any classes that extend this one will also have access to these configurations
@ContextConfiguration(locations={"classpath:spring/applicationContext-serverPropertiesTest.xml",
								"classpath:spring/applicationContext-dao.xml",
                              "classpath:spring/applicationContext-beans.xml"},
                              loader=com.inthinc.pro.spring.test.WebSessionContextLoader.class)
                              
                              
                              

public class BaseSpringTest implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	@Test
	public void dummy()
	{
		assertTrue(true);
	}

}
