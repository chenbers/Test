package com.inthinc.pro.security.userdetails;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.User;


public class ProUserServiceImpl implements UserDetailsService
{
    private static final Logger logger = Logger.getLogger(ProUserServiceImpl.class);
    
    static Map<String, User> mockUserMap = new HashMap<String, User>();
    static
    {
        User user = new User();
        user.setUsername("expired");
        user.setPassword("nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7");   // password is 'password'
        user.setActive(false);
        user.setRole(Role.ROLE_USER);
        mockUserMap.put("expired", user);
        
        user = new User();
        user.setUsername("admin");
        user.setPassword("nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7");   // password is 'password'
        user.setActive(true);
        user.setRole(Role.ROLE_ADMIN);
        mockUserMap.put("admin", user);

        user = new User();
        user.setUsername("manager");
        user.setPassword("nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7");   // password is 'password'
        user.setActive(true);
        user.setRole(Role.ROLE_MANAGER);
        mockUserMap.put("manager", user);

        user = new User();
        user.setUsername("superuser");
        user.setPassword("nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7");   // password is 'password'
        user.setActive(true);
        user.setRole(Role.ROLE_SUPERUSER);
        mockUserMap.put("superuser", user);
        
        for (int i = 0; i < 10; i++)
        {
            String username="user"+i;
            user = new User();
            user.setUsername(username);
            user.setPassword("nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7");   // password is 'password'
            user.setActive(true);
            user.setRole(Role.ROLE_USER);
            mockUserMap.put(username, user);
            
        }
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        logger.debug("ProUserServiceImpl:loadUserByUsername " + username);
        User user = lookup(username);
        if (user == null)
            throw new UsernameNotFoundException("Username could not be found");
        return new ProUser(user, user.getRole().toString());
    }

    // TODO: replace with real impl once dao settles down
    private User lookup(String username)
    {
        return mockUserMap.get(username);
    }
}
