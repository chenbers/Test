package com.inthinc.pro.service;

import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Test;

import com.inthinc.pro.model.User;

public class UserServiceTest extends BaseTest {

    private UserService userService;

    
    @Test
    public void testUsers() {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
        userService = ProxyFactory.create(UserService.class, "http://localhost:8080");
        
        List<User> list = userService.getAll();
        for(User user : list) {
            System.out.println(user);
        }
    }

}
