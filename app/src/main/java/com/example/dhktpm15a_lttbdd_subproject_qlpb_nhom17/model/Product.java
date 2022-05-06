package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.model;

import java.util.Arrays;

public class Product {
    private String name;
    private String description;
    private double price;
    private byte[] imageProduct;

    public Product(String name, String description, double price, byte[] imageProduct) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageProduct = imageProduct;
    }

    public Product() {
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageProduct=" + Arrays.toString(imageProduct) +
                '}';
    }
}
