package com.example.weather.post;

public class PostItem {

    private String id;
    private String title;
    private String content;


    public PostItem(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
