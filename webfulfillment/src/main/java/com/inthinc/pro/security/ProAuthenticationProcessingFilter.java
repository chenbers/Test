package com.inthinc.pro.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

public class ProAuthenticationProcessingFilter extends AuthenticationProcessingFilter
{
    private static final Logger logger = Logger.getLogger(ProAuthenticationProcessingFilter.class);

    public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
    	return super.attemptAuthentication(request);
    }

}
