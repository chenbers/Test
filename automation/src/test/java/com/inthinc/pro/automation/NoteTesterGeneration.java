package com.inthinc.pro.automation;

import org.junit.Test;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.objects.TiwiProDevice;
import com.inthinc.pro.automation.utils.AutomationCalendar;

public class NoteTesterGeneration extends Thread{
    private TiwiProDevice tiwi1;
    private TiwiProDevice tiwi2;
    private TiwiProDevice tiwi3;
    private TiwiProDevice tiwi4;
    
    private String IMEI1;
    private String IMEI2;
    private String IMEI3;
    private String IMEI4;
    private Addresses server;
    private AutomationCalendar initialTime;
    
    


    public void start(String IMEI1, String IMEI2, String IMEI3, String IMEI4, Addresses server, AutomationCalendar initialTime) {
        this.IMEI1=IMEI1;
        this.IMEI2=IMEI2;
        this.IMEI3=IMEI3;
        this.IMEI4=IMEI4;
        this.server=server;
        this.initialTime = initialTime;
        super.start();
        
    }
    
    public void noteTesterFirstGeneration(String IMEI1, String IMEI2, String IMEI3, String IMEI4, Addresses server, AutomationCalendar initialTime) {
        this.IMEI1=IMEI1;
        this.IMEI2=IMEI2;
        this.IMEI3=IMEI3;
        this.IMEI4=IMEI4;
        this.server=server;
        this.initialTime = initialTime;
        noteTesterFirstGeneration();
    }

    public void noteTesterFirstGeneration() {
        tiwi1 = new TiwiProDevice(IMEI1, server);
        tiwi2 = new TiwiProDevice(IMEI2, server);
        tiwi3 = new TiwiProDevice(IMEI3, server);
        tiwi4 = new TiwiProDevice(IMEI4, server);
        
        
        TiwiProDevice[] tiwiArray = new TiwiProDevice[4];
        
        tiwiArray[0] = tiwi1;
        tiwiArray[1] = tiwi2;
        tiwiArray[2] = tiwi3;
        tiwiArray[3] = tiwi4;
        
        for(int i=0; i<4; i++){
            tiwiArray[i].set_time( initialTime.addToSeconds(60));
            tiwiArray[i].power_on_device();
            tiwiArray[i].turn_key_on(15);
        }
        
        tiwi1.nonTripNote( initialTime.addToSeconds(60), 1, 1, 10.0, 0.0, 60, 100);
        tiwi2.nonTripNote( initialTime.addToSeconds(60), 1, 1, 0.0, 10.0, 60, 100);
        tiwi3.nonTripNote( initialTime.addToSeconds(60), 1, 1, -10.0, 0.0, 60, 100);
        tiwi4.nonTripNote( initialTime.addToSeconds(60), 1, 1, 0.0, -10.0, 60, 100);

        
        //Driving Style notes.
        tiwi1.add_note_event(2, 100, 10);
        tiwi2.add_note_event(-200, 37, 22);
        tiwi3.add_note_event(10, 10, 1000);
        tiwi4.add_note_event(750, 50, 600);
        
        //Speeding notes.
        tiwi1.addSpeedingNote(100, 70, 55);
        tiwi4.addSpeedingNote(6000, 100, 72);
        
        //Seat Belt notes.
        tiwi1.add_seatBelt(60, 40, 100);
        tiwi3.add_seatBelt(50, 45, 50);
        
        //TODO Send Crash notes (USER STORY'D!).
        
        //Tampering notes.
        tiwi4.tampering(15);
        tiwi2.tampering(15);
        
        tiwi2.addIdlingNote(10, 20);
        tiwi3.addIdlingNote(23, 19);

        //Zone notes.
        //Pac Man is 915, Kazakhstan is 916, Tasmania is 917.
        tiwi2.enter_zone(915);
        tiwi3.enter_zone(916);
        tiwi4.enter_zone(917);
        tiwi2.leave_zone(917);
        tiwi3.leave_zone(915);
        tiwi4.leave_zone(916);
        
        tiwi1.update_location(10, 0, 15);
        tiwi2.update_location(0, 10, 15);
        tiwi3.update_location(-10, 0, 15);
        tiwi4.update_location(0, -10, 15);
        
        for(int i=0; i<4; i++){
            tiwiArray[i].add_stats();
            tiwiArray[i].turn_key_off(30);
            tiwiArray[i].power_off_device(900);
        }
    }
    
    @Test
    public void generateTestData() {
        System.out.println("generateTestData() running");
        main(null);
    }
    
    public static void main(String[] args){
        NoteTesterGeneration trip = new NoteTesterGeneration();
        AutomationCalendar initialTime = new AutomationCalendar();
        Addresses address;
        String imei1; String imei2; String imei3; String imei4;
        imei1 = "999999000109741";
        imei2 = "999999000109742";
        imei3 = "999999000109743";
        imei4 = "999999000109744";
        address=Addresses.QA;
        
        
        trip.noteTesterFirstGeneration( imei1, imei2, imei3, imei4, address, initialTime);
    }


}
