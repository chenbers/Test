package com.inthinc.pro.util;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class DebugUtil
{
    private static final Logger logger = Logger.getLogger(DebugUtil.class);
    
    public static void dumpRequestParameterMap()
    {
        Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        if (paramMap == null)
        {
            logger.debug("Request Param map is null");
            return;
        }
        
        logger.debug("---- Request param map -----");
        for (Map.Entry<String, String> entry : paramMap.entrySet())
        {
            logger.debug(entry.getKey() + " = " + entry.getValue());
        }
        logger.debug("----------------------------");
    }
    public static void dumpRequestMap()
    {
        Map<String, Object> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        
        if (paramMap == null)
        {
            logger.debug("Request Param map is null");
            return;
        }
        
        logger.debug("---- Request map -----");
        for (Map.Entry<String, Object> entry : paramMap.entrySet())
        {
            logger.debug(entry.getKey() + " = " + entry.getValue());
        }
        logger.debug("----------------------------");
    }


}    
