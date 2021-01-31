package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.profileItemViewHolder>{

    Context context;
    List<Article> articles;
    ClickProItemListener clickProItemListener;

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

            if( article != null && article.getCategory().getCat_name().matches("View")
                    && article.getMyAuthor().getId().matches("6000f23560489c7e32233d4c")
            ){

                String author = article.getMyAuthor().getName();
                String igmAuthor = article.getMyAuthor().getImage();
                Intent intent = new Intent("myAuth");
                intent.putExtra("Auth",author);
                intent.putExtra("igmAuthor",igmAuthor);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


                Glide.with(context)
                        .load(article.getImage())
                        .into((holder).imgProfile);
                    Log.d("TAG", "TotalEq: " + articles.get(position));


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
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
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
    TextView totalPost;
    public profileItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imgProfile = itemView.findViewById(R.id.profileImageItem);
        totalPost = itemView.findViewById(R.id.postCount);

    }
}


}
