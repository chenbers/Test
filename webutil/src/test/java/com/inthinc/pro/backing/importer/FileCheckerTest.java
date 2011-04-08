package com.inthinc.pro.backing.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.BaseSpringTest;

import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class FileCheckerTest extends BaseSpringTest {
    

//    @Ignore
    @Test
    public void checkDriverFileMissing() {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateMissing.xls");
        
        FileChecker importFile = new FileChecker();
        List<String> msgList = importFile.checkFile(ImportType.DRIVERS, stream, false);
        dumpErrors(msgList);

        assertEquals("Error Count", 1, msgList.size());
    }
    
    @Test
    public void checkDriverFileWithErrors() {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateErrors.xls");

        FileChecker importFile = new FileChecker();
        List<String> msgList = importFile.checkFile(ImportType.DRIVERS, stream, false);
        dumpErrors(msgList);

        // 2 rows (error message count)
        assertEquals("Error Count", 2, msgList.size());
        // missing 10 mandatory columns
        String row1Error[] = msgList.get(0).split(",");
        assertEquals("Row 1 Error Count", 10, row1Error.length);
        // 5 invalid columns
        String row2Error[] = msgList.get(1).split(",");
        assertEquals("Row 2 Error Count", 5, row2Error.length);
        
    }
    
    //@Ignore
    @Test
    public void checkDriverFileWithMissingColumn() {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateMissingCol.xls");

        FileChecker importFile = new FileChecker();
        List<String> msgList = importFile.checkFile(ImportType.DRIVERS, stream, false);
        dumpErrors(msgList);

        assertEquals("Error Count", 1, msgList.size());
        String row1Error[] = msgList.get(0).split(",");
        assertEquals("Row 1 Error Count", 5, row1Error.length);
    }
    
    private void dumpErrors(List<String> msgList) {
        for (String msg : msgList)
            System.out.println(msg);
    }
}
