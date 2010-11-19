package com.inthinc.pro.backing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.inthinc.pro.model.File;

/**
 * @author Ilya Shaikovsky
 * 
 */
public class FileUploadBean extends BaseBean {

    private ArrayList<File> files = new ArrayList<File>();
    private int uploadsAvailable = 1;
    private boolean autoUpload = false;
    private boolean useFlash = false;
    private String test = "FileUploadBean is present and accounted for";

    public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }

    public FileUploadBean() {}

    public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer) object).getData());
    }

    public void listener(UploadEvent event) throws Exception {
        System.out.println("public void listener(UploadEvent "+event+") throws Exception");
        UploadItem item = event.getUploadItem();
        File file = new File();
        file.setLength(item.getData().length);
        file.setName(item.getFileName());
        file.setData(item.getData());
        files.add(file);
        
        //TODO: jwimmer: send the "File" to the backend via Hessian
        
        uploadsAvailable--;
    }

    public String clearUploadData() {
        files.clear();
        setUploadsAvailable(5);
        return null;
    }

    public long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public int getUploadsAvailable() {
        return uploadsAvailable;
    }

    public void setUploadsAvailable(int uploadsAvailable) {
        this.uploadsAvailable = uploadsAvailable;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public boolean isUseFlash() {
        return useFlash;
    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;
    }

    public String getTest() {
        return "test: "+test;
    }

    public void setTest(String test) {
        this.test = test;
    }

}