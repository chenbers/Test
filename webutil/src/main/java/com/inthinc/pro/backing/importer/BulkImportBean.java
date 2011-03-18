package com.inthinc.pro.backing.importer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.inthinc.pro.backing.BaseBean;

public class BulkImportBean extends BaseBean {

    private ImportType importType;
    private String feedback;
    private UploadFile uploadFile;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }


    public ImportType getImportType() {
        return importType;
    }

    public void setImportType(ImportType importType) {
        this.importType = importType;
    }

    public List<SelectItem> getImportTypes() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        for (ImportType it : ImportType.values()) {
               selectItemList.add(new SelectItem(it,it.getDescription()));
        }
        selectItemList.add(0, new SelectItem(null,"--- Select ---"));
        return selectItemList;
    }
    
    
    public void checkAction() {
        System.out.println("checkAction called " + uploadFile.getName());
        System.out.println("" + uploadFile.getData() == null ? "data is null" : "data is not null");
        
        setFeedback("Check file goes here.");
    }
    
    public void importAction() {
        setFeedback("Import file goes here.");
        
    }

    public void downloadTemplateAction() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        
        response.setContentType("application/xls");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + importType.getTemplate() + "\"");

        OutputStream out = null;

        try
        {
            out = response.getOutputStream();
            InputStream downloadStream = loadFile(importType.getTemplate());
            byte[] downloadBytes = new byte[4096];
            int len = 0;
            while((len = downloadStream.read(downloadBytes, 0, 4096)) != -1)
            {
                out.write(downloadBytes, 0, len);
            }
            downloadStream.close();
            out.flush();
            facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String PACKAGE_PATH = "com/inthinc/pro/download/";
    public static InputStream loadFile(String fileName) throws IOException
    {
        String path = PACKAGE_PATH + fileName;
        InputStream inputStream = BulkImportBean.class.getClassLoader().getResourceAsStream(path);
        if(inputStream == null){
            throw new IOException("Could not find file: " + fileName);
        }
        return inputStream;
    }
    public void resetAction()
    {
        uploadFile = null;
        feedback = null;
    }
    
    public void fileUploadListener(UploadEvent event) throws Exception{
System.out.println("upload listener");        
        UploadItem item = event.getUploadItem();
        uploadFile = new UploadFile();
        uploadFile.setName(item.getFileName());
        uploadFile.setData(item.getData());
    }  
}
