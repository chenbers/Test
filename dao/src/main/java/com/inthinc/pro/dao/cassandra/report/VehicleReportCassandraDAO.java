package com.inthinc.pro.dao.cassandra.report;

import java.util.List;

import com.inthinc.pro.model.CustomDuration;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.report.VehicleReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

public class VehicleReportCassandraDAO extends ReportCassandraDAO implements VehicleReportDAO {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(VehicleReportCassandraDAO.class);

    @Override
    public Score getScore(Integer driverID, Duration duration) {
        logger.debug("getScore(): " + driverID);
        return getScoreForAsset(driverID, EntityType.ENTITY_DRIVER, duration);
    }

    @Override
    public Score getScore(Integer driverID, CustomDuration customDuration) {
        logger.debug("getScore(): " + driverID);
        return getScoreForAsset(driverID, EntityType.ENTITY_DRIVER, customDuration);
    }

    @Override
    public List<Trend> getTrend(Integer driverID, Duration duration) {
        logger.debug("getTrend(): " + driverID);
        return getTrendForAsset(driverID, EntityType.ENTITY_DRIVER, duration);
    }

    @Override
    public List<Trend> getTrend(Integer driverID, CustomDuration customDuration) {
        logger.debug("getTrend(): " + driverID);
        return getTrendForAsset(driverID, EntityType.ENTITY_DRIVER, customDuration);
    }

}
