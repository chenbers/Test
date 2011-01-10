package com.inthinc.pro.backing;

import java.util.Locale;

import javax.faces.context.FacesContext;

import com.inthinc.pro.backing.model.HelpConfigProperties;
import com.inthinc.pro.util.DebugUtil;
import com.ocpsoft.pretty.PrettyContext;

public class HelpBean extends BaseBean {

	private String path;
	
	private HelpConfigProperties helpConfigProperties;


    public String getPath() {
		String lang = this.getLocale().getLanguage();
		// TODO: remove this when romanian help is available
		lang = Locale.ENGLISH.getLanguage();
		path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/secured/lochelp/" + lang + "/"  + lang + "/";
        
//		System.out.println("PrettyContext.getCurrentInstance() is null ? " + (PrettyContext.getCurrentInstance() == null));
		String prettyID = (PrettyContext.getCurrentInstance() == null || PrettyContext.getCurrentInstance().getCurrentMapping() == null) ? null : PrettyContext.getCurrentInstance().getCurrentMapping().getId();
		if (prettyID == null || getHelpConfigProperties().get(prettyID) == null)
		    return path+getHelpConfigProperties().getDefault();
		
		return path+getHelpConfigProperties().get(prettyID);
	}

	public void setPath(String path) {
		this.path = path;
	}
	
    public HelpConfigProperties getHelpConfigProperties() {
        return helpConfigProperties;
    }

    public void setHelpConfigProperties(HelpConfigProperties helpConfigProperties) {
        this.helpConfigProperties = helpConfigProperties;
    }
}
