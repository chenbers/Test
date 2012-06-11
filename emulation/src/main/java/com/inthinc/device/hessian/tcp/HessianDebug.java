package com.inthinc.device.hessian.tcp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.inthinc.pro.automation.logging.Log;

public class HessianDebug {
    public static boolean debugIn = false;
    public static boolean debugOut = false;
    public static boolean debugRequest = false;
    public static String debugInFileNamePrefix = "c:/debugHessianReq";
    public static String debugOutFileNamePrefix = "c:/debugHessianOut";

    public static void debugInput(String methodName, Object[] args, HessianProxyFactory factory) throws IOException {

        Log.debug("methodName: " + methodName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(debugInFileNamePrefix + methodName + ".dmp");
        } catch (FileNotFoundException e) {
            Log.error(e);
        }
        AbstractHessianOutput debugOut = factory.getHessianOutput(fos);
        debugOut.call(methodName, args);
        debugOut.flush();
    }

    public static InputStream debugOutput(String methodName, InputStream ins) {

        String fileName = debugOutFileNamePrefix + methodName + ".dmp";
        BufferedInputStream is = new BufferedInputStream(ins);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            int c = is.read();
            while (c >= 0) {
                c = is.read();
                fos.write(c);

            }
            is.close();
            fos.close();
        } catch (IOException e) {
            Log.error(e);
        }

        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            Log.error(e);
        }
        return null;

    }

    @SuppressWarnings("unchecked")
    public static void debugRequest(String methodName, Object[] args) {

        Log.debug("methodName: " + methodName);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    dumpParams((Map<String, Object>) args[i]);
                } else if (args[i] != null) {
                    Log.debug("arg[" + i + "] " + args[i].toString());
                }
            }
        }
    }

    public static void dumpParams(Map<String, Object> params) {
        for (String param : params.keySet()) {
            Object value = params.get(param);
            Log.debug(param + " = " + ((value == null) ? "<null>" : value.toString()));
        }
    }

}
