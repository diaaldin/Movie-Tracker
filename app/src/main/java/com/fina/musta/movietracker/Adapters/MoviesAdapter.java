package com.fina.musta.movietracker.Adapters;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.fina.musta.movietracker.activities.MovieDetailsActivity;
import com.fina.musta.movietracker.model.Movies;
import com.fina.musta.movietracker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by musta on 12/23/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    List<Movies> list ;
    Context context;
    private LayoutInflater mInflater;
    public MoviesAdapter(Context context, List<Movies> list){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_single_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        final int pos = position;
        final MoviesAdapter.ViewHolder hold;
       Picasso.with(context).load(list.get(position).getPosterPath()).into( holder.image);
       holder.rating.setRating(list.get(position).getVoteAverage()/2);
//       setAnimation(holder);
       hold = holder;
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent i = new Intent(context,MovieDetailsActivity.class);
               ActivityOptionsCompat options = ActivityOptionsCompat.
                       makeSceneTransitionAnimation((Activity) context,hold.image,ViewCompat.getTransitionName(hold.image));
               i.putExtra("imageUrl",list.get(pos).getPosterPath());
               i.putExtra("movieTitle",list.get(pos).getOriginalTitle());
               i.putExtra("movieId",list.get(pos).getId());
               i.putExtra("backdropPath",list.get(pos).getBackdropPath());
               i.putExtra("overview",list.get(pos).getOverview());
               i.putExtra("releaseDate",list.get(pos).getReleaseDate());
               i.putExtra("voteAverage",list.get(pos).getVoteAverage()+"");
               i.putExtra("user",((Activity) context).getIntent().getStringExtra("user"));
               context.startActivity(i,options.toBundle());


           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        RatingBar rating;
        ImageView image;
        public ViewHolder(View itemView) {

            super(itemView);
            image =itemView.findViewById(R.id.movie_image_id);
            rating = itemView.findViewById(R.id.rating_bar_id);
        }
    }

    private void setAnimation(ViewHolder holder) {
        //ObjectAnimator.ofFloat(holder.itemView, "translationX", 1000, 0)
          //      .setDuration(1000)
            //    .start();
        ObjectAnimator.ofFloat(holder.itemView,View.ALPHA,0,1).setDuration(1500).start();
    }
}
