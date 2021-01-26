package com.example.afinal;

import com.google.gson.annotations.SerializedName;

public class ArticleResponseByID {

    @SerializedName("data")
    Article article;

    public ArticleResponseByID(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
