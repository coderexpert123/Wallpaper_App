package com.singh.yourwallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class FullscrennActivity extends AppCompatActivity {
String originalUrl="";
PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscrenn);


        Intent i = getIntent();
        originalUrl=i.getStringExtra("originalurl");


        photoView=findViewById(R.id.photoview);
        Glide.with(this).load(originalUrl).into(photoView);



    }

    public void setwallpaper(View view) {
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(this);
        Bitmap bitmap=((BitmapDrawable)photoView.getDrawable() ).getBitmap();

   try {
       wallpaperManager.setBitmap(bitmap);
       Toast.makeText(this, "Wallpaper Set Successfully !", Toast.LENGTH_SHORT).show();

   }catch (IOException e){
            e.printStackTrace();
   }


    }

    public void downloadwalllper(View view) {

        DownloadManager downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(originalUrl);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Download Start !", Toast.LENGTH_SHORT).show();
    }
}