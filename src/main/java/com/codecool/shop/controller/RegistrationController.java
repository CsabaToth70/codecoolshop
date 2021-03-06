package com.codecool.shop.controller;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ShopDatabaseManager;
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

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {
    private static final String SYSTEM_ADMIN_EMAIL_1 = "lesitocsa@gmail.com";
    private static final String SYSTEM_ADMIN_EMAIL_2 = "polli.burjan@gmail.com";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/registration.html", context, resp.getWriter());

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user;
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String token = passwordAuthentication.hash(password);
            HttpSession session = request.getSession();
            response.sendRedirect("http://localhost:8888/");
            if (isSystemAdmin(email) && !isAlreadyRegistered(email) && !sysadminPositionsFilledIn()) {
                session.removeAttribute("login_message");
                user = new User(name, email, token, true);
            } else {
                if (email.equals("")) {
                    session.setAttribute("login_message", "Incomplete email or password!");
                    response.sendRedirect("http://localhost:8888/registration");
                } else if (isAlreadyRegistered(email)) {
                    session.setAttribute("login_message", "We already have a registered user with the email address you provided");
                    response.sendRedirect("http://localhost:8888/");
                }
                session.removeAttribute("login_message");
                user = new User(name, email, token, false);
            }
            ShopDatabaseManager shopDatabaseManager = Initializer.shopDatabaseManager;
            shopDatabaseManager.saveUser(user);
            if (!user.isSysadmin()) {
                JavaMailUtil.SendEmail(email, Initializer.getShopEmail(), Initializer.getShopEmailPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean sysadminPositionsFilledIn() {
        boolean sysadminPositionsFilledIn = false;
        ShopDatabaseManager shopDatabaseManager = Initializer.shopDatabaseManager;
        sysadminPositionsFilledIn = shopDatabaseManager.getUser(SYSTEM_ADMIN_EMAIL_1) != null;
        return sysadminPositionsFilledIn && shopDatabaseManager.getUser(SYSTEM_ADMIN_EMAIL_2) != null;

    }

    private boolean isAlreadyRegistered(String email) {
        ShopDatabaseManager shopDatabaseManager = Initializer.shopDatabaseManager;
        if (shopDatabaseManager.getUser(email) != null) {
            return shopDatabaseManager.getUser(email).getEmail().equals(email);
        }
        return false;
    }

    private boolean isSystemAdmin(String email) {
        if (SYSTEM_ADMIN_EMAIL_1.equals(email) || SYSTEM_ADMIN_EMAIL_2.equals(email)) {
            return true;
        }
        return false;
    }

    public static String getSystemAdminEmail1() {
        return SYSTEM_ADMIN_EMAIL_1;
    }

    public static String getSystemAdminEmail2() {
        return SYSTEM_ADMIN_EMAIL_2;
    }
}
