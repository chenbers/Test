package com.inthinc.pro.util;

import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ReportsUtil {
    
    public static Response checkParameters(Integer groupID, Date startDate, Date endDate, boolean ifta) {
        Response res = null;

        if( groupID == null || startDate == null || endDate == null ){
            res = Response.status(Status.BAD_REQUEST).build();
        }
        else if(endDate.before(startDate)) {
            res = Response.status(Status.BAD_REQUEST).build();
        }
        return res;
    }

}
