package com.inthinc.pro.security;

import java.io.IOException;

import javax.faces.render.ResponseStateManager;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.ajax4jsf.renderkit.AjaxContainerRenderer;
import org.apache.log4j.Logger;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;
import org.springframework.security.util.PortResolverImpl;

public class ProAuthenticationProcessingFilterEntryPoint extends
		AuthenticationProcessingFilterEntryPoint {
    
	private static final Logger logger = Logger.getLogger(ProAuthenticationProcessingFilterEntryPoint.class);
	/*
	 * This class is overridden in order to fix some issues with bookmarking  that occurred when a user's session timed out 
	 * and they had to log in again.  com.sun.faces.lifecycle.RestoreViewPhase -- execute was throwing a ViewExpiredException because it
	 * was trying to restore the saved view from the session (postback), but the view had already been wiped out when the session was recreated on login.
	 * By removing some of the request params that triggered the postback code, it did not try to do this and instead reloaded the page. 
	 * (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#commence(javax.servlet.ServletRequest, javax.servlet.ServletResponse, org.springframework.security.AuthenticationException)
	 */
    
	@SuppressWarnings("serial")
	@Override
	public void commence(ServletRequest request, ServletResponse response,
			org.springframework.security.AuthenticationException authException) throws IOException,
			ServletException {
		super.commence(request, response, authException);

		if (request instanceof HttpServletRequest) {

			if (isAjaxRequest(request) || isPostback(request)) {
				logger.debug("AJAX REQUEST IS TRUE");
				HttpServletRequest httpRequest = (HttpServletRequest) request;

				SavedRequest savedRequest = new SavedRequest(httpRequest,
						new PortResolverImpl()) {
					@Override
					public String[] getParameterValues(String name) {
						logger.debug("getParameterValues name " + name);
						if (AjaxContainerRenderer.AJAX_PARAMETER_NAME.equals(name) ||
								ResponseStateManager.VIEW_STATE_PARAM.equals(name)) {
							logger.debug(" -- returns NULL");
							return null;
						}
						logger.debug(" -- returns " + super.getParameterValues(name));
						return super.getParameterValues(name);
					}
				};
				httpRequest.getSession().setAttribute(
								AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY,
								savedRequest);
			}

		}
	}

	private boolean isPostback(ServletRequest request) {
		try {
			return null != request.getParameter(ResponseStateManager.VIEW_STATE_PARAM);
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean isAjaxRequest(ServletRequest request) {
		try {
			return null != request.getParameter(AjaxContainerRenderer.AJAX_PARAMETER_NAME);
		} catch (Exception e) {
			return false;
		}
	}

}
