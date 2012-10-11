package com.inthinc.pro.backing.importer;

import java.io.InputStream;
import java.util.ArrayList;
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
//        msgList.add("test feedback");
        if (msgList.size() != 0)
            return msgList;
        
        ImportThread thread = new ImportThread(importType, dataList, progressBarBean);
        thread.start();

        return null;
    }

    class ImportThread extends Thread
    {
        ImportType importType;
        List<DataRow> dataList;
        ProgressBarBean progressBarBean; 
        public ImportThread(ImportType importType, List<DataRow> dataList, ProgressBarBean progressBarBean) 
        {
            this.importType = importType;
            this.dataList = dataList;
            this.progressBarBean = progressBarBean;
        }
        
        @Override
        public void run() {
            
            RowImporter rowImporter = importType.getRowImporter();
            List<String> msgList = new ArrayList<String>();
            long totalRows = dataList.size();
            long count = 0l;
            
            for (DataRow row : dataList) {
                String msg = rowImporter.importRow(row.getData());
                if (msg != null) {
                    msgList.add(row.getLabel() + ": " + msg);
                    logger.error("Import: " + row.getLabel() + " ERROR: " + msg);
                }
                else logger.info("Import: " + row.getLabel());
                
                if (progressBarBean != null) {
                    progressBarBean.setCurrentValue((count++ * 100l) / totalRows);
                }
            }
            
            logger.info("COMPLETED");
            if (progressBarBean != null) {
                progressBarBean.stopProcess(msgList);
            }
            
        }
    }
}
