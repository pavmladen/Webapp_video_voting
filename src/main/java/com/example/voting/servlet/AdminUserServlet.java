package com.example.voting.servlet;

import com.example.voting.dao.UserDao;
import com.example.voting.dao.RoleDao;
import com.example.voting.model.User;
import com.example.voting.model.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            User user = userDao.findById(id);
            List<Role> allRoles = roleDao.findAll();
            req.setAttribute("user", user);
            req.setAttribute("roles", allRoles);
            req.setAttribute("isEdit", true);
            req.getRequestDispatcher("/WEB-INF/views/user_form.jsp").forward(req, resp);
            return;
        }

        if("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            userDao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        if ("add".equals(action)) {
            List<Role> allRoles = roleDao.findAll();
            req.setAttribute("roles", allRoles);
            req.setAttribute("isEdit", false);
            req.getRequestDispatcher("/WEB-INF/views/user_form.jsp").forward(req, resp);
            return;
        }

        List<User> users = userDao.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        Long id = (idParam != null && !idParam.isEmpty()) ? Long.parseLong(idParam) : null;

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] roleIds = req.getParameterValues("roleIds");

        User user = (id != null) ? userDao.findById(id) : new User();
        user.setUsername(username);
        user.setPassword(password); 

        user.getRoles().clear(); 
        if (roleIds != null) {
            for (String roleIdStr : roleIds) {
                Long roleId = Long.parseLong(roleIdStr);
                Role role = roleDao.findById(roleId);
                if (role != null) {
                    user.getRoles().add(role);
                }
            }
        }

        if (id == null) {
            userDao.create(user);
        } else {
            userDao.update(user);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }

}