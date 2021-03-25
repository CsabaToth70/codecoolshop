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
import java.io.*;

@WebServlet(urlPatterns = {"/validation"})
public class Validation extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Validation.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PropertyConfigurator.configure("log4j.properties");
        try {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("order", CheckoutController.order);
            context.setVariable("subtotal", CartController.subtotal);
            engine.process("product/validation.html", context, resp.getWriter());
        } catch (IOException e) {
            logger.warn("Problem connecting to validation page (IOException)");
            AdminLog logMessage = new AdminLog(CheckoutController.currentDate(), "WARN", "Problem connecting to validation page (IOException)");
            CheckoutController.objectOutputStream.writeObject(logMessage);
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            logger.info("Validated order");
            AdminLog logMessage = new AdminLog(CheckoutController.currentDate(), "INFO", "Validated order");
            CheckoutController.objectOutputStream.writeObject(logMessage);
            createJsonFile(CheckoutController.order);
            try {
                getOrderFromFile("shopping_order_" + CheckoutController.order.getId() + ".txt");
                getMessagesFromFile(CheckoutController.logName);
                logger.info("Order successfully converted into JSON");
                logMessage = new AdminLog(CheckoutController.currentDate(), "INFO", "Order successfully converted into JSON");
                CheckoutController.objectOutputStream.writeObject(logMessage);
            } catch (ClassNotFoundException e) {
                logger.warn("Could not covert order to JSON (ClassNotFoundException)");
                logMessage = new AdminLog(CheckoutController.currentDate(), "WARN", "Could not covert order to JSON (ClassNotFoundException)");
                CheckoutController.objectOutputStream.writeObject(logMessage);
                e.printStackTrace();
            }
            logger.info("An email was sent to the User about the Order");
            logMessage = new AdminLog(CheckoutController.currentDate(), "INFO", "An email was sent to the User about the Order");
            CheckoutController.objectOutputStream.writeObject(logMessage);
            CheckoutController.objectOutputStream.flush();
            CheckoutController.objectOutputStream.close();
            CartController.subtotal = 0;
            ProductController.cart.clear();
            response.sendRedirect("http://localhost:8888/");
        } catch (IOException e) {
            logger.warn("Problem redirecting to the main page (IOException)");
            AdminLog logMessage = new AdminLog(CheckoutController.currentDate(), "WARN", "Problem redirecting to the main page (IOException)");
            CheckoutController.objectOutputStream.writeObject(logMessage);
            e.printStackTrace();
        }
    }
    private void createJsonFile(Order order) throws IOException {
        String filename = "shopping_order_" + order.getId() + ".txt";
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(order);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
        private void getOrderFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Order order = (Order) objectInputStream.readObject();
        objectInputStream.close();
        displayOrder(order);
    }
    private void displayOrder(Order order){
        System.out.println("\nOrder's data:");
        System.out.println("Name: " + order.getFirstName() + " " + order.getLastName());
        System.out.println("E-mail: " + order.getEmail());
        System.out.println("Ordered products" + order.getItems());
        System.out.println("Total amount of ordered products: " + order.getTotal() + " USD");
    }

    private void getMessagesFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            for (;;) {
                AdminLog order = (AdminLog) objectInputStream.readObject();
                displayLog(order);
            }
        } catch (EOFException exc) {
            System.out.println("End of file reached");
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        objectInputStream.close();
    }

    private void displayLog(AdminLog order){
        System.out.println("\nLog details:");
        System.out.println("Date: " + order.getDate());
        System.out.println("Level: " + order.getLogLevel());
        System.out.println("Message: " + order.getMessage());
    }
}
