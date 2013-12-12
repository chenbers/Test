package com.inthinc.pro.dao.mock;

import java.util.List;

import com.inthinc.pro.dao.report.TrailerReportDAO;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class MockTrailerReportDAO implements TrailerReportDAO{

    @Override
    public List<TrailerReportItem> getTrailerReportItemByGroupPaging(Integer acctID, List<Integer> groupIDList, PageParams pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getTrailerReportCount(Integer acctID, List<Integer> groupIDList, List<TableFilterField> tableFilterFieldList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isValidTrailer(Integer acctID, String trailerName) {
        // TODO Auto-generated method stub
        return null;
    }

}
