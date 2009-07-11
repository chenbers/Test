package com.inthinc.pro.backing;

public class RedFlagsCountBean extends RedFlagsBean {

    @Override
    public void initBean()
    {
        super.initBean();
        searchCoordinationBean.addSearchChangeListener(this);
    }

}
