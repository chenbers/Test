package com.inthinc.pro.backing;

import com.inthinc.pro.backing.listener.SearchChangeListener;

public class RedFlagsCountBean extends RedFlagsBean implements SearchChangeListener{

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

}
