package com.inthinc.pro.service.impl;

import org.junit.Test;

import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.it.BaseTest;


public class VehicleServiceTest extends BaseTest {
    
    @Test
    public void testVehicle() {
//        List<Vehicle> list = service.getAll();
//        for(Vehicle vehicle : list) {
//            System.out.println(vehicle);
//        }
    }
    
    @Test
    public void createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setDriverID(0);
        vehicle.setDeviceID(0);
    }
}
