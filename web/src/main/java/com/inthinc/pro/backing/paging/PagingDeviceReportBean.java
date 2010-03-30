package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.util.MessageUtil;

public class PagingDeviceReportBean extends BasePagingReportBean<DeviceReportItem>
{
	private static final long serialVersionUID = 9116805820208771789L;


	private static final Logger logger = Logger.getLogger(PagingDeviceReportBean.class);

    private String filterStatus;
	private List<SelectItem> deviceStatuses;
	
    
    @Override
	public TableSortField getDefaultSort() {
		return new TableSortField(SortOrder.ASCENDING, "deviceName");
	}


    @Override
	protected ReportCriteria getReportCriteria()
    {
    	return getReportCriteriaService().getDevicesReportCriteria(getUser().getGroupID(), getLocale());
    }


	public List<SelectItem>  getDeviceStatuses() {
		if (deviceStatuses == null) {
			deviceStatuses = new ArrayList<SelectItem> ();
			SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
			blankItem.setEscape(false);
			deviceStatuses.add(blankItem);
	        for (DeviceStatus deviceStatus : EnumSet.allOf(DeviceStatus.class)) {
	        	if (deviceStatus.equals(DeviceStatus.DELETED))	// we don't show deleted ones
	        		continue;
	        	deviceStatuses.add(new SelectItem(deviceStatus.getCode().toString(), MessageUtil.getMessageString("status"+deviceStatus.getCode(), getLocale())));
	    	}
		}
	    
	    return deviceStatuses;
    }

    public String getFilterStatus() {
		return filterStatus;
	}


	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
}

