package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShopDatabaseManager;
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
import java.sql.SQLException;
import java.util.ArrayList;

@WebListener
public class Initializer implements ServletContextListener {
    public static ArrayList<ProductCategory> categoryList = new ArrayList<>();
    public static ArrayList<Supplier> supplierList = new ArrayList<>();
    public static ShopDatabaseManager shopDatabaseManager = new ShopDatabaseManager();
    private static String shopEmailPassword;
    private static String shopEmail;


    //  created temporary email box for testing: codecoolshopmasters@gmail.com

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        runDatabase();


        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier wacom = new Supplier("Wacom", "Drawing Tablets");
        supplierDataStore.add(wacom);
        Supplier asus = new Supplier("Asus", "Computers");
        supplierDataStore.add(asus);
        Supplier dell = new Supplier("Dell", "Computers");
        supplierDataStore.add(dell);


        supplierList.add(amazon);
        supplierList.add(lenovo);
        supplierList.add(wacom);
        supplierList.add(asus);

        for (Supplier supplierFromMemory : supplierList) {
            if (!shopDatabaseManager.getAllSuppliers().contains(supplierFromMemory)) {
                shopDatabaseManager.addSupplier(supplierFromMemory);
            }
        }

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
//        shopDatabaseManager.addProductCategory(tablet);
//        shopDatabaseManager.addProductCategory(laptop);
//        shopDatabaseManager.addProductCategory(penComputer);
//        shopDatabaseManager.addProductCategory(penDisplay);
//        shopDatabaseManager.addProductCategory(penTablet);

        categoryList.add(tablet);
        categoryList.add(laptop);
        categoryList.add(penComputer);
        categoryList.add(penDisplay);
        categoryList.add(penTablet);

        for (ProductCategory categoryFromMemory : categoryList) {
            if (!shopDatabaseManager.getAllProductCategory().contains(categoryFromMemory)) {
                shopDatabaseManager.addProductCategory(categoryFromMemory);
            }
        }

        //setting up products and printing it
        Product product_1 = new Product("Amazon Fire", 49.9d, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon);
        Product product_2 = new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo);
        Product product_3 = new Product("Amazon Fire HD 8", 89, "USD", "Whether for work or play, ASUS X515 is the entry-level laptop that delivers powerful performance and immersive visuals.\n", tablet, amazon);
        Product product_4 = new Product("Asus X515MA-BR228T Notebook", 605, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, asus);
        Product product_5 = new Product("Cintiq 22HD", 1200, "USD", "A high-definition pen display designed for creative professionals.", penDisplay, wacom);
        Product product_6 = new Product("Lenovo IdeaPad YOGA S940", 1700, "USD", "Exceptionally designed without compromise. Run your programs faster with Premium Intel® Core™ processing.", laptop, lenovo);
        Product product_7 = new Product("Lenovo ThinkPad T14s", 800, "USD", "Heavyweight performance that won’t weigh you down. Perfect for the on-the-go or work from home professional.", laptop, lenovo);
        Product product_8 = new Product("Lenovo Yoga 9", 1540, "USD", "The 2 in 1 machine, which swells with innovative features and lives in a clean engine room, is available with an optional dark black leather cover.", laptop, lenovo);
        Product product_9 = new Product("MobileStudio Pro 16", 3500, "USD", "Your Wacom MobileStudio Pro leads the way, with powerful new features to help you stay in the zone.", penComputer, wacom);
        Product product_10 = new Product("One by Wacom", 70, "USD", "The responsive, ergonomic, pressure-sensitive pen gives you a natural feeling when sketching, drawing or annotating.", penTablet, wacom);
        Product product_11 = new Product("Cintiq 16", 650, "USD", "Creative Pen Display for On Screen Sketching, Illustrating and Drawing with 1920 x 1080 Full HD Display, Vibrant Color and Unbelievable Pen Precision, Compatible with Windows and Mac.", penDisplay, wacom);
        Product product_12 = new Product("Cintiq Pro 32", 3300, "USD", "Multi-Touch 4K Screen/32 Inch Creative Pen Display with Integrated Legs Including Wacom Pro Pen 2 Stylus with Pen Holder and Replacement Tips/Compatible with Windows and Mac.", penDisplay, wacom);
        Product product_13 = new Product("Intuos Pro Medium", 380, "USD", "The Intuos Pro medium graphics tablet by Wacom is the ideal drawing pad for digital sketching, professional graphic & fashion design as well as photo editing, 3D sculpting, illustrating and much more.", penTablet, wacom);
        Product product_14 = new Product("Intuos Small", 80, "USD", "Clever, compact and stylish: With the Intuos tablet from Wacom, sketching or retouching photos has never been easier/with Bluetooth connection, you have everything at your fingertips.", penTablet, wacom);



        productDataStore.add(product_1);
        productDataStore.add(product_2);
        productDataStore.add(product_3);
        productDataStore.add(product_4);
        productDataStore.add(product_5);
        productDataStore.add(product_6);
        productDataStore.add(product_7);
        productDataStore.add(product_8);
        productDataStore.add(product_9);
        productDataStore.add(product_10);
        productDataStore.add(product_11);
        productDataStore.add(product_12);
        productDataStore.add(product_13);
        productDataStore.add(product_14);


//        shopDatabaseManager.addProduct(product_1);
//        shopDatabaseManager.addProduct(product_2);
//        shopDatabaseManager.addProduct(product_3);
//        shopDatabaseManager.addProduct(product_4);
//        shopDatabaseManager.addProduct(product_5);
//        shopDatabaseManager.addProduct(product_6);
//        shopDatabaseManager.addProduct(product_7);
//        shopDatabaseManager.addProduct(product_8);
//        shopDatabaseManager.addProduct(product_9);
//        shopDatabaseManager.addProduct(product_10);
//        shopDatabaseManager.addProduct(product_11);
//        shopDatabaseManager.addProduct(product_12);
//        shopDatabaseManager.addProduct(product_13);
//        shopDatabaseManager.addProduct(product_14);
    }

    private void runDatabase() {
        try {
            shopDatabaseManager.setup();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getShopEmail() {
        return shopEmail;
    }

    public static void setShopEmail(String shopEmail) {
        Initializer.shopEmail = shopEmail;
    }

    public static String getShopEmailPassword() {
        return shopEmailPassword;
    }

    public static void setShopEmailPassword(String shopEmailPassword) {
        Initializer.shopEmailPassword = shopEmailPassword;
    }
}
