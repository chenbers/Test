package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AddressJDBCDAO;
import com.inthinc.pro.dao.jdbc.TimeZoneJDBCDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.SupportedTimeZone;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Infrasoft02 on 4/22/2014.
 */
public class TimeZoneJDBCDAOTest extends SimpleJdbcDaoSupport {


    //AddressJDBCDAOTest

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    private Integer addrID;


    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

        addrID = itData.address.getAddrID();

    }

    @Test
    public void findByIDTest() {
        TimeZoneJDBCDAO timeZoneJDBCDAO = new TimeZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        timeZoneJDBCDAO.setDataSource(dataSource);

        //findById method
        SupportedTimeZone timeZone = timeZoneJDBCDAO.findByID(24);
        assertTrue(timeZone.getTzName().equals("Africa/Gaborone"));
        assertTrue(timeZone.getEnabled().equals(1));
        assertNotNull(timeZone);

    }





}
