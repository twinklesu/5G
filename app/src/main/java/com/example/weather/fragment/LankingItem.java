package com.example.weather.fragment;

public class LankingItem {
    String lank;
    String id;

    public LankingItem(String id, String lank) {
        this.lank = lank;
        this.id = id;
    }

    public String getLank() {
        return lank;
    }

    public void setLank(String lank) {
        this.lank = lank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
