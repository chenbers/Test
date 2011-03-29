package com.inthinc.pro.backing.importer.row;

import java.util.List;

import com.inthinc.pro.backing.importer.TemplateFormat;

public abstract class RowValidator {
    
    public abstract String validateRow(List<String> rowData); 
    private static final String colLabel[] = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    protected String checkFormat(List<String> rowData, TemplateFormat format)
    {
        StringBuffer errorBuffer = new StringBuffer();
        
        int columnIndex = 0;
        for (String colData : rowData) {
            if (format.isColumnManditory(columnIndex) && colData.isEmpty()) {
                if (errorBuffer.length() > 0) errorBuffer.append(", ");
                errorBuffer.append("Col " + colLabel[columnIndex]+ " - Mandatory cell is blank");
            }
            else if (!format.isColumnValid(columnIndex, colData)) {
                if (errorBuffer.length() > 0) errorBuffer.append(", ");
                errorBuffer.append("Col " + colLabel[columnIndex] + " - " + format.getInvalidMessage(columnIndex));
            }
            columnIndex++;
        }
        
        return errorBuffer.toString();
    }


}
