package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {
    APIArticleService apiArticleService;
    RetrofitInstance retrofitInstance;
    ArticleAdapter adapter;
//    List<Article> articles;
    RecyclerView recyclerView;
    boolean isLoading = false;

    List<Article> rowArrayList;
//    ArrayList<String> rowArrayList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        initAdapter();

        rowArrayList = new ArrayList<>();
//        articles = new ArrayList<>();
        apiArticleService = RetrofitInstance.createService(APIArticleService.class);
        Call<ArticleResponse> call = apiArticleService.getArticles();
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                Log.d("TAG", "response: " + response.body().articles.toString());
                rowArrayList = response.body().articles;
                adapter = new ArticleAdapter(rowArrayList, HomePageActivity.this);
                recyclerView.setAdapter(adapter);


            }
                @Override
                public void onFailure(Call<ArticleResponse> call, Throwable t) {

                }
            });

        }

}




