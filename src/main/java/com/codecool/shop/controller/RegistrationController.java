package com.codecool.shop.controller;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.config.TemplateEngineUtil;
//import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShopDatabaseManager;
//import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.user.User;
import com.codecool.shop.util.PasswordAuthentication;
import com.codecool.shop.util.JavaMailUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {

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
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String token = passwordAuthentication.hash(password);
            response.sendRedirect("http://localhost:8888/");

            User user = new User(name, email, token);
            ShopDatabaseManager shopDatabaseManager = Initializer.shopDatabaseManager;
            shopDatabaseManager.saveUser(user);
            String userMail = Initializer.getTestUserEmail(); // ToDo: read user email address from SQL table!
            JavaMailUtil.SendEmail(userMail, Initializer.getShopPassword());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
