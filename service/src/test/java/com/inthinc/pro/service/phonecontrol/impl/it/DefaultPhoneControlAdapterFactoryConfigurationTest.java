package com.inthinc.pro.service.phonecontrol.impl.it;

import static junit.framework.Assert.assertSame;

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
import com.inthinc.pro.service.phonecontrol.impl.CellcontrolAdapter;
import com.inthinc.pro.service.phonecontrol.impl.ZoomsaferAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class DefaultPhoneControlAdapterFactoryConfigurationTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Test
    public void testCreatesCellcontrolAdapter() {
        PhoneControlAdapterFactory factory = (PhoneControlAdapterFactory) BeanFactoryUtils.beanOfType(this.applicationContext, PhoneControlAdapterFactory.class);

        PhoneControlAdapter cellControlAdapter = factory.createAdapter(CellProviderType.CELL_CONTROL);

        assertSame(CellcontrolAdapter.class, cellControlAdapter.getClass());
    }

    @Test
    public void testCreatesZoomsaferAdapter() {
        PhoneControlAdapterFactory factory = (PhoneControlAdapterFactory) BeanFactoryUtils.beanOfType(this.applicationContext, PhoneControlAdapterFactory.class);

        PhoneControlAdapter cellControlAdapter = factory.createAdapter(CellProviderType.ZOOM_SAFER);

        assertSame(ZoomsaferAdapter.class, cellControlAdapter.getClass());
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
