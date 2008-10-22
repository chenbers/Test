package com.inthinc.pro.dao.mock.proserver;

import java.lang.reflect.ParameterizedType;

import com.inthinc.pro.dao.mock.service.impl.CentralServiceMockImpl;
import com.inthinc.pro.dao.mock.service.impl.SiloServiceMockImpl;
import com.inthinc.pro.dao.service.CentralService;
import com.inthinc.pro.dao.service.DAOService;
import com.inthinc.pro.dao.service.ServiceCreator;
import com.inthinc.pro.dao.service.SiloService;

public class MockServiceCreator<T extends DAOService> implements ServiceCreator<T>
{
    private T service;
    private Class<T> serviceType;
    
    

    @SuppressWarnings("unchecked")
    public MockServiceCreator()
    {
      serviceType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public T getService()
    {
      if (service == null)
      {
          if (serviceType.equals(CentralService.class))
          {
              service = (T) new CentralServiceMockImpl();
          }
          else if (serviceType.equals(SiloService.class))
          {
              service = (T) new SiloServiceMockImpl();
          }
      }
      return service;
    }
  }

