package com.inthinc.pro.selenium.steps;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripDriver;

public class PopulateEventsSteps {
    
    public void createNotes(){
        
        TiwiProDevice tiwi = new TiwiProDevice("999999000109750");
        TripDriver driver = new TripDriver(tiwi);
        String work = "40.709792,-111.993256", random1 = "40.636492,-111.950254";
        String random2 = "40.593328,-111.801703";
        
        driver.addToTrip(work, random1);
        driver.addToTrip(random1, random2);
        driver.addToTrip(random2, work);

        tiwi.getState().getTime().changeHourseTo(6).changeMinutesTo(0).changeMillisecondsTo(0);
        driver.addEvent(24, AutomationDeviceEvents.speeding(tiwi.getState(), null));
        driver.addEvent(35, AutomationDeviceEvents.speeding(tiwi.getState(), null));
        driver.addEvent(75, AutomationDeviceEvents.speeding(tiwi.getState(), null));
        
        driver.run();
    }

}
