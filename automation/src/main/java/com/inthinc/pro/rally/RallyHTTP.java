package com.inthinc.pro.rally;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.HTTPCommands;
import com.inthinc.pro.automation.utils.ObjectConverter;
import com.inthinc.pro.automation.utils.StackToString;

public class RallyHTTP extends HTTPCommands {
    

    
    private JSONObject workspace;
    private String query;

    private RallyWebServices workingSpace;
    
    private RallyHTTPResults results;

    
    public interface RallyFields{}


    public RallyHTTP(String username, String password) {
        super();
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
        constructQuery(queryString, startPosition, pageSize, false);
    }

    public void constructQuery(String queryString, Integer startPosition,
            Integer pageSize, Boolean fetch) {
        query = constructQuery("workspace", workingSpace.getValue(), 
                "query", queryString,
                "start", startPosition.toString(), 
                "pageSize", pageSize.toString(),
                "fetch", fetch.toString());
        Log.debug(query);
    }

    public String constructFilter(String filterString) throws URIException {
        if (filterString == null || filterString.equals(""))
            return "";
        String filter = encodeURLQuery(filterString);
        Log.debug(filter);
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
            createResult(httpRequest(getRequest));
        } catch (UnsupportedEncodingException e) {
            Log.error(e);
        } catch (IOException e) {
            Log.error(e);
        } catch (JSONException e) {
            Log.error(e);
        }
        response = null;
    }

    public void postObjects(RallyWebServices request, JSONObject item) {
        postObjects(request, item, false);
    }
    
    private void createResult(String response) throws JSONException{
        JSONObject jo = new JSONObject(response);
        Log.debug(jo);
        String type = (String) jo.keys().next();
        results = ObjectConverter.convertJSONToObject(response, type, RallyHTTPResults.class);
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
            Log.debug(PrettyJSON.toString(postJSON));

            PostMethod postRequest = new PostMethod(url);
            RequestEntity requestEntity = new StringRequestEntity(content,
                    "application/json", "UTF-8");
            postRequest.setRequestEntity(requestEntity);
            createResult(httpRequest(postRequest));
            
        } catch (JSONException e) {
            Log.error(url);
            Log.error(response);
            Log.error(e);
        } catch (UnsupportedEncodingException e) {
            Log.error(e);
        } catch (IOException e) {
            Log.error(e);
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
            Log.error(e);
        }
        response = null;
    }

    public void deleteObject(String url) {
        try {
            DeleteMethod deleteRequest = new DeleteMethod(url);
            createResult(httpRequest(deleteRequest));
        } catch (JSONException e) {
            Log.error(url);
            Log.error(response);
            Log.error(e);
        } catch (HttpException e) {
            Log.error(e);
        } catch (IOException e) {
            Log.error(e);
        }
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
            workspace = results.getResults().getJSONObject(0);
        } catch (HttpException e1) {
            Log.error(StackToString.toString(e1));
        } catch (JSONException e1) {
            Log.error(StackToString.toString(e1));
        }
        Log.debug(workspace);
    }

    public JSONObject getWorkspace() {
        return workspace;
    }

    public String getQuery() {
        return query;
    }

    public RallyHTTPResults getResponse() {
        return results;
    }

}
