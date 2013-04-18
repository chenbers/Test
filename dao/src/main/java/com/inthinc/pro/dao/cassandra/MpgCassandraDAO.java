package com.inthinc.pro.dao.cassandra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRows;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Vehicle;

public class MpgCassandraDAO extends AggregationCassandraDAO implements MpgDAO {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(MpgCassandraDAO.class);

    private ReportService reportService;

    @Override
    public MpgEntity findByID(Integer id) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }

    @Override
    public Integer create(Integer id, MpgEntity entity) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }

    @Override
    public Integer update(MpgEntity entity) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }

    @Override
    public Integer deleteByID(Integer id) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public List<MpgEntity> getEntities(Group group, Duration duration, GroupHierarchy gh) {
        logger.debug("getEntities: " + group.getGroupID());
        List<MpgEntity> mpgList = new ArrayList<MpgEntity>();
        List<Integer> groupIDList = gh.getChildrenIDList(group.getGroupID());
        for (Integer groupID : groupIDList){
            mpgList.addAll(getForGroup(groupID, duration, gh));
        }
        return mpgList; 
    }

    @Override
    public List<MpgEntity> getDriverEntities(Integer driverID, Duration duration, Integer count) {
        logger.debug("getDriverEntities: " + driverID);
        return getForAsset(driverID, duration, count, true);
    }

    @Override
    public List<MpgEntity> getVehicleEntities(Integer vehicleID, Duration duration, Integer count) {
        logger.debug("getVehicleEntities: " + vehicleID);
        return getForAsset(vehicleID, duration, count, false);
    }

    private List<MpgEntity> getForAsset(Integer driverID, Duration duration, Integer count, boolean isDriver) {
        count = (count == null) ? duration.getDvqCount() : count;
        List<MpgEntity> mpgList = new ArrayList<MpgEntity>();
        String endDate = getTodayForDriver(driverID); // TO DO: set correct timezone

        List<Composite> rowKeys = createDateIDKeys(endDate, driverID, duration.getAggregationBinSize(), count);

        String CF = (isDriver) ? getDriverAggCF(duration.getCode()) : getVehicleAggCF(duration.getCode());
        CounterRows<Composite, String> rows = fetchAggsForKeys(CF, rowKeys);
        Map<String, Map<String, Long>> driverMap = summarizeByDate(rows);

        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet()) {
            Map<String, Long> columnMap = entry.getValue();
            MpgEntity mpgEntity = new MpgEntity();

            double galHeavy = convertLong2Double(getValue(columnMap, MPGGALHEAVY));
            double galLight = convertLong2Double(getValue(columnMap, MPGGALLIGHT));
            double galMedium = convertLong2Double(getValue(columnMap, MPGGALMEDIUM));
            long odomHeavy = getValue(columnMap, MPGODOMETERHEAVY);
            long odomLight = getValue(columnMap, MPGODOMETERLIGHT);
            long odomMedium = getValue(columnMap, MPGODOMETERMEDIUM);
            double mpgHeavy = (galHeavy == 0.0) ? 0.0 : odomHeavy / galHeavy;
            double mpgLight = (galLight == 0.0) ? 0.0 : odomLight / galLight;
            double mpgMedium = (galMedium == 0.0) ? 0.0 : odomMedium / galMedium;

            logger.debug("Gallons: " + galHeavy + " " + galLight + " " + galMedium);
            logger.debug("Odometer: " + odomHeavy + " " + odomLight + " " + odomMedium);
            logger.debug("mpg: " + mpgHeavy + " " + mpgLight + " " + mpgMedium);
            logger.debug("Math.round(mpgLight)/10.0d: " + Math.round(mpgLight) / 10.0d);

            mpgEntity.setHeavyValue((mpgHeavy == 0.0) ? null : Math.round(mpgHeavy) / 10.0d);
            mpgEntity.setLightValue((mpgLight == 0.0) ? null : Math.round(mpgLight) / 10.0d);
            mpgEntity.setMediumValue((mpgMedium == 0.0) ? null : Math.round(mpgMedium) / 10.0d);
            mpgEntity.setOdometer(getValue(columnMap, ODOMETER6));
            mpgEntity.setDate(getDatefromString(entry.getKey()));
            mpgList.add(mpgEntity);
        }
        // Collections.sort(mpgList);
        return mpgList;
    }

    private List<MpgEntity> getForGroup(Integer groupID, Duration duration, GroupHierarchy gh) {
        int count = duration.getDvqCount();
        List<MpgEntity> mpgList = new ArrayList<MpgEntity>();

        List<Integer> groupIDList = gh.getGroupIDList(groupID);
        List<Composite> rowKeys = new ArrayList<Composite>();
        for (Integer groupId : groupIDList)
            rowKeys.addAll(createDateIDKeys(getTodayForGroup(groupId), groupId, duration.getDvqCode(), count));

        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(duration.getCode()), rowKeys);
        Map<String, Map<String, Long>> driverMap = summarize(rows);

        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet()) {
            Map<String, Long> columnMap = entry.getValue();
            MpgEntity mpgEntity = new MpgEntity();

            double galHeavy = convertLong2Double(getValue(columnMap, MPGGALHEAVY));
            double galLight = convertLong2Double(getValue(columnMap, MPGGALLIGHT));
            double galMedium = convertLong2Double(getValue(columnMap, MPGGALMEDIUM));
            long odomHeavy = getValue(columnMap, MPGODOMETERHEAVY);
            long odomLight = getValue(columnMap, MPGODOMETERLIGHT);
            long odomMedium = getValue(columnMap, MPGODOMETERMEDIUM);
            double mpgHeavy = (galHeavy == 0.0) ? 0.0 : odomHeavy / galHeavy;
            double mpgLight = (galLight == 0.0) ? 0.0 : odomLight / galLight;
            double mpgMedium = (galMedium == 0.0) ? 0.0 : odomMedium / galMedium;

            logger.debug("Gallons: " + galHeavy + " " + galLight + " " + galMedium);
            logger.debug("Odometer: " + odomHeavy + " " + odomLight + " " + odomMedium);
            logger.debug("mpg: " + mpgHeavy + " " + mpgLight + " " + mpgMedium);
            logger.debug("Math.round(mpgLight)/10.0d: " + Math.round(mpgLight) / 10.0d);

            mpgEntity.setHeavyValue((mpgHeavy == 0.0) ? null : Math.round(mpgHeavy) / 10.0d);
            mpgEntity.setLightValue((mpgLight == 0.0) ? null : Math.round(mpgLight) / 10.0d);
            mpgEntity.setMediumValue((mpgMedium == 0.0) ? null : Math.round(mpgMedium) / 10.0d);
            mpgEntity.setOdometer(getValue(columnMap, ODOMETER6));
            // mpgEntity.setDate(getDatefromString(entry.getKey()));
//            int subGroupId = Integer.parseInt(getKeyPart(entry.getKey(), 2));
            mpgEntity.setGroupID(groupID);
            mpgEntity.setEntityID(groupID);
            mpgEntity.setEntityName(gh.getGroup(groupID).getName());

//            logger.debug("mpg subGroupId: " + subGroupId + " key: " + entry.getKey());

            mpgList.add(mpgEntity);
        }
        // Collections.sort(mpgList);
        return mpgList;
    }

}
