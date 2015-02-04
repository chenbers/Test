package com.inthinc.pro.table.model.provider;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test case for {@link EventPaginationTableDataProvider}.
 */
public class EventPaginationTableDataProviderTest {
    @Mocked
    private DeviceDAO mockDeviceDAO;

    private Device mockDevice;

    private ProductType mockProductType;

    private EventPaginationTableDataProvider provider = new EventPaginationTableDataProvider();
    private NonMaintenanceEventPaginationTableDataProvider nonMaintProvider = new NonMaintenanceEventPaginationTableDataProvider();

    private Integer MOCK_DEVICE_ID = 1;

    private List<Event> mockEmptyEventList = new ArrayList<Event>();

    private List<Event> mockBadEventList = new ArrayList<Event>();

    private List<Event> mockGoodEventList = new ArrayList<Event>();

    @Before
    public void beforeTest() {
        provider.setDeviceDAO(mockDeviceDAO);
        nonMaintProvider.setDeviceDAO(mockDeviceDAO);

        mockDevice = new Device();
        mockDevice.setDeviceID(MOCK_DEVICE_ID);
        mockProductType = ProductType.TIWIPRO;
        mockDevice.setProductVersion(mockProductType);

        for (int i = 0; i <= 10; i++) {

            // first generate bad data (no device name)
            Event event = new Event();
            event.setDeviceName(null);
            event.setDeviceID(null);
            mockBadEventList.add(event);

            // then good data
            event = new Event();
            event.setDeviceID(MOCK_DEVICE_ID);
            mockGoodEventList.add(event);
        }
    }

    @Test
    public void testFilterOutMaintenance() {

        // save 'bad types'
        List<NoteType> badNoteTypes = new ArrayList<NoteType>();

        for (EventSubCategory subCategories : Arrays.asList(EventSubCategory.PREVENTATIVE_MAINTENANCE, EventSubCategory.CONDITIONAL, EventSubCategory.IGNITION_OFF)) {
            for (EventType eventType : subCategories.getEventTypeSet()) {
                for (NoteType noteType : eventType.getNoteTypeList()) {
                    if (!badNoteTypes.contains(noteType))
                        badNoteTypes.add(noteType);
                }
            }
        }

        // get using method
        List<NoteType> checkList = nonMaintProvider.getNonMaintenanceNoteTypesInCategory(EventCategory.DIAGNOSTICS);


        // check for bad types
        for (NoteType nt: checkList){
            if (nt.equals(NoteType.IGNITION_OFF))
                continue; // this one is found in vehicle also

            Assert.assertFalse(nt.toString()+" found in bad types", badNoteTypes.contains(nt));
        }
    }


    @Test
    public void testPopulateDeviceNames() {
        new NonStrictExpectations() {{
            mockDeviceDAO.findByID(MOCK_DEVICE_ID);
            returns(mockDevice);
        }};

        // for empty requests no names should be saved
        provider.populateDeviceNames(mockEmptyEventList);

        for (Event event : mockEmptyEventList) {
            Assert.assertNull(event.getDeviceName());
        }

        // for bad requests no names should be saved
        provider.populateDeviceNames(mockBadEventList);

        for (Event event : mockEmptyEventList) {
            Assert.assertNull(event.getDeviceName());
        }

        // for good events, names should be saved
        provider.populateDeviceNames(mockGoodEventList);

        for (Event event : mockEmptyEventList) {
            Assert.assertNotNull(event.getDeviceName());
            Assert.assertEquals(event.getDeviceName(), mockDevice.getProductVersion().name());
        }
    }
}
