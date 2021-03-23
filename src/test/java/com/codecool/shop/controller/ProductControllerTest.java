package com.codecool.shop.controller;

import com.codecool.shop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private URL url;
    private HttpURLConnection con;
    private static ArrayList<Product> cart = new ArrayList<>();

    @BeforeEach
    void initTests () {
    }

    @Test
    void testaddToCart_productWithNullProductCategory() {
    }

    @Test
    void getLastModified() {
    }

    @Test
    void doPost() {
    }

    @Test
    void doPut() {
    }

    @Test
    void doDelete() {
    }

    @Test
    void doOptions() {
    }

    @Test
    void doTrace() {
    }

    @Test
    void service() {
    }
}