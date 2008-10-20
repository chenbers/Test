package com.inthinc.pro.dao.hessian.proserver;

public interface ServiceCreator<T extends HessianService>
{
  T getService();
}
