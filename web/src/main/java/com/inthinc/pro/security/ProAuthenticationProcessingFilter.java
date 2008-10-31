package com.inthinc.pro.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.security.userdetails.ProUser;

public class ProAuthenticationProcessingFilter extends AuthenticationProcessingFilter
{
    private static final Logger logger = Logger.getLogger(ProAuthenticationProcessingFilter.class);
    
    private GroupDAO groupDAO;
    
    @Override
    public String getDefaultTargetUrl()
    {
        GroupHierarchy groupHierarchy = initGroupHierarchy();
        
        String url = getTargetUrlFromGroupHierarchy(groupHierarchy);

        if (url != null)
        {
            return url;
        }
        logger.debug("Default Target URL: " + url);        
        return super.getDefaultTargetUrl();
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }
    
    private GroupHierarchy initGroupHierarchy()
    {
        ProUser proUser = (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Integer topGroupID = proUser.getUser().getGroupID();
        
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroupID);
        
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupList);
        
        proUser.setGroupHierarchy(groupHierarchy);
        
        return groupHierarchy;
    }
    
    private String getTargetUrlFromGroupHierarchy(GroupHierarchy groupHierarchy )
    {
        GroupLevel groupLevel = groupHierarchy.getGroupLevel(groupHierarchy.getTopGroup());
        
        return groupLevel.getUrl();
        
        
    }

}
