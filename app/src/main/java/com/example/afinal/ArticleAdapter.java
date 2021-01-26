package com.example.afinal;

import android.content.Context;
import android.util.Log;
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

    Context context;
     List<Article> articleList;
    ClickItemListener clickItemListener;

//    public ArticleAdapter(List<Article> articleList, Context context) {
//        this.articleList = articleList;
//        this.context = context;
//    }

    public ArticleAdapter(Context context, List<Article> articleList, ClickItemListener clickItemListener) {
        this.context = context;
        this.articleList = articleList;
        this.clickItemListener = clickItemListener;
    }


//    public ArticleAdapter(NewFeedFragment newFeedFragment, List<Article> rowArrayList, ClickItemListener clickItemListener) {
//    }
//    public void addArticleToApi(List<Article> articles){
//        this.articleList.clear();
//        this.articleList = articles;
//        notifyDataSetChanged();
//    }

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

            if(article != null && article.getCategory().getCat_name().matches("View")){
                holder.tvItem.setText(article.getTitle());
                holder.txtDescription.setText(article.getDescription());
                Glide.with(context)
                        .load(article.getImage())
                        .into((holder).imageView);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myItemID = article.getId();
//                        Toast.makeText(context,"ID: "+myItemID,Toast.LENGTH_SHORT).show();
                        if (myItemID != null){
                            clickItemListener.onItemClick(myItemID);
                        }
                    }
                });

            }  else {
//                Toast.makeText(context, "Item is null", Toast.LENGTH_LONG).show();
            }

        }catch (NullPointerException e){
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public interface ClickItemListener {
        void onItemClick(String id);
    }



     class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem,txtDescription;
        ImageView imageView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageItem);
            tvItem = itemView.findViewById(R.id.tvItem);
            txtDescription = itemView.findViewById(R.id.textDescription);
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
