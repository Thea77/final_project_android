package com.example.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> {

     List<Article> articleList;
    Context context;

    public ArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Article article = articleList.get(position);

        try {
            if(article != null && article.getCategory().getCat_name().matches("My_InstaAPP")){
                holder.tvItem.setText(article.getTitle());
                Glide.with(context)
                        .load(article.getImage())
                        .into((holder).imageView);
            }  else {

//                Toast.makeText(context, "Item is null", Toast.LENGTH_LONG).show();
            }
        }catch (NullPointerException e){
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public int getItemCount() {
        return articleList == null ? 0 : articleList.size();
    }


//
//    @NonNull
//    @Override
//    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
//        return new ItemViewHolder(view);
//    }


//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//       Article article = articleList.get(position);
////        if (holder instanceof ItemViewHolder) {
//            holder..setText(article.getCategory().cat_name);
//            Glide.with(context)
//                    .load(article.getImage())
//                    .into(((ItemViewHolder) holder).imageView);
//
////        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return articleList == null ? 0 : articleList.size();
//    }



     class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        ImageView imageView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageItem);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }

//    private class LoadingViewHolder extends RecyclerView.ViewHolder {
//
//        ProgressBar progressBar;
//
//        public LoadingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.progressBar);
//        }
//    }

}
