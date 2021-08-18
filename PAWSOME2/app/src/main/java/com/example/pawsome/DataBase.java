package com.example.pawsome;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Fav.class}, version =  1)
public abstract class DataBase extends RoomDatabase {
    public abstract FavDao favDao();
}