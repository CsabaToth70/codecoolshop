package com.codecool.shop.controller;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private int categoryID = 0;
    private int supplierID = 0;
    public static ArrayList<Product> cart = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        context.setVariable("categoryList", Initializer.categoryList);
        context.setVariable("supplierList", Initializer.supplierList);
//        context.setVariable("username", context.getSession().getAttribute("username"));
        engine.process("product/index.html", context, resp.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dropdown = request.getParameter("dropdown");
        String addToCart = request.getParameter("addToCart");

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        if (addToCart != null) {
            int itemID = Integer.parseInt(request.getParameter("addToCart"));
            ProductManager productManager = new ProductManager(productDataStore);
            productManager.addToCart(itemID, cart);
            if (supplierID != 0) {
                context.setVariable("products", productDataStore.getBy(productSupplierDataStore.find(supplierID)));
            } else if (categoryID != 0) {
                context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryID)));
            } else {
                context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
            }
        } else {
            if (dropdown.equals("category")) {
                int categoryID = Integer.parseInt(request.getParameter("categoryList"));
                this.categoryID = categoryID;
                this.supplierID = 0;
                context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryID)));
            } else if (dropdown.equals("supplier")) {
                int supplierID = Integer.parseInt(request.getParameter("supplierList"));
                this.supplierID = supplierID;
                this.categoryID = 0;
                context.setVariable("products", productDataStore.getBy(productSupplierDataStore.find(supplierID)));
            }
        }

        context.setVariable("supplierList", Initializer.supplierList);
        context.setVariable("categoryList", Initializer.categoryList);
        engine.process("product/index.html", context, response.getWriter());
    }


}
