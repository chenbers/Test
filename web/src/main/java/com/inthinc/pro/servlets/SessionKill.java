package com.inthinc.pro.servlets;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.logout.SecurityContextLogoutHandler;
import org.springframework.security.ui.rememberme.AbstractRememberMeServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionKill extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(true);

//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response, null);
//        SecurityContextHolder.clearContext();
//        session.invalidate();
                response.sendRedirect("/logout");
        return;
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doGet(req, resp);
    }
}
