package com.example.afinal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.profileItemViewHolder>{

    Context context;
    List<Article> articles;
    ClickProItemListener clickProItemListener;

    MainActivity activity;
    String name;

    public ProfileAdapter(MainActivity activity,String userName) {
        this.activity = activity;
        this.name = userName;
    }

    public ProfileAdapter(Context context, List<Article> articles, ClickProItemListener clickProItemListener) {
        this.context = context;
        this.articles = articles;
        this.clickProItemListener = clickProItemListener;
    }

    @NonNull
    @Override
    public profileItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new profileItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull profileItemViewHolder holder, int position) {
            Article article = articles.get(position);
        try {
            Log.e("TAG","myUser:" + name);


            if( article != null && article.getCategory().getCat_name().matches("View")
                    && article.getMyAuthor().getName().matches("Lim Sokunthea")
            ){
                holder.cardView.setVisibility(View.VISIBLE);


                String author = article.getMyAuthor().getName();
                String igmAuthor = article.getMyAuthor().getImage();
                Intent intent = new Intent("myAuth");
                intent.putExtra("Auth",author);
                intent.putExtra("igmAuthor",igmAuthor);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

//                Log.d("TAG", "TotalEq: " + holder.getAdapterPosition());

                Glide.with(context)
                        .load(article.getImage())
                        .into((holder).imgProfile);




                    holder.imgProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myItemID = article.getId();
                        if (myItemID != null){
                            clickProItemListener.onItemClick(myItemID);
                        }
                    }

            });

            }

        }catch (NullPointerException e){
//            Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }



    public interface  ClickProItemListener{
        void onItemClick(String id);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }



    class profileItemViewHolder extends RecyclerView.ViewHolder {
    ImageView imgProfile;
    CardView cardView;
    public profileItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imgProfile = itemView.findViewById(R.id.profileImageItem);
        cardView = itemView.findViewById(R.id.cardViewPro);

    }
}


}
