package com.inthinc.pro.dao.hessian.mapper;

import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.DriverReportItem;

public class DriverPerformanceMapper extends AbstractMapper {
    private static final Logger logger = Logger.getLogger(DriverPerformanceMapper.class);
    private DriverDAO driverDAO;
    @Override
    public <E> E convertToModelObject(Map<String, Object> map, Class<E> type) {
        logger.debug("public <E> E convertToModelObject(Map<String, Object> "+map+", Class<E> "+type+")");
        if (map == null)
            return null;
        
        if(type == DriverReportItem.class){
            DriverReportItem result = (DriverReportItem) type.cast(super.convertToModelObject(map, type));
            result.setStatus(driverDAO.findByID(result.getDriverID()).getStatus());
            return (E) result;
        } else {
            return super.convertToModelObject(map, type);
        }
    }
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
}
