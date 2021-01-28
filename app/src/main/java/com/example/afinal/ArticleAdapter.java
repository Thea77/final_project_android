package com.example.afinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> {

    Context context;
    List<Article> articleList;
    ClickItemListener clickItemListener;


    public ArticleAdapter(Context context, List<Article> articleList, ClickItemListener clickItemListener) {
        this.context = context;
        this.articleList = articleList;
        this.clickItemListener = clickItemListener;
    }


    public void delete(String position) {
        articleList.remove(position);
        notifyDataSetChanged();
    }

    public ArticleAdapter(ArrayList<Article> articleList) {
        this.articleList = articleList;
    }
    public void filterList(ArrayList<Article> filterdNames) {
        this.articleList = filterdNames;
        notifyDataSetChanged();
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

            if(article != null && article.getCategory().getCat_name().matches("View")){
                holder.tvItem.setText(article.getTitle());
                holder.txtDescription.setText(article.getDescription());
                Glide.with(context)
                        .load(article.getImage())
                        .into((holder).imageView);


                NewFeedFragment.pDialog.dismiss();
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myItemID = article.getId();
                        if (myItemID != null){
                            clickItemListener.onItemClick(myItemID);
                        }
                    }
                });
              holder.imgMenu.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      clickItemListener.onItemMenuClick(article);
//                      Toast.makeText(context, "Menu Click", Toast.LENGTH_LONG).show();
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
        void onItemMenuClick(Article article);
    }



     class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem,txtDescription;
        ImageView imageView,imgMenu;
        ProgressBar progressBar;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageItem);
            imgMenu = itemView.findViewById(R.id.menuItem);
            tvItem = itemView.findViewById(R.id.tvItem);
            txtDescription = itemView.findViewById(R.id.textDescription);
//            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }


//
//    class LoadingViewHolder extends RecyclerView.ViewHolder {
//        public ProgressBar progressBar;
//        public LoadingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.progressBar);
//        }
//    }

}
