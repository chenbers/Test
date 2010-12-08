package com.inthinc.pro.backing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.CrashTrace;

public class CrashTraceBean extends BaseBean implements ServableFileObject {
    private static final long serialVersionUID = 1459750210891008671L;

    private static final Logger logger = Logger.getLogger(CrashTraceBean.class);

    private CrashTrace crashTrace = new CrashTrace();

    public CrashTraceBean() {
        super();
        logger.info("public CrashTraceBean() ");
    }
    public CrashTraceBean(CrashTrace crashTrace) {
        this.crashTrace = crashTrace;
    }
    public CrashTraceBean(Integer crashID, byte[] traceData) {
        this.crashTrace = new CrashTrace(crashID, null, traceData);
    }

    public CrashTraceBean getMockObject() throws IOException {
        logger.warn("getMockObject still getting called!");
        CrashTraceBean results = new CrashTraceBean(); 
        //StringBuffer URL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
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
        return crashTrace.getEventID().toString();
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
            logger.error("e: "+e);
            logger.error("crashTrace: "+crashTrace);
            logger.error("crashTrace.getBinaryTraceData_ba: "+crashTrace.getBinaryTraceData_ba());
            logger.error("Data is not in proper format. (binary array)");
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
            logger.error("Data is not in proper format. (BLOB)");
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
