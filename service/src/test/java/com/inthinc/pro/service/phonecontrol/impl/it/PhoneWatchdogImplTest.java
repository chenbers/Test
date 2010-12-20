/**
 * 
 */
package com.inthinc.pro.service.phonecontrol.impl.it;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.Response;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint;
import com.inthinc.pro.service.phonecontrol.dao.impl.InMemoryDriverPhoneDAO;
import com.inthinc.pro.service.phonecontrol.impl.CellcontrolAdapter;
import com.inthinc.pro.service.phonecontrol.impl.DefaultPhoneControlAdapterFactory;
import com.inthinc.pro.service.phonecontrol.impl.PhoneWatchdogImpl;

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

	private static final Integer DRIVER_ID = new Integer(77711);
	private static final int STATUS = 555; 
	  // 77711: see DriverDaoStubBehaviourAdvice. Change to a real driverID when back-end is ready.
	
	private PhoneWatchdogImpl watchdogSUT;
	
	@Test
    public void testDummy() {}
	
	@SuppressWarnings("unchecked")
//	@Test
	public void testEnablePhonesWhenLostCommIntegration(
			@Mocked final EventDAO eventDAOMock,
			@Mocked final CellcontrolEndpoint cellcontrolEndpointMock) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
		  // Unfortunately Spring 2.5 is incompatible with JUnit 4.5, so we cannot use the easier @RunWith(SpringJUnit4ClassRunner.class)
		
		// Get the SUT with dependencies from the Spring Config
		watchdogSUT = (PhoneWatchdogImpl) applicationContext.getBean("phoneWatchdogImpl");
			
		// Overwrite the endpoint in the CellcontrolAdpater to use a Jmockit Mock 
		DefaultPhoneControlAdapterFactory factory = (DefaultPhoneControlAdapterFactory) applicationContext.getBean("phoneControlAdapterFactory");
		   Map<CellProviderType, PhoneControlAdapter> map = (Map<CellProviderType, PhoneControlAdapter>) Deencapsulation.getField(factory, Map.class);
		CellcontrolAdapter cellcontrolAdapter = (CellcontrolAdapter) map.get(CellProviderType.CELL_CONTROL);
		Deencapsulation.setField(cellcontrolAdapter, "cellcontrolEndpoint", cellcontrolEndpointMock);
		
		// Overwrite the eventDAO bean 
		Deencapsulation.setField(watchdogSUT, "eventDAO", eventDAOMock);
		
		// Include a driver with the phone disabled
		InMemoryDriverPhoneDAO driverPhoneDAO = (InMemoryDriverPhoneDAO) applicationContext.getBean("inMemoryDriverPhoneDAO");
		driverPhoneDAO.addDriverToDisabledPhoneList(DRIVER_ID);
		
		// Get the phone number for the driver
		DriverDAO driverDAO = (DriverDAO) applicationContext.getBean("driverDAO");
		final Driver driver = driverDAO.findByID(DRIVER_ID);
		assertNotNull(driver);
		
		new Expectations() {{

			// For now we need to Mock eventDAO to return no events for the driver
			// When the back-end is ready, this could be replaced with the real call.
			eventDAOMock.getEventsForDriver(DRIVER_ID, (Date) any, (Date) any, null, EventDAO.INCLUDE_FORGIVEN);
			returns(new ArrayList<Event>());
			
			// Verifies that the correct endpoint was called
			cellcontrolEndpointMock.enablePhone(driver.getCellPhone());
			returns(Response.status(STATUS).build());
		}};
		
		// Run the test
		watchdogSUT.enablePhonesWhenLostComm();
	}
		
}
