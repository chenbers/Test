package com.inthinc.pro.backing.importer.row;

import java.util.HashMap;
import java.util.Map;


public class RowImporterFactory {
    private static DriverRowImporter driverRowImporter;
    private static VehicleRowImporter vehicleRowImporter;
    
    private static Map<RowImporterType, RowImporter> rowImporterMap;
    
    public static void init() {
        System.out.println("RowImporterFactory.init()");
        rowImporterMap = new HashMap<RowImporterType, RowImporter>();
        rowImporterMap.put(RowImporterType.DRIVER, driverRowImporter);
        rowImporterMap.put(RowImporterType.VEHICLE, vehicleRowImporter);
    }
    
    public static RowImporter getRowImporter(RowImporterType rowImporterType) {
        return rowImporterMap.get(rowImporterType);
    }
    public void setDriverRowImporter(DriverRowImporter driverRowImporterIn) {
        RowImporterFactory.driverRowImporter = driverRowImporterIn;
    }
    public void setVehicleRowImporter(VehicleRowImporter vehicleRowImporterIn) {
        RowImporterFactory.vehicleRowImporter = vehicleRowImporterIn;
    }
}
