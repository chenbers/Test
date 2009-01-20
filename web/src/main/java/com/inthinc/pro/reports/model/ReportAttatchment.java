package com.inthinc.pro.reports.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.mail.internet.ContentType;

public class ReportAttatchment
{
    private String fileName;
    private byte[] attatchmentData;
   
    
    public ReportAttatchment(String name,byte[] attatchmentData){
        this.fileName = name;
        this.attatchmentData = attatchmentData;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public byte[] getAttatchmentData()
    {
        return attatchmentData;
    }

    public void setAttatchmentData(byte[] attatchmentData)
    {
        this.attatchmentData = attatchmentData;
    }
    
    public File getAttatchmentAsFile() throws IOException
    {
        File file = new File(fileName);
        FileOutputStream out = new FileOutputStream(file);
        out.write(attatchmentData);
        return file;
    }
    
    
}
