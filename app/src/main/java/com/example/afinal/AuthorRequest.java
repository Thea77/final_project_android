package com.example.afinal;

public class AuthorRequest {

    String name;
    String image;

    public AuthorRequest(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public AuthorRequest() {
    }

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

    @Override
    public String toString() {
        return "AuthorRequest{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
