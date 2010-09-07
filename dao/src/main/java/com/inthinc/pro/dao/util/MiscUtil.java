package com.inthinc.pro.dao.util;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.User;

public class MiscUtil {

    public static List<User> removeInThinc(List<User> list) {
        List<User> users = new ArrayList<User>();
        
        // Remove the inthinc user
        for ( User u:list ) {
            if ( u.getPerson() != null ) { 
                if (    u.getPerson().getFirst()  != null    &&     u.getPerson().getFirst().equalsIgnoreCase("first")  &&
                        u.getPerson().getLast()   != null    &&     u.getPerson().getLast().equalsIgnoreCase("last")    &&
                        u.getPerson().getMiddle() != null    &&     u.getPerson().getMiddle().equalsIgnoreCase("m")     &&
                        u.getPerson().getSuffix() != null    &&     u.getPerson().getSuffix().equalsIgnoreCase("jr") ) {
                    continue;
                } else {
                    users.add(u);
                }
            } else {
                users.add(u);
            }
        }        
        
        return users;
    }
}
