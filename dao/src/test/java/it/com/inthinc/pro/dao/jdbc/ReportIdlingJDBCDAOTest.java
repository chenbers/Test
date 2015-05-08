package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.jdbc.ReportIdlingJDBCDAO;
import com.inthinc.pro.model.IdlingReportItem;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

public class ReportIdlingJDBCDAOTest {
    static int MAX_PERF_ALLOWED_SECONDS = 120;
    static ReportIdlingJDBCDAO reportIdlingJDBCDAO;
    static int TEST_GROUP_ID = 1;
    private static SiloService siloService;
    IntegrationConfig config = new IntegrationConfig();
    String host = config.get(IntegrationConfig.SILO_HOST).toString();
    Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        reportIdlingJDBCDAO = new ReportIdlingJDBCDAO();
        reportIdlingJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
    }

    /**
     * Test Idling Report Data.
     */
    @Test
    public void testIdlingReportData() {
        DateTime endDate = new DateTime();
        DateTime startDate = endDate.minusMonths(1);
        Interval interval = new Interval(startDate, endDate);


        DateTime startSelect = new DateTime();
        List<IdlingReportItem> items = reportIdlingJDBCDAO.getIdlingReportData(TEST_GROUP_ID, interval);
        DateTime endSelect = new DateTime();

        Interval perfDuration = new Interval(startSelect, endSelect);
        assertTrue("Query was too slow", perfDuration.toDuration().getStandardSeconds() < MAX_PERF_ALLOWED_SECONDS);
    }
}
