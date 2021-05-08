package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.AdminLog;
import com.codecool.shop.model.Order;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
    private ArrayList<Order> orders = new ArrayList<>();
    private int orderID = 1;
    public static Order order;
    public static ObjectOutputStream objectOutputStream;
    public static String logName;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PropertyConfigurator.configure("log4j.properties");
        try {
            setUpJSON();
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            engine.process("product/checkout.html", context, resp.getWriter());
        } catch (IOException e) {
            logger.warn("Problem connecting to checkout page (IOException)");
            AdminLog logMessage = new AdminLog(currentDate(), "WARN", "Problem connecting to checkout page (IOException)");
            objectOutputStream.writeObject(logMessage);
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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

            context.setVariable("address", address);
            boolean valid = checkData(context, firstName, lastName, phoneNumber, city, zipCode);

            if (valid) {
                int zipC = Integer.parseInt(zipCode);
                orderIDGenerator(firstName, lastName, email, phoneNumber, country, city, address, zipC);
                logger.info("Successful checkout.");
                AdminLog logMessage = new AdminLog(currentDate(), "INFO", "Successful checkout.");
                objectOutputStream.writeObject(logMessage);
                response.sendRedirect("http://localhost:8888/payment");
            } else {
                context.setVariable("email", email);
                context.setVariable("country", country);
                logger.warn("Unsuccessful checkout.");
                AdminLog logMessage = new AdminLog(currentDate(), "WARN", "Unsuccessful checkout.");
                objectOutputStream.writeObject(logMessage);
                engine.process("product/checkout.html", context, response.getWriter());
            }
        } catch (IOException e) {
            logger.warn("Problem connecting to checkout page or redirecting to payment page(IOException)");
            AdminLog logMessage = new AdminLog(currentDate(), "WARN", "Problem connecting to checkout page or redirecting to payment page(IOException)");
            objectOutputStream.writeObject(logMessage);
            e.printStackTrace();
        }

    }

    public boolean getDigits(String input) {
        boolean number = false;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                number = true;
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

    public void setUpJSON() throws IOException {
        try {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String name = orderID + "-" + formatter.format(date) + ".txt";
            logName = name;
            FileOutputStream fileOutputStream = new FileOutputStream(name);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (IOException e) {
            logger.warn("Unsuccessful admin log creation (IOException)");
            e.printStackTrace();
        }
    }

    public static String currentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public boolean checkData(WebContext context, String firstName, String lastName, String phoneNumber, String city, String zipCode) {
        boolean allValid = true;
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
        return allValid;
    }
}
