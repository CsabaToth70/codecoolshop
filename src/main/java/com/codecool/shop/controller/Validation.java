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
import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/validation"})
public class Validation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("order", CheckoutController.order);
        context.setVariable("subtotal", CartController.subtotal);
        engine.process("product/validation.html", context, resp.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO: Make JSON with order from CheckoutController
        createJsonFile(CheckoutController.order);
        try {
            getOrderFromFile("shopping_order_" + CheckoutController.order.getId() + ".txt");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CartController.subtotal = 0;
        ProductController.cart.clear();
        response.sendRedirect("http://localhost:8888/");
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
}
