package com.inthinc.pro.iphone;
 
import java.io.IOException;
import java.util.Enumeration;

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

//System.out.println(userAgent);
//		userAgent="xx_iphone";		
//System.out.println(uri);


		if (uri.indexOf("j_spring_security_")>=0)
		{
			if(uri.indexOf("/iphone/")>=0)
			{
			    queryString="";
				for(Enumeration e=req.getParameterNames(); e.hasMoreElements(); )
				{
				 String paramName = (String)e.nextElement();

				 String paramValue = req.getParameter(paramName);
				 if (queryString.length()>0)
					 queryString+="&";
				 queryString+=paramName + "=" + paramValue;
				}


				System.out.println(queryString);
				uri = StringUtils.replace(uri, req.getContextPath() + "/iphone/", req.getContextPath() + "/");		
				if (StringUtils.isNotBlank(queryString)) {
					uri = uri + "?" + queryString;
				}
				res.sendRedirect(uri);
				return;							
			}			
		}
//		else if (userAgent.toLowerCase().indexOf("iphone")>=0 
//				&& uri.endsWith("home.faces"))
//		{
//			uri = StringUtils.replace(uri, ".faces", ".jsp");		
//			res.sendRedirect(uri);
//		}
		else if (userAgent.toLowerCase().indexOf("iphone")>=0 
			&& uri.indexOf("/iphone/")<0)
		{
			uri = StringUtils.replace(uri, req.getContextPath() + "/", req.getContextPath() + "/iphone/");		
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