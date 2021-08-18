package com.example.pawsome;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Fav> onefavs = new ArrayList<>();

    public FavAdapter(Context context, ArrayList<Fav> onefavs) {
        this.context = context;
        this.onefavs = onefavs;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_data_3,viewGroup,false);
        return new FavAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        holder.textView.setText(onefavs.get(position).getName());
        holder.textView3.setText(onefavs.get(position).getId());
        if(onefavs.get(position).getUrl()!=null)
            Picasso.get().load(onefavs.get(position).getUrl())
                    .resize(400,400)
                    .centerCrop()
                    .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return onefavs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public TextView textView3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.textView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            textView3 = (TextView)itemView.findViewById(R.id.textView3);
        }
    }
    public String query(ViewHolder viewHolder){
        return (String) viewHolder.textView3.getText();
    }
}
