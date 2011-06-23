package com.inthinc.pro.service.exceptions;

import java.util.Date;

public class BadDateRangeException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Date startDate;
    private Date endDate;
    
    public BadDateRangeException(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return "Start date (" + startDate + ") can't be greater than end date (" + endDate + ").";
    }
}
