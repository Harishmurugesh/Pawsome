package com.example.pawsome;



import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstFragment extends Fragment {
    ArrayList<OneDog> oneDogs = new ArrayList<>();
    ArrayList<ImageRes> imageReses = new ArrayList<>();
    private DogsAdapter dogsAdapter;
    private RecyclerView recyclerView;
    public LinearLayoutManager linearLayoutManager;
    public ProgressBar progressBar;
    public SearchView searchView;


    private static final int START_PAGE = 0;
    private int TOTAL_PAGES = 20;
    private boolean isLoading = false;
    private boolean isLastpage = false;
    private int CURRENT_PAGE = START_PAGE;
    public String query = "";
    public int i =0;




    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_first, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.search_view);


        getResponse();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query=q;
                getResponse();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = newText;
                getResponse();
                return false;
            }
        });


        return view;
    }

    private void getResponse() {


        CURRENT_PAGE = START_PAGE;

        recyclerView.addOnScrollListener(new Pagination(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                CURRENT_PAGE += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            protected int getTotalPages() {
                return TOTAL_PAGES;
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }

            @Override
            protected boolean isLastPage() {
                return isLastpage;
            }
        });

        if ((query.equals(""))||(query.equals(null))) {
            Call<List<OneDog>> call = apiInterface.getDogs(START_PAGE,20);
            call.enqueue(new Callback<List<OneDog>>() {
                @Override
                public void onResponse(Call<List<OneDog>> call, Response<List<OneDog>> response) {
                    if (response.isSuccessful()) {

                        oneDogs = new ArrayList<>(response.body());
                        dogsAdapter = new DogsAdapter(getContext(), oneDogs);
                        recyclerView.setAdapter(dogsAdapter);
                        progressBar.setVisibility(View.INVISIBLE);

                        if (CURRENT_PAGE <= TOTAL_PAGES) {
                            dogsAdapter.addBottomItem();
                        } else {
                            isLastpage = true;
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<OneDog>> call, Throwable t) {

                }
            });

        }else {
            Call<List<OneDog>> call = apiInterface.searchDogs(START_PAGE,20,query);
            call.enqueue(new Callback<List<OneDog>>() {
                @Override
                public void onResponse(Call<List<OneDog>> call, Response<List<OneDog>> response) {
                    if (response.isSuccessful()) {

                        oneDogs = new ArrayList<>(response.body());
                        dogsAdapter = new DogsAdapter(getContext(), oneDogs);
                        recyclerView.setAdapter(dogsAdapter);
                        progressBar.setVisibility(View.INVISIBLE);

                        if (CURRENT_PAGE <= TOTAL_PAGES) {

                        } else {
                            isLastpage = true;
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<OneDog>> call, Throwable t) {

                }
            });

        }
    }
    private void loadNextPage() {

        if((query.equals(""))||(query==null)){

            Call<List<OneDog>> call = apiInterface.getDogs(CURRENT_PAGE, 20);
            call.enqueue(new Callback<List<OneDog>>() {
                @Override
                public void onResponse(Call<List<OneDog>> call, Response<List<OneDog>> response) {
                    if (response.isSuccessful()) {

                        oneDogs = new ArrayList<>(response.body());

                        dogsAdapter.removedLastEmptyItem();
                        isLoading = false;
                        dogsAdapter.addAll(oneDogs);
                        progressBar.setVisibility(View.INVISIBLE);


                        if (CURRENT_PAGE != TOTAL_PAGES) {
                            dogsAdapter.addBottomItem();
                        } else {
                            isLastpage = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<OneDog>> call, Throwable t) {

                }
            });
        }
        else{
            Call<List<OneDog>> call = apiInterface.searchDogs(CURRENT_PAGE, 20,query);
            call.enqueue(new Callback<List<OneDog>>() {
                @Override
                public void onResponse(Call<List<OneDog>> call, Response<List<OneDog>> response) {
                    if (response.isSuccessful()) {

                        oneDogs = new ArrayList<>(response.body());

                        dogsAdapter.removedLastEmptyItem();
                        isLoading = false;
                        dogsAdapter.addAll(oneDogs);
                        progressBar.setVisibility(View.INVISIBLE);


                        if (CURRENT_PAGE != TOTAL_PAGES) {

                        } else {
                            isLastpage = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<OneDog>> call, Throwable t) {

                }
            });

        }
    }
}

