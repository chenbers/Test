package com.inthinc.pro.backing.importer.row;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.importer.DataRow;
import com.inthinc.pro.backing.importer.TemplateFormat;

public abstract class RowValidator {
    

    private static final String colLabel[] = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public abstract List<String> validateRow(List<String> rowData, boolean includeWarnings);
    public abstract Integer getColumnCount();
    public abstract List<String> validate(List<DataRow> allRows, boolean includeWarnings);

    protected List<String> checkFormat(List<String> rowData, TemplateFormat format)
    {
        List<String> errorList = new ArrayList<String>();
        
        if (rowData.size() > format.getNumColumns()) {
            errorList.add("ERROR: The document format does not match the template.  Please make sure you are trying to check/import the correct type of document.");
            
            return errorList;
        }
        
        int columnIndex = 0;
        for (String colData : rowData) {
            if (format.isColumnManditory(columnIndex) && (colData == null || colData.trim().isEmpty())) {
                errorList.add("Col " + colLabel[columnIndex] + " (" + format.getColumnLabel(columnIndex) + ") - " + " - Mandatory cell is blank");
            }
            else if (colData != null && !colData.trim().isEmpty() && !format.isColumnValid(columnIndex, colData)) {
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
