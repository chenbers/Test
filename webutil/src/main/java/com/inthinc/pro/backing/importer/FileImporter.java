package com.inthinc.pro.backing.importer;

import java.io.InputStream;
import java.util.List;

import com.inthinc.pro.backing.importer.row.RowImporter;

public class FileImporter {
    
    public List<String> importFile(ImportType importType, InputStream is) {
        
        List<DataRow> dataList = new ExcelFile().parseFile(is);
        List<String> msgList = new FileChecker().checkDataList(importType, dataList);
        if (msgList.size() != 0)
            return msgList;

        RowImporter rowImporter = importType.getRowImporter();
        for (DataRow row : dataList) {
            String msg = rowImporter.importRow(row.getData());
            if (msg != null) {
                msgList.add("ERROR (" + row.getLabel() + "): " + msg);
            }
        }
        return msgList;
    }

}
