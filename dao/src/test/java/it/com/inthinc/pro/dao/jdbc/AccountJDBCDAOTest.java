package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import it.config.ITDataSource;

import java.util.List;

import org.junit.Test;

import com.inthinc.pro.dao.jdbc.AccountJDBCDAO;

public class AccountJDBCDAOTest extends BaseJDBCTest{

    
    @Test
    public void getAllActiveAccountIDsTest(){
        
        AccountJDBCDAO accountJDBCDAO = new AccountJDBCDAO();
        accountJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<Long> acctIDs = accountJDBCDAO.getAllValidAcctIDs();
        assertNotNull(acctIDs);
    }
}
