package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;

public class ProductManager {

    int itemID;
    ProductDao productDataStore;
    ArrayList<Product> cart;


    public ProductManager() {
    }

    public ProductManager(int useThisItemID, ProductDao useThisProductDataStore, ArrayList<Product> useThisCart) {
        this.itemID = useThisItemID;
        this.productDataStore = useThisProductDataStore;
        this.cart = useThisCart;
    }

    public void addToCart(int itemID, ProductDao productDataStore, ArrayList<Product> cart) {
        Product product = productDataStore.find(itemID);
        if (cart.size() == 0) {
            product.setQuantity(1);
            cart.add(product);
        } else {
            boolean used = checkItems(itemID, cart);
            if (!used) {
                product.setQuantity(1);
                cart.add(product);
            }
        }
    }
    public boolean checkItems(int itemID, ArrayList<Product> cart) {
        for (Product item : cart) {
            if (item.getId() == itemID) {
                item.setQuantity(item.getQuantity() + 1);
                return true;
            }
        }
        return false;
    }


}
