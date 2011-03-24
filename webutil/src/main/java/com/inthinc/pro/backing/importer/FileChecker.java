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

public class FileChecker {
    
    
    public List<String> checkFile(ImportType importType, InputStream is) {
        List<String> msgList = new ArrayList<String>();
        HSSFWorkbook wb;
        try {
            if (is == null) {
                msgList.add("ERROR: Spreadsheet is not found");
                return msgList;
            }
            wb = new HSSFWorkbook(new POIFSFileSystem(is));
        } catch (IOException e) {
            msgList.add("ERROR: Exception reading in spreadsheet: " + e.getMessage());
            return msgList;
        }

        TemplateFormat validFormat = importType.getTemplateFormat();
        
        Sheet sheet1 = wb.getSheetAt(0);
        boolean firstRow = true;
        for (Row row : sheet1) {
            
  
            if (firstRow || isBlankRow(row)) {
                firstRow = false;
                continue;
            }
            
            if (row.getLastCellNum() != validFormat.getNumColumns()) {
                msgList.add("ERROR: Columns missing -- Number of columns found: " + row.getLastCellNum() + " Expected: " + validFormat.getNumColumns());
                return msgList;
            }
          
            for (Cell cell : row) {
                
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                int columnIndex = cell.getColumnIndex();
                switch(cell.getCellType()) {
                  case Cell.CELL_TYPE_STRING:
                    if (!validFormat.isColumnValid(columnIndex, cell.getRichStringCellValue().getString()))
                        msgList.add("ERROR: " + cellRef.formatAsString() + " " + validFormat.getInvalidMessage(columnIndex));
                    break;
                  case Cell.CELL_TYPE_NUMERIC:
                      if (!validFormat.isColumnValid(columnIndex, Double.valueOf(cell.getNumericCellValue()).toString()))
                          msgList.add("ERROR: " + cellRef.formatAsString() + " " + validFormat.getInvalidMessage(columnIndex));
                      break;
                  case Cell.CELL_TYPE_BLANK:
                     if (validFormat.isColumnManditory(columnIndex)) 
                          msgList.add("ERROR: " + cellRef.formatAsString() + " Mandatory cell is blank");
                    break;
                  default:
                     msgList.add("ERROR: " + cellRef.formatAsString() + " Invalid cell type, expected a String");
                     break;
                 }
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
