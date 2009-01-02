package com.inthinc.pro.backing.ui;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.ajax4jsf.component.html.HtmlAjaxCommandButton;

public class CommandButton extends HtmlAjaxCommandButton
{
    protected static final Renderer renderer = new CommandButtonRenderer();

    @Override
    protected Renderer getRenderer(FacesContext context)
    {
        return renderer;
    }
}
