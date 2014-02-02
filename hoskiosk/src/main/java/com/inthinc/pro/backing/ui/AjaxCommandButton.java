package com.inthinc.pro.backing.ui;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.ajax4jsf.component.html.HtmlAjaxCommandButton;

public class AjaxCommandButton extends HtmlAjaxCommandButton
{
    protected static final Renderer renderer = new AjaxCommandButtonRenderer();

    @Override
    protected Renderer getRenderer(FacesContext context)
    {
        return renderer;
    }
}
