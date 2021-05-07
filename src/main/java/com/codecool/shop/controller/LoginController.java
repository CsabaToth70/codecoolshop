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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UserDao userDataStore = UserDaoJdbc.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/login.html", context, resp.getWriter());

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String token = passwordAuthentication.hash(password);

            ShopDatabaseManager shopDatabaseManager = Initializer.shopDatabaseManager;

            System.out.println("token based on given email:" + token);
            System.out.println("read token from sql table: " + shopDatabaseManager.getUser(email).getToken_for_authentication());

            if (passwordAuthentication.authenticate(password, shopDatabaseManager.getUser(email).getToken_for_authentication())) {
                String username = shopDatabaseManager.getUser(email).getName();

                // todo add user - email - to the session!
                HttpSession session = request.getSession();


                // These lines just a practice to display session status information
//                PrintWriter writer = response.getWriter();
//                writer.println("Session ID: " + session.getId());
//                writer.println("Creation Time: " + new Date(session.getCreationTime()));
//                writer. println("Last Accessed Time: " + new Date(session.getLastAccessedTime()));

                session.setAttribute("username", username);
                session.setAttribute("email", email);

                response.sendRedirect("http://localhost:8888");
            } else {
                response.sendRedirect("http://localhost:8888/login");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
