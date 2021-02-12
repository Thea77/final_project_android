package com.example.afinal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIArticleService {

    @GET("/api/articles")
//    Call<ArticleResponse> getArticles(
//            @Field("category") String category);

//    Call<ArticleResponse> getArticles(
//            @Query("page") int page,
//            @Query("size") int size);

    Call<ArticleResponse> getArticles();

    @GET("/api/articles/{id}")
    Call<ArticleResponseByID> getArticlesByID(@Path("id") String id);



    @POST("/api/articles")
    Call<ArticleResponse> addArticle(@Body ArticleRequest articleRequest );
    @POST("/api/author")
    Call<AuthorResponse> createAuthor(@Body AuthorRequest authorRequest );

    @GET("/api/category")
    Call<CategoryResponse> getCategories();

    @GET("api/author")
    Call<AuthorResponse> getAllAuthor();

    @PATCH("/api/articles/{id}")
    Call<Void> updateArticle(@Path("id") String id , @Body UpdateArticle updateArticle);

    @DELETE("/api/articles/{id}")
    Call<Void> deleteArticle(@Path("id") String id);


}
