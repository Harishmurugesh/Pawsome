package com.example.pawsome;

import java.util.ArrayList;

public class ImageRes {

    public ArrayList<OneDog> breeds;
    public String url;

    public ArrayList<OneDog> getBreeds() {
        return breeds;
    }

    public void setBreeds(ArrayList<OneDog> breeds) {
        this.breeds = breeds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
