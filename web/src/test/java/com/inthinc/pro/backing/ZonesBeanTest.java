package com.inthinc.pro.backing;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.type.OptionValue;
import com.inthinc.pro.model.zone.option.type.SpeedValue;
import com.inthinc.pro.util.MiscUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.expect;
import static org.easymock.classextension.EasyMock.replay;

public class ZonesBeanTest extends BaseBeanTest
{
    @Ignore
    @Test
    public void list()
    {
        // login
        loginUser("custom101");

        // get the bean from the applicationContext (initialized by Spring injection)
        ZonesBean zonesBean = getZonesBean();

        // items
        assertNotNull(zonesBean);
        assertNotNull(zonesBean.getZoneIDs());
        assertTrue(zonesBean.getZoneIDs().size() > 0);
        assertEquals(zonesBean.getZoneIDs().size(), zonesBean.getZonesCount());
    }

    @Ignore
    @Test
    public void display()
    {
        // login
        loginUser("custom101");

        // get the bean from the applicationContext (initialized by Spring injection)
        ZonesBean zonesBean = getZonesBean();

        // first item populated by default
        assertNotNull(zonesBean.getItem());
        assertEquals(zonesBean.getItem().getZoneID(), zonesBean.getItemID());
        assertNotNull(zonesBean.getPointsString());

        // set a display ID
        final Integer id = (Integer)zonesBean.getZoneIDs().get(0).getValue();
        zonesBean.setItemID(id);
        assertEquals(id, zonesBean.getItemID());
        assertEquals(zonesBean.getItem().getZoneID(), zonesBean.getItemID());
        assertNotNull(zonesBean.getPointsString());
    }

    // TODO: the following test works when run standalone, but when run as part of the build, the newly added item isn't assigned an ID
    @Ignore
    @Test
    public void add()
    {
        // login
        loginUser("custom101");

        // get the bean from the applicationContext (initialized by Spring injection)
        ZonesBean zonesBean = getZonesBean();
        zonesBean.getZoneIDs();

        // add
        assertFalse(zonesBean.isAdd());
        assertFalse(zonesBean.isEditing());
        zonesBean.add();
        assertTrue(zonesBean.isAdd());
        assertTrue(zonesBean.isEditing());

        // edit item (used for adding)
        assertNotNull(zonesBean.getItem());
        assertNull(zonesBean.getItem().getZoneID());

        // cancel it
        zonesBean.cancelEdit();
        assertFalse(zonesBean.isEditing());

        // start another add
        zonesBean.add();
        assertTrue(zonesBean.isAdd());
        assertTrue(zonesBean.isEditing());

        // populate
        populate(zonesBean.getItem());
        assertNull(zonesBean.getItem().getZoneID());

        // save
        int count = zonesBean.getZonesCount();
        zonesBean.save();
        assertNotNull(zonesBean.getItem().getZoneID());
        assertFalse(zonesBean.isAdd());
        assertFalse(zonesBean.isEditing());
        assertEquals(count + 1, zonesBean.getZonesCount());
    }

    @Ignore
    @Test
    public void edit()
    {
        // login
        loginUser("custom101");

        // get the bean from the applicationContext (initialized by Spring injection)
        ZonesBean zonesBean = getZonesBean();

        // set an edit ID
        final Integer id = (Integer)zonesBean.getZoneIDs().get(0).getValue();
        zonesBean.setItemID(id);
        assertEquals(id, zonesBean.getItemID());
        assertEquals(zonesBean.getItem().getZoneID(), zonesBean.getItemID());
        assertNotNull(zonesBean.getPointsString());

        // edit
        assertFalse(zonesBean.isAdd());
        assertFalse(zonesBean.isEditing());
        zonesBean.edit();
        assertFalse(zonesBean.isAdd());
        assertTrue(zonesBean.isEditing());

        // cancel edit
        zonesBean.cancelEdit();
        assertFalse(zonesBean.isEditing());

        // start another edit
        zonesBean.edit();

        // populate
        populate(zonesBean.getItem());

        // save
        int count = zonesBean.getZonesCount();
        zonesBean.save();
        assertFalse(zonesBean.isAdd());
        assertFalse(zonesBean.isEditing());
        assertEquals(zonesBean.getZonesCount(), count);
    }

    @Ignore
    @Test
    public void delete()
    {
        // login
        loginUser("custom101");

        // get the bean from the applicationContext (initialized by Spring injection)
        ZonesBean zonesBean = getZonesBean();

        // select an item
        final Integer id = (Integer)zonesBean.getZoneIDs().get(0).getValue();
        zonesBean.setItemID(id);
        assertEquals(id, zonesBean.getItemID());
        assertEquals(zonesBean.getItem().getZoneID(), zonesBean.getItemID());
        assertNotNull(zonesBean.getPointsString());

        // delete
        int count = zonesBean.getZonesCount();
        zonesBean.delete();
        assertEquals(count - 1, zonesBean.getZonesCount());
    }

    @Test
    public void testTransformItemSpeedLimit() throws NoSuchMethodException {
        // 100 mph in english should be 100 mph
        ZonesBean mockZoneBean = getMockZoneBean(MeasurementType.ENGLISH, 100);
        mockZoneBean.transformItemSpeedLimit();
        SpeedValue speedValue = (SpeedValue) mockZoneBean.getItem().getOptionsMap().get(ZoneAvailableOption.SPEED_LIMIT);
        assertEquals(speedValue.getValue().intValue(), 100);

        // 100 kph in mph should be 62 km
        mockZoneBean = getMockZoneBean(MeasurementType.METRIC, 100);
        mockZoneBean.transformItemSpeedLimit();
        speedValue = (SpeedValue) mockZoneBean.getItem().getOptionsMap().get(ZoneAvailableOption.SPEED_LIMIT);
        assertEquals(speedValue.getValue().intValue(), 62);
    }

    @Test
    public void testGetMeasurementTypeMaxSpeedLimit() throws NoSuchMethodException {
        // english should have a max speed limit of 100 mph
        ZonesBean mockZoneBean = getMockZoneBean(MeasurementType.ENGLISH, 100);
        assertEquals(mockZoneBean.getMeasurementTypeMaxSpeedLimit(), ZonesBean.MAX_SPEED_IN_MPH.intValue());

        // metric should have a max speed limit of 160 kph
        mockZoneBean = getMockZoneBean(MeasurementType.METRIC, 100);
        assertEquals(mockZoneBean.getMeasurementTypeMaxSpeedLimit(), ZonesBean.MAX_SPEED_IN_KPH.intValue());
    }

    @Test
    public void testMeasurementTypeSpeedLimit() throws NoSuchMethodException {
        // english should be in miles
        ZonesBean mockZoneBean = getMockZoneBean(MeasurementType.ENGLISH, 100);
        assertEquals(mockZoneBean.getMeasurementTypeSpeedLimit(), 100);

        // metric should be transformed
        mockZoneBean = getMockZoneBean(MeasurementType.METRIC, 100);
        assertEquals(mockZoneBean.getMeasurementTypeSpeedLimit(), 161);
    }
    
    @Test
    public void testgetMeasurementTypeSpeedLimit_noZones_noException() throws NoSuchMethodException {
        ZonesBean mockZonesBean = getMockZoneBeanWithNoZones();
        assertEquals(mockZonesBean.getMeasurementTypeSpeedLimit(), 0);
    }

    @Test
    public void testGetSpeedMeasurementName() throws NoSuchMethodException {
        // english should be mph
        ZonesBean mockZoneBean = getMockZoneBean(MeasurementType.ENGLISH, 100);
        assertEquals(mockZoneBean.getSpeedMeasurementName(), "mph");

        // metric should be kph
        mockZoneBean = getMockZoneBean(MeasurementType.METRIC, 100);
        assertEquals(mockZoneBean.getSpeedMeasurementName(), "kph");
    }

    private ZonesBean getMockZoneBeanWithNoZones() throws NoSuchMethodException {
        ZonesBean zonesBean = createMock(ZonesBean.class, new Method[]{ZonesBean.class.getMethod("getPerson", null)});
//        expect(zonesBean.getPerson()).andReturn(getMockPerson(measurementType));
//        replay(zonesBean);
        return zonesBean;
    }
    
    private ZonesBean getMockZoneBean(MeasurementType measurementType, Integer value) throws NoSuchMethodException {
        ZonesBean zonesBean = createMock(ZonesBean.class, new Method[]{ZonesBean.class.getMethod("getPerson", null)});
        Zone zone = getMockZone(value);
        zonesBean.setTestZone(zone);
        expect(zonesBean.getPerson()).andReturn(getMockPerson(measurementType));
        replay(zonesBean);
        return zonesBean;
    }

    private Person getMockPerson(MeasurementType measurementType) throws NoSuchMethodException {
        Person person = new Person();
        person.setMeasurementType(measurementType);
        return person;
    }

    private Zone getMockZone(Integer value) {
        Zone zone = new Zone();
        Map<ZoneAvailableOption, OptionValue> optionMap = new HashMap<ZoneAvailableOption, OptionValue>();
        optionMap.put(ZoneAvailableOption.SPEED_LIMIT, new SpeedValue(value));
        zone.setOptionsMap(optionMap);
        return zone;
    }

    private ZonesBean getZonesBean()
    {
        return (ZonesBean) applicationContext.getBean("zonesBean");
    }

    private void populate(Zone zone)
    {
        zone.setCreated(new Date());
        zone.setName("Test zone");
        zone.setAddress("4225 West Lake Park Blvd. Suite 100 West Valley City UT 84120");
        final ArrayList<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < MiscUtil.randomInt(3, 9); i++)
            points.add(new LatLng(randomLat(), randomLng()));
        points.add(new LatLng(points.get(0).getLat(), points.get(0).getLng()));
        zone.setPoints(points);
    }

    private static float randomLng()
    {
        // longitude in range -117 to -101
        float base = -101.0f;

        return base + (MiscUtil.randomInt(0, 16000) / 1000.0f * -1.0f);
    }

    private static float randomLat()
    {
        // latitude in range 36 to 49
        float base = 36.0f;

        return base + (MiscUtil.randomInt(0, 15000) / 1000.0f);
    }
}
