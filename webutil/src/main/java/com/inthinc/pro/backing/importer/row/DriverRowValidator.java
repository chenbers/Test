package com.inthinc.pro.backing.importer.row;

import java.util.List;

import com.inthinc.pro.backing.importer.DriverTemplateFormat;

public class DriverRowValidator extends RowValidator {

    @Override
    public String validateRow(List<String> rowData) {
        
        DriverTemplateFormat format = new DriverTemplateFormat();
        String errorMsg = super.checkFormat(rowData, format);
        
        if (errorMsg.isEmpty()) {
            // more checks
            return null;
        }
        return errorMsg;
    }
}
