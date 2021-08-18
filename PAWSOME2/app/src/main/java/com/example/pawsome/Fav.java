package com.example.pawsome;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "favs",indices = {@Index(value = {"id","url","name"},unique = true)})
public class Fav {
    @NonNull
    @PrimaryKey
    private String id;
    private String url;
    public String name;

    public Fav(@NonNull String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
