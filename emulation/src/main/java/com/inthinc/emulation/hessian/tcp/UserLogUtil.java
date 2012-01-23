package com.inthinc.emulation.hessian.tcp;

import java.lang.reflect.Method;

import com.inthinc.pro.automation.utils.MasterTest;

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
        
        MasterTest.print(message);// specific to user.log
    }


    /**
     * Generates a human readable version of the args Object[].
     * 
     * @param args
     * @return human readable version of the args Object[]
     */
    private static String argsToString(Object[] args) {
        StringBuffer params = new StringBuffer("");
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                params.append(", " + args[i]);
            }
            if (params.charAt(0) == ',') {
                params.delete(0, 1);
            }
        }
        return params.toString();
    }
}
