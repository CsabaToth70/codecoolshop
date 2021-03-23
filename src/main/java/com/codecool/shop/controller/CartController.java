package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    public static int subtotal = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        for (Product item : ProductController.cart) {
            int price = (int) item.getDefaultPrice() * item.getQuantity();
            subtotal = subtotal + price;
        }
        context.setVariable("subtotal", subtotal);
        context.setVariable("products", ProductController.cart);
        engine.process("product/cart.html", context, resp.getWriter());

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int itemID = Integer.parseInt(request.getParameter("itemID"));
        int itemQuantity = Integer.parseInt(request.getParameter("quantity"));
        ProductDao productDataStore = ProductDaoMem.getInstance();
        Product product = productDataStore.find(itemID);
        if (itemQuantity == 0) {
            removeItem(itemID);
        }
        product.setQuantity(itemQuantity);
        doGet(request, response);

    }

    public void removeItem (int itemID) {
        int remove = 0;
        for (Product item : ProductController.cart) {
            if (item.getId() == itemID) {
                remove = ProductController.cart.indexOf(item);
            }
        }
        ProductController.cart.remove(remove);
        subtotal = 0;
    }
}
