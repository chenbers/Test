package com.inthinc.pro.backing;

import javax.faces.context.FacesContext;

import com.inthinc.pro.backing.model.HelpConfigProperties;
import com.ocpsoft.pretty.PrettyContext;

public class HelpBean extends BaseBean {

	private String subID;
	


    private HelpConfigProperties helpConfigProperties;

    private static final String BASE_HELP_FILE = "/secured/help/WebHelp/inthincPortalUserGuide.htm";
    private static final String DEFAULT = "default";

    public String getPath() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + BASE_HELP_FILE;
	}

    public String getMapID() {
        
        String prettyID = (PrettyContext.getCurrentInstance() == null || PrettyContext.getCurrentInstance().getCurrentMapping() == null) ? null : PrettyContext.getCurrentInstance().getCurrentMapping().getId();
//System.out.println("prettyID: " + prettyID + " subID: " + getSubID());        
        
        if (prettyID == null || getHelpConfigProperties().get(prettyID) == null)
            return getHelpConfigProperties().get(DEFAULT).toString();
        
        if (getSubID() != null && getHelpConfigProperties().get(prettyID+"_"+getSubID()) != null)
            return getHelpConfigProperties().get(prettyID+"_"+getSubID()).toString();
        
        setSubID(null);
        
        return getHelpConfigProperties().get(prettyID).toString();
    }

	
    public HelpConfigProperties getHelpConfigProperties() {
        return helpConfigProperties;
    }

    public void setHelpConfigProperties(HelpConfigProperties helpConfigProperties) {
        this.helpConfigProperties = helpConfigProperties;
    }
    public String getSubID() {
        return subID;
    }

    public void setSubID(String subID) {
        this.subID = subID;
    }
    
}
