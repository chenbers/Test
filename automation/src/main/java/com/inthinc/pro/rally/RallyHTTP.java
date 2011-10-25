package com.inthinc.pro.rally;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.utils.HTTPCommands;
import com.inthinc.pro.automation.utils.StackToString;

public class RallyHTTP extends HTTPCommands {
    

    private final static Logger logger = Logger.getLogger(HTTPCommands.class);
    
    private JSONObject queryResults, workspace;
    private String query;

    private RallyWebServices workingSpace;


    public RallyHTTP(String username, String password) {
        httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(10000);
        httpClient.getParams().setAuthenticationPreemptive(true);
        httpClient.getState().setCredentials(
                new AuthScope("rally1.rallydev.com", 443),
                new UsernamePasswordCredentials(username, password));
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
            JSONObject postJSON = new JSONObject();
            postJSON.put(request.getName(), item);
            String content = postJSON.toString();
            logger.debug(PrettyJSON.toString(postJSON));

            PostMethod postRequest = new PostMethod(url);
            RequestEntity requestEntity = new StringRequestEntity(content,
                    "application/json", "UTF-8");
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
        if (objectID.endsWith(".js")) {
            objectID = type.getValue() + "/" + objectID + ".js";
        }
        deleteObject(objectID);
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
        try {
            return queryResults.getJSONArray("Results");
        } catch (JSONException e) {
            return new JSONArray().put(queryResults.getJSONObject("Object"));
        } catch (Exception e){
            return null;
        }
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

    public String getQuery() {
        return query;
    }

}
