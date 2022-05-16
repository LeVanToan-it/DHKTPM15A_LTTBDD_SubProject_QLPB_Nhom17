package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.model;

import java.util.Arrays;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private byte[] imageProduct;

    public Product(int id, String name, String description, double price, byte[] imageProduct) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageProduct = imageProduct;
    }

    public Product(String name, String description, double price, byte[] imageProduct) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageProduct = imageProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageProduct=" + Arrays.toString(imageProduct) +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte[] imageProduct) {
        this.imageProduct = imageProduct;
    }

    public Product() {
    }
}
