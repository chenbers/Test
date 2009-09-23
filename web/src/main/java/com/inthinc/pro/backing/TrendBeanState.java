package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.util.MessageUtil;

public class TrendBeanState extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7822673653887488933L;

	private Boolean maximized;
    
    private Map<Integer, Boolean> flyout;
    
    private Integer groupID;
    private Group group;

    public TrendBeanState()
    {
    }
        
    public Boolean getMaximized() {
    	if (maximized == null)
    		maximized = Boolean.FALSE;
		return maximized;
	}

	public void setMaximized(Boolean maximized) {
		this.maximized = maximized;
	}
    public Map<Integer, Boolean> getFlyout()
    {
    	if (flyout == null)
    		flyout = new HashMap<Integer, Boolean>();
        return flyout;
    }

    public void setFlyout(Map<Integer, Boolean> flyout)
    {
        this.flyout = flyout;
    }
    
    public Integer getGroupID() {
    	if (groupID == null)
    		groupID = getUser().getGroupID();
		return groupID;
	}

	public void setGroupID(Integer newGroupID) {
		if (groupID == null || !groupID.equals(newGroupID))
		{
			setFlyout(null);
		}
		groupID = newGroupID;
        group = getGroupHierarchy().getGroup(groupID);
        if (group == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getLocale()));

	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}



}
