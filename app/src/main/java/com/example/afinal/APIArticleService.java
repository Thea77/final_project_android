package com.example.afinal;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIArticleService {

    @GET("/api/articles")
//    Call<ArticleResponse> getArticles(
//            @Field("category") String category);

//    Call<ArticleResponse> getArticles(
//            @Query("page") int page,
//            @Query("size") int size);

        Call<ArticleResponse> getArticles();
}
