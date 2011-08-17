package com.inthinc.pro.automation.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

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

import com.inthinc.pro.automation.device_emulation.TiwiProDevice;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.Locales;
import com.inthinc.pro.automation.utils.MasterTest.ErrorLevel;
import com.inthinc.pro.rally.RallyWebServices;

public class DownloadFile {
    
    private static final int kiloBit = 1024;
    
    public boolean fileEquals(String firstFile, String secondFile){
        
        
        return false;
    }
    
    public boolean download(String url, String destinationFileName){
        
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
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination));
            SVNRepository repo = clientManager.createRepository(source, false);
            repo.getFile(fileDir, -1, null, bos);
            bos.flush();
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
    
    public static boolean fileMatchesSVN(String svnSource, String fileDir, File localCopy){
        DAVRepositoryFactory.setup( );
        SVNURL temp;
        try {
            temp = SVNURL.parseURIDecoded(svnSource);
        } catch (SVNException e) {
            e.printStackTrace();
            return false;
        }    
        
        return fileMatchesSVN(temp, fileDir, localCopy);
    }
    
    public static boolean fileMatchesSVN(SVNURL svnSource, String fileDir, File localCopy){
        SVNRepository repository = null;
        
        try {
            repository = SVNRepositoryFactory.create(svnSource);
            ISVNAuthenticationManager authManager = new BasicAuthenticationManager(RallyWebServices.username, RallyWebServices.password);
            repository.setAuthenticationManager(authManager);
            repository.testConnection();
            SVNNodeKind nodeKind = repository.checkPath(fileDir, -1);
            System.out.println(nodeKind);
            
            
        } catch (SVNException e) {
            e.printStackTrace();
        }
        
        
        return false;
    }
    
    public static void main(String[] args){
        
        
        TiwiProDevice tiwi = new TiwiProDevice("javadeviceindavidsaccount", Addresses.QA);
        tiwi.set_WMP(17207);
        
        for (int i=1;i<=33;i++){
            for (Locales locale: EnumSet.allOf(Locales.class)){
                int fileNumber = i;
                
                String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"+locale.getFolder();
                String path = "src/main/resources/svnVersion/";
                String fileName = String.format("%02d.pcm", fileNumber);
                File dest = new File(path + fileName);
                
                DownloadFile.downloadSvnDirectory(url, fileName, dest);
                
                tiwi.getAudioFile(fileNumber, locale);
        
                String svn = MD5Checksum.getMD5Checksum(path + fileName);
                String hessian = MD5Checksum.getMD5Checksum(path.replace("svn", "hessian") + fileName);
                System.out.println(fileName + " " + locale + " "+ hessian.equals(svn));
                System.out.println("svn: " + svn + "  hessian: " + hessian);
                
            }
        }
        
        
        
        
//        int fileNumber = 1;
//        Locales locale = Locales.ENGLISH_US;
//        
//        String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"+locale.getFolder();
//        String path = "src/main/resources/svnVersion/";
//        String fileName = String.format("%02d.pcm", fileNumber);
//        File dest = new File(path + fileName);
//        
//        System.out.println(downloadSvnDirectory(url, fileName, dest));
//        
//
//        String svn = MD5Checksum.getMD5Checksum(path + fileName);
//        
//        TiwiProDevice tiwi = new TiwiProDevice("javadeviceindavidsaccount", Addresses.QA);
//        tiwi.set_WMP(17207);
//        tiwi.getAudioFile(fileNumber, locale);
//            
//        String hessian = MD5Checksum.getMD5Checksum(path.replace("svn", "hessian") + fileName);
//        System.out.println(svn);
//        System.out.println(hessian);
//        System.out.println(svn.equals(hessian));
    }
}
