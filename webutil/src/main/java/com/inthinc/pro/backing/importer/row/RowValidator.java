package com.inthinc.pro.backing.importer.row;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.importer.TemplateFormat;

public abstract class RowValidator {
    

    private static final String colLabel[] = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public abstract List<String> validateRow(List<String> rowData, boolean includeWarnings); 

    protected List<String> checkFormat(List<String> rowData, TemplateFormat format)
    {
        List<String> errorList = new ArrayList<String>();
        
        int columnIndex = 0;
        for (String colData : rowData) {
            if (format.isColumnManditory(columnIndex) && colData.isEmpty()) {
                errorList.add("Col " + colLabel[columnIndex] + " (" + format.getColumnLabel(columnIndex) + ") - " + " - Mandatory cell is blank");
            }
            else if (!format.isColumnValid(columnIndex, colData)) {
                errorList.add("Col " + colLabel[columnIndex] + " (" + format.getColumnLabel(columnIndex) + ") - " + format.getInvalidMessage(columnIndex, colData));
            }
            columnIndex++;
        }
        
        return errorList;
    }

    protected void addToList(String str, List<String> strList) {
        if (str != null)
            strList.add(str);
    }
    
}
