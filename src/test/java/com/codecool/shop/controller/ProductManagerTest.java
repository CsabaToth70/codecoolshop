package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductManagerTest {
    ProductDao testProductDao;
    ArrayList<Product> testCart;
    int TestItemID;
    ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A tablet computer, " +
            "commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
    ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, " +
            "commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
    Supplier asus = new Supplier("Asus", "Computers");
    Supplier lenovo = new Supplier("Lenovo", "Computers");
    Product testProduct_1 = new Product("Asus X515MA-BR228T Notebook", 605, "USD",
            "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, asus);
    Product testProduct_2 = new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo);


    @BeforeEach
    void setUp() {
        testProductDao = Mockito.mock(ProductDao.class);
        testCart = new ArrayList<>();
    }

    @Test
    void test_addMoreProductToCart() {
        int TestItemID = 4;
        String requiredProductName = "Asus X515MA-BR228T Notebook";
        when(testProductDao.find(TestItemID)).thenReturn(testProduct_1);
        ProductManager testProductManager = new ProductManager(testProductDao);

        testProductManager.addToCart(TestItemID, testCart);

        assertEquals(requiredProductName, testCart.get(0).getName());
    }

    @Test
    void test_addOneProductToCart() {
        int TestItemID = 4;
        when(testProductDao.find(TestItemID)).thenReturn(testProduct_1);
        ProductManager testProductManager = new ProductManager(testProductDao);

        testProductManager.addToCart(TestItemID, testCart);

        TestItemID = 2;
        when(testProductDao.find(TestItemID)).thenReturn(testProduct_2);
        testProductManager = new ProductManager(testProductDao);
        testProductManager.addToCart(TestItemID, testCart);

        String requiredProductName = "Lenovo IdeaPad Miix 700";
        assertEquals(requiredProductName, testCart.get(1).getName());
    }

    @Test
    void test_addOneProductToCart_WhenIdIsZero_ThrowIndexOutOfBoundsException() {
        int TestItemID = 0;
        String requiredProductName = "Asus X515MA-BR228T Notebook";
        when(testProductDao.find(TestItemID)).thenReturn(testProduct_1);
        ProductManager testProductManager = new ProductManager(testProductDao);

        testProductManager.addToCart(TestItemID, testCart);

        assertThrows(IndexOutOfBoundsException.class, () -> testCart.get(1).getName());
    }

}