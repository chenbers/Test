package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.listener.SearchChangeListener;
import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.RedFlag;

public class RedFlagsCountBean extends RedFlagsBean implements SearchChangeListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
    public void initBean()
    {
        super.initBean();
        searchCoordinationBean.addSearchChangeListener(this);
    }
	@Override
	public synchronized void searchChanged() {

        if (!searchCoordinationBean.isGoodSearch())
        {
            setCategoryFilter(null);
            setEventFilter(null);
        }

		filterTableData();
	}
	
	@Override
	protected void initTableData()
    {
        setFilteredTableData(null);
        
        List<RedFlag> redFlagList = getRedFlagDAO().getRedFlags(getEffectiveGroupId(), 7, RedFlagDAO.EXCLUDE_FORGIVEN);
        List<RedFlagReportItem> redFlagReportItemList = new ArrayList<RedFlagReportItem>();
        
        addDrivers(redFlagList);
        addVehicles(redFlagList);
        
        for (RedFlag redFlag : redFlagList)
        {
            RedFlagReportItem item = new RedFlagReportItem(redFlag, getGroupHierarchy(),getMeasurementType());
            
            redFlagReportItemList.add(item);
        }
        
        setTableData(redFlagReportItemList);
    }
}
