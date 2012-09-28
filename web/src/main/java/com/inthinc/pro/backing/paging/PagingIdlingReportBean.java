package com.inthinc.pro.backing.paging;

import java.util.Date;
import java.util.TimeZone;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.model.provider.IdlingReportPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;
@KeepAlive
public class PagingIdlingReportBean extends BasePagingReportBean<IdlingReportItem>
{

	private static final long serialVersionUID = 5349999687948286628L;
	private static final Logger logger = Logger.getLogger(PagingIdlingReportBean.class);
	
    private static final TimeZone timeZone = TimeZone.getTimeZone("GMT");
    private static final DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
	private static final long ONE_MINUTE = 60000L;

    private String badDates;
    private Date startDate;
    private Date endDate;

    public Integer getTotalDrivers() {
		return ((IdlingReportPaginationTableDataProvider)getTableDataProvider()).getTotalDrivers();
	}

    public Integer getTotalEMUDrivers(){
		return ((IdlingReportPaginationTableDataProvider)getTableDataProvider()).getTotalDriversWithIdleStats();
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	public Date getEndDate()
	{
		return endDate;
	}
    
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
		initInterval();
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
		initInterval();
	}

	private Interval initInterval() {
		try {
			setBadDates(null);
	        if (startDate == null) {
	        	setBadDates(MessageUtil.getMessageString("noStartDate",getLocale()));
	        	return null;
	        }
	        if (this.endDate == null) {
	        	setBadDates(MessageUtil.getMessageString("noEndDate",getLocale()));
	        	return null;
	        }

	        DateMidnight start = new DateMidnight(new DateTime(startDate.getTime(), dateTimeZone), dateTimeZone);
			DateTime end = new DateMidnight(endDate.getTime(), dateTimeZone).toDateTime().plusDays(1).minus(ONE_MINUTE);
	        return new Interval(start, end);
		}
		catch (Exception e) {
			setBadDates(MessageUtil.getMessageString("endDateBeforeStartDate",getLocale()));
			return null;
		}
	}
	
    public void refreshAction() {
    	
		Interval interval = initInterval();
		if (interval != null) {
			((IdlingReportPaginationTableDataProvider)getTableDataProvider()).setInterval(interval);
			getTable().reset();
			
		}
    }
    
    @Override
    public void searchAction()
    {
    	setSearchFor(getSearchCoordinationBean().getSearchFor());
		Interval interval = initInterval();
		if (interval == null) {
	        startDate = new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone).toDate();
	        endDate = new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(ONE_MINUTE).toDate();
	        ((IdlingReportPaginationTableDataProvider)getTableDataProvider()).setInterval(initInterval());
		}
    	getTable().reset();
		getSearchCoordinationBean().setSearchFor("");
    }

    @Override
    public void init()
    {
        if ( startDate == null ) {
		startDate = new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone).toDate();
        }
        if ( endDate == null ) {
        endDate = new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(ONE_MINUTE).toDate();
        }
        ((IdlingReportPaginationTableDataProvider)getTableDataProvider()).setInterval(initInterval());

        super.init();
		
    }
	@Override
	public TableSortField getDefaultSort() {
		return new TableSortField(SortOrder.ASCENDING, "driverName");
	}

    @Override
	protected ReportCriteria getReportCriteria()
    {
    	ReportCriteria reportCriteria =  getReportCriteriaService().getIdlingReportCriteria(getTableDataProvider().getGroupID(), ((IdlingReportPaginationTableDataProvider)getTableDataProvider()).getInterval(), getLocale(), false);
    	int rowCount = this.getTableDataProvider().getRowCount();
    	reportCriteria.setMainDataset(getTableDataProvider().getItemsByRange(0, rowCount));
    	return reportCriteria;

    }
    
	public String getBadDates() {
		return badDates;
	}

	public void setBadDates(String badDates) {
		this.badDates = badDates;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}
}

