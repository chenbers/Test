package com.inthinc.pro.dao.jdbc;

import static com.inthinc.pro.dao.util.NumberUtil.objectToInteger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.report.TrailerReportDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.User;
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
                    " , trailer.vehicleID" +
                    " , vehicle.name as vehicleName" +
                    " , trailer.driverID" +
                    " , concat(person.first,' ',person.middle,' ',person.last,' ',person.suffix) as driverName  " +
                    " , trailer.groupID " +
                    " , groups.name as groupName " +
                    " , trailer.pairingDate as pairingDate " +
                    " , trailer.entryDate as entryDate " +
                    " from trailer " +
                    " left outer join vehicle on trailer.vehicleID = vehicle.vehicleID " +
                    " left outer join driver on trailer.driverID = driver.driverID " +
                    " left outer join person on driver.personID = person.personID " +
                    " join groups on trailer.groupID = groups.groupID ";
    
    private static final String SELECT_COUNT_FROM_TRAILER_PERFORMANCE = "select COUNT(*) from  trailer " +
                    " left outer join vehicle on trailer.vehicleID = vehicle.vehicleID " +
                    " left outer join driver on trailer.driverID = driver.driverID " +
                    " left outer join person on driver.personID = person.personID " +
                    " join groups on trailer.groupID = groups.groupID ";
    
    
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
            trailerReportItem.setEntryMethod((Date)rs.getDate("pairingDate"), (Date)rs.getDate("entryDate"));
            trailerReportItem.setStatus(Status.valueOf(objectToInteger(rs.getObject("trailerStatus"))));
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
        replaceColumnNameMap.put("entryMethod", "trailer.pairingDate");
        replaceColumnNameMap.put("status", "trailer.status");
        replaceColumnNameMap.put("entryMethod", "if(trailer.pairingDate>coalesce(trailer.entryDate,FROM_UNIXTIME(0)), 1, if(trailer.entryDate>coalesce(trailer.pairingDate,FROM_UNIXTIME(0)),2, 0)) ");
        replaceColumnNameMap.put("assignedStatus", "trailer.driverID is not null || trailer.vehicleID is not null");
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
                    if(filter.getFilter() instanceof String){
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
    public List<TrailerReportItem> getTrailerReportItemByGroupPaging(List<Integer> groupIDList, PageParams pageParams) {
        
        
        StringBuilder sqlQuery = new StringBuilder(SELECT_ALL_FROM_TRAILER_PERFORMANCE);
        
        //TODO: remove before deploy... for dev debugging only
        for(Integer groupID: groupIDList){
            logger.debug("will search groupid: "+groupID);
        }
        String paramName = "filter_groupIDs"; //TODO: I think ryry did this becasue he didn't know how to use an IN here ... so he was anticipating having to have multiple group id params (one for each)
        sqlQuery.append(" where trailer.groupID in ( :" + paramName + " ) "); //TODO: to match vehicle this would need to be an IN??? from, AdminVehicleJDBCDAO "WHERE v.groupID in (:group_list) and v.status != 3";
        
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
    public Integer getTrailerReportCount(List<Integer> groupIDList, List<TableFilterField> tableFilterFieldList) {
        args = new HashMap<String, Object>();
        
        StringBuilder sqlQuery = new StringBuilder(SELECT_COUNT_FROM_TRAILER_PERFORMANCE);
        
        //TODO: remove before deploy... for dev debugging only
        for(Integer groupID: groupIDList){
            logger.debug("will search groupid: "+groupID);
        }
        String paramName = "filter_groupIDs";
        sqlQuery.append(" where trailer.groupID in ( :" + paramName + " ) ");
        args.put(paramName, groupIDList);

        /***FILTERING***/
        sqlQuery.append(buildFilteringString(tableFilterFieldList));
        
        logger.debug("getTrailerReportCount: " + sqlQuery.toString() );
        
        int result = getSimpleJdbcTemplate().queryForInt(sqlQuery.toString(), args);
        return result;
    }
    

}
