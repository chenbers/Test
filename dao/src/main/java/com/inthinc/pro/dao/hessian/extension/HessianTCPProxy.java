package com.inthinc.pro.dao.hessian.extension;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CommonsLogWriter;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.HessianDebugInputStream;
import com.caucho.hessian.io.HessianDebugOutputStream;
import com.caucho.hessian.io.HessianProtocolException;
import com.inthinc.pro.dao.hessian.exceptions.HessianExceptionConverter;
import com.inthinc.pro.dao.util.UserLogUtil;

public class HessianTCPProxy implements InvocationHandler
{
    
    private static int count;
    public static int getCount(){
        return count;
    }
    
    private static final Log log = LogFactory.getLog(HessianTCPProxy.class);

//    private static final Log profileLog = LogFactory.getLog(HessianTCPProxy.class);

    static class ResultInputStream extends InputStream
    {
        private InputStream _connIs;

        private InputStream _hessianIs;

        private AbstractHessianInput _in;

        private Socket _socket;

        ResultInputStream(Socket socket, InputStream is, AbstractHessianInput in, InputStream hessianIs)
        {
            _socket = socket;
            _connIs = is;
            _in = in;
            _hessianIs = hessianIs;
        }

        public void close() throws IOException
        {
            Socket socket = _socket;
            _socket = null;

            InputStream connIs = _connIs;
            _connIs = null;

            AbstractHessianInput in = _in;
            _in = null;

            InputStream hessianIs = _hessianIs;
            _hessianIs = null;

            if (hessianIs != null)
                hessianIs.close();

            if (in != null)
            {
                in.completeReply();
                in.close();
            }

            if (connIs != null)
            {
                connIs.close();
            }

            if (socket != null)
            {
                socket.close();
            }
        }

        public int read() throws IOException
        {
            if (_hessianIs != null)
            {
                int value = _hessianIs.read();

                if (value < 0)
                    close();

                return value;
            }
            else
                return -1;
        }

        public int read(byte[] buffer, int offset, int length) throws IOException
        {
            if (_hessianIs != null)
            {
                int value = _hessianIs.read(buffer, offset, length);

                if (value < 0)
                    close();

                return value;
            }
            else
                return -1;
        }
    }

    // private static final Logger log = Logger.getLogger(HessianTCPProxy.class);

    private HessianProxyFactory _factory;
    private String hostName;

    private int port;

    public HessianTCPProxy(HessianProxyFactory factory, String hostName, int port)
    {
        _factory = factory;
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * Handles the object invocation.
     * 
     * @param proxy
     *            the proxy object to invoke
     * @param method
     *            the method to call
     * @param args
     *            the arguments to the proxy object
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        UserLogUtil.logBeforeMethodWithUser(method, args, proxy);
        String methodName = method.getName();
//        Date traceStartTime = null;
//        if (profileLog.isInfoEnabled())
//        {
//        	traceStartTime = new Date();
//        }
        
        InputStream is = null;
        Socket socket = null;

        try
        {
            socket = sendRequest(methodName, args);
            is = socket.getInputStream();

            if (log.isTraceEnabled())
                is = new HessianDebugInputStream(is, new PrintWriter(new CommonsLogWriter(log)));

            AbstractHessianInput in = _factory.getHessianInput(is);
            in.startReply();
            Object value = in.readObject();

            if (method.getReturnType().isInstance(value))
            {
                in.completeReply();

                
//                if (profileLog.isInfoEnabled())
//                {
//                	profileLog.info(method.getName() + " " + (new Date().getTime() - traceStartTime.getTime()) + " ms");
//                }

                return value;

            }

            // If return value is an integer, then we have an error condition
            if (value instanceof Integer && (Integer)value != 0)
            {
                throw HessianExceptionConverter.convert(methodName, args, (Integer) value);
            }

            return null;

        }
        catch (HessianProtocolException e)
        {
            log.error(e);
            throw new HessianRuntimeException(e);
        }
        finally
        {
            try
            {
                if (is != null)
                    is.close();
            }
            catch (Throwable e)
            {
                log.error(e);
            }

            try
            {
                if (socket != null)
                    socket.close();
            }
            catch (Throwable e)
            {
                log.error(e);
            }
        }
    }
    
   

    protected Socket sendRequest(String methodName, Object[] args) throws IOException
    {
        Socket socket = null;
        socket = new Socket(InetAddress.getByName(hostName), port);
        count ++;
        socket.setSoLinger(false, 0);

        OutputStream os = null;
        os = socket.getOutputStream();

        if (log.isTraceEnabled())
            os = new HessianDebugOutputStream(os, new PrintWriter(new CommonsLogWriter(log)));

        AbstractHessianOutput out = _factory.getHessianOutput(os);
        out.call(methodName, args);
        out.flush();

        return socket;
    }

}
