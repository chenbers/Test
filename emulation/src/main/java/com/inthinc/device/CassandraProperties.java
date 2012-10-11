package com.inthinc.device;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CassandraProperties {

    private static final String[] configFiles = {"classpath:cassandra/applicationContext-cassandra.xml"};
    private static final BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);
    private static final CassandraPropertiesBean apb = (CassandraPropertiesBean) factory.getBean("cassandraPropertiesBean");
    
        public static CassandraPropertiesBean getPropertyBean(){
            return apb;
        }
}
