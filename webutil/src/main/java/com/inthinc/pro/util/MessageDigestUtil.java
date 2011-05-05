package com.inthinc.pro.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {
    
    public static String getHash(String stringToDigest){
        
        byte[] defaultBytes = stringToDigest.getBytes();
        try{
            
            MessageDigest algorithm = MessageDigest.getInstance("SHA");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();
                    
            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<messageDigest.length;i++) {
                
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

//            System.out.println("sessionid "+stringToDigest+" md5 version is "+hexString.toString());
            stringToDigest=hexString+"";
            
            return hexString.toString();
            
        }catch(NoSuchAlgorithmException nsae){
            
            return null;        
        }
    }
}
