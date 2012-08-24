package com.inthinc.pro.dao.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.map.ObjectMapper;

import com.inthinc.forms.common.model.SubmissionData;
import com.inthinc.forms.common.model.enums.TriggerType;
import com.inthinc.pro.dao.FormsDAO;
import com.inthinc.pro.model.form.FormSubmission;

@SuppressWarnings("serial")
public class FormsServiceDAO extends GenericServiceDAO<Integer, Integer> implements FormsDAO {

    private String protocol;

    private String host;
    private Integer port;
    private String path;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void switchSilo(Integer siloID) {
        // TODO Auto-generated method stub

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public Integer findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer create(Integer id, Integer entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer update(Integer entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FormSubmission getForm(Long timestamp, Integer vehicleID) {

        HttpMethod getForm = new GetMethod(formatRequest() + "submission" + "/" + timestamp + "/" + vehicleID);
        getForm.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        HttpClient client = setupClient();
        addCredentials(client);
        try {
            int statusCode = client.executeMethod(getForm);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream body = getForm.getResponseBodyAsStream();
                ObjectMapper mapper = new ObjectMapper();
                FormSubmission value = mapper.readValue(body, FormSubmission.class);
            }
        } catch (HttpException he) {

        } catch (IOException ioe) {

        } finally {
            getForm.releaseConnection();
        }
        return null;
    }

    @Override
    public SubmissionData getSubmissions(TriggerType triggerType, Date startDate, Date endDate, Integer groupID) {

        HttpMethod getForm = new GetMethod(formatRequest() + "submissions" + "/" + triggerType + "/" + formatDate(startDate) + "/" + formatDate(endDate) + "/" + groupID);
        getForm.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        HttpClient client = setupClient();
        addCredentials(client);
        try {
            int statusCode = client.executeMethod(getForm);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream body = getForm.getResponseBodyAsStream();
                ObjectMapper mapper = new ObjectMapper();
                SubmissionData value = mapper.readValue(body, SubmissionData.class);
            }
        } catch (HttpException he) {

        } catch (IOException ioe) {

        } finally {
            getForm.releaseConnection();
        }
        return null;
    }

    private HttpClient setupClient() {
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        HttpClient client = new HttpClient(params);
        return client;
    }

    private void addCredentials(HttpClient client) {
        Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
        client.getState().setCredentials(new AuthScope(host, port == null ? -1 : port, AuthScope.ANY_REALM), defaultcreds);
    }

    private String formatRequest() {
        StringBuilder request = new StringBuilder();
        request.append(protocol == null ? "http" : protocol).append("://").append(host);
        if (port != null) {
            request.append(":").append(port);
        }
        request.append("/").append(path).append("/");
        return request.toString();
    }

    static final String DASHED_DATE_FORMAT = "yyyy-MM-dd";
    private String formatDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DASHED_DATE_FORMAT);
        String formattedTime = dateFormatter.format(date);

        return formattedTime;
    }
}
