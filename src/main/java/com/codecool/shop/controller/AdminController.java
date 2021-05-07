package com.codecool.shop.controller;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.util.PasswordAuthentication;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/admin.html", context, resp.getWriter());

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
            HttpSession session = request.getSession();
            if (shopDatabaseManager.getUser(email) != null) {
                if (passwordAuthentication.authenticate(password, shopDatabaseManager.getUser(email).getToken_for_authentication())) {
                    String username = shopDatabaseManager.getUser(email).getName();
                    Initializer.setShopEmail(request.getParameter("shop_email"));
                    Initializer.setShopEmailPassword(request.getParameter("shop_email_password"));
                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.removeAttribute("login_message");
                    response.sendRedirect("http://localhost:8888");
                } else {
                    session.setAttribute("login_message", "Wrong email or password!");
                    response.sendRedirect("http://localhost:8888/admin");
                }
            } else {
                session.setAttribute("login_message", "Wrong email or password!");
                response.sendRedirect("http://localhost:8888/admin");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}