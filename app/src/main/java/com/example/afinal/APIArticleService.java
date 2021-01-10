package com.example.afinal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIArticleService {

    @GET("/api/articles")
    Call<ArticleResponse> getArticles(@Query("page") int page, @Query("limit") int limit, @Query("total_page") int total_page);
}
