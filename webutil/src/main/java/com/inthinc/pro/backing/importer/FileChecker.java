package com.inthinc.pro.backing.importer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.importer.row.RowValidator;

public class FileChecker {
    
    public static final String ROW_MARKER = "<b>Row:";
    public static final String WARNING_MARKER = "WARNING";
    
    public List<String> checkFile(ImportType importType, InputStream is, boolean includeWarnings) {
        
        
        List<DataRow> dataList = new ExcelFile().parseFile(is);
        return checkDataList(importType, dataList, includeWarnings);
    }
    
    public List<String> checkDataList(ImportType importType, List<DataRow> dataList, boolean includeWarnings ) {
        List<String> msgList = new ArrayList<String>();
        if (dataList == null) {
            msgList.add("ERROR: Unable to read in excel document.");
            return msgList;
        }
        
        RowValidator rowValidator = importType.getRowValidator();
        for (DataRow row : dataList) {
            List<String> errorList = rowValidator.validateRow(row.getData(), includeWarnings);
            if (!errorList.isEmpty()) {
                msgList.add(ROW_MARKER+ row.getLabel()+"</b>");
                msgList.addAll(errorList);
            }
        }
        return msgList;
        
    }
}
