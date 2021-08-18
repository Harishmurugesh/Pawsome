package com.example.pawsome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    public ImageView imageView2;
    public ImageView imageView3;
    public ImageView imageView4;
    public ImageView imageView5;
    ArrayList<String> strings1 = new ArrayList<>();
    ArrayList<String> strings2 = new ArrayList<>();
    public InfoAdapter infoAdapter;
    private RecyclerView recyclerView2;
    public FavDao favDao;
    public DataBase dataBase;
    public FavReq favReq;
    public Fav fav;
    public String ref;
    public ImageRes imageRes;


    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public ApiInterface apiInterface = retrofit.create(ApiInterface.class);




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        dataBase = Room.databaseBuilder(this, DataBase.class, "FavDataBase").allowMainThreadQueries().build();
        favDao = dataBase.favDao();
        recyclerView2 = (RecyclerView)findViewById(R.id.recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        imageView5 = (ImageView)findViewById(R.id.imageView5);
        strings1 = new ArrayList<>();
        strings2 = new ArrayList<>();
        favReq = new FavReq();
        imageRes = new ImageRes();


        Intent intent = getIntent();

        strings1.add((intent.getStringArrayListExtra("name")).get(0));
        strings1.add((intent.getStringArrayListExtra("id")).get(0));
        strings1.add((intent.getStringArrayListExtra("bfor")).get(0));
        strings1.add((intent.getStringArrayListExtra("bgrp")).get(0));
        strings1.add((intent.getStringArrayListExtra("ls")).get(0));
        strings1.add((intent.getStringArrayListExtra("origin")).get(0));
        strings1.add((intent.getStringArrayListExtra("temper")).get(0));
        strings1.add((intent.getStringArrayListExtra("h(i)")).get(0));
        strings1.add((intent.getStringArrayListExtra("h(m)")).get(0));
        strings1.add((intent.getStringArrayListExtra("w(i)")).get(0));
        strings1.add((intent.getStringArrayListExtra("w(m)")).get(0));

        strings2.add((intent.getStringArrayListExtra("name")).get(1));
        strings2.add((intent.getStringArrayListExtra("id")).get(1));
        strings2.add((intent.getStringArrayListExtra("bfor")).get(1));
        strings2.add((intent.getStringArrayListExtra("bgrp")).get(1));
        strings2.add((intent.getStringArrayListExtra("ls")).get(1));
        strings2.add((intent.getStringArrayListExtra("origin")).get(1));
        strings2.add((intent.getStringArrayListExtra("temper")).get(1));
        strings2.add((intent.getStringArrayListExtra("h(i)")).get(1));
        strings2.add((intent.getStringArrayListExtra("h(m)")).get(1));
        strings2.add((intent.getStringArrayListExtra("w(i)")).get(1));
        strings2.add((intent.getStringArrayListExtra("w(m)")).get(1));




        if(intent.getIntExtra("count",0)==1)
            Picasso.get()
                    .load(intent.getStringExtra("url"))
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageView2);
        else if(intent.getStringExtra("ref")!=null){
            ref = intent.getStringExtra("ref");

            Call<ImageRes> call = apiInterface.getImage(ref);
            call.enqueue(new Callback<ImageRes>() {
                @Override
                public void onResponse(Call<ImageRes> call, Response<ImageRes> response) {
                    imageRes = response.body();
                    if(imageRes!=null){
                        Picasso.get().load(imageRes.getUrl())
                                .resize(400,400)
                                .centerCrop()
                                .into(imageView2);
                    }
                }

                @Override
                public void onFailure(Call<ImageRes> call, Throwable t) {

                }
            });
        }


        if(favDao.getById(intent.getStringArrayListExtra("id").get(1))==null){
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.INVISIBLE);
        }else{
            imageView4.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
        }

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getIntExtra("count",0)==1) {
                    fav = new Fav("" + intent.getStringArrayListExtra("id").get(1), "" + intent.getStringExtra("url"), "" + intent.getStringArrayListExtra("name").get(1));
                    favDao.Insert(fav);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                }else{
                    fav = new Fav("" + intent.getStringArrayListExtra("id").get(1), imageRes.getUrl(), "" + intent.getStringArrayListExtra("name").get(1));
                    favDao.Insert(fav);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                }
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favDao.Delete(favDao.getById(intent.getStringArrayListExtra("id").get(1)));
                imageView4.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.VISIBLE);
            }
        });

        Picasso.get().load(intent.getStringExtra("url"))
                .resize(400,400)
                .centerCrop()
                .into(imageView2);


        infoAdapter = new InfoAdapter(this,strings1,strings2);
        recyclerView2.setAdapter(infoAdapter);

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView2.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Some Title",null);
                Intent intent = new Intent(Intent.ACTION_SEND);
                Uri bitmapUri = Uri.parse(bitmapPath);

                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM,bitmapUri);
                intent.putExtra(Intent.EXTRA_TEXT,strings2.get(0)+" : "+strings1.get(0));
                startActivity(Intent.createChooser(intent,"Share Image"));
            }
        });
    }
}