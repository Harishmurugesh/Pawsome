package com.example.pawsome;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v1/breeds")
    Call<List<OneDog>> getDogs(@Query("page") int page , @Query("limit") int limit);

    @GET("v1/breeds/search")
    Call<List<OneDog>> searchDogs(@Query("page") int page , @Query("limit") int limit , @Query("q") String q);

    @POST("v1/favourites?api_key=bea8221c-21c6-4407-ab09-7d2649d4d0b5")
    Call<FavRes> saveFav(@Body FavReq favReq);

    @GET("v1/images/{image_id}")
    Call<ImageRes> getImage(@Path("image_id") String image_id);

}