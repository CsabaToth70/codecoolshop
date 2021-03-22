package com.codecool.shop.controller;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.PropertyConfigurator;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
    private ArrayList<Order> orders = new ArrayList<>();
    private int orderID = 1;
    public static Order order;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/checkout.html", context, resp.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PropertyConfigurator.configure("log4j.properties");
        boolean allValid = true;
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String zipCode = request.getParameter("zipCode");

        boolean checkFirstName = getDigits(firstName);
        if (!checkFirstName) {
            context.setVariable("firstName", firstName);
        } else {
            allValid = false;
        }

        boolean checkLastName = getDigits(lastName);
        if (!checkLastName) {
            context.setVariable("lastName", lastName);
        } else {
            allValid = false;
        }

        boolean checkPhone = getDigits(phoneNumber);
        if (checkPhone) {
            context.setVariable("phoneNumber", phoneNumber);
        } else {
            allValid = false;
        }

        boolean checkCity = getDigits(city);
        if (!checkCity) {
            context.setVariable("city", city);
        } else {
            allValid = false;
        }

        if (zipCode.length() == 4) {
            context.setVariable("zipCode", zipCode);
        } else {
            allValid = false;
        }

        if (allValid) {
            int zipC = Integer.parseInt(zipCode);
            orderIDGenerator(firstName, lastName, email, phoneNumber, country, city, address, zipC);
            logger.info("Successful checkout.");
            response.sendRedirect("http://localhost:8888/payment");
        } else {
            context.setVariable("email", email);
            context.setVariable("country", country);
            engine.process("product/checkout.html", context, response.getWriter());
        }

    }

    public boolean getDigits(String input) {
        boolean number = false;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                number = true;
            }
            else {
                return false;
            }
        }
        return number;
    }

    public void orderIDGenerator(String firstName, String lastName, String email, String phoneNumber, String country, String city, String address, int zipC) {
        Order order = new Order(orderID, firstName, lastName, email, phoneNumber, country, city, address, zipC, ProductController.cart, CartController.subtotal);
        CheckoutController.order = order;
        orders.add(order);
        orderID = orderID + 1;
    }
}
