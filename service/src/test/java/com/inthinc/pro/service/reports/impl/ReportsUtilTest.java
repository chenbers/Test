package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

import mockit.Mocked;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.util.ReportsUtil;

public class ReportsUtilTest extends BaseUnitTest {
    
    @Autowired
    @Mocked
    private GroupDAOAdapter groupDAOAdapter;
    
    private ReportsUtil serviceSUT = new ReportsUtil();

    private Integer expectedGroupID = 1504;
    
    @Test
    public void checkParametersWithBadInputTestNullGroup() {
           
        Response response = serviceSUT.checkParameters(null, buildDateFromString("20090101"), buildDateFromString("20100101"));
        
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void checkParametersWithBadInputTestNullStartDate() {
           
        Response response = serviceSUT.checkParameters(expectedGroupID, null, buildDateFromString("20100101"));
        
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void checkParametersWithBadInputTestNullEndDate() {
           
        Response response = serviceSUT.checkParameters(expectedGroupID,  buildDateFromString("20100101"),null);
        
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void checkParametersWithBadInputTestStartDateBiggerThanEndDate() {
           
        Response response = serviceSUT.checkParameters(expectedGroupID,  buildDateFromString("20100101"), buildDateFromString("20090101"));
        
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void checkParametersWithBadInputTestNegativeGroupID() {
           
        Response response = serviceSUT.checkParameters(-expectedGroupID,  buildDateFromString("20090101"), buildDateFromString("20100101"));
        
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }
    
    private Date buildDateFromString(String strDate) {
        DateFormat df = new SimpleDateFormat(IFTAServiceImpl.getSimpleDateFormat()); 
        try
        {
            Date convertedDate = df.parse(strDate);           
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
