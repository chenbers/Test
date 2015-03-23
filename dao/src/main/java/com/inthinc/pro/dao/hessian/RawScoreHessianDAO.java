package com.inthinc.pro.dao.hessian;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.RawScoreDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;

public class RawScoreHessianDAO implements RawScoreDAO {

    private ReportService reportService;

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public Map<String, Object> getDPctByGT(Integer groupID, Integer duration, Integer metric) {
        try {
            return reportService.getDPctByGT(groupID, duration, metric);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getDScoreByDT(Integer driverID, Integer duration) {
        try {
            return reportService.getDScoreByDT(driverID, duration);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getDTrendByDTC(Integer driverID, Integer duration, Integer count) {
        try {
            return reportService.getDTrendByDTC(driverID, duration, count);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getDVScoresByGSE(Integer groupID, Long startDate, Long endDate) {
        try {
            return reportService.getDVScoresByGSE(groupID, startDate, endDate);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getDVScoresByGT(Integer groupID, Integer duration) {
        try {
            return reportService.getDVScoresByGT(groupID, duration);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getGDScoreByGSE(Integer groupID, Long startDate, Long endDate) {
        try {
            return reportService.getGDScoreByGSE(groupID, startDate, endDate);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getGDScoreByGT(Integer groupID, Integer duration) {
        try {
            return reportService.getGDScoreByGT(groupID, duration);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getGDTrendByGTC(Integer groupID, Integer duration, Integer count) {
        try {
            return reportService.getGDTrendByGTC(groupID, duration, count);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getSDScoresByGT(Integer groupID, Integer duration) {
        try {
            return reportService.getSDScoresByGT(groupID, duration);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getSDTrendsByGTC(Integer groupID, Integer duration, Integer count) {
        try {
            return reportService.getSDTrendsByGTC(groupID, duration, count);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getVDScoresByGT(Integer groupID, Integer duration) {
        try {
            return reportService.getVDScoresByGT(groupID, duration);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getVDScoresByGSE(Integer groupID, Long startDate, Long endDate) {
        try {
            return reportService.getVDScoresByGSE(groupID, startDate, endDate);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getVScoreByVT(Integer vehicleID, Integer duration) {
        try {
            return reportService.getVScoreByVT(vehicleID, duration);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count) {
        try {
            return reportService.getVTrendByVTC(vehicleID, duration, count);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

}
