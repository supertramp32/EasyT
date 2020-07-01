package com.seshra.everestcab.models;

public class ServiceType {

    String image;
    String name;

    public ServiceType(String image, String name) {
        this.image = image;
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
