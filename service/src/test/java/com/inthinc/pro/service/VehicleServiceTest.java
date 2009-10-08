package com.inthinc.pro.service;

import java.util.List;

import org.junit.Test;

import com.inthinc.pro.model.Vehicle;


public class VehicleServiceTest extends BaseTest<VehicleService> {
    
    @Test
    public void testVehicle() {
        List<Vehicle> list = service.getAll();
        for(Vehicle vehicle : list) {
            System.out.println(vehicle);
        }
    }
}
