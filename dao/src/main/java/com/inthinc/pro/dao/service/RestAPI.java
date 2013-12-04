package com.inthinc.pro.dao.service;

import java.io.InputStream;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAPI {

    private String baseURL;
    private HttpClient client;
    private ObjectMapper objectMapper;

    public RestAPI(String baseURL, String username, String password) {
        this.baseURL = baseURL;
        this.client = setupClient();
        addCredentials(this.client, username, password);
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    public <T> T postObject(Class<T> type, T instance, String uri) throws Exception {
        PostMethod post = new PostMethod(baseURL + uri);
        String json = objectMapper.writeValueAsString(instance);

        StringRequestEntity body = new StringRequestEntity(json, "application/json", "UTF-8");
        post.setRequestEntity(body);

        return objectMapper.readValue(httpRequest(client, post), type);
    }

    public <T> T getObject(Class<T> type, String uri) throws Exception {
        GetMethod get = new GetMethod(baseURL + uri);
        get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        return objectMapper.readValue(httpRequest(client, get), type);
    }

    private InputStream httpRequest(HttpClient httpClient, HttpMethod method) throws Exception {
        method.setRequestHeader(new Header("Accept", "application/json"));
        int statusCode = httpClient.executeMethod(method);
        if (!(statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED)) {
            throw new Exception(method.getName() + " method failed: " + method.getStatusLine() + " statusCode: " + statusCode + " exception: " + method.getResponseBodyAsString());
        }

        return method.getResponseBodyAsStream();
    }

    private HttpClient setupClient() {
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        HttpClient client = new HttpClient(params);
        return client;
    }

    private void addCredentials(HttpClient client, String username, String password) {
        Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
        client.getState().setCredentials(AuthScope.ANY, defaultcreds);
    }

}
