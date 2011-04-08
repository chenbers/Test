package com.inthinc.pro.backing.importer;

import com.inthinc.pro.backing.importer.validator.StateValidator;


public class VehicleTemplateFormat extends TemplateFormat {
    public static final int ACCOUNT_NAME_IDX = 0;
    public static final int TEAM_PATH_IDX = 1;
    public static final int MAKE_IDX = 2;
    public static final int MODEL_IDX = 3;
    public static final int YEAR_IDX = 4;
    public static final int VEHICLE_NAME_IDX = 5;
    public static final int VIN_IDX = 6;
    public static final int STATE_IDX = 7;
    public static final int LICENSE_IDX = 8;
    public static final int DEVICE_SERIAL_NUMBER_IDX = 9;
    public static final int ECALL_NUMBER_IDX = 10;
    public static final int DRIVER_EMPLOYEE_ID_IDX = 11;

    
    ColumnFormat columns[] = {
            new ColumnFormat("Account Name", true, 30),
            new ColumnFormat("Team (full path)", true, 255),
            new ColumnFormat("Make", false, 22),
            new ColumnFormat("Model", false, 22),
            new ColumnFormat("Year", false, 4),
            new ColumnFormat("Vehicle Name", true, 30),
            new ColumnFormat("VIN", true, 17),
            new ColumnFormat("State", false, new StateValidator()),
            new ColumnFormat("License", false, 10),
            new ColumnFormat("Device Serial Number", false, 10),
            new ColumnFormat("E-call number", false, 22),
            new ColumnFormat("Employee ID", false, 30),
    };



    @Override
    public ColumnFormat[] getColumns() {
        return columns;
    }
}
