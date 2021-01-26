package com.example.afinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFeedFragment extends Fragment {


    private RecyclerView recyclerView;
    APIArticleService apiArticleService;
    RetrofitInstance retrofitInstance;
    ArticleAdapter adapter;
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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
                adapter = new ArticleAdapter(view.getContext(),rowArrayList,null);

                adapter = new ArticleAdapter(view.getContext(),rowArrayList, new ArticleAdapter.ClickItemListener() {
                    @Override
                    public void onItemClick(String id) {
//                        Log.d("click","Click");
                        openDetailActivity(id);
                    }

                });
                recyclerView.setAdapter(adapter);



            }
            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });

        return view;
    }

    private void openDetailActivity(String id) {
        Log.d("TAG","ID: "+id);
        Intent intent = new Intent(recyclerView.getContext(),DetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

}
