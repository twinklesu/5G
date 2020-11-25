package com.example.weather.post;

public class CommentItem {
    private String comment;
    private String Id;

    public CommentItem(String comment, String id) {
        this.comment = comment;
        Id = id;
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
}
