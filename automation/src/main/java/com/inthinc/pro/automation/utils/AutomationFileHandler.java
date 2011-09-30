package com.inthinc.pro.automation.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.inthinc.pro.rally.RallyWebServices;

public class AutomationFileHandler {
    
    private static final int kiloBit = 1024;
    
    public static boolean filesEqual(String firstFile, String secondFile){
        String first = SHA1Checksum.getSHA1Checksum(firstFile);
        String second = SHA1Checksum.getSHA1Checksum(secondFile);
        return first.equals(second);
    }
    
    public static boolean download(String url, String destinationFileName){
        
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream fos = new FileOutputStream(destinationFileName);
            BufferedOutputStream bout = new BufferedOutputStream(fos, kiloBit);
            byte data[] = new byte[kiloBit];
            
            int count = in.read(data, 0, kiloBit);
            while (count >= 0){
                bout.write(data, 0, count);
                count = in.read(data, 0, kiloBit);
            }
            
            bout.flush();
            bout.close();
            fos.flush();
            fos.close();
            in.close();
            return true;
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return false;
    }
    
    public static boolean downloadSvnDirectory(String source, String fileDir, File destination){
        DAVRepositoryFactory.setup( );
        SVNURL temp;
        try {
            temp = SVNURL.parseURIDecoded(source);
        } catch (SVNException e) {
            e.printStackTrace();
            return false;
        }
        return downloadSvnDirectory(temp, fileDir, destination);
    }
    
    public static boolean downloadSvnDirectory(SVNURL source, String fileDir, File destination){
        ISVNAuthenticationManager authManager = new BasicAuthenticationManager("dtanner", RallyWebServices.password);
        
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
        try {
            destination.getParentFile().mkdirs();
            destination.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination));
            SVNRepository repo = clientManager.createRepository(source, false);
            repo.getFile(fileDir, -1, null, bos);
            bos.flush();
            destination.deleteOnExit();
            return true;
        } catch (SVNException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientManager.dispose();
        }
        return false;
    }
    
    
    public static boolean chopFile(int start, String fileLocation){
        try {
            File file = new File(fileLocation);
            byte[] bytes;
            bytes = readFileToByteArray(file);
            return writeByteArrayToFile(Arrays.copyOfRange(bytes, start, bytes.length), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean chopFile(int start, int stop, String fileLocation){

        try {
            File file = new File(fileLocation);
            byte[] bytes;
            bytes = readFileToByteArray(file);
            return writeByteArrayToFile(Arrays.copyOfRange(bytes, start, stop), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean writeByteArrayToFile(byte[] bytes, File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static byte[] readFileToByteArray(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        Long length = file.length();
        byte[] bytes = new byte[length.intValue()];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=fis.read(bytes, offset, bytes.length-offset)) >= 0){
            offset += numRead;
        }
        
        if (offset < bytes.length){
            throw new IOException("Could not completely read file " + file.getName());
        }
        
        fis.close();
        return bytes;
    }
    
//    public static void main(String[] args){
//        int numTested = 0;
//        int numFailed = 0;
//        String destPath = new String("target/test/resources/audioFiles/");
//        TiwiProDevice tiwi = new TiwiProDevice("javadeviceindavidsaccount", Addresses.QA);
//        tiwi.set_WMP(17207);
//        
//        for (Locales locale: EnumSet.allOf(Locales.class)){
//            String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"+locale.getFolder();
//            for (int i=1;i<=33;i++){
//                int fileNumber = i;
//                String fileName = String.format("%02d.pcm", fileNumber);
//                String svnFileName = destPath+"svnVersion/"+locale+fileName;
//                String hessianFileName = destPath+"hessianVersion/"+locale+fileName;
//                File svnFile = new File(svnFileName);
//                
//                DownloadFile.downloadSvnDirectory(url, fileName, svnFile);
//                
//                tiwi.getAudioFile(hessianFileName, fileNumber, locale);
//        
//                String svn = MD5Checksum.getMD5Checksum(svnFileName);
//                String hessian = MD5Checksum.getMD5Checksum(hessianFileName);
//                if(!hessian.equals(svn)){
//                    numFailed++;
//                    System.out.println("FAIL:"+"svn: " + svn + "  hessian: " + hessian);
//                } 
//                numTested++;
//            }
//        }
//        if(numFailed > 0)
//            System.out.println(numFailed+"/"+numTested+" files FAILED MD5Checksum comparison.");
//        else
//            System.out.println(numTested+"/"+numTested+" files PASSED MD5Checksum comparison.");
//    }
}
