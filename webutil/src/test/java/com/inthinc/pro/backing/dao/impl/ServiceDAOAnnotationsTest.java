package com.inthinc.pro.backing.dao.impl;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.inthinc.pro.backing.dao.annotation.DAODescription;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.backing.dao.model.CrudType;

public class ServiceDAOAnnotationsTest {

    @Test
    public void testSiloService() {
        Method[] methods = SiloServiceImpl.class.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MethodDescription.class)) {
                MethodDescription methodDescription = method.getAnnotation(MethodDescription.class);
                CrudType crudType = methodDescription.crudType();
                if (crudType == CrudType.NOT_AVAILABLE) {
                    continue;
                }
                assertTrue("expected a DAODescription annotation for method: " + method.getName(), method.isAnnotationPresent(DAODescription.class));
            }
        }
    }

    @Test
    public void testReportService() {
        Method[] methods = ReportServiceImpl.class.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MethodDescription.class)) {
                MethodDescription methodDescription = method.getAnnotation(MethodDescription.class);
                CrudType crudType = methodDescription.crudType();
                if (crudType == CrudType.NOT_AVAILABLE) {
                    continue;
                }
                assertTrue("expected a DAODescription annotation for method: " + method.getName(), method.isAnnotationPresent(DAODescription.class));
            }
        }
    }
}
