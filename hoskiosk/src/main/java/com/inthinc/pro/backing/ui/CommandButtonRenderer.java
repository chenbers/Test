package com.inthinc.pro.backing.ui;

import java.io.IOException;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.ButtonRenderer;

public class CommandButtonRenderer extends ButtonRenderer
{
    private static final String ATTRIBUTES[];

    static
    {
        ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.COMMANDBUTTON);
    }

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        if (isImage(component))
            super.decode(context, component);
        else
        {
            rendererParamsNotNull(context, component);
            if (!shouldDecode(component))
                return;
            if (wasClicked(context, component) && !isReset(component))
                component.queueEvent(new ActionEvent(component));
        }
    }

    protected static boolean wasClicked(FacesContext context, UIComponent component)
    {
        String proxyId = getProxyId(component.getClientId(context));
        return "true".equals(context.getExternalContext().getRequestParameterMap().get(proxyId));
    }

    protected static boolean isReset(UIComponent component)
    {
        return "reset".equals(component.getAttributes().get("type"));
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
        if (isImage(component))
            super.encodeBegin(context, component);
        else
        {
            rendererParamsNotNull(context, component);
            if (!shouldEncode(component))
                return;
            String type = (String) component.getAttributes().get("type");
            if (type == null)
                type = "submit";
            ResponseWriter writer = context.getResponseWriter();
            if (writer == null)
                throw new AssertionError();
            String value = "";
            Object valueObj = ((UICommand) component).getValue();
            if (valueObj != null)
                value = valueObj.toString();
            String clientId = component.getClientId(context);

            // hidden proxy, to avoid IE bugs
            String proxyId = getProxyId(clientId);
            writer.startElement("input", component);
            writer.writeAttribute("type", "hidden", "type");
            writer.writeAttribute("name", proxyId, "name");
            writer.writeAttribute("id", proxyId, "id");
            writer.endElement("input");

            // button
            writer.startElement("button", component);
            writeIdAttributeIfNecessary(context, writer, component);
            writer.writeAttribute("type", type, "type");
            writer.writeAttribute("name", clientId, "clientId");
            writer.writeAttribute("value", value, "value");
            writer.writeAttribute("onclick", buildOnClick(context, component), "onclick");
            RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
            RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
            String styleClass = (String) component.getAttributes().get("styleClass");
            if (styleClass != null && styleClass.length() > 0)
                writer.writeAttribute("class", styleClass, "styleClass");
        }
    }

    protected static String buildOnClick(FacesContext context, UIComponent component)
    {
        StringBuilder onClick = new StringBuilder();
        String curClick = (String) component.getAttributes().get("onclick");
        if (curClick != null)
        {
            curClick.replaceAll("return (true|false);?", "");
            onClick.append(curClick);
            onClick.append(';');
        }
        String clientId = component.getClientId(context);
        if (!isBooleanAttribute(component, "disabled"))
            onClick.append("document.getElementById('" + getProxyId(clientId) + "').value = 'true'");
        else
            onClick = new StringBuilder("return false;");
        return onClick.toString();
    }

    protected static String getProxyId(String clientId)
    {
        return clientId + ":proxy";
    }

    protected static boolean isBooleanAttribute(UIComponent component, String name)
    {
        Object attrValue = component.getAttributes().get(name);
        boolean result = false;
        if (null != attrValue)
            if (attrValue instanceof String)
                result = "true".equalsIgnoreCase((String) attrValue);
            else
                result = Boolean.TRUE.equals(attrValue);
        return result;
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        if (isImage(component))
            super.encodeEnd(context, component);
        else
            context.getResponseWriter().endElement("button");
    }

    protected static boolean isImage(UIComponent component)
    {
        return component.getAttributes().get("image") != null;
    }
}
