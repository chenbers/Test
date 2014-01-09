package it.com.inthinc.pro.dao.cassandra;

import static org.junit.Assert.*;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.cassandra.CassandraDB;
import com.inthinc.pro.dao.cassandra.EventCassandraDAO;
import com.inthinc.pro.dao.cassandra.LocationCassandraDAO;
import com.inthinc.pro.dao.hessian.EventHessianJdbcHybridDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;

public class EventCassandraDAOTest {
    private static CassandraDB cassandraDB;
    private static SiloService siloService;
    private static Integer TESTING_DRIVER_ID; 
    private static Integer TESTING_VEHICLE_ID;

    private static final String REPORT_BASE_DATA_XML = "ReportTest.xml";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        
        String clusterName = config.get(IntegrationConfig.CASSANDRA_CLUSTER).toString();
        String keyspaceName = config.get(IntegrationConfig.CASSANDRA_KEYSPACE).toString();
        String cacheKeyspaceName = config.get(IntegrationConfig.CASSANDRA_CACHE_KEYSPACE).toString();
        String nodeAddress = config.get(IntegrationConfig.CASSANDRA_NODE_ADDRESS).toString();
        Boolean autoDiscoverHosts = Boolean.valueOf(config.get(IntegrationConfig.CASSANDRA_AUTODISCOVER_HOSTS).toString());
        Boolean quorumConsistency = Boolean.valueOf(config.get(IntegrationConfig.CASSANDRA_QUORUM_CONSISTENCY).toString());
        Integer maxActive = Integer.valueOf(config.get(IntegrationConfig.CASSANDRA_MAXACTIVE).toString());
        cassandraDB = new CassandraDB(true, clusterName, keyspaceName, cacheKeyspaceName, nodeAddress, maxActive, autoDiscoverHosts, quorumConsistency);

        ITData itData = new ITData();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(REPORT_BASE_DATA_XML);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

        TESTING_DRIVER_ID = itData.teamGroupData.get(ITData.BAD).driver.getDriverID();
        TESTING_VEHICLE_ID = itData.teamGroupData.get(ITData.BAD).vehicle.getVehicleID();
    }
    

    @Test
    // DE9189
    public void testDriverEvents() {
        EventHessianJdbcHybridDAO eventDAO = new EventHessianJdbcHybridDAO();
        eventDAO.setSiloService(siloService);
        eventDAO.setDataSource(new ITDataSource().getRealDataSource());
        DateTime endDate = new DateTime();
        DateTime startDate = endDate.minusDays(1);
        List<Event> events = eventDAO.getEventsForDriver(TESTING_DRIVER_ID, startDate.toDate(), endDate.toDate(), EventCategory.VIOLATION.getNoteTypesInCategory(),
                EventDAO.EXCLUDE_FORGIVEN);
        assertNotNull("Expected events from database for driver " + TESTING_DRIVER_ID, events);

        // check against what is in cassandra
        EventCassandraDAO eventCassandraDAO = new EventCassandraDAO();
        eventCassandraDAO.setCassandraDB(cassandraDB);
        
        for (Event event : events) {
            List<Event> cassandraEvents = eventCassandraDAO.getEventsForDriver(TESTING_DRIVER_ID, event.getTime(), event.getTime(), EventCategory.VIOLATION.getNoteTypesInCategory(), EventDAO.EXCLUDE_FORGIVEN);
            assertTrue("Expected an event from cassandra for driver " + TESTING_DRIVER_ID, cassandraEvents != null && cassandraEvents.size() == 1);
            assertEquals("Expected matching event type", event.getType(), cassandraEvents.get(0).getType());
        }

    }

}
