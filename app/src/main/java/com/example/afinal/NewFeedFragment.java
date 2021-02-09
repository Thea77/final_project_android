package com.example.afinal;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFeedFragment extends Fragment {


    private RecyclerView recyclerView;
    APIArticleService apiArticleService;
    RetrofitInstance retrofitInstance;
    ArticleAdapter adapter;
    Article currentArticle;
    Category currentCategory;
    public static SweetAlertDialog pDialog;

//    Context context;
    //    List<Article> articles;
//    RecyclerView recyclerView;
//    boolean isLoading = false;

    List<Article> rowArrayList;
//    ArrayList<String> rowArrayList = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewFeedFragment newInstance(String param1, String param2) {
        NewFeedFragment fragment = new NewFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_feed, container, false);
        EditText search = view.findViewById(R.id.search);




        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        initAdapter();


        currentArticle = new Article();
        currentCategory = new Category();
        rowArrayList = new ArrayList<>();

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.show();


//        search with edittext
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filter(s.toString());
            }
        });


ImageView imgMenu = view.findViewById(R.id.menuItem);


//        articles = new ArrayList<>();
        apiArticleService = RetrofitInstance.createService(APIArticleService.class);
        Call<ArticleResponse> call = apiArticleService.getArticles();
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful()){
                    try {

                Log.d("TAG", "response: " + response.body().articles.toString());
                rowArrayList = response.body().articles;
                adapter = new ArticleAdapter(view.getContext(),rowArrayList,null);
                adapter = new ArticleAdapter(view.getContext(),rowArrayList, new ArticleAdapter.ClickItemListener() {
                    @Override
                    public void onItemClick(String id) {
//                        Log.d("click","Click");
                        openDetailActivity(id);
                    }


                    @Override
                    public void onItemMenuClick(Article article) {
                        currentArticle = article;
//                        currentCategory = category;
                        //creating a popup menu
                        final PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(R.id.menuItem));
                        //inflating menu from xml resource
                        MenuInflater menuInflater = popup.getMenuInflater();
                        menuInflater.inflate(R.menu.menu, popup.getMenu());

                        //adding click listener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.edit:
                                        handleUpdateArticle();
                                        break;
                                    case R.id.remove:
                                        handleDeleteArticleClick();
                                        break;
                                    case R.id.share:

                                        break;
                                }
                                return false;
                            }
                        });
                        //displaying the popup
                        popup.show();
                    }

                });
                recyclerView.setAdapter(adapter);


                    }catch (Exception e){
//                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }

        });

        return view;
    }

    private void filter(String text) {
        try {
            ArrayList<Article> filterList = new ArrayList<>();
            for (Article item : rowArrayList){
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())){
                    filterList.add(item);
                }
            }
            adapter.filterList(filterList);
        }catch (Exception e){

        }

    }


    private void handleUpdateArticle() {
            List<Article> articleList;
            List<Category> categoryList;

            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.update_post_dialog);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

            ImageView close = dialog.findViewById(R.id.close);
            EditText title = dialog.findViewById(R.id.title_update);
            EditText desc = dialog.findViewById(R.id.desc_update);
            EditText imageUrl= dialog.findViewById(R.id.imageUpdate);

            Button save = dialog.findViewById(R.id.save_update);
            Button cancel = dialog.findViewById(R.id.cancel_update);

            save.setText("Update");
            desc.setText(currentArticle.getDescription());
            title.setText(currentArticle.getTitle());
            imageUrl.setText(currentArticle.getImage());


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

//            articleList = new ArrayList<>();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> call = apiArticleService.updateArticle(currentArticle.getId(),
                            new UpdateArticle(title.getText().toString(),
                                    desc.getText().toString(),imageUrl.getText().toString())
                            );
                    new SweetAlertDialog(getContext())
                            .setTitleText("Updated!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(recyclerView.getContext(),HomePageActivity.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .show();

                }
            });

            dialog.show();

    }



    private void handleDeleteArticleClick() {
        Call<Void> call = apiArticleService.deleteArticle(currentArticle.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("You won't be able to recover this file!")
                        .setConfirmText("Delete!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                adapter.delete(currentArticle.getId());
//                                Toast.makeText(getContext(),"Delete Successfully!",Toast.LENGTH_SHORT).show();
                                if(response.isSuccessful()){
                                    Intent intent = new Intent(recyclerView.getContext(),HomePageActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    private void openDetailActivity(String id) {
        Log.d("TAG","ID: "+id);
        Intent intent = new Intent(recyclerView.getContext(),DetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

}
