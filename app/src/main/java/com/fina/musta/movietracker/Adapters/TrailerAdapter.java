package com.fina.musta.movietracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fina.musta.movietracker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by musta on 1/2/2018.
 */

public class TrailerAdapter extends  RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    List<String> imageUrlList ;
    Context context;
    LayoutInflater inflater;

    public TrailerAdapter( Context context,List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.trailer_image_single, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, final int position) {
        Log.d("imageKey",imageUrlList.get(position));
      Picasso.with(context).load("https://img.youtube.com/vi/"+imageUrlList.get(position)+"/maxresdefault.jpg").into( holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+imageUrlList.get(position)));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.trailer_image_id);
        }
    }
}
