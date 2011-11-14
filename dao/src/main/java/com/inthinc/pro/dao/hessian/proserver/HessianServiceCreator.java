package com.inthinc.pro.dao.hessian.proserver;

import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;

public class HessianServiceCreator<T extends HessianService> implements ServiceCreator<T>
{
  private Integer port;
  private String host;
  private T service;
  private Class<T> serviceType;
  private static final HessianTCPProxyFactory factory = new HessianTCPProxyFactory();

  @SuppressWarnings("unchecked")
  public HessianServiceCreator(String host, Integer port)
  {
    this.host = host;
    this.port = port;
    serviceType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  public T getService()
  {
    if (service == null)
    {
      try
      {
        service = serviceType.cast(factory.create(serviceType, host, port));
      }
      catch (MalformedURLException e)
      {
        throw new ProDAOException(e);
      }
    }
    return service;
  }
  public Integer getPort() {
      return port;
  }

  public String getHost() {
      return host;
  }

}
