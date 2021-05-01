package com.codecool.shop.controller;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
//import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
//import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.model.AdminLog;
import com.codecool.shop.user.User;
import com.sun.source.doctree.SeeTree;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {
    private final Set<User> users = new HashSet<User>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UserDao userDataStore = UserDaoJdbc.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/registration.html", context, resp.getWriter());

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            String name = request.getParameter("name");
            String email = request.getParameter("email");
//            JPasswordField passwordField = new JPasswordField(10);

            String password = request.getParameter("password");

//            passwordField.setActionCommand(OK);
//            passwordField.addActionListener(password);
            response.sendRedirect("http://localhost:8888/");

            User user = new User(name, email, password);

            System.out.println(name);
            System.out.println(email);
            System.out.println(password);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
