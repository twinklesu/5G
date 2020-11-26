package com.example.weather.post;

public class CommentItem {
    private String comment;
    private String Id;
    private String time;

    public CommentItem(String comment, String id, String time) {
        this.comment = comment;
        Id = id;
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
