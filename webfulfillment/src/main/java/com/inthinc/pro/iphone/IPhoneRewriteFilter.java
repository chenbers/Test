package com.inthinc.pro.iphone;
 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
 
import org.apache.commons.lang.StringUtils;
 
 
public class IPhoneRewriteFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private FilterConfig filterConfig;
	public void destroy() {
	}
 
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		String queryString = req.getQueryString();
		String userAgent = req.getHeader("USER-AGENT");
		
		if (userAgent.toLowerCase().indexOf("iphone")>=0 
//		if (true
		&& uri.indexOf("/secured/")>=0)
		{
			uri = StringUtils.replace(uri, "/secured/", "/iphone/");		
			if (StringUtils.isNotBlank(queryString)) {
				uri = uri + "?" + queryString;
			}
			res.sendRedirect(uri);
			return;			
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
 
}