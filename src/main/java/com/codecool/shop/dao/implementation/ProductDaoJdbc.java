package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.config.Initializer.shopDatabaseManager;

public class ProductDaoJdbc implements ProductDao {
    private DataSource dataSource;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO products (name, price, currency, description, product_category_id, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDefaultCurrency().getCurrencyCode());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getProductCategory().getId());
            statement.setInt(6, product.getSupplier().getId());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, price, currency, description, product_category_id, supplier_id FROM products WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ProductCategory productCategory = shopDatabaseManager.findProductCategory(rs.getInt(5));
            Supplier supplier = shopDatabaseManager.findSupplier(rs.getInt(6));
            Product product = new Product(rs.getString(1), rs.getDouble(2), rs.getString(3), rs.getString(4), productCategory, supplier);
            product.setId(id);
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product category with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM products WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, product_category_id, supplier_id FROM products";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> productList = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double price = rs.getDouble(3);
                String currency = rs.getString(4);
                String description = rs.getString(5);
                int product_category_id = rs.getInt(6);
                int supplier_id = rs.getInt(7);
                ProductCategory product_category = shopDatabaseManager.findProductCategory(product_category_id);
                Supplier supplier = shopDatabaseManager.findSupplier(supplier_id);

                Product product = new Product(name, price, currency, description, product_category, supplier);
                product.setId(id);
                productList.add(product);
            }
            return productList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, product_category_id, supplier_id FROM products WHERE supplier_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, supplier.getId());
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double price = rs.getDouble(3);
                String currency = rs.getString(4);
                String description = rs.getString(5);
                int product_category_id = rs.getInt(6);
                int supplier_id = rs.getInt(7);
                ProductCategory product_category = shopDatabaseManager.findProductCategory(product_category_id);
                Supplier tmpSupplier = shopDatabaseManager.findSupplier(supplier_id);
                Product product = new Product(name, price, currency, description, product_category, tmpSupplier);
                product.setId(id);
                productList.add(product);
            }
            return productList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, product_category_id, supplier_id FROM products WHERE product_category_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, productCategory.getId());
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double price = rs.getDouble(3);
                String currency = rs.getString(4);
                String description = rs.getString(5);
                int product_category_id = rs.getInt(6);
                int supplier_id = rs.getInt(7);
                ProductCategory tmpProduct_category = shopDatabaseManager.findProductCategory(product_category_id);
                Supplier tmpSupplier = shopDatabaseManager.findSupplier(supplier_id);
                Product product = new Product(name, price, currency, description, tmpProduct_category, tmpSupplier);
                product.setId(id);
                productList.add(product);
            }
            return productList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
