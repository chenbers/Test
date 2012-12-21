package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.config.ITDataSource;

import org.junit.Test;

import com.inthinc.pro.dao.EmuDAO;
import com.inthinc.pro.dao.jdbc.EmuJDBCDAO;

public class EmuJDBCDAOTest {
    @Test
    public void testEmuLookup() {
        EmuDAO emuDAO = new EmuJDBCDAO();
        ((EmuJDBCDAO)emuDAO).setDataSource(new ITDataSource().getRealDataSource());

        String filename = emuDAO.lookupEMUFilename("696d6acbc199d607a5704642c67f4d86");
//        System.out.println("filname: " + filename);
        
        assertNotNull("excepted to find a filename for emu", filename);

        filename = emuDAO.lookupEMUFilename("BOGUS");
        
        assertNull("excepted to NOT find a filename for BOGUS emu", filename);

    }


}
