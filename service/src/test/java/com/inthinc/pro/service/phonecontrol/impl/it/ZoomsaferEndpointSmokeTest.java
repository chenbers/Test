package com.inthinc.pro.service.phonecontrol.impl.it;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;
import com.inthinc.pro.service.test.mock.aspects.PhoneControlDaoStubBehaviourAdvice;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class ZoomsaferEndpointSmokeTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * Cell phone test number provided by Zoomsafer.
     */
//    private final String CELL_PHONE_NUMBER = "8016737947";
    private final String CELL_PHONE_NUMBER = "8017127234";

    /**
     * Dummy test required to avoid JUnit initialization errors.
     */
    @Test
    public void testDummy() {}

    /*
     * This method should not be added to automatic execution during the integration test. It should be run manually as a smoke test since it hits the actual Cellcontrol service.
     */
    // DO NOT RUN THIS TEST AS PART OF THE AUTOMATED SUITE
    //@Test
    public void testDisablePhone() {
        PhoneControlAdapterFactory factory = (PhoneControlAdapterFactory) BeanFactoryUtils.beanOfType(this.applicationContext, PhoneControlAdapterFactory.class);
        PhoneControlAdapter zoomsaferAdapter = factory.createAdapter(CellProviderType.ZOOM_SAFER, PhoneControlDaoStubBehaviourAdvice.ZOOMSAFER_USERNAME, PhoneControlDaoStubBehaviourAdvice.ZOOMSAFER_PASSWORD);

        zoomsaferAdapter.disablePhone(CELL_PHONE_NUMBER);
    }

    /*
     * This method should not be added to automatic execution during the integration test. It should be run manually as a smoke test since it hits the actual Cellcontrol service.
     */
    // DO NOT RUN THIS TEST AS PART OF THE AUTOMATED SUITE
    //@Test
    public void testEnablePhone() {
        PhoneControlAdapterFactory factory = (PhoneControlAdapterFactory) BeanFactoryUtils.beanOfType(this.applicationContext, PhoneControlAdapterFactory.class);
        PhoneControlAdapter zoomsaferAdapter = factory.createAdapter(CellProviderType.ZOOM_SAFER, PhoneControlDaoStubBehaviourAdvice.ZOOMSAFER_USERNAME, PhoneControlDaoStubBehaviourAdvice.ZOOMSAFER_PASSWORD);

        zoomsaferAdapter.enablePhone(CELL_PHONE_NUMBER);
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
