package com.example.pawsome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    FavDao favDao;
    private DataBase dataBase;
    private FavAdapter favAdapter;
    public ArrayList<Fav> favs;
    public Fav fav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        dataBase = Room.databaseBuilder(getContext(), DataBase.class, "FavDataBase").allowMainThreadQueries().build();
        favDao = dataBase.favDao();
        getResponse();
        return view;
    }

    private void getResponse() {

        favs = new ArrayList<>();
        favs = (ArrayList<Fav>) favDao.getFav();
        favAdapter = new FavAdapter(getContext(),favs);
        recyclerView.setAdapter(favAdapter);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            favs.remove(viewHolder.getAdapterPosition());
            favAdapter.notifyDataSetChanged();
            fav = favDao.getById(favAdapter.query((FavAdapter.ViewHolder) viewHolder));
            favDao.Delete(fav);
        }
    };
}
