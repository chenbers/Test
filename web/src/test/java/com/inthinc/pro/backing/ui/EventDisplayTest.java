package com.inthinc.pro.backing.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SpeedingEvent;

public class EventDisplayTest extends BaseBeanTest
{

    @Test
    public void eventDisplay() throws Exception
    {
        // create a test event with a driver and vehicle
        Person person = new Person();
        person.setEmpid(String.valueOf(100));
        person.setPersonID(100);
        person.setFirst("John");
        person.setLast("Doe");
        person.setTimeZone(TimeZone.getTimeZone("US/Pacific"));
        Driver driver = new Driver();
        driver.setDriverID(200);
        driver.setPersonID(100);
        driver.setPerson(person);
        driver.setGroupID(1);
        person.setDriver(driver);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID(400);
        vehicle.setGroupID(1);
        vehicle.setName("fred");
        
        String nowDateStr = "08/30/2007 10:30:00";
        SimpleDateFormat tzFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        tzFormat.setTimeZone(TimeZone.getTimeZone("US/Mountain"));
        Date eventDate = tzFormat.parse(nowDateStr);

        Event event = new SpeedingEvent(100l, vehicle.getVehicleID(), NoteType.SPEEDING_EX3, 
                eventDate,
                70, 50, 33.0089, -117.1100, 90, 75,
                65, 70, 50);
        event.setDriver(driver);
        event.setVehicle(vehicle);

        EventDisplay display = new EventDisplay(event);
        assertTrue(display.getDateStr().startsWith("Aug 30, 2007 "));
        assertEquals("John Doe", display.getDriverName());
        assertEquals(event, display.getEvent());
        //assertEquals(EventType.SPEEDING.toString(), display.getEventType());
        assertEquals(event.getTime().getTime(), display.getTime().getTime());
        assertEquals("fred", display.getVehicleName());
        
        
    }
    
    
    
}
