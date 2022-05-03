package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

public class User {
    public String name;
    public String image;
    public String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String image, String email) {
        this.name = name;
        this.image = image;
        this.email = email;

    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
