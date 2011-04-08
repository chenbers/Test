package com.inthinc.pro.backing.importer;

import com.inthinc.pro.backing.importer.datacheck.AccountNameChecker;
import com.inthinc.pro.backing.importer.validator.CountryValidator;
import com.inthinc.pro.backing.importer.validator.FuelEfficiencyTypeValidator;
import com.inthinc.pro.backing.importer.validator.LanguageValidator;
import com.inthinc.pro.backing.importer.validator.MeasurementTypeValidator;
import com.inthinc.pro.backing.importer.validator.TimeZoneValidator;

public class DriverTemplateFormat extends TemplateFormat {

    public static final int ACCOUNT_NAME_IDX = 0;
    public static final int TEAM_PATH_IDX = 1;
    public static final int LAST_NAME_IDX = 2;
    public static final int FIRST_NAME_IDX = 3;
    public static final int MIDDLE_NAME_IDX = 4;
    public static final int EMPLOYEE_ID_IDX = 5;
    public static final int BARCODE_IDX = 6;
    public static final int USERNAME_IDX = 7;
    public static final int PASSWORD_IDX = 8;
    public static final int EMAIL_IDX = 9;
    public static final int TIMEZONE_IDX = 10;
    public static final int LANGUAGE_IDX = 11;
    public static final int COUNTRY_IDX = 12;
    public static final int MEASUREMENT_TYPE_IDX = 13;
    public static final int FUEL_EFFICIENCY_TYPE_IDX = 14;

    
    // TODO: got string lengths from the db, but double check what portal allows.
    ColumnFormat columns[] = {
            new ColumnFormat("Account Name", true, 30),
            new ColumnFormat("Team (full path)", true, 255),
            new ColumnFormat("Last Name", true, 30),
            new ColumnFormat("First Name", true, 30),
            new ColumnFormat("Middle Name", false, 30),
            new ColumnFormat("Employee ID", true, 30),
            new ColumnFormat("RFID Barcode", false, 19),
            new ColumnFormat("username", false, 30),
            new ColumnFormat("password", false, 64),
            new ColumnFormat("e-mail", false, 62),
            new ColumnFormat("TimeZone", true, new TimeZoneValidator()),
            new ColumnFormat("Language", true, new LanguageValidator()),
            new ColumnFormat("Country", true, new CountryValidator()),
            new ColumnFormat("Measurement", true, new MeasurementTypeValidator()),
            new ColumnFormat("Fuel Efficiency", true, new FuelEfficiencyTypeValidator()),
    };


    @Override
    public ColumnFormat[] getColumns() {
        return columns;
    }
}
