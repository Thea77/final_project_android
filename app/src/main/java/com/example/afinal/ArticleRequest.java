package com.example.afinal;

import com.google.gson.annotations.SerializedName;

public class ArticleRequest {


    String title;
    String description;
    String image;
    @SerializedName("category")
    private Category category;

    public ArticleRequest(String title, String description, String image, Category category) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", category=" + category +
                '}';
    }

    public ArticleRequest(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArticleRequest() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
