package com.kiran.wallpaper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private static final String url = "https://pixabay.com/api/?key=31796805-225e7f8d8c42ac7ea2eb243fc&q=cute&image_type=photo&pretty=true";

    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    wallpaperAdapter adapter;
    ArrayList<wallpaperModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadData(url);


    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressBar);

        arrayList = new ArrayList<>();
        adapter = new wallpaperAdapter(arrayList,getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void loadData(String url) {
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: Response : " + response);

                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    Log.d(TAG, "onResponse: jsonArray : " + jsonArray);

                    for (int i=0; i< jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String largeImage = jsonObject.getString("largeImageURL");
                        Log.d(TAG, "onResponse: Position : "+i +"url is: "+ largeImage);
                        int likes = jsonObject.getInt("likes");
                        String name = jsonObject.getString("user");
                        wallpaperModel data = new wallpaperModel();
                        data.setLikes(likes);
                        data.setUrl(largeImage);
                        data.setName(name);
                        arrayList.add(data);
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Error is : " + error);
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}