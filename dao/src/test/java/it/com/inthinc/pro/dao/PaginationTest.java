package it.com.inthinc.pro.dao;

import java.io.InputStream;

import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;


public class PaginationTest {
	
    private static final Logger logger = Logger.getLogger(PaginationTest.class);
    private static SiloService siloService;
    private static ITData itData;
    
    private static final String PAGINATION_BASE_DATA_XML = "PageTest.xml";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PAGINATION_BASE_DATA_XML);
        if (!itData.parseTestData(stream, siloService, true)) {
            throw new Exception("Error parsing Test data xml file");
        }

        Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
  }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);

        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();

    }
    
    @Test
    public void events() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);

    }

    @Test
    public void redFlags() {
    }
}
