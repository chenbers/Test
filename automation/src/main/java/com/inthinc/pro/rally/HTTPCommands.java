package com.inthinc.pro.rally;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.utils.StackToString;

public class HTTPCommands {

    private final static Logger logger = Logger.getLogger(HTTPCommands.class);

    private HttpClient httpClient;
    private JSONObject queryResults, workspace;
    private String query;

    private RallyWebServices workingSpace;

    private String response;

    public HTTPCommands(String username, String password) {
        httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(10000);
        httpClient.getParams().setAuthenticationPreemptive(true);
        httpClient.getState().setCredentials(
                new AuthScope("rally1.rallydev.com", 443),
                new UsernamePasswordCredentials(username, password));
    }

    /**
     * Method httpRequest<br />
     * <br />
     * Using the given HttpMethod, execute it and get the status code<br />
     * Depending on the status, print an error or success method, consume the body<br />
     * then finally release the connection
     * 
     * @param method
     * @throws IOException
     * @throws HttpException
     */
    public void httpRequest(HttpMethod method) throws IOException,
            HttpException {
        response = null;
        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                logger.debug("POST method failed: " + method.getStatusLine());
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            } else {
                logger.debug("POST method succeeded: " + method.getStatusLine());
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            }
            method.releaseConnection();
        } catch (URIException e) {
            logger.fatal(method.getQueryString());
            throw new URIException("Failed");
        }

    }

    public void constructQuery(Integer startPosition, Integer pageSize)
            throws URIException, JSONException {
        constructQuery("", startPosition, pageSize);
    }

    public void constructQuery(Integer startPosition, Integer pageSize,
            Boolean fetch) {
        constructQuery("", startPosition, pageSize, fetch);
    }

    public void constructQuery(String queryString, Integer startPosition,
            Integer pageSize) {

        query = "workspace=" + workingSpace.getValue();
        query += "&query=" + queryString;
        query += "&start=" + startPosition;
        query += "&pageSize=" + pageSize;
        logger.debug(query);
    }

    public void constructQuery(String queryString, Integer startPosition,
            Integer pageSize, Boolean fetch) {
        query = "workspace=" + workingSpace.getValue();
        query += "&query=" + queryString;
        query += "&start=" + startPosition;
        query += "&pageSize=" + pageSize;
        query += "&fetch=" + fetch;
        logger.debug(query);
    }

    private String encodeURLQuery(String string) throws URIException {
        return URIUtil.encodeQuery(string);
    }

    public String constructFilter(String filterString) throws URIException {
        if (filterString == null || filterString.equals(""))
            return "";
        String filter = encodeURLQuery(filterString);
        logger.debug(filter);
        return filter;
    }

    public String constructFilter(NameValuePair[] filterBy) throws URIException {
        String filterRaw = null;
        for (NameValuePair pair : filterBy) {
            if (filterRaw == null) {
                filterRaw = constructFilter(pair, false);
            } else {
                filterRaw = "( " + filterRaw + " AND "
                        + constructFilter(pair, false) + " )";
            }
        }
        return encodeURLQuery(filterRaw);
    }

    public String constructFilter(NameValuePair pair) throws URIException {
        return constructFilter(pair, true);
    }

    public String constructFilter(NameValuePair pair, Boolean encode)
            throws URIException {
        String formatted = "( " + pair.getName() + " = \"" + pair.getValue()
                + "\" )";
        if (encode)
            return encodeURLQuery(formatted);
        else
            return formatted;
    }

    public void getObjects(RallyWebServices request) {
        try {
            GetMethod getRequest = new GetMethod(request.getMethod());
            getRequest.setQueryString(query);
            httpRequest(getRequest);
            JSONObject workspace = new JSONObject(response);
            setQueryResult(workspace.getJSONObject("QueryResult"));
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        } catch (UnsupportedEncodingException e) {
            logger.fatal(StackToString.toString(e));
        } catch (IOException e) {
            logger.fatal(StackToString.toString(e));
        }
        response = null;
    }

    public void postObjects(RallyWebServices request, JSONObject item) {
        postObjects(request, item, false);
    }

    public void postObjects(RallyWebServices request, JSONObject item,
            Boolean create) {
        String url = null;
        try {
            if (create)
                url = request.getValue() + "/create.js";
            else {
                url = item.getString("_ref");
            }
            logger.debug(PrettyJSON.toString(item));
            logger.debug(url);
            JSONObject postJSON = new JSONObject();
            postJSON.put(request.getName(), item);

            PostMethod postRequest = new PostMethod(url);
            RequestEntity requestEntity = new StringRequestEntity(
                    postJSON.toString(), "application/json", "UTF-8");
            postRequest.setRequestEntity(requestEntity);

            httpRequest(postRequest);

            JSONObject reply = new JSONObject(response);
            logger.debug(PrettyJSON.toString(reply));
            try {
                setOperationResult(reply.getJSONObject("QueryResult"));
            } catch (JSONException e) {
                try {
                    setOperationResult(reply.getJSONObject("OperationResult"));
                } catch (JSONException e1) {
                    setCreateResult(reply.getJSONObject("CreateResult"));
                }
            }
        } catch (JSONException e) {
            logger.fatal(url);
            logger.fatal(response);
            logger.fatal(StackToString.toString(e));
        } catch (UnsupportedEncodingException e) {
            logger.fatal(StackToString.toString(e));
        } catch (IOException e) {
            logger.fatal(StackToString.toString(e));
        }
        response = null;
    }

    public void deleteObject(RallyWebServices type, String objectID) {
        String url = type.getValue() + "/" + objectID + ".js";
        deleteObject(url);
    }

    public void deleteObject(JSONObject item) {
        String url;
        try {
            url = item.getString("_ref");
            deleteObject(url);
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
        response = null;
    }

    public void deleteObject(String url) {
        try {
            DeleteMethod deleteRequest = new DeleteMethod(url);
            httpRequest(deleteRequest);
            JSONObject workspace = new JSONObject(response);
            setQueryResult(workspace.getJSONObject("OperationResult"));
        } catch (JSONException e) {
            logger.fatal(url);
            logger.fatal(response);
            logger.fatal(StackToString.toString(e));
        } catch (HttpException e) {
            logger.fatal(StackToString.toString(e));
        } catch (IOException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    private void setQueryResult(JSONObject queryResult) {
        this.queryResults = queryResult;
    }

    private void setOperationResult(JSONObject queryResult) {
        logger.info(PrettyJSON.toString(queryResult));
        this.queryResults = queryResult;
    }

    private void setCreateResult(JSONObject queryResult) {
        this.queryResults = queryResult;
    }

    public void setWorkspace(RallyWebServices sandbox) {
        setWorkspace(sandbox, false);
    }

    public void setWorkspace(RallyWebServices space, Boolean fetch) {
        workingSpace = space;
        try {
            if (fetch)
                constructQuery(1, 200, fetch);
            else
                constructQuery(1, 200);
            getObjects(RallyWebServices.WORKSPACE);
            workspace = getResults().getJSONObject(0);
        } catch (HttpException e1) {
            logger.fatal(StackToString.toString(e1));
        } catch (JSONException e1) {
            logger.fatal(StackToString.toString(e1));
        }
        logger.debug(workspace);
    }

    /**
     * Get the Query Result from Rally. Objects have the form JSONObject > JSONArray > JSONObject
     * 
     * @return
     */
    public JSONObject getQueryResult() {
        return queryResults;
    }

    public JSONArray getResults() throws JSONException {
        return queryResults.getJSONArray("Results");
    }

    public JSONArray getErrors() throws JSONException {
        return queryResults.getJSONArray("Warnings");
    }

    public JSONArray getWarnings() throws JSONException {
        return queryResults.getJSONArray("Errors");
    }

    public JSONObject getWorkspace() {
        return workspace;
    }

    public static String getResponseBodyFromStream(InputStream is) {
        String str = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[128];
            int size = 0;
            while ((size = is.read(buffer)) > 0) {
                baos.write(buffer, 0, size);
            }
            str = new String(baos.toByteArray());
        } catch (IOException ioe) {}
        return str;
    }

    public String getQuery() {
        return query;
    }

}
