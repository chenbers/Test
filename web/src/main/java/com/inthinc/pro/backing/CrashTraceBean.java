package com.inthinc.pro.backing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.CrashTraceDAO;
import com.inthinc.pro.model.CrashTrace;

public class CrashTraceBean extends BaseBean implements ServableFileObject {
    private static final Logger logger = Logger.getLogger(CrashTraceBean.class);

    private CrashTraceDAO crashTraceDAO;
    private CrashTrace crashTrace = new CrashTrace();

    public CrashTraceBean() {
        super();
        logger.info("public CrashTraceBean() ");
    }

    public CrashTraceBean(String eventID) {
        logger.fatal("public WaysmartCrashTrace(String " + eventID + ") is not ready for public consumption");
        crashTraceDAO.getCrashTraceByEventID(eventID);// TODO: jwimmer: throwing null probably because of the inner class!!!
    }

    public CrashTraceBean getMockObject() throws IOException {
        CrashTraceBean results = new CrashTraceBean(); 
        StringBuffer URL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
        System.out.println(URL.toString());
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String server = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServerName();
        Integer port =  ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getLocalPort(); 
        CrashTrace ct = crashTrace.getMockObject("http://"+server+":"+port+path);
        ct.setBinaryTraceData(ct.getBinaryTraceData());
        ct.setBinaryTraceData_ba(ct.getBinaryTraceData_ba());
        ct.setEventID(ct.getEventID());
        results.setCrashTrace(ct);
        return results;
    }

    @Override
    public String getFileName() {
        return crashTrace.getEventID();
    }

    @Override
    public String getFileNamePlusExtension() {
        return crashTrace.getEventID() + "." + getFormatType().getSuffix();
    }

    @Override
    public FormatType getFormatType() {
        return FormatType.CRASHTRACE;
    }

    @Override
    public void write(OutputStream out) throws IOException {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        response.setContentType(getFormatType().getContentType());
        response.addHeader("Content-Disposition", "attachment; filename=\"" + this.getFileNamePlusExtension() + "\"");

        try {
            out.write(crashTrace.getBinaryTraceData_ba(), 0, crashTrace.getBinaryTraceData_ba().length);
        } catch (Exception e) {
            // IF the data is mal-formatted.
            logger.error("Data is not in proper format.");
        } finally {
            out.flush();
            getFacesContext().responseComplete();
        }
    }

    public void writeBLOB(OutputStream out) throws IOException {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        response.setContentType(getFormatType().getContentType());
        response.addHeader("Content-Disposition", "attachment; filename=\"" + this.getFileNamePlusExtension() + "\"");

        try {
            InputStream is = crashTrace.getBinaryTraceData().getBinaryStream();
            byte[] buf = new byte[32 * 1024]; // 32k buffer
            int nRead = 0;
            while ((nRead = is.read(buf)) != -1) {
                out.write(buf, 0, nRead);
            }
            return;

        } catch (Exception e) {
            // IF the data is mal-formatted.
            logger.error("Data is not in proper format.");
        } finally {
            out.flush();
        }
    }

    public CrashTrace getCrashTrace() {
        return crashTrace;
    }

    public void setCrashTrace(CrashTrace crashTrace) {
        this.crashTrace = crashTrace;
    }
}
