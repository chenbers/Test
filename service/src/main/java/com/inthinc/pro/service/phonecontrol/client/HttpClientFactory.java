package com.inthinc.pro.service.phonecontrol.client;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;

/**
 * A Factory for {@link HttpClient} objects with basic (username/password) http authentication information.
 */
public class HttpClientFactory {

    private String host;
    private int port;

    public HttpClient createHttpClient(String username, String password) {
        HttpClient httpClient;
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials credentials = new UsernamePasswordCredentials(username, password);
        httpClient.getState().setCredentials(new AuthScope(this.host, this.port, AuthScope.ANY_REALM), credentials);
        return httpClient;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
