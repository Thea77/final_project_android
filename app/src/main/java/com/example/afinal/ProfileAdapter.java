package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.profileItemViewHolder>{

    Context context;
    List<Article> articles;
    ClickProItemListener clickProItemListener;

    MainActivity activity;
    String userName;

    public ProfileAdapter() {
    }

//        public ProfileAdapter(MainActivity activity,List<Article> articleList,String userName) {
//        this.activity = activity;
//        this.articles = articleList;
//        this.userName = userName;
//    }


//    public void getUserName(String userName){
//         this.userName = userName;
////        Log.e("TAG","myUser:"+ userName);
//    }
//    public String myUser(){
//        return userName;
//    }

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
//        getUserName(userName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            String name = user.getDisplayName();
            Log.e("USER:", ":"+ name);

        try {


            if( article != null && article.getCategory().getCat_name().matches("View")
                && article.getMyAuthor().getName().matches(name)
            ){

//                holder.cardView.setVisibility(View.VISIBLE);


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
