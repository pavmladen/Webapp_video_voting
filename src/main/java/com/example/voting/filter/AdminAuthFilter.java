package com.example.voting.filter;

import com.example.voting.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();

        if (path.equals("/admin/login") || path.equals("/admin/logout")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        chain.doFilter(request, response);
    }
}