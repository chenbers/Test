package com.inthinc.pro.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.security.userdetails.ProUser;

public class ProAuthenticationProcessingFilter extends AuthenticationProcessingFilter
{
    private static final Logger logger = Logger.getLogger(ProAuthenticationProcessingFilter.class);

    private GroupDAO groupDAO;
    private UserDAO userDAO;

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

//    @Override
//    public String getDefaultTargetUrl()
//    {
//        ProUser proUser = (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String url = getTargetUrlFromGroupHierarchy(proUser.getGroupHierarchy());
//
//        if (url != null)
//        {
//            return url;
//        }
//        logger.debug("Default Target URL: " + url);
//        return super.getDefaultTargetUrl();
//    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws java.io.IOException {
//        BadCredentialsException.class
        if(!(failed instanceof BadCredentialsException) && (failed.getAuthentication() != null) && (failed.getAuthentication().getPrincipal() instanceof ProUser)) {
            ProUser proUser = (ProUser)failed.getAuthentication().getPrincipal();
            proUser.getUser().setStatus(Status.INACTIVE);
            userDAO.update(proUser.getUser());
        }
    }
    @Override
    protected void onSuccessfulAuthentication( HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        ProUser proUser; 
        if(authResult.getPrincipal() instanceof ProUser){
            proUser = (ProUser)authResult.getPrincipal();
            proUser.getUser().setLastLogin(new Date());
            userDAO.update(proUser.getUser());
        }        
    }
    
    
    
    private String getTargetUrlFromGroupHierarchy(GroupHierarchy groupHierarchy)
    {
        GroupLevel groupLevel = GroupLevel.getGroupLevel(groupHierarchy.getTopGroup());

        return groupLevel.getUrl() + "?groupID=" + groupHierarchy.getTopGroup().getGroupID().toString();

    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // private GroupHierarchy initGroupHierarchy()
    // {
    // ProUser proUser = (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //
    // Integer topGroupID = proUser.getUser().getGroupID();
    //
    // // TODO: re-factor when back end methods getGroupsByGroupIDDeep is available
    // Group topGroup = groupDAO.findByID(proUser.getUser().getGroupID());
    // List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroupID);
    //
    // GroupHierarchy groupHierarchy = new GroupHierarchy(groupList);
    //
    // proUser.setGroupHierarchy(groupHierarchy);
    //
    // return groupHierarchy;
    // }
}
