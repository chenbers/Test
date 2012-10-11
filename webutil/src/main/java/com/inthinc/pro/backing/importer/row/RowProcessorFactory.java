package com.inthinc.pro.backing.importer.row;

import java.util.HashMap;
import java.util.Map;


public class RowProcessorFactory {
    private static DriverRowImporter driverRowImporter;
    private static VehicleRowImporter vehicleRowImporter;
    
    private static Map<RowProcessorType, RowImporter> rowImporterMap;

    private static DriverRowValidator driverRowValidator;
    private static VehicleRowValidator vehicleRowValidator;
    
    private static Map<RowProcessorType, RowValidator> rowValidatorMap;
    
    public static void init() {
        System.out.println("RowImporterFactory.init()");
        rowImporterMap = new HashMap<RowProcessorType, RowImporter>();
        rowImporterMap.put(RowProcessorType.DRIVER, driverRowImporter);
        rowImporterMap.put(RowProcessorType.VEHICLE, vehicleRowImporter);
        rowValidatorMap = new HashMap<RowProcessorType, RowValidator>();
        rowValidatorMap.put(RowProcessorType.DRIVER, driverRowValidator);
        rowValidatorMap.put(RowProcessorType.VEHICLE, vehicleRowValidator);
    }
    
    public static RowImporter getRowImporter(RowProcessorType rowType) {
        return rowImporterMap.get(rowType);
    }
    public void setDriverRowImporter(DriverRowImporter driverRowImporterIn) {
        RowProcessorFactory.driverRowImporter = driverRowImporterIn;
    }
    public void setVehicleRowImporter(VehicleRowImporter vehicleRowImporterIn) {
        RowProcessorFactory.vehicleRowImporter = vehicleRowImporterIn;
    }

    public static RowValidator getRowValidator(RowProcessorType rowType) {
        return rowValidatorMap.get(rowType);
    }
    public void setDriverRowValidator(DriverRowValidator driverRowValidatorIn) {
        RowProcessorFactory.driverRowValidator = driverRowValidatorIn;
    }
    public void setVehicleRowValidator(VehicleRowValidator vehicleRowValidatorIn) {
        RowProcessorFactory.vehicleRowValidator = vehicleRowValidatorIn;
    }

}
