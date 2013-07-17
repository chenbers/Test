package com.inthinc.pro.backing;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

import com.inthinc.pro.dao.annotations.Column;

public class ViewUpdateableTest {
    private Class<?> viewClassList [] = {
            AccountBean.AccountView.class,
            CustomRolesBean.CustomRoleView.class,
            DevicesBean.DeviceView.class,
            PersonBean.PersonView.class,
            ReportScheduleBean.ReportScheduleView.class,
            UnknownDriverBean.UnknownDriverView.class,
            VehiclesBean.VehicleView.class
    };

    // check that all fields in view classes that extend model classes are annotated with @Column so the updateable flag is set correctly 
    // otherwise there can be issues when creating the hessian map with unwanted stuff getting in the map
    @Test
    public void testViewUpdateableIsFalse() {
        
        for (Class<?> clazz : viewClassList) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                assertTrue(clazz.getName() + " expected annotion on field " + field.getName(), field.isAnnotationPresent(Column.class));
                Column column = field.getAnnotation(Column.class);
                assertTrue(clazz.getName() + " expected annotion on field " + field.getName(), (column != null));
            }
        }
    }

}
