package com.inthinc.device.hessian.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.HessianProtocolException;

public class HessianTCPProxy implements InvocationHandler {

	// private static final Log profileLog =
	// LogFactory.getLog(HessianTCPProxy.class);

	static class ResultInputStream extends InputStream {
		private InputStream _connIs;

		private InputStream _hessianIs;

		private AbstractHessianInput _in;

		private Socket _socket;

		ResultInputStream(Socket socket, InputStream is,
				AbstractHessianInput in, InputStream hessianIs) {
			_socket = socket;
			_connIs = is;
			_in = in;
			_hessianIs = hessianIs;
		}

		public void close() throws IOException {
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

			if (in != null) {
				in.completeReply();
				in.close();
			}

			if (connIs != null) {
				connIs.close();
			}

			if (socket != null) {
				socket.close();
			}
		}

		public int read() throws IOException {
			if (_hessianIs != null) {
				int value = _hessianIs.read();

				if (value < 0)
					close();

				return value;
			} else
				return -1;
		}

		public int read(byte[] buffer, int offset, int length)
				throws IOException {
			if (_hessianIs != null) {
				int value = _hessianIs.read(buffer, offset, length);

				if (value < 0)
					close();

				return value;
			} else
				return -1;
		}
	}

	// private static final Logger log =
	// Logger.getLogger(HessianTCPProxy.class);

	private HessianProxyFactory _factory;
	private String hostName;

	private int port;

	public HessianTCPProxy(HessianProxyFactory factory, String hostName,
			int port) {
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
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		UserLogUtil.logBeforeMethodWithUser(method, args, proxy);
		String methodName = method.getName();
		// Date traceStartTime = null;
		// if (profileLog.isInfoEnabled())
		// {
		// traceStartTime = new Date();
		// }

		InputStream is = null;
		Socket socket = null;

		try {
			socket = sendRequest(methodName, args);
			is = socket.getInputStream();

			AbstractHessianInput in = _factory.getHessianInput(is);
			in.startReply();
			Object value = in.readObject();

			if (method.getReturnType().isInstance(value)) {
				in.completeReply();
				return value;

			}

			// If return value is an integer, then we have an error condition
			if (value instanceof Integer && !value.equals(0)) {
				throw HessianExceptionConverter.convert(methodName, args,
						(Integer) value);
			}

			return null;

		} catch (HessianProtocolException e) {
			Log.e(null, e);
			throw new HessianRuntimeException(e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Throwable e) {
				Log.e(null, e);
			}

			try {
				if (socket != null)
					socket.close();
			} catch (Throwable e) {
				Log.e(null, e);
			}
		}
	}

	protected Socket sendRequest(String methodName, Object[] args)
			throws IOException {
		Socket socket = null;
		socket = new Socket(InetAddress.getByName(hostName), port);

		OutputStream os = null;
		os = socket.getOutputStream();

		AbstractHessianOutput out = _factory.getHessianOutput(os);
		out.call(methodName, args);
		out.flush();

		return socket;
	}

}
