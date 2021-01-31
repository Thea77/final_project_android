package com.example.afinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    APIArticleService apiArticleService;
    RetrofitInstance retrofitInstance;
    ProfileAdapter adapter;
    Author author = new Author();
    public static SweetAlertDialog pDialog;

    List<Article> rowArrayList;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.profileRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        TextView totalCount = view.findViewById(R.id.postCount);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, new IntentFilter("myAuth"));


        rowArrayList = new ArrayList<>();


        apiArticleService = RetrofitInstance.createService(APIArticleService.class);
        Call<ArticleResponse> call = apiArticleService.getArticles();
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                rowArrayList = response.body().articles;
                if (response.isSuccessful()){
                    try {

                        //  Log.d("TAG", "responseProfile: " + rowArrayList.size());
                        adapter = new ProfileAdapter(view.getContext(),rowArrayList,null);
                        adapter = new ProfileAdapter(getContext(), rowArrayList, new ProfileAdapter.ClickProItemListener() {
                            @Override
                            public void onItemClick(String id) {
                                openDetailActivity(id);
                            }
                        });

                        recyclerView.setAdapter(adapter);

                    }catch (Exception e){

                    }

                }

            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });


        return view;
    }
    //    get Author from Profile Adapter
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String Auth = intent.getStringExtra("Auth");
            String imgAuthor = intent.getStringExtra("igmAuthor");
//        Log.e("TAG","myUser : "+ Auth);
            ImageView myImProfile = getActivity().findViewById(R.id.circleImageProfile);
            TextView userName = getActivity().findViewById(R.id.userNameProfile);
            userName.setText(Auth);
            Glide.with(context)
                    .load(imgAuthor)
                    .into(myImProfile);

        }
    };
    private void openDetailActivity(String id) {
        Log.d("TAG","ID: "+id);
        Intent intent = new Intent(recyclerView.getContext(),DetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}