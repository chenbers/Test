package com.inthinc.pro.model.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.SiloDAO;
import com.inthinc.pro.model.silo.SiloDef;

public class Silos {
    private SiloDAO siloDAO;
    
    private static Map<Integer, SiloDef> siloIDMap;
    private static Map<String, SiloDef> siloURLMap;
    

    public Silos()
    {
    }
    
    
    public void init()
    {
        List<SiloDef> siloList = siloDAO.getSiloDefs();
        siloIDMap = new HashMap<Integer, SiloDef>();
        siloURLMap = new HashMap<String, SiloDef>();

        for (SiloDef siloDef : siloList)
        {
               siloIDMap.put(siloDef.getSiloID(), siloDef);
               siloURLMap.put(siloDef.getUrl(), siloDef);
        }
    }
    
    public static List<SiloDef> getSiloList()
    {
        return (siloIDMap == null) ? null : Collections.list(Collections.enumeration(siloIDMap.values()));
    }
    
    public static SiloDef getSiloById(Integer id)
    {
        return (siloIDMap == null) ? null : siloIDMap.get(id);
    }

    public static SiloDef getSiloByUrl(String url)
    {
        return (siloURLMap == null) ? null : siloURLMap.get(url);
    }
    


    public SiloDAO getSiloDAO() {
        return siloDAO;
    }

    public void setSiloDAO(SiloDAO siloDAO) {
        this.siloDAO = siloDAO;
    }
    
}
