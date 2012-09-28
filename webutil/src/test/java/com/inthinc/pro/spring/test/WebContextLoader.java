package com.inthinc.pro.spring.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.support.GenericWebApplicationContext;

// the classes in this package basically just let us load session scoped beans from the application context during our unit tests

public class WebContextLoader extends AbstractContextLoader
{

    private static final Logger logger = Logger.getLogger(WebContextLoader.class);

    public final ConfigurableApplicationContext loadContext(final String... locations) throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("Loading ApplicationContext for locations [" + locations + "].");
        }

        GenericWebApplicationContext context = new GenericWebApplicationContext();
        customizeBeanFactory(context.getDefaultListableBeanFactory());
        createBeanDefinitionReader(context).loadBeanDefinitions(locations);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
        customizeContext(context);

        context.registerShutdownHook();
        return context;
    }

    protected void customizeBeanFactory(final DefaultListableBeanFactory beanFactory)
    {
        /* no-op */
    }

    protected void customizeContext(final GenericWebApplicationContext context)
    {
        /* refresh must be called when customizeContext is overriden */
        context.refresh();
    }

    protected BeanDefinitionReader createBeanDefinitionReader(final GenericApplicationContext context)
    {
        return new XmlBeanDefinitionReader(context);
    }

    @Override
    public String getResourceSuffix()
    {
        return "-context.xml";
    }
}
