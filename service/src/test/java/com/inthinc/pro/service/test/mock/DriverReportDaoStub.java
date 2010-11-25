/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import java.util.List;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

/**
 * @author dfreitas
 * 
 */
@Component
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
