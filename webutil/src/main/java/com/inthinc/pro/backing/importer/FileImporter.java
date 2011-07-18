package com.inthinc.pro.backing.importer;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.importer.row.RowImporter;

public class FileImporter {
    
    private static final Logger logger = Logger.getLogger(BulkImportBean.class);

    public List<String> importFile(ImportType importType, InputStream is) {
        return importFile(importType, is, null);
    }
    public List<String> importFile(ImportType importType, InputStream is, ProgressBarBean progressBarBean) {
        
        Integer columnCount = importType.getRowValidator().getColumnCount();
        List<DataRow> dataList = new ExcelFile().parseFile(is, columnCount);
        List<String> msgList = new FileChecker().checkDataList(importType, dataList, false);
        if (msgList.size() != 0)
            return msgList;

        RowImporter rowImporter = importType.getRowImporter();
        
        long totalRows = dataList.size();
        long count = 0l;
        
        for (DataRow row : dataList) {
            String msg = rowImporter.importRow(row.getData());
            if (msg != null)
                msgList.add(row.getLabel() + ": " + msg);
            logger.info("Import: " + row.getLabel());
            
            if (progressBarBean != null) {
                progressBarBean.setCurrentValue((count++ * 100l) / totalRows);
            }
        }
        return msgList;
    }

}
