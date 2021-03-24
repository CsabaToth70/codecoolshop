package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.AdminLog;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("paymentMethod", "None");
        context.setVariable("subtotal", CartController.subtotal);
        engine.process("product/payment.html", context, resp.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PropertyConfigurator.configure("log4j.properties");
        String paymentMethod = request.getParameter("paymentMethod");
        if (paymentMethod != null) {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            context.setVariable("paymentMethod", paymentMethod);
            context.setVariable("subtotal", CartController.subtotal);
            engine.process("product/payment.html", context, response.getWriter());
        } else {
            String method = request.getParameter("method");
            if (method.equals("creditCard")) {
                checkCredit();
                logger.info("Payment with credit card.");
                AdminLog logMessage = new AdminLog(CheckoutController.currentDate(), "INFO", "Payment with credit card.");
                CheckoutController.objectOutputStream.writeObject(logMessage);
                response.sendRedirect("http://localhost:8888/validation");
            } else if (method.equals("payPal")) {
                logger.info("Payment with PayPal.");
                AdminLog logMessage = new AdminLog(CheckoutController.currentDate(), "INFO", "Payment with PayPal");
                CheckoutController.objectOutputStream.writeObject(logMessage);
                response.sendRedirect("http://localhost:8888/validation");
            }
        }
    }

    public void checkCredit() {

    }

}