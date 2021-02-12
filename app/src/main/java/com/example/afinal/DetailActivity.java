package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    String id;
    ImageView image,arrowBack,profileImage;
    TextView title,des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        image = findViewById(R.id.imgDetail);
        title = findViewById(R.id.txtTitleDetail);
        des = findViewById(R.id.txtDescriptionDetail);
        arrowBack = findViewById(R.id.arrowBack);
        profileImage = findViewById(R.id.profile_image);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent= new Intent(DetailActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        APIArticleService apiArticleService = RetrofitInstance.createService(APIArticleService.class);
        Call<ArticleResponseByID> call = apiArticleService.getArticlesByID(id);
        call.enqueue(new Callback<ArticleResponseByID>() {
            @Override
            public void onResponse(Call<ArticleResponseByID> call, Response<ArticleResponseByID> response) {

                Log.d("TAG",response.body().article.toString());
                setDataToView(response.body().article);
            }

            @Override
            public void onFailure(Call<ArticleResponseByID> call, Throwable t) {

            }
        });

    }

    private void setDataToView(Article article) {
        try {
            title.setText(article.getTitle());
            des.setText(article.getDescription());
            Glide.with(DetailActivity.this)
                    .load(article.getImage())
                    .into(image);
            Glide.with(DetailActivity.this)
                    .load(article.getMyAuthor().image)
                    .into(profileImage);
        }catch (Exception e){

        }

//        Glide.with(DetailActivity.this)
//                .load("")
//                .into(profileImage);
    }
}