package com.inthinc.pro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class HTTPSRedirect
 */
public class HTTPSRedirectFilter implements Filter {
    private static Logger logger = Logger.getLogger(HTTPSRedirectFilter.class);

    private boolean redirectToHTTPS = false;

    /**
     * Default constructor.
     */
    public HTTPSRedirectFilter() {
    // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (redirectToHTTPS) {
            final HttpServletRequest httpReq = (HttpServletRequest) request;
            HttpServletResponse wrappedResp = new HttpServletResponseWrapper((HttpServletResponse) response) {
              public void sendRedirect(String location) throws IOException {
                  logger.debug("Sending a Redirect to: " + "https://" + httpReq.getServerName() + location);
                  super.sendRedirect("https://" + httpReq.getServerName() + location);
              }
            };
            chain.doFilter(request,wrappedResp);
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    // TODO Auto-generated method stub
    }

    public boolean isRedirectToHTTPS() {
        return redirectToHTTPS;
    }

    public void setRedirectToHTTPS(boolean redirectToHTTPS) {
        this.redirectToHTTPS = redirectToHTTPS;
    }

}
