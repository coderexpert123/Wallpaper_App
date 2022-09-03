package com.singh.yourwallpaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<wallpaperviewholder> {

private Context context;
private List<WallpaperModel> wallpaperModels;

    public WallpaperAdapter(Context context, List<WallpaperModel> wallpaperModels) {
        this.context = context;
        this.wallpaperModels = wallpaperModels;
    }

    @NonNull
    @Override
    public wallpaperviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_class,parent,false);

        return new wallpaperviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull wallpaperviewholder holder,int position) {
            Glide.with(context).load(wallpaperModels.get(position).getMediumurl()).into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,FullscrennActivity.class)
                            .putExtra("originalurl",wallpaperModels.get(position).getOriginalurl()));


                }
            });
    }
    //rerturning the size of the wallaper model size

    @Override
    public int getItemCount() {

        return wallpaperModels.size();

    }
}
class wallpaperviewholder extends RecyclerView.ViewHolder{

ImageView imageView;

    public wallpaperviewholder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView);

    }
}
