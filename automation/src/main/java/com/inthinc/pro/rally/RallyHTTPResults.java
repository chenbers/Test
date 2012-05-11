package com.inthinc.pro.rally;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inthinc.pro.automation.utils.ObjectConverter;

public class RallyHTTPResults {
    
    private String _rallyAPIMajor;
    
    private String _rallyAPIMinor;

    private JSONArray Errors;
    private JSONObject Object;
    private int PageSize;
    private JSONArray Results;

    private int StartIndex;
    private int TotalResultCount;
    private String type;
    

    private JSONArray Warnings;

    public String get_rallyAPIMajor() {
        return _rallyAPIMajor;
    }

    public String get_rallyAPIMinor() {
        return _rallyAPIMinor;
    }

    public JSONArray getErrors() {
        return Errors;
    }

    public int getPageSize() {
        return PageSize;
    }

    public JSONArray getResults() {
        return Results;
    }

    public int getStartIndex() {
        return StartIndex;
    }

    public int getTotalResultCount() {
        return TotalResultCount;
    }

    public String getType() {
        return type;
    }

    public JSONArray getWarnings() {
        return Warnings;
    }

    public boolean hasErrors(){
        return Errors != null;
    }

    public boolean hasResults(){
        return Results != null;
    }
    
    public boolean hasWarnings(){
        return Warnings != null;
    }
    
    public void set_rallyAPIMajor(String _rallyAPIMajor) {
        this._rallyAPIMajor = _rallyAPIMajor;
    }
    
    public void set_rallyAPIMinor(String _rallyAPIMinor) {
        this._rallyAPIMinor = _rallyAPIMinor;
    }

    @JsonProperty("Errors")
    public void setErrors(Object errors) {
        Errors = ObjectConverter.converToJSONArray(errors);
    }

    @JsonProperty("PageSize")
    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }
    
    @JsonProperty("Results")
    public void setResults(Object results) {
        Results = ObjectConverter.converToJSONArray(results);
    }

    @JsonProperty("StartIndex")
    public void setStartIndex(int startIndex) {
        this.StartIndex = startIndex;
    }

    @JsonProperty("TotalResultCount")
    public void setTotalResultCount(int totalResultCount) {
        TotalResultCount = totalResultCount;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("Warnings")
    public void setWarnings(Object warnings) {
        Warnings = ObjectConverter.converToJSONArray(warnings);
    }

    public JSONObject getObject() {
        return Object;
    }

    @JsonProperty("Object")
    public void setObject(Object object) {
        Object = ObjectConverter.convertToJSONObject(object, "");
    }
}

