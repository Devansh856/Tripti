package com.example.tripti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newsAct extends AppCompatActivity {
    private ArrayList<model> arrayList;


    RecyclerView recview;
    TextView view;
    ProgressBar bar;
    newsadapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        bar=findViewById(R.id.newsprogress);
        arrayList=new ArrayList<>();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isConnected()) {
                     bar.setVisibility(View.INVISIBLE);
                    recview = (RecyclerView) findViewById(R.id.recview);
                    recview.setLayoutManager(new LinearLayoutManager(newsAct.this));

                    adapter = new newsadapter(arrayList,newsAct.this);
                    recview.setAdapter(adapter);
                    news();
                    adapter.notifyDataSetChanged();
                }
                else{

                }
            }
        },1500);


    }
    boolean isConnected()
    {
        ConnectivityManager con=(ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return( con.getActiveNetworkInfo()!=null && con.getActiveNetworkInfo().isConnectedOrConnecting());
    }

    public void news(){
        arrayList.clear();
        String baseurl="https://newsapi.org/";
        String url="v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&apiKey=6e0aeb2b88c34b8586ab082f0c5d809f";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getAPI api=retrofit.create(getAPI.class);
        Call<NewsModel> call=api.gNews(url);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel=response.body();

                ArrayList<model> models=newsModel.getArticles();
                //  Log.d("datxa", String.valueOf(models.size()));
                for(int i=0;i<models.size();i++){
                    arrayList.add(new model(models.get(i).getTitle(),models.get(i).getUrl(),models.get(i).getUrlToImage()));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(newsAct.this, "Error occurred", Toast.LENGTH_SHORT).show();

            }
        });
    }




}