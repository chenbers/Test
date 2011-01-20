package com.inthinc.pro.backing.paging;

import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;

@KeepAlive
public class PagingVehicleReportBean extends BasePagingReportBean<VehicleReportItem>
{
	private static final long serialVersionUID = 9116805820208771789L;

	private static final Logger logger = Logger.getLogger(PagingVehicleReportBean.class);

	@Override
	public TableSortField getDefaultSort() {
		return new TableSortField(SortOrder.ASCENDING, "vehicleName");
	}

    @Override
	protected ReportCriteria getReportCriteria()
    {
    	ReportCriteria reportCriteria =  getReportCriteriaService().getVehicleReportCriteria(getUser().getGroupID(), Duration.TWELVE, getLocale(), false);
    	
    	TableSortField originalSort = getTableDataProvider().getSort();
    	List<TableFilterField> originalFilterList = getTableDataProvider().getFilters();
    	
    	Integer rowCount = getTableDataProvider().getRowCount();
    	
    	getTableDataProvider().getItemsByRange(0, rowCount);
		reportCriteria.setMainDataset(getTableDataProvider().getItemsByRange(0, rowCount));

		getTableDataProvider().setSort(originalSort);
    	getTableDataProvider().setFilters(originalFilterList);
    	
    	return reportCriteria;
    }


}

