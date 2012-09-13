package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsEditEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms/buildedit"),
    TITLE("Edit Form", "//span[@class='admin']"),

    NAME_LABEL("Name:", "//label[@for='renameFormField']"),
    DESCRIPTION_LABEL("Description:", "//label[@for='description']"),
    TRIGGER_LABEL("Trigger:", "//label[@for='select-trigger']"),
    ROUTE_LABEL("Route Trigger Expression:", "//label[@for='routeTrigger']"),
    VERSION_LABEL("Version:", "//label[@for='version']"),
    STATUS_LABEL("Status:", "//label[@for='select-status']"),
    HOS_LABEL("HOS:", "//label[@for='hos-filter']"),
    VEHICLE_TAGS_LABEL("Vehicle Tags:", "//label[@for='tags']"),
    FILTER_GROUPS_LABEL("Filter Groups:", "//label[@for='group-search']"),
    PROPERTIES_LABEL("Properties", "//div[@class='propertiesPane']/h3"),
    VIEW_PROPERTIES_LABEL("First add a control, then select it to view its properties here.", "//li[@class='emptyData']"),
    
    SAVE_AS_NEW_TOP("Save As New", "//a[@id='saveAs-top']"),
    SAVE_TOP("Save", "//a[@id='saveForm-top']"),
    CANCEL_TOP("Cancel", "//a[@id='cancelForm-top']"),
    SAVE_AS_NEW_BOTTOM("Save As New", "saveAs-bottom"),
    SAVE_BOTTOM("Save", "//a[@id='saveForm-bottom']"), 
    CANCEL_BOTTOM("Cancel", "//a[@id='cancelForm-bottom']"),

    NAME_FIELD(null, "renameFormField"),
    DESCRIPTION_FIELD(null, "description"),
    TRIGGER_DROPDOWN(null, "select-trigger"),
    STATUS_DROPDOWN(null, "select-status"),
    ROUTE_TRIGGER_EXPRESSION_FIELD(null, "routeTrigger"),
    VERSION_TEXT(null, "//label[@id='version']"),
    HOS_DROPDOWN(null, "//select[@id='select-hos-status']"),
    VEHICLE_TAGS_DROPDOWN(null, "//div[@class='text-wrap']"),
    VEHICLE_TAGS_ARROW(null, "//div[@class='text-arrow']"),
    FILTER_GROUPS_FIELD(null, "group-search"), 
    GROUPS_ARROW(null, "//ins[@class='jstree-icon']"),//needs more work so I can select specific arrows
    GROUPS_CHECKBOX(null, "//ins[@class='jstree-checkbox']"),//needs more work so I can select specific checkboxes
    
    TEXT_LINK("Text", "//a[@class='toolButton inputText ui-draggable']"),
    NUMERIC_LINK("Numeric", "//a[@class='toolButton inputNumeric ui-draggable']"),
    DATE_LINK("Date", "//a[@class='toolButton inputDate ui-draggable']"),
    CHOOSE_ONE_LINK("Choose One", "//a[@class='toolButton inputSelectOne ui-draggable']"),
    SELECT_MULTIPLE_LINK("Select Multiple", "//a[@class='toolButton inputSelectMany ui-draggable']"),
    GROUP_LINK("Group", "//a[@class='toolButton group ui-draggable']"),
 
    PREVIEW_AREA(null, "//div[@class='workspaceScrollArea']"),
    DELETE_CONTROL(null, "//a[@class='deleteControl']"),

    DATANAME_FIELD("untitled###", "property_Data Name"),
    CAPTION_FIELD(null, ""),
    HINT_FIELD(null, ""),
    DEFAULT_VALUE(null, "property_Default Value"),
    READ_ONLY_CHECKBOX(null, "property_Read Only"),
    REQUIRED_CHECKBOX(null, "property_Required"),
    LENGTH_CHECKBOX(null, "property_range_enabled"),
    RANGE_NUMERIC_CHECKBOX(null, "property_range_enabled"),//may need unique id
    MINIMUM_NUMERIC_FIELD(null, "//input[@class='editorTextfield min']"),//may need unique id
    MINIMUM_NUMERIC_INCLUSIVE_CHECKBOX(null, "property_range_min_inclusive"),//may need unique id
    MAXIMUM_NUMERIC_FIELD(null, "//input[@class='editorTextfield max']"),//may need unique id
    MAXIMUM_NUMERIC_INCLUSIVE_CHECKBOX(null, "property_range_max_inclusive"),//may need unique id
    RANGE_DATE_CHECKBOX(null, "property_range_enabled"),//may need unique id
    MINIMUM_DATE_DROPDOWN(null, "//input[@class='dp1343142341964']"),//id keeps changing, needs to be static
    MINIMUM_DATE_INCLUSIVE_CHECKBOX(null, "property_range_min_inclusive"), //may need unique id
    MAXIMUM_DATE_DROPDOWN(null, "//input[@class='dp1343142341965']"),//id keeps changing, needs to be static
    MAXIMUM_DATE_INCLUSIVE_CHECKBOX(null, "property_range_max_inclusive"), //may need unique id
    INVALID_TEXT_FIELD(null, ""),
    KIND_DROPDOWN(null, "//select[@class='editorSelect']"),//may need unique id
    ADD_OPTION_LINK("Add Option", "//a[@class='addOption']"),//may need unique id
    BULK_EDIT_LINK("bulk edit", "//a[@class='optionsEditorLink']"),//may need unique id
    OPTION_FIELD(null, ""),
    OPTION_UNDERLYING_VALUE_FIELD(null, "//input[@class='editorTextfield underlyingValue']"),//may need unique id
    OPTION_REMOVE(null, "//a[@class='removeOption']"),//we are going to need to be able to reference which option # you want to remove
    LABEL_FIELD(null, ""),
    LOOPED_CHECKBOX(null, "property_Looped"),
    DISPLAY_ON_ONE_SCREEN_CHECKBOX(null, "property_Display On One Screen"),
    
    ADVANCED_ARROW(null, "//div[@class='icon']"),
    RELEVANCE_FIELD(null, "property_Relevance"),
    CONSTRAINT_FIELD(null, "property_Constraint"),
    INSTANCE_DESTINATION_FIELD(null, "property_Instance Destination"),
    
    ERROR_NAME_LABEL("Name is a required field", "//label[@id='displayNameError']"),
    ERROR_CONTROL_LABEL("Form needs at least one control", "//label[@id='payloadError']"),
    DATA_NAME_ERROR("This property is required", "//ul[@class='errorList']"),
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsEditEnum(String url){
    	this.url = url;
    }
    private FormsEditEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
}
