package com.example.afinal;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("_id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("image")
    String image;

    public Article(String id, String title, String description, String image, Category category, com.example.afinal.myAuthor myAuthor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.category = category;
        this.myAuthor = myAuthor;
    }

//    @SerializedName("category")
    private Category category;
    @SerializedName("author")
    private myAuthor myAuthor;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", category=" + category +
                ", myAuthor=" + myAuthor +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public com.example.afinal.myAuthor getMyAuthor() {
        return myAuthor;
    }

    public void setMyAuthor(com.example.afinal.myAuthor myAuthor) {
        this.myAuthor = myAuthor;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
class Category{
    @SerializedName("_id")
    String cat_id;
    @SerializedName("name")
    String cat_name;

    public Category(String cat_id, String cat_name) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cat_id='" + cat_id + '\'' +
                ", cat_name='" + cat_name + '\'' +
                '}';
    }
}


class myAuthor{
    @SerializedName("_id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("image")
    String image;

    @Override
    public String toString() {
        return "myAuthor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public myAuthor(String id, String name, String email, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }
}



