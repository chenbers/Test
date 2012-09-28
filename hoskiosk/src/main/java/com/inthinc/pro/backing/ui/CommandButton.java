package com.inthinc.pro.backing.ui;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

public class CommandButton extends HtmlCommandButton
{
    protected static final Renderer renderer = new CommandButtonRenderer();

    @Override
    protected Renderer getRenderer(FacesContext context)
    {
        return renderer;
    }
}
