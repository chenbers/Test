package com.inthinc.pro.backing.importer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.importer.row.RowValidator;

public class FileChecker {
    
    
    public List<String> checkFile(ImportType importType, InputStream is) {
        
        
        List<DataRow> dataList = new ExcelFile().parseFile(is);
        return checkDataList(importType, dataList);
    }
    
    public List<String> checkDataList(ImportType importType, List<DataRow> dataList ) {
        List<String> msgList = new ArrayList<String>();
        if (dataList == null) {
            msgList.add("ERROR: Unable to read in excel document.");
            return msgList;
        }
        
        RowValidator rowValidator = importType.getRowValidator();
        for (DataRow row : dataList) {
            String msg = rowValidator.validateRow(row.getData());
            if (msg != null) {
                msgList.add("ERROR (" + row.getLabel() + "): " + msg);
            }
        }
        return msgList;
        
    }
}
