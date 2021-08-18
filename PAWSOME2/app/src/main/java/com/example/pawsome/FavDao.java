package com.example.pawsome;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface FavDao {

    @Query("SELECT * FROM favs")
    List<Fav> getFav();
    @Query("SELECT * FROM favs WHERE id=:id")
    Fav getById(String id);

    @Insert
    void Insert(Fav fav);
    @Delete
    void Delete(Fav fav);
}
