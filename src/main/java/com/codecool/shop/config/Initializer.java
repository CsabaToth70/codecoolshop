package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier wacom = new Supplier("Wacom", "Drawing Tablets");
        supplierDataStore.add(wacom);
        Supplier asus = new Supplier("Asus", "Computers");
        supplierDataStore.add(wacom);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory penComputer = new ProductCategory("Pen Computer", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory penDisplay = new ProductCategory("Pen Display", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory penTablet = new ProductCategory("Pen Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        productCategoryDataStore.add(laptop);
        productCategoryDataStore.add(penComputer);
        productCategoryDataStore.add(penDisplay);
        productCategoryDataStore.add(penTablet);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Asus X515MA-BR228T Notebook", 605, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, amazon));
        productDataStore.add(new Product("Cintiq 22HD", 1200, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penDisplay, wacom));
        productDataStore.add(new Product("Lenovo IdeaPad YOGA S940", 1700, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, lenovo));
        productDataStore.add(new Product("Lenovo ThinkPad T14s", 800, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, lenovo));
        productDataStore.add(new Product("Lenovo Yoga 9", 1540, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, lenovo));
        productDataStore.add(new Product("Lenovo Yoga 9", 1540, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, lenovo));
        productDataStore.add(new Product("MobileStudio Pro 16", 3500, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penComputer, wacom));
        productDataStore.add(new Product("One by Wacom", 70, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penTablet, wacom));
        productDataStore.add(new Product("Cintiq 16", 650, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penDisplay, wacom));
        productDataStore.add(new Product("Cintiq Pro 32", 3300, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penDisplay, wacom));
        productDataStore.add(new Product("Intuos Pro Medium", 380, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penTablet, wacom));
        productDataStore.add(new Product("Intuos Small", 80, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penTablet, wacom));
        productDataStore.add(new Product("MobileStudio Pro 13", 2600, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", penComputer, wacom));

    }
}
