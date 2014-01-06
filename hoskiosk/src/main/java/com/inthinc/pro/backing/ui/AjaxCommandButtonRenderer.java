package com.inthinc.pro.backing.ui;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.UIAjaxCommandButton;
import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;

public class AjaxCommandButtonRenderer extends org.ajax4jsf.renderkit.html.CommandButtonRenderer
{
    @Override
    protected void doEncodeBegin(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException
    {
        if (isImage(component))
            super.doEncodeBegin(writer, context, component);
        else
        {
            ComponentVariables variables = ComponentsVariableResolver.getVariables(this, component);
            doEncodeBegin(writer, context, (UIAjaxCommandButton) component, variables);
            ComponentsVariableResolver.removeVariables(this, component);
        }
    }

    public void doEncodeBegin(ResponseWriter writer, FacesContext context, UIAjaxCommandButton component, ComponentVariables variables) throws IOException
    {
        String clientId = component.getClientId(context);
        writer.startElement("button", component);
        getUtils().writeAttribute(writer, "class", component.getAttributes().get("styleClass"));
        getUtils().writeAttribute(writer, "id", clientId);
        getUtils().writeAttribute(writer, "name", clientId);
        getUtils().writeAttribute(writer, "onclick", getOnClick(context, component));
        getUtils().writeAttribute(writer, "value", getValue(component));
        getUtils().encodeAttributesFromArray(
                context,
                component,
                new String[] { "accept", "accesskey", "align", "alt", "checked", "dir", "disabled", "lang", "maxlength", "onblur", "onchange", "ondblclick", "onfocus",
                        "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "readonly", "size", "src",
                        "style", "tabindex", "title", "usemap", "xml:lang" });

        String type = (String) component.getAttributes().get("type");
        if (null != type)
            writer.writeAttribute("type", type.toLowerCase(), "type");
        else
            writer.writeAttribute("type", "button", "type");
    }

    @Override
    public void doEncodeEnd(ResponseWriter writer, FacesContext context, UIAjaxCommandButton component, ComponentVariables variables) throws IOException
    {
        if (isImage(component))
            super.doEncodeEnd(writer, context, component, variables);
        else
            writer.endElement("button");
    }

    protected boolean isImage(UIComponent component)
    {
        return component.getAttributes().get("image") != null;
    }
}
