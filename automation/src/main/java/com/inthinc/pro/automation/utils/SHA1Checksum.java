package com.inthinc.pro.automation.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Checksum;

public class SHA1Checksum {

    public static byte[] createChecksum(String filename)
            throws NoSuchAlgorithmException, IOException {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA1");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static String getSHA1Checksum(String filename) {
        byte[] b;
        try {
            b = createChecksum(filename);        
            
            String result = "";
            for (int i = 0; i < b.length; i++) {
                result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
            }
            return result;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getChecksumValue(Checksum checksum, String fname) {
        try {
            BufferedInputStream is = new BufferedInputStream(
                    new FileInputStream(fname));
            byte[] bytes = new byte[1024];
            int len = 0;

            while ((len = is.read(bytes)) >= 0) {
                checksum.update(bytes, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checksum.getValue();
    }
}
