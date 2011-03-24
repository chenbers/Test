package com.inthinc.pro.spring.test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.context.support.GenericWebApplicationContext;

public class WebSessionContextLoader extends WebContextLoader {
    
    @Override
    protected void customizeContext(final GenericWebApplicationContext context) 
    {
        MockServletContext servlet = new MockServletContext();
        context.setServletContext(servlet);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    
        context.refresh();
        context.getBeanFactory().registerScope("session", new SessionScope());
    }
}
