package com.inthinc.pro.dao.mock.proserver;

import java.lang.reflect.ParameterizedType;

import com.inthinc.pro.dao.hessian.proserver.HessianService;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.hessian.proserver.ServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

public class MockServiceCreator<T extends HessianService> implements ServiceCreator<T>
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
          if (serviceType.equals(SiloService.class))
          {
              service = (T) new SiloServiceMockImpl();
          }
          else if (serviceType.equals(ReportService.class))
          {
              service = (T) new ReportServiceMockImpl();
          }
      }
      return service;
    }
  }

