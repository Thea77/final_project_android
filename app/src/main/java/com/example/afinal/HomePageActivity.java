package com.example.afinal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {

    APIArticleService apiArticleService;
    RetrofitInstance retrofitInstance;
    ArticleAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    ImageView img_add;
    List<Article> articleList;
    List<Category> categoryList;
    List<Author> authorList;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initUI();

        apiArticleService = RetrofitInstance.createService(APIArticleService.class);

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCustomDialog();
            }
        });






//view pager fragment
        viewPager = findViewById(R.id.view);
        tabLayout = findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.travel));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // view pager fragment

        }

    private void initUI() {
//        add_post = findViewById(R.id.add_post);
        img_add =  findViewById(R.id.img_add);
//        progressBar=findViewById(R.id.progressBar);


    }
    private void  myCustomDialog(){
        Dialog dialog = new Dialog(HomePageActivity.this);
        dialog.setContentView(R.layout.add_post_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id


        ImageView close = dialog.findViewById(R.id.close);
        EditText title = dialog.findViewById(R.id.title_dialog);
        EditText desc = dialog.findViewById(R.id.desc_dialog);
        EditText imageUrl= dialog.findViewById(R.id.imageURL);
        Spinner spinner = dialog.findViewById(R.id.spinner);
        Button save = dialog.findViewById(R.id.save_dialog);
        Button cancel = dialog.findViewById(R.id.cancel_dialog);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        articleList = new ArrayList<>();
        categoryList = new ArrayList<>();
//        authorList = new ArrayList<>();

        Call<CategoryResponse> call = apiArticleService.getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    try {
                        categoryList = response.body().getCategories();


                        String[] s = new String[categoryList.size()];
                        String[] catID = new String[categoryList.size()];

                        for(int i=0; i<categoryList.size();i++) {
                            s[i] = categoryList.get(i).getCat_name();
                            catID[i] = categoryList.get(i).getCat_id();
//                            Log.e("TAG", "cat: " + s[i]);
                            Log.e("TAG", "catID: " + catID[i]);

//
                            final ArrayAdapter arrayAdapter = new ArrayAdapter
                                    (getApplicationContext(), android.R.layout.simple_spinner_item, s);

                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(arrayAdapter);
//                        }
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String catSelected = adapterView.getSelectedItem().toString();

                                try{
//                                    Article article = new Article();
                                    Category category = new Category();
                                    Author author = new Author();

                                    category.setCat_name(catSelected);
                                    category.setCat_id(catID[i]);
//                                    article.setCategory(category);
                                    author.setName("anonymous");
                                    author.setId("6000f23560489c7e32233d4c");

//                                Toast.makeText(getApplicationContext(), "The option is:"+ category , Toast.LENGTH_SHORT).show();

                                save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Call<ArticleResponse> call =  apiArticleService.addArticle(new ArticleRequest(
                                                title.getText().toString(), desc.getText().toString(),
                                                imageUrl.getText().toString(), category, author
                                        ));

                                        call.enqueue(new Callback<ArticleResponse>() {
                                            @Override
                                            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {

                                                if(response.isSuccessful()){
                                                    try {
                                                        Log.d("TAG","addResponse: "+ response.body().articles.toString());
                                                        recreate();
                                                    }catch (RuntimeException e){
                                                        Log.e("TAG",e.getMessage());
                                                    }

                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                                                Log.e("Don't send! ",t.getMessage());
                                            }
                                        });
                                        dialog.dismiss();
                                        new SweetAlertDialog(HomePageActivity.this)
                                                .setTitleText("Create Successfully!")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        recreate();
                                                    }
                                                })
                                                .show();


                                    }
                                });
                                }catch (RuntimeException e){

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        }

                    }catch (NullPointerException e){
                        Toast.makeText(HomePageActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });



        dialog.show();
    }

}




