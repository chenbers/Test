package com.inthinc.pro.backing.importer;

import com.inthinc.pro.backing.importer.validator.CountryValidator;
import com.inthinc.pro.backing.importer.validator.FuelEfficiencyTypeValidator;
import com.inthinc.pro.backing.importer.validator.LanguageValidator;
import com.inthinc.pro.backing.importer.validator.MeasurementTypeValidator;
import com.inthinc.pro.backing.importer.validator.TimeZoneValidator;

public class DriverTemplateFormat extends TemplateFormat {
    
    ColumnFormat columns[] = {
            new ColumnFormat("Account Name", true, 30),
            new ColumnFormat("Team (full path)", true, 255),
            new ColumnFormat("Last Name", true, 30),
            new ColumnFormat("First Name", true, 30),
            new ColumnFormat("Middle Name", false, 30),
            new ColumnFormat("Employee ID", true, 30),
            new ColumnFormat("RFID", false, 19),
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
    public int getNumColumns() {
        return columns.length;
    }

    @Override
    public boolean isColumnManditory(int columnIndex) {
        return columns[columnIndex].isManditory();
    }

    @Override
    public boolean isColumnValid(int columnIndex, String value) {
        return columns[columnIndex].getValidator().isValid(value);
    }

    @Override
    public String getInvalidMessage(int columnIndex) {
        return columns[columnIndex].getValidator().getInvalidMessage();
    }
}
