package com.inthinc.pro.backing.importer;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;


public class ImportFileTest {
    

    @Ignore
    @Test
    public void checkDriverFileMissing() {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateMissing.xls");
        
        ImportFile importFile = new ImportFile();
        List<String> msgList = importFile.checkFile(ImportType.DRIVERS, stream);
        dumpErrors(msgList);

        assertEquals("Error Count", 1, msgList.size());
    }
    
    @Test
    public void checkDriverFileWithErrors() {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateErrors.xls");

        ImportFile importFile = new ImportFile();
        List<String> msgList = importFile.checkFile(ImportType.DRIVERS, stream);
        dumpErrors(msgList);

        // missing 10 mandatory columns
        // 5 invalid columns
        assertEquals("Error Count", 15, msgList.size());
    }
    
    @Ignore
    @Test
    public void checkDriverFileWithMissingColumn() {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateMissingCol.xls");

        ImportFile importFile = new ImportFile();
        List<String> msgList = importFile.checkFile(ImportType.DRIVERS, stream);
        dumpErrors(msgList);

        assertEquals("Error Count", 1, msgList.size());
        assertTrue("unexpected error message " + msgList.get(0), msgList.get(0).contains("Number of columns"));
    }
    
    private void dumpErrors(List<String> msgList) {
        for (String msg : msgList)
            System.out.println(msg);
    }
}
