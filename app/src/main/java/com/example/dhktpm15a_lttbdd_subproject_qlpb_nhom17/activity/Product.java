package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

public class Product {
    public String name;
    public String description;
    public double price;
    public int imageProduct;

    public Product(String name, String description, double price, int imageProduct) {
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

    public int getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.imageProduct = imageProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageProduct=" + imageProduct +
                '}';
    }
}
