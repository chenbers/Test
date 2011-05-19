package com.inthinc.pro.backing.ui.component;

import javax.el.ValueExpression;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public class UICustomSelectOneRadio extends UIInput {
    
    public static final String COMPONENT_TYPE = "customSelectOneRadio";
    public static final String COMPONENT_FAMILY = "customSelectOneRadio";


    public String getValueExpressionAsString(String attr) {
       ValueExpression valueExpression = getValueExpression(attr);
       if (valueExpression != null)
           return valueExpression.getValue(this.getFacesContext().getELContext()).toString();
        else
           return null;         
    }
    public Object getValueExpressionAsObject(String attr) {
        ValueExpression valueExpression = getValueExpression(attr);
        if (valueExpression != null)
            return valueExpression.getValue(this.getFacesContext().getELContext());
         else
            return null;         
     }
    
    private String name = null;
    private String overrideName = null;
    private String styleClass = null;
    private String style = null;
    private String disabled = null;
    private String itemLabel = null;
    private Object itemValue = null;
    private String onClick = null;
    private String onMouseOver = null;
    private String onMouseOut = null;
    private String onFocus = null;
    private String onBlur = null;

    public String getName() {
        if (null != name) {
            return name;
         }
         return getValueExpressionAsString("name");
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOverrideName() {
        if (null != overrideName) {
            return overrideName;
         }
         return getValueExpressionAsString("overrideName");
    }
    public void setOverrideName(String overrideName) {
        this.overrideName = overrideName;
    }
    public String getStyleClass() {
        if (null != styleClass) {
            return styleClass;
         }
        return getValueExpressionAsString("styleClass");
    }
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    public String getStyle() {
        if (null != style) {
            return style;
         }
         return getValueExpressionAsString("style");
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public String getDisabled() {
        if (null != disabled) {
            return disabled;
         }
         return getValueExpressionAsString("disabled");
    }
    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }
    public String getItemLabel() {
        if (null != itemLabel) {
            return itemLabel;
         }
         return getValueExpressionAsString("itemLabel");
    }
    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }
    public Object getItemValue() {
        if (null != itemValue) {
            return itemValue;
         }
         return getValueExpressionAsObject("itemValue");
    }
    public void setItemValue(Object itemValue) {
        this.itemValue = itemValue;
    }
    public String getOnClick() {
        if (null != onClick) {
            return onClick;
         }
         return getValueExpressionAsString("onClick");
    }
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }
    public String getOnMouseOver() {
        if (null != onMouseOver) {
            return onMouseOver;
         }
         return getValueExpressionAsString("onMouseOver");
    }
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }
    public String getOnMouseOut() {
        if (null != onMouseOut) {
            return onMouseOut;
         }
         return getValueExpressionAsString("onMouseOut");
    }
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }
    public String getOnFocus() {
        if (null != onFocus) {
            return onFocus;
         }
         return getValueExpressionAsString("onFocus");
    }
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }
    public String getOnBlur() {
        if (null != onBlur) {
            return onBlur;
         }
         return getValueExpressionAsString("onBlur");
    }
    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[13];
        values[0] = super.saveState(context);
        values[1] = styleClass;
        values[2] = style;
        values[3] = disabled;
        values[4] = itemLabel;
        values[5] = itemValue;
        values[6] = onClick;
        values[7] = onMouseOver;
        values[8] = onMouseOut;
        values[9] = onFocus;
        values[11] = name;
        values[12] = overrideName;
       return (values);
     }
     public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        styleClass = (String) values[1];
        style = (String) values[2];
        disabled = (String) values[3];
        itemLabel = (String) values[4];
        itemValue =  values[5];
        onClick = (String) values[6];
        onMouseOver = (String) values[7];
        onMouseOut = (String) values[8];
        onFocus = (String) values[9];
        onBlur = (String) values[10];
        name = (String) values[11];
        overrideName = (String) values[12];
     }
     public String getFamily() {
         return COMPONENT_FAMILY;
      }
 }
