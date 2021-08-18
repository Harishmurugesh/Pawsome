package com.example.pawsome;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> strings1 = new ArrayList<>();
    private ArrayList<String> strings2 = new ArrayList<>();

    public InfoAdapter(Context context, ArrayList<String> strings1, ArrayList<String> strings2) {
        this.context = context;
        this.strings1 = strings1;
        this.strings2 = strings2;
    }

    @NonNull
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_data_2,viewGroup,false);
        return new InfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.ViewHolder viewHolder, int position) {
        viewHolder.textView2.setText(strings1.get(position)+" : "+strings2.get(position));

    }

    @Override
    public int getItemCount() {
        return strings1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView2 = (TextView)itemView.findViewById(R.id.textView2);

        }
    }
}

