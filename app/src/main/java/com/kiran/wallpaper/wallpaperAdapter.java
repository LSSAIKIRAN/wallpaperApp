package com.kiran.wallpaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class wallpaperAdapter extends RecyclerView.Adapter<wallpaperAdapter.viewHolder> {

    ArrayList<wallpaperModel> arrayList;
    Context context;

    public wallpaperAdapter(ArrayList<wallpaperModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        wallpaperModel data = arrayList.get(position);
        Glide.with(context).load(data.getUrl()).into(holder.imageView);
        holder.likes.setText(String.valueOf(data.getLikes()));
        holder.name.setText(data.getName());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView likes, name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            likes = itemView.findViewById(R.id.likesCount);
            name = itemView.findViewById(R.id.name);
        }
    }
}
