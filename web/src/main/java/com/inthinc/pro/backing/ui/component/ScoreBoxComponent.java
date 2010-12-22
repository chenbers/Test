package com.inthinc.pro.backing.ui.component;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.security.userdetails.ProUser;

/**
 * Control for displaying score with the appropriate size and color.
 */

public class ScoreBoxComponent extends HtmlOutputText
{
    public static final String COMPONENT_TYPE = "ScoreBoxDisplay";
    public static final String OUTER_DIV = "<div style='display: table; #position: relative; overflow: hidden; width:100%;height:100%;'>";
    public static final String MIDDLE_DIV = "<div style='#position: relative;  #top: 50%; display: table-cell; vertical-align: middle;width:100%;'>";
    public static final String INNER_DIV = "<div style='#position: relative;  #top: -50%; width:100%;text-align:center'>";
    public static final String DEFAULT_SIZE = "x_sm";
    public static final String sizes[] = {
        "xx_sm",   //0
        "x_sm",   //1
        "sm",   //2
        "med",   //3
        "lg",   //4
        "x_lg"  //5
    };

    ResponseWriter innerHtml; 
    public ScoreBoxComponent()
    {
        super();
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException
    {
        ResponseWriter out = context.getResponseWriter();

        StringBuffer buffer = new StringBuffer();
        String scoreCssClass = "score_" + getSuffix(getValue());
        String sizeCssClass = "score_" + getSize(); 
        buffer.append("<span><div class='scoreBox'>");
        buffer.append("<div class='score " + scoreCssClass + " " + sizeCssClass + "'>");
        buffer.append(OUTER_DIV);
        buffer.append(MIDDLE_DIV);
        buffer.append(INNER_DIV);
        out.write(buffer.toString());
        
    }
    
    @Override
    public void encodeChildren(FacesContext context) throws IOException
    {
        super.encodeChildren(context);
        
    }
    @Override
    public void encodeEnd(FacesContext context) throws IOException
    {
        ResponseWriter out = context.getResponseWriter();
        StringBuffer buffer = new StringBuffer();
        buffer.append("</div></div></div></div></div></span>");
        out.write(buffer.toString());
    }

    private String getSize() {
        Object sizeAttr = getAttributes().get("size");
        
        if (sizeAttr == null)
            return DEFAULT_SIZE;
        
        int sizeInt = 0;
        try {
            sizeInt = Integer.valueOf(sizeAttr.toString());
        }
        catch (NumberFormatException ex) {
            
        }
        if (sizeInt < 0 || sizeInt > 5)
            return DEFAULT_SIZE;
        return sizes[sizeInt];
    }

    private String getDisplayValue(Object value) {
        if (value == null)
            return "N/A";
        
        NumberFormat format = NumberFormat.getNumberInstance(getLocale());
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
    
        return format.format(Integer.class.cast(value).doubleValue()/10.0d);
    }
    
    private ProUser getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof ProUser)
            return (ProUser) principal;
        return null;
    }
    private Locale getLocale() {
        if (getUser() != null)
            return getUser().getUser().getPerson().getLocale();
         return Locale.getDefault();
    }


    private String getSuffix(Object value) {
        if (value == null)
            return "na";
        int score = 0;
        try {
            score = Integer.valueOf(value.toString());
        }
        catch (NumberFormatException ex){
            return "na";
        }
        
        if(score < 11)
            return "1";
        if(score < 21)
            return "2";
        if(score < 31)
            return "3";
        if(score < 41)
            return "4";
        return "5";

        
    }



}
