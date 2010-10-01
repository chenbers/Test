package com.inthinc.pro.backing.ui;

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * 
 * @author mstrong
 *
 * Control for adding css conditional comments around css styles for use with IE 6 and IE 7
 * 
 *
 */

public class UIConditionalCSS extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "conditionalCSS";
    public static final String COMPONENT_FAMILY = "facelets";

    public static final String COMMENT_BEGIN_1 = "\n<!--[if ";
    public static final String COMMENT_BEGIN_2 = "]>";
    public static final String COMMENT_END = "<![endif]-->";
    
    private String browserVersion = null;

    public UIConditionalCSS()
    {
        super();
        this.setTransient(true);
        this.setRendered(true);
        this.setRendererType(null);
    }

    public boolean getRendersChildren()
    {
        return false;
    }

    public void encodeBegin(FacesContext context) throws IOException
    {
        ResponseWriter out = context.getResponseWriter();
        out.write(COMMENT_BEGIN_1 + browserVersion + COMMENT_BEGIN_2);
    }

    public void encodeEnd(FacesContext context) throws IOException
    {
        ResponseWriter out = context.getResponseWriter();
        out.write(COMMENT_END);
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setBrowserVersion(String browserVersion)
    {
        this.browserVersion = browserVersion;
    }

    public String getBrowserVersion()
    {
        return browserVersion;
    }

}
