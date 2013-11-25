package com.inthinc.pro.scheduler.data;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean that stores report logging data.
 */
public abstract class ReportLogData {
    private Integer reportID;
    private String reportType;
    private Integer accountId;
    private String accountName;
    private Integer idUserRequestingReport;
    private List<Integer> recipientUserIds = new ArrayList<Integer>();
    private List<String> recipientEmailAddresses = new ArrayList<String>();
    private List<Throwable> errors  = new ArrayList<Throwable>();
    private Integer scheduledTime;
    private DateTime actualTimeSent;
    private Long processMilis;
    private Boolean success;

    /**
     * Transforms all data to a logstash readable form.
     * All changes to logstash accepted format should be made here.
     *
     * @return all information contained in a logstash readable form
     */
    public abstract String formatForStash();

    /**
     * Formats date by specific
     *
     * @param dateTime
     * @return
     */
    protected String formatDate(DateTime dateTime, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (dateTime==null)
            return "";

        return sdf.format(dateTime.toDate());
    }

    /**
     * Resets all.
     */
    public void clear(){
        reportID = null;
        reportType = null;
        accountId = null;
        accountName = null;
        idUserRequestingReport = null;
        recipientUserIds = new ArrayList<Integer>();
        recipientEmailAddresses = new ArrayList<String>();
        scheduledTime = null;
        actualTimeSent = null;
        processMilis = null;
        errors  = new ArrayList<Throwable>();
        success = null;
    }

    public Integer getReportID() {
        return reportID;
    }

    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getIdUserRequestingReport() {
        return idUserRequestingReport;
    }

    public void setIdUserRequestingReport(Integer idUserRequestingReport) {
        this.idUserRequestingReport = idUserRequestingReport;
    }

    public List<Integer> getRecipientUserIds() {
        return recipientUserIds;
    }

    public void setRecipientUserIds(List<Integer> recipientUserIds) {
        this.recipientUserIds = recipientUserIds;
    }

    public List<String> getRecipientEmailAddresses() {
        return recipientEmailAddresses;
    }

    public void setRecipientEmailAddresses(List<String> recipientEmailAddresses) {
        this.recipientEmailAddresses = recipientEmailAddresses;
    }

    public Integer getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Integer scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public DateTime getActualTimeSent() {
        return actualTimeSent;
    }

    public void setActualTimeSent(DateTime actualTimeSent) {
        this.actualTimeSent = actualTimeSent;
    }

    public Long getProcessMilis() {
        return processMilis;
    }

    public void setProcessMilis(Long processMilis) {
        this.processMilis = processMilis;
    }

    public List<Throwable> getErrors() {
        return errors;
    }

    public void setErrors(List<Throwable> errors) {
        this.errors = errors;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
