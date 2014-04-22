package it.com.inthinc.pro.dao.jdbc;
import com.inthinc.pro.dao.jdbc.TimeZoneJDBCDAO;
import com.inthinc.pro.model.SupportedTimeZone;
import it.config.ITDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import javax.sql.DataSource;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TimeZoneJDBCDAOTest extends SimpleJdbcDaoSupport {


    //TimeZoneJDBCDAOTest



    @Test
    public void findByIDTest() {
        TimeZoneJDBCDAO timeZoneJDBCDAO = new TimeZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        timeZoneJDBCDAO.setDataSource(dataSource);

        SupportedTimeZone timeZone = timeZoneJDBCDAO.findByID(24);
        assertTrue(timeZone.getTzName().equals("Africa/Gaborone"));
        assertTrue(timeZone.getEnabled().equals(1));
        assertNotNull(timeZone);

    }

    @Test
    public void getSupportedTimeZones(){

        TimeZoneJDBCDAO timeZoneJDBCDAO = new TimeZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        timeZoneJDBCDAO.setDataSource(dataSource);

        assertNotNull(timeZoneJDBCDAO.getSupportedTimeZones());

    }

}
