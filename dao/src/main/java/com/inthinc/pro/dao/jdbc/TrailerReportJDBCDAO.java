package com.inthinc.pro.dao.jdbc;

import static com.inthinc.pro.dao.util.NumberUtil.objectToInteger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.report.TrailerReportDAO;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TrailerAssignedStatus;
import com.inthinc.pro.model.TrailerEntryMethod;
import com.inthinc.pro.model.TrailerPairingType;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;

public class TrailerReportJDBCDAO extends SimpleJdbcDaoSupport implements TrailerReportDAO{
    private static final Logger logger = Logger.getLogger(TrailerReportJDBCDAO.class);

    private static final String SELECT_ALL_FROM_TRAILER_PERFORMANCE =
            " select trailer.trailerID " +
                    " , trailer.status as trailerStatus" +
                    " , trailer.name as trailerName" +
                    " , vdd.vehicleID" +
                    " , vehicle.name as vehicleName" +
                    " , vdd.driverID" +
                    " , concat(person.first,' ',person.middle,' ',person.last,' ',person.suffix) as driverName  " +
                    " , vehicle.groupID " +
                    " , groups.name as groupName " +
                    " , trailer.pairingDate as pairingDate " +
                    " , trailer.pairingType as pairingType " +
                    " from trailer " +
                    " left outer join vddlog vdd ON (trailer.deviceID = vdd.deviceID and vdd.stop is null) "+
                    " left outer join vehicle on vdd.vehicleID = vehicle.vehicleID " +
                    " left outer join driver on vdd.driverID = driver.driverID " +
                    " left outer join person on driver.personID = person.personID " +
                    " left outer join groups on vehicle.groupID = groups.groupID ";

    private static final String SELECT_COUNT_FROM_TRAILER_PERFORMANCE = "select COUNT(*) from  trailer " +
            " left outer join vddlog vdd ON (trailer.deviceID = vdd.deviceID and vdd.stop is null) "+
            " left outer join vehicle on vdd.vehicleID = vehicle.vehicleID " +
            " left outer join driver on vdd.driverID = driver.driverID " +
            " left outer join person on driver.personID = person.personID " +
            " left outer join groups on vehicle.groupID = groups.groupID ";


    private static ParameterizedRowMapper<TrailerReportItem> trailerReportItemRowMapper = new ParameterizedRowMapper<TrailerReportItem>() {
        @Override
        public TrailerReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrailerReportItem trailerReportItem = new TrailerReportItem();
            trailerReportItem.setTrailerID(objectToInteger(rs.getObject("trailerID")));
            trailerReportItem.setTrailerName(rs.getString("trailerName"));
            trailerReportItem.setVehicleID(objectToInteger(rs.getObject("vehicleID")));
            trailerReportItem.setVehicleName(rs.getString("vehicleName"));
            trailerReportItem.setDriverID(objectToInteger(rs.getObject("driverID")));
            trailerReportItem.setDriverName(rs.getString("driverName"));
            trailerReportItem.setGroupID(objectToInteger(rs.getObject("groupID")));
            trailerReportItem.setGroupName(rs.getString("groupName"));
            TrailerPairingType trailerPairingType = TrailerPairingType.valueOf(objectToInteger(rs.getObject("pairingType")));
            trailerReportItem.setEntryMethod(trailerPairingType);
            trailerReportItem.setStatus(Status.valueOf(objectToInteger(rs.getObject("trailerStatus"))));
            trailerReportItem.setAssignedStatus(trailerPairingType);
            return trailerReportItem;
        }
    };
    private static final LinkedHashMap<String, String> replaceColumnNameMap = new LinkedHashMap<String, String>();

    static {
        replaceColumnNameMap.put("name", "trailer.name");
        replaceColumnNameMap.put("trailerName", "trailer.name");
        replaceColumnNameMap.put("groupName", "groups.name");
        replaceColumnNameMap.put("vehicleName", "vehicle.name");
        replaceColumnNameMap.put("driverName", "concat(person.first,' ',person.middle,' ',person.last,' ',person.suffix)");
        replaceColumnNameMap.put("status", "trailer.status");
        replaceColumnNameMap.put("entryMethod", "trailer.pairingType");
        replaceColumnNameMap.put("assignedStatus", "trailer.pairingType");
    };
    private String replaceColumnName(String paramColName){
        String tempFieldIdentifier = "";
        if(replaceColumnNameMap.containsKey(paramColName)){
            tempFieldIdentifier = replaceColumnNameMap.get(paramColName);
        } else {
            tempFieldIdentifier = paramColName;
        }
        return tempFieldIdentifier;
    }

    private String buildFilteringString(List<TableFilterField> filters){
        String paramName = "";
        StringBuilder filteringString = new StringBuilder();
        if(filters != null && !filters.isEmpty()) {
            for(TableFilterField filter : filters) {
                String tempFieldIdentifier = replaceColumnName(filter.getField());
                if(filter.getField() != null && filter.getFilter() != null && !filter.getFilter().equals("") ) {
                    if (filter.getField().equals("entryMethod")) {
                        TrailerEntryMethod trailerEntryMethod = TrailerEntryMethod.valueOf(Integer.valueOf(filter.getFilter().toString()));
                        filteringString.append(" AND " + tempFieldIdentifier + " IN (" + (trailerEntryMethod == TrailerEntryMethod.DETECTED ? "1)" : "2, 3)"));
                    }
                    else if (filter.getField().equals("assignedStatus")) {
                        TrailerAssignedStatus trailerAssignedStatus = TrailerAssignedStatus.valueOf(Integer.valueOf(filter.getFilter().toString()));
                        filteringString.append(" AND (" + tempFieldIdentifier + " IN (" + (trailerAssignedStatus == TrailerAssignedStatus.ASSIGNED ? "1, 2, 3))" : "0) OR " + tempFieldIdentifier + " is null)"));

                    }
                    else if(filter.getFilter() instanceof String){
                        paramName = "filter_"+filter.getField();
                        filteringString.append(" AND " + tempFieldIdentifier + " LIKE :" + paramName);
                        args.put(paramName, "%"+filter.getFilter()+"%");
                    }
                    else if(filter.getFilter() instanceof Range) {
                        Range range = (Range)filter.getFilter();
                        paramName = "filter_"+filter.getField()+"_min";
                        filteringString.append(" AND " + filter.getField() + " >= :" + paramName);
                        args.put(paramName, range.getMin());

                        paramName = "filter_"+filter.getField()+"_max";
                        filteringString.append(" AND " + filter.getField() + " < :" + paramName);
                        args.put(paramName, range.getMax());
                    }
                }
            }
        }
        return filteringString.toString();
    }
    private Map<String, Object> args = new HashMap<String, Object>();
    @Override
    public List<TrailerReportItem> getTrailerReportItemByGroupPaging(Integer acctID, List<Integer> groupIDList, PageParams pageParams) {


        StringBuilder sqlQuery = new StringBuilder(SELECT_ALL_FROM_TRAILER_PERFORMANCE);

        //TODO: remove before deploy... for dev debugging only
        for(Integer groupID: groupIDList){
            logger.debug("will search groupid: "+groupID);
        }
        String paramName = "filter_groupIDs"; //TODO: I think ryry did this becasue he didn't know how to use an IN here ... so he was anticipating having to have multiple group id params (one for each)
        sqlQuery.append(" where ((trailer.deviceID is null and trailer.acctID = " + acctID + ") or vehicle.groupID in ( :" + paramName + " )) and trailer.status != 3");

        args.put(paramName, groupIDList);

        /***FILTERING***/
        List<TableFilterField> filters = pageParams.getFilterList();
        sqlQuery.append(buildFilteringString(filters));

        /***SORTING***/
        if(pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty()) {
            sqlQuery.append(" ORDER BY " + replaceColumnName(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));
        }

        /***PAGING***/
        if(pageParams.getStartRow() != null && pageParams.getEndRow() != null) {
            sqlQuery.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow())+1) );
        }
        logger.debug("getTrailerReportItemByGroupPaging: " + sqlQuery.toString() );

        return getSimpleJdbcTemplate().query(sqlQuery.toString(), trailerReportItemRowMapper, args);
    }

    @Override
    public Integer getTrailerReportCount(Integer acctID, List<Integer> groupIDList, List<TableFilterField> tableFilterFieldList) {
        args = new HashMap<String, Object>();

        StringBuilder sqlQuery = new StringBuilder(SELECT_COUNT_FROM_TRAILER_PERFORMANCE);

        //TODO: remove before deploy... for dev debugging only
        for(Integer groupID: groupIDList){
            logger.debug("will search groupid: "+groupID);
        }
        String paramName = "filter_groupIDs";
        sqlQuery.append(" where ((trailer.deviceID is null and trailer.acctID = " + acctID + ") or vehicle.groupID in ( :" + paramName + " ))  and trailer.status != 3");
        args.put(paramName, groupIDList);

        /***FILTERING***/
        sqlQuery.append(buildFilteringString(tableFilterFieldList));

        logger.debug("getTrailerReportCount: " + sqlQuery.toString() );

        int result = getSimpleJdbcTemplate().queryForInt(sqlQuery.toString(), args);
        return result;
    }

    @Override
    public Boolean isValidTrailer(Integer acctID, String trailerName) {
        String sqlQuery = "select COUNT(*) from  trailer where acctID = " + acctID + " and name = '" + trailerName +"'  and status != 3";
        int result = getSimpleJdbcTemplate().queryForInt(sqlQuery);
        return result > 0;
    }


}
