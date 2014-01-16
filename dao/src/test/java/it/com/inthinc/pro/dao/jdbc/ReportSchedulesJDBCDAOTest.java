package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import it.config.ITDataSource;

import java.util.List;

import org.junit.Test;

import com.inthinc.pro.dao.jdbc.ReportScheduleJDBCDAO;
import com.inthinc.pro.model.ReportSchedule;

public class ReportSchedulesJDBCDAOTest {
    @Test
    public void getActiveReportSchedulesTest(){
        
        ReportScheduleJDBCDAO reportScheduleJDBCDAO = new ReportScheduleJDBCDAO();
        reportScheduleJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<ReportSchedule> reportSchedules = reportScheduleJDBCDAO.getActiveReportSchedulesByAccountID(1);
        assertNotNull(reportSchedules);
        ReportSchedule reportSchedule = reportSchedules.get(0);
        assertNotNull(reportSchedule.getTimeOfDay());
    }


}
