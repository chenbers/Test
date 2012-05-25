package com.inthinc.pro.automation.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.inthinc.pro.automation.logging.Log;

public class AutoPasswordManager {
    
    public static void main(String[] args){
        String password = "password";
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        String portal = "kVOIrk0RgJuc+ZxKQKB3GqrTxPeoPymd/ZmhOCfEBQkaFHS3wLXBsVqccCOT1prv";
        String encrypted = encryptor.encryptPassword("password");
        Log.info(encrypted);
        Log.info(encryptor.checkPassword(password, portal));
        Log.info(encryptor.checkPassword(password, encrypted));
    }

}
