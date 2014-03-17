package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AddressJDBCDAO;
import com.inthinc.pro.model.Address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Date;

public class AddressJDBCDAOTest extends SimpleJdbcDaoSupport {

    //AddressJDBCDAOTest

    private static SiloService siloService;
    private static final String BASE_DATA_XML = "TeamStops.xml";
    private static ITData itData = new ITData();
    private Integer addrID;


    @Before
    public void setUpBeforeTest() throws Exception {

        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(BASE_DATA_XML);

        if (itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

        addrID = itData.address.getAddrID();

    }


      @Test
      public void findByIDTest() {
          AddressJDBCDAO addressDAO = new AddressJDBCDAO();
          DataSource dataSource = new ITDataSource().getRealDataSource();
          addressDAO.setDataSource(dataSource);

          Address address = addressDAO.findByID(addrID);

          assertNotNull(address);
      }


}