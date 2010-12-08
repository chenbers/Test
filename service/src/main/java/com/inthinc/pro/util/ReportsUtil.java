package com.inthinc.pro.util;

import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;

@Component
public class ReportsUtil {
    
    @Autowired
    private GroupDAOAdapter groupDAOAdapter;
    
    public Response checkParameters(Integer groupID, Date startDate, Date endDate) {
        Response res = null;

        if( groupID == null || startDate == null || endDate == null 
                  || groupID < 0 || endDate.before(startDate) ){
           
            res = Response.status(Status.BAD_REQUEST).build();
        }
        else {
            try {
                Group group = groupDAOAdapter.findByID(groupID);
                // Group does not exist at all in the backend
                if (group == null) {
                    res = Response.status(Status.NOT_FOUND).build();
                }
            }
            catch(AccessDeniedException e){
                res = Response.status(Status.FORBIDDEN).build();
            }
        }
            
        return res;
    }

}
