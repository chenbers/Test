package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.inthinc.pro.model.Account;
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
    @Test
    public void getAllAccountIDsTest(){

        AccountJDBCDAO accountJDBCDAO = new AccountJDBCDAO();
        accountJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<Account> acctIDs = accountJDBCDAO.getAllAcctIDs();
        assertNotNull(acctIDs);
        assertTrue(acctIDs.size()>=1);

    }
}
