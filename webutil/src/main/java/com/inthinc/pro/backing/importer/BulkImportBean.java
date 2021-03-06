package com.inthinc.pro.backing.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.importer.datacheck.DataCache;

public class BulkImportBean extends BaseBean {

    private static final Logger logger = Logger.getLogger(BulkImportBean.class);

    private DataCache dataCache;
    private ImportType importType;
    private String feedback;
    private UploadFile uploadFile;
    private ProgressBarBean progressBarBean;


    public ProgressBarBean getProgressBarBean() {
        return progressBarBean;
    }

    public void setProgressBarBean(ProgressBarBean progressBarBean) {
        this.progressBarBean = progressBarBean;
    }

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
        try {
            setFeedback(null);
            progressBarBean.setErrorList(null);
            dataCache.init();
            List<String> msgList = new FileChecker().checkFile(importType, new FileInputStream(uploadFile.getFile()), true);
            if (msgList.size() == 0)
                setFeedback("The file check was SUCCESSFUL.  No issues were found.");
            else {
                if (warningsOnly(msgList))
                    setFeedback("The file check was SUCCESSFUL but please note the following warnings:");
                else setFeedback("The file check was NOT SUCCESSFUL.  Please correct the following errors and try again:");
                progressBarBean.setErrorList(msgList);
            }
        } catch (FileNotFoundException e) {
            setFeedback("File upload failed.  File not found. " + e.getMessage());
        }
        
    }
    
    private boolean warningsOnly(List<String> msgList) {
        for (String msg : msgList) {
            if (msg.isEmpty() || msg.startsWith(FileChecker.WARNING_MARKER) || msg.startsWith(FileChecker.ROW_MARKER))
                continue;
            System.out.println("NOT warnings only due to msg: " + msg);
            return false;
        }
        return true;
    }

    public void importAction() {
        try {
            setFeedback(null);
            progressBarBean.setErrorList(null);
            progressBarBean.startProcess();
            dataCache.init();
            logger.info("Import File: " + uploadFile.getFile().getName());
            List<String> msgList = new FileImporter().importFile(importType, new FileInputStream(uploadFile.getFile()), progressBarBean);
            if (msgList == null) {
                logger.info("Import File: " + uploadFile.getName() + " IN PROGRESS");
            }
            else {
                setFeedback("The file check prior to import was NOT SUCCESSFUL.  The following errors were found:");
                progressBarBean.reset();
                progressBarBean.setErrorList(msgList);
                logger.info("Import File: " + uploadFile.getName() + " ERRORS");
            }
        } catch (FileNotFoundException e) {
            progressBarBean.stopProcess();
            setFeedback("File upload failed.  File not found. " + e.getMessage());
        }
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
            InputStream downloadStream = importType.loadTemplate();
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
    
    public void resetAction()
    {
        uploadFile = null;
        feedback = null;
        progressBarBean.reset();
    }
    
    public void fileUploadListener(UploadEvent event) throws Exception{
        UploadItem item = event.getUploadItem();
        uploadFile = new UploadFile();
        uploadFile.setName(item.getFileName());
        uploadFile.setFile(item.getFile());
    }  
    public DataCache getDataCache() {
        return dataCache;
    }

    public void setDataCache(DataCache dataCache) {
        this.dataCache = dataCache;
    }

}
