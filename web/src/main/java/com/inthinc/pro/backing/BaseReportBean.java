package com.inthinc.pro.backing;

import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class BaseReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(BaseReportBean.class);

    protected String checkForRequestMap()
    {
        String searchFor = null;

        Map m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Iterator imap = m.entrySet().iterator();

        //if there is a map, the request came from the
        //  main menu search, so grab it
        while (imap.hasNext()) {
            Map.Entry entry = (Map.Entry) imap.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            //search parm
            if ( key.equalsIgnoreCase("searchFor") ) {              
                searchFor = value;    
            }
        }
        
        return searchFor;
    }
}
