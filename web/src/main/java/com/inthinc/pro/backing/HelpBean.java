package com.inthinc.pro.backing;

import javax.faces.context.FacesContext;

public class HelpBean extends BaseBean {

	String path;

	public String getPath() {
		if (path == null)
		{
			String lang = this.getLocale().getLanguage();
			path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/secured/lochelp/" + lang + "/"  + lang + "/";  

		}
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
