package com.inthinc.pro.dao.util;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

public class UserLogUtil {
    private static Log log = null;
    private static Logger logger = Logger.getLogger(UserLogUtil.class);

    /**
     * Generates log messages for method calls, including the User who is currently making the call.
     * 
     * @param method
     * @param args
     * @param target
     */
    public static void logBeforeMethodWithUser(Method method, Object[] args, Object target) {
        log = LogFactory.getLog(target.getClass());

        // log to file
        String message = method.toGenericString() + " with param values (" + argsToString(args) + ") called by " + findUserName();
        log.info(message);// log using the class to which the method belongs
        logger.info(message);// specific to user.log

        // log to DB
        // TODO: jwimmer: follow up with DaveHarry and MattRaby about correct structure and procedure to get this into DB
        // UserLogItem userLogItem = new UserLogItem(method.toGenericString(), findUserName(), args, new Date());
        // UserLogItemJDBCDAO dao = new UserLogItemJDBCDAO();
        // dao.create(null, userLogItem);
    }

    /**
     * Determine the userName for the current user.
     * 
     * @return the current userName if available, else "noPersonFound"
     */
    private static String findUserName() {
        String userName;
        Object userObject = null;
        User user = null;
        try {
            userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = (User) userObject;
            userName = user.getUsername();
        } catch (NullPointerException npe) {
            userName = "noPersonFound";
            // do nothing... not being able to determine the user happens in a number of legitimate situations
        } catch (Exception e) {
            userName = "noPersonFound";
            log.error(e);
        }
        return userName;
    }

    /**
     * Generates a human readable version of the args Object[].
     * 
     * @param args
     * @return human readable version of the args Object[]
     */
    private static String argsToString(Object[] args) {
        StringBuffer params = new StringBuffer("");
        if (args != null) {
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
