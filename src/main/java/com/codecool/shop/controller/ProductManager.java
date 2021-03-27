package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;

public class ProductManager {


    ProductDao productDataStore;


//    public ProductManager() {
//    }

    public ProductManager(ProductDao useThisProductDataStore) {
        this.productDataStore = useThisProductDataStore;
    }

    public void addToCart(int itemID, ArrayList<Product> cart) {
        try{
            Product product = this.productDataStore.find(itemID);
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

        } catch (Exception e){
            throw e;
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
