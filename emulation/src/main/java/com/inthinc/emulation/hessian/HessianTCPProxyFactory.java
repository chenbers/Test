package com.inthinc.emulation.hessian;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;

public class HessianTCPProxyFactory extends HessianProxyFactory
{

    public Object create(Class<?> api, String hostName, int port) throws MalformedURLException
    {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (api == null)
            throw new NullPointerException("api must not be null for HessianTCPProxyFactory.create()");

        InvocationHandler handler = new HessianTCPProxy(this, hostName, port);

        return Proxy.newProxyInstance(loader, new Class[] { api, HessianRemoteObject.class }, handler);
    }

}
