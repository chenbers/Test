/**
 * 
 */
package com.inthinc.pro.service.phonecontrol.impl.it;

import static org.junit.Assert.assertNotNull;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint;
import com.inthinc.pro.service.phonecontrol.client.ZoomsaferEndPoint;
import com.inthinc.pro.service.phonecontrol.dao.impl.InMemoryDriverPhoneDAO;
import com.inthinc.pro.service.phonecontrol.impl.CellcontrolAdapter;
import com.inthinc.pro.service.phonecontrol.impl.DefaultPhoneControlAdapterFactory;
import com.inthinc.pro.service.phonecontrol.impl.PhoneControlMovementEventHandler;
import com.inthinc.pro.service.phonecontrol.impl.PhoneWatchdogImpl;
import com.inthinc.pro.service.phonecontrol.impl.ZoomsaferAdapter;

/**
 * Integration test for PhoneWatchdogImpl.
 * The test mocks the CellControl provider and the backend eventDAO. 
 * The latter can be replaced by the real implementation when support for driver cellphones is implemented.
 * 
 * This test uses the production Spring configuration and replaces some of the beans in the context.
 * 
 * @author dcueva
 */
public class PhoneWatchdogImplTest {

	private static final Integer DRIVER_ID = new Integer(87);
	private static final int STATUS = 555; 
	  // 77711: see DriverDaoStubBehaviourAdvice. Change to a real driverID when back-end is ready.
	
	private PhoneWatchdogImpl watchdogSUT;
	
	@Test
    public void testDummy() {}
	@Ignore
	@SuppressWarnings("unchecked")
	@Test
	public void testEnablePhonesWhenLostCommIntegration(
			@Mocked final DriverDAO driverDAOMock,
			@Mocked final CellcontrolEndpoint cellcontrolEndpointMock,
	         @Mocked final ZoomsaferEndPoint zoomsaferEndpointMock) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
		  // Unfortunately Spring 2.5 is incompatible with JUnit 4.5, so we cannot use the easier @RunWith(SpringJUnit4ClassRunner.class)
		
		// Get the SUT with dependencies from the Spring Config
		watchdogSUT = (PhoneWatchdogImpl) applicationContext.getBean("phoneWatchdogImpl");
			
		// Overwrite the endpoint in the CellcontrolAdpater to use a Jmockit Mock 
		final DefaultPhoneControlAdapterFactory factory = (DefaultPhoneControlAdapterFactory) applicationContext.getBean("phoneControlAdapterFactory");
		final CellcontrolAdapter cellcontrolAdapter = (CellcontrolAdapter)factory.createAdapter(CellProviderType.CELL_CONTROL, "inthincapi", "1qa@WS3ed");
		Deencapsulation.setField(cellcontrolAdapter, "cellcontrolEndpoint", cellcontrolEndpointMock);
        final ZoomsaferAdapter zoomsaferAdapter = (ZoomsaferAdapter)factory.createAdapter(CellProviderType.ZOOM_SAFER, "8017127234", "password");
        Deencapsulation.setField(zoomsaferAdapter, "zoomsaferEndpoint", zoomsaferEndpointMock);
		
		// Overwrite the driverDAO bean 
		Deencapsulation.setField(watchdogSUT, "driverDAO", driverDAOMock);
//        Deencapsulation.setField(watchdogSUT, "phoneControl", phoneControlMock);
       PhoneControlMovementEventHandler phoneControl =  (PhoneControlMovementEventHandler)Deencapsulation.getField(watchdogSUT, "phoneControl");
		// Include a driver with the phone disabled
		InMemoryDriverPhoneDAO driverPhoneDAO = (InMemoryDriverPhoneDAO) applicationContext.getBean("inMemoryDriverPhoneDAO");
		driverPhoneDAO.addDriverToDisabledPhoneList(DRIVER_ID);
		
		// Get the phone number for the driver
		PhoneControlDAO phoneControlDAO = (PhoneControlDAO) applicationContext.getBean("phoneControlDAO");
		final Cellblock driver = phoneControlDAO.findByID(DRIVER_ID);
		assertNotNull(driver);
		
		new Expectations() {{
			// For now we need to Mock driverDAO to return no events for the driver
			// When the back-end is ready, this could be replaced with the real call.
			driverDAOMock.getLastLocation(DRIVER_ID);
			returns(null);
			factory.createAdapter(CellProviderType.CELL_CONTROL, "inthincapi", "1qa@WS3ed");
			returns(cellcontrolAdapter);
//            factory.createAdapter(CellProviderType.ZOOM_SAFER, "8017127234", "password");
//            returns(zoomsaferAdapter);
//			// Verifies that the correct endpoint was called
//			cellcontrolEndpointMock.enablePhone("8017127234");
//			returns(Response.status(STATUS).build());
//            phoneControlMock.handleDriverStoppedMoving(DRIVER_ID);

		}};
		
		// Run the test
		watchdogSUT.enablePhonesWhenLostComm();
	}
		
}
