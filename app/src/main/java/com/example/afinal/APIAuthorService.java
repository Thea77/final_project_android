package com.example.afinal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIAuthorService {
    @GET("api/author")
    Call<AuthorResponse> getAllAuthor();
}
