package com.inthinc.pro.service.phonecontrol.impl.it;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class SpringConfigurationTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    
    @Test
    public void testDummy() {}

//    @Test
    public void testMovementEventHandlerBeanWiring() {
        MovementEventHandler movementEventHandler = (MovementEventHandler) BeanFactoryUtils.beanOfType(this.applicationContext, MovementEventHandler.class);
        
        assertNotNull(movementEventHandler);
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
