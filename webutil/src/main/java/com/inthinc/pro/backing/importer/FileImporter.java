package com.inthinc.pro.backing.importer;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.importer.row.RowImporter;

public class FileImporter {
    
    private static final Logger logger = Logger.getLogger(BulkImportBean.class);

    public List<String> importFile(ImportType importType, InputStream is) {
        
        Integer columnCount = importType.getRowValidator().getColumnCount();
        List<DataRow> dataList = new ExcelFile().parseFile(is, columnCount);
        List<String> msgList = new FileChecker().checkDataList(importType, dataList, false);
        if (msgList.size() != 0)
            return msgList;

        RowImporter rowImporter = importType.getRowImporter();
        for (DataRow row : dataList) {
            String msg = rowImporter.importRow(row.getData());
            if (msg != null)
                msgList.add(row.getLabel() + ": " + msg);
            logger.info("Import: " + row.getLabel());
        }
        return msgList;
    }

}
