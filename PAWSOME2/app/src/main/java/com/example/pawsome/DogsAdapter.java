package com.example.pawsome;


import android.app.Activity;
import android.content.Context;


import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {
    public Context context;
    public ArrayList<OneDog> oneDogs = new ArrayList<>();
    public ImageRes imageRes;
    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    public DogsAdapter(Context context, ArrayList<OneDog> oneDogs) {
        this.context = context;
        this.oneDogs = oneDogs;
    }

    @NonNull
    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_data,viewGroup,false);
        return new DogsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(oneDogs.get(i).getName());
        if(oneDogs.get(i).getImage() != null){
            Picasso.get()
                    .load(oneDogs.get(i).getImage().getUrl())
                    .resize(400, 400)
                    .centerCrop()
                    .into(viewHolder.imageView);
        }else if(oneDogs.get(i).getReference_image_id() != null){
            Call<ImageRes> call = apiInterface.getImage(oneDogs.get(i).getReference_image_id());
            call.enqueue(new Callback<ImageRes>() {
                @Override
                public void onResponse(Call<ImageRes> call, Response<ImageRes> response) {
                    imageRes = new ImageRes();
                    imageRes = response.body();
                    if(imageRes!=null){
                        Picasso.get().load(imageRes.getUrl())
                                .resize(400,400)
                                .centerCrop()
                                .into(viewHolder.imageView);
                    }
                }

                @Override
                public void onFailure(Call<ImageRes> call, Throwable t) {

                }
            });
        }


        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,viewHolder.imageView, ViewCompat.getTransitionName(viewHolder.imageView));
                Intent intent = new Intent(context,MainActivity2.class);

                if(oneDogs.get(i).getImage() != null) {
                    intent.putExtra("count",1);
                    intent.putExtra("url",oneDogs.get(i).getImage().getUrl());
                    intent.putExtra("image_id",oneDogs.get(i).getImage().getId());
                } else
                    intent.putExtra("count",0);


                intent.putStringArrayListExtra("name", new ArrayList<>(Arrays.asList("Name",""+oneDogs.get(i).getName())));
                intent.putStringArrayListExtra("id", new ArrayList<>(Arrays.asList("ID",""+oneDogs.get(i).getId())));
                intent.putStringArrayListExtra("bfor", new ArrayList<>(Arrays.asList("Bred For",""+oneDogs.get(i).getBred_for())));
                intent.putStringArrayListExtra("bgrp", new ArrayList<>(Arrays.asList("Bred Group",""+oneDogs.get(i).getBred_for())));
                intent.putStringArrayListExtra("ls", new ArrayList<>(Arrays.asList("Life Span",""+oneDogs.get(i).getLife_span())));
                intent.putStringArrayListExtra("origin", new ArrayList<>(Arrays.asList("Origin",""+oneDogs.get(i).getOrigin())));
                intent.putStringArrayListExtra("temper", new ArrayList<>(Arrays.asList("Temperament",""+oneDogs.get(i).getTemperament())));
                intent.putStringArrayListExtra("h(i)", new ArrayList<>(Arrays.asList("Height(imperial)",""+oneDogs.get(i).getHeight().getImperial())));
                intent.putStringArrayListExtra("h(m)", new ArrayList<>(Arrays.asList("Height(metric)",""+oneDogs.get(i).getHeight().getMetric())));
                intent.putStringArrayListExtra("w(i)", new ArrayList<>(Arrays.asList("Weight(imperial)",""+oneDogs.get(i).getWeight().getImperial())));
                intent.putStringArrayListExtra("w(m)", new ArrayList<>(Arrays.asList("Weight(metric)",""+oneDogs.get(i).getWeight().getMetric())));
                intent.putExtra("ref",""+oneDogs.get(i).getReference_image_id());
                context.startActivity(intent,options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return oneDogs == null ? 0 :oneDogs.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.textView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            this.itemClickListener.onItemClickListener(v,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }
    }

    public void add(OneDog oneDog){
        oneDogs.add(oneDog);
        notifyItemInserted(oneDogs.size()-1);
    }

    public void addAll(List<OneDog> oneDogList){
        for(OneDog dog: oneDogList){
            add(dog);}
    }

    public void addBottomItem(){
        add(new OneDog());
        notifyItemInserted(oneDogs.size() - 1);
    }

    public void removedLastEmptyItem(){
        int position = oneDogs.size()-1;
        OneDog item = getItem(position);

        if(item != null){
            oneDogs.remove(position);
            notifyItemRemoved(position);
        }
    }

    private OneDog getItem(int position){
        return oneDogs.get(position);
    }

}
