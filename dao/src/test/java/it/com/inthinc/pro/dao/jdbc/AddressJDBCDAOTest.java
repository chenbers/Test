package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AddressJDBCDAO;
import com.inthinc.pro.model.Address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;

import com.inthinc.pro.model.State;
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
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    private Integer addrID;


    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

        addrID = itData.address.getAddrID();

    }

    @Test
    public void findByIDTest() {
        AddressJDBCDAO addressDAO = new AddressJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        addressDAO.setDataSource(dataSource);

        //findById method
        Address address = addressDAO.findByID(addrID);
        assertTrue(address.getAddr1().equals(itData.address.getAddr1()));
        assertTrue(address.getCity().equals(itData.address.getCity()));
        assertTrue(address.getZip().equals(itData.address.getZip()));
        assertTrue(address.getState().getName().equals(itData.address.getState().getName()));
        assertNotNull(address);

    }

    private static void initApp() throws Exception {
    }

    @Test
    public void createTest() {
        AddressJDBCDAO addressDAO = new AddressJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        addressDAO.setDataSource(dataSource);
        boolean returnAddressID = false;

        Address addressToInsert = new Address();
        addressToInsert.setAccountID(1);
        addressToInsert.setAddr1("832 Street");
        addressToInsert.setCity("City 71");
        State state = new State();
        state.setAbbrev("UT");
        state.setName("Utah");
        state.setStateID(45);
        addressToInsert.setState(state);
        addressToInsert.setZip("10021");

        // create method
        Integer addressID = addressDAO.create(addressToInsert.getAccountID(), addressToInsert);
        returnAddressID = (addressID != null);
        assertTrue(returnAddressID);

        //find by id test method
        Address createdAddress = addressDAO.findByID(addressID);
        assertEquals("832 Street", addressToInsert.getAddr1(), createdAddress.getAddr1());
        assertEquals("City 71", addressToInsert.getCity(), createdAddress.getCity());
        assertEquals("Utah", addressToInsert.getState().getName(), createdAddress.getState().getName());
        assertEquals("10021", addressToInsert.getZip(), createdAddress.getZip());

        //now delete  using method deleteByID
        addressDAO.deleteByID(addressID);


    }

    @Test
    public void updateTest() {
        AddressJDBCDAO addressDAO = new AddressJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        addressDAO.setDataSource(dataSource);
        boolean returnAddressID = false;

        Address addressToInsert = new Address();
        addressToInsert.setAccountID(1);
        addressToInsert.setAddr1("832 Street");
        addressToInsert.setCity("City 71");
        State state = new State();
        state.setAbbrev("UT");
        state.setName("Utah");
        state.setStateID(45);
        addressToInsert.setState(state);
        addressToInsert.setZip("10021");

        Integer addressID = addressDAO.create(addressToInsert.getAccountID(), addressToInsert);
        returnAddressID = (addressID != null);
        assertTrue(returnAddressID);
        System.out.println("inserted "+ addressID);

        //get info for update
        Address addressToupdate = new Address();
        addressToupdate.setAccountID(2057);
        addressToupdate.setAddr1("815 St");
        addressToupdate.setCity("City 18");
        State nwState = new State();
        nwState.setAbbrev("AL");
        nwState.setName("Alabama");
        nwState.setStateID(1);
        addressToupdate.setState(nwState);
        addressToupdate.setZip("10025");
        addressToupdate.setAddrID(addressID);

        //update method
        addressDAO.update(addressToupdate);

        //find by id - updated address
        Address updatedAddress = addressDAO.findByID(addressID);
        assertEquals("815 St", addressToupdate.getAddr1(), updatedAddress.getAddr1());
        assertEquals("City 18", addressToupdate.getCity(), updatedAddress.getCity());
        assertEquals("Alabama", addressToupdate.getState().getName(), updatedAddress.getState().getName());
        assertEquals("10025", addressToupdate.getZip(), updatedAddress.getZip());


        //now delete using method deleteByID
        addressDAO.deleteByID(addressID);

    }

}