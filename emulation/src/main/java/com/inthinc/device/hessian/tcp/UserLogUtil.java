package com.inthinc.device.hessian.tcp;

import java.lang.reflect.Method;
import java.util.List;

import android.util.Log;

import com.inthinc.device.emulation.notes.DeviceNote;

public class UserLogUtil {

    /**
     * Generates log messages for method calls, including the User who is currently making the call.
     * 
     * @param method
     * @param args
     * @param target
     */
    public static void logBeforeMethodWithUser(Method method, Object[] args, Object target) {
        String message = method.getName()+"("+argsToString(args)+");";
        
        Log.i(message);// specific to user.log
    }


    /**
     * Generates a human readable version of the args Object[].
     * 
     * @param args
     * @return human readable version of the args Object[]
     */
    @SuppressWarnings("unchecked")
	private static String argsToString(Object[] args) {
        StringBuffer params = new StringBuffer("");
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
            	if (args[i] instanceof List<?>){
            		try {
            			params.append(", ");
	            		for (byte[] bits: (List<byte[]>)args[i]){
	            			params.append(DeviceNote.unPackageS((byte[]) bits));
	            			params.append(", ");
	            		}
	            		params.append(") ");
            		} catch (Exception e){
                		params.append(args[i]);
            		}
            	} else {
            		params.append(", " + args[i]);
            	}
            }
            if (params.charAt(0) == ',') {
                params.delete(0, 1);
            }
        }
        return params.toString();
    }
    
    
}
