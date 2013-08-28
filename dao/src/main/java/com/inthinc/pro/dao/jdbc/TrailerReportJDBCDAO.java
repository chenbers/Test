package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.report.TrailerReportDAO;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;

public class TrailerReportJDBCDAO extends SimpleJdbcDaoSupport implements TrailerReportDAO{
    private static final Logger logger = Logger.getLogger(TrailerReportJDBCDAO.class);
    
    private static final String SELECT_ALL_FROM_TRAILER_PERFORMANCE = "select * from  trailerPerformance";
    
    private static final String SELECT_COUNT_FROM_TRAILER_PERFORMANCE = "select COUNT(*) from  trailerPerformance";
    
    private static ParameterizedRowMapper<TrailerReportItem> trailerReportItemRowMapper = new ParameterizedRowMapper<TrailerReportItem>() {
        @Override
        public TrailerReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrailerReportItem trailerReportItem = new TrailerReportItem();
            trailerReportItem.setTrailerID(rs.getInt("trailerID"));
            trailerReportItem.setTrailerName(rs.getString("trailerName"));
            trailerReportItem.setTrailerYMM(rs.getString("trailerYMM"));
            trailerReportItem.setVehicleID(rs.getInt("vehicleID"));
            trailerReportItem.setVehicleName(rs.getString("vehicleName"));
            trailerReportItem.setDriverID(rs.getInt("driverID"));
            trailerReportItem.setDriverName(rs.getString("driverName"));
            trailerReportItem.setGroupID(rs.getInt("groupID"));
            trailerReportItem.setGroupName(rs.getString("groupName"));
            trailerReportItem.setMilesDriven(rs.getInt("milesDriven"));
            trailerReportItem.setOdometer(rs.getInt("odometer"));
            trailerReportItem.setOverallScore(rs.getInt("overallScore"));
            trailerReportItem.setSpeedScore(rs.getInt("speedScore"));
            trailerReportItem.setStyleScore(rs.getInt("styleScore"));
            return trailerReportItem;
        }
    };
    
    public List<TrailerReportItem> getTrailerReportItemByGroupPaging(Integer groupID, PageParams pageParams) {
        Map<String, Object> args = new HashMap<String, Object>();
        
        StringBuilder sqlQuery = new StringBuilder(SELECT_ALL_FROM_TRAILER_PERFORMANCE);
        
        String paramName = "filter_"+groupID+"_groupID";
        sqlQuery.append(" where groupID = :" + paramName);
        args.put(paramName, groupID);
        
        /***FILTERING***/
        List<TableFilterField> filters = pageParams.getFilterList();
        if(filters != null && !filters.isEmpty()) {
            for(TableFilterField filter : filters) {
                if(filter.getField() != null && filter.getFilter() != null ) {
                    if(filter.getFilter() instanceof String){
                        paramName = "filter_"+filter.getField();
                        sqlQuery.append(" AND " + filter.getField() + " LIKE :" + paramName);
                        args.put(paramName, "%"+filter.getFilter()+"%");
                    }
                    else if(filter.getFilter() instanceof Range) {
                        Range range = (Range)filter.getFilter();
                        paramName = "filter_"+filter.getField()+"_min";
                        sqlQuery.append(" AND " + filter.getField() + " >= :" + paramName);
                        args.put(paramName, range.getMin());
                        
                        paramName = "filter_"+filter.getField()+"_max";
                        sqlQuery.append(" AND " + filter.getField() + " < :" + paramName);
                        args.put(paramName, range.getMax());
                    }
                }
            }
        }
        
        /***SORTING***/
        if(pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty()) {
            sqlQuery.append(" ORDER BY " + pageParams.getSort().getField() + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));
        }
        
        /***PAGING***/
        if(pageParams.getStartRow() != null && pageParams.getEndRow() != null) {
            sqlQuery.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow())+1) );
        }
        logger.debug("getTrailerReportItemByGroupPaging: " + sqlQuery.toString() );
        return getSimpleJdbcTemplate().query(sqlQuery.toString(), trailerReportItemRowMapper, args);
    }

    @Override
    public Integer getTrailerReportCount(Integer groupID, List<TableFilterField> tableFilterFieldList) {
        Map<String, Object> args = new HashMap<String, Object>();
        
        StringBuilder sqlQuery = new StringBuilder(SELECT_COUNT_FROM_TRAILER_PERFORMANCE);
        
        String paramName = "filter_"+groupID+"_groupID";
        sqlQuery.append(" where groupID = :" + paramName);
        args.put(paramName, groupID);

        /***FILTERING***/
        if(tableFilterFieldList != null && !tableFilterFieldList.isEmpty()) {
            for(TableFilterField filter : tableFilterFieldList) {
                if(filter.getField() != null && filter.getFilter() != null ) {
                    if(filter.getFilter() instanceof String){
                        paramName = "filter_"+filter.getField();
                        sqlQuery.append(" AND " + filter.getField() + " LIKE :" + paramName);
                        args.put(paramName, "%"+filter.getFilter()+"%");
                    }
                    else if(filter.getFilter() instanceof Range) {
                        Range range = (Range)filter.getFilter();
                        paramName = "filter_"+filter.getField()+"_min";
                        sqlQuery.append(" AND " + filter.getField() + " >= :" + paramName);
                        args.put(paramName, range.getMin());
                        
                        paramName = "filter_"+filter.getField()+"_max";
                        sqlQuery.append(" AND " + filter.getField() + " < :" + paramName);
                        args.put(paramName, range.getMax());
                    }
                }
            }
        }
        
        logger.debug("getTrailerReportCount: " + sqlQuery.toString() );
        
        return getSimpleJdbcTemplate().queryForInt(sqlQuery.toString(), args);
    }
    
}
