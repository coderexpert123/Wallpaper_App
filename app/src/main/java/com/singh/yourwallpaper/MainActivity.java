package com.singh.yourwallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AbsListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {


RecyclerView recyclerView;
WallpaperAdapter wallpaperAdapter;
List<WallpaperModel> wallpaperModelList;
int pageNumer=1;
Boolean isScrolling=false;
int currentItem,totalItem,scrolloutItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        wallpaperModelList=new ArrayList<>();
        wallpaperAdapter =new WallpaperAdapter(this,wallpaperModelList);
        recyclerView.setAdapter(wallpaperAdapter);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItem=gridLayoutManager.getChildCount();
                totalItem=gridLayoutManager.getItemCount();
                scrolloutItem=gridLayoutManager.findFirstVisibleItemPosition();


                if (isScrolling && (currentItem+scrolloutItem==totalItem)){

                    isScrolling=false;
                    fetchWallpepr();

                }

            }
        });

        fetchWallpepr();
    }

    //function forfetching thye wallaper

    public void fetchWallpepr(){

        StringRequest request=new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/curated/?page="+pageNumer+"&per_page=80", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("photos");
                    int length=jsonArray.length();

                    for(int i=0;i<length;i++){

                        JSONObject object=jsonArray.getJSONObject(i);
                        int id=object.getInt("id");

                        JSONObject objectImages=object.getJSONObject("src");

                        String originalURl=objectImages.getString("original");
                        String mediumUrl=objectImages.getString("medium");

                        WallpaperModel wallpaperModel=new WallpaperModel(id,originalURl,mediumUrl);
                        wallpaperModelList.add(wallpaperModel);




                    }

                    wallpaperAdapter.notifyDataSetChanged();
                    pageNumer++;



                }catch (JSONException e){

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

               Map<String,String> params=new HashMap<>();
               params.put("Authorization","563492ad6f91700001000001602e1ea20f5546dd95f1a4cd4ab0c92c");

                return params;
            }
        };


        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);



    }
}