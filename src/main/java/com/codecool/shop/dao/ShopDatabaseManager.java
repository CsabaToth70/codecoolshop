package com.codecool.shop.dao;

import javax.sql.DataSource;
import java.sql.SQLException;

import com.codecool.shop.dao.implementation.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.user.User;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ShopDatabaseManager {
    private UserDao userDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private ProductDao productDao;

    public void setup() throws SQLException {
        System.out.println("setup");
        DataSource dataSource = connect();
        System.out.println(dataSource);
        productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
        supplierDao = new SupplierDaoJdbc(dataSource);
        productDao = new ProductDaoJdbc(dataSource);
        userDao = new UserDaoJdbc(dataSource);

    }

    public void saveUser(User user){
        userDao.save(user);
    }

    public User getUser(String email){
        User user = userDao.get(email);
        return user;
    }

    public void addSupplier(Supplier supplier){
        supplierDao.add(supplier);
    }

    public List<Supplier> getAllSuppliers(){
        return supplierDao.getAll();
    }

    public Supplier findSupplier(int supplier_id) {
        Supplier supplier = supplierDao.find(supplier_id);
        return supplier;
    }

    public void addProduct(Product product){
        productDao.add(product);
    }

    public List<Product> getAllProduct() {
        return productDao.getAll();
    }

    public void addProductCategory(ProductCategory productCategory){
        productCategoryDao.add(productCategory);
    }

    public List<ProductCategory> getAllProductCategory() {
        return productCategoryDao.getAll();
    }

    public ProductCategory findProductCategory(int product_category_id) {
        ProductCategory productCategory = productCategoryDao.find(product_category_id);
        return productCategory;
    }


    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "codecoolshop";
        String user = "user";
        String password = "tocsa";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


}
