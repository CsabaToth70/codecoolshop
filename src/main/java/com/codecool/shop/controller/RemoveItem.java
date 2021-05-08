package com.codecool.shop.controller;

import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/remove"})
public class RemoveItem extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemI = req.getParameter("id");
        if (itemI != null) {
            int itemID = Integer.parseInt(itemI);
            removeItem(itemID);
            itemI = null;
        }
        CartController.subtotal = 0;
        resp.sendRedirect("http://localhost:8888/cart");
    }

    public void removeItem(int itemID) {
        int remove = 0;
        for (Product item : ProductController.cart) {
            if (item.getId() == itemID) {
                remove = ProductController.cart.indexOf(item);
            }
        }
        ProductController.cart.remove(remove);
    }
}
