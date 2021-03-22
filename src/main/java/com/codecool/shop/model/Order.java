package com.codecool.shop.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    private String address;
    private int zipCode;
    private ArrayList<Product> items;
    private int total;

    public Order (int id, String firstName, String lastName, String email, String phoneNumber, String country, String city, String address, int zipCode, ArrayList<Product> items, int total) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
        this.items = items;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

//    private void writeObject(ObjectOutputStream out) throws IOException {
//
//    }
//    private void readObject(ObjectInputStream in)
//            throws IOException, ClassNotFoundException {
//    }
    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(total);
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Integer total = (Integer) ois.readObject();
        Integer a = total;
        this.setTotal(a);
    }
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                ", items=" + items +
                ", total=" + total +
                '}';
    }
}
