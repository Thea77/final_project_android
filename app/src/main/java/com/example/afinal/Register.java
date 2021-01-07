package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        APIAuthorService apiAuthorService;
        List<Author> authors;

        apiAuthorService = RetrofitInstance.createService(APIAuthorService.class);
        Call<AuthorResponse> call = apiAuthorService.getAllAuthor();
        call.enqueue(new Callback<AuthorResponse>() {
            @Override
            public void onResponse(Call<AuthorResponse> call, Response<AuthorResponse> response) {
                Log.d("TAG","Response: "+ response.body().authorList.toString());
            }

            @Override
            public void onFailure(Call<AuthorResponse> call, Throwable t) {
                Log.d("TAG","fail: "+ t.getMessage());
            }
        });
    }
}