package com.inthinc.pro.dao.report;

import java.util.List;

import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public interface TrailerReportDAO {
    public List<TrailerReportItem> getTrailerReportItemByGroupPaging(List<Integer> groupIDList, PageParams pageParams);
    public Integer getTrailerReportCount(List<Integer> groupIDList, List<TableFilterField> tableFilterFieldList);
}
