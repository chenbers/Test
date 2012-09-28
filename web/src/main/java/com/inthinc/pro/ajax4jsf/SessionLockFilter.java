package com.inthinc.pro.ajax4jsf;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ajax4jsf.Filter;
import org.apache.log4j.Logger;

public class SessionLockFilter extends Filter {
    private static final Logger logger = Logger.getLogger(SessionLockFilter.class);

    protected void handleRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session == null) {
            logger.debug("No session, no lock");
            super.handleRequest(request, response, chain);
            return;
        }
        String sessionId = session.getId();
        if (sessionId == null) {
            logger.debug("No session id, no lock");
            super.handleRequest(request, response, chain);
            return;
        }
        synchronized (sessionId) {
            try {
                logger.debug("acquired lock on {}");
                super.handleRequest(request, response, chain);
            } finally {
                logger.debug("released lock on {}");
            }
        }
    }
}
