/**
 * 
 */
package com.inthinc.pro.service.security.stubs;

import java.util.List;

import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

/**
 * @author dfreitas
 * 
 */
public class DriverReportDaoStub implements DriverReportDAO {

    @Override
    public List<Trend> getTrend(Integer driverID, Duration duration) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Score getScore(Integer driverID, Duration duration) {
        // TODO Auto-generated method stub
        return new Score();
    }

}
