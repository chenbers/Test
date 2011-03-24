package com.inthinc.pro.backing.importer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

import com.inthinc.pro.backing.importer.row.RowImporter;

public class FileImporter {
    
    public List<String> importFile(ImportType importType, InputStream is) {
        
        
        // TODO: if you do the check like this then the stream is closed, can we clone the stream or something?
        // or check it first in a separate call
//        List<String> msgList = new FileChecker().checkFile(importType, is);
//        if (msgList.size() != 0)
//            return msgList;
        
        List<String> msgList = new ArrayList<String>();
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(new POIFSFileSystem(is));
        } catch (IOException e) {
            msgList.add("ERROR: Exception reading in spreadsheet: " + e.getMessage());
            return msgList;
        }

        RowImporter rowImporter = importType.getRowImporter();
        
        Sheet sheet1 = wb.getSheetAt(0);
        boolean firstRow = true;
        for (Row row : sheet1) {
            if (firstRow || isBlankRow(row)) {
                firstRow = false;
                continue;
            }
            
            List<String> rowData = new ArrayList<String>();
            for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum, Row.RETURN_BLANK_AS_NULL);
                if (cell == null) {
                    rowData.add("");
                    continue;
                }
                switch(cell.getCellType()) {
                  case Cell.CELL_TYPE_STRING:
                    rowData.add(cell.getRichStringCellValue().getString());
                    break;
                  case Cell.CELL_TYPE_NUMERIC:
                    rowData.add(Double.valueOf(cell.getNumericCellValue()).toString());
                    break;
                  default:
                    rowData.add("");
                    break;
                 }
            }
            
            String msg = rowImporter.importRow(rowData);
            if (msg != null) {
                msgList.add("ERROR (Row " + row.getRowNum() + "): " + msg);
            }
        }
        return msgList;
    }

    private boolean isBlankRow(Row row) {
        for (Cell cell : row) {
            if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }
    


}
