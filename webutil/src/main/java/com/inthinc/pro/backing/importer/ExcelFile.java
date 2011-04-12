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

public class ExcelFile {
    
    public List<DataRow> parseFile(InputStream is) {

        if (is == null) {
            System.out.println("ERROR: Input Stream is null.");
            return null;
        }
        List<DataRow> dataList = new ArrayList<DataRow>();
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(new POIFSFileSystem(is));
        } catch (IOException e) {
            System.out.println("ERROR: Exception reading in spreadsheet: " + e.getMessage());
            return null;
        }

        
        Sheet sheet1 = wb.getSheetAt(0);
        boolean firstRow = true;
        for (Row row : sheet1) {
            if (firstRow || isBlankRow(row)) {
                firstRow = false;
                continue;
            }
            
            List<String> rowData = new ArrayList<String>();
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
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
                    Double cellValue = Double.valueOf(cell.getNumericCellValue());
                    if (cellValue != null)
                        rowData.add(String.valueOf(cellValue.longValue()));
                    else rowData.add("");
                    break;
                  default:
                    rowData.add("");
                    break;
                 }
            }

            dataList.add(new DataRow("Row " + (row.getRowNum()+1), rowData));
        }
        return dataList;
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
