package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import it.config.ITDataSource;
import it.config.IntegrationConfig;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.SiloAware;
import com.inthinc.pro.dao.SiloDAO;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.SiloJDBCDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.app.Silos;
import com.inthinc.pro.model.silo.SiloDef;

public class SilosTest {

    @Test
    public void testSilos() {
        SiloDAO siloDAO = new SiloJDBCDAO();
        ((SiloJDBCDAO)siloDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        Silos silos = new Silos();
        silos.setSiloDAO(siloDAO);
        silos.init();
        
        List<SiloDef> silosList = Silos.getSiloList();
        
        assertEquals("expected one silo on dev", 1, silosList.size());
        SiloDef siloDef = silosList.get(0);
        
        SiloDef siloDefByID = Silos.getSiloById(siloDef.getSiloID());
        assertEquals("expected same silo def", siloDef.getUrl(), siloDefByID.getUrl());
        
        SiloDef siloDefByURL = Silos.getSiloByUrl(siloDef.getUrl());
        assertEquals("expected same silo def", siloDef.getSiloID(), siloDefByURL.getSiloID());
        
    }

    @Test
    public void testSwitchSilo() {
        SiloDAO siloDAO = new SiloJDBCDAO();
        ((SiloJDBCDAO)siloDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        Silos silos = new Silos();
        silos.setSiloDAO(siloDAO);
        silos.init();
        

        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        SiloService siloService = new SiloServiceCreator(host, port).getService();
        
        AccountDAO accountDAO = new AccountHessianDAO();
        ((AccountHessianDAO)accountDAO).setSiloService(siloService);
        assertTrue("account Hessian DAO is siloAware", (accountDAO instanceof SiloAware));
        
        List<Account> accountList = accountDAO.getAllAcctIDs();
        
        // basically just switching to same silo because only one on dev
        ((SiloAware)accountDAO).switchSilo(0);
        List<Account> accountList2 = accountDAO.getAllAcctIDs();
        
        assertEquals("account list size", accountList.size(), accountList2.size());
        
    }


}
